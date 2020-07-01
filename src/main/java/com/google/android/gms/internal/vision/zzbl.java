package com.google.android.gms.internal.vision;

public enum zzbl implements zzgb {
    RGBA(0),
    NV21(1),
    RGB(2),
    GRAY(3);
    
    private static final zzgc<zzbl> zzdv = null;
    private final int value;

    public final int zzr() {
        return this.value;
    }

    public static zzbl zzg(int i) {
        if (i == 0) {
            return RGBA;
        }
        if (i == 1) {
            return NV21;
        }
        if (i != 2) {
            return i != 3 ? null : GRAY;
        } else {
            return RGB;
        }
    }

    public static zzgd zzah() {
        return zzbn.zzhl;
    }

    private zzbl(int i) {
        this.value = i;
    }

    static {
        zzdv = new zzbm();
    }
}
