package com.facebook.react.devsupport;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import androidx.core.app.NotificationCompat;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Inspector;
import com.facebook.react.bridge.Inspector.LocalConnection;
import com.facebook.react.bridge.Inspector.Page;
import com.facebook.react.bridge.Inspector.RemoteConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InspectorPackagerConnection {
    private static final String TAG = "InspectorPackagerConnection";
    private BundleStatusProvider mBundleStatusProvider;
    private final Connection mConnection;
    private final Map<String, LocalConnection> mInspectorConnections = new HashMap();
    private final String mPackageName;

    public static class BundleStatus {
        public Boolean isLastDownloadSucess;
        public long updateTimestamp;

        public BundleStatus(Boolean bool, long j) {
            this.updateTimestamp = -1;
            this.isLastDownloadSucess = bool;
            this.updateTimestamp = j;
        }

        public BundleStatus() {
            this(Boolean.valueOf(false), -1);
        }
    }

    public interface BundleStatusProvider {
        BundleStatus getBundleStatus();
    }

    private class Connection extends WebSocketListener {
        private static final int RECONNECT_DELAY_MS = 2000;
        private boolean mClosed;
        private final Handler mHandler = new Handler(Looper.getMainLooper());
        private OkHttpClient mHttpClient;
        private boolean mSuppressConnectionErrors;
        private final String mUrl;
        @Nullable
        private WebSocket mWebSocket;

        public Connection(String str) {
            this.mUrl = str;
        }

        public void onOpen(WebSocket webSocket, Response response) {
            this.mWebSocket = webSocket;
        }

        public void onFailure(WebSocket webSocket, Throwable th, Response response) {
            if (this.mWebSocket != null) {
                abort("Websocket exception", th);
            }
            if (!this.mClosed) {
                reconnect();
            }
        }

        public void onMessage(WebSocket webSocket, String str) {
            try {
                InspectorPackagerConnection.this.handleProxyMessage(new JSONObject(str));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public void onClosed(WebSocket webSocket, int i, String str) {
            this.mWebSocket = null;
            InspectorPackagerConnection.this.closeAllConnections();
            if (!this.mClosed) {
                reconnect();
            }
        }

        public void connect() {
            if (this.mClosed) {
                throw new IllegalStateException("Can't connect closed client");
            }
            if (this.mHttpClient == null) {
                this.mHttpClient = new Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(0, TimeUnit.MINUTES).build();
            }
            this.mHttpClient.newWebSocket(new Request.Builder().url(this.mUrl).build(), this);
        }

        private void reconnect() {
            if (this.mClosed) {
                throw new IllegalStateException("Can't reconnect closed client");
            }
            if (!this.mSuppressConnectionErrors) {
                FLog.w(InspectorPackagerConnection.TAG, "Couldn't connect to packager, will silently retry");
                this.mSuppressConnectionErrors = true;
            }
            this.mHandler.postDelayed(new Runnable() {
                public void run() {
                    if (!Connection.this.mClosed) {
                        Connection.this.connect();
                    }
                }
            }, 2000);
        }

        public void close() {
            this.mClosed = true;
            WebSocket webSocket = this.mWebSocket;
            if (webSocket != null) {
                try {
                    webSocket.close(1000, "End of session");
                } catch (Exception unused) {
                    this.mWebSocket = null;
                }
            }
        }

        public void send(final JSONObject jSONObject) {
            new AsyncTask<WebSocket, Void, Void>() {
                protected Void doInBackground(WebSocket... webSocketArr) {
                    if (!(webSocketArr == null || webSocketArr.length == 0)) {
                        try {
                            webSocketArr[0].send(jSONObject.toString());
                        } catch (Throwable e) {
                            FLog.w(InspectorPackagerConnection.TAG, "Couldn't send event to packager", e);
                        }
                    }
                    return null;
                }
            }.execute(new WebSocket[]{this.mWebSocket});
        }

        private void abort(String str, Throwable th) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error occurred, shutting down websocket connection: ");
            stringBuilder.append(str);
            FLog.e(InspectorPackagerConnection.TAG, stringBuilder.toString(), th);
            InspectorPackagerConnection.this.closeAllConnections();
            closeWebSocketQuietly();
        }

        private void closeWebSocketQuietly() {
            WebSocket webSocket = this.mWebSocket;
            if (webSocket != null) {
                try {
                    webSocket.close(1000, "End of session");
                } catch (Exception unused) {
                    this.mWebSocket = null;
                }
            }
        }
    }

    public InspectorPackagerConnection(String str, String str2, BundleStatusProvider bundleStatusProvider) {
        this.mConnection = new Connection(str);
        this.mPackageName = str2;
        this.mBundleStatusProvider = bundleStatusProvider;
    }

    public void connect() {
        this.mConnection.connect();
    }

    public void closeQuietly() {
        this.mConnection.close();
    }

    public void sendEventToAllConnections(String str) {
        for (Entry value : this.mInspectorConnections.entrySet()) {
            ((LocalConnection) value.getValue()).sendMessage(str);
        }
    }

    void handleProxyMessage(org.json.JSONObject r7) throws org.json.JSONException, java.io.IOException {
        /*
        r6 = this;
        r0 = "event";
        r0 = r7.getString(r0);
        r1 = r0.hashCode();
        r2 = "getPages";
        r3 = 3;
        r4 = 2;
        r5 = 1;
        switch(r1) {
            case 530405532: goto L_0x002f;
            case 951351530: goto L_0x0025;
            case 1328613653: goto L_0x001b;
            case 1962251790: goto L_0x0013;
            default: goto L_0x0012;
        };
    L_0x0012:
        goto L_0x0039;
    L_0x0013:
        r1 = r0.equals(r2);
        if (r1 == 0) goto L_0x0039;
    L_0x0019:
        r1 = 0;
        goto L_0x003a;
    L_0x001b:
        r1 = "wrappedEvent";
        r1 = r0.equals(r1);
        if (r1 == 0) goto L_0x0039;
    L_0x0023:
        r1 = 1;
        goto L_0x003a;
    L_0x0025:
        r1 = "connect";
        r1 = r0.equals(r1);
        if (r1 == 0) goto L_0x0039;
    L_0x002d:
        r1 = 2;
        goto L_0x003a;
    L_0x002f:
        r1 = "disconnect";
        r1 = r0.equals(r1);
        if (r1 == 0) goto L_0x0039;
    L_0x0037:
        r1 = 3;
        goto L_0x003a;
    L_0x0039:
        r1 = -1;
    L_0x003a:
        if (r1 == 0) goto L_0x0073;
    L_0x003c:
        r2 = "payload";
        if (r1 == r5) goto L_0x006b;
    L_0x0040:
        if (r1 == r4) goto L_0x0063;
    L_0x0042:
        if (r1 != r3) goto L_0x004c;
    L_0x0044:
        r7 = r7.getJSONObject(r2);
        r6.handleDisconnect(r7);
        goto L_0x007a;
    L_0x004c:
        r7 = new java.lang.IllegalArgumentException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Unknown event: ";
        r1.append(r2);
        r1.append(r0);
        r0 = r1.toString();
        r7.<init>(r0);
        throw r7;
    L_0x0063:
        r7 = r7.getJSONObject(r2);
        r6.handleConnect(r7);
        goto L_0x007a;
    L_0x006b:
        r7 = r7.getJSONObject(r2);
        r6.handleWrappedEvent(r7);
        goto L_0x007a;
    L_0x0073:
        r7 = r6.getPages();
        r6.sendEvent(r2, r7);
    L_0x007a:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.devsupport.InspectorPackagerConnection.handleProxyMessage(org.json.JSONObject):void");
    }

    void closeAllConnections() {
        for (Entry value : this.mInspectorConnections.entrySet()) {
            ((LocalConnection) value.getValue()).disconnect();
        }
        this.mInspectorConnections.clear();
    }

    private void handleConnect(JSONObject jSONObject) throws JSONException {
        StringBuilder stringBuilder;
        final String string = jSONObject.getString("pageId");
        if (((LocalConnection) this.mInspectorConnections.remove(string)) == null) {
            try {
                this.mInspectorConnections.put(string, Inspector.connect(Integer.parseInt(string), new RemoteConnection() {
                    public void onMessage(String str) {
                        try {
                            InspectorPackagerConnection.this.sendWrappedEvent(string, str);
                        } catch (Throwable e) {
                            FLog.w(InspectorPackagerConnection.TAG, "Couldn't send event to packager", e);
                        }
                    }

                    public void onDisconnect() {
                        try {
                            InspectorPackagerConnection.this.mInspectorConnections.remove(string);
                            InspectorPackagerConnection.this.sendEvent("disconnect", InspectorPackagerConnection.this.makePageIdPayload(string));
                        } catch (Throwable e) {
                            FLog.w(InspectorPackagerConnection.TAG, "Couldn't send event to packager", e);
                        }
                    }
                }));
                return;
            } catch (Throwable e) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to open page: ");
                stringBuilder.append(string);
                FLog.w(TAG, stringBuilder.toString(), e);
                sendEvent("disconnect", makePageIdPayload(string));
                return;
            }
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Already connected: ");
        stringBuilder.append(string);
        throw new IllegalStateException(stringBuilder.toString());
    }

    private void handleDisconnect(JSONObject jSONObject) throws JSONException {
        LocalConnection localConnection = (LocalConnection) this.mInspectorConnections.remove(jSONObject.getString("pageId"));
        if (localConnection != null) {
            localConnection.disconnect();
        }
    }

    private void handleWrappedEvent(JSONObject jSONObject) throws JSONException {
        String string = jSONObject.getString("pageId");
        String string2 = jSONObject.getString("wrappedEvent");
        LocalConnection localConnection = (LocalConnection) this.mInspectorConnections.get(string);
        if (localConnection != null) {
            localConnection.sendMessage(string2);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not connected: ");
        stringBuilder.append(string);
        throw new IllegalStateException(stringBuilder.toString());
    }

    private JSONArray getPages() throws JSONException {
        List<Page> pages = Inspector.getPages();
        JSONArray jSONArray = new JSONArray();
        BundleStatus bundleStatus = this.mBundleStatusProvider.getBundleStatus();
        for (Page page : pages) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", String.valueOf(page.getId()));
            jSONObject.put("title", page.getTitle());
            jSONObject.put("app", this.mPackageName);
            jSONObject.put("vm", page.getVM());
            jSONObject.put("isLastBundleDownloadSuccess", bundleStatus.isLastDownloadSucess);
            jSONObject.put("bundleUpdateTimestamp", bundleStatus.updateTimestamp);
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    private void sendWrappedEvent(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("pageId", str);
        str = "wrappedEvent";
        jSONObject.put(str, str2);
        sendEvent(str, jSONObject);
    }

    private void sendEvent(String str, Object obj) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NotificationCompat.CATEGORY_EVENT, str);
        jSONObject.put("payload", obj);
        this.mConnection.send(jSONObject);
    }

    private JSONObject makePageIdPayload(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("pageId", str);
        return jSONObject;
    }
}
