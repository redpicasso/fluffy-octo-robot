package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

final class zziy implements zziv {
    zziy() {
    }

    public final Map<?, ?> zzi(Object obj) {
        return (zziw) obj;
    }

    public final zzit<?, ?> zzn(Object obj) {
        throw new NoSuchMethodError();
    }

    public final Map<?, ?> zzj(Object obj) {
        return (zziw) obj;
    }

    public final boolean zzk(Object obj) {
        return !((zziw) obj).isMutable();
    }

    public final Object zzl(Object obj) {
        ((zziw) obj).zzfy();
        return obj;
    }

    public final Object zzm(Object obj) {
        return zziw.zzji().zzjj();
    }

    public final Object zzc(Object obj, Object obj2) {
        obj = (zziw) obj;
        zziw zziw = (zziw) obj2;
        if (!zziw.isEmpty()) {
            if (!obj.isMutable()) {
                obj = obj.zzjj();
            }
            obj.zza(zziw);
        }
        return obj;
    }

    public final int zzb(int i, Object obj, Object obj2) {
        zziw zziw = (zziw) obj;
        if (zziw.isEmpty()) {
            return 0;
        }
        Iterator it = zziw.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Entry entry = (Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw new NoSuchMethodError();
    }
}
