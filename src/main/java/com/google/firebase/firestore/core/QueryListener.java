package com.google.firebase.firestore.core;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.core.DocumentViewChange.Type;
import com.google.firebase.firestore.core.EventManager.ListenOptions;
import com.google.firebase.firestore.util.Assert;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class QueryListener {
    private final EventListener<ViewSnapshot> listener;
    private OnlineState onlineState = OnlineState.UNKNOWN;
    private final ListenOptions options;
    private final Query query;
    private boolean raisedInitialEvent = false;
    @Nullable
    private ViewSnapshot snapshot;

    public QueryListener(Query query, ListenOptions listenOptions, EventListener<ViewSnapshot> eventListener) {
        this.query = query;
        this.listener = eventListener;
        this.options = listenOptions;
    }

    public Query getQuery() {
        return this.query;
    }

    public void onViewSnapshot(ViewSnapshot viewSnapshot) {
        boolean z = !viewSnapshot.getChanges().isEmpty() || viewSnapshot.didSyncStateChange();
        Assert.hardAssert(z, "We got a new snapshot with no changes?", new Object[0]);
        if (!this.options.includeDocumentMetadataChanges) {
            List arrayList = new ArrayList();
            for (DocumentViewChange documentViewChange : viewSnapshot.getChanges()) {
                if (documentViewChange.getType() != Type.METADATA) {
                    arrayList.add(documentViewChange);
                }
            }
            viewSnapshot = new ViewSnapshot(viewSnapshot.getQuery(), viewSnapshot.getDocuments(), viewSnapshot.getOldDocuments(), arrayList, viewSnapshot.isFromCache(), viewSnapshot.getMutatedKeys(), viewSnapshot.didSyncStateChange(), true);
        }
        if (this.raisedInitialEvent) {
            if (shouldRaiseEvent(viewSnapshot)) {
                this.listener.onEvent(viewSnapshot, null);
            }
        } else if (shouldRaiseInitialEvent(viewSnapshot, this.onlineState)) {
            raiseInitialEvent(viewSnapshot);
        }
        this.snapshot = viewSnapshot;
    }

    public void onError(FirebaseFirestoreException firebaseFirestoreException) {
        this.listener.onEvent(null, firebaseFirestoreException);
    }

    public void onOnlineStateChanged(OnlineState onlineState) {
        this.onlineState = onlineState;
        ViewSnapshot viewSnapshot = this.snapshot;
        if (viewSnapshot != null && !this.raisedInitialEvent && shouldRaiseInitialEvent(viewSnapshot, onlineState)) {
            raiseInitialEvent(this.snapshot);
        }
    }

    private boolean shouldRaiseInitialEvent(ViewSnapshot viewSnapshot, OnlineState onlineState) {
        boolean z = true;
        Assert.hardAssert(this.raisedInitialEvent ^ true, "Determining whether to raise first event but already had first event.", new Object[0]);
        if (!viewSnapshot.isFromCache()) {
            return true;
        }
        int equals = onlineState.equals(OnlineState.OFFLINE) ^ 1;
        if (!this.options.waitForSyncWhenOnline || equals == 0) {
            if (viewSnapshot.getDocuments().isEmpty() && !onlineState.equals(OnlineState.OFFLINE)) {
                z = false;
            }
            return z;
        }
        Assert.hardAssert(viewSnapshot.isFromCache(), "Waiting for sync, but snapshot is not from cache", new Object[0]);
        return false;
    }

    private boolean shouldRaiseEvent(ViewSnapshot viewSnapshot) {
        boolean z = true;
        if (!viewSnapshot.getChanges().isEmpty()) {
            return true;
        }
        ViewSnapshot viewSnapshot2 = this.snapshot;
        if (viewSnapshot2 == null || viewSnapshot2.hasPendingWrites() == viewSnapshot.hasPendingWrites()) {
            z = false;
        }
        if (viewSnapshot.didSyncStateChange() || z) {
            return this.options.includeQueryMetadataChanges;
        }
        return false;
    }

    private void raiseInitialEvent(ViewSnapshot viewSnapshot) {
        Assert.hardAssert(this.raisedInitialEvent ^ true, "Trying to raise initial event for second time", new Object[0]);
        viewSnapshot = ViewSnapshot.fromInitialDocuments(viewSnapshot.getQuery(), viewSnapshot.getDocuments(), viewSnapshot.getMutatedKeys(), viewSnapshot.isFromCache(), viewSnapshot.excludesMetadataChanges());
        this.raisedInitialEvent = true;
        this.listener.onEvent(viewSnapshot, null);
    }
}
