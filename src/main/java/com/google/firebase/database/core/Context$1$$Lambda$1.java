package com.google.firebase.database.core;

import com.google.firebase.database.connection.ConnectionAuthTokenProvider.GetTokenCallback;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
final /* synthetic */ class Context$1$$Lambda$1 implements Runnable {
    private final GetTokenCallback arg$1;
    private final String arg$2;

    private Context$1$$Lambda$1(GetTokenCallback getTokenCallback, String str) {
        this.arg$1 = getTokenCallback;
        this.arg$2 = str;
    }

    public static Runnable lambdaFactory$(GetTokenCallback getTokenCallback, String str) {
        return new Context$1$$Lambda$1(getTokenCallback, str);
    }

    public void run() {
        this.arg$1.lambda$onSuccess$0(this.arg$2);
    }
}
