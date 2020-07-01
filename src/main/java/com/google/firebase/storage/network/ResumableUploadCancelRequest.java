package com.google.firebase.storage.network;

import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.google.firebase.FirebaseApp;
import io.grpc.internal.GrpcUtil;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class ResumableUploadCancelRequest extends ResumableNetworkRequest {
    @VisibleForTesting
    public static boolean CANCEL_CALLED = false;
    private final String uploadURL;

    @NonNull
    protected String getAction() {
        return GrpcUtil.HTTP_METHOD;
    }

    public ResumableUploadCancelRequest(@NonNull Uri uri, @NonNull FirebaseApp firebaseApp, @NonNull String str) {
        super(uri, firebaseApp);
        CANCEL_CALLED = true;
        if (TextUtils.isEmpty(str)) {
            this.mException = new IllegalArgumentException("uploadURL is null or empty");
        }
        this.uploadURL = str;
        super.setCustomHeader("X-Goog-Upload-Protocol", "resumable");
        super.setCustomHeader("X-Goog-Upload-Command", "cancel");
    }

    @NonNull
    protected String getURL() {
        return this.uploadURL;
    }
}
