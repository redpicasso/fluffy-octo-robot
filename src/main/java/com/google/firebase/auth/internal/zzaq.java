package com.google.firebase.auth.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.internal.firebase_auth.zzay;
import com.google.android.gms.internal.firebase_auth.zzfm;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public final class zzaq {
    @VisibleForTesting
    private static long zzuq = 3600000;
    private static final zzay<String> zzur = zzay.zza("firebaseAppName", "firebaseUserUid", "operation", "tenantId", "verifyAssertionRequest", "statusCode", "statusMessage", "timestamp");
    private static final zzaq zzus = new zzaq();
    private Task<AuthResult> zzut;
    private long zzuu = 0;

    private zzaq() {
    }

    public static zzaq zzfp() {
        return zzus;
    }

    public static void zza(Context context, FirebaseAuth firebaseAuth) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(firebaseAuth);
        Editor edit = context.getSharedPreferences("com.google.firebase.auth.internal.ProcessDeathHelper", 0).edit();
        edit.putString("firebaseAppName", firebaseAuth.zzcu().getName());
        edit.commit();
    }

    public static void zza(Context context, FirebaseAuth firebaseAuth, FirebaseUser firebaseUser) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(firebaseAuth);
        Preconditions.checkNotNull(firebaseUser);
        Editor edit = context.getSharedPreferences("com.google.firebase.auth.internal.ProcessDeathHelper", 0).edit();
        edit.putString("firebaseAppName", firebaseAuth.zzcu().getName());
        edit.putString("firebaseUserUid", firebaseUser.getUid());
        edit.commit();
    }

    public static void zza(Context context, zzfm zzfm, String str, @Nullable String str2) {
        Editor edit = context.getSharedPreferences("com.google.firebase.auth.internal.ProcessDeathHelper", 0).edit();
        edit.putString("verifyAssertionRequest", SafeParcelableSerializer.serializeToString(zzfm));
        edit.putString("operation", str);
        edit.putString("tenantId", str2);
        edit.putLong("timestamp", DefaultClock.getInstance().currentTimeMillis());
        edit.commit();
    }

    public static void zza(Context context, Status status) {
        Editor edit = context.getSharedPreferences("com.google.firebase.auth.internal.ProcessDeathHelper", 0).edit();
        edit.putInt("statusCode", status.getStatusCode());
        edit.putString("statusMessage", status.getStatusMessage());
        edit.putLong("timestamp", DefaultClock.getInstance().currentTimeMillis());
        edit.commit();
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00e0  */
    /* JADX WARNING: Missing block: B:15:0x007f, code:
            if (r4.equals("com.google.firebase.auth.internal.SIGN_IN") == false) goto L_0x0096;
     */
    public final void zzg(com.google.firebase.auth.FirebaseAuth r12) {
        /*
        r11 = this;
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r12);
        r0 = r12.zzcu();
        r0 = r0.getApplicationContext();
        r1 = 0;
        r2 = "com.google.firebase.auth.internal.ProcessDeathHelper";
        r0 = r0.getSharedPreferences(r2, r1);
        r2 = "";
        r3 = "firebaseAppName";
        r3 = r0.getString(r3, r2);
        r4 = r12.zzcu();
        r4 = r4.getName();
        r3 = r4.equals(r3);
        if (r3 != 0) goto L_0x0029;
    L_0x0028:
        return;
    L_0x0029:
        r3 = "verifyAssertionRequest";
        r4 = r0.contains(r3);
        r5 = 0;
        r7 = "timestamp";
        if (r4 == 0) goto L_0x00ee;
    L_0x0035:
        r3 = r0.getString(r3, r2);
        r4 = com.google.android.gms.internal.firebase_auth.zzfm.CREATOR;
        r3 = com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer.deserializeFromString(r3, r4);
        r3 = (com.google.android.gms.internal.firebase_auth.zzfm) r3;
        r4 = "operation";
        r4 = r0.getString(r4, r2);
        r8 = 0;
        r9 = "tenantId";
        r9 = r0.getString(r9, r8);
        r10 = "firebaseUserUid";
        r2 = r0.getString(r10, r2);
        r5 = r0.getLong(r7, r5);
        r11.zzuu = r5;
        if (r9 == 0) goto L_0x0062;
    L_0x005c:
        r12.zzf(r9);
        r3.zzcz(r9);
    L_0x0062:
        r5 = -1;
        r6 = r4.hashCode();
        r7 = -1843829902; // 0xffffffff92196372 float:-4.8400863E-28 double:NaN;
        r9 = 2;
        r10 = 1;
        if (r6 == r7) goto L_0x008c;
    L_0x006e:
        r7 = -286760092; // 0xffffffffeee86364 float:-3.596034E28 double:NaN;
        if (r6 == r7) goto L_0x0082;
    L_0x0073:
        r7 = 1731327805; // 0x6731f73d float:8.404196E23 double:8.5538959E-315;
        if (r6 == r7) goto L_0x0079;
    L_0x0078:
        goto L_0x0096;
    L_0x0079:
        r6 = "com.google.firebase.auth.internal.SIGN_IN";
        r4 = r4.equals(r6);
        if (r4 == 0) goto L_0x0096;
    L_0x0081:
        goto L_0x0097;
    L_0x0082:
        r1 = "com.google.firebase.auth.internal.LINK";
        r1 = r4.equals(r1);
        if (r1 == 0) goto L_0x0096;
    L_0x008a:
        r1 = 1;
        goto L_0x0097;
    L_0x008c:
        r1 = "com.google.firebase.auth.internal.REAUTHENTICATE";
        r1 = r4.equals(r1);
        if (r1 == 0) goto L_0x0096;
    L_0x0094:
        r1 = 2;
        goto L_0x0097;
    L_0x0096:
        r1 = -1;
    L_0x0097:
        if (r1 == 0) goto L_0x00e0;
    L_0x0099:
        if (r1 == r10) goto L_0x00c0;
    L_0x009b:
        if (r1 == r9) goto L_0x00a0;
    L_0x009d:
        r11.zzut = r8;
        goto L_0x00ea;
    L_0x00a0:
        r1 = r12.getCurrentUser();
        r1 = r1.getUid();
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x00bd;
    L_0x00ae:
        r12 = r12.getCurrentUser();
        r1 = com.google.firebase.auth.zzf.zza(r3);
        r12 = r12.reauthenticateAndRetrieveData(r1);
        r11.zzut = r12;
        goto L_0x00ea;
    L_0x00bd:
        r11.zzut = r8;
        goto L_0x00ea;
    L_0x00c0:
        r1 = r12.getCurrentUser();
        r1 = r1.getUid();
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x00dd;
    L_0x00ce:
        r12 = r12.getCurrentUser();
        r1 = com.google.firebase.auth.zzf.zza(r3);
        r12 = r12.linkWithCredential(r1);
        r11.zzut = r12;
        goto L_0x00ea;
    L_0x00dd:
        r11.zzut = r8;
        goto L_0x00ea;
    L_0x00e0:
        r1 = com.google.firebase.auth.zzf.zza(r3);
        r12 = r12.signInWithCredential(r1);
        r11.zzut = r12;
    L_0x00ea:
        zza(r0);
        return;
    L_0x00ee:
        r12 = "statusCode";
        r1 = r0.contains(r12);
        if (r1 == 0) goto L_0x011a;
    L_0x00f6:
        r1 = 17062; // 0x42a6 float:2.3909E-41 double:8.4297E-320;
        r12 = r0.getInt(r12, r1);
        r1 = "statusMessage";
        r1 = r0.getString(r1, r2);
        r2 = new com.google.android.gms.common.api.Status;
        r2.<init>(r12, r1);
        r3 = r0.getLong(r7, r5);
        r11.zzuu = r3;
        zza(r0);
        r12 = com.google.firebase.auth.api.internal.zzdr.zzb(r2);
        r12 = com.google.android.gms.tasks.Tasks.forException(r12);
        r11.zzut = r12;
    L_0x011a:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.internal.zzaq.zzg(com.google.firebase.auth.FirebaseAuth):void");
    }

    public final Task<AuthResult> zzfo() {
        return DefaultClock.getInstance().currentTimeMillis() - this.zzuu < zzuq ? this.zzut : null;
    }

    public final void zza(Context context) {
        Preconditions.checkNotNull(context);
        zza(context.getSharedPreferences("com.google.firebase.auth.internal.ProcessDeathHelper", 0));
        this.zzut = null;
        this.zzuu = 0;
    }

    private static void zza(SharedPreferences sharedPreferences) {
        Editor edit = sharedPreferences.edit();
        zzay zzay = zzur;
        int size = zzay.size();
        int i = 0;
        while (i < size) {
            Object obj = zzay.get(i);
            i++;
            edit.remove((String) obj);
        }
        edit.commit();
    }
}
