package com.google.android.gms.internal.vision;

final class zziq extends zzio<zzip, zzip> {
    zziq() {
    }

    final boolean zza(zzhv zzhv) {
        return false;
    }

    private static void zza(Object obj, zzip zzip) {
        ((zzfy) obj).zzwj = zzip;
    }

    final void zze(Object obj) {
        ((zzfy) obj).zzwj.zzci();
    }

    final /* synthetic */ Object zzg(Object obj, Object obj2) {
        zzip zzip = (zzip) obj;
        zzip zzip2 = (zzip) obj2;
        if (zzip2.equals(zzip.zzhe())) {
            return zzip;
        }
        return zzip.zza(zzip, zzip2);
    }

    final /* synthetic */ Object zzu(Object obj) {
        zzip zzip = ((zzfy) obj).zzwj;
        if (zzip != zzip.zzhe()) {
            return zzip;
        }
        zzip = zzip.zzhf();
        zza(obj, zzip);
        return zzip;
    }

    final /* synthetic */ Object zzt(Object obj) {
        return ((zzfy) obj).zzwj;
    }

    final /* synthetic */ Object zzm(Object obj) {
        zzip zzip = (zzip) obj;
        zzip.zzci();
        return zzip;
    }

    final /* synthetic */ void zzb(Object obj, int i, long j) {
        ((zzip) obj).zzb((i << 3) | 1, Long.valueOf(j));
    }

    final /* synthetic */ void zzc(Object obj, int i, int i2) {
        ((zzip) obj).zzb((i << 3) | 5, Integer.valueOf(i2));
    }
}
