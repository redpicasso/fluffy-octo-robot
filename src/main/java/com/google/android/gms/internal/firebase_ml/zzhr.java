package com.google.android.gms.internal.firebase_ml;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class zzhr extends FilterOutputStream {
    private final zzhp zzaac;

    public zzhr(OutputStream outputStream, Logger logger, Level level, int i) {
        super(outputStream);
        this.zzaac = new zzhp(logger, level, i);
    }

    public final void write(int i) throws IOException {
        this.out.write(i);
        this.zzaac.write(i);
    }

    public final void write(byte[] bArr, int i, int i2) throws IOException {
        this.out.write(bArr, i, i2);
        this.zzaac.write(bArr, i, i2);
    }

    public final void close() throws IOException {
        this.zzaac.close();
        super.close();
    }

    public final zzhp zzhj() {
        return this.zzaac;
    }
}
