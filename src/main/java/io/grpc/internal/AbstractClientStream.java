package io.grpc.internal;

import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs.CastExtraArgs;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.Compressor;
import io.grpc.Deadline;
import io.grpc.DecompressorRegistry;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.internal.ClientStreamListener.RpcProgress;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public abstract class AbstractClientStream extends AbstractStream implements ClientStream, io.grpc.internal.MessageFramer.Sink {
    private static final Logger log = Logger.getLogger(AbstractClientStream.class.getName());
    private volatile boolean cancelled;
    private final Framer framer;
    private Metadata headers;
    private final TransportTracer transportTracer;
    private boolean useGet;

    protected interface Sink {
        void cancel(Status status);

        void request(int i);

        void writeFrame(@Nullable WritableBuffer writableBuffer, boolean z, boolean z2, int i);

        void writeHeaders(Metadata metadata, @Nullable byte[] bArr);
    }

    private class GetFramer implements Framer {
        private boolean closed;
        private Metadata headers;
        private byte[] payload;
        private final StatsTraceContext statsTraceCtx;

        public void flush() {
        }

        public Framer setCompressor(Compressor compressor) {
            return this;
        }

        public void setMaxOutboundMessageSize(int i) {
        }

        public Framer setMessageCompression(boolean z) {
            return this;
        }

        public GetFramer(Metadata metadata, StatsTraceContext statsTraceContext) {
            this.headers = (Metadata) Preconditions.checkNotNull(metadata, "headers");
            this.statsTraceCtx = (StatsTraceContext) Preconditions.checkNotNull(statsTraceContext, "statsTraceCtx");
        }

        public void writePayload(InputStream inputStream) {
            Preconditions.checkState(this.payload == null, "writePayload should not be called multiple times");
            try {
                this.payload = IoUtils.toByteArray(inputStream);
                this.statsTraceCtx.outboundMessage(0);
                StatsTraceContext statsTraceContext = this.statsTraceCtx;
                byte[] bArr = this.payload;
                statsTraceContext.outboundMessageSent(0, (long) bArr.length, (long) bArr.length);
                this.statsTraceCtx.outboundUncompressedSize((long) this.payload.length);
                this.statsTraceCtx.outboundWireSize((long) this.payload.length);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public boolean isClosed() {
            return this.closed;
        }

        public void close() {
            boolean z = true;
            this.closed = true;
            if (this.payload == null) {
                z = false;
            }
            Preconditions.checkState(z, "Lack of request message. GET request is only supported for unary requests");
            AbstractClientStream.this.abstractClientStreamSink().writeHeaders(this.headers, this.payload);
            this.payload = null;
            this.headers = null;
        }

        public void dispose() {
            this.closed = true;
            this.payload = null;
            this.headers = null;
        }
    }

    protected static abstract class TransportState extends io.grpc.internal.AbstractStream.TransportState {
        private DecompressorRegistry decompressorRegistry = DecompressorRegistry.getDefaultInstance();
        private boolean deframerClosed = false;
        private Runnable deframerClosedTask;
        private boolean fullStreamDecompression;
        private ClientStreamListener listener;
        private boolean listenerClosed;
        private volatile boolean outboundClosed;
        private final StatsTraceContext statsTraceCtx;
        private boolean statusReported;
        private Status trailerStatus;
        private Metadata trailers;

        protected TransportState(int i, StatsTraceContext statsTraceContext, TransportTracer transportTracer) {
            super(i, statsTraceContext, transportTracer);
            this.statsTraceCtx = (StatsTraceContext) Preconditions.checkNotNull(statsTraceContext, "statsTraceCtx");
        }

        private void setFullStreamDecompression(boolean z) {
            this.fullStreamDecompression = z;
        }

        private void setDecompressorRegistry(DecompressorRegistry decompressorRegistry) {
            Preconditions.checkState(this.listener == null, "Already called start");
            this.decompressorRegistry = (DecompressorRegistry) Preconditions.checkNotNull(decompressorRegistry, "decompressorRegistry");
        }

        @VisibleForTesting
        public final void setListener(ClientStreamListener clientStreamListener) {
            Preconditions.checkState(this.listener == null, "Already called setListener");
            this.listener = (ClientStreamListener) Preconditions.checkNotNull(clientStreamListener, CastExtraArgs.LISTENER);
        }

        public void deframerClosed(boolean z) {
            this.deframerClosed = true;
            Status status = this.trailerStatus;
            if (status != null) {
                if (status.isOk() && z) {
                    this.trailerStatus = Status.INTERNAL.withDescription("Encountered end-of-stream mid-frame");
                    this.trailers = new Metadata();
                }
                transportReportStatus(this.trailerStatus, false, this.trailers);
            } else {
                Preconditions.checkState(this.statusReported, "status should have been reported on deframer closed");
            }
            Runnable runnable = this.deframerClosedTask;
            if (runnable != null) {
                runnable.run();
                this.deframerClosedTask = null;
            }
        }

        protected final ClientStreamListener listener() {
            return this.listener;
        }

        private final void setOutboundClosed() {
            this.outboundClosed = true;
        }

        protected final boolean isOutboundClosed() {
            return this.outboundClosed;
        }

        /* JADX WARNING: Removed duplicated region for block: B:13:0x005a  */
        protected void inboundHeadersReceived(io.grpc.Metadata r6) {
            /*
            r5 = this;
            r0 = r5.statusReported;
            r1 = 1;
            r0 = r0 ^ r1;
            r2 = "Received headers on closed stream";
            com.google.common.base.Preconditions.checkState(r0, r2);
            r0 = r5.statsTraceCtx;
            r0.clientInboundHeaders();
            r0 = io.grpc.internal.GrpcUtil.CONTENT_ENCODING_KEY;
            r0 = r6.get(r0);
            r0 = (java.lang.String) r0;
            r2 = r5.fullStreamDecompression;
            r3 = 0;
            if (r2 == 0) goto L_0x004f;
        L_0x001b:
            if (r0 == 0) goto L_0x004f;
        L_0x001d:
            r2 = "gzip";
            r2 = r0.equalsIgnoreCase(r2);
            if (r2 == 0) goto L_0x002f;
        L_0x0025:
            r0 = new io.grpc.internal.GzipInflatingBuffer;
            r0.<init>();
            r5.setFullStreamDecompressor(r0);
            r0 = 1;
            goto L_0x0050;
        L_0x002f:
            r2 = "identity";
            r2 = r0.equalsIgnoreCase(r2);
            if (r2 != 0) goto L_0x004f;
        L_0x0037:
            r6 = io.grpc.Status.INTERNAL;
            r1 = new java.lang.Object[r1];
            r1[r3] = r0;
            r0 = "Can't find full stream decompressor for %s";
            r0 = java.lang.String.format(r0, r1);
            r6 = r6.withDescription(r0);
            r6 = r6.asRuntimeException();
            r5.deframeFailed(r6);
            return;
        L_0x004f:
            r0 = 0;
        L_0x0050:
            r2 = io.grpc.internal.GrpcUtil.MESSAGE_ENCODING_KEY;
            r2 = r6.get(r2);
            r2 = (java.lang.String) r2;
            if (r2 == 0) goto L_0x0099;
        L_0x005a:
            r4 = r5.decompressorRegistry;
            r4 = r4.lookupDecompressor(r2);
            if (r4 != 0) goto L_0x007a;
        L_0x0062:
            r6 = io.grpc.Status.INTERNAL;
            r0 = new java.lang.Object[r1];
            r0[r3] = r2;
            r1 = "Can't find decompressor for %s";
            r0 = java.lang.String.format(r1, r0);
            r6 = r6.withDescription(r0);
            r6 = r6.asRuntimeException();
            r5.deframeFailed(r6);
            return;
        L_0x007a:
            r1 = io.grpc.Codec.Identity.NONE;
            if (r4 == r1) goto L_0x0099;
        L_0x007e:
            if (r0 == 0) goto L_0x0096;
        L_0x0080:
            r6 = io.grpc.Status.INTERNAL;
            r0 = new java.lang.Object[r3];
            r1 = "Full stream and gRPC message encoding cannot both be set";
            r0 = java.lang.String.format(r1, r0);
            r6 = r6.withDescription(r0);
            r6 = r6.asRuntimeException();
            r5.deframeFailed(r6);
            return;
        L_0x0096:
            r5.setDecompressor(r4);
        L_0x0099:
            r0 = r5.listener();
            r0.headersRead(r6);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.AbstractClientStream.TransportState.inboundHeadersReceived(io.grpc.Metadata):void");
        }

        protected void inboundDataReceived(ReadableBuffer readableBuffer) {
            Preconditions.checkNotNull(readableBuffer, "frame");
            Object obj = 1;
            try {
                if (this.statusReported) {
                    AbstractClientStream.log.log(Level.INFO, "Received data on closed stream");
                    readableBuffer.close();
                    return;
                }
                obj = null;
                deframe(readableBuffer);
            } catch (Throwable th) {
                if (obj != null) {
                    readableBuffer.close();
                }
            }
        }

        protected void inboundTrailersReceived(Metadata metadata, Status status) {
            Preconditions.checkNotNull(status, NotificationCompat.CATEGORY_STATUS);
            Preconditions.checkNotNull(metadata, GrpcUtil.TE_TRAILERS);
            if (this.statusReported) {
                AbstractClientStream.log.log(Level.INFO, "Received trailers on closed stream:\n {1}\n {2}", new Object[]{status, metadata});
                return;
            }
            this.trailers = metadata;
            this.trailerStatus = status;
            closeDeframer(false);
        }

        public final void transportReportStatus(Status status, boolean z, Metadata metadata) {
            transportReportStatus(status, RpcProgress.PROCESSED, z, metadata);
        }

        public final void transportReportStatus(final Status status, final RpcProgress rpcProgress, boolean z, final Metadata metadata) {
            Preconditions.checkNotNull(status, NotificationCompat.CATEGORY_STATUS);
            Preconditions.checkNotNull(metadata, GrpcUtil.TE_TRAILERS);
            if (!this.statusReported || z) {
                this.statusReported = true;
                onStreamDeallocated();
                if (this.deframerClosed) {
                    this.deframerClosedTask = null;
                    closeListener(status, rpcProgress, metadata);
                } else {
                    this.deframerClosedTask = new Runnable() {
                        public void run() {
                            TransportState.this.closeListener(status, rpcProgress, metadata);
                        }
                    };
                    closeDeframer(z);
                }
            }
        }

        private void closeListener(Status status, RpcProgress rpcProgress, Metadata metadata) {
            if (!this.listenerClosed) {
                this.listenerClosed = true;
                this.statsTraceCtx.streamClosed(status);
                listener().closed(status, rpcProgress, metadata);
                if (getTransportTracer() != null) {
                    getTransportTracer().reportStreamClosed(status.isOk());
                }
            }
        }
    }

    protected abstract Sink abstractClientStreamSink();

    protected abstract TransportState transportState();

    protected AbstractClientStream(WritableBufferAllocator writableBufferAllocator, StatsTraceContext statsTraceContext, TransportTracer transportTracer, Metadata metadata, boolean z) {
        Preconditions.checkNotNull(metadata, "headers");
        this.transportTracer = (TransportTracer) Preconditions.checkNotNull(transportTracer, "transportTracer");
        this.useGet = z;
        if (z) {
            this.framer = new GetFramer(metadata, statsTraceContext);
            return;
        }
        this.framer = new MessageFramer(this, writableBufferAllocator, statsTraceContext);
        this.headers = metadata;
    }

    public void setDeadline(Deadline deadline) {
        this.headers.discardAll(GrpcUtil.TIMEOUT_KEY);
        this.headers.put(GrpcUtil.TIMEOUT_KEY, Long.valueOf(Math.max(0, deadline.timeRemaining(TimeUnit.NANOSECONDS))));
    }

    public void setMaxOutboundMessageSize(int i) {
        this.framer.setMaxOutboundMessageSize(i);
    }

    public void setMaxInboundMessageSize(int i) {
        transportState().setMaxInboundMessageSize(i);
    }

    public final void setFullStreamDecompression(boolean z) {
        transportState().setFullStreamDecompression(z);
    }

    public final void setDecompressorRegistry(DecompressorRegistry decompressorRegistry) {
        transportState().setDecompressorRegistry(decompressorRegistry);
    }

    public final void start(ClientStreamListener clientStreamListener) {
        transportState().setListener(clientStreamListener);
        if (!this.useGet) {
            abstractClientStreamSink().writeHeaders(this.headers, null);
            this.headers = null;
        }
    }

    protected final Framer framer() {
        return this.framer;
    }

    public final void request(int i) {
        abstractClientStreamSink().request(i);
    }

    public final void deliverFrame(WritableBuffer writableBuffer, boolean z, boolean z2, int i) {
        boolean z3 = writableBuffer != null || z;
        Preconditions.checkArgument(z3, "null frame before EOS");
        abstractClientStreamSink().writeFrame(writableBuffer, z, z2, i);
    }

    public final void halfClose() {
        if (!transportState().isOutboundClosed()) {
            transportState().setOutboundClosed();
            endOfMessages();
        }
    }

    public final void cancel(Status status) {
        Preconditions.checkArgument(status.isOk() ^ true, "Should not cancel with OK status");
        this.cancelled = true;
        abstractClientStreamSink().cancel(status);
    }

    public final boolean isReady() {
        return super.isReady() && !this.cancelled;
    }

    /* renamed from: getTransportTracer */
    protected TransportTracer access$800() {
        return this.transportTracer;
    }
}
