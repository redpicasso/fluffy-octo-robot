package com.google.android.gms.maps.internal;

public final class zza {
    public static Boolean zza(byte b) {
        if (b != (byte) 0) {
            return b != (byte) 1 ? null : Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public static byte zza(Boolean bool) {
        if (bool != null) {
            return bool.booleanValue() ? (byte) 1 : (byte) 0;
        } else {
            return (byte) -1;
        }
    }
}
