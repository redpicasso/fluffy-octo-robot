package com.google.android.gms.internal.firebase_ml;

import com.google.android.gms.internal.firebase_ml.zzue.zzd;
import java.io.IOException;
import java.util.Map.Entry;

final class zztu extends zztt<Object> {
    zztu() {
    }

    final boolean zze(zzvo zzvo) {
        return zzvo instanceof zzd;
    }

    final zztw<Object> zzo(Object obj) {
        return ((zzd) obj).zzbon;
    }

    final zztw<Object> zzp(Object obj) {
        return ((zzd) obj).zzrk();
    }

    final void zzq(Object obj) {
        zzo(obj).zzpt();
    }

    final int zza(Entry<?, ?> entry) {
        entry.getKey();
        throw new NoSuchMethodError();
    }

    final void zza(zzxr zzxr, Entry<?, ?> entry) throws IOException {
        entry.getKey();
        throw new NoSuchMethodError();
    }

    final Object zza(zztr zztr, zzvo zzvo, int i) {
        return zztr.zza(zzvo, i);
    }
}
