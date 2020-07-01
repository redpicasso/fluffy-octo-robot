package com.google.android.gms.internal.firebase_ml;

import com.google.common.base.Ascii;

public final class zzgt extends zzgu {
    private static final byte[] zzxh = new byte[]{Ascii.CR, (byte) 10};
    private static final byte[] zzxi = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 43, (byte) 47};
    private static final byte[] zzxj = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 45, (byte) 95};
    private static final byte[] zzxk = new byte[]{(byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 62, (byte) -1, (byte) 62, (byte) -1, (byte) 63, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 58, (byte) 59, (byte) 60, (byte) 61, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, Ascii.VT, Ascii.FF, Ascii.CR, Ascii.SO, Ascii.SI, Ascii.DLE, (byte) 17, Ascii.DC2, (byte) 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.EM, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 63, (byte) -1, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, (byte) 32, (byte) 33, (byte) 34, (byte) 35, (byte) 36, (byte) 37, (byte) 38, (byte) 39, (byte) 40, (byte) 41, (byte) 42, (byte) 43, (byte) 44, (byte) 45, (byte) 46, (byte) 47, (byte) 48, (byte) 49, (byte) 50, (byte) 51};
    private final byte[] zzxl;
    private final byte[] zzxm;
    private final byte[] zzxn;
    private final int zzxo;
    private final int zzxp;

    public zzgt() {
        this(0);
    }

    private zzgt(int i) {
        this(0, zzxh);
    }

    private zzgt(int i, byte[] bArr) {
        this(0, bArr, false);
    }

    private zzgt(int i, byte[] bArr, boolean z) {
        super(3, 4, i, bArr == null ? 0 : bArr.length);
        this.zzxm = zzxk;
        if (bArr != null) {
            Object obj;
            if (bArr != null) {
                for (byte b : bArr) {
                    if (this.zzxr == b || zza(b)) {
                        obj = 1;
                        break;
                    }
                }
            }
            obj = null;
            if (obj != null) {
                String zzd = zzgw.zzd(bArr);
                StringBuilder stringBuilder = new StringBuilder("lineSeparator must not contain base64 characters: [");
                stringBuilder.append(zzd);
                stringBuilder.append("]");
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (i > 0) {
                this.zzxp = bArr.length + 4;
                this.zzxn = new byte[bArr.length];
                System.arraycopy(bArr, 0, this.zzxn, 0, bArr.length);
            } else {
                this.zzxp = 4;
                this.zzxn = null;
            }
        } else {
            this.zzxp = 4;
            this.zzxn = null;
        }
        this.zzxo = this.zzxp - 1;
        this.zzxl = z ? zzxj : zzxi;
    }

    final void zza(byte[] bArr, int i, int i2, zzgv zzgv) {
        if (!zzgv.eof) {
            if (i2 < 0) {
                zzgv.eof = true;
                if (zzgv.zzxz != 0 || this.zzxu != 0) {
                    Object zza = zza(this.zzxp, zzgv);
                    i = zzgv.pos;
                    i2 = zzgv.zzxz;
                    if (i2 != 0) {
                        if (i2 == 1) {
                            i2 = zzgv.pos;
                            zzgv.pos = i2 + 1;
                            zza[i2] = this.zzxl[(zzgv.zzxw >> 2) & 63];
                            i2 = zzgv.pos;
                            zzgv.pos = i2 + 1;
                            zza[i2] = this.zzxl[(zzgv.zzxw << 4) & 63];
                            if (this.zzxl == zzxi) {
                                i2 = zzgv.pos;
                                zzgv.pos = i2 + 1;
                                zza[i2] = this.zzxr;
                                i2 = zzgv.pos;
                                zzgv.pos = i2 + 1;
                                zza[i2] = this.zzxr;
                            }
                        } else if (i2 == 2) {
                            i2 = zzgv.pos;
                            zzgv.pos = i2 + 1;
                            zza[i2] = this.zzxl[(zzgv.zzxw >> 10) & 63];
                            i2 = zzgv.pos;
                            zzgv.pos = i2 + 1;
                            zza[i2] = this.zzxl[(zzgv.zzxw >> 4) & 63];
                            i2 = zzgv.pos;
                            zzgv.pos = i2 + 1;
                            zza[i2] = this.zzxl[(zzgv.zzxw << 2) & 63];
                            if (this.zzxl == zzxi) {
                                i2 = zzgv.pos;
                                zzgv.pos = i2 + 1;
                                zza[i2] = this.zzxr;
                            }
                        } else {
                            StringBuilder stringBuilder = new StringBuilder("Impossible modulus ");
                            stringBuilder.append(zzgv.zzxz);
                            throw new IllegalStateException(stringBuilder.toString());
                        }
                    }
                    zzgv.zzxy += zzgv.pos - i;
                    if (this.zzxu > 0 && zzgv.zzxy > 0) {
                        System.arraycopy(this.zzxn, 0, zza, zzgv.pos, this.zzxn.length);
                        zzgv.pos += this.zzxn.length;
                    }
                    return;
                }
                return;
            }
            int i3 = i;
            i = 0;
            while (i < i2) {
                Object zza2 = zza(this.zzxp, zzgv);
                zzgv.zzxz = (zzgv.zzxz + 1) % 3;
                int i4 = i3 + 1;
                i3 = bArr[i3];
                if (i3 < 0) {
                    i3 += 256;
                }
                zzgv.zzxw = (zzgv.zzxw << 8) + i3;
                if (zzgv.zzxz == 0) {
                    i3 = zzgv.pos;
                    zzgv.pos = i3 + 1;
                    zza2[i3] = this.zzxl[(zzgv.zzxw >> 18) & 63];
                    i3 = zzgv.pos;
                    zzgv.pos = i3 + 1;
                    zza2[i3] = this.zzxl[(zzgv.zzxw >> 12) & 63];
                    i3 = zzgv.pos;
                    zzgv.pos = i3 + 1;
                    zza2[i3] = this.zzxl[(zzgv.zzxw >> 6) & 63];
                    i3 = zzgv.pos;
                    zzgv.pos = i3 + 1;
                    zza2[i3] = this.zzxl[zzgv.zzxw & 63];
                    zzgv.zzxy += 4;
                    if (this.zzxu > 0 && this.zzxu <= zzgv.zzxy) {
                        System.arraycopy(this.zzxn, 0, zza2, zzgv.pos, this.zzxn.length);
                        zzgv.pos += this.zzxn.length;
                        zzgv.zzxy = 0;
                    }
                }
                i++;
                i3 = i4;
            }
        }
    }

    public static String zzb(byte[] bArr) {
        if (!(bArr == null || bArr.length == 0)) {
            zzgu zzgt = new zzgt(0, zzxh, true);
            long zzc = zzgt.zzc(bArr);
            if (zzc > 2147483647L) {
                StringBuilder stringBuilder = new StringBuilder("Input array too big, the output array would be bigger (");
                stringBuilder.append(zzc);
                stringBuilder.append(") than the specified maximum size of 2147483647");
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (!(bArr == null || bArr.length == 0)) {
                zzgv zzgv = new zzgv();
                zzgt.zza(bArr, 0, bArr.length, zzgv);
                zzgt.zza(bArr, 0, -1, zzgv);
                bArr = new byte[(zzgv.pos - zzgv.zzxx)];
                int length = bArr.length;
                if (zzgv.buffer != null) {
                    length = Math.min(zzgv.buffer != null ? zzgv.pos - zzgv.zzxx : 0, length);
                    System.arraycopy(zzgv.buffer, zzgv.zzxx, bArr, 0, length);
                    zzgv.zzxx += length;
                    if (zzgv.zzxx >= zzgv.pos) {
                        zzgv.buffer = null;
                    }
                } else {
                    boolean z = zzgv.eof;
                }
            }
        }
        return zzgw.zzd(bArr);
    }

    protected final boolean zza(byte b) {
        if (b >= (byte) 0) {
            byte[] bArr = this.zzxm;
            if (b < bArr.length && bArr[b] != (byte) -1) {
                return true;
            }
        }
        return false;
    }
}
