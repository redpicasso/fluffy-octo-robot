package com.google.firebase.functions;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
class HttpsCallOptions {
    private static final long DEFAULT_TIMEOUT = 70;
    private static final TimeUnit DEFAULT_TIMEOUT_UNITS = TimeUnit.SECONDS;
    private long timeout = DEFAULT_TIMEOUT;
    private TimeUnit timeoutUnits = DEFAULT_TIMEOUT_UNITS;

    HttpsCallOptions() {
    }

    void setTimeout(long j, TimeUnit timeUnit) {
        this.timeout = j;
        this.timeoutUnits = timeUnit;
    }

    OkHttpClient apply(OkHttpClient okHttpClient) {
        return okHttpClient.newBuilder().callTimeout(this.timeout, this.timeoutUnits).build();
    }
}
