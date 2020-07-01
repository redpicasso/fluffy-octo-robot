package com.google.firebase.functions;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
final /* synthetic */ class FirebaseContextProvider$$Lambda$1 implements Continuation {
    private final FirebaseContextProvider arg$1;

    private FirebaseContextProvider$$Lambda$1(FirebaseContextProvider firebaseContextProvider) {
        this.arg$1 = firebaseContextProvider;
    }

    public static Continuation lambdaFactory$(FirebaseContextProvider firebaseContextProvider) {
        return new FirebaseContextProvider$$Lambda$1(firebaseContextProvider);
    }

    public Object then(Task task) {
        return FirebaseContextProvider.lambda$getContext$0(this.arg$1, task);
    }
}
