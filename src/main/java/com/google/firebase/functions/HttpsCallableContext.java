package com.google.firebase.functions;

import androidx.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
class HttpsCallableContext {
    @Nullable
    private final String authToken;
    @Nullable
    private final String instanceIdToken;

    HttpsCallableContext(@Nullable String str, @Nullable String str2) {
        this.authToken = str;
        this.instanceIdToken = str2;
    }

    @Nullable
    public String getAuthToken() {
        return this.authToken;
    }

    @Nullable
    public String getInstanceIdToken() {
        return this.instanceIdToken;
    }
}
