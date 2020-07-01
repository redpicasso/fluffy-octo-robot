package com.google.android.gms.internal.firebase_ml;

import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.util.IOUtils;
import com.google.common.net.HttpHeaders;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseRemoteModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@WorkerThread
final class zzom {
    private static final GmsLogger zzaoz = new GmsLogger("ModelInfoRetriever", "");

    @Nullable
    static zzop zza(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseRemoteModel firebaseRemoteModel, @NonNull zzon zzon) throws FirebaseMLException {
        Throwable e;
        String str = "inferenceInfo";
        HttpsURLConnection zza = zzou.zza(zza(firebaseApp, firebaseRemoteModel.getModelName(), zzon), zzon);
        if (zza == null) {
            return null;
        }
        String headerField = zza.getHeaderField(HttpHeaders.CONTENT_LOCATION);
        String headerField2 = zza.getHeaderField(HttpHeaders.ETAG);
        GmsLogger gmsLogger = zzaoz;
        String str2 = "Received download URL: ";
        String valueOf = String.valueOf(headerField);
        gmsLogger.d("ModelInfoRetriever", valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        if (headerField == null) {
            return null;
        }
        int i = 0;
        if (headerField2 != null) {
            firebaseRemoteModel.zzce(headerField2);
            try {
                String str3 = new String(IOUtils.readInputStreamFully(zza.getInputStream()));
                if (TextUtils.isEmpty(str3)) {
                    str3 = "{}";
                }
                JSONObject jSONObject = new JSONObject(str3);
                zzok zzok = jSONObject.has(str) ? zzok.AUTOML : zzok.CUSTOM;
                if (zzok.equals(zzok.AUTOML)) {
                    jSONObject = jSONObject.getJSONObject(str);
                    if (jSONObject != null) {
                        JSONArray jSONArray = jSONObject.getJSONArray("labels");
                        if (jSONArray == null || jSONArray.length() == 0) {
                            throw new FirebaseMLException("Cannot parse AutoML model's labels from model downloading backend.", 13);
                        }
                        List arrayList = new ArrayList();
                        while (i < jSONArray.length()) {
                            arrayList.add(jSONArray.getString(i));
                            i++;
                        }
                        zzob.zza(firebaseApp, firebaseRemoteModel.zzmj(), arrayList);
                    }
                }
                return new zzop(firebaseRemoteModel.zzmj(), Uri.parse(headerField), headerField2, zzok);
            } catch (IOException e2) {
                e = e2;
                throw new FirebaseMLException("Failed to parse the model backend response message", 13, e);
            } catch (JSONException e3) {
                e = e3;
                throw new FirebaseMLException("Failed to parse the model backend response message", 13, e);
            }
        }
        zzon.zza(zzmk.MODEL_INFO_DOWNLOAD_NO_HASH, false);
        throw new FirebaseMLException("No hash value for the custom model", 13);
    }

    @VisibleForTesting
    @Nullable
    private static String zza(FirebaseApp firebaseApp, String str, @NonNull zzon zzon) throws FirebaseMLException {
        String gcmSenderId = firebaseApp.getOptions().getGcmSenderId();
        if (gcmSenderId != null) {
            FirebaseInstanceId instance = FirebaseInstanceId.getInstance(firebaseApp);
            String str2 = "ModelInfoRetriever";
            if (instance == null) {
                zzaoz.w(str2, "Cannot get a valid instance of FirebaseInstanceId. Cannot retrieve model info.");
                return null;
            }
            if (instance.getId() == null) {
                zzaoz.w(str2, "Firebase instance id is null. Cannot retrieve model info.");
                return null;
            }
            try {
                zzon = instance.getToken(gcmSenderId, "*");
                if (zzon == null) {
                    zzaoz.w(str2, "Firebase instance token is null. Cannot retrieve model info.");
                    return null;
                }
                return String.format("https://mlkit.googleapis.com/v1beta1/projects/%s/models/%s/versions/active?key=%s&app_instance_id=%s&app_instance_token=%s", new Object[]{firebaseApp.getOptions().getProjectId(), str, firebaseApp.getOptions().getApiKey(), r4, zzon});
            } catch (Throwable e) {
                zzon.zza(zzmk.MODEL_INFO_DOWNLOAD_CONNECTION_FAILED, false);
                throw new FirebaseMLException("Failed to get model URL", 13, e);
            }
        }
        throw new FirebaseMLException("GCM sender id cannot be null in FirebaseOptions. Please configure FirebaseApp properly.", 9);
    }
}
