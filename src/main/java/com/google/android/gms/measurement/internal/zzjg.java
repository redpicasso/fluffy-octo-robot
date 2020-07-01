package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import com.adobe.xmp.XMPError;
import com.drew.metadata.exif.makernotes.OlympusRawInfoMakernoteDirectory;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzbs.zzc;
import com.google.android.gms.internal.measurement.zzbs.zze;
import com.google.android.gms.internal.measurement.zzbs.zzf;
import com.google.android.gms.internal.measurement.zzbs.zzg;
import com.google.android.gms.internal.measurement.zzbs.zzk;
import com.google.android.gms.internal.measurement.zzbw;
import com.google.android.gms.internal.measurement.zzey;
import com.google.common.net.HttpHeaders;
import com.google.logging.type.LogSeverity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class zzjg implements zzgh {
    private static volatile zzjg zzsn;
    private boolean zzdh;
    private final zzfj zzj;
    private zzfd zzso;
    private zzej zzsp;
    private zzx zzsq;
    private zzem zzsr;
    private zzjc zzss;
    private zzp zzst;
    private final zzjo zzsu;
    private zzhp zzsv;
    private boolean zzsw;
    private boolean zzsx;
    @VisibleForTesting
    private long zzsy;
    private List<Runnable> zzsz;
    private int zzta;
    private int zztb;
    private boolean zztc;
    private boolean zztd;
    private boolean zzte;
    private FileLock zztf;
    private FileChannel zztg;
    private List<Long> zzth;
    private List<Long> zzti;
    private long zztj;

    class zza implements zzz {
        zzg zztn;
        List<Long> zzto;
        List<zzc> zztp;
        private long zztq;

        private zza() {
        }

        public final void zzb(zzg zzg) {
            Preconditions.checkNotNull(zzg);
            this.zztn = zzg;
        }

        public final boolean zza(long j, zzc zzc) {
            Preconditions.checkNotNull(zzc);
            if (this.zztp == null) {
                this.zztp = new ArrayList();
            }
            if (this.zzto == null) {
                this.zzto = new ArrayList();
            }
            if (this.zztp.size() > 0 && zza((zzc) this.zztp.get(0)) != zza(zzc)) {
                return false;
            }
            long zzuk = this.zztq + ((long) zzc.zzuk());
            if (zzuk >= ((long) Math.max(0, ((Integer) zzak.zzgn.get(null)).intValue()))) {
                return false;
            }
            this.zztq = zzuk;
            this.zztp.add(zzc);
            this.zzto.add(Long.valueOf(j));
            if (this.zztp.size() >= Math.max(1, ((Integer) zzak.zzgo.get(null)).intValue())) {
                return false;
            }
            return true;
        }

        private static long zza(zzc zzc) {
            return ((zzc.getTimestampMillis() / 1000) / 60) / 60;
        }

        /* synthetic */ zza(zzjg zzjg, zzjj zzjj) {
            this();
        }
    }

    public static zzjg zzm(Context context) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzsn == null) {
            synchronized (zzjg.class) {
                if (zzsn == null) {
                    zzsn = new zzjg(new zzjm(context));
                }
            }
        }
        return zzsn;
    }

    private zzjg(zzjm zzjm) {
        this(zzjm, null);
    }

    private zzjg(zzjm zzjm, zzfj zzfj) {
        this.zzdh = false;
        Preconditions.checkNotNull(zzjm);
        this.zzj = zzfj.zza(zzjm.zzob, null);
        this.zztj = -1;
        zzjh zzjo = new zzjo(this);
        zzjo.initialize();
        this.zzsu = zzjo;
        zzjo = new zzej(this);
        zzjo.initialize();
        this.zzsp = zzjo;
        zzjo = new zzfd(this);
        zzjo.initialize();
        this.zzso = zzjo;
        this.zzj.zzaa().zza(new zzjj(this, zzjm));
    }

    @WorkerThread
    private final void zza(zzjm zzjm) {
        this.zzj.zzaa().zzo();
        zzjh zzx = new zzx(this);
        zzx.initialize();
        this.zzsq = zzx;
        this.zzj.zzad().zza(this.zzso);
        zzx = new zzp(this);
        zzx.initialize();
        this.zzst = zzx;
        zzx = new zzhp(this);
        zzx.initialize();
        this.zzsv = zzx;
        zzx = new zzjc(this);
        zzx.initialize();
        this.zzss = zzx;
        this.zzsr = new zzem(this);
        if (this.zzta != this.zztb) {
            this.zzj.zzab().zzgk().zza("Not all upload components initialized", Integer.valueOf(this.zzta), Integer.valueOf(this.zztb));
        }
        this.zzdh = true;
    }

    @WorkerThread
    protected final void start() {
        this.zzj.zzaa().zzo();
        zzgy().zzca();
        if (this.zzj.zzac().zzlj.get() == 0) {
            this.zzj.zzac().zzlj.set(this.zzj.zzx().currentTimeMillis());
        }
        zzjn();
    }

    public final zzr zzae() {
        return this.zzj.zzae();
    }

    public final zzs zzad() {
        return this.zzj.zzad();
    }

    public final zzef zzab() {
        return this.zzj.zzab();
    }

    public final zzfc zzaa() {
        return this.zzj.zzaa();
    }

    public final zzfd zzgz() {
        zza(this.zzso);
        return this.zzso;
    }

    public final zzej zzjf() {
        zza(this.zzsp);
        return this.zzsp;
    }

    public final zzx zzgy() {
        zza(this.zzsq);
        return this.zzsq;
    }

    private final zzem zzjg() {
        zzem zzem = this.zzsr;
        if (zzem != null) {
            return zzem;
        }
        throw new IllegalStateException("Network broadcast receiver not created");
    }

    private final zzjc zzjh() {
        zza(this.zzss);
        return this.zzss;
    }

    public final zzp zzgx() {
        zza(this.zzst);
        return this.zzst;
    }

    public final zzhp zzji() {
        zza(this.zzsv);
        return this.zzsv;
    }

    public final zzjo zzgw() {
        zza(this.zzsu);
        return this.zzsu;
    }

    public final zzed zzy() {
        return this.zzj.zzy();
    }

    public final Context getContext() {
        return this.zzj.getContext();
    }

    public final Clock zzx() {
        return this.zzj.zzx();
    }

    public final zzjs zzz() {
        return this.zzj.zzz();
    }

    @WorkerThread
    private final void zzo() {
        this.zzj.zzaa().zzo();
    }

    final void zzjj() {
        if (!this.zzdh) {
            throw new IllegalStateException("UploadController is not initialized");
        }
    }

    private static void zza(zzjh zzjh) {
        if (zzjh == null) {
            throw new IllegalStateException("Upload Component not created");
        } else if (!zzjh.isInitialized()) {
            String valueOf = String.valueOf(zzjh.getClass());
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 27);
            stringBuilder.append("Component not initialized: ");
            stringBuilder.append(valueOf);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    final void zze(zzn zzn) {
        zzo();
        zzjj();
        Preconditions.checkNotEmpty(zzn.packageName);
        zzg(zzn);
    }

    private final long zzjk() {
        long currentTimeMillis = this.zzj.zzx().currentTimeMillis();
        zzgf zzac = this.zzj.zzac();
        zzac.zzbi();
        zzac.zzo();
        long j = zzac.zzln.get();
        if (j == 0) {
            j = 1 + ((long) zzac.zzz().zzjw().nextInt(86400000));
            zzac.zzln.set(j);
        }
        return ((((currentTimeMillis + j) / 1000) / 60) / 60) / 24;
    }

    @WorkerThread
    final void zzd(zzai zzai, String str) {
        zzai zzai2 = zzai;
        String str2 = str;
        zzf zzab = zzgy().zzab(str2);
        if (zzab == null || TextUtils.isEmpty(zzab.zzal())) {
            this.zzj.zzab().zzgr().zza("No app data available; dropping event", str2);
            return;
        }
        Boolean zzc = zzc(zzab);
        if (zzc == null) {
            if (!"_ui".equals(zzai2.name)) {
                this.zzj.zzab().zzgn().zza("Could not find package. appId", zzef.zzam(str));
            }
        } else if (!zzc.booleanValue()) {
            this.zzj.zzab().zzgk().zza("App version does not match; dropping event. appId", zzef.zzam(str));
            return;
        }
        zzn zzn = r2;
        zzf zzf = zzab;
        zzn zzn2 = new zzn(str, zzab.getGmpAppId(), zzab.zzal(), zzab.zzam(), zzab.zzan(), zzab.zzao(), zzab.zzap(), null, zzab.isMeasurementEnabled(), false, zzf.getFirebaseInstanceId(), zzf.zzbd(), 0, 0, zzf.zzbe(), zzf.zzbf(), false, zzf.zzah(), zzf.zzbg(), zzf.zzaq(), zzf.zzbh());
        zzc(zzai2, zzn);
    }

    @WorkerThread
    final void zzc(zzai zzai, zzn zzn) {
        zzai zzai2 = zzai;
        zzn zzn2 = zzn;
        Preconditions.checkNotNull(zzn);
        Preconditions.checkNotEmpty(zzn2.packageName);
        zzo();
        zzjj();
        String str = zzn2.packageName;
        long j = zzai2.zzfu;
        if (!zzgw().zze(zzai2, zzn2)) {
            return;
        }
        if (zzn2.zzcq) {
            if (this.zzj.zzad().zze(str, zzak.zzix) && zzn2.zzcw != null) {
                if (zzn2.zzcw.contains(zzai2.name)) {
                    Bundle zzcv = zzai2.zzfq.zzcv();
                    zzcv.putLong("ga_safelisted", 1);
                    zzai2 = new zzai(zzai2.name, new zzah(zzcv), zzai2.origin, zzai2.zzfu);
                } else {
                    this.zzj.zzab().zzgr().zza("Dropping non-safelisted event. appId, event name, origin", str, zzai2.name, zzai2.origin);
                    return;
                }
            }
            zzgy().beginTransaction();
            try {
                List emptyList;
                zzgf zzgy = zzgy();
                Preconditions.checkNotEmpty(str);
                zzgy.zzo();
                zzgy.zzbi();
                int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
                if (i < 0) {
                    zzgy.zzab().zzgn().zza("Invalid time querying timed out conditional properties", zzef.zzam(str), Long.valueOf(j));
                    emptyList = Collections.emptyList();
                } else {
                    emptyList = zzgy.zzb("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{str, String.valueOf(j)});
                }
                for (zzq zzq : emptyList) {
                    if (zzq != null) {
                        this.zzj.zzab().zzgr().zza("User property timed out", zzq.packageName, this.zzj.zzy().zzal(zzq.zzdw.name), zzq.zzdw.getValue());
                        if (zzq.zzdx != null) {
                            zzd(new zzai(zzq.zzdx, j), zzn2);
                        }
                        zzgy().zzg(str, zzq.zzdw.name);
                    }
                }
                zzgy = zzgy();
                Preconditions.checkNotEmpty(str);
                zzgy.zzo();
                zzgy.zzbi();
                if (i < 0) {
                    zzgy.zzab().zzgn().zza("Invalid time querying expired conditional properties", zzef.zzam(str), Long.valueOf(j));
                    emptyList = Collections.emptyList();
                } else {
                    emptyList = zzgy.zzb("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{str, String.valueOf(j)});
                }
                List arrayList = new ArrayList(r4.size());
                for (zzq zzq2 : r4) {
                    if (zzq2 != null) {
                        this.zzj.zzab().zzgr().zza("User property expired", zzq2.packageName, this.zzj.zzy().zzal(zzq2.zzdw.name), zzq2.zzdw.getValue());
                        zzgy().zzd(str, zzq2.zzdw.name);
                        if (zzq2.zzdz != null) {
                            arrayList.add(zzq2.zzdz);
                        }
                        zzgy().zzg(str, zzq2.zzdw.name);
                    }
                }
                ArrayList arrayList2 = (ArrayList) arrayList;
                int size = arrayList2.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList2.get(i2);
                    i2++;
                    zzd(new zzai((zzai) obj, j), zzn2);
                }
                zzgy = zzgy();
                String str2 = zzai2.name;
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotEmpty(str2);
                zzgy.zzo();
                zzgy.zzbi();
                List emptyList2;
                if (i < 0) {
                    zzgy.zzab().zzgn().zza("Invalid time querying triggered conditional properties", zzef.zzam(str), zzgy.zzy().zzaj(str2), Long.valueOf(j));
                    emptyList2 = Collections.emptyList();
                } else {
                    emptyList2 = zzgy.zzb("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{str, str2, String.valueOf(j)});
                }
                List arrayList3 = new ArrayList(emptyList2.size());
                for (zzq zzq3 : emptyList2) {
                    if (zzq3 != null) {
                        zzjn zzjn = zzq3.zzdw;
                        zzjp zzjp = r4;
                        zzjp zzjp2 = new zzjp(zzq3.packageName, zzq3.origin, zzjn.name, j, zzjn.getValue());
                        if (zzgy().zza(zzjp)) {
                            this.zzj.zzab().zzgr().zza("User property triggered", zzq3.packageName, this.zzj.zzy().zzal(zzjp.name), zzjp.value);
                        } else {
                            this.zzj.zzab().zzgk().zza("Too many active user properties, ignoring", zzef.zzam(zzq3.packageName), this.zzj.zzy().zzal(zzjp.name), zzjp.value);
                        }
                        if (zzq3.zzdy != null) {
                            arrayList3.add(zzq3.zzdy);
                        }
                        zzq3.zzdw = new zzjn(zzjp);
                        zzq3.active = true;
                        zzgy().zza(zzq3);
                    }
                }
                zzd(zzai2, zzn2);
                ArrayList arrayList4 = (ArrayList) arrayList3;
                int size2 = arrayList4.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj2 = arrayList4.get(i3);
                    i3++;
                    zzd(new zzai((zzai) obj2, j), zzn2);
                }
                zzgy().setTransactionSuccessful();
            } finally {
                zzgy().endTransaction();
            }
        } else {
            zzg(zzn2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:91:0x0321  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x02f4 A:{Catch:{ SQLiteException -> 0x0237, all -> 0x08c5 }} */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x02a6 A:{Catch:{ SQLiteException -> 0x0237, all -> 0x08c5 }} */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x02f4 A:{Catch:{ SQLiteException -> 0x0237, all -> 0x08c5 }} */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0321  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x02a6 A:{Catch:{ SQLiteException -> 0x0237, all -> 0x08c5 }} */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0321  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x02f4 A:{Catch:{ SQLiteException -> 0x0237, all -> 0x08c5 }} */
    /* JADX WARNING: Missing block: B:239:0x0846, code:
            if (r10.zzej < ((long) r1.zzj.zzad().zzi(r4.zzce))) goto L_0x084a;
     */
    @androidx.annotation.WorkerThread
    private final void zzd(com.google.android.gms.measurement.internal.zzai r28, com.google.android.gms.measurement.internal.zzn r29) {
        /*
        r27 = this;
        r1 = r27;
        r2 = r28;
        r3 = r29;
        r4 = "_s";
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r29);
        r5 = r3.packageName;
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r5);
        r5 = java.lang.System.nanoTime();
        r27.zzo();
        r27.zzjj();
        r15 = r3.packageName;
        r7 = r27.zzgw();
        r7 = r7.zze(r2, r3);
        if (r7 != 0) goto L_0x0027;
    L_0x0026:
        return;
    L_0x0027:
        r7 = r3.zzcq;
        if (r7 != 0) goto L_0x002f;
    L_0x002b:
        r1.zzg(r3);
        return;
    L_0x002f:
        r7 = r27.zzgz();
        r8 = r2.name;
        r7 = r7.zzk(r15, r8);
        r14 = "_err";
        r13 = 0;
        r12 = 1;
        if (r7 == 0) goto L_0x00db;
    L_0x003f:
        r3 = r1.zzj;
        r3 = r3.zzab();
        r3 = r3.zzgn();
        r4 = com.google.android.gms.measurement.internal.zzef.zzam(r15);
        r5 = r1.zzj;
        r5 = r5.zzy();
        r6 = r2.name;
        r5 = r5.zzaj(r6);
        r6 = "Dropping blacklisted event. appId";
        r3.zza(r6, r4, r5);
        r3 = r27.zzgz();
        r3 = r3.zzbc(r15);
        if (r3 != 0) goto L_0x0075;
    L_0x0068:
        r3 = r27.zzgz();
        r3 = r3.zzbd(r15);
        if (r3 == 0) goto L_0x0073;
    L_0x0072:
        goto L_0x0075;
    L_0x0073:
        r3 = 0;
        goto L_0x0076;
    L_0x0075:
        r3 = 1;
    L_0x0076:
        if (r3 != 0) goto L_0x0091;
    L_0x0078:
        r4 = r2.name;
        r4 = r14.equals(r4);
        if (r4 != 0) goto L_0x0091;
    L_0x0080:
        r4 = r1.zzj;
        r7 = r4.zzz();
        r9 = 11;
        r11 = r2.name;
        r12 = 0;
        r10 = "_ev";
        r8 = r15;
        r7.zza(r8, r9, r10, r11, r12);
    L_0x0091:
        if (r3 == 0) goto L_0x00da;
    L_0x0093:
        r2 = r27.zzgy();
        r2 = r2.zzab(r15);
        if (r2 == 0) goto L_0x00da;
    L_0x009d:
        r3 = r2.zzat();
        r5 = r2.zzas();
        r3 = java.lang.Math.max(r3, r5);
        r5 = r1.zzj;
        r5 = r5.zzx();
        r5 = r5.currentTimeMillis();
        r5 = r5 - r3;
        r3 = java.lang.Math.abs(r5);
        r5 = com.google.android.gms.measurement.internal.zzak.zzhe;
        r5 = r5.get(r13);
        r5 = (java.lang.Long) r5;
        r5 = r5.longValue();
        r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r7 <= 0) goto L_0x00da;
    L_0x00c8:
        r3 = r1.zzj;
        r3 = r3.zzab();
        r3 = r3.zzgr();
        r4 = "Fetching config for blacklisted app";
        r3.zzao(r4);
        r1.zzb(r2);
    L_0x00da:
        return;
    L_0x00db:
        r7 = r1.zzj;
        r7 = r7.zzab();
        r10 = 2;
        r7 = r7.isLoggable(r10);
        if (r7 == 0) goto L_0x0101;
    L_0x00e8:
        r7 = r1.zzj;
        r7 = r7.zzab();
        r7 = r7.zzgs();
        r8 = r1.zzj;
        r8 = r8.zzy();
        r8 = r8.zzb(r2);
        r9 = "Logging event";
        r7.zza(r9, r8);
    L_0x0101:
        r7 = r27.zzgy();
        r7.beginTransaction();
        r1.zzg(r3);	 Catch:{ all -> 0x08c5 }
        r7 = "_iap";
        r8 = r2.name;	 Catch:{ all -> 0x08c5 }
        r7 = r7.equals(r8);	 Catch:{ all -> 0x08c5 }
        r8 = "ecommerce_purchase";
        if (r7 != 0) goto L_0x0125;
    L_0x0117:
        r7 = r2.name;	 Catch:{ all -> 0x08c5 }
        r7 = r8.equals(r7);	 Catch:{ all -> 0x08c5 }
        if (r7 == 0) goto L_0x0120;
    L_0x011f:
        goto L_0x0125;
    L_0x0120:
        r23 = r5;
        r6 = 0;
        goto L_0x02b5;
    L_0x0125:
        r7 = r2.zzfq;	 Catch:{ all -> 0x08c5 }
        r9 = "currency";
        r7 = r7.getString(r9);	 Catch:{ all -> 0x08c5 }
        r9 = r2.name;	 Catch:{ all -> 0x08c5 }
        r8 = r8.equals(r9);	 Catch:{ all -> 0x08c5 }
        r9 = "value";
        if (r8 == 0) goto L_0x0189;
    L_0x0137:
        r8 = r2.zzfq;	 Catch:{ all -> 0x08c5 }
        r8 = r8.zzah(r9);	 Catch:{ all -> 0x08c5 }
        r16 = r8.doubleValue();	 Catch:{ all -> 0x08c5 }
        r18 = 4696837146684686336; // 0x412e848000000000 float:0.0 double:1000000.0;
        r16 = r16 * r18;
        r20 = 0;
        r8 = (r16 > r20 ? 1 : (r16 == r20 ? 0 : -1));
        if (r8 != 0) goto L_0x015b;
    L_0x014e:
        r8 = r2.zzfq;	 Catch:{ all -> 0x08c5 }
        r8 = r8.getLong(r9);	 Catch:{ all -> 0x08c5 }
        r8 = r8.longValue();	 Catch:{ all -> 0x08c5 }
        r8 = (double) r8;	 Catch:{ all -> 0x08c5 }
        r16 = r8 * r18;
    L_0x015b:
        r8 = 4890909195324358656; // 0x43e0000000000000 float:0.0 double:9.223372036854776E18;
        r18 = (r16 > r8 ? 1 : (r16 == r8 ? 0 : -1));
        if (r18 > 0) goto L_0x016c;
    L_0x0161:
        r8 = -4332462841530417152; // 0xc3e0000000000000 float:0.0 double:-9.223372036854776E18;
        r18 = (r16 > r8 ? 1 : (r16 == r8 ? 0 : -1));
        if (r18 < 0) goto L_0x016c;
    L_0x0167:
        r8 = java.lang.Math.round(r16);	 Catch:{ all -> 0x08c5 }
        goto L_0x0193;
    L_0x016c:
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzab();	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzgn();	 Catch:{ all -> 0x08c5 }
        r8 = "Data lost. Currency value is too big. appId";
        r9 = com.google.android.gms.measurement.internal.zzef.zzam(r15);	 Catch:{ all -> 0x08c5 }
        r10 = java.lang.Double.valueOf(r16);	 Catch:{ all -> 0x08c5 }
        r7.zza(r8, r9, r10);	 Catch:{ all -> 0x08c5 }
        r23 = r5;
        r6 = 0;
        r11 = 0;
        goto L_0x02a4;
    L_0x0189:
        r8 = r2.zzfq;	 Catch:{ all -> 0x08c5 }
        r8 = r8.getLong(r9);	 Catch:{ all -> 0x08c5 }
        r8 = r8.longValue();	 Catch:{ all -> 0x08c5 }
    L_0x0193:
        r10 = android.text.TextUtils.isEmpty(r7);	 Catch:{ all -> 0x08c5 }
        if (r10 != 0) goto L_0x02a0;
    L_0x0199:
        r10 = java.util.Locale.US;	 Catch:{ all -> 0x08c5 }
        r7 = r7.toUpperCase(r10);	 Catch:{ all -> 0x08c5 }
        r10 = "[A-Z]{3}";
        r10 = r7.matches(r10);	 Catch:{ all -> 0x08c5 }
        if (r10 == 0) goto L_0x02a0;
    L_0x01a7:
        r10 = "_ltv_";
        r7 = java.lang.String.valueOf(r7);	 Catch:{ all -> 0x08c5 }
        r16 = r7.length();	 Catch:{ all -> 0x08c5 }
        if (r16 == 0) goto L_0x01b8;
    L_0x01b3:
        r7 = r10.concat(r7);	 Catch:{ all -> 0x08c5 }
        goto L_0x01bd;
    L_0x01b8:
        r7 = new java.lang.String;	 Catch:{ all -> 0x08c5 }
        r7.<init>(r10);	 Catch:{ all -> 0x08c5 }
    L_0x01bd:
        r10 = r7;
        r7 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r7 = r7.zze(r15, r10);	 Catch:{ all -> 0x08c5 }
        if (r7 == 0) goto L_0x01fe;
    L_0x01c8:
        r11 = r7.value;	 Catch:{ all -> 0x08c5 }
        r11 = r11 instanceof java.lang.Long;	 Catch:{ all -> 0x08c5 }
        if (r11 != 0) goto L_0x01cf;
    L_0x01ce:
        goto L_0x01fe;
    L_0x01cf:
        r7 = r7.value;	 Catch:{ all -> 0x08c5 }
        r7 = (java.lang.Long) r7;	 Catch:{ all -> 0x08c5 }
        r19 = r7.longValue();	 Catch:{ all -> 0x08c5 }
        r17 = new com.google.android.gms.measurement.internal.zzjp;	 Catch:{ all -> 0x08c5 }
        r11 = r2.origin;	 Catch:{ all -> 0x08c5 }
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzx();	 Catch:{ all -> 0x08c5 }
        r21 = r7.currentTimeMillis();	 Catch:{ all -> 0x08c5 }
        r19 = r19 + r8;
        r19 = java.lang.Long.valueOf(r19);	 Catch:{ all -> 0x08c5 }
        r7 = r17;
        r8 = r15;
        r9 = r11;
        r11 = 2;
        r23 = r5;
        r5 = 1;
        r6 = 0;
        r11 = r21;
        r13 = r19;
        r7.<init>(r8, r9, r10, r11, r13);	 Catch:{ all -> 0x08c5 }
        r5 = r17;
        goto L_0x0265;
    L_0x01fe:
        r23 = r5;
        r5 = 1;
        r6 = 0;
        r7 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r11 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r11 = r11.zzad();	 Catch:{ all -> 0x08c5 }
        r12 = com.google.android.gms.measurement.internal.zzak.zzhj;	 Catch:{ all -> 0x08c5 }
        r11 = r11.zzb(r15, r12);	 Catch:{ all -> 0x08c5 }
        r11 = r11 - r5;
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r15);	 Catch:{ all -> 0x08c5 }
        r7.zzo();	 Catch:{ all -> 0x08c5 }
        r7.zzbi();	 Catch:{ all -> 0x08c5 }
        r12 = r7.getWritableDatabase();	 Catch:{ SQLiteException -> 0x0237 }
        r13 = "delete from user_attributes where app_id=? and name in (select name from user_attributes where app_id=? and name like '_ltv_%' order by set_timestamp desc limit ?,10);";
        r5 = 3;
        r5 = new java.lang.String[r5];	 Catch:{ SQLiteException -> 0x0237 }
        r5[r6] = r15;	 Catch:{ SQLiteException -> 0x0237 }
        r16 = 1;
        r5[r16] = r15;	 Catch:{ SQLiteException -> 0x0237 }
        r11 = java.lang.String.valueOf(r11);	 Catch:{ SQLiteException -> 0x0237 }
        r16 = 2;
        r5[r16] = r11;	 Catch:{ SQLiteException -> 0x0237 }
        r12.execSQL(r13, r5);	 Catch:{ SQLiteException -> 0x0237 }
        goto L_0x024a;
    L_0x0237:
        r0 = move-exception;
        r5 = r0;
        r7 = r7.zzab();	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzgk();	 Catch:{ all -> 0x08c5 }
        r11 = "Error pruning currencies. appId";
        r12 = com.google.android.gms.measurement.internal.zzef.zzam(r15);	 Catch:{ all -> 0x08c5 }
        r7.zza(r11, r12, r5);	 Catch:{ all -> 0x08c5 }
    L_0x024a:
        r5 = new com.google.android.gms.measurement.internal.zzjp;	 Catch:{ all -> 0x08c5 }
        r11 = r2.origin;	 Catch:{ all -> 0x08c5 }
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzx();	 Catch:{ all -> 0x08c5 }
        r12 = r7.currentTimeMillis();	 Catch:{ all -> 0x08c5 }
        r16 = java.lang.Long.valueOf(r8);	 Catch:{ all -> 0x08c5 }
        r7 = r5;
        r8 = r15;
        r9 = r11;
        r11 = r12;
        r13 = r16;
        r7.<init>(r8, r9, r10, r11, r13);	 Catch:{ all -> 0x08c5 }
    L_0x0265:
        r7 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r7 = r7.zza(r5);	 Catch:{ all -> 0x08c5 }
        if (r7 != 0) goto L_0x02a3;
    L_0x026f:
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzab();	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzgk();	 Catch:{ all -> 0x08c5 }
        r8 = "Too many unique user properties are set. Ignoring user property. appId";
        r9 = com.google.android.gms.measurement.internal.zzef.zzam(r15);	 Catch:{ all -> 0x08c5 }
        r10 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r10 = r10.zzy();	 Catch:{ all -> 0x08c5 }
        r11 = r5.name;	 Catch:{ all -> 0x08c5 }
        r10 = r10.zzal(r11);	 Catch:{ all -> 0x08c5 }
        r5 = r5.value;	 Catch:{ all -> 0x08c5 }
        r7.zza(r8, r9, r10, r5);	 Catch:{ all -> 0x08c5 }
        r5 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r5.zzz();	 Catch:{ all -> 0x08c5 }
        r9 = 9;
        r10 = 0;
        r11 = 0;
        r12 = 0;
        r8 = r15;
        r7.zza(r8, r9, r10, r11, r12);	 Catch:{ all -> 0x08c5 }
        goto L_0x02a3;
    L_0x02a0:
        r23 = r5;
        r6 = 0;
    L_0x02a3:
        r11 = 1;
    L_0x02a4:
        if (r11 != 0) goto L_0x02b5;
    L_0x02a6:
        r2 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r2.setTransactionSuccessful();	 Catch:{ all -> 0x08c5 }
        r2 = r27.zzgy();
        r2.endTransaction();
        return;
    L_0x02b5:
        r5 = r2.name;	 Catch:{ all -> 0x08c5 }
        r5 = com.google.android.gms.measurement.internal.zzjs.zzbk(r5);	 Catch:{ all -> 0x08c5 }
        r7 = r2.name;	 Catch:{ all -> 0x08c5 }
        r16 = r14.equals(r7);	 Catch:{ all -> 0x08c5 }
        r7 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r8 = r27.zzjk();	 Catch:{ all -> 0x08c5 }
        r11 = 1;
        r13 = 0;
        r17 = 0;
        r10 = r15;
        r12 = r5;
        r14 = r16;
        r18 = r15;
        r15 = r17;
        r7 = r7.zza(r8, r10, r11, r12, r13, r14, r15);	 Catch:{ all -> 0x08c5 }
        r8 = r7.zzeg;	 Catch:{ all -> 0x08c5 }
        r10 = com.google.android.gms.measurement.internal.zzak.zzgp;	 Catch:{ all -> 0x08c5 }
        r14 = 0;
        r10 = r10.get(r14);	 Catch:{ all -> 0x08c5 }
        r10 = (java.lang.Integer) r10;	 Catch:{ all -> 0x08c5 }
        r10 = r10.intValue();	 Catch:{ all -> 0x08c5 }
        r10 = (long) r10;	 Catch:{ all -> 0x08c5 }
        r8 = r8 - r10;
        r10 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r12 = 1;
        r14 = 0;
        r17 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1));
        if (r17 <= 0) goto L_0x0321;
    L_0x02f4:
        r8 = r8 % r10;
        r2 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1));
        if (r2 != 0) goto L_0x0312;
    L_0x02f9:
        r2 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r2 = r2.zzab();	 Catch:{ all -> 0x08c5 }
        r2 = r2.zzgk();	 Catch:{ all -> 0x08c5 }
        r3 = "Data loss. Too many events logged. appId, count";
        r4 = com.google.android.gms.measurement.internal.zzef.zzam(r18);	 Catch:{ all -> 0x08c5 }
        r5 = r7.zzeg;	 Catch:{ all -> 0x08c5 }
        r5 = java.lang.Long.valueOf(r5);	 Catch:{ all -> 0x08c5 }
        r2.zza(r3, r4, r5);	 Catch:{ all -> 0x08c5 }
    L_0x0312:
        r2 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r2.setTransactionSuccessful();	 Catch:{ all -> 0x08c5 }
        r2 = r27.zzgy();
        r2.endTransaction();
        return;
    L_0x0321:
        if (r5 == 0) goto L_0x0379;
    L_0x0323:
        r8 = r7.zzef;	 Catch:{ all -> 0x08c5 }
        r6 = com.google.android.gms.measurement.internal.zzak.zzgr;	 Catch:{ all -> 0x08c5 }
        r12 = 0;
        r6 = r6.get(r12);	 Catch:{ all -> 0x08c5 }
        r6 = (java.lang.Integer) r6;	 Catch:{ all -> 0x08c5 }
        r6 = r6.intValue();	 Catch:{ all -> 0x08c5 }
        r12 = (long) r6;	 Catch:{ all -> 0x08c5 }
        r8 = r8 - r12;
        r6 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1));
        if (r6 <= 0) goto L_0x0379;
    L_0x0338:
        r8 = r8 % r10;
        r3 = 1;
        r5 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1));
        if (r5 != 0) goto L_0x0358;
    L_0x033f:
        r3 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r3 = r3.zzab();	 Catch:{ all -> 0x08c5 }
        r3 = r3.zzgk();	 Catch:{ all -> 0x08c5 }
        r4 = "Data loss. Too many public events logged. appId, count";
        r5 = com.google.android.gms.measurement.internal.zzef.zzam(r18);	 Catch:{ all -> 0x08c5 }
        r6 = r7.zzef;	 Catch:{ all -> 0x08c5 }
        r6 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x08c5 }
        r3.zza(r4, r5, r6);	 Catch:{ all -> 0x08c5 }
    L_0x0358:
        r3 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r3.zzz();	 Catch:{ all -> 0x08c5 }
        r9 = 16;
        r10 = "_ev";
        r11 = r2.name;	 Catch:{ all -> 0x08c5 }
        r12 = 0;
        r8 = r18;
        r7.zza(r8, r9, r10, r11, r12);	 Catch:{ all -> 0x08c5 }
        r2 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r2.setTransactionSuccessful();	 Catch:{ all -> 0x08c5 }
        r2 = r27.zzgy();
        r2.endTransaction();
        return;
    L_0x0379:
        if (r16 == 0) goto L_0x03cb;
    L_0x037b:
        r8 = r7.zzei;	 Catch:{ all -> 0x08c5 }
        r6 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzad();	 Catch:{ all -> 0x08c5 }
        r10 = r3.packageName;	 Catch:{ all -> 0x08c5 }
        r11 = com.google.android.gms.measurement.internal.zzak.zzgq;	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzb(r10, r11);	 Catch:{ all -> 0x08c5 }
        r10 = 1000000; // 0xf4240 float:1.401298E-39 double:4.940656E-318;
        r6 = java.lang.Math.min(r10, r6);	 Catch:{ all -> 0x08c5 }
        r12 = 0;
        r6 = java.lang.Math.max(r12, r6);	 Catch:{ all -> 0x08c5 }
        r10 = (long) r6;	 Catch:{ all -> 0x08c5 }
        r8 = r8 - r10;
        r6 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1));
        if (r6 <= 0) goto L_0x03cc;
    L_0x039d:
        r10 = 1;
        r2 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r2 != 0) goto L_0x03bc;
    L_0x03a3:
        r2 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r2 = r2.zzab();	 Catch:{ all -> 0x08c5 }
        r2 = r2.zzgk();	 Catch:{ all -> 0x08c5 }
        r3 = "Too many error events logged. appId, count";
        r4 = com.google.android.gms.measurement.internal.zzef.zzam(r18);	 Catch:{ all -> 0x08c5 }
        r5 = r7.zzei;	 Catch:{ all -> 0x08c5 }
        r5 = java.lang.Long.valueOf(r5);	 Catch:{ all -> 0x08c5 }
        r2.zza(r3, r4, r5);	 Catch:{ all -> 0x08c5 }
    L_0x03bc:
        r2 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r2.setTransactionSuccessful();	 Catch:{ all -> 0x08c5 }
        r2 = r27.zzgy();
        r2.endTransaction();
        return;
    L_0x03cb:
        r12 = 0;
    L_0x03cc:
        r6 = r2.zzfq;	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzcv();	 Catch:{ all -> 0x08c5 }
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzz();	 Catch:{ all -> 0x08c5 }
        r8 = "_o";
        r9 = r2.origin;	 Catch:{ all -> 0x08c5 }
        r7.zza(r6, r8, r9);	 Catch:{ all -> 0x08c5 }
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzz();	 Catch:{ all -> 0x08c5 }
        r13 = r18;
        r7 = r7.zzbr(r13);	 Catch:{ all -> 0x08c5 }
        r11 = "_r";
        if (r7 == 0) goto L_0x040d;
    L_0x03ef:
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzz();	 Catch:{ all -> 0x08c5 }
        r8 = "_dbg";
        r9 = 1;
        r12 = java.lang.Long.valueOf(r9);	 Catch:{ all -> 0x08c5 }
        r7.zza(r6, r8, r12);	 Catch:{ all -> 0x08c5 }
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzz();	 Catch:{ all -> 0x08c5 }
        r8 = java.lang.Long.valueOf(r9);	 Catch:{ all -> 0x08c5 }
        r7.zza(r6, r11, r8);	 Catch:{ all -> 0x08c5 }
    L_0x040d:
        r7 = r2.name;	 Catch:{ all -> 0x08c5 }
        r7 = r4.equals(r7);	 Catch:{ all -> 0x08c5 }
        r8 = "_sno";
        if (r7 == 0) goto L_0x0442;
    L_0x0417:
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzad();	 Catch:{ all -> 0x08c5 }
        r9 = r3.packageName;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzw(r9);	 Catch:{ all -> 0x08c5 }
        if (r7 == 0) goto L_0x0442;
    L_0x0425:
        r7 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r9 = r3.packageName;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zze(r9, r8);	 Catch:{ all -> 0x08c5 }
        if (r7 == 0) goto L_0x0442;
    L_0x0431:
        r9 = r7.value;	 Catch:{ all -> 0x08c5 }
        r9 = r9 instanceof java.lang.Long;	 Catch:{ all -> 0x08c5 }
        if (r9 == 0) goto L_0x0442;
    L_0x0437:
        r9 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r9 = r9.zzz();	 Catch:{ all -> 0x08c5 }
        r7 = r7.value;	 Catch:{ all -> 0x08c5 }
        r9.zza(r6, r8, r7);	 Catch:{ all -> 0x08c5 }
    L_0x0442:
        r7 = r2.name;	 Catch:{ all -> 0x08c5 }
        r4 = r4.equals(r7);	 Catch:{ all -> 0x08c5 }
        if (r4 == 0) goto L_0x0472;
    L_0x044a:
        r4 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r4 = r4.zzad();	 Catch:{ all -> 0x08c5 }
        r7 = r3.packageName;	 Catch:{ all -> 0x08c5 }
        r9 = com.google.android.gms.measurement.internal.zzak.zzif;	 Catch:{ all -> 0x08c5 }
        r4 = r4.zze(r7, r9);	 Catch:{ all -> 0x08c5 }
        if (r4 == 0) goto L_0x0472;
    L_0x045a:
        r4 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r4 = r4.zzad();	 Catch:{ all -> 0x08c5 }
        r7 = r3.packageName;	 Catch:{ all -> 0x08c5 }
        r4 = r4.zzw(r7);	 Catch:{ all -> 0x08c5 }
        if (r4 != 0) goto L_0x0472;
    L_0x0468:
        r4 = new com.google.android.gms.measurement.internal.zzjn;	 Catch:{ all -> 0x08c5 }
        r12 = 0;
        r4.<init>(r8, r14, r12);	 Catch:{ all -> 0x08c5 }
        r1.zzc(r4, r3);	 Catch:{ all -> 0x08c5 }
        goto L_0x0473;
    L_0x0472:
        r12 = 0;
    L_0x0473:
        r4 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r7 = r4.zzac(r13);	 Catch:{ all -> 0x08c5 }
        r4 = (r7 > r14 ? 1 : (r7 == r14 ? 0 : -1));
        if (r4 <= 0) goto L_0x0496;
    L_0x047f:
        r4 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r4 = r4.zzab();	 Catch:{ all -> 0x08c5 }
        r4 = r4.zzgn();	 Catch:{ all -> 0x08c5 }
        r9 = "Data lost. Too many events stored on disk, deleted. appId";
        r10 = com.google.android.gms.measurement.internal.zzef.zzam(r13);	 Catch:{ all -> 0x08c5 }
        r7 = java.lang.Long.valueOf(r7);	 Catch:{ all -> 0x08c5 }
        r4.zza(r9, r10, r7);	 Catch:{ all -> 0x08c5 }
    L_0x0496:
        r4 = new com.google.android.gms.measurement.internal.zzaf;	 Catch:{ all -> 0x08c5 }
        r8 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r9 = r2.origin;	 Catch:{ all -> 0x08c5 }
        r10 = r2.name;	 Catch:{ all -> 0x08c5 }
        r14 = r2.zzfu;	 Catch:{ all -> 0x08c5 }
        r19 = 0;
        r7 = r4;
        r2 = r10;
        r10 = r13;
        r26 = r11;
        r11 = r2;
        r16 = r12;
        r2 = r13;
        r25 = 0;
        r12 = r14;
        r28 = r16;
        r14 = r19;
        r16 = r6;
        r7.<init>(r8, r9, r10, r11, r12, r14, r16);	 Catch:{ all -> 0x08c5 }
        r6 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r7 = r4.name;	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzc(r2, r7);	 Catch:{ all -> 0x08c5 }
        if (r6 != 0) goto L_0x0529;
    L_0x04c3:
        r6 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzag(r2);	 Catch:{ all -> 0x08c5 }
        r8 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r10 < 0) goto L_0x050f;
    L_0x04d1:
        if (r5 == 0) goto L_0x050f;
    L_0x04d3:
        r3 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r3 = r3.zzab();	 Catch:{ all -> 0x08c5 }
        r3 = r3.zzgk();	 Catch:{ all -> 0x08c5 }
        r5 = "Too many event names used, ignoring event. appId, name, supported count";
        r6 = com.google.android.gms.measurement.internal.zzef.zzam(r2);	 Catch:{ all -> 0x08c5 }
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzy();	 Catch:{ all -> 0x08c5 }
        r4 = r4.name;	 Catch:{ all -> 0x08c5 }
        r4 = r7.zzaj(r4);	 Catch:{ all -> 0x08c5 }
        r7 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r7 = java.lang.Integer.valueOf(r7);	 Catch:{ all -> 0x08c5 }
        r3.zza(r5, r6, r4, r7);	 Catch:{ all -> 0x08c5 }
        r3 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r3.zzz();	 Catch:{ all -> 0x08c5 }
        r9 = 8;
        r10 = 0;
        r11 = 0;
        r12 = 0;
        r8 = r2;
        r7.zza(r8, r9, r10, r11, r12);	 Catch:{ all -> 0x08c5 }
        r2 = r27.zzgy();
        r2.endTransaction();
        return;
    L_0x050f:
        r5 = new com.google.android.gms.measurement.internal.zzae;	 Catch:{ all -> 0x08c5 }
        r9 = r4.name;	 Catch:{ all -> 0x08c5 }
        r10 = 0;
        r12 = 0;
        r14 = r4.timestamp;	 Catch:{ all -> 0x08c5 }
        r16 = 0;
        r18 = 0;
        r19 = 0;
        r20 = 0;
        r21 = 0;
        r7 = r5;
        r8 = r2;
        r7.<init>(r8, r9, r10, r12, r14, r16, r18, r19, r20, r21);	 Catch:{ all -> 0x08c5 }
        goto L_0x0537;
    L_0x0529:
        r2 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r6.zzfj;	 Catch:{ all -> 0x08c5 }
        r4 = r4.zza(r2, r7);	 Catch:{ all -> 0x08c5 }
        r7 = r4.timestamp;	 Catch:{ all -> 0x08c5 }
        r5 = r6.zzw(r7);	 Catch:{ all -> 0x08c5 }
    L_0x0537:
        r2 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r2.zza(r5);	 Catch:{ all -> 0x08c5 }
        r27.zzo();	 Catch:{ all -> 0x08c5 }
        r27.zzjj();	 Catch:{ all -> 0x08c5 }
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r4);	 Catch:{ all -> 0x08c5 }
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r29);	 Catch:{ all -> 0x08c5 }
        r2 = r4.zzce;	 Catch:{ all -> 0x08c5 }
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r2);	 Catch:{ all -> 0x08c5 }
        r2 = r4.zzce;	 Catch:{ all -> 0x08c5 }
        r5 = r3.packageName;	 Catch:{ all -> 0x08c5 }
        r2 = r2.equals(r5);	 Catch:{ all -> 0x08c5 }
        com.google.android.gms.common.internal.Preconditions.checkArgument(r2);	 Catch:{ all -> 0x08c5 }
        r2 = com.google.android.gms.internal.measurement.zzbs.zzg.zzpr();	 Catch:{ all -> 0x08c5 }
        r5 = 1;
        r2 = r2.zzp(r5);	 Catch:{ all -> 0x08c5 }
        r6 = "android";
        r2 = r2.zzcc(r6);	 Catch:{ all -> 0x08c5 }
        r6 = r3.packageName;	 Catch:{ all -> 0x08c5 }
        r6 = android.text.TextUtils.isEmpty(r6);	 Catch:{ all -> 0x08c5 }
        if (r6 != 0) goto L_0x0576;
    L_0x0571:
        r6 = r3.packageName;	 Catch:{ all -> 0x08c5 }
        r2.zzch(r6);	 Catch:{ all -> 0x08c5 }
    L_0x0576:
        r6 = r3.zzco;	 Catch:{ all -> 0x08c5 }
        r6 = android.text.TextUtils.isEmpty(r6);	 Catch:{ all -> 0x08c5 }
        if (r6 != 0) goto L_0x0583;
    L_0x057e:
        r6 = r3.zzco;	 Catch:{ all -> 0x08c5 }
        r2.zzcg(r6);	 Catch:{ all -> 0x08c5 }
    L_0x0583:
        r6 = r3.zzcm;	 Catch:{ all -> 0x08c5 }
        r6 = android.text.TextUtils.isEmpty(r6);	 Catch:{ all -> 0x08c5 }
        if (r6 != 0) goto L_0x0590;
    L_0x058b:
        r6 = r3.zzcm;	 Catch:{ all -> 0x08c5 }
        r2.zzci(r6);	 Catch:{ all -> 0x08c5 }
    L_0x0590:
        r6 = r3.zzcn;	 Catch:{ all -> 0x08c5 }
        r8 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r10 == 0) goto L_0x059f;
    L_0x0599:
        r6 = r3.zzcn;	 Catch:{ all -> 0x08c5 }
        r7 = (int) r6;	 Catch:{ all -> 0x08c5 }
        r2.zzv(r7);	 Catch:{ all -> 0x08c5 }
    L_0x059f:
        r6 = r3.zzr;	 Catch:{ all -> 0x08c5 }
        r2.zzas(r6);	 Catch:{ all -> 0x08c5 }
        r6 = r3.zzcg;	 Catch:{ all -> 0x08c5 }
        r6 = android.text.TextUtils.isEmpty(r6);	 Catch:{ all -> 0x08c5 }
        if (r6 != 0) goto L_0x05b1;
    L_0x05ac:
        r6 = r3.zzcg;	 Catch:{ all -> 0x08c5 }
        r2.zzcm(r6);	 Catch:{ all -> 0x08c5 }
    L_0x05b1:
        r6 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzad();	 Catch:{ all -> 0x08c5 }
        r7 = com.google.android.gms.measurement.internal.zzak.zzit;	 Catch:{ all -> 0x08c5 }
        r6 = r6.zza(r7);	 Catch:{ all -> 0x08c5 }
        if (r6 == 0) goto L_0x05d7;
    L_0x05bf:
        r6 = r2.getGmpAppId();	 Catch:{ all -> 0x08c5 }
        r6 = android.text.TextUtils.isEmpty(r6);	 Catch:{ all -> 0x08c5 }
        if (r6 == 0) goto L_0x05e4;
    L_0x05c9:
        r6 = r3.zzcu;	 Catch:{ all -> 0x08c5 }
        r6 = android.text.TextUtils.isEmpty(r6);	 Catch:{ all -> 0x08c5 }
        if (r6 != 0) goto L_0x05e4;
    L_0x05d1:
        r6 = r3.zzcu;	 Catch:{ all -> 0x08c5 }
        r2.zzcq(r6);	 Catch:{ all -> 0x08c5 }
        goto L_0x05e4;
    L_0x05d7:
        r6 = r3.zzcu;	 Catch:{ all -> 0x08c5 }
        r6 = android.text.TextUtils.isEmpty(r6);	 Catch:{ all -> 0x08c5 }
        if (r6 != 0) goto L_0x05e4;
    L_0x05df:
        r6 = r3.zzcu;	 Catch:{ all -> 0x08c5 }
        r2.zzcq(r6);	 Catch:{ all -> 0x08c5 }
    L_0x05e4:
        r6 = r3.zzcp;	 Catch:{ all -> 0x08c5 }
        r8 = 0;
        r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r10 == 0) goto L_0x05f1;
    L_0x05ec:
        r6 = r3.zzcp;	 Catch:{ all -> 0x08c5 }
        r2.zzau(r6);	 Catch:{ all -> 0x08c5 }
    L_0x05f1:
        r6 = r3.zzs;	 Catch:{ all -> 0x08c5 }
        r2.zzax(r6);	 Catch:{ all -> 0x08c5 }
        r6 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzad();	 Catch:{ all -> 0x08c5 }
        r7 = r3.packageName;	 Catch:{ all -> 0x08c5 }
        r10 = com.google.android.gms.measurement.internal.zzak.zzin;	 Catch:{ all -> 0x08c5 }
        r6 = r6.zze(r7, r10);	 Catch:{ all -> 0x08c5 }
        if (r6 == 0) goto L_0x0613;
    L_0x0606:
        r6 = r27.zzgw();	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzju();	 Catch:{ all -> 0x08c5 }
        if (r6 == 0) goto L_0x0613;
    L_0x0610:
        r2.zzd(r6);	 Catch:{ all -> 0x08c5 }
    L_0x0613:
        r6 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzac();	 Catch:{ all -> 0x08c5 }
        r7 = r3.packageName;	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzap(r7);	 Catch:{ all -> 0x08c5 }
        if (r6 == 0) goto L_0x0646;
    L_0x0621:
        r7 = r6.first;	 Catch:{ all -> 0x08c5 }
        r7 = (java.lang.CharSequence) r7;	 Catch:{ all -> 0x08c5 }
        r7 = android.text.TextUtils.isEmpty(r7);	 Catch:{ all -> 0x08c5 }
        if (r7 != 0) goto L_0x0646;
    L_0x062b:
        r7 = r3.zzcs;	 Catch:{ all -> 0x08c5 }
        if (r7 == 0) goto L_0x06a8;
    L_0x062f:
        r7 = r6.first;	 Catch:{ all -> 0x08c5 }
        r7 = (java.lang.String) r7;	 Catch:{ all -> 0x08c5 }
        r2.zzcj(r7);	 Catch:{ all -> 0x08c5 }
        r7 = r6.second;	 Catch:{ all -> 0x08c5 }
        if (r7 == 0) goto L_0x06a8;
    L_0x063a:
        r6 = r6.second;	 Catch:{ all -> 0x08c5 }
        r6 = (java.lang.Boolean) r6;	 Catch:{ all -> 0x08c5 }
        r6 = r6.booleanValue();	 Catch:{ all -> 0x08c5 }
        r2.zzm(r6);	 Catch:{ all -> 0x08c5 }
        goto L_0x06a8;
    L_0x0646:
        r6 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzw();	 Catch:{ all -> 0x08c5 }
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.getContext();	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzj(r7);	 Catch:{ all -> 0x08c5 }
        if (r6 != 0) goto L_0x06a8;
    L_0x0658:
        r6 = r3.zzct;	 Catch:{ all -> 0x08c5 }
        if (r6 == 0) goto L_0x06a8;
    L_0x065c:
        r6 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r6 = r6.getContext();	 Catch:{ all -> 0x08c5 }
        r6 = r6.getContentResolver();	 Catch:{ all -> 0x08c5 }
        r7 = "android_id";
        r6 = android.provider.Settings.Secure.getString(r6, r7);	 Catch:{ all -> 0x08c5 }
        if (r6 != 0) goto L_0x0688;
    L_0x066e:
        r6 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzab();	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzgn();	 Catch:{ all -> 0x08c5 }
        r7 = "null secure ID. appId";
        r10 = r2.zzag();	 Catch:{ all -> 0x08c5 }
        r10 = com.google.android.gms.measurement.internal.zzef.zzam(r10);	 Catch:{ all -> 0x08c5 }
        r6.zza(r7, r10);	 Catch:{ all -> 0x08c5 }
        r6 = "null";
        goto L_0x06a5;
    L_0x0688:
        r7 = r6.isEmpty();	 Catch:{ all -> 0x08c5 }
        if (r7 == 0) goto L_0x06a5;
    L_0x068e:
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzab();	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzgn();	 Catch:{ all -> 0x08c5 }
        r10 = "empty secure ID. appId";
        r11 = r2.zzag();	 Catch:{ all -> 0x08c5 }
        r11 = com.google.android.gms.measurement.internal.zzef.zzam(r11);	 Catch:{ all -> 0x08c5 }
        r7.zza(r10, r11);	 Catch:{ all -> 0x08c5 }
    L_0x06a5:
        r2.zzco(r6);	 Catch:{ all -> 0x08c5 }
    L_0x06a8:
        r6 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzw();	 Catch:{ all -> 0x08c5 }
        r6.zzbi();	 Catch:{ all -> 0x08c5 }
        r6 = android.os.Build.MODEL;	 Catch:{ all -> 0x08c5 }
        r6 = r2.zzce(r6);	 Catch:{ all -> 0x08c5 }
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzw();	 Catch:{ all -> 0x08c5 }
        r7.zzbi();	 Catch:{ all -> 0x08c5 }
        r7 = android.os.Build.VERSION.RELEASE;	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzcd(r7);	 Catch:{ all -> 0x08c5 }
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzw();	 Catch:{ all -> 0x08c5 }
        r10 = r7.zzcq();	 Catch:{ all -> 0x08c5 }
        r7 = (int) r10;	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzt(r7);	 Catch:{ all -> 0x08c5 }
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzw();	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzcr();	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzcf(r7);	 Catch:{ all -> 0x08c5 }
        r10 = r3.zzcr;	 Catch:{ all -> 0x08c5 }
        r6.zzaw(r10);	 Catch:{ all -> 0x08c5 }
        r6 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r6 = r6.isEnabled();	 Catch:{ all -> 0x08c5 }
        if (r6 == 0) goto L_0x0704;
    L_0x06f0:
        r6 = com.google.android.gms.measurement.internal.zzs.zzbv();	 Catch:{ all -> 0x08c5 }
        if (r6 == 0) goto L_0x0704;
    L_0x06f6:
        r2.zzag();	 Catch:{ all -> 0x08c5 }
        r6 = android.text.TextUtils.isEmpty(r28);	 Catch:{ all -> 0x08c5 }
        if (r6 != 0) goto L_0x0704;
    L_0x06ff:
        r6 = r28;
        r2.zzcp(r6);	 Catch:{ all -> 0x08c5 }
    L_0x0704:
        r6 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r7 = r3.packageName;	 Catch:{ all -> 0x08c5 }
        r6 = r6.zzab(r7);	 Catch:{ all -> 0x08c5 }
        if (r6 != 0) goto L_0x0777;
    L_0x0710:
        r6 = new com.google.android.gms.measurement.internal.zzf;	 Catch:{ all -> 0x08c5 }
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r10 = r3.packageName;	 Catch:{ all -> 0x08c5 }
        r6.<init>(r7, r10);	 Catch:{ all -> 0x08c5 }
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzz();	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzjy();	 Catch:{ all -> 0x08c5 }
        r6.zza(r7);	 Catch:{ all -> 0x08c5 }
        r7 = r3.zzci;	 Catch:{ all -> 0x08c5 }
        r6.zze(r7);	 Catch:{ all -> 0x08c5 }
        r7 = r3.zzcg;	 Catch:{ all -> 0x08c5 }
        r6.zzb(r7);	 Catch:{ all -> 0x08c5 }
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzac();	 Catch:{ all -> 0x08c5 }
        r10 = r3.packageName;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzaq(r10);	 Catch:{ all -> 0x08c5 }
        r6.zzd(r7);	 Catch:{ all -> 0x08c5 }
        r6.zzk(r8);	 Catch:{ all -> 0x08c5 }
        r6.zze(r8);	 Catch:{ all -> 0x08c5 }
        r6.zzf(r8);	 Catch:{ all -> 0x08c5 }
        r7 = r3.zzcm;	 Catch:{ all -> 0x08c5 }
        r6.zzf(r7);	 Catch:{ all -> 0x08c5 }
        r10 = r3.zzcn;	 Catch:{ all -> 0x08c5 }
        r6.zzg(r10);	 Catch:{ all -> 0x08c5 }
        r7 = r3.zzco;	 Catch:{ all -> 0x08c5 }
        r6.zzg(r7);	 Catch:{ all -> 0x08c5 }
        r10 = r3.zzr;	 Catch:{ all -> 0x08c5 }
        r6.zzh(r10);	 Catch:{ all -> 0x08c5 }
        r10 = r3.zzcp;	 Catch:{ all -> 0x08c5 }
        r6.zzi(r10);	 Catch:{ all -> 0x08c5 }
        r7 = r3.zzcq;	 Catch:{ all -> 0x08c5 }
        r6.setMeasurementEnabled(r7);	 Catch:{ all -> 0x08c5 }
        r10 = r3.zzcr;	 Catch:{ all -> 0x08c5 }
        r6.zzt(r10);	 Catch:{ all -> 0x08c5 }
        r10 = r3.zzs;	 Catch:{ all -> 0x08c5 }
        r6.zzj(r10);	 Catch:{ all -> 0x08c5 }
        r7 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r7.zza(r6);	 Catch:{ all -> 0x08c5 }
    L_0x0777:
        r7 = r6.getAppInstanceId();	 Catch:{ all -> 0x08c5 }
        r7 = android.text.TextUtils.isEmpty(r7);	 Catch:{ all -> 0x08c5 }
        if (r7 != 0) goto L_0x0788;
    L_0x0781:
        r7 = r6.getAppInstanceId();	 Catch:{ all -> 0x08c5 }
        r2.zzck(r7);	 Catch:{ all -> 0x08c5 }
    L_0x0788:
        r7 = r6.getFirebaseInstanceId();	 Catch:{ all -> 0x08c5 }
        r7 = android.text.TextUtils.isEmpty(r7);	 Catch:{ all -> 0x08c5 }
        if (r7 != 0) goto L_0x0799;
    L_0x0792:
        r6 = r6.getFirebaseInstanceId();	 Catch:{ all -> 0x08c5 }
        r2.zzcn(r6);	 Catch:{ all -> 0x08c5 }
    L_0x0799:
        r6 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r3 = r3.packageName;	 Catch:{ all -> 0x08c5 }
        r3 = r6.zzaa(r3);	 Catch:{ all -> 0x08c5 }
        r6 = 0;
    L_0x07a4:
        r7 = r3.size();	 Catch:{ all -> 0x08c5 }
        if (r6 >= r7) goto L_0x07db;
    L_0x07aa:
        r7 = com.google.android.gms.internal.measurement.zzbs.zzk.zzqu();	 Catch:{ all -> 0x08c5 }
        r10 = r3.get(r6);	 Catch:{ all -> 0x08c5 }
        r10 = (com.google.android.gms.measurement.internal.zzjp) r10;	 Catch:{ all -> 0x08c5 }
        r10 = r10.name;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzdb(r10);	 Catch:{ all -> 0x08c5 }
        r10 = r3.get(r6);	 Catch:{ all -> 0x08c5 }
        r10 = (com.google.android.gms.measurement.internal.zzjp) r10;	 Catch:{ all -> 0x08c5 }
        r10 = r10.zztr;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzbk(r10);	 Catch:{ all -> 0x08c5 }
        r10 = r27.zzgw();	 Catch:{ all -> 0x08c5 }
        r11 = r3.get(r6);	 Catch:{ all -> 0x08c5 }
        r11 = (com.google.android.gms.measurement.internal.zzjp) r11;	 Catch:{ all -> 0x08c5 }
        r11 = r11.value;	 Catch:{ all -> 0x08c5 }
        r10.zza(r7, r11);	 Catch:{ all -> 0x08c5 }
        r2.zza(r7);	 Catch:{ all -> 0x08c5 }
        r6 = r6 + 1;
        goto L_0x07a4;
    L_0x07db:
        r3 = r27.zzgy();	 Catch:{ IOException -> 0x0853 }
        r6 = r2.zzug();	 Catch:{ IOException -> 0x0853 }
        r6 = (com.google.android.gms.internal.measurement.zzey) r6;	 Catch:{ IOException -> 0x0853 }
        r6 = (com.google.android.gms.internal.measurement.zzbs.zzg) r6;	 Catch:{ IOException -> 0x0853 }
        r2 = r3.zza(r6);	 Catch:{ IOException -> 0x0853 }
        r6 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r7 = r4.zzfq;	 Catch:{ all -> 0x08c5 }
        if (r7 == 0) goto L_0x0849;
    L_0x07f3:
        r7 = r4.zzfq;	 Catch:{ all -> 0x08c5 }
        r7 = r7.iterator();	 Catch:{ all -> 0x08c5 }
    L_0x07f9:
        r10 = r7.hasNext();	 Catch:{ all -> 0x08c5 }
        if (r10 == 0) goto L_0x0811;
    L_0x07ff:
        r10 = r7.next();	 Catch:{ all -> 0x08c5 }
        r10 = (java.lang.String) r10;	 Catch:{ all -> 0x08c5 }
        r11 = r26;
        r10 = r11.equals(r10);	 Catch:{ all -> 0x08c5 }
        if (r10 == 0) goto L_0x080e;
    L_0x080d:
        goto L_0x084a;
    L_0x080e:
        r26 = r11;
        goto L_0x07f9;
    L_0x0811:
        r7 = r27.zzgz();	 Catch:{ all -> 0x08c5 }
        r10 = r4.zzce;	 Catch:{ all -> 0x08c5 }
        r11 = r4.name;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzl(r10, r11);	 Catch:{ all -> 0x08c5 }
        r10 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r11 = r27.zzjk();	 Catch:{ all -> 0x08c5 }
        r13 = r4.zzce;	 Catch:{ all -> 0x08c5 }
        r14 = 0;
        r15 = 0;
        r16 = 0;
        r17 = 0;
        r18 = 0;
        r10 = r10.zza(r11, r13, r14, r15, r16, r17, r18);	 Catch:{ all -> 0x08c5 }
        if (r7 == 0) goto L_0x0849;
    L_0x0835:
        r10 = r10.zzej;	 Catch:{ all -> 0x08c5 }
        r7 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzad();	 Catch:{ all -> 0x08c5 }
        r12 = r4.zzce;	 Catch:{ all -> 0x08c5 }
        r7 = r7.zzi(r12);	 Catch:{ all -> 0x08c5 }
        r12 = (long) r7;	 Catch:{ all -> 0x08c5 }
        r7 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1));
        if (r7 >= 0) goto L_0x0849;
    L_0x0848:
        goto L_0x084a;
    L_0x0849:
        r5 = 0;
    L_0x084a:
        r2 = r6.zza(r4, r2, r5);	 Catch:{ all -> 0x08c5 }
        if (r2 == 0) goto L_0x086c;
    L_0x0850:
        r1.zzsy = r8;	 Catch:{ all -> 0x08c5 }
        goto L_0x086c;
    L_0x0853:
        r0 = move-exception;
        r3 = r0;
        r5 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r5 = r5.zzab();	 Catch:{ all -> 0x08c5 }
        r5 = r5.zzgk();	 Catch:{ all -> 0x08c5 }
        r6 = "Data loss. Failed to insert raw event metadata. appId";
        r2 = r2.zzag();	 Catch:{ all -> 0x08c5 }
        r2 = com.google.android.gms.measurement.internal.zzef.zzam(r2);	 Catch:{ all -> 0x08c5 }
        r5.zza(r6, r2, r3);	 Catch:{ all -> 0x08c5 }
    L_0x086c:
        r2 = r27.zzgy();	 Catch:{ all -> 0x08c5 }
        r2.setTransactionSuccessful();	 Catch:{ all -> 0x08c5 }
        r2 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r2 = r2.zzab();	 Catch:{ all -> 0x08c5 }
        r3 = 2;
        r2 = r2.isLoggable(r3);	 Catch:{ all -> 0x08c5 }
        if (r2 == 0) goto L_0x0899;
    L_0x0880:
        r2 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r2 = r2.zzab();	 Catch:{ all -> 0x08c5 }
        r2 = r2.zzgs();	 Catch:{ all -> 0x08c5 }
        r3 = "Event recorded";
        r5 = r1.zzj;	 Catch:{ all -> 0x08c5 }
        r5 = r5.zzy();	 Catch:{ all -> 0x08c5 }
        r4 = r5.zza(r4);	 Catch:{ all -> 0x08c5 }
        r2.zza(r3, r4);	 Catch:{ all -> 0x08c5 }
    L_0x0899:
        r2 = r27.zzgy();
        r2.endTransaction();
        r27.zzjn();
        r2 = r1.zzj;
        r2 = r2.zzab();
        r2 = r2.zzgs();
        r3 = java.lang.System.nanoTime();
        r3 = r3 - r23;
        r5 = 500000; // 0x7a120 float:7.00649E-40 double:2.47033E-318;
        r3 = r3 + r5;
        r5 = 1000000; // 0xf4240 float:1.401298E-39 double:4.940656E-318;
        r3 = r3 / r5;
        r3 = java.lang.Long.valueOf(r3);
        r4 = "Background event processing time, ms";
        r2.zza(r4, r3);
        return;
    L_0x08c5:
        r0 = move-exception;
        r2 = r0;
        r3 = r27.zzgy();
        r3.endTransaction();
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzjg.zzd(com.google.android.gms.measurement.internal.zzai, com.google.android.gms.measurement.internal.zzn):void");
    }

    @WorkerThread
    final void zzjl() {
        zzo();
        zzjj();
        this.zzte = true;
        String zzby;
        String str;
        try {
            this.zzj.zzae();
            Boolean zzit = this.zzj.zzs().zzit();
            if (zzit == null) {
                this.zzj.zzab().zzgn().zzao("Upload data called on the client side before use of service was decided");
                this.zzte = false;
                zzjo();
            } else if (zzit.booleanValue()) {
                this.zzj.zzab().zzgk().zzao("Upload called in the client side when service should be used");
                this.zzte = false;
                zzjo();
            } else if (this.zzsy > 0) {
                zzjn();
                this.zzte = false;
                zzjo();
            } else {
                zzo();
                if ((this.zzth != null ? 1 : null) != null) {
                    this.zzj.zzab().zzgs().zzao("Uploading requested multiple times");
                    this.zzte = false;
                    zzjo();
                } else if (zzjf().zzgv()) {
                    long currentTimeMillis = this.zzj.zzx().currentTimeMillis();
                    zzd(null, currentTimeMillis - zzs.zzbt());
                    long j = this.zzj.zzac().zzlj.get();
                    if (j != 0) {
                        this.zzj.zzab().zzgr().zza("Uploading events. Elapsed time since last upload attempt (ms)", Long.valueOf(Math.abs(currentTimeMillis - j)));
                    }
                    zzby = zzgy().zzby();
                    if (TextUtils.isEmpty(zzby)) {
                        this.zztj = -1;
                        Object zzu = zzgy().zzu(currentTimeMillis - zzs.zzbt());
                        if (!TextUtils.isEmpty(zzu)) {
                            zzf zzab = zzgy().zzab(zzu);
                            if (zzab != null) {
                                zzb(zzab);
                            }
                        }
                    } else {
                        if (this.zztj == -1) {
                            this.zztj = zzgy().zzcf();
                        }
                        List<Pair> zza = zzgy().zza(zzby, this.zzj.zzad().zzb(zzby, zzak.zzgl), Math.max(0, this.zzj.zzad().zzb(zzby, zzak.zzgm)));
                        if (!zza.isEmpty()) {
                            Object zzot;
                            int i;
                            List zza2;
                            for (Pair pair : zza2) {
                                zzg zzg = (zzg) pair.first;
                                if (!TextUtils.isEmpty(zzg.zzot())) {
                                    zzot = zzg.zzot();
                                    break;
                                }
                            }
                            zzot = null;
                            if (zzot != null) {
                                for (i = 0; i < zza2.size(); i++) {
                                    zzg zzg2 = (zzg) ((Pair) zza2.get(i)).first;
                                    if (!TextUtils.isEmpty(zzg2.zzot()) && !zzg2.zzot().equals(zzot)) {
                                        zza2 = zza2.subList(0, i);
                                        break;
                                    }
                                }
                            }
                            com.google.android.gms.internal.measurement.zzey.zza zznj = zzf.zznj();
                            i = zza2.size();
                            Collection arrayList = new ArrayList(zza2.size());
                            Object obj = (zzs.zzbv() && this.zzj.zzad().zzl(zzby)) ? 1 : null;
                            for (int i2 = 0; i2 < i; i2++) {
                                com.google.android.gms.internal.measurement.zzbs.zzg.zza zza3 = (com.google.android.gms.internal.measurement.zzbs.zzg.zza) ((zzg) ((Pair) zza2.get(i2)).first).zzuj();
                                arrayList.add((Long) ((Pair) zza2.get(i2)).second);
                                com.google.android.gms.internal.measurement.zzbs.zzg.zza zzan = zza3.zzat(this.zzj.zzad().zzao()).zzan(currentTimeMillis);
                                this.zzj.zzae();
                                zzan.zzn(false);
                                if (obj == null) {
                                    zza3.zznw();
                                }
                                if (this.zzj.zzad().zze(zzby, zzak.zzis)) {
                                    zza3.zzay(zzgw().zza(((zzg) ((zzey) zza3.zzug())).toByteArray()));
                                }
                                zznj.zza(zza3);
                            }
                            Object zza4 = this.zzj.zzab().isLoggable(2) ? zzgw().zza((zzf) ((zzey) zznj.zzug())) : null;
                            zzgw();
                            Object toByteArray = ((zzf) ((zzey) zznj.zzug())).toByteArray();
                            str = (String) zzak.zzgv.get(null);
                            URL url = new URL(str);
                            Preconditions.checkArgument(!arrayList.isEmpty());
                            if (this.zzth != null) {
                                this.zzj.zzab().zzgk().zzao("Set uploading progress before finishing the previous upload");
                            } else {
                                this.zzth = new ArrayList(arrayList);
                            }
                            this.zzj.zzac().zzlk.set(currentTimeMillis);
                            Object obj2 = "?";
                            if (i > 0) {
                                obj2 = zznj.zzo(0).zzag();
                            }
                            this.zzj.zzab().zzgs().zza("Uploading data. app, uncompressed size, data", obj2, Integer.valueOf(toByteArray.length), zza4);
                            this.zztd = true;
                            zzgf zzjf = zzjf();
                            zzji zzji = new zzji(this, zzby);
                            zzjf.zzo();
                            zzjf.zzbi();
                            Preconditions.checkNotNull(url);
                            Preconditions.checkNotNull(toByteArray);
                            Preconditions.checkNotNull(zzji);
                            zzjf.zzaa().zzb(new zzen(zzjf, zzby, url, toByteArray, null, zzji));
                        }
                    }
                    this.zzte = false;
                    zzjo();
                } else {
                    this.zzj.zzab().zzgs().zzao("Network not connected, ignoring upload request");
                    zzjn();
                    this.zzte = false;
                    zzjo();
                }
            }
        } catch (MalformedURLException unused) {
            this.zzj.zzab().zzgk().zza("Failed to parse upload URL. Not uploading. appId", zzef.zzam(zzby), str);
        } catch (Throwable th) {
            this.zzte = false;
            zzjo();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:318:0x0767 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:293:0x06bf A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:325:0x0797 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:324:0x077d A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:293:0x06bf A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:318:0x0767 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:324:0x077d A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:325:0x0797 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:208:0x03e2 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:201:0x03d6 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:208:0x03e2 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:201:0x03d6 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:208:0x03e2 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:260:0x05f6 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:269:0x0623 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:318:0x0767 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:293:0x06bf A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:325:0x0797 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:324:0x077d A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:585:0x0f01  */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x02a0 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0292 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x02a0 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:585:0x0f01  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0292 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:585:0x0f01  */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x02a0 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0292 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x02a0 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:585:0x0f01  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0292 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:585:0x0f01  */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x02a0 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0292 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x02a0 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:585:0x0f01  */
    /* JADX WARNING: Removed duplicated region for block: B:593:0x0f19 A:{SYNTHETIC, Splitter: B:593:0x0f19} */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x028b A:{SYNTHETIC, Splitter: B:151:0x028b} */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0292 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:585:0x0f01  */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x02a0 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:593:0x0f19 A:{SYNTHETIC, Splitter: B:593:0x0f19} */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x028b A:{SYNTHETIC, Splitter: B:151:0x028b} */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0292 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x02a0 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:585:0x0f01  */
    /* JADX WARNING: Removed duplicated region for block: B:593:0x0f19 A:{SYNTHETIC, Splitter: B:593:0x0f19} */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x028b A:{SYNTHETIC, Splitter: B:151:0x028b} */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0292 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:585:0x0f01  */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x02a0 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:593:0x0f19 A:{SYNTHETIC, Splitter: B:593:0x0f19} */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0040 A:{PHI: r3 , ExcHandler: all (th java.lang.Throwable), Splitter: B:9:0x0031} */
    /* JADX WARNING: Removed duplicated region for block: B:593:0x0f19 A:{SYNTHETIC, Splitter: B:593:0x0f19} */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x028b A:{SYNTHETIC, Splitter: B:151:0x028b} */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0292 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x02a0 A:{Catch:{ IOException -> 0x0233, all -> 0x0f1f }} */
    /* JADX WARNING: Removed duplicated region for block: B:585:0x0f01  */
    /* JADX WARNING: Removed duplicated region for block: B:593:0x0f19 A:{SYNTHETIC, Splitter: B:593:0x0f19} */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0040 A:{PHI: r3 , ExcHandler: all (th java.lang.Throwable), Splitter: B:9:0x0031} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:11:0x0040, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:12:0x0041, code:
            r6 = r1;
            r22 = r3;
     */
    /* JADX WARNING: Missing block: B:13:0x0046, code:
            r0 = e;
     */
    /* JADX WARNING: Missing block: B:14:0x0047, code:
            r5 = null;
            r7 = r5;
     */
    /* JADX WARNING: Missing block: B:35:0x0098, code:
            r0 = e;
     */
    /* JADX WARNING: Missing block: B:36:0x0099, code:
            r5 = r3;
     */
    /* JADX WARNING: Missing block: B:332:0x07ea, code:
            r7 = r5;
            r9 = r6;
            r25 = r15;
     */
    /* JADX WARNING: Missing block: B:333:0x07f0, code:
            if (r4 == false) goto L_0x0847;
     */
    /* JADX WARNING: Missing block: B:334:0x07f2, code:
            r5 = r25;
            r13 = r26;
            r4 = 0;
     */
    /* JADX WARNING: Missing block: B:335:0x07f7, code:
            if (r4 >= r5) goto L_0x0849;
     */
    /* JADX WARNING: Missing block: B:336:0x07f9, code:
            r6 = r3.zzq(r4);
     */
    /* JADX WARNING: Missing block: B:337:0x0805, code:
            if (r9.equals(r6.getName()) == false) goto L_0x081a;
     */
    /* JADX WARNING: Missing block: B:338:0x0807, code:
            zzgw();
     */
    /* JADX WARNING: Missing block: B:339:0x0810, code:
            if (com.google.android.gms.measurement.internal.zzjo.zza(r6, "_fr") == null) goto L_0x081a;
     */
    /* JADX WARNING: Missing block: B:340:0x0812, code:
            r3.zzr(r4);
            r5 = r5 - 1;
            r4 = r4 - 1;
     */
    /* JADX WARNING: Missing block: B:341:0x081a, code:
            zzgw();
            r6 = com.google.android.gms.measurement.internal.zzjo.zza(r6, r7);
     */
    /* JADX WARNING: Missing block: B:342:0x0821, code:
            if (r6 == null) goto L_0x0844;
     */
    /* JADX WARNING: Missing block: B:344:0x0827, code:
            if (r6.zzna() == false) goto L_0x0832;
     */
    /* JADX WARNING: Missing block: B:345:0x0829, code:
            r6 = java.lang.Long.valueOf(r6.zznb());
     */
    /* JADX WARNING: Missing block: B:346:0x0832, code:
            r6 = null;
     */
    /* JADX WARNING: Missing block: B:347:0x0833, code:
            if (r6 == null) goto L_0x0844;
     */
    /* JADX WARNING: Missing block: B:349:0x083d, code:
            if (r6.longValue() <= 0) goto L_0x0844;
     */
    /* JADX WARNING: Missing block: B:350:0x083f, code:
            r13 = r13 + r6.longValue();
     */
    /* JADX WARNING: Missing block: B:351:0x0844, code:
            r4 = r4 + 1;
     */
    /* JADX WARNING: Missing block: B:352:0x0847, code:
            r13 = r26;
     */
    /* JADX WARNING: Missing block: B:353:0x0849, code:
            r1.zza((com.google.android.gms.internal.measurement.zzbs.zzg.zza) r3, r13, false);
     */
    /* JADX WARNING: Missing block: B:354:0x085d, code:
            if (r1.zzj.zzad().zze(r3.zzag(), com.google.android.gms.measurement.internal.zzak.zzja) == false) goto L_0x0896;
     */
    /* JADX WARNING: Missing block: B:355:0x085f, code:
            r4 = r3.zznl().iterator();
     */
    /* JADX WARNING: Missing block: B:357:0x086b, code:
            if (r4.hasNext() == false) goto L_0x0881;
     */
    /* JADX WARNING: Missing block: B:359:0x087d, code:
            if ("_s".equals(((com.google.android.gms.internal.measurement.zzbs.zzc) r4.next()).getName()) == false) goto L_0x0867;
     */
    /* JADX WARNING: Missing block: B:360:0x087f, code:
            r4 = 1;
     */
    /* JADX WARNING: Missing block: B:361:0x0881, code:
            r4 = null;
     */
    /* JADX WARNING: Missing block: B:362:0x0882, code:
            if (r4 == null) goto L_0x0891;
     */
    /* JADX WARNING: Missing block: B:363:0x0884, code:
            zzgy().zzd(r3.zzag(), "_se");
     */
    /* JADX WARNING: Missing block: B:364:0x0891, code:
            r1.zza((com.google.android.gms.internal.measurement.zzbs.zzg.zza) r3, r13, true);
     */
    /* JADX WARNING: Missing block: B:366:0x08a6, code:
            if (r1.zzj.zzad().zze(r3.zzag(), com.google.android.gms.measurement.internal.zzak.zzjb) == false) goto L_0x08b5;
     */
    /* JADX WARNING: Missing block: B:367:0x08a8, code:
            zzgy().zzd(r3.zzag(), "_se");
     */
    /* JADX WARNING: Missing block: B:369:0x08c5, code:
            if (r1.zzj.zzad().zze(r3.zzag(), com.google.android.gms.measurement.internal.zzak.zzij) == false) goto L_0x095a;
     */
    /* JADX WARNING: Missing block: B:370:0x08c7, code:
            r4 = zzgw();
            r4.zzab().zzgs().zzao("Checking account type status for ad personalization signals");
     */
    /* JADX WARNING: Missing block: B:371:0x08e4, code:
            if (r4.zzgz().zzba(r3.zzag()) == false) goto L_0x095a;
     */
    /* JADX WARNING: Missing block: B:372:0x08e6, code:
            r5 = r4.zzgy().zzab(r3.zzag());
     */
    /* JADX WARNING: Missing block: B:373:0x08f2, code:
            if (r5 == null) goto L_0x095a;
     */
    /* JADX WARNING: Missing block: B:375:0x08f8, code:
            if (r5.zzbe() == false) goto L_0x095a;
     */
    /* JADX WARNING: Missing block: B:377:0x0902, code:
            if (r4.zzw().zzcu() == false) goto L_0x095a;
     */
    /* JADX WARNING: Missing block: B:378:0x0904, code:
            r4.zzab().zzgr().zzao("Turning off ad personalization due to account type");
            r4 = (com.google.android.gms.internal.measurement.zzbs.zzk) ((com.google.android.gms.internal.measurement.zzey) com.google.android.gms.internal.measurement.zzbs.zzk.zzqu().zzdb("_npa").zzbk(r4.zzw().zzcs()).zzbl(1).zzug());
            r5 = 0;
     */
    /* JADX WARNING: Missing block: B:380:0x093a, code:
            if (r5 >= r3.zznp()) goto L_0x0954;
     */
    /* JADX WARNING: Missing block: B:382:0x094a, code:
            if ("_npa".equals(r3.zzs(r5).getName()) == false) goto L_0x0951;
     */
    /* JADX WARNING: Missing block: B:383:0x094c, code:
            r3.zza(r5, r4);
            r5 = 1;
     */
    /* JADX WARNING: Missing block: B:384:0x0951, code:
            r5 = r5 + 1;
     */
    /* JADX WARNING: Missing block: B:385:0x0954, code:
            r5 = null;
     */
    /* JADX WARNING: Missing block: B:386:0x0955, code:
            if (r5 != null) goto L_0x095a;
     */
    /* JADX WARNING: Missing block: B:387:0x0957, code:
            r3.zza(r4);
     */
    /* JADX WARNING: Missing block: B:388:0x095a, code:
            r4 = r3.zznv();
            r5 = r3.zzag();
            r6 = r3.zzno();
            r7 = r3.zznl();
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r5);
            r4.zzc(zzgx().zza(r5, r7, r6));
     */
    /* JADX WARNING: Missing block: B:389:0x0988, code:
            if (r1.zzj.zzad().zzm(r2.zztn.zzag()) == false) goto L_0x0d29;
     */
    /* JADX WARNING: Missing block: B:391:?, code:
            r4 = new java.util.HashMap();
            r5 = new java.util.ArrayList();
            r6 = r1.zzj.zzz().zzjw();
            r7 = 0;
     */
    /* JADX WARNING: Missing block: B:393:0x09a3, code:
            if (r7 >= r3.zznm()) goto L_0x0cf4;
     */
    /* JADX WARNING: Missing block: B:394:0x09a5, code:
            r8 = (com.google.android.gms.internal.measurement.zzbs.zzc.zza) r3.zzq(r7).zzuj();
     */
    /* JADX WARNING: Missing block: B:395:0x09bb, code:
            r10 = "_sr";
     */
    /* JADX WARNING: Missing block: B:396:0x09bd, code:
            if (r8.getName().equals("_ep") == false) goto L_0x0a34;
     */
    /* JADX WARNING: Missing block: B:398:?, code:
            zzgw();
            r9 = (java.lang.String) com.google.android.gms.measurement.internal.zzjo.zzb((com.google.android.gms.internal.measurement.zzbs.zzc) ((com.google.android.gms.internal.measurement.zzey) r8.zzug()), "_en");
            r11 = (com.google.android.gms.measurement.internal.zzae) r4.get(r9);
     */
    /* JADX WARNING: Missing block: B:399:0x09d8, code:
            if (r11 != null) goto L_0x09eb;
     */
    /* JADX WARNING: Missing block: B:400:0x09da, code:
            r11 = zzgy().zzc(r2.zztn.zzag(), r9);
            r4.put(r9, r11);
     */
    /* JADX WARNING: Missing block: B:402:0x09ed, code:
            if (r11.zzfm != null) goto L_0x0a28;
     */
    /* JADX WARNING: Missing block: B:404:0x09f9, code:
            if (r11.zzfn.longValue() <= 1) goto L_0x0a03;
     */
    /* JADX WARNING: Missing block: B:405:0x09fb, code:
            zzgw();
            com.google.android.gms.measurement.internal.zzjo.zza(r8, r10, r11.zzfn);
     */
    /* JADX WARNING: Missing block: B:407:0x0a05, code:
            if (r11.zzfo == null) goto L_0x0a1d;
     */
    /* JADX WARNING: Missing block: B:409:0x0a0d, code:
            if (r11.zzfo.booleanValue() == false) goto L_0x0a1d;
     */
    /* JADX WARNING: Missing block: B:410:0x0a0f, code:
            zzgw();
            com.google.android.gms.measurement.internal.zzjo.zza(r8, "_efs", java.lang.Long.valueOf(1));
     */
    /* JADX WARNING: Missing block: B:411:0x0a1d, code:
            r5.add((com.google.android.gms.internal.measurement.zzbs.zzc) ((com.google.android.gms.internal.measurement.zzey) r8.zzug()));
     */
    /* JADX WARNING: Missing block: B:412:0x0a28, code:
            r3.zza(r7, r8);
     */
    /* JADX WARNING: Missing block: B:413:0x0a2b, code:
            r1 = r3;
            r63 = r6;
            r3 = r2;
            r2 = r7;
     */
    /* JADX WARNING: Missing block: B:415:?, code:
            r11 = zzgz().zzbb(r2.zztn.zzag());
            r1.zzj.zzz();
            r13 = com.google.android.gms.measurement.internal.zzjs.zzc(r8.getTimestampMillis(), r11);
            r9 = (com.google.android.gms.internal.measurement.zzbs.zzc) ((com.google.android.gms.internal.measurement.zzey) r8.zzug());
            r15 = "_dbg";
            r26 = r11;
            r11 = java.lang.Long.valueOf(1);
     */
    /* JADX WARNING: Missing block: B:416:0x0a65, code:
            if (android.text.TextUtils.isEmpty(r15) != false) goto L_0x0ac1;
     */
    /* JADX WARNING: Missing block: B:417:0x0a67, code:
            if (r11 != null) goto L_0x0a6a;
     */
    /* JADX WARNING: Missing block: B:419:?, code:
            r9 = r9.zzmj().iterator();
     */
    /* JADX WARNING: Missing block: B:421:0x0a76, code:
            if (r9.hasNext() == false) goto L_0x0ac1;
     */
    /* JADX WARNING: Missing block: B:422:0x0a78, code:
            r12 = (com.google.android.gms.internal.measurement.zzbs.zze) r9.next();
            r63 = r9;
     */
    /* JADX WARNING: Missing block: B:423:0x0a88, code:
            if (r15.equals(r12.getName()) == false) goto L_0x0abe;
     */
    /* JADX WARNING: Missing block: B:425:0x0a8c, code:
            if ((r11 instanceof java.lang.Long) == false) goto L_0x0a9c;
     */
    /* JADX WARNING: Missing block: B:427:0x0a9a, code:
            if (r11.equals(java.lang.Long.valueOf(r12.zznb())) != false) goto L_0x0abc;
     */
    /* JADX WARNING: Missing block: B:429:0x0a9e, code:
            if ((r11 instanceof java.lang.String) == false) goto L_0x0aaa;
     */
    /* JADX WARNING: Missing block: B:431:0x0aa8, code:
            if (r11.equals(r12.zzmy()) != false) goto L_0x0abc;
     */
    /* JADX WARNING: Missing block: B:433:0x0aac, code:
            if ((r11 instanceof java.lang.Double) == false) goto L_0x0ac1;
     */
    /* JADX WARNING: Missing block: B:435:0x0aba, code:
            if (r11.equals(java.lang.Double.valueOf(r12.zzne())) == false) goto L_0x0ac1;
     */
    /* JADX WARNING: Missing block: B:436:0x0abc, code:
            r9 = 1;
     */
    /* JADX WARNING: Missing block: B:437:0x0abe, code:
            r9 = r63;
     */
    /* JADX WARNING: Missing block: B:438:0x0ac1, code:
            r9 = null;
     */
    /* JADX WARNING: Missing block: B:439:0x0ac2, code:
            if (r9 != null) goto L_0x0ad7;
     */
    /* JADX WARNING: Missing block: B:440:0x0ac4, code:
            r12 = zzgz().zzm(r2.zztn.zzag(), r8.getName());
     */
    /* JADX WARNING: Missing block: B:441:0x0ad7, code:
            r12 = 1;
     */
    /* JADX WARNING: Missing block: B:442:0x0ad8, code:
            if (r12 > 0) goto L_0x0b01;
     */
    /* JADX WARNING: Missing block: B:443:0x0ada, code:
            r1.zzj.zzab().zzgn().zza("Sample rate must be positive. event, rate", r8.getName(), java.lang.Integer.valueOf(r12));
            r5.add((com.google.android.gms.internal.measurement.zzbs.zzc) ((com.google.android.gms.internal.measurement.zzey) r8.zzug()));
            r3.zza(r7, r8);
     */
    /* JADX WARNING: Missing block: B:445:?, code:
            r9 = (com.google.android.gms.measurement.internal.zzae) r4.get(r8.getName());
     */
    /* JADX WARNING: Missing block: B:446:0x0b0b, code:
            if (r9 != null) goto L_0x0b9a;
     */
    /* JADX WARNING: Missing block: B:448:?, code:
            r9 = zzgy().zzc(r2.zztn.zzag(), r8.getName());
     */
    /* JADX WARNING: Missing block: B:449:0x0b1f, code:
            if (r9 != null) goto L_0x0b9a;
     */
    /* JADX WARNING: Missing block: B:450:0x0b21, code:
            r18 = r13;
            r1.zzj.zzab().zzgn().zza("Event being bundled has no eventAggregate. appId, eventName", r2.zztn.zzag(), r8.getName());
     */
    /* JADX WARNING: Missing block: B:451:0x0b4e, code:
            if (r1.zzj.zzad().zze(r2.zztn.zzag(), com.google.android.gms.measurement.internal.zzak.zziz) == false) goto L_0x0b76;
     */
    /* JADX WARNING: Missing block: B:452:0x0b50, code:
            r30 = new com.google.android.gms.measurement.internal.zzae(r2.zztn.zzag(), r8.getName(), 1, 1, 1, r8.getTimestampMillis(), 0, null, null, null, null);
     */
    /* JADX WARNING: Missing block: B:453:0x0b76, code:
            r47 = new com.google.android.gms.measurement.internal.zzae(r2.zztn.zzag(), r8.getName(), 1, 1, r8.getTimestampMillis(), 0, null, null, null, null);
     */
    /* JADX WARNING: Missing block: B:454:0x0b9a, code:
            r18 = r13;
     */
    /* JADX WARNING: Missing block: B:456:?, code:
            zzgw();
            r11 = (java.lang.Long) com.google.android.gms.measurement.internal.zzjo.zzb((com.google.android.gms.internal.measurement.zzbs.zzc) ((com.google.android.gms.internal.measurement.zzey) r8.zzug()), "_eid");
     */
    /* JADX WARNING: Missing block: B:457:0x0baf, code:
            if (r11 == null) goto L_0x0bb3;
     */
    /* JADX WARNING: Missing block: B:458:0x0bb1, code:
            r13 = true;
     */
    /* JADX WARNING: Missing block: B:459:0x0bb3, code:
            r13 = false;
     */
    /* JADX WARNING: Missing block: B:460:0x0bb4, code:
            r13 = java.lang.Boolean.valueOf(r13);
     */
    /* JADX WARNING: Missing block: B:462:0x0bb9, code:
            if (r12 != 1) goto L_0x0be9;
     */
    /* JADX WARNING: Missing block: B:464:?, code:
            r5.add((com.google.android.gms.internal.measurement.zzbs.zzc) ((com.google.android.gms.internal.measurement.zzey) r8.zzug()));
     */
    /* JADX WARNING: Missing block: B:465:0x0bca, code:
            if (r13.booleanValue() == false) goto L_0x0be4;
     */
    /* JADX WARNING: Missing block: B:467:0x0bce, code:
            if (r9.zzfm != null) goto L_0x0bd8;
     */
    /* JADX WARNING: Missing block: B:469:0x0bd2, code:
            if (r9.zzfn != null) goto L_0x0bd8;
     */
    /* JADX WARNING: Missing block: B:471:0x0bd6, code:
            if (r9.zzfo == null) goto L_0x0be4;
     */
    /* JADX WARNING: Missing block: B:472:0x0bd8, code:
            r4.put(r8.getName(), r9.zza(null, null, null));
     */
    /* JADX WARNING: Missing block: B:473:0x0be4, code:
            r3.zza(r7, r8);
     */
    /* JADX WARNING: Missing block: B:476:0x0bed, code:
            if (r6.nextInt(r12) != 0) goto L_0x0c2e;
     */
    /* JADX WARNING: Missing block: B:478:?, code:
            zzgw();
            r11 = (long) r12;
            com.google.android.gms.measurement.internal.zzjo.zza(r8, r10, java.lang.Long.valueOf(r11));
            r5.add((com.google.android.gms.internal.measurement.zzbs.zzc) ((com.google.android.gms.internal.measurement.zzey) r8.zzug()));
     */
    /* JADX WARNING: Missing block: B:479:0x0c09, code:
            if (r13.booleanValue() == false) goto L_0x0c14;
     */
    /* JADX WARNING: Missing block: B:480:0x0c0b, code:
            r9 = r9.zza(null, java.lang.Long.valueOf(r11), null);
     */
    /* JADX WARNING: Missing block: B:481:0x0c14, code:
            r4.put(r8.getName(), r9.zza(r8.getTimestampMillis(), r18));
     */
    /* JADX WARNING: Missing block: B:482:0x0c25, code:
            r1 = r3;
            r63 = r6;
            r3 = r2;
            r2 = r7;
     */
    /* JADX WARNING: Missing block: B:483:0x0c2e, code:
            r63 = r6;
            r14 = r18;
     */
    /* JADX WARNING: Missing block: B:485:?, code:
            r18 = r3;
     */
    /* JADX WARNING: Missing block: B:486:0x0c44, code:
            if (r1.zzj.zzad().zzu(r2.zztn.zzag()) == false) goto L_0x0c6e;
     */
    /* JADX WARNING: Missing block: B:488:0x0c48, code:
            if (r9.zzfl == null) goto L_0x0c54;
     */
    /* JADX WARNING: Missing block: B:490:?, code:
            r26 = r9.zzfl.longValue();
     */
    /* JADX WARNING: Missing block: B:491:0x0c50, code:
            r3 = r2;
            r19 = r7;
     */
    /* JADX WARNING: Missing block: B:493:?, code:
            r1.zzj.zzz();
            r3 = r2;
            r19 = r7;
            r26 = com.google.android.gms.measurement.internal.zzjs.zzc(r8.zzmm(), r26);
     */
    /* JADX WARNING: Missing block: B:495:0x0c68, code:
            if (r26 == r14) goto L_0x0c6c;
     */
    /* JADX WARNING: Missing block: B:496:0x0c6a, code:
            r1 = 1;
     */
    /* JADX WARNING: Missing block: B:497:0x0c6c, code:
            r1 = null;
     */
    /* JADX WARNING: Missing block: B:498:0x0c6e, code:
            r3 = r2;
            r19 = r7;
     */
    /* JADX WARNING: Missing block: B:499:0x0c81, code:
            if (java.lang.Math.abs(r8.getTimestampMillis() - r9.zzfk) < 86400000) goto L_0x0c6c;
     */
    /* JADX WARNING: Missing block: B:500:0x0c84, code:
            if (r1 == null) goto L_0x0cce;
     */
    /* JADX WARNING: Missing block: B:501:0x0c86, code:
            zzgw();
            com.google.android.gms.measurement.internal.zzjo.zza(r8, "_efs", java.lang.Long.valueOf(1));
            zzgw();
            r1 = (long) r12;
            com.google.android.gms.measurement.internal.zzjo.zza(r8, r10, java.lang.Long.valueOf(r1));
            r5.add((com.google.android.gms.internal.measurement.zzbs.zzc) ((com.google.android.gms.internal.measurement.zzey) r8.zzug()));
     */
    /* JADX WARNING: Missing block: B:502:0x0cae, code:
            if (r13.booleanValue() == false) goto L_0x0cbe;
     */
    /* JADX WARNING: Missing block: B:503:0x0cb0, code:
            r9 = r9.zza(null, java.lang.Long.valueOf(r1), java.lang.Boolean.valueOf(true));
     */
    /* JADX WARNING: Missing block: B:504:0x0cbe, code:
            r4.put(r8.getName(), r9.zza(r8.getTimestampMillis(), r14));
     */
    /* JADX WARNING: Missing block: B:506:0x0cd4, code:
            if (r13.booleanValue() == false) goto L_0x0ce2;
     */
    /* JADX WARNING: Missing block: B:507:0x0cd6, code:
            r4.put(r8.getName(), r9.zza(r11, null, null));
     */
    /* JADX WARNING: Missing block: B:508:0x0ce2, code:
            r1 = r18;
            r2 = r19;
     */
    /* JADX WARNING: Missing block: B:509:0x0ce6, code:
            r1.zza(r2, r8);
     */
    /* JADX WARNING: Missing block: B:510:0x0ce9, code:
            r6 = r63;
            r7 = r2 + 1;
            r2 = r3;
            r3 = r1;
            r1 = r62;
     */
    /* JADX WARNING: Missing block: B:511:0x0cf4, code:
            r1 = r3;
            r3 = r2;
     */
    /* JADX WARNING: Missing block: B:512:0x0cfe, code:
            if (r5.size() >= r1.zznm()) goto L_0x0d07;
     */
    /* JADX WARNING: Missing block: B:513:0x0d00, code:
            r1.zznn().zza(r5);
     */
    /* JADX WARNING: Missing block: B:514:0x0d07, code:
            r2 = r4.entrySet().iterator();
     */
    /* JADX WARNING: Missing block: B:516:0x0d13, code:
            if (r2.hasNext() == false) goto L_0x0d2b;
     */
    /* JADX WARNING: Missing block: B:517:0x0d15, code:
            zzgy().zza((com.google.android.gms.measurement.internal.zzae) ((java.util.Map.Entry) r2.next()).getValue());
     */
    /* JADX WARNING: Missing block: B:518:0x0d29, code:
            r1 = r3;
            r3 = r2;
     */
    /* JADX WARNING: Missing block: B:519:0x0d2b, code:
            r1.zzao(Long.MAX_VALUE).zzap(Long.MIN_VALUE);
            r2 = 0;
     */
    /* JADX WARNING: Missing block: B:521:0x0d3e, code:
            if (r2 >= r1.zznm()) goto L_0x0d6d;
     */
    /* JADX WARNING: Missing block: B:522:0x0d40, code:
            r4 = r1.zzq(r2);
     */
    /* JADX WARNING: Missing block: B:523:0x0d4e, code:
            if (r4.getTimestampMillis() >= r1.zznq()) goto L_0x0d57;
     */
    /* JADX WARNING: Missing block: B:524:0x0d50, code:
            r1.zzao(r4.getTimestampMillis());
     */
    /* JADX WARNING: Missing block: B:526:0x0d61, code:
            if (r4.getTimestampMillis() <= r1.zznr()) goto L_0x0d6a;
     */
    /* JADX WARNING: Missing block: B:527:0x0d63, code:
            r1.zzap(r4.getTimestampMillis());
     */
    /* JADX WARNING: Missing block: B:528:0x0d6a, code:
            r2 = r2 + 1;
     */
    /* JADX WARNING: Missing block: B:529:0x0d6d, code:
            r2 = r3.zztn.zzag();
            r4 = zzgy().zzab(r2);
     */
    /* JADX WARNING: Missing block: B:530:0x0d7b, code:
            if (r4 != null) goto L_0x0d99;
     */
    /* JADX WARNING: Missing block: B:531:0x0d7d, code:
            r6 = r62;
     */
    /* JADX WARNING: Missing block: B:533:?, code:
            r6.zzj.zzab().zzgk().zza("Bundling raw events w/o app info. appId", com.google.android.gms.measurement.internal.zzef.zzam(r3.zztn.zzag()));
     */
    /* JADX WARNING: Missing block: B:534:0x0d99, code:
            r6 = r62;
     */
    /* JADX WARNING: Missing block: B:535:0x0d9f, code:
            if (r1.zznm() <= 0) goto L_0x0df6;
     */
    /* JADX WARNING: Missing block: B:536:0x0da1, code:
            r7 = r4.zzak();
     */
    /* JADX WARNING: Missing block: B:537:0x0da9, code:
            if (r7 == 0) goto L_0x0daf;
     */
    /* JADX WARNING: Missing block: B:538:0x0dab, code:
            r1.zzar(r7);
     */
    /* JADX WARNING: Missing block: B:539:0x0daf, code:
            r1.zznt();
     */
    /* JADX WARNING: Missing block: B:540:0x0db2, code:
            r9 = r4.zzaj();
     */
    /* JADX WARNING: Missing block: B:541:0x0dba, code:
            if (r9 != 0) goto L_0x0dbd;
     */
    /* JADX WARNING: Missing block: B:542:0x0dbd, code:
            r7 = r9;
     */
    /* JADX WARNING: Missing block: B:544:0x0dc0, code:
            if (r7 == 0) goto L_0x0dc6;
     */
    /* JADX WARNING: Missing block: B:545:0x0dc2, code:
            r1.zzaq(r7);
     */
    /* JADX WARNING: Missing block: B:546:0x0dc6, code:
            r1.zzns();
     */
    /* JADX WARNING: Missing block: B:547:0x0dc9, code:
            r4.zzau();
            r1.zzu((int) r4.zzar());
            r4.zze(r1.zznq());
            r4.zzf(r1.zznr());
            r5 = r4.zzbc();
     */
    /* JADX WARNING: Missing block: B:548:0x0de6, code:
            if (r5 == null) goto L_0x0dec;
     */
    /* JADX WARNING: Missing block: B:549:0x0de8, code:
            r1.zzcl(r5);
     */
    /* JADX WARNING: Missing block: B:550:0x0dec, code:
            r1.zznu();
     */
    /* JADX WARNING: Missing block: B:551:0x0def, code:
            zzgy().zza(r4);
     */
    /* JADX WARNING: Missing block: B:553:0x0dfa, code:
            if (r1.zznm() <= 0) goto L_0x0e5c;
     */
    /* JADX WARNING: Missing block: B:554:0x0dfc, code:
            r6.zzj.zzae();
            r4 = zzgz().zzaw(r3.zztn.zzag());
     */
    /* JADX WARNING: Missing block: B:555:0x0e0f, code:
            if (r4 == null) goto L_0x0e20;
     */
    /* JADX WARNING: Missing block: B:557:0x0e13, code:
            if (r4.zzzk != null) goto L_0x0e16;
     */
    /* JADX WARNING: Missing block: B:558:0x0e16, code:
            r1.zzav(r4.zzzk.longValue());
     */
    /* JADX WARNING: Missing block: B:560:0x0e2a, code:
            if (android.text.TextUtils.isEmpty(r3.zztn.getGmpAppId()) == false) goto L_0x0e32;
     */
    /* JADX WARNING: Missing block: B:561:0x0e2c, code:
            r1.zzav(-1);
     */
    /* JADX WARNING: Missing block: B:562:0x0e32, code:
            r6.zzj.zzab().zzgn().zza("Did not find measurement config or missing version info. appId", com.google.android.gms.measurement.internal.zzef.zzam(r3.zztn.zzag()));
     */
    /* JADX WARNING: Missing block: B:563:0x0e4b, code:
            zzgy().zza((com.google.android.gms.internal.measurement.zzbs.zzg) ((com.google.android.gms.internal.measurement.zzey) r1.zzug()), r21);
     */
    /* JADX WARNING: Missing block: B:564:0x0e5c, code:
            r1 = zzgy();
            r3 = r3.zzto;
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r3);
            r1.zzo();
            r1.zzbi();
            r4 = new java.lang.StringBuilder("rowid in (");
            r5 = 0;
     */
    /* JADX WARNING: Missing block: B:566:0x0e77, code:
            if (r5 >= r3.size()) goto L_0x0e90;
     */
    /* JADX WARNING: Missing block: B:567:0x0e79, code:
            if (r5 == 0) goto L_0x0e80;
     */
    /* JADX WARNING: Missing block: B:568:0x0e7b, code:
            r4.append(",");
     */
    /* JADX WARNING: Missing block: B:569:0x0e80, code:
            r4.append(((java.lang.Long) r3.get(r5)).longValue());
            r5 = r5 + 1;
     */
    /* JADX WARNING: Missing block: B:570:0x0e90, code:
            r4.append(")");
            r4 = r1.getWritableDatabase().delete("raw_events", r4.toString(), null);
     */
    /* JADX WARNING: Missing block: B:571:0x0ea8, code:
            if (r4 == r3.size()) goto L_0x0ec3;
     */
    /* JADX WARNING: Missing block: B:572:0x0eaa, code:
            r1.zzab().zzgk().zza("Deleted fewer rows from raw events table than expected", java.lang.Integer.valueOf(r4), java.lang.Integer.valueOf(r3.size()));
     */
    /* JADX WARNING: Missing block: B:573:0x0ec3, code:
            r1 = zzgy();
     */
    /* JADX WARNING: Missing block: B:575:?, code:
            r1.getWritableDatabase().execSQL("delete from raw_events_metadata where app_id=? and metadata_fingerprint not in (select distinct metadata_fingerprint from raw_events where app_id=?)", new java.lang.String[]{r2, r2});
     */
    /* JADX WARNING: Missing block: B:576:0x0eda, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:579:?, code:
            r1.zzab().zzgk().zza("Failed to remove unused event metadata. appId", com.google.android.gms.measurement.internal.zzef.zzam(r2), r0);
     */
    /* JADX WARNING: Missing block: B:583:0x0efd, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:584:0x0efe, code:
            r6 = r62;
     */
    /* JADX WARNING: Missing block: B:596:0x0f1d, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:599:0x0f21, code:
            r1 = r0;
            zzgy().endTransaction();
     */
    /* JADX WARNING: Missing block: B:600:0x0f29, code:
            throw r1;
     */
    @androidx.annotation.WorkerThread
    private final boolean zzd(java.lang.String r63, long r64) {
        /*
        r62 = this;
        r1 = r62;
        r2 = r62.zzgy();
        r2.beginTransaction();
        r2 = new com.google.android.gms.measurement.internal.zzjg$zza;	 Catch:{ all -> 0x0f1f }
        r3 = 0;
        r2.<init>(r1, r3);	 Catch:{ all -> 0x0f1f }
        r4 = r62.zzgy();	 Catch:{ all -> 0x0f1f }
        r5 = r1.zztj;	 Catch:{ all -> 0x0f1f }
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r2);	 Catch:{ all -> 0x0f1f }
        r4.zzo();	 Catch:{ all -> 0x0f1f }
        r4.zzbi();	 Catch:{ all -> 0x0f1f }
        r8 = -1;
        r10 = 2;
        r11 = 0;
        r12 = 1;
        r15 = r4.getWritableDatabase();	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r13 = android.text.TextUtils.isEmpty(r3);	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        if (r13 == 0) goto L_0x009b;
    L_0x002d:
        r13 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1));
        if (r13 == 0) goto L_0x004c;
    L_0x0031:
        r14 = new java.lang.String[r10];	 Catch:{ SQLiteException -> 0x0046, all -> 0x0040 }
        r16 = java.lang.String.valueOf(r5);	 Catch:{ SQLiteException -> 0x0046, all -> 0x0040 }
        r14[r11] = r16;	 Catch:{ SQLiteException -> 0x0046, all -> 0x0040 }
        r16 = java.lang.String.valueOf(r64);	 Catch:{ SQLiteException -> 0x0046, all -> 0x0040 }
        r14[r12] = r16;	 Catch:{ SQLiteException -> 0x0046, all -> 0x0040 }
        goto L_0x0054;
    L_0x0040:
        r0 = move-exception;
        r6 = r1;
        r22 = r3;
        goto L_0x0271;
    L_0x0046:
        r0 = move-exception;
        r5 = r3;
        r7 = r5;
    L_0x0049:
        r3 = r0;
        goto L_0x0278;
    L_0x004c:
        r14 = new java.lang.String[r12];	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r16 = java.lang.String.valueOf(r64);	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r14[r11] = r16;	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
    L_0x0054:
        if (r13 == 0) goto L_0x0059;
    L_0x0056:
        r13 = "rowid <= ? and ";
        goto L_0x005b;
    L_0x0059:
        r13 = "";
    L_0x005b:
        r7 = r13.length();	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r7 = r7 + 148;
        r3 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r3.<init>(r7);	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r7 = "select app_id, metadata_fingerprint from raw_events where ";
        r3.append(r7);	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r3.append(r13);	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r7 = "app_id in (select app_id from apps where config_fetched_time >= ?) order by rowid limit 1;";
        r3.append(r7);	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r3 = r3.toString();	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r3 = r15.rawQuery(r3, r14);	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r7 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x0268, all -> 0x0040 }
        if (r7 != 0) goto L_0x0088;
    L_0x0081:
        if (r3 == 0) goto L_0x028e;
    L_0x0083:
        r3.close();	 Catch:{ all -> 0x0f1f }
        goto L_0x028e;
    L_0x0088:
        r7 = r3.getString(r11);	 Catch:{ SQLiteException -> 0x0268, all -> 0x0040 }
        r13 = r3.getString(r12);	 Catch:{ SQLiteException -> 0x0098, all -> 0x0040 }
        r3.close();	 Catch:{ SQLiteException -> 0x0098, all -> 0x0040 }
        r23 = r3;
        r3 = r7;
        r7 = r13;
        goto L_0x00f0;
    L_0x0098:
        r0 = move-exception;
        r5 = r3;
        goto L_0x0049;
    L_0x009b:
        r3 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1));
        if (r3 == 0) goto L_0x00ac;
    L_0x009f:
        r7 = new java.lang.String[r10];	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r13 = 0;
        r7[r11] = r13;	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r13 = java.lang.String.valueOf(r5);	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r7[r12] = r13;	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r13 = r7;
        goto L_0x00b1;
    L_0x00ac:
        r7 = 0;
        r13 = new java.lang.String[]{r7};	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
    L_0x00b1:
        if (r3 == 0) goto L_0x00b6;
    L_0x00b3:
        r3 = " and rowid <= ?";
        goto L_0x00b8;
    L_0x00b6:
        r3 = "";
    L_0x00b8:
        r7 = r3.length();	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r7 = r7 + 84;
        r14 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r14.<init>(r7);	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r7 = "select metadata_fingerprint from raw_events where app_id = ?";
        r14.append(r7);	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r14.append(r3);	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r3 = " order by rowid limit 1;";
        r14.append(r3);	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r3 = r14.toString();	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r3 = r15.rawQuery(r3, r13);	 Catch:{ SQLiteException -> 0x0274, all -> 0x026d }
        r7 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x0268, all -> 0x0040 }
        if (r7 != 0) goto L_0x00e5;
    L_0x00de:
        if (r3 == 0) goto L_0x028e;
    L_0x00e0:
        r3.close();	 Catch:{ all -> 0x0f1f }
        goto L_0x028e;
    L_0x00e5:
        r13 = r3.getString(r11);	 Catch:{ SQLiteException -> 0x0268, all -> 0x0040 }
        r3.close();	 Catch:{ SQLiteException -> 0x0268, all -> 0x0040 }
        r23 = r3;
        r7 = r13;
        r3 = 0;
    L_0x00f0:
        r14 = "raw_events_metadata";
        r13 = "metadata";
        r16 = new java.lang.String[]{r13};	 Catch:{ SQLiteException -> 0x0262, all -> 0x025d }
        r17 = "app_id = ? and metadata_fingerprint = ?";
        r13 = new java.lang.String[r10];	 Catch:{ SQLiteException -> 0x0262, all -> 0x025d }
        r13[r11] = r3;	 Catch:{ SQLiteException -> 0x0262, all -> 0x025d }
        r13[r12] = r7;	 Catch:{ SQLiteException -> 0x0262, all -> 0x025d }
        r18 = 0;
        r19 = 0;
        r20 = "rowid";
        r21 = "2";
        r24 = r13;
        r13 = r15;
        r25 = r15;
        r15 = r16;
        r16 = r17;
        r17 = r24;
        r15 = r13.query(r14, r15, r16, r17, r18, r19, r20, r21);	 Catch:{ SQLiteException -> 0x0262, all -> 0x025d }
        r13 = r15.moveToFirst();	 Catch:{ SQLiteException -> 0x0257, all -> 0x0251 }
        if (r13 != 0) goto L_0x0140;
    L_0x011d:
        r5 = r4.zzab();	 Catch:{ SQLiteException -> 0x013b, all -> 0x0135 }
        r5 = r5.zzgk();	 Catch:{ SQLiteException -> 0x013b, all -> 0x0135 }
        r6 = "Raw event metadata record is missing. appId";
        r7 = com.google.android.gms.measurement.internal.zzef.zzam(r3);	 Catch:{ SQLiteException -> 0x013b, all -> 0x0135 }
        r5.zza(r6, r7);	 Catch:{ SQLiteException -> 0x013b, all -> 0x0135 }
        if (r15 == 0) goto L_0x028e;
    L_0x0130:
        r15.close();	 Catch:{ all -> 0x0f1f }
        goto L_0x028e;
    L_0x0135:
        r0 = move-exception;
        r6 = r1;
        r22 = r15;
        goto L_0x0271;
    L_0x013b:
        r0 = move-exception;
        r7 = r3;
        r5 = r15;
        goto L_0x0049;
    L_0x0140:
        r13 = r15.getBlob(r11);	 Catch:{ SQLiteException -> 0x0257, all -> 0x0251 }
        r14 = com.google.android.gms.internal.measurement.zzel.zztq();	 Catch:{ IOException -> 0x0233 }
        r13 = com.google.android.gms.internal.measurement.zzbs.zzg.zzd(r13, r14);	 Catch:{ IOException -> 0x0233 }
        r14 = r15.moveToNext();	 Catch:{ SQLiteException -> 0x0257, all -> 0x0251 }
        if (r14 == 0) goto L_0x0163;
    L_0x0152:
        r14 = r4.zzab();	 Catch:{ SQLiteException -> 0x013b, all -> 0x0135 }
        r14 = r14.zzgn();	 Catch:{ SQLiteException -> 0x013b, all -> 0x0135 }
        r10 = "Get multiple raw event metadata records, expected one. appId";
        r12 = com.google.android.gms.measurement.internal.zzef.zzam(r3);	 Catch:{ SQLiteException -> 0x013b, all -> 0x0135 }
        r14.zza(r10, r12);	 Catch:{ SQLiteException -> 0x013b, all -> 0x0135 }
    L_0x0163:
        r15.close();	 Catch:{ SQLiteException -> 0x0257, all -> 0x0251 }
        r2.zzb(r13);	 Catch:{ SQLiteException -> 0x0257, all -> 0x0251 }
        r10 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1));
        if (r10 == 0) goto L_0x0183;
    L_0x016d:
        r10 = "app_id = ? and metadata_fingerprint = ? and rowid <= ?";
        r12 = 3;
        r13 = new java.lang.String[r12];	 Catch:{ SQLiteException -> 0x013b, all -> 0x0135 }
        r13[r11] = r3;	 Catch:{ SQLiteException -> 0x013b, all -> 0x0135 }
        r12 = 1;
        r13[r12] = r7;	 Catch:{ SQLiteException -> 0x013b, all -> 0x0135 }
        r5 = java.lang.String.valueOf(r5);	 Catch:{ SQLiteException -> 0x013b, all -> 0x0135 }
        r6 = 2;
        r13[r6] = r5;	 Catch:{ SQLiteException -> 0x013b, all -> 0x0135 }
        r16 = r10;
        r17 = r13;
        goto L_0x0191;
    L_0x0183:
        r5 = "app_id = ? and metadata_fingerprint = ?";
        r6 = 2;
        r10 = new java.lang.String[r6];	 Catch:{ SQLiteException -> 0x0257, all -> 0x0251 }
        r10[r11] = r3;	 Catch:{ SQLiteException -> 0x0257, all -> 0x0251 }
        r6 = 1;
        r10[r6] = r7;	 Catch:{ SQLiteException -> 0x0257, all -> 0x0251 }
        r16 = r5;
        r17 = r10;
    L_0x0191:
        r14 = "raw_events";
        r5 = "rowid";
        r6 = "name";
        r7 = "timestamp";
        r10 = "data";
        r5 = new java.lang.String[]{r5, r6, r7, r10};	 Catch:{ SQLiteException -> 0x0257, all -> 0x0251 }
        r18 = 0;
        r19 = 0;
        r20 = "rowid";
        r21 = 0;
        r13 = r25;
        r6 = r15;
        r15 = r5;
        r5 = r13.query(r14, r15, r16, r17, r18, r19, r20, r21);	 Catch:{ SQLiteException -> 0x024f, all -> 0x024d }
        r6 = r5.moveToFirst();	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        if (r6 != 0) goto L_0x01cd;
    L_0x01b5:
        r6 = r4.zzab();	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        r6 = r6.zzgn();	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        r7 = "Raw event data disappeared while in transaction. appId";
        r10 = com.google.android.gms.measurement.internal.zzef.zzam(r3);	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        r6.zza(r7, r10);	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        if (r5 == 0) goto L_0x028e;
    L_0x01c8:
        r5.close();	 Catch:{ all -> 0x0f1f }
        goto L_0x028e;
    L_0x01cd:
        r6 = r5.getLong(r11);	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        r10 = 3;
        r12 = r5.getBlob(r10);	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        r10 = com.google.android.gms.internal.measurement.zzbs.zzc.zzmq();	 Catch:{ IOException -> 0x020a }
        r13 = com.google.android.gms.internal.measurement.zzel.zztq();	 Catch:{ IOException -> 0x020a }
        r10 = r10.zzf(r12, r13);	 Catch:{ IOException -> 0x020a }
        r10 = (com.google.android.gms.internal.measurement.zzbs.zzc.zza) r10;	 Catch:{ IOException -> 0x020a }
        r12 = 1;
        r13 = r5.getString(r12);	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        r12 = r10.zzbx(r13);	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        r13 = 2;
        r14 = r5.getLong(r13);	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        r12.zzag(r14);	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        r10 = r10.zzug();	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        r10 = (com.google.android.gms.internal.measurement.zzey) r10;	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        r10 = (com.google.android.gms.internal.measurement.zzbs.zzc) r10;	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        r6 = r2.zza(r6, r10);	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        if (r6 != 0) goto L_0x021d;
    L_0x0203:
        if (r5 == 0) goto L_0x028e;
    L_0x0205:
        r5.close();	 Catch:{ all -> 0x0f1f }
        goto L_0x028e;
    L_0x020a:
        r0 = move-exception;
        r6 = r0;
        r7 = r4.zzab();	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        r7 = r7.zzgk();	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        r10 = "Data loss. Failed to merge raw event. appId";
        r12 = com.google.android.gms.measurement.internal.zzef.zzam(r3);	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        r7.zza(r10, r12, r6);	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
    L_0x021d:
        r6 = r5.moveToNext();	 Catch:{ SQLiteException -> 0x022f, all -> 0x022a }
        if (r6 != 0) goto L_0x01cd;
    L_0x0223:
        if (r5 == 0) goto L_0x028e;
    L_0x0225:
        r5.close();	 Catch:{ all -> 0x0f1f }
        goto L_0x028e;
    L_0x022a:
        r0 = move-exception;
        r6 = r1;
        r22 = r5;
        goto L_0x0271;
    L_0x022f:
        r0 = move-exception;
        r7 = r3;
        goto L_0x0049;
    L_0x0233:
        r0 = move-exception;
        r6 = r15;
        r5 = r0;
        r7 = r4.zzab();	 Catch:{ SQLiteException -> 0x024f, all -> 0x024d }
        r7 = r7.zzgk();	 Catch:{ SQLiteException -> 0x024f, all -> 0x024d }
        r10 = "Data loss. Failed to merge raw event metadata. appId";
        r12 = com.google.android.gms.measurement.internal.zzef.zzam(r3);	 Catch:{ SQLiteException -> 0x024f, all -> 0x024d }
        r7.zza(r10, r12, r5);	 Catch:{ SQLiteException -> 0x024f, all -> 0x024d }
        if (r6 == 0) goto L_0x028e;
    L_0x0249:
        r6.close();	 Catch:{ all -> 0x0f1f }
        goto L_0x028e;
    L_0x024d:
        r0 = move-exception;
        goto L_0x0253;
    L_0x024f:
        r0 = move-exception;
        goto L_0x0259;
    L_0x0251:
        r0 = move-exception;
        r6 = r15;
    L_0x0253:
        r22 = r6;
        r6 = r1;
        goto L_0x0271;
    L_0x0257:
        r0 = move-exception;
        r6 = r15;
    L_0x0259:
        r7 = r3;
        r5 = r6;
        goto L_0x0049;
    L_0x025d:
        r0 = move-exception;
        r6 = r1;
        r22 = r23;
        goto L_0x0271;
    L_0x0262:
        r0 = move-exception;
        r7 = r3;
        r5 = r23;
        goto L_0x0049;
    L_0x0268:
        r0 = move-exception;
        r5 = r3;
        r7 = 0;
        goto L_0x0049;
    L_0x026d:
        r0 = move-exception;
        r6 = r1;
        r22 = 0;
    L_0x0271:
        r1 = r0;
        goto L_0x0f17;
    L_0x0274:
        r0 = move-exception;
        r3 = r0;
        r5 = 0;
        r7 = 0;
    L_0x0278:
        r4 = r4.zzab();	 Catch:{ all -> 0x0f12 }
        r4 = r4.zzgk();	 Catch:{ all -> 0x0f12 }
        r6 = "Data loss. Error selecting raw event. appId";
        r7 = com.google.android.gms.measurement.internal.zzef.zzam(r7);	 Catch:{ all -> 0x0f12 }
        r4.zza(r6, r7, r3);	 Catch:{ all -> 0x0f12 }
        if (r5 == 0) goto L_0x028e;
    L_0x028b:
        r5.close();	 Catch:{ all -> 0x0f1f }
    L_0x028e:
        r3 = r2.zztp;	 Catch:{ all -> 0x0f1f }
        if (r3 == 0) goto L_0x029d;
    L_0x0292:
        r3 = r2.zztp;	 Catch:{ all -> 0x0f1f }
        r3 = r3.isEmpty();	 Catch:{ all -> 0x0f1f }
        if (r3 == 0) goto L_0x029b;
    L_0x029a:
        goto L_0x029d;
    L_0x029b:
        r3 = 0;
        goto L_0x029e;
    L_0x029d:
        r3 = 1;
    L_0x029e:
        if (r3 != 0) goto L_0x0f01;
    L_0x02a0:
        r3 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r3 = r3.zzuj();	 Catch:{ all -> 0x0f1f }
        r3 = (com.google.android.gms.internal.measurement.zzey.zza) r3;	 Catch:{ all -> 0x0f1f }
        r3 = (com.google.android.gms.internal.measurement.zzbs.zzg.zza) r3;	 Catch:{ all -> 0x0f1f }
        r3 = r3.zznn();	 Catch:{ all -> 0x0f1f }
        r4 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzad();	 Catch:{ all -> 0x0f1f }
        r5 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r5 = r5.zzag();	 Catch:{ all -> 0x0f1f }
        r6 = com.google.android.gms.measurement.internal.zzak.zzii;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zze(r5, r6);	 Catch:{ all -> 0x0f1f }
        r7 = 0;
        r8 = -1;
        r9 = -1;
        r12 = 0;
        r13 = 0;
        r15 = 0;
        r18 = 0;
        r19 = 0;
    L_0x02cb:
        r11 = r2.zztp;	 Catch:{ all -> 0x0f1f }
        r11 = r11.size();	 Catch:{ all -> 0x0f1f }
        r5 = "_et";
        r6 = "_e";
        r21 = r12;
        r26 = r13;
        if (r7 >= r11) goto L_0x07ea;
    L_0x02db:
        r11 = r2.zztp;	 Catch:{ all -> 0x0f1f }
        r11 = r11.get(r7);	 Catch:{ all -> 0x0f1f }
        r11 = (com.google.android.gms.internal.measurement.zzbs.zzc) r11;	 Catch:{ all -> 0x0f1f }
        r11 = r11.zzuj();	 Catch:{ all -> 0x0f1f }
        r11 = (com.google.android.gms.internal.measurement.zzey.zza) r11;	 Catch:{ all -> 0x0f1f }
        r11 = (com.google.android.gms.internal.measurement.zzbs.zzc.zza) r11;	 Catch:{ all -> 0x0f1f }
        r14 = r62.zzgz();	 Catch:{ all -> 0x0f1f }
        r10 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r10 = r10.zzag();	 Catch:{ all -> 0x0f1f }
        r12 = r11.getName();	 Catch:{ all -> 0x0f1f }
        r10 = r14.zzk(r10, r12);	 Catch:{ all -> 0x0f1f }
        r12 = "_err";
        if (r10 == 0) goto L_0x037d;
    L_0x0301:
        r5 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r5 = r5.zzab();	 Catch:{ all -> 0x0f1f }
        r5 = r5.zzgn();	 Catch:{ all -> 0x0f1f }
        r6 = "Dropping blacklisted raw event. appId";
        r10 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r10 = r10.zzag();	 Catch:{ all -> 0x0f1f }
        r10 = com.google.android.gms.measurement.internal.zzef.zzam(r10);	 Catch:{ all -> 0x0f1f }
        r13 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r13 = r13.zzy();	 Catch:{ all -> 0x0f1f }
        r14 = r11.getName();	 Catch:{ all -> 0x0f1f }
        r13 = r13.zzaj(r14);	 Catch:{ all -> 0x0f1f }
        r5.zza(r6, r10, r13);	 Catch:{ all -> 0x0f1f }
        r5 = r62.zzgz();	 Catch:{ all -> 0x0f1f }
        r6 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r6 = r6.zzag();	 Catch:{ all -> 0x0f1f }
        r5 = r5.zzbc(r6);	 Catch:{ all -> 0x0f1f }
        if (r5 != 0) goto L_0x034b;
    L_0x0338:
        r5 = r62.zzgz();	 Catch:{ all -> 0x0f1f }
        r6 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r6 = r6.zzag();	 Catch:{ all -> 0x0f1f }
        r5 = r5.zzbd(r6);	 Catch:{ all -> 0x0f1f }
        if (r5 == 0) goto L_0x0349;
    L_0x0348:
        goto L_0x034b;
    L_0x0349:
        r5 = 0;
        goto L_0x034c;
    L_0x034b:
        r5 = 1;
    L_0x034c:
        if (r5 != 0) goto L_0x0371;
    L_0x034e:
        r5 = r11.getName();	 Catch:{ all -> 0x0f1f }
        r5 = r12.equals(r5);	 Catch:{ all -> 0x0f1f }
        if (r5 != 0) goto L_0x0371;
    L_0x0358:
        r5 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r28 = r5.zzz();	 Catch:{ all -> 0x0f1f }
        r5 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r29 = r5.zzag();	 Catch:{ all -> 0x0f1f }
        r30 = 11;
        r31 = "_ev";
        r32 = r11.getName();	 Catch:{ all -> 0x0f1f }
        r33 = 0;
        r28.zza(r29, r30, r31, r32, r33);	 Catch:{ all -> 0x0f1f }
    L_0x0371:
        r31 = r4;
        r6 = r9;
        r12 = r21;
        r13 = r26;
        r5 = -1;
        r10 = 3;
        r9 = r7;
        goto L_0x07e3;
    L_0x037d:
        r10 = r62.zzgz();	 Catch:{ all -> 0x0f1f }
        r13 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r13 = r13.zzag();	 Catch:{ all -> 0x0f1f }
        r14 = r11.getName();	 Catch:{ all -> 0x0f1f }
        r10 = r10.zzl(r13, r14);	 Catch:{ all -> 0x0f1f }
        r13 = "_c";
        if (r10 != 0) goto L_0x03ec;
    L_0x0393:
        r62.zzgw();	 Catch:{ all -> 0x0f1f }
        r14 = r11.getName();	 Catch:{ all -> 0x0f1f }
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r14);	 Catch:{ all -> 0x0f1f }
        r25 = r15;
        r15 = r14.hashCode();	 Catch:{ all -> 0x0f1f }
        r30 = r7;
        r7 = 94660; // 0x171c4 float:1.32647E-40 double:4.67683E-319;
        if (r15 == r7) goto L_0x03c9;
    L_0x03aa:
        r7 = 95025; // 0x17331 float:1.33158E-40 double:4.69486E-319;
        if (r15 == r7) goto L_0x03bf;
    L_0x03af:
        r7 = 95027; // 0x17333 float:1.33161E-40 double:4.69496E-319;
        if (r15 == r7) goto L_0x03b5;
    L_0x03b4:
        goto L_0x03d3;
    L_0x03b5:
        r7 = "_ui";
        r7 = r14.equals(r7);	 Catch:{ all -> 0x0f1f }
        if (r7 == 0) goto L_0x03d3;
    L_0x03bd:
        r7 = 1;
        goto L_0x03d4;
    L_0x03bf:
        r7 = "_ug";
        r7 = r14.equals(r7);	 Catch:{ all -> 0x0f1f }
        if (r7 == 0) goto L_0x03d3;
    L_0x03c7:
        r7 = 2;
        goto L_0x03d4;
    L_0x03c9:
        r7 = "_in";
        r7 = r14.equals(r7);	 Catch:{ all -> 0x0f1f }
        if (r7 == 0) goto L_0x03d3;
    L_0x03d1:
        r7 = 0;
        goto L_0x03d4;
    L_0x03d3:
        r7 = -1;
    L_0x03d4:
        if (r7 == 0) goto L_0x03de;
    L_0x03d6:
        r14 = 1;
        if (r7 == r14) goto L_0x03de;
    L_0x03d9:
        r14 = 2;
        if (r7 == r14) goto L_0x03de;
    L_0x03dc:
        r7 = 0;
        goto L_0x03df;
    L_0x03de:
        r7 = 1;
    L_0x03df:
        if (r7 == 0) goto L_0x03e2;
    L_0x03e1:
        goto L_0x03f0;
    L_0x03e2:
        r31 = r4;
        r7 = r5;
        r32 = r8;
        r33 = r9;
        r9 = r6;
        goto L_0x05d0;
    L_0x03ec:
        r30 = r7;
        r25 = r15;
    L_0x03f0:
        r31 = r4;
        r7 = 0;
        r14 = 0;
        r15 = 0;
    L_0x03f5:
        r4 = r11.zzmk();	 Catch:{ all -> 0x0f1f }
        r32 = r8;
        r8 = "_r";
        if (r7 >= r4) goto L_0x0463;
    L_0x03ff:
        r4 = r11.zzl(r7);	 Catch:{ all -> 0x0f1f }
        r4 = r4.getName();	 Catch:{ all -> 0x0f1f }
        r4 = r13.equals(r4);	 Catch:{ all -> 0x0f1f }
        if (r4 == 0) goto L_0x042e;
    L_0x040d:
        r4 = r11.zzl(r7);	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzuj();	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzey.zza) r4;	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzbs.zze.zza) r4;	 Catch:{ all -> 0x0f1f }
        r33 = r9;
        r8 = 1;
        r4 = r4.zzam(r8);	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzug();	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzey) r4;	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzbs.zze) r4;	 Catch:{ all -> 0x0f1f }
        r11.zza(r7, r4);	 Catch:{ all -> 0x0f1f }
        r14 = 1;
        goto L_0x045c;
    L_0x042e:
        r33 = r9;
        r4 = r11.zzl(r7);	 Catch:{ all -> 0x0f1f }
        r4 = r4.getName();	 Catch:{ all -> 0x0f1f }
        r4 = r8.equals(r4);	 Catch:{ all -> 0x0f1f }
        if (r4 == 0) goto L_0x045c;
    L_0x043e:
        r4 = r11.zzl(r7);	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzuj();	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzey.zza) r4;	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzbs.zze.zza) r4;	 Catch:{ all -> 0x0f1f }
        r8 = 1;
        r4 = r4.zzam(r8);	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzug();	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzey) r4;	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzbs.zze) r4;	 Catch:{ all -> 0x0f1f }
        r11.zza(r7, r4);	 Catch:{ all -> 0x0f1f }
        r15 = 1;
    L_0x045c:
        r7 = r7 + 1;
        r8 = r32;
        r9 = r33;
        goto L_0x03f5;
    L_0x0463:
        r33 = r9;
        if (r14 != 0) goto L_0x049a;
    L_0x0467:
        if (r10 == 0) goto L_0x049a;
    L_0x0469:
        r4 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzab();	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzgs();	 Catch:{ all -> 0x0f1f }
        r7 = "Marking event as conversion";
        r9 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r9 = r9.zzy();	 Catch:{ all -> 0x0f1f }
        r14 = r11.getName();	 Catch:{ all -> 0x0f1f }
        r9 = r9.zzaj(r14);	 Catch:{ all -> 0x0f1f }
        r4.zza(r7, r9);	 Catch:{ all -> 0x0f1f }
        r4 = com.google.android.gms.internal.measurement.zzbs.zze.zzng();	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzbz(r13);	 Catch:{ all -> 0x0f1f }
        r7 = r5;
        r9 = r6;
        r5 = 1;
        r4 = r4.zzam(r5);	 Catch:{ all -> 0x0f1f }
        r11.zza(r4);	 Catch:{ all -> 0x0f1f }
        goto L_0x049c;
    L_0x049a:
        r7 = r5;
        r9 = r6;
    L_0x049c:
        if (r15 != 0) goto L_0x04cc;
    L_0x049e:
        r4 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzab();	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzgs();	 Catch:{ all -> 0x0f1f }
        r5 = "Marking event as real-time";
        r6 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r6 = r6.zzy();	 Catch:{ all -> 0x0f1f }
        r14 = r11.getName();	 Catch:{ all -> 0x0f1f }
        r6 = r6.zzaj(r14);	 Catch:{ all -> 0x0f1f }
        r4.zza(r5, r6);	 Catch:{ all -> 0x0f1f }
        r4 = com.google.android.gms.internal.measurement.zzbs.zze.zzng();	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzbz(r8);	 Catch:{ all -> 0x0f1f }
        r5 = 1;
        r4 = r4.zzam(r5);	 Catch:{ all -> 0x0f1f }
        r11.zza(r4);	 Catch:{ all -> 0x0f1f }
    L_0x04cc:
        r34 = r62.zzgy();	 Catch:{ all -> 0x0f1f }
        r35 = r62.zzjk();	 Catch:{ all -> 0x0f1f }
        r4 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r37 = r4.zzag();	 Catch:{ all -> 0x0f1f }
        r38 = 0;
        r39 = 0;
        r40 = 0;
        r41 = 0;
        r42 = 1;
        r4 = r34.zza(r35, r37, r38, r39, r40, r41, r42);	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzej;	 Catch:{ all -> 0x0f1f }
        r6 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r6 = r6.zzad();	 Catch:{ all -> 0x0f1f }
        r14 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r14 = r14.zzag();	 Catch:{ all -> 0x0f1f }
        r6 = r6.zzi(r14);	 Catch:{ all -> 0x0f1f }
        r14 = (long) r6;	 Catch:{ all -> 0x0f1f }
        r6 = (r4 > r14 ? 1 : (r4 == r14 ? 0 : -1));
        if (r6 <= 0) goto L_0x0503;
    L_0x04ff:
        zza(r11, r8);	 Catch:{ all -> 0x0f1f }
        goto L_0x0505;
    L_0x0503:
        r21 = 1;
    L_0x0505:
        r4 = r11.getName();	 Catch:{ all -> 0x0f1f }
        r4 = com.google.android.gms.measurement.internal.zzjs.zzbk(r4);	 Catch:{ all -> 0x0f1f }
        if (r4 == 0) goto L_0x05d0;
    L_0x050f:
        if (r10 == 0) goto L_0x05d0;
    L_0x0511:
        r34 = r62.zzgy();	 Catch:{ all -> 0x0f1f }
        r35 = r62.zzjk();	 Catch:{ all -> 0x0f1f }
        r4 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r37 = r4.zzag();	 Catch:{ all -> 0x0f1f }
        r38 = 0;
        r39 = 0;
        r40 = 1;
        r41 = 0;
        r42 = 0;
        r4 = r34.zza(r35, r37, r38, r39, r40, r41, r42);	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzeh;	 Catch:{ all -> 0x0f1f }
        r6 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r6 = r6.zzad();	 Catch:{ all -> 0x0f1f }
        r8 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r8 = r8.zzag();	 Catch:{ all -> 0x0f1f }
        r14 = com.google.android.gms.measurement.internal.zzak.zzgs;	 Catch:{ all -> 0x0f1f }
        r6 = r6.zzb(r8, r14);	 Catch:{ all -> 0x0f1f }
        r14 = (long) r6;	 Catch:{ all -> 0x0f1f }
        r6 = (r4 > r14 ? 1 : (r4 == r14 ? 0 : -1));
        if (r6 <= 0) goto L_0x05d0;
    L_0x0546:
        r4 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzab();	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzgn();	 Catch:{ all -> 0x0f1f }
        r5 = "Too many conversions. Not logging as conversion. appId";
        r6 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r6 = r6.zzag();	 Catch:{ all -> 0x0f1f }
        r6 = com.google.android.gms.measurement.internal.zzef.zzam(r6);	 Catch:{ all -> 0x0f1f }
        r4.zza(r5, r6);	 Catch:{ all -> 0x0f1f }
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r8 = -1;
    L_0x0563:
        r14 = r11.zzmk();	 Catch:{ all -> 0x0f1f }
        if (r4 >= r14) goto L_0x058f;
    L_0x0569:
        r14 = r11.zzl(r4);	 Catch:{ all -> 0x0f1f }
        r15 = r14.getName();	 Catch:{ all -> 0x0f1f }
        r15 = r13.equals(r15);	 Catch:{ all -> 0x0f1f }
        if (r15 == 0) goto L_0x0581;
    L_0x0577:
        r6 = r14.zzuj();	 Catch:{ all -> 0x0f1f }
        r6 = (com.google.android.gms.internal.measurement.zzey.zza) r6;	 Catch:{ all -> 0x0f1f }
        r6 = (com.google.android.gms.internal.measurement.zzbs.zze.zza) r6;	 Catch:{ all -> 0x0f1f }
        r8 = r4;
        goto L_0x058c;
    L_0x0581:
        r14 = r14.getName();	 Catch:{ all -> 0x0f1f }
        r14 = r12.equals(r14);	 Catch:{ all -> 0x0f1f }
        if (r14 == 0) goto L_0x058c;
    L_0x058b:
        r5 = 1;
    L_0x058c:
        r4 = r4 + 1;
        goto L_0x0563;
    L_0x058f:
        if (r5 == 0) goto L_0x0597;
    L_0x0591:
        if (r6 == 0) goto L_0x0597;
    L_0x0593:
        r11.zzm(r8);	 Catch:{ all -> 0x0f1f }
        goto L_0x05d0;
    L_0x0597:
        if (r6 == 0) goto L_0x05b7;
    L_0x0599:
        r4 = r6.clone();	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzey.zza) r4;	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzbs.zze.zza) r4;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzbz(r12);	 Catch:{ all -> 0x0f1f }
        r5 = 10;
        r4 = r4.zzam(r5);	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzug();	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzey) r4;	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzbs.zze) r4;	 Catch:{ all -> 0x0f1f }
        r11.zza(r8, r4);	 Catch:{ all -> 0x0f1f }
        goto L_0x05d0;
    L_0x05b7:
        r4 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzab();	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzgk();	 Catch:{ all -> 0x0f1f }
        r5 = "Did not find conversion parameter. appId";
        r6 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r6 = r6.zzag();	 Catch:{ all -> 0x0f1f }
        r6 = com.google.android.gms.measurement.internal.zzef.zzam(r6);	 Catch:{ all -> 0x0f1f }
        r4.zza(r5, r6);	 Catch:{ all -> 0x0f1f }
    L_0x05d0:
        r4 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzad();	 Catch:{ all -> 0x0f1f }
        r5 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r5 = r5.zzag();	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzs(r5);	 Catch:{ all -> 0x0f1f }
        if (r4 == 0) goto L_0x06a9;
    L_0x05e2:
        if (r10 == 0) goto L_0x06a9;
    L_0x05e4:
        r4 = new java.util.ArrayList;	 Catch:{ all -> 0x0f1f }
        r5 = r11.zzmj();	 Catch:{ all -> 0x0f1f }
        r4.<init>(r5);	 Catch:{ all -> 0x0f1f }
        r5 = 0;
        r6 = -1;
        r8 = -1;
    L_0x05f0:
        r10 = r4.size();	 Catch:{ all -> 0x0f1f }
        if (r5 >= r10) goto L_0x0620;
    L_0x05f6:
        r10 = "value";
        r12 = r4.get(r5);	 Catch:{ all -> 0x0f1f }
        r12 = (com.google.android.gms.internal.measurement.zzbs.zze) r12;	 Catch:{ all -> 0x0f1f }
        r12 = r12.getName();	 Catch:{ all -> 0x0f1f }
        r10 = r10.equals(r12);	 Catch:{ all -> 0x0f1f }
        if (r10 == 0) goto L_0x060a;
    L_0x0608:
        r6 = r5;
        goto L_0x061d;
    L_0x060a:
        r10 = "currency";
        r12 = r4.get(r5);	 Catch:{ all -> 0x0f1f }
        r12 = (com.google.android.gms.internal.measurement.zzbs.zze) r12;	 Catch:{ all -> 0x0f1f }
        r12 = r12.getName();	 Catch:{ all -> 0x0f1f }
        r10 = r10.equals(r12);	 Catch:{ all -> 0x0f1f }
        if (r10 == 0) goto L_0x061d;
    L_0x061c:
        r8 = r5;
    L_0x061d:
        r5 = r5 + 1;
        goto L_0x05f0;
    L_0x0620:
        r5 = -1;
        if (r6 == r5) goto L_0x06aa;
    L_0x0623:
        r5 = r4.get(r6);	 Catch:{ all -> 0x0f1f }
        r5 = (com.google.android.gms.internal.measurement.zzbs.zze) r5;	 Catch:{ all -> 0x0f1f }
        r5 = r5.zzna();	 Catch:{ all -> 0x0f1f }
        if (r5 != 0) goto L_0x0658;
    L_0x062f:
        r5 = r4.get(r6);	 Catch:{ all -> 0x0f1f }
        r5 = (com.google.android.gms.internal.measurement.zzbs.zze) r5;	 Catch:{ all -> 0x0f1f }
        r5 = r5.zznd();	 Catch:{ all -> 0x0f1f }
        if (r5 != 0) goto L_0x0658;
    L_0x063b:
        r4 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzab();	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzgp();	 Catch:{ all -> 0x0f1f }
        r5 = "Value must be specified with a numeric type.";
        r4.zzao(r5);	 Catch:{ all -> 0x0f1f }
        r11.zzm(r6);	 Catch:{ all -> 0x0f1f }
        zza(r11, r13);	 Catch:{ all -> 0x0f1f }
        r4 = 18;
        r5 = "value";
        zza(r11, r4, r5);	 Catch:{ all -> 0x0f1f }
        goto L_0x06a9;
    L_0x0658:
        r5 = -1;
        if (r8 != r5) goto L_0x065e;
    L_0x065b:
        r4 = 1;
        r10 = 3;
        goto L_0x068a;
    L_0x065e:
        r4 = r4.get(r8);	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzbs.zze) r4;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzmy();	 Catch:{ all -> 0x0f1f }
        r8 = r4.length();	 Catch:{ all -> 0x0f1f }
        r10 = 3;
        if (r8 == r10) goto L_0x0671;
    L_0x066f:
        r4 = 1;
        goto L_0x068a;
    L_0x0671:
        r8 = 0;
    L_0x0672:
        r12 = r4.length();	 Catch:{ all -> 0x0f1f }
        if (r8 >= r12) goto L_0x0689;
    L_0x0678:
        r12 = r4.codePointAt(r8);	 Catch:{ all -> 0x0f1f }
        r14 = java.lang.Character.isLetter(r12);	 Catch:{ all -> 0x0f1f }
        if (r14 != 0) goto L_0x0683;
    L_0x0682:
        goto L_0x066f;
    L_0x0683:
        r12 = java.lang.Character.charCount(r12);	 Catch:{ all -> 0x0f1f }
        r8 = r8 + r12;
        goto L_0x0672;
    L_0x0689:
        r4 = 0;
    L_0x068a:
        if (r4 == 0) goto L_0x06ab;
    L_0x068c:
        r4 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzab();	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzgp();	 Catch:{ all -> 0x0f1f }
        r8 = "Value parameter discarded. You must also supply a 3-letter ISO_4217 currency code in the currency parameter.";
        r4.zzao(r8);	 Catch:{ all -> 0x0f1f }
        r11.zzm(r6);	 Catch:{ all -> 0x0f1f }
        zza(r11, r13);	 Catch:{ all -> 0x0f1f }
        r4 = 19;
        r6 = "currency";
        zza(r11, r4, r6);	 Catch:{ all -> 0x0f1f }
        goto L_0x06ab;
    L_0x06a9:
        r5 = -1;
    L_0x06aa:
        r10 = 3;
    L_0x06ab:
        r4 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzad();	 Catch:{ all -> 0x0f1f }
        r6 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r6 = r6.zzag();	 Catch:{ all -> 0x0f1f }
        r8 = com.google.android.gms.measurement.internal.zzak.zzih;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zze(r6, r8);	 Catch:{ all -> 0x0f1f }
        if (r4 == 0) goto L_0x0767;
    L_0x06bf:
        r4 = r11.getName();	 Catch:{ all -> 0x0f1f }
        r4 = r9.equals(r4);	 Catch:{ all -> 0x0f1f }
        if (r4 == 0) goto L_0x0715;
    L_0x06c9:
        r62.zzgw();	 Catch:{ all -> 0x0f1f }
        r4 = r11.zzug();	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzey) r4;	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzbs.zzc) r4;	 Catch:{ all -> 0x0f1f }
        r6 = "_fr";
        r4 = com.google.android.gms.measurement.internal.zzjo.zza(r4, r6);	 Catch:{ all -> 0x0f1f }
        if (r4 != 0) goto L_0x0712;
    L_0x06dc:
        if (r19 == 0) goto L_0x070b;
    L_0x06de:
        r12 = r19.getTimestampMillis();	 Catch:{ all -> 0x0f1f }
        r14 = r11.getTimestampMillis();	 Catch:{ all -> 0x0f1f }
        r12 = r12 - r14;
        r12 = java.lang.Math.abs(r12);	 Catch:{ all -> 0x0f1f }
        r14 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r4 > 0) goto L_0x070b;
    L_0x06f1:
        r4 = r19.clone();	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzey.zza) r4;	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzbs.zzc.zza) r4;	 Catch:{ all -> 0x0f1f }
        r6 = r1.zza(r11, r4);	 Catch:{ all -> 0x0f1f }
        if (r6 == 0) goto L_0x070b;
    L_0x06ff:
        r6 = r33;
        r3.zza(r6, r4);	 Catch:{ all -> 0x0f1f }
        r8 = r32;
    L_0x0706:
        r18 = 0;
        r19 = 0;
        goto L_0x076b;
    L_0x070b:
        r6 = r33;
        r18 = r11;
        r8 = r25;
        goto L_0x076b;
    L_0x0712:
        r6 = r33;
        goto L_0x0764;
    L_0x0715:
        r6 = r33;
        r4 = "_vs";
        r8 = r11.getName();	 Catch:{ all -> 0x0f1f }
        r4 = r4.equals(r8);	 Catch:{ all -> 0x0f1f }
        if (r4 == 0) goto L_0x0764;
    L_0x0723:
        r62.zzgw();	 Catch:{ all -> 0x0f1f }
        r4 = r11.zzug();	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzey) r4;	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzbs.zzc) r4;	 Catch:{ all -> 0x0f1f }
        r4 = com.google.android.gms.measurement.internal.zzjo.zza(r4, r7);	 Catch:{ all -> 0x0f1f }
        if (r4 != 0) goto L_0x0764;
    L_0x0734:
        if (r18 == 0) goto L_0x075d;
    L_0x0736:
        r12 = r18.getTimestampMillis();	 Catch:{ all -> 0x0f1f }
        r14 = r11.getTimestampMillis();	 Catch:{ all -> 0x0f1f }
        r12 = r12 - r14;
        r12 = java.lang.Math.abs(r12);	 Catch:{ all -> 0x0f1f }
        r14 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r4 > 0) goto L_0x075d;
    L_0x0749:
        r4 = r18.clone();	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzey.zza) r4;	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzbs.zzc.zza) r4;	 Catch:{ all -> 0x0f1f }
        r8 = r1.zza(r4, r11);	 Catch:{ all -> 0x0f1f }
        if (r8 == 0) goto L_0x075d;
    L_0x0757:
        r8 = r32;
        r3.zza(r8, r4);	 Catch:{ all -> 0x0f1f }
        goto L_0x0706;
    L_0x075d:
        r8 = r32;
        r19 = r11;
        r6 = r25;
        goto L_0x076b;
    L_0x0764:
        r8 = r32;
        goto L_0x076b;
    L_0x0767:
        r8 = r32;
        r6 = r33;
    L_0x076b:
        if (r31 != 0) goto L_0x07cb;
    L_0x076d:
        r4 = r11.getName();	 Catch:{ all -> 0x0f1f }
        r4 = r9.equals(r4);	 Catch:{ all -> 0x0f1f }
        if (r4 == 0) goto L_0x07cb;
    L_0x0777:
        r4 = r11.zzmk();	 Catch:{ all -> 0x0f1f }
        if (r4 != 0) goto L_0x0797;
    L_0x077d:
        r4 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzab();	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzgn();	 Catch:{ all -> 0x0f1f }
        r7 = "Engagement event does not contain any parameters. appId";
        r9 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r9 = r9.zzag();	 Catch:{ all -> 0x0f1f }
        r9 = com.google.android.gms.measurement.internal.zzef.zzam(r9);	 Catch:{ all -> 0x0f1f }
        r4.zza(r7, r9);	 Catch:{ all -> 0x0f1f }
        goto L_0x07cb;
    L_0x0797:
        r62.zzgw();	 Catch:{ all -> 0x0f1f }
        r4 = r11.zzug();	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzey) r4;	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzbs.zzc) r4;	 Catch:{ all -> 0x0f1f }
        r4 = com.google.android.gms.measurement.internal.zzjo.zzb(r4, r7);	 Catch:{ all -> 0x0f1f }
        r4 = (java.lang.Long) r4;	 Catch:{ all -> 0x0f1f }
        if (r4 != 0) goto L_0x07c4;
    L_0x07aa:
        r4 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzab();	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzgn();	 Catch:{ all -> 0x0f1f }
        r7 = "Engagement event does not include duration. appId";
        r9 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r9 = r9.zzag();	 Catch:{ all -> 0x0f1f }
        r9 = com.google.android.gms.measurement.internal.zzef.zzam(r9);	 Catch:{ all -> 0x0f1f }
        r4.zza(r7, r9);	 Catch:{ all -> 0x0f1f }
        goto L_0x07cb;
    L_0x07c4:
        r12 = r4.longValue();	 Catch:{ all -> 0x0f1f }
        r13 = r26 + r12;
        goto L_0x07cd;
    L_0x07cb:
        r13 = r26;
    L_0x07cd:
        r4 = r2.zztp;	 Catch:{ all -> 0x0f1f }
        r7 = r11.zzug();	 Catch:{ all -> 0x0f1f }
        r7 = (com.google.android.gms.internal.measurement.zzey) r7;	 Catch:{ all -> 0x0f1f }
        r7 = (com.google.android.gms.internal.measurement.zzbs.zzc) r7;	 Catch:{ all -> 0x0f1f }
        r9 = r30;
        r4.set(r9, r7);	 Catch:{ all -> 0x0f1f }
        r15 = r25 + 1;
        r3.zza(r11);	 Catch:{ all -> 0x0f1f }
        r12 = r21;
    L_0x07e3:
        r7 = r9 + 1;
        r9 = r6;
        r4 = r31;
        goto L_0x02cb;
    L_0x07ea:
        r31 = r4;
        r7 = r5;
        r9 = r6;
        r25 = r15;
        if (r31 == 0) goto L_0x0847;
    L_0x07f2:
        r5 = r25;
        r13 = r26;
        r4 = 0;
    L_0x07f7:
        if (r4 >= r5) goto L_0x0849;
    L_0x07f9:
        r6 = r3.zzq(r4);	 Catch:{ all -> 0x0f1f }
        r8 = r6.getName();	 Catch:{ all -> 0x0f1f }
        r8 = r9.equals(r8);	 Catch:{ all -> 0x0f1f }
        if (r8 == 0) goto L_0x081a;
    L_0x0807:
        r62.zzgw();	 Catch:{ all -> 0x0f1f }
        r8 = "_fr";
        r8 = com.google.android.gms.measurement.internal.zzjo.zza(r6, r8);	 Catch:{ all -> 0x0f1f }
        if (r8 == 0) goto L_0x081a;
    L_0x0812:
        r3.zzr(r4);	 Catch:{ all -> 0x0f1f }
        r5 = r5 + -1;
        r4 = r4 + -1;
        goto L_0x0844;
    L_0x081a:
        r62.zzgw();	 Catch:{ all -> 0x0f1f }
        r6 = com.google.android.gms.measurement.internal.zzjo.zza(r6, r7);	 Catch:{ all -> 0x0f1f }
        if (r6 == 0) goto L_0x0844;
    L_0x0823:
        r8 = r6.zzna();	 Catch:{ all -> 0x0f1f }
        if (r8 == 0) goto L_0x0832;
    L_0x0829:
        r10 = r6.zznb();	 Catch:{ all -> 0x0f1f }
        r6 = java.lang.Long.valueOf(r10);	 Catch:{ all -> 0x0f1f }
        goto L_0x0833;
    L_0x0832:
        r6 = 0;
    L_0x0833:
        if (r6 == 0) goto L_0x0844;
    L_0x0835:
        r10 = r6.longValue();	 Catch:{ all -> 0x0f1f }
        r18 = 0;
        r8 = (r10 > r18 ? 1 : (r10 == r18 ? 0 : -1));
        if (r8 <= 0) goto L_0x0844;
    L_0x083f:
        r10 = r6.longValue();	 Catch:{ all -> 0x0f1f }
        r13 = r13 + r10;
    L_0x0844:
        r6 = 1;
        r4 = r4 + r6;
        goto L_0x07f7;
    L_0x0847:
        r13 = r26;
    L_0x0849:
        r4 = 0;
        r1.zza(r3, r13, r4);	 Catch:{ all -> 0x0f1f }
        r4 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzad();	 Catch:{ all -> 0x0f1f }
        r5 = r3.zzag();	 Catch:{ all -> 0x0f1f }
        r6 = com.google.android.gms.measurement.internal.zzak.zzja;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zze(r5, r6);	 Catch:{ all -> 0x0f1f }
        if (r4 == 0) goto L_0x0896;
    L_0x085f:
        r4 = r3.zznl();	 Catch:{ all -> 0x0f1f }
        r4 = r4.iterator();	 Catch:{ all -> 0x0f1f }
    L_0x0867:
        r5 = r4.hasNext();	 Catch:{ all -> 0x0f1f }
        if (r5 == 0) goto L_0x0881;
    L_0x086d:
        r5 = r4.next();	 Catch:{ all -> 0x0f1f }
        r5 = (com.google.android.gms.internal.measurement.zzbs.zzc) r5;	 Catch:{ all -> 0x0f1f }
        r6 = "_s";
        r5 = r5.getName();	 Catch:{ all -> 0x0f1f }
        r5 = r6.equals(r5);	 Catch:{ all -> 0x0f1f }
        if (r5 == 0) goto L_0x0867;
    L_0x087f:
        r4 = 1;
        goto L_0x0882;
    L_0x0881:
        r4 = 0;
    L_0x0882:
        if (r4 == 0) goto L_0x0891;
    L_0x0884:
        r4 = r62.zzgy();	 Catch:{ all -> 0x0f1f }
        r5 = r3.zzag();	 Catch:{ all -> 0x0f1f }
        r6 = "_se";
        r4.zzd(r5, r6);	 Catch:{ all -> 0x0f1f }
    L_0x0891:
        r4 = 1;
        r1.zza(r3, r13, r4);	 Catch:{ all -> 0x0f1f }
        goto L_0x08b5;
    L_0x0896:
        r4 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzad();	 Catch:{ all -> 0x0f1f }
        r5 = r3.zzag();	 Catch:{ all -> 0x0f1f }
        r6 = com.google.android.gms.measurement.internal.zzak.zzjb;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zze(r5, r6);	 Catch:{ all -> 0x0f1f }
        if (r4 == 0) goto L_0x08b5;
    L_0x08a8:
        r4 = r62.zzgy();	 Catch:{ all -> 0x0f1f }
        r5 = r3.zzag();	 Catch:{ all -> 0x0f1f }
        r6 = "_se";
        r4.zzd(r5, r6);	 Catch:{ all -> 0x0f1f }
    L_0x08b5:
        r4 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzad();	 Catch:{ all -> 0x0f1f }
        r5 = r3.zzag();	 Catch:{ all -> 0x0f1f }
        r6 = com.google.android.gms.measurement.internal.zzak.zzij;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zze(r5, r6);	 Catch:{ all -> 0x0f1f }
        if (r4 == 0) goto L_0x095a;
    L_0x08c7:
        r4 = r62.zzgw();	 Catch:{ all -> 0x0f1f }
        r5 = r4.zzab();	 Catch:{ all -> 0x0f1f }
        r5 = r5.zzgs();	 Catch:{ all -> 0x0f1f }
        r6 = "Checking account type status for ad personalization signals";
        r5.zzao(r6);	 Catch:{ all -> 0x0f1f }
        r5 = r4.zzgz();	 Catch:{ all -> 0x0f1f }
        r6 = r3.zzag();	 Catch:{ all -> 0x0f1f }
        r5 = r5.zzba(r6);	 Catch:{ all -> 0x0f1f }
        if (r5 == 0) goto L_0x095a;
    L_0x08e6:
        r5 = r4.zzgy();	 Catch:{ all -> 0x0f1f }
        r6 = r3.zzag();	 Catch:{ all -> 0x0f1f }
        r5 = r5.zzab(r6);	 Catch:{ all -> 0x0f1f }
        if (r5 == 0) goto L_0x095a;
    L_0x08f4:
        r5 = r5.zzbe();	 Catch:{ all -> 0x0f1f }
        if (r5 == 0) goto L_0x095a;
    L_0x08fa:
        r5 = r4.zzw();	 Catch:{ all -> 0x0f1f }
        r5 = r5.zzcu();	 Catch:{ all -> 0x0f1f }
        if (r5 == 0) goto L_0x095a;
    L_0x0904:
        r5 = r4.zzab();	 Catch:{ all -> 0x0f1f }
        r5 = r5.zzgr();	 Catch:{ all -> 0x0f1f }
        r6 = "Turning off ad personalization due to account type";
        r5.zzao(r6);	 Catch:{ all -> 0x0f1f }
        r5 = com.google.android.gms.internal.measurement.zzbs.zzk.zzqu();	 Catch:{ all -> 0x0f1f }
        r6 = "_npa";
        r5 = r5.zzdb(r6);	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzw();	 Catch:{ all -> 0x0f1f }
        r6 = r4.zzcs();	 Catch:{ all -> 0x0f1f }
        r4 = r5.zzbk(r6);	 Catch:{ all -> 0x0f1f }
        r5 = 1;
        r4 = r4.zzbl(r5);	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzug();	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzey) r4;	 Catch:{ all -> 0x0f1f }
        r4 = (com.google.android.gms.internal.measurement.zzbs.zzk) r4;	 Catch:{ all -> 0x0f1f }
        r5 = 0;
    L_0x0936:
        r6 = r3.zznp();	 Catch:{ all -> 0x0f1f }
        if (r5 >= r6) goto L_0x0954;
    L_0x093c:
        r6 = "_npa";
        r7 = r3.zzs(r5);	 Catch:{ all -> 0x0f1f }
        r7 = r7.getName();	 Catch:{ all -> 0x0f1f }
        r6 = r6.equals(r7);	 Catch:{ all -> 0x0f1f }
        if (r6 == 0) goto L_0x0951;
    L_0x094c:
        r3.zza(r5, r4);	 Catch:{ all -> 0x0f1f }
        r5 = 1;
        goto L_0x0955;
    L_0x0951:
        r5 = r5 + 1;
        goto L_0x0936;
    L_0x0954:
        r5 = 0;
    L_0x0955:
        if (r5 != 0) goto L_0x095a;
    L_0x0957:
        r3.zza(r4);	 Catch:{ all -> 0x0f1f }
    L_0x095a:
        r4 = r3.zznv();	 Catch:{ all -> 0x0f1f }
        r5 = r3.zzag();	 Catch:{ all -> 0x0f1f }
        r6 = r3.zzno();	 Catch:{ all -> 0x0f1f }
        r7 = r3.zznl();	 Catch:{ all -> 0x0f1f }
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r5);	 Catch:{ all -> 0x0f1f }
        r8 = r62.zzgx();	 Catch:{ all -> 0x0f1f }
        r5 = r8.zza(r5, r7, r6);	 Catch:{ all -> 0x0f1f }
        r4.zzc(r5);	 Catch:{ all -> 0x0f1f }
        r4 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzad();	 Catch:{ all -> 0x0f1f }
        r5 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r5 = r5.zzag();	 Catch:{ all -> 0x0f1f }
        r4 = r4.zzm(r5);	 Catch:{ all -> 0x0f1f }
        if (r4 == 0) goto L_0x0d29;
    L_0x098a:
        r4 = new java.util.HashMap;	 Catch:{ all -> 0x0efd }
        r4.<init>();	 Catch:{ all -> 0x0efd }
        r5 = new java.util.ArrayList;	 Catch:{ all -> 0x0efd }
        r5.<init>();	 Catch:{ all -> 0x0efd }
        r6 = r1.zzj;	 Catch:{ all -> 0x0efd }
        r6 = r6.zzz();	 Catch:{ all -> 0x0efd }
        r6 = r6.zzjw();	 Catch:{ all -> 0x0efd }
        r7 = 0;
    L_0x099f:
        r8 = r3.zznm();	 Catch:{ all -> 0x0efd }
        if (r7 >= r8) goto L_0x0cf4;
    L_0x09a5:
        r8 = r3.zzq(r7);	 Catch:{ all -> 0x0efd }
        r8 = r8.zzuj();	 Catch:{ all -> 0x0efd }
        r8 = (com.google.android.gms.internal.measurement.zzey.zza) r8;	 Catch:{ all -> 0x0efd }
        r8 = (com.google.android.gms.internal.measurement.zzbs.zzc.zza) r8;	 Catch:{ all -> 0x0efd }
        r9 = r8.getName();	 Catch:{ all -> 0x0efd }
        r10 = "_ep";
        r9 = r9.equals(r10);	 Catch:{ all -> 0x0efd }
        r10 = "_sr";
        if (r9 == 0) goto L_0x0a34;
    L_0x09bf:
        r62.zzgw();	 Catch:{ all -> 0x0f1f }
        r9 = r8.zzug();	 Catch:{ all -> 0x0f1f }
        r9 = (com.google.android.gms.internal.measurement.zzey) r9;	 Catch:{ all -> 0x0f1f }
        r9 = (com.google.android.gms.internal.measurement.zzbs.zzc) r9;	 Catch:{ all -> 0x0f1f }
        r11 = "_en";
        r9 = com.google.android.gms.measurement.internal.zzjo.zzb(r9, r11);	 Catch:{ all -> 0x0f1f }
        r9 = (java.lang.String) r9;	 Catch:{ all -> 0x0f1f }
        r11 = r4.get(r9);	 Catch:{ all -> 0x0f1f }
        r11 = (com.google.android.gms.measurement.internal.zzae) r11;	 Catch:{ all -> 0x0f1f }
        if (r11 != 0) goto L_0x09eb;
    L_0x09da:
        r11 = r62.zzgy();	 Catch:{ all -> 0x0f1f }
        r12 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r12 = r12.zzag();	 Catch:{ all -> 0x0f1f }
        r11 = r11.zzc(r12, r9);	 Catch:{ all -> 0x0f1f }
        r4.put(r9, r11);	 Catch:{ all -> 0x0f1f }
    L_0x09eb:
        r9 = r11.zzfm;	 Catch:{ all -> 0x0f1f }
        if (r9 != 0) goto L_0x0a28;
    L_0x09ef:
        r9 = r11.zzfn;	 Catch:{ all -> 0x0f1f }
        r12 = r9.longValue();	 Catch:{ all -> 0x0f1f }
        r14 = 1;
        r9 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r9 <= 0) goto L_0x0a03;
    L_0x09fb:
        r62.zzgw();	 Catch:{ all -> 0x0f1f }
        r9 = r11.zzfn;	 Catch:{ all -> 0x0f1f }
        com.google.android.gms.measurement.internal.zzjo.zza(r8, r10, r9);	 Catch:{ all -> 0x0f1f }
    L_0x0a03:
        r9 = r11.zzfo;	 Catch:{ all -> 0x0f1f }
        if (r9 == 0) goto L_0x0a1d;
    L_0x0a07:
        r9 = r11.zzfo;	 Catch:{ all -> 0x0f1f }
        r9 = r9.booleanValue();	 Catch:{ all -> 0x0f1f }
        if (r9 == 0) goto L_0x0a1d;
    L_0x0a0f:
        r62.zzgw();	 Catch:{ all -> 0x0f1f }
        r9 = "_efs";
        r10 = 1;
        r12 = java.lang.Long.valueOf(r10);	 Catch:{ all -> 0x0f1f }
        com.google.android.gms.measurement.internal.zzjo.zza(r8, r9, r12);	 Catch:{ all -> 0x0f1f }
    L_0x0a1d:
        r9 = r8.zzug();	 Catch:{ all -> 0x0f1f }
        r9 = (com.google.android.gms.internal.measurement.zzey) r9;	 Catch:{ all -> 0x0f1f }
        r9 = (com.google.android.gms.internal.measurement.zzbs.zzc) r9;	 Catch:{ all -> 0x0f1f }
        r5.add(r9);	 Catch:{ all -> 0x0f1f }
    L_0x0a28:
        r3.zza(r7, r8);	 Catch:{ all -> 0x0f1f }
    L_0x0a2b:
        r1 = r3;
        r63 = r6;
        r3 = r2;
        r2 = r7;
        r6 = 1;
        goto L_0x0ce9;
    L_0x0a34:
        r9 = r62.zzgz();	 Catch:{ all -> 0x0efd }
        r11 = r2.zztn;	 Catch:{ all -> 0x0efd }
        r11 = r11.zzag();	 Catch:{ all -> 0x0efd }
        r11 = r9.zzbb(r11);	 Catch:{ all -> 0x0efd }
        r9 = r1.zzj;	 Catch:{ all -> 0x0efd }
        r9.zzz();	 Catch:{ all -> 0x0efd }
        r13 = r8.getTimestampMillis();	 Catch:{ all -> 0x0efd }
        r13 = com.google.android.gms.measurement.internal.zzjs.zzc(r13, r11);	 Catch:{ all -> 0x0efd }
        r9 = r8.zzug();	 Catch:{ all -> 0x0efd }
        r9 = (com.google.android.gms.internal.measurement.zzey) r9;	 Catch:{ all -> 0x0efd }
        r9 = (com.google.android.gms.internal.measurement.zzbs.zzc) r9;	 Catch:{ all -> 0x0efd }
        r15 = "_dbg";
        r26 = r11;
        r18 = 1;
        r11 = java.lang.Long.valueOf(r18);	 Catch:{ all -> 0x0efd }
        r12 = android.text.TextUtils.isEmpty(r15);	 Catch:{ all -> 0x0efd }
        if (r12 != 0) goto L_0x0ac1;
    L_0x0a67:
        if (r11 != 0) goto L_0x0a6a;
    L_0x0a69:
        goto L_0x0ac1;
    L_0x0a6a:
        r9 = r9.zzmj();	 Catch:{ all -> 0x0f1f }
        r9 = r9.iterator();	 Catch:{ all -> 0x0f1f }
    L_0x0a72:
        r12 = r9.hasNext();	 Catch:{ all -> 0x0f1f }
        if (r12 == 0) goto L_0x0ac1;
    L_0x0a78:
        r12 = r9.next();	 Catch:{ all -> 0x0f1f }
        r12 = (com.google.android.gms.internal.measurement.zzbs.zze) r12;	 Catch:{ all -> 0x0f1f }
        r63 = r9;
        r9 = r12.getName();	 Catch:{ all -> 0x0f1f }
        r9 = r15.equals(r9);	 Catch:{ all -> 0x0f1f }
        if (r9 == 0) goto L_0x0abe;
    L_0x0a8a:
        r9 = r11 instanceof java.lang.Long;	 Catch:{ all -> 0x0f1f }
        if (r9 == 0) goto L_0x0a9c;
    L_0x0a8e:
        r18 = r12.zznb();	 Catch:{ all -> 0x0f1f }
        r9 = java.lang.Long.valueOf(r18);	 Catch:{ all -> 0x0f1f }
        r9 = r11.equals(r9);	 Catch:{ all -> 0x0f1f }
        if (r9 != 0) goto L_0x0abc;
    L_0x0a9c:
        r9 = r11 instanceof java.lang.String;	 Catch:{ all -> 0x0f1f }
        if (r9 == 0) goto L_0x0aaa;
    L_0x0aa0:
        r9 = r12.zzmy();	 Catch:{ all -> 0x0f1f }
        r9 = r11.equals(r9);	 Catch:{ all -> 0x0f1f }
        if (r9 != 0) goto L_0x0abc;
    L_0x0aaa:
        r9 = r11 instanceof java.lang.Double;	 Catch:{ all -> 0x0f1f }
        if (r9 == 0) goto L_0x0ac1;
    L_0x0aae:
        r18 = r12.zzne();	 Catch:{ all -> 0x0f1f }
        r9 = java.lang.Double.valueOf(r18);	 Catch:{ all -> 0x0f1f }
        r9 = r11.equals(r9);	 Catch:{ all -> 0x0f1f }
        if (r9 == 0) goto L_0x0ac1;
    L_0x0abc:
        r9 = 1;
        goto L_0x0ac2;
    L_0x0abe:
        r9 = r63;
        goto L_0x0a72;
    L_0x0ac1:
        r9 = 0;
    L_0x0ac2:
        if (r9 != 0) goto L_0x0ad7;
    L_0x0ac4:
        r9 = r62.zzgz();	 Catch:{ all -> 0x0f1f }
        r11 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r11 = r11.zzag();	 Catch:{ all -> 0x0f1f }
        r12 = r8.getName();	 Catch:{ all -> 0x0f1f }
        r12 = r9.zzm(r11, r12);	 Catch:{ all -> 0x0f1f }
        goto L_0x0ad8;
    L_0x0ad7:
        r12 = 1;
    L_0x0ad8:
        if (r12 > 0) goto L_0x0b01;
    L_0x0ada:
        r9 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r9 = r9.zzab();	 Catch:{ all -> 0x0f1f }
        r9 = r9.zzgn();	 Catch:{ all -> 0x0f1f }
        r10 = "Sample rate must be positive. event, rate";
        r11 = r8.getName();	 Catch:{ all -> 0x0f1f }
        r12 = java.lang.Integer.valueOf(r12);	 Catch:{ all -> 0x0f1f }
        r9.zza(r10, r11, r12);	 Catch:{ all -> 0x0f1f }
        r9 = r8.zzug();	 Catch:{ all -> 0x0f1f }
        r9 = (com.google.android.gms.internal.measurement.zzey) r9;	 Catch:{ all -> 0x0f1f }
        r9 = (com.google.android.gms.internal.measurement.zzbs.zzc) r9;	 Catch:{ all -> 0x0f1f }
        r5.add(r9);	 Catch:{ all -> 0x0f1f }
        r3.zza(r7, r8);	 Catch:{ all -> 0x0f1f }
        goto L_0x0a2b;
    L_0x0b01:
        r9 = r8.getName();	 Catch:{ all -> 0x0efd }
        r9 = r4.get(r9);	 Catch:{ all -> 0x0efd }
        r9 = (com.google.android.gms.measurement.internal.zzae) r9;	 Catch:{ all -> 0x0efd }
        if (r9 != 0) goto L_0x0b9a;
    L_0x0b0d:
        r9 = r62.zzgy();	 Catch:{ all -> 0x0f1f }
        r11 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r11 = r11.zzag();	 Catch:{ all -> 0x0f1f }
        r15 = r8.getName();	 Catch:{ all -> 0x0f1f }
        r9 = r9.zzc(r11, r15);	 Catch:{ all -> 0x0f1f }
        if (r9 != 0) goto L_0x0b9a;
    L_0x0b21:
        r9 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r9 = r9.zzab();	 Catch:{ all -> 0x0f1f }
        r9 = r9.zzgn();	 Catch:{ all -> 0x0f1f }
        r11 = "Event being bundled has no eventAggregate. appId, eventName";
        r15 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r15 = r15.zzag();	 Catch:{ all -> 0x0f1f }
        r18 = r13;
        r13 = r8.getName();	 Catch:{ all -> 0x0f1f }
        r9.zza(r11, r15, r13);	 Catch:{ all -> 0x0f1f }
        r9 = r1.zzj;	 Catch:{ all -> 0x0f1f }
        r9 = r9.zzad();	 Catch:{ all -> 0x0f1f }
        r11 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r11 = r11.zzag();	 Catch:{ all -> 0x0f1f }
        r13 = com.google.android.gms.measurement.internal.zzak.zziz;	 Catch:{ all -> 0x0f1f }
        r9 = r9.zze(r11, r13);	 Catch:{ all -> 0x0f1f }
        if (r9 == 0) goto L_0x0b76;
    L_0x0b50:
        r9 = new com.google.android.gms.measurement.internal.zzae;	 Catch:{ all -> 0x0f1f }
        r11 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r31 = r11.zzag();	 Catch:{ all -> 0x0f1f }
        r32 = r8.getName();	 Catch:{ all -> 0x0f1f }
        r33 = 1;
        r35 = 1;
        r37 = 1;
        r39 = r8.getTimestampMillis();	 Catch:{ all -> 0x0f1f }
        r41 = 0;
        r43 = 0;
        r44 = 0;
        r45 = 0;
        r46 = 0;
        r30 = r9;
        r30.<init>(r31, r32, r33, r35, r37, r39, r41, r43, r44, r45, r46);	 Catch:{ all -> 0x0f1f }
        goto L_0x0b9c;
    L_0x0b76:
        r9 = new com.google.android.gms.measurement.internal.zzae;	 Catch:{ all -> 0x0f1f }
        r11 = r2.zztn;	 Catch:{ all -> 0x0f1f }
        r48 = r11.zzag();	 Catch:{ all -> 0x0f1f }
        r49 = r8.getName();	 Catch:{ all -> 0x0f1f }
        r50 = 1;
        r52 = 1;
        r54 = r8.getTimestampMillis();	 Catch:{ all -> 0x0f1f }
        r56 = 0;
        r58 = 0;
        r59 = 0;
        r60 = 0;
        r61 = 0;
        r47 = r9;
        r47.<init>(r48, r49, r50, r52, r54, r56, r58, r59, r60, r61);	 Catch:{ all -> 0x0f1f }
        goto L_0x0b9c;
    L_0x0b9a:
        r18 = r13;
    L_0x0b9c:
        r62.zzgw();	 Catch:{ all -> 0x0efd }
        r11 = r8.zzug();	 Catch:{ all -> 0x0efd }
        r11 = (com.google.android.gms.internal.measurement.zzey) r11;	 Catch:{ all -> 0x0efd }
        r11 = (com.google.android.gms.internal.measurement.zzbs.zzc) r11;	 Catch:{ all -> 0x0efd }
        r13 = "_eid";
        r11 = com.google.android.gms.measurement.internal.zzjo.zzb(r11, r13);	 Catch:{ all -> 0x0efd }
        r11 = (java.lang.Long) r11;	 Catch:{ all -> 0x0efd }
        if (r11 == 0) goto L_0x0bb3;
    L_0x0bb1:
        r13 = 1;
        goto L_0x0bb4;
    L_0x0bb3:
        r13 = 0;
    L_0x0bb4:
        r13 = java.lang.Boolean.valueOf(r13);	 Catch:{ all -> 0x0efd }
        r14 = 1;
        if (r12 != r14) goto L_0x0be9;
    L_0x0bbb:
        r10 = r8.zzug();	 Catch:{ all -> 0x0f1f }
        r10 = (com.google.android.gms.internal.measurement.zzey) r10;	 Catch:{ all -> 0x0f1f }
        r10 = (com.google.android.gms.internal.measurement.zzbs.zzc) r10;	 Catch:{ all -> 0x0f1f }
        r5.add(r10);	 Catch:{ all -> 0x0f1f }
        r10 = r13.booleanValue();	 Catch:{ all -> 0x0f1f }
        if (r10 == 0) goto L_0x0be4;
    L_0x0bcc:
        r10 = r9.zzfm;	 Catch:{ all -> 0x0f1f }
        if (r10 != 0) goto L_0x0bd8;
    L_0x0bd0:
        r10 = r9.zzfn;	 Catch:{ all -> 0x0f1f }
        if (r10 != 0) goto L_0x0bd8;
    L_0x0bd4:
        r10 = r9.zzfo;	 Catch:{ all -> 0x0f1f }
        if (r10 == 0) goto L_0x0be4;
    L_0x0bd8:
        r10 = 0;
        r9 = r9.zza(r10, r10, r10);	 Catch:{ all -> 0x0f1f }
        r10 = r8.getName();	 Catch:{ all -> 0x0f1f }
        r4.put(r10, r9);	 Catch:{ all -> 0x0f1f }
    L_0x0be4:
        r3.zza(r7, r8);	 Catch:{ all -> 0x0f1f }
        goto L_0x0a2b;
    L_0x0be9:
        r14 = r6.nextInt(r12);	 Catch:{ all -> 0x0efd }
        if (r14 != 0) goto L_0x0c2e;
    L_0x0bef:
        r62.zzgw();	 Catch:{ all -> 0x0f1f }
        r11 = (long) r12;	 Catch:{ all -> 0x0f1f }
        r14 = java.lang.Long.valueOf(r11);	 Catch:{ all -> 0x0f1f }
        com.google.android.gms.measurement.internal.zzjo.zza(r8, r10, r14);	 Catch:{ all -> 0x0f1f }
        r10 = r8.zzug();	 Catch:{ all -> 0x0f1f }
        r10 = (com.google.android.gms.internal.measurement.zzey) r10;	 Catch:{ all -> 0x0f1f }
        r10 = (com.google.android.gms.internal.measurement.zzbs.zzc) r10;	 Catch:{ all -> 0x0f1f }
        r5.add(r10);	 Catch:{ all -> 0x0f1f }
        r10 = r13.booleanValue();	 Catch:{ all -> 0x0f1f }
        if (r10 == 0) goto L_0x0c14;
    L_0x0c0b:
        r10 = java.lang.Long.valueOf(r11);	 Catch:{ all -> 0x0f1f }
        r11 = 0;
        r9 = r9.zza(r11, r10, r11);	 Catch:{ all -> 0x0f1f }
    L_0x0c14:
        r10 = r8.getName();	 Catch:{ all -> 0x0f1f }
        r11 = r8.getTimestampMillis();	 Catch:{ all -> 0x0f1f }
        r14 = r18;
        r9 = r9.zza(r11, r14);	 Catch:{ all -> 0x0f1f }
        r4.put(r10, r9);	 Catch:{ all -> 0x0f1f }
        r1 = r3;
        r63 = r6;
        r3 = r2;
        r2 = r7;
        r6 = 1;
        goto L_0x0ce6;
    L_0x0c2e:
        r63 = r6;
        r14 = r18;
        r6 = r1.zzj;	 Catch:{ all -> 0x0efd }
        r6 = r6.zzad();	 Catch:{ all -> 0x0efd }
        r18 = r3;
        r3 = r2.zztn;	 Catch:{ all -> 0x0efd }
        r3 = r3.zzag();	 Catch:{ all -> 0x0efd }
        r3 = r6.zzu(r3);	 Catch:{ all -> 0x0efd }
        if (r3 == 0) goto L_0x0c6e;
    L_0x0c46:
        r3 = r9.zzfl;	 Catch:{ all -> 0x0efd }
        if (r3 == 0) goto L_0x0c54;
    L_0x0c4a:
        r3 = r9.zzfl;	 Catch:{ all -> 0x0f1f }
        r26 = r3.longValue();	 Catch:{ all -> 0x0f1f }
        r3 = r2;
        r19 = r7;
        goto L_0x0c66;
    L_0x0c54:
        r3 = r1.zzj;	 Catch:{ all -> 0x0efd }
        r3.zzz();	 Catch:{ all -> 0x0efd }
        r3 = r2;
        r1 = r8.zzmm();	 Catch:{ all -> 0x0efd }
        r19 = r7;
        r6 = r26;
        r26 = com.google.android.gms.measurement.internal.zzjs.zzc(r1, r6);	 Catch:{ all -> 0x0efd }
    L_0x0c66:
        r1 = (r26 > r14 ? 1 : (r26 == r14 ? 0 : -1));
        if (r1 == 0) goto L_0x0c6c;
    L_0x0c6a:
        r1 = 1;
        goto L_0x0c84;
    L_0x0c6c:
        r1 = 0;
        goto L_0x0c84;
    L_0x0c6e:
        r3 = r2;
        r19 = r7;
        r1 = r9.zzfk;	 Catch:{ all -> 0x0efd }
        r6 = r8.getTimestampMillis();	 Catch:{ all -> 0x0efd }
        r6 = r6 - r1;
        r1 = java.lang.Math.abs(r6);	 Catch:{ all -> 0x0efd }
        r6 = 86400000; // 0x5265c00 float:7.82218E-36 double:4.2687272E-316;
        r23 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1));
        if (r23 < 0) goto L_0x0c6c;
    L_0x0c83:
        goto L_0x0c6a;
    L_0x0c84:
        if (r1 == 0) goto L_0x0cce;
    L_0x0c86:
        r62.zzgw();	 Catch:{ all -> 0x0efd }
        r1 = "_efs";
        r6 = 1;
        r2 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x0efd }
        com.google.android.gms.measurement.internal.zzjo.zza(r8, r1, r2);	 Catch:{ all -> 0x0efd }
        r62.zzgw();	 Catch:{ all -> 0x0efd }
        r1 = (long) r12;	 Catch:{ all -> 0x0efd }
        r11 = java.lang.Long.valueOf(r1);	 Catch:{ all -> 0x0efd }
        com.google.android.gms.measurement.internal.zzjo.zza(r8, r10, r11);	 Catch:{ all -> 0x0efd }
        r10 = r8.zzug();	 Catch:{ all -> 0x0efd }
        r10 = (com.google.android.gms.internal.measurement.zzey) r10;	 Catch:{ all -> 0x0efd }
        r10 = (com.google.android.gms.internal.measurement.zzbs.zzc) r10;	 Catch:{ all -> 0x0efd }
        r5.add(r10);	 Catch:{ all -> 0x0efd }
        r10 = r13.booleanValue();	 Catch:{ all -> 0x0efd }
        if (r10 == 0) goto L_0x0cbe;
    L_0x0cb0:
        r1 = java.lang.Long.valueOf(r1);	 Catch:{ all -> 0x0efd }
        r2 = 1;
        r10 = java.lang.Boolean.valueOf(r2);	 Catch:{ all -> 0x0efd }
        r2 = 0;
        r9 = r9.zza(r2, r1, r10);	 Catch:{ all -> 0x0efd }
    L_0x0cbe:
        r1 = r8.getName();	 Catch:{ all -> 0x0efd }
        r10 = r8.getTimestampMillis();	 Catch:{ all -> 0x0efd }
        r2 = r9.zza(r10, r14);	 Catch:{ all -> 0x0efd }
        r4.put(r1, r2);	 Catch:{ all -> 0x0efd }
        goto L_0x0ce2;
    L_0x0cce:
        r6 = 1;
        r1 = r13.booleanValue();	 Catch:{ all -> 0x0efd }
        if (r1 == 0) goto L_0x0ce2;
    L_0x0cd6:
        r1 = r8.getName();	 Catch:{ all -> 0x0efd }
        r2 = 0;
        r9 = r9.zza(r11, r2, r2);	 Catch:{ all -> 0x0efd }
        r4.put(r1, r9);	 Catch:{ all -> 0x0efd }
    L_0x0ce2:
        r1 = r18;
        r2 = r19;
    L_0x0ce6:
        r1.zza(r2, r8);	 Catch:{ all -> 0x0efd }
    L_0x0ce9:
        r2 = r2 + 1;
        r6 = r63;
        r7 = r2;
        r2 = r3;
        r3 = r1;
        r1 = r62;
        goto L_0x099f;
    L_0x0cf4:
        r1 = r3;
        r3 = r2;
        r2 = r5.size();	 Catch:{ all -> 0x0efd }
        r6 = r1.zznm();	 Catch:{ all -> 0x0efd }
        if (r2 >= r6) goto L_0x0d07;
    L_0x0d00:
        r2 = r1.zznn();	 Catch:{ all -> 0x0efd }
        r2.zza(r5);	 Catch:{ all -> 0x0efd }
    L_0x0d07:
        r2 = r4.entrySet();	 Catch:{ all -> 0x0efd }
        r2 = r2.iterator();	 Catch:{ all -> 0x0efd }
    L_0x0d0f:
        r4 = r2.hasNext();	 Catch:{ all -> 0x0efd }
        if (r4 == 0) goto L_0x0d2b;
    L_0x0d15:
        r4 = r2.next();	 Catch:{ all -> 0x0efd }
        r4 = (java.util.Map.Entry) r4;	 Catch:{ all -> 0x0efd }
        r5 = r62.zzgy();	 Catch:{ all -> 0x0efd }
        r4 = r4.getValue();	 Catch:{ all -> 0x0efd }
        r4 = (com.google.android.gms.measurement.internal.zzae) r4;	 Catch:{ all -> 0x0efd }
        r5.zza(r4);	 Catch:{ all -> 0x0efd }
        goto L_0x0d0f;
    L_0x0d29:
        r1 = r3;
        r3 = r2;
    L_0x0d2b:
        r4 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
        r2 = r1.zzao(r4);	 Catch:{ all -> 0x0efd }
        r4 = -9223372036854775808;
        r2.zzap(r4);	 Catch:{ all -> 0x0efd }
        r2 = 0;
    L_0x0d3a:
        r4 = r1.zznm();	 Catch:{ all -> 0x0efd }
        if (r2 >= r4) goto L_0x0d6d;
    L_0x0d40:
        r4 = r1.zzq(r2);	 Catch:{ all -> 0x0efd }
        r5 = r4.getTimestampMillis();	 Catch:{ all -> 0x0efd }
        r7 = r1.zznq();	 Catch:{ all -> 0x0efd }
        r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1));
        if (r9 >= 0) goto L_0x0d57;
    L_0x0d50:
        r5 = r4.getTimestampMillis();	 Catch:{ all -> 0x0efd }
        r1.zzao(r5);	 Catch:{ all -> 0x0efd }
    L_0x0d57:
        r5 = r4.getTimestampMillis();	 Catch:{ all -> 0x0efd }
        r7 = r1.zznr();	 Catch:{ all -> 0x0efd }
        r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1));
        if (r9 <= 0) goto L_0x0d6a;
    L_0x0d63:
        r4 = r4.getTimestampMillis();	 Catch:{ all -> 0x0efd }
        r1.zzap(r4);	 Catch:{ all -> 0x0efd }
    L_0x0d6a:
        r2 = r2 + 1;
        goto L_0x0d3a;
    L_0x0d6d:
        r2 = r3.zztn;	 Catch:{ all -> 0x0efd }
        r2 = r2.zzag();	 Catch:{ all -> 0x0efd }
        r4 = r62.zzgy();	 Catch:{ all -> 0x0efd }
        r4 = r4.zzab(r2);	 Catch:{ all -> 0x0efd }
        if (r4 != 0) goto L_0x0d99;
    L_0x0d7d:
        r6 = r62;
        r4 = r6.zzj;	 Catch:{ all -> 0x0f1d }
        r4 = r4.zzab();	 Catch:{ all -> 0x0f1d }
        r4 = r4.zzgk();	 Catch:{ all -> 0x0f1d }
        r5 = "Bundling raw events w/o app info. appId";
        r7 = r3.zztn;	 Catch:{ all -> 0x0f1d }
        r7 = r7.zzag();	 Catch:{ all -> 0x0f1d }
        r7 = com.google.android.gms.measurement.internal.zzef.zzam(r7);	 Catch:{ all -> 0x0f1d }
        r4.zza(r5, r7);	 Catch:{ all -> 0x0f1d }
        goto L_0x0df6;
    L_0x0d99:
        r6 = r62;
        r5 = r1.zznm();	 Catch:{ all -> 0x0f1d }
        if (r5 <= 0) goto L_0x0df6;
    L_0x0da1:
        r7 = r4.zzak();	 Catch:{ all -> 0x0f1d }
        r9 = 0;
        r5 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1));
        if (r5 == 0) goto L_0x0daf;
    L_0x0dab:
        r1.zzar(r7);	 Catch:{ all -> 0x0f1d }
        goto L_0x0db2;
    L_0x0daf:
        r1.zznt();	 Catch:{ all -> 0x0f1d }
    L_0x0db2:
        r9 = r4.zzaj();	 Catch:{ all -> 0x0f1d }
        r11 = 0;
        r5 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1));
        if (r5 != 0) goto L_0x0dbd;
    L_0x0dbc:
        goto L_0x0dbe;
    L_0x0dbd:
        r7 = r9;
    L_0x0dbe:
        r5 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1));
        if (r5 == 0) goto L_0x0dc6;
    L_0x0dc2:
        r1.zzaq(r7);	 Catch:{ all -> 0x0f1d }
        goto L_0x0dc9;
    L_0x0dc6:
        r1.zzns();	 Catch:{ all -> 0x0f1d }
    L_0x0dc9:
        r4.zzau();	 Catch:{ all -> 0x0f1d }
        r7 = r4.zzar();	 Catch:{ all -> 0x0f1d }
        r5 = (int) r7;	 Catch:{ all -> 0x0f1d }
        r1.zzu(r5);	 Catch:{ all -> 0x0f1d }
        r7 = r1.zznq();	 Catch:{ all -> 0x0f1d }
        r4.zze(r7);	 Catch:{ all -> 0x0f1d }
        r7 = r1.zznr();	 Catch:{ all -> 0x0f1d }
        r4.zzf(r7);	 Catch:{ all -> 0x0f1d }
        r5 = r4.zzbc();	 Catch:{ all -> 0x0f1d }
        if (r5 == 0) goto L_0x0dec;
    L_0x0de8:
        r1.zzcl(r5);	 Catch:{ all -> 0x0f1d }
        goto L_0x0def;
    L_0x0dec:
        r1.zznu();	 Catch:{ all -> 0x0f1d }
    L_0x0def:
        r5 = r62.zzgy();	 Catch:{ all -> 0x0f1d }
        r5.zza(r4);	 Catch:{ all -> 0x0f1d }
    L_0x0df6:
        r4 = r1.zznm();	 Catch:{ all -> 0x0f1d }
        if (r4 <= 0) goto L_0x0e5c;
    L_0x0dfc:
        r4 = r6.zzj;	 Catch:{ all -> 0x0f1d }
        r4.zzae();	 Catch:{ all -> 0x0f1d }
        r4 = r62.zzgz();	 Catch:{ all -> 0x0f1d }
        r5 = r3.zztn;	 Catch:{ all -> 0x0f1d }
        r5 = r5.zzag();	 Catch:{ all -> 0x0f1d }
        r4 = r4.zzaw(r5);	 Catch:{ all -> 0x0f1d }
        if (r4 == 0) goto L_0x0e20;
    L_0x0e11:
        r5 = r4.zzzk;	 Catch:{ all -> 0x0f1d }
        if (r5 != 0) goto L_0x0e16;
    L_0x0e15:
        goto L_0x0e20;
    L_0x0e16:
        r4 = r4.zzzk;	 Catch:{ all -> 0x0f1d }
        r4 = r4.longValue();	 Catch:{ all -> 0x0f1d }
        r1.zzav(r4);	 Catch:{ all -> 0x0f1d }
        goto L_0x0e4b;
    L_0x0e20:
        r4 = r3.zztn;	 Catch:{ all -> 0x0f1d }
        r4 = r4.getGmpAppId();	 Catch:{ all -> 0x0f1d }
        r4 = android.text.TextUtils.isEmpty(r4);	 Catch:{ all -> 0x0f1d }
        if (r4 == 0) goto L_0x0e32;
    L_0x0e2c:
        r4 = -1;
        r1.zzav(r4);	 Catch:{ all -> 0x0f1d }
        goto L_0x0e4b;
    L_0x0e32:
        r4 = r6.zzj;	 Catch:{ all -> 0x0f1d }
        r4 = r4.zzab();	 Catch:{ all -> 0x0f1d }
        r4 = r4.zzgn();	 Catch:{ all -> 0x0f1d }
        r5 = "Did not find measurement config or missing version info. appId";
        r7 = r3.zztn;	 Catch:{ all -> 0x0f1d }
        r7 = r7.zzag();	 Catch:{ all -> 0x0f1d }
        r7 = com.google.android.gms.measurement.internal.zzef.zzam(r7);	 Catch:{ all -> 0x0f1d }
        r4.zza(r5, r7);	 Catch:{ all -> 0x0f1d }
    L_0x0e4b:
        r4 = r62.zzgy();	 Catch:{ all -> 0x0f1d }
        r1 = r1.zzug();	 Catch:{ all -> 0x0f1d }
        r1 = (com.google.android.gms.internal.measurement.zzey) r1;	 Catch:{ all -> 0x0f1d }
        r1 = (com.google.android.gms.internal.measurement.zzbs.zzg) r1;	 Catch:{ all -> 0x0f1d }
        r12 = r21;
        r4.zza(r1, r12);	 Catch:{ all -> 0x0f1d }
    L_0x0e5c:
        r1 = r62.zzgy();	 Catch:{ all -> 0x0f1d }
        r3 = r3.zzto;	 Catch:{ all -> 0x0f1d }
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r3);	 Catch:{ all -> 0x0f1d }
        r1.zzo();	 Catch:{ all -> 0x0f1d }
        r1.zzbi();	 Catch:{ all -> 0x0f1d }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0f1d }
        r5 = "rowid in (";
        r4.<init>(r5);	 Catch:{ all -> 0x0f1d }
        r5 = 0;
    L_0x0e73:
        r7 = r3.size();	 Catch:{ all -> 0x0f1d }
        if (r5 >= r7) goto L_0x0e90;
    L_0x0e79:
        if (r5 == 0) goto L_0x0e80;
    L_0x0e7b:
        r7 = ",";
        r4.append(r7);	 Catch:{ all -> 0x0f1d }
    L_0x0e80:
        r7 = r3.get(r5);	 Catch:{ all -> 0x0f1d }
        r7 = (java.lang.Long) r7;	 Catch:{ all -> 0x0f1d }
        r7 = r7.longValue();	 Catch:{ all -> 0x0f1d }
        r4.append(r7);	 Catch:{ all -> 0x0f1d }
        r5 = r5 + 1;
        goto L_0x0e73;
    L_0x0e90:
        r5 = ")";
        r4.append(r5);	 Catch:{ all -> 0x0f1d }
        r5 = r1.getWritableDatabase();	 Catch:{ all -> 0x0f1d }
        r7 = "raw_events";
        r4 = r4.toString();	 Catch:{ all -> 0x0f1d }
        r8 = 0;
        r4 = r5.delete(r7, r4, r8);	 Catch:{ all -> 0x0f1d }
        r5 = r3.size();	 Catch:{ all -> 0x0f1d }
        if (r4 == r5) goto L_0x0ec3;
    L_0x0eaa:
        r1 = r1.zzab();	 Catch:{ all -> 0x0f1d }
        r1 = r1.zzgk();	 Catch:{ all -> 0x0f1d }
        r5 = "Deleted fewer rows from raw events table than expected";
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ all -> 0x0f1d }
        r3 = r3.size();	 Catch:{ all -> 0x0f1d }
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ all -> 0x0f1d }
        r1.zza(r5, r4, r3);	 Catch:{ all -> 0x0f1d }
    L_0x0ec3:
        r1 = r62.zzgy();	 Catch:{ all -> 0x0f1d }
        r3 = r1.getWritableDatabase();	 Catch:{ all -> 0x0f1d }
        r4 = "delete from raw_events_metadata where app_id=? and metadata_fingerprint not in (select distinct metadata_fingerprint from raw_events where app_id=?)";
        r5 = 2;
        r5 = new java.lang.String[r5];	 Catch:{ SQLiteException -> 0x0eda }
        r7 = 0;
        r5[r7] = r2;	 Catch:{ SQLiteException -> 0x0eda }
        r7 = 1;
        r5[r7] = r2;	 Catch:{ SQLiteException -> 0x0eda }
        r3.execSQL(r4, r5);	 Catch:{ SQLiteException -> 0x0eda }
        goto L_0x0eed;
    L_0x0eda:
        r0 = move-exception;
        r3 = r0;
        r1 = r1.zzab();	 Catch:{ all -> 0x0f1d }
        r1 = r1.zzgk();	 Catch:{ all -> 0x0f1d }
        r4 = "Failed to remove unused event metadata. appId";
        r2 = com.google.android.gms.measurement.internal.zzef.zzam(r2);	 Catch:{ all -> 0x0f1d }
        r1.zza(r4, r2, r3);	 Catch:{ all -> 0x0f1d }
    L_0x0eed:
        r1 = r62.zzgy();	 Catch:{ all -> 0x0f1d }
        r1.setTransactionSuccessful();	 Catch:{ all -> 0x0f1d }
        r1 = r62.zzgy();
        r1.endTransaction();
        r1 = 1;
        return r1;
    L_0x0efd:
        r0 = move-exception;
        r6 = r62;
        goto L_0x0f21;
    L_0x0f01:
        r6 = r1;
        r1 = r62.zzgy();	 Catch:{ all -> 0x0f1d }
        r1.setTransactionSuccessful();	 Catch:{ all -> 0x0f1d }
        r1 = r62.zzgy();
        r1.endTransaction();
        r1 = 0;
        return r1;
    L_0x0f12:
        r0 = move-exception;
        r6 = r1;
        r1 = r0;
        r22 = r5;
    L_0x0f17:
        if (r22 == 0) goto L_0x0f1c;
    L_0x0f19:
        r22.close();	 Catch:{ all -> 0x0f1d }
    L_0x0f1c:
        throw r1;	 Catch:{ all -> 0x0f1d }
    L_0x0f1d:
        r0 = move-exception;
        goto L_0x0f21;
    L_0x0f1f:
        r0 = move-exception;
        r6 = r1;
    L_0x0f21:
        r1 = r0;
        r2 = r62.zzgy();
        r2.endTransaction();
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzjg.zzd(java.lang.String, long):boolean");
    }

    @VisibleForTesting
    private final void zza(com.google.android.gms.internal.measurement.zzbs.zzg.zza zza, long j, boolean z) {
        String str = z ? "_se" : "_lte";
        zzjp zze = zzgy().zze(zza.zzag(), str);
        if (zze == null || zze.value == null) {
            zze = new zzjp(zza.zzag(), "auto", str, this.zzj.zzx().currentTimeMillis(), Long.valueOf(j));
        } else {
            zze = new zzjp(zza.zzag(), "auto", str, this.zzj.zzx().currentTimeMillis(), Long.valueOf(((Long) zze.value).longValue() + j));
        }
        zzk zzk = (zzk) ((zzey) zzk.zzqu().zzdb(str).zzbk(this.zzj.zzx().currentTimeMillis()).zzbl(((Long) r8.value).longValue()).zzug());
        Object obj = null;
        for (int i = 0; i < zza.zznp(); i++) {
            if (str.equals(zza.zzs(i).getName())) {
                zza.zza(i, zzk);
                obj = 1;
                break;
            }
        }
        if (obj == null) {
            zza.zza(zzk);
        }
        if (j > 0) {
            zzgy().zza(r8);
            this.zzj.zzab().zzgr().zza("Updated engagement user property. scope, value", z ? "session-scoped" : "lifetime", r8.value);
        }
    }

    private final boolean zza(com.google.android.gms.internal.measurement.zzbs.zzc.zza zza, com.google.android.gms.internal.measurement.zzbs.zzc.zza zza2) {
        Preconditions.checkArgument("_e".equals(zza.getName()));
        zzgw();
        zze zza3 = zzjo.zza((zzc) ((zzey) zza.zzug()), "_sc");
        String str = null;
        Object obj;
        if (zza3 == null) {
            obj = null;
        } else {
            obj = zza3.zzmy();
        }
        zzgw();
        zze zza4 = zzjo.zza((zzc) ((zzey) zza2.zzug()), "_pc");
        if (zza4 != null) {
            str = zza4.zzmy();
        }
        if (str == null || !str.equals(obj)) {
            return false;
        }
        zzgw();
        str = "_et";
        zza3 = zzjo.zza((zzc) ((zzey) zza.zzug()), str);
        if (zza3.zzna() && zza3.zznb() > 0) {
            long zznb = zza3.zznb();
            zzgw();
            zza3 = zzjo.zza((zzc) ((zzey) zza2.zzug()), str);
            if (zza3 != null && zza3.zznb() > 0) {
                zznb += zza3.zznb();
            }
            zzgw();
            zzjo.zza(zza2, str, Long.valueOf(zznb));
            zzgw();
            zzjo.zza(zza, "_fr", Long.valueOf(1));
        }
        return true;
    }

    @VisibleForTesting
    private static void zza(com.google.android.gms.internal.measurement.zzbs.zzc.zza zza, @NonNull String str) {
        List zzmj = zza.zzmj();
        for (int i = 0; i < zzmj.size(); i++) {
            if (str.equals(((zze) zzmj.get(i)).getName())) {
                zza.zzm(i);
                return;
            }
        }
    }

    @VisibleForTesting
    private static void zza(com.google.android.gms.internal.measurement.zzbs.zzc.zza zza, int i, String str) {
        List zzmj = zza.zzmj();
        int i2 = 0;
        while (true) {
            String str2 = "_err";
            if (i2 >= zzmj.size()) {
                zze zze = (zze) ((zzey) zze.zzng().zzbz("_ev").zzca(str).zzug());
                zza.zza((zze) ((zzey) zze.zzng().zzbz(str2).zzam(Long.valueOf((long) i).longValue()).zzug())).zza(zze);
                return;
            } else if (!str2.equals(((zze) zzmj.get(i2)).getName())) {
                i2++;
            } else {
                return;
            }
        }
    }

    @WorkerThread
    @VisibleForTesting
    final void zza(int i, Throwable th, byte[] bArr, String str) {
        zzo();
        zzjj();
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zztd = false;
                zzjo();
            }
        }
        List<Long> list = this.zzth;
        this.zzth = null;
        int i2 = 1;
        if ((i == LogSeverity.INFO_VALUE || i == XMPError.BADSTREAM) && th == null) {
            try {
                this.zzj.zzac().zzlj.set(this.zzj.zzx().currentTimeMillis());
                this.zzj.zzac().zzlk.set(0);
                zzjn();
                this.zzj.zzab().zzgs().zza("Successful upload. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                zzgy().beginTransaction();
                try {
                    for (Long l : list) {
                        zzgf zzgy;
                        try {
                            zzgy = zzgy();
                            long longValue = l.longValue();
                            zzgy.zzo();
                            zzgy.zzbi();
                            if (zzgy.getWritableDatabase().delete("queue", "rowid=?", new String[]{String.valueOf(longValue)}) != 1) {
                                throw new SQLiteException("Deleted fewer rows from queue than expected");
                            }
                        } catch (SQLiteException e) {
                            zzgy.zzab().zzgk().zza("Failed to delete a bundle in a queue table", e);
                            throw e;
                        } catch (SQLiteException e2) {
                            if (this.zzti == null || !this.zzti.contains(l)) {
                                throw e2;
                            }
                        }
                    }
                    zzgy().setTransactionSuccessful();
                    this.zzti = null;
                    if (zzjf().zzgv() && zzjm()) {
                        zzjl();
                    } else {
                        this.zztj = -1;
                        zzjn();
                    }
                    this.zzsy = 0;
                } finally {
                    zzgy().endTransaction();
                }
            } catch (SQLiteException e3) {
                this.zzj.zzab().zzgk().zza("Database error while trying to delete uploaded bundles", e3);
                this.zzsy = this.zzj.zzx().elapsedRealtime();
                this.zzj.zzab().zzgs().zza("Disable upload, time", Long.valueOf(this.zzsy));
            }
        } else {
            this.zzj.zzab().zzgs().zza("Network upload failed. Will retry later. code, error", Integer.valueOf(i), th);
            this.zzj.zzac().zzlk.set(this.zzj.zzx().currentTimeMillis());
            if (!(i == 503 || i == 429)) {
                i2 = 0;
            }
            if (i2 != 0) {
                this.zzj.zzac().zzll.set(this.zzj.zzx().currentTimeMillis());
            }
            zzgy().zzb(list);
            zzjn();
        }
        this.zztd = false;
        zzjo();
    }

    private final boolean zzjm() {
        zzo();
        zzjj();
        return zzgy().zzcd() || !TextUtils.isEmpty(zzgy().zzby());
    }

    @WorkerThread
    private final void zzb(zzf zzf) {
        zzo();
        if (!TextUtils.isEmpty(zzf.getGmpAppId()) || (zzs.zzbx() && !TextUtils.isEmpty(zzf.zzah()))) {
            zzs zzad = this.zzj.zzad();
            Builder builder = new Builder();
            Object gmpAppId = zzf.getGmpAppId();
            if (TextUtils.isEmpty(gmpAppId) && zzs.zzbx()) {
                gmpAppId = zzf.zzah();
            }
            Map map = null;
            Builder encodedAuthority = builder.scheme((String) zzak.zzgj.get(null)).encodedAuthority((String) zzak.zzgk.get(null));
            String str = "config/app/";
            String valueOf = String.valueOf(gmpAppId);
            encodedAuthority.path(valueOf.length() != 0 ? str.concat(valueOf) : new String(str)).appendQueryParameter("app_instance_id", zzf.getAppInstanceId()).appendQueryParameter("platform", "android").appendQueryParameter("gmp_version", String.valueOf(zzad.zzao()));
            String uri = builder.build().toString();
            try {
                URL url = new URL(uri);
                this.zzj.zzab().zzgs().zza("Fetching remote configuration", zzf.zzag());
                zzbw zzaw = zzgz().zzaw(zzf.zzag());
                CharSequence zzax = zzgz().zzax(zzf.zzag());
                if (!(zzaw == null || TextUtils.isEmpty(zzax))) {
                    map = new ArrayMap();
                    map.put(HttpHeaders.IF_MODIFIED_SINCE, zzax);
                }
                Map map2 = map;
                this.zztc = true;
                zzgf zzjf = zzjf();
                String zzag = zzf.zzag();
                zzel zzjl = new zzjl(this);
                zzjf.zzo();
                zzjf.zzbi();
                Preconditions.checkNotNull(url);
                Preconditions.checkNotNull(zzjl);
                zzjf.zzaa().zzb(new zzen(zzjf, zzag, url, null, map2, zzjl));
                return;
            } catch (MalformedURLException unused) {
                this.zzj.zzab().zzgk().zza("Failed to parse config URL. Not fetching. appId", zzef.zzam(zzf.zzag()), uri);
                return;
            }
        }
        zzb(zzf.zzag(), XMPError.BADSTREAM, null, null, null);
    }

    @WorkerThread
    @VisibleForTesting
    final void zzb(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        zzo();
        zzjj();
        Preconditions.checkNotEmpty(str);
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zztc = false;
                zzjo();
            }
        }
        this.zzj.zzab().zzgs().zza("onConfigFetched. Response size", Integer.valueOf(bArr.length));
        zzgy().beginTransaction();
        zzf zzab = zzgy().zzab(str);
        Object obj = 1;
        Object obj2 = ((i == LogSeverity.INFO_VALUE || i == XMPError.BADSTREAM || i == OlympusRawInfoMakernoteDirectory.TagWbRbLevelsDaylightFluor) && th == null) ? 1 : null;
        if (zzab == null) {
            this.zzj.zzab().zzgn().zza("App does not exist in onConfigFetched. appId", zzef.zzam(str));
        } else if (obj2 != null || i == 404) {
            List list = map != null ? (List) map.get(HttpHeaders.LAST_MODIFIED) : null;
            String str2 = (list == null || list.size() <= 0) ? null : (String) list.get(0);
            if (i == 404 || i == OlympusRawInfoMakernoteDirectory.TagWbRbLevelsDaylightFluor) {
                if (zzgz().zzaw(str) == null && !zzgz().zza(str, null, null)) {
                    zzgy().endTransaction();
                    this.zztc = false;
                    zzjo();
                    return;
                }
            } else if (!zzgz().zza(str, bArr, str2)) {
                zzgy().endTransaction();
                this.zztc = false;
                zzjo();
                return;
            }
            zzab.zzl(this.zzj.zzx().currentTimeMillis());
            zzgy().zza(zzab);
            if (i == 404) {
                this.zzj.zzab().zzgp().zza("Config not found. Using empty config. appId", str);
            } else {
                this.zzj.zzab().zzgs().zza("Successfully fetched config. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
            }
            if (zzjf().zzgv() && zzjm()) {
                zzjl();
            } else {
                zzjn();
            }
        } else {
            zzab.zzm(this.zzj.zzx().currentTimeMillis());
            zzgy().zza(zzab);
            this.zzj.zzab().zzgs().zza("Fetching config failed. code, error", Integer.valueOf(i), th);
            zzgz().zzay(str);
            this.zzj.zzac().zzlk.set(this.zzj.zzx().currentTimeMillis());
            if (!(i == 503 || i == 429)) {
                obj = null;
            }
            if (obj != null) {
                this.zzj.zzac().zzll.set(this.zzj.zzx().currentTimeMillis());
            }
            zzjn();
        }
        zzgy().setTransactionSuccessful();
        zzgy().endTransaction();
        this.zztc = false;
        zzjo();
    }

    /* JADX WARNING: Removed duplicated region for block: B:56:0x01c0  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x01a2  */
    @androidx.annotation.WorkerThread
    private final void zzjn() {
        /*
        r21 = this;
        r0 = r21;
        r21.zzo();
        r21.zzjj();
        r1 = r21.zzjr();
        if (r1 != 0) goto L_0x001d;
    L_0x000e:
        r1 = r0.zzj;
        r1 = r1.zzad();
        r2 = com.google.android.gms.measurement.internal.zzak.zzim;
        r1 = r1.zza(r2);
        if (r1 != 0) goto L_0x001d;
    L_0x001c:
        return;
    L_0x001d:
        r1 = r0.zzsy;
        r3 = 0;
        r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1));
        if (r5 <= 0) goto L_0x0062;
    L_0x0025:
        r1 = r0.zzj;
        r1 = r1.zzx();
        r1 = r1.elapsedRealtime();
        r5 = 3600000; // 0x36ee80 float:5.044674E-39 double:1.7786363E-317;
        r7 = r0.zzsy;
        r1 = r1 - r7;
        r1 = java.lang.Math.abs(r1);
        r5 = r5 - r1;
        r1 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1));
        if (r1 <= 0) goto L_0x0060;
    L_0x003e:
        r1 = r0.zzj;
        r1 = r1.zzab();
        r1 = r1.zzgs();
        r2 = java.lang.Long.valueOf(r5);
        r3 = "Upload has been suspended. Will update scheduling later in approximately ms";
        r1.zza(r3, r2);
        r1 = r21.zzjg();
        r1.unregister();
        r1 = r21.zzjh();
        r1.cancel();
        return;
    L_0x0060:
        r0.zzsy = r3;
    L_0x0062:
        r1 = r0.zzj;
        r1 = r1.zzie();
        if (r1 == 0) goto L_0x026b;
    L_0x006a:
        r1 = r21.zzjm();
        if (r1 != 0) goto L_0x0072;
    L_0x0070:
        goto L_0x026b;
    L_0x0072:
        r1 = r0.zzj;
        r1 = r1.zzx();
        r1 = r1.currentTimeMillis();
        r5 = com.google.android.gms.measurement.internal.zzak.zzhf;
        r6 = 0;
        r5 = r5.get(r6);
        r5 = (java.lang.Long) r5;
        r7 = r5.longValue();
        r7 = java.lang.Math.max(r3, r7);
        r5 = r21.zzgy();
        r5 = r5.zzce();
        if (r5 != 0) goto L_0x00a4;
    L_0x0097:
        r5 = r21.zzgy();
        r5 = r5.zzbz();
        if (r5 == 0) goto L_0x00a2;
    L_0x00a1:
        goto L_0x00a4;
    L_0x00a2:
        r5 = 0;
        goto L_0x00a5;
    L_0x00a4:
        r5 = 1;
    L_0x00a5:
        if (r5 == 0) goto L_0x00e1;
    L_0x00a7:
        r10 = r0.zzj;
        r10 = r10.zzad();
        r10 = r10.zzbu();
        r11 = android.text.TextUtils.isEmpty(r10);
        if (r11 != 0) goto L_0x00d0;
    L_0x00b7:
        r11 = ".none.";
        r10 = r11.equals(r10);
        if (r10 != 0) goto L_0x00d0;
    L_0x00bf:
        r10 = com.google.android.gms.measurement.internal.zzak.zzha;
        r10 = r10.get(r6);
        r10 = (java.lang.Long) r10;
        r10 = r10.longValue();
        r10 = java.lang.Math.max(r3, r10);
        goto L_0x00f1;
    L_0x00d0:
        r10 = com.google.android.gms.measurement.internal.zzak.zzgz;
        r10 = r10.get(r6);
        r10 = (java.lang.Long) r10;
        r10 = r10.longValue();
        r10 = java.lang.Math.max(r3, r10);
        goto L_0x00f1;
    L_0x00e1:
        r10 = com.google.android.gms.measurement.internal.zzak.zzgy;
        r10 = r10.get(r6);
        r10 = (java.lang.Long) r10;
        r10 = r10.longValue();
        r10 = java.lang.Math.max(r3, r10);
    L_0x00f1:
        r12 = r0.zzj;
        r12 = r12.zzac();
        r12 = r12.zzlj;
        r12 = r12.get();
        r14 = r0.zzj;
        r14 = r14.zzac();
        r14 = r14.zzlk;
        r14 = r14.get();
        r16 = r21.zzgy();
        r17 = r10;
        r9 = r16.zzcb();
        r11 = r21.zzgy();
        r19 = r7;
        r6 = r11.zzcc();
        r6 = java.lang.Math.max(r9, r6);
        r8 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1));
        if (r8 != 0) goto L_0x0128;
    L_0x0125:
        r8 = r3;
        goto L_0x019e;
    L_0x0128:
        r6 = r6 - r1;
        r6 = java.lang.Math.abs(r6);
        r6 = r1 - r6;
        r12 = r12 - r1;
        r8 = java.lang.Math.abs(r12);
        r8 = r1 - r8;
        r14 = r14 - r1;
        r10 = java.lang.Math.abs(r14);
        r1 = r1 - r10;
        r8 = java.lang.Math.max(r8, r1);
        r10 = r6 + r19;
        if (r5 == 0) goto L_0x014e;
    L_0x0144:
        r5 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1));
        if (r5 <= 0) goto L_0x014e;
    L_0x0148:
        r10 = java.lang.Math.min(r6, r8);
        r10 = r10 + r17;
    L_0x014e:
        r5 = r21.zzgw();
        r12 = r17;
        r5 = r5.zzb(r8, r12);
        if (r5 != 0) goto L_0x015c;
    L_0x015a:
        r8 = r8 + r12;
        goto L_0x015d;
    L_0x015c:
        r8 = r10;
    L_0x015d:
        r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1));
        if (r5 == 0) goto L_0x019e;
    L_0x0161:
        r5 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1));
        if (r5 < 0) goto L_0x019e;
    L_0x0165:
        r5 = 0;
    L_0x0166:
        r6 = 20;
        r7 = com.google.android.gms.measurement.internal.zzak.zzhh;
        r10 = 0;
        r7 = r7.get(r10);
        r7 = (java.lang.Integer) r7;
        r7 = r7.intValue();
        r11 = 0;
        r7 = java.lang.Math.max(r11, r7);
        r6 = java.lang.Math.min(r6, r7);
        if (r5 >= r6) goto L_0x0125;
    L_0x0180:
        r6 = 1;
        r6 = r6 << r5;
        r12 = com.google.android.gms.measurement.internal.zzak.zzhg;
        r12 = r12.get(r10);
        r12 = (java.lang.Long) r12;
        r12 = r12.longValue();
        r12 = java.lang.Math.max(r3, r12);
        r12 = r12 * r6;
        r8 = r8 + r12;
        r6 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1));
        if (r6 <= 0) goto L_0x019b;
    L_0x019a:
        goto L_0x019e;
    L_0x019b:
        r5 = r5 + 1;
        goto L_0x0166;
    L_0x019e:
        r1 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1));
        if (r1 != 0) goto L_0x01c0;
    L_0x01a2:
        r1 = r0.zzj;
        r1 = r1.zzab();
        r1 = r1.zzgs();
        r2 = "Next upload time is 0";
        r1.zzao(r2);
        r1 = r21.zzjg();
        r1.unregister();
        r1 = r21.zzjh();
        r1.cancel();
        return;
    L_0x01c0:
        r1 = r21.zzjf();
        r1 = r1.zzgv();
        if (r1 != 0) goto L_0x01e8;
    L_0x01ca:
        r1 = r0.zzj;
        r1 = r1.zzab();
        r1 = r1.zzgs();
        r2 = "No network";
        r1.zzao(r2);
        r1 = r21.zzjg();
        r1.zzha();
        r1 = r21.zzjh();
        r1.cancel();
        return;
    L_0x01e8:
        r1 = r0.zzj;
        r1 = r1.zzac();
        r1 = r1.zzll;
        r1 = r1.get();
        r5 = com.google.android.gms.measurement.internal.zzak.zzgw;
        r6 = 0;
        r5 = r5.get(r6);
        r5 = (java.lang.Long) r5;
        r5 = r5.longValue();
        r5 = java.lang.Math.max(r3, r5);
        r7 = r21.zzgw();
        r7 = r7.zzb(r1, r5);
        if (r7 != 0) goto L_0x0214;
    L_0x020f:
        r1 = r1 + r5;
        r8 = java.lang.Math.max(r8, r1);
    L_0x0214:
        r1 = r21.zzjg();
        r1.unregister();
        r1 = r0.zzj;
        r1 = r1.zzx();
        r1 = r1.currentTimeMillis();
        r8 = r8 - r1;
        r1 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1));
        if (r1 > 0) goto L_0x0250;
    L_0x022a:
        r1 = com.google.android.gms.measurement.internal.zzak.zzhb;
        r2 = 0;
        r1 = r1.get(r2);
        r1 = (java.lang.Long) r1;
        r1 = r1.longValue();
        r8 = java.lang.Math.max(r3, r1);
        r1 = r0.zzj;
        r1 = r1.zzac();
        r1 = r1.zzlj;
        r2 = r0.zzj;
        r2 = r2.zzx();
        r2 = r2.currentTimeMillis();
        r1.set(r2);
    L_0x0250:
        r1 = r0.zzj;
        r1 = r1.zzab();
        r1 = r1.zzgs();
        r2 = java.lang.Long.valueOf(r8);
        r3 = "Upload scheduled in approximately ms";
        r1.zza(r3, r2);
        r1 = r21.zzjh();
        r1.zzv(r8);
        return;
    L_0x026b:
        r1 = r0.zzj;
        r1 = r1.zzab();
        r1 = r1.zzgs();
        r2 = "Nothing to upload or uploading impossible";
        r1.zzao(r2);
        r1 = r21.zzjg();
        r1.unregister();
        r1 = r21.zzjh();
        r1.cancel();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzjg.zzjn():void");
    }

    @WorkerThread
    final void zzf(Runnable runnable) {
        zzo();
        if (this.zzsz == null) {
            this.zzsz = new ArrayList();
        }
        this.zzsz.add(runnable);
    }

    @WorkerThread
    private final void zzjo() {
        zzo();
        if (this.zztc || this.zztd || this.zzte) {
            this.zzj.zzab().zzgs().zza("Not stopping services. fetch, network, upload", Boolean.valueOf(this.zztc), Boolean.valueOf(this.zztd), Boolean.valueOf(this.zzte));
            return;
        }
        this.zzj.zzab().zzgs().zzao("Stopping uploading service(s)");
        List<Runnable> list = this.zzsz;
        if (list != null) {
            for (Runnable run : list) {
                run.run();
            }
            this.zzsz.clear();
        }
    }

    @WorkerThread
    private final Boolean zzc(zzf zzf) {
        try {
            if (zzf.zzam() != -2147483648L) {
                if (zzf.zzam() == ((long) Wrappers.packageManager(this.zzj.getContext()).getPackageInfo(zzf.zzag(), 0).versionCode)) {
                    return Boolean.valueOf(true);
                }
            }
            String str = Wrappers.packageManager(this.zzj.getContext()).getPackageInfo(zzf.zzag(), 0).versionName;
            if (zzf.zzal() != null && zzf.zzal().equals(str)) {
                return Boolean.valueOf(true);
            }
            return Boolean.valueOf(false);
        } catch (NameNotFoundException unused) {
            return null;
        }
    }

    @WorkerThread
    @VisibleForTesting
    private final boolean zzjp() {
        zzo();
        String str = "Storage concurrent access okay";
        if (this.zzj.zzad().zza(zzak.zzjh)) {
            FileLock fileLock = this.zztf;
            if (fileLock != null && fileLock.isValid()) {
                this.zzj.zzab().zzgs().zzao(str);
                return true;
            }
        }
        try {
            this.zztg = new RandomAccessFile(new File(this.zzj.getContext().getFilesDir(), "google_app_measurement.db"), "rw").getChannel();
            this.zztf = this.zztg.tryLock();
            if (this.zztf != null) {
                this.zzj.zzab().zzgs().zzao(str);
                return true;
            }
            this.zzj.zzab().zzgk().zzao("Storage concurrent data access panic");
            return false;
        } catch (FileNotFoundException e) {
            this.zzj.zzab().zzgk().zza("Failed to acquire storage lock", e);
        } catch (IOException e2) {
            this.zzj.zzab().zzgk().zza("Failed to access storage lock file", e2);
        } catch (OverlappingFileLockException e3) {
            this.zzj.zzab().zzgn().zza("Storage lock already acquired", e3);
        }
    }

    @WorkerThread
    @VisibleForTesting
    private final int zza(FileChannel fileChannel) {
        zzo();
        int i = 0;
        if (fileChannel == null || !fileChannel.isOpen()) {
            this.zzj.zzab().zzgk().zzao("Bad channel to read from");
            return 0;
        }
        ByteBuffer allocate = ByteBuffer.allocate(4);
        try {
            fileChannel.position(0);
            int read = fileChannel.read(allocate);
            if (read != 4) {
                if (read != -1) {
                    this.zzj.zzab().zzgn().zza("Unexpected data length. Bytes read", Integer.valueOf(read));
                }
                return 0;
            }
            allocate.flip();
            i = allocate.getInt();
            return i;
        } catch (IOException e) {
            this.zzj.zzab().zzgk().zza("Failed to read from channel", e);
        }
    }

    @WorkerThread
    @VisibleForTesting
    private final boolean zza(int i, FileChannel fileChannel) {
        zzo();
        if (fileChannel == null || !fileChannel.isOpen()) {
            this.zzj.zzab().zzgk().zzao("Bad channel to read from");
            return false;
        }
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.putInt(i);
        allocate.flip();
        try {
            fileChannel.truncate(0);
            fileChannel.write(allocate);
            fileChannel.force(true);
            if (fileChannel.size() != 4) {
                this.zzj.zzab().zzgk().zza("Error writing to channel. Bytes written", Long.valueOf(fileChannel.size()));
            }
            return true;
        } catch (IOException e) {
            this.zzj.zzab().zzgk().zza("Failed to write to channel", e);
            return false;
        }
    }

    @WorkerThread
    final void zzjq() {
        zzo();
        zzjj();
        if (!this.zzsx) {
            this.zzsx = true;
            zzo();
            zzjj();
            if ((this.zzj.zzad().zza(zzak.zzim) || zzjr()) && zzjp()) {
                int zza = zza(this.zztg);
                int zzgf = this.zzj.zzr().zzgf();
                zzo();
                if (zza > zzgf) {
                    this.zzj.zzab().zzgk().zza("Panic: can't downgrade version. Previous, current version", Integer.valueOf(zza), Integer.valueOf(zzgf));
                } else if (zza < zzgf) {
                    if (zza(zzgf, this.zztg)) {
                        this.zzj.zzab().zzgs().zza("Storage version upgraded. Previous, current version", Integer.valueOf(zza), Integer.valueOf(zzgf));
                    } else {
                        this.zzj.zzab().zzgk().zza("Storage version upgrade failed. Previous, current version", Integer.valueOf(zza), Integer.valueOf(zzgf));
                    }
                }
            }
        }
        if (!this.zzsw && !this.zzj.zzad().zza(zzak.zzim)) {
            this.zzj.zzab().zzgq().zzao("This instance being marked as an uploader");
            this.zzsw = true;
            zzjn();
        }
    }

    @WorkerThread
    private final boolean zzjr() {
        zzo();
        zzjj();
        return this.zzsw;
    }

    @WorkerThread
    @VisibleForTesting
    final void zzd(zzn zzn) {
        String str = "app_id=?";
        if (this.zzth != null) {
            this.zzti = new ArrayList();
            this.zzti.addAll(this.zzth);
        }
        zzgf zzgy = zzgy();
        String str2 = zzn.packageName;
        Preconditions.checkNotEmpty(str2);
        zzgy.zzo();
        zzgy.zzbi();
        try {
            SQLiteDatabase writableDatabase = zzgy.getWritableDatabase();
            String[] strArr = new String[]{str2};
            int delete = ((((((((writableDatabase.delete("apps", str, strArr) + 0) + writableDatabase.delete("events", str, strArr)) + writableDatabase.delete("user_attributes", str, strArr)) + writableDatabase.delete("conditional_properties", str, strArr)) + writableDatabase.delete("raw_events", str, strArr)) + writableDatabase.delete("raw_events_metadata", str, strArr)) + writableDatabase.delete("queue", str, strArr)) + writableDatabase.delete("audience_filter_values", str, strArr)) + writableDatabase.delete("main_event_params", str, strArr);
            if (delete > 0) {
                zzgy.zzab().zzgs().zza("Reset analytics data. app, records", str2, Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzgy.zzab().zzgk().zza("Error resetting analytics data. appId, error", zzef.zzam(str2), e);
        }
        zzn zza = zza(this.zzj.getContext(), zzn.packageName, zzn.zzcg, zzn.zzcq, zzn.zzcs, zzn.zzct, zzn.zzdr, zzn.zzcu);
        if (zzn.zzcq) {
            zzf(zza);
        }
    }

    private final zzn zza(Context context, String str, String str2, boolean z, boolean z2, boolean z3, long j, String str3) {
        String str4 = str;
        String str5 = "Unknown";
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            this.zzj.zzab().zzgk().zzao("PackageManager is null, can not log app install information");
            return null;
        }
        String installerPackageName;
        try {
            installerPackageName = packageManager.getInstallerPackageName(str4);
        } catch (IllegalArgumentException unused) {
            this.zzj.zzab().zzgk().zza("Error retrieving installer package name. appId", zzef.zzam(str));
            installerPackageName = str5;
        }
        if (installerPackageName == null) {
            installerPackageName = "manual_install";
        } else if ("com.android.vending".equals(installerPackageName)) {
            installerPackageName = "";
        }
        String str6 = installerPackageName;
        try {
            int i;
            String str7;
            PackageInfo packageInfo = Wrappers.packageManager(context).getPackageInfo(str4, 0);
            if (packageInfo != null) {
                CharSequence applicationLabel = Wrappers.packageManager(context).getApplicationLabel(str4);
                if (!TextUtils.isEmpty(applicationLabel)) {
                    str5 = applicationLabel.toString();
                }
                String str8 = packageInfo.versionName;
                i = packageInfo.versionCode;
                str7 = str8;
            } else {
                i = Integer.MIN_VALUE;
                str7 = str5;
            }
            this.zzj.zzae();
            return new zzn(str, str2, str7, (long) i, str6, this.zzj.zzad().zzao(), this.zzj.zzz().zzc(context, str4), null, z, false, "", 0, this.zzj.zzad().zzr(str4) ? j : 0, 0, z2, z3, false, str3, null, 0, null);
        } catch (NameNotFoundException unused2) {
            this.zzj.zzab().zzgk().zza("Error retrieving newly installed package info. appId, appName", zzef.zzam(str), str5);
            return null;
        }
    }

    @WorkerThread
    final void zzb(zzjn zzjn, zzn zzn) {
        zzo();
        zzjj();
        if (!TextUtils.isEmpty(zzn.zzcg) || !TextUtils.isEmpty(zzn.zzcu)) {
            if (zzn.zzcq) {
                int zzbm = this.zzj.zzz().zzbm(zzjn.name);
                if (zzbm != 0) {
                    this.zzj.zzz();
                    this.zzj.zzz().zza(zzn.packageName, zzbm, "_ev", zzjs.zza(zzjn.name, 24, true), zzjn.name != null ? zzjn.name.length() : 0);
                    return;
                }
                int zzc = this.zzj.zzz().zzc(zzjn.name, zzjn.getValue());
                if (zzc != 0) {
                    this.zzj.zzz();
                    String zza = zzjs.zza(zzjn.name, 24, true);
                    Object value = zzjn.getValue();
                    int length = (value == null || !((value instanceof String) || (value instanceof CharSequence))) ? 0 : String.valueOf(value).length();
                    this.zzj.zzz().zza(zzn.packageName, zzc, "_ev", zza, length);
                    return;
                }
                Object zzd = this.zzj.zzz().zzd(zzjn.name, zzjn.getValue());
                if (zzd != null) {
                    zzjp zze;
                    if ("_sid".equals(zzjn.name) && this.zzj.zzad().zzw(zzn.packageName)) {
                        long j = zzjn.zztr;
                        String str = zzjn.origin;
                        long j2 = 0;
                        zze = zzgy().zze(zzn.packageName, "_sno");
                        if (zze == null || !(zze.value instanceof Long)) {
                            if (zze != null) {
                                this.zzj.zzab().zzgn().zza("Retrieved last session number from database does not contain a valid (long) value", zze.value);
                            }
                            if (this.zzj.zzad().zze(zzn.packageName, zzak.zzie)) {
                                zzae zzc2 = zzgy().zzc(zzn.packageName, "_s");
                                if (zzc2 != null) {
                                    j2 = zzc2.zzfg;
                                    this.zzj.zzab().zzgs().zza("Backfill the session number. Last used session number", Long.valueOf(j2));
                                }
                            }
                        } else {
                            j2 = ((Long) zze.value).longValue();
                        }
                        zzb(new zzjn("_sno", j, Long.valueOf(j2 + 1), str), zzn);
                    }
                    zze = new zzjp(zzn.packageName, zzjn.origin, zzjn.name, zzjn.zztr, zzd);
                    this.zzj.zzab().zzgr().zza("Setting user property", this.zzj.zzy().zzal(zze.name), zzd);
                    zzgy().beginTransaction();
                    try {
                        zzg(zzn);
                        boolean zza2 = zzgy().zza(zze);
                        zzgy().setTransactionSuccessful();
                        if (zza2) {
                            this.zzj.zzab().zzgr().zza("User property set", this.zzj.zzy().zzal(zze.name), zze.value);
                        } else {
                            this.zzj.zzab().zzgk().zza("Too many unique user properties are set. Ignoring user property", this.zzj.zzy().zzal(zze.name), zze.value);
                            this.zzj.zzz().zza(zzn.packageName, 9, null, null, 0);
                        }
                        zzgy().endTransaction();
                        return;
                    } catch (Throwable th) {
                        zzgy().endTransaction();
                    }
                } else {
                    return;
                }
            }
            zzg(zzn);
        }
    }

    @WorkerThread
    final void zzc(zzjn zzjn, zzn zzn) {
        zzo();
        zzjj();
        if (!TextUtils.isEmpty(zzn.zzcg) || !TextUtils.isEmpty(zzn.zzcu)) {
            if (zzn.zzcq) {
                String str = "User property removed";
                String str2 = "Removing user property";
                if (this.zzj.zzad().zze(zzn.packageName, zzak.zzij)) {
                    if (!"_npa".equals(zzjn.name) || zzn.zzcv == null) {
                        this.zzj.zzab().zzgr().zza(str2, this.zzj.zzy().zzal(zzjn.name));
                        zzgy().beginTransaction();
                        try {
                            zzg(zzn);
                            zzgy().zzd(zzn.packageName, zzjn.name);
                            zzgy().setTransactionSuccessful();
                            this.zzj.zzab().zzgr().zza(str, this.zzj.zzy().zzal(zzjn.name));
                            return;
                        } finally {
                            zzgy().endTransaction();
                        }
                    } else {
                        this.zzj.zzab().zzgr().zzao("Falling back to manifest metadata value for ad personalization");
                        zzb(new zzjn("_npa", this.zzj.zzx().currentTimeMillis(), Long.valueOf(zzn.zzcv.booleanValue() ? 1 : 0), "auto"), zzn);
                        return;
                    }
                }
                this.zzj.zzab().zzgr().zza(str2, this.zzj.zzy().zzal(zzjn.name));
                zzgy().beginTransaction();
                try {
                    zzg(zzn);
                    zzgy().zzd(zzn.packageName, zzjn.name);
                    zzgy().setTransactionSuccessful();
                    this.zzj.zzab().zzgr().zza(str, this.zzj.zzy().zzal(zzjn.name));
                } finally {
                    zzgy().endTransaction();
                }
            } else {
                zzg(zzn);
            }
        }
    }

    final void zzb(zzjh zzjh) {
        this.zzta++;
    }

    final void zzjs() {
        this.zztb++;
    }

    final zzfj zzjt() {
        return this.zzj;
    }

    /* JADX WARNING: Removed duplicated region for block: B:85:0x0273 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0266 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x04a7 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0285 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01f5 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0266 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0273 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0285 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x04a7 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0140 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01f5 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0273 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0266 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x04a7 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0285 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01f5 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0266 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0273 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0285 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x04a7 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01f5 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0273 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0266 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x04a7 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0285 A:{Catch:{ NameNotFoundException -> 0x0347, all -> 0x04d2 }} */
    @androidx.annotation.WorkerThread
    final void zzf(com.google.android.gms.measurement.internal.zzn r22) {
        /*
        r21 = this;
        r1 = r21;
        r2 = r22;
        r3 = "_sys";
        r4 = "_pfo";
        r5 = "_uwa";
        r0 = "app_id=?";
        r21.zzo();
        r21.zzjj();
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r22);
        r6 = r2.packageName;
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r6);
        r6 = r2.zzcg;
        r6 = android.text.TextUtils.isEmpty(r6);
        if (r6 == 0) goto L_0x002b;
    L_0x0022:
        r6 = r2.zzcu;
        r6 = android.text.TextUtils.isEmpty(r6);
        if (r6 == 0) goto L_0x002b;
    L_0x002a:
        return;
    L_0x002b:
        r6 = r21.zzgy();
        r7 = r2.packageName;
        r6 = r6.zzab(r7);
        r7 = 0;
        if (r6 == 0) goto L_0x005e;
    L_0x0039:
        r9 = r6.getGmpAppId();
        r9 = android.text.TextUtils.isEmpty(r9);
        if (r9 == 0) goto L_0x005e;
    L_0x0043:
        r9 = r2.zzcg;
        r9 = android.text.TextUtils.isEmpty(r9);
        if (r9 != 0) goto L_0x005e;
    L_0x004b:
        r6.zzl(r7);
        r9 = r21.zzgy();
        r9.zza(r6);
        r6 = r21.zzgz();
        r9 = r2.packageName;
        r6.zzaz(r9);
    L_0x005e:
        r6 = r2.zzcq;
        if (r6 != 0) goto L_0x0066;
    L_0x0062:
        r21.zzg(r22);
        return;
    L_0x0066:
        r9 = r2.zzdr;
        r6 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1));
        if (r6 != 0) goto L_0x0076;
    L_0x006c:
        r6 = r1.zzj;
        r6 = r6.zzx();
        r9 = r6.currentTimeMillis();
    L_0x0076:
        r6 = r1.zzj;
        r6 = r6.zzad();
        r11 = r2.packageName;
        r12 = com.google.android.gms.measurement.internal.zzak.zzij;
        r6 = r6.zze(r11, r12);
        if (r6 == 0) goto L_0x008f;
    L_0x0086:
        r6 = r1.zzj;
        r6 = r6.zzw();
        r6.zzct();
    L_0x008f:
        r6 = r2.zzds;
        r15 = 0;
        r13 = 1;
        if (r6 == 0) goto L_0x00b1;
    L_0x0095:
        if (r6 == r13) goto L_0x00b1;
    L_0x0097:
        r11 = r1.zzj;
        r11 = r11.zzab();
        r11 = r11.zzgn();
        r12 = r2.packageName;
        r12 = com.google.android.gms.measurement.internal.zzef.zzam(r12);
        r6 = java.lang.Integer.valueOf(r6);
        r14 = "Incorrect app type, assuming installed app. appId, appType";
        r11.zza(r14, r12, r6);
        r6 = 0;
    L_0x00b1:
        r11 = r21.zzgy();
        r11.beginTransaction();
        r11 = r1.zzj;	 Catch:{ all -> 0x04d2 }
        r11 = r11.zzad();	 Catch:{ all -> 0x04d2 }
        r12 = r2.packageName;	 Catch:{ all -> 0x04d2 }
        r14 = com.google.android.gms.measurement.internal.zzak.zzij;	 Catch:{ all -> 0x04d2 }
        r11 = r11.zze(r12, r14);	 Catch:{ all -> 0x04d2 }
        if (r11 == 0) goto L_0x0131;
    L_0x00c8:
        r11 = r21.zzgy();	 Catch:{ all -> 0x04d2 }
        r12 = r2.packageName;	 Catch:{ all -> 0x04d2 }
        r14 = "_npa";
        r14 = r11.zze(r12, r14);	 Catch:{ all -> 0x04d2 }
        if (r14 == 0) goto L_0x00e0;
    L_0x00d6:
        r11 = "auto";
        r12 = r14.origin;	 Catch:{ all -> 0x04d2 }
        r11 = r11.equals(r12);	 Catch:{ all -> 0x04d2 }
        if (r11 == 0) goto L_0x0131;
    L_0x00e0:
        r11 = r2.zzcv;	 Catch:{ all -> 0x04d2 }
        if (r11 == 0) goto L_0x011b;
    L_0x00e4:
        r12 = new com.google.android.gms.measurement.internal.zzjn;	 Catch:{ all -> 0x04d2 }
        r16 = "_npa";
        r11 = r2.zzcv;	 Catch:{ all -> 0x04d2 }
        r11 = r11.booleanValue();	 Catch:{ all -> 0x04d2 }
        if (r11 == 0) goto L_0x00f3;
    L_0x00f0:
        r17 = 1;
        goto L_0x00f5;
    L_0x00f3:
        r17 = 0;
    L_0x00f5:
        r17 = java.lang.Long.valueOf(r17);	 Catch:{ all -> 0x04d2 }
        r18 = "auto";
        r11 = r12;
        r7 = r12;
        r12 = r16;
        r19 = r3;
        r8 = r14;
        r3 = 1;
        r13 = r9;
        r15 = r17;
        r16 = r18;
        r11.<init>(r12, r13, r15, r16);	 Catch:{ all -> 0x04d2 }
        if (r8 == 0) goto L_0x0117;
    L_0x010d:
        r8 = r8.value;	 Catch:{ all -> 0x04d2 }
        r11 = r7.zzts;	 Catch:{ all -> 0x04d2 }
        r8 = r8.equals(r11);	 Catch:{ all -> 0x04d2 }
        if (r8 != 0) goto L_0x0134;
    L_0x0117:
        r1.zzb(r7, r2);	 Catch:{ all -> 0x04d2 }
        goto L_0x0134;
    L_0x011b:
        r19 = r3;
        r8 = r14;
        r3 = 1;
        if (r8 == 0) goto L_0x0134;
    L_0x0121:
        r7 = new com.google.android.gms.measurement.internal.zzjn;	 Catch:{ all -> 0x04d2 }
        r12 = "_npa";
        r15 = 0;
        r16 = "auto";
        r11 = r7;
        r13 = r9;
        r11.<init>(r12, r13, r15, r16);	 Catch:{ all -> 0x04d2 }
        r1.zzc(r7, r2);	 Catch:{ all -> 0x04d2 }
        goto L_0x0134;
    L_0x0131:
        r19 = r3;
        r3 = 1;
    L_0x0134:
        r7 = r21.zzgy();	 Catch:{ all -> 0x04d2 }
        r8 = r2.packageName;	 Catch:{ all -> 0x04d2 }
        r7 = r7.zzab(r8);	 Catch:{ all -> 0x04d2 }
        if (r7 == 0) goto L_0x01f2;
    L_0x0140:
        r11 = r1.zzj;	 Catch:{ all -> 0x04d2 }
        r11.zzz();	 Catch:{ all -> 0x04d2 }
        r11 = r2.zzcg;	 Catch:{ all -> 0x04d2 }
        r12 = r7.getGmpAppId();	 Catch:{ all -> 0x04d2 }
        r13 = r2.zzcu;	 Catch:{ all -> 0x04d2 }
        r14 = r7.zzah();	 Catch:{ all -> 0x04d2 }
        r11 = com.google.android.gms.measurement.internal.zzjs.zza(r11, r12, r13, r14);	 Catch:{ all -> 0x04d2 }
        if (r11 == 0) goto L_0x01f2;
    L_0x0157:
        r11 = r1.zzj;	 Catch:{ all -> 0x04d2 }
        r11 = r11.zzab();	 Catch:{ all -> 0x04d2 }
        r11 = r11.zzgn();	 Catch:{ all -> 0x04d2 }
        r12 = "New GMP App Id passed in. Removing cached database data. appId";
        r13 = r7.zzag();	 Catch:{ all -> 0x04d2 }
        r13 = com.google.android.gms.measurement.internal.zzef.zzam(r13);	 Catch:{ all -> 0x04d2 }
        r11.zza(r12, r13);	 Catch:{ all -> 0x04d2 }
        r11 = r21.zzgy();	 Catch:{ all -> 0x04d2 }
        r7 = r7.zzag();	 Catch:{ all -> 0x04d2 }
        r11.zzbi();	 Catch:{ all -> 0x04d2 }
        r11.zzo();	 Catch:{ all -> 0x04d2 }
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r7);	 Catch:{ all -> 0x04d2 }
        r12 = r11.getWritableDatabase();	 Catch:{ SQLiteException -> 0x01dd }
        r13 = new java.lang.String[r3];	 Catch:{ SQLiteException -> 0x01dd }
        r15 = 0;
        r13[r15] = r7;	 Catch:{ SQLiteException -> 0x01db }
        r14 = "events";
        r14 = r12.delete(r14, r0, r13);	 Catch:{ SQLiteException -> 0x01db }
        r14 = r14 + r15;
        r8 = "user_attributes";
        r8 = r12.delete(r8, r0, r13);	 Catch:{ SQLiteException -> 0x01db }
        r14 = r14 + r8;
        r8 = "conditional_properties";
        r8 = r12.delete(r8, r0, r13);	 Catch:{ SQLiteException -> 0x01db }
        r14 = r14 + r8;
        r8 = "apps";
        r8 = r12.delete(r8, r0, r13);	 Catch:{ SQLiteException -> 0x01db }
        r14 = r14 + r8;
        r8 = "raw_events";
        r8 = r12.delete(r8, r0, r13);	 Catch:{ SQLiteException -> 0x01db }
        r14 = r14 + r8;
        r8 = "raw_events_metadata";
        r8 = r12.delete(r8, r0, r13);	 Catch:{ SQLiteException -> 0x01db }
        r14 = r14 + r8;
        r8 = "event_filters";
        r8 = r12.delete(r8, r0, r13);	 Catch:{ SQLiteException -> 0x01db }
        r14 = r14 + r8;
        r8 = "property_filters";
        r8 = r12.delete(r8, r0, r13);	 Catch:{ SQLiteException -> 0x01db }
        r14 = r14 + r8;
        r8 = "audience_filter_values";
        r0 = r12.delete(r8, r0, r13);	 Catch:{ SQLiteException -> 0x01db }
        r14 = r14 + r0;
        if (r14 <= 0) goto L_0x01f0;
    L_0x01c9:
        r0 = r11.zzab();	 Catch:{ SQLiteException -> 0x01db }
        r0 = r0.zzgs();	 Catch:{ SQLiteException -> 0x01db }
        r8 = "Deleted application data. app, records";
        r12 = java.lang.Integer.valueOf(r14);	 Catch:{ SQLiteException -> 0x01db }
        r0.zza(r8, r7, r12);	 Catch:{ SQLiteException -> 0x01db }
        goto L_0x01f0;
    L_0x01db:
        r0 = move-exception;
        goto L_0x01df;
    L_0x01dd:
        r0 = move-exception;
        r15 = 0;
    L_0x01df:
        r8 = r11.zzab();	 Catch:{ all -> 0x04d2 }
        r8 = r8.zzgk();	 Catch:{ all -> 0x04d2 }
        r11 = "Error deleting application data. appId, error";
        r7 = com.google.android.gms.measurement.internal.zzef.zzam(r7);	 Catch:{ all -> 0x04d2 }
        r8.zza(r11, r7, r0);	 Catch:{ all -> 0x04d2 }
    L_0x01f0:
        r7 = 0;
        goto L_0x01f3;
    L_0x01f2:
        r15 = 0;
    L_0x01f3:
        if (r7 == 0) goto L_0x0260;
    L_0x01f5:
        r11 = r7.zzam();	 Catch:{ all -> 0x04d2 }
        r13 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r0 = "_pv";
        r8 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1));
        if (r8 == 0) goto L_0x022d;
    L_0x0202:
        r11 = r7.zzam();	 Catch:{ all -> 0x04d2 }
        r13 = r2.zzcn;	 Catch:{ all -> 0x04d2 }
        r8 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1));
        if (r8 == 0) goto L_0x0260;
    L_0x020c:
        r8 = new android.os.Bundle;	 Catch:{ all -> 0x04d2 }
        r8.<init>();	 Catch:{ all -> 0x04d2 }
        r7 = r7.zzal();	 Catch:{ all -> 0x04d2 }
        r8.putString(r0, r7);	 Catch:{ all -> 0x04d2 }
        r0 = new com.google.android.gms.measurement.internal.zzai;	 Catch:{ all -> 0x04d2 }
        r12 = "_au";
        r13 = new com.google.android.gms.measurement.internal.zzah;	 Catch:{ all -> 0x04d2 }
        r13.<init>(r8);	 Catch:{ all -> 0x04d2 }
        r14 = "auto";
        r11 = r0;
        r8 = 0;
        r15 = r9;
        r11.<init>(r12, r13, r14, r15);	 Catch:{ all -> 0x04d2 }
        r1.zzc(r0, r2);	 Catch:{ all -> 0x04d2 }
        goto L_0x0261;
    L_0x022d:
        r8 = 0;
        r11 = r7.zzal();	 Catch:{ all -> 0x04d2 }
        if (r11 == 0) goto L_0x0261;
    L_0x0234:
        r11 = r7.zzal();	 Catch:{ all -> 0x04d2 }
        r12 = r2.zzcm;	 Catch:{ all -> 0x04d2 }
        r11 = r11.equals(r12);	 Catch:{ all -> 0x04d2 }
        if (r11 != 0) goto L_0x0261;
    L_0x0240:
        r11 = new android.os.Bundle;	 Catch:{ all -> 0x04d2 }
        r11.<init>();	 Catch:{ all -> 0x04d2 }
        r7 = r7.zzal();	 Catch:{ all -> 0x04d2 }
        r11.putString(r0, r7);	 Catch:{ all -> 0x04d2 }
        r0 = new com.google.android.gms.measurement.internal.zzai;	 Catch:{ all -> 0x04d2 }
        r12 = "_au";
        r13 = new com.google.android.gms.measurement.internal.zzah;	 Catch:{ all -> 0x04d2 }
        r13.<init>(r11);	 Catch:{ all -> 0x04d2 }
        r14 = "auto";
        r11 = r0;
        r15 = r9;
        r11.<init>(r12, r13, r14, r15);	 Catch:{ all -> 0x04d2 }
        r1.zzc(r0, r2);	 Catch:{ all -> 0x04d2 }
        goto L_0x0261;
    L_0x0260:
        r8 = 0;
    L_0x0261:
        r21.zzg(r22);	 Catch:{ all -> 0x04d2 }
        if (r6 != 0) goto L_0x0273;
    L_0x0266:
        r0 = r21.zzgy();	 Catch:{ all -> 0x04d2 }
        r7 = r2.packageName;	 Catch:{ all -> 0x04d2 }
        r11 = "_f";
        r0 = r0.zzc(r7, r11);	 Catch:{ all -> 0x04d2 }
        goto L_0x0283;
    L_0x0273:
        if (r6 != r3) goto L_0x0282;
    L_0x0275:
        r0 = r21.zzgy();	 Catch:{ all -> 0x04d2 }
        r7 = r2.packageName;	 Catch:{ all -> 0x04d2 }
        r11 = "_v";
        r0 = r0.zzc(r7, r11);	 Catch:{ all -> 0x04d2 }
        goto L_0x0283;
    L_0x0282:
        r0 = 0;
    L_0x0283:
        if (r0 != 0) goto L_0x04a7;
    L_0x0285:
        r11 = 3600000; // 0x36ee80 float:5.044674E-39 double:1.7786363E-317;
        r13 = r9 / r11;
        r15 = 1;
        r13 = r13 + r15;
        r13 = r13 * r11;
        r0 = "_dac";
        r7 = "_r";
        r15 = "_c";
        r12 = "_et";
        if (r6 != 0) goto L_0x040a;
    L_0x0299:
        r6 = new com.google.android.gms.measurement.internal.zzjn;	 Catch:{ all -> 0x04d2 }
        r16 = "_fot";
        r18 = java.lang.Long.valueOf(r13);	 Catch:{ all -> 0x04d2 }
        r20 = "auto";
        r11 = r6;
        r13 = r12;
        r12 = r16;
        r3 = r13;
        r13 = r9;
        r8 = r15;
        r15 = r18;
        r16 = r20;
        r11.<init>(r12, r13, r15, r16);	 Catch:{ all -> 0x04d2 }
        r1.zzb(r6, r2);	 Catch:{ all -> 0x04d2 }
        r6 = r1.zzj;	 Catch:{ all -> 0x04d2 }
        r6 = r6.zzad();	 Catch:{ all -> 0x04d2 }
        r11 = r2.zzcg;	 Catch:{ all -> 0x04d2 }
        r6 = r6.zzt(r11);	 Catch:{ all -> 0x04d2 }
        if (r6 == 0) goto L_0x02d0;
    L_0x02c2:
        r21.zzo();	 Catch:{ all -> 0x04d2 }
        r6 = r1.zzj;	 Catch:{ all -> 0x04d2 }
        r6 = r6.zzht();	 Catch:{ all -> 0x04d2 }
        r11 = r2.packageName;	 Catch:{ all -> 0x04d2 }
        r6.zzat(r11);	 Catch:{ all -> 0x04d2 }
    L_0x02d0:
        r21.zzo();	 Catch:{ all -> 0x04d2 }
        r21.zzjj();	 Catch:{ all -> 0x04d2 }
        r6 = new android.os.Bundle;	 Catch:{ all -> 0x04d2 }
        r6.<init>();	 Catch:{ all -> 0x04d2 }
        r11 = 1;
        r6.putLong(r8, r11);	 Catch:{ all -> 0x04d2 }
        r6.putLong(r7, r11);	 Catch:{ all -> 0x04d2 }
        r7 = 0;
        r6.putLong(r5, r7);	 Catch:{ all -> 0x04d2 }
        r6.putLong(r4, r7);	 Catch:{ all -> 0x04d2 }
        r15 = r19;
        r6.putLong(r15, r7);	 Catch:{ all -> 0x04d2 }
        r11 = "_sysu";
        r6.putLong(r11, r7);	 Catch:{ all -> 0x04d2 }
        r7 = r1.zzj;	 Catch:{ all -> 0x04d2 }
        r7 = r7.zzad();	 Catch:{ all -> 0x04d2 }
        r8 = r2.packageName;	 Catch:{ all -> 0x04d2 }
        r7 = r7.zzz(r8);	 Catch:{ all -> 0x04d2 }
        if (r7 == 0) goto L_0x0309;
    L_0x0303:
        r7 = 1;
        r6.putLong(r3, r7);	 Catch:{ all -> 0x04d2 }
        goto L_0x030b;
    L_0x0309:
        r7 = 1;
    L_0x030b:
        r11 = r2.zzdt;	 Catch:{ all -> 0x04d2 }
        if (r11 == 0) goto L_0x0312;
    L_0x030f:
        r6.putLong(r0, r7);	 Catch:{ all -> 0x04d2 }
    L_0x0312:
        r0 = r1.zzj;	 Catch:{ all -> 0x04d2 }
        r0 = r0.getContext();	 Catch:{ all -> 0x04d2 }
        r0 = r0.getPackageManager();	 Catch:{ all -> 0x04d2 }
        if (r0 != 0) goto L_0x0335;
    L_0x031e:
        r0 = r1.zzj;	 Catch:{ all -> 0x04d2 }
        r0 = r0.zzab();	 Catch:{ all -> 0x04d2 }
        r0 = r0.zzgk();	 Catch:{ all -> 0x04d2 }
        r5 = "PackageManager is null, first open report might be inaccurate. appId";
        r7 = r2.packageName;	 Catch:{ all -> 0x04d2 }
        r7 = com.google.android.gms.measurement.internal.zzef.zzam(r7);	 Catch:{ all -> 0x04d2 }
        r0.zza(r5, r7);	 Catch:{ all -> 0x04d2 }
        goto L_0x03d8;
    L_0x0335:
        r0 = r1.zzj;	 Catch:{ NameNotFoundException -> 0x0347 }
        r0 = r0.getContext();	 Catch:{ NameNotFoundException -> 0x0347 }
        r0 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r0);	 Catch:{ NameNotFoundException -> 0x0347 }
        r7 = r2.packageName;	 Catch:{ NameNotFoundException -> 0x0347 }
        r8 = 0;
        r0 = r0.getPackageInfo(r7, r8);	 Catch:{ NameNotFoundException -> 0x0347 }
        goto L_0x035e;
    L_0x0347:
        r0 = move-exception;
        r7 = r1.zzj;	 Catch:{ all -> 0x04d2 }
        r7 = r7.zzab();	 Catch:{ all -> 0x04d2 }
        r7 = r7.zzgk();	 Catch:{ all -> 0x04d2 }
        r8 = "Package info is null, first open report might be inaccurate. appId";
        r11 = r2.packageName;	 Catch:{ all -> 0x04d2 }
        r11 = com.google.android.gms.measurement.internal.zzef.zzam(r11);	 Catch:{ all -> 0x04d2 }
        r7.zza(r8, r11, r0);	 Catch:{ all -> 0x04d2 }
        r0 = 0;
    L_0x035e:
        if (r0 == 0) goto L_0x0394;
    L_0x0360:
        r7 = r0.firstInstallTime;	 Catch:{ all -> 0x04d2 }
        r11 = 0;
        r13 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1));
        if (r13 == 0) goto L_0x0394;
    L_0x0368:
        r7 = r0.firstInstallTime;	 Catch:{ all -> 0x04d2 }
        r11 = r0.lastUpdateTime;	 Catch:{ all -> 0x04d2 }
        r0 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1));
        if (r0 == 0) goto L_0x0377;
    L_0x0370:
        r7 = 1;
        r6.putLong(r5, r7);	 Catch:{ all -> 0x04d2 }
        r0 = 0;
        goto L_0x0378;
    L_0x0377:
        r0 = 1;
    L_0x0378:
        r5 = new com.google.android.gms.measurement.internal.zzjn;	 Catch:{ all -> 0x04d2 }
        r12 = "_fi";
        if (r0 == 0) goto L_0x0381;
    L_0x037e:
        r7 = 1;
        goto L_0x0383;
    L_0x0381:
        r7 = 0;
    L_0x0383:
        r0 = java.lang.Long.valueOf(r7);	 Catch:{ all -> 0x04d2 }
        r16 = "auto";
        r11 = r5;
        r13 = r9;
        r7 = r15;
        r15 = r0;
        r11.<init>(r12, r13, r15, r16);	 Catch:{ all -> 0x04d2 }
        r1.zzb(r5, r2);	 Catch:{ all -> 0x04d2 }
        goto L_0x0395;
    L_0x0394:
        r7 = r15;
    L_0x0395:
        r0 = r1.zzj;	 Catch:{ NameNotFoundException -> 0x03a7 }
        r0 = r0.getContext();	 Catch:{ NameNotFoundException -> 0x03a7 }
        r0 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r0);	 Catch:{ NameNotFoundException -> 0x03a7 }
        r5 = r2.packageName;	 Catch:{ NameNotFoundException -> 0x03a7 }
        r8 = 0;
        r8 = r0.getApplicationInfo(r5, r8);	 Catch:{ NameNotFoundException -> 0x03a7 }
        goto L_0x03be;
    L_0x03a7:
        r0 = move-exception;
        r5 = r1.zzj;	 Catch:{ all -> 0x04d2 }
        r5 = r5.zzab();	 Catch:{ all -> 0x04d2 }
        r5 = r5.zzgk();	 Catch:{ all -> 0x04d2 }
        r8 = "Application info is null, first open report might be inaccurate. appId";
        r11 = r2.packageName;	 Catch:{ all -> 0x04d2 }
        r11 = com.google.android.gms.measurement.internal.zzef.zzam(r11);	 Catch:{ all -> 0x04d2 }
        r5.zza(r8, r11, r0);	 Catch:{ all -> 0x04d2 }
        r8 = 0;
    L_0x03be:
        if (r8 == 0) goto L_0x03d8;
    L_0x03c0:
        r0 = r8.flags;	 Catch:{ all -> 0x04d2 }
        r5 = 1;
        r0 = r0 & r5;
        if (r0 == 0) goto L_0x03cb;
    L_0x03c6:
        r11 = 1;
        r6.putLong(r7, r11);	 Catch:{ all -> 0x04d2 }
    L_0x03cb:
        r0 = r8.flags;	 Catch:{ all -> 0x04d2 }
        r0 = r0 & 128;
        if (r0 == 0) goto L_0x03d8;
    L_0x03d1:
        r0 = "_sysu";
        r7 = 1;
        r6.putLong(r0, r7);	 Catch:{ all -> 0x04d2 }
    L_0x03d8:
        r0 = r21.zzgy();	 Catch:{ all -> 0x04d2 }
        r5 = r2.packageName;	 Catch:{ all -> 0x04d2 }
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r5);	 Catch:{ all -> 0x04d2 }
        r0.zzo();	 Catch:{ all -> 0x04d2 }
        r0.zzbi();	 Catch:{ all -> 0x04d2 }
        r7 = "first_open_count";
        r7 = r0.zzj(r5, r7);	 Catch:{ all -> 0x04d2 }
        r11 = 0;
        r0 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1));
        if (r0 < 0) goto L_0x03f6;
    L_0x03f3:
        r6.putLong(r4, r7);	 Catch:{ all -> 0x04d2 }
    L_0x03f6:
        r0 = new com.google.android.gms.measurement.internal.zzai;	 Catch:{ all -> 0x04d2 }
        r12 = "_f";
        r13 = new com.google.android.gms.measurement.internal.zzah;	 Catch:{ all -> 0x04d2 }
        r13.<init>(r6);	 Catch:{ all -> 0x04d2 }
        r14 = "auto";
        r11 = r0;
        r15 = r9;
        r11.<init>(r12, r13, r14, r15);	 Catch:{ all -> 0x04d2 }
        r1.zzc(r0, r2);	 Catch:{ all -> 0x04d2 }
        goto L_0x0464;
    L_0x040a:
        r3 = r12;
        r8 = r15;
        r4 = 1;
        if (r6 != r4) goto L_0x0464;
    L_0x040f:
        r4 = new com.google.android.gms.measurement.internal.zzjn;	 Catch:{ all -> 0x04d2 }
        r12 = "_fvt";
        r15 = java.lang.Long.valueOf(r13);	 Catch:{ all -> 0x04d2 }
        r16 = "auto";
        r11 = r4;
        r13 = r9;
        r11.<init>(r12, r13, r15, r16);	 Catch:{ all -> 0x04d2 }
        r1.zzb(r4, r2);	 Catch:{ all -> 0x04d2 }
        r21.zzo();	 Catch:{ all -> 0x04d2 }
        r21.zzjj();	 Catch:{ all -> 0x04d2 }
        r4 = new android.os.Bundle;	 Catch:{ all -> 0x04d2 }
        r4.<init>();	 Catch:{ all -> 0x04d2 }
        r5 = 1;
        r4.putLong(r8, r5);	 Catch:{ all -> 0x04d2 }
        r4.putLong(r7, r5);	 Catch:{ all -> 0x04d2 }
        r5 = r1.zzj;	 Catch:{ all -> 0x04d2 }
        r5 = r5.zzad();	 Catch:{ all -> 0x04d2 }
        r6 = r2.packageName;	 Catch:{ all -> 0x04d2 }
        r5 = r5.zzz(r6);	 Catch:{ all -> 0x04d2 }
        if (r5 == 0) goto L_0x0448;
    L_0x0442:
        r5 = 1;
        r4.putLong(r3, r5);	 Catch:{ all -> 0x04d2 }
        goto L_0x044a;
    L_0x0448:
        r5 = 1;
    L_0x044a:
        r7 = r2.zzdt;	 Catch:{ all -> 0x04d2 }
        if (r7 == 0) goto L_0x0451;
    L_0x044e:
        r4.putLong(r0, r5);	 Catch:{ all -> 0x04d2 }
    L_0x0451:
        r0 = new com.google.android.gms.measurement.internal.zzai;	 Catch:{ all -> 0x04d2 }
        r12 = "_v";
        r13 = new com.google.android.gms.measurement.internal.zzah;	 Catch:{ all -> 0x04d2 }
        r13.<init>(r4);	 Catch:{ all -> 0x04d2 }
        r14 = "auto";
        r11 = r0;
        r15 = r9;
        r11.<init>(r12, r13, r14, r15);	 Catch:{ all -> 0x04d2 }
        r1.zzc(r0, r2);	 Catch:{ all -> 0x04d2 }
    L_0x0464:
        r0 = r1.zzj;	 Catch:{ all -> 0x04d2 }
        r0 = r0.zzad();	 Catch:{ all -> 0x04d2 }
        r4 = r2.packageName;	 Catch:{ all -> 0x04d2 }
        r5 = com.google.android.gms.measurement.internal.zzak.zzii;	 Catch:{ all -> 0x04d2 }
        r0 = r0.zze(r4, r5);	 Catch:{ all -> 0x04d2 }
        if (r0 != 0) goto L_0x04c3;
    L_0x0474:
        r0 = new android.os.Bundle;	 Catch:{ all -> 0x04d2 }
        r0.<init>();	 Catch:{ all -> 0x04d2 }
        r4 = 1;
        r0.putLong(r3, r4);	 Catch:{ all -> 0x04d2 }
        r3 = r1.zzj;	 Catch:{ all -> 0x04d2 }
        r3 = r3.zzad();	 Catch:{ all -> 0x04d2 }
        r4 = r2.packageName;	 Catch:{ all -> 0x04d2 }
        r3 = r3.zzz(r4);	 Catch:{ all -> 0x04d2 }
        if (r3 == 0) goto L_0x0493;
    L_0x048c:
        r3 = "_fr";
        r4 = 1;
        r0.putLong(r3, r4);	 Catch:{ all -> 0x04d2 }
    L_0x0493:
        r3 = new com.google.android.gms.measurement.internal.zzai;	 Catch:{ all -> 0x04d2 }
        r12 = "_e";
        r13 = new com.google.android.gms.measurement.internal.zzah;	 Catch:{ all -> 0x04d2 }
        r13.<init>(r0);	 Catch:{ all -> 0x04d2 }
        r14 = "auto";
        r11 = r3;
        r15 = r9;
        r11.<init>(r12, r13, r14, r15);	 Catch:{ all -> 0x04d2 }
        r1.zzc(r3, r2);	 Catch:{ all -> 0x04d2 }
        goto L_0x04c3;
    L_0x04a7:
        r0 = r2.zzdq;	 Catch:{ all -> 0x04d2 }
        if (r0 == 0) goto L_0x04c3;
    L_0x04ab:
        r0 = new android.os.Bundle;	 Catch:{ all -> 0x04d2 }
        r0.<init>();	 Catch:{ all -> 0x04d2 }
        r3 = new com.google.android.gms.measurement.internal.zzai;	 Catch:{ all -> 0x04d2 }
        r12 = "_cd";
        r13 = new com.google.android.gms.measurement.internal.zzah;	 Catch:{ all -> 0x04d2 }
        r13.<init>(r0);	 Catch:{ all -> 0x04d2 }
        r14 = "auto";
        r11 = r3;
        r15 = r9;
        r11.<init>(r12, r13, r14, r15);	 Catch:{ all -> 0x04d2 }
        r1.zzc(r3, r2);	 Catch:{ all -> 0x04d2 }
    L_0x04c3:
        r0 = r21.zzgy();	 Catch:{ all -> 0x04d2 }
        r0.setTransactionSuccessful();	 Catch:{ all -> 0x04d2 }
        r0 = r21.zzgy();
        r0.endTransaction();
        return;
    L_0x04d2:
        r0 = move-exception;
        r2 = r21.zzgy();
        r2.endTransaction();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzjg.zzf(com.google.android.gms.measurement.internal.zzn):void");
    }

    @WorkerThread
    private final zzn zzbi(String str) {
        String str2 = str;
        zzf zzab = zzgy().zzab(str2);
        if (zzab == null || TextUtils.isEmpty(zzab.zzal())) {
            this.zzj.zzab().zzgr().zza("No app data available; dropping", str2);
            return null;
        }
        Boolean zzc = zzc(zzab);
        if (zzc == null || zzc.booleanValue()) {
            zzf zzf = zzab;
            return new zzn(str, zzab.getGmpAppId(), zzab.zzal(), zzab.zzam(), zzab.zzan(), zzab.zzao(), zzab.zzap(), null, zzab.isMeasurementEnabled(), false, zzab.getFirebaseInstanceId(), zzf.zzbd(), 0, 0, zzf.zzbe(), zzf.zzbf(), false, zzf.zzah(), zzf.zzbg(), zzf.zzaq(), zzf.zzbh());
        }
        this.zzj.zzab().zzgk().zza("App version does not match; dropping. appId", zzef.zzam(str));
        return null;
    }

    @WorkerThread
    final void zze(zzq zzq) {
        zzn zzbi = zzbi(zzq.packageName);
        if (zzbi != null) {
            zzb(zzq, zzbi);
        }
    }

    @WorkerThread
    final void zzb(zzq zzq, zzn zzn) {
        Preconditions.checkNotNull(zzq);
        Preconditions.checkNotEmpty(zzq.packageName);
        Preconditions.checkNotNull(zzq.origin);
        Preconditions.checkNotNull(zzq.zzdw);
        Preconditions.checkNotEmpty(zzq.zzdw.name);
        zzo();
        zzjj();
        if (!TextUtils.isEmpty(zzn.zzcg) || !TextUtils.isEmpty(zzn.zzcu)) {
            if (zzn.zzcq) {
                zzq zzq2 = new zzq(zzq);
                boolean z = false;
                zzq2.active = false;
                zzgy().beginTransaction();
                try {
                    zzq zzf = zzgy().zzf(zzq2.packageName, zzq2.zzdw.name);
                    if (!(zzf == null || zzf.origin.equals(zzq2.origin))) {
                        this.zzj.zzab().zzgn().zza("Updating a conditional user property with different origin. name, origin, origin (from DB)", this.zzj.zzy().zzal(zzq2.zzdw.name), zzq2.origin, zzf.origin);
                    }
                    if (zzf != null && zzf.active) {
                        zzq2.origin = zzf.origin;
                        zzq2.creationTimestamp = zzf.creationTimestamp;
                        zzq2.triggerTimeout = zzf.triggerTimeout;
                        zzq2.triggerEventName = zzf.triggerEventName;
                        zzq2.zzdy = zzf.zzdy;
                        zzq2.active = zzf.active;
                        zzq2.zzdw = new zzjn(zzq2.zzdw.name, zzf.zzdw.zztr, zzq2.zzdw.getValue(), zzf.zzdw.origin);
                    } else if (TextUtils.isEmpty(zzq2.triggerEventName)) {
                        zzq2.zzdw = new zzjn(zzq2.zzdw.name, zzq2.creationTimestamp, zzq2.zzdw.getValue(), zzq2.zzdw.origin);
                        zzq2.active = true;
                        z = true;
                    }
                    if (zzq2.active) {
                        zzjn zzjn = zzq2.zzdw;
                        zzjp zzjp = new zzjp(zzq2.packageName, zzq2.origin, zzjn.name, zzjn.zztr, zzjn.getValue());
                        if (zzgy().zza(zzjp)) {
                            this.zzj.zzab().zzgr().zza("User property updated immediately", zzq2.packageName, this.zzj.zzy().zzal(zzjp.name), zzjp.value);
                        } else {
                            this.zzj.zzab().zzgk().zza("(2)Too many active user properties, ignoring", zzef.zzam(zzq2.packageName), this.zzj.zzy().zzal(zzjp.name), zzjp.value);
                        }
                        if (z && zzq2.zzdy != null) {
                            zzd(new zzai(zzq2.zzdy, zzq2.creationTimestamp), zzn);
                        }
                    }
                    if (zzgy().zza(zzq2)) {
                        this.zzj.zzab().zzgr().zza("Conditional property added", zzq2.packageName, this.zzj.zzy().zzal(zzq2.zzdw.name), zzq2.zzdw.getValue());
                    } else {
                        this.zzj.zzab().zzgk().zza("Too many conditional properties, ignoring", zzef.zzam(zzq2.packageName), this.zzj.zzy().zzal(zzq2.zzdw.name), zzq2.zzdw.getValue());
                    }
                    zzgy().setTransactionSuccessful();
                } finally {
                    zzgy().endTransaction();
                }
            } else {
                zzg(zzn);
            }
        }
    }

    @WorkerThread
    final void zzf(zzq zzq) {
        zzn zzbi = zzbi(zzq.packageName);
        if (zzbi != null) {
            zzc(zzq, zzbi);
        }
    }

    @WorkerThread
    final void zzc(zzq zzq, zzn zzn) {
        Preconditions.checkNotNull(zzq);
        Preconditions.checkNotEmpty(zzq.packageName);
        Preconditions.checkNotNull(zzq.zzdw);
        Preconditions.checkNotEmpty(zzq.zzdw.name);
        zzo();
        zzjj();
        if (!TextUtils.isEmpty(zzn.zzcg) || !TextUtils.isEmpty(zzn.zzcu)) {
            if (zzn.zzcq) {
                zzgy().beginTransaction();
                try {
                    zzg(zzn);
                    zzq zzf = zzgy().zzf(zzq.packageName, zzq.zzdw.name);
                    if (zzf != null) {
                        this.zzj.zzab().zzgr().zza("Removing conditional user property", zzq.packageName, this.zzj.zzy().zzal(zzq.zzdw.name));
                        zzgy().zzg(zzq.packageName, zzq.zzdw.name);
                        if (zzf.active) {
                            zzgy().zzd(zzq.packageName, zzq.zzdw.name);
                        }
                        if (zzq.zzdz != null) {
                            Bundle bundle = null;
                            if (zzq.zzdz.zzfq != null) {
                                bundle = zzq.zzdz.zzfq.zzcv();
                            }
                            Bundle bundle2 = bundle;
                            zzd(this.zzj.zzz().zza(zzq.packageName, zzq.zzdz.name, bundle2, zzf.origin, zzq.zzdz.zzfu, true, false), zzn);
                        }
                    } else {
                        this.zzj.zzab().zzgn().zza("Conditional user property doesn't exist", zzef.zzam(zzq.packageName), this.zzj.zzy().zzal(zzq.zzdw.name));
                    }
                    zzgy().setTransactionSuccessful();
                } finally {
                    zzgy().endTransaction();
                }
            } else {
                zzg(zzn);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00fe  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x010c  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0136  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0144  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0152  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x018e  */
    @androidx.annotation.WorkerThread
    private final com.google.android.gms.measurement.internal.zzf zzg(com.google.android.gms.measurement.internal.zzn r11) {
        /*
        r10 = this;
        r10.zzo();
        r10.zzjj();
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r11);
        r0 = r11.packageName;
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r0);
        r0 = r10.zzgy();
        r1 = r11.packageName;
        r0 = r0.zzab(r1);
        r1 = r10.zzj;
        r1 = r1.zzac();
        r2 = r11.packageName;
        r1 = r1.zzaq(r2);
        r2 = 1;
        if (r0 != 0) goto L_0x0042;
    L_0x0027:
        r0 = new com.google.android.gms.measurement.internal.zzf;
        r3 = r10.zzj;
        r4 = r11.packageName;
        r0.<init>(r3, r4);
        r3 = r10.zzj;
        r3 = r3.zzz();
        r3 = r3.zzjy();
        r0.zza(r3);
        r0.zzd(r1);
    L_0x0040:
        r1 = 1;
        goto L_0x005e;
    L_0x0042:
        r3 = r0.zzai();
        r3 = r1.equals(r3);
        if (r3 != 0) goto L_0x005d;
    L_0x004c:
        r0.zzd(r1);
        r1 = r10.zzj;
        r1 = r1.zzz();
        r1 = r1.zzjy();
        r0.zza(r1);
        goto L_0x0040;
    L_0x005d:
        r1 = 0;
    L_0x005e:
        r3 = r11.zzcg;
        r4 = r0.getGmpAppId();
        r3 = android.text.TextUtils.equals(r3, r4);
        if (r3 != 0) goto L_0x0070;
    L_0x006a:
        r1 = r11.zzcg;
        r0.zzb(r1);
        r1 = 1;
    L_0x0070:
        r3 = r11.zzcu;
        r4 = r0.zzah();
        r3 = android.text.TextUtils.equals(r3, r4);
        if (r3 != 0) goto L_0x0082;
    L_0x007c:
        r1 = r11.zzcu;
        r0.zzc(r1);
        r1 = 1;
    L_0x0082:
        r3 = r11.zzci;
        r3 = android.text.TextUtils.isEmpty(r3);
        if (r3 != 0) goto L_0x009c;
    L_0x008a:
        r3 = r11.zzci;
        r4 = r0.getFirebaseInstanceId();
        r3 = r3.equals(r4);
        if (r3 != 0) goto L_0x009c;
    L_0x0096:
        r1 = r11.zzci;
        r0.zze(r1);
        r1 = 1;
    L_0x009c:
        r3 = r11.zzr;
        r5 = 0;
        r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r7 == 0) goto L_0x00b4;
    L_0x00a4:
        r3 = r11.zzr;
        r7 = r0.zzao();
        r9 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1));
        if (r9 == 0) goto L_0x00b4;
    L_0x00ae:
        r3 = r11.zzr;
        r0.zzh(r3);
        r1 = 1;
    L_0x00b4:
        r3 = r11.zzcm;
        r3 = android.text.TextUtils.isEmpty(r3);
        if (r3 != 0) goto L_0x00ce;
    L_0x00bc:
        r3 = r11.zzcm;
        r4 = r0.zzal();
        r3 = r3.equals(r4);
        if (r3 != 0) goto L_0x00ce;
    L_0x00c8:
        r1 = r11.zzcm;
        r0.zzf(r1);
        r1 = 1;
    L_0x00ce:
        r3 = r11.zzcn;
        r7 = r0.zzam();
        r9 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1));
        if (r9 == 0) goto L_0x00de;
    L_0x00d8:
        r3 = r11.zzcn;
        r0.zzg(r3);
        r1 = 1;
    L_0x00de:
        r3 = r11.zzco;
        if (r3 == 0) goto L_0x00f4;
    L_0x00e2:
        r3 = r11.zzco;
        r4 = r0.zzan();
        r3 = r3.equals(r4);
        if (r3 != 0) goto L_0x00f4;
    L_0x00ee:
        r1 = r11.zzco;
        r0.zzg(r1);
        r1 = 1;
    L_0x00f4:
        r3 = r11.zzcp;
        r7 = r0.zzap();
        r9 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1));
        if (r9 == 0) goto L_0x0104;
    L_0x00fe:
        r3 = r11.zzcp;
        r0.zzi(r3);
        r1 = 1;
    L_0x0104:
        r3 = r11.zzcq;
        r4 = r0.isMeasurementEnabled();
        if (r3 == r4) goto L_0x0112;
    L_0x010c:
        r1 = r11.zzcq;
        r0.setMeasurementEnabled(r1);
        r1 = 1;
    L_0x0112:
        r3 = r11.zzdp;
        r3 = android.text.TextUtils.isEmpty(r3);
        if (r3 != 0) goto L_0x012c;
    L_0x011a:
        r3 = r11.zzdp;
        r4 = r0.zzbb();
        r3 = r3.equals(r4);
        if (r3 != 0) goto L_0x012c;
    L_0x0126:
        r1 = r11.zzdp;
        r0.zzh(r1);
        r1 = 1;
    L_0x012c:
        r3 = r11.zzcr;
        r7 = r0.zzbd();
        r9 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1));
        if (r9 == 0) goto L_0x013c;
    L_0x0136:
        r3 = r11.zzcr;
        r0.zzt(r3);
        r1 = 1;
    L_0x013c:
        r3 = r11.zzcs;
        r4 = r0.zzbe();
        if (r3 == r4) goto L_0x014a;
    L_0x0144:
        r1 = r11.zzcs;
        r0.zzb(r1);
        r1 = 1;
    L_0x014a:
        r3 = r11.zzct;
        r4 = r0.zzbf();
        if (r3 == r4) goto L_0x0158;
    L_0x0152:
        r1 = r11.zzct;
        r0.zzc(r1);
        r1 = 1;
    L_0x0158:
        r3 = r10.zzj;
        r3 = r3.zzad();
        r4 = r11.packageName;
        r7 = com.google.android.gms.measurement.internal.zzak.zzij;
        r3 = r3.zze(r4, r7);
        if (r3 == 0) goto L_0x0176;
    L_0x0168:
        r3 = r11.zzcv;
        r4 = r0.zzbg();
        if (r3 == r4) goto L_0x0176;
    L_0x0170:
        r1 = r11.zzcv;
        r0.zza(r1);
        r1 = 1;
    L_0x0176:
        r3 = r11.zzs;
        r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r7 == 0) goto L_0x018c;
    L_0x017c:
        r3 = r11.zzs;
        r5 = r0.zzaq();
        r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r7 == 0) goto L_0x018c;
    L_0x0186:
        r3 = r11.zzs;
        r0.zzj(r3);
        r1 = 1;
    L_0x018c:
        if (r1 == 0) goto L_0x0195;
    L_0x018e:
        r11 = r10.zzgy();
        r11.zza(r0);
    L_0x0195:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzjg.zzg(com.google.android.gms.measurement.internal.zzn):com.google.android.gms.measurement.internal.zzf");
    }

    final String zzh(zzn zzn) {
        Object e;
        try {
            return (String) this.zzj.zzaa().zza(new zzjk(this, zzn)).get(30000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e2) {
            e = e2;
            this.zzj.zzab().zzgk().zza("Failed to get app instance id. appId", zzef.zzam(zzn.packageName), e);
            return null;
        } catch (InterruptedException e3) {
            e = e3;
            this.zzj.zzab().zzgk().zza("Failed to get app instance id. appId", zzef.zzam(zzn.packageName), e);
            return null;
        } catch (ExecutionException e4) {
            e = e4;
            this.zzj.zzab().zzgk().zza("Failed to get app instance id. appId", zzef.zzam(zzn.packageName), e);
            return null;
        }
    }

    final void zzj(boolean z) {
        zzjn();
    }
}
