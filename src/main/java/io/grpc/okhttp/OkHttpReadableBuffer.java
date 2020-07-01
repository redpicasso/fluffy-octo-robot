package io.grpc.okhttp;

import io.grpc.internal.AbstractReadableBuffer;
import io.grpc.internal.ReadableBuffer;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import okio.Buffer;

class OkHttpReadableBuffer extends AbstractReadableBuffer {
    private final Buffer buffer;

    OkHttpReadableBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public int readableBytes() {
        return (int) this.buffer.size();
    }

    public int readUnsignedByte() {
        return this.buffer.readByte() & 255;
    }

    public void skipBytes(int i) {
        try {
            this.buffer.skip((long) i);
        } catch (EOFException e) {
            throw new IndexOutOfBoundsException(e.getMessage());
        }
    }

    public void readBytes(byte[] bArr, int i, int i2) {
        while (i2 > 0) {
            int read = this.buffer.read(bArr, i, i2);
            if (read != -1) {
                i2 -= read;
                i += read;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("EOF trying to read ");
                stringBuilder.append(i2);
                stringBuilder.append(" bytes");
                throw new IndexOutOfBoundsException(stringBuilder.toString());
            }
        }
    }

    public void readBytes(ByteBuffer byteBuffer) {
        throw new UnsupportedOperationException();
    }

    public void readBytes(OutputStream outputStream, int i) throws IOException {
        this.buffer.writeTo(outputStream, (long) i);
    }

    public ReadableBuffer readBytes(int i) {
        Buffer buffer = new Buffer();
        buffer.write(this.buffer, (long) i);
        return new OkHttpReadableBuffer(buffer);
    }

    public void close() {
        this.buffer.clear();
    }
}
