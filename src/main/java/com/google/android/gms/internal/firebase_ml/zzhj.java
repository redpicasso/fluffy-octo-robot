package com.google.android.gms.internal.firebase_ml;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map.Entry;

final class zzhj extends AbstractSet<Entry<String, Object>> {
    private final /* synthetic */ zzhg zzzf;

    zzhj(zzhg zzhg) {
        this.zzzf = zzhg;
    }

    public final int size() {
        int i = 0;
        for (String zzao : this.zzzf.zztx.zzyp) {
            if (this.zzzf.zztx.zzao(zzao).zzh(this.zzzf.object) != null) {
                i++;
            }
        }
        return i;
    }

    public final void clear() {
        for (String zzao : this.zzzf.zztx.zzyp) {
            this.zzzf.zztx.zzao(zzao).zzb(this.zzzf.object, null);
        }
    }

    public final boolean isEmpty() {
        for (String zzao : this.zzzf.zztx.zzyp) {
            if (this.zzzf.zztx.zzao(zzao).zzh(this.zzzf.object) != null) {
                return false;
            }
        }
        return true;
    }

    public final /* synthetic */ Iterator iterator() {
        return new zzhi(this.zzzf);
    }
}
