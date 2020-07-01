package okio;

import java.io.IOException;
import javax.annotation.Nullable;

public final class Pipe {
    final Buffer buffer = new Buffer();
    @Nullable
    private Sink foldedSink;
    final long maxBufferSize;
    private final Sink sink = new PipeSink();
    boolean sinkClosed;
    private final Source source = new PipeSource();
    boolean sourceClosed;

    final class PipeSink implements Sink {
        final PushableTimeout timeout = new PushableTimeout();

        PipeSink() {
        }

        public void write(Buffer buffer, long j) throws IOException {
            Sink access$000;
            synchronized (Pipe.this.buffer) {
                if (Pipe.this.sinkClosed) {
                    throw new IllegalStateException("closed");
                }
                while (j > 0) {
                    if (Pipe.this.foldedSink != null) {
                        access$000 = Pipe.this.foldedSink;
                        break;
                    } else if (Pipe.this.sourceClosed) {
                        throw new IOException("source is closed");
                    } else {
                        long size = Pipe.this.maxBufferSize - Pipe.this.buffer.size();
                        if (size == 0) {
                            this.timeout.waitUntilNotified(Pipe.this.buffer);
                        } else {
                            long min = Math.min(size, j);
                            Pipe.this.buffer.write(buffer, min);
                            j -= min;
                            Pipe.this.buffer.notifyAll();
                        }
                    }
                }
                access$000 = null;
            }
            if (access$000 != null) {
                this.timeout.push(access$000.timeout());
                try {
                    access$000.write(buffer, j);
                } finally {
                    this.timeout.pop();
                }
            }
        }

        public void flush() throws IOException {
            Sink access$000;
            synchronized (Pipe.this.buffer) {
                if (Pipe.this.sinkClosed) {
                    throw new IllegalStateException("closed");
                }
                if (Pipe.this.foldedSink != null) {
                    access$000 = Pipe.this.foldedSink;
                } else if (!Pipe.this.sourceClosed || Pipe.this.buffer.size() <= 0) {
                    access$000 = null;
                } else {
                    throw new IOException("source is closed");
                }
            }
            if (access$000 != null) {
                this.timeout.push(access$000.timeout());
                try {
                    access$000.flush();
                } finally {
                    this.timeout.pop();
                }
            }
        }

        /* JADX WARNING: Missing block: B:18:0x0047, code:
            if (r1 == null) goto L_0x0062;
     */
        /* JADX WARNING: Missing block: B:19:0x0049, code:
            r6.timeout.push(r1.timeout());
     */
        /* JADX WARNING: Missing block: B:21:?, code:
            r1.close();
     */
        /* JADX WARNING: Missing block: B:23:0x005c, code:
            r6.timeout.pop();
     */
        public void close() throws java.io.IOException {
            /*
            r6 = this;
            r0 = okio.Pipe.this;
            r0 = r0.buffer;
            monitor-enter(r0);
            r1 = okio.Pipe.this;	 Catch:{ all -> 0x0063 }
            r1 = r1.sinkClosed;	 Catch:{ all -> 0x0063 }
            if (r1 == 0) goto L_0x000d;
        L_0x000b:
            monitor-exit(r0);	 Catch:{ all -> 0x0063 }
            return;
        L_0x000d:
            r1 = okio.Pipe.this;	 Catch:{ all -> 0x0063 }
            r1 = r1.foldedSink;	 Catch:{ all -> 0x0063 }
            if (r1 == 0) goto L_0x001c;
        L_0x0015:
            r1 = okio.Pipe.this;	 Catch:{ all -> 0x0063 }
            r1 = r1.foldedSink;	 Catch:{ all -> 0x0063 }
            goto L_0x0046;
        L_0x001c:
            r1 = okio.Pipe.this;	 Catch:{ all -> 0x0063 }
            r1 = r1.sourceClosed;	 Catch:{ all -> 0x0063 }
            if (r1 == 0) goto L_0x0039;
        L_0x0022:
            r1 = okio.Pipe.this;	 Catch:{ all -> 0x0063 }
            r1 = r1.buffer;	 Catch:{ all -> 0x0063 }
            r1 = r1.size();	 Catch:{ all -> 0x0063 }
            r3 = 0;
            r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1));
            if (r5 > 0) goto L_0x0031;
        L_0x0030:
            goto L_0x0039;
        L_0x0031:
            r1 = new java.io.IOException;	 Catch:{ all -> 0x0063 }
            r2 = "source is closed";
            r1.<init>(r2);	 Catch:{ all -> 0x0063 }
            throw r1;	 Catch:{ all -> 0x0063 }
        L_0x0039:
            r1 = okio.Pipe.this;	 Catch:{ all -> 0x0063 }
            r2 = 1;
            r1.sinkClosed = r2;	 Catch:{ all -> 0x0063 }
            r1 = okio.Pipe.this;	 Catch:{ all -> 0x0063 }
            r1 = r1.buffer;	 Catch:{ all -> 0x0063 }
            r1.notifyAll();	 Catch:{ all -> 0x0063 }
            r1 = 0;
        L_0x0046:
            monitor-exit(r0);	 Catch:{ all -> 0x0063 }
            if (r1 == 0) goto L_0x0062;
        L_0x0049:
            r0 = r6.timeout;
            r2 = r1.timeout();
            r0.push(r2);
            r1.close();	 Catch:{ all -> 0x005b }
            r0 = r6.timeout;
            r0.pop();
            goto L_0x0062;
        L_0x005b:
            r0 = move-exception;
            r1 = r6.timeout;
            r1.pop();
            throw r0;
        L_0x0062:
            return;
        L_0x0063:
            r1 = move-exception;
            monitor-exit(r0);	 Catch:{ all -> 0x0063 }
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.Pipe.PipeSink.close():void");
        }

        public Timeout timeout() {
            return this.timeout;
        }
    }

    final class PipeSource implements Source {
        final Timeout timeout = new Timeout();

        PipeSource() {
        }

        public long read(Buffer buffer, long j) throws IOException {
            synchronized (Pipe.this.buffer) {
                if (Pipe.this.sourceClosed) {
                    throw new IllegalStateException("closed");
                }
                while (Pipe.this.buffer.size() == 0) {
                    if (Pipe.this.sinkClosed) {
                        return -1;
                    }
                    this.timeout.waitUntilNotified(Pipe.this.buffer);
                }
                long read = Pipe.this.buffer.read(buffer, j);
                Pipe.this.buffer.notifyAll();
                return read;
            }
        }

        public void close() throws IOException {
            synchronized (Pipe.this.buffer) {
                Pipe.this.sourceClosed = true;
                Pipe.this.buffer.notifyAll();
            }
        }

        public Timeout timeout() {
            return this.timeout;
        }
    }

    public Pipe(long j) {
        if (j >= 1) {
            this.maxBufferSize = j;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("maxBufferSize < 1: ");
        stringBuilder.append(j);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final Source source() {
        return this.source;
    }

    public final Sink sink() {
        return this.sink;
    }

    /* JADX WARNING: Missing block: B:13:?, code:
            r8.write(r3, r3.size);
     */
    /* JADX WARNING: Missing block: B:14:0x0031, code:
            if (r1 == false) goto L_0x0037;
     */
    /* JADX WARNING: Missing block: B:15:0x0033, code:
            r8.close();
     */
    /* JADX WARNING: Missing block: B:16:0x0037, code:
            r8.flush();
     */
    /* JADX WARNING: Missing block: B:20:0x003e, code:
            monitor-enter(r7.buffer);
     */
    /* JADX WARNING: Missing block: B:22:?, code:
            r7.sourceClosed = true;
            r7.buffer.notifyAll();
     */
    public void fold(okio.Sink r8) throws java.io.IOException {
        /*
        r7 = this;
    L_0x0000:
        r0 = r7.buffer;
        monitor-enter(r0);
        r1 = r7.foldedSink;	 Catch:{ all -> 0x0053 }
        if (r1 != 0) goto L_0x004b;
    L_0x0007:
        r1 = r7.buffer;	 Catch:{ all -> 0x0053 }
        r1 = r1.exhausted();	 Catch:{ all -> 0x0053 }
        r2 = 1;
        if (r1 == 0) goto L_0x0016;
    L_0x0010:
        r7.sourceClosed = r2;	 Catch:{ all -> 0x0053 }
        r7.foldedSink = r8;	 Catch:{ all -> 0x0053 }
        monitor-exit(r0);	 Catch:{ all -> 0x0053 }
        return;
    L_0x0016:
        r1 = r7.sinkClosed;	 Catch:{ all -> 0x0053 }
        r3 = new okio.Buffer;	 Catch:{ all -> 0x0053 }
        r3.<init>();	 Catch:{ all -> 0x0053 }
        r4 = r7.buffer;	 Catch:{ all -> 0x0053 }
        r5 = r7.buffer;	 Catch:{ all -> 0x0053 }
        r5 = r5.size;	 Catch:{ all -> 0x0053 }
        r3.write(r4, r5);	 Catch:{ all -> 0x0053 }
        r4 = r7.buffer;	 Catch:{ all -> 0x0053 }
        r4.notifyAll();	 Catch:{ all -> 0x0053 }
        monitor-exit(r0);	 Catch:{ all -> 0x0053 }
        r4 = r3.size;	 Catch:{ all -> 0x003b }
        r8.write(r3, r4);	 Catch:{ all -> 0x003b }
        if (r1 == 0) goto L_0x0037;
    L_0x0033:
        r8.close();	 Catch:{ all -> 0x003b }
        goto L_0x0000;
    L_0x0037:
        r8.flush();	 Catch:{ all -> 0x003b }
        goto L_0x0000;
    L_0x003b:
        r8 = move-exception;
        r1 = r7.buffer;
        monitor-enter(r1);
        r7.sourceClosed = r2;	 Catch:{ all -> 0x0048 }
        r0 = r7.buffer;	 Catch:{ all -> 0x0048 }
        r0.notifyAll();	 Catch:{ all -> 0x0048 }
        monitor-exit(r1);	 Catch:{ all -> 0x0048 }
        throw r8;
    L_0x0048:
        r8 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0048 }
        throw r8;
    L_0x004b:
        r8 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0053 }
        r1 = "sink already folded";
        r8.<init>(r1);	 Catch:{ all -> 0x0053 }
        throw r8;	 Catch:{ all -> 0x0053 }
    L_0x0053:
        r8 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0053 }
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Pipe.fold(okio.Sink):void");
    }
}
