package com.google.firebase.database.connection;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public interface ConnectionAuthTokenProvider {

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface GetTokenCallback {
        /* renamed from: onError */
        void lambda$onError$1(String str);

        /* renamed from: onSuccess */
        void lambda$onSuccess$0(String str);
    }

    void getToken(boolean z, GetTokenCallback getTokenCallback);
}
