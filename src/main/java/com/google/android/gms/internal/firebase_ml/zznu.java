package com.google.android.gms.internal.firebase_ml;

import android.content.Context;
import android.content.res.Resources;
import android.os.SystemClock;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_ml.zzmd.zzac;
import com.google.android.gms.internal.firebase_ml.zzmd.zzq;
import com.google.android.gms.internal.firebase_ml.zzmd.zzq.zza;
import com.google.firebase.FirebaseApp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class zznu {
    private static final GmsLogger zzaoz = new GmsLogger("MlStatsLogger", "");
    @Nullable
    private static List<String> zzapn;
    @GuardedBy("MlStatsLogger.class")
    private static final Map<String, zznu> zzax = new HashMap();
    private final FirebaseApp zzapo;
    private final String zzapp;
    private final String zzapq;
    private final String zzapr;
    private final String zzaps;
    private final String zzapt;
    private final ClearcutLogger zzapu;
    @GuardedBy("this")
    private final Map<zzmn, Long> zzapv = new HashMap();
    private final int zzapw;

    private zznu(FirebaseApp firebaseApp, int i) {
        this.zzapo = firebaseApp;
        this.zzapw = i;
        String projectId = firebaseApp.getOptions().getProjectId();
        String str = "";
        if (projectId == null) {
            projectId = str;
        }
        this.zzapr = projectId;
        projectId = firebaseApp.getOptions().getGcmSenderId();
        if (projectId == null) {
            projectId = str;
        }
        this.zzaps = projectId;
        projectId = firebaseApp.getOptions().getApiKey();
        if (projectId == null) {
            projectId = str;
        }
        this.zzapt = projectId;
        Context applicationContext = firebaseApp.getApplicationContext();
        this.zzapu = ClearcutLogger.anonymousLogger(applicationContext, "FIREBASE_ML_SDK");
        this.zzapp = applicationContext.getPackageName();
        this.zzapq = zznk.zza(applicationContext);
    }

    public static synchronized zznu zza(@NonNull FirebaseApp firebaseApp, int i) {
        zznu zznu;
        synchronized (zznu.class) {
            Preconditions.checkNotNull(firebaseApp);
            String str = "";
            if (i == 1) {
                str = "_vision";
            } else if (i == 2) {
                str = "_model";
            } else if (i == 3) {
                str = "_natural_language";
            } else if (i == 4) {
                str = "_model_download";
            }
            String valueOf = String.valueOf(firebaseApp.getPersistenceKey());
            Object concat = str.length() != 0 ? valueOf.concat(str) : new String(valueOf);
            zznu = (zznu) zzax.get(concat);
            if (zznu == null) {
                zznu = new zznu(firebaseApp, i);
                zzax.put(concat, zznu);
            }
        }
        return zznu;
    }

    public final synchronized void zza(@NonNull zza zza, @NonNull zzmn zzmn) {
        if (zzfd()) {
            String zzlh = zza.zzjw().zzlh();
            if ("NA".equals(zzlh) || "".equals(zzlh)) {
                zzlh = "NA";
            }
            zza.zzb(zzmn).zzb(zzac.zzli().zzbq(this.zzapp).zzbr(this.zzapq).zzbs(this.zzapr).zzbv(this.zzaps).zzbw(this.zzapt).zzbu(zzlh).zzn(zzlo()).zzbt(zznl.zzll().getVersion("firebase-ml-common")));
            zzq zzq = (zzq) ((zzue) zza.zzrj());
            String valueOf = String.valueOf(zzq);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 30);
            stringBuilder.append("Logging FirebaseMlSdkLogEvent ");
            stringBuilder.append(valueOf);
            zzaoz.d("MlStatsLogger", stringBuilder.toString());
            this.zzapu.newEvent(zzq.toByteArray()).log();
            return;
        }
        zzaoz.d("MlStatsLogger", "Logging is disabled.");
    }

    public final synchronized void zza(@NonNull zznv zznv, @NonNull zzmn zzmn) {
        if (zzfd()) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            Object obj = (this.zzapv.get(zzmn) == null || elapsedRealtime - ((Long) this.zzapv.get(zzmn)).longValue() > TimeUnit.SECONDS.toMillis(30)) ? 1 : null;
            if (obj != null) {
                this.zzapv.put(zzmn, Long.valueOf(elapsedRealtime));
                zza(zznv.zzm(), zzmn);
            }
        }
    }

    @NonNull
    private static synchronized List<String> zzlo() {
        synchronized (zznu.class) {
            List<String> list;
            if (zzapn != null) {
                list = zzapn;
                return list;
            }
            LocaleListCompat locales = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration());
            zzapn = new ArrayList(locales.size());
            for (int i = 0; i < locales.size(); i++) {
                zzapn.add(zznk.zza(locales.get(i)));
            }
            list = zzapn;
            return list;
        }
    }

    private final boolean zzfd() {
        int i = this.zzapw;
        if (i == 1) {
            return zzoa.zzc(this.zzapo);
        }
        if (i != 2) {
            return i == 3 || i == 4;
        } else {
            return zzoa.zzd(this.zzapo);
        }
    }
}
