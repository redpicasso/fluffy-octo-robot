package com.google.android.gms.internal.firebase_ml;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

final class zzhi implements Iterator<Entry<String, Object>> {
    private final /* synthetic */ zzhg zzzf;
    private int zzzg = -1;
    private zzhl zzzh;
    private Object zzzi;
    private boolean zzzj;
    private boolean zzzk;
    private zzhl zzzl;

    zzhi(zzhg zzhg) {
        this.zzzf = zzhg;
    }

    public final boolean hasNext() {
        if (!this.zzzk) {
            this.zzzk = true;
            this.zzzi = null;
            while (this.zzzi == null) {
                int i = this.zzzg + 1;
                this.zzzg = i;
                if (i >= this.zzzf.zztx.zzyp.size()) {
                    break;
                }
                this.zzzh = this.zzzf.zztx.zzao((String) this.zzzf.zztx.zzyp.get(this.zzzg));
                this.zzzi = this.zzzh.zzh(this.zzzf.object);
            }
        }
        if (this.zzzi != null) {
            return true;
        }
        return false;
    }

    public final void remove() {
        boolean z = (this.zzzl == null || this.zzzj) ? false : true;
        zzks.checkState(z);
        this.zzzj = true;
        this.zzzl.zzb(this.zzzf.object, null);
    }

    public final /* synthetic */ Object next() {
        if (hasNext()) {
            this.zzzl = this.zzzh;
            Object obj = this.zzzi;
            this.zzzk = false;
            this.zzzj = false;
            this.zzzh = null;
            this.zzzi = null;
            return new zzhh(this.zzzf, this.zzzl, obj);
        }
        throw new NoSuchElementException();
    }
}
