package com.google.firebase.storage;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
final /* synthetic */ class StorageTask$$Lambda$15 implements OnFailureListener {
    private final TaskCompletionSource arg$1;

    private StorageTask$$Lambda$15(TaskCompletionSource taskCompletionSource) {
        this.arg$1 = taskCompletionSource;
    }

    public static OnFailureListener lambdaFactory$(TaskCompletionSource taskCompletionSource) {
        return new StorageTask$$Lambda$15(taskCompletionSource);
    }

    public void onFailure(Exception exception) {
        this.arg$1.setException(exception);
    }
}
