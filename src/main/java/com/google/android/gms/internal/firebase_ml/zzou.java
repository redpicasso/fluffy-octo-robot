package com.google.android.gms.internal.firebase_ml;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.drew.metadata.exif.makernotes.OlympusRawInfoMakernoteDirectory;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.util.IOUtils;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseRemoteModel;
import com.google.logging.type.LogSeverity;
import java.io.InputStream;
import java.util.Locale;
import javax.net.ssl.HttpsURLConnection;

@WorkerThread
public final class zzou {
    private static final GmsLogger zzaoz = new GmsLogger("RmModelInfoRetriever", "");

    @WorkerThread
    @Nullable
    static zzop zzb(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseRemoteModel firebaseRemoteModel, @NonNull zzon zzon) throws FirebaseMLException {
        if (firebaseRemoteModel.zzmk()) {
            return zzof.zza(firebaseRemoteModel, zzon);
        }
        return zzom.zza(firebaseApp, firebaseRemoteModel, zzon);
    }

    @Nullable
    static HttpsURLConnection zza(@Nullable String str, @NonNull zzon zzon) throws FirebaseMLException {
        if (str == null) {
            return null;
        }
        try {
            zzoy zzoy = new zzoy(str);
            GmsLogger gmsLogger = zzaoz;
            String str2 = "RmModelInfoRetriever";
            String str3 = "Checking model URL: ";
            str = String.valueOf(str);
            gmsLogger.d(str2, str.length() != 0 ? str3.concat(str) : new String(str3));
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) zzoy.openConnection();
            httpsURLConnection.setConnectTimeout(CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE);
            httpsURLConnection.connect();
            if (httpsURLConnection.getResponseCode() == LogSeverity.INFO_VALUE || httpsURLConnection.getResponseCode() == OlympusRawInfoMakernoteDirectory.TagWbRbLevelsDaylightFluor) {
                return httpsURLConnection;
            }
            String str4;
            zzon.zza(zzmk.MODEL_INFO_DOWNLOAD_UNSUCCESSFUL_HTTP_STATUS, false);
            InputStream errorStream = httpsURLConnection.getErrorStream();
            if (errorStream == null) {
                str4 = "";
            } else {
                str4 = new String(IOUtils.readInputStreamFully(errorStream));
            }
            throw new FirebaseMLException(String.format(Locale.getDefault(), "Failed to connect to Firebase ML console server with HTTP status code: %d and error message: %s", new Object[]{Integer.valueOf(httpsURLConnection.getResponseCode()), str4}), 13);
        } catch (Throwable e) {
            zzon.zza(zzmk.TIME_OUT_FETCHING_MODEL_METADATA, false);
            throw new FirebaseMLException("Failed to get model URL due to time out", 13, e);
        } catch (Throwable e2) {
            zzon.zza(zzmk.MODEL_INFO_DOWNLOAD_CONNECTION_FAILED, false);
            throw new FirebaseMLException("Failed to get model URL", 13, e2);
        }
    }
}
