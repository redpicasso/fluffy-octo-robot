package com.google.android.gms.internal.firebase_auth;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class zzjo {
    private static final zzjo zzadn = new zzjo();
    private final zzjr zzado = new zzip();
    private final ConcurrentMap<Class<?>, zzjs<?>> zzadp = new ConcurrentHashMap();

    public static zzjo zzjv() {
        return zzadn;
    }

    public final <T> zzjs<T> zzf(Class<T> cls) {
        String str = "messageType";
        zzht.zza(cls, str);
        zzjs<T> zzjs = (zzjs) this.zzadp.get(cls);
        if (zzjs != null) {
            return zzjs;
        }
        zzjs = this.zzado.zze(cls);
        zzht.zza(cls, str);
        zzht.zza(zzjs, "schema");
        zzjs<T> zzjs2 = (zzjs) this.zzadp.putIfAbsent(cls, zzjs);
        return zzjs2 != null ? zzjs2 : zzjs;
    }

    public final <T> zzjs<T> zzr(T t) {
        return zzf(t.getClass());
    }

    private zzjo() {
    }
}
