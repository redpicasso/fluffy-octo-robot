package com.google.firebase.firestore.local;

import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.local.LruGarbageCollector.Params;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Supplier;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class MemoryPersistence extends Persistence {
    private final MemoryIndexManager indexManager = new MemoryIndexManager();
    private final Map<User, MemoryMutationQueue> mutationQueues = new HashMap();
    private final MemoryQueryCache queryCache = new MemoryQueryCache(this);
    private ReferenceDelegate referenceDelegate;
    private final MemoryRemoteDocumentCache remoteDocumentCache = new MemoryRemoteDocumentCache(this);
    private boolean started;

    public static MemoryPersistence createEagerGcMemoryPersistence() {
        MemoryPersistence memoryPersistence = new MemoryPersistence();
        memoryPersistence.setReferenceDelegate(new MemoryEagerReferenceDelegate(memoryPersistence));
        return memoryPersistence;
    }

    public static MemoryPersistence createLruGcMemoryPersistence(Params params, LocalSerializer localSerializer) {
        MemoryPersistence memoryPersistence = new MemoryPersistence();
        memoryPersistence.setReferenceDelegate(new MemoryLruReferenceDelegate(memoryPersistence, params, localSerializer));
        return memoryPersistence;
    }

    private MemoryPersistence() {
    }

    public void start() {
        Assert.hardAssert(this.started ^ true, "MemoryPersistence double-started!", new Object[0]);
        this.started = true;
    }

    public void shutdown() {
        Assert.hardAssert(this.started, "MemoryPersistence shutdown without start", new Object[0]);
        this.started = false;
    }

    public boolean isStarted() {
        return this.started;
    }

    ReferenceDelegate getReferenceDelegate() {
        return this.referenceDelegate;
    }

    private void setReferenceDelegate(ReferenceDelegate referenceDelegate) {
        this.referenceDelegate = referenceDelegate;
    }

    MutationQueue getMutationQueue(User user) {
        MemoryMutationQueue memoryMutationQueue = (MemoryMutationQueue) this.mutationQueues.get(user);
        if (memoryMutationQueue != null) {
            return memoryMutationQueue;
        }
        MutationQueue memoryMutationQueue2 = new MemoryMutationQueue(this);
        this.mutationQueues.put(user, memoryMutationQueue2);
        return memoryMutationQueue2;
    }

    Iterable<MemoryMutationQueue> getMutationQueues() {
        return this.mutationQueues.values();
    }

    MemoryQueryCache getQueryCache() {
        return this.queryCache;
    }

    MemoryRemoteDocumentCache getRemoteDocumentCache() {
        return this.remoteDocumentCache;
    }

    IndexManager getIndexManager() {
        return this.indexManager;
    }

    void runTransaction(String str, Runnable runnable) {
        this.referenceDelegate.onTransactionStarted();
        try {
            runnable.run();
        } finally {
            this.referenceDelegate.onTransactionCommitted();
        }
    }

    <T> T runTransaction(String str, Supplier<T> supplier) {
        this.referenceDelegate.onTransactionStarted();
        try {
            T t = supplier.get();
            return t;
        } finally {
            this.referenceDelegate.onTransactionCommitted();
        }
    }
}
