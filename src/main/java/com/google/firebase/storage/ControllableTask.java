package com.google.firebase.storage;

import android.app.Activity;
import androidx.annotation.NonNull;
import com.google.firebase.annotations.PublicApi;
import java.util.concurrent.Executor;

@PublicApi
/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public abstract class ControllableTask<TState> extends CancellableTask<TState> {
    @PublicApi
    public abstract ControllableTask<TState> addOnPausedListener(@NonNull Activity activity, @NonNull OnPausedListener<? super TState> onPausedListener);

    @PublicApi
    public abstract ControllableTask<TState> addOnPausedListener(@NonNull OnPausedListener<? super TState> onPausedListener);

    @PublicApi
    public abstract ControllableTask<TState> addOnPausedListener(@NonNull Executor executor, @NonNull OnPausedListener<? super TState> onPausedListener);

    @PublicApi
    public abstract boolean isPaused();

    @PublicApi
    public abstract boolean pause();

    @PublicApi
    public abstract boolean resume();
}
