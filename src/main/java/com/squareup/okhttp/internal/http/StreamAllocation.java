package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.io.RealConnection;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import okio.Sink;

public final class StreamAllocation {
    public final Address address;
    private boolean canceled;
    private RealConnection connection;
    private final ConnectionPool connectionPool;
    private boolean released;
    private RouteSelector routeSelector;
    private HttpStream stream;

    public StreamAllocation(ConnectionPool connectionPool, Address address) {
        this.connectionPool = connectionPool;
        this.address = address;
    }

    public HttpStream newStream(int i, int i2, int i3, boolean z, boolean z2) throws RouteException, IOException {
        try {
            HttpStream http2xStream;
            RealConnection findHealthyConnection = findHealthyConnection(i, i2, i3, z, z2);
            if (findHealthyConnection.framedConnection != null) {
                http2xStream = new Http2xStream(this, findHealthyConnection.framedConnection);
            } else {
                findHealthyConnection.getSocket().setSoTimeout(i2);
                findHealthyConnection.source.timeout().timeout((long) i2, TimeUnit.MILLISECONDS);
                findHealthyConnection.sink.timeout().timeout((long) i3, TimeUnit.MILLISECONDS);
                http2xStream = new Http1xStream(this, findHealthyConnection.source, findHealthyConnection.sink);
            }
            synchronized (this.connectionPool) {
                findHealthyConnection.streamCount++;
                this.stream = http2xStream;
            }
            return http2xStream;
        } catch (IOException e) {
            throw new RouteException(e);
        }
    }

    /* JADX WARNING: Missing block: B:9:0x0012, code:
            if (r0.isHealthy(r8) == false) goto L_0x0015;
     */
    /* JADX WARNING: Missing block: B:10:0x0014, code:
            return r0;
     */
    private com.squareup.okhttp.internal.io.RealConnection findHealthyConnection(int r4, int r5, int r6, boolean r7, boolean r8) throws java.io.IOException, com.squareup.okhttp.internal.http.RouteException {
        /*
        r3 = this;
    L_0x0000:
        r0 = r3.findConnection(r4, r5, r6, r7);
        r1 = r3.connectionPool;
        monitor-enter(r1);
        r2 = r0.streamCount;	 Catch:{ all -> 0x0019 }
        if (r2 != 0) goto L_0x000d;
    L_0x000b:
        monitor-exit(r1);	 Catch:{ all -> 0x0019 }
        return r0;
    L_0x000d:
        monitor-exit(r1);	 Catch:{ all -> 0x0019 }
        r1 = r0.isHealthy(r8);
        if (r1 == 0) goto L_0x0015;
    L_0x0014:
        return r0;
    L_0x0015:
        r3.connectionFailed();
        goto L_0x0000;
    L_0x0019:
        r4 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0019 }
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.StreamAllocation.findHealthyConnection(int, int, int, boolean, boolean):com.squareup.okhttp.internal.io.RealConnection");
    }

    /* JADX WARNING: Missing block: B:24:0x003b, code:
            r7 = new com.squareup.okhttp.internal.io.RealConnection(r8.routeSelector.next());
            acquire(r7);
            r1 = r8.connectionPool;
     */
    /* JADX WARNING: Missing block: B:25:0x004b, code:
            monitor-enter(r1);
     */
    /* JADX WARNING: Missing block: B:27:?, code:
            com.squareup.okhttp.internal.Internal.instance.put(r8.connectionPool, r7);
            r8.connection = r7;
     */
    /* JADX WARNING: Missing block: B:28:0x0057, code:
            if (r8.canceled != false) goto L_0x0074;
     */
    /* JADX WARNING: Missing block: B:29:0x0059, code:
            monitor-exit(r1);
     */
    /* JADX WARNING: Missing block: B:30:0x005a, code:
            r7.connect(r9, r10, r11, r8.address.getConnectionSpecs(), r12);
            routeDatabase().connected(r7.getRoute());
     */
    /* JADX WARNING: Missing block: B:31:0x0073, code:
            return r7;
     */
    /* JADX WARNING: Missing block: B:34:0x007b, code:
            throw new java.io.IOException("Canceled");
     */
    private com.squareup.okhttp.internal.io.RealConnection findConnection(int r9, int r10, int r11, boolean r12) throws java.io.IOException, com.squareup.okhttp.internal.http.RouteException {
        /*
        r8 = this;
        r0 = r8.connectionPool;
        monitor-enter(r0);
        r1 = r8.released;	 Catch:{ all -> 0x0097 }
        if (r1 != 0) goto L_0x008f;
    L_0x0007:
        r1 = r8.stream;	 Catch:{ all -> 0x0097 }
        if (r1 != 0) goto L_0x0087;
    L_0x000b:
        r1 = r8.canceled;	 Catch:{ all -> 0x0097 }
        if (r1 != 0) goto L_0x007f;
    L_0x000f:
        r1 = r8.connection;	 Catch:{ all -> 0x0097 }
        if (r1 == 0) goto L_0x0019;
    L_0x0013:
        r2 = r1.noNewStreams;	 Catch:{ all -> 0x0097 }
        if (r2 != 0) goto L_0x0019;
    L_0x0017:
        monitor-exit(r0);	 Catch:{ all -> 0x0097 }
        return r1;
    L_0x0019:
        r1 = com.squareup.okhttp.internal.Internal.instance;	 Catch:{ all -> 0x0097 }
        r2 = r8.connectionPool;	 Catch:{ all -> 0x0097 }
        r3 = r8.address;	 Catch:{ all -> 0x0097 }
        r1 = r1.get(r2, r3, r8);	 Catch:{ all -> 0x0097 }
        if (r1 == 0) goto L_0x0029;
    L_0x0025:
        r8.connection = r1;	 Catch:{ all -> 0x0097 }
        monitor-exit(r0);	 Catch:{ all -> 0x0097 }
        return r1;
    L_0x0029:
        r1 = r8.routeSelector;	 Catch:{ all -> 0x0097 }
        if (r1 != 0) goto L_0x003a;
    L_0x002d:
        r1 = new com.squareup.okhttp.internal.http.RouteSelector;	 Catch:{ all -> 0x0097 }
        r2 = r8.address;	 Catch:{ all -> 0x0097 }
        r3 = r8.routeDatabase();	 Catch:{ all -> 0x0097 }
        r1.<init>(r2, r3);	 Catch:{ all -> 0x0097 }
        r8.routeSelector = r1;	 Catch:{ all -> 0x0097 }
    L_0x003a:
        monitor-exit(r0);	 Catch:{ all -> 0x0097 }
        r0 = r8.routeSelector;
        r0 = r0.next();
        r7 = new com.squareup.okhttp.internal.io.RealConnection;
        r7.<init>(r0);
        r8.acquire(r7);
        r1 = r8.connectionPool;
        monitor-enter(r1);
        r0 = com.squareup.okhttp.internal.Internal.instance;	 Catch:{ all -> 0x007c }
        r2 = r8.connectionPool;	 Catch:{ all -> 0x007c }
        r0.put(r2, r7);	 Catch:{ all -> 0x007c }
        r8.connection = r7;	 Catch:{ all -> 0x007c }
        r0 = r8.canceled;	 Catch:{ all -> 0x007c }
        if (r0 != 0) goto L_0x0074;
    L_0x0059:
        monitor-exit(r1);	 Catch:{ all -> 0x007c }
        r0 = r8.address;
        r5 = r0.getConnectionSpecs();
        r1 = r7;
        r2 = r9;
        r3 = r10;
        r4 = r11;
        r6 = r12;
        r1.connect(r2, r3, r4, r5, r6);
        r9 = r8.routeDatabase();
        r10 = r7.getRoute();
        r9.connected(r10);
        return r7;
    L_0x0074:
        r9 = new java.io.IOException;	 Catch:{ all -> 0x007c }
        r10 = "Canceled";
        r9.<init>(r10);	 Catch:{ all -> 0x007c }
        throw r9;	 Catch:{ all -> 0x007c }
    L_0x007c:
        r9 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x007c }
        throw r9;
    L_0x007f:
        r9 = new java.io.IOException;	 Catch:{ all -> 0x0097 }
        r10 = "Canceled";
        r9.<init>(r10);	 Catch:{ all -> 0x0097 }
        throw r9;	 Catch:{ all -> 0x0097 }
    L_0x0087:
        r9 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0097 }
        r10 = "stream != null";
        r9.<init>(r10);	 Catch:{ all -> 0x0097 }
        throw r9;	 Catch:{ all -> 0x0097 }
    L_0x008f:
        r9 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0097 }
        r10 = "released";
        r9.<init>(r10);	 Catch:{ all -> 0x0097 }
        throw r9;	 Catch:{ all -> 0x0097 }
    L_0x0097:
        r9 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0097 }
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.StreamAllocation.findConnection(int, int, int, boolean):com.squareup.okhttp.internal.io.RealConnection");
    }

    public void streamFinished(HttpStream httpStream) {
        synchronized (this.connectionPool) {
            if (httpStream != null) {
                if (httpStream == this.stream) {
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("expected ");
            stringBuilder.append(this.stream);
            stringBuilder.append(" but was ");
            stringBuilder.append(httpStream);
            throw new IllegalStateException(stringBuilder.toString());
        }
        deallocate(false, false, true);
    }

    public HttpStream stream() {
        HttpStream httpStream;
        synchronized (this.connectionPool) {
            httpStream = this.stream;
        }
        return httpStream;
    }

    private RouteDatabase routeDatabase() {
        return Internal.instance.routeDatabase(this.connectionPool);
    }

    public synchronized RealConnection connection() {
        return this.connection;
    }

    public void release() {
        deallocate(false, true, false);
    }

    public void noNewStreams() {
        deallocate(true, false, false);
    }

    private void deallocate(boolean z, boolean z2, boolean z3) {
        RealConnection realConnection;
        synchronized (this.connectionPool) {
            if (z3) {
                this.stream = null;
            }
            if (z2) {
                this.released = true;
            }
            if (this.connection != null) {
                if (z) {
                    this.connection.noNewStreams = true;
                }
                if (this.stream == null && (this.released || this.connection.noNewStreams)) {
                    release(this.connection);
                    if (this.connection.streamCount > 0) {
                        this.routeSelector = null;
                    }
                    if (this.connection.allocations.isEmpty()) {
                        this.connection.idleAtNanos = System.nanoTime();
                        if (Internal.instance.connectionBecameIdle(this.connectionPool, this.connection)) {
                            realConnection = this.connection;
                            this.connection = null;
                        }
                    }
                    realConnection = null;
                    this.connection = null;
                }
            }
            realConnection = null;
        }
        if (realConnection != null) {
            Util.closeQuietly(realConnection.getSocket());
        }
    }

    public void cancel() {
        HttpStream httpStream;
        RealConnection realConnection;
        synchronized (this.connectionPool) {
            this.canceled = true;
            httpStream = this.stream;
            realConnection = this.connection;
        }
        if (httpStream != null) {
            httpStream.cancel();
        } else if (realConnection != null) {
            realConnection.cancel();
        }
    }

    private void connectionFailed(IOException iOException) {
        synchronized (this.connectionPool) {
            if (this.routeSelector != null) {
                if (this.connection.streamCount == 0) {
                    this.routeSelector.connectFailed(this.connection.getRoute(), iOException);
                } else {
                    this.routeSelector = null;
                }
            }
        }
        connectionFailed();
    }

    public void connectionFailed() {
        deallocate(true, false, true);
    }

    public void acquire(RealConnection realConnection) {
        realConnection.allocations.add(new WeakReference(this));
    }

    private void release(RealConnection realConnection) {
        int size = realConnection.allocations.size();
        for (int i = 0; i < size; i++) {
            if (((Reference) realConnection.allocations.get(i)).get() == this) {
                realConnection.allocations.remove(i);
                return;
            }
        }
        throw new IllegalStateException();
    }

    public boolean recover(RouteException routeException) {
        if (this.connection != null) {
            connectionFailed(routeException.getLastConnectException());
        }
        RouteSelector routeSelector = this.routeSelector;
        return (routeSelector == null || routeSelector.hasNext()) && isRecoverable(routeException);
    }

    public boolean recover(IOException iOException, Sink sink) {
        RealConnection realConnection = this.connection;
        if (realConnection != null) {
            int i = realConnection.streamCount;
            connectionFailed(iOException);
            if (i == 1) {
                return false;
            }
        }
        Object obj = (sink == null || (sink instanceof RetryableSink)) ? 1 : null;
        RouteSelector routeSelector = this.routeSelector;
        return (routeSelector == null || routeSelector.hasNext()) && isRecoverable(iOException) && obj != null;
    }

    private boolean isRecoverable(IOException iOException) {
        if ((iOException instanceof ProtocolException) || (iOException instanceof InterruptedIOException)) {
            return false;
        }
        return true;
    }

    private boolean isRecoverable(RouteException routeException) {
        IOException lastConnectException = routeException.getLastConnectException();
        if (lastConnectException instanceof ProtocolException) {
            return false;
        }
        if (lastConnectException instanceof InterruptedIOException) {
            return lastConnectException instanceof SocketTimeoutException;
        }
        if (((lastConnectException instanceof SSLHandshakeException) && (lastConnectException.getCause() instanceof CertificateException)) || (lastConnectException instanceof SSLPeerUnverifiedException)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return this.address.toString();
    }
}
