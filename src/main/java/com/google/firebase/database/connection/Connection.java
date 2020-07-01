package com.google.firebase.database.connection;

import com.google.common.net.HttpHeaders;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.logging.Logger;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
class Connection implements com.google.firebase.database.connection.WebsocketConnection.Delegate {
    private static final String REQUEST_PAYLOAD = "d";
    private static final String REQUEST_TYPE = "t";
    private static final String REQUEST_TYPE_DATA = "d";
    private static final String SERVER_CONTROL_MESSAGE = "c";
    private static final String SERVER_CONTROL_MESSAGE_DATA = "d";
    private static final String SERVER_CONTROL_MESSAGE_HELLO = "h";
    private static final String SERVER_CONTROL_MESSAGE_RESET = "r";
    private static final String SERVER_CONTROL_MESSAGE_SHUTDOWN = "s";
    private static final String SERVER_CONTROL_MESSAGE_TYPE = "t";
    private static final String SERVER_DATA_MESSAGE = "d";
    private static final String SERVER_ENVELOPE_DATA = "d";
    private static final String SERVER_ENVELOPE_TYPE = "t";
    private static final String SERVER_HELLO_HOST = "h";
    private static final String SERVER_HELLO_SESSION_ID = "s";
    private static final String SERVER_HELLO_TIMESTAMP = "ts";
    private static long connectionIds;
    private WebsocketConnection conn;
    private Delegate delegate;
    private HostInfo hostInfo;
    private final LogWrapper logger;
    private State state = State.REALTIME_CONNECTING;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface Delegate {
        void onCacheHost(String str);

        void onDataMessage(Map<String, Object> map);

        void onDisconnect(DisconnectReason disconnectReason);

        void onKill(String str);

        void onReady(long j, String str);
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public enum DisconnectReason {
        SERVER_RESET,
        OTHER
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private enum State {
        REALTIME_CONNECTING,
        REALTIME_CONNECTED,
        REALTIME_DISCONNECTED
    }

    public Connection(ConnectionContext connectionContext, HostInfo hostInfo, String str, Delegate delegate, String str2) {
        long j = connectionIds;
        connectionIds = 1 + j;
        this.hostInfo = hostInfo;
        this.delegate = delegate;
        Logger logger = connectionContext.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("conn_");
        stringBuilder.append(j);
        this.logger = new LogWrapper(logger, HttpHeaders.CONNECTION, stringBuilder.toString());
        this.conn = new WebsocketConnection(connectionContext, hostInfo, str, this, str2);
    }

    public void open() {
        if (this.logger.logsDebug()) {
            this.logger.debug("Opening a connection", new Object[0]);
        }
        this.conn.open();
    }

    public void close(DisconnectReason disconnectReason) {
        if (this.state != State.REALTIME_DISCONNECTED) {
            if (this.logger.logsDebug()) {
                this.logger.debug("closing realtime connection", new Object[0]);
            }
            this.state = State.REALTIME_DISCONNECTED;
            WebsocketConnection websocketConnection = this.conn;
            if (websocketConnection != null) {
                websocketConnection.close();
                this.conn = null;
            }
            this.delegate.onDisconnect(disconnectReason);
        }
    }

    public void close() {
        close(DisconnectReason.OTHER);
    }

    public void sendRequest(Map<String, Object> map, boolean z) {
        Map hashMap = new HashMap();
        String str = "d";
        hashMap.put("t", str);
        hashMap.put(str, map);
        sendData(hashMap, z);
    }

    public void onMessage(Map<String, Object> map) {
        String str = "d";
        LogWrapper logWrapper;
        StringBuilder stringBuilder;
        try {
            String str2 = (String) map.get("t");
            if (str2 == null) {
                if (this.logger.logsDebug()) {
                    logWrapper = this.logger;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to parse server message: missing message type:");
                    stringBuilder.append(map.toString());
                    logWrapper.debug(stringBuilder.toString(), new Object[0]);
                }
                close();
            } else if (str2.equals(str)) {
                onDataMessage((Map) map.get(str));
            } else if (str2.equals(SERVER_CONTROL_MESSAGE)) {
                onControlMessage((Map) map.get(str));
            } else if (this.logger.logsDebug()) {
                LogWrapper logWrapper2 = this.logger;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Ignoring unknown server message type: ");
                stringBuilder2.append(str2);
                logWrapper2.debug(stringBuilder2.toString(), new Object[0]);
            }
        } catch (ClassCastException e) {
            if (this.logger.logsDebug()) {
                logWrapper = this.logger;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to parse server message: ");
                stringBuilder.append(e.toString());
                logWrapper.debug(stringBuilder.toString(), new Object[0]);
            }
            close();
        }
    }

    public void onDisconnect(boolean z) {
        this.conn = null;
        if (z || this.state != State.REALTIME_CONNECTING) {
            if (this.logger.logsDebug()) {
                this.logger.debug("Realtime connection lost", new Object[0]);
            }
        } else if (this.logger.logsDebug()) {
            this.logger.debug("Realtime connection failed", new Object[0]);
        }
        close();
    }

    private void onDataMessage(Map<String, Object> map) {
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("received data message: ");
            stringBuilder.append(map.toString());
            logWrapper.debug(stringBuilder.toString(), new Object[0]);
        }
        this.delegate.onDataMessage(map);
    }

    private void onControlMessage(Map<String, Object> map) {
        LogWrapper logWrapper;
        StringBuilder stringBuilder;
        if (this.logger.logsDebug()) {
            logWrapper = this.logger;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Got control message: ");
            stringBuilder.append(map.toString());
            logWrapper.debug(stringBuilder.toString(), new Object[0]);
        }
        try {
            String str = (String) map.get("t");
            if (str != null) {
                String str2 = "d";
                if (str.equals("s")) {
                    onConnectionShutdown((String) map.get(str2));
                    return;
                } else if (str.equals(SERVER_CONTROL_MESSAGE_RESET)) {
                    onReset((String) map.get(str2));
                    return;
                } else if (str.equals("h")) {
                    onHandshake((Map) map.get(str2));
                    return;
                } else if (this.logger.logsDebug()) {
                    LogWrapper logWrapper2 = this.logger;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Ignoring unknown control message: ");
                    stringBuilder.append(str);
                    logWrapper2.debug(stringBuilder.toString(), new Object[0]);
                    return;
                } else {
                    return;
                }
            }
            if (this.logger.logsDebug()) {
                logWrapper = this.logger;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Got invalid control message: ");
                stringBuilder.append(map.toString());
                logWrapper.debug(stringBuilder.toString(), new Object[0]);
            }
            close();
        } catch (ClassCastException e) {
            if (this.logger.logsDebug()) {
                logWrapper = this.logger;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to parse control message: ");
                stringBuilder.append(e.toString());
                logWrapper.debug(stringBuilder.toString(), new Object[0]);
            }
            close();
        }
    }

    private void onConnectionShutdown(String str) {
        if (this.logger.logsDebug()) {
            this.logger.debug("Connection shutdown command received. Shutting down...", new Object[0]);
        }
        this.delegate.onKill(str);
        close();
    }

    private void onHandshake(Map<String, Object> map) {
        long longValue = ((Long) map.get(SERVER_HELLO_TIMESTAMP)).longValue();
        this.delegate.onCacheHost((String) map.get("h"));
        String str = (String) map.get("s");
        if (this.state == State.REALTIME_CONNECTING) {
            this.conn.start();
            onConnectionReady(longValue, str);
        }
    }

    private void onConnectionReady(long j, String str) {
        if (this.logger.logsDebug()) {
            this.logger.debug("realtime connection established", new Object[0]);
        }
        this.state = State.REALTIME_CONNECTED;
        this.delegate.onReady(j, str);
    }

    private void onReset(String str) {
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Got a reset; killing connection to ");
            stringBuilder.append(this.hostInfo.getHost());
            stringBuilder.append("; Updating internalHost to ");
            stringBuilder.append(str);
            logWrapper.debug(stringBuilder.toString(), new Object[0]);
        }
        this.delegate.onCacheHost(str);
        close(DisconnectReason.SERVER_RESET);
    }

    private void sendData(Map<String, Object> map, boolean z) {
        if (this.state != State.REALTIME_CONNECTED) {
            this.logger.debug("Tried to send on an unconnected connection", new Object[0]);
            return;
        }
        if (z) {
            this.logger.debug("Sending data (contents hidden)", new Object[0]);
        } else {
            this.logger.debug("Sending data: %s", map);
        }
        this.conn.send(map);
    }

    public void injectConnectionFailure() {
        close();
    }
}
