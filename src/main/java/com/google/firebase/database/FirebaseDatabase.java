package com.google.firebase.database;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.database.Logger.Level;
import com.google.firebase.database.core.DatabaseConfig;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.Repo;
import com.google.firebase.database.core.RepoInfo;
import com.google.firebase.database.core.RepoManager;
import com.google.firebase.database.core.utilities.ParsedUrl;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.utilities.Validation;

@PublicApi
/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class FirebaseDatabase {
    private static final String SDK_VERSION = "3.0.0";
    private final FirebaseApp app;
    private final DatabaseConfig config;
    private Repo repo;
    private final RepoInfo repoInfo;

    @PublicApi
    @NonNull
    public static String getSdkVersion() {
        return SDK_VERSION;
    }

    @PublicApi
    @NonNull
    public static FirebaseDatabase getInstance() {
        FirebaseApp instance = FirebaseApp.getInstance();
        if (instance != null) {
            return getInstance(instance, instance.getOptions().getDatabaseUrl());
        }
        throw new DatabaseException("You must call FirebaseApp.initialize() first.");
    }

    @PublicApi
    @NonNull
    public static FirebaseDatabase getInstance(@NonNull String str) {
        FirebaseApp instance = FirebaseApp.getInstance();
        if (instance != null) {
            return getInstance(instance, str);
        }
        throw new DatabaseException("You must call FirebaseApp.initialize() first.");
    }

    @PublicApi
    @NonNull
    public static FirebaseDatabase getInstance(@NonNull FirebaseApp firebaseApp) {
        return getInstance(firebaseApp, firebaseApp.getOptions().getDatabaseUrl());
    }

    @PublicApi
    @NonNull
    public static synchronized FirebaseDatabase getInstance(@NonNull FirebaseApp firebaseApp, @NonNull String str) {
        FirebaseDatabase firebaseDatabase;
        synchronized (FirebaseDatabase.class) {
            if (TextUtils.isEmpty(str)) {
                throw new DatabaseException("Failed to get FirebaseDatabase instance: Specify DatabaseURL within FirebaseApp or from your getInstance() call.");
            }
            ParsedUrl parseUrl = Utilities.parseUrl(str);
            if (parseUrl.path.isEmpty()) {
                Preconditions.checkNotNull(firebaseApp, "Provided FirebaseApp must not be null.");
                FirebaseDatabaseComponent firebaseDatabaseComponent = (FirebaseDatabaseComponent) firebaseApp.get(FirebaseDatabaseComponent.class);
                Preconditions.checkNotNull(firebaseDatabaseComponent, "Firebase Database component is not present.");
                firebaseDatabase = firebaseDatabaseComponent.get(parseUrl.repoInfo);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Specified Database URL '");
                stringBuilder.append(str);
                stringBuilder.append("' is invalid. It should point to the root of a Firebase Database but it includes a path: ");
                stringBuilder.append(parseUrl.path.toString());
                throw new DatabaseException(stringBuilder.toString());
            }
        }
        return firebaseDatabase;
    }

    static FirebaseDatabase createForTests(FirebaseApp firebaseApp, RepoInfo repoInfo, DatabaseConfig databaseConfig) {
        FirebaseDatabase firebaseDatabase = new FirebaseDatabase(firebaseApp, repoInfo, databaseConfig);
        firebaseDatabase.ensureRepo();
        return firebaseDatabase;
    }

    FirebaseDatabase(FirebaseApp firebaseApp, RepoInfo repoInfo, DatabaseConfig databaseConfig) {
        this.app = firebaseApp;
        this.repoInfo = repoInfo;
        this.config = databaseConfig;
    }

    @PublicApi
    @NonNull
    public FirebaseApp getApp() {
        return this.app;
    }

    @PublicApi
    @NonNull
    public DatabaseReference getReference() {
        ensureRepo();
        return new DatabaseReference(this.repo, Path.getEmptyPath());
    }

    @PublicApi
    @NonNull
    public DatabaseReference getReference(@NonNull String str) {
        ensureRepo();
        if (str != null) {
            Validation.validateRootPathString(str);
            return new DatabaseReference(this.repo, new Path(str));
        }
        throw new NullPointerException("Can't pass null for argument 'pathString' in FirebaseDatabase.getReference()");
    }

    @PublicApi
    @NonNull
    public DatabaseReference getReferenceFromUrl(@NonNull String str) {
        ensureRepo();
        if (str != null) {
            ParsedUrl parseUrl = Utilities.parseUrl(str);
            if (parseUrl.repoInfo.host.equals(this.repo.getRepoInfo().host)) {
                return new DatabaseReference(this.repo, parseUrl.path);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid URL (");
            stringBuilder.append(str);
            stringBuilder.append(") passed to getReference().  URL was expected to match configured Database URL: ");
            stringBuilder.append(getReference().toString());
            throw new DatabaseException(stringBuilder.toString());
        }
        throw new NullPointerException("Can't pass null for argument 'url' in FirebaseDatabase.getReferenceFromUrl()");
    }

    @PublicApi
    public void purgeOutstandingWrites() {
        ensureRepo();
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                FirebaseDatabase.this.repo.purgeOutstandingWrites();
            }
        });
    }

    @PublicApi
    public void goOnline() {
        ensureRepo();
        RepoManager.resume(this.repo);
    }

    @PublicApi
    public void goOffline() {
        ensureRepo();
        RepoManager.interrupt(this.repo);
    }

    @PublicApi
    public synchronized void setLogLevel(@NonNull Level level) {
        assertUnfrozen("setLogLevel");
        this.config.setLogLevel(level);
    }

    @PublicApi
    public synchronized void setPersistenceEnabled(boolean z) {
        assertUnfrozen("setPersistenceEnabled");
        this.config.setPersistenceEnabled(z);
    }

    @PublicApi
    public synchronized void setPersistenceCacheSizeBytes(long j) {
        assertUnfrozen("setPersistenceCacheSizeBytes");
        this.config.setPersistenceCacheSizeBytes(j);
    }

    private void assertUnfrozen(String str) {
        if (this.repo != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Calls to ");
            stringBuilder.append(str);
            stringBuilder.append("() must be made before any other usage of FirebaseDatabase instance.");
            throw new DatabaseException(stringBuilder.toString());
        }
    }

    private synchronized void ensureRepo() {
        if (this.repo == null) {
            this.repo = RepoManager.createRepo(this.config, this.repoInfo, this);
        }
    }

    DatabaseConfig getConfig() {
        return this.config;
    }
}
