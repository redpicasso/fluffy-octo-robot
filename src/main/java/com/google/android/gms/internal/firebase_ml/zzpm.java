package com.google.android.gms.internal.firebase_ml;

import android.graphics.Rect;
import androidx.annotation.Nullable;

public final class zzpm {
    @Nullable
    public static String zzbl(int i) {
        return i != 1 ? i != 2 ? null : "builtin/latest" : "builtin/stable";
    }

    public static String zzch(@Nullable String str) {
        return str == null ? "" : str;
    }

    public static float zza(@Nullable Float f) {
        return f == null ? 0.0f : f.floatValue();
    }

    private static int zzb(@Nullable Integer num) {
        return num == null ? 0 : num.intValue();
    }

    @Nullable
    public static Rect zza(@Nullable zziv zziv, float f) {
        if (zziv == null || zziv.zzhs() == null || zziv.zzhs().size() != 4) {
            return null;
        }
        int i = Integer.MAX_VALUE;
        int i2 = Integer.MAX_VALUE;
        int i3 = 0;
        int i4 = 0;
        for (zzju zzju : zziv.zzhs()) {
            i = Math.min(zzb(zzju.zzib()), i);
            i2 = Math.min(zzb(zzju.zzic()), i2);
            i3 = Math.max(zzb(zzju.zzib()), i3);
            i4 = Math.max(zzb(zzju.zzic()), i4);
        }
        return new Rect(Math.round(((float) i) * f), Math.round(((float) i2) * f), Math.round(((float) i3) * f), Math.round(((float) i4) * f));
    }
}
