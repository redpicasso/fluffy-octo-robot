package com.google.firebase.storage.network;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.FirebaseApp;
import org.json.JSONObject;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class UpdateMetadataNetworkRequest extends NetworkRequest {
    private final JSONObject metadata;

    @NonNull
    protected String getAction() {
        return "PUT";
    }

    public UpdateMetadataNetworkRequest(@NonNull Uri uri, @NonNull FirebaseApp firebaseApp, @Nullable JSONObject jSONObject) {
        super(uri, firebaseApp);
        this.metadata = jSONObject;
        setCustomHeader("X-HTTP-Method-Override", "PATCH");
    }

    @Nullable
    protected JSONObject getOutputJSON() {
        return this.metadata;
    }
}
