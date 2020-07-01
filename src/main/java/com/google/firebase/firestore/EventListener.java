package com.google.firebase.firestore;

import com.google.firebase.annotations.PublicApi;
import javax.annotation.Nullable;

@PublicApi
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface EventListener<T> {
    @PublicApi
    void onEvent(@Nullable T t, @Nullable FirebaseFirestoreException firebaseFirestoreException);
}
