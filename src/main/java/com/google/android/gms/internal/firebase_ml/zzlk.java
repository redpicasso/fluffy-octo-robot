package com.google.android.gms.internal.firebase_ml;

import java.util.Arrays;

public final class zzlk<K, V> {
    private int size;
    private Object[] zzadd;
    private boolean zzade;

    public zzlk() {
        this(4);
    }

    private zzlk(int i) {
        this.zzadd = new Object[8];
        this.size = 0;
        this.zzade = false;
    }

    public final zzlk<K, V> zzd(K k, V v) {
        int i = (this.size + 1) << 1;
        Object[] objArr = this.zzadd;
        if (i > objArr.length) {
            int length = objArr.length;
            if (i >= 0) {
                length = (length + (length >> 1)) + 1;
                if (length < i) {
                    length = Integer.highestOneBit(i - 1) << 1;
                }
                if (length < 0) {
                    length = Integer.MAX_VALUE;
                }
                this.zzadd = Arrays.copyOf(objArr, length);
                this.zzade = false;
            } else {
                throw new AssertionError("cannot store more than MAX_VALUE elements");
            }
        }
        zzld.zzc(k, v);
        Object[] objArr2 = this.zzadd;
        int i2 = this.size;
        objArr2[i2 * 2] = k;
        objArr2[(i2 * 2) + 1] = v;
        this.size = i2 + 1;
        return this;
    }

    public final zzlj<K, V> zzit() {
        this.zzade = true;
        return zzln.zza(this.size, this.zzadd);
    }
}
