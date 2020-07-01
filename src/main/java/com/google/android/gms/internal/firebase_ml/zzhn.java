package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;
import java.io.OutputStream;

public final class zzhn {
    public static long zzb(zzhy zzhy) throws IOException {
        OutputStream zzha = new zzha();
        try {
            zzhy.writeTo(zzha);
            return zzha.zzyj;
        } finally {
            zzha.close();
        }
    }
}
