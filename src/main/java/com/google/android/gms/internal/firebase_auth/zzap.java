package com.google.android.gms.internal.firebase_auth;

abstract class zzap extends zzaa<String> {
    private int limit;
    private int offset = 0;
    private final zzae zzgm;
    private final boolean zzgn;
    final CharSequence zzgr;

    protected zzap(zzam zzam, CharSequence charSequence) {
        this.zzgm = zzam.zzgm;
        this.zzgn = false;
        this.limit = zzam.limit;
        this.zzgr = charSequence;
    }

    abstract int zze(int i);

    abstract int zzf(int i);

    protected final /* synthetic */ Object zzbw() {
        int i;
        int i2;
        int i3 = this.offset;
        while (true) {
            i = this.offset;
            if (i != -1) {
                i = zze(i);
                if (i == -1) {
                    i = this.zzgr.length();
                    this.offset = -1;
                } else {
                    this.offset = zzf(i);
                }
                i2 = this.offset;
                if (i2 == i3) {
                    this.offset = i2 + 1;
                    if (this.offset > this.zzgr.length()) {
                        this.offset = -1;
                    }
                } else {
                    while (i3 < i && this.zzgm.zza(this.zzgr.charAt(i3))) {
                        i3++;
                    }
                    while (i > i3 && this.zzgm.zza(this.zzgr.charAt(i - 1))) {
                        i--;
                    }
                    if (this.zzgn && i3 == i) {
                        i3 = this.offset;
                    } else {
                        i2 = this.limit;
                    }
                }
            } else {
                zzbx();
                return null;
            }
        }
        i2 = this.limit;
        if (i2 == 1) {
            i = this.zzgr.length();
            this.offset = -1;
            while (i > i3 && this.zzgm.zza(this.zzgr.charAt(i - 1))) {
                i--;
            }
        } else {
            this.limit = i2 - 1;
        }
        return this.zzgr.subSequence(i3, i).toString();
    }
}
