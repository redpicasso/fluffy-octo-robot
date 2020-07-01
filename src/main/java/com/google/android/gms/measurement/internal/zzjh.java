package com.google.android.gms.measurement.internal;

abstract class zzjh extends zzje {
    private boolean zzdh;

    zzjh(zzjg zzjg) {
        super(zzjg);
        this.zzkz.zzb(this);
    }

    protected abstract boolean zzbk();

    final boolean isInitialized() {
        return this.zzdh;
    }

    protected final void zzbi() {
        if (!isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    public final void initialize() {
        if (this.zzdh) {
            throw new IllegalStateException("Can't initialize twice");
        }
        zzbk();
        this.zzkz.zzjs();
        this.zzdh = true;
    }
}
