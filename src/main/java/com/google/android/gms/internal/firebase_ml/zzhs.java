package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class zzhs implements zzhy {
    private final Logger logger;
    private final Level zzaab;
    private final zzhy zztt;
    private final int zzuk;

    public zzhs(zzhy zzhy, Logger logger, Level level, int i) {
        this.zztt = zzhy;
        this.logger = logger;
        this.zzaab = level;
        this.zzuk = i;
    }

    public final void writeTo(OutputStream outputStream) throws IOException {
        OutputStream zzhr = new zzhr(outputStream, this.logger, this.zzaab, this.zzuk);
        try {
            this.zztt.writeTo(zzhr);
            outputStream.flush();
        } finally {
            zzhr.zzhj().close();
        }
    }
}
