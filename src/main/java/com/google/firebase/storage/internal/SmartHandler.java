package com.google.firebase.storage.internal;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.storage.StorageTaskScheduler;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class SmartHandler {
    static boolean testMode = false;
    private final Executor executor;
    private final Handler handler;

    public SmartHandler(@Nullable Executor executor) {
        this.executor = executor;
        if (this.executor != null) {
            this.handler = null;
        } else if (testMode) {
            this.handler = null;
        } else {
            this.handler = new Handler(Looper.getMainLooper());
        }
    }

    public void callBack(@NonNull Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        Handler handler = this.handler;
        if (handler == null) {
            Executor executor = this.executor;
            if (executor != null) {
                executor.execute(runnable);
                return;
            } else {
                StorageTaskScheduler.getInstance().scheduleCallback(runnable);
                return;
            }
        }
        handler.post(runnable);
    }
}
