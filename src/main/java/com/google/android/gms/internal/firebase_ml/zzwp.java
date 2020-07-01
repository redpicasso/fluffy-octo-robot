package com.google.android.gms.internal.firebase_ml;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzwp implements Iterator<Entry<K, V>> {
    private int pos;
    private Iterator<Entry<K, V>> zzbse;
    private final /* synthetic */ zzwh zzbsf;
    private boolean zzbsj;

    private zzwp(zzwh zzwh) {
        this.zzbsf = zzwh;
        this.pos = -1;
    }

    public final boolean hasNext() {
        if (this.pos + 1 < this.zzbsf.zzbrz.size() || (!this.zzbsf.zzbsa.isEmpty() && zztc().hasNext())) {
            return true;
        }
        return false;
    }

    public final void remove() {
        if (this.zzbsj) {
            this.zzbsj = false;
            this.zzbsf.zzta();
            if (this.pos < this.zzbsf.zzbrz.size()) {
                zzwh zzwh = this.zzbsf;
                int i = this.pos;
                this.pos = i - 1;
                zzwh.zzdq(i);
                return;
            }
            zztc().remove();
            return;
        }
        throw new IllegalStateException("remove() was called before next()");
    }

    private final Iterator<Entry<K, V>> zztc() {
        if (this.zzbse == null) {
            this.zzbse = this.zzbsf.zzbsa.entrySet().iterator();
        }
        return this.zzbse;
    }

    public final /* synthetic */ Object next() {
        this.zzbsj = true;
        int i = this.pos + 1;
        this.pos = i;
        if (i < this.zzbsf.zzbrz.size()) {
            return (Entry) this.zzbsf.zzbrz.get(this.pos);
        }
        return (Entry) zztc().next();
    }

    /* synthetic */ zzwp(zzwh zzwh, zzwi zzwi) {
        this(zzwh);
    }
}
