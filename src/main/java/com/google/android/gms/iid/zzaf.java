package com.google.android.gms.iid;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.collection.ArrayMap;
import com.brentvatne.react.ReactVideoView;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.util.PlatformVersion;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ShowFirstParty
public class zzaf {
    private static int zzcp = 0;
    private static final zzaj<Boolean> zzct = zzai.zzy().zzd("gcm_iid_use_messenger_ipc", true);
    private static String zzcu = null;
    private static boolean zzcv = false;
    private static int zzcw = 0;
    private static int zzcx = 0;
    @GuardedBy("Rpc.class")
    private static BroadcastReceiver zzcy = null;
    private PendingIntent zzaf;
    private Messenger zzaj;
    private Map<String, Object> zzcz = new ArrayMap();
    private Messenger zzda;
    private MessengerCompat zzdb;
    private Context zzl;

    public zzaf(Context context) {
        this.zzl = context;
    }

    @ShowFirstParty
    public static boolean zzk(Context context) {
        if (zzcu != null) {
            zzl(context);
        }
        return zzcv;
    }

    @ShowFirstParty
    public static String zzl(Context context) {
        String str = zzcu;
        if (str != null) {
            return str;
        }
        String str2;
        zzcw = Process.myUid();
        PackageManager packageManager = context.getPackageManager();
        boolean z = true;
        if (!PlatformVersion.isAtLeastO()) {
            Object obj;
            str2 = "com.google.android.c2dm.intent.REGISTER";
            for (ResolveInfo resolveInfo : packageManager.queryIntentServices(new Intent(str2), 0)) {
                if (zzd(packageManager, resolveInfo.serviceInfo.packageName, str2)) {
                    zzcv = false;
                    obj = 1;
                    break;
                }
            }
            obj = null;
            if (obj != null) {
                return zzcu;
            }
        }
        str2 = "com.google.iid.TOKEN_REQUEST";
        for (ResolveInfo resolveInfo2 : packageManager.queryBroadcastReceivers(new Intent(str2), 0)) {
            if (zzd(packageManager, resolveInfo2.activityInfo.packageName, str2)) {
                zzcv = true;
                break;
            }
        }
        z = false;
        if (z) {
            return zzcu;
        }
        str = "InstanceID";
        Log.w(str, "Failed to resolve IID implementation package, falling back");
        if (zzd(packageManager, "com.google.android.gms")) {
            zzcv = PlatformVersion.isAtLeastO();
            return zzcu;
        } else if (PlatformVersion.isAtLeastLollipop() || !zzd(packageManager, "com.google.android.gsf")) {
            Log.w(str, "Google Play services is missing, unable to get tokens");
            return null;
        } else {
            zzcv = false;
            return zzcu;
        }
    }

    private static boolean zzd(PackageManager packageManager, String str, String str2) {
        if (packageManager.checkPermission("com.google.android.c2dm.permission.SEND", str) == 0) {
            return zzd(packageManager, str);
        }
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 56) + String.valueOf(str2).length());
        stringBuilder.append("Possible malicious package ");
        stringBuilder.append(str);
        stringBuilder.append(" declares ");
        stringBuilder.append(str2);
        stringBuilder.append(" without permission");
        Log.w("InstanceID", stringBuilder.toString());
        return false;
    }

    private static boolean zzd(PackageManager packageManager, String str) {
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
            zzcu = applicationInfo.packageName;
            zzcx = applicationInfo.uid;
            return true;
        } catch (NameNotFoundException unused) {
            return false;
        }
    }

    private static int zzm(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(zzl(context), 0).versionCode;
        } catch (NameNotFoundException unused) {
            return -1;
        }
    }

    public final void zze(Message message) {
        if (message != null) {
            if (message.obj instanceof Intent) {
                Intent intent = (Intent) message.obj;
                intent.setExtrasClassLoader(MessengerCompat.class.getClassLoader());
                String str = "google.messenger";
                if (intent.hasExtra(str)) {
                    Parcelable parcelableExtra = intent.getParcelableExtra(str);
                    if (parcelableExtra instanceof MessengerCompat) {
                        this.zzdb = (MessengerCompat) parcelableExtra;
                    }
                    if (parcelableExtra instanceof Messenger) {
                        this.zzda = (Messenger) parcelableExtra;
                    }
                }
                zzh((Intent) message.obj);
                return;
            }
            Log.w("InstanceID", "Dropping invalid message");
        }
    }

    private final synchronized void zzg(Intent intent) {
        if (this.zzaf == null) {
            Intent intent2 = new Intent();
            intent2.setPackage("com.google.example.invalidpackage");
            this.zzaf = PendingIntent.getBroadcast(this.zzl, 0, intent2, 0);
        }
        intent.putExtra("app", this.zzaf);
    }

    static String zzi(Bundle bundle) throws IOException {
        String str = InstanceID.ERROR_SERVICE_NOT_AVAILABLE;
        if (bundle != null) {
            String string = bundle.getString("registration_id");
            if (string == null) {
                string = bundle.getString("unregistered");
            }
            if (string != null) {
                return string;
            }
            string = bundle.getString(ReactVideoView.EVENT_PROP_ERROR);
            if (string != null) {
                throw new IOException(string);
            }
            String valueOf = String.valueOf(bundle);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 29);
            stringBuilder.append("Unexpected response from GCM ");
            stringBuilder.append(valueOf);
            Log.w("InstanceID", stringBuilder.toString(), new Throwable());
            throw new IOException(str);
        }
        throw new IOException(str);
    }

    private final void zzd(String str, Object obj) {
        synchronized (getClass()) {
            Object obj2 = this.zzcz.get(str);
            this.zzcz.put(str, obj);
            zzd(obj2, obj);
        }
    }

    private static void zzd(Object obj, Object obj2) {
        if (obj instanceof ConditionVariable) {
            ((ConditionVariable) obj).open();
        }
        if (obj instanceof Messenger) {
            Messenger messenger = (Messenger) obj;
            Message obtain = Message.obtain();
            obtain.obj = obj2;
            try {
                messenger.send(obtain);
            } catch (RemoteException e) {
                String valueOf = String.valueOf(e);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 24);
                stringBuilder.append("Failed to send response ");
                stringBuilder.append(valueOf);
                Log.w("InstanceID", stringBuilder.toString());
            }
        }
    }

    public final void zzh(Intent intent) {
        if (intent == null) {
            if (Log.isLoggable("InstanceID", 3)) {
                Log.d("InstanceID", "Unexpected response: null");
            }
            return;
        }
        String action = intent.getAction();
        String valueOf;
        String str;
        if ("com.google.android.c2dm.intent.REGISTRATION".equals(action) || "com.google.android.gms.iid.InstanceID".equals(action)) {
            CharSequence stringExtra = intent.getStringExtra("registration_id");
            if (stringExtra == null) {
                stringExtra = intent.getStringExtra("unregistered");
            }
            if (stringExtra == null) {
                Object stringExtra2 = intent.getStringExtra(ReactVideoView.EVENT_PROP_ERROR);
                if (stringExtra2 == null) {
                    valueOf = String.valueOf(intent.getExtras());
                    StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 49);
                    stringBuilder.append("Unexpected response, no error or registration id ");
                    stringBuilder.append(valueOf);
                    Log.w("InstanceID", stringBuilder.toString());
                    return;
                }
                String str2;
                if (Log.isLoggable("InstanceID", 3)) {
                    str2 = "Received InstanceID error ";
                    String valueOf2 = String.valueOf(stringExtra2);
                    Log.d("InstanceID", valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
                }
                str2 = null;
                if (stringExtra2.startsWith("|")) {
                    String[] split = stringExtra2.split("\\|");
                    if (!"ID".equals(split[1])) {
                        String str3 = "Unexpected structured response ";
                        action = String.valueOf(stringExtra2);
                        Log.w("InstanceID", action.length() != 0 ? str3.concat(action) : new String(str3));
                    }
                    if (split.length > 2) {
                        action = split[2];
                        str = split[3];
                        if (str.startsWith(":")) {
                            str = str.substring(1);
                        }
                        str2 = action;
                    } else {
                        str = "UNKNOWN";
                    }
                    stringExtra2 = str;
                    intent.putExtra(ReactVideoView.EVENT_PROP_ERROR, stringExtra2);
                }
                if (str2 == null) {
                    synchronized (getClass()) {
                        for (String str4 : this.zzcz.keySet()) {
                            Object obj = this.zzcz.get(str4);
                            this.zzcz.put(str4, stringExtra2);
                            zzd(obj, stringExtra2);
                        }
                    }
                    return;
                }
                zzd(str2, stringExtra2);
                return;
            }
            Matcher matcher = Pattern.compile("\\|ID\\|([^|]+)\\|:?+(.*)").matcher(stringExtra);
            if (matcher.matches()) {
                str = matcher.group(1);
                action = matcher.group(2);
                Object extras = intent.getExtras();
                extras.putString("registration_id", action);
                zzd(str, extras);
                return;
            }
            if (Log.isLoggable("InstanceID", 3)) {
                valueOf = "Unexpected response string: ";
                str = String.valueOf(stringExtra);
                Log.d("InstanceID", str.length() != 0 ? valueOf.concat(str) : new String(valueOf));
            }
            return;
        }
        if (Log.isLoggable("InstanceID", 3)) {
            str = "Unexpected response ";
            valueOf = String.valueOf(intent.getAction());
            Log.d("InstanceID", valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0076  */
    final android.os.Bundle zzd(android.os.Bundle r5, java.security.KeyPair r6) throws java.io.IOException {
        /*
        r4 = this;
        r0 = r4.zzl;
        r0 = zzm(r0);
        r1 = java.lang.Integer.toString(r0);
        r2 = "gmsv";
        r5.putString(r2, r1);
        r1 = android.os.Build.VERSION.SDK_INT;
        r1 = java.lang.Integer.toString(r1);
        r2 = "osv";
        r5.putString(r2, r1);
        r1 = r4.zzl;
        r1 = com.google.android.gms.iid.InstanceID.zzg(r1);
        r1 = java.lang.Integer.toString(r1);
        r2 = "app_ver";
        r5.putString(r2, r1);
        r1 = r4.zzl;
        r1 = com.google.android.gms.iid.InstanceID.zzh(r1);
        r2 = "app_ver_name";
        r5.putString(r2, r1);
        r1 = "cliv";
        r2 = "iid-12451000";
        r5.putString(r1, r2);
        r6 = com.google.android.gms.iid.InstanceID.zzd(r6);
        r1 = "appid";
        r5.putString(r1, r6);
        r6 = 12000000; // 0xb71b00 float:1.6815582E-38 double:5.9287878E-317;
        if (r0 < r6) goto L_0x00b4;
    L_0x0049:
        r6 = zzct;
        r6 = r6.get();
        r6 = (java.lang.Boolean) r6;
        r6 = r6.booleanValue();
        if (r6 == 0) goto L_0x00b4;
    L_0x0057:
        r6 = new com.google.android.gms.iid.zzr;
        r0 = r4.zzl;
        r6.<init>(r0);
        r0 = 1;
        r6 = r6.zzd(r0, r5);
        r6 = com.google.android.gms.tasks.Tasks.await(r6);	 Catch:{ InterruptedException -> 0x006c, ExecutionException -> 0x006a }
        r6 = (android.os.Bundle) r6;	 Catch:{ InterruptedException -> 0x006c, ExecutionException -> 0x006a }
        return r6;
    L_0x006a:
        r6 = move-exception;
        goto L_0x006d;
    L_0x006c:
        r6 = move-exception;
    L_0x006d:
        r0 = 3;
        r1 = "InstanceID";
        r0 = android.util.Log.isLoggable(r1, r0);
        if (r0 == 0) goto L_0x0098;
    L_0x0076:
        r0 = java.lang.String.valueOf(r6);
        r2 = java.lang.String.valueOf(r0);
        r2 = r2.length();
        r2 = r2 + 22;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
        r2 = "Error making request: ";
        r3.append(r2);
        r3.append(r0);
        r0 = r3.toString();
        android.util.Log.d(r1, r0);
    L_0x0098:
        r0 = r6.getCause();
        r0 = r0 instanceof com.google.android.gms.iid.zzaa;
        if (r0 == 0) goto L_0x00b2;
    L_0x00a0:
        r6 = r6.getCause();
        r6 = (com.google.android.gms.iid.zzaa) r6;
        r6 = r6.getErrorCode();
        r0 = 4;
        if (r6 != r0) goto L_0x00b2;
    L_0x00ad:
        r5 = r4.zzj(r5);
        return r5;
    L_0x00b2:
        r5 = 0;
        return r5;
    L_0x00b4:
        r5 = r4.zzj(r5);
        return r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.iid.zzaf.zzd(android.os.Bundle, java.security.KeyPair):android.os.Bundle");
    }

    private final Bundle zzj(Bundle bundle) throws IOException {
        Bundle zzk = zzk(bundle);
        if (zzk == null) {
            return zzk;
        }
        String str = "google.messenger";
        if (!zzk.containsKey(str)) {
            return zzk;
        }
        zzk = zzk(bundle);
        return (zzk == null || !zzk.containsKey(str)) ? zzk : null;
    }

    private static synchronized String zzx() {
        String num;
        synchronized (zzaf.class) {
            int i = zzcp;
            zzcp = i + 1;
            num = Integer.toString(i);
        }
        return num;
    }

    /* JADX WARNING: Removed duplicated region for block: B:58:0x018b A:{SYNTHETIC} */
    private final android.os.Bundle zzk(android.os.Bundle r9) throws java.io.IOException {
        /*
        r8 = this;
        r0 = new android.os.ConditionVariable;
        r0.<init>();
        r1 = zzx();
        r2 = r8.getClass();
        monitor-enter(r2);
        r3 = r8.zzcz;	 Catch:{ all -> 0x01dc }
        r3.put(r1, r0);	 Catch:{ all -> 0x01dc }
        monitor-exit(r2);	 Catch:{ all -> 0x01dc }
        r2 = r8.zzaj;
        if (r2 != 0) goto L_0x002d;
    L_0x0018:
        r2 = r8.zzl;
        zzl(r2);
        r2 = new android.os.Messenger;
        r3 = new com.google.android.gms.iid.zzag;
        r4 = android.os.Looper.getMainLooper();
        r3.<init>(r8, r4);
        r2.<init>(r3);
        r8.zzaj = r2;
    L_0x002d:
        r2 = zzcu;
        if (r2 == 0) goto L_0x01d4;
    L_0x0031:
        r2 = new android.content.Intent;
        r3 = zzcv;
        if (r3 == 0) goto L_0x003a;
    L_0x0037:
        r3 = "com.google.iid.TOKEN_REQUEST";
        goto L_0x003c;
    L_0x003a:
        r3 = "com.google.android.c2dm.intent.REGISTER";
    L_0x003c:
        r2.<init>(r3);
        r3 = zzcu;
        r2.setPackage(r3);
        r2.putExtras(r9);
        r8.zzg(r2);
        r9 = java.lang.String.valueOf(r1);
        r9 = r9.length();
        r9 = r9 + 5;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r9);
        r9 = "|ID|";
        r3.append(r9);
        r3.append(r1);
        r9 = "|";
        r3.append(r9);
        r9 = r3.toString();
        r3 = "kid";
        r2.putExtra(r3, r9);
        r9 = java.lang.String.valueOf(r1);
        r9 = r9.length();
        r9 = r9 + 5;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r9);
        r9 = "|ID|";
        r3.append(r9);
        r3.append(r1);
        r9 = "|";
        r3.append(r9);
        r9 = r3.toString();
        r3 = "X-kid";
        r2.putExtra(r3, r9);
        r9 = zzcu;
        r3 = "com.google.android.gsf";
        r9 = r3.equals(r9);
        r3 = "useGsf";
        r3 = r2.getStringExtra(r3);
        if (r3 == 0) goto L_0x00aa;
    L_0x00a4:
        r9 = "1";
        r9 = r9.equals(r3);
    L_0x00aa:
        r3 = 3;
        r4 = "InstanceID";
        r4 = android.util.Log.isLoggable(r4, r3);
        if (r4 == 0) goto L_0x00db;
    L_0x00b3:
        r4 = r2.getExtras();
        r4 = java.lang.String.valueOf(r4);
        r5 = java.lang.String.valueOf(r4);
        r5 = r5.length();
        r5 = r5 + 8;
        r6 = new java.lang.StringBuilder;
        r6.<init>(r5);
        r5 = "Sending ";
        r6.append(r5);
        r6.append(r4);
        r4 = r6.toString();
        r5 = "InstanceID";
        android.util.Log.d(r5, r4);
    L_0x00db:
        r4 = r8.zzda;
        if (r4 == 0) goto L_0x0102;
    L_0x00df:
        r4 = r8.zzaj;
        r5 = "google.messenger";
        r2.putExtra(r5, r4);
        r4 = android.os.Message.obtain();
        r4.obj = r2;
        r5 = r8.zzda;	 Catch:{ RemoteException -> 0x00f3 }
        r5.send(r4);	 Catch:{ RemoteException -> 0x00f3 }
        goto L_0x0181;
    L_0x00f3:
        r4 = "InstanceID";
        r4 = android.util.Log.isLoggable(r4, r3);
        if (r4 == 0) goto L_0x0102;
    L_0x00fb:
        r4 = "InstanceID";
        r5 = "Messenger failed, fallback to startService";
        android.util.Log.d(r4, r5);
    L_0x0102:
        if (r9 == 0) goto L_0x0145;
    L_0x0104:
        r9 = com.google.android.gms.iid.zzaf.class;
        monitor-enter(r9);
        r4 = zzcy;	 Catch:{ all -> 0x0142 }
        if (r4 != 0) goto L_0x013b;
    L_0x010b:
        r4 = new com.google.android.gms.iid.zzah;	 Catch:{ all -> 0x0142 }
        r4.<init>(r8);	 Catch:{ all -> 0x0142 }
        zzcy = r4;	 Catch:{ all -> 0x0142 }
        r4 = "InstanceID";
        r3 = android.util.Log.isLoggable(r4, r3);	 Catch:{ all -> 0x0142 }
        if (r3 == 0) goto L_0x0121;
    L_0x011a:
        r3 = "InstanceID";
        r4 = "Registered GSF callback receiver";
        android.util.Log.d(r3, r4);	 Catch:{ all -> 0x0142 }
    L_0x0121:
        r3 = new android.content.IntentFilter;	 Catch:{ all -> 0x0142 }
        r4 = "com.google.android.c2dm.intent.REGISTRATION";
        r3.<init>(r4);	 Catch:{ all -> 0x0142 }
        r4 = r8.zzl;	 Catch:{ all -> 0x0142 }
        r4 = r4.getPackageName();	 Catch:{ all -> 0x0142 }
        r3.addCategory(r4);	 Catch:{ all -> 0x0142 }
        r4 = r8.zzl;	 Catch:{ all -> 0x0142 }
        r5 = zzcy;	 Catch:{ all -> 0x0142 }
        r6 = "com.google.android.c2dm.permission.SEND";
        r7 = 0;
        r4.registerReceiver(r5, r3, r6, r7);	 Catch:{ all -> 0x0142 }
    L_0x013b:
        monitor-exit(r9);	 Catch:{ all -> 0x0142 }
        r9 = r8.zzl;
        r9.sendBroadcast(r2);
        goto L_0x0181;
    L_0x0142:
        r0 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x0142 }
        throw r0;
    L_0x0145:
        r9 = r8.zzaj;
        r4 = "google.messenger";
        r2.putExtra(r4, r9);
        r9 = "messenger2";
        r4 = "1";
        r2.putExtra(r9, r4);
        r9 = r8.zzdb;
        if (r9 == 0) goto L_0x0172;
    L_0x0157:
        r9 = android.os.Message.obtain();
        r9.obj = r2;
        r4 = r8.zzdb;	 Catch:{ RemoteException -> 0x0163 }
        r4.send(r9);	 Catch:{ RemoteException -> 0x0163 }
        goto L_0x0181;
    L_0x0163:
        r9 = "InstanceID";
        r9 = android.util.Log.isLoggable(r9, r3);
        if (r9 == 0) goto L_0x0172;
    L_0x016b:
        r9 = "InstanceID";
        r3 = "Messenger failed, fallback to startService";
        android.util.Log.d(r9, r3);
    L_0x0172:
        r9 = zzcv;
        if (r9 == 0) goto L_0x017c;
    L_0x0176:
        r9 = r8.zzl;
        r9.sendBroadcast(r2);
        goto L_0x0181;
    L_0x017c:
        r9 = r8.zzl;
        r9.startService(r2);
    L_0x0181:
        r2 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r0.block(r2);
        r9 = r8.getClass();
        monitor-enter(r9);
        r0 = r8.zzcz;	 Catch:{ all -> 0x01d1 }
        r0 = r0.remove(r1);	 Catch:{ all -> 0x01d1 }
        r1 = r0 instanceof android.os.Bundle;	 Catch:{ all -> 0x01d1 }
        if (r1 == 0) goto L_0x0199;
    L_0x0195:
        r0 = (android.os.Bundle) r0;	 Catch:{ all -> 0x01d1 }
        monitor-exit(r9);	 Catch:{ all -> 0x01d1 }
        return r0;
    L_0x0199:
        r1 = r0 instanceof java.lang.String;	 Catch:{ all -> 0x01d1 }
        if (r1 == 0) goto L_0x01a5;
    L_0x019d:
        r1 = new java.io.IOException;	 Catch:{ all -> 0x01d1 }
        r0 = (java.lang.String) r0;	 Catch:{ all -> 0x01d1 }
        r1.<init>(r0);	 Catch:{ all -> 0x01d1 }
        throw r1;	 Catch:{ all -> 0x01d1 }
    L_0x01a5:
        r1 = "InstanceID";
        r0 = java.lang.String.valueOf(r0);	 Catch:{ all -> 0x01d1 }
        r2 = java.lang.String.valueOf(r0);	 Catch:{ all -> 0x01d1 }
        r2 = r2.length();	 Catch:{ all -> 0x01d1 }
        r2 = r2 + 12;
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01d1 }
        r3.<init>(r2);	 Catch:{ all -> 0x01d1 }
        r2 = "No response ";
        r3.append(r2);	 Catch:{ all -> 0x01d1 }
        r3.append(r0);	 Catch:{ all -> 0x01d1 }
        r0 = r3.toString();	 Catch:{ all -> 0x01d1 }
        android.util.Log.w(r1, r0);	 Catch:{ all -> 0x01d1 }
        r0 = new java.io.IOException;	 Catch:{ all -> 0x01d1 }
        r1 = "TIMEOUT";
        r0.<init>(r1);	 Catch:{ all -> 0x01d1 }
        throw r0;	 Catch:{ all -> 0x01d1 }
    L_0x01d1:
        r0 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x01d1 }
        throw r0;
    L_0x01d4:
        r9 = new java.io.IOException;
        r0 = "MISSING_INSTANCEID_SERVICE";
        r9.<init>(r0);
        throw r9;
    L_0x01dc:
        r9 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x01dc }
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.iid.zzaf.zzk(android.os.Bundle):android.os.Bundle");
    }
}
