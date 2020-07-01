package com.google.firebase.firestore.core;

import com.google.firebase.firestore.util.Util;
import io.grpc.Status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class EventManager implements SyncEngineCallback {
    private OnlineState onlineState = OnlineState.UNKNOWN;
    private final Map<Query, QueryListenersInfo> queries;
    private final SyncEngine syncEngine;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static class ListenOptions {
        public boolean includeDocumentMetadataChanges;
        public boolean includeQueryMetadataChanges;
        public boolean waitForSyncWhenOnline;
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    private static class QueryListenersInfo {
        private final List<QueryListener> listeners = new ArrayList();
        private int targetId;
        private ViewSnapshot viewSnapshot;

        QueryListenersInfo() {
        }
    }

    public EventManager(SyncEngine syncEngine) {
        this.syncEngine = syncEngine;
        this.queries = new HashMap();
        syncEngine.setCallback(this);
    }

    public int addQueryListener(QueryListener queryListener) {
        Query query = queryListener.getQuery();
        QueryListenersInfo queryListenersInfo = (QueryListenersInfo) this.queries.get(query);
        Object obj = queryListenersInfo == null ? 1 : null;
        if (obj != null) {
            queryListenersInfo = new QueryListenersInfo();
            this.queries.put(query, queryListenersInfo);
        }
        queryListenersInfo.listeners.add(queryListener);
        queryListener.onOnlineStateChanged(this.onlineState);
        if (queryListenersInfo.viewSnapshot != null) {
            queryListener.onViewSnapshot(queryListenersInfo.viewSnapshot);
        }
        if (obj != null) {
            queryListenersInfo.targetId = this.syncEngine.listen(query);
        }
        return queryListenersInfo.targetId;
    }

    public boolean removeQueryListener(QueryListener queryListener) {
        boolean isEmpty;
        Query query = queryListener.getQuery();
        QueryListenersInfo queryListenersInfo = (QueryListenersInfo) this.queries.get(query);
        boolean z = false;
        if (queryListenersInfo != null) {
            z = queryListenersInfo.listeners.remove(queryListener);
            isEmpty = queryListenersInfo.listeners.isEmpty();
        } else {
            isEmpty = false;
        }
        if (isEmpty) {
            this.queries.remove(query);
            this.syncEngine.stopListening(query);
        }
        return z;
    }

    public void onViewSnapshots(List<ViewSnapshot> list) {
        for (ViewSnapshot viewSnapshot : list) {
            QueryListenersInfo queryListenersInfo = (QueryListenersInfo) this.queries.get(viewSnapshot.getQuery());
            if (queryListenersInfo != null) {
                for (QueryListener onViewSnapshot : queryListenersInfo.listeners) {
                    onViewSnapshot.onViewSnapshot(viewSnapshot);
                }
                queryListenersInfo.viewSnapshot = viewSnapshot;
            }
        }
    }

    public void onError(Query query, Status status) {
        QueryListenersInfo queryListenersInfo = (QueryListenersInfo) this.queries.get(query);
        if (queryListenersInfo != null) {
            for (QueryListener onError : queryListenersInfo.listeners) {
                onError.onError(Util.exceptionFromStatus(status));
            }
        }
        this.queries.remove(query);
    }

    public void handleOnlineStateChange(OnlineState onlineState) {
        this.onlineState = onlineState;
        for (QueryListenersInfo access$000 : this.queries.values()) {
            for (QueryListener onOnlineStateChanged : access$000.listeners) {
                onOnlineStateChanged.onOnlineStateChanged(onlineState);
            }
        }
    }
}
