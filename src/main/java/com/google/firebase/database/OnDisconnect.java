package com.google.firebase.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.Task;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.Repo;
import com.google.firebase.database.core.ValidationPath;
import com.google.firebase.database.core.utilities.Pair;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.utilities.encoding.CustomClassMapper;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.Map;

@PublicApi
/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class OnDisconnect {
    private Path path;
    private Repo repo;

    OnDisconnect(Repo repo, Path path) {
        this.repo = repo;
        this.path = path;
    }

    @PublicApi
    @NonNull
    public Task<Void> setValue(@Nullable Object obj) {
        return onDisconnectSetInternal(obj, PriorityUtilities.NullPriority(), null);
    }

    @PublicApi
    @NonNull
    public Task<Void> setValue(@Nullable Object obj, @Nullable String str) {
        return onDisconnectSetInternal(obj, PriorityUtilities.parsePriority(this.path, str), null);
    }

    @PublicApi
    @NonNull
    public Task<Void> setValue(@Nullable Object obj, double d) {
        return onDisconnectSetInternal(obj, PriorityUtilities.parsePriority(this.path, Double.valueOf(d)), null);
    }

    @PublicApi
    public void setValue(@Nullable Object obj, @Nullable CompletionListener completionListener) {
        onDisconnectSetInternal(obj, PriorityUtilities.NullPriority(), completionListener);
    }

    @PublicApi
    public void setValue(@Nullable Object obj, @Nullable String str, @Nullable CompletionListener completionListener) {
        onDisconnectSetInternal(obj, PriorityUtilities.parsePriority(this.path, str), completionListener);
    }

    @PublicApi
    public void setValue(@Nullable Object obj, double d, @Nullable CompletionListener completionListener) {
        onDisconnectSetInternal(obj, PriorityUtilities.parsePriority(this.path, Double.valueOf(d)), completionListener);
    }

    @PublicApi
    public void setValue(@Nullable Object obj, @Nullable Map map, @Nullable CompletionListener completionListener) {
        onDisconnectSetInternal(obj, PriorityUtilities.parsePriority(this.path, map), completionListener);
    }

    private Task<Void> onDisconnectSetInternal(Object obj, Node node, CompletionListener completionListener) {
        Validation.validateWritablePath(this.path);
        ValidationPath.validateWithObject(this.path, obj);
        obj = CustomClassMapper.convertToPlainJavaTypes(obj);
        Validation.validateWritableObject(obj);
        final Node NodeFromJSON = NodeUtilities.NodeFromJSON(obj, node);
        final Pair wrapOnComplete = Utilities.wrapOnComplete(completionListener);
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                OnDisconnect.this.repo.onDisconnectSetValue(OnDisconnect.this.path, NodeFromJSON, (CompletionListener) wrapOnComplete.getSecond());
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

    private Task<Void> updateChildrenInternal(final Map<String, Object> map, CompletionListener completionListener) {
        final Map parseAndValidateUpdate = Validation.parseAndValidateUpdate(this.path, map);
        final Pair wrapOnComplete = Utilities.wrapOnComplete(completionListener);
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                OnDisconnect.this.repo.onDisconnectUpdate(OnDisconnect.this.path, parseAndValidateUpdate, (CompletionListener) wrapOnComplete.getSecond(), map);
            }
        });
        return (Task) wrapOnComplete.getFirst();
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
    public Task<Void> cancel() {
        return cancelInternal(null);
    }

    @PublicApi
    public void cancel(@NonNull CompletionListener completionListener) {
        cancelInternal(completionListener);
    }

    private Task<Void> cancelInternal(CompletionListener completionListener) {
        final Pair wrapOnComplete = Utilities.wrapOnComplete(completionListener);
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                OnDisconnect.this.repo.onDisconnectCancel(OnDisconnect.this.path, (CompletionListener) wrapOnComplete.getSecond());
            }
        });
        return (Task) wrapOnComplete.getFirst();
    }
}
