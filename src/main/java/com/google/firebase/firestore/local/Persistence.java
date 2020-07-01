package com.google.firebase.firestore.local;

import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.util.Supplier;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public abstract class Persistence {
    public static boolean INDEXING_SUPPORT_ENABLED = false;
    static final String TAG = "Persistence";

    abstract IndexManager getIndexManager();

    abstract MutationQueue getMutationQueue(User user);

    abstract QueryCache getQueryCache();

    abstract ReferenceDelegate getReferenceDelegate();

    abstract RemoteDocumentCache getRemoteDocumentCache();

    public abstract boolean isStarted();

    abstract <T> T runTransaction(String str, Supplier<T> supplier);

    abstract void runTransaction(String str, Runnable runnable);

    public abstract void shutdown();

    public abstract void start();

    Persistence() {
    }
}
