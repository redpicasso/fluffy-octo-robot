package com.google.android.gms.internal.firebase_messaging;

import com.airbnb.lottie.utils.Utils;
import com.drew.metadata.photoshop.PhotoshopDirectory;

/* compiled from: com.google.firebase:firebase-messaging@@20.0.0 */
public final class zzn {
    private static final byte[] zza = new byte[]{(byte) 9, (byte) 9, (byte) 9, (byte) 8, (byte) 8, (byte) 8, (byte) 7, (byte) 7, (byte) 7, (byte) 6, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 5, (byte) 5, (byte) 4, (byte) 4, (byte) 4, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 2, (byte) 2, (byte) 2, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
    private static final int[] zzb = new int[]{1, 10, 100, 1000, PhotoshopDirectory.TAG_PRINT_FLAGS_INFO, 100000, 1000000, 10000000, 100000000, Utils.SECOND_IN_NANOS};
    private static final int[] zzc = new int[]{3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, Integer.MAX_VALUE};
    private static final int[] zzd = new int[]{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600};
    private static int[] zze = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, 65536, 2345, 477, 193, 110, 75, 58, 49, 43, 39, 37, 35, 34, 34, 33};

    public static int zza(int i, int i2) {
        long j = ((long) i) << 1;
        return j > 2147483647L ? Integer.MAX_VALUE : j < -2147483648L ? Integer.MIN_VALUE : (int) j;
    }
}
