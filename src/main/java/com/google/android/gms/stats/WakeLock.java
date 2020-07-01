package com.google.android.gms.stats;

import android.content.Context;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.util.WorkSourceUtil;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.ThreadSafe;

@ShowFirstParty
@ThreadSafe
@KeepForSdk
public class WakeLock {
    private static ScheduledExecutorService zzn;
    private static volatile zza zzo = new zza();
    private final Object zza;
    private final android.os.PowerManager.WakeLock zzb;
    private WorkSource zzc;
    private final int zzd;
    private final String zze;
    private final String zzf;
    private final String zzg;
    private final Context zzh;
    private boolean zzi;
    private final Map<String, Integer[]> zzj;
    private final Set<Future<?>> zzk;
    private int zzl;
    private AtomicInteger zzm;

    public interface zza {
    }

    @KeepForSdk
    public WakeLock(@NonNull Context context, int i, @NonNull String str) {
        this(context, i, str, null, context == null ? null : context.getPackageName());
    }

    private WakeLock(@NonNull Context context, int i, @NonNull String str, @Nullable String str2, @NonNull String str3) {
        this(context, i, str, null, str3, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00b3  */
    @android.annotation.SuppressLint({"UnwrappedWakeLock"})
    private WakeLock(@androidx.annotation.NonNull android.content.Context r2, int r3, @androidx.annotation.NonNull java.lang.String r4, @androidx.annotation.Nullable java.lang.String r5, @androidx.annotation.NonNull java.lang.String r6, @androidx.annotation.Nullable java.lang.String r7) {
        /*
        r1 = this;
        r1.<init>();
        r1.zza = r1;
        r5 = 1;
        r1.zzi = r5;
        r5 = new java.util.HashMap;
        r5.<init>();
        r1.zzj = r5;
        r5 = new java.util.HashSet;
        r5.<init>();
        r5 = java.util.Collections.synchronizedSet(r5);
        r1.zzk = r5;
        r5 = new java.util.concurrent.atomic.AtomicInteger;
        r7 = 0;
        r5.<init>(r7);
        r1.zzm = r5;
        r5 = "WakeLock: context must not be null";
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r2, r5);
        r5 = "WakeLock: wakeLockName must not be empty";
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r4, r5);
        r1.zzd = r3;
        r5 = 0;
        r1.zzf = r5;
        r1.zzg = r5;
        r5 = r2.getApplicationContext();
        r1.zzh = r5;
        r5 = r2.getPackageName();
        r7 = "com.google.android.gms";
        r5 = r7.equals(r5);
        if (r5 != 0) goto L_0x005f;
    L_0x0045:
        r5 = "*gcore*:";
        r7 = java.lang.String.valueOf(r4);
        r0 = r7.length();
        if (r0 == 0) goto L_0x0056;
    L_0x0051:
        r5 = r5.concat(r7);
        goto L_0x005c;
    L_0x0056:
        r7 = new java.lang.String;
        r7.<init>(r5);
        r5 = r7;
    L_0x005c:
        r1.zze = r5;
        goto L_0x0061;
    L_0x005f:
        r1.zze = r4;
    L_0x0061:
        r5 = "power";
        r5 = r2.getSystemService(r5);
        r5 = (android.os.PowerManager) r5;
        r3 = r5.newWakeLock(r3, r4);
        r1.zzb = r3;
        r3 = com.google.android.gms.common.util.WorkSourceUtil.hasWorkSourcePermission(r2);
        if (r3 == 0) goto L_0x00af;
    L_0x0075:
        r3 = com.google.android.gms.common.util.Strings.isEmptyOrWhitespace(r6);
        if (r3 == 0) goto L_0x007f;
    L_0x007b:
        r6 = r2.getPackageName();
    L_0x007f:
        r2 = com.google.android.gms.common.util.WorkSourceUtil.fromPackage(r2, r6);
        r1.zzc = r2;
        r2 = r1.zzc;
        if (r2 == 0) goto L_0x00af;
    L_0x0089:
        r3 = r1.zzh;
        r3 = com.google.android.gms.common.util.WorkSourceUtil.hasWorkSourcePermission(r3);
        if (r3 == 0) goto L_0x00af;
    L_0x0091:
        r3 = r1.zzc;
        if (r3 == 0) goto L_0x0099;
    L_0x0095:
        r3.add(r2);
        goto L_0x009b;
    L_0x0099:
        r1.zzc = r2;
    L_0x009b:
        r2 = r1.zzc;
        r3 = r1.zzb;	 Catch:{ IllegalArgumentException -> 0x00a5, ArrayIndexOutOfBoundsException -> 0x00a3 }
        r3.setWorkSource(r2);	 Catch:{ IllegalArgumentException -> 0x00a5, ArrayIndexOutOfBoundsException -> 0x00a3 }
        goto L_0x00af;
    L_0x00a3:
        r2 = move-exception;
        goto L_0x00a6;
    L_0x00a5:
        r2 = move-exception;
    L_0x00a6:
        r2 = r2.toString();
        r3 = "WakeLock";
        android.util.Log.wtf(r3, r2);
    L_0x00af:
        r2 = zzn;
        if (r2 != 0) goto L_0x00bd;
    L_0x00b3:
        r2 = com.google.android.gms.common.providers.PooledExecutorsProvider.getInstance();
        r2 = r2.newSingleThreadScheduledExecutor();
        zzn = r2;
    L_0x00bd:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.stats.WakeLock.<init>(android.content.Context, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }

    private final List<String> zza() {
        return WorkSourceUtil.getNames(this.zzc);
    }

    /* JADX WARNING: Missing block: B:16:0x0054, code:
            if (r2 == 0) goto L_0x0056;
     */
    /* JADX WARNING: Missing block: B:20:0x005c, code:
            if (r13.zzl == 0) goto L_0x005e;
     */
    /* JADX WARNING: Missing block: B:21:0x005e, code:
            com.google.android.gms.common.stats.WakeLockTracker.getInstance().registerEvent(r13.zzh, com.google.android.gms.common.stats.StatsUtils.getEventKey(r13.zzb, r6), 7, r13.zze, r6, null, r13.zzd, zza(), r14);
            r13.zzl++;
     */
    @com.google.android.gms.common.annotation.KeepForSdk
    public void acquire(long r14) {
        /*
        r13 = this;
        r0 = r13.zzm;
        r0.incrementAndGet();
        r0 = 0;
        r6 = r13.zza(r0);
        r0 = r13.zza;
        monitor-enter(r0);
        r1 = r13.zzj;	 Catch:{ all -> 0x0096 }
        r1 = r1.isEmpty();	 Catch:{ all -> 0x0096 }
        r2 = 0;
        if (r1 == 0) goto L_0x001a;
    L_0x0016:
        r1 = r13.zzl;	 Catch:{ all -> 0x0096 }
        if (r1 <= 0) goto L_0x0029;
    L_0x001a:
        r1 = r13.zzb;	 Catch:{ all -> 0x0096 }
        r1 = r1.isHeld();	 Catch:{ all -> 0x0096 }
        if (r1 != 0) goto L_0x0029;
    L_0x0022:
        r1 = r13.zzj;	 Catch:{ all -> 0x0096 }
        r1.clear();	 Catch:{ all -> 0x0096 }
        r13.zzl = r2;	 Catch:{ all -> 0x0096 }
    L_0x0029:
        r1 = r13.zzi;	 Catch:{ all -> 0x0096 }
        r12 = 1;
        if (r1 == 0) goto L_0x0056;
    L_0x002e:
        r1 = r13.zzj;	 Catch:{ all -> 0x0096 }
        r1 = r1.get(r6);	 Catch:{ all -> 0x0096 }
        r1 = (java.lang.Integer[]) r1;	 Catch:{ all -> 0x0096 }
        if (r1 != 0) goto L_0x0047;
    L_0x0038:
        r1 = r13.zzj;	 Catch:{ all -> 0x0096 }
        r3 = new java.lang.Integer[r12];	 Catch:{ all -> 0x0096 }
        r4 = java.lang.Integer.valueOf(r12);	 Catch:{ all -> 0x0096 }
        r3[r2] = r4;	 Catch:{ all -> 0x0096 }
        r1.put(r6, r3);	 Catch:{ all -> 0x0096 }
        r2 = 1;
        goto L_0x0054;
    L_0x0047:
        r3 = r1[r2];	 Catch:{ all -> 0x0096 }
        r3 = r3.intValue();	 Catch:{ all -> 0x0096 }
        r3 = r3 + r12;
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ all -> 0x0096 }
        r1[r2] = r3;	 Catch:{ all -> 0x0096 }
    L_0x0054:
        if (r2 != 0) goto L_0x005e;
    L_0x0056:
        r1 = r13.zzi;	 Catch:{ all -> 0x0096 }
        if (r1 != 0) goto L_0x007d;
    L_0x005a:
        r1 = r13.zzl;	 Catch:{ all -> 0x0096 }
        if (r1 != 0) goto L_0x007d;
    L_0x005e:
        r1 = com.google.android.gms.common.stats.WakeLockTracker.getInstance();	 Catch:{ all -> 0x0096 }
        r2 = r13.zzh;	 Catch:{ all -> 0x0096 }
        r3 = r13.zzb;	 Catch:{ all -> 0x0096 }
        r3 = com.google.android.gms.common.stats.StatsUtils.getEventKey(r3, r6);	 Catch:{ all -> 0x0096 }
        r4 = 7;
        r5 = r13.zze;	 Catch:{ all -> 0x0096 }
        r7 = 0;
        r8 = r13.zzd;	 Catch:{ all -> 0x0096 }
        r9 = r13.zza();	 Catch:{ all -> 0x0096 }
        r10 = r14;
        r1.registerEvent(r2, r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ all -> 0x0096 }
        r1 = r13.zzl;	 Catch:{ all -> 0x0096 }
        r1 = r1 + r12;
        r13.zzl = r1;	 Catch:{ all -> 0x0096 }
    L_0x007d:
        monitor-exit(r0);	 Catch:{ all -> 0x0096 }
        r0 = r13.zzb;
        r0.acquire();
        r0 = 0;
        r2 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1));
        if (r2 <= 0) goto L_0x0095;
    L_0x0089:
        r0 = zzn;
        r1 = new com.google.android.gms.stats.zzb;
        r1.<init>(r13);
        r2 = java.util.concurrent.TimeUnit.MILLISECONDS;
        r0.schedule(r1, r14, r2);
    L_0x0095:
        return;
    L_0x0096:
        r14 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0096 }
        throw r14;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.stats.WakeLock.acquire(long):void");
    }

    /* JADX WARNING: Missing block: B:15:0x0050, code:
            if (r1 != null) goto L_0x005a;
     */
    /* JADX WARNING: Missing block: B:19:0x0058, code:
            if (r12.zzl == 1) goto L_0x005a;
     */
    /* JADX WARNING: Missing block: B:20:0x005a, code:
            com.google.android.gms.common.stats.WakeLockTracker.getInstance().registerEvent(r12.zzh, com.google.android.gms.common.stats.StatsUtils.getEventKey(r12.zzb, r6), 8, r12.zze, r6, null, r12.zzd, zza());
            r12.zzl--;
     */
    @com.google.android.gms.common.annotation.KeepForSdk
    public void release() {
        /*
        r12 = this;
        r0 = r12.zzm;
        r0 = r0.decrementAndGet();
        if (r0 >= 0) goto L_0x0019;
    L_0x0008:
        r0 = r12.zze;
        r0 = java.lang.String.valueOf(r0);
        r1 = " release without a matched acquire!";
        r0 = r0.concat(r1);
        r1 = "WakeLock";
        android.util.Log.e(r1, r0);
    L_0x0019:
        r0 = 0;
        r6 = r12.zza(r0);
        r0 = r12.zza;
        monitor-enter(r0);
        r1 = r12.zzi;	 Catch:{ all -> 0x007e }
        r10 = 1;
        r11 = 0;
        if (r1 == 0) goto L_0x0052;
    L_0x0027:
        r1 = r12.zzj;	 Catch:{ all -> 0x007e }
        r1 = r1.get(r6);	 Catch:{ all -> 0x007e }
        r1 = (java.lang.Integer[]) r1;	 Catch:{ all -> 0x007e }
        if (r1 != 0) goto L_0x0033;
    L_0x0031:
        r1 = 0;
        goto L_0x0050;
    L_0x0033:
        r2 = r1[r11];	 Catch:{ all -> 0x007e }
        r2 = r2.intValue();	 Catch:{ all -> 0x007e }
        if (r2 != r10) goto L_0x0042;
    L_0x003b:
        r1 = r12.zzj;	 Catch:{ all -> 0x007e }
        r1.remove(r6);	 Catch:{ all -> 0x007e }
        r1 = 1;
        goto L_0x0050;
    L_0x0042:
        r2 = r1[r11];	 Catch:{ all -> 0x007e }
        r2 = r2.intValue();	 Catch:{ all -> 0x007e }
        r2 = r2 - r10;
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ all -> 0x007e }
        r1[r11] = r2;	 Catch:{ all -> 0x007e }
        goto L_0x0031;
    L_0x0050:
        if (r1 != 0) goto L_0x005a;
    L_0x0052:
        r1 = r12.zzi;	 Catch:{ all -> 0x007e }
        if (r1 != 0) goto L_0x0079;
    L_0x0056:
        r1 = r12.zzl;	 Catch:{ all -> 0x007e }
        if (r1 != r10) goto L_0x0079;
    L_0x005a:
        r1 = com.google.android.gms.common.stats.WakeLockTracker.getInstance();	 Catch:{ all -> 0x007e }
        r2 = r12.zzh;	 Catch:{ all -> 0x007e }
        r3 = r12.zzb;	 Catch:{ all -> 0x007e }
        r3 = com.google.android.gms.common.stats.StatsUtils.getEventKey(r3, r6);	 Catch:{ all -> 0x007e }
        r4 = 8;
        r5 = r12.zze;	 Catch:{ all -> 0x007e }
        r7 = 0;
        r8 = r12.zzd;	 Catch:{ all -> 0x007e }
        r9 = r12.zza();	 Catch:{ all -> 0x007e }
        r1.registerEvent(r2, r3, r4, r5, r6, r7, r8, r9);	 Catch:{ all -> 0x007e }
        r1 = r12.zzl;	 Catch:{ all -> 0x007e }
        r1 = r1 - r10;
        r12.zzl = r1;	 Catch:{ all -> 0x007e }
    L_0x0079:
        monitor-exit(r0);	 Catch:{ all -> 0x007e }
        r12.zza(r11);
        return;
    L_0x007e:
        r1 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x007e }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.stats.WakeLock.release():void");
    }

    private final void zza(int i) {
        if (this.zzb.isHeld()) {
            try {
                this.zzb.release();
            } catch (Throwable e) {
                if (e.getClass().equals(RuntimeException.class)) {
                    Log.e("WakeLock", String.valueOf(this.zze).concat(" was already released!"), e);
                } else {
                    throw e;
                }
            }
            this.zzb.isHeld();
        }
    }

    private final String zza(String str) {
        if (this.zzi) {
            return !TextUtils.isEmpty(str) ? str : this.zzf;
        } else {
            return this.zzf;
        }
    }

    @KeepForSdk
    public void setReferenceCounted(boolean z) {
        this.zzb.setReferenceCounted(z);
        this.zzi = z;
    }

    @KeepForSdk
    public boolean isHeld() {
        return this.zzb.isHeld();
    }
}
