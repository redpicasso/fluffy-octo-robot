package com.google.firebase.iid;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.VisibleForTesting;
import com.brentvatne.react.ReactVideoView;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.internal.LibraryVersion;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseApp;
import com.google.firebase.platforminfo.UserAgentPublisher;
import java.io.IOException;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
final class zzl {
    private final FirebaseApp zza;
    private final zzai zzb;
    private final zzao zzc;
    private final Executor zzd;
    private final UserAgentPublisher zze;

    zzl(FirebaseApp firebaseApp, zzai zzai, Executor executor, UserAgentPublisher userAgentPublisher) {
        this(firebaseApp, zzai, executor, new zzao(firebaseApp.getApplicationContext(), zzai), userAgentPublisher);
    }

    @VisibleForTesting
    private zzl(FirebaseApp firebaseApp, zzai zzai, Executor executor, zzao zzao, UserAgentPublisher userAgentPublisher) {
        this.zza = firebaseApp;
        this.zzb = zzai;
        this.zzc = zzao;
        this.zzd = executor;
        this.zze = userAgentPublisher;
    }

    public final Task<String> zza(String str, String str2, String str3) {
        return zzb(zza(str, str2, str3, new Bundle()));
    }

    public final Task<Void> zzb(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("delete", "1");
        return zza(zzb(zza(str, str2, str3, bundle)));
    }

    public final Task<Void> zza(String str) {
        Bundle bundle = new Bundle();
        String str2 = "delete";
        bundle.putString("iid-operation", str2);
        bundle.putString(str2, "1");
        str2 = "*";
        return zza(zzb(zza(str, str2, str2, bundle)));
    }

    public final Task<Void> zzc(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        String valueOf = String.valueOf(str3);
        String str4 = "/topics/";
        bundle.putString("gcm.topic", valueOf.length() != 0 ? str4.concat(valueOf) : new String(str4));
        str3 = String.valueOf(str3);
        return zza(zzb(zza(str, str2, str3.length() != 0 ? str4.concat(str3) : new String(str4), bundle)));
    }

    public final Task<Void> zzd(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        String valueOf = String.valueOf(str3);
        String str4 = "/topics/";
        bundle.putString("gcm.topic", valueOf.length() != 0 ? str4.concat(valueOf) : new String(str4));
        bundle.putString("delete", "1");
        str3 = String.valueOf(str3);
        return zza(zzb(zza(str, str2, str3.length() != 0 ? str4.concat(str3) : new String(str4), bundle)));
    }

    private final Task<Bundle> zza(String str, String str2, String str3, Bundle bundle) {
        bundle.putString("scope", str3);
        bundle.putString("sender", str2);
        bundle.putString("subtype", str2);
        bundle.putString("appid", str);
        bundle.putString("gmp_app_id", this.zza.getOptions().getApplicationId());
        bundle.putString("gmsv", Integer.toString(this.zzb.zzd()));
        bundle.putString("osv", Integer.toString(VERSION.SDK_INT));
        bundle.putString("app_ver", this.zzb.zzb());
        bundle.putString("app_ver_name", this.zzb.zzc());
        Object version = LibraryVersion.getInstance().getVersion("firebase-iid");
        if ("UNKNOWN".equals(version)) {
            int i = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
            StringBuilder stringBuilder = new StringBuilder(19);
            stringBuilder.append("unknown_");
            stringBuilder.append(i);
            version = stringBuilder.toString();
        }
        str2 = "fiid-";
        str = String.valueOf(version);
        bundle.putString("cliv", str.length() != 0 ? str2.concat(str) : new String(str2));
        bundle.putString("Firebase-Client", this.zze.getUserAgent());
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.zzd.execute(new zzn(this, bundle, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    private static String zza(Bundle bundle) throws IOException {
        String str = InstanceID.ERROR_SERVICE_NOT_AVAILABLE;
        if (bundle != null) {
            String string = bundle.getString("registration_id");
            if (string != null) {
                return string;
            }
            string = bundle.getString("unregistered");
            if (string != null) {
                return string;
            }
            string = bundle.getString(ReactVideoView.EVENT_PROP_ERROR);
            if ("RST".equals(string)) {
                throw new IOException("INSTANCE_ID_RESET");
            } else if (string != null) {
                throw new IOException(string);
            } else {
                String valueOf = String.valueOf(bundle);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 21);
                stringBuilder.append("Unexpected response: ");
                stringBuilder.append(valueOf);
                Log.w("FirebaseInstanceId", stringBuilder.toString(), new Throwable());
                throw new IOException(str);
            }
        }
        throw new IOException(str);
    }

    private final <T> Task<Void> zza(Task<T> task) {
        return task.continueWith(zza.zza(), new zzm(this));
    }

    private final Task<String> zzb(Task<Bundle> task) {
        return task.continueWith(this.zzd, new zzp(this));
    }
}
