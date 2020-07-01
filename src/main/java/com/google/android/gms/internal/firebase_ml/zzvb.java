package com.google.android.gms.internal.firebase_ml;

final class zzvb extends zzuy {
    private zzvb() {
        super();
    }

    final void zzb(Object obj, long j) {
        zzd(obj, j).zzpt();
    }

    final <E> void zza(Object obj, Object obj2, long j) {
        zzun zzd = zzd(obj, j);
        obj2 = zzd(obj2, j);
        int size = zzd.size();
        int size2 = obj2.size();
        if (size > 0 && size2 > 0) {
            if (!zzd.zzps()) {
                zzd = zzd.zzck(size2 + size);
            }
            zzd.addAll(obj2);
        }
        if (size > 0) {
            obj2 = zzd;
        }
        zzxc.zza(obj, j, obj2);
    }

    private static <E> zzun<E> zzd(Object obj, long j) {
        return (zzun) zzxc.zzp(obj, j);
    }
}
