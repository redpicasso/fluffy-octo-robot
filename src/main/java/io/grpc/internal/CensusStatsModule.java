package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientCall.Listener;
import io.grpc.ClientInterceptor;
import io.grpc.ClientStreamTracer;
import io.grpc.ClientStreamTracer.Factory;
import io.grpc.Context;
import io.grpc.ForwardingClientCall.SimpleForwardingClientCall;
import io.grpc.ForwardingClientCallListener.SimpleForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.Metadata.BinaryMarshaller;
import io.grpc.Metadata.Key;
import io.grpc.MethodDescriptor;
import io.grpc.ServerStreamTracer;
import io.grpc.Status;
import io.opencensus.contrib.grpc.metrics.RpcMeasureConstants;
import io.opencensus.stats.MeasureMap;
import io.opencensus.stats.Stats;
import io.opencensus.stats.StatsRecorder;
import io.opencensus.tags.TagContext;
import io.opencensus.tags.TagValue;
import io.opencensus.tags.Tagger;
import io.opencensus.tags.Tags;
import io.opencensus.tags.propagation.TagContextBinarySerializer;
import io.opencensus.tags.unsafe.ContextUtils;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class CensusStatsModule {
    private static final ClientTracer BLANK_CLIENT_TRACER = new ClientTracer();
    private static final double NANOS_PER_MILLI = ((double) TimeUnit.MILLISECONDS.toNanos(1));
    private static final Logger logger = Logger.getLogger(CensusStatsModule.class.getName());
    private final boolean propagateTags;
    @VisibleForTesting
    final Key<TagContext> statsHeader;
    private final StatsRecorder statsRecorder;
    private final Supplier<Stopwatch> stopwatchSupplier;
    private final Tagger tagger;

    @VisibleForTesting
    static final class ClientCallTracer extends Factory {
        @Nullable
        private static final AtomicIntegerFieldUpdater<ClientCallTracer> callEndedUpdater;
        @Nullable
        private static final AtomicReferenceFieldUpdater<ClientCallTracer, ClientTracer> streamTracerUpdater;
        private volatile int callEnded;
        private final CensusStatsModule module;
        private final TagContext parentCtx;
        private final boolean recordFinishedRpcs;
        private final TagContext startCtx;
        private final Stopwatch stopwatch;
        private volatile ClientTracer streamTracer;

        static {
            AtomicIntegerFieldUpdater atomicIntegerFieldUpdater;
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = null;
            try {
                AtomicReferenceFieldUpdater newUpdater = AtomicReferenceFieldUpdater.newUpdater(ClientCallTracer.class, ClientTracer.class, "streamTracer");
                atomicReferenceFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(ClientCallTracer.class, "callEnded");
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = newUpdater;
                atomicIntegerFieldUpdater = atomicReferenceFieldUpdater;
                atomicReferenceFieldUpdater = atomicReferenceFieldUpdater2;
            } catch (Throwable th) {
                CensusStatsModule.logger.log(Level.SEVERE, "Creating atomic field updaters failed", th);
                atomicIntegerFieldUpdater = atomicReferenceFieldUpdater;
            }
            streamTracerUpdater = atomicReferenceFieldUpdater;
            callEndedUpdater = atomicIntegerFieldUpdater;
        }

        ClientCallTracer(CensusStatsModule censusStatsModule, TagContext tagContext, String str, boolean z, boolean z2) {
            this.module = censusStatsModule;
            this.parentCtx = (TagContext) Preconditions.checkNotNull(tagContext);
            this.startCtx = censusStatsModule.tagger.toBuilder(tagContext).put(RpcMeasureConstants.RPC_METHOD, TagValue.create(str)).build();
            this.stopwatch = ((Stopwatch) censusStatsModule.stopwatchSupplier.get()).start();
            this.recordFinishedRpcs = z2;
            if (z) {
                censusStatsModule.statsRecorder.newMeasureMap().put(RpcMeasureConstants.RPC_CLIENT_STARTED_COUNT, 1).record(this.startCtx);
            }
        }

        public ClientStreamTracer newClientStreamTracer(CallOptions callOptions, Metadata metadata) {
            ClientStreamTracer clientTracer = new ClientTracer();
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = streamTracerUpdater;
            String str = "Are you creating multiple streams per call? This class doesn't yet support this case";
            if (atomicReferenceFieldUpdater != null) {
                Preconditions.checkState(atomicReferenceFieldUpdater.compareAndSet(this, null, clientTracer), str);
            } else {
                Preconditions.checkState(this.streamTracer == null, str);
                this.streamTracer = clientTracer;
            }
            if (this.module.propagateTags) {
                metadata.discardAll(this.module.statsHeader);
                if (!this.module.tagger.empty().equals(this.parentCtx)) {
                    metadata.put(this.module.statsHeader, this.parentCtx);
                }
            }
            return clientTracer;
        }

        void callEnded(Status status) {
            AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = callEndedUpdater;
            if (atomicIntegerFieldUpdater != null) {
                if (atomicIntegerFieldUpdater.getAndSet(this, 1) != 0) {
                    return;
                }
            } else if (this.callEnded == 0) {
                this.callEnded = 1;
            } else {
                return;
            }
            if (this.recordFinishedRpcs) {
                this.stopwatch.stop();
                long elapsed = this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
                ClientTracer clientTracer = this.streamTracer;
                if (clientTracer == null) {
                    clientTracer = CensusStatsModule.BLANK_CLIENT_TRACER;
                }
                MeasureMap put = this.module.statsRecorder.newMeasureMap().put(RpcMeasureConstants.RPC_CLIENT_FINISHED_COUNT, 1).put(RpcMeasureConstants.RPC_CLIENT_ROUNDTRIP_LATENCY, ((double) elapsed) / CensusStatsModule.NANOS_PER_MILLI).put(RpcMeasureConstants.RPC_CLIENT_REQUEST_COUNT, clientTracer.outboundMessageCount).put(RpcMeasureConstants.RPC_CLIENT_RESPONSE_COUNT, clientTracer.inboundMessageCount).put(RpcMeasureConstants.RPC_CLIENT_REQUEST_BYTES, (double) clientTracer.outboundWireSize).put(RpcMeasureConstants.RPC_CLIENT_RESPONSE_BYTES, (double) clientTracer.inboundWireSize).put(RpcMeasureConstants.RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES, (double) clientTracer.outboundUncompressedSize).put(RpcMeasureConstants.RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES, (double) clientTracer.inboundUncompressedSize);
                if (!status.isOk()) {
                    put.put(RpcMeasureConstants.RPC_CLIENT_ERROR_COUNT, 1);
                }
                put.record(this.module.tagger.toBuilder(this.startCtx).put(RpcMeasureConstants.RPC_STATUS, TagValue.create(status.getCode().toString())).build());
            }
        }
    }

    @VisibleForTesting
    final class ServerTracerFactory extends ServerStreamTracer.Factory {
        private final boolean recordFinishedRpcs;
        private final boolean recordStartedRpcs;

        ServerTracerFactory(boolean z, boolean z2) {
            this.recordStartedRpcs = z;
            this.recordFinishedRpcs = z2;
        }

        public ServerStreamTracer newServerStreamTracer(String str, Metadata metadata) {
            TagContext tagContext = (TagContext) metadata.get(CensusStatsModule.this.statsHeader);
            if (tagContext == null) {
                tagContext = CensusStatsModule.this.tagger.empty();
            }
            TagContext build = CensusStatsModule.this.tagger.toBuilder(tagContext).put(RpcMeasureConstants.RPC_METHOD, TagValue.create(str)).build();
            CensusStatsModule censusStatsModule = CensusStatsModule.this;
            return new ServerTracer(censusStatsModule, build, censusStatsModule.stopwatchSupplier, CensusStatsModule.this.tagger, this.recordStartedRpcs, this.recordFinishedRpcs);
        }
    }

    @VisibleForTesting
    final class StatsClientInterceptor implements ClientInterceptor {
        private final boolean recordFinishedRpcs;
        private final boolean recordStartedRpcs;

        StatsClientInterceptor(boolean z, boolean z2) {
            this.recordStartedRpcs = z;
            this.recordFinishedRpcs = z2;
        }

        public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
            final Factory newClientCallTracer = CensusStatsModule.this.newClientCallTracer(CensusStatsModule.this.tagger.getCurrentTagContext(), methodDescriptor.getFullMethodName(), this.recordStartedRpcs, this.recordFinishedRpcs);
            return new SimpleForwardingClientCall<ReqT, RespT>(channel.newCall(methodDescriptor, callOptions.withStreamTracerFactory(newClientCallTracer))) {
                public void start(Listener<RespT> listener, Metadata metadata) {
                    delegate().start(new SimpleForwardingClientCallListener<RespT>(listener) {
                        public void onClose(Status status, Metadata metadata) {
                            newClientCallTracer.callEnded(status);
                            super.onClose(status, metadata);
                        }
                    }, metadata);
                }
            };
        }
    }

    private static final class ClientTracer extends ClientStreamTracer {
        @Nullable
        private static final AtomicLongFieldUpdater<ClientTracer> inboundMessageCountUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ClientTracer> inboundUncompressedSizeUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ClientTracer> inboundWireSizeUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ClientTracer> outboundMessageCountUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ClientTracer> outboundUncompressedSizeUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ClientTracer> outboundWireSizeUpdater;
        volatile long inboundMessageCount;
        volatile long inboundUncompressedSize;
        volatile long inboundWireSize;
        volatile long outboundMessageCount;
        volatile long outboundUncompressedSize;
        volatile long outboundWireSize;

        private ClientTracer() {
        }

        /* synthetic */ ClientTracer(AnonymousClass1 anonymousClass1) {
            this();
        }

        static {
            AtomicLongFieldUpdater newUpdater;
            AtomicLongFieldUpdater newUpdater2;
            AtomicLongFieldUpdater newUpdater3;
            AtomicLongFieldUpdater newUpdater4;
            AtomicLongFieldUpdater newUpdater5;
            AtomicLongFieldUpdater atomicLongFieldUpdater = null;
            try {
                newUpdater = AtomicLongFieldUpdater.newUpdater(ClientTracer.class, "outboundMessageCount");
                newUpdater2 = AtomicLongFieldUpdater.newUpdater(ClientTracer.class, "inboundMessageCount");
                newUpdater3 = AtomicLongFieldUpdater.newUpdater(ClientTracer.class, "outboundWireSize");
                newUpdater4 = AtomicLongFieldUpdater.newUpdater(ClientTracer.class, "inboundWireSize");
                newUpdater5 = AtomicLongFieldUpdater.newUpdater(ClientTracer.class, "outboundUncompressedSize");
                atomicLongFieldUpdater = AtomicLongFieldUpdater.newUpdater(ClientTracer.class, "inboundUncompressedSize");
                AtomicLongFieldUpdater atomicLongFieldUpdater2 = newUpdater;
                newUpdater = atomicLongFieldUpdater;
                atomicLongFieldUpdater = atomicLongFieldUpdater2;
            } catch (Throwable th) {
                CensusStatsModule.logger.log(Level.SEVERE, "Creating atomic field updaters failed", th);
                newUpdater = atomicLongFieldUpdater;
                newUpdater2 = newUpdater;
                newUpdater3 = newUpdater2;
                newUpdater4 = newUpdater3;
                newUpdater5 = newUpdater4;
            }
            outboundMessageCountUpdater = atomicLongFieldUpdater;
            inboundMessageCountUpdater = newUpdater2;
            outboundWireSizeUpdater = newUpdater3;
            inboundWireSizeUpdater = newUpdater4;
            outboundUncompressedSizeUpdater = newUpdater5;
            inboundUncompressedSizeUpdater = newUpdater;
        }

        public void outboundWireSize(long j) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = outboundWireSizeUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndAdd(this, j);
            } else {
                this.outboundWireSize += j;
            }
        }

        public void inboundWireSize(long j) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = inboundWireSizeUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndAdd(this, j);
            } else {
                this.inboundWireSize += j;
            }
        }

        public void outboundUncompressedSize(long j) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = outboundUncompressedSizeUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndAdd(this, j);
            } else {
                this.outboundUncompressedSize += j;
            }
        }

        public void inboundUncompressedSize(long j) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = inboundUncompressedSizeUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndAdd(this, j);
            } else {
                this.inboundUncompressedSize += j;
            }
        }

        public void inboundMessage(int i) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = inboundMessageCountUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndIncrement(this);
            } else {
                this.inboundMessageCount++;
            }
        }

        public void outboundMessage(int i) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = outboundMessageCountUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndIncrement(this);
            } else {
                this.outboundMessageCount++;
            }
        }
    }

    private static final class ServerTracer extends ServerStreamTracer {
        @Nullable
        private static final AtomicLongFieldUpdater<ServerTracer> inboundMessageCountUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ServerTracer> inboundUncompressedSizeUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ServerTracer> inboundWireSizeUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ServerTracer> outboundMessageCountUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ServerTracer> outboundUncompressedSizeUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ServerTracer> outboundWireSizeUpdater;
        @Nullable
        private static final AtomicIntegerFieldUpdater<ServerTracer> streamClosedUpdater;
        private volatile long inboundMessageCount;
        private volatile long inboundUncompressedSize;
        private volatile long inboundWireSize;
        private final CensusStatsModule module;
        private volatile long outboundMessageCount;
        private volatile long outboundUncompressedSize;
        private volatile long outboundWireSize;
        private final TagContext parentCtx;
        private final boolean recordFinishedRpcs;
        private final Stopwatch stopwatch;
        private volatile int streamClosed;
        private final Tagger tagger;

        static {
            AtomicLongFieldUpdater newUpdater;
            AtomicLongFieldUpdater newUpdater2;
            AtomicLongFieldUpdater newUpdater3;
            AtomicLongFieldUpdater newUpdater4;
            AtomicLongFieldUpdater newUpdater5;
            AtomicLongFieldUpdater atomicLongFieldUpdater;
            AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = null;
            try {
                AtomicIntegerFieldUpdater newUpdater6 = AtomicIntegerFieldUpdater.newUpdater(ServerTracer.class, "streamClosed");
                newUpdater = AtomicLongFieldUpdater.newUpdater(ServerTracer.class, "outboundMessageCount");
                newUpdater2 = AtomicLongFieldUpdater.newUpdater(ServerTracer.class, "inboundMessageCount");
                newUpdater3 = AtomicLongFieldUpdater.newUpdater(ServerTracer.class, "outboundWireSize");
                newUpdater4 = AtomicLongFieldUpdater.newUpdater(ServerTracer.class, "inboundWireSize");
                newUpdater5 = AtomicLongFieldUpdater.newUpdater(ServerTracer.class, "outboundUncompressedSize");
                atomicIntegerFieldUpdater = AtomicLongFieldUpdater.newUpdater(ServerTracer.class, "inboundUncompressedSize");
                AtomicIntegerFieldUpdater atomicIntegerFieldUpdater2 = newUpdater6;
                atomicLongFieldUpdater = atomicIntegerFieldUpdater;
                atomicIntegerFieldUpdater = atomicIntegerFieldUpdater2;
            } catch (Throwable th) {
                CensusStatsModule.logger.log(Level.SEVERE, "Creating atomic field updaters failed", th);
                atomicLongFieldUpdater = atomicIntegerFieldUpdater;
                newUpdater = atomicLongFieldUpdater;
                newUpdater2 = newUpdater;
                newUpdater3 = newUpdater2;
                newUpdater4 = newUpdater3;
                newUpdater5 = newUpdater4;
            }
            streamClosedUpdater = atomicIntegerFieldUpdater;
            outboundMessageCountUpdater = newUpdater;
            inboundMessageCountUpdater = newUpdater2;
            outboundWireSizeUpdater = newUpdater3;
            inboundWireSizeUpdater = newUpdater4;
            outboundUncompressedSizeUpdater = newUpdater5;
            inboundUncompressedSizeUpdater = atomicLongFieldUpdater;
        }

        ServerTracer(CensusStatsModule censusStatsModule, TagContext tagContext, Supplier<Stopwatch> supplier, Tagger tagger, boolean z, boolean z2) {
            this.module = censusStatsModule;
            this.parentCtx = (TagContext) Preconditions.checkNotNull(tagContext, "parentCtx");
            this.stopwatch = ((Stopwatch) supplier.get()).start();
            this.tagger = tagger;
            this.recordFinishedRpcs = z2;
            if (z) {
                censusStatsModule.statsRecorder.newMeasureMap().put(RpcMeasureConstants.RPC_SERVER_STARTED_COUNT, 1).record(tagContext);
            }
        }

        public void outboundWireSize(long j) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = outboundWireSizeUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndAdd(this, j);
            } else {
                this.outboundWireSize += j;
            }
        }

        public void inboundWireSize(long j) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = inboundWireSizeUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndAdd(this, j);
            } else {
                this.inboundWireSize += j;
            }
        }

        public void outboundUncompressedSize(long j) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = outboundUncompressedSizeUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndAdd(this, j);
            } else {
                this.outboundUncompressedSize += j;
            }
        }

        public void inboundUncompressedSize(long j) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = inboundUncompressedSizeUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndAdd(this, j);
            } else {
                this.inboundUncompressedSize += j;
            }
        }

        public void inboundMessage(int i) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = inboundMessageCountUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndIncrement(this);
            } else {
                this.inboundMessageCount++;
            }
        }

        public void outboundMessage(int i) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = outboundMessageCountUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndIncrement(this);
            } else {
                this.outboundMessageCount++;
            }
        }

        public void streamClosed(Status status) {
            AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = streamClosedUpdater;
            if (atomicIntegerFieldUpdater != null) {
                if (atomicIntegerFieldUpdater.getAndSet(this, 1) != 0) {
                    return;
                }
            } else if (this.streamClosed == 0) {
                this.streamClosed = 1;
            } else {
                return;
            }
            if (this.recordFinishedRpcs) {
                this.stopwatch.stop();
                MeasureMap put = this.module.statsRecorder.newMeasureMap().put(RpcMeasureConstants.RPC_SERVER_FINISHED_COUNT, 1).put(RpcMeasureConstants.RPC_SERVER_SERVER_LATENCY, ((double) this.stopwatch.elapsed(TimeUnit.NANOSECONDS)) / CensusStatsModule.NANOS_PER_MILLI).put(RpcMeasureConstants.RPC_SERVER_RESPONSE_COUNT, this.outboundMessageCount).put(RpcMeasureConstants.RPC_SERVER_REQUEST_COUNT, this.inboundMessageCount).put(RpcMeasureConstants.RPC_SERVER_RESPONSE_BYTES, (double) this.outboundWireSize).put(RpcMeasureConstants.RPC_SERVER_REQUEST_BYTES, (double) this.inboundWireSize).put(RpcMeasureConstants.RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES, (double) this.outboundUncompressedSize).put(RpcMeasureConstants.RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES, (double) this.inboundUncompressedSize);
                if (!status.isOk()) {
                    put.put(RpcMeasureConstants.RPC_SERVER_ERROR_COUNT, 1);
                }
                put.record(this.module.tagger.toBuilder(this.parentCtx).put(RpcMeasureConstants.RPC_STATUS, TagValue.create(status.getCode().toString())).build());
            }
        }

        public Context filterContext(Context context) {
            return !this.tagger.empty().equals(this.parentCtx) ? context.withValue(ContextUtils.TAG_CONTEXT_KEY, this.parentCtx) : context;
        }
    }

    CensusStatsModule(Supplier<Stopwatch> supplier, boolean z) {
        this(Tags.getTagger(), Tags.getTagPropagationComponent().getBinarySerializer(), Stats.getStatsRecorder(), supplier, z);
    }

    public CensusStatsModule(final Tagger tagger, final TagContextBinarySerializer tagContextBinarySerializer, StatsRecorder statsRecorder, Supplier<Stopwatch> supplier, boolean z) {
        this.tagger = (Tagger) Preconditions.checkNotNull(tagger, "tagger");
        this.statsRecorder = (StatsRecorder) Preconditions.checkNotNull(statsRecorder, "statsRecorder");
        Preconditions.checkNotNull(tagContextBinarySerializer, "tagCtxSerializer");
        this.stopwatchSupplier = (Supplier) Preconditions.checkNotNull(supplier, "stopwatchSupplier");
        this.propagateTags = z;
        this.statsHeader = Key.of("grpc-tags-bin", new BinaryMarshaller<TagContext>() {
            public byte[] toBytes(TagContext tagContext) {
                try {
                    return tagContextBinarySerializer.toByteArray(tagContext);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }

            public TagContext parseBytes(byte[] bArr) {
                try {
                    return tagContextBinarySerializer.fromByteArray(bArr);
                } catch (Throwable e) {
                    CensusStatsModule.logger.log(Level.FINE, "Failed to parse stats header", e);
                    return tagger.empty();
                }
            }
        });
    }

    @VisibleForTesting
    ClientCallTracer newClientCallTracer(TagContext tagContext, String str, boolean z, boolean z2) {
        return new ClientCallTracer(this, tagContext, str, z, z2);
    }

    ServerStreamTracer.Factory getServerTracerFactory(boolean z, boolean z2) {
        return new ServerTracerFactory(z, z2);
    }

    ClientInterceptor getClientInterceptor(boolean z, boolean z2) {
        return new StatsClientInterceptor(z, z2);
    }
}
