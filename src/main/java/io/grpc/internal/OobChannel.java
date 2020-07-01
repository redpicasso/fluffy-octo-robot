package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.ClientCall;
import io.grpc.ConnectivityState;
import io.grpc.ConnectivityStateInfo;
import io.grpc.Context;
import io.grpc.EquivalentAddressGroup;
import io.grpc.InternalChannelz;
import io.grpc.InternalChannelz.ChannelStats;
import io.grpc.InternalChannelz.ChannelTrace.Event.Builder;
import io.grpc.InternalChannelz.ChannelTrace.Event.Severity;
import io.grpc.InternalInstrumented;
import io.grpc.InternalLogId;
import io.grpc.LoadBalancer.PickResult;
import io.grpc.LoadBalancer.PickSubchannelArgs;
import io.grpc.LoadBalancer.Subchannel;
import io.grpc.LoadBalancer.SubchannelPicker;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.internal.ManagedClientTransport.Listener;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
final class OobChannel extends ManagedChannel implements InternalInstrumented<ChannelStats> {
    private static final Logger log = Logger.getLogger(OobChannel.class.getName());
    private final String authority;
    private final CallTracer channelCallsTracer;
    @CheckForNull
    private final ChannelTracer channelTracer;
    private final InternalChannelz channelz;
    private final ScheduledExecutorService deadlineCancellationExecutor;
    private final DelayedClientTransport delayedTransport;
    private final Executor executor;
    private final ObjectPool<? extends Executor> executorPool;
    private final InternalLogId logId = InternalLogId.allocate(getClass().getName());
    private volatile boolean shutdown;
    private InternalSubchannel subchannel;
    private AbstractSubchannel subchannelImpl;
    private SubchannelPicker subchannelPicker;
    private final CountDownLatch terminatedLatch = new CountDownLatch(1);
    private final TimeProvider timeProvider;
    private final ClientTransportProvider transportProvider = new ClientTransportProvider() {
        public ClientTransport get(PickSubchannelArgs pickSubchannelArgs) {
            return OobChannel.this.delayedTransport;
        }

        public <ReqT> RetriableStream<ReqT> newRetriableStream(MethodDescriptor<ReqT, ?> methodDescriptor, CallOptions callOptions, Metadata metadata, Context context) {
            throw new UnsupportedOperationException("OobChannel should not create retriable streams");
        }
    };

    /* renamed from: io.grpc.internal.OobChannel$6 */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$io$grpc$ConnectivityState = new int[ConnectivityState.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$io$grpc$ConnectivityState[io.grpc.ConnectivityState.TRANSIENT_FAILURE.ordinal()] = 3;
     */
        static {
            /*
            r0 = io.grpc.ConnectivityState.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$io$grpc$ConnectivityState = r0;
            r0 = $SwitchMap$io$grpc$ConnectivityState;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = io.grpc.ConnectivityState.READY;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$io$grpc$ConnectivityState;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = io.grpc.ConnectivityState.IDLE;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$io$grpc$ConnectivityState;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = io.grpc.ConnectivityState.TRANSIENT_FAILURE;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.OobChannel.6.<clinit>():void");
        }
    }

    OobChannel(String str, ObjectPool<? extends Executor> objectPool, ScheduledExecutorService scheduledExecutorService, ChannelExecutor channelExecutor, CallTracer callTracer, @Nullable ChannelTracer channelTracer, InternalChannelz internalChannelz, TimeProvider timeProvider) {
        this.authority = (String) Preconditions.checkNotNull(str, "authority");
        this.executorPool = (ObjectPool) Preconditions.checkNotNull(objectPool, "executorPool");
        this.executor = (Executor) Preconditions.checkNotNull(objectPool.getObject(), "executor");
        this.deadlineCancellationExecutor = (ScheduledExecutorService) Preconditions.checkNotNull(scheduledExecutorService, "deadlineCancellationExecutor");
        this.delayedTransport = new DelayedClientTransport(this.executor, channelExecutor);
        this.channelz = (InternalChannelz) Preconditions.checkNotNull(internalChannelz);
        this.delayedTransport.start(new Listener() {
            public void transportInUse(boolean z) {
            }

            public void transportReady() {
            }

            public void transportShutdown(Status status) {
            }

            public void transportTerminated() {
                OobChannel.this.subchannelImpl.shutdown();
            }
        });
        this.channelCallsTracer = callTracer;
        this.channelTracer = channelTracer;
        this.timeProvider = timeProvider;
    }

    void setSubchannel(final InternalSubchannel internalSubchannel) {
        log.log(Level.FINE, "[{0}] Created with [{1}]", new Object[]{this, internalSubchannel});
        this.subchannel = internalSubchannel;
        this.subchannelImpl = new AbstractSubchannel() {
            public void shutdown() {
                internalSubchannel.shutdown(Status.UNAVAILABLE.withDescription("OobChannel is shutdown"));
            }

            ClientTransport obtainActiveTransport() {
                return internalSubchannel.obtainActiveTransport();
            }

            InternalInstrumented<ChannelStats> getInternalSubchannel() {
                return internalSubchannel;
            }

            public void requestConnection() {
                internalSubchannel.obtainActiveTransport();
            }

            public List<EquivalentAddressGroup> getAllAddresses() {
                return internalSubchannel.getAddressGroups();
            }

            public Attributes getAttributes() {
                return Attributes.EMPTY;
            }
        };
        this.subchannelPicker = new SubchannelPicker() {
            final PickResult result = PickResult.withSubchannel(OobChannel.this.subchannelImpl);

            public PickResult pickSubchannel(PickSubchannelArgs pickSubchannelArgs) {
                return this.result;
            }
        };
        this.delayedTransport.reprocess(this.subchannelPicker);
    }

    void updateAddresses(EquivalentAddressGroup equivalentAddressGroup) {
        this.subchannel.updateAddresses(Collections.singletonList(equivalentAddressGroup));
    }

    public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(MethodDescriptor<RequestT, ResponseT> methodDescriptor, CallOptions callOptions) {
        return new ClientCallImpl(methodDescriptor, callOptions.getExecutor() == null ? this.executor : callOptions.getExecutor(), callOptions, this.transportProvider, this.deadlineCancellationExecutor, this.channelCallsTracer, false);
    }

    public String authority() {
        return this.authority;
    }

    public boolean isTerminated() {
        return this.terminatedLatch.getCount() == 0;
    }

    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        return this.terminatedLatch.await(j, timeUnit);
    }

    public ConnectivityState getState(boolean z) {
        InternalSubchannel internalSubchannel = this.subchannel;
        if (internalSubchannel == null) {
            return ConnectivityState.IDLE;
        }
        return internalSubchannel.getState();
    }

    public ManagedChannel shutdown() {
        this.shutdown = true;
        this.delayedTransport.shutdown(Status.UNAVAILABLE.withDescription("OobChannel.shutdown() called"));
        return this;
    }

    public boolean isShutdown() {
        return this.shutdown;
    }

    public ManagedChannel shutdownNow() {
        this.shutdown = true;
        this.delayedTransport.shutdownNow(Status.UNAVAILABLE.withDescription("OobChannel.shutdownNow() called"));
        return this;
    }

    void handleSubchannelStateChange(final ConnectivityStateInfo connectivityStateInfo) {
        ChannelTracer channelTracer = this.channelTracer;
        if (channelTracer != null) {
            Builder builder = new Builder();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Entering ");
            stringBuilder.append(connectivityStateInfo.getState());
            stringBuilder.append(" state");
            channelTracer.reportEvent(builder.setDescription(stringBuilder.toString()).setSeverity(Severity.CT_INFO).setTimestampNanos(this.timeProvider.currentTimeNanos()).build());
        }
        int i = AnonymousClass6.$SwitchMap$io$grpc$ConnectivityState[connectivityStateInfo.getState().ordinal()];
        if (i == 1 || i == 2) {
            this.delayedTransport.reprocess(this.subchannelPicker);
        } else if (i == 3) {
            this.delayedTransport.reprocess(new SubchannelPicker() {
                final PickResult errorResult = PickResult.withError(connectivityStateInfo.getStatus());

                public PickResult pickSubchannel(PickSubchannelArgs pickSubchannelArgs) {
                    return this.errorResult;
                }
            });
        }
    }

    void handleSubchannelTerminated() {
        this.channelz.removeSubchannel(this);
        this.executorPool.returnObject(this.executor);
        this.terminatedLatch.countDown();
    }

    @VisibleForTesting
    Subchannel getSubchannel() {
        return this.subchannelImpl;
    }

    InternalSubchannel getInternalSubchannel() {
        return this.subchannel;
    }

    public ListenableFuture<ChannelStats> getStats() {
        ListenableFuture create = SettableFuture.create();
        ChannelStats.Builder builder = new ChannelStats.Builder();
        this.channelCallsTracer.updateBuilder(builder);
        ChannelTracer channelTracer = this.channelTracer;
        if (channelTracer != null) {
            channelTracer.updateBuilder(builder);
        }
        builder.setTarget(this.authority).setState(this.subchannel.getState()).setSubchannels(Collections.singletonList(this.subchannel));
        create.set(builder.build());
        return create;
    }

    public InternalLogId getLogId() {
        return this.logId;
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("logId", this.logId.getId()).add("authority", this.authority).toString();
    }

    public void resetConnectBackoff() {
        this.subchannel.resetConnectBackoff();
    }
}
