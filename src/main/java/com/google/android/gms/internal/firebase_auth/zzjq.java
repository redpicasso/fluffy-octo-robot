package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs.zzd;

final class zzjq implements zzja {
    private final int flags;
    private final String info;
    private final zzjc zzacr;
    private final Object[] zzacy;

    zzjq(zzjc zzjc, String str, Object[] objArr) {
        this.zzacr = zzjc;
        this.info = str;
        this.zzacy = objArr;
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

    final String zzjw() {
        return this.info;
    }

    final Object[] zzjx() {
        return this.zzacy;
    }

    public final zzjc zzjq() {
        return this.zzacr;
    }

    public final int zzjo() {
        return (this.flags & 1) == 1 ? zzd.zzaav : zzd.zzaaw;
    }

    public final boolean zzjp() {
        return (this.flags & 2) == 2;
    }
}
