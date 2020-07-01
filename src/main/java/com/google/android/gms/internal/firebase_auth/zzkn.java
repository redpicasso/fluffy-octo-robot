package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs.zzd;
import java.io.IOException;
import java.util.Arrays;

public final class zzkn {
    private static final zzkn zzael = new zzkn(0, new int[0], new Object[0], false);
    private int count;
    private int zzaak;
    private Object[] zzacy;
    private int[] zzaem;
    private boolean zzvo;

    public static zzkn zzko() {
        return zzael;
    }

    static zzkn zzkp() {
        return new zzkn();
    }

    static zzkn zza(zzkn zzkn, zzkn zzkn2) {
        int i = zzkn.count + zzkn2.count;
        Object copyOf = Arrays.copyOf(zzkn.zzaem, i);
        System.arraycopy(zzkn2.zzaem, 0, copyOf, zzkn.count, zzkn2.count);
        Object copyOf2 = Arrays.copyOf(zzkn.zzacy, i);
        System.arraycopy(zzkn2.zzacy, 0, copyOf2, zzkn.count, zzkn2.count);
        return new zzkn(i, copyOf, copyOf2, true);
    }

    private zzkn() {
        this(0, new int[8], new Object[8], true);
    }

    private zzkn(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zzaak = -1;
        this.count = i;
        this.zzaem = iArr;
        this.zzacy = objArr;
        this.zzvo = z;
    }

    public final void zzfy() {
        this.zzvo = false;
    }

    final void zza(zzlh zzlh) throws IOException {
        int i;
        if (zzlh.zzhl() == zzd.zzaaz) {
            for (i = this.count - 1; i >= 0; i--) {
                zzlh.zza(this.zzaem[i] >>> 3, this.zzacy[i]);
            }
            return;
        }
        for (i = 0; i < this.count; i++) {
            zzlh.zza(this.zzaem[i] >>> 3, this.zzacy[i]);
        }
    }

    public final void zzb(zzlh zzlh) throws IOException {
        if (this.count != 0) {
            int i;
            if (zzlh.zzhl() == zzd.zzaay) {
                for (i = 0; i < this.count; i++) {
                    zzb(this.zzaem[i], this.zzacy[i], zzlh);
                }
                return;
            }
            for (i = this.count - 1; i >= 0; i--) {
                zzb(this.zzaem[i], this.zzacy[i], zzlh);
            }
        }
    }

    private static void zzb(int i, Object obj, zzlh zzlh) throws IOException {
        int i2 = i >>> 3;
        i &= 7;
        if (i == 0) {
            zzlh.zzi(i2, ((Long) obj).longValue());
        } else if (i == 1) {
            zzlh.zzc(i2, ((Long) obj).longValue());
        } else if (i == 2) {
            zzlh.zza(i2, (zzgf) obj);
        } else if (i != 3) {
            if (i == 5) {
                zzlh.zzi(i2, ((Integer) obj).intValue());
                return;
            }
            throw new RuntimeException(zzic.zziw());
        } else if (zzlh.zzhl() == zzd.zzaay) {
            zzlh.zzat(i2);
            ((zzkn) obj).zzb(zzlh);
            zzlh.zzau(i2);
        } else {
            zzlh.zzau(i2);
            ((zzkn) obj).zzb(zzlh);
            zzlh.zzat(i2);
        }
    }

    public final int zzkq() {
        int i = this.zzaak;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (i = 0; i < this.count; i++) {
            i2 += zzha.zzd(this.zzaem[i] >>> 3, (zzgf) this.zzacy[i]);
        }
        this.zzaak = i2;
        return i2;
    }

    public final int zzik() {
        int i = this.zzaak;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (i = 0; i < this.count; i++) {
            int i3 = this.zzaem[i];
            int i4 = i3 >>> 3;
            i3 &= 7;
            if (i3 == 0) {
                i3 = zzha.zze(i4, ((Long) this.zzacy[i]).longValue());
            } else if (i3 == 1) {
                i3 = zzha.zzg(i4, ((Long) this.zzacy[i]).longValue());
            } else if (i3 == 2) {
                i3 = zzha.zzc(i4, (zzgf) this.zzacy[i]);
            } else if (i3 == 3) {
                i3 = (zzha.zzak(i4) << 1) + ((zzkn) this.zzacy[i]).zzik();
            } else if (i3 == 5) {
                i3 = zzha.zzm(i4, ((Integer) this.zzacy[i]).intValue());
            } else {
                throw new IllegalStateException(zzic.zziw());
            }
            i2 += i3;
        }
        this.zzaak = i2;
        return i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzkn)) {
            return false;
        }
        zzkn zzkn = (zzkn) obj;
        int i = this.count;
        if (i == zzkn.count) {
            Object obj2;
            int[] iArr = this.zzaem;
            int[] iArr2 = zzkn.zzaem;
            for (int i2 = 0; i2 < i; i2++) {
                if (iArr[i2] != iArr2[i2]) {
                    obj2 = null;
                    break;
                }
            }
            obj2 = 1;
            if (obj2 != null) {
                Object[] objArr = this.zzacy;
                Object[] objArr2 = zzkn.zzacy;
                int i3 = this.count;
                for (int i4 = 0; i4 < i3; i4++) {
                    if (!objArr[i4].equals(objArr2[i4])) {
                        obj = null;
                        break;
                    }
                }
                obj = 1;
                return obj != null;
            }
        }
    }

    public final int hashCode() {
        int i = this.count;
        int i2 = (i + 527) * 31;
        int[] iArr = this.zzaem;
        int i3 = 17;
        int i4 = 17;
        for (int i5 = 0; i5 < i; i5++) {
            i4 = (i4 * 31) + iArr[i5];
        }
        i2 = (i2 + i4) * 31;
        Object[] objArr = this.zzacy;
        for (int i6 = 0; i6 < this.count; i6++) {
            i3 = (i3 * 31) + objArr[i6].hashCode();
        }
        return i2 + i3;
    }

    final void zza(StringBuilder stringBuilder, int i) {
        for (int i2 = 0; i2 < this.count; i2++) {
            zzjd.zza(stringBuilder, i, String.valueOf(this.zzaem[i2] >>> 3), this.zzacy[i2]);
        }
    }

    final void zzb(int i, Object obj) {
        if (this.zzvo) {
            int i2;
            int i3 = this.count;
            if (i3 == this.zzaem.length) {
                i2 = this.count + (i3 < 4 ? 8 : i3 >> 1);
                this.zzaem = Arrays.copyOf(this.zzaem, i2);
                this.zzacy = Arrays.copyOf(this.zzacy, i2);
            }
            int[] iArr = this.zzaem;
            i2 = this.count;
            iArr[i2] = i;
            this.zzacy[i2] = obj;
            this.count = i2 + 1;
            return;
        }
        throw new UnsupportedOperationException();
    }
}
