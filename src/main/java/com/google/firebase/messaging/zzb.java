package com.google.firebase.messaging;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.google.common.primitives.Ints;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: com.google.firebase:firebase-messaging@@20.0.0 */
public final class zzb {
    private static final AtomicInteger zza = new AtomicInteger((int) SystemClock.elapsedRealtime());

    /* JADX WARNING: Removed duplicated region for block: B:61:0x0207  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0217  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0248  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0257  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0264  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x026e  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0280  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0289  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0293  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x02a7  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x01bd  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x01ce  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0207  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0217  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0248  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0257  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0264  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x026e  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0280  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0289  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0293  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x02a7  */
    static com.google.firebase.messaging.zza zza(android.content.Context r9, com.google.firebase.messaging.zzk r10) {
        /*
        r0 = r9.getPackageManager();
        r1 = r9.getPackageName();
        r0 = zza(r0, r1);
        r1 = r9.getPackageName();
        r2 = "gcm.n.android_channel_id";
        r2 = r10.zza(r2);
        r2 = zzb(r9, r2, r0);
        r3 = r9.getResources();
        r4 = r9.getPackageManager();
        r5 = new androidx.core.app.NotificationCompat$Builder;
        r5.<init>(r9, r2);
        r2 = zza(r1, r10, r4, r3);
        r5.setContentTitle(r2);
        r2 = "gcm.n.body";
        r2 = r10.zza(r3, r1, r2);
        r6 = android.text.TextUtils.isEmpty(r2);
        if (r6 != 0) goto L_0x0049;
    L_0x003a:
        r5.setContentText(r2);
        r6 = new androidx.core.app.NotificationCompat$BigTextStyle;
        r6.<init>();
        r2 = r6.bigText(r2);
        r5.setStyle(r2);
    L_0x0049:
        r2 = "gcm.n.icon";
        r2 = r10.zza(r2);
        r2 = zza(r4, r3, r1, r2, r0);
        r5.setSmallIcon(r2);
        r2 = r10.zzb();
        r6 = android.text.TextUtils.isEmpty(r2);
        r7 = 2;
        r8 = 0;
        if (r6 == 0) goto L_0x0064;
    L_0x0062:
        r2 = r8;
        goto L_0x00a9;
    L_0x0064:
        r6 = "default";
        r6 = r6.equals(r2);
        if (r6 != 0) goto L_0x00a5;
    L_0x006c:
        r6 = "raw";
        r3 = r3.getIdentifier(r2, r6, r1);
        if (r3 == 0) goto L_0x00a5;
    L_0x0074:
        r3 = java.lang.String.valueOf(r1);
        r3 = r3.length();
        r3 = r3 + 24;
        r6 = java.lang.String.valueOf(r2);
        r6 = r6.length();
        r3 = r3 + r6;
        r6 = new java.lang.StringBuilder;
        r6.<init>(r3);
        r3 = "android.resource://";
        r6.append(r3);
        r6.append(r1);
        r3 = "/raw/";
        r6.append(r3);
        r6.append(r2);
        r2 = r6.toString();
        r2 = android.net.Uri.parse(r2);
        goto L_0x00a9;
    L_0x00a5:
        r2 = android.media.RingtoneManager.getDefaultUri(r7);
    L_0x00a9:
        if (r2 == 0) goto L_0x00ae;
    L_0x00ab:
        r5.setSound(r2);
    L_0x00ae:
        r2 = "gcm.n.click_action";
        r2 = r10.zza(r2);
        r3 = android.text.TextUtils.isEmpty(r2);
        r6 = "FirebaseMessaging";
        if (r3 != 0) goto L_0x00ca;
    L_0x00bc:
        r3 = new android.content.Intent;
        r3.<init>(r2);
        r3.setPackage(r1);
        r1 = 268435456; // 0x10000000 float:2.5243549E-29 double:1.32624737E-315;
        r3.setFlags(r1);
        goto L_0x00ea;
    L_0x00ca:
        r2 = r10.zza();
        if (r2 == 0) goto L_0x00de;
    L_0x00d0:
        r3 = new android.content.Intent;
        r4 = "android.intent.action.VIEW";
        r3.<init>(r4);
        r3.setPackage(r1);
        r3.setData(r2);
        goto L_0x00ea;
    L_0x00de:
        r1 = r4.getLaunchIntentForPackage(r1);
        if (r1 != 0) goto L_0x00e9;
    L_0x00e4:
        r2 = "No activity found to launch app";
        android.util.Log.w(r6, r2);
    L_0x00e9:
        r3 = r1;
    L_0x00ea:
        r1 = "google.c.a.e";
        if (r3 != 0) goto L_0x00f0;
    L_0x00ee:
        r2 = r8;
        goto L_0x0127;
    L_0x00f0:
        r2 = 67108864; // 0x4000000 float:1.5046328E-36 double:3.31561842E-316;
        r3.addFlags(r2);
        r2 = r10.zze();
        r3.putExtras(r2);
        r2 = zza;
        r2 = r2.incrementAndGet();
        r4 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r2 = android.app.PendingIntent.getActivity(r9, r2, r3, r4);
        r3 = r10.zzb(r1);
        if (r3 == 0) goto L_0x0127;
    L_0x010e:
        r3 = new android.content.Intent;
        r4 = "com.google.firebase.messaging.NOTIFICATION_OPEN";
        r3.<init>(r4);
        r4 = r10.zzf();
        r3 = r3.putExtras(r4);
        r4 = "pending_intent";
        r2 = r3.putExtra(r4, r2);
        r2 = zza(r9, r2);
    L_0x0127:
        r5.setContentIntent(r2);
        r1 = r10.zzb(r1);
        if (r1 != 0) goto L_0x0132;
    L_0x0130:
        r1 = r8;
        goto L_0x0145;
    L_0x0132:
        r1 = new android.content.Intent;
        r2 = "com.google.firebase.messaging.NOTIFICATION_DISMISS";
        r1.<init>(r2);
        r2 = r10.zzf();
        r1 = r1.putExtras(r2);
        r1 = zza(r9, r1);
    L_0x0145:
        if (r1 == 0) goto L_0x014a;
    L_0x0147:
        r5.setDeleteIntent(r1);
    L_0x014a:
        r1 = "gcm.n.color";
        r1 = r10.zza(r1);
        r9 = zza(r9, r1, r0);
        if (r9 == 0) goto L_0x015d;
    L_0x0156:
        r9 = r9.intValue();
        r5.setColor(r9);
    L_0x015d:
        r9 = "gcm.n.sticky";
        r9 = r10.zzb(r9);
        r0 = 1;
        r9 = r9 ^ r0;
        r5.setAutoCancel(r9);
        r9 = "gcm.n.local_only";
        r9 = r10.zzb(r9);
        r5.setLocalOnly(r9);
        r9 = "gcm.n.ticker";
        r9 = r10.zza(r9);
        if (r9 == 0) goto L_0x017c;
    L_0x0179:
        r5.setTicker(r9);
    L_0x017c:
        r9 = "gcm.n.notification_priority";
        r9 = r10.zzc(r9);
        if (r9 != 0) goto L_0x0186;
    L_0x0184:
        r9 = r8;
        goto L_0x01bb;
    L_0x0186:
        r1 = r9.intValue();
        r2 = -2;
        if (r1 < r2) goto L_0x0193;
    L_0x018d:
        r1 = r9.intValue();
        if (r1 <= r7) goto L_0x01bb;
    L_0x0193:
        r9 = java.lang.String.valueOf(r9);
        r1 = java.lang.String.valueOf(r9);
        r1 = r1.length();
        r1 = r1 + 72;
        r2 = new java.lang.StringBuilder;
        r2.<init>(r1);
        r1 = "notificationPriority is invalid ";
        r2.append(r1);
        r2.append(r9);
        r9 = ". Skipping setting notificationPriority.";
        r2.append(r9);
        r9 = r2.toString();
        android.util.Log.w(r6, r9);
        goto L_0x0184;
    L_0x01bb:
        if (r9 == 0) goto L_0x01c4;
    L_0x01bd:
        r9 = r9.intValue();
        r5.setPriority(r9);
    L_0x01c4:
        r9 = "gcm.n.visibility";
        r9 = r10.zzc(r9);
        if (r9 != 0) goto L_0x01ce;
    L_0x01cc:
        r9 = r8;
        goto L_0x0205;
    L_0x01ce:
        r1 = r9.intValue();
        r2 = -1;
        if (r1 < r2) goto L_0x01db;
    L_0x01d5:
        r1 = r9.intValue();
        if (r1 <= r0) goto L_0x0205;
    L_0x01db:
        r9 = java.lang.String.valueOf(r9);
        r1 = java.lang.String.valueOf(r9);
        r1 = r1.length();
        r1 = r1 + 53;
        r2 = new java.lang.StringBuilder;
        r2.<init>(r1);
        r1 = "visibility is invalid: ";
        r2.append(r1);
        r2.append(r9);
        r9 = ". Skipping setting visibility.";
        r2.append(r9);
        r9 = r2.toString();
        r1 = "NotificationParams";
        android.util.Log.w(r1, r9);
        goto L_0x01cc;
    L_0x0205:
        if (r9 == 0) goto L_0x020e;
    L_0x0207:
        r9 = r9.intValue();
        r5.setVisibility(r9);
    L_0x020e:
        r9 = "gcm.n.notification_count";
        r9 = r10.zzc(r9);
        if (r9 != 0) goto L_0x0217;
    L_0x0216:
        goto L_0x0246;
    L_0x0217:
        r1 = r9.intValue();
        if (r1 >= 0) goto L_0x0245;
    L_0x021d:
        r9 = java.lang.String.valueOf(r9);
        r1 = java.lang.String.valueOf(r9);
        r1 = r1.length();
        r1 = r1 + 67;
        r2 = new java.lang.StringBuilder;
        r2.<init>(r1);
        r1 = "notificationCount is invalid: ";
        r2.append(r1);
        r2.append(r9);
        r9 = ". Skipping setting notificationCount.";
        r2.append(r9);
        r9 = r2.toString();
        android.util.Log.w(r6, r9);
        goto L_0x0246;
    L_0x0245:
        r8 = r9;
    L_0x0246:
        if (r8 == 0) goto L_0x024f;
    L_0x0248:
        r9 = r8.intValue();
        r5.setNumber(r9);
    L_0x024f:
        r9 = "gcm.n.event_time";
        r9 = r10.zzd(r9);
        if (r9 == 0) goto L_0x025e;
    L_0x0257:
        r1 = r9.longValue();
        r5.setWhen(r1);
    L_0x025e:
        r9 = r10.zzc();
        if (r9 == 0) goto L_0x0267;
    L_0x0264:
        r5.setVibrate(r9);
    L_0x0267:
        r9 = r10.zzd();
        r1 = 0;
        if (r9 == 0) goto L_0x0277;
    L_0x026e:
        r2 = r9[r1];
        r3 = r9[r0];
        r9 = r9[r7];
        r5.setLights(r2, r3, r9);
    L_0x0277:
        r9 = "gcm.n.default_sound";
        r9 = r10.zzb(r9);
        if (r9 == 0) goto L_0x0280;
    L_0x027f:
        goto L_0x0281;
    L_0x0280:
        r0 = 0;
    L_0x0281:
        r9 = "gcm.n.default_vibrate_timings";
        r9 = r10.zzb(r9);
        if (r9 == 0) goto L_0x028b;
    L_0x0289:
        r0 = r0 | 2;
    L_0x028b:
        r9 = "gcm.n.default_light_settings";
        r9 = r10.zzb(r9);
        if (r9 == 0) goto L_0x0295;
    L_0x0293:
        r0 = r0 | 4;
    L_0x0295:
        r5.setDefaults(r0);
        r9 = new com.google.firebase.messaging.zza;
        r0 = "gcm.n.tag";
        r10 = r10.zza(r0);
        r0 = android.text.TextUtils.isEmpty(r10);
        if (r0 != 0) goto L_0x02a7;
    L_0x02a6:
        goto L_0x02be;
    L_0x02a7:
        r2 = android.os.SystemClock.uptimeMillis();
        r10 = 37;
        r0 = new java.lang.StringBuilder;
        r0.<init>(r10);
        r10 = "FCM-Notification:";
        r0.append(r10);
        r0.append(r2);
        r10 = r0.toString();
    L_0x02be:
        r9.<init>(r5, r10, r1);
        return r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.zzb.zza(android.content.Context, com.google.firebase.messaging.zzk):com.google.firebase.messaging.zza");
    }

    @NonNull
    private static CharSequence zza(String str, zzk zzk, PackageManager packageManager, Resources resources) {
        CharSequence zza = zzk.zza(resources, str, "gcm.n.title");
        if (!TextUtils.isEmpty(zza)) {
            return zza;
        }
        try {
            return packageManager.getApplicationInfo(str, 0).loadLabel(packageManager);
        } catch (NameNotFoundException e) {
            str = String.valueOf(e);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 35);
            stringBuilder.append("Couldn't get own application info: ");
            stringBuilder.append(str);
            Log.e("FirebaseMessaging", stringBuilder.toString());
            return "";
        }
    }

    @TargetApi(26)
    private static boolean zza(Resources resources, int i) {
        String str = "FirebaseMessaging";
        if (VERSION.SDK_INT != 26) {
            return true;
        }
        StringBuilder stringBuilder;
        try {
            if (!(resources.getDrawable(i, null) instanceof AdaptiveIconDrawable)) {
                return true;
            }
            stringBuilder = new StringBuilder(77);
            stringBuilder.append("Adaptive icons cannot be used in notifications. Ignoring icon id: ");
            stringBuilder.append(i);
            Log.e(str, stringBuilder.toString());
            return false;
        } catch (NotFoundException unused) {
            stringBuilder = new StringBuilder(66);
            stringBuilder.append("Couldn't find resource ");
            stringBuilder.append(i);
            stringBuilder.append(", treating it as an invalid icon");
            Log.e(str, stringBuilder.toString());
            return false;
        }
    }

    private static int zza(PackageManager packageManager, Resources resources, String str, String str2, Bundle bundle) {
        String str3 = "FirebaseMessaging";
        if (!TextUtils.isEmpty(str2)) {
            int identifier = resources.getIdentifier(str2, "drawable", str);
            if (identifier != 0 && zza(resources, identifier)) {
                return identifier;
            }
            identifier = resources.getIdentifier(str2, "mipmap", str);
            if (identifier != 0 && zza(resources, identifier)) {
                return identifier;
            }
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str2).length() + 61);
            stringBuilder.append("Icon resource ");
            stringBuilder.append(str2);
            stringBuilder.append(" not found. Notification will use default icon.");
            Log.w(str3, stringBuilder.toString());
        }
        int i = bundle.getInt("com.google.firebase.messaging.default_notification_icon", 0);
        if (i == 0 || !zza(resources, i)) {
            try {
                i = packageManager.getApplicationInfo(str, 0).icon;
            } catch (NameNotFoundException e) {
                String valueOf = String.valueOf(e);
                StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(valueOf).length() + 35);
                stringBuilder2.append("Couldn't get own application info: ");
                stringBuilder2.append(valueOf);
                Log.w(str3, stringBuilder2.toString());
            }
        }
        if (i == 0 || !zza(resources, i)) {
            i = 17301651;
        }
        return i;
    }

    private static Integer zza(Context context, String str, Bundle bundle) {
        if (VERSION.SDK_INT < 21) {
            return null;
        }
        String str2 = "FirebaseMessaging";
        if (!TextUtils.isEmpty(str)) {
            try {
                context = Integer.valueOf(Color.parseColor(str));
                return context;
            } catch (IllegalArgumentException unused) {
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 56);
                stringBuilder.append("Color is invalid: ");
                stringBuilder.append(str);
                stringBuilder.append(". Notification will use default color.");
                Log.w(str2, stringBuilder.toString());
            }
        }
        int i = bundle.getInt("com.google.firebase.messaging.default_notification_color", 0);
        if (i != 0) {
            try {
                return Integer.valueOf(ContextCompat.getColor(context, i));
            } catch (NotFoundException unused2) {
                Log.w(str2, "Cannot find the color resource referenced in AndroidManifest.");
            }
        }
        return null;
    }

    private static Bundle zza(PackageManager packageManager, String str) {
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 128);
            if (!(applicationInfo == null || applicationInfo.metaData == null)) {
                return applicationInfo.metaData;
            }
        } catch (NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 35);
            stringBuilder.append("Couldn't get own application info: ");
            stringBuilder.append(valueOf);
            Log.w("FirebaseMessaging", stringBuilder.toString());
        }
        return Bundle.EMPTY;
    }

    @TargetApi(26)
    private static String zzb(Context context, String str, Bundle bundle) {
        if (VERSION.SDK_INT < 26) {
            return null;
        }
        try {
            if (context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).targetSdkVersion < 26) {
                return null;
            }
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
            String str2 = "FirebaseMessaging";
            if (!TextUtils.isEmpty(str)) {
                if (notificationManager.getNotificationChannel(str) != null) {
                    return str;
                }
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 122);
                stringBuilder.append("Notification Channel requested (");
                stringBuilder.append(str);
                stringBuilder.append(") has not been created by the app. Manifest configuration, or default, value will be used.");
                Log.w(str2, stringBuilder.toString());
            }
            str = bundle.getString("com.google.firebase.messaging.default_notification_channel_id");
            if (TextUtils.isEmpty(str)) {
                Log.w(str2, "Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
            } else if (notificationManager.getNotificationChannel(str) != null) {
                return str;
            } else {
                Log.w(str2, "Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
            }
            str = "fcm_fallback_notification_channel";
            if (notificationManager.getNotificationChannel(str) == null) {
                notificationManager.createNotificationChannel(new NotificationChannel(str, context.getString(context.getResources().getIdentifier("fcm_fallback_notification_channel_label", "string", context.getPackageName())), 3));
            }
            return str;
        } catch (NameNotFoundException unused) {
            return null;
        }
    }

    private static PendingIntent zza(Context context, Intent intent) {
        return PendingIntent.getBroadcast(context, zza.incrementAndGet(), new Intent("com.google.firebase.MESSAGING_EVENT").setComponent(new ComponentName(context, "com.google.firebase.iid.FirebaseInstanceIdReceiver")).putExtra("wrapped_intent", intent), Ints.MAX_POWER_OF_TWO);
    }
}
