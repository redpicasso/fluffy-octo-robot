package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;

public abstract class zzfp {
    private String zzuy;
    private String zzuz;
    private long zzvg = -1;
    private zzhy zzvh;

    public abstract void addHeader(String str, String str2) throws IOException;

    public void zza(int i, int i2) throws IOException {
    }

    public abstract zzfq zzfo() throws IOException;

    public final void setContentLength(long j) throws IOException {
        this.zzvg = j;
    }

    public final long getContentLength() {
        return this.zzvg;
    }

    public final void setContentEncoding(String str) throws IOException {
        this.zzuy = str;
    }

    public final String getContentEncoding() {
        return this.zzuy;
    }

    public final void setContentType(String str) throws IOException {
        this.zzuz = str;
    }

    public final String getContentType() {
        return this.zzuz;
    }

    public final void zza(zzhy zzhy) throws IOException {
        this.zzvh = zzhy;
    }

    public final zzhy zzfn() {
        return this.zzvh;
    }
}
