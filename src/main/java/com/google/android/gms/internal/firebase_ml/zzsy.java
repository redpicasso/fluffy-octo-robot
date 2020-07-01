package com.google.android.gms.internal.firebase_ml;

import java.util.Comparator;

final class zzsy implements Comparator<zzsw> {
    zzsy() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        zzsw zzsw = (zzsw) obj;
        zzsw zzsw2 = (zzsw) obj2;
        zztd zztd = (zztd) zzsw.iterator();
        zztd zztd2 = (zztd) zzsw2.iterator();
        while (zztd.hasNext() && zztd2.hasNext()) {
            int compare = Integer.compare(zzsw.zzb(zztd.nextByte()), zzsw.zzb(zztd2.nextByte()));
            if (compare != 0) {
                return compare;
            }
        }
        return Integer.compare(zzsw.size(), zzsw2.size());
    }
}
