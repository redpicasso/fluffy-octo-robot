package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;
import java.io.OutputStream;

public final class zzfc implements zzhy {
    private final zzhy zztt;
    private final zzfb zztu;

    public zzfc(zzhy zzhy, zzfb zzfb) {
        this.zztt = (zzhy) zzks.checkNotNull(zzhy);
        this.zztu = (zzfb) zzks.checkNotNull(zzfb);
    }

    public final void writeTo(OutputStream outputStream) throws IOException {
        this.zztu.zza(this.zztt, outputStream);
    }
}
