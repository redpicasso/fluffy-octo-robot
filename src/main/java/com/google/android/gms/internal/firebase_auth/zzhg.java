package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs.zzb;
import java.io.IOException;
import java.util.Map.Entry;

final class zzhg extends zzhh<Object> {
    zzhg() {
    }

    final boolean zzf(zzjc zzjc) {
        return zzjc instanceof zzb;
    }

    final zzhi<Object> zzd(Object obj) {
        return ((zzb) obj).zzaam;
    }

    final zzhi<Object> zze(Object obj) {
        zzb zzb = (zzb) obj;
        if (zzb.zzaam.isImmutable()) {
            zzb.zzaam = (zzhi) zzb.zzaam.clone();
        }
        return zzb.zzaam;
    }

    final void zzf(Object obj) {
        zzd(obj).zzfy();
    }

    final <UT, UB> UB zza(zzjp zzjp, Object obj, zzhf zzhf, zzhi<Object> zzhi, UB ub, zzkk<UT, UB> zzkk) throws IOException {
        throw new NoSuchMethodError();
    }

    final int zza(Entry<?, ?> entry) {
        entry.getKey();
        throw new NoSuchMethodError();
    }

    final void zza(zzlh zzlh, Entry<?, ?> entry) throws IOException {
        entry.getKey();
        throw new NoSuchMethodError();
    }

    final Object zza(zzhf zzhf, zzjc zzjc, int i) {
        return zzhf.zza(zzjc, i);
    }

    final void zza(zzjp zzjp, Object obj, zzhf zzhf, zzhi<Object> zzhi) throws IOException {
        throw new NoSuchMethodError();
    }

    final void zza(zzgf zzgf, Object obj, zzhf zzhf, zzhi<Object> zzhi) throws IOException {
        throw new NoSuchMethodError();
    }
}
