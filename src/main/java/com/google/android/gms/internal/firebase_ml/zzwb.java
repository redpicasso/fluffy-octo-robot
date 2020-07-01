package com.google.android.gms.internal.firebase_ml;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class zzwb {
    private static final zzwb zzbrq = new zzwb();
    private final zzwf zzbrr = new zzvd();
    private final ConcurrentMap<Class<?>, zzwe<?>> zzbrs = new ConcurrentHashMap();

    public static zzwb zzso() {
        return zzbrq;
    }

    public final <T> zzwe<T> zzk(Class<T> cls) {
        String str = "messageType";
        zzug.zza(cls, str);
        zzwe<T> zzwe = (zzwe) this.zzbrs.get(cls);
        if (zzwe != null) {
            return zzwe;
        }
        zzwe = this.zzbrr.zzj(cls);
        zzug.zza(cls, str);
        zzug.zza(zzwe, "schema");
        zzwe<T> zzwe2 = (zzwe) this.zzbrs.putIfAbsent(cls, zzwe);
        return zzwe2 != null ? zzwe2 : zzwe;
    }

    public final <T> zzwe<T> zzad(T t) {
        return zzk(t.getClass());
    }

    private zzwb() {
    }
}
