package com.google.android.gms.internal.vision;

import java.io.IOException;

abstract class zzio<T, B> {
    zzio() {
    }

    abstract void zza(B b, int i, long j);

    abstract void zza(B b, int i, zzeo zzeo);

    abstract void zza(B b, int i, T t);

    abstract void zza(T t, zzjj zzjj) throws IOException;

    abstract boolean zza(zzhv zzhv);

    abstract void zzb(B b, int i, long j);

    abstract void zzc(B b, int i, int i2);

    abstract void zzc(T t, zzjj zzjj) throws IOException;

    abstract void zze(Object obj);

    abstract void zze(Object obj, T t);

    abstract void zzf(Object obj, B b);

    abstract T zzg(T t, T t2);

    abstract B zzhd();

    abstract T zzm(B b);

    abstract int zzp(T t);

    abstract T zzt(Object obj);

    abstract B zzu(Object obj);

    abstract int zzv(T t);

    final boolean zza(B b, zzhv zzhv) throws IOException {
        int tag = zzhv.getTag();
        int i = tag >>> 3;
        tag &= 7;
        if (tag == 0) {
            zza((Object) b, i, zzhv.zzcq());
            return true;
        } else if (tag == 1) {
            zzb(b, i, zzhv.zzcs());
            return true;
        } else if (tag == 2) {
            zza((Object) b, i, zzhv.zzcw());
            return true;
        } else if (tag == 3) {
            Object zzhd = zzhd();
            int i2 = 4 | (i << 3);
            while (zzhv.zzcn() != Integer.MAX_VALUE) {
                if (!zza(zzhd, zzhv)) {
                    break;
                }
            }
            if (i2 == zzhv.getTag()) {
                zza((Object) b, i, zzm(zzhd));
                return true;
            }
            throw zzgf.zzfl();
        } else if (tag == 4) {
            return false;
        } else {
            if (tag == 5) {
                zzc(b, i, zzhv.zzct());
                return true;
            }
            throw zzgf.zzfm();
        }
    }
}
