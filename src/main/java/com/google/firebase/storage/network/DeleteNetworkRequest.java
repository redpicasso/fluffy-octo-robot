package com.google.firebase.storage.network;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.firebase.FirebaseApp;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class DeleteNetworkRequest extends NetworkRequest {
    @NonNull
    protected String getAction() {
        return "DELETE";
    }

    public DeleteNetworkRequest(@NonNull Uri uri, @NonNull FirebaseApp firebaseApp) {
        super(uri, firebaseApp);
    }
}
