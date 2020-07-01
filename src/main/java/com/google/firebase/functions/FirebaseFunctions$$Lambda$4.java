package com.google.firebase.functions;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
final /* synthetic */ class FirebaseFunctions$$Lambda$4 implements Continuation {
    private final FirebaseFunctions arg$1;

    private FirebaseFunctions$$Lambda$4(FirebaseFunctions firebaseFunctions) {
        this.arg$1 = firebaseFunctions;
    }

    public static Continuation lambdaFactory$(FirebaseFunctions firebaseFunctions) {
        return new FirebaseFunctions$$Lambda$4(firebaseFunctions);
    }

    public Object then(Task task) {
        return this.arg$1.contextProvider.getContext();
    }
}
