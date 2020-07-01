package com.google.android.gms.internal.vision;

public class zzgm {
    private static final zzfk zzrk = zzfk.zzek();
    private zzeo zzyh;
    private volatile zzhf zzyi;
    private volatile zzeo zzyj;

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzgm)) {
            return false;
        }
        zzgm zzgm = (zzgm) obj;
        zzhf zzhf = this.zzyi;
        zzhf zzhf2 = zzgm.zzyi;
        if (zzhf == null && zzhf2 == null) {
            return zzce().equals(zzgm.zzce());
        }
        if (zzhf != null && zzhf2 != null) {
            return zzhf.equals(zzhf2);
        }
        if (zzhf != null) {
            return zzhf.equals(zzgm.zzh(zzhf.zzfb()));
        }
        return zzh(zzhf2.zzfb()).equals(zzhf2);
    }

    private final zzhf zzh(zzhf zzhf) {
        if (this.zzyi == null) {
            synchronized (this) {
                if (this.zzyi != null) {
                } else {
                    this.zzyi = zzhf;
                    this.zzyj = zzeo.zzrx;
                    try {
                    } catch (zzgf unused) {
                        this.zzyi = zzhf;
                        this.zzyj = zzeo.zzrx;
                    }
                }
            }
        }
        return this.zzyi;
        return this.zzyi;
    }

    public final zzhf zzi(zzhf zzhf) {
        zzhf zzhf2 = this.zzyi;
        this.zzyh = null;
        this.zzyj = null;
        this.zzyi = zzhf;
        return zzhf2;
    }

    public final int zzeq() {
        if (this.zzyj != null) {
            return this.zzyj.size();
        }
        return this.zzyi != null ? this.zzyi.zzeq() : 0;
    }

    public final zzeo zzce() {
        if (this.zzyj != null) {
            return this.zzyj;
        }
        synchronized (this) {
            zzeo zzeo;
            if (this.zzyj != null) {
                zzeo = this.zzyj;
                return zzeo;
            }
            if (this.zzyi == null) {
                this.zzyj = zzeo.zzrx;
            } else {
                this.zzyj = this.zzyi.zzce();
            }
            zzeo = this.zzyj;
            return zzeo;
        }
    }
}
