package com.google.firebase.firestore.core;

import android.content.Context;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.common.base.Function;
import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreException.Code;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.core.EventManager.ListenOptions;
import com.google.firebase.firestore.local.LocalSerializer;
import com.google.firebase.firestore.local.LocalStore;
import com.google.firebase.firestore.local.LruGarbageCollector;
import com.google.firebase.firestore.local.LruGarbageCollector.Params;
import com.google.firebase.firestore.local.LruGarbageCollector.Scheduler;
import com.google.firebase.firestore.local.MemoryPersistence;
import com.google.firebase.firestore.local.Persistence;
import com.google.firebase.firestore.local.SQLitePersistence;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.NoDocument;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationBatchResult;
import com.google.firebase.firestore.remote.AndroidConnectivityMonitor;
import com.google.firebase.firestore.remote.Datastore;
import com.google.firebase.firestore.remote.RemoteEvent;
import com.google.firebase.firestore.remote.RemoteSerializer;
import com.google.firebase.firestore.remote.RemoteStore;
import com.google.firebase.firestore.remote.RemoteStore.RemoteStoreCallback;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.Logger;
import io.grpc.Status;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class FirestoreClient implements RemoteStoreCallback {
    private static final String LOG_TAG = "FirestoreClient";
    private final AsyncQueue asyncQueue;
    private final CredentialsProvider credentialsProvider;
    private final DatabaseInfo databaseInfo;
    private EventManager eventManager;
    private LocalStore localStore;
    @Nullable
    private Scheduler lruScheduler;
    private Persistence persistence;
    private RemoteStore remoteStore;
    private SyncEngine syncEngine;

    public FirestoreClient(Context context, DatabaseInfo databaseInfo, FirebaseFirestoreSettings firebaseFirestoreSettings, CredentialsProvider credentialsProvider, AsyncQueue asyncQueue) {
        this.databaseInfo = databaseInfo;
        this.credentialsProvider = credentialsProvider;
        this.asyncQueue = asyncQueue;
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        credentialsProvider.setChangeListener(FirestoreClient$$Lambda$1.lambdaFactory$(this, new AtomicBoolean(false), taskCompletionSource, asyncQueue));
        asyncQueue.enqueueAndForget(FirestoreClient$$Lambda$2.lambdaFactory$(this, taskCompletionSource, context, firebaseFirestoreSettings));
    }

    static /* synthetic */ void lambda$new$1(FirestoreClient firestoreClient, AtomicBoolean atomicBoolean, TaskCompletionSource taskCompletionSource, AsyncQueue asyncQueue, User user) {
        if (atomicBoolean.compareAndSet(false, true)) {
            Assert.hardAssert(taskCompletionSource.getTask().isComplete() ^ true, "Already fulfilled first user task", new Object[0]);
            taskCompletionSource.setResult(user);
            return;
        }
        asyncQueue.enqueueAndForget(FirestoreClient$$Lambda$14.lambdaFactory$(firestoreClient, user));
    }

    static /* synthetic */ void lambda$new$0(FirestoreClient firestoreClient, User user) {
        Logger.debug(LOG_TAG, "Credential changed. Current user: %s", user.getUid());
        firestoreClient.syncEngine.handleCredentialChange(user);
    }

    static /* synthetic */ void lambda$new$2(FirestoreClient firestoreClient, TaskCompletionSource taskCompletionSource, Context context, FirebaseFirestoreSettings firebaseFirestoreSettings) {
        Throwable e;
        try {
            firestoreClient.initialize(context, (User) Tasks.await(taskCompletionSource.getTask()), firebaseFirestoreSettings.isPersistenceEnabled(), firebaseFirestoreSettings.getCacheSizeBytes());
        } catch (InterruptedException e2) {
            e = e2;
            throw new RuntimeException(e);
        } catch (ExecutionException e3) {
            e = e3;
            throw new RuntimeException(e);
        }
    }

    public Task<Void> disableNetwork() {
        return this.asyncQueue.enqueue(FirestoreClient$$Lambda$3.lambdaFactory$(this));
    }

    public Task<Void> enableNetwork() {
        return this.asyncQueue.enqueue(FirestoreClient$$Lambda$4.lambdaFactory$(this));
    }

    public Task<Void> shutdown() {
        this.credentialsProvider.removeChangeListener();
        return this.asyncQueue.enqueue(FirestoreClient$$Lambda$5.lambdaFactory$(this));
    }

    static /* synthetic */ void lambda$shutdown$5(FirestoreClient firestoreClient) {
        firestoreClient.remoteStore.shutdown();
        firestoreClient.persistence.shutdown();
        Scheduler scheduler = firestoreClient.lruScheduler;
        if (scheduler != null) {
            scheduler.stop();
        }
    }

    public QueryListener listen(Query query, ListenOptions listenOptions, EventListener<ViewSnapshot> eventListener) {
        QueryListener queryListener = new QueryListener(query, listenOptions, eventListener);
        this.asyncQueue.enqueueAndForget(FirestoreClient$$Lambda$6.lambdaFactory$(this, queryListener));
        return queryListener;
    }

    public void stopListening(QueryListener queryListener) {
        this.asyncQueue.enqueueAndForget(FirestoreClient$$Lambda$7.lambdaFactory$(this, queryListener));
    }

    public Task<Document> getDocumentFromLocalCache(DocumentKey documentKey) {
        return this.asyncQueue.enqueue(FirestoreClient$$Lambda$8.lambdaFactory$(this, documentKey)).continueWith(FirestoreClient$$Lambda$9.lambdaFactory$());
    }

    static /* synthetic */ Document lambda$getDocumentFromLocalCache$9(Task task) throws Exception {
        MaybeDocument maybeDocument = (MaybeDocument) task.getResult();
        if (maybeDocument instanceof Document) {
            return (Document) maybeDocument;
        }
        if (maybeDocument instanceof NoDocument) {
            return null;
        }
        throw new FirebaseFirestoreException("Failed to get document from cache. (However, this document may exist on the server. Run again without setting source to CACHE to attempt to retrieve the document from the server.)", Code.UNAVAILABLE);
    }

    public Task<ViewSnapshot> getDocumentsFromLocalCache(Query query) {
        return this.asyncQueue.enqueue(FirestoreClient$$Lambda$10.lambdaFactory$(this, query));
    }

    static /* synthetic */ ViewSnapshot lambda$getDocumentsFromLocalCache$10(FirestoreClient firestoreClient, Query query) throws Exception {
        ImmutableSortedMap executeQuery = firestoreClient.localStore.executeQuery(query);
        View view = new View(query, new ImmutableSortedSet(Collections.emptyList(), FirestoreClient$$Lambda$13.lambdaFactory$()));
        return view.applyChanges(view.computeDocChanges(executeQuery)).getSnapshot();
    }

    public Task<Void> write(List<Mutation> list) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.asyncQueue.enqueueAndForget(FirestoreClient$$Lambda$11.lambdaFactory$(this, list, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    public <TResult> Task<TResult> transaction(Function<Transaction, Task<TResult>> function, int i) {
        return AsyncQueue.callTask(this.asyncQueue.getExecutor(), FirestoreClient$$Lambda$12.lambdaFactory$(this, function, i));
    }

    private void initialize(Context context, User user, boolean z, long j) {
        LruGarbageCollector garbageCollector;
        Logger.debug(LOG_TAG, "Initializing. user=%s", user.getUid());
        if (z) {
            Context context2 = context;
            Persistence sQLitePersistence = new SQLitePersistence(context2, this.databaseInfo.getPersistenceKey(), this.databaseInfo.getDatabaseId(), new LocalSerializer(new RemoteSerializer(this.databaseInfo.getDatabaseId())), Params.WithCacheSizeBytes(j));
            garbageCollector = sQLitePersistence.getReferenceDelegate().getGarbageCollector();
            this.persistence = sQLitePersistence;
        } else {
            this.persistence = MemoryPersistence.createEagerGcMemoryPersistence();
            garbageCollector = null;
        }
        this.persistence.start();
        this.localStore = new LocalStore(this.persistence, user);
        if (garbageCollector != null) {
            this.lruScheduler = garbageCollector.newScheduler(this.asyncQueue, this.localStore);
            this.lruScheduler.start();
        }
        this.remoteStore = new RemoteStore(this, this.localStore, new Datastore(this.databaseInfo, this.asyncQueue, this.credentialsProvider, context), this.asyncQueue, new AndroidConnectivityMonitor(context));
        this.syncEngine = new SyncEngine(this.localStore, this.remoteStore, user);
        this.eventManager = new EventManager(this.syncEngine);
        this.localStore.start();
        this.remoteStore.start();
    }

    public void handleRemoteEvent(RemoteEvent remoteEvent) {
        this.syncEngine.handleRemoteEvent(remoteEvent);
    }

    public void handleRejectedListen(int i, Status status) {
        this.syncEngine.handleRejectedListen(i, status);
    }

    public void handleSuccessfulWrite(MutationBatchResult mutationBatchResult) {
        this.syncEngine.handleSuccessfulWrite(mutationBatchResult);
    }

    public void handleRejectedWrite(int i, Status status) {
        this.syncEngine.handleRejectedWrite(i, status);
    }

    public void handleOnlineStateChange(OnlineState onlineState) {
        this.syncEngine.handleOnlineStateChange(onlineState);
    }

    public ImmutableSortedSet<DocumentKey> getRemoteKeysForTarget(int i) {
        return this.syncEngine.getRemoteKeysForTarget(i);
    }
}
