package com.google.android.gms.internal.firebase_ml;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class zzhq extends FilterInputStream {
    private final zzhp zzaac;

    public zzhq(InputStream inputStream, Logger logger, Level level, int i) {
        super(inputStream);
        this.zzaac = new zzhp(logger, level, i);
    }

    public final int read() throws IOException {
        int read = super.read();
        this.zzaac.write(read);
        return read;
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        i2 = super.read(bArr, i, i2);
        if (i2 > 0) {
            this.zzaac.write(bArr, i, i2);
        }
        return i2;
    }

    public final void close() throws IOException {
        this.zzaac.close();
        super.close();
    }
}
