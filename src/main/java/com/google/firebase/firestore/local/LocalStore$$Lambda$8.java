package com.google.firebase.firestore.local;

import com.google.firebase.firestore.core.Query;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class LocalStore$$Lambda$8 implements Runnable {
    private final LocalStore arg$1;
    private final AllocateQueryHolder arg$2;
    private final Query arg$3;

    private LocalStore$$Lambda$8(LocalStore localStore, AllocateQueryHolder allocateQueryHolder, Query query) {
        this.arg$1 = localStore;
        this.arg$2 = allocateQueryHolder;
        this.arg$3 = query;
    }

    public static Runnable lambdaFactory$(LocalStore localStore, AllocateQueryHolder allocateQueryHolder, Query query) {
        return new LocalStore$$Lambda$8(localStore, allocateQueryHolder, query);
    }

    public void run() {
        LocalStore.lambda$allocateQuery$7(this.arg$1, this.arg$2, this.arg$3);
    }
}
