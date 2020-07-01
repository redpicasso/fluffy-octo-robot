package com.google.android.gms.internal.firebase_ml;

import com.google.android.gms.internal.firebase_ml.zzue.zzf;
import java.io.IOException;
import java.util.Arrays;

public final class zzwx {
    private static final zzwx zzbsm = new zzwx(0, new int[0], new Object[0], false);
    private int count;
    private boolean zzbkd;
    private int zzboi;
    private Object[] zzbqy;
    private int[] zzbsn;

    public static zzwx zztg() {
        return zzbsm;
    }

    static zzwx zzth() {
        return new zzwx();
    }

    static zzwx zza(zzwx zzwx, zzwx zzwx2) {
        int i = zzwx.count + zzwx2.count;
        Object copyOf = Arrays.copyOf(zzwx.zzbsn, i);
        System.arraycopy(zzwx2.zzbsn, 0, copyOf, zzwx.count, zzwx2.count);
        Object copyOf2 = Arrays.copyOf(zzwx.zzbqy, i);
        System.arraycopy(zzwx2.zzbqy, 0, copyOf2, zzwx.count, zzwx2.count);
        return new zzwx(i, copyOf, copyOf2, true);
    }

    private zzwx() {
        this(0, new int[8], new Object[8], true);
    }

    private zzwx(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zzboi = -1;
        this.count = i;
        this.zzbsn = iArr;
        this.zzbqy = objArr;
        this.zzbkd = z;
    }

    public final void zzpt() {
        this.zzbkd = false;
    }

    final void zza(zzxr zzxr) throws IOException {
        int i;
        if (zzxr.zzqh() == zzf.zzbpa) {
            for (i = this.count - 1; i >= 0; i--) {
                zzxr.zza(this.zzbsn[i] >>> 3, this.zzbqy[i]);
            }
            return;
        }
        for (i = 0; i < this.count; i++) {
            zzxr.zza(this.zzbsn[i] >>> 3, this.zzbqy[i]);
        }
    }

    public final void zzb(zzxr zzxr) throws IOException {
        if (this.count != 0) {
            int i;
            if (zzxr.zzqh() == zzf.zzboz) {
                for (i = 0; i < this.count; i++) {
                    zzb(this.zzbsn[i], this.zzbqy[i], zzxr);
                }
                return;
            }
            for (i = this.count - 1; i >= 0; i--) {
                zzb(this.zzbsn[i], this.zzbqy[i], zzxr);
            }
        }
    }

    private static void zzb(int i, Object obj, zzxr zzxr) throws IOException {
        int i2 = i >>> 3;
        i &= 7;
        if (i == 0) {
            zzxr.zzi(i2, ((Long) obj).longValue());
        } else if (i == 1) {
            zzxr.zzc(i2, ((Long) obj).longValue());
        } else if (i == 2) {
            zzxr.zza(i2, (zzsw) obj);
        } else if (i != 3) {
            if (i == 5) {
                zzxr.zzk(i2, ((Integer) obj).intValue());
                return;
            }
            throw new RuntimeException(zzuo.zzrp());
        } else if (zzxr.zzqh() == zzf.zzboz) {
            zzxr.zzde(i2);
            ((zzwx) obj).zzb(zzxr);
            zzxr.zzdf(i2);
        } else {
            zzxr.zzdf(i2);
            ((zzwx) obj).zzb(zzxr);
            zzxr.zzde(i2);
        }
    }

    public final int zzti() {
        int i = this.zzboi;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (i = 0; i < this.count; i++) {
            i2 += zztl.zzd(this.zzbsn[i] >>> 3, (zzsw) this.zzbqy[i]);
        }
        this.zzboi = i2;
        return i2;
    }

    public final int zzqy() {
        int i = this.zzboi;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (i = 0; i < this.count; i++) {
            int i3 = this.zzbsn[i];
            int i4 = i3 >>> 3;
            i3 &= 7;
            if (i3 == 0) {
                i3 = zztl.zze(i4, ((Long) this.zzbqy[i]).longValue());
            } else if (i3 == 1) {
                i3 = zztl.zzg(i4, ((Long) this.zzbqy[i]).longValue());
            } else if (i3 == 2) {
                i3 = zztl.zzc(i4, (zzsw) this.zzbqy[i]);
            } else if (i3 == 3) {
                i3 = (zztl.zzcu(i4) << 1) + ((zzwx) this.zzbqy[i]).zzqy();
            } else if (i3 == 5) {
                i3 = zztl.zzo(i4, ((Integer) this.zzbqy[i]).intValue());
            } else {
                throw new IllegalStateException(zzuo.zzrp());
            }
            i2 += i3;
        }
        this.zzboi = i2;
        return i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzwx)) {
            return false;
        }
        zzwx zzwx = (zzwx) obj;
        int i = this.count;
        if (i == zzwx.count) {
            Object obj2;
            int[] iArr = this.zzbsn;
            int[] iArr2 = zzwx.zzbsn;
            for (int i2 = 0; i2 < i; i2++) {
                if (iArr[i2] != iArr2[i2]) {
                    obj2 = null;
                    break;
                }
            }
            obj2 = 1;
            if (obj2 != null) {
                Object[] objArr = this.zzbqy;
                Object[] objArr2 = zzwx.zzbqy;
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
        int[] iArr = this.zzbsn;
        int i3 = 17;
        int i4 = 17;
        for (int i5 = 0; i5 < i; i5++) {
            i4 = (i4 * 31) + iArr[i5];
        }
        i2 = (i2 + i4) * 31;
        Object[] objArr = this.zzbqy;
        for (int i6 = 0; i6 < this.count; i6++) {
            i3 = (i3 * 31) + objArr[i6].hashCode();
        }
        return i2 + i3;
    }

    final void zzb(StringBuilder stringBuilder, int i) {
        for (int i2 = 0; i2 < this.count; i2++) {
            zzvr.zza(stringBuilder, i, String.valueOf(this.zzbsn[i2] >>> 3), this.zzbqy[i2]);
        }
    }

    final void zzb(int i, Object obj) {
        if (this.zzbkd) {
            int i2;
            int i3 = this.count;
            if (i3 == this.zzbsn.length) {
                i2 = this.count + (i3 < 4 ? 8 : i3 >> 1);
                this.zzbsn = Arrays.copyOf(this.zzbsn, i2);
                this.zzbqy = Arrays.copyOf(this.zzbqy, i2);
            }
            int[] iArr = this.zzbsn;
            i2 = this.count;
            iArr[i2] = i;
            this.zzbqy[i2] = obj;
            this.count = i2 + 1;
            return;
        }
        throw new UnsupportedOperationException();
    }
}
