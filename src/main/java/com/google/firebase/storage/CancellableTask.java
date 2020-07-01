package com.google.firebase.storage;

import android.app.Activity;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Task;
import com.google.firebase.annotations.PublicApi;
import java.util.concurrent.Executor;

@PublicApi
/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public abstract class CancellableTask<TState> extends Task<TState> {
    @PublicApi
    public abstract CancellableTask<TState> addOnProgressListener(@NonNull Activity activity, @NonNull OnProgressListener<? super TState> onProgressListener);

    @PublicApi
    public abstract CancellableTask<TState> addOnProgressListener(@NonNull OnProgressListener<? super TState> onProgressListener);

    @PublicApi
    public abstract CancellableTask<TState> addOnProgressListener(@NonNull Executor executor, @NonNull OnProgressListener<? super TState> onProgressListener);

    @PublicApi
    public abstract boolean cancel();

    @PublicApi
    public abstract boolean isCanceled();

    @PublicApi
    public abstract boolean isInProgress();
}
