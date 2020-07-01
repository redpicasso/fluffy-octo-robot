package com.google.android.gms.internal.measurement;

import java.io.IOException;

abstract class zzhp<T, B> {
    zzhp() {
    }

    abstract void zza(B b, int i, long j);

    abstract void zza(B b, int i, zzdp zzdp);

    abstract void zza(B b, int i, T t);

    abstract void zza(T t, zzim zzim) throws IOException;

    abstract boolean zza(zzgy zzgy);

    abstract void zzb(B b, int i, long j);

    abstract void zzc(B b, int i, int i2);

    abstract void zzc(T t, zzim zzim) throws IOException;

    abstract void zze(Object obj, T t);

    abstract void zzf(Object obj, B b);

    abstract T zzg(T t, T t2);

    abstract void zzj(Object obj);

    abstract T zzp(B b);

    abstract int zzt(T t);

    abstract B zzwp();

    abstract T zzx(Object obj);

    abstract B zzy(Object obj);

    abstract int zzz(T t);

    final boolean zza(B b, zzgy zzgy) throws IOException {
        int tag = zzgy.getTag();
        int i = tag >>> 3;
        tag &= 7;
        if (tag == 0) {
            zza((Object) b, i, zzgy.zzsi());
            return true;
        } else if (tag == 1) {
            zzb(b, i, zzgy.zzsk());
            return true;
        } else if (tag == 2) {
            zza((Object) b, i, zzgy.zzso());
            return true;
        } else if (tag == 3) {
            Object zzwp = zzwp();
            int i2 = 4 | (i << 3);
            while (zzgy.zzsy() != Integer.MAX_VALUE) {
                if (!zza(zzwp, zzgy)) {
                    break;
                }
            }
            if (i2 == zzgy.getTag()) {
                zza((Object) b, i, zzp(zzwp));
                return true;
            }
            throw zzfi.zzux();
        } else if (tag == 4) {
            return false;
        } else {
            if (tag == 5) {
                zzc(b, i, zzgy.zzsl());
                return true;
            }
            throw zzfi.zzuy();
        }
    }
}
