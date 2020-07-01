package com.google.firebase.functions;

import androidx.annotation.Nullable;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
public class HttpsCallableReference {
    private final FirebaseFunctions functionsClient;
    private final String name;
    HttpsCallOptions options = new HttpsCallOptions();

    HttpsCallableReference(FirebaseFunctions firebaseFunctions, String str) {
        this.functionsClient = firebaseFunctions;
        this.name = str;
    }

    public Task<HttpsCallableResult> call(@Nullable Object obj) {
        return this.functionsClient.call(this.name, obj, this.options);
    }

    public Task<HttpsCallableResult> call() {
        return this.functionsClient.call(this.name, null, this.options);
    }

    public void setTimeout(long j, TimeUnit timeUnit) {
        this.options.setTimeout(j, timeUnit);
    }

    public HttpsCallableReference withTimeout(long j, TimeUnit timeUnit) {
        HttpsCallableReference httpsCallableReference = new HttpsCallableReference(this.functionsClient, this.name);
        httpsCallableReference.setTimeout(j, timeUnit);
        return httpsCallableReference;
    }
}
