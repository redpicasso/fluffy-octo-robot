package com.squareup.okhttp.internal.framed;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class FramedStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    long bytesLeftInWriteWindow;
    private final FramedConnection connection;
    private ErrorCode errorCode = null;
    private final int id;
    private final StreamTimeout readTimeout = new StreamTimeout();
    private final List<Header> requestHeaders;
    private List<Header> responseHeaders;
    final FramedDataSink sink;
    private final FramedDataSource source;
    long unacknowledgedBytesRead = 0;
    private final StreamTimeout writeTimeout = new StreamTimeout();

    final class FramedDataSink implements Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long EMIT_BUFFER_SIZE = 16384;
        private boolean closed;
        private boolean finished;
        private final Buffer sendBuffer = new Buffer();

        FramedDataSink() {
        }

        public void write(Buffer buffer, long j) throws IOException {
            this.sendBuffer.write(buffer, j);
            while (this.sendBuffer.size() >= 16384) {
                emitDataFrame(false);
            }
        }

        private void emitDataFrame(boolean z) throws IOException {
            long min;
            synchronized (FramedStream.this) {
                FramedStream.this.writeTimeout.enter();
                while (FramedStream.this.bytesLeftInWriteWindow <= 0 && !this.finished && !this.closed && FramedStream.this.errorCode == null) {
                    try {
                        FramedStream.this.waitForIo();
                    } finally {
                        FramedStream.this.writeTimeout.exitAndThrowIfTimedOut();
                    }
                }
                FramedStream.this.checkOutNotClosed();
                min = Math.min(FramedStream.this.bytesLeftInWriteWindow, this.sendBuffer.size());
                FramedStream framedStream = FramedStream.this;
                framedStream.bytesLeftInWriteWindow -= min;
            }
            FramedStream.this.writeTimeout.enter();
            try {
                FramedConnection access$500 = FramedStream.this.connection;
                int access$600 = FramedStream.this.id;
                boolean z2 = z && min == this.sendBuffer.size();
                access$500.writeData(access$600, z2, this.sendBuffer, min);
            } finally {
                FramedStream.this.writeTimeout.exitAndThrowIfTimedOut();
            }
        }

        public void flush() throws IOException {
            synchronized (FramedStream.this) {
                FramedStream.this.checkOutNotClosed();
            }
            while (this.sendBuffer.size() > 0) {
                emitDataFrame(false);
                FramedStream.this.connection.flush();
            }
        }

        public Timeout timeout() {
            return FramedStream.this.writeTimeout;
        }

        /* JADX WARNING: Missing block: B:9:0x0011, code:
            if (r8.this$0.sink.finished != false) goto L_0x0040;
     */
        /* JADX WARNING: Missing block: B:11:0x001d, code:
            if (r8.sendBuffer.size() <= 0) goto L_0x002d;
     */
        /* JADX WARNING: Missing block: B:13:0x0027, code:
            if (r8.sendBuffer.size() <= 0) goto L_0x0040;
     */
        /* JADX WARNING: Missing block: B:14:0x0029, code:
            emitDataFrame(true);
     */
        /* JADX WARNING: Missing block: B:15:0x002d, code:
            com.squareup.okhttp.internal.framed.FramedStream.access$500(r8.this$0).writeData(com.squareup.okhttp.internal.framed.FramedStream.access$600(r8.this$0), true, null, 0);
     */
        /* JADX WARNING: Missing block: B:16:0x0040, code:
            r2 = r8.this$0;
     */
        /* JADX WARNING: Missing block: B:17:0x0042, code:
            monitor-enter(r2);
     */
        /* JADX WARNING: Missing block: B:19:?, code:
            r8.closed = true;
     */
        /* JADX WARNING: Missing block: B:20:0x0045, code:
            monitor-exit(r2);
     */
        /* JADX WARNING: Missing block: B:21:0x0046, code:
            com.squareup.okhttp.internal.framed.FramedStream.access$500(r8.this$0).flush();
            com.squareup.okhttp.internal.framed.FramedStream.access$1000(r8.this$0);
     */
        /* JADX WARNING: Missing block: B:22:0x0054, code:
            return;
     */
        public void close() throws java.io.IOException {
            /*
            r8 = this;
            r0 = com.squareup.okhttp.internal.framed.FramedStream.this;
            monitor-enter(r0);
            r1 = r8.closed;	 Catch:{ all -> 0x0058 }
            if (r1 == 0) goto L_0x0009;
        L_0x0007:
            monitor-exit(r0);	 Catch:{ all -> 0x0058 }
            return;
        L_0x0009:
            monitor-exit(r0);	 Catch:{ all -> 0x0058 }
            r0 = com.squareup.okhttp.internal.framed.FramedStream.this;
            r0 = r0.sink;
            r0 = r0.finished;
            r1 = 1;
            if (r0 != 0) goto L_0x0040;
        L_0x0013:
            r0 = r8.sendBuffer;
            r2 = r0.size();
            r4 = 0;
            r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
            if (r0 <= 0) goto L_0x002d;
        L_0x001f:
            r0 = r8.sendBuffer;
            r2 = r0.size();
            r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
            if (r0 <= 0) goto L_0x0040;
        L_0x0029:
            r8.emitDataFrame(r1);
            goto L_0x001f;
        L_0x002d:
            r0 = com.squareup.okhttp.internal.framed.FramedStream.this;
            r2 = r0.connection;
            r0 = com.squareup.okhttp.internal.framed.FramedStream.this;
            r3 = r0.id;
            r4 = 1;
            r5 = 0;
            r6 = 0;
            r2.writeData(r3, r4, r5, r6);
        L_0x0040:
            r2 = com.squareup.okhttp.internal.framed.FramedStream.this;
            monitor-enter(r2);
            r8.closed = r1;	 Catch:{ all -> 0x0055 }
            monitor-exit(r2);	 Catch:{ all -> 0x0055 }
            r0 = com.squareup.okhttp.internal.framed.FramedStream.this;
            r0 = r0.connection;
            r0.flush();
            r0 = com.squareup.okhttp.internal.framed.FramedStream.this;
            r0.cancelStreamIfNecessary();
            return;
        L_0x0055:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0055 }
            throw r0;
        L_0x0058:
            r1 = move-exception;
            monitor-exit(r0);	 Catch:{ all -> 0x0058 }
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.FramedStream.FramedDataSink.close():void");
        }
    }

    private final class FramedDataSource implements Source {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean closed;
        private boolean finished;
        private final long maxByteCount;
        private final Buffer readBuffer;
        private final Buffer receiveBuffer;

        static {
            Class cls = FramedStream.class;
        }

        private FramedDataSource(long j) {
            this.receiveBuffer = new Buffer();
            this.readBuffer = new Buffer();
            this.maxByteCount = j;
        }

        /* JADX WARNING: Missing block: B:14:0x0065, code:
            r11 = com.squareup.okhttp.internal.framed.FramedStream.access$500(r8.this$0);
     */
        /* JADX WARNING: Missing block: B:15:0x006b, code:
            monitor-enter(r11);
     */
        /* JADX WARNING: Missing block: B:17:?, code:
            r2 = com.squareup.okhttp.internal.framed.FramedStream.access$500(r8.this$0);
            r2.unacknowledgedBytesRead += r9;
     */
        /* JADX WARNING: Missing block: B:18:0x0090, code:
            if (com.squareup.okhttp.internal.framed.FramedStream.access$500(r8.this$0).unacknowledgedBytesRead < ((long) (com.squareup.okhttp.internal.framed.FramedStream.access$500(r8.this$0).okHttpSettings.getInitialWindowSize(65536) / 2))) goto L_0x00ac;
     */
        /* JADX WARNING: Missing block: B:19:0x0092, code:
            com.squareup.okhttp.internal.framed.FramedStream.access$500(r8.this$0).writeWindowUpdateLater(0, com.squareup.okhttp.internal.framed.FramedStream.access$500(r8.this$0).unacknowledgedBytesRead);
            com.squareup.okhttp.internal.framed.FramedStream.access$500(r8.this$0).unacknowledgedBytesRead = 0;
     */
        /* JADX WARNING: Missing block: B:20:0x00ac, code:
            monitor-exit(r11);
     */
        /* JADX WARNING: Missing block: B:21:0x00ad, code:
            return r9;
     */
        public long read(okio.Buffer r9, long r10) throws java.io.IOException {
            /*
            r8 = this;
            r0 = 0;
            r2 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1));
            if (r2 < 0) goto L_0x00b4;
        L_0x0006:
            r2 = com.squareup.okhttp.internal.framed.FramedStream.this;
            monitor-enter(r2);
            r8.waitUntilReadable();	 Catch:{ all -> 0x00b1 }
            r8.checkNotClosed();	 Catch:{ all -> 0x00b1 }
            r3 = r8.readBuffer;	 Catch:{ all -> 0x00b1 }
            r3 = r3.size();	 Catch:{ all -> 0x00b1 }
            r5 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1));
            if (r5 != 0) goto L_0x001d;
        L_0x0019:
            r9 = -1;
            monitor-exit(r2);	 Catch:{ all -> 0x00b1 }
            return r9;
        L_0x001d:
            r3 = r8.readBuffer;	 Catch:{ all -> 0x00b1 }
            r4 = r8.readBuffer;	 Catch:{ all -> 0x00b1 }
            r4 = r4.size();	 Catch:{ all -> 0x00b1 }
            r10 = java.lang.Math.min(r10, r4);	 Catch:{ all -> 0x00b1 }
            r9 = r3.read(r9, r10);	 Catch:{ all -> 0x00b1 }
            r11 = com.squareup.okhttp.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00b1 }
            r3 = r11.unacknowledgedBytesRead;	 Catch:{ all -> 0x00b1 }
            r3 = r3 + r9;
            r11.unacknowledgedBytesRead = r3;	 Catch:{ all -> 0x00b1 }
            r11 = com.squareup.okhttp.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00b1 }
            r3 = r11.unacknowledgedBytesRead;	 Catch:{ all -> 0x00b1 }
            r11 = com.squareup.okhttp.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00b1 }
            r11 = r11.connection;	 Catch:{ all -> 0x00b1 }
            r11 = r11.okHttpSettings;	 Catch:{ all -> 0x00b1 }
            r5 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
            r11 = r11.getInitialWindowSize(r5);	 Catch:{ all -> 0x00b1 }
            r11 = r11 / 2;
            r6 = (long) r11;	 Catch:{ all -> 0x00b1 }
            r11 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1));
            if (r11 < 0) goto L_0x0064;
        L_0x004d:
            r11 = com.squareup.okhttp.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00b1 }
            r11 = r11.connection;	 Catch:{ all -> 0x00b1 }
            r3 = com.squareup.okhttp.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00b1 }
            r3 = r3.id;	 Catch:{ all -> 0x00b1 }
            r4 = com.squareup.okhttp.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00b1 }
            r6 = r4.unacknowledgedBytesRead;	 Catch:{ all -> 0x00b1 }
            r11.writeWindowUpdateLater(r3, r6);	 Catch:{ all -> 0x00b1 }
            r11 = com.squareup.okhttp.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00b1 }
            r11.unacknowledgedBytesRead = r0;	 Catch:{ all -> 0x00b1 }
        L_0x0064:
            monitor-exit(r2);	 Catch:{ all -> 0x00b1 }
            r11 = com.squareup.okhttp.internal.framed.FramedStream.this;
            r11 = r11.connection;
            monitor-enter(r11);
            r2 = com.squareup.okhttp.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00ae }
            r2 = r2.connection;	 Catch:{ all -> 0x00ae }
            r3 = r2.unacknowledgedBytesRead;	 Catch:{ all -> 0x00ae }
            r3 = r3 + r9;
            r2.unacknowledgedBytesRead = r3;	 Catch:{ all -> 0x00ae }
            r2 = com.squareup.okhttp.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00ae }
            r2 = r2.connection;	 Catch:{ all -> 0x00ae }
            r2 = r2.unacknowledgedBytesRead;	 Catch:{ all -> 0x00ae }
            r4 = com.squareup.okhttp.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00ae }
            r4 = r4.connection;	 Catch:{ all -> 0x00ae }
            r4 = r4.okHttpSettings;	 Catch:{ all -> 0x00ae }
            r4 = r4.getInitialWindowSize(r5);	 Catch:{ all -> 0x00ae }
            r4 = r4 / 2;
            r4 = (long) r4;	 Catch:{ all -> 0x00ae }
            r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
            if (r6 < 0) goto L_0x00ac;
        L_0x0092:
            r2 = com.squareup.okhttp.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00ae }
            r2 = r2.connection;	 Catch:{ all -> 0x00ae }
            r3 = 0;
            r4 = com.squareup.okhttp.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00ae }
            r4 = r4.connection;	 Catch:{ all -> 0x00ae }
            r4 = r4.unacknowledgedBytesRead;	 Catch:{ all -> 0x00ae }
            r2.writeWindowUpdateLater(r3, r4);	 Catch:{ all -> 0x00ae }
            r2 = com.squareup.okhttp.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00ae }
            r2 = r2.connection;	 Catch:{ all -> 0x00ae }
            r2.unacknowledgedBytesRead = r0;	 Catch:{ all -> 0x00ae }
        L_0x00ac:
            monitor-exit(r11);	 Catch:{ all -> 0x00ae }
            return r9;
        L_0x00ae:
            r9 = move-exception;
            monitor-exit(r11);	 Catch:{ all -> 0x00ae }
            throw r9;
        L_0x00b1:
            r9 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x00b1 }
            throw r9;
        L_0x00b4:
            r9 = new java.lang.IllegalArgumentException;
            r0 = new java.lang.StringBuilder;
            r0.<init>();
            r1 = "byteCount < 0: ";
            r0.append(r1);
            r0.append(r10);
            r10 = r0.toString();
            r9.<init>(r10);
            throw r9;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.FramedStream.FramedDataSource.read(okio.Buffer, long):long");
        }

        private void waitUntilReadable() throws IOException {
            FramedStream.this.readTimeout.enter();
            while (this.readBuffer.size() == 0 && !this.finished && !this.closed && FramedStream.this.errorCode == null) {
                try {
                    FramedStream.this.waitForIo();
                } catch (Throwable th) {
                    FramedStream.this.readTimeout.exitAndThrowIfTimedOut();
                }
            }
            FramedStream.this.readTimeout.exitAndThrowIfTimedOut();
        }

        void receive(BufferedSource bufferedSource, long j) throws IOException {
            while (j > 0) {
                boolean z;
                Object obj;
                Object obj2;
                synchronized (FramedStream.this) {
                    z = this.finished;
                    obj = 1;
                    obj2 = this.readBuffer.size() + j > this.maxByteCount ? 1 : null;
                }
                if (obj2 != null) {
                    bufferedSource.skip(j);
                    FramedStream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                    return;
                } else if (z) {
                    bufferedSource.skip(j);
                    return;
                } else {
                    long read = bufferedSource.read(this.receiveBuffer, j);
                    if (read != -1) {
                        j -= read;
                        synchronized (FramedStream.this) {
                            if (this.readBuffer.size() != 0) {
                                obj = null;
                            }
                            this.readBuffer.writeAll(this.receiveBuffer);
                            if (obj != null) {
                                FramedStream.this.notifyAll();
                            }
                        }
                    } else {
                        throw new EOFException();
                    }
                }
            }
        }

        public Timeout timeout() {
            return FramedStream.this.readTimeout;
        }

        public void close() throws IOException {
            synchronized (FramedStream.this) {
                this.closed = true;
                this.readBuffer.clear();
                FramedStream.this.notifyAll();
            }
            FramedStream.this.cancelStreamIfNecessary();
        }

        private void checkNotClosed() throws IOException {
            if (this.closed) {
                throw new IOException("stream closed");
            } else if (FramedStream.this.errorCode != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("stream was reset: ");
                stringBuilder.append(FramedStream.this.errorCode);
                throw new IOException(stringBuilder.toString());
            }
        }
    }

    class StreamTimeout extends AsyncTimeout {
        StreamTimeout() {
        }

        protected void timedOut() {
            FramedStream.this.closeLater(ErrorCode.CANCEL);
        }

        protected IOException newTimeoutException(IOException iOException) {
            IOException socketTimeoutException = new SocketTimeoutException("timeout");
            if (iOException != null) {
                socketTimeoutException.initCause(iOException);
            }
            return socketTimeoutException;
        }

        public void exitAndThrowIfTimedOut() throws IOException {
            if (exit()) {
                throw newTimeoutException(null);
            }
        }
    }

    FramedStream(int i, FramedConnection framedConnection, boolean z, boolean z2, List<Header> list) {
        if (framedConnection == null) {
            throw new NullPointerException("connection == null");
        } else if (list != null) {
            this.id = i;
            this.connection = framedConnection;
            this.bytesLeftInWriteWindow = (long) framedConnection.peerSettings.getInitialWindowSize(65536);
            this.source = new FramedDataSource((long) framedConnection.okHttpSettings.getInitialWindowSize(65536));
            this.sink = new FramedDataSink();
            this.source.finished = z2;
            this.sink.finished = z;
            this.requestHeaders = list;
        } else {
            throw new NullPointerException("requestHeaders == null");
        }
    }

    public int getId() {
        return this.id;
    }

    public synchronized boolean isOpen() {
        if (this.errorCode != null) {
            return false;
        }
        if ((this.source.finished || this.source.closed) && ((this.sink.finished || this.sink.closed) && this.responseHeaders != null)) {
            return false;
        }
        return true;
    }

    public boolean isLocallyInitiated() {
        if (this.connection.client == ((this.id & 1) == 1)) {
            return true;
        }
        return false;
    }

    public FramedConnection getConnection() {
        return this.connection;
    }

    public List<Header> getRequestHeaders() {
        return this.requestHeaders;
    }

    public synchronized List<Header> getResponseHeaders() throws IOException {
        this.readTimeout.enter();
        while (this.responseHeaders == null && this.errorCode == null) {
            try {
                waitForIo();
            } finally {
                this.readTimeout.exitAndThrowIfTimedOut();
            }
        }
        if (this.responseHeaders != null) {
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("stream was reset: ");
            stringBuilder.append(this.errorCode);
            throw new IOException(stringBuilder.toString());
        }
        return this.responseHeaders;
    }

    public synchronized ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public void reply(List<Header> list, boolean z) throws IOException {
        boolean z2 = false;
        synchronized (this) {
            if (list == null) {
                throw new NullPointerException("responseHeaders == null");
            } else if (this.responseHeaders == null) {
                this.responseHeaders = list;
                if (!z) {
                    this.sink.finished = true;
                    z2 = true;
                }
            } else {
                throw new IllegalStateException("reply already sent");
            }
        }
        this.connection.writeSynReply(this.id, z2, list);
        if (z2) {
            this.connection.flush();
        }
    }

    public Timeout readTimeout() {
        return this.readTimeout;
    }

    public Timeout writeTimeout() {
        return this.writeTimeout;
    }

    public Source getSource() {
        return this.source;
    }

    public Sink getSink() {
        synchronized (this) {
            if (this.responseHeaders != null || isLocallyInitiated()) {
            } else {
                throw new IllegalStateException("reply before requesting the sink");
            }
        }
        return this.sink;
    }

    public void close(ErrorCode errorCode) throws IOException {
        if (closeInternal(errorCode)) {
            this.connection.writeSynReset(this.id, errorCode);
        }
    }

    public void closeLater(ErrorCode errorCode) {
        if (closeInternal(errorCode)) {
            this.connection.writeSynResetLater(this.id, errorCode);
        }
    }

    private boolean closeInternal(ErrorCode errorCode) {
        synchronized (this) {
            if (this.errorCode != null) {
                return false;
            } else if (this.source.finished && this.sink.finished) {
                return false;
            } else {
                this.errorCode = errorCode;
                notifyAll();
                this.connection.removeStream(this.id);
                return true;
            }
        }
    }

    void receiveHeaders(List<Header> list, HeadersMode headersMode) {
        ErrorCode errorCode = null;
        boolean z = true;
        synchronized (this) {
            if (this.responseHeaders == null) {
                if (headersMode.failIfHeadersAbsent()) {
                    errorCode = ErrorCode.PROTOCOL_ERROR;
                } else {
                    this.responseHeaders = list;
                    z = isOpen();
                    notifyAll();
                }
            } else if (headersMode.failIfHeadersPresent()) {
                errorCode = ErrorCode.STREAM_IN_USE;
            } else {
                List arrayList = new ArrayList();
                arrayList.addAll(this.responseHeaders);
                arrayList.addAll(list);
                this.responseHeaders = arrayList;
            }
        }
        if (errorCode != null) {
            closeLater(errorCode);
        } else if (!z) {
            this.connection.removeStream(this.id);
        }
    }

    void receiveData(BufferedSource bufferedSource, int i) throws IOException {
        this.source.receive(bufferedSource, (long) i);
    }

    void receiveFin() {
        boolean isOpen;
        synchronized (this) {
            this.source.finished = true;
            isOpen = isOpen();
            notifyAll();
        }
        if (!isOpen) {
            this.connection.removeStream(this.id);
        }
    }

    synchronized void receiveRstStream(ErrorCode errorCode) {
        if (this.errorCode == null) {
            this.errorCode = errorCode;
            notifyAll();
        }
    }

    private void cancelStreamIfNecessary() throws IOException {
        Object obj;
        boolean isOpen;
        synchronized (this) {
            obj = (!this.source.finished && this.source.closed && (this.sink.finished || this.sink.closed)) ? 1 : null;
            isOpen = isOpen();
        }
        if (obj != null) {
            close(ErrorCode.CANCEL);
        } else if (!isOpen) {
            this.connection.removeStream(this.id);
        }
    }

    void addBytesToWriteWindow(long j) {
        this.bytesLeftInWriteWindow += j;
        if (j > 0) {
            notifyAll();
        }
    }

    private void checkOutNotClosed() throws IOException {
        if (this.sink.closed) {
            throw new IOException("stream closed");
        } else if (this.sink.finished) {
            throw new IOException("stream finished");
        } else if (this.errorCode != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("stream was reset: ");
            stringBuilder.append(this.errorCode);
            throw new IOException(stringBuilder.toString());
        }
    }

    private void waitForIo() throws InterruptedIOException {
        try {
            wait();
        } catch (InterruptedException unused) {
            throw new InterruptedIOException();
        }
    }
}
