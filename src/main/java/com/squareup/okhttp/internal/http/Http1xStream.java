package com.squareup.okhttp.internal.http;

import com.google.common.net.HttpHeaders;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.io.RealConnection;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingTimeout;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class Http1xStream implements HttpStream {
    private static final int STATE_CLOSED = 6;
    private static final int STATE_IDLE = 0;
    private static final int STATE_OPEN_REQUEST_BODY = 1;
    private static final int STATE_OPEN_RESPONSE_BODY = 4;
    private static final int STATE_READING_RESPONSE_BODY = 5;
    private static final int STATE_READ_RESPONSE_HEADERS = 3;
    private static final int STATE_WRITING_REQUEST_BODY = 2;
    private HttpEngine httpEngine;
    private final BufferedSink sink;
    private final BufferedSource source;
    private int state = 0;
    private final StreamAllocation streamAllocation;

    private abstract class AbstractSource implements Source {
        protected boolean closed;
        protected final ForwardingTimeout timeout;

        private AbstractSource() {
            this.timeout = new ForwardingTimeout(Http1xStream.this.source.timeout());
        }

        public Timeout timeout() {
            return this.timeout;
        }

        protected final void endOfInput() throws IOException {
            if (Http1xStream.this.state == 5) {
                Http1xStream.this.detachTimeout(this.timeout);
                Http1xStream.this.state = 6;
                if (Http1xStream.this.streamAllocation != null) {
                    Http1xStream.this.streamAllocation.streamFinished(Http1xStream.this);
                    return;
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("state: ");
            stringBuilder.append(Http1xStream.this.state);
            throw new IllegalStateException(stringBuilder.toString());
        }

        protected final void unexpectedEndOfInput() {
            if (Http1xStream.this.state != 6) {
                Http1xStream.this.state = 6;
                if (Http1xStream.this.streamAllocation != null) {
                    Http1xStream.this.streamAllocation.noNewStreams();
                    Http1xStream.this.streamAllocation.streamFinished(Http1xStream.this);
                }
            }
        }
    }

    private final class ChunkedSink implements Sink {
        private boolean closed;
        private final ForwardingTimeout timeout;

        private ChunkedSink() {
            this.timeout = new ForwardingTimeout(Http1xStream.this.sink.timeout());
        }

        public Timeout timeout() {
            return this.timeout;
        }

        public void write(Buffer buffer, long j) throws IOException {
            if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (j != 0) {
                Http1xStream.this.sink.writeHexadecimalUnsignedLong(j);
                String str = "\r\n";
                Http1xStream.this.sink.writeUtf8(str);
                Http1xStream.this.sink.write(buffer, j);
                Http1xStream.this.sink.writeUtf8(str);
            }
        }

        public synchronized void flush() throws IOException {
            if (!this.closed) {
                Http1xStream.this.sink.flush();
            }
        }

        public synchronized void close() throws IOException {
            if (!this.closed) {
                this.closed = true;
                Http1xStream.this.sink.writeUtf8("0\r\n\r\n");
                Http1xStream.this.detachTimeout(this.timeout);
                Http1xStream.this.state = 3;
            }
        }
    }

    private final class FixedLengthSink implements Sink {
        private long bytesRemaining;
        private boolean closed;
        private final ForwardingTimeout timeout;

        private FixedLengthSink(long j) {
            this.timeout = new ForwardingTimeout(Http1xStream.this.sink.timeout());
            this.bytesRemaining = j;
        }

        public Timeout timeout() {
            return this.timeout;
        }

        public void write(Buffer buffer, long j) throws IOException {
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            Util.checkOffsetAndCount(buffer.size(), 0, j);
            if (j <= this.bytesRemaining) {
                Http1xStream.this.sink.write(buffer, j);
                this.bytesRemaining -= j;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("expected ");
            stringBuilder.append(this.bytesRemaining);
            stringBuilder.append(" bytes but received ");
            stringBuilder.append(j);
            throw new ProtocolException(stringBuilder.toString());
        }

        public void flush() throws IOException {
            if (!this.closed) {
                Http1xStream.this.sink.flush();
            }
        }

        public void close() throws IOException {
            if (!this.closed) {
                this.closed = true;
                if (this.bytesRemaining <= 0) {
                    Http1xStream.this.detachTimeout(this.timeout);
                    Http1xStream.this.state = 3;
                    return;
                }
                throw new ProtocolException("unexpected end of stream");
            }
        }
    }

    private class ChunkedSource extends AbstractSource {
        private static final long NO_CHUNK_YET = -1;
        private long bytesRemainingInChunk = -1;
        private boolean hasMoreChunks = true;
        private final HttpEngine httpEngine;

        ChunkedSource(HttpEngine httpEngine) throws IOException {
            super();
            this.httpEngine = httpEngine;
        }

        public long read(Buffer buffer, long j) throws IOException {
            if (j < 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("byteCount < 0: ");
                stringBuilder.append(j);
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (!this.hasMoreChunks) {
                return -1;
            } else {
                long j2 = this.bytesRemainingInChunk;
                if (j2 == 0 || j2 == -1) {
                    readChunkSize();
                    if (!this.hasMoreChunks) {
                        return -1;
                    }
                }
                long read = Http1xStream.this.source.read(buffer, Math.min(j, this.bytesRemainingInChunk));
                if (read != -1) {
                    this.bytesRemainingInChunk -= read;
                    return read;
                }
                unexpectedEndOfInput();
                throw new ProtocolException("unexpected end of stream");
            }
        }

        private void readChunkSize() throws IOException {
            if (this.bytesRemainingInChunk != -1) {
                Http1xStream.this.source.readUtf8LineStrict();
            }
            try {
                this.bytesRemainingInChunk = Http1xStream.this.source.readHexadecimalUnsignedLong();
                String trim = Http1xStream.this.source.readUtf8LineStrict().trim();
                if (this.bytesRemainingInChunk < 0 || !(trim.isEmpty() || trim.startsWith(";"))) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("expected chunk size and optional extensions but was \"");
                    stringBuilder.append(this.bytesRemainingInChunk);
                    stringBuilder.append(trim);
                    stringBuilder.append("\"");
                    throw new ProtocolException(stringBuilder.toString());
                } else if (this.bytesRemainingInChunk == 0) {
                    this.hasMoreChunks = false;
                    this.httpEngine.receiveHeaders(Http1xStream.this.readHeaders());
                    endOfInput();
                }
            } catch (NumberFormatException e) {
                throw new ProtocolException(e.getMessage());
            }
        }

        public void close() throws IOException {
            if (!this.closed) {
                if (this.hasMoreChunks && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }
    }

    private class FixedLengthSource extends AbstractSource {
        private long bytesRemaining;

        public FixedLengthSource(long j) throws IOException {
            super();
            this.bytesRemaining = j;
            if (this.bytesRemaining == 0) {
                endOfInput();
            }
        }

        public long read(Buffer buffer, long j) throws IOException {
            if (j < 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("byteCount < 0: ");
                stringBuilder.append(j);
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (this.bytesRemaining == 0) {
                return -1;
            } else {
                long read = Http1xStream.this.source.read(buffer, Math.min(this.bytesRemaining, j));
                if (read != -1) {
                    this.bytesRemaining -= read;
                    if (this.bytesRemaining == 0) {
                        endOfInput();
                    }
                    return read;
                }
                unexpectedEndOfInput();
                throw new ProtocolException("unexpected end of stream");
            }
        }

        public void close() throws IOException {
            if (!this.closed) {
                if (!(this.bytesRemaining == 0 || Util.discard(this, 100, TimeUnit.MILLISECONDS))) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }
    }

    private class UnknownLengthSource extends AbstractSource {
        private boolean inputExhausted;

        private UnknownLengthSource() {
            super();
        }

        public long read(Buffer buffer, long j) throws IOException {
            if (j < 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("byteCount < 0: ");
                stringBuilder.append(j);
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else if (this.inputExhausted) {
                return -1;
            } else {
                long read = Http1xStream.this.source.read(buffer, j);
                if (read != -1) {
                    return read;
                }
                this.inputExhausted = true;
                endOfInput();
                return -1;
            }
        }

        public void close() throws IOException {
            if (!this.closed) {
                if (!this.inputExhausted) {
                    unexpectedEndOfInput();
                }
                this.closed = true;
            }
        }
    }

    public Http1xStream(StreamAllocation streamAllocation, BufferedSource bufferedSource, BufferedSink bufferedSink) {
        this.streamAllocation = streamAllocation;
        this.source = bufferedSource;
        this.sink = bufferedSink;
    }

    public void setHttpEngine(HttpEngine httpEngine) {
        this.httpEngine = httpEngine;
    }

    public Sink createRequestBody(Request request, long j) throws IOException {
        if ("chunked".equalsIgnoreCase(request.header(HttpHeaders.TRANSFER_ENCODING))) {
            return newChunkedSink();
        }
        if (j != -1) {
            return newFixedLengthSink(j);
        }
        throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
    }

    public void cancel() {
        RealConnection connection = this.streamAllocation.connection();
        if (connection != null) {
            connection.cancel();
        }
    }

    public void writeRequestHeaders(Request request) throws IOException {
        this.httpEngine.writingRequestHeaders();
        writeRequest(request.headers(), RequestLine.get(request, this.httpEngine.getConnection().getRoute().getProxy().type()));
    }

    public Builder readResponseHeaders() throws IOException {
        return readResponse();
    }

    public ResponseBody openResponseBody(Response response) throws IOException {
        return new RealResponseBody(response.headers(), Okio.buffer(getTransferStream(response)));
    }

    private Source getTransferStream(Response response) throws IOException {
        if (!HttpEngine.hasBody(response)) {
            return newFixedLengthSource(0);
        }
        if ("chunked".equalsIgnoreCase(response.header(HttpHeaders.TRANSFER_ENCODING))) {
            return newChunkedSource(this.httpEngine);
        }
        long contentLength = OkHeaders.contentLength(response);
        if (contentLength != -1) {
            return newFixedLengthSource(contentLength);
        }
        return newUnknownLengthSource();
    }

    public boolean isClosed() {
        return this.state == 6;
    }

    public void finishRequest() throws IOException {
        this.sink.flush();
    }

    public void writeRequest(Headers headers, String str) throws IOException {
        if (this.state == 0) {
            String str2 = "\r\n";
            this.sink.writeUtf8(str).writeUtf8(str2);
            int size = headers.size();
            for (int i = 0; i < size; i++) {
                this.sink.writeUtf8(headers.name(i)).writeUtf8(": ").writeUtf8(headers.value(i)).writeUtf8(str2);
            }
            this.sink.writeUtf8(str2);
            this.state = 1;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("state: ");
        stringBuilder.append(this.state);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public Builder readResponse() throws IOException {
        int i = this.state;
        if (i == 1 || i == 3) {
            Builder headers;
            StatusLine parse;
            do {
                try {
                    parse = StatusLine.parse(this.source.readUtf8LineStrict());
                    headers = new Builder().protocol(parse.protocol).code(parse.code).message(parse.message).headers(readHeaders());
                } catch (Throwable e) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("unexpected end of stream on ");
                    stringBuilder.append(this.streamAllocation);
                    IOException iOException = new IOException(stringBuilder.toString());
                    iOException.initCause(e);
                    throw iOException;
                }
            } while (parse.code == 100);
            this.state = 4;
            return headers;
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("state: ");
        stringBuilder2.append(this.state);
        throw new IllegalStateException(stringBuilder2.toString());
    }

    public Headers readHeaders() throws IOException {
        Headers.Builder builder = new Headers.Builder();
        while (true) {
            String readUtf8LineStrict = this.source.readUtf8LineStrict();
            if (readUtf8LineStrict.length() == 0) {
                return builder.build();
            }
            Internal.instance.addLenient(builder, readUtf8LineStrict);
        }
    }

    public Sink newChunkedSink() {
        if (this.state == 1) {
            this.state = 2;
            return new ChunkedSink();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("state: ");
        stringBuilder.append(this.state);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public Sink newFixedLengthSink(long j) {
        if (this.state == 1) {
            this.state = 2;
            return new FixedLengthSink(j);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("state: ");
        stringBuilder.append(this.state);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void writeRequestBody(RetryableSink retryableSink) throws IOException {
        if (this.state == 1) {
            this.state = 3;
            retryableSink.writeToSocket(this.sink);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("state: ");
        stringBuilder.append(this.state);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public Source newFixedLengthSource(long j) throws IOException {
        if (this.state == 4) {
            this.state = 5;
            return new FixedLengthSource(j);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("state: ");
        stringBuilder.append(this.state);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public Source newChunkedSource(HttpEngine httpEngine) throws IOException {
        if (this.state == 4) {
            this.state = 5;
            return new ChunkedSource(httpEngine);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("state: ");
        stringBuilder.append(this.state);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public Source newUnknownLengthSource() throws IOException {
        if (this.state == 4) {
            StreamAllocation streamAllocation = this.streamAllocation;
            if (streamAllocation != null) {
                this.state = 5;
                streamAllocation.noNewStreams();
                return new UnknownLengthSource();
            }
            throw new IllegalStateException("streamAllocation == null");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("state: ");
        stringBuilder.append(this.state);
        throw new IllegalStateException(stringBuilder.toString());
    }

    private void detachTimeout(ForwardingTimeout forwardingTimeout) {
        Timeout delegate = forwardingTimeout.delegate();
        forwardingTimeout.setDelegate(Timeout.NONE);
        delegate.clearDeadline();
        delegate.clearTimeout();
    }
}
