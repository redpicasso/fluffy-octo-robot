package com.google.firebase.database.tubesock;

import android.net.SSLCertificateSocketFactory;
import android.net.SSLSessionCache;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Key;
import com.google.firebase.database.connection.ConnectionContext;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.logging.Logger;
import io.grpc.internal.GrpcUtil;
import java.io.File;
import java.net.Socket;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class WebSocket {
    static final byte OPCODE_BINARY = (byte) 2;
    static final byte OPCODE_CLOSE = (byte) 8;
    static final byte OPCODE_NONE = (byte) 0;
    static final byte OPCODE_PING = (byte) 9;
    static final byte OPCODE_PONG = (byte) 10;
    static final byte OPCODE_TEXT = (byte) 1;
    private static final int SSL_HANDSHAKE_TIMEOUT_MS = 60000;
    private static final String THREAD_BASE_NAME = "TubeSock";
    private static final Charset UTF8 = Charset.forName(Key.STRING_CHARSET_NAME);
    private static final AtomicInteger clientCount = new AtomicInteger(0);
    private static ThreadInitializer intializer = new ThreadInitializer() {
        public void setName(Thread thread, String str) {
            thread.setName(str);
        }
    };
    private static ThreadFactory threadFactory = Executors.defaultThreadFactory();
    private final int clientId;
    private WebSocketEventHandler eventHandler;
    private final WebSocketHandshake handshake;
    private final Thread innerThread;
    private final LogWrapper logger;
    private final WebSocketReceiver receiver;
    private volatile Socket socket;
    @Nullable
    private final String sslCacheDirectory;
    private volatile State state;
    private final URI url;
    private final WebSocketWriter writer;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    /* renamed from: com.google.firebase.database.tubesock.WebSocket$3 */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$database$tubesock$WebSocket$State = new int[State.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:11:0x0040, code:
            return;
     */
        static {
            /*
            r0 = com.google.firebase.database.tubesock.WebSocket.State.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firebase$database$tubesock$WebSocket$State = r0;
            r0 = $SwitchMap$com$google$firebase$database$tubesock$WebSocket$State;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.firebase.database.tubesock.WebSocket.State.NONE;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$firebase$database$tubesock$WebSocket$State;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.firebase.database.tubesock.WebSocket.State.CONNECTING;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$google$firebase$database$tubesock$WebSocket$State;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.google.firebase.database.tubesock.WebSocket.State.CONNECTED;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r0 = $SwitchMap$com$google$firebase$database$tubesock$WebSocket$State;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = com.google.firebase.database.tubesock.WebSocket.State.DISCONNECTING;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            r0 = $SwitchMap$com$google$firebase$database$tubesock$WebSocket$State;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r1 = com.google.firebase.database.tubesock.WebSocket.State.DISCONNECTED;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0040 }
            r2 = 5;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0040 }
        L_0x0040:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.tubesock.WebSocket.3.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private enum State {
        NONE,
        CONNECTING,
        CONNECTED,
        DISCONNECTING,
        DISCONNECTED
    }

    static ThreadFactory getThreadFactory() {
        return threadFactory;
    }

    static ThreadInitializer getIntializer() {
        return intializer;
    }

    public static void setThreadFactory(ThreadFactory threadFactory, ThreadInitializer threadInitializer) {
        threadFactory = threadFactory;
        intializer = threadInitializer;
    }

    public WebSocket(ConnectionContext connectionContext, URI uri) {
        this(connectionContext, uri, null);
    }

    public WebSocket(ConnectionContext connectionContext, URI uri, String str) {
        this(connectionContext, uri, str, null);
    }

    public WebSocket(ConnectionContext connectionContext, URI uri, String str, Map<String, String> map) {
        this.state = State.NONE;
        this.socket = null;
        this.eventHandler = null;
        this.clientId = clientCount.incrementAndGet();
        this.innerThread = getThreadFactory().newThread(new Runnable() {
            public void run() {
                WebSocket.this.runReader();
            }
        });
        this.url = uri;
        this.sslCacheDirectory = connectionContext.getSslCacheDirectory();
        Logger logger = connectionContext.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("sk_");
        stringBuilder.append(this.clientId);
        this.logger = new LogWrapper(logger, "WebSocket", stringBuilder.toString());
        this.handshake = new WebSocketHandshake(uri, str, map);
        this.receiver = new WebSocketReceiver(this);
        this.writer = new WebSocketWriter(this, THREAD_BASE_NAME, this.clientId);
    }

    public void setEventHandler(WebSocketEventHandler webSocketEventHandler) {
        this.eventHandler = webSocketEventHandler;
    }

    WebSocketEventHandler getEventHandler() {
        return this.eventHandler;
    }

    public synchronized void connect() {
        if (this.state != State.NONE) {
            this.eventHandler.onError(new WebSocketException("connect() already called"));
            close();
            return;
        }
        ThreadInitializer intializer = getIntializer();
        Thread innerThread = getInnerThread();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TubeSockReader-");
        stringBuilder.append(this.clientId);
        intializer.setName(innerThread, stringBuilder.toString());
        this.state = State.CONNECTING;
        getInnerThread().start();
    }

    public synchronized void send(String str) {
        send((byte) 1, str.getBytes(UTF8));
    }

    public synchronized void send(byte[] bArr) {
        send((byte) 2, bArr);
    }

    synchronized void pong(byte[] bArr) {
        send((byte) 10, bArr);
    }

    private synchronized void send(byte b, byte[] bArr) {
        if (this.state != State.CONNECTED) {
            this.eventHandler.onError(new WebSocketException("error while sending data: not connected"));
        } else {
            try {
                this.writer.send(b, true, bArr);
            } catch (Throwable e) {
                this.eventHandler.onError(new WebSocketException("Failed to send frame", e));
                close();
            }
        }
        return;
    }

    void handleReceiverError(WebSocketException webSocketException) {
        this.eventHandler.onError(webSocketException);
        if (this.state == State.CONNECTED) {
            close();
        }
        closeSocket();
    }

    public synchronized void close() {
        int i = AnonymousClass3.$SwitchMap$com$google$firebase$database$tubesock$WebSocket$State[this.state.ordinal()];
        if (i == 1) {
            this.state = State.DISCONNECTED;
        } else if (i == 2) {
            closeSocket();
        } else if (i == 3) {
            sendCloseHandshake();
        } else if (i == 4) {
        } else {
            if (i == 5) {
            }
        }
    }

    void onCloseOpReceived() {
        closeSocket();
    }

    private synchronized void closeSocket() {
        if (this.state != State.DISCONNECTED) {
            this.receiver.stopit();
            this.writer.stopIt();
            if (this.socket != null) {
                try {
                    this.socket.close();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
            this.state = State.DISCONNECTED;
            this.eventHandler.onClose();
        }
    }

    private void sendCloseHandshake() {
        try {
            this.state = State.DISCONNECTING;
            this.writer.stopIt();
            this.writer.send((byte) 8, true, new byte[0]);
        } catch (Throwable e) {
            this.eventHandler.onError(new WebSocketException("Failed to send close frame", e));
        }
    }

    private Socket createSocket() {
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2;
        String scheme = this.url.getScheme();
        String host = this.url.getHost();
        int port = this.url.getPort();
        String str = "unknown host: ";
        if (scheme != null && scheme.equals("ws")) {
            if (port == -1) {
                port = 80;
            }
            try {
                return new Socket(host, port);
            } catch (Throwable e) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(host);
                throw new WebSocketException(stringBuilder.toString(), e);
            } catch (Throwable e2) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("error while creating socket to ");
                stringBuilder2.append(this.url);
                throw new WebSocketException(stringBuilder2.toString(), e2);
            }
        } else if (scheme == null || !scheme.equals("wss")) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("unsupported protocol: ");
            stringBuilder2.append(scheme);
            throw new WebSocketException(stringBuilder2.toString());
        } else {
            if (port == -1) {
                port = GrpcUtil.DEFAULT_PORT_SSL;
            }
            SSLSessionCache sSLSessionCache = null;
            try {
                if (this.sslCacheDirectory != null) {
                    sSLSessionCache = new SSLSessionCache(new File(this.sslCacheDirectory));
                }
            } catch (Throwable e3) {
                this.logger.debug("Failed to initialize SSL session cache", e3, new Object[0]);
            }
            try {
                SSLSocket sSLSocket = (SSLSocket) SSLCertificateSocketFactory.getDefault(SSL_HANDSHAKE_TIMEOUT_MS, sSLSessionCache).createSocket(host, port);
                if (HttpsURLConnection.getDefaultHostnameVerifier().verify(host, sSLSocket.getSession())) {
                    return sSLSocket;
                }
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Error while verifying secure socket to ");
                stringBuilder2.append(this.url);
                throw new WebSocketException(stringBuilder2.toString());
            } catch (Throwable e22) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(host);
                throw new WebSocketException(stringBuilder.toString(), e22);
            } catch (Throwable e222) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("error while creating secure socket to ");
                stringBuilder2.append(this.url);
                throw new WebSocketException(stringBuilder2.toString(), e222);
            }
        }
    }

    public void blockClose() throws InterruptedException {
        if (this.writer.getInnerThread().getState() != java.lang.Thread.State.NEW) {
            this.writer.getInnerThread().join();
        }
        getInnerThread().join();
    }

    /* JADX WARNING: Missing block: B:20:?, code:
            r1 = new java.io.DataInputStream(r0.getInputStream());
            r0 = r0.getOutputStream();
            r0.write(r11.handshake.getHandshake());
            r3 = new byte[1000];
            r4 = new java.util.ArrayList();
            r6 = r3;
            r3 = null;
     */
    /* JADX WARNING: Missing block: B:21:0x0044, code:
            r7 = 0;
     */
    /* JADX WARNING: Missing block: B:23:0x0046, code:
            if (r3 != null) goto L_0x00ac;
     */
    /* JADX WARNING: Missing block: B:24:0x0048, code:
            r9 = r1.read();
     */
    /* JADX WARNING: Missing block: B:25:0x004d, code:
            if (r9 == -1) goto L_0x00a4;
     */
    /* JADX WARNING: Missing block: B:26:0x004f, code:
            r6[r7] = (byte) r9;
            r7 = r7 + 1;
     */
    /* JADX WARNING: Missing block: B:27:0x005a, code:
            if (r6[r7 - 1] != (byte) 10) goto L_0x0083;
     */
    /* JADX WARNING: Missing block: B:29:0x0062, code:
            if (r6[r7 - 2] != com.google.common.base.Ascii.CR) goto L_0x0083;
     */
    /* JADX WARNING: Missing block: B:30:0x0064, code:
            r7 = new java.lang.String(r6, UTF8);
     */
    /* JADX WARNING: Missing block: B:31:0x0075, code:
            if (r7.trim().equals("") == false) goto L_0x0079;
     */
    /* JADX WARNING: Missing block: B:32:0x0077, code:
            r3 = 1;
     */
    /* JADX WARNING: Missing block: B:33:0x0079, code:
            r4.add(r7.trim());
     */
    /* JADX WARNING: Missing block: B:34:0x0080, code:
            r6 = new byte[1000];
     */
    /* JADX WARNING: Missing block: B:35:0x0083, code:
            if (r7 == 1000) goto L_0x0086;
     */
    /* JADX WARNING: Missing block: B:37:0x0086, code:
            r0 = new java.lang.String(r6, UTF8);
            r2 = new java.lang.StringBuilder();
            r2.append("Unexpected long line in handshake: ");
            r2.append(r0);
     */
    /* JADX WARNING: Missing block: B:38:0x00a3, code:
            throw new com.google.firebase.database.tubesock.WebSocketException(r2.toString());
     */
    /* JADX WARNING: Missing block: B:40:0x00ab, code:
            throw new com.google.firebase.database.tubesock.WebSocketException("Connection closed before handshake was complete");
     */
    /* JADX WARNING: Missing block: B:41:0x00ac, code:
            r11.handshake.verifyServerStatusLine((java.lang.String) r4.get(0));
            r4.remove(0);
            r2 = new java.util.HashMap();
            r3 = r4.iterator();
     */
    /* JADX WARNING: Missing block: B:43:0x00c7, code:
            if (r3.hasNext() == false) goto L_0x00ea;
     */
    /* JADX WARNING: Missing block: B:44:0x00c9, code:
            r4 = ((java.lang.String) r3.next()).split(": ", 2);
            r2.put(r4[0].toLowerCase(java.util.Locale.US), r4[1].toLowerCase(java.util.Locale.US));
     */
    /* JADX WARNING: Missing block: B:45:0x00ea, code:
            r11.handshake.verifyServerHandshakeHeaders(r2);
            r11.writer.setOutput(r0);
            r11.receiver.setInput(r1);
            r11.state = com.google.firebase.database.tubesock.WebSocket.State.CONNECTED;
            r11.writer.getInnerThread().start();
            r11.eventHandler.onOpen();
            r11.receiver.run();
     */
    private void runReader() {
        /*
        r11 = this;
        r0 = r11.createSocket();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        monitor-enter(r11);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r11.socket = r0;	 Catch:{ all -> 0x0111 }
        r1 = r11.state;	 Catch:{ all -> 0x0111 }
        r2 = com.google.firebase.database.tubesock.WebSocket.State.DISCONNECTED;	 Catch:{ all -> 0x0111 }
        if (r1 != r2) goto L_0x0021;
    L_0x000d:
        r0 = r11.socket;	 Catch:{ IOException -> 0x001a }
        r0.close();	 Catch:{ IOException -> 0x001a }
        r0 = 0;
        r11.socket = r0;	 Catch:{ all -> 0x0111 }
        monitor-exit(r11);	 Catch:{ all -> 0x0111 }
        r11.close();
        return;
    L_0x001a:
        r0 = move-exception;
        r1 = new java.lang.RuntimeException;	 Catch:{ all -> 0x0111 }
        r1.<init>(r0);	 Catch:{ all -> 0x0111 }
        throw r1;	 Catch:{ all -> 0x0111 }
    L_0x0021:
        monitor-exit(r11);	 Catch:{ all -> 0x0111 }
        r1 = new java.io.DataInputStream;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r2 = r0.getInputStream();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r1.<init>(r2);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r0 = r0.getOutputStream();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r2 = r11.handshake;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r2 = r2.getHandshake();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r0.write(r2);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r2 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r3 = new byte[r2];	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r4 = new java.util.ArrayList;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r4.<init>();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r5 = 0;
        r6 = r3;
        r3 = 0;
    L_0x0044:
        r7 = 0;
    L_0x0045:
        r8 = 1;
        if (r3 != 0) goto L_0x00ac;
    L_0x0048:
        r9 = r1.read();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r10 = -1;
        if (r9 == r10) goto L_0x00a4;
    L_0x004f:
        r9 = (byte) r9;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r6[r7] = r9;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r7 = r7 + 1;
        r9 = r7 + -1;
        r9 = r6[r9];	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r10 = 10;
        if (r9 != r10) goto L_0x0083;
    L_0x005c:
        r9 = r7 + -2;
        r9 = r6[r9];	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r10 = 13;
        if (r9 != r10) goto L_0x0083;
    L_0x0064:
        r7 = new java.lang.String;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r9 = UTF8;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r7.<init>(r6, r9);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r6 = r7.trim();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r9 = "";
        r6 = r6.equals(r9);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        if (r6 == 0) goto L_0x0079;
    L_0x0077:
        r3 = 1;
        goto L_0x0080;
    L_0x0079:
        r6 = r7.trim();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r4.add(r6);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
    L_0x0080:
        r6 = new byte[r2];	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        goto L_0x0044;
    L_0x0083:
        if (r7 == r2) goto L_0x0086;
    L_0x0085:
        goto L_0x0045;
    L_0x0086:
        r0 = new java.lang.String;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r1 = UTF8;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r0.<init>(r6, r1);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r1 = new com.google.firebase.database.tubesock.WebSocketException;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r2 = new java.lang.StringBuilder;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r2.<init>();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r3 = "Unexpected long line in handshake: ";
        r2.append(r3);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r2.append(r0);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r0 = r2.toString();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r1.<init>(r0);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        throw r1;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
    L_0x00a4:
        r0 = new com.google.firebase.database.tubesock.WebSocketException;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r1 = "Connection closed before handshake was complete";
        r0.<init>(r1);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        throw r0;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
    L_0x00ac:
        r2 = r11.handshake;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r3 = r4.get(r5);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r3 = (java.lang.String) r3;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r2.verifyServerStatusLine(r3);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r4.remove(r5);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r2 = new java.util.HashMap;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r2.<init>();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r3 = r4.iterator();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
    L_0x00c3:
        r4 = r3.hasNext();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        if (r4 == 0) goto L_0x00ea;
    L_0x00c9:
        r4 = r3.next();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r4 = (java.lang.String) r4;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r6 = ": ";
        r7 = 2;
        r4 = r4.split(r6, r7);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r6 = r4[r5];	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r7 = java.util.Locale.US;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r6 = r6.toLowerCase(r7);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r4 = r4[r8];	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r7 = java.util.Locale.US;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r4 = r4.toLowerCase(r7);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r2.put(r6, r4);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        goto L_0x00c3;
    L_0x00ea:
        r3 = r11.handshake;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r3.verifyServerHandshakeHeaders(r2);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r2 = r11.writer;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r2.setOutput(r0);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r0 = r11.receiver;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r0.setInput(r1);	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r0 = com.google.firebase.database.tubesock.WebSocket.State.CONNECTED;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r11.state = r0;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r0 = r11.writer;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r0 = r0.getInnerThread();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r0.start();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r0 = r11.eventHandler;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r0.onOpen();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r0 = r11.receiver;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        r0.run();	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
        goto L_0x013d;
    L_0x0111:
        r0 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x0111 }
        throw r0;	 Catch:{ WebSocketException -> 0x0137, IOException -> 0x0116 }
    L_0x0114:
        r0 = move-exception;
        goto L_0x0141;
    L_0x0116:
        r0 = move-exception;
        r1 = r11.eventHandler;	 Catch:{ all -> 0x0114 }
        r2 = new com.google.firebase.database.tubesock.WebSocketException;	 Catch:{ all -> 0x0114 }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0114 }
        r3.<init>();	 Catch:{ all -> 0x0114 }
        r4 = "error while connecting: ";
        r3.append(r4);	 Catch:{ all -> 0x0114 }
        r4 = r0.getMessage();	 Catch:{ all -> 0x0114 }
        r3.append(r4);	 Catch:{ all -> 0x0114 }
        r3 = r3.toString();	 Catch:{ all -> 0x0114 }
        r2.<init>(r3, r0);	 Catch:{ all -> 0x0114 }
        r1.onError(r2);	 Catch:{ all -> 0x0114 }
        goto L_0x013d;
    L_0x0137:
        r0 = move-exception;
        r1 = r11.eventHandler;	 Catch:{ all -> 0x0114 }
        r1.onError(r0);	 Catch:{ all -> 0x0114 }
    L_0x013d:
        r11.close();
        return;
    L_0x0141:
        r11.close();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.tubesock.WebSocket.runReader():void");
    }

    Thread getInnerThread() {
        return this.innerThread;
    }
}
