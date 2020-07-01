package com.google.android.gms.internal.firebase_ml;

import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.Callable;

final class zzno implements Runnable {
    private final /* synthetic */ Callable val$callable;
    private final /* synthetic */ TaskCompletionSource zzapd;

    zzno(zznn zznn, Callable callable, TaskCompletionSource taskCompletionSource) {
        this.val$callable = callable;
        this.zzapd = taskCompletionSource;
    }

    public final void run() {
        zznn.zza(this.val$callable, this.zzapd);
    }
}
