package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public final class zzex implements zzfb {
    public final String getName() {
        return "gzip";
    }

    public final void zza(zzhy zzhy, OutputStream outputStream) throws IOException {
        outputStream = new GZIPOutputStream(new zzey(this, outputStream));
        zzhy.writeTo(outputStream);
        outputStream.close();
    }
}
