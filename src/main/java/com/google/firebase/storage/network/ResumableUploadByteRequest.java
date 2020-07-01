package com.google.firebase.storage.network;

import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.FirebaseApp;
import io.grpc.internal.GrpcUtil;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class ResumableUploadByteRequest extends ResumableNetworkRequest {
    private final int bytesToWrite;
    private final byte[] chunk;
    private final boolean isFinal;
    private final long offset;
    private final String uploadURL;

    @NonNull
    protected String getAction() {
        return GrpcUtil.HTTP_METHOD;
    }

    public ResumableUploadByteRequest(@NonNull Uri uri, @NonNull FirebaseApp firebaseApp, @NonNull String str, @Nullable byte[] bArr, long j, int i, boolean z) {
        super(uri, firebaseApp);
        if (TextUtils.isEmpty(str)) {
            this.mException = new IllegalArgumentException("uploadURL is null or empty");
        }
        if (bArr == null && i != -1) {
            this.mException = new IllegalArgumentException("contentType is null or empty");
        }
        if (j < 0) {
            this.mException = new IllegalArgumentException("offset cannot be negative");
        }
        this.bytesToWrite = i;
        this.uploadURL = str;
        if (i <= 0) {
            bArr = null;
        }
        this.chunk = bArr;
        this.offset = j;
        this.isFinal = z;
        super.setCustomHeader("X-Goog-Upload-Protocol", "resumable");
        String str2 = "X-Goog-Upload-Command";
        if (this.isFinal && this.bytesToWrite > 0) {
            super.setCustomHeader(str2, "upload, finalize");
        } else if (this.isFinal) {
            super.setCustomHeader(str2, "finalize");
        } else {
            super.setCustomHeader(str2, "upload");
        }
        super.setCustomHeader("X-Goog-Upload-Offset", Long.toString(this.offset));
    }

    @NonNull
    protected String getURL() {
        return this.uploadURL;
    }

    @Nullable
    protected byte[] getOutputRaw() {
        return this.chunk;
    }

    protected int getOutputRawSize() {
        int i = this.bytesToWrite;
        return i > 0 ? i : 0;
    }
}
