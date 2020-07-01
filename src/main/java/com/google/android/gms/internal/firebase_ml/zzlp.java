package com.google.android.gms.internal.firebase_ml;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

final class zzlp extends zzlg<Entry<K, V>> {
    private final /* synthetic */ zzlo zzadm;

    zzlp(zzlo zzlo) {
        this.zzadm = zzlo;
    }

    public final boolean zzio() {
        return true;
    }

    public final int size() {
        return this.zzadm.size;
    }

    public final /* synthetic */ Object get(int i) {
        zzks.zzb(i, this.zzadm.size);
        Object[] zzb = this.zzadm.zzadd;
        i *= 2;
        zzlo zzlo = this.zzadm;
        Object obj = zzb[i];
        Object[] zzb2 = zzlo.zzadd;
        zzlo zzlo2 = this.zzadm;
        return new SimpleImmutableEntry(obj, zzb2[i + 1]);
    }
}
