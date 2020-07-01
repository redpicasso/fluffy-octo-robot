package com.google.firebase.iid;

import androidx.annotation.NonNull;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
public interface InstanceIdResult {
    @NonNull
    String getId();

    @NonNull
    String getToken();
}
