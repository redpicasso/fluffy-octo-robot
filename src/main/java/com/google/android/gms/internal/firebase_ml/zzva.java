package com.google.android.gms.internal.firebase_ml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class zzva extends zzuy {
    private static final Class<?> zzbqg = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzva() {
        super();
    }

    final void zzb(Object obj, long j) {
        Object zzrw;
        List list = (List) zzxc.zzp(obj, j);
        if (list instanceof zzux) {
            zzrw = ((zzux) list).zzrw();
        } else if (!zzbqg.isAssignableFrom(list.getClass())) {
            if ((list instanceof zzwa) && (list instanceof zzun)) {
                zzun zzun = (zzun) list;
                if (zzun.zzps()) {
                    zzun.zzpt();
                }
                return;
            }
            zzrw = Collections.unmodifiableList(list);
        } else {
            return;
        }
        zzxc.zza(obj, j, zzrw);
    }

    final <E> void zza(Object obj, Object obj2, long j) {
        obj2 = zzc(obj2, j);
        int size = obj2.size();
        List zzc = zzc(obj, j);
        if (zzc.isEmpty()) {
            if (zzc instanceof zzux) {
                zzc = new zzuw(size);
            } else if ((zzc instanceof zzwa) && (zzc instanceof zzun)) {
                zzc = ((zzun) zzc).zzck(size);
            } else {
                zzc = new ArrayList(size);
            }
            zzxc.zza(obj, j, (Object) zzc);
        } else {
            ArrayList arrayList;
            if (zzbqg.isAssignableFrom(zzc.getClass())) {
                arrayList = new ArrayList(zzc.size() + size);
                arrayList.addAll(zzc);
                zzxc.zza(obj, j, (Object) arrayList);
            } else if (zzc instanceof zzwz) {
                Object arrayList2 = new zzuw(zzc.size() + size);
                arrayList2.addAll((zzwz) zzc);
                zzxc.zza(obj, j, arrayList2);
            } else if ((zzc instanceof zzwa) && (zzc instanceof zzun)) {
                zzun zzun = (zzun) zzc;
                if (!zzun.zzps()) {
                    List zzck = zzun.zzck(zzc.size() + size);
                    zzxc.zza(obj, j, (Object) zzck);
                    zzc = zzck;
                }
            }
            zzc = arrayList2;
        }
        size = zzc.size();
        int size2 = obj2.size();
        if (size > 0 && size2 > 0) {
            zzc.addAll(obj2);
        }
        if (size > 0) {
            obj2 = zzc;
        }
        zzxc.zza(obj, j, obj2);
    }

    private static <E> List<E> zzc(Object obj, long j) {
        return (List) zzxc.zzp(obj, j);
    }
}
