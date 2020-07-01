package com.google.android.gms.internal.firebase_ml;

import java.util.Map;
import java.util.Map.Entry;

final class zzvk implements zzvj {
    zzvk() {
    }

    public final Map<?, ?> zzu(Object obj) {
        return (zzvi) obj;
    }

    public final zzvh<?, ?> zzz(Object obj) {
        return ((zzvg) obj).zzsa();
    }

    public final Map<?, ?> zzv(Object obj) {
        return (zzvi) obj;
    }

    public final boolean zzw(Object obj) {
        return !((zzvi) obj).isMutable();
    }

    public final Object zzx(Object obj) {
        ((zzvi) obj).zzpt();
        return obj;
    }

    public final Object zzy(Object obj) {
        return zzvi.zzsb().zzsc();
    }

    public final Object zzf(Object obj, Object obj2) {
        obj = (zzvi) obj;
        zzvi zzvi = (zzvi) obj2;
        if (!zzvi.isEmpty()) {
            if (!obj.isMutable()) {
                obj = obj.zzsc();
            }
            obj.zza(zzvi);
        }
        return obj;
    }

    public final int zzd(int i, Object obj, Object obj2) {
        zzvi zzvi = (zzvi) obj;
        zzvg zzvg = (zzvg) obj2;
        int i2 = 0;
        if (zzvi.isEmpty()) {
            return 0;
        }
        for (Entry entry : zzvi.entrySet()) {
            i2 += zzvg.zzc(i, entry.getKey(), entry.getValue());
        }
        return i2;
    }
}
