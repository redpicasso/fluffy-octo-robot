package com.google.android.gms.internal.firebase_ml;

import com.bumptech.glide.load.Key;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public final class zzug {
    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    static final Charset UTF_8 = Charset.forName(Key.STRING_CHARSET_NAME);
    public static final byte[] zzbpe;
    private static final ByteBuffer zzbpf;
    private static final zzti zzbpg;

    public static int zzaj(boolean z) {
        return z ? 1231 : 1237;
    }

    static boolean zzf(zzvo zzvo) {
        return false;
    }

    public static int zzz(long j) {
        return (int) (j ^ (j >>> 32));
    }

    static <T> T checkNotNull(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    static <T> T zza(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    public static boolean zzi(byte[] bArr) {
        return zzxe.zzi(bArr);
    }

    public static String zzj(byte[] bArr) {
        return new String(bArr, UTF_8);
    }

    public static int hashCode(byte[] bArr) {
        int length = bArr.length;
        int zza = zza(length, bArr, 0, length);
        return zza == 0 ? 1 : zza;
    }

    static int zza(int i, byte[] bArr, int i2, int i3) {
        int i4 = i;
        for (i = i2; i < i2 + i3; i++) {
            i4 = (i4 * 31) + bArr[i];
        }
        return i4;
    }

    static Object zze(Object obj, Object obj2) {
        return ((zzvo) obj).zzrc().zza((zzvo) obj2).zzri();
    }

    static {
        byte[] bArr = new byte[0];
        zzbpe = bArr;
        zzbpf = ByteBuffer.wrap(bArr);
        bArr = zzbpe;
        zzbpg = zzti.zza(bArr, 0, bArr.length, false);
    }
}
