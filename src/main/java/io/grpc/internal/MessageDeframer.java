package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.Codec.Identity;
import io.grpc.Decompressor;
import io.grpc.Status;
import io.grpc.internal.StreamListener.MessageProducer;
import java.io.Closeable;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class MessageDeframer implements Closeable, Deframer {
    private static final int COMPRESSED_FLAG_MASK = 1;
    private static final int HEADER_LENGTH = 5;
    private static final int MAX_BUFFER_SIZE = 2097152;
    private static final int RESERVED_MASK = 254;
    private boolean closeWhenComplete = false;
    private boolean compressedFlag;
    private int currentMessageSeqNo = -1;
    private Decompressor decompressor;
    private GzipInflatingBuffer fullStreamDecompressor;
    private boolean inDelivery = false;
    private int inboundBodyWireSize;
    private byte[] inflatedBuffer;
    private int inflatedIndex;
    private Listener listener;
    private int maxInboundMessageSize;
    private CompositeReadableBuffer nextFrame;
    private long pendingDeliveries;
    private int requiredLength = 5;
    private State state = State.HEADER;
    private final StatsTraceContext statsTraceCtx;
    private volatile boolean stopDelivery = false;
    private final TransportTracer transportTracer;
    private CompositeReadableBuffer unprocessed = new CompositeReadableBuffer();

    /* renamed from: io.grpc.internal.MessageDeframer$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$grpc$internal$MessageDeframer$State = new int[State.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:5:0x001f, code:
            return;
     */
        static {
            /*
            r0 = io.grpc.internal.MessageDeframer.State.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$io$grpc$internal$MessageDeframer$State = r0;
            r0 = $SwitchMap$io$grpc$internal$MessageDeframer$State;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = io.grpc.internal.MessageDeframer.State.HEADER;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$io$grpc$internal$MessageDeframer$State;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = io.grpc.internal.MessageDeframer.State.BODY;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.MessageDeframer.1.<clinit>():void");
        }
    }

    public interface Listener {
        void bytesRead(int i);

        void deframeFailed(Throwable th);

        void deframerClosed(boolean z);

        void messagesAvailable(MessageProducer messageProducer);
    }

    @VisibleForTesting
    static final class SizeEnforcingInputStream extends FilterInputStream {
        private long count;
        private long mark = -1;
        private long maxCount;
        private final int maxMessageSize;
        private final StatsTraceContext statsTraceCtx;

        SizeEnforcingInputStream(InputStream inputStream, int i, StatsTraceContext statsTraceContext) {
            super(inputStream);
            this.maxMessageSize = i;
            this.statsTraceCtx = statsTraceContext;
        }

        public int read() throws IOException {
            int read = this.in.read();
            if (read != -1) {
                this.count++;
            }
            verifySize();
            reportCount();
            return read;
        }

        public int read(byte[] bArr, int i, int i2) throws IOException {
            int read = this.in.read(bArr, i, i2);
            if (read != -1) {
                this.count += (long) read;
            }
            verifySize();
            reportCount();
            return read;
        }

        public long skip(long j) throws IOException {
            j = this.in.skip(j);
            this.count += j;
            verifySize();
            reportCount();
            return j;
        }

        public synchronized void mark(int i) {
            this.in.mark(i);
            this.mark = this.count;
        }

        public synchronized void reset() throws IOException {
            if (!this.in.markSupported()) {
                throw new IOException("Mark not supported");
            } else if (this.mark != -1) {
                this.in.reset();
                this.count = this.mark;
            } else {
                throw new IOException("Mark not set");
            }
        }

        private void reportCount() {
            long j = this.count;
            long j2 = this.maxCount;
            if (j > j2) {
                this.statsTraceCtx.inboundUncompressedSize(j - j2);
                this.maxCount = this.count;
            }
        }

        private void verifySize() {
            if (this.count > ((long) this.maxMessageSize)) {
                throw Status.RESOURCE_EXHAUSTED.withDescription(String.format("Compressed gRPC message exceeds maximum size %d: %d bytes read", new Object[]{Integer.valueOf(this.maxMessageSize), Long.valueOf(this.count)})).asRuntimeException();
            }
        }
    }

    private enum State {
        HEADER,
        BODY
    }

    private static class SingleMessageProducer implements MessageProducer {
        private InputStream message;

        /* synthetic */ SingleMessageProducer(InputStream inputStream, AnonymousClass1 anonymousClass1) {
            this(inputStream);
        }

        private SingleMessageProducer(InputStream inputStream) {
            this.message = inputStream;
        }

        @Nullable
        public InputStream next() {
            InputStream inputStream = this.message;
            this.message = null;
            return inputStream;
        }
    }

    public MessageDeframer(Listener listener, Decompressor decompressor, int i, StatsTraceContext statsTraceContext, TransportTracer transportTracer) {
        this.listener = (Listener) Preconditions.checkNotNull(listener, "sink");
        this.decompressor = (Decompressor) Preconditions.checkNotNull(decompressor, "decompressor");
        this.maxInboundMessageSize = i;
        this.statsTraceCtx = (StatsTraceContext) Preconditions.checkNotNull(statsTraceContext, "statsTraceCtx");
        this.transportTracer = (TransportTracer) Preconditions.checkNotNull(transportTracer, "transportTracer");
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setMaxInboundMessageSize(int i) {
        this.maxInboundMessageSize = i;
    }

    public void setDecompressor(Decompressor decompressor) {
        Preconditions.checkState(this.fullStreamDecompressor == null, "Already set full stream decompressor");
        this.decompressor = (Decompressor) Preconditions.checkNotNull(decompressor, "Can't pass an empty decompressor");
    }

    public void setFullStreamDecompressor(GzipInflatingBuffer gzipInflatingBuffer) {
        boolean z = true;
        Preconditions.checkState(this.decompressor == Identity.NONE, "per-message decompressor already set");
        if (this.fullStreamDecompressor != null) {
            z = false;
        }
        Preconditions.checkState(z, "full stream decompressor already set");
        this.fullStreamDecompressor = (GzipInflatingBuffer) Preconditions.checkNotNull(gzipInflatingBuffer, "Can't pass a null full stream decompressor");
        this.unprocessed = null;
    }

    public void request(int i) {
        Preconditions.checkArgument(i > 0, "numMessages must be > 0");
        if (!isClosed()) {
            this.pendingDeliveries += (long) i;
            deliver();
        }
    }

    public void deframe(ReadableBuffer readableBuffer) {
        Preconditions.checkNotNull(readableBuffer, "data");
        Object obj = 1;
        try {
            if (!isClosedOrScheduledToClose()) {
                if (this.fullStreamDecompressor != null) {
                    this.fullStreamDecompressor.addGzippedBytes(readableBuffer);
                } else {
                    this.unprocessed.addBuffer(readableBuffer);
                }
                obj = null;
                deliver();
            }
            if (obj != null) {
                readableBuffer.close();
            }
        } catch (Throwable th) {
            if (1 != null) {
                readableBuffer.close();
            }
        }
    }

    public void closeWhenComplete() {
        if (!isClosed()) {
            if (isStalled()) {
                close();
            } else {
                this.closeWhenComplete = true;
            }
        }
    }

    void stopDelivery() {
        this.stopDelivery = true;
    }

    public void close() {
        if (!isClosed()) {
            CompositeReadableBuffer compositeReadableBuffer = this.nextFrame;
            boolean z = compositeReadableBuffer != null && compositeReadableBuffer.readableBytes() > 0;
            try {
                if (this.fullStreamDecompressor != null) {
                    z = z || this.fullStreamDecompressor.hasPartialData();
                    this.fullStreamDecompressor.close();
                }
                if (this.unprocessed != null) {
                    this.unprocessed.close();
                }
                if (this.nextFrame != null) {
                    this.nextFrame.close();
                }
                this.fullStreamDecompressor = null;
                this.unprocessed = null;
                this.nextFrame = null;
                this.listener.deframerClosed(z);
            } catch (Throwable th) {
                this.fullStreamDecompressor = null;
                this.unprocessed = null;
                this.nextFrame = null;
            }
        }
    }

    public boolean isClosed() {
        return this.unprocessed == null && this.fullStreamDecompressor == null;
    }

    private boolean isClosedOrScheduledToClose() {
        return isClosed() || this.closeWhenComplete;
    }

    private boolean isStalled() {
        GzipInflatingBuffer gzipInflatingBuffer = this.fullStreamDecompressor;
        if (gzipInflatingBuffer != null) {
            return gzipInflatingBuffer.isStalled();
        }
        return this.unprocessed.readableBytes() == 0;
    }

    private void deliver() {
        if (!this.inDelivery) {
            this.inDelivery = true;
            while (!this.stopDelivery && this.pendingDeliveries > 0 && readRequiredBytes()) {
                try {
                    int i = AnonymousClass1.$SwitchMap$io$grpc$internal$MessageDeframer$State[this.state.ordinal()];
                    if (i == 1) {
                        processHeader();
                    } else if (i == 2) {
                        processBody();
                        this.pendingDeliveries--;
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Invalid state: ");
                        stringBuilder.append(this.state);
                        throw new AssertionError(stringBuilder.toString());
                    }
                } finally {
                    this.inDelivery = false;
                }
            }
            if (this.stopDelivery) {
                close();
                return;
            }
            if (this.closeWhenComplete && isStalled()) {
                close();
            }
            this.inDelivery = false;
        }
    }

    private boolean readRequiredBytes() {
        Throwable e;
        int i;
        int i2;
        try {
            if (this.nextFrame == null) {
                this.nextFrame = new CompositeReadableBuffer();
            }
            i = 0;
            i2 = 0;
            while (true) {
                try {
                    int readableBytes = this.requiredLength - this.nextFrame.readableBytes();
                    if (readableBytes <= 0) {
                        if (i > 0) {
                            this.listener.bytesRead(i);
                            if (this.state == State.BODY) {
                                if (this.fullStreamDecompressor != null) {
                                    this.statsTraceCtx.inboundWireSize((long) i2);
                                    this.inboundBodyWireSize += i2;
                                } else {
                                    this.statsTraceCtx.inboundWireSize((long) i);
                                    this.inboundBodyWireSize += i;
                                }
                            }
                        }
                        return true;
                    } else if (this.fullStreamDecompressor != null) {
                        if (this.inflatedBuffer == null || this.inflatedIndex == this.inflatedBuffer.length) {
                            this.inflatedBuffer = new byte[Math.min(readableBytes, 2097152)];
                            this.inflatedIndex = 0;
                        }
                        readableBytes = this.fullStreamDecompressor.inflateBytes(this.inflatedBuffer, this.inflatedIndex, Math.min(readableBytes, this.inflatedBuffer.length - this.inflatedIndex));
                        i += this.fullStreamDecompressor.getAndResetBytesConsumed();
                        i2 += this.fullStreamDecompressor.getAndResetDeflatedBytesConsumed();
                        if (readableBytes == 0) {
                            if (i > 0) {
                                this.listener.bytesRead(i);
                                if (this.state == State.BODY) {
                                    if (this.fullStreamDecompressor != null) {
                                        this.statsTraceCtx.inboundWireSize((long) i2);
                                        this.inboundBodyWireSize += i2;
                                    } else {
                                        this.statsTraceCtx.inboundWireSize((long) i);
                                        this.inboundBodyWireSize += i;
                                    }
                                }
                            }
                            return false;
                        }
                        this.nextFrame.addBuffer(ReadableBuffers.wrap(this.inflatedBuffer, this.inflatedIndex, readableBytes));
                        this.inflatedIndex += readableBytes;
                    } else if (this.unprocessed.readableBytes() == 0) {
                        if (i > 0) {
                            this.listener.bytesRead(i);
                            if (this.state == State.BODY) {
                                if (this.fullStreamDecompressor != null) {
                                    this.statsTraceCtx.inboundWireSize((long) i2);
                                    this.inboundBodyWireSize += i2;
                                } else {
                                    this.statsTraceCtx.inboundWireSize((long) i);
                                    this.inboundBodyWireSize += i;
                                }
                            }
                        }
                        return false;
                    } else {
                        readableBytes = Math.min(readableBytes, this.unprocessed.readableBytes());
                        i += readableBytes;
                        this.nextFrame.addBuffer(this.unprocessed.readBytes(readableBytes));
                    }
                } catch (Throwable e2) {
                    throw new RuntimeException(e2);
                } catch (Throwable e22) {
                    throw new RuntimeException(e22);
                } catch (Throwable th) {
                    e22 = th;
                }
            }
        } catch (Throwable th2) {
            e22 = th2;
            i = 0;
            i2 = 0;
            if (i > 0) {
                this.listener.bytesRead(i);
                if (this.state == State.BODY) {
                    if (this.fullStreamDecompressor != null) {
                        this.statsTraceCtx.inboundWireSize((long) i2);
                        this.inboundBodyWireSize += i2;
                    } else {
                        this.statsTraceCtx.inboundWireSize((long) i);
                        this.inboundBodyWireSize += i;
                    }
                }
            }
            throw e22;
        }
    }

    private void processHeader() {
        int readUnsignedByte = this.nextFrame.readUnsignedByte();
        if ((readUnsignedByte & 254) == 0) {
            this.compressedFlag = (readUnsignedByte & 1) != 0;
            this.requiredLength = this.nextFrame.readInt();
            readUnsignedByte = this.requiredLength;
            if (readUnsignedByte < 0 || readUnsignedByte > this.maxInboundMessageSize) {
                throw Status.RESOURCE_EXHAUSTED.withDescription(String.format("gRPC message exceeds maximum size %d: %d", new Object[]{Integer.valueOf(this.maxInboundMessageSize), Integer.valueOf(this.requiredLength)})).asRuntimeException();
            }
            this.currentMessageSeqNo++;
            this.statsTraceCtx.inboundMessage(this.currentMessageSeqNo);
            this.transportTracer.reportMessageReceived();
            this.state = State.BODY;
            return;
        }
        throw Status.INTERNAL.withDescription("gRPC frame header malformed: reserved bits not zero").asRuntimeException();
    }

    private void processBody() {
        this.statsTraceCtx.inboundMessageRead(this.currentMessageSeqNo, (long) this.inboundBodyWireSize, -1);
        this.inboundBodyWireSize = 0;
        InputStream compressedBody = this.compressedFlag ? getCompressedBody() : getUncompressedBody();
        this.nextFrame = null;
        this.listener.messagesAvailable(new SingleMessageProducer(compressedBody, null));
        this.state = State.HEADER;
        this.requiredLength = 5;
    }

    private InputStream getUncompressedBody() {
        this.statsTraceCtx.inboundUncompressedSize((long) this.nextFrame.readableBytes());
        return ReadableBuffers.openStream(this.nextFrame, true);
    }

    private InputStream getCompressedBody() {
        if (this.decompressor != Identity.NONE) {
            try {
                return new SizeEnforcingInputStream(this.decompressor.decompress(ReadableBuffers.openStream(this.nextFrame, true)), this.maxInboundMessageSize, this.statsTraceCtx);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        throw Status.INTERNAL.withDescription("Can't decode compressed gRPC message as compression not configured").asRuntimeException();
    }
}
