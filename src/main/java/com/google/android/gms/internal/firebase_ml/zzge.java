package com.google.android.gms.internal.firebase_ml;

import com.bumptech.glide.load.Key;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public abstract class zzge {
    public abstract zzgf zza(OutputStream outputStream, Charset charset) throws IOException;

    public abstract zzgi zza(InputStream inputStream) throws IOException;

    public abstract zzgi zza(InputStream inputStream, Charset charset) throws IOException;

    public abstract zzgi zzam(String str) throws IOException;

    public final String toString(Object obj) throws IOException {
        return zza(obj, false);
    }

    public final String zzc(Object obj) throws IOException {
        return zza(obj, true);
    }

    private final String zza(Object obj, boolean z) throws IOException {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        zzgf zza = zza(byteArrayOutputStream, zzhc.UTF_8);
        if (z) {
            zza.zzgd();
        }
        zza.zzd(obj);
        zza.flush();
        return byteArrayOutputStream.toString(Key.STRING_CHARSET_NAME);
    }
}
