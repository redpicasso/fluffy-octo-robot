package io.grpc.internal;

import com.google.common.base.Preconditions;
import io.grpc.Codec.Identity;
import io.grpc.Compressor;
import io.grpc.Drainable;
import io.grpc.KnownLength;
import io.grpc.Status;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public class MessageFramer implements Framer {
    private static final byte COMPRESSED = (byte) 1;
    private static final int HEADER_LENGTH = 5;
    private static final int NO_MAX_OUTBOUND_MESSAGE_SIZE = -1;
    private static final byte UNCOMPRESSED = (byte) 0;
    private WritableBuffer buffer;
    private final WritableBufferAllocator bufferAllocator;
    private boolean closed;
    private Compressor compressor = Identity.NONE;
    private int currentMessageSeqNo = -1;
    private long currentMessageWireSize;
    private final byte[] headerScratch = new byte[5];
    private int maxOutboundMessageSize = -1;
    private boolean messageCompression = true;
    private int messagesBuffered;
    private final OutputStreamAdapter outputStreamAdapter = new OutputStreamAdapter();
    private final Sink sink;
    private final StatsTraceContext statsTraceCtx;

    private final class BufferChainOutputStream extends OutputStream {
        private final List<WritableBuffer> bufferList;
        private WritableBuffer current;

        private BufferChainOutputStream() {
            this.bufferList = new ArrayList();
        }

        public void write(int i) throws IOException {
            WritableBuffer writableBuffer = this.current;
            if (writableBuffer == null || writableBuffer.writableBytes() <= 0) {
                write(new byte[]{(byte) i}, 0, 1);
                return;
            }
            this.current.write((byte) i);
        }

        public void write(byte[] bArr, int i, int i2) {
            if (this.current == null) {
                this.current = MessageFramer.this.bufferAllocator.allocate(i2);
                this.bufferList.add(this.current);
            }
            while (i2 > 0) {
                int min = Math.min(i2, this.current.writableBytes());
                if (min == 0) {
                    this.current = MessageFramer.this.bufferAllocator.allocate(Math.max(i2, this.current.readableBytes() * 2));
                    this.bufferList.add(this.current);
                } else {
                    this.current.write(bArr, i, min);
                    i += min;
                    i2 -= min;
                }
            }
        }

        private int readableBytes() {
            int i = 0;
            for (WritableBuffer readableBytes : this.bufferList) {
                i += readableBytes.readableBytes();
            }
            return i;
        }
    }

    private class OutputStreamAdapter extends OutputStream {
        private OutputStreamAdapter() {
        }

        public void write(int i) {
            write(new byte[]{(byte) i}, 0, 1);
        }

        public void write(byte[] bArr, int i, int i2) {
            MessageFramer.this.writeRaw(bArr, i, i2);
        }
    }

    public interface Sink {
        void deliverFrame(@Nullable WritableBuffer writableBuffer, boolean z, boolean z2, int i);
    }

    public MessageFramer(Sink sink, WritableBufferAllocator writableBufferAllocator, StatsTraceContext statsTraceContext) {
        this.sink = (Sink) Preconditions.checkNotNull(sink, "sink");
        this.bufferAllocator = (WritableBufferAllocator) Preconditions.checkNotNull(writableBufferAllocator, "bufferAllocator");
        this.statsTraceCtx = (StatsTraceContext) Preconditions.checkNotNull(statsTraceContext, "statsTraceCtx");
    }

    public MessageFramer setCompressor(Compressor compressor) {
        this.compressor = (Compressor) Preconditions.checkNotNull(compressor, "Can't pass an empty compressor");
        return this;
    }

    public MessageFramer setMessageCompression(boolean z) {
        this.messageCompression = z;
        return this;
    }

    public void setMaxOutboundMessageSize(int i) {
        Preconditions.checkState(this.maxOutboundMessageSize == -1, "max size already set");
        this.maxOutboundMessageSize = i;
    }

    public void writePayload(InputStream inputStream) {
        String str = "Failed to frame message";
        verifyNotClosed();
        this.messagesBuffered++;
        this.currentMessageSeqNo++;
        this.currentMessageWireSize = 0;
        this.statsTraceCtx.outboundMessage(this.currentMessageSeqNo);
        Object obj = (!this.messageCompression || this.compressor == Identity.NONE) ? null : 1;
        try {
            int writeUncompressed;
            int knownLength = getKnownLength(inputStream);
            if (knownLength == 0 || obj == null) {
                writeUncompressed = writeUncompressed(inputStream, knownLength);
            } else {
                writeUncompressed = writeCompressed(inputStream, knownLength);
            }
            if (knownLength == -1 || writeUncompressed == knownLength) {
                long j = (long) writeUncompressed;
                this.statsTraceCtx.outboundUncompressedSize(j);
                this.statsTraceCtx.outboundWireSize(this.currentMessageWireSize);
                this.statsTraceCtx.outboundMessageSent(this.currentMessageSeqNo, this.currentMessageWireSize, j);
                return;
            }
            throw Status.INTERNAL.withDescription(String.format("Message length inaccurate %s != %s", new Object[]{Integer.valueOf(writeUncompressed), Integer.valueOf(knownLength)})).asRuntimeException();
        } catch (Throwable e) {
            throw Status.INTERNAL.withDescription(str).withCause(e).asRuntimeException();
        } catch (Throwable e2) {
            throw Status.INTERNAL.withDescription(str).withCause(e2).asRuntimeException();
        }
    }

    private int writeUncompressed(InputStream inputStream, int i) throws IOException {
        if (i != -1) {
            this.currentMessageWireSize = (long) i;
            return writeKnownLengthUncompressed(inputStream, i);
        }
        OutputStream bufferChainOutputStream = new BufferChainOutputStream();
        int writeToOutputStream = writeToOutputStream(inputStream, bufferChainOutputStream);
        int i2 = this.maxOutboundMessageSize;
        if (i2 < 0 || writeToOutputStream <= i2) {
            writeBufferChain(bufferChainOutputStream, false);
            return writeToOutputStream;
        }
        throw Status.RESOURCE_EXHAUSTED.withDescription(String.format("message too large %d > %d", new Object[]{Integer.valueOf(writeToOutputStream), Integer.valueOf(this.maxOutboundMessageSize)})).asRuntimeException();
    }

    private int writeCompressed(InputStream inputStream, int i) throws IOException {
        OutputStream bufferChainOutputStream = new BufferChainOutputStream();
        OutputStream compress = this.compressor.compress(bufferChainOutputStream);
        try {
            int writeToOutputStream = writeToOutputStream(inputStream, compress);
            int i2 = this.maxOutboundMessageSize;
            if (i2 < 0 || writeToOutputStream <= i2) {
                writeBufferChain(bufferChainOutputStream, true);
                return writeToOutputStream;
            }
            throw Status.RESOURCE_EXHAUSTED.withDescription(String.format("message too large %d > %d", new Object[]{Integer.valueOf(writeToOutputStream), Integer.valueOf(this.maxOutboundMessageSize)})).asRuntimeException();
        } finally {
            compress.close();
        }
    }

    private int getKnownLength(InputStream inputStream) throws IOException {
        if ((inputStream instanceof KnownLength) || (inputStream instanceof ByteArrayInputStream)) {
            return inputStream.available();
        }
        return -1;
    }

    private int writeKnownLengthUncompressed(InputStream inputStream, int i) throws IOException {
        int i2 = this.maxOutboundMessageSize;
        if (i2 < 0 || i <= i2) {
            ByteBuffer wrap = ByteBuffer.wrap(this.headerScratch);
            wrap.put((byte) 0);
            wrap.putInt(i);
            if (this.buffer == null) {
                this.buffer = this.bufferAllocator.allocate(wrap.position() + i);
            }
            writeRaw(this.headerScratch, 0, wrap.position());
            return writeToOutputStream(inputStream, this.outputStreamAdapter);
        }
        throw Status.RESOURCE_EXHAUSTED.withDescription(String.format("message too large %d > %d", new Object[]{Integer.valueOf(i), Integer.valueOf(this.maxOutboundMessageSize)})).asRuntimeException();
    }

    private void writeBufferChain(BufferChainOutputStream bufferChainOutputStream, boolean z) {
        ByteBuffer wrap = ByteBuffer.wrap(this.headerScratch);
        wrap.put(z);
        int access$200 = bufferChainOutputStream.readableBytes();
        wrap.putInt(access$200);
        WritableBuffer allocate = this.bufferAllocator.allocate(5);
        allocate.write(this.headerScratch, 0, wrap.position());
        if (access$200 == 0) {
            this.buffer = allocate;
            return;
        }
        this.sink.deliverFrame(allocate, false, false, this.messagesBuffered - 1);
        this.messagesBuffered = 1;
        List access$300 = bufferChainOutputStream.bufferList;
        for (int i = 0; i < access$300.size() - 1; i++) {
            this.sink.deliverFrame((WritableBuffer) access$300.get(i), false, false, 0);
        }
        this.buffer = (WritableBuffer) access$300.get(access$300.size() - 1);
        this.currentMessageWireSize = (long) access$200;
    }

    private static int writeToOutputStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (inputStream instanceof Drainable) {
            return ((Drainable) inputStream).drainTo(outputStream);
        }
        long copy = IoUtils.copy(inputStream, outputStream);
        Preconditions.checkArgument(copy <= 2147483647L, "Message size overflow: %s", copy);
        return (int) copy;
    }

    private void writeRaw(byte[] bArr, int i, int i2) {
        while (i2 > 0) {
            WritableBuffer writableBuffer = this.buffer;
            if (writableBuffer != null && writableBuffer.writableBytes() == 0) {
                commitToSink(false, false);
            }
            if (this.buffer == null) {
                this.buffer = this.bufferAllocator.allocate(i2);
            }
            int min = Math.min(i2, this.buffer.writableBytes());
            this.buffer.write(bArr, i, min);
            i += min;
            i2 -= min;
        }
    }

    public void flush() {
        WritableBuffer writableBuffer = this.buffer;
        if (writableBuffer != null && writableBuffer.readableBytes() > 0) {
            commitToSink(false, true);
        }
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void close() {
        if (!isClosed()) {
            this.closed = true;
            WritableBuffer writableBuffer = this.buffer;
            if (writableBuffer != null && writableBuffer.readableBytes() == 0) {
                releaseBuffer();
            }
            commitToSink(true, true);
        }
    }

    public void dispose() {
        this.closed = true;
        releaseBuffer();
    }

    private void releaseBuffer() {
        WritableBuffer writableBuffer = this.buffer;
        if (writableBuffer != null) {
            writableBuffer.release();
            this.buffer = null;
        }
    }

    private void commitToSink(boolean z, boolean z2) {
        WritableBuffer writableBuffer = this.buffer;
        this.buffer = null;
        this.sink.deliverFrame(writableBuffer, z, z2, this.messagesBuffered);
        this.messagesBuffered = 0;
    }

    private void verifyNotClosed() {
        if (isClosed()) {
            throw new IllegalStateException("Framer already closed");
        }
    }
}
