package com.google.android.gms.internal.firebase_ml;

abstract class zzkx extends zzjz<String> {
    private int limit;
    private int offset = 0;
    private final zzkc zzabj;
    private final boolean zzabk;
    final CharSequence zzabo;

    protected zzkx(zzku zzku, CharSequence charSequence) {
        this.zzabj = zzku.zzabj;
        this.zzabk = false;
        this.limit = zzku.limit;
        this.zzabo = charSequence;
    }

    abstract int zzaj(int i);

    abstract int zzak(int i);

    protected final /* synthetic */ Object zzid() {
        int i;
        int i2;
        int i3 = this.offset;
        while (true) {
            i = this.offset;
            if (i != -1) {
                i = zzaj(i);
                if (i == -1) {
                    i = this.zzabo.length();
                    this.offset = -1;
                } else {
                    this.offset = zzak(i);
                }
                i2 = this.offset;
                if (i2 == i3) {
                    this.offset = i2 + 1;
                    if (this.offset > this.zzabo.length()) {
                        this.offset = -1;
                    }
                } else {
                    while (i3 < i && this.zzabj.zzb(this.zzabo.charAt(i3))) {
                        i3++;
                    }
                    while (i > i3 && this.zzabj.zzb(this.zzabo.charAt(i - 1))) {
                        i--;
                    }
                    if (this.zzabk && i3 == i) {
                        i3 = this.offset;
                    } else {
                        i2 = this.limit;
                    }
                }
            } else {
                zzie();
                return null;
            }
        }
        i2 = this.limit;
        if (i2 == 1) {
            i = this.zzabo.length();
            this.offset = -1;
            while (i > i3 && this.zzabj.zzb(this.zzabo.charAt(i - 1))) {
                i--;
            }
        } else {
            this.limit = i2 - 1;
        }
        return this.zzabo.subSequence(i3, i).toString();
    }
}
