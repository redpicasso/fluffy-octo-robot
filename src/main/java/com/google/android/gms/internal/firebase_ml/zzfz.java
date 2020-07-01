package com.google.android.gms.internal.firebase_ml;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

final class zzfz extends FilterInputStream {
    private long zzwb = 0;
    private final /* synthetic */ zzfy zzwc;

    public zzfz(zzfy zzfy, InputStream inputStream) {
        this.zzwc = zzfy;
        super(inputStream);
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        int read = this.in.read(bArr, i, i2);
        if (read == -1) {
            zzfw();
        } else {
            this.zzwb += (long) read;
        }
        return read;
    }

    public final int read() throws IOException {
        int read = this.in.read();
        if (read == -1) {
            zzfw();
        } else {
            this.zzwb++;
        }
        return read;
    }

    public final long skip(long j) throws IOException {
        j = this.in.skip(j);
        this.zzwb += j;
        return j;
    }

    private final void zzfw() throws IOException {
        long contentLength = this.zzwc.getContentLength();
        if (contentLength != -1) {
            long j = this.zzwb;
            if (j != 0 && j < contentLength) {
                StringBuilder stringBuilder = new StringBuilder(102);
                stringBuilder.append("Connection closed prematurely: bytesRead = ");
                stringBuilder.append(j);
                stringBuilder.append(", Content-Length = ");
                stringBuilder.append(contentLength);
                throw new IOException(stringBuilder.toString());
            }
        }
    }
}
