package com.google.android.gms.internal.firebase_ml;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.common.net.HttpHeaders;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseRemoteModel;
import javax.net.ssl.HttpsURLConnection;

@WorkerThread
final class zzof {
    private static final GmsLogger zzaoz = new GmsLogger("BaseModelInfoRetriever", "");

    @Nullable
    static zzop zza(@NonNull FirebaseRemoteModel firebaseRemoteModel, @NonNull zzon zzon) throws FirebaseMLException {
        HttpsURLConnection zza = zzou.zza(String.format("https://mlkit.googleapis.com/_i/v1/1p/m?n=%s", new Object[]{firebaseRemoteModel.zzmj()}), zzon);
        if (zza == null) {
            return null;
        }
        String headerField = zza.getHeaderField(HttpHeaders.CONTENT_LOCATION);
        String headerField2 = zza.getHeaderField(HttpHeaders.ETAG);
        GmsLogger gmsLogger = zzaoz;
        String str = "Received download URL: ";
        String valueOf = String.valueOf(headerField);
        gmsLogger.d("BaseModelInfoRetriever", valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        if (headerField == null) {
            return null;
        }
        if (headerField2 == null) {
            zzon.zza(zzmk.MODEL_INFO_DOWNLOAD_NO_HASH, false);
            throw new FirebaseMLException("No hash value for the base model", 13);
        } else if (firebaseRemoteModel.zzcd(headerField2)) {
            firebaseRemoteModel.zzce(headerField2);
            return new zzop(firebaseRemoteModel.zzmj(), Uri.parse(headerField), headerField2, zzok.BASE);
        } else {
            throw new FirebaseMLException("Downloaded model hash doesn't match the expected. ", 13);
        }
    }
}
