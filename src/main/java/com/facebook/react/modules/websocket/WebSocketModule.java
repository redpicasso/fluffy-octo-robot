package com.facebook.react.modules.websocket;

import com.facebook.common.logging.FLog;
import com.facebook.common.util.UriUtil;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import com.facebook.react.modules.network.ForwardingCookieHandler;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import okhttp3.WebSocket;
import okio.ByteString;

@ReactModule(hasConstants = false, name = "WebSocketModule")
public final class WebSocketModule extends ReactContextBaseJavaModule {
    public static final String NAME = "WebSocketModule";
    private final Map<Integer, ContentHandler> mContentHandlers = new ConcurrentHashMap();
    private ForwardingCookieHandler mCookieHandler;
    private ReactContext mReactContext;
    private final Map<Integer, WebSocket> mWebSocketConnections = new ConcurrentHashMap();

    public interface ContentHandler {
        void onMessage(String str, WritableMap writableMap);

        void onMessage(ByteString byteString, WritableMap writableMap);
    }

    public String getName() {
        return NAME;
    }

    public WebSocketModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.mReactContext = reactApplicationContext;
        this.mCookieHandler = new ForwardingCookieHandler(reactApplicationContext);
    }

    private void sendEvent(String str, WritableMap writableMap) {
        ((RCTDeviceEventEmitter) this.mReactContext.getJSModule(RCTDeviceEventEmitter.class)).emit(str, writableMap);
    }

    public void setContentHandler(int i, ContentHandler contentHandler) {
        if (contentHandler != null) {
            this.mContentHandlers.put(Integer.valueOf(i), contentHandler);
        } else {
            this.mContentHandlers.remove(Integer.valueOf(i));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00e4  */
    @com.facebook.react.bridge.ReactMethod
    public void connect(java.lang.String r7, @javax.annotation.Nullable com.facebook.react.bridge.ReadableArray r8, @javax.annotation.Nullable com.facebook.react.bridge.ReadableMap r9, final int r10) {
        /*
        r6 = this;
        r0 = new okhttp3.OkHttpClient$Builder;
        r0.<init>();
        r1 = java.util.concurrent.TimeUnit.SECONDS;
        r2 = 10;
        r0 = r0.connectTimeout(r2, r1);
        r1 = java.util.concurrent.TimeUnit.SECONDS;
        r0 = r0.writeTimeout(r2, r1);
        r1 = java.util.concurrent.TimeUnit.MINUTES;
        r2 = 0;
        r0 = r0.readTimeout(r2, r1);
        r0 = r0.build();
        r1 = new okhttp3.Request$Builder;
        r1.<init>();
        r2 = java.lang.Integer.valueOf(r10);
        r1 = r1.tag(r2);
        r1 = r1.url(r7);
        r2 = r6.getCookie(r7);
        if (r2 == 0) goto L_0x003b;
    L_0x0036:
        r3 = "Cookie";
        r1.addHeader(r3, r2);
    L_0x003b:
        r2 = "origin";
        if (r9 == 0) goto L_0x00a2;
    L_0x003f:
        r3 = "headers";
        r4 = r9.hasKey(r3);
        if (r4 == 0) goto L_0x00a2;
    L_0x0047:
        r4 = r9.getType(r3);
        r5 = com.facebook.react.bridge.ReadableType.Map;
        r4 = r4.equals(r5);
        if (r4 == 0) goto L_0x00a2;
    L_0x0053:
        r9 = r9.getMap(r3);
        r3 = r9.keySetIterator();
        r4 = r9.hasKey(r2);
        if (r4 != 0) goto L_0x0068;
    L_0x0061:
        r7 = getDefaultOrigin(r7);
        r1.addHeader(r2, r7);
    L_0x0068:
        r7 = r3.hasNextKey();
        if (r7 == 0) goto L_0x00a9;
    L_0x006e:
        r7 = r3.nextKey();
        r2 = com.facebook.react.bridge.ReadableType.String;
        r4 = r9.getType(r7);
        r2 = r2.equals(r4);
        if (r2 == 0) goto L_0x0086;
    L_0x007e:
        r2 = r9.getString(r7);
        r1.addHeader(r7, r2);
        goto L_0x0068;
    L_0x0086:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r4 = "Ignoring: requested ";
        r2.append(r4);
        r2.append(r7);
        r7 = ", value not a string";
        r2.append(r7);
        r7 = r2.toString();
        r2 = "ReactNative";
        com.facebook.common.logging.FLog.w(r2, r7);
        goto L_0x0068;
    L_0x00a2:
        r7 = getDefaultOrigin(r7);
        r1.addHeader(r2, r7);
    L_0x00a9:
        if (r8 == 0) goto L_0x00fa;
    L_0x00ab:
        r7 = r8.size();
        if (r7 <= 0) goto L_0x00fa;
    L_0x00b1:
        r7 = new java.lang.StringBuilder;
        r9 = "";
        r7.<init>(r9);
        r2 = 0;
    L_0x00b9:
        r3 = r8.size();
        if (r2 >= r3) goto L_0x00de;
    L_0x00bf:
        r3 = r8.getString(r2);
        r3 = r3.trim();
        r4 = r3.isEmpty();
        if (r4 != 0) goto L_0x00db;
    L_0x00cd:
        r4 = ",";
        r5 = r3.contains(r4);
        if (r5 != 0) goto L_0x00db;
    L_0x00d5:
        r7.append(r3);
        r7.append(r4);
    L_0x00db:
        r2 = r2 + 1;
        goto L_0x00b9;
    L_0x00de:
        r8 = r7.length();
        if (r8 <= 0) goto L_0x00fa;
    L_0x00e4:
        r8 = r7.length();
        r8 = r8 + -1;
        r2 = r7.length();
        r7.replace(r8, r2, r9);
        r7 = r7.toString();
        r8 = "Sec-WebSocket-Protocol";
        r1.addHeader(r8, r7);
    L_0x00fa:
        r7 = r1.build();
        r8 = new com.facebook.react.modules.websocket.WebSocketModule$1;
        r8.<init>(r10);
        r0.newWebSocket(r7, r8);
        r7 = r0.dispatcher();
        r7 = r7.executorService();
        r7.shutdown();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.modules.websocket.WebSocketModule.connect(java.lang.String, com.facebook.react.bridge.ReadableArray, com.facebook.react.bridge.ReadableMap, int):void");
    }

    @ReactMethod
    public void close(int i, String str, int i2) {
        WebSocket webSocket = (WebSocket) this.mWebSocketConnections.get(Integer.valueOf(i2));
        if (webSocket != null) {
            try {
                webSocket.close(i, str);
                this.mWebSocketConnections.remove(Integer.valueOf(i2));
                this.mContentHandlers.remove(Integer.valueOf(i2));
            } catch (Throwable e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not close WebSocket connection for id ");
                stringBuilder.append(i2);
                FLog.e(ReactConstants.TAG, stringBuilder.toString(), e);
            }
        }
    }

    @ReactMethod
    public void send(String str, int i) {
        WebSocket webSocket = (WebSocket) this.mWebSocketConnections.get(Integer.valueOf(i));
        if (webSocket == null) {
            WritableMap createMap = Arguments.createMap();
            String str2 = "id";
            createMap.putInt(str2, i);
            String str3 = "client is null";
            createMap.putString("message", str3);
            sendEvent("websocketFailed", createMap);
            createMap = Arguments.createMap();
            createMap.putInt(str2, i);
            createMap.putInt("code", 0);
            createMap.putString("reason", str3);
            sendEvent("websocketClosed", createMap);
            this.mWebSocketConnections.remove(Integer.valueOf(i));
            this.mContentHandlers.remove(Integer.valueOf(i));
            return;
        }
        try {
            webSocket.send(str);
        } catch (Exception e) {
            notifyWebSocketFailed(i, e.getMessage());
        }
    }

    @ReactMethod
    public void sendBinary(String str, int i) {
        WebSocket webSocket = (WebSocket) this.mWebSocketConnections.get(Integer.valueOf(i));
        if (webSocket == null) {
            WritableMap createMap = Arguments.createMap();
            String str2 = "id";
            createMap.putInt(str2, i);
            String str3 = "client is null";
            createMap.putString("message", str3);
            sendEvent("websocketFailed", createMap);
            createMap = Arguments.createMap();
            createMap.putInt(str2, i);
            createMap.putInt("code", 0);
            createMap.putString("reason", str3);
            sendEvent("websocketClosed", createMap);
            this.mWebSocketConnections.remove(Integer.valueOf(i));
            this.mContentHandlers.remove(Integer.valueOf(i));
            return;
        }
        try {
            webSocket.send(ByteString.decodeBase64(str));
        } catch (Exception e) {
            notifyWebSocketFailed(i, e.getMessage());
        }
    }

    public void sendBinary(ByteString byteString, int i) {
        WebSocket webSocket = (WebSocket) this.mWebSocketConnections.get(Integer.valueOf(i));
        if (webSocket == null) {
            WritableMap createMap = Arguments.createMap();
            String str = "id";
            createMap.putInt(str, i);
            String str2 = "client is null";
            createMap.putString("message", str2);
            sendEvent("websocketFailed", createMap);
            createMap = Arguments.createMap();
            createMap.putInt(str, i);
            createMap.putInt("code", 0);
            createMap.putString("reason", str2);
            sendEvent("websocketClosed", createMap);
            this.mWebSocketConnections.remove(Integer.valueOf(i));
            this.mContentHandlers.remove(Integer.valueOf(i));
            return;
        }
        try {
            webSocket.send(byteString);
        } catch (Exception e) {
            notifyWebSocketFailed(i, e.getMessage());
        }
    }

    @ReactMethod
    public void ping(int i) {
        WebSocket webSocket = (WebSocket) this.mWebSocketConnections.get(Integer.valueOf(i));
        if (webSocket == null) {
            WritableMap createMap = Arguments.createMap();
            String str = "id";
            createMap.putInt(str, i);
            String str2 = "client is null";
            createMap.putString("message", str2);
            sendEvent("websocketFailed", createMap);
            createMap = Arguments.createMap();
            createMap.putInt(str, i);
            createMap.putInt("code", 0);
            createMap.putString("reason", str2);
            sendEvent("websocketClosed", createMap);
            this.mWebSocketConnections.remove(Integer.valueOf(i));
            this.mContentHandlers.remove(Integer.valueOf(i));
            return;
        }
        try {
            webSocket.send(ByteString.EMPTY);
        } catch (Exception e) {
            notifyWebSocketFailed(i, e.getMessage());
        }
    }

    private void notifyWebSocketFailed(int i, String str) {
        WritableMap createMap = Arguments.createMap();
        createMap.putInt("id", i);
        createMap.putString("message", str);
        sendEvent("websocketFailed", createMap);
    }

    private static String getDefaultOrigin(String str) {
        try {
            String str2 = "";
            URI uri = new URI(str);
            boolean equals = uri.getScheme().equals("wss");
            String str3 = UriUtil.HTTPS_SCHEME;
            StringBuilder stringBuilder;
            if (equals) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str2);
                stringBuilder.append(str3);
                str2 = stringBuilder.toString();
            } else {
                equals = uri.getScheme().equals("ws");
                String str4 = UriUtil.HTTP_SCHEME;
                if (equals) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str2);
                    stringBuilder.append(str4);
                    str2 = stringBuilder.toString();
                } else if (uri.getScheme().equals(str4) || uri.getScheme().equals(str3)) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str2);
                    stringBuilder.append(uri.getScheme());
                    str2 = stringBuilder.toString();
                }
            }
            if (uri.getPort() != -1) {
                return String.format("%s://%s:%s", new Object[]{str2, uri.getHost(), Integer.valueOf(uri.getPort())});
            }
            return String.format("%s://%s", new Object[]{str2, uri.getHost()});
        } catch (URISyntaxException unused) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Unable to set ");
            stringBuilder2.append(str);
            stringBuilder2.append(" as default origin header");
            throw new IllegalArgumentException(stringBuilder2.toString());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x002f A:{ExcHandler: java.net.URISyntaxException (unused java.net.URISyntaxException), Splitter: B:0:0x0000} */
    /* JADX WARNING: Missing block: B:9:0x002f, code:
            r1 = new java.lang.StringBuilder();
            r1.append("Unable to get cookie from ");
            r1.append(r4);
     */
    /* JADX WARNING: Missing block: B:10:0x0045, code:
            throw new java.lang.IllegalArgumentException(r1.toString());
     */
    private java.lang.String getCookie(java.lang.String r4) {
        /*
        r3 = this;
        r0 = new java.net.URI;	 Catch:{ URISyntaxException -> 0x002f, URISyntaxException -> 0x002f }
        r1 = getDefaultOrigin(r4);	 Catch:{ URISyntaxException -> 0x002f, URISyntaxException -> 0x002f }
        r0.<init>(r1);	 Catch:{ URISyntaxException -> 0x002f, URISyntaxException -> 0x002f }
        r1 = r3.mCookieHandler;	 Catch:{ URISyntaxException -> 0x002f, URISyntaxException -> 0x002f }
        r2 = new java.util.HashMap;	 Catch:{ URISyntaxException -> 0x002f, URISyntaxException -> 0x002f }
        r2.<init>();	 Catch:{ URISyntaxException -> 0x002f, URISyntaxException -> 0x002f }
        r0 = r1.get(r0, r2);	 Catch:{ URISyntaxException -> 0x002f, URISyntaxException -> 0x002f }
        r1 = "Cookie";
        r0 = r0.get(r1);	 Catch:{ URISyntaxException -> 0x002f, URISyntaxException -> 0x002f }
        r0 = (java.util.List) r0;	 Catch:{ URISyntaxException -> 0x002f, URISyntaxException -> 0x002f }
        if (r0 == 0) goto L_0x002d;
    L_0x001e:
        r1 = r0.isEmpty();	 Catch:{ URISyntaxException -> 0x002f, URISyntaxException -> 0x002f }
        if (r1 == 0) goto L_0x0025;
    L_0x0024:
        goto L_0x002d;
    L_0x0025:
        r1 = 0;
        r0 = r0.get(r1);	 Catch:{ URISyntaxException -> 0x002f, URISyntaxException -> 0x002f }
        r0 = (java.lang.String) r0;	 Catch:{ URISyntaxException -> 0x002f, URISyntaxException -> 0x002f }
        return r0;
    L_0x002d:
        r4 = 0;
        return r4;
    L_0x002f:
        r0 = new java.lang.IllegalArgumentException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Unable to get cookie from ";
        r1.append(r2);
        r1.append(r4);
        r4 = r1.toString();
        r0.<init>(r4);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.modules.websocket.WebSocketModule.getCookie(java.lang.String):java.lang.String");
    }
}
