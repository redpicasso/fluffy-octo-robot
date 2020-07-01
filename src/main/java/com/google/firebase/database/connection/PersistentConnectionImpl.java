package com.google.firebase.database.connection;

import androidx.core.app.NotificationCompat;
import com.google.firebase.database.connection.Connection.Delegate;
import com.google.firebase.database.connection.Connection.DisconnectReason;
import com.google.firebase.database.connection.ConnectionAuthTokenProvider.GetTokenCallback;
import com.google.firebase.database.connection.util.RetryHelper;
import com.google.firebase.database.connection.util.RetryHelper.Builder;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.logging.Logger;
import com.google.firebase.database.util.GAuthToken;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class PersistentConnectionImpl implements Delegate, PersistentConnection {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String IDLE_INTERRUPT_REASON = "connection_idle";
    private static final long IDLE_TIMEOUT = 60000;
    private static final long INVALID_AUTH_TOKEN_THRESHOLD = 3;
    private static final String REQUEST_ACTION = "a";
    private static final String REQUEST_ACTION_AUTH = "auth";
    private static final String REQUEST_ACTION_GAUTH = "gauth";
    private static final String REQUEST_ACTION_MERGE = "m";
    private static final String REQUEST_ACTION_ONDISCONNECT_CANCEL = "oc";
    private static final String REQUEST_ACTION_ONDISCONNECT_MERGE = "om";
    private static final String REQUEST_ACTION_ONDISCONNECT_PUT = "o";
    private static final String REQUEST_ACTION_PUT = "p";
    private static final String REQUEST_ACTION_QUERY = "q";
    private static final String REQUEST_ACTION_QUERY_UNLISTEN = "n";
    private static final String REQUEST_ACTION_STATS = "s";
    private static final String REQUEST_ACTION_UNAUTH = "unauth";
    private static final String REQUEST_AUTHVAR = "authvar";
    private static final String REQUEST_COMPOUND_HASH = "ch";
    private static final String REQUEST_COMPOUND_HASH_HASHES = "hs";
    private static final String REQUEST_COMPOUND_HASH_PATHS = "ps";
    private static final String REQUEST_COUNTERS = "c";
    private static final String REQUEST_CREDENTIAL = "cred";
    private static final String REQUEST_DATA_HASH = "h";
    private static final String REQUEST_DATA_PAYLOAD = "d";
    private static final String REQUEST_ERROR = "error";
    private static final String REQUEST_NUMBER = "r";
    private static final String REQUEST_PATH = "p";
    private static final String REQUEST_PAYLOAD = "b";
    private static final String REQUEST_QUERIES = "q";
    private static final String REQUEST_STATUS = "s";
    private static final String REQUEST_TAG = "t";
    private static final String RESPONSE_FOR_REQUEST = "b";
    private static final String SERVER_ASYNC_ACTION = "a";
    private static final String SERVER_ASYNC_AUTH_REVOKED = "ac";
    private static final String SERVER_ASYNC_DATA_MERGE = "m";
    private static final String SERVER_ASYNC_DATA_RANGE_MERGE = "rm";
    private static final String SERVER_ASYNC_DATA_UPDATE = "d";
    private static final String SERVER_ASYNC_LISTEN_CANCELLED = "c";
    private static final String SERVER_ASYNC_PAYLOAD = "b";
    private static final String SERVER_ASYNC_SECURITY_DEBUG = "sd";
    private static final String SERVER_DATA_END_PATH = "e";
    private static final String SERVER_DATA_RANGE_MERGE = "m";
    private static final String SERVER_DATA_START_PATH = "s";
    private static final String SERVER_DATA_TAG = "t";
    private static final String SERVER_DATA_UPDATE_BODY = "d";
    private static final String SERVER_DATA_UPDATE_PATH = "p";
    private static final String SERVER_DATA_WARNINGS = "w";
    private static final String SERVER_KILL_INTERRUPT_REASON = "server_kill";
    private static final String SERVER_RESPONSE_DATA = "d";
    private static final long SUCCESSFUL_CONNECTION_ESTABLISHED_DELAY = 30000;
    private static final String TOKEN_REFRESH_INTERRUPT_REASON = "token_refresh";
    private static long connectionIds;
    private String authToken;
    private final ConnectionAuthTokenProvider authTokenProvider;
    private String cachedHost;
    private ConnectionState connectionState = ConnectionState.Disconnected;
    private final ConnectionContext context;
    private long currentGetTokenAttempt = 0;
    private final PersistentConnection.Delegate delegate;
    private final ScheduledExecutorService executorService;
    private boolean firstConnection = true;
    private boolean forceAuthTokenRefresh;
    private boolean hasOnDisconnects;
    private final HostInfo hostInfo;
    private ScheduledFuture<?> inactivityTimer = null;
    private HashSet<String> interruptReasons = new HashSet();
    private int invalidAuthTokenCount = 0;
    private long lastConnectionEstablishedTime;
    private String lastSessionId;
    private long lastWriteTimestamp;
    private Map<ListenQuerySpec, OutstandingListen> listens;
    private final LogWrapper logger;
    private List<OutstandingDisconnect> onDisconnectRequestQueue;
    private Map<Long, OutstandingPut> outstandingPuts;
    private Connection realtime;
    private Map<Long, ConnectionRequestCallback> requestCBHash;
    private long requestCounter = 0;
    private final RetryHelper retryHelper;
    private long writeCounter = 0;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private interface ConnectionRequestCallback {
        void onResponse(Map<String, Object> map);
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private enum ConnectionState {
        Disconnected,
        GettingToken,
        Connecting,
        Authenticating,
        Connected
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private static class ListenQuerySpec {
        private final List<String> path;
        private final Map<String, Object> queryParams;

        public ListenQuerySpec(List<String> list, Map<String, Object> map) {
            this.path = list;
            this.queryParams = map;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ListenQuerySpec)) {
                return false;
            }
            ListenQuerySpec listenQuerySpec = (ListenQuerySpec) obj;
            if (this.path.equals(listenQuerySpec.path)) {
                return this.queryParams.equals(listenQuerySpec.queryParams);
            }
            return false;
        }

        public int hashCode() {
            return (this.path.hashCode() * 31) + this.queryParams.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ConnectionUtils.pathToString(this.path));
            stringBuilder.append(" (params: ");
            stringBuilder.append(this.queryParams);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private static class OutstandingDisconnect {
        private final String action;
        private final Object data;
        private final RequestResultCallback onComplete;
        private final List<String> path;

        /* synthetic */ OutstandingDisconnect(String str, List list, Object obj, RequestResultCallback requestResultCallback, AnonymousClass1 anonymousClass1) {
            this(str, list, obj, requestResultCallback);
        }

        private OutstandingDisconnect(String str, List<String> list, Object obj, RequestResultCallback requestResultCallback) {
            this.action = str;
            this.path = list;
            this.data = obj;
            this.onComplete = requestResultCallback;
        }

        public String getAction() {
            return this.action;
        }

        public List<String> getPath() {
            return this.path;
        }

        public Object getData() {
            return this.data;
        }

        public RequestResultCallback getOnComplete() {
            return this.onComplete;
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private static class OutstandingListen {
        private final ListenHashProvider hashFunction;
        private final ListenQuerySpec query;
        private final RequestResultCallback resultCallback;
        private final Long tag;

        /* synthetic */ OutstandingListen(RequestResultCallback requestResultCallback, ListenQuerySpec listenQuerySpec, Long l, ListenHashProvider listenHashProvider, AnonymousClass1 anonymousClass1) {
            this(requestResultCallback, listenQuerySpec, l, listenHashProvider);
        }

        private OutstandingListen(RequestResultCallback requestResultCallback, ListenQuerySpec listenQuerySpec, Long l, ListenHashProvider listenHashProvider) {
            this.resultCallback = requestResultCallback;
            this.query = listenQuerySpec;
            this.hashFunction = listenHashProvider;
            this.tag = l;
        }

        public ListenQuerySpec getQuery() {
            return this.query;
        }

        public Long getTag() {
            return this.tag;
        }

        public ListenHashProvider getHashFunction() {
            return this.hashFunction;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.query.toString());
            stringBuilder.append(" (Tag: ");
            stringBuilder.append(this.tag);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private static class OutstandingPut {
        private String action;
        private RequestResultCallback onComplete;
        private Map<String, Object> request;
        private boolean sent;

        /* synthetic */ OutstandingPut(String str, Map map, RequestResultCallback requestResultCallback, AnonymousClass1 anonymousClass1) {
            this(str, map, requestResultCallback);
        }

        private OutstandingPut(String str, Map<String, Object> map, RequestResultCallback requestResultCallback) {
            this.action = str;
            this.request = map;
            this.onComplete = requestResultCallback;
        }

        public String getAction() {
            return this.action;
        }

        public Map<String, Object> getRequest() {
            return this.request;
        }

        public RequestResultCallback getOnComplete() {
            return this.onComplete;
        }

        public void markSent() {
            this.sent = true;
        }

        public boolean wasSent() {
            return this.sent;
        }
    }

    public PersistentConnectionImpl(ConnectionContext connectionContext, HostInfo hostInfo, PersistentConnection.Delegate delegate) {
        this.delegate = delegate;
        this.context = connectionContext;
        this.executorService = connectionContext.getExecutorService();
        this.authTokenProvider = connectionContext.getAuthTokenProvider();
        this.hostInfo = hostInfo;
        this.listens = new HashMap();
        this.requestCBHash = new HashMap();
        this.outstandingPuts = new HashMap();
        this.onDisconnectRequestQueue = new ArrayList();
        this.retryHelper = new Builder(this.executorService, connectionContext.getLogger(), "ConnectionRetryHelper").withMinDelayAfterFailure(1000).withRetryExponent(1.3d).withMaxDelay(SUCCESSFUL_CONNECTION_ESTABLISHED_DELAY).withJitterFactor(0.7d).build();
        long j = connectionIds;
        connectionIds = 1 + j;
        Logger logger = connectionContext.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("pc_");
        stringBuilder.append(j);
        this.logger = new LogWrapper(logger, "PersistentConnection", stringBuilder.toString());
        this.lastSessionId = null;
        doIdleCheck();
    }

    public void onReady(long j, String str) {
        if (this.logger.logsDebug()) {
            this.logger.debug("onReady", new Object[0]);
        }
        this.lastConnectionEstablishedTime = System.currentTimeMillis();
        handleTimestamp(j);
        if (this.firstConnection) {
            sendConnectStats();
        }
        restoreAuth();
        this.firstConnection = false;
        this.lastSessionId = str;
        this.delegate.onConnect();
    }

    public void onCacheHost(String str) {
        this.cachedHost = str;
    }

    public void listen(List<String> list, Map<String, Object> map, ListenHashProvider listenHashProvider, Long l, RequestResultCallback requestResultCallback) {
        LogWrapper logWrapper;
        StringBuilder stringBuilder;
        ListenQuerySpec listenQuerySpec = new ListenQuerySpec(list, map);
        if (this.logger.logsDebug()) {
            logWrapper = this.logger;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Listening on ");
            stringBuilder.append(listenQuerySpec);
            logWrapper.debug(stringBuilder.toString(), new Object[0]);
        }
        ConnectionUtils.hardAssert(this.listens.containsKey(listenQuerySpec) ^ 1, "listen() called twice for same QuerySpec.", new Object[0]);
        if (this.logger.logsDebug()) {
            logWrapper = this.logger;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Adding listen query: ");
            stringBuilder.append(listenQuerySpec);
            logWrapper.debug(stringBuilder.toString(), new Object[0]);
        }
        OutstandingListen outstandingListen = new OutstandingListen(requestResultCallback, listenQuerySpec, l, listenHashProvider, null);
        this.listens.put(listenQuerySpec, outstandingListen);
        if (connected()) {
            sendListen(outstandingListen);
        }
        doIdleCheck();
    }

    public void initialize() {
        tryScheduleReconnect();
    }

    public void shutdown() {
        interrupt("shutdown");
    }

    public void put(List<String> list, Object obj, RequestResultCallback requestResultCallback) {
        putInternal("p", list, obj, null, requestResultCallback);
    }

    public void compareAndPut(List<String> list, Object obj, String str, RequestResultCallback requestResultCallback) {
        putInternal("p", list, obj, str, requestResultCallback);
    }

    public void merge(List<String> list, Map<String, Object> map, RequestResultCallback requestResultCallback) {
        putInternal("m", list, map, null, requestResultCallback);
    }

    public void purgeOutstandingWrites() {
        String str;
        Iterator it = this.outstandingPuts.values().iterator();
        while (true) {
            str = "write_canceled";
            if (!it.hasNext()) {
                break;
            }
            OutstandingPut outstandingPut = (OutstandingPut) it.next();
            if (outstandingPut.onComplete != null) {
                outstandingPut.onComplete.onRequestResult(str, null);
            }
        }
        for (OutstandingDisconnect outstandingDisconnect : this.onDisconnectRequestQueue) {
            if (outstandingDisconnect.onComplete != null) {
                outstandingDisconnect.onComplete.onRequestResult(str, null);
            }
        }
        this.outstandingPuts.clear();
        this.onDisconnectRequestQueue.clear();
        if (!connected()) {
            this.hasOnDisconnects = false;
        }
        doIdleCheck();
    }

    public void onDataMessage(Map<String, Object> map) {
        String str = REQUEST_NUMBER;
        String str2 = "b";
        if (map.containsKey(str)) {
            ConnectionRequestCallback connectionRequestCallback = (ConnectionRequestCallback) this.requestCBHash.remove(Long.valueOf((long) ((Integer) map.get(str)).intValue()));
            if (connectionRequestCallback != null) {
                connectionRequestCallback.onResponse((Map) map.get(str2));
            }
        } else if (!map.containsKey("error")) {
            str = "a";
            if (map.containsKey(str)) {
                onDataPush((String) map.get(str), (Map) map.get(str2));
            } else if (this.logger.logsDebug()) {
                LogWrapper logWrapper = this.logger;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Ignoring unknown message: ");
                stringBuilder.append(map);
                logWrapper.debug(stringBuilder.toString(), new Object[0]);
            }
        }
    }

    public void onDisconnect(DisconnectReason disconnectReason) {
        boolean z = false;
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Got on disconnect due to ");
            stringBuilder.append(disconnectReason.name());
            logWrapper.debug(stringBuilder.toString(), new Object[0]);
        }
        this.connectionState = ConnectionState.Disconnected;
        this.realtime = null;
        this.hasOnDisconnects = false;
        this.requestCBHash.clear();
        cancelSentTransactions();
        if (shouldReconnect()) {
            long currentTimeMillis = System.currentTimeMillis();
            long j = this.lastConnectionEstablishedTime;
            currentTimeMillis -= j;
            if (j > 0 && currentTimeMillis > SUCCESSFUL_CONNECTION_ESTABLISHED_DELAY) {
                z = true;
            }
            if (disconnectReason == DisconnectReason.SERVER_RESET || z) {
                this.retryHelper.signalSuccess();
            }
            tryScheduleReconnect();
        }
        this.lastConnectionEstablishedTime = 0;
        this.delegate.onDisconnect();
    }

    public void onKill(String str) {
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Firebase Database connection was forcefully killed by the server. Will not attempt reconnect. Reason: ");
            stringBuilder.append(str);
            logWrapper.debug(stringBuilder.toString(), new Object[0]);
        }
        interrupt(SERVER_KILL_INTERRUPT_REASON);
    }

    public void unlisten(List<String> list, Map<String, Object> map) {
        ListenQuerySpec listenQuerySpec = new ListenQuerySpec(list, map);
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unlistening on ");
            stringBuilder.append(listenQuerySpec);
            logWrapper.debug(stringBuilder.toString(), new Object[0]);
        }
        OutstandingListen removeListen = removeListen(listenQuerySpec);
        if (removeListen != null && connected()) {
            sendUnlisten(removeListen);
        }
        doIdleCheck();
    }

    private boolean connected() {
        return this.connectionState == ConnectionState.Authenticating || this.connectionState == ConnectionState.Connected;
    }

    public void onDisconnectPut(List<String> list, Object obj, RequestResultCallback requestResultCallback) {
        this.hasOnDisconnects = true;
        if (canSendWrites()) {
            sendOnDisconnect(REQUEST_ACTION_ONDISCONNECT_PUT, list, obj, requestResultCallback);
        } else {
            this.onDisconnectRequestQueue.add(new OutstandingDisconnect(REQUEST_ACTION_ONDISCONNECT_PUT, list, obj, requestResultCallback, null));
        }
        doIdleCheck();
    }

    private boolean canSendWrites() {
        return this.connectionState == ConnectionState.Connected;
    }

    public void onDisconnectMerge(List<String> list, Map<String, Object> map, RequestResultCallback requestResultCallback) {
        this.hasOnDisconnects = true;
        if (canSendWrites()) {
            sendOnDisconnect(REQUEST_ACTION_ONDISCONNECT_MERGE, list, map, requestResultCallback);
        } else {
            this.onDisconnectRequestQueue.add(new OutstandingDisconnect(REQUEST_ACTION_ONDISCONNECT_MERGE, list, map, requestResultCallback, null));
        }
        doIdleCheck();
    }

    public void onDisconnectCancel(List<String> list, RequestResultCallback requestResultCallback) {
        if (canSendWrites()) {
            sendOnDisconnect(REQUEST_ACTION_ONDISCONNECT_CANCEL, list, null, requestResultCallback);
        } else {
            this.onDisconnectRequestQueue.add(new OutstandingDisconnect(REQUEST_ACTION_ONDISCONNECT_CANCEL, list, null, requestResultCallback, null));
        }
        doIdleCheck();
    }

    public void interrupt(String str) {
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Connection interrupted for: ");
            stringBuilder.append(str);
            logWrapper.debug(stringBuilder.toString(), new Object[0]);
        }
        this.interruptReasons.add(str);
        Connection connection = this.realtime;
        if (connection != null) {
            connection.close();
            this.realtime = null;
        } else {
            this.retryHelper.cancel();
            this.connectionState = ConnectionState.Disconnected;
        }
        this.retryHelper.signalSuccess();
    }

    public void resume(String str) {
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Connection no longer interrupted for: ");
            stringBuilder.append(str);
            logWrapper.debug(stringBuilder.toString(), new Object[0]);
        }
        this.interruptReasons.remove(str);
        if (shouldReconnect() && this.connectionState == ConnectionState.Disconnected) {
            tryScheduleReconnect();
        }
    }

    public boolean isInterrupted(String str) {
        return this.interruptReasons.contains(str);
    }

    boolean shouldReconnect() {
        return this.interruptReasons.size() == 0;
    }

    public void refreshAuthToken() {
        this.logger.debug("Auth token refresh requested", new Object[0]);
        String str = TOKEN_REFRESH_INTERRUPT_REASON;
        interrupt(str);
        resume(str);
    }

    public void refreshAuthToken(String str) {
        this.logger.debug("Auth token refreshed.", new Object[0]);
        this.authToken = str;
        if (!connected()) {
            return;
        }
        if (str != null) {
            upgradeAuth();
        } else {
            sendUnauth();
        }
    }

    private void tryScheduleReconnect() {
        if (shouldReconnect()) {
            ConnectionUtils.hardAssert(this.connectionState == ConnectionState.Disconnected, "Not in disconnected state: %s", this.connectionState);
            final boolean z = this.forceAuthTokenRefresh;
            this.logger.debug("Scheduling connection attempt", new Object[0]);
            this.forceAuthTokenRefresh = false;
            this.retryHelper.retry(new Runnable() {
                public void run() {
                    PersistentConnectionImpl.this.logger.debug("Trying to fetch auth token", new Object[0]);
                    ConnectionUtils.hardAssert(PersistentConnectionImpl.this.connectionState == ConnectionState.Disconnected, "Not in disconnected state: %s", PersistentConnectionImpl.this.connectionState);
                    PersistentConnectionImpl.this.connectionState = ConnectionState.GettingToken;
                    PersistentConnectionImpl.this.currentGetTokenAttempt = 1 + PersistentConnectionImpl.this.currentGetTokenAttempt;
                    final long access$600 = PersistentConnectionImpl.this.currentGetTokenAttempt;
                    PersistentConnectionImpl.this.authTokenProvider.getToken(z, new GetTokenCallback() {
                        public void onSuccess(String str) {
                            if (access$600 != PersistentConnectionImpl.this.currentGetTokenAttempt) {
                                PersistentConnectionImpl.this.logger.debug("Ignoring getToken result, because this was not the latest attempt.", new Object[0]);
                            } else if (PersistentConnectionImpl.this.connectionState == ConnectionState.GettingToken) {
                                PersistentConnectionImpl.this.logger.debug("Successfully fetched token, opening connection", new Object[0]);
                                PersistentConnectionImpl.this.openNetworkConnection(str);
                            } else {
                                ConnectionUtils.hardAssert(PersistentConnectionImpl.this.connectionState == ConnectionState.Disconnected, "Expected connection state disconnected, but was %s", PersistentConnectionImpl.this.connectionState);
                                PersistentConnectionImpl.this.logger.debug("Not opening connection after token refresh, because connection was set to disconnected", new Object[0]);
                            }
                        }

                        public void onError(String str) {
                            if (access$600 == PersistentConnectionImpl.this.currentGetTokenAttempt) {
                                PersistentConnectionImpl.this.connectionState = ConnectionState.Disconnected;
                                LogWrapper access$400 = PersistentConnectionImpl.this.logger;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Error fetching token: ");
                                stringBuilder.append(str);
                                access$400.debug(stringBuilder.toString(), new Object[0]);
                                PersistentConnectionImpl.this.tryScheduleReconnect();
                                return;
                            }
                            PersistentConnectionImpl.this.logger.debug("Ignoring getToken error, because this was not the latest attempt.", new Object[0]);
                        }
                    });
                }
            });
        }
    }

    public void openNetworkConnection(String str) {
        ConnectionUtils.hardAssert(this.connectionState == ConnectionState.GettingToken, "Trying to open network connection while in the wrong state: %s", this.connectionState);
        if (str == null) {
            this.delegate.onAuthStatus(false);
        }
        this.authToken = str;
        this.connectionState = ConnectionState.Connecting;
        this.realtime = new Connection(this.context, this.hostInfo, this.cachedHost, this, this.lastSessionId);
        this.realtime.open();
    }

    private void sendOnDisconnect(String str, List<String> list, Object obj, final RequestResultCallback requestResultCallback) {
        Map hashMap = new HashMap();
        hashMap.put("p", ConnectionUtils.pathToString(list));
        hashMap.put("d", obj);
        sendAction(str, hashMap, new ConnectionRequestCallback() {
            public void onResponse(Map<String, Object> map) {
                String str = (String) map.get("s");
                String str2 = null;
                if (str.equals("ok")) {
                    str = null;
                } else {
                    str2 = (String) map.get("d");
                }
                RequestResultCallback requestResultCallback = requestResultCallback;
                if (requestResultCallback != null) {
                    requestResultCallback.onRequestResult(str, str2);
                }
            }
        });
    }

    private void cancelSentTransactions() {
        List<OutstandingPut> arrayList = new ArrayList();
        Iterator it = this.outstandingPuts.entrySet().iterator();
        while (it.hasNext()) {
            OutstandingPut outstandingPut = (OutstandingPut) ((Entry) it.next()).getValue();
            if (outstandingPut.getRequest().containsKey(REQUEST_DATA_HASH) && outstandingPut.wasSent()) {
                arrayList.add(outstandingPut);
                it.remove();
            }
        }
        for (OutstandingPut onComplete : arrayList) {
            onComplete.getOnComplete().onRequestResult("disconnected", null);
        }
    }

    private void sendUnlisten(OutstandingListen outstandingListen) {
        Map hashMap = new HashMap();
        hashMap.put("p", ConnectionUtils.pathToString(outstandingListen.query.path));
        Long tag = outstandingListen.getTag();
        if (tag != null) {
            hashMap.put("q", outstandingListen.getQuery().queryParams);
            hashMap.put("t", tag);
        }
        sendAction(REQUEST_ACTION_QUERY_UNLISTEN, hashMap, null);
    }

    private OutstandingListen removeListen(ListenQuerySpec listenQuerySpec) {
        LogWrapper logWrapper;
        StringBuilder stringBuilder;
        if (this.logger.logsDebug()) {
            logWrapper = this.logger;
            stringBuilder = new StringBuilder();
            stringBuilder.append("removing query ");
            stringBuilder.append(listenQuerySpec);
            logWrapper.debug(stringBuilder.toString(), new Object[0]);
        }
        if (this.listens.containsKey(listenQuerySpec)) {
            OutstandingListen outstandingListen = (OutstandingListen) this.listens.get(listenQuerySpec);
            this.listens.remove(listenQuerySpec);
            doIdleCheck();
            return outstandingListen;
        }
        if (this.logger.logsDebug()) {
            logWrapper = this.logger;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Trying to remove listener for QuerySpec ");
            stringBuilder.append(listenQuerySpec);
            stringBuilder.append(" but no listener exists.");
            logWrapper.debug(stringBuilder.toString(), new Object[0]);
        }
        return null;
    }

    private Collection<OutstandingListen> removeListens(List<String> list) {
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("removing all listens at path ");
            stringBuilder.append(list);
            logWrapper.debug(stringBuilder.toString(), new Object[0]);
        }
        Collection<OutstandingListen> arrayList = new ArrayList();
        for (Entry entry : this.listens.entrySet()) {
            ListenQuerySpec listenQuerySpec = (ListenQuerySpec) entry.getKey();
            OutstandingListen outstandingListen = (OutstandingListen) entry.getValue();
            if (listenQuerySpec.path.equals(list)) {
                arrayList.add(outstandingListen);
            }
        }
        for (OutstandingListen query : arrayList) {
            this.listens.remove(query.getQuery());
        }
        doIdleCheck();
        return arrayList;
    }

    private void onDataPush(String str, Map<String, Object> map) {
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("handleServerMessage: ");
            stringBuilder.append(str);
            stringBuilder.append(" ");
            stringBuilder.append(map);
            logWrapper.debug(stringBuilder.toString(), new Object[0]);
        }
        String str2 = "d";
        String str3 = "t";
        String str4 = "p";
        String str5 = "m";
        Object obj;
        Long longFromObject;
        if (str.equals(str2) || str.equals(str5)) {
            boolean equals = str.equals(str5);
            String str6 = (String) map.get(str4);
            obj = map.get(str2);
            longFromObject = ConnectionUtils.longFromObject(map.get(str3));
            if (!equals || !(obj instanceof Map) || ((Map) obj).size() != 0) {
                this.delegate.onDataUpdate(ConnectionUtils.stringToPath(str6), obj, equals, longFromObject);
                return;
            } else if (this.logger.logsDebug()) {
                LogWrapper logWrapper2 = this.logger;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("ignoring empty merge for path ");
                stringBuilder2.append(str6);
                logWrapper2.debug(stringBuilder2.toString(), new Object[0]);
                return;
            } else {
                return;
            }
        }
        String str7 = "s";
        LogWrapper logWrapper3;
        StringBuilder stringBuilder3;
        if (str.equals(SERVER_ASYNC_DATA_RANGE_MERGE)) {
            str = (String) map.get(str4);
            List stringToPath = ConnectionUtils.stringToPath(str);
            obj = map.get(str2);
            longFromObject = ConnectionUtils.longFromObject(map.get(str3));
            List<Map> list = (List) obj;
            List arrayList = new ArrayList();
            for (Map map2 : list) {
                String str8 = (String) map2.get(str7);
                String str9 = (String) map2.get(SERVER_DATA_END_PATH);
                List list2 = null;
                List stringToPath2 = str8 != null ? ConnectionUtils.stringToPath(str8) : null;
                if (str9 != null) {
                    list2 = ConnectionUtils.stringToPath(str9);
                }
                arrayList.add(new RangeMerge(stringToPath2, list2, map2.get(str5)));
            }
            if (!arrayList.isEmpty()) {
                this.delegate.onRangeMergeUpdate(stringToPath, arrayList, longFromObject);
            } else if (this.logger.logsDebug()) {
                logWrapper3 = this.logger;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("Ignoring empty range merge for path ");
                stringBuilder3.append(str);
                logWrapper3.debug(stringBuilder3.toString(), new Object[0]);
            }
        } else if (str.equals("c")) {
            onListenRevoked(ConnectionUtils.stringToPath((String) map.get(str4)));
        } else if (str.equals(SERVER_ASYNC_AUTH_REVOKED)) {
            onAuthRevoked((String) map.get(str7), (String) map.get(str2));
        } else if (str.equals(SERVER_ASYNC_SECURITY_DEBUG)) {
            onSecurityDebugPacket(map);
        } else if (this.logger.logsDebug()) {
            logWrapper3 = this.logger;
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Unrecognized action from server: ");
            stringBuilder3.append(str);
            logWrapper3.debug(stringBuilder3.toString(), new Object[0]);
        }
    }

    private void onListenRevoked(List<String> list) {
        Collection<OutstandingListen> removeListens = removeListens(list);
        if (removeListens != null) {
            for (OutstandingListen access$1200 : removeListens) {
                access$1200.resultCallback.onRequestResult("permission_denied", null);
            }
        }
    }

    private void onAuthRevoked(String str, String str2) {
        LogWrapper logWrapper = this.logger;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Auth token revoked: ");
        stringBuilder.append(str);
        stringBuilder.append(" (");
        stringBuilder.append(str2);
        stringBuilder.append(")");
        logWrapper.debug(stringBuilder.toString(), new Object[0]);
        this.authToken = null;
        this.forceAuthTokenRefresh = true;
        this.delegate.onAuthStatus(false);
        this.realtime.close();
    }

    private void onSecurityDebugPacket(Map<String, Object> map) {
        this.logger.info((String) map.get(NotificationCompat.CATEGORY_MESSAGE));
    }

    private void upgradeAuth() {
        sendAuthHelper(false);
    }

    private void sendAuthAndRestoreState() {
        sendAuthHelper(true);
    }

    private void sendAuthHelper(final boolean z) {
        ConnectionUtils.hardAssert(connected(), "Must be connected to send auth, but was: %s", this.connectionState);
        ConnectionUtils.hardAssert(this.authToken != null, "Auth token must be set to authenticate!", new Object[0]);
        ConnectionRequestCallback anonymousClass3 = new ConnectionRequestCallback() {
            public void onResponse(Map<String, Object> map) {
                PersistentConnectionImpl.this.connectionState = ConnectionState.Connected;
                String str = (String) map.get("s");
                if (str.equals("ok")) {
                    PersistentConnectionImpl.this.invalidAuthTokenCount = 0;
                    PersistentConnectionImpl.this.delegate.onAuthStatus(true);
                    if (z) {
                        PersistentConnectionImpl.this.restoreState();
                        return;
                    }
                    return;
                }
                PersistentConnectionImpl.this.authToken = null;
                PersistentConnectionImpl.this.forceAuthTokenRefresh = true;
                PersistentConnectionImpl.this.delegate.onAuthStatus(false);
                String str2 = (String) map.get("d");
                LogWrapper access$400 = PersistentConnectionImpl.this.logger;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Authentication failed: ");
                stringBuilder.append(str);
                stringBuilder.append(" (");
                stringBuilder.append(str2);
                stringBuilder.append(")");
                access$400.debug(stringBuilder.toString(), new Object[0]);
                PersistentConnectionImpl.this.realtime.close();
                if (str.equals("invalid_token")) {
                    PersistentConnectionImpl.this.invalidAuthTokenCount = PersistentConnectionImpl.this.invalidAuthTokenCount + 1;
                    if (((long) PersistentConnectionImpl.this.invalidAuthTokenCount) >= 3) {
                        PersistentConnectionImpl.this.retryHelper.setMaxDelay();
                        PersistentConnectionImpl.this.logger.warn("Provided authentication credentials are invalid. This usually indicates your FirebaseApp instance was not initialized correctly. Make sure your google-services.json file has the correct firebase_url and api_key. You can re-download google-services.json from https://console.firebase.google.com/.");
                    }
                }
            }
        };
        Map hashMap = new HashMap();
        GAuthToken tryParseFromString = GAuthToken.tryParseFromString(this.authToken);
        String str = REQUEST_CREDENTIAL;
        if (tryParseFromString != null) {
            hashMap.put(str, tryParseFromString.getToken());
            if (tryParseFromString.getAuth() != null) {
                hashMap.put(REQUEST_AUTHVAR, tryParseFromString.getAuth());
            }
            sendSensitive(REQUEST_ACTION_GAUTH, true, hashMap, anonymousClass3);
            return;
        }
        hashMap.put(str, this.authToken);
        sendSensitive(REQUEST_ACTION_AUTH, true, hashMap, anonymousClass3);
    }

    private void sendUnauth() {
        ConnectionUtils.hardAssert(connected(), "Must be connected to send unauth.", new Object[0]);
        ConnectionUtils.hardAssert(this.authToken == null, "Auth token must not be set.", new Object[0]);
        sendAction(REQUEST_ACTION_UNAUTH, Collections.emptyMap(), null);
    }

    private void restoreAuth() {
        if (this.logger.logsDebug()) {
            this.logger.debug("calling restore state", new Object[0]);
        }
        ConnectionUtils.hardAssert(this.connectionState == ConnectionState.Connecting, "Wanted to restore auth, but was in wrong state: %s", this.connectionState);
        if (this.authToken == null) {
            if (this.logger.logsDebug()) {
                this.logger.debug("Not restoring auth because token is null.", new Object[0]);
            }
            this.connectionState = ConnectionState.Connected;
            restoreState();
            return;
        }
        if (this.logger.logsDebug()) {
            this.logger.debug("Restoring auth.", new Object[0]);
        }
        this.connectionState = ConnectionState.Authenticating;
        sendAuthAndRestoreState();
    }

    private void restoreState() {
        ConnectionUtils.hardAssert(this.connectionState == ConnectionState.Connected, "Should be connected if we're restoring state, but we are: %s", this.connectionState);
        if (this.logger.logsDebug()) {
            this.logger.debug("Restoring outstanding listens", new Object[0]);
        }
        for (OutstandingListen outstandingListen : this.listens.values()) {
            if (this.logger.logsDebug()) {
                LogWrapper logWrapper = this.logger;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Restoring listen ");
                stringBuilder.append(outstandingListen.getQuery());
                logWrapper.debug(stringBuilder.toString(), new Object[0]);
            }
            sendListen(outstandingListen);
        }
        if (this.logger.logsDebug()) {
            this.logger.debug("Restoring writes.", new Object[0]);
        }
        Object arrayList = new ArrayList(this.outstandingPuts.keySet());
        Collections.sort(arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            sendPut(((Long) it.next()).longValue());
        }
        for (OutstandingDisconnect outstandingDisconnect : this.onDisconnectRequestQueue) {
            sendOnDisconnect(outstandingDisconnect.getAction(), outstandingDisconnect.getPath(), outstandingDisconnect.getData(), outstandingDisconnect.getOnComplete());
        }
        this.onDisconnectRequestQueue.clear();
    }

    private void handleTimestamp(long j) {
        if (this.logger.logsDebug()) {
            this.logger.debug("handling timestamp", new Object[0]);
        }
        j -= System.currentTimeMillis();
        Map hashMap = new HashMap();
        hashMap.put(Constants.DOT_INFO_SERVERTIME_OFFSET, Long.valueOf(j));
        this.delegate.onServerInfoUpdate(hashMap);
    }

    private Map<String, Object> getPutObject(List<String> list, Object obj, String str) {
        Map<String, Object> hashMap = new HashMap();
        hashMap.put("p", ConnectionUtils.pathToString(list));
        hashMap.put("d", obj);
        if (str != null) {
            hashMap.put(REQUEST_DATA_HASH, str);
        }
        return hashMap;
    }

    private void putInternal(String str, List<String> list, Object obj, String str2, RequestResultCallback requestResultCallback) {
        Map putObject = getPutObject(list, obj, str2);
        long j = this.writeCounter;
        this.writeCounter = 1 + j;
        this.outstandingPuts.put(Long.valueOf(j), new OutstandingPut(str, putObject, requestResultCallback, null));
        if (canSendWrites()) {
            sendPut(j);
        }
        this.lastWriteTimestamp = System.currentTimeMillis();
        doIdleCheck();
    }

    private void sendPut(long j) {
        final OutstandingPut outstandingPut = (OutstandingPut) this.outstandingPuts.get(Long.valueOf(j));
        final RequestResultCallback onComplete = outstandingPut.getOnComplete();
        String action = outstandingPut.getAction();
        outstandingPut.markSent();
        final String str = action;
        final long j2 = j;
        sendAction(action, outstandingPut.getRequest(), new ConnectionRequestCallback() {
            public void onResponse(Map<String, Object> map) {
                if (PersistentConnectionImpl.this.logger.logsDebug()) {
                    LogWrapper access$400 = PersistentConnectionImpl.this.logger;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(str);
                    stringBuilder.append(" response: ");
                    stringBuilder.append(map);
                    access$400.debug(stringBuilder.toString(), new Object[0]);
                }
                if (((OutstandingPut) PersistentConnectionImpl.this.outstandingPuts.get(Long.valueOf(j2))) == outstandingPut) {
                    PersistentConnectionImpl.this.outstandingPuts.remove(Long.valueOf(j2));
                    if (onComplete != null) {
                        String str = (String) map.get("s");
                        if (str.equals("ok")) {
                            onComplete.onRequestResult(null, null);
                        } else {
                            onComplete.onRequestResult(str, (String) map.get("d"));
                        }
                    }
                } else if (PersistentConnectionImpl.this.logger.logsDebug()) {
                    LogWrapper access$4002 = PersistentConnectionImpl.this.logger;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Ignoring on complete for put ");
                    stringBuilder2.append(j2);
                    stringBuilder2.append(" because it was removed already.");
                    access$4002.debug(stringBuilder2.toString(), new Object[0]);
                }
                PersistentConnectionImpl.this.doIdleCheck();
            }
        });
    }

    private void sendListen(final OutstandingListen outstandingListen) {
        Map hashMap = new HashMap();
        hashMap.put("p", ConnectionUtils.pathToString(outstandingListen.getQuery().path));
        Long tag = outstandingListen.getTag();
        String str = "q";
        if (tag != null) {
            hashMap.put(str, outstandingListen.query.queryParams);
            hashMap.put("t", tag);
        }
        ListenHashProvider hashFunction = outstandingListen.getHashFunction();
        hashMap.put(REQUEST_DATA_HASH, hashFunction.getSimpleHash());
        if (hashFunction.shouldIncludeCompoundHash()) {
            CompoundHash compoundHash = hashFunction.getCompoundHash();
            List arrayList = new ArrayList();
            for (List pathToString : compoundHash.getPosts()) {
                arrayList.add(ConnectionUtils.pathToString(pathToString));
            }
            Map hashMap2 = new HashMap();
            hashMap2.put(REQUEST_COMPOUND_HASH_HASHES, compoundHash.getHashes());
            hashMap2.put(REQUEST_COMPOUND_HASH_PATHS, arrayList);
            hashMap.put(REQUEST_COMPOUND_HASH, hashMap2);
        }
        sendAction(str, hashMap, new ConnectionRequestCallback() {
            public void onResponse(Map<String, Object> map) {
                String str = (String) map.get("s");
                String str2 = "ok";
                String str3 = "d";
                if (str.equals(str2)) {
                    Map map2 = (Map) map.get(str3);
                    String str4 = PersistentConnectionImpl.SERVER_DATA_WARNINGS;
                    if (map2.containsKey(str4)) {
                        PersistentConnectionImpl.this.warnOnListenerWarnings((List) map2.get(str4), outstandingListen.query);
                    }
                }
                if (((OutstandingListen) PersistentConnectionImpl.this.listens.get(outstandingListen.getQuery())) != outstandingListen) {
                    return;
                }
                if (str.equals(str2)) {
                    outstandingListen.resultCallback.onRequestResult(null, null);
                    return;
                }
                PersistentConnectionImpl.this.removeListen(outstandingListen.getQuery());
                outstandingListen.resultCallback.onRequestResult(str, (String) map.get(str3));
            }
        });
    }

    private void sendStats(Map<String, Integer> map) {
        if (!map.isEmpty()) {
            Map hashMap = new HashMap();
            hashMap.put("c", map);
            sendAction("s", hashMap, new ConnectionRequestCallback() {
                public void onResponse(Map<String, Object> map) {
                    String str = (String) map.get("s");
                    if (!str.equals("ok")) {
                        String str2 = (String) map.get("d");
                        if (PersistentConnectionImpl.this.logger.logsDebug()) {
                            LogWrapper access$400 = PersistentConnectionImpl.this.logger;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Failed to send stats: ");
                            stringBuilder.append(str);
                            stringBuilder.append(" (message: ");
                            stringBuilder.append(str2);
                            stringBuilder.append(")");
                            access$400.debug(stringBuilder.toString(), new Object[0]);
                        }
                    }
                }
            });
        } else if (this.logger.logsDebug()) {
            this.logger.debug("Not sending stats because stats are empty", new Object[0]);
        }
    }

    private void warnOnListenerWarnings(List<String> list, ListenQuerySpec listenQuerySpec) {
        if (list.contains("no_index")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\".indexOn\": \"");
            stringBuilder.append(listenQuerySpec.queryParams.get("i"));
            stringBuilder.append('\"');
            String stringBuilder2 = stringBuilder.toString();
            LogWrapper logWrapper = this.logger;
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Using an unspecified index. Your data will be downloaded and filtered on the client. Consider adding '");
            stringBuilder3.append(stringBuilder2);
            stringBuilder3.append("' at ");
            stringBuilder3.append(ConnectionUtils.pathToString(listenQuerySpec.path));
            stringBuilder3.append(" to your security and Firebase Database rules for better performance");
            logWrapper.warn(stringBuilder3.toString());
        }
    }

    private void sendConnectStats() {
        Map hashMap = new HashMap();
        boolean isPersistenceEnabled = this.context.isPersistenceEnabled();
        Integer valueOf = Integer.valueOf(1);
        if (isPersistenceEnabled) {
            hashMap.put("persistence.android.enabled", valueOf);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("sdk.android.");
        stringBuilder.append(this.context.getClientSdkVersion().replace('.', '-'));
        hashMap.put(stringBuilder.toString(), valueOf);
        if (this.logger.logsDebug()) {
            this.logger.debug("Sending first connection stats", new Object[0]);
        }
        sendStats(hashMap);
    }

    private void sendAction(String str, Map<String, Object> map, ConnectionRequestCallback connectionRequestCallback) {
        sendSensitive(str, false, map, connectionRequestCallback);
    }

    private void sendSensitive(String str, boolean z, Map<String, Object> map, ConnectionRequestCallback connectionRequestCallback) {
        long nextRequestNumber = nextRequestNumber();
        Map hashMap = new HashMap();
        hashMap.put(REQUEST_NUMBER, Long.valueOf(nextRequestNumber));
        hashMap.put("a", str);
        hashMap.put("b", map);
        this.realtime.sendRequest(hashMap, z);
        this.requestCBHash.put(Long.valueOf(nextRequestNumber), connectionRequestCallback);
    }

    private long nextRequestNumber() {
        long j = this.requestCounter;
        this.requestCounter = 1 + j;
        return j;
    }

    private void doIdleCheck() {
        if (isIdle()) {
            ScheduledFuture scheduledFuture = this.inactivityTimer;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
            this.inactivityTimer = this.executorService.schedule(new Runnable() {
                public void run() {
                    PersistentConnectionImpl.this.inactivityTimer = null;
                    if (PersistentConnectionImpl.this.idleHasTimedOut()) {
                        PersistentConnectionImpl.this.interrupt(PersistentConnectionImpl.IDLE_INTERRUPT_REASON);
                    } else {
                        PersistentConnectionImpl.this.doIdleCheck();
                    }
                }
            }, IDLE_TIMEOUT, TimeUnit.MILLISECONDS);
            return;
        }
        String str = IDLE_INTERRUPT_REASON;
        if (isInterrupted(str)) {
            ConnectionUtils.hardAssert(isIdle() ^ 1);
            resume(str);
        }
    }

    private boolean isIdle() {
        return this.listens.isEmpty() && this.requestCBHash.isEmpty() && !this.hasOnDisconnects && this.outstandingPuts.isEmpty();
    }

    private boolean idleHasTimedOut() {
        return isIdle() && System.currentTimeMillis() > this.lastWriteTimestamp + IDLE_TIMEOUT;
    }

    public void injectConnectionFailure() {
        Connection connection = this.realtime;
        if (connection != null) {
            connection.injectConnectionFailure();
        }
    }
}
