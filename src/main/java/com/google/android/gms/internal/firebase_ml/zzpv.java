package com.google.android.gms.internal.firebase_ml;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import com.google.android.gms.internal.firebase_ml.zzmd.zzr;
import com.google.android.gms.internal.firebase_ml.zzmd.zzr.zzb;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

public final class zzpv {
    public static int zzbm(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 90;
        }
        if (i == 2) {
            return 180;
        }
        if (i == 3) {
            return 270;
        }
        StringBuilder stringBuilder = new StringBuilder(29);
        stringBuilder.append("Invalid rotation: ");
        stringBuilder.append(i);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static int zzbn(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        StringBuilder stringBuilder = new StringBuilder(34);
        stringBuilder.append("Invalid landmark type: ");
        stringBuilder.append(i);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static int zzbo(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        StringBuilder stringBuilder = new StringBuilder(30);
        stringBuilder.append("Invalid mode type: ");
        stringBuilder.append(i);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static int zzbp(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        StringBuilder stringBuilder = new StringBuilder(40);
        stringBuilder.append("Invalid classification type: ");
        stringBuilder.append(i);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @TargetApi(19)
    public static zzr zzc(zzpz zzpz) {
        zzb zzb;
        int allocationByteCount;
        if (zzpz.zzaxe.getBitmap() != null) {
            zzb = zzb.BITMAP;
            if (VERSION.SDK_INT >= 19) {
                allocationByteCount = zzpz.zzaxe.getBitmap().getAllocationByteCount();
            } else {
                allocationByteCount = zzpz.zzaxe.getBitmap().getByteCount();
            }
        } else {
            int format = zzpz.zzaxe.getMetadata().getFormat();
            if (format == 16) {
                zzb = zzb.NV16;
            } else if (format == 17) {
                zzb = zzb.NV21;
            } else if (format != FirebaseVisionImageMetadata.IMAGE_FORMAT_YV12) {
                zzb = zzb.UNKNOWN_FORMAT;
            } else {
                zzb = zzb.YV12;
            }
            allocationByteCount = zzpz.zzaxe.getGrayscaleImageData().capacity();
        }
        return (zzr) ((zzue) zzr.zzjz().zzb(zzb).zzav(allocationByteCount).zzrj());
    }
}
