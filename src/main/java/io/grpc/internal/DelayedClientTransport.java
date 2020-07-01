package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import io.grpc.CallOptions;
import io.grpc.Context;
import io.grpc.InternalChannelz.SocketStats;
import io.grpc.InternalLogId;
import io.grpc.LoadBalancer.PickSubchannelArgs;
import io.grpc.LoadBalancer.SubchannelPicker;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.internal.ClientTransport.PingCallback;
import io.grpc.internal.ManagedClientTransport.Listener;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.concurrent.Executor;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

final class DelayedClientTransport implements ManagedClientTransport {
    private final ChannelExecutor channelExecutor;
    private final Executor defaultAppExecutor;
    @GuardedBy("lock")
    @Nullable
    private SubchannelPicker lastPicker;
    @GuardedBy("lock")
    private long lastPickerVersion;
    private Listener listener;
    private final Object lock = new Object();
    private final InternalLogId lodId = InternalLogId.allocate(getClass().getName());
    @GuardedBy("lock")
    @Nonnull
    private Collection<PendingStream> pendingStreams = new LinkedHashSet();
    private Runnable reportTransportInUse;
    private Runnable reportTransportNotInUse;
    private Runnable reportTransportTerminated;
    @GuardedBy("lock")
    private Status shutdownStatus;

    private class PendingStream extends DelayedStream {
        private final PickSubchannelArgs args;
        private final Context context;

        /* synthetic */ PendingStream(DelayedClientTransport delayedClientTransport, PickSubchannelArgs pickSubchannelArgs, AnonymousClass1 anonymousClass1) {
            this(pickSubchannelArgs);
        }

        private PendingStream(PickSubchannelArgs pickSubchannelArgs) {
            this.context = Context.current();
            this.args = pickSubchannelArgs;
        }

        private void createRealStream(ClientTransport clientTransport) {
            Context attach = this.context.attach();
            try {
                ClientStream newStream = clientTransport.newStream(this.args.getMethodDescriptor(), this.args.getHeaders(), this.args.getCallOptions());
                setStream(newStream);
            } finally {
                this.context.detach(attach);
            }
        }

        public void cancel(Status status) {
            super.cancel(status);
            synchronized (DelayedClientTransport.this.lock) {
                if (DelayedClientTransport.this.reportTransportTerminated != null) {
                    boolean remove = DelayedClientTransport.this.pendingStreams.remove(this);
                    if (!DelayedClientTransport.this.hasPendingStreams() && remove) {
                        DelayedClientTransport.this.channelExecutor.executeLater(DelayedClientTransport.this.reportTransportNotInUse);
                        if (DelayedClientTransport.this.shutdownStatus != null) {
                            DelayedClientTransport.this.channelExecutor.executeLater(DelayedClientTransport.this.reportTransportTerminated);
                            DelayedClientTransport.this.reportTransportTerminated = null;
                        }
                    }
                }
            }
            DelayedClientTransport.this.channelExecutor.drain();
        }
    }

    DelayedClientTransport(Executor executor, ChannelExecutor channelExecutor) {
        this.defaultAppExecutor = executor;
        this.channelExecutor = channelExecutor;
    }

    public final Runnable start(final Listener listener) {
        this.listener = listener;
        this.reportTransportInUse = new Runnable() {
            public void run() {
                listener.transportInUse(true);
            }
        };
        this.reportTransportNotInUse = new Runnable() {
            public void run() {
                listener.transportInUse(false);
            }
        };
        this.reportTransportTerminated = new Runnable() {
            public void run() {
                listener.transportTerminated();
            }
        };
        return null;
    }

    public final ClientStream newStream(MethodDescriptor<?, ?> methodDescriptor, Metadata metadata, CallOptions callOptions) {
        try {
            ClientStream failingClientStream;
            PickSubchannelArgs pickSubchannelArgsImpl = new PickSubchannelArgsImpl(methodDescriptor, metadata, callOptions);
            synchronized (this.lock) {
                if (this.shutdownStatus != null) {
                    failingClientStream = new FailingClientStream(this.shutdownStatus);
                } else if (this.lastPicker == null) {
                    failingClientStream = createPendingStream(pickSubchannelArgsImpl);
                } else {
                    SubchannelPicker subchannelPicker = this.lastPicker;
                    long j = this.lastPickerVersion;
                    while (true) {
                        ClientTransport transportFromPickResult = GrpcUtil.getTransportFromPickResult(subchannelPicker.pickSubchannel(pickSubchannelArgsImpl), callOptions.isWaitForReady());
                        if (transportFromPickResult != null) {
                            ClientStream newStream = transportFromPickResult.newStream(pickSubchannelArgsImpl.getMethodDescriptor(), pickSubchannelArgsImpl.getHeaders(), pickSubchannelArgsImpl.getCallOptions());
                            this.channelExecutor.drain();
                            return newStream;
                        }
                        synchronized (this.lock) {
                            if (this.shutdownStatus != null) {
                                failingClientStream = new FailingClientStream(this.shutdownStatus);
                            } else if (j == this.lastPickerVersion) {
                                failingClientStream = createPendingStream(pickSubchannelArgsImpl);
                            } else {
                                subchannelPicker = this.lastPicker;
                                j = this.lastPickerVersion;
                            }
                        }
                    }
                }
            }
            this.channelExecutor.drain();
            return failingClientStream;
        } catch (Throwable th) {
            this.channelExecutor.drain();
        }
    }

    @GuardedBy("lock")
    private PendingStream createPendingStream(PickSubchannelArgs pickSubchannelArgs) {
        PendingStream pendingStream = new PendingStream(this, pickSubchannelArgs, null);
        this.pendingStreams.add(pendingStream);
        if (getPendingStreamsCount() == 1) {
            this.channelExecutor.executeLater(this.reportTransportInUse);
        }
        return pendingStream;
    }

    public final void ping(PingCallback pingCallback, Executor executor) {
        throw new UnsupportedOperationException("This method is not expected to be called");
    }

    public ListenableFuture<SocketStats> getStats() {
        ListenableFuture create = SettableFuture.create();
        create.set(null);
        return create;
    }

    /* JADX WARNING: Missing block: B:13:0x002a, code:
            r3.channelExecutor.drain();
     */
    /* JADX WARNING: Missing block: B:14:0x002f, code:
            return;
     */
    public final void shutdown(final io.grpc.Status r4) {
        /*
        r3 = this;
        r0 = r3.lock;
        monitor-enter(r0);
        r1 = r3.shutdownStatus;	 Catch:{ all -> 0x0030 }
        if (r1 == 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r0);	 Catch:{ all -> 0x0030 }
        return;
    L_0x0009:
        r3.shutdownStatus = r4;	 Catch:{ all -> 0x0030 }
        r1 = r3.channelExecutor;	 Catch:{ all -> 0x0030 }
        r2 = new io.grpc.internal.DelayedClientTransport$4;	 Catch:{ all -> 0x0030 }
        r2.<init>(r4);	 Catch:{ all -> 0x0030 }
        r1.executeLater(r2);	 Catch:{ all -> 0x0030 }
        r4 = r3.hasPendingStreams();	 Catch:{ all -> 0x0030 }
        if (r4 != 0) goto L_0x0029;
    L_0x001b:
        r4 = r3.reportTransportTerminated;	 Catch:{ all -> 0x0030 }
        if (r4 == 0) goto L_0x0029;
    L_0x001f:
        r4 = r3.channelExecutor;	 Catch:{ all -> 0x0030 }
        r1 = r3.reportTransportTerminated;	 Catch:{ all -> 0x0030 }
        r4.executeLater(r1);	 Catch:{ all -> 0x0030 }
        r4 = 0;
        r3.reportTransportTerminated = r4;	 Catch:{ all -> 0x0030 }
    L_0x0029:
        monitor-exit(r0);	 Catch:{ all -> 0x0030 }
        r4 = r3.channelExecutor;
        r4.drain();
        return;
    L_0x0030:
        r4 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0030 }
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.DelayedClientTransport.shutdown(io.grpc.Status):void");
    }

    public final void shutdownNow(Status status) {
        Runnable runnable;
        shutdown(status);
        synchronized (this.lock) {
            Collection<PendingStream> collection = this.pendingStreams;
            runnable = this.reportTransportTerminated;
            this.reportTransportTerminated = null;
            if (!this.pendingStreams.isEmpty()) {
                this.pendingStreams = Collections.emptyList();
            }
        }
        if (runnable != null) {
            for (PendingStream cancel : collection) {
                cancel.cancel(status);
            }
            this.channelExecutor.executeLater(runnable).drain();
        }
    }

    public final boolean hasPendingStreams() {
        boolean z;
        synchronized (this.lock) {
            z = !this.pendingStreams.isEmpty();
        }
        return z;
    }

    @VisibleForTesting
    final int getPendingStreamsCount() {
        int size;
        synchronized (this.lock) {
            size = this.pendingStreams.size();
        }
        return size;
    }

    /* JADX WARNING: Missing block: B:9:0x001e, code:
            r0 = new java.util.ArrayList();
            r1 = r1.iterator();
     */
    /* JADX WARNING: Missing block: B:11:0x002b, code:
            if (r1.hasNext() == false) goto L_0x0065;
     */
    /* JADX WARNING: Missing block: B:12:0x002d, code:
            r2 = (io.grpc.internal.DelayedClientTransport.PendingStream) r1.next();
            r3 = r8.pickSubchannel(io.grpc.internal.DelayedClientTransport.PendingStream.access$200(r2));
            r4 = io.grpc.internal.DelayedClientTransport.PendingStream.access$200(r2).getCallOptions();
            r3 = io.grpc.internal.GrpcUtil.getTransportFromPickResult(r3, r4.isWaitForReady());
     */
    /* JADX WARNING: Missing block: B:13:0x004b, code:
            if (r3 == null) goto L_0x0027;
     */
    /* JADX WARNING: Missing block: B:14:0x004d, code:
            r5 = r7.defaultAppExecutor;
     */
    /* JADX WARNING: Missing block: B:15:0x0053, code:
            if (r4.getExecutor() == null) goto L_0x0059;
     */
    /* JADX WARNING: Missing block: B:16:0x0055, code:
            r5 = r4.getExecutor();
     */
    /* JADX WARNING: Missing block: B:17:0x0059, code:
            r5.execute(new io.grpc.internal.DelayedClientTransport.AnonymousClass5(r7));
            r0.add(r2);
     */
    /* JADX WARNING: Missing block: B:18:0x0065, code:
            r8 = r7.lock;
     */
    /* JADX WARNING: Missing block: B:19:0x0067, code:
            monitor-enter(r8);
     */
    /* JADX WARNING: Missing block: B:22:0x006c, code:
            if (hasPendingStreams() != false) goto L_0x0070;
     */
    /* JADX WARNING: Missing block: B:23:0x006e, code:
            monitor-exit(r8);
     */
    /* JADX WARNING: Missing block: B:24:0x006f, code:
            return;
     */
    /* JADX WARNING: Missing block: B:25:0x0070, code:
            r7.pendingStreams.removeAll(r0);
     */
    /* JADX WARNING: Missing block: B:26:0x007b, code:
            if (r7.pendingStreams.isEmpty() == false) goto L_0x0084;
     */
    /* JADX WARNING: Missing block: B:27:0x007d, code:
            r7.pendingStreams = new java.util.LinkedHashSet();
     */
    /* JADX WARNING: Missing block: B:29:0x0088, code:
            if (hasPendingStreams() != false) goto L_0x00a3;
     */
    /* JADX WARNING: Missing block: B:30:0x008a, code:
            r7.channelExecutor.executeLater(r7.reportTransportNotInUse);
     */
    /* JADX WARNING: Missing block: B:31:0x0093, code:
            if (r7.shutdownStatus == null) goto L_0x00a3;
     */
    /* JADX WARNING: Missing block: B:33:0x0097, code:
            if (r7.reportTransportTerminated == null) goto L_0x00a3;
     */
    /* JADX WARNING: Missing block: B:34:0x0099, code:
            r7.channelExecutor.executeLater(r7.reportTransportTerminated);
            r7.reportTransportTerminated = null;
     */
    /* JADX WARNING: Missing block: B:35:0x00a3, code:
            monitor-exit(r8);
     */
    /* JADX WARNING: Missing block: B:36:0x00a4, code:
            r7.channelExecutor.drain();
     */
    /* JADX WARNING: Missing block: B:37:0x00a9, code:
            return;
     */
    final void reprocess(@javax.annotation.Nullable io.grpc.LoadBalancer.SubchannelPicker r8) {
        /*
        r7 = this;
        r0 = r7.lock;
        monitor-enter(r0);
        r7.lastPicker = r8;	 Catch:{ all -> 0x00af }
        r1 = r7.lastPickerVersion;	 Catch:{ all -> 0x00af }
        r3 = 1;
        r1 = r1 + r3;
        r7.lastPickerVersion = r1;	 Catch:{ all -> 0x00af }
        if (r8 == 0) goto L_0x00ad;
    L_0x000e:
        r1 = r7.hasPendingStreams();	 Catch:{ all -> 0x00af }
        if (r1 != 0) goto L_0x0016;
    L_0x0014:
        goto L_0x00ad;
    L_0x0016:
        r1 = new java.util.ArrayList;	 Catch:{ all -> 0x00af }
        r2 = r7.pendingStreams;	 Catch:{ all -> 0x00af }
        r1.<init>(r2);	 Catch:{ all -> 0x00af }
        monitor-exit(r0);	 Catch:{ all -> 0x00af }
        r0 = new java.util.ArrayList;
        r0.<init>();
        r1 = r1.iterator();
    L_0x0027:
        r2 = r1.hasNext();
        if (r2 == 0) goto L_0x0065;
    L_0x002d:
        r2 = r1.next();
        r2 = (io.grpc.internal.DelayedClientTransport.PendingStream) r2;
        r3 = r2.args;
        r3 = r8.pickSubchannel(r3);
        r4 = r2.args;
        r4 = r4.getCallOptions();
        r5 = r4.isWaitForReady();
        r3 = io.grpc.internal.GrpcUtil.getTransportFromPickResult(r3, r5);
        if (r3 == 0) goto L_0x0027;
    L_0x004d:
        r5 = r7.defaultAppExecutor;
        r6 = r4.getExecutor();
        if (r6 == 0) goto L_0x0059;
    L_0x0055:
        r5 = r4.getExecutor();
    L_0x0059:
        r4 = new io.grpc.internal.DelayedClientTransport$5;
        r4.<init>(r2, r3);
        r5.execute(r4);
        r0.add(r2);
        goto L_0x0027;
    L_0x0065:
        r8 = r7.lock;
        monitor-enter(r8);
        r1 = r7.hasPendingStreams();	 Catch:{ all -> 0x00aa }
        if (r1 != 0) goto L_0x0070;
    L_0x006e:
        monitor-exit(r8);	 Catch:{ all -> 0x00aa }
        return;
    L_0x0070:
        r1 = r7.pendingStreams;	 Catch:{ all -> 0x00aa }
        r1.removeAll(r0);	 Catch:{ all -> 0x00aa }
        r0 = r7.pendingStreams;	 Catch:{ all -> 0x00aa }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x00aa }
        if (r0 == 0) goto L_0x0084;
    L_0x007d:
        r0 = new java.util.LinkedHashSet;	 Catch:{ all -> 0x00aa }
        r0.<init>();	 Catch:{ all -> 0x00aa }
        r7.pendingStreams = r0;	 Catch:{ all -> 0x00aa }
    L_0x0084:
        r0 = r7.hasPendingStreams();	 Catch:{ all -> 0x00aa }
        if (r0 != 0) goto L_0x00a3;
    L_0x008a:
        r0 = r7.channelExecutor;	 Catch:{ all -> 0x00aa }
        r1 = r7.reportTransportNotInUse;	 Catch:{ all -> 0x00aa }
        r0.executeLater(r1);	 Catch:{ all -> 0x00aa }
        r0 = r7.shutdownStatus;	 Catch:{ all -> 0x00aa }
        if (r0 == 0) goto L_0x00a3;
    L_0x0095:
        r0 = r7.reportTransportTerminated;	 Catch:{ all -> 0x00aa }
        if (r0 == 0) goto L_0x00a3;
    L_0x0099:
        r0 = r7.channelExecutor;	 Catch:{ all -> 0x00aa }
        r1 = r7.reportTransportTerminated;	 Catch:{ all -> 0x00aa }
        r0.executeLater(r1);	 Catch:{ all -> 0x00aa }
        r0 = 0;
        r7.reportTransportTerminated = r0;	 Catch:{ all -> 0x00aa }
    L_0x00a3:
        monitor-exit(r8);	 Catch:{ all -> 0x00aa }
        r8 = r7.channelExecutor;
        r8.drain();
        return;
    L_0x00aa:
        r0 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x00aa }
        throw r0;
    L_0x00ad:
        monitor-exit(r0);	 Catch:{ all -> 0x00af }
        return;
    L_0x00af:
        r8 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x00af }
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.DelayedClientTransport.reprocess(io.grpc.LoadBalancer$SubchannelPicker):void");
    }

    public InternalLogId getLogId() {
        return this.lodId;
    }
}
