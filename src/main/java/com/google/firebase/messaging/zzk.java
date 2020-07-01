package com.google.firebase.messaging;

import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: com.google.firebase:firebase-messaging@@20.0.0 */
public final class zzk {
    @NonNull
    private final Bundle zza;

    public zzk(@NonNull Bundle bundle) {
        if (bundle != null) {
            this.zza = new Bundle(bundle);
            return;
        }
        throw new NullPointerException("data");
    }

    public final String zza(String str) {
        Bundle bundle = this.zza;
        if (!bundle.containsKey(str) && str.startsWith("gcm.n.")) {
            String zzi = zzi(str);
            if (this.zza.containsKey(zzi)) {
                str = zzi;
            }
        }
        return bundle.getString(str);
    }

    public final boolean zzb(String str) {
        str = zza(str);
        return "1".equals(str) || Boolean.parseBoolean(str);
    }

    public final Integer zzc(String str) {
        String zza = zza(str);
        if (!TextUtils.isEmpty(zza)) {
            try {
                str = Integer.valueOf(Integer.parseInt(zza));
                return str;
            } catch (NumberFormatException unused) {
                str = zzh(str);
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 38) + String.valueOf(zza).length());
                stringBuilder.append("Couldn't parse value of ");
                stringBuilder.append(str);
                stringBuilder.append("(");
                stringBuilder.append(zza);
                stringBuilder.append(") into an int");
                Log.w("NotificationParams", stringBuilder.toString());
            }
        }
        return null;
    }

    public final Long zzd(String str) {
        String zza = zza(str);
        if (!TextUtils.isEmpty(zza)) {
            try {
                str = Long.valueOf(Long.parseLong(zza));
                return str;
            } catch (NumberFormatException unused) {
                str = zzh(str);
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 38) + String.valueOf(zza).length());
                stringBuilder.append("Couldn't parse value of ");
                stringBuilder.append(str);
                stringBuilder.append("(");
                stringBuilder.append(zza);
                stringBuilder.append(") into a long");
                Log.w("NotificationParams", stringBuilder.toString());
            }
        }
        return null;
    }

    @Nullable
    public final String zze(String str) {
        str = String.valueOf(str);
        String str2 = "_loc_key";
        return zza(str2.length() != 0 ? str.concat(str2) : new String(str));
    }

    @Nullable
    public final Object[] zzf(String str) {
        str = String.valueOf(str);
        String str2 = "_loc_args";
        JSONArray zzg = zzg(str2.length() != 0 ? str.concat(str2) : new String(str));
        if (zzg == null) {
            return null;
        }
        String[] strArr = new String[zzg.length()];
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = zzg.optString(i);
        }
        return strArr;
    }

    @Nullable
    private final JSONArray zzg(String str) {
        String zza = zza(str);
        if (!TextUtils.isEmpty(zza)) {
            try {
                return new JSONArray(zza);
            } catch (JSONException unused) {
                str = zzh(str);
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 50) + String.valueOf(zza).length());
                stringBuilder.append("Malformed JSON for key ");
                stringBuilder.append(str);
                stringBuilder.append(": ");
                stringBuilder.append(zza);
                stringBuilder.append(", falling back to default");
                Log.w("NotificationParams", stringBuilder.toString());
            }
        }
        return null;
    }

    private static String zzh(String str) {
        return str.startsWith("gcm.n.") ? str.substring(6) : str;
    }

    @Nullable
    public final Uri zza() {
        Object zza = zza("gcm.n.link_android");
        if (TextUtils.isEmpty(zza)) {
            zza = zza("gcm.n.link");
        }
        return !TextUtils.isEmpty(zza) ? Uri.parse(zza) : null;
    }

    @Nullable
    public final String zzb() {
        String zza = zza("gcm.n.sound2");
        return TextUtils.isEmpty(zza) ? zza("gcm.n.sound") : zza;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x002d A:{ExcHandler: org.json.JSONException (unused org.json.JSONException), Splitter: B:3:0x000a} */
    /* JADX WARNING: Missing block: B:13:0x002d, code:
            r0 = java.lang.String.valueOf(r0);
            r3 = new java.lang.StringBuilder(java.lang.String.valueOf(r0).length() + 74);
            r3.append("User defined vibrateTimings is invalid: ");
            r3.append(r0);
            r3.append(". Skipping setting vibrateTimings.");
            android.util.Log.w("NotificationParams", r3.toString());
     */
    /* JADX WARNING: Missing block: B:14:0x0056, code:
            return null;
     */
    @androidx.annotation.Nullable
    public final long[] zzc() {
        /*
        r6 = this;
        r0 = "gcm.n.vibrate_timings";
        r0 = r6.zzg(r0);
        r1 = 0;
        if (r0 != 0) goto L_0x000a;
    L_0x0009:
        return r1;
    L_0x000a:
        r2 = r0.length();	 Catch:{ JSONException -> 0x002d, JSONException -> 0x002d }
        r3 = 1;
        if (r2 <= r3) goto L_0x0025;
    L_0x0011:
        r2 = r0.length();	 Catch:{ JSONException -> 0x002d, JSONException -> 0x002d }
        r2 = new long[r2];	 Catch:{ JSONException -> 0x002d, JSONException -> 0x002d }
        r3 = 0;
    L_0x0018:
        r4 = r2.length;	 Catch:{ JSONException -> 0x002d, JSONException -> 0x002d }
        if (r3 >= r4) goto L_0x0024;
    L_0x001b:
        r4 = r0.optLong(r3);	 Catch:{ JSONException -> 0x002d, JSONException -> 0x002d }
        r2[r3] = r4;	 Catch:{ JSONException -> 0x002d, JSONException -> 0x002d }
        r3 = r3 + 1;
        goto L_0x0018;
    L_0x0024:
        return r2;
    L_0x0025:
        r2 = new org.json.JSONException;	 Catch:{ JSONException -> 0x002d, JSONException -> 0x002d }
        r3 = "vibrateTimings have invalid length";
        r2.<init>(r3);	 Catch:{ JSONException -> 0x002d, JSONException -> 0x002d }
        throw r2;	 Catch:{ JSONException -> 0x002d, JSONException -> 0x002d }
    L_0x002d:
        r0 = java.lang.String.valueOf(r0);
        r2 = java.lang.String.valueOf(r0);
        r2 = r2.length();
        r2 = r2 + 74;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
        r2 = "User defined vibrateTimings is invalid: ";
        r3.append(r2);
        r3.append(r0);
        r0 = ". Skipping setting vibrateTimings.";
        r3.append(r0);
        r0 = r3.toString();
        r2 = "NotificationParams";
        android.util.Log.w(r2, r0);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.zzk.zzc():long[]");
    }

    @Nullable
    final int[] zzd() {
        String valueOf;
        String str = ". Skipping setting LightSettings";
        String str2 = "LightSettings is invalid: ";
        String str3 = "NotificationParams";
        JSONArray zzg = zzg("gcm.n.light_settings");
        if (zzg == null) {
            return null;
        }
        int[] iArr = new int[3];
        try {
            if (zzg.length() == 3) {
                int parseColor = Color.parseColor(zzg.optString(0));
                if (parseColor != ViewCompat.MEASURED_STATE_MASK) {
                    iArr[0] = parseColor;
                    iArr[1] = zzg.optInt(1);
                    iArr[2] = zzg.optInt(2);
                    return iArr;
                }
                throw new IllegalArgumentException("Transparent color is invalid");
            }
            throw new JSONException("lightSettings don't have all three fields");
        } catch (JSONException unused) {
            valueOf = String.valueOf(zzg);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 58);
            stringBuilder.append(str2);
            stringBuilder.append(valueOf);
            stringBuilder.append(str);
            Log.w(str3, stringBuilder.toString());
            return null;
        } catch (IllegalArgumentException e) {
            valueOf = String.valueOf(zzg);
            String message = e.getMessage();
            StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(valueOf).length() + 60) + String.valueOf(message).length());
            stringBuilder2.append(str2);
            stringBuilder2.append(valueOf);
            stringBuilder2.append(". ");
            stringBuilder2.append(message);
            stringBuilder2.append(str);
            Log.w(str3, stringBuilder2.toString());
            return null;
        }
    }

    public final Bundle zze() {
        Bundle bundle = new Bundle(this.zza);
        for (String str : this.zza.keySet()) {
            Object obj = (str.startsWith("google.c.") || str.startsWith("gcm.n.") || str.startsWith("gcm.notification.")) ? 1 : null;
            if (obj != null) {
                bundle.remove(str);
            }
        }
        return bundle;
    }

    public final Bundle zzf() {
        Bundle bundle = new Bundle(this.zza);
        for (String str : this.zza.keySet()) {
            Object obj = (str.startsWith("google.c.a.") || str.equals("from")) ? 1 : null;
            if (obj == null) {
                bundle.remove(str);
            }
        }
        return bundle;
    }

    @Nullable
    private final String zzb(Resources resources, String str, String str2) {
        Object zze = zze(str2);
        if (TextUtils.isEmpty(zze)) {
            return null;
        }
        int identifier = resources.getIdentifier(zze, "string", str);
        String str3 = " Default value will be used.";
        String str4 = "NotificationParams";
        if (identifier == 0) {
            String valueOf = String.valueOf(str2);
            str = "_loc_key";
            valueOf = zzh(str.length() != 0 ? valueOf.concat(str) : new String(valueOf));
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 49) + String.valueOf(str2).length());
            stringBuilder.append(valueOf);
            stringBuilder.append(" resource not found: ");
            stringBuilder.append(str2);
            stringBuilder.append(str3);
            Log.w(str4, stringBuilder.toString());
            return null;
        }
        Object[] zzf = zzf(str2);
        if (zzf == null) {
            return resources.getString(identifier);
        }
        try {
            return resources.getString(identifier, zzf);
        } catch (Throwable e) {
            str = zzh(str2);
            str2 = Arrays.toString(zzf);
            StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(str).length() + 58) + String.valueOf(str2).length());
            stringBuilder2.append("Missing format argument for ");
            stringBuilder2.append(str);
            stringBuilder2.append(": ");
            stringBuilder2.append(str2);
            stringBuilder2.append(str3);
            Log.w(str4, stringBuilder2.toString(), e);
            return null;
        }
    }

    public final String zza(Resources resources, String str, String str2) {
        Object zza = zza(str2);
        if (TextUtils.isEmpty(zza)) {
            return zzb(resources, str, str2);
        }
        return zza;
    }

    public static boolean zza(Bundle bundle) {
        String str = "gcm.n.e";
        String str2 = "1";
        if (!(str2.equals(bundle.getString(str)) || str2.equals(bundle.getString(zzi(str))))) {
            str = "gcm.n.icon";
            if (bundle.getString(str) == null && bundle.getString(zzi(str)) == null) {
                return false;
            }
        }
        return true;
    }

    private static String zzi(String str) {
        CharSequence charSequence = "gcm.n.";
        if (str.startsWith(charSequence)) {
            return str.replace(charSequence, "gcm.notification.");
        }
        return str;
    }
}
