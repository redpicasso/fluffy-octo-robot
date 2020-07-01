package com.google.firebase.storage.network;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.firebase.FirebaseApp;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class GetMetadataNetworkRequest extends NetworkRequest {
    @NonNull
    protected String getAction() {
        return "GET";
    }

    public GetMetadataNetworkRequest(@NonNull Uri uri, @NonNull FirebaseApp firebaseApp) {
        super(uri, firebaseApp);
    }
}
