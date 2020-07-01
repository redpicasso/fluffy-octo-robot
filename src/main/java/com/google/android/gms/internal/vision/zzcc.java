package com.google.android.gms.internal.vision;

public enum zzcc implements zzgb {
    CLASSIFICATION_TYPE_UNKNOWN(0),
    NO_CLASSIFICATION(1),
    ALL_CLASSIFICATIONS(2);
    
    private static final zzgc<zzcc> zzdv = null;
    private final int value;

    public final int zzr() {
        return this.value;
    }

    public static zzcc zzo(int i) {
        if (i == 0) {
            return CLASSIFICATION_TYPE_UNKNOWN;
        }
        if (i != 1) {
            return i != 2 ? null : ALL_CLASSIFICATIONS;
        } else {
            return NO_CLASSIFICATION;
        }
    }

    public static zzgd zzah() {
        return zzce.zzhl;
    }

    private zzcc(int i) {
        this.value = i;
    }

    static {
        zzdv = new zzcd();
    }
}
