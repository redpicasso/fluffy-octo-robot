package com.google.firebase.storage.network;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.common.net.HttpHeaders;
import com.google.firebase.FirebaseApp;
import java.io.UnsupportedEncodingException;
import java.util.Collections;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class GetNetworkRequest extends NetworkRequest {
    private static final String TAG = "GetNetworkRequest";

    @NonNull
    protected String getAction() {
        return "GET";
    }

    public GetNetworkRequest(@NonNull Uri uri, @NonNull FirebaseApp firebaseApp, long j) {
        super(uri, firebaseApp);
        if (j != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("bytes=");
            stringBuilder.append(j);
            stringBuilder.append("-");
            super.setCustomHeader(HttpHeaders.RANGE, stringBuilder.toString());
        }
    }

    @NonNull
    protected String getQueryParameters() throws UnsupportedEncodingException {
        return getPostDataString(Collections.singletonList("alt"), Collections.singletonList("media"), true);
    }
}
