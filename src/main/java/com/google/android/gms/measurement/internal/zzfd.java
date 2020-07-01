package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.zzbk;
import com.google.android.gms.internal.measurement.zzbk.zzb;
import com.google.android.gms.internal.measurement.zzbk.zzd;
import com.google.android.gms.internal.measurement.zzbq.zza;
import com.google.android.gms.internal.measurement.zzbv;
import com.google.android.gms.internal.measurement.zzbw;
import com.google.android.gms.internal.measurement.zzbx;
import com.google.android.gms.internal.measurement.zzey;
import com.google.android.gms.internal.measurement.zzil;
import com.google.android.gms.internal.measurement.zzio;
import com.google.android.gms.internal.measurement.zziw;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import java.io.IOException;
import java.util.Map;

public final class zzfd extends zzjh implements zzu {
    @VisibleForTesting
    private static int zznk = 65535;
    @VisibleForTesting
    private static int zznl = 2;
    private final Map<String, Map<String, String>> zznm = new ArrayMap();
    private final Map<String, Map<String, Boolean>> zznn = new ArrayMap();
    private final Map<String, Map<String, Boolean>> zzno = new ArrayMap();
    private final Map<String, zzbw> zznp = new ArrayMap();
    private final Map<String, Map<String, Integer>> zznq = new ArrayMap();
    private final Map<String, String> zznr = new ArrayMap();

    zzfd(zzjg zzjg) {
        super(zzjg);
    }

    protected final boolean zzbk() {
        return false;
    }

    @WorkerThread
    private final void zzav(String str) {
        zzbi();
        zzo();
        Preconditions.checkNotEmpty(str);
        if (this.zznp.get(str) == null) {
            byte[] zzad = zzgy().zzad(str);
            if (zzad == null) {
                this.zznm.put(str, null);
                this.zznn.put(str, null);
                this.zzno.put(str, null);
                this.zznp.put(str, null);
                this.zznr.put(str, null);
                this.zznq.put(str, null);
                return;
            }
            zzbw zza = zza(str, zzad);
            this.zznm.put(str, zza(zza));
            zza(str, zza);
            this.zznp.put(str, zza);
            this.zznr.put(str, null);
        }
    }

    @WorkerThread
    protected final zzbw zzaw(String str) {
        zzbi();
        zzo();
        Preconditions.checkNotEmpty(str);
        zzav(str);
        return (zzbw) this.zznp.get(str);
    }

    @WorkerThread
    protected final String zzax(String str) {
        zzo();
        return (String) this.zznr.get(str);
    }

    @WorkerThread
    protected final void zzay(String str) {
        zzo();
        this.zznr.put(str, null);
    }

    @WorkerThread
    final void zzaz(String str) {
        zzo();
        this.zznp.remove(str);
    }

    @WorkerThread
    final boolean zzba(String str) {
        zzo();
        zzbw zzaw = zzaw(str);
        if (zzaw == null) {
            return false;
        }
        Boolean bool = zzaw.zzzq;
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @WorkerThread
    public final String zzb(String str, String str2) {
        zzo();
        zzav(str);
        Map map = (Map) this.zznm.get(str);
        return map != null ? (String) map.get(str2) : null;
    }

    private static Map<String, String> zza(zzbw zzbw) {
        Map<String, String> arrayMap = new ArrayMap();
        if (!(zzbw == null || zzbw.zzzm == null)) {
            for (zza zza : zzbw.zzzm) {
                if (zza != null) {
                    arrayMap.put(zza.getKey(), zza.getValue());
                }
            }
        }
        return arrayMap;
    }

    private final void zza(String str, zzbw zzbw) {
        Map arrayMap = new ArrayMap();
        Map arrayMap2 = new ArrayMap();
        Map arrayMap3 = new ArrayMap();
        if (!(zzbw == null || zzbw.zzzn == null)) {
            for (zzbx zzbx : zzbw.zzzn) {
                if (TextUtils.isEmpty(zzbx.name)) {
                    zzab().zzgn().zzao("EventConfig contained null event name");
                } else {
                    Object zzbe = zzgj.zzbe(zzbx.name);
                    if (!TextUtils.isEmpty(zzbe)) {
                        zzbx.name = zzbe;
                    }
                    arrayMap.put(zzbx.name, zzbx.zzzs);
                    arrayMap2.put(zzbx.name, zzbx.zzzt);
                    if (zzbx.zzzu != null) {
                        if (zzbx.zzzu.intValue() < zznl || zzbx.zzzu.intValue() > zznk) {
                            zzab().zzgn().zza("Invalid sampling rate. Event name, sample rate", zzbx.name, zzbx.zzzu);
                        } else {
                            arrayMap3.put(zzbx.name, zzbx.zzzu);
                        }
                    }
                }
            }
        }
        this.zznn.put(str, arrayMap);
        this.zzno.put(str, arrayMap2);
        this.zznq.put(str, arrayMap3);
    }

    @WorkerThread
    protected final boolean zza(String str, byte[] bArr, String str2) {
        String str3 = str;
        zzbi();
        zzo();
        Preconditions.checkNotEmpty(str);
        zzbw zza = zza(str, bArr);
        if (zza == null) {
            return false;
        }
        byte[] bArr2;
        zza(str3, zza);
        this.zznp.put(str3, zza);
        this.zznr.put(str3, str2);
        this.zznm.put(str3, zza(zza));
        zzje zzgx = zzgx();
        zzbv[] zzbvArr = zza.zzzo;
        Preconditions.checkNotNull(zzbvArr);
        for (zzbv zzbv : zzbvArr) {
            if (zzbv.zzzh != null) {
                for (int i = 0; i < zzbv.zzzh.length; i++) {
                    Object obj;
                    zzbk.zza.zza zza2 = (zzbk.zza.zza) zzbv.zzzh[i].zzuj();
                    zzbk.zza.zza zza3 = (zzbk.zza.zza) ((zzey.zza) zza2.clone());
                    String zzbe = zzgj.zzbe(zza2.zzjz());
                    if (zzbe != null) {
                        zza3.zzbs(zzbe);
                        obj = 1;
                    } else {
                        obj = null;
                    }
                    Object obj2 = obj;
                    for (int i2 = 0; i2 < zza2.zzka(); i2++) {
                        zzey zze = zza2.zze(i2);
                        String zzbe2 = zzgi.zzbe(zze.zzkr());
                        if (zzbe2 != null) {
                            zza3.zza(i2, (zzb) ((zzey) ((zzb.zza) zze.zzuj()).zzbu(zzbe2).zzug()));
                            obj2 = 1;
                        }
                    }
                    if (obj2 != null) {
                        zzbv.zzzh[i] = (zzbk.zza) ((zzey) zza3.zzug());
                    }
                }
            }
            if (zzbv.zzzg != null) {
                for (int i3 = 0; i3 < zzbv.zzzg.length; i3++) {
                    zzey zzey = zzbv.zzzg[i3];
                    String zzbe3 = zzgl.zzbe(zzey.getPropertyName());
                    if (zzbe3 != null) {
                        zzbv.zzzg[i3] = (zzd) ((zzey) ((zzd.zza) zzey.zzuj()).zzbw(zzbe3).zzug());
                    }
                }
            }
        }
        zzgx.zzgy().zza(str3, zzbvArr);
        try {
            zza.zzzo = null;
            bArr2 = new byte[zza.zzuk()];
            zza.zza(zzio.zzk(bArr2, 0, bArr2.length));
        } catch (IOException e) {
            zzab().zzgn().zza("Unable to serialize reduced-size config. Storing full config instead. appId", zzef.zzam(str), e);
            bArr2 = bArr;
        }
        zzgf zzgy = zzgy();
        Preconditions.checkNotEmpty(str);
        zzgy.zzo();
        zzgy.zzbi();
        ContentValues contentValues = new ContentValues();
        contentValues.put("remote_config", bArr2);
        try {
            if (((long) zzgy.getWritableDatabase().update("apps", contentValues, "app_id = ?", new String[]{str3})) == 0) {
                zzgy.zzab().zzgk().zza("Failed to update remote config (got 0). appId", zzef.zzam(str));
            }
        } catch (SQLiteException e2) {
            zzgy.zzab().zzgk().zza("Error storing remote config. appId", zzef.zzam(str), e2);
        }
        return true;
    }

    @WorkerThread
    final boolean zzk(String str, String str2) {
        zzo();
        zzav(str);
        if (zzbc(str) && zzjs.zzbq(str2)) {
            return true;
        }
        if (zzbd(str) && zzjs.zzbk(str2)) {
            return true;
        }
        Map map = (Map) this.zznn.get(str);
        if (map == null) {
            return false;
        }
        Boolean bool = (Boolean) map.get(str2);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @WorkerThread
    final boolean zzl(String str, String str2) {
        zzo();
        zzav(str);
        if (Event.ECOMMERCE_PURCHASE.equals(str2)) {
            return true;
        }
        Map map = (Map) this.zzno.get(str);
        if (map == null) {
            return false;
        }
        Boolean bool = (Boolean) map.get(str2);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @WorkerThread
    final int zzm(String str, String str2) {
        zzo();
        zzav(str);
        Map map = (Map) this.zznq.get(str);
        if (map == null) {
            return 1;
        }
        Integer num = (Integer) map.get(str2);
        if (num == null) {
            return 1;
        }
        return num.intValue();
    }

    @WorkerThread
    final long zzbb(String str) {
        Object zzb = zzb(str, "measurement.account.time_zone_offset_minutes");
        if (!TextUtils.isEmpty(zzb)) {
            try {
                return Long.parseLong(zzb);
            } catch (NumberFormatException e) {
                zzab().zzgn().zza("Unable to parse timezone offset. appId", zzef.zzam(str), e);
            }
        }
        return 0;
    }

    @WorkerThread
    private final zzbw zza(String str, byte[] bArr) {
        if (bArr == null) {
            return new zzbw();
        }
        zzil zzj = zzil.zzj(bArr, 0, bArr.length);
        zziw zzbw = new zzbw();
        try {
            zzbw.zza(zzj);
            zzab().zzgs().zza("Parsed config. version, gmp_app_id", zzbw.zzzk, zzbw.zzcg);
            return zzbw;
        } catch (IOException e) {
            zzab().zzgn().zza("Unable to merge remote config. appId", zzef.zzam(str), e);
            return new zzbw();
        }
    }

    final boolean zzbc(String str) {
        return "1".equals(zzb(str, "measurement.upload.blacklist_internal"));
    }

    final boolean zzbd(String str) {
        return "1".equals(zzb(str, "measurement.upload.blacklist_public"));
    }
}
