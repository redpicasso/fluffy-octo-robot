package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.FirebaseApp;
import com.google.firebase.events.EventHandler;
import com.google.firebase.events.Subscriber;
import com.google.firebase.platforminfo.UserAgentPublisher;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
public class FirebaseInstanceId {
    private static final long zza = TimeUnit.HOURS.toSeconds(8);
    private static zzat zzb;
    @GuardedBy("FirebaseInstanceId.class")
    @VisibleForTesting
    private static ScheduledExecutorService zzc;
    @VisibleForTesting
    private final Executor zzd;
    private final FirebaseApp zze;
    private final zzai zzf;
    private final zzl zzg;
    private final zzan zzh;
    private final zzax zzi;
    @GuardedBy("this")
    private boolean zzj;
    private final zza zzk;

    /* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
    private class zza {
        private boolean zzb;
        private final Subscriber zzc;
        @GuardedBy("this")
        private boolean zzd;
        @GuardedBy("this")
        @Nullable
        private EventHandler<DataCollectionDefaultChange> zze;
        @GuardedBy("this")
        @Nullable
        private Boolean zzf;

        zza(Subscriber subscriber) {
            this.zzc = subscriber;
        }

        private final synchronized void zzb() {
            if (!this.zzd) {
                this.zzb = zzd();
                this.zzf = zzc();
                if (this.zzf == null && this.zzb) {
                    this.zze = new zzk(this);
                    this.zzc.subscribe(DataCollectionDefaultChange.class, this.zze);
                }
                this.zzd = true;
            }
        }

        final synchronized boolean zza() {
            zzb();
            if (this.zzf == null) {
                return this.zzb && FirebaseInstanceId.this.zze.isDataCollectionDefaultEnabled();
            } else {
                return this.zzf.booleanValue();
            }
        }

        final synchronized void zza(boolean z) {
            zzb();
            if (this.zze != null) {
                this.zzc.unsubscribe(DataCollectionDefaultChange.class, this.zze);
                this.zze = null;
            }
            Editor edit = FirebaseInstanceId.this.zze.getApplicationContext().getSharedPreferences("com.google.firebase.messaging", 0).edit();
            edit.putBoolean("auto_init", z);
            edit.apply();
            if (z) {
                FirebaseInstanceId.this.zzj();
            }
            this.zzf = Boolean.valueOf(z);
        }

        @Nullable
        private final Boolean zzc() {
            String str = "firebase_messaging_auto_init_enabled";
            Context applicationContext = FirebaseInstanceId.this.zze.getApplicationContext();
            SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("com.google.firebase.messaging", 0);
            String str2 = "auto_init";
            if (sharedPreferences.contains(str2)) {
                return Boolean.valueOf(sharedPreferences.getBoolean(str2, false));
            }
            try {
                PackageManager packageManager = applicationContext.getPackageManager();
                if (packageManager != null) {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(applicationContext.getPackageName(), 128);
                    if (!(applicationInfo == null || applicationInfo.metaData == null || !applicationInfo.metaData.containsKey(str))) {
                        return Boolean.valueOf(applicationInfo.metaData.getBoolean(str));
                    }
                }
            } catch (NameNotFoundException unused) {
                return null;
            }
        }

        private final boolean zzd() {
            try {
                Class.forName("com.google.firebase.messaging.FirebaseMessaging");
                return true;
            } catch (ClassNotFoundException unused) {
                Context applicationContext = FirebaseInstanceId.this.zze.getApplicationContext();
                Intent intent = new Intent("com.google.firebase.MESSAGING_EVENT");
                intent.setPackage(applicationContext.getPackageName());
                ResolveInfo resolveService = applicationContext.getPackageManager().resolveService(intent, 0);
                if (resolveService == null || resolveService.serviceInfo == null) {
                    return false;
                }
                return true;
            }
        }
    }

    @NonNull
    public static FirebaseInstanceId getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    @NonNull
    @Keep
    public static FirebaseInstanceId getInstance(@NonNull FirebaseApp firebaseApp) {
        return (FirebaseInstanceId) firebaseApp.get(FirebaseInstanceId.class);
    }

    FirebaseInstanceId(FirebaseApp firebaseApp, Subscriber subscriber, UserAgentPublisher userAgentPublisher) {
        this(firebaseApp, new zzai(firebaseApp.getApplicationContext()), zza.zzb(), zza.zzb(), subscriber, userAgentPublisher);
    }

    private FirebaseInstanceId(FirebaseApp firebaseApp, zzai zzai, Executor executor, Executor executor2, Subscriber subscriber, UserAgentPublisher userAgentPublisher) {
        this.zzj = false;
        if (zzai.zza(firebaseApp) != null) {
            synchronized (FirebaseInstanceId.class) {
                if (zzb == null) {
                    zzb = new zzat(firebaseApp.getApplicationContext());
                }
            }
            this.zze = firebaseApp;
            this.zzf = zzai;
            this.zzg = new zzl(firebaseApp, zzai, executor, userAgentPublisher);
            this.zzd = executor2;
            this.zzi = new zzax(zzb);
            this.zzk = new zza(subscriber);
            this.zzh = new zzan(executor);
            executor2.execute(new zzh(this));
            return;
        }
        throw new IllegalStateException("FirebaseInstanceId failed to initialize, FirebaseApp is missing project ID");
    }

    private final void zzj() {
        if (zza(zzb()) || this.zzi.zza()) {
            zzk();
        }
    }

    final FirebaseApp zza() {
        return this.zze;
    }

    final synchronized void zza(boolean z) {
        this.zzj = z;
    }

    private final synchronized void zzk() {
        if (!this.zzj) {
            zza(0);
        }
    }

    final synchronized void zza(long j) {
        zza(new zzav(this, this.zzf, this.zzi, Math.min(Math.max(30, j << 1), zza)), j);
        this.zzj = true;
    }

    static void zza(Runnable runnable, long j) {
        synchronized (FirebaseInstanceId.class) {
            if (zzc == null) {
                zzc = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("FirebaseInstanceId"));
            }
            zzc.schedule(runnable, j, TimeUnit.SECONDS);
        }
    }

    @WorkerThread
    @NonNull
    public String getId() {
        zzj();
        return zzl();
    }

    private static String zzl() {
        return zzb.zzb("").zza();
    }

    public long getCreationTime() {
        return zzb.zzb("").zzb();
    }

    @NonNull
    public Task<InstanceIdResult> getInstanceId() {
        return zza(zzai.zza(this.zze), "*");
    }

    private final Task<InstanceIdResult> zza(String str, String str2) {
        return Tasks.forResult(null).continueWithTask(this.zzd, new zzg(this, str, zzd(str2)));
    }

    @WorkerThread
    public void deleteInstanceId() throws IOException {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            zza(this.zzg.zza(zzl()));
            zze();
            return;
        }
        throw new IOException(InstanceID.ERROR_MAIN_THREAD);
    }

    @Deprecated
    @Nullable
    public String getToken() {
        zzas zzb = zzb();
        if (zza(zzb)) {
            zzk();
        }
        return zzas.zza(zzb);
    }

    @WorkerThread
    @Nullable
    public String getToken(@NonNull String str, @NonNull String str2) throws IOException {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            return ((InstanceIdResult) zza(zza(str, str2))).getToken();
        }
        throw new IOException(InstanceID.ERROR_MAIN_THREAD);
    }

    @Nullable
    final zzas zzb() {
        return zzb(zzai.zza(this.zze), "*");
    }

    @Nullable
    @VisibleForTesting
    private static zzas zzb(String str, String str2) {
        return zzb.zza("", str, str2);
    }

    final String zzc() throws IOException {
        return getToken(zzai.zza(this.zze), "*");
    }

    /* JADX WARNING: Removed duplicated region for block: B:4:0x0009 A:{ExcHandler: java.lang.InterruptedException (unused java.lang.InterruptedException), Splitter: B:1:0x0002} */
    /* JADX WARNING: Missing block: B:5:0x0010, code:
            throw new java.io.IOException(com.google.android.gms.iid.InstanceID.ERROR_SERVICE_NOT_AVAILABLE);
     */
    private final <T> T zza(com.google.android.gms.tasks.Task<T> r4) throws java.io.IOException {
        /*
        r3 = this;
        r0 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r2 = java.util.concurrent.TimeUnit.MILLISECONDS;	 Catch:{ ExecutionException -> 0x0011, InterruptedException -> 0x0009, InterruptedException -> 0x0009 }
        r4 = com.google.android.gms.tasks.Tasks.await(r4, r0, r2);	 Catch:{ ExecutionException -> 0x0011, InterruptedException -> 0x0009, InterruptedException -> 0x0009 }
        return r4;
    L_0x0009:
        r4 = new java.io.IOException;
        r0 = "SERVICE_NOT_AVAILABLE";
        r4.<init>(r0);
        throw r4;
    L_0x0011:
        r4 = move-exception;
        r0 = r4.getCause();
        r1 = r0 instanceof java.io.IOException;
        if (r1 == 0) goto L_0x002c;
    L_0x001a:
        r4 = r0.getMessage();
        r1 = "INSTANCE_ID_RESET";
        r4 = r1.equals(r4);
        if (r4 == 0) goto L_0x0029;
    L_0x0026:
        r3.zze();
    L_0x0029:
        r0 = (java.io.IOException) r0;
        throw r0;
    L_0x002c:
        r1 = r0 instanceof java.lang.RuntimeException;
        if (r1 == 0) goto L_0x0033;
    L_0x0030:
        r0 = (java.lang.RuntimeException) r0;
        throw r0;
    L_0x0033:
        r0 = new java.io.IOException;
        r0.<init>(r4);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.FirebaseInstanceId.zza(com.google.android.gms.tasks.Task):T");
    }

    @WorkerThread
    public void deleteToken(@NonNull String str, @NonNull String str2) throws IOException {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            str2 = zzd(str2);
            zza(this.zzg.zzb(zzl(), str, str2));
            zzb.zzb("", str, str2);
            return;
        }
        throw new IOException(InstanceID.ERROR_MAIN_THREAD);
    }

    public final synchronized Task<Void> zza(String str) {
        Task<Void> zza;
        zza = this.zzi.zza(str);
        zzk();
        return zza;
    }

    final void zzb(String str) throws IOException {
        zzas zzb = zzb();
        if (zza(zzb)) {
            throw new IOException("token not available");
        }
        zza(this.zzg.zzc(zzl(), zzb.zza, str));
    }

    final void zzc(String str) throws IOException {
        zzas zzb = zzb();
        if (zza(zzb)) {
            throw new IOException("token not available");
        }
        zza(this.zzg.zzd(zzl(), zzb.zza, str));
    }

    static boolean zzd() {
        String str = "FirebaseInstanceId";
        return Log.isLoggable(str, 3) || (VERSION.SDK_INT == 23 && Log.isLoggable(str, 3));
    }

    final synchronized void zze() {
        zzb.zzb();
        if (this.zzk.zza()) {
            zzk();
        }
    }

    final boolean zzf() {
        return this.zzf.zza() != 0;
    }

    final void zzg() {
        zzb.zzc("");
        zzk();
    }

    @VisibleForTesting
    public final boolean zzh() {
        return this.zzk.zza();
    }

    @VisibleForTesting
    public final void zzb(boolean z) {
        this.zzk.zza(z);
    }

    private static String zzd(String str) {
        return (str.isEmpty() || str.equalsIgnoreCase("fcm") || str.equalsIgnoreCase("gcm")) ? "*" : str;
    }

    final boolean zza(@Nullable zzas zzas) {
        return zzas == null || zzas.zzb(this.zzf.zzb());
    }

    final /* synthetic */ Task zza(String str, String str2, String str3, String str4) throws Exception {
        zzb.zza("", str, str2, str4, this.zzf.zzb());
        return Tasks.forResult(new zzu(str3, str4));
    }

    final /* synthetic */ void zzi() {
        if (this.zzk.zza()) {
            zzj();
        }
    }
}
