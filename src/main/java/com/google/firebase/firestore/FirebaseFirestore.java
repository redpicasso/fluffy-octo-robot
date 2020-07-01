package com.google.firebase.firestore;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.common.base.Preconditions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.firestore.FirebaseFirestoreSettings.Builder;
import com.google.firebase.firestore.Transaction.Function;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.auth.EmptyCredentialsProvider;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;
import com.google.firebase.firestore.core.DatabaseInfo;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.core.Transaction;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.Logger;
import com.google.firebase.firestore.util.Logger.Level;
import java.util.concurrent.Executor;

@PublicApi
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class FirebaseFirestore {
    private static final String TAG = "FirebaseFirestore";
    private final AsyncQueue asyncQueue;
    private volatile FirestoreClient client;
    private final Context context;
    private final CredentialsProvider credentialsProvider;
    private final UserDataConverter dataConverter;
    private final DatabaseId databaseId;
    private final FirebaseApp firebaseApp;
    private final String persistenceKey;
    private FirebaseFirestoreSettings settings = new Builder().build();

    @PublicApi
    @NonNull
    public static FirebaseFirestore getInstance() {
        FirebaseApp instance = FirebaseApp.getInstance();
        if (instance != null) {
            return getInstance(instance, DatabaseId.DEFAULT_DATABASE_ID);
        }
        throw new IllegalStateException("You must call FirebaseApp.initializeApp first.");
    }

    @PublicApi
    @NonNull
    public static FirebaseFirestore getInstance(@NonNull FirebaseApp firebaseApp) {
        return getInstance(firebaseApp, DatabaseId.DEFAULT_DATABASE_ID);
    }

    @NonNull
    private static FirebaseFirestore getInstance(@NonNull FirebaseApp firebaseApp, @NonNull String str) {
        Preconditions.checkNotNull(firebaseApp, "Provided FirebaseApp must not be null.");
        FirestoreMultiDbComponent firestoreMultiDbComponent = (FirestoreMultiDbComponent) firebaseApp.get(FirestoreMultiDbComponent.class);
        Preconditions.checkNotNull(firestoreMultiDbComponent, "Firestore component is not present.");
        return firestoreMultiDbComponent.get(str);
    }

    @NonNull
    static FirebaseFirestore newInstance(@NonNull Context context, @NonNull FirebaseApp firebaseApp, @Nullable InternalAuthProvider internalAuthProvider, @NonNull String str) {
        String projectId = firebaseApp.getOptions().getProjectId();
        if (projectId != null) {
            CredentialsProvider emptyCredentialsProvider;
            DatabaseId forDatabase = DatabaseId.forDatabase(projectId, str);
            AsyncQueue asyncQueue = new AsyncQueue();
            if (internalAuthProvider == null) {
                Logger.debug(TAG, "Firebase Auth not available, falling back to unauthenticated usage.", new Object[0]);
                emptyCredentialsProvider = new EmptyCredentialsProvider();
            } else {
                emptyCredentialsProvider = new FirebaseAuthCredentialsProvider(internalAuthProvider);
            }
            asyncQueue.enqueueAndForget(FirebaseFirestore$$Lambda$1.lambdaFactory$(context));
            return new FirebaseFirestore(context, forDatabase, firebaseApp.getName(), emptyCredentialsProvider, asyncQueue, firebaseApp);
        }
        throw new IllegalArgumentException("FirebaseOptions.getProjectId() cannot be null");
    }

    /* JADX WARNING: Removed duplicated region for block: B:2:0x0004 A:{ExcHandler: com.google.android.gms.common.GooglePlayServicesNotAvailableException (unused com.google.android.gms.common.GooglePlayServicesNotAvailableException), Splitter: B:0:0x0000} */
    /* JADX WARNING: Missing block: B:2:0x0004, code:
            com.google.firebase.firestore.util.Logger.warn("Firestore", "Failed to update ssl context", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:3:?, code:
            return;
     */
    static /* synthetic */ void lambda$newInstance$0(android.content.Context r2) {
        /*
        com.google.android.gms.security.ProviderInstaller.installIfNeeded(r2);	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0004, GooglePlayServicesNotAvailableException -> 0x0004 }
        goto L_0x000e;
    L_0x0004:
        r2 = 0;
        r2 = new java.lang.Object[r2];
        r0 = "Firestore";
        r1 = "Failed to update ssl context";
        com.google.firebase.firestore.util.Logger.warn(r0, r1, r2);
    L_0x000e:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.FirebaseFirestore.lambda$newInstance$0(android.content.Context):void");
    }

    @VisibleForTesting
    FirebaseFirestore(Context context, DatabaseId databaseId, String str, CredentialsProvider credentialsProvider, AsyncQueue asyncQueue, @Nullable FirebaseApp firebaseApp) {
        this.context = (Context) Preconditions.checkNotNull(context);
        this.databaseId = (DatabaseId) Preconditions.checkNotNull((DatabaseId) Preconditions.checkNotNull(databaseId));
        this.dataConverter = new UserDataConverter(databaseId);
        this.persistenceKey = (String) Preconditions.checkNotNull(str);
        this.credentialsProvider = (CredentialsProvider) Preconditions.checkNotNull(credentialsProvider);
        this.asyncQueue = (AsyncQueue) Preconditions.checkNotNull(asyncQueue);
        this.firebaseApp = firebaseApp;
    }

    @PublicApi
    @NonNull
    public FirebaseFirestoreSettings getFirestoreSettings() {
        return this.settings;
    }

    @PublicApi
    public void setFirestoreSettings(@NonNull FirebaseFirestoreSettings firebaseFirestoreSettings) {
        synchronized (this.databaseId) {
            Preconditions.checkNotNull(firebaseFirestoreSettings, "Provided settings must not be null.");
            if (this.client == null || this.settings.equals(firebaseFirestoreSettings)) {
                this.settings = firebaseFirestoreSettings;
            } else {
                throw new IllegalStateException("FirebaseFirestore has already been started and its settings can no longer be changed. You can only call setFirestoreSettings() before calling any other methods on a FirebaseFirestore object.");
            }
        }
    }

    private void ensureClientConfigured() {
        if (this.client == null) {
            synchronized (this.databaseId) {
                if (this.client != null) {
                    return;
                }
                this.client = new FirestoreClient(this.context, new DatabaseInfo(this.databaseId, this.persistenceKey, this.settings.getHost(), this.settings.isSslEnabled()), this.settings, this.credentialsProvider, this.asyncQueue);
            }
        }
    }

    @PublicApi
    @NonNull
    public FirebaseApp getApp() {
        return this.firebaseApp;
    }

    @PublicApi
    @NonNull
    public CollectionReference collection(@NonNull String str) {
        Preconditions.checkNotNull(str, "Provided collection path must not be null.");
        ensureClientConfigured();
        return new CollectionReference(ResourcePath.fromString(str), this);
    }

    @PublicApi
    @NonNull
    public DocumentReference document(@NonNull String str) {
        Preconditions.checkNotNull(str, "Provided document path must not be null.");
        ensureClientConfigured();
        return DocumentReference.forPath(ResourcePath.fromString(str), this);
    }

    @PublicApi
    @NonNull
    public Query collectionGroup(@NonNull String str) {
        Preconditions.checkNotNull(str, "Provided collection ID must not be null.");
        if (str.contains("/")) {
            throw new IllegalArgumentException(String.format("Invalid collectionId '%s'. Collection IDs must not contain '/'.", new Object[]{str}));
        }
        ensureClientConfigured();
        return new Query(new Query(ResourcePath.EMPTY, str), this);
    }

    private <TResult> Task<TResult> runTransaction(Function<TResult> function, Executor executor) {
        ensureClientConfigured();
        return this.client.transaction(FirebaseFirestore$$Lambda$2.lambdaFactory$(this, executor, function), 5);
    }

    @PublicApi
    @NonNull
    public <TResult> Task<TResult> runTransaction(@NonNull Function<TResult> function) {
        Preconditions.checkNotNull(function, "Provided transaction update function must not be null.");
        return runTransaction(function, Transaction.getDefaultExecutor());
    }

    @PublicApi
    @NonNull
    public WriteBatch batch() {
        ensureClientConfigured();
        return new WriteBatch(this);
    }

    @PublicApi
    @NonNull
    public Task<Void> runBatch(@NonNull WriteBatch.Function function) {
        WriteBatch batch = batch();
        function.apply(batch);
        return batch.commit();
    }

    @VisibleForTesting
    Task<Void> shutdown() {
        if (this.client == null) {
            return Tasks.forResult(null);
        }
        return this.client.shutdown();
    }

    @VisibleForTesting
    AsyncQueue getAsyncQueue() {
        return this.asyncQueue;
    }

    @PublicApi
    public Task<Void> enableNetwork() {
        ensureClientConfigured();
        return this.client.enableNetwork();
    }

    @PublicApi
    public Task<Void> disableNetwork() {
        ensureClientConfigured();
        return this.client.disableNetwork();
    }

    @PublicApi
    public static void setLoggingEnabled(boolean z) {
        if (z) {
            Logger.setLogLevel(Level.DEBUG);
        } else {
            Logger.setLogLevel(Level.WARN);
        }
    }

    FirestoreClient getClient() {
        return this.client;
    }

    DatabaseId getDatabaseId() {
        return this.databaseId;
    }

    UserDataConverter getDataConverter() {
        return this.dataConverter;
    }

    void validateReference(DocumentReference documentReference) {
        Preconditions.checkNotNull(documentReference, "Provided DocumentReference must not be null.");
        if (documentReference.getFirestore() != this) {
            throw new IllegalArgumentException("Provided document reference is from a different Firestore instance.");
        }
    }
}
