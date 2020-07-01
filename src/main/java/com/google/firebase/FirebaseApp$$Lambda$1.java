package com.google.firebase;

import android.content.Context;
import com.google.firebase.events.Publisher;
import com.google.firebase.inject.Provider;
import com.google.firebase.internal.DataCollectionConfigStorage;

/* compiled from: com.google.firebase:firebase-common@@19.0.0 */
final /* synthetic */ class FirebaseApp$$Lambda$1 implements Provider {
    private final FirebaseApp arg$1;
    private final Context arg$2;

    private FirebaseApp$$Lambda$1(FirebaseApp firebaseApp, Context context) {
        this.arg$1 = firebaseApp;
        this.arg$2 = context;
    }

    public static Provider lambdaFactory$(FirebaseApp firebaseApp, Context context) {
        return new FirebaseApp$$Lambda$1(firebaseApp, context);
    }

    public Object get() {
        return new DataCollectionConfigStorage(this.arg$2, this.arg$1.getPersistenceKey(), (Publisher) this.arg$1.componentRuntime.get(Publisher.class));
    }
}
