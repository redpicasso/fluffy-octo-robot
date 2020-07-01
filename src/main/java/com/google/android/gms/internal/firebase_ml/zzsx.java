package com.google.android.gms.internal.firebase_ml;

import java.util.NoSuchElementException;

final class zzsx extends zzsz {
    private final int limit = this.zzbko.size();
    private int position = 0;
    private final /* synthetic */ zzsw zzbko;

    zzsx(zzsw zzsw) {
        this.zzbko = zzsw;
    }

    public final boolean hasNext() {
        return this.position < this.limit;
    }

    public final byte nextByte() {
        int i = this.position;
        if (i < this.limit) {
            this.position = i + 1;
            return this.zzbko.zzcm(i);
        }
        throw new NoSuchElementException();
    }
}
