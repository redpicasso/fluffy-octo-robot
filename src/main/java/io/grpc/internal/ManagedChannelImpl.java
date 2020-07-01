package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ClientInterceptors;
import io.grpc.ClientStreamTracer;
import io.grpc.CompressorRegistry;
import io.grpc.ConnectivityState;
import io.grpc.ConnectivityStateInfo;
import io.grpc.Context;
import io.grpc.DecompressorRegistry;
import io.grpc.EquivalentAddressGroup;
import io.grpc.InternalChannelz;
import io.grpc.InternalChannelz.ChannelStats;
import io.grpc.InternalChannelz.ChannelTrace.Event.Builder;
import io.grpc.InternalChannelz.ChannelTrace.Event.Severity;
import io.grpc.InternalInstrumented;
import io.grpc.InternalLogId;
import io.grpc.LoadBalancer;
import io.grpc.LoadBalancer.Helper;
import io.grpc.LoadBalancer.PickResult;
import io.grpc.LoadBalancer.PickSubchannelArgs;
import io.grpc.LoadBalancer.Subchannel;
import io.grpc.LoadBalancer.SubchannelPicker;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.NameResolver;
import io.grpc.Status;
import io.grpc.internal.BackoffPolicy.Provider;
import io.grpc.internal.CallTracer.Factory;
import io.grpc.internal.ManagedClientTransport.Listener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
final class ManagedChannelImpl extends ManagedChannel implements InternalInstrumented<ChannelStats> {
    static final long IDLE_TIMEOUT_MILLIS_DISABLE = -1;
    @VisibleForTesting
    static final Status SHUTDOWN_NOW_STATUS = Status.UNAVAILABLE.withDescription("Channel shutdownNow invoked");
    @VisibleForTesting
    static final Status SHUTDOWN_STATUS = Status.UNAVAILABLE.withDescription("Channel shutdown invoked");
    @VisibleForTesting
    static final long SUBCHANNEL_SHUTDOWN_DELAY_SECONDS = 5;
    @VisibleForTesting
    static final Status SUBCHANNEL_SHUTDOWN_STATUS = Status.UNAVAILABLE.withDescription("Subchannel shutdown invoked");
    @VisibleForTesting
    static final Pattern URI_PATTERN = Pattern.compile("[a-zA-Z][a-zA-Z0-9+.-]*:/.*");
    static final Logger logger = Logger.getLogger(ManagedChannelImpl.class.getName());
    private final Provider backoffPolicyProvider;
    private final Factory callTracerFactory;
    private final long channelBufferLimit;
    private final ChannelBufferMeter channelBufferUsed = new ChannelBufferMeter();
    private final CallTracer channelCallTracer;
    private final ChannelExecutor channelExecutor = new PanicChannelExecutor();
    private final ConnectivityStateManager channelStateManager = new ConnectivityStateManager();
    @CheckForNull
    private final ChannelTracer channelTracer;
    private final InternalChannelz channelz;
    private final CompressorRegistry compressorRegistry;
    private final DecompressorRegistry decompressorRegistry;
    private final DelayedClientTransport delayedTransport;
    private final Listener delayedTransportListener = new DelayedTransportListener();
    private final Executor executor;
    private final ObjectPool<? extends Executor> executorPool;
    private boolean fullStreamDecompression;
    @CheckForNull
    private Boolean haveBackends;
    private final long idleTimeoutMillis;
    private final Rescheduler idleTimer;
    @VisibleForTesting
    final InUseStateAggregator<Object> inUseStateAggregator = new IdleModeStateAggregator();
    private final Channel interceptorChannel;
    @Nullable
    private Map<String, Object> lastServiceConfig;
    @Nullable
    private LbHelperImpl lbHelper;
    private final LoadBalancer.Factory loadBalancerFactory;
    private final InternalLogId logId = InternalLogId.allocate(getClass().getName());
    private final int maxTraceEvents;
    private NameResolver nameResolver;
    @Nullable
    private BackoffPolicy nameResolverBackoffPolicy;
    private final NameResolver.Factory nameResolverFactory;
    private final Attributes nameResolverParams;
    @Nullable
    private NameResolverRefresh nameResolverRefresh;
    @Nullable
    private ScheduledFuture<?> nameResolverRefreshFuture;
    private boolean nameResolverStarted;
    private final Set<OobChannel> oobChannels = new HashSet(1, 0.75f);
    private final ObjectPool<? extends Executor> oobExecutorPool;
    private boolean panicMode;
    private final long perRpcBufferLimit;
    private final boolean retryEnabled;
    private final ServiceConfigInterceptor serviceConfigInterceptor;
    private final AtomicBoolean shutdown = new AtomicBoolean(false);
    private boolean shutdownNowed;
    private final Supplier<Stopwatch> stopwatchSupplier;
    @Nullable
    private volatile SubchannelPicker subchannelPicker;
    private final Set<InternalSubchannel> subchannels = new HashSet(16, 0.75f);
    private final String target;
    private volatile boolean terminated;
    private final CountDownLatch terminatedLatch = new CountDownLatch(1);
    private volatile boolean terminating;
    @Nullable
    private Throttle throttle;
    private final TimeProvider timeProvider;
    private final ClientTransportFactory transportFactory;
    private final ClientTransportProvider transportProvider = new ChannelTransportProvider();
    private final UncommittedRetriableStreamsRegistry uncommittedRetriableStreamsRegistry = new UncommittedRetriableStreamsRegistry();
    @Nullable
    private final String userAgent;

    private class IdleModeTimer implements Runnable {
        private IdleModeTimer() {
        }

        public void run() {
            ManagedChannelImpl.this.enterIdleMode();
        }
    }

    @VisibleForTesting
    class NameResolverRefresh implements Runnable {
        boolean cancelled;

        NameResolverRefresh() {
        }

        public void run() {
            if (!this.cancelled) {
                ManagedChannelImpl.this.nameResolverRefreshFuture = null;
                ManagedChannelImpl.this.nameResolverRefresh = null;
                if (ManagedChannelImpl.this.nameResolver != null) {
                    ManagedChannelImpl.this.nameResolver.refresh();
                }
            }
        }
    }

    private final class UncommittedRetriableStreamsRegistry {
        final Object lock;
        @GuardedBy("lock")
        Status shutdownStatus;
        @GuardedBy("lock")
        Collection<ClientStream> uncommittedRetriableStreams;

        private UncommittedRetriableStreamsRegistry() {
            this.lock = new Object();
            this.uncommittedRetriableStreams = new HashSet();
        }

        /* JADX WARNING: Missing block: B:9:0x0012, code:
            if (r1 == false) goto L_0x001d;
     */
        /* JADX WARNING: Missing block: B:10:0x0014, code:
            io.grpc.internal.ManagedChannelImpl.access$1600(r2.this$0).shutdown(r3);
     */
        /* JADX WARNING: Missing block: B:11:0x001d, code:
            return;
     */
        void onShutdown(io.grpc.Status r3) {
            /*
            r2 = this;
            r0 = r2.lock;
            monitor-enter(r0);
            r1 = r2.shutdownStatus;	 Catch:{ all -> 0x001e }
            if (r1 == 0) goto L_0x0009;
        L_0x0007:
            monitor-exit(r0);	 Catch:{ all -> 0x001e }
            return;
        L_0x0009:
            r2.shutdownStatus = r3;	 Catch:{ all -> 0x001e }
            r1 = r2.uncommittedRetriableStreams;	 Catch:{ all -> 0x001e }
            r1 = r1.isEmpty();	 Catch:{ all -> 0x001e }
            monitor-exit(r0);	 Catch:{ all -> 0x001e }
            if (r1 == 0) goto L_0x001d;
        L_0x0014:
            r0 = io.grpc.internal.ManagedChannelImpl.this;
            r0 = r0.delayedTransport;
            r0.shutdown(r3);
        L_0x001d:
            return;
        L_0x001e:
            r3 = move-exception;
            monitor-exit(r0);	 Catch:{ all -> 0x001e }
            throw r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.ManagedChannelImpl.UncommittedRetriableStreamsRegistry.onShutdown(io.grpc.Status):void");
        }

        void onShutdownNow(Status status) {
            onShutdown(status);
            synchronized (this.lock) {
                Collection<ClientStream> arrayList = new ArrayList(this.uncommittedRetriableStreams);
            }
            for (ClientStream cancel : arrayList) {
                cancel.cancel(status);
            }
            ManagedChannelImpl.this.delayedTransport.shutdownNow(status);
        }

        @Nullable
        Status add(RetriableStream<?> retriableStream) {
            synchronized (this.lock) {
                if (this.shutdownStatus != null) {
                    Status status = this.shutdownStatus;
                    return status;
                }
                this.uncommittedRetriableStreams.add(retriableStream);
                return null;
            }
        }

        void remove(RetriableStream<?> retriableStream) {
            Status status;
            synchronized (this.lock) {
                this.uncommittedRetriableStreams.remove(retriableStream);
                if (this.uncommittedRetriableStreams.isEmpty()) {
                    status = this.shutdownStatus;
                    this.uncommittedRetriableStreams = new HashSet();
                } else {
                    status = null;
                }
            }
            if (status != null) {
                ManagedChannelImpl.this.delayedTransport.shutdown(status);
            }
        }
    }

    private final class ChannelTransportProvider implements ClientTransportProvider {

        /* renamed from: io.grpc.internal.ManagedChannelImpl$ChannelTransportProvider$1RetryStream */
        final class AnonymousClass1RetryStream extends RetriableStream<ReqT> {
            final /* synthetic */ ChannelTransportProvider this$1;
            final /* synthetic */ CallOptions val$callOptions;
            final /* synthetic */ Context val$context;
            final /* synthetic */ Metadata val$headers;
            final /* synthetic */ MethodDescriptor val$method;

            AnonymousClass1RetryStream(ChannelTransportProvider channelTransportProvider, MethodDescriptor methodDescriptor, Metadata metadata, CallOptions callOptions, Context context) {
                ChannelTransportProvider channelTransportProvider2 = channelTransportProvider;
                CallOptions callOptions2 = callOptions;
                this.this$1 = channelTransportProvider2;
                this.val$method = methodDescriptor;
                this.val$headers = metadata;
                this.val$callOptions = callOptions2;
                this.val$context = context;
                ChannelBufferMeter access$1900 = ManagedChannelImpl.this.channelBufferUsed;
                long access$2000 = ManagedChannelImpl.this.perRpcBufferLimit;
                long access$2100 = ManagedChannelImpl.this.channelBufferLimit;
                Executor access$2200 = ManagedChannelImpl.this.getCallExecutor(callOptions2);
                ScheduledExecutorService scheduledExecutorService = ManagedChannelImpl.this.transportFactory.getScheduledExecutorService();
                Provider provider = (Provider) callOptions2.getOption(ServiceConfigInterceptor.RETRY_POLICY_KEY);
                Provider provider2 = (Provider) callOptions2.getOption(ServiceConfigInterceptor.HEDGING_POLICY_KEY);
                Throttle access$2400 = ManagedChannelImpl.this.throttle;
                super(methodDescriptor, metadata, access$1900, access$2000, access$2100, access$2200, scheduledExecutorService, provider, provider2, access$2400);
            }

            Status prestart() {
                return ManagedChannelImpl.this.uncommittedRetriableStreamsRegistry.add(this);
            }

            void postCommit() {
                ManagedChannelImpl.this.uncommittedRetriableStreamsRegistry.remove(this);
            }

            ClientStream newSubstream(ClientStreamTracer.Factory factory, Metadata metadata) {
                CallOptions withStreamTracerFactory = this.val$callOptions.withStreamTracerFactory(factory);
                ClientTransport clientTransport = this.this$1.get(new PickSubchannelArgsImpl(this.val$method, metadata, withStreamTracerFactory));
                Context attach = this.val$context.attach();
                try {
                    ClientStream newStream = clientTransport.newStream(this.val$method, metadata, withStreamTracerFactory);
                    return newStream;
                } finally {
                    this.val$context.detach(attach);
                }
            }
        }

        private ChannelTransportProvider() {
        }

        public ClientTransport get(PickSubchannelArgs pickSubchannelArgs) {
            SubchannelPicker access$1400 = ManagedChannelImpl.this.subchannelPicker;
            if (ManagedChannelImpl.this.shutdown.get()) {
                return ManagedChannelImpl.this.delayedTransport;
            }
            if (access$1400 == null) {
                ManagedChannelImpl.this.channelExecutor.executeLater(new Runnable() {
                    public void run() {
                        ManagedChannelImpl.this.exitIdleMode();
                    }
                }).drain();
                return ManagedChannelImpl.this.delayedTransport;
            }
            ClientTransport transportFromPickResult = GrpcUtil.getTransportFromPickResult(access$1400.pickSubchannel(pickSubchannelArgs), pickSubchannelArgs.getCallOptions().isWaitForReady());
            if (transportFromPickResult != null) {
                return transportFromPickResult;
            }
            return ManagedChannelImpl.this.delayedTransport;
        }

        public <ReqT> RetriableStream<ReqT> newRetriableStream(MethodDescriptor<ReqT, ?> methodDescriptor, CallOptions callOptions, Metadata metadata, Context context) {
            Preconditions.checkState(ManagedChannelImpl.this.retryEnabled, "retry should be enabled");
            return new AnonymousClass1RetryStream(this, methodDescriptor, metadata, callOptions, context);
        }
    }

    private final class DelayedTransportListener implements Listener {
        public void transportReady() {
        }

        private DelayedTransportListener() {
        }

        public void transportShutdown(Status status) {
            Preconditions.checkState(ManagedChannelImpl.this.shutdown.get(), "Channel must have been shut down");
        }

        public void transportInUse(boolean z) {
            ManagedChannelImpl.this.inUseStateAggregator.updateObjectInUse(ManagedChannelImpl.this.delayedTransport, z);
        }

        public void transportTerminated() {
            Preconditions.checkState(ManagedChannelImpl.this.shutdown.get(), "Channel must have been shut down");
            ManagedChannelImpl.this.terminating = true;
            ManagedChannelImpl.this.shutdownNameResolverAndLoadBalancer(false);
            ManagedChannelImpl.this.maybeShutdownNowSubchannels();
            ManagedChannelImpl.this.maybeTerminateChannel();
        }
    }

    private final class IdleModeStateAggregator extends InUseStateAggregator<Object> {
        private IdleModeStateAggregator() {
        }

        void handleInUse() {
            ManagedChannelImpl.this.exitIdleMode();
        }

        void handleNotInUse() {
            if (!ManagedChannelImpl.this.shutdown.get()) {
                ManagedChannelImpl.this.rescheduleIdleTimer();
            }
        }
    }

    private class LbHelperImpl extends Helper {
        LoadBalancer lb;
        final NameResolver nr;

        LbHelperImpl(NameResolver nameResolver) {
            this.nr = (NameResolver) Preconditions.checkNotNull(nameResolver, "NameResolver");
        }

        private void handleInternalSubchannelState(ConnectivityStateInfo connectivityStateInfo) {
            if (connectivityStateInfo.getState() == ConnectivityState.TRANSIENT_FAILURE || connectivityStateInfo.getState() == ConnectivityState.IDLE) {
                this.nr.refresh();
            }
        }

        public AbstractSubchannel createSubchannel(List<EquivalentAddressGroup> list, Attributes attributes) {
            Attributes attributes2 = attributes;
            Preconditions.checkNotNull(list, "addressGroups");
            Preconditions.checkNotNull(attributes2, "attrs");
            Preconditions.checkState(ManagedChannelImpl.this.terminated ^ 1, "Channel is terminated");
            final AbstractSubchannel subchannelImpl = new SubchannelImpl(attributes2);
            ChannelTracer channelTracer = null;
            long currentTimeNanos = ManagedChannelImpl.this.timeProvider.currentTimeNanos();
            if (ManagedChannelImpl.this.maxTraceEvents > 0) {
                channelTracer = new ChannelTracer(ManagedChannelImpl.this.maxTraceEvents, currentTimeNanos, "Subchannel");
            }
            ChannelTracer channelTracer2 = channelTracer;
            InternalSubchannel internalSubchannel = r1;
            long j = currentTimeNanos;
            AbstractSubchannel abstractSubchannel = subchannelImpl;
            InternalSubchannel internalSubchannel2 = new InternalSubchannel(list, ManagedChannelImpl.this.authority(), ManagedChannelImpl.this.userAgent, ManagedChannelImpl.this.backoffPolicyProvider, ManagedChannelImpl.this.transportFactory, ManagedChannelImpl.this.transportFactory.getScheduledExecutorService(), ManagedChannelImpl.this.stopwatchSupplier, ManagedChannelImpl.this.channelExecutor, new Callback() {
                void onTerminated(InternalSubchannel internalSubchannel) {
                    ManagedChannelImpl.this.subchannels.remove(internalSubchannel);
                    ManagedChannelImpl.this.channelz.removeSubchannel(internalSubchannel);
                    ManagedChannelImpl.this.maybeTerminateChannel();
                }

                void onStateChange(InternalSubchannel internalSubchannel, ConnectivityStateInfo connectivityStateInfo) {
                    LbHelperImpl.this.handleInternalSubchannelState(connectivityStateInfo);
                    LbHelperImpl lbHelperImpl = LbHelperImpl.this;
                    if (lbHelperImpl == ManagedChannelImpl.this.lbHelper) {
                        LbHelperImpl.this.lb.handleSubchannelState(subchannelImpl, connectivityStateInfo);
                    }
                }

                void onInUse(InternalSubchannel internalSubchannel) {
                    ManagedChannelImpl.this.inUseStateAggregator.updateObjectInUse(internalSubchannel, true);
                }

                void onNotInUse(InternalSubchannel internalSubchannel) {
                    ManagedChannelImpl.this.inUseStateAggregator.updateObjectInUse(internalSubchannel, false);
                }
            }, ManagedChannelImpl.this.channelz, ManagedChannelImpl.this.callTracerFactory.create(), channelTracer2, ManagedChannelImpl.this.timeProvider);
            if (ManagedChannelImpl.this.channelTracer != null) {
                ManagedChannelImpl.this.channelTracer.reportEvent(new Builder().setDescription("Child channel created").setSeverity(Severity.CT_INFO).setTimestampNanos(j).setSubchannelRef(internalSubchannel).build());
            }
            ManagedChannelImpl.this.channelz.addSubchannel(internalSubchannel);
            AbstractSubchannel abstractSubchannel2 = abstractSubchannel;
            abstractSubchannel2.subchannel = internalSubchannel;
            Logger logger = ManagedChannelImpl.logger;
            Level level = Level.FINE;
            Object[] objArr = new Object[3];
            objArr[0] = ManagedChannelImpl.this.getLogId();
            objArr[1] = internalSubchannel.getLogId();
            final InternalSubchannel internalSubchannel3 = internalSubchannel;
            objArr[2] = list;
            logger.log(level, "[{0}] {1} created for {2}", objArr);
            runSerialized(new Runnable() {
                public void run() {
                    if (ManagedChannelImpl.this.terminating) {
                        internalSubchannel3.shutdown(ManagedChannelImpl.SHUTDOWN_STATUS);
                    }
                    if (!ManagedChannelImpl.this.terminated) {
                        ManagedChannelImpl.this.subchannels.add(internalSubchannel3);
                    }
                }
            });
            return abstractSubchannel2;
        }

        public void updateBalancingState(final ConnectivityState connectivityState, final SubchannelPicker subchannelPicker) {
            Preconditions.checkNotNull(connectivityState, "newState");
            Preconditions.checkNotNull(subchannelPicker, "newPicker");
            runSerialized(new Runnable() {
                public void run() {
                    LbHelperImpl lbHelperImpl = LbHelperImpl.this;
                    if (lbHelperImpl == ManagedChannelImpl.this.lbHelper) {
                        ManagedChannelImpl.this.updateSubchannelPicker(subchannelPicker);
                        if (connectivityState != ConnectivityState.SHUTDOWN) {
                            if (ManagedChannelImpl.this.channelTracer != null) {
                                ChannelTracer access$500 = ManagedChannelImpl.this.channelTracer;
                                Builder builder = new Builder();
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Entering ");
                                stringBuilder.append(connectivityState);
                                stringBuilder.append(" state");
                                access$500.reportEvent(builder.setDescription(stringBuilder.toString()).setSeverity(Severity.CT_INFO).setTimestampNanos(ManagedChannelImpl.this.timeProvider.currentTimeNanos()).build());
                            }
                            ManagedChannelImpl.this.channelStateManager.gotoState(connectivityState);
                        }
                    }
                }
            });
        }

        public void updateSubchannelAddresses(Subchannel subchannel, List<EquivalentAddressGroup> list) {
            Preconditions.checkArgument(subchannel instanceof SubchannelImpl, "subchannel must have been returned from createSubchannel");
            ((SubchannelImpl) subchannel).subchannel.updateAddresses(list);
        }

        public ManagedChannel createOobChannel(EquivalentAddressGroup equivalentAddressGroup, String str) {
            Preconditions.checkState(ManagedChannelImpl.this.terminated ^ 1, "Channel is terminated");
            long currentTimeNanos = ManagedChannelImpl.this.timeProvider.currentTimeNanos();
            ChannelTracer channelTracer = null;
            ChannelTracer channelTracer2 = ManagedChannelImpl.this.channelTracer != null ? new ChannelTracer(ManagedChannelImpl.this.maxTraceEvents, currentTimeNanos, "OobChannel") : null;
            final ManagedChannel oobChannel = new OobChannel(str, ManagedChannelImpl.this.oobExecutorPool, ManagedChannelImpl.this.transportFactory.getScheduledExecutorService(), ManagedChannelImpl.this.channelExecutor, ManagedChannelImpl.this.callTracerFactory.create(), channelTracer2, ManagedChannelImpl.this.channelz, ManagedChannelImpl.this.timeProvider);
            String str2 = "Child channel created";
            if (ManagedChannelImpl.this.channelTracer != null) {
                ManagedChannelImpl.this.channelTracer.reportEvent(new Builder().setDescription(str2).setSeverity(Severity.CT_INFO).setTimestampNanos(currentTimeNanos).setChannelRef(oobChannel).build());
                channelTracer = new ChannelTracer(ManagedChannelImpl.this.maxTraceEvents, currentTimeNanos, "Subchannel");
            }
            String str3 = str;
            InternalSubchannel internalSubchannel = new InternalSubchannel(Collections.singletonList(equivalentAddressGroup), str3, ManagedChannelImpl.this.userAgent, ManagedChannelImpl.this.backoffPolicyProvider, ManagedChannelImpl.this.transportFactory, ManagedChannelImpl.this.transportFactory.getScheduledExecutorService(), ManagedChannelImpl.this.stopwatchSupplier, ManagedChannelImpl.this.channelExecutor, new Callback() {
                void onTerminated(InternalSubchannel internalSubchannel) {
                    ManagedChannelImpl.this.oobChannels.remove(oobChannel);
                    ManagedChannelImpl.this.channelz.removeSubchannel(internalSubchannel);
                    oobChannel.handleSubchannelTerminated();
                    ManagedChannelImpl.this.maybeTerminateChannel();
                }

                void onStateChange(InternalSubchannel internalSubchannel, ConnectivityStateInfo connectivityStateInfo) {
                    LbHelperImpl.this.handleInternalSubchannelState(connectivityStateInfo);
                    oobChannel.handleSubchannelStateChange(connectivityStateInfo);
                }
            }, ManagedChannelImpl.this.channelz, ManagedChannelImpl.this.callTracerFactory.create(), channelTracer, ManagedChannelImpl.this.timeProvider);
            if (channelTracer2 != null) {
                channelTracer2.reportEvent(new Builder().setDescription(str2).setSeverity(Severity.CT_INFO).setTimestampNanos(currentTimeNanos).setSubchannelRef(internalSubchannel).build());
            }
            ManagedChannelImpl.this.channelz.addSubchannel(oobChannel);
            ManagedChannelImpl.this.channelz.addSubchannel(internalSubchannel);
            oobChannel.setSubchannel(internalSubchannel);
            runSerialized(new Runnable() {
                public void run() {
                    if (ManagedChannelImpl.this.terminating) {
                        oobChannel.shutdown();
                    }
                    if (!ManagedChannelImpl.this.terminated) {
                        ManagedChannelImpl.this.oobChannels.add(oobChannel);
                    }
                }
            });
            return oobChannel;
        }

        public void updateOobChannelAddresses(ManagedChannel managedChannel, EquivalentAddressGroup equivalentAddressGroup) {
            Preconditions.checkArgument(managedChannel instanceof OobChannel, "channel must have been returned from createOobChannel");
            ((OobChannel) managedChannel).updateAddresses(equivalentAddressGroup);
        }

        public String getAuthority() {
            return ManagedChannelImpl.this.authority();
        }

        public NameResolver.Factory getNameResolverFactory() {
            return ManagedChannelImpl.this.nameResolverFactory;
        }

        public void runSerialized(Runnable runnable) {
            ManagedChannelImpl.this.channelExecutor.executeLater(runnable).drain();
        }
    }

    private class NameResolverListenerImpl implements NameResolver.Listener {
        final LbHelperImpl helper;

        NameResolverListenerImpl(LbHelperImpl lbHelperImpl) {
            this.helper = lbHelperImpl;
        }

        public void onAddresses(final List<EquivalentAddressGroup> list, final Attributes attributes) {
            if (list.isEmpty()) {
                onError(Status.UNAVAILABLE.withDescription("NameResolver returned an empty list"));
                return;
            }
            if (ManagedChannelImpl.logger.isLoggable(Level.FINE)) {
                ManagedChannelImpl.logger.log(Level.FINE, "[{0}] resolved address: {1}, config={2}", new Object[]{ManagedChannelImpl.this.getLogId(), list, attributes});
            }
            if (ManagedChannelImpl.this.channelTracer != null && (ManagedChannelImpl.this.haveBackends == null || !ManagedChannelImpl.this.haveBackends.booleanValue())) {
                ChannelTracer access$500 = ManagedChannelImpl.this.channelTracer;
                Builder builder = new Builder();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Address resolved: ");
                stringBuilder.append(list);
                access$500.reportEvent(builder.setDescription(stringBuilder.toString()).setSeverity(Severity.CT_INFO).setTimestampNanos(ManagedChannelImpl.this.timeProvider.currentTimeNanos()).build());
                ManagedChannelImpl.this.haveBackends = Boolean.valueOf(true);
            }
            final Map map = (Map) attributes.get(GrpcAttributes.NAME_RESOLVER_SERVICE_CONFIG);
            if (!(ManagedChannelImpl.this.channelTracer == null || map == null || map.equals(ManagedChannelImpl.this.lastServiceConfig))) {
                ManagedChannelImpl.this.channelTracer.reportEvent(new Builder().setDescription("Service config changed").setSeverity(Severity.CT_INFO).setTimestampNanos(ManagedChannelImpl.this.timeProvider.currentTimeNanos()).build());
                ManagedChannelImpl.this.lastServiceConfig = map;
            }
            this.helper.runSerialized(new Runnable() {
                public void run() {
                    if (NameResolverListenerImpl.this.helper == ManagedChannelImpl.this.lbHelper) {
                        ManagedChannelImpl.this.nameResolverBackoffPolicy = null;
                        if (map != null) {
                            try {
                                ManagedChannelImpl.this.serviceConfigInterceptor.handleUpdate(map);
                                if (ManagedChannelImpl.this.retryEnabled) {
                                    ManagedChannelImpl.this.throttle = ManagedChannelImpl.getThrottle(attributes);
                                }
                            } catch (Throwable e) {
                                Logger logger = ManagedChannelImpl.logger;
                                Level level = Level.WARNING;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("[");
                                stringBuilder.append(ManagedChannelImpl.this.getLogId());
                                stringBuilder.append("] Unexpected exception from parsing service config");
                                logger.log(level, stringBuilder.toString(), e);
                            }
                        }
                        NameResolverListenerImpl.this.helper.lb.handleResolvedAddressGroups(list, attributes);
                    }
                }
            });
        }

        public void onError(final Status status) {
            Preconditions.checkArgument(status.isOk() ^ true, "the error status must not be OK");
            ManagedChannelImpl.logger.log(Level.WARNING, "[{0}] Failed to resolve name. status={1}", new Object[]{ManagedChannelImpl.this.getLogId(), status});
            if (ManagedChannelImpl.this.channelTracer != null && (ManagedChannelImpl.this.haveBackends == null || ManagedChannelImpl.this.haveBackends.booleanValue())) {
                ManagedChannelImpl.this.channelTracer.reportEvent(new Builder().setDescription("Failed to resolve name").setSeverity(Severity.CT_WARNING).setTimestampNanos(ManagedChannelImpl.this.timeProvider.currentTimeNanos()).build());
                ManagedChannelImpl.this.haveBackends = Boolean.valueOf(false);
            }
            ManagedChannelImpl.this.channelExecutor.executeLater(new Runnable() {
                public void run() {
                    if (NameResolverListenerImpl.this.helper == ManagedChannelImpl.this.lbHelper) {
                        NameResolverListenerImpl.this.helper.lb.handleNameResolutionError(status);
                        if (ManagedChannelImpl.this.nameResolverRefreshFuture == null) {
                            if (ManagedChannelImpl.this.nameResolverBackoffPolicy == null) {
                                ManagedChannelImpl.this.nameResolverBackoffPolicy = ManagedChannelImpl.this.backoffPolicyProvider.get();
                            }
                            long nextBackoffNanos = ManagedChannelImpl.this.nameResolverBackoffPolicy.nextBackoffNanos();
                            if (ManagedChannelImpl.logger.isLoggable(Level.FINE)) {
                                ManagedChannelImpl.logger.log(Level.FINE, "[{0}] Scheduling DNS resolution backoff for {1} ns", new Object[]{ManagedChannelImpl.this.logId, Long.valueOf(nextBackoffNanos)});
                            }
                            ManagedChannelImpl.this.nameResolverRefresh = new NameResolverRefresh();
                            ManagedChannelImpl.this.nameResolverRefreshFuture = ManagedChannelImpl.this.transportFactory.getScheduledExecutorService().schedule(ManagedChannelImpl.this.nameResolverRefresh, nextBackoffNanos, TimeUnit.NANOSECONDS);
                        }
                    }
                }
            }).drain();
        }
    }

    private final class PanicChannelExecutor extends ChannelExecutor {
        private PanicChannelExecutor() {
        }

        void handleUncaughtThrowable(Throwable th) {
            super.handleUncaughtThrowable(th);
            ManagedChannelImpl.this.panic(th);
        }
    }

    private class RealChannel extends Channel {
        private final String authority;

        private RealChannel(String str) {
            this.authority = (String) Preconditions.checkNotNull(str, "authority");
        }

        public <ReqT, RespT> ClientCall<ReqT, RespT> newCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions) {
            return new ClientCallImpl(methodDescriptor, ManagedChannelImpl.this.getCallExecutor(callOptions), callOptions, ManagedChannelImpl.this.transportProvider, ManagedChannelImpl.this.terminated ? null : ManagedChannelImpl.this.transportFactory.getScheduledExecutorService(), ManagedChannelImpl.this.channelCallTracer, ManagedChannelImpl.this.retryEnabled).setFullStreamDecompression(ManagedChannelImpl.this.fullStreamDecompression).setDecompressorRegistry(ManagedChannelImpl.this.decompressorRegistry).setCompressorRegistry(ManagedChannelImpl.this.compressorRegistry);
        }

        public String authority() {
            return this.authority;
        }
    }

    private final class SubchannelImpl extends AbstractSubchannel {
        final Attributes attrs;
        @GuardedBy("shutdownLock")
        ScheduledFuture<?> delayedShutdownTask;
        final Object shutdownLock = new Object();
        @GuardedBy("shutdownLock")
        boolean shutdownRequested;
        InternalSubchannel subchannel;

        SubchannelImpl(Attributes attributes) {
            this.attrs = (Attributes) Preconditions.checkNotNull(attributes, "attrs");
        }

        ClientTransport obtainActiveTransport() {
            return this.subchannel.obtainActiveTransport();
        }

        InternalInstrumented<ChannelStats> getInternalSubchannel() {
            return this.subchannel;
        }

        /* JADX WARNING: Missing block: B:11:0x001e, code:
            return;
     */
        public void shutdown() {
            /*
            r6 = this;
            r0 = r6.shutdownLock;
            monitor-enter(r0);
            r1 = r6.shutdownRequested;	 Catch:{ all -> 0x0053 }
            if (r1 == 0) goto L_0x001f;
        L_0x0007:
            r1 = io.grpc.internal.ManagedChannelImpl.this;	 Catch:{ all -> 0x0053 }
            r1 = r1.terminating;	 Catch:{ all -> 0x0053 }
            if (r1 == 0) goto L_0x001d;
        L_0x000f:
            r1 = r6.delayedShutdownTask;	 Catch:{ all -> 0x0053 }
            if (r1 == 0) goto L_0x001d;
        L_0x0013:
            r1 = r6.delayedShutdownTask;	 Catch:{ all -> 0x0053 }
            r2 = 0;
            r1.cancel(r2);	 Catch:{ all -> 0x0053 }
            r1 = 0;
            r6.delayedShutdownTask = r1;	 Catch:{ all -> 0x0053 }
            goto L_0x0022;
        L_0x001d:
            monitor-exit(r0);	 Catch:{ all -> 0x0053 }
            return;
        L_0x001f:
            r1 = 1;
            r6.shutdownRequested = r1;	 Catch:{ all -> 0x0053 }
        L_0x0022:
            r1 = io.grpc.internal.ManagedChannelImpl.this;	 Catch:{ all -> 0x0053 }
            r1 = r1.terminating;	 Catch:{ all -> 0x0053 }
            if (r1 != 0) goto L_0x004a;
        L_0x002a:
            r1 = io.grpc.internal.ManagedChannelImpl.this;	 Catch:{ all -> 0x0053 }
            r1 = r1.transportFactory;	 Catch:{ all -> 0x0053 }
            r1 = r1.getScheduledExecutorService();	 Catch:{ all -> 0x0053 }
            r2 = new io.grpc.internal.LogExceptionRunnable;	 Catch:{ all -> 0x0053 }
            r3 = new io.grpc.internal.ManagedChannelImpl$SubchannelImpl$1ShutdownSubchannel;	 Catch:{ all -> 0x0053 }
            r3.<init>();	 Catch:{ all -> 0x0053 }
            r2.<init>(r3);	 Catch:{ all -> 0x0053 }
            r3 = 5;
            r5 = java.util.concurrent.TimeUnit.SECONDS;	 Catch:{ all -> 0x0053 }
            r1 = r1.schedule(r2, r3, r5);	 Catch:{ all -> 0x0053 }
            r6.delayedShutdownTask = r1;	 Catch:{ all -> 0x0053 }
            monitor-exit(r0);	 Catch:{ all -> 0x0053 }
            return;
        L_0x004a:
            monitor-exit(r0);	 Catch:{ all -> 0x0053 }
            r0 = r6.subchannel;
            r1 = io.grpc.internal.ManagedChannelImpl.SHUTDOWN_STATUS;
            r0.shutdown(r1);
            return;
        L_0x0053:
            r1 = move-exception;
            monitor-exit(r0);	 Catch:{ all -> 0x0053 }
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.ManagedChannelImpl.SubchannelImpl.shutdown():void");
        }

        public void requestConnection() {
            this.subchannel.obtainActiveTransport();
        }

        public List<EquivalentAddressGroup> getAllAddresses() {
            return this.subchannel.getAddressGroups();
        }

        public Attributes getAttributes() {
            return this.attrs;
        }

        public String toString() {
            return this.subchannel.getLogId().toString();
        }
    }

    private void maybeShutdownNowSubchannels() {
        if (this.shutdownNowed) {
            for (InternalSubchannel shutdownNow : this.subchannels) {
                shutdownNow.shutdownNow(SHUTDOWN_NOW_STATUS);
            }
            for (OobChannel internalSubchannel : this.oobChannels) {
                internalSubchannel.getInternalSubchannel().shutdownNow(SHUTDOWN_NOW_STATUS);
            }
        }
    }

    public ListenableFuture<ChannelStats> getStats() {
        final ListenableFuture create = SettableFuture.create();
        this.channelExecutor.executeLater(new Runnable() {
            public void run() {
                ChannelStats.Builder builder = new ChannelStats.Builder();
                ManagedChannelImpl.this.channelCallTracer.updateBuilder(builder);
                if (ManagedChannelImpl.this.channelTracer != null) {
                    ManagedChannelImpl.this.channelTracer.updateBuilder(builder);
                }
                builder.setTarget(ManagedChannelImpl.this.target).setState(ManagedChannelImpl.this.channelStateManager.getState());
                List arrayList = new ArrayList();
                arrayList.addAll(ManagedChannelImpl.this.subchannels);
                arrayList.addAll(ManagedChannelImpl.this.oobChannels);
                builder.setSubchannels(arrayList);
                create.set(builder.build());
            }
        }).drain();
        return create;
    }

    public InternalLogId getLogId() {
        return this.logId;
    }

    private void shutdownNameResolverAndLoadBalancer(boolean z) {
        if (z) {
            boolean z2 = true;
            Preconditions.checkState(this.nameResolver != null, "nameResolver is null");
            if (this.lbHelper == null) {
                z2 = false;
            }
            Preconditions.checkState(z2, "lbHelper is null");
        }
        if (this.nameResolver != null) {
            cancelNameResolverBackoff();
            this.nameResolver.shutdown();
            this.nameResolver = null;
            this.nameResolverStarted = false;
        }
        LbHelperImpl lbHelperImpl = this.lbHelper;
        if (lbHelperImpl != null) {
            lbHelperImpl.lb.shutdown();
            this.lbHelper = null;
        }
        this.subchannelPicker = null;
    }

    @VisibleForTesting
    void exitIdleMode() {
        if (!(this.shutdown.get() || this.panicMode)) {
            if (this.inUseStateAggregator.isInUse()) {
                cancelIdleTimer(false);
            } else {
                rescheduleIdleTimer();
            }
            if (this.lbHelper == null) {
                logger.log(Level.FINE, "[{0}] Exiting idle mode", getLogId());
                this.lbHelper = new LbHelperImpl(this.nameResolver);
                Helper helper = this.lbHelper;
                helper.lb = this.loadBalancerFactory.newLoadBalancer(helper);
                Object nameResolverListenerImpl = new NameResolverListenerImpl(this.lbHelper);
                try {
                    this.nameResolver.start(nameResolverListenerImpl);
                    this.nameResolverStarted = true;
                } catch (Throwable th) {
                    nameResolverListenerImpl.onError(Status.fromThrowable(th));
                }
            }
        }
    }

    private void enterIdleMode() {
        logger.log(Level.FINE, "[{0}] Entering idle mode", getLogId());
        shutdownNameResolverAndLoadBalancer(true);
        this.delayedTransport.reprocess(null);
        this.nameResolver = getNameResolver(this.target, this.nameResolverFactory, this.nameResolverParams);
        ChannelTracer channelTracer = this.channelTracer;
        if (channelTracer != null) {
            channelTracer.reportEvent(new Builder().setDescription("Entering IDLE state").setSeverity(Severity.CT_INFO).setTimestampNanos(this.timeProvider.currentTimeNanos()).build());
        }
        this.channelStateManager.gotoState(ConnectivityState.IDLE);
        if (this.inUseStateAggregator.isInUse()) {
            exitIdleMode();
        }
    }

    private void cancelIdleTimer(boolean z) {
        this.idleTimer.cancel(z);
    }

    private void rescheduleIdleTimer() {
        long j = this.idleTimeoutMillis;
        if (j != -1) {
            this.idleTimer.reschedule(j, TimeUnit.MILLISECONDS);
        }
    }

    private void cancelNameResolverBackoff() {
        ScheduledFuture scheduledFuture = this.nameResolverRefreshFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            this.nameResolverRefresh.cancelled = true;
            this.nameResolverRefreshFuture = null;
            this.nameResolverRefresh = null;
            this.nameResolverBackoffPolicy = null;
        }
    }

    ManagedChannelImpl(AbstractManagedChannelImplBuilder<?> abstractManagedChannelImplBuilder, ClientTransportFactory clientTransportFactory, Provider provider, ObjectPool<? extends Executor> objectPool, Supplier<Stopwatch> supplier, List<ClientInterceptor> list, final TimeProvider timeProvider) {
        this.target = (String) Preconditions.checkNotNull(abstractManagedChannelImplBuilder.target, "target");
        this.nameResolverFactory = abstractManagedChannelImplBuilder.getNameResolverFactory();
        this.nameResolverParams = (Attributes) Preconditions.checkNotNull(abstractManagedChannelImplBuilder.getNameResolverParams(), "nameResolverParams");
        this.nameResolver = getNameResolver(this.target, this.nameResolverFactory, this.nameResolverParams);
        this.timeProvider = (TimeProvider) Preconditions.checkNotNull(timeProvider, "timeProvider");
        this.maxTraceEvents = abstractManagedChannelImplBuilder.maxTraceEvents;
        if (this.maxTraceEvents > 0) {
            this.channelTracer = new ChannelTracer(abstractManagedChannelImplBuilder.maxTraceEvents, timeProvider.currentTimeNanos(), "Channel");
        } else {
            this.channelTracer = null;
        }
        if (abstractManagedChannelImplBuilder.loadBalancerFactory == null) {
            this.loadBalancerFactory = new AutoConfiguredLoadBalancerFactory(this.channelTracer, timeProvider);
        } else {
            this.loadBalancerFactory = abstractManagedChannelImplBuilder.loadBalancerFactory;
        }
        this.executorPool = (ObjectPool) Preconditions.checkNotNull(abstractManagedChannelImplBuilder.executorPool, "executorPool");
        this.oobExecutorPool = (ObjectPool) Preconditions.checkNotNull(objectPool, "oobExecutorPool");
        this.executor = (Executor) Preconditions.checkNotNull(this.executorPool.getObject(), "executor");
        this.delayedTransport = new DelayedClientTransport(this.executor, this.channelExecutor);
        this.delayedTransport.start(this.delayedTransportListener);
        this.backoffPolicyProvider = provider;
        this.transportFactory = new CallCredentialsApplyingTransportFactory(clientTransportFactory, this.executor);
        boolean z = abstractManagedChannelImplBuilder.retryEnabled && !abstractManagedChannelImplBuilder.temporarilyDisableRetry;
        this.retryEnabled = z;
        this.serviceConfigInterceptor = new ServiceConfigInterceptor(this.retryEnabled, abstractManagedChannelImplBuilder.maxRetryAttempts, abstractManagedChannelImplBuilder.maxHedgedAttempts);
        Channel intercept = ClientInterceptors.intercept(new RealChannel(this.nameResolver.getServiceAuthority()), this.serviceConfigInterceptor);
        if (abstractManagedChannelImplBuilder.binlog != null) {
            intercept = abstractManagedChannelImplBuilder.binlog.wrapChannel(intercept);
        }
        this.interceptorChannel = ClientInterceptors.intercept(intercept, (List) list);
        this.stopwatchSupplier = (Supplier) Preconditions.checkNotNull(supplier, "stopwatchSupplier");
        if (abstractManagedChannelImplBuilder.idleTimeoutMillis == -1) {
            this.idleTimeoutMillis = abstractManagedChannelImplBuilder.idleTimeoutMillis;
        } else {
            Preconditions.checkArgument(abstractManagedChannelImplBuilder.idleTimeoutMillis >= AbstractManagedChannelImplBuilder.IDLE_MODE_MIN_TIMEOUT_MILLIS, "invalid idleTimeoutMillis %s", abstractManagedChannelImplBuilder.idleTimeoutMillis);
            this.idleTimeoutMillis = abstractManagedChannelImplBuilder.idleTimeoutMillis;
        }
        this.idleTimer = new Rescheduler(new IdleModeTimer(), new Executor() {
            public void execute(Runnable runnable) {
                ManagedChannelImpl.this.channelExecutor.executeLater(runnable);
                ManagedChannelImpl.this.channelExecutor.drain();
            }
        }, this.transportFactory.getScheduledExecutorService(), (Stopwatch) supplier.get());
        this.fullStreamDecompression = abstractManagedChannelImplBuilder.fullStreamDecompression;
        this.decompressorRegistry = (DecompressorRegistry) Preconditions.checkNotNull(abstractManagedChannelImplBuilder.decompressorRegistry, "decompressorRegistry");
        this.compressorRegistry = (CompressorRegistry) Preconditions.checkNotNull(abstractManagedChannelImplBuilder.compressorRegistry, "compressorRegistry");
        this.userAgent = abstractManagedChannelImplBuilder.userAgent;
        this.channelBufferLimit = abstractManagedChannelImplBuilder.retryBufferSize;
        this.perRpcBufferLimit = abstractManagedChannelImplBuilder.perRpcBufferLimit;
        this.callTracerFactory = new Factory() {
            public CallTracer create() {
                return new CallTracer(timeProvider);
            }
        };
        this.channelCallTracer = this.callTracerFactory.create();
        this.channelz = (InternalChannelz) Preconditions.checkNotNull(abstractManagedChannelImplBuilder.channelz);
        this.channelz.addRootChannel(this);
        logger.log(Level.FINE, "[{0}] Created with target {1}", new Object[]{getLogId(), this.target});
    }

    @VisibleForTesting
    static NameResolver getNameResolver(String str, NameResolver.Factory factory, Attributes attributes) {
        URI uri;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            uri = new URI(str);
        } catch (URISyntaxException e) {
            stringBuilder.append(e.getMessage());
            uri = null;
        }
        if (uri != null) {
            NameResolver newNameResolver = factory.newNameResolver(uri, attributes);
            if (newNameResolver != null) {
                return newNameResolver;
            }
        }
        String str2 = "";
        if (!URI_PATTERN.matcher(str).matches()) {
            try {
                String defaultScheme = factory.getDefaultScheme();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("/");
                stringBuilder2.append(str);
                NameResolver newNameResolver2 = factory.newNameResolver(new URI(defaultScheme, str2, stringBuilder2.toString(), null), attributes);
                if (newNameResolver2 != null) {
                    return newNameResolver2;
                }
            } catch (Throwable e2) {
                throw new IllegalArgumentException(e2);
            }
        }
        Object[] objArr = new Object[2];
        objArr[0] = str;
        if (stringBuilder.length() > 0) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(" (");
            stringBuilder3.append(stringBuilder);
            stringBuilder3.append(")");
            str2 = stringBuilder3.toString();
        }
        objArr[1] = str2;
        throw new IllegalArgumentException(String.format("cannot find a NameResolver for %s%s", objArr));
    }

    public ManagedChannelImpl shutdown() {
        logger.log(Level.FINE, "[{0}] shutdown() called", getLogId());
        if (!this.shutdown.compareAndSet(false, true)) {
            return this;
        }
        this.channelExecutor.executeLater(new Runnable() {
            public void run() {
                if (ManagedChannelImpl.this.channelTracer != null) {
                    ManagedChannelImpl.this.channelTracer.reportEvent(new Builder().setDescription("Entering SHUTDOWN state").setSeverity(Severity.CT_INFO).setTimestampNanos(ManagedChannelImpl.this.timeProvider.currentTimeNanos()).build());
                }
                ManagedChannelImpl.this.channelStateManager.gotoState(ConnectivityState.SHUTDOWN);
            }
        });
        this.uncommittedRetriableStreamsRegistry.onShutdown(SHUTDOWN_STATUS);
        this.channelExecutor.executeLater(new Runnable() {
            public void run() {
                ManagedChannelImpl.this.cancelIdleTimer(true);
            }
        }).drain();
        logger.log(Level.FINE, "[{0}] Shutting down", getLogId());
        return this;
    }

    public ManagedChannelImpl shutdownNow() {
        logger.log(Level.FINE, "[{0}] shutdownNow() called", getLogId());
        shutdown();
        this.uncommittedRetriableStreamsRegistry.onShutdownNow(SHUTDOWN_NOW_STATUS);
        this.channelExecutor.executeLater(new Runnable() {
            public void run() {
                if (!ManagedChannelImpl.this.shutdownNowed) {
                    ManagedChannelImpl.this.shutdownNowed = true;
                    ManagedChannelImpl.this.maybeShutdownNowSubchannels();
                }
            }
        }).drain();
        return this;
    }

    @VisibleForTesting
    void panic(final Throwable th) {
        if (!this.panicMode) {
            this.panicMode = true;
            cancelIdleTimer(true);
            shutdownNameResolverAndLoadBalancer(false);
            updateSubchannelPicker(new SubchannelPicker() {
                private final PickResult panicPickResult = PickResult.withDrop(Status.INTERNAL.withDescription("Panic! This is a bug!").withCause(th));

                public PickResult pickSubchannel(PickSubchannelArgs pickSubchannelArgs) {
                    return this.panicPickResult;
                }
            });
            ChannelTracer channelTracer = this.channelTracer;
            if (channelTracer != null) {
                channelTracer.reportEvent(new Builder().setDescription("Entering TRANSIENT_FAILURE state").setSeverity(Severity.CT_INFO).setTimestampNanos(this.timeProvider.currentTimeNanos()).build());
            }
            this.channelStateManager.gotoState(ConnectivityState.TRANSIENT_FAILURE);
        }
    }

    private void updateSubchannelPicker(SubchannelPicker subchannelPicker) {
        this.subchannelPicker = subchannelPicker;
        this.delayedTransport.reprocess(subchannelPicker);
    }

    public boolean isShutdown() {
        return this.shutdown.get();
    }

    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        return this.terminatedLatch.await(j, timeUnit);
    }

    public boolean isTerminated() {
        return this.terminated;
    }

    public <ReqT, RespT> ClientCall<ReqT, RespT> newCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions) {
        return this.interceptorChannel.newCall(methodDescriptor, callOptions);
    }

    public String authority() {
        return this.interceptorChannel.authority();
    }

    private Executor getCallExecutor(CallOptions callOptions) {
        Executor executor = callOptions.getExecutor();
        return executor == null ? this.executor : executor;
    }

    private void maybeTerminateChannel() {
        if (!this.terminated && this.shutdown.get() && this.subchannels.isEmpty() && this.oobChannels.isEmpty()) {
            logger.log(Level.FINE, "[{0}] Terminated", getLogId());
            this.channelz.removeRootChannel(this);
            this.terminated = true;
            this.terminatedLatch.countDown();
            this.executorPool.returnObject(this.executor);
            this.transportFactory.close();
        }
    }

    public ConnectivityState getState(boolean z) {
        ConnectivityState state = this.channelStateManager.getState();
        if (z && state == ConnectivityState.IDLE) {
            this.channelExecutor.executeLater(new Runnable() {
                public void run() {
                    ManagedChannelImpl.this.exitIdleMode();
                    if (ManagedChannelImpl.this.subchannelPicker != null) {
                        ManagedChannelImpl.this.subchannelPicker.requestConnection();
                    }
                }
            }).drain();
        }
        return state;
    }

    public void notifyWhenStateChanged(final ConnectivityState connectivityState, final Runnable runnable) {
        this.channelExecutor.executeLater(new Runnable() {
            public void run() {
                ManagedChannelImpl.this.channelStateManager.notifyWhenStateChanged(runnable, ManagedChannelImpl.this.executor, connectivityState);
            }
        }).drain();
    }

    public void resetConnectBackoff() {
        this.channelExecutor.executeLater(new Runnable() {
            public void run() {
                if (!ManagedChannelImpl.this.shutdown.get()) {
                    if (ManagedChannelImpl.this.nameResolverRefreshFuture != null) {
                        Preconditions.checkState(ManagedChannelImpl.this.nameResolverStarted, "name resolver must be started");
                        ManagedChannelImpl.this.cancelNameResolverBackoff();
                        ManagedChannelImpl.this.nameResolver.refresh();
                    }
                    for (InternalSubchannel resetConnectBackoff : ManagedChannelImpl.this.subchannels) {
                        resetConnectBackoff.resetConnectBackoff();
                    }
                    for (OobChannel resetConnectBackoff2 : ManagedChannelImpl.this.oobChannels) {
                        resetConnectBackoff2.resetConnectBackoff();
                    }
                }
            }
        }).drain();
    }

    public void enterIdle() {
        this.channelExecutor.executeLater(new Runnable() {
            public void run() {
                if (!ManagedChannelImpl.this.shutdown.get() && ManagedChannelImpl.this.lbHelper != null) {
                    ManagedChannelImpl.this.cancelIdleTimer(false);
                    ManagedChannelImpl.this.enterIdleMode();
                }
            }
        }).drain();
    }

    @Nullable
    private static Throttle getThrottle(Attributes attributes) {
        return ServiceConfigUtil.getThrottlePolicy((Map) attributes.get(GrpcAttributes.NAME_RESOLVER_SERVICE_CONFIG));
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("logId", this.logId.getId()).add("target", this.target).toString();
    }
}
