package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.util.Log;
import androidx.collection.SimpleArrayMap;
import com.brentvatne.react.ReactVideoView;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.iid.zzf.zza;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
final class zzao {
    private static int zza;
    private static PendingIntent zzb;
    @GuardedBy("responseCallbacks")
    private final SimpleArrayMap<String, TaskCompletionSource<Bundle>> zzc = new SimpleArrayMap();
    private final Context zzd;
    private final zzai zze;
    private Messenger zzf;
    private Messenger zzg;
    private zzf zzh;

    public zzao(Context context, zzai zzai) {
        this.zzd = context;
        this.zze = zzai;
        this.zzf = new Messenger(new zzar(this, Looper.getMainLooper()));
    }

    private final void zza(Message message) {
        if (message == null || !(message.obj instanceof Intent)) {
            Log.w("FirebaseInstanceId", "Dropping invalid message");
        } else {
            Intent intent = (Intent) message.obj;
            intent.setExtrasClassLoader(new zza());
            if (intent.hasExtra("google.messenger")) {
                Parcelable parcelableExtra = intent.getParcelableExtra("google.messenger");
                if (parcelableExtra instanceof zzf) {
                    this.zzh = (zzf) parcelableExtra;
                }
                if (parcelableExtra instanceof Messenger) {
                    this.zzg = (Messenger) parcelableExtra;
                }
            }
            Intent intent2 = (Intent) message.obj;
            String action = intent2.getAction();
            String valueOf;
            if ("com.google.android.c2dm.intent.REGISTRATION".equals(action)) {
                CharSequence stringExtra = intent2.getStringExtra("registration_id");
                if (stringExtra == null) {
                    stringExtra = intent2.getStringExtra("unregistered");
                }
                String str;
                if (stringExtra == null) {
                    action = intent2.getStringExtra(ReactVideoView.EVENT_PROP_ERROR);
                    if (action == null) {
                        valueOf = String.valueOf(intent2.getExtras());
                        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 49);
                        stringBuilder.append("Unexpected response, no error or registration id ");
                        stringBuilder.append(valueOf);
                        Log.w("FirebaseInstanceId", stringBuilder.toString());
                    } else {
                        if (Log.isLoggable("FirebaseInstanceId", 3)) {
                            String str2 = "Received InstanceID error ";
                            String valueOf2 = String.valueOf(action);
                            Log.d("FirebaseInstanceId", valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
                        }
                        if (action.startsWith("|")) {
                            String[] split = action.split("\\|");
                            if (split.length > 2) {
                                if ("ID".equals(split[1])) {
                                    action = split[2];
                                    str = split[3];
                                    if (str.startsWith(":")) {
                                        str = str.substring(1);
                                    }
                                    zza(action, intent2.putExtra(ReactVideoView.EVENT_PROP_ERROR, str).getExtras());
                                }
                            }
                            valueOf = "Unexpected structured response ";
                            action = String.valueOf(action);
                            Log.w("FirebaseInstanceId", action.length() != 0 ? valueOf.concat(action) : new String(valueOf));
                        } else {
                            synchronized (this.zzc) {
                                for (int i = 0; i < this.zzc.size(); i++) {
                                    zza((String) this.zzc.keyAt(i), intent2.getExtras());
                                }
                            }
                        }
                    }
                } else {
                    Matcher matcher = Pattern.compile("\\|ID\\|([^|]+)\\|:?+(.*)").matcher(stringExtra);
                    if (matcher.matches()) {
                        action = matcher.group(1);
                        str = matcher.group(2);
                        Bundle extras = intent2.getExtras();
                        extras.putString("registration_id", str);
                        zza(action, extras);
                        return;
                    }
                    if (Log.isLoggable("FirebaseInstanceId", 3)) {
                        valueOf = "Unexpected response string: ";
                        action = String.valueOf(stringExtra);
                        Log.d("FirebaseInstanceId", action.length() != 0 ? valueOf.concat(action) : new String(valueOf));
                    }
                    return;
                }
            }
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                valueOf = "Unexpected response action: ";
                action = String.valueOf(action);
                Log.d("FirebaseInstanceId", action.length() != 0 ? valueOf.concat(action) : new String(valueOf));
            }
        }
    }

    private static synchronized void zza(Context context, Intent intent) {
        synchronized (zzao.class) {
            if (zzb == null) {
                Intent intent2 = new Intent();
                intent2.setPackage("com.google.example.invalidpackage");
                zzb = PendingIntent.getBroadcast(context, 0, intent2, 0);
            }
            intent.putExtra("app", zzb);
        }
    }

    private final void zza(String str, Bundle bundle) {
        synchronized (this.zzc) {
            TaskCompletionSource taskCompletionSource = (TaskCompletionSource) this.zzc.remove(str);
            if (taskCompletionSource == null) {
                String str2 = "FirebaseInstanceId";
                String str3 = "Missing callback for ";
                str = String.valueOf(str);
                Log.w(str2, str.length() != 0 ? str3.concat(str) : new String(str3));
                return;
            }
            taskCompletionSource.setResult(bundle);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0029  */
    final android.os.Bundle zza(android.os.Bundle r6) throws java.io.IOException {
        /*
        r5 = this;
        r0 = r5.zze;
        r0 = r0.zzd();
        r1 = 12000000; // 0xb71b00 float:1.6815582E-38 double:5.9287878E-317;
        if (r0 < r1) goto L_0x0067;
    L_0x000b:
        r0 = r5.zzd;
        r0 = com.google.firebase.iid.zzv.zza(r0);
        r1 = 1;
        r0 = r0.zzb(r1, r6);
        r0 = com.google.android.gms.tasks.Tasks.await(r0);	 Catch:{ InterruptedException -> 0x001f, ExecutionException -> 0x001d }
        r0 = (android.os.Bundle) r0;	 Catch:{ InterruptedException -> 0x001f, ExecutionException -> 0x001d }
        return r0;
    L_0x001d:
        r0 = move-exception;
        goto L_0x0020;
    L_0x001f:
        r0 = move-exception;
    L_0x0020:
        r1 = 3;
        r2 = "FirebaseInstanceId";
        r1 = android.util.Log.isLoggable(r2, r1);
        if (r1 == 0) goto L_0x004b;
    L_0x0029:
        r1 = java.lang.String.valueOf(r0);
        r3 = java.lang.String.valueOf(r1);
        r3 = r3.length();
        r3 = r3 + 22;
        r4 = new java.lang.StringBuilder;
        r4.<init>(r3);
        r3 = "Error making request: ";
        r4.append(r3);
        r4.append(r1);
        r1 = r4.toString();
        android.util.Log.d(r2, r1);
    L_0x004b:
        r1 = r0.getCause();
        r1 = r1 instanceof com.google.firebase.iid.zzag;
        if (r1 == 0) goto L_0x0065;
    L_0x0053:
        r0 = r0.getCause();
        r0 = (com.google.firebase.iid.zzag) r0;
        r0 = r0.zza();
        r1 = 4;
        if (r0 != r1) goto L_0x0065;
    L_0x0060:
        r6 = r5.zzb(r6);
        return r6;
    L_0x0065:
        r6 = 0;
        return r6;
    L_0x0067:
        r6 = r5.zzb(r6);
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzao.zza(android.os.Bundle):android.os.Bundle");
    }

    private final Bundle zzb(Bundle bundle) throws IOException {
        Bundle zzc = zzc(bundle);
        if (zzc == null) {
            return zzc;
        }
        String str = "google.messenger";
        if (!zzc.containsKey(str)) {
            return zzc;
        }
        zzc = zzc(bundle);
        return (zzc == null || !zzc.containsKey(str)) ? zzc : null;
    }

    private static synchronized String zza() {
        String num;
        synchronized (zzao.class) {
            int i = zza;
            zza = i + 1;
            num = Integer.toString(i);
        }
        return num;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.google.firebase.iid.zzao.zzc(android.os.Bundle):android.os.Bundle, dom blocks: []
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:89)
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:293)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:201)
        */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00ef A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0102 A:{Catch:{ InterruptedException -> 0x0102, InterruptedException -> 0x0102, ExecutionException -> 0x00fb, all -> 0x00f9 }, ExcHandler: java.lang.InterruptedException (unused java.lang.InterruptedException), Splitter: B:31:0x00de} */
    private final android.os.Bundle zzc(android.os.Bundle r8) throws java.io.IOException {
        /*
        r7 = this;
        r0 = zza();
        r1 = new com.google.android.gms.tasks.TaskCompletionSource;
        r1.<init>();
        r2 = r7.zzc;
        monitor-enter(r2);
        r3 = r7.zzc;	 Catch:{ all -> 0x0126 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0126 }
        monitor-exit(r2);	 Catch:{ all -> 0x0126 }
        r2 = r7.zze;
        r2 = r2.zza();
        if (r2 == 0) goto L_0x011e;
    L_0x001a:
        r2 = new android.content.Intent;
        r2.<init>();
        r3 = "com.google.android.gms";
        r2.setPackage(r3);
        r3 = r7.zze;
        r3 = r3.zza();
        r4 = 2;
        if (r3 != r4) goto L_0x0033;
    L_0x002d:
        r3 = "com.google.iid.TOKEN_REQUEST";
        r2.setAction(r3);
        goto L_0x0038;
    L_0x0033:
        r3 = "com.google.android.c2dm.intent.REGISTER";
        r2.setAction(r3);
    L_0x0038:
        r2.putExtras(r8);
        r8 = r7.zzd;
        zza(r8, r2);
        r8 = java.lang.String.valueOf(r0);
        r8 = r8.length();
        r8 = r8 + 5;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r8);
        r8 = "|ID|";
        r3.append(r8);
        r3.append(r0);
        r8 = "|";
        r3.append(r8);
        r8 = r3.toString();
        r3 = "kid";
        r2.putExtra(r3, r8);
        r8 = 3;
        r3 = "FirebaseInstanceId";
        r3 = android.util.Log.isLoggable(r3, r8);
        if (r3 == 0) goto L_0x0096;
    L_0x006e:
        r3 = r2.getExtras();
        r3 = java.lang.String.valueOf(r3);
        r5 = java.lang.String.valueOf(r3);
        r5 = r5.length();
        r5 = r5 + 8;
        r6 = new java.lang.StringBuilder;
        r6.<init>(r5);
        r5 = "Sending ";
        r6.append(r5);
        r6.append(r3);
        r3 = r6.toString();
        r5 = "FirebaseInstanceId";
        android.util.Log.d(r5, r3);
    L_0x0096:
        r3 = r7.zzf;
        r5 = "google.messenger";
        r2.putExtra(r5, r3);
        r3 = r7.zzg;
        if (r3 != 0) goto L_0x00a5;
    L_0x00a1:
        r3 = r7.zzh;
        if (r3 == 0) goto L_0x00cb;
    L_0x00a5:
        r3 = android.os.Message.obtain();
        r3.obj = r2;
        r5 = r7.zzg;	 Catch:{ RemoteException -> 0x00bb }
        if (r5 == 0) goto L_0x00b5;	 Catch:{ RemoteException -> 0x00bb }
    L_0x00af:
        r5 = r7.zzg;	 Catch:{ RemoteException -> 0x00bb }
        r5.send(r3);	 Catch:{ RemoteException -> 0x00bb }
        goto L_0x00de;	 Catch:{ RemoteException -> 0x00bb }
    L_0x00b5:
        r5 = r7.zzh;	 Catch:{ RemoteException -> 0x00bb }
        r5.zza(r3);	 Catch:{ RemoteException -> 0x00bb }
        goto L_0x00de;
        r3 = "FirebaseInstanceId";
        r8 = android.util.Log.isLoggable(r3, r8);
        if (r8 == 0) goto L_0x00cb;
    L_0x00c4:
        r8 = "FirebaseInstanceId";
        r3 = "Messenger failed, fallback to startService";
        android.util.Log.d(r8, r3);
    L_0x00cb:
        r8 = r7.zze;
        r8 = r8.zza();
        if (r8 != r4) goto L_0x00d9;
    L_0x00d3:
        r8 = r7.zzd;
        r8.sendBroadcast(r2);
        goto L_0x00de;
    L_0x00d9:
        r8 = r7.zzd;
        r8.startService(r2);
    L_0x00de:
        r8 = r1.getTask();	 Catch:{ InterruptedException -> 0x0102, InterruptedException -> 0x0102, ExecutionException -> 0x00fb }
        r1 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;	 Catch:{ InterruptedException -> 0x0102, InterruptedException -> 0x0102, ExecutionException -> 0x00fb }
        r3 = java.util.concurrent.TimeUnit.MILLISECONDS;	 Catch:{ InterruptedException -> 0x0102, InterruptedException -> 0x0102, ExecutionException -> 0x00fb }
        r8 = com.google.android.gms.tasks.Tasks.await(r8, r1, r3);	 Catch:{ InterruptedException -> 0x0102, InterruptedException -> 0x0102, ExecutionException -> 0x00fb }
        r8 = (android.os.Bundle) r8;	 Catch:{ InterruptedException -> 0x0102, InterruptedException -> 0x0102, ExecutionException -> 0x00fb }
        r1 = r7.zzc;
        monitor-enter(r1);
        r2 = r7.zzc;	 Catch:{ all -> 0x00f6 }
        r2.remove(r0);	 Catch:{ all -> 0x00f6 }
        monitor-exit(r1);	 Catch:{ all -> 0x00f6 }
        return r8;	 Catch:{ all -> 0x00f6 }
    L_0x00f6:
        r8 = move-exception;	 Catch:{ all -> 0x00f6 }
        monitor-exit(r1);	 Catch:{ all -> 0x00f6 }
        throw r8;
    L_0x00f9:
        r8 = move-exception;
        goto L_0x0111;
    L_0x00fb:
        r8 = move-exception;
        r1 = new java.io.IOException;	 Catch:{ all -> 0x00f9 }
        r1.<init>(r8);	 Catch:{ all -> 0x00f9 }
        throw r1;	 Catch:{ all -> 0x00f9 }
    L_0x0102:
        r8 = "FirebaseInstanceId";	 Catch:{ all -> 0x00f9 }
        r1 = "No response";	 Catch:{ all -> 0x00f9 }
        android.util.Log.w(r8, r1);	 Catch:{ all -> 0x00f9 }
        r8 = new java.io.IOException;	 Catch:{ all -> 0x00f9 }
        r1 = "TIMEOUT";	 Catch:{ all -> 0x00f9 }
        r8.<init>(r1);	 Catch:{ all -> 0x00f9 }
        throw r8;	 Catch:{ all -> 0x00f9 }
    L_0x0111:
        r1 = r7.zzc;
        monitor-enter(r1);
        r2 = r7.zzc;	 Catch:{ all -> 0x011b }
        r2.remove(r0);	 Catch:{ all -> 0x011b }
        monitor-exit(r1);	 Catch:{ all -> 0x011b }
        throw r8;
    L_0x011b:
        r8 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x011b }
        throw r8;
    L_0x011e:
        r8 = new java.io.IOException;
        r0 = "MISSING_INSTANCEID_SERVICE";
        r8.<init>(r0);
        throw r8;
    L_0x0126:
        r8 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0126 }
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzao.zzc(android.os.Bundle):android.os.Bundle");
    }
}
