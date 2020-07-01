package com.google.android.gms.internal.firebase_auth;

final class zzkm extends zzkk<zzkn, zzkn> {
    zzkm() {
    }

    final boolean zza(zzjp zzjp) {
        return false;
    }

    private static void zza(Object obj, zzkn zzkn) {
        ((zzhs) obj).zzaaj = zzkn;
    }

    final void zzf(Object obj) {
        ((zzhs) obj).zzaaj.zzfy();
    }

    final /* synthetic */ Object zzh(Object obj, Object obj2) {
        zzkn zzkn = (zzkn) obj;
        zzkn zzkn2 = (zzkn) obj2;
        if (zzkn2.equals(zzkn.zzko())) {
            return zzkn;
        }
        return zzkn.zza(zzkn, zzkn2);
    }

    final /* synthetic */ Object zzt(Object obj) {
        zzkn zzkn = ((zzhs) obj).zzaaj;
        if (zzkn != zzkn.zzko()) {
            return zzkn;
        }
        zzkn = zzkn.zzkp();
        zza(obj, zzkn);
        return zzkn;
    }

    final /* synthetic */ Object zzs(Object obj) {
        return ((zzhs) obj).zzaaj;
    }

    final /* synthetic */ Object zzl(Object obj) {
        zzkn zzkn = (zzkn) obj;
        zzkn.zzfy();
        return zzkn;
    }

    final /* synthetic */ void zzb(Object obj, int i, long j) {
        ((zzkn) obj).zzb((i << 3) | 1, Long.valueOf(j));
    }

    final /* synthetic */ void zzc(Object obj, int i, int i2) {
        ((zzkn) obj).zzb((i << 3) | 5, Integer.valueOf(i2));
    }
}
