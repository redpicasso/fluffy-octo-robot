package com.google.firebase.functions;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
final /* synthetic */ class FirebaseFunctions$$Lambda$5 implements Continuation {
    private final FirebaseFunctions arg$1;
    private final String arg$2;
    private final Object arg$3;
    private final HttpsCallOptions arg$4;

    private FirebaseFunctions$$Lambda$5(FirebaseFunctions firebaseFunctions, String str, Object obj, HttpsCallOptions httpsCallOptions) {
        this.arg$1 = firebaseFunctions;
        this.arg$2 = str;
        this.arg$3 = obj;
        this.arg$4 = httpsCallOptions;
    }

    public static Continuation lambdaFactory$(FirebaseFunctions firebaseFunctions, String str, Object obj, HttpsCallOptions httpsCallOptions) {
        return new FirebaseFunctions$$Lambda$5(firebaseFunctions, str, obj, httpsCallOptions);
    }

    public Object then(Task task) {
        return FirebaseFunctions.lambda$call$2(this.arg$1, this.arg$2, this.arg$3, this.arg$4, task);
    }
}
