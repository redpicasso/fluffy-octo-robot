package com.google.android.gms.internal.firebase_auth;

public class zzih {
    private static final zzhf zzvq = zzhf.zzhq();
    private zzgf zzabv;
    private volatile zzjc zzabw;
    private volatile zzgf zzabx;

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzih)) {
            return false;
        }
        zzih zzih = (zzih) obj;
        zzjc zzjc = this.zzabw;
        zzjc zzjc2 = zzih.zzabw;
        if (zzjc == null && zzjc2 == null) {
            return zzft().equals(zzih.zzft());
        }
        if (zzjc != null && zzjc2 != null) {
            return zzjc.equals(zzjc2);
        }
        if (zzjc != null) {
            return zzjc.equals(zzih.zzi(zzjc.zzii()));
        }
        return zzi(zzjc2.zzii()).equals(zzjc2);
    }

    private final zzjc zzi(zzjc zzjc) {
        if (this.zzabw == null) {
            synchronized (this) {
                if (this.zzabw != null) {
                } else {
                    this.zzabw = zzjc;
                    this.zzabx = zzgf.zzvv;
                    try {
                    } catch (zzic unused) {
                        this.zzabw = zzjc;
                        this.zzabx = zzgf.zzvv;
                    }
                }
            }
        }
        return this.zzabw;
        return this.zzabw;
    }

    public final zzjc zzj(zzjc zzjc) {
        zzjc zzjc2 = this.zzabw;
        this.zzabv = null;
        this.zzabx = null;
        this.zzabw = zzjc;
        return zzjc2;
    }

    public final int zzik() {
        if (this.zzabx != null) {
            return this.zzabx.size();
        }
        return this.zzabw != null ? this.zzabw.zzik() : 0;
    }

    public final zzgf zzft() {
        if (this.zzabx != null) {
            return this.zzabx;
        }
        synchronized (this) {
            zzgf zzgf;
            if (this.zzabx != null) {
                zzgf = this.zzabx;
                return zzgf;
            }
            if (this.zzabw == null) {
                this.zzabx = zzgf.zzvv;
            } else {
                this.zzabx = this.zzabw.zzft();
            }
            zzgf = this.zzabx;
            return zzgf;
        }
    }
}
