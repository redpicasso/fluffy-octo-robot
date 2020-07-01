package com.google.android.gms.internal.vision;

public enum zzch implements zzgb {
    LANDMARK_TYPE_UNKNOWN(0),
    NO_LANDMARK(1),
    ALL_LANDMARKS(2),
    CONTOUR_LANDMARKS(3);
    
    private static final zzgc<zzch> zzdv = null;
    private final int value;

    public final int zzr() {
        return this.value;
    }

    public static zzch zzq(int i) {
        if (i == 0) {
            return LANDMARK_TYPE_UNKNOWN;
        }
        if (i == 1) {
            return NO_LANDMARK;
        }
        if (i != 2) {
            return i != 3 ? null : CONTOUR_LANDMARKS;
        } else {
            return ALL_LANDMARKS;
        }
    }

    public static zzgd zzah() {
        return zzcj.zzhl;
    }

    private zzch(int i) {
        this.value = i;
    }

    static {
        zzdv = new zzci();
    }
}
