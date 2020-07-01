package com.google.android.gms.internal.vision;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class zzjq implements Cloneable {
    private Object value;
    private zzjo<?, ?> zzadm;
    private List<zzjv> zzadn = new ArrayList();

    zzjq() {
    }

    final void zza(zzjv zzjv) throws IOException {
        List list = this.zzadn;
        if (list != null) {
            list.add(zzjv);
            return;
        }
        Object obj = this.value;
        if (obj instanceof zzjt) {
            byte[] bArr = zzjv.zzse;
            zzjk zzk = zzjk.zzk(bArr, 0, bArr.length);
            int zzdt = zzk.zzdt();
            if (zzdt == bArr.length - zzjl.zzaw(zzdt)) {
                zzjt zza = ((zzjt) this.value).zza(zzk);
                this.zzadm = this.zzadm;
                this.value = zza;
                this.zzadn = null;
                return;
            }
            throw zzjs.zzht();
        } else if (obj instanceof zzjt[]) {
            Collections.singletonList(zzjv);
            throw new NoSuchMethodError();
        } else {
            Collections.singletonList(zzjv);
            throw new NoSuchMethodError();
        }
    }

    final int zzt() {
        if (this.value == null) {
            int i = 0;
            for (zzjv zzjv : this.zzadn) {
                i += (zzjl.zzbd(zzjv.tag) + 0) + zzjv.zzse.length;
            }
            return i;
        }
        throw new NoSuchMethodError();
    }

    final void zza(zzjl zzjl) throws IOException {
        if (this.value == null) {
            for (zzjv zzjv : this.zzadn) {
                zzjl.zzbv(zzjv.tag);
                zzjl.zzl(zzjv.zzse);
            }
            return;
        }
        throw new NoSuchMethodError();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzjq)) {
            return false;
        }
        zzjq zzjq = (zzjq) obj;
        if (this.value == null || zzjq.value == null) {
            List list = this.zzadn;
            if (list != null) {
                List list2 = zzjq.zzadn;
                if (list2 != null) {
                    return list.equals(list2);
                }
            }
            try {
                return Arrays.equals(toByteArray(), zzjq.toByteArray());
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
        zzjo zzjo = this.zzadm;
        if (zzjo != zzjq.zzadm) {
            return false;
        }
        if (!zzjo.zzadh.isArray()) {
            return this.value.equals(zzjq.value);
        }
        Object obj2 = this.value;
        if (obj2 instanceof byte[]) {
            return Arrays.equals((byte[]) obj2, (byte[]) zzjq.value);
        }
        if (obj2 instanceof int[]) {
            return Arrays.equals((int[]) obj2, (int[]) zzjq.value);
        }
        if (obj2 instanceof long[]) {
            return Arrays.equals((long[]) obj2, (long[]) zzjq.value);
        }
        if (obj2 instanceof float[]) {
            return Arrays.equals((float[]) obj2, (float[]) zzjq.value);
        }
        if (obj2 instanceof double[]) {
            return Arrays.equals((double[]) obj2, (double[]) zzjq.value);
        }
        if (obj2 instanceof boolean[]) {
            return Arrays.equals((boolean[]) obj2, (boolean[]) zzjq.value);
        }
        return Arrays.deepEquals((Object[]) obj2, (Object[]) zzjq.value);
    }

    public final int hashCode() {
        try {
            return Arrays.hashCode(toByteArray()) + 527;
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    private final byte[] toByteArray() throws IOException {
        byte[] bArr = new byte[zzt()];
        zza(zzjl.zzk(bArr));
        return bArr;
    }

    private final zzjq zzhs() {
        zzjq zzjq = new zzjq();
        try {
            zzjq.zzadm = this.zzadm;
            if (this.zzadn == null) {
                zzjq.zzadn = null;
            } else {
                zzjq.zzadn.addAll(this.zzadn);
            }
            if (this.value != null) {
                if (this.value instanceof zzjt) {
                    zzjq.value = (zzjt) ((zzjt) this.value).clone();
                } else if (this.value instanceof byte[]) {
                    zzjq.value = ((byte[]) this.value).clone();
                } else {
                    int i = 0;
                    Object obj;
                    if (this.value instanceof byte[][]) {
                        byte[][] bArr = (byte[][]) this.value;
                        obj = new byte[bArr.length][];
                        zzjq.value = obj;
                        while (i < bArr.length) {
                            obj[i] = (byte[]) bArr[i].clone();
                            i++;
                        }
                    } else if (this.value instanceof boolean[]) {
                        zzjq.value = ((boolean[]) this.value).clone();
                    } else if (this.value instanceof int[]) {
                        zzjq.value = ((int[]) this.value).clone();
                    } else if (this.value instanceof long[]) {
                        zzjq.value = ((long[]) this.value).clone();
                    } else if (this.value instanceof float[]) {
                        zzjq.value = ((float[]) this.value).clone();
                    } else if (this.value instanceof double[]) {
                        zzjq.value = ((double[]) this.value).clone();
                    } else if (this.value instanceof zzjt[]) {
                        zzjt[] zzjtArr = (zzjt[]) this.value;
                        obj = new zzjt[zzjtArr.length];
                        zzjq.value = obj;
                        while (i < zzjtArr.length) {
                            obj[i] = (zzjt) zzjtArr[i].clone();
                            i++;
                        }
                    }
                }
            }
            return zzjq;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
