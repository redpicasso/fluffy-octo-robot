package com.google.android.gms.maps.model;

public final class Gap extends PatternItem {
    public final float length;

    public Gap(float f) {
        super(2, Float.valueOf(Math.max(f, 0.0f)));
        this.length = Math.max(f, 0.0f);
    }

    public final String toString() {
        float f = this.length;
        StringBuilder stringBuilder = new StringBuilder(29);
        stringBuilder.append("[Gap: length=");
        stringBuilder.append(f);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
