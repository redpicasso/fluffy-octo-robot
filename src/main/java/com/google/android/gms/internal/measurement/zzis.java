package com.google.android.gms.internal.measurement;

public final class zzis implements Cloneable {
    private static final zzir zzaor = new zzir();
    private int mSize;
    private boolean zzaos;
    private int[] zzaot;
    private zzir[] zzaou;

    zzis() {
        this(10);
    }

    private zzis(int i) {
        this.zzaos = false;
        i = idealIntArraySize(i);
        this.zzaot = new int[i];
        this.zzaou = new zzir[i];
        this.mSize = 0;
    }

    final zzir zzcl(int i) {
        i = zzcn(i);
        if (i >= 0) {
            zzir[] zzirArr = this.zzaou;
            if (zzirArr[i] != zzaor) {
                return zzirArr[i];
            }
        }
        return null;
    }

    final void zza(int i, zzir zzir) {
        int zzcn = zzcn(i);
        if (zzcn >= 0) {
            this.zzaou[zzcn] = zzir;
            return;
        }
        Object obj;
        Object obj2;
        zzcn = ~zzcn;
        if (zzcn < this.mSize) {
            zzir[] zzirArr = this.zzaou;
            if (zzirArr[zzcn] == zzaor) {
                this.zzaot[zzcn] = i;
                zzirArr[zzcn] = zzir;
                return;
            }
        }
        int i2 = this.mSize;
        if (i2 >= this.zzaot.length) {
            i2 = idealIntArraySize(i2 + 1);
            obj = new int[i2];
            obj2 = new zzir[i2];
            Object obj3 = this.zzaot;
            System.arraycopy(obj3, 0, obj, 0, obj3.length);
            obj3 = this.zzaou;
            System.arraycopy(obj3, 0, obj2, 0, obj3.length);
            this.zzaot = obj;
            this.zzaou = obj2;
        }
        i2 = this.mSize;
        if (i2 - zzcn != 0) {
            obj = this.zzaot;
            int i3 = zzcn + 1;
            System.arraycopy(obj, zzcn, obj, i3, i2 - zzcn);
            obj2 = this.zzaou;
            System.arraycopy(obj2, zzcn, obj2, i3, this.mSize - zzcn);
        }
        this.zzaot[zzcn] = i;
        this.zzaou[zzcn] = zzir;
        this.mSize++;
    }

    final int size() {
        return this.mSize;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    final zzir zzcm(int i) {
        return this.zzaou[i];
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzis)) {
            return false;
        }
        zzis zzis = (zzis) obj;
        int i = this.mSize;
        if (i != zzis.mSize) {
            return false;
        }
        Object obj2;
        int[] iArr = this.zzaot;
        int[] iArr2 = zzis.zzaot;
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr[i2] != iArr2[i2]) {
                obj2 = null;
                break;
            }
        }
        obj2 = 1;
        if (obj2 != null) {
            zzir[] zzirArr = this.zzaou;
            zzir[] zzirArr2 = zzis.zzaou;
            int i3 = this.mSize;
            for (int i4 = 0; i4 < i3; i4++) {
                if (!zzirArr[i4].equals(zzirArr2[i4])) {
                    obj = null;
                    break;
                }
            }
            obj = 1;
            if (obj != null) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = 17;
        for (int i2 = 0; i2 < this.mSize; i2++) {
            i = (((i * 31) + this.zzaot[i2]) * 31) + this.zzaou[i2].hashCode();
        }
        return i;
    }

    private static int idealIntArraySize(int i) {
        i <<= 2;
        for (int i2 = 4; i2 < 32; i2++) {
            int i3 = (1 << i2) - 12;
            if (i <= i3) {
                i = i3;
                break;
            }
        }
        return i / 4;
    }

    private final int zzcn(int i) {
        int i2 = this.mSize - 1;
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) >>> 1;
            int i5 = this.zzaot[i4];
            if (i5 < i) {
                i3 = i4 + 1;
            } else if (i5 <= i) {
                return i4;
            } else {
                i2 = i4 - 1;
            }
        }
        return ~i3;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        int i = this.mSize;
        zzis zzis = new zzis(i);
        int i2 = 0;
        System.arraycopy(this.zzaot, 0, zzis.zzaot, 0, i);
        while (i2 < i) {
            zzir[] zzirArr = this.zzaou;
            if (zzirArr[i2] != null) {
                zzis.zzaou[i2] = (zzir) zzirArr[i2].clone();
            }
            i2++;
        }
        zzis.mSize = i;
        return zzis;
    }
}
