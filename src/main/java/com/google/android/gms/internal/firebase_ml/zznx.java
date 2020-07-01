package com.google.android.gms.internal.firebase_ml;

import android.app.Application;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class zznx {
    private static final GmsLogger zzape = new GmsLogger("ModelResourceManager", "");
    @GuardedBy("ModelResourceManager.class")
    private static zznx zzaqc;
    private final zznn zzapx = zznn.zzln();
    private final AtomicLong zzapy = new AtomicLong(300000);
    @GuardedBy("this")
    private final Set<zznw> zzapz = new HashSet();
    private final Set<zznw> zzaqa = new HashSet();
    private final ConcurrentHashMap<zznw, zznz> zzaqb = new ConcurrentHashMap();

    public static synchronized zznx zzb(FirebaseApp firebaseApp) {
        zznx zznx;
        synchronized (zznx.class) {
            if (zzaqc == null) {
                zzaqc = new zznx(firebaseApp);
            }
            zznx = zzaqc;
        }
        return zznx;
    }

    private zznx(FirebaseApp firebaseApp) {
        if (firebaseApp.getApplicationContext() instanceof Application) {
            BackgroundDetector.initialize((Application) firebaseApp.getApplicationContext());
        } else {
            zzape.e("ModelResourceManager", "No valid Application available and auto-manage cannot work");
        }
        BackgroundDetector.getInstance().addListener(new zzny(this));
        if (BackgroundDetector.getInstance().readCurrentStateIfPossible(true)) {
            this.zzapy.set(2000);
        }
    }

    public final synchronized void zza(@NonNull zznw zznw) {
        Preconditions.checkNotNull(zznw, "Model source can not be null");
        zzape.d("ModelResourceManager", "Add auto-managed model resource");
        if (this.zzapz.contains(zznw)) {
            zzape.i("ModelResourceManager", "The model resource is already registered.");
            return;
        }
        this.zzapz.add(zznw);
        zzb(zznw);
    }

    /* JADX WARNING: Missing block: B:9:0x001d, code:
            return;
     */
    private final synchronized void zzb(@androidx.annotation.Nullable com.google.android.gms.internal.firebase_ml.zznw r4) {
        /*
        r3 = this;
        monitor-enter(r3);
        if (r4 != 0) goto L_0x0005;
    L_0x0003:
        monitor-exit(r3);
        return;
    L_0x0005:
        r0 = r3.zzapx;	 Catch:{ all -> 0x001e }
        r1 = new com.google.android.gms.internal.firebase_ml.zznz;	 Catch:{ all -> 0x001e }
        r2 = "OPERATION_LOAD";
        r1.<init>(r3, r4, r2);	 Catch:{ all -> 0x001e }
        r0.zza(r1);	 Catch:{ all -> 0x001e }
        r0 = r3.zzapz;	 Catch:{ all -> 0x001e }
        r0 = r0.contains(r4);	 Catch:{ all -> 0x001e }
        if (r0 == 0) goto L_0x001c;
    L_0x0019:
        r3.zzc(r4);	 Catch:{ all -> 0x001e }
    L_0x001c:
        monitor-exit(r3);
        return;
    L_0x001e:
        r4 = move-exception;
        monitor-exit(r3);
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zznx.zzb(com.google.android.gms.internal.firebase_ml.zznw):void");
    }

    private final synchronized void zzlq() {
        for (zznw zzc : this.zzapz) {
            zzc(zzc);
        }
    }

    private final synchronized void zzc(zznw zznw) {
        Callable zze = zze(zznw);
        this.zzapx.zzb(zze);
        long j = this.zzapy.get();
        StringBuilder stringBuilder = new StringBuilder(62);
        stringBuilder.append("Rescheduling modelResource release after: ");
        stringBuilder.append(j);
        zzape.v("ModelResourceManager", stringBuilder.toString());
        this.zzapx.zza(zze, j);
    }

    public final synchronized void zzd(@Nullable zznw zznw) {
        if (zznw != null) {
            Callable zze = zze(zznw);
            this.zzapx.zzb(zze);
            this.zzapx.zza(zze, 0);
        }
    }

    private final zznz zze(zznw zznw) {
        this.zzaqb.putIfAbsent(zznw, new zznz(this, zznw, "OPERATION_RELEASE"));
        return (zznz) this.zzaqb.get(zznw);
    }

    @WorkerThread
    final void zzf(zznw zznw) throws FirebaseMLException {
        if (!this.zzaqa.contains(zznw)) {
            try {
                zznw.zzlp();
                this.zzaqa.add(zznw);
            } catch (Throwable e) {
                throw new FirebaseMLException("The load task failed", 13, e);
            }
        }
    }
}
