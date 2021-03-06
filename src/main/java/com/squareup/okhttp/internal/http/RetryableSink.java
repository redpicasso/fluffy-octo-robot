package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.ProtocolException;
import okio.Buffer;
import okio.Sink;
import okio.Timeout;

public final class RetryableSink implements Sink {
    private boolean closed;
    private final Buffer content;
    private final int limit;

    public void flush() throws IOException {
    }

    public RetryableSink(int i) {
        this.content = new Buffer();
        this.limit = i;
    }

    public RetryableSink() {
        this(-1);
    }

    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            if (this.content.size() < ((long) this.limit)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("content-length promised ");
                stringBuilder.append(this.limit);
                stringBuilder.append(" bytes, but received ");
                stringBuilder.append(this.content.size());
                throw new ProtocolException(stringBuilder.toString());
            }
        }
    }

    public void write(Buffer buffer, long j) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        Util.checkOffsetAndCount(buffer.size(), 0, j);
        if (this.limit == -1 || this.content.size() <= ((long) this.limit) - j) {
            this.content.write(buffer, j);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("exceeded content-length limit of ");
        stringBuilder.append(this.limit);
        stringBuilder.append(" bytes");
        throw new ProtocolException(stringBuilder.toString());
    }

    public Timeout timeout() {
        return Timeout.NONE;
    }

    public long contentLength() throws IOException {
        return this.content.size();
    }

    public void writeToSocket(Sink sink) throws IOException {
        Buffer buffer = new Buffer();
        Buffer buffer2 = this.content;
        buffer2.copyTo(buffer, 0, buffer2.size());
        sink.write(buffer, buffer.size());
    }
}
