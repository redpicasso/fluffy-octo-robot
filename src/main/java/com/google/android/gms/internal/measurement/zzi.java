package com.google.android.gms.internal.measurement;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.util.Log;
import androidx.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@TargetApi(24)
public final class zzi {
    @Nullable
    private static final Method zzg = zza();
    @Nullable
    private static final Method zzh = zzb();
    private final JobScheduler zzf;

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.google.android.gms.internal.measurement.zzi.zza():java.lang.reflect.Method, dom blocks: []
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
    @androidx.annotation.Nullable
    private static java.lang.reflect.Method zza() {
        /*
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 24;
        if (r0 < r1) goto L_0x0035;
    L_0x0006:
        r0 = android.app.job.JobScheduler.class;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r1 = "scheduleAsPackage";	 Catch:{ NoSuchMethodException -> 0x0026 }
        r2 = 4;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r2 = new java.lang.Class[r2];	 Catch:{ NoSuchMethodException -> 0x0026 }
        r3 = 0;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r4 = android.app.job.JobInfo.class;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r2[r3] = r4;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r3 = 1;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r4 = java.lang.String.class;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r2[r3] = r4;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r3 = 2;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r4 = java.lang.Integer.TYPE;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r2[r3] = r4;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r3 = 3;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r4 = java.lang.String.class;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r2[r3] = r4;	 Catch:{ NoSuchMethodException -> 0x0026 }
        r0 = r0.getDeclaredMethod(r1, r2);	 Catch:{ NoSuchMethodException -> 0x0026 }
        return r0;
        r0 = 6;
        r1 = "JobSchedulerCompat";
        r0 = android.util.Log.isLoggable(r1, r0);
        if (r0 == 0) goto L_0x0035;
    L_0x0030:
        r0 = "No scheduleAsPackage method available, falling back to schedule";
        android.util.Log.e(r1, r0);
    L_0x0035:
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzi.zza():java.lang.reflect.Method");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.google.android.gms.internal.measurement.zzi.zzb():java.lang.reflect.Method, dom blocks: []
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
    @androidx.annotation.Nullable
    private static java.lang.reflect.Method zzb() {
        /*
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 0;
        r2 = 24;
        if (r0 < r2) goto L_0x001f;
    L_0x0007:
        r0 = android.os.UserHandle.class;	 Catch:{ NoSuchMethodException -> 0x0010 }
        r2 = "myUserId";	 Catch:{ NoSuchMethodException -> 0x0010 }
        r0 = r0.getDeclaredMethod(r2, r1);	 Catch:{ NoSuchMethodException -> 0x0010 }
        return r0;
        r0 = 6;
        r2 = "JobSchedulerCompat";
        r0 = android.util.Log.isLoggable(r2, r0);
        if (r0 == 0) goto L_0x001f;
    L_0x001a:
        r0 = "No myUserId method available";
        android.util.Log.e(r2, r0);
    L_0x001f:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzi.zzb():java.lang.reflect.Method");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001f  */
    private static int zzc() {
        /*
        r0 = zzh;
        r1 = 0;
        if (r0 == 0) goto L_0x0024;
    L_0x0005:
        r2 = 0;
        r3 = new java.lang.Object[r1];	 Catch:{ IllegalAccessException -> 0x0015, InvocationTargetException -> 0x0013 }
        r0 = r0.invoke(r2, r3);	 Catch:{ IllegalAccessException -> 0x0015, InvocationTargetException -> 0x0013 }
        r0 = (java.lang.Integer) r0;	 Catch:{ IllegalAccessException -> 0x0015, InvocationTargetException -> 0x0013 }
        r0 = r0.intValue();	 Catch:{ IllegalAccessException -> 0x0015, InvocationTargetException -> 0x0013 }
        return r0;
    L_0x0013:
        r0 = move-exception;
        goto L_0x0016;
    L_0x0015:
        r0 = move-exception;
    L_0x0016:
        r2 = 6;
        r3 = "JobSchedulerCompat";
        r2 = android.util.Log.isLoggable(r3, r2);
        if (r2 == 0) goto L_0x0024;
    L_0x001f:
        r2 = "myUserId invocation illegal";
        android.util.Log.e(r3, r2, r0);
    L_0x0024:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzi.zzc():int");
    }

    private zzi(JobScheduler jobScheduler) {
        this.zzf = jobScheduler;
    }

    private final int zza(JobInfo jobInfo, String str, int i, String str2) {
        Throwable e;
        Method method = zzg;
        if (method != null) {
            try {
                jobInfo = ((Integer) method.invoke(this.zzf, new Object[]{jobInfo, str, Integer.valueOf(i), str2})).intValue();
                return jobInfo;
            } catch (IllegalAccessException e2) {
                e = e2;
                Log.e(str2, "error calling scheduleAsPackage", e);
                return this.zzf.schedule(jobInfo);
            } catch (InvocationTargetException e3) {
                e = e3;
                Log.e(str2, "error calling scheduleAsPackage", e);
                return this.zzf.schedule(jobInfo);
            }
        }
        return this.zzf.schedule(jobInfo);
    }

    public static int zza(Context context, JobInfo jobInfo, String str, String str2) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        if (zzg == null || context.checkSelfPermission("android.permission.UPDATE_DEVICE_STATS") != 0) {
            return jobScheduler.schedule(jobInfo);
        }
        return new zzi(jobScheduler).zza(jobInfo, str, zzc(), str2);
    }
}
