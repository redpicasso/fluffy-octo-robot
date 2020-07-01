package io.grpc.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Queue;

public class CompositeReadableBuffer extends AbstractReadableBuffer {
    private final Queue<ReadableBuffer> buffers = new ArrayDeque();
    private int readableBytes;

    private static abstract class ReadOperation {
        IOException ex;
        int value;

        abstract int readInternal(ReadableBuffer readableBuffer, int i) throws IOException;

        private ReadOperation() {
        }

        /* synthetic */ ReadOperation(AnonymousClass1 anonymousClass1) {
            this();
        }

        final void read(ReadableBuffer readableBuffer, int i) {
            try {
                this.value = readInternal(readableBuffer, i);
            } catch (IOException e) {
                this.ex = e;
            }
        }

        final boolean isError() {
            return this.ex != null;
        }
    }

    public void addBuffer(ReadableBuffer readableBuffer) {
        if (readableBuffer instanceof CompositeReadableBuffer) {
            CompositeReadableBuffer compositeReadableBuffer = (CompositeReadableBuffer) readableBuffer;
            while (!compositeReadableBuffer.buffers.isEmpty()) {
                this.buffers.add((ReadableBuffer) compositeReadableBuffer.buffers.remove());
            }
            this.readableBytes += compositeReadableBuffer.readableBytes;
            compositeReadableBuffer.readableBytes = 0;
            compositeReadableBuffer.close();
            return;
        }
        this.buffers.add(readableBuffer);
        this.readableBytes += readableBuffer.readableBytes();
    }

    public int readableBytes() {
        return this.readableBytes;
    }

    public int readUnsignedByte() {
        ReadOperation anonymousClass1 = new ReadOperation() {
            int readInternal(ReadableBuffer readableBuffer, int i) {
                return readableBuffer.readUnsignedByte();
            }
        };
        execute(anonymousClass1, 1);
        return anonymousClass1.value;
    }

    public void skipBytes(int i) {
        execute(new ReadOperation() {
            public int readInternal(ReadableBuffer readableBuffer, int i) {
                readableBuffer.skipBytes(i);
                return 0;
            }
        }, i);
    }

    public void readBytes(final byte[] bArr, final int i, int i2) {
        execute(new ReadOperation() {
            int currentOffset = i;

            public int readInternal(ReadableBuffer readableBuffer, int i) {
                readableBuffer.readBytes(bArr, this.currentOffset, i);
                this.currentOffset += i;
                return 0;
            }
        }, i2);
    }

    public void readBytes(final ByteBuffer byteBuffer) {
        execute(new ReadOperation() {
            public int readInternal(ReadableBuffer readableBuffer, int i) {
                int limit = byteBuffer.limit();
                ByteBuffer byteBuffer = byteBuffer;
                byteBuffer.limit(byteBuffer.position() + i);
                readableBuffer.readBytes(byteBuffer);
                byteBuffer.limit(limit);
                return 0;
            }
        }, byteBuffer.remaining());
    }

    public void readBytes(final OutputStream outputStream, int i) throws IOException {
        ReadOperation anonymousClass5 = new ReadOperation() {
            public int readInternal(ReadableBuffer readableBuffer, int i) throws IOException {
                readableBuffer.readBytes(outputStream, i);
                return 0;
            }
        };
        execute(anonymousClass5, i);
        if (anonymousClass5.isError()) {
            throw anonymousClass5.ex;
        }
    }

    public CompositeReadableBuffer readBytes(int i) {
        checkReadable(i);
        this.readableBytes -= i;
        CompositeReadableBuffer compositeReadableBuffer = new CompositeReadableBuffer();
        while (i > 0) {
            ReadableBuffer readableBuffer = (ReadableBuffer) this.buffers.peek();
            if (readableBuffer.readableBytes() > i) {
                compositeReadableBuffer.addBuffer(readableBuffer.readBytes(i));
                i = 0;
            } else {
                compositeReadableBuffer.addBuffer((ReadableBuffer) this.buffers.poll());
                i -= readableBuffer.readableBytes();
            }
        }
        return compositeReadableBuffer;
    }

    public void close() {
        while (!this.buffers.isEmpty()) {
            ((ReadableBuffer) this.buffers.remove()).close();
        }
    }

    private void execute(ReadOperation readOperation, int i) {
        checkReadable(i);
        if (!this.buffers.isEmpty()) {
            advanceBufferIfNecessary();
        }
        while (i > 0 && !this.buffers.isEmpty()) {
            ReadableBuffer readableBuffer = (ReadableBuffer) this.buffers.peek();
            int min = Math.min(i, readableBuffer.readableBytes());
            readOperation.read(readableBuffer, min);
            if (!readOperation.isError()) {
                i -= min;
                this.readableBytes -= min;
                advanceBufferIfNecessary();
            } else {
                return;
            }
        }
        if (i > 0) {
            throw new AssertionError("Failed executing read operation");
        }
    }

    private void advanceBufferIfNecessary() {
        if (((ReadableBuffer) this.buffers.peek()).readableBytes() == 0) {
            ((ReadableBuffer) this.buffers.remove()).close();
        }
    }
}
