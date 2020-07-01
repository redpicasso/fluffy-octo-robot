package com.google.firebase.database.connection;

import com.google.common.net.HttpHeaders;
import com.google.firebase.database.connection.util.StringListReader;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.logging.Logger;
import com.google.firebase.database.tubesock.WebSocket;
import com.google.firebase.database.tubesock.WebSocketEventHandler;
import com.google.firebase.database.tubesock.WebSocketException;
import com.google.firebase.database.tubesock.WebSocketMessage;
import com.google.firebase.database.util.JsonMapper;
import java.io.EOFException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
class WebsocketConnection {
    private static final long CONNECT_TIMEOUT_MS = 30000;
    private static final long KEEP_ALIVE_TIMEOUT_MS = 45000;
    private static final int MAX_FRAME_SIZE = 16384;
    private static long connectionId;
    private WSClient conn;
    private ScheduledFuture<?> connectTimeout;
    private final ConnectionContext connectionContext;
    private Delegate delegate;
    private boolean everConnected = false;
    private final ScheduledExecutorService executorService;
    private StringListReader frameReader;
    private boolean isClosed = false;
    private ScheduledFuture<?> keepAlive;
    private final LogWrapper logger;
    private long totalFrames = 0;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface Delegate {
        void onDisconnect(boolean z);

        void onMessage(Map<String, Object> map);
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private interface WSClient {
        void close();

        void connect();

        void send(String str);
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private class WSClientTubesock implements WSClient, WebSocketEventHandler {
        private WebSocket ws;

        /* synthetic */ WSClientTubesock(WebsocketConnection websocketConnection, WebSocket webSocket, AnonymousClass1 anonymousClass1) {
            this(webSocket);
        }

        private WSClientTubesock(WebSocket webSocket) {
            this.ws = webSocket;
            this.ws.setEventHandler(this);
        }

        public void onOpen() {
            WebsocketConnection.this.executorService.execute(new Runnable() {
                public void run() {
                    WebsocketConnection.this.connectTimeout.cancel(false);
                    WebsocketConnection.this.everConnected = true;
                    if (WebsocketConnection.this.logger.logsDebug()) {
                        WebsocketConnection.this.logger.debug("websocket opened", new Object[0]);
                    }
                    WebsocketConnection.this.resetKeepAlive();
                }
            });
        }

        public void onMessage(WebSocketMessage webSocketMessage) {
            final String text = webSocketMessage.getText();
            if (WebsocketConnection.this.logger.logsDebug()) {
                LogWrapper access$200 = WebsocketConnection.this.logger;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("ws message: ");
                stringBuilder.append(text);
                access$200.debug(stringBuilder.toString(), new Object[0]);
            }
            WebsocketConnection.this.executorService.execute(new Runnable() {
                public void run() {
                    WebsocketConnection.this.handleIncomingFrame(text);
                }
            });
        }

        public void onClose() {
            WebsocketConnection.this.executorService.execute(new Runnable() {
                public void run() {
                    if (WebsocketConnection.this.logger.logsDebug()) {
                        WebsocketConnection.this.logger.debug("closed", new Object[0]);
                    }
                    WebsocketConnection.this.onClosed();
                }
            });
        }

        public void onError(final WebSocketException webSocketException) {
            WebsocketConnection.this.executorService.execute(new Runnable() {
                public void run() {
                    if (webSocketException.getCause() == null || !(webSocketException.getCause() instanceof EOFException)) {
                        WebsocketConnection.this.logger.debug("WebSocket error.", webSocketException, new Object[0]);
                    } else {
                        WebsocketConnection.this.logger.debug("WebSocket reached EOF.", new Object[0]);
                    }
                    WebsocketConnection.this.onClosed();
                }
            });
        }

        public void onLogMessage(String str) {
            if (WebsocketConnection.this.logger.logsDebug()) {
                LogWrapper access$200 = WebsocketConnection.this.logger;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Tubesock: ");
                stringBuilder.append(str);
                access$200.debug(stringBuilder.toString(), new Object[0]);
            }
        }

        public void send(String str) {
            this.ws.send(str);
        }

        public void close() {
            this.ws.close();
        }

        private void shutdown() {
            this.ws.close();
            try {
                this.ws.blockClose();
            } catch (Throwable e) {
                WebsocketConnection.this.logger.error("Interrupted while shutting down websocket threads", e);
            }
        }

        public void connect() {
            try {
                this.ws.connect();
            } catch (Throwable e) {
                if (WebsocketConnection.this.logger.logsDebug()) {
                    WebsocketConnection.this.logger.debug("Error connecting", e, new Object[0]);
                }
                shutdown();
            }
        }
    }

    public void start() {
    }

    public WebsocketConnection(ConnectionContext connectionContext, HostInfo hostInfo, String str, Delegate delegate, String str2) {
        this.connectionContext = connectionContext;
        this.executorService = connectionContext.getExecutorService();
        this.delegate = delegate;
        long j = connectionId;
        connectionId = 1 + j;
        Logger logger = connectionContext.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ws_");
        stringBuilder.append(j);
        this.logger = new LogWrapper(logger, "WebSocket", stringBuilder.toString());
        this.conn = createConnection(hostInfo, str, str2);
    }

    private WSClient createConnection(HostInfo hostInfo, String str, String str2) {
        if (str == null) {
            str = hostInfo.getHost();
        }
        URI connectionUrl = HostInfo.getConnectionUrl(str, hostInfo.isSecure(), hostInfo.getNamespace(), str2);
        Map hashMap = new HashMap();
        hashMap.put(HttpHeaders.USER_AGENT, this.connectionContext.getUserAgent());
        return new WSClientTubesock(this, new WebSocket(this.connectionContext, connectionUrl, null, hashMap), null);
    }

    public void open() {
        this.conn.connect();
        this.connectTimeout = this.executorService.schedule(new Runnable() {
            public void run() {
                WebsocketConnection.this.closeIfNeverConnected();
            }
        }, CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS);
    }

    public void close() {
        if (this.logger.logsDebug()) {
            this.logger.debug("websocket is being closed", new Object[0]);
        }
        this.isClosed = true;
        this.conn.close();
        ScheduledFuture scheduledFuture = this.connectTimeout;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
        scheduledFuture = this.keepAlive;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }

    public void send(Map<String, Object> map) {
        resetKeepAlive();
        StringBuilder stringBuilder;
        try {
            String[] splitIntoFrames = splitIntoFrames(JsonMapper.serializeJson(map), 16384);
            if (splitIntoFrames.length > 1) {
                WSClient wSClient = this.conn;
                stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(splitIntoFrames.length);
                wSClient.send(stringBuilder.toString());
            }
            for (String send : splitIntoFrames) {
                this.conn.send(send);
            }
        } catch (Throwable e) {
            LogWrapper logWrapper = this.logger;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to serialize message: ");
            stringBuilder.append(map.toString());
            logWrapper.error(stringBuilder.toString(), e);
            shutdown();
        }
    }

    private void appendFrame(String str) {
        this.frameReader.addString(str);
        this.totalFrames--;
        if (this.totalFrames == 0) {
            LogWrapper logWrapper;
            StringBuilder stringBuilder;
            try {
                this.frameReader.freeze();
                Map parseJson = JsonMapper.parseJson(this.frameReader.toString());
                this.frameReader = null;
                if (this.logger.logsDebug()) {
                    logWrapper = this.logger;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("handleIncomingFrame complete frame: ");
                    stringBuilder.append(parseJson);
                    logWrapper.debug(stringBuilder.toString(), new Object[0]);
                }
                this.delegate.onMessage(parseJson);
            } catch (Throwable e) {
                logWrapper = this.logger;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Error parsing frame: ");
                stringBuilder.append(this.frameReader.toString());
                logWrapper.error(stringBuilder.toString(), e);
                close();
                shutdown();
            } catch (Throwable e2) {
                logWrapper = this.logger;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Error parsing frame (cast error): ");
                stringBuilder.append(this.frameReader.toString());
                logWrapper.error(stringBuilder.toString(), e2);
                close();
                shutdown();
            }
        }
    }

    private void handleNewFrameCount(int i) {
        this.totalFrames = (long) i;
        this.frameReader = new StringListReader();
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("HandleNewFrameCount: ");
            stringBuilder.append(this.totalFrames);
            logWrapper.debug(stringBuilder.toString(), new Object[0]);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:8:0x0012 A:{ExcHandler: java.lang.NumberFormatException (unused java.lang.NumberFormatException), Splitter: B:2:0x0007} */
    private java.lang.String extractFrameCount(java.lang.String r3) {
        /*
        r2 = this;
        r0 = r3.length();
        r1 = 6;
        if (r0 > r1) goto L_0x0012;
    L_0x0007:
        r0 = java.lang.Integer.parseInt(r3);	 Catch:{ NumberFormatException -> 0x0012 }
        if (r0 <= 0) goto L_0x0010;
    L_0x000d:
        r2.handleNewFrameCount(r0);	 Catch:{ NumberFormatException -> 0x0012 }
    L_0x0010:
        r3 = 0;
        return r3;
    L_0x0012:
        r0 = 1;
        r2.handleNewFrameCount(r0);
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.connection.WebsocketConnection.extractFrameCount(java.lang.String):java.lang.String");
    }

    private void handleIncomingFrame(String str) {
        if (!this.isClosed) {
            resetKeepAlive();
            if (isBuffering()) {
                appendFrame(str);
                return;
            }
            str = extractFrameCount(str);
            if (str != null) {
                appendFrame(str);
            }
        }
    }

    private void resetKeepAlive() {
        if (!this.isClosed) {
            ScheduledFuture scheduledFuture = this.keepAlive;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
                if (this.logger.logsDebug()) {
                    LogWrapper logWrapper = this.logger;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Reset keepAlive. Remaining: ");
                    stringBuilder.append(this.keepAlive.getDelay(TimeUnit.MILLISECONDS));
                    logWrapper.debug(stringBuilder.toString(), new Object[0]);
                }
            } else if (this.logger.logsDebug()) {
                this.logger.debug("Reset keepAlive", new Object[0]);
            }
            this.keepAlive = this.executorService.schedule(nop(), KEEP_ALIVE_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        }
    }

    private Runnable nop() {
        return new Runnable() {
            public void run() {
                if (WebsocketConnection.this.conn != null) {
                    WebsocketConnection.this.conn.send("0");
                    WebsocketConnection.this.resetKeepAlive();
                }
            }
        };
    }

    private boolean isBuffering() {
        return this.frameReader != null;
    }

    private void onClosed() {
        if (!this.isClosed) {
            if (this.logger.logsDebug()) {
                this.logger.debug("closing itself", new Object[0]);
            }
            shutdown();
        }
        this.conn = null;
        ScheduledFuture scheduledFuture = this.keepAlive;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
    }

    private void shutdown() {
        this.isClosed = true;
        this.delegate.onDisconnect(this.everConnected);
    }

    private void closeIfNeverConnected() {
        if (!this.everConnected && !this.isClosed) {
            if (this.logger.logsDebug()) {
                this.logger.debug("timed out on connect", new Object[0]);
            }
            this.conn.close();
        }
    }

    private static String[] splitIntoFrames(String str, int i) {
        int i2 = 0;
        if (str.length() <= i) {
            return new String[]{str};
        }
        ArrayList arrayList = new ArrayList();
        while (i2 < str.length()) {
            int i3 = i2 + i;
            arrayList.add(str.substring(i2, Math.min(i3, str.length())));
            i2 = i3;
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }
}
