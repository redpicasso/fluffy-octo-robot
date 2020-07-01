package com.google.android.gms.internal.vision;

public enum zzbq implements zzgb {
    ROTATION_0(0),
    ROTATION_90(1),
    ROTATION_180(2),
    ROTATION_270(3);
    
    private static final zzgc<zzbq> zzdv = null;
    private final int value;

    public final int zzr() {
        return this.value;
    }

    public static zzbq zzk(int i) {
        if (i == 0) {
            return ROTATION_0;
        }
        if (i == 1) {
            return ROTATION_90;
        }
        if (i != 2) {
            return i != 3 ? null : ROTATION_270;
        } else {
            return ROTATION_180;
        }
    }

    public static zzgd zzah() {
        return zzbs.zzhl;
    }

    private zzbq(int i) {
        this.value = i;
    }

    static {
        zzdv = new zzbr();
    }
}
