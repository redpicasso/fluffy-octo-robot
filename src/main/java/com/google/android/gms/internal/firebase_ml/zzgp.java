package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

final class zzgp extends zzgf {
    private final zzqq zzxa;
    private final zzgn zzxb;

    zzgp(zzgn zzgn, zzqq zzqq) {
        this.zzxb = zzgn;
        this.zzxa = zzqq;
        zzqq.setLenient(true);
    }

    public final void flush() throws IOException {
        this.zzxa.flush();
    }

    public final void writeBoolean(boolean z) throws IOException {
        this.zzxa.zzag(z);
    }

    public final void zzfz() throws IOException {
        this.zzxa.zzny();
    }

    public final void zzgb() throws IOException {
        this.zzxa.zzoa();
    }

    public final void zzan(String str) throws IOException {
        this.zzxa.zzcj(str);
    }

    public final void zzgc() throws IOException {
        this.zzxa.zzoc();
    }

    public final void zzad(int i) throws IOException {
        this.zzxa.zzo((long) i);
    }

    public final void zzg(long j) throws IOException {
        this.zzxa.zzo(j);
    }

    public final void zza(BigInteger bigInteger) throws IOException {
        this.zzxa.zza(bigInteger);
    }

    public final void zza(double d) throws IOException {
        this.zzxa.zzb(d);
    }

    public final void zzl(float f) throws IOException {
        this.zzxa.zzb((double) f);
    }

    public final void zza(BigDecimal bigDecimal) throws IOException {
        this.zzxa.zza(bigDecimal);
    }

    public final void zzfy() throws IOException {
        this.zzxa.zznx();
    }

    public final void zzga() throws IOException {
        this.zzxa.zznz();
    }

    public final void writeString(String str) throws IOException {
        this.zzxa.zzck(str);
    }

    public final void zzgd() throws IOException {
        this.zzxa.setIndent("  ");
    }
}
