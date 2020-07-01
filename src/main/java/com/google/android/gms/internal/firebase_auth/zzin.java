package com.google.android.gms.internal.firebase_auth;

import java.util.List;

final class zzin extends zzim {
    private zzin() {
        super();
    }

    final <L> List<L> zza(Object obj, long j) {
        List<L> zzc = zzc(obj, j);
        if (zzc.zzfx()) {
            return zzc;
        }
        int size = zzc.size();
        Object zzo = zzc.zzo(size == 0 ? 10 : size << 1);
        zzkq.zza(obj, j, zzo);
        return zzo;
    }

    final void zzb(Object obj, long j) {
        zzc(obj, j).zzfy();
    }

    final <E> void zza(Object obj, Object obj2, long j) {
        zzhz zzc = zzc(obj, j);
        obj2 = zzc(obj2, j);
        int size = zzc.size();
        int size2 = obj2.size();
        if (size > 0 && size2 > 0) {
            if (!zzc.zzfx()) {
                zzc = zzc.zzo(size2 + size);
            }
            zzc.addAll(obj2);
        }
        if (size > 0) {
            obj2 = zzc;
        }
        zzkq.zza(obj, j, obj2);
    }

    private static <E> zzhz<E> zzc(Object obj, long j) {
        return (zzhz) zzkq.zzp(obj, j);
    }
}
