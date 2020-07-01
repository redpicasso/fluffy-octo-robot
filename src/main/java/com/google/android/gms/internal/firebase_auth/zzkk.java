package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;

abstract class zzkk<T, B> {
    zzkk() {
    }

    abstract void zza(B b, int i, long j);

    abstract void zza(B b, int i, zzgf zzgf);

    abstract void zza(B b, int i, T t);

    abstract void zza(T t, zzlh zzlh) throws IOException;

    abstract boolean zza(zzjp zzjp);

    abstract void zzb(B b, int i, long j);

    abstract void zzc(B b, int i, int i2);

    abstract void zzc(T t, zzlh zzlh) throws IOException;

    abstract void zzf(Object obj);

    abstract void zzf(Object obj, T t);

    abstract void zzg(Object obj, B b);

    abstract T zzh(T t, T t2);

    abstract B zzkn();

    abstract T zzl(B b);

    abstract int zzq(T t);

    abstract T zzs(Object obj);

    abstract B zzt(Object obj);

    abstract int zzu(T t);

    final boolean zza(B b, zzjp zzjp) throws IOException {
        int tag = zzjp.getTag();
        int i = tag >>> 3;
        tag &= 7;
        if (tag == 0) {
            zza((Object) b, i, zzjp.zzgk());
            return true;
        } else if (tag == 1) {
            zzb(b, i, zzjp.zzgm());
            return true;
        } else if (tag == 2) {
            zza((Object) b, i, zzjp.zzgq());
            return true;
        } else if (tag == 3) {
            Object zzkn = zzkn();
            int i2 = 4 | (i << 3);
            while (zzjp.zzhg() != Integer.MAX_VALUE) {
                if (!zza(zzkn, zzjp)) {
                    break;
                }
            }
            if (i2 == zzjp.getTag()) {
                zza((Object) b, i, zzl(zzkn));
                return true;
            }
            throw zzic.zziv();
        } else if (tag == 4) {
            return false;
        } else {
            if (tag == 5) {
                zzc(b, i, zzjp.zzgn());
                return true;
            }
            throw zzic.zziw();
        }
    }
}
