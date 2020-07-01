package com.google.firebase.storage.network;

import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.facebook.react.uimanager.ViewProps;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.internal.SlashUtil;
import io.grpc.internal.GrpcUtil;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class ResumableUploadStartRequest extends ResumableNetworkRequest {
    private final String contentType;
    private final JSONObject metadata;

    @NonNull
    protected String getAction() {
        return GrpcUtil.HTTP_METHOD;
    }

    public ResumableUploadStartRequest(@NonNull Uri uri, @NonNull FirebaseApp firebaseApp, @Nullable JSONObject jSONObject, @NonNull String str) {
        super(uri, firebaseApp);
        this.metadata = jSONObject;
        this.contentType = str;
        if (TextUtils.isEmpty(this.contentType)) {
            this.mException = new IllegalArgumentException("mContentType is null or empty");
        }
        super.setCustomHeader("X-Goog-Upload-Protocol", "resumable");
        super.setCustomHeader("X-Goog-Upload-Command", ViewProps.START);
        super.setCustomHeader("X-Goog-Upload-Header-Content-Type", this.contentType);
    }

    @NonNull
    protected String getURL() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sUploadUrl);
        stringBuilder.append(this.mGsUri.getAuthority());
        stringBuilder.append("/o");
        return stringBuilder.toString();
    }

    @NonNull
    protected String getQueryParameters() throws UnsupportedEncodingException {
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        String pathWithoutBucket = getPathWithoutBucket();
        arrayList.add(ConditionalUserProperty.NAME);
        arrayList2.add(pathWithoutBucket != null ? SlashUtil.unSlashize(pathWithoutBucket) : "");
        arrayList.add("uploadType");
        arrayList2.add("resumable");
        return getPostDataString(arrayList, arrayList2, false);
    }

    @Nullable
    protected JSONObject getOutputJSON() {
        return this.metadata;
    }
}
