package com.google.android.gms.internal.vision;

public enum zzck implements zzgb {
    MODE_TYPE_UNKNOWN(0),
    FAST(1),
    ACCURATE(2),
    SELFIE(3);
    
    private static final zzgc<zzck> zzdv = null;
    private final int value;

    public final int zzr() {
        return this.value;
    }

    public static zzck zzr(int i) {
        if (i == 0) {
            return MODE_TYPE_UNKNOWN;
        }
        if (i == 1) {
            return FAST;
        }
        if (i != 2) {
            return i != 3 ? null : SELFIE;
        } else {
            return ACCURATE;
        }
    }

    public static zzgd zzah() {
        return zzcm.zzhl;
    }

    private zzck(int i) {
        this.value = i;
    }

    static {
        zzdv = new zzcl();
    }
}
