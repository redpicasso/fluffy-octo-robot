package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
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
import io.grpc.ServerStreamTracer.ServerCallInfo;
import io.grpc.Status;
import io.opencensus.trace.BlankSpan;
import io.opencensus.trace.EndSpanOptions;
import io.opencensus.trace.MessageEvent;
import io.opencensus.trace.MessageEvent.Builder;
import io.opencensus.trace.MessageEvent.Type;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.propagation.BinaryFormat;
import io.opencensus.trace.unsafe.ContextUtils;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

final class CensusTracingModule {
    @Nullable
    private static final AtomicIntegerFieldUpdater<ClientCallTracer> callEndedUpdater;
    private static final Logger logger = Logger.getLogger(CensusTracingModule.class.getName());
    @Nullable
    private static final AtomicIntegerFieldUpdater<ServerTracer> streamClosedUpdater;
    private final Tracer censusTracer;
    private final TracingClientInterceptor clientInterceptor = new TracingClientInterceptor();
    private final ServerTracerFactory serverTracerFactory = new ServerTracerFactory();
    @VisibleForTesting
    final Key<SpanContext> tracingHeader;

    @VisibleForTesting
    final class ClientCallTracer extends Factory {
        volatile int callEnded;
        private final boolean isSampledToLocalTracing;
        private final Span span;

        ClientCallTracer(@Nullable Span span, MethodDescriptor<?, ?> methodDescriptor) {
            Preconditions.checkNotNull(methodDescriptor, Param.METHOD);
            this.isSampledToLocalTracing = methodDescriptor.isSampledToLocalTracing();
            this.span = CensusTracingModule.this.censusTracer.spanBuilderWithExplicitParent(CensusTracingModule.generateTraceSpanName(false, methodDescriptor.getFullMethodName()), span).setRecordEvents(true).startSpan();
        }

        public ClientStreamTracer newClientStreamTracer(CallOptions callOptions, Metadata metadata) {
            if (this.span != BlankSpan.INSTANCE) {
                metadata.discardAll(CensusTracingModule.this.tracingHeader);
                metadata.put(CensusTracingModule.this.tracingHeader, this.span.getContext());
            }
            return new ClientTracer(this.span);
        }

        void callEnded(Status status) {
            if (CensusTracingModule.callEndedUpdater != null) {
                if (CensusTracingModule.callEndedUpdater.getAndSet(this, 1) != 0) {
                    return;
                }
            } else if (this.callEnded == 0) {
                this.callEnded = 1;
            } else {
                return;
            }
            this.span.end(CensusTracingModule.createEndSpanOptions(status, this.isSampledToLocalTracing));
        }
    }

    @VisibleForTesting
    final class ServerTracerFactory extends ServerStreamTracer.Factory {
        ServerTracerFactory() {
        }

        public ServerStreamTracer newServerStreamTracer(String str, Metadata metadata) {
            SpanContext spanContext = (SpanContext) metadata.get(CensusTracingModule.this.tracingHeader);
            if (spanContext == SpanContext.INVALID) {
                spanContext = null;
            }
            return new ServerTracer(str, spanContext);
        }
    }

    @VisibleForTesting
    final class TracingClientInterceptor implements ClientInterceptor {
        TracingClientInterceptor() {
        }

        public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
            final Factory newClientCallTracer = CensusTracingModule.this.newClientCallTracer((Span) ContextUtils.CONTEXT_SPAN_KEY.get(), methodDescriptor);
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
        private final Span span;

        ClientTracer(Span span) {
            this.span = (Span) Preconditions.checkNotNull(span, "span");
        }

        public void outboundMessageSent(int i, long j, long j2) {
            CensusTracingModule.recordMessageEvent(this.span, Type.SENT, i, j, j2);
        }

        public void inboundMessageRead(int i, long j, long j2) {
            CensusTracingModule.recordMessageEvent(this.span, Type.RECEIVED, i, j, j2);
        }
    }

    private final class ServerTracer extends ServerStreamTracer {
        volatile boolean isSampledToLocalTracing;
        private final Span span;
        volatile int streamClosed;

        ServerTracer(String str, @Nullable SpanContext spanContext) {
            Preconditions.checkNotNull(str, "fullMethodName");
            this.span = CensusTracingModule.this.censusTracer.spanBuilderWithRemoteParent(CensusTracingModule.generateTraceSpanName(true, str), spanContext).setRecordEvents(true).startSpan();
        }

        public void serverCallStarted(ServerCallInfo<?, ?> serverCallInfo) {
            this.isSampledToLocalTracing = serverCallInfo.getMethodDescriptor().isSampledToLocalTracing();
        }

        public void streamClosed(Status status) {
            if (CensusTracingModule.streamClosedUpdater != null) {
                if (CensusTracingModule.streamClosedUpdater.getAndSet(this, 1) != 0) {
                    return;
                }
            } else if (this.streamClosed == 0) {
                this.streamClosed = 1;
            } else {
                return;
            }
            this.span.end(CensusTracingModule.createEndSpanOptions(status, this.isSampledToLocalTracing));
        }

        public Context filterContext(Context context) {
            return context.withValue(ContextUtils.CONTEXT_SPAN_KEY, this.span);
        }

        public void outboundMessageSent(int i, long j, long j2) {
            CensusTracingModule.recordMessageEvent(this.span, Type.SENT, i, j, j2);
        }

        public void inboundMessageRead(int i, long j, long j2) {
            CensusTracingModule.recordMessageEvent(this.span, Type.RECEIVED, i, j, j2);
        }
    }

    static {
        AtomicIntegerFieldUpdater newUpdater;
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = null;
        try {
            newUpdater = AtomicIntegerFieldUpdater.newUpdater(ClientCallTracer.class, "callEnded");
            atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(ServerTracer.class, "streamClosed");
            AtomicIntegerFieldUpdater atomicIntegerFieldUpdater2 = newUpdater;
            newUpdater = atomicIntegerFieldUpdater;
            atomicIntegerFieldUpdater = atomicIntegerFieldUpdater2;
        } catch (Throwable th) {
            logger.log(Level.SEVERE, "Creating atomic field updaters failed", th);
            newUpdater = atomicIntegerFieldUpdater;
        }
        callEndedUpdater = atomicIntegerFieldUpdater;
        streamClosedUpdater = newUpdater;
    }

    CensusTracingModule(Tracer tracer, final BinaryFormat binaryFormat) {
        this.censusTracer = (Tracer) Preconditions.checkNotNull(tracer, "censusTracer");
        Preconditions.checkNotNull(binaryFormat, "censusPropagationBinaryFormat");
        this.tracingHeader = Key.of("grpc-trace-bin", new BinaryMarshaller<SpanContext>() {
            public byte[] toBytes(SpanContext spanContext) {
                return binaryFormat.toByteArray(spanContext);
            }

            public SpanContext parseBytes(byte[] bArr) {
                try {
                    return binaryFormat.fromByteArray(bArr);
                } catch (Throwable e) {
                    CensusTracingModule.logger.log(Level.FINE, "Failed to parse tracing header", e);
                    return SpanContext.INVALID;
                }
            }
        });
    }

    @VisibleForTesting
    ClientCallTracer newClientCallTracer(@Nullable Span span, MethodDescriptor<?, ?> methodDescriptor) {
        return new ClientCallTracer(span, methodDescriptor);
    }

    ServerStreamTracer.Factory getServerTracerFactory() {
        return this.serverTracerFactory;
    }

    ClientInterceptor getClientInterceptor() {
        return this.clientInterceptor;
    }

    @VisibleForTesting
    static io.opencensus.trace.Status convertStatus(Status status) {
        io.opencensus.trace.Status status2;
        switch (status.getCode()) {
            case OK:
                status2 = io.opencensus.trace.Status.OK;
                break;
            case CANCELLED:
                status2 = io.opencensus.trace.Status.CANCELLED;
                break;
            case UNKNOWN:
                status2 = io.opencensus.trace.Status.UNKNOWN;
                break;
            case INVALID_ARGUMENT:
                status2 = io.opencensus.trace.Status.INVALID_ARGUMENT;
                break;
            case DEADLINE_EXCEEDED:
                status2 = io.opencensus.trace.Status.DEADLINE_EXCEEDED;
                break;
            case NOT_FOUND:
                status2 = io.opencensus.trace.Status.NOT_FOUND;
                break;
            case ALREADY_EXISTS:
                status2 = io.opencensus.trace.Status.ALREADY_EXISTS;
                break;
            case PERMISSION_DENIED:
                status2 = io.opencensus.trace.Status.PERMISSION_DENIED;
                break;
            case RESOURCE_EXHAUSTED:
                status2 = io.opencensus.trace.Status.RESOURCE_EXHAUSTED;
                break;
            case FAILED_PRECONDITION:
                status2 = io.opencensus.trace.Status.FAILED_PRECONDITION;
                break;
            case ABORTED:
                status2 = io.opencensus.trace.Status.ABORTED;
                break;
            case OUT_OF_RANGE:
                status2 = io.opencensus.trace.Status.OUT_OF_RANGE;
                break;
            case UNIMPLEMENTED:
                status2 = io.opencensus.trace.Status.UNIMPLEMENTED;
                break;
            case INTERNAL:
                status2 = io.opencensus.trace.Status.INTERNAL;
                break;
            case UNAVAILABLE:
                status2 = io.opencensus.trace.Status.UNAVAILABLE;
                break;
            case DATA_LOSS:
                status2 = io.opencensus.trace.Status.DATA_LOSS;
                break;
            case UNAUTHENTICATED:
                status2 = io.opencensus.trace.Status.UNAUTHENTICATED;
                break;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unhandled status code ");
                stringBuilder.append(status.getCode());
                throw new AssertionError(stringBuilder.toString());
        }
        return status.getDescription() != null ? status2.withDescription(status.getDescription()) : status2;
    }

    private static EndSpanOptions createEndSpanOptions(Status status, boolean z) {
        return EndSpanOptions.builder().setStatus(convertStatus(status)).setSampleToLocalSpanStore(z).build();
    }

    private static void recordMessageEvent(Span span, Type type, int i, long j, long j2) {
        Builder builder = MessageEvent.builder(type, (long) i);
        if (j2 != -1) {
            builder.setUncompressedMessageSize(j2);
        }
        if (j != -1) {
            builder.setCompressedMessageSize(j);
        }
        span.addMessageEvent(builder.build());
    }

    @VisibleForTesting
    static String generateTraceSpanName(boolean z, String str) {
        String str2 = z ? "Recv" : "Sent";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append(".");
        stringBuilder.append(str.replace('/', '.'));
        return stringBuilder.toString();
    }
}
