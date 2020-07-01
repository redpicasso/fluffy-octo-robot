package io.grpc.internal;

import com.google.common.base.Preconditions;
import io.grpc.CallCredentials2.MetadataApplier;
import io.grpc.CallOptions;
import io.grpc.Context;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

final class MetadataApplierImpl extends MetadataApplier {
    private final CallOptions callOptions;
    private final Context ctx;
    DelayedStream delayedStream;
    boolean finalized;
    private final Object lock = new Object();
    private final MethodDescriptor<?, ?> method;
    private final Metadata origHeaders;
    @GuardedBy("lock")
    @Nullable
    private ClientStream returnedStream;
    private final ClientTransport transport;

    MetadataApplierImpl(ClientTransport clientTransport, MethodDescriptor<?, ?> methodDescriptor, Metadata metadata, CallOptions callOptions) {
        this.transport = clientTransport;
        this.method = methodDescriptor;
        this.origHeaders = metadata;
        this.callOptions = callOptions;
        this.ctx = Context.current();
    }

    public void apply(Metadata metadata) {
        Preconditions.checkState(this.finalized ^ 1, "apply() or fail() already called");
        Preconditions.checkNotNull(metadata, "headers");
        this.origHeaders.merge(metadata);
        Context attach = this.ctx.attach();
        try {
            ClientStream newStream = this.transport.newStream(this.method, this.origHeaders, this.callOptions);
            finalizeWith(newStream);
        } finally {
            this.ctx.detach(attach);
        }
    }

    public void fail(Status status) {
        Preconditions.checkArgument(status.isOk() ^ 1, "Cannot fail with OK status");
        Preconditions.checkState(this.finalized ^ 1, "apply() or fail() already called");
        finalizeWith(new FailingClientStream(status));
    }

    /* JADX WARNING: Missing block: B:10:0x0019, code:
            if (r3.delayedStream == null) goto L_0x001c;
     */
    /* JADX WARNING: Missing block: B:11:0x001c, code:
            r1 = false;
     */
    /* JADX WARNING: Missing block: B:12:0x001d, code:
            com.google.common.base.Preconditions.checkState(r1, "delayedStream is null");
            r3.delayedStream.setStream(r4);
     */
    /* JADX WARNING: Missing block: B:13:0x0027, code:
            return;
     */
    private void finalizeWith(io.grpc.internal.ClientStream r4) {
        /*
        r3 = this;
        r0 = r3.finalized;
        r1 = 1;
        r0 = r0 ^ r1;
        r2 = "already finalized";
        com.google.common.base.Preconditions.checkState(r0, r2);
        r3.finalized = r1;
        r0 = r3.lock;
        monitor-enter(r0);
        r2 = r3.returnedStream;	 Catch:{ all -> 0x0028 }
        if (r2 != 0) goto L_0x0016;
    L_0x0012:
        r3.returnedStream = r4;	 Catch:{ all -> 0x0028 }
        monitor-exit(r0);	 Catch:{ all -> 0x0028 }
        return;
    L_0x0016:
        monitor-exit(r0);	 Catch:{ all -> 0x0028 }
        r0 = r3.delayedStream;
        if (r0 == 0) goto L_0x001c;
    L_0x001b:
        goto L_0x001d;
    L_0x001c:
        r1 = 0;
    L_0x001d:
        r0 = "delayedStream is null";
        com.google.common.base.Preconditions.checkState(r1, r0);
        r0 = r3.delayedStream;
        r0.setStream(r4);
        return;
    L_0x0028:
        r4 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0028 }
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.MetadataApplierImpl.finalizeWith(io.grpc.internal.ClientStream):void");
    }

    ClientStream returnStream() {
        synchronized (this.lock) {
            ClientStream clientStream;
            if (this.returnedStream == null) {
                this.delayedStream = new DelayedStream();
                clientStream = this.delayedStream;
                this.returnedStream = clientStream;
                return clientStream;
            }
            clientStream = this.returnedStream;
            return clientStream;
        }
    }
}
