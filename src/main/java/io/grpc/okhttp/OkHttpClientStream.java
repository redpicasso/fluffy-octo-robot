package io.grpc.okhttp;

import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import io.grpc.Attributes;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.MethodDescriptor.MethodType;
import io.grpc.Status;
import io.grpc.internal.AbstractClientStream;
import io.grpc.internal.ClientStreamListener.RpcProgress;
import io.grpc.internal.Http2ClientStreamTransportState;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.TransportTracer;
import io.grpc.internal.WritableBuffer;
import io.grpc.okhttp.internal.framed.ErrorCode;
import io.grpc.okhttp.internal.framed.Header;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import javax.annotation.concurrent.GuardedBy;
import okio.Buffer;

class OkHttpClientStream extends AbstractClientStream {
    public static final int ABSENT_ID = -1;
    private static final Buffer EMPTY_BUFFER = new Buffer();
    private static final int WINDOW_UPDATE_THRESHOLD = 32767;
    private final Attributes attributes;
    private String authority;
    private volatile int id = -1;
    private final MethodDescriptor<?, ?> method;
    private Object outboundFlowState;
    private final Sink sink = new Sink();
    private final TransportState state;
    private final StatsTraceContext statsTraceCtx;
    private boolean useGet = false;
    private final String userAgent;

    private static class PendingData {
        Buffer buffer;
        boolean endOfStream;
        boolean flush;

        PendingData(Buffer buffer, boolean z, boolean z2) {
            this.buffer = buffer;
            this.endOfStream = z;
            this.flush = z2;
        }
    }

    class Sink implements Sink {
        Sink() {
        }

        public void writeHeaders(Metadata metadata, byte[] bArr) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("/");
            stringBuilder.append(OkHttpClientStream.this.method.getFullMethodName());
            String stringBuilder2 = stringBuilder.toString();
            if (bArr != null) {
                OkHttpClientStream.this.useGet = true;
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(stringBuilder2);
                stringBuilder3.append("?");
                stringBuilder3.append(BaseEncoding.base64().encode(bArr));
                stringBuilder2 = stringBuilder3.toString();
            }
            synchronized (OkHttpClientStream.this.state.lock) {
                OkHttpClientStream.this.state.streamReady(metadata, stringBuilder2);
            }
        }

        public void writeFrame(WritableBuffer writableBuffer, boolean z, boolean z2, int i) {
            Buffer access$500;
            if (writableBuffer == null) {
                access$500 = OkHttpClientStream.EMPTY_BUFFER;
            } else {
                access$500 = ((OkHttpWritableBuffer) writableBuffer).buffer();
                int size = (int) access$500.size();
                if (size > 0) {
                    OkHttpClientStream.this.access$600(size);
                }
            }
            synchronized (OkHttpClientStream.this.state.lock) {
                OkHttpClientStream.this.state.sendBuffer(access$500, z, z2);
                OkHttpClientStream.this.access$800().reportMessageSent(i);
            }
        }

        public void request(int i) {
            synchronized (OkHttpClientStream.this.state.lock) {
                OkHttpClientStream.this.state.requestMessagesFromDeframer(i);
            }
        }

        public void cancel(Status status) {
            synchronized (OkHttpClientStream.this.state.lock) {
                OkHttpClientStream.this.state.cancel(status, true, null);
            }
        }
    }

    class TransportState extends Http2ClientStreamTransportState {
        @GuardedBy("lock")
        private boolean cancelSent = false;
        @GuardedBy("lock")
        private final AsyncFrameWriter frameWriter;
        private final Object lock;
        @GuardedBy("lock")
        private final OutboundFlowController outboundFlow;
        @GuardedBy("lock")
        private Queue<PendingData> pendingData = new ArrayDeque();
        @GuardedBy("lock")
        private int processedWindow = 65535;
        @GuardedBy("lock")
        private List<Header> requestHeaders;
        @GuardedBy("lock")
        private final OkHttpClientTransport transport;
        @GuardedBy("lock")
        private int window = 65535;

        public TransportState(int i, StatsTraceContext statsTraceContext, Object obj, AsyncFrameWriter asyncFrameWriter, OutboundFlowController outboundFlowController, OkHttpClientTransport okHttpClientTransport) {
            super(i, statsTraceContext, OkHttpClientStream.this.access$800());
            this.lock = Preconditions.checkNotNull(obj, "lock");
            this.frameWriter = asyncFrameWriter;
            this.outboundFlow = outboundFlowController;
            this.transport = okHttpClientTransport;
        }

        @GuardedBy("lock")
        public void start(int i) {
            Preconditions.checkState(OkHttpClientStream.this.id == -1, "the stream has been started with id %s", i);
            OkHttpClientStream.this.id = i;
            OkHttpClientStream.this.state.onStreamAllocated();
            if (this.pendingData != null) {
                this.frameWriter.synStream(OkHttpClientStream.this.useGet, false, OkHttpClientStream.this.id, 0, this.requestHeaders);
                OkHttpClientStream.this.statsTraceCtx.clientOutboundHeaders();
                this.requestHeaders = null;
                Object obj = null;
                while (!this.pendingData.isEmpty()) {
                    PendingData pendingData = (PendingData) this.pendingData.poll();
                    this.outboundFlow.data(pendingData.endOfStream, OkHttpClientStream.this.id, pendingData.buffer, false);
                    if (pendingData.flush) {
                        obj = 1;
                    }
                }
                if (obj != null) {
                    this.outboundFlow.flush();
                }
                this.pendingData = null;
            }
        }

        @GuardedBy("lock")
        protected void onStreamAllocated() {
            super.onStreamAllocated();
            getTransportTracer().reportLocalStreamStarted();
        }

        @GuardedBy("lock")
        protected void http2ProcessingFailed(Status status, boolean z, Metadata metadata) {
            cancel(status, z, metadata);
        }

        @GuardedBy("lock")
        public void deframeFailed(Throwable th) {
            http2ProcessingFailed(Status.fromThrowable(th), true, new Metadata());
        }

        @GuardedBy("lock")
        public void bytesRead(int i) {
            this.processedWindow -= i;
            i = this.processedWindow;
            if (i <= OkHttpClientStream.WINDOW_UPDATE_THRESHOLD) {
                int i2 = 65535 - i;
                this.window += i2;
                this.processedWindow = i + i2;
                this.frameWriter.windowUpdate(OkHttpClientStream.this.id(), (long) i2);
            }
        }

        @GuardedBy("lock")
        public void deframerClosed(boolean z) {
            onEndOfStream();
            super.deframerClosed(z);
        }

        @GuardedBy("lock")
        public void runOnTransportThread(Runnable runnable) {
            synchronized (this.lock) {
                runnable.run();
            }
        }

        @GuardedBy("lock")
        public void transportHeadersReceived(List<Header> list, boolean z) {
            if (z) {
                transportTrailersReceived(Utils.convertTrailers(list));
            } else {
                transportHeadersReceived(Utils.convertHeaders(list));
            }
        }

        @GuardedBy("lock")
        public void transportDataReceived(Buffer buffer, boolean z) {
            this.window -= (int) buffer.size();
            if (this.window < 0) {
                this.frameWriter.rstStream(OkHttpClientStream.this.id(), ErrorCode.FLOW_CONTROL_ERROR);
                this.transport.finishStream(OkHttpClientStream.this.id(), Status.INTERNAL.withDescription("Received data size exceeded our receiving window size"), RpcProgress.PROCESSED, false, null, null);
                return;
            }
            super.transportDataReceived(new OkHttpReadableBuffer(buffer), z);
        }

        @GuardedBy("lock")
        private void onEndOfStream() {
            if (isOutboundClosed()) {
                this.transport.finishStream(OkHttpClientStream.this.id(), null, RpcProgress.PROCESSED, false, null, null);
            } else {
                this.transport.finishStream(OkHttpClientStream.this.id(), null, RpcProgress.PROCESSED, false, ErrorCode.CANCEL, null);
            }
        }

        @GuardedBy("lock")
        private void cancel(Status status, boolean z, Metadata metadata) {
            if (!this.cancelSent) {
                this.cancelSent = true;
                if (this.pendingData != null) {
                    this.transport.removePendingStream(OkHttpClientStream.this);
                    this.requestHeaders = null;
                    for (PendingData pendingData : this.pendingData) {
                        pendingData.buffer.clear();
                    }
                    this.pendingData = null;
                    if (metadata == null) {
                        metadata = new Metadata();
                    }
                    transportReportStatus(status, true, metadata);
                } else {
                    this.transport.finishStream(OkHttpClientStream.this.id(), status, RpcProgress.PROCESSED, z, ErrorCode.CANCEL, metadata);
                }
            }
        }

        @GuardedBy("lock")
        private void sendBuffer(Buffer buffer, boolean z, boolean z2) {
            if (!this.cancelSent) {
                Queue queue = this.pendingData;
                if (queue != null) {
                    queue.add(new PendingData(buffer, z, z2));
                } else {
                    Preconditions.checkState(OkHttpClientStream.this.id() != -1, "streamId should be set");
                    this.outboundFlow.data(z, OkHttpClientStream.this.id(), buffer, z2);
                }
            }
        }

        @GuardedBy("lock")
        private void streamReady(Metadata metadata, String str) {
            this.requestHeaders = Headers.createRequestHeaders(metadata, str, OkHttpClientStream.this.authority, OkHttpClientStream.this.userAgent, OkHttpClientStream.this.useGet);
            this.transport.streamReadyToStart(OkHttpClientStream.this);
        }
    }

    OkHttpClientStream(MethodDescriptor<?, ?> methodDescriptor, Metadata metadata, AsyncFrameWriter asyncFrameWriter, OkHttpClientTransport okHttpClientTransport, OutboundFlowController outboundFlowController, Object obj, int i, String str, String str2, StatsTraceContext statsTraceContext, TransportTracer transportTracer) {
        super(new OkHttpWritableBufferAllocator(), statsTraceContext, transportTracer, metadata, methodDescriptor.isSafe());
        StatsTraceContext statsTraceContext2 = statsTraceContext;
        this.statsTraceCtx = (StatsTraceContext) Preconditions.checkNotNull(statsTraceContext2, "statsTraceCtx");
        this.method = methodDescriptor;
        this.authority = str;
        this.userAgent = str2;
        this.attributes = okHttpClientTransport.getAttributes();
        this.state = new TransportState(i, statsTraceContext2, obj, asyncFrameWriter, outboundFlowController, okHttpClientTransport);
    }

    protected TransportState transportState() {
        return this.state;
    }

    protected Sink abstractClientStreamSink() {
        return this.sink;
    }

    public MethodType getType() {
        return this.method.getType();
    }

    public int id() {
        return this.id;
    }

    boolean useGet() {
        return this.useGet;
    }

    public void setAuthority(String str) {
        this.authority = (String) Preconditions.checkNotNull(str, "authority");
    }

    public Attributes getAttributes() {
        return this.attributes;
    }

    void setOutboundFlowState(Object obj) {
        this.outboundFlowState = obj;
    }

    Object getOutboundFlowState() {
        return this.outboundFlowState;
    }
}
