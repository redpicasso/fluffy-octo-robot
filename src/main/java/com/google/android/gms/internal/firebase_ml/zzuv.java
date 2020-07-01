package com.google.android.gms.internal.firebase_ml;

public class zzuv {
    private static final zztr zzbkc = zztr.zzql();
    private zzsw zzbpy;
    private volatile zzvo zzbpz;
    private volatile zzsw zzbqa;

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzuv)) {
            return false;
        }
        zzuv zzuv = (zzuv) obj;
        zzvo zzvo = this.zzbpz;
        zzvo zzvo2 = zzuv.zzbpz;
        if (zzvo == null && zzvo2 == null) {
            return zzpp().equals(zzuv.zzpp());
        }
        if (zzvo != null && zzvo2 != null) {
            return zzvo.equals(zzvo2);
        }
        if (zzvo != null) {
            return zzvo.equals(zzuv.zzh(zzvo.zzre()));
        }
        return zzh(zzvo2.zzre()).equals(zzvo2);
    }

    private final zzvo zzh(zzvo zzvo) {
        if (this.zzbpz == null) {
            synchronized (this) {
                if (this.zzbpz != null) {
                } else {
                    this.zzbpz = zzvo;
                    this.zzbqa = zzsw.zzbkl;
                    try {
                    } catch (zzuo unused) {
                        this.zzbpz = zzvo;
                        this.zzbqa = zzsw.zzbkl;
                    }
                }
            }
        }
        return this.zzbpz;
        return this.zzbpz;
    }

    public final zzvo zzi(zzvo zzvo) {
        zzvo zzvo2 = this.zzbpz;
        this.zzbpy = null;
        this.zzbqa = null;
        this.zzbpz = zzvo;
        return zzvo2;
    }

    public final int zzqy() {
        if (this.zzbqa != null) {
            return this.zzbqa.size();
        }
        return this.zzbpz != null ? this.zzbpz.zzqy() : 0;
    }

    public final zzsw zzpp() {
        if (this.zzbqa != null) {
            return this.zzbqa;
        }
        synchronized (this) {
            zzsw zzsw;
            if (this.zzbqa != null) {
                zzsw = this.zzbqa;
                return zzsw;
            }
            if (this.zzbpz == null) {
                this.zzbqa = zzsw.zzbkl;
            } else {
                this.zzbqa = this.zzbpz.zzpp();
            }
            zzsw = this.zzbqa;
            return zzsw;
        }
    }
}
