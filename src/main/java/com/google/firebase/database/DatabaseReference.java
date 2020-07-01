package com.google.firebase.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Key;
import com.google.android.gms.tasks.Task;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.database.Transaction.Handler;
import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Context;
import com.google.firebase.database.core.DatabaseConfig;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.Repo;
import com.google.firebase.database.core.RepoManager;
import com.google.firebase.database.core.ValidationPath;
import com.google.firebase.database.core.utilities.Pair;
import com.google.firebase.database.core.utilities.ParsedUrl;
import com.google.firebase.database.core.utilities.PushIdGenerator;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.utilities.encoding.CustomClassMapper;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.net.URLEncoder;
import java.util.Map;

@PublicApi
/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class DatabaseReference extends Query {
    private static DatabaseConfig defaultConfig;

    @PublicApi
    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface CompletionListener {
        @PublicApi
        void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference);
    }

    DatabaseReference(Repo repo, Path path) {
        super(repo, path);
    }

    DatabaseReference(String str, DatabaseConfig databaseConfig) {
        this(Utilities.parseUrl(str), databaseConfig);
    }

    private DatabaseReference(ParsedUrl parsedUrl, DatabaseConfig databaseConfig) {
        this(RepoManager.getRepo(databaseConfig, parsedUrl.repoInfo), parsedUrl.path);
    }

    @PublicApi
    @NonNull
    public DatabaseReference child(@NonNull String str) {
        if (str != null) {
            if (getPath().isEmpty()) {
                Validation.validateRootPathString(str);
            } else {
                Validation.validatePathString(str);
            }
            return new DatabaseReference(this.repo, getPath().child(new Path(str)));
        }
        throw new NullPointerException("Can't pass null for argument 'pathString' in child()");
    }

    @PublicApi
    @NonNull
    public DatabaseReference push() {
        return new DatabaseReference(this.repo, getPath().child(ChildKey.fromString(PushIdGenerator.generatePushChildName(this.repo.getServerTime()))));
    }

    @PublicApi
    @NonNull
    public Task<Void> setValue(@Nullable Object obj) {
        return setValueInternal(obj, PriorityUtilities.parsePriority(this.path, null), null);
    }

    @PublicApi
    @NonNull
    public Task<Void> setValue(@Nullable Object obj, @Nullable Object obj2) {
        return setValueInternal(obj, PriorityUtilities.parsePriority(this.path, obj2), null);
    }

    @PublicApi
    public void setValue(@Nullable Object obj, @Nullable CompletionListener completionListener) {
        setValueInternal(obj, PriorityUtilities.parsePriority(this.path, null), completionListener);
    }

    @PublicApi
    public void setValue(@Nullable Object obj, @Nullable Object obj2, @Nullable CompletionListener completionListener) {
        setValueInternal(obj, PriorityUtilities.parsePriority(this.path, obj2), completionListener);
    }

    private Task<Void> setValueInternal(Object obj, Node node, CompletionListener completionListener) {
        Validation.validateWritablePath(getPath());
        ValidationPath.validateWithObject(getPath(), obj);
        obj = CustomClassMapper.convertToPlainJavaTypes(obj);
        Validation.validateWritableObject(obj);
        final Node NodeFromJSON = NodeUtilities.NodeFromJSON(obj, node);
        final Pair wrapOnComplete = Utilities.wrapOnComplete(completionListener);
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                DatabaseReference.this.repo.setValue(DatabaseReference.this.getPath(), NodeFromJSON, (CompletionListener) wrapOnComplete.getSecond());
            }
        });
        return (Task) wrapOnComplete.getFirst();
    }

    @PublicApi
    @NonNull
    public Task<Void> setPriority(@Nullable Object obj) {
        return setPriorityInternal(PriorityUtilities.parsePriority(this.path, obj), null);
    }

    @PublicApi
    public void setPriority(@Nullable Object obj, @Nullable CompletionListener completionListener) {
        setPriorityInternal(PriorityUtilities.parsePriority(this.path, obj), completionListener);
    }

    private Task<Void> setPriorityInternal(final Node node, CompletionListener completionListener) {
        Validation.validateWritablePath(getPath());
        final Pair wrapOnComplete = Utilities.wrapOnComplete(completionListener);
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                DatabaseReference.this.repo.setValue(DatabaseReference.this.getPath().child(ChildKey.getPriorityKey()), node, (CompletionListener) wrapOnComplete.getSecond());
            }
        });
        return (Task) wrapOnComplete.getFirst();
    }

    @PublicApi
    @NonNull
    public Task<Void> updateChildren(@NonNull Map<String, Object> map) {
        return updateChildrenInternal(map, null);
    }

    @PublicApi
    public void updateChildren(@NonNull Map<String, Object> map, @Nullable CompletionListener completionListener) {
        updateChildrenInternal(map, completionListener);
    }

    private Task<Void> updateChildrenInternal(Map<String, Object> map, CompletionListener completionListener) {
        if (map != null) {
            final Map convertToPlainJavaTypes = CustomClassMapper.convertToPlainJavaTypes((Map) map);
            final CompoundWrite fromPathMerge = CompoundWrite.fromPathMerge(Validation.parseAndValidateUpdate(getPath(), convertToPlainJavaTypes));
            final Pair wrapOnComplete = Utilities.wrapOnComplete(completionListener);
            this.repo.scheduleNow(new Runnable() {
                public void run() {
                    DatabaseReference.this.repo.updateChildren(DatabaseReference.this.getPath(), fromPathMerge, (CompletionListener) wrapOnComplete.getSecond(), convertToPlainJavaTypes);
                }
            });
            return (Task) wrapOnComplete.getFirst();
        }
        throw new NullPointerException("Can't pass null for argument 'update' in updateChildren()");
    }

    @PublicApi
    @NonNull
    public Task<Void> removeValue() {
        return setValue(null);
    }

    @PublicApi
    public void removeValue(@Nullable CompletionListener completionListener) {
        setValue(null, completionListener);
    }

    @PublicApi
    @NonNull
    public OnDisconnect onDisconnect() {
        Validation.validateWritablePath(getPath());
        return new OnDisconnect(this.repo, getPath());
    }

    @PublicApi
    public void runTransaction(@NonNull Handler handler) {
        runTransaction(handler, true);
    }

    @PublicApi
    public void runTransaction(@NonNull final Handler handler, final boolean z) {
        if (handler != null) {
            Validation.validateWritablePath(getPath());
            this.repo.scheduleNow(new Runnable() {
                public void run() {
                    DatabaseReference.this.repo.startTransaction(DatabaseReference.this.getPath(), handler, z);
                }
            });
            return;
        }
        throw new NullPointerException("Can't pass null for argument 'handler' in runTransaction()");
    }

    @PublicApi
    public static void goOffline() {
        goOffline(getDefaultConfig());
    }

    static void goOffline(DatabaseConfig databaseConfig) {
        RepoManager.interrupt((Context) databaseConfig);
    }

    @PublicApi
    public static void goOnline() {
        goOnline(getDefaultConfig());
    }

    static void goOnline(DatabaseConfig databaseConfig) {
        RepoManager.resume((Context) databaseConfig);
    }

    @PublicApi
    @NonNull
    public FirebaseDatabase getDatabase() {
        return this.repo.getDatabase();
    }

    public String toString() {
        DatabaseReference parent = getParent();
        if (parent == null) {
            return this.repo.toString();
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(parent.toString());
            stringBuilder.append("/");
            stringBuilder.append(URLEncoder.encode(getKey(), Key.STRING_CHARSET_NAME).replace("+", "%20"));
            return stringBuilder.toString();
        } catch (Throwable e) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Failed to URLEncode key: ");
            stringBuilder2.append(getKey());
            throw new DatabaseException(stringBuilder2.toString(), e);
        }
    }

    @PublicApi
    @Nullable
    public DatabaseReference getParent() {
        Path parent = getPath().getParent();
        return parent != null ? new DatabaseReference(this.repo, parent) : null;
    }

    @PublicApi
    @NonNull
    public DatabaseReference getRoot() {
        return new DatabaseReference(this.repo, new Path(""));
    }

    @PublicApi
    @Nullable
    public String getKey() {
        if (getPath().isEmpty()) {
            return null;
        }
        return getPath().getBack().asString();
    }

    public boolean equals(Object obj) {
        return (obj instanceof DatabaseReference) && toString().equals(obj.toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    void setHijackHash(final boolean z) {
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                DatabaseReference.this.repo.setHijackHash(z);
            }
        });
    }

    private static synchronized DatabaseConfig getDefaultConfig() {
        DatabaseConfig databaseConfig;
        synchronized (DatabaseReference.class) {
            if (defaultConfig == null) {
                defaultConfig = new DatabaseConfig();
            }
            databaseConfig = defaultConfig;
        }
        return databaseConfig;
    }
}
