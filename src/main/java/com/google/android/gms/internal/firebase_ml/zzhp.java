package com.google.android.gms.internal.firebase_ml;

import com.bumptech.glide.load.Key;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class zzhp extends ByteArrayOutputStream {
    private boolean closed;
    private final Logger logger;
    private final int zzaaa;
    private final Level zzaab;
    private int zzzz;

    public zzhp(Logger logger, Level level, int i) {
        this.logger = (Logger) zzks.checkNotNull(logger);
        this.zzaab = (Level) zzks.checkNotNull(level);
        zzks.checkArgument(i >= 0);
        this.zzaaa = i;
    }

    public final synchronized void write(int i) {
        zzks.checkArgument(!this.closed);
        this.zzzz++;
        if (this.count < this.zzaaa) {
            super.write(i);
        }
    }

    public final synchronized void write(byte[] bArr, int i, int i2) {
        zzks.checkArgument(!this.closed);
        this.zzzz += i2;
        if (this.count < this.zzaaa) {
            int i3 = this.count + i2;
            if (i3 > this.zzaaa) {
                i2 += this.zzaaa - i3;
            }
            super.write(bArr, i, i2);
        }
    }

    public final synchronized void close() throws IOException {
        if (!this.closed) {
            if (this.zzzz != 0) {
                StringBuilder stringBuilder = new StringBuilder("Total: ");
                zza(stringBuilder, this.zzzz);
                if (this.count != 0 && this.count < this.zzzz) {
                    stringBuilder.append(" (logging first ");
                    zza(stringBuilder, this.count);
                    stringBuilder.append(")");
                }
                this.logger.logp(Level.CONFIG, "com.google.api.client.util.LoggingByteArrayOutputStream", "close", stringBuilder.toString());
                if (this.count != 0) {
                    this.logger.logp(this.zzaab, "com.google.api.client.util.LoggingByteArrayOutputStream", "close", toString(Key.STRING_CHARSET_NAME).replaceAll("[\\x00-\\x09\\x0B\\x0C\\x0E-\\x1F\\x7F]", " "));
                }
            }
            this.closed = true;
        }
    }

    private static void zza(StringBuilder stringBuilder, int i) {
        if (i == 1) {
            stringBuilder.append("1 byte");
            return;
        }
        stringBuilder.append(NumberFormat.getInstance().format((long) i));
        stringBuilder.append(" bytes");
    }
}
