package com.google.android.gms.internal.firebase_auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class zzio extends zzim {
    private static final Class<?> zzace = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzio() {
        super();
    }

    final <L> List<L> zza(Object obj, long j) {
        return zza(obj, j, 10);
    }

    final void zzb(Object obj, long j) {
        Object zzje;
        List list = (List) zzkq.zzp(obj, j);
        if (list instanceof zzij) {
            zzje = ((zzij) list).zzje();
        } else if (!zzace.isAssignableFrom(list.getClass())) {
            if ((list instanceof zzjl) && (list instanceof zzhz)) {
                zzhz zzhz = (zzhz) list;
                if (zzhz.zzfx()) {
                    zzhz.zzfy();
                }
                return;
            }
            zzje = Collections.unmodifiableList(list);
        } else {
            return;
        }
        zzkq.zza(obj, j, zzje);
    }

    private static <L> List<L> zza(Object obj, long j, int i) {
        List<L> zzd = zzd(obj, j);
        Object zzik;
        if (zzd.isEmpty()) {
            if (zzd instanceof zzij) {
                zzik = new zzik(i);
            } else if ((zzd instanceof zzjl) && (zzd instanceof zzhz)) {
                zzik = ((zzhz) zzd).zzo(i);
            } else {
                zzik = new ArrayList(i);
            }
            zzkq.zza(obj, j, zzik);
            return zzik;
        }
        ArrayList arrayList;
        if (zzace.isAssignableFrom(zzd.getClass())) {
            arrayList = new ArrayList(zzd.size() + i);
            arrayList.addAll(zzd);
            zzkq.zza(obj, j, (Object) arrayList);
        } else if (zzd instanceof zzkp) {
            Object arrayList2 = new zzik(zzd.size() + i);
            arrayList2.addAll((zzkp) zzd);
            zzkq.zza(obj, j, arrayList2);
        } else if (!(zzd instanceof zzjl) || !(zzd instanceof zzhz)) {
            return zzd;
        } else {
            zzhz zzhz = (zzhz) zzd;
            if (zzhz.zzfx()) {
                return zzd;
            }
            zzik = zzhz.zzo(zzd.size() + i);
            zzkq.zza(obj, j, zzik);
            return zzik;
        }
        return arrayList2;
    }

    final <E> void zza(Object obj, Object obj2, long j) {
        obj2 = zzd(obj2, j);
        List zza = zza(obj, j, obj2.size());
        int size = zza.size();
        int size2 = obj2.size();
        if (size > 0 && size2 > 0) {
            zza.addAll(obj2);
        }
        if (size > 0) {
            obj2 = zza;
        }
        zzkq.zza(obj, j, obj2);
    }

    private static <E> List<E> zzd(Object obj, long j) {
        return (List) zzkq.zzp(obj, j);
    }
}
