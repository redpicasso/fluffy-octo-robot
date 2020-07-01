package com.google.android.gms.internal.firebase_ml;

import com.google.android.gms.internal.firebase_ml.zzue.zzf;

final class zzwd implements zzvm {
    private final int flags;
    private final String info;
    private final Object[] zzbqy;
    private final zzvo zzbrb;

    zzwd(zzvo zzvo, String str, Object[] objArr) {
        this.zzbrb = zzvo;
        this.info = str;
        this.zzbqy = objArr;
        char charAt = str.charAt(0);
        if (charAt < 55296) {
            this.flags = charAt;
            return;
        }
        int i = charAt & 8191;
        int i2 = 13;
        int i3 = 1;
        while (true) {
            int i4 = i3 + 1;
            char charAt2 = str.charAt(i3);
            if (charAt2 >= 55296) {
                i |= (charAt2 & 8191) << i2;
                i2 += 13;
                i3 = i4;
            } else {
                this.flags = i | (charAt2 << i2);
                return;
            }
        }
    }

    final String zzsq() {
        return this.info;
    }

    final Object[] zzsr() {
        return this.zzbqy;
    }

    public final zzvo zzsj() {
        return this.zzbrb;
    }

    public final int zzsh() {
        return (this.flags & 1) == 1 ? zzf.zzbow : zzf.zzbox;
    }

    public final boolean zzsi() {
        return (this.flags & 2) == 2;
    }
}
