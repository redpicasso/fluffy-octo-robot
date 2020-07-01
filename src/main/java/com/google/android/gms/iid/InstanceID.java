package com.google.android.gms.iid;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import java.io.IOException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Deprecated
public class InstanceID {
    public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
    public static final String ERROR_MISSING_INSTANCEID_SERVICE = "MISSING_INSTANCEID_SERVICE";
    public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    public static final String ERROR_TIMEOUT = "TIMEOUT";
    private static final zzaj<Boolean> zzbu = zzai.zzy().zzd("gcm_check_for_different_iid_in_token", true);
    private static Map<String, InstanceID> zzbv = new ArrayMap();
    private static final long zzbw = TimeUnit.DAYS.toMillis(7);
    private static zzak zzbx;
    private static zzaf zzby;
    private static String zzbz;
    private String zzca = "";
    private Context zzl;

    @ShowFirstParty
    private InstanceID(Context context, String str) {
        this.zzl = context.getApplicationContext();
        this.zzca = str;
    }

    static int zzg(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 38);
            stringBuilder.append("Never happens: can't find own package ");
            stringBuilder.append(valueOf);
            Log.w("InstanceID", stringBuilder.toString());
            return 0;
        }
    }

    static String zzh(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 38);
            stringBuilder.append("Never happens: can't find own package ");
            stringBuilder.append(valueOf);
            Log.w("InstanceID", stringBuilder.toString());
            return null;
        }
    }

    @Deprecated
    public static InstanceID getInstance(Context context) {
        return getInstance(context, null);
    }

    @KeepForSdk
    public static synchronized InstanceID getInstance(Context context, Bundle bundle) {
        InstanceID instanceID;
        synchronized (InstanceID.class) {
            String string = bundle == null ? "" : bundle.getString("subtype");
            if (string == null) {
                string = "";
            }
            context = context.getApplicationContext();
            if (zzbx == null) {
                String packageName = context.getPackageName();
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(packageName).length() + 73);
                stringBuilder.append("Instance ID SDK is deprecated, ");
                stringBuilder.append(packageName);
                stringBuilder.append(" should update to use Firebase Instance ID");
                Log.w("InstanceID", stringBuilder.toString());
                zzbx = new zzak(context);
                zzby = new zzaf(context);
            }
            zzbz = Integer.toString(zzg(context));
            instanceID = (InstanceID) zzbv.get(string);
            if (instanceID == null) {
                instanceID = new InstanceID(context, string);
                zzbv.put(string, instanceID);
            }
        }
        return instanceID;
    }

    private final KeyPair getKeyPair() {
        return zzbx.zzj(this.zzca).getKeyPair();
    }

    @KeepForSdk
    public String getSubtype() {
        return this.zzca;
    }

    @Deprecated
    public String getId() {
        return zzd(getKeyPair());
    }

    static String zzd(KeyPair keyPair) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(keyPair.getPublic().getEncoded());
            digest[0] = (byte) ((digest[0] & 15) + 112);
            return Base64.encodeToString(digest, 0, 8, 11);
        } catch (NoSuchAlgorithmException unused) {
            Log.w("InstanceID", "Unexpected error, device missing required algorithms");
            return null;
        }
    }

    @Deprecated
    public long getCreationTime() {
        return zzbx.zzj(this.zzca).getCreationTime();
    }

    @Deprecated
    public void deleteInstanceID() throws IOException {
        String str = "*";
        zzd(str, str, null);
        zzo();
    }

    final void zzo() {
        zzbx.zzk(this.zzca);
    }

    @Deprecated
    public void deleteToken(String str, String str2) throws IOException {
        zzd(str, str2, null);
    }

    @ShowFirstParty
    public final void zzd(String str, String str2, Bundle bundle) throws IOException {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            zzbx.zzh(this.zzca, str, str2);
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString("sender", str);
            if (str2 != null) {
                bundle.putString("scope", str2);
            }
            bundle.putString("subscription", str);
            str2 = "1";
            bundle.putString("delete", str2);
            bundle.putString("X-delete", str2);
            String str3 = "";
            bundle.putString("subtype", str3.equals(this.zzca) ? str : this.zzca);
            if (!str3.equals(this.zzca)) {
                str = this.zzca;
            }
            bundle.putString("X-subtype", str);
            zzaf.zzi(zzby.zzd(bundle, getKeyPair()));
            return;
        }
        throw new IOException(ERROR_MAIN_THREAD);
    }

    public static zzak zzp() {
        return zzbx;
    }

    @Deprecated
    public String getToken(String str, String str2) throws IOException {
        return getToken(str, str2, null);
    }

    @Deprecated
    public String getToken(String str, String str2, Bundle bundle) throws IOException {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            String str3 = null;
            String str4 = zzbx.get("appVersion");
            Object obj = 1;
            if (str4 != null && str4.equals(zzbz)) {
                long zzg = zzbx.zzg(this.zzca, str, str2);
                if (zzg >= 0 && System.currentTimeMillis() - zzg < zzbw) {
                    obj = null;
                }
            }
            if (obj == null) {
                str3 = zzbx.zzf(this.zzca, str, str2);
            }
            if (str3 != null) {
                return str3;
            }
            if (bundle == null) {
                bundle = new Bundle();
            }
            String zze = zze(str, str2, bundle);
            if (((Boolean) zzbu.get()).booleanValue()) {
                Object obj2 = ":";
                if (zze.contains(obj2) && !zze.startsWith(String.valueOf(getId()).concat(obj2))) {
                    InstanceIDListenerService.zzd(this.zzl, zzbx);
                    throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
                }
            }
            if (zze == null) {
                return zze;
            }
            zzbx.zzd(this.zzca, str, str2, zze, zzbz);
            return zze;
        }
        throw new IOException(ERROR_MAIN_THREAD);
    }

    public final String zze(String str, String str2, Bundle bundle) throws IOException {
        if (str2 != null) {
            bundle.putString("scope", str2);
        }
        bundle.putString("sender", str);
        str2 = "".equals(this.zzca) ? str : this.zzca;
        if (!bundle.containsKey("legacy.register")) {
            bundle.putString("subscription", str);
            bundle.putString("subtype", str2);
            bundle.putString("X-subscription", str);
            bundle.putString("X-subtype", str2);
        }
        str = zzaf.zzi(zzby.zzd(bundle, getKeyPair()));
        if (!"RST".equals(str) && !str.startsWith("RST|")) {
            return str;
        }
        InstanceIDListenerService.zzd(this.zzl, zzbx);
        throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
    }
}
