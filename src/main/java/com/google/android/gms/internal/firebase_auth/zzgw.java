package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

final class zzgw extends zzgr {
    private final byte[] buffer;
    private int pos;
    private int zzwk;
    private int zzwm;
    private int zzwn;
    private final InputStream zzwo;
    private int zzwp;
    private int zzwq;
    private zzgv zzwr;

    private zzgw(InputStream inputStream, int i) {
        super();
        this.zzwn = Integer.MAX_VALUE;
        this.zzwr = null;
        zzht.zza(inputStream, "input");
        this.zzwo = inputStream;
        this.buffer = new byte[i];
        this.zzwp = 0;
        this.pos = 0;
        this.zzwq = 0;
    }

    public final int zzgi() throws IOException {
        if (zzgy()) {
            this.zzwm = 0;
            return 0;
        }
        this.zzwm = zzha();
        int i = this.zzwm;
        if ((i >>> 3) != 0) {
            return i;
        }
        throw zzic.zziu();
    }

    public final void zzs(int i) throws zzic {
        if (this.zzwm != i) {
            throw zzic.zziv();
        }
    }

    public final boolean zzt(int i) throws IOException {
        int i2 = i & 7;
        int i3 = 0;
        if (i2 == 0) {
            if (this.zzwp - this.pos >= 10) {
                while (i3 < 10) {
                    byte[] bArr = this.buffer;
                    int i4 = this.pos;
                    this.pos = i4 + 1;
                    if (bArr[i4] < (byte) 0) {
                        i3++;
                    }
                }
                throw zzic.zzit();
            }
            while (i3 < 10) {
                if (zzhf() < (byte) 0) {
                    i3++;
                }
            }
            throw zzic.zzit();
            return true;
        } else if (i2 == 1) {
            zzx(8);
            return true;
        } else if (i2 == 2) {
            zzx(zzha());
            return true;
        } else if (i2 == 3) {
            do {
                i2 = zzgi();
                if (i2 == 0) {
                    break;
                }
            } while (zzt(i2));
            zzs(((i >>> 3) << 3) | 4);
            return true;
        } else if (i2 == 4) {
            return false;
        } else {
            if (i2 == 5) {
                zzx(4);
                return true;
            }
            throw zzic.zziw();
        }
    }

    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(zzhd());
    }

    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(zzhc());
    }

    public final long zzgj() throws IOException {
        return zzhb();
    }

    public final long zzgk() throws IOException {
        return zzhb();
    }

    public final int zzgl() throws IOException {
        return zzha();
    }

    public final long zzgm() throws IOException {
        return zzhd();
    }

    public final int zzgn() throws IOException {
        return zzhc();
    }

    public final boolean zzgo() throws IOException {
        return zzhb() != 0;
    }

    public final String readString() throws IOException {
        String str;
        int zzha = zzha();
        if (zzha > 0) {
            int i = this.zzwp;
            int i2 = this.pos;
            if (zzha <= i - i2) {
                str = new String(this.buffer, i2, zzha, zzht.UTF_8);
                this.pos += zzha;
                return str;
            }
        }
        if (zzha == 0) {
            return "";
        }
        if (zzha > this.zzwp) {
            return new String(zzb(zzha, false), zzht.UTF_8);
        }
        zzy(zzha);
        str = new String(this.buffer, this.pos, zzha, zzht.UTF_8);
        this.pos += zzha;
        return str;
    }

    public final String zzgp() throws IOException {
        byte[] bArr;
        int zzha = zzha();
        int i = this.pos;
        int i2 = 0;
        if (zzha <= this.zzwp - i && zzha > 0) {
            bArr = this.buffer;
            this.pos = i + zzha;
            i2 = i;
        } else if (zzha == 0) {
            return "";
        } else {
            if (zzha <= this.zzwp) {
                zzy(zzha);
                bArr = this.buffer;
                this.pos = zzha;
            } else {
                bArr = zzb(zzha, false);
            }
        }
        return zzkt.zzg(bArr, i2, zzha);
    }

    public final zzgf zzgq() throws IOException {
        int zzha = zzha();
        int i = this.zzwp;
        int i2 = this.pos;
        if (zzha <= i - i2 && zzha > 0) {
            zzgf zza = zzgf.zza(this.buffer, i2, zzha);
            this.pos += zzha;
            return zza;
        } else if (zzha == 0) {
            return zzgf.zzvv;
        } else {
            byte[] zzaa = zzaa(zzha);
            if (zzaa != null) {
                return zzgf.zza(zzaa);
            }
            i = this.pos;
            i2 = this.zzwp;
            int i3 = i2 - i;
            this.zzwq += i2;
            this.pos = 0;
            this.zzwp = 0;
            List<byte[]> zzab = zzab(zzha - i3);
            byte[] bArr = new byte[zzha];
            System.arraycopy(this.buffer, i, bArr, 0, i3);
            for (byte[] bArr2 : zzab) {
                System.arraycopy(bArr2, 0, bArr, i3, bArr2.length);
                i3 += bArr2.length;
            }
            return zzgf.zzb(bArr);
        }
    }

    public final int zzgr() throws IOException {
        return zzha();
    }

    public final int zzgs() throws IOException {
        return zzha();
    }

    public final int zzgt() throws IOException {
        return zzhc();
    }

    public final long zzgu() throws IOException {
        return zzhd();
    }

    public final int zzgv() throws IOException {
        return zzgr.zzw(zzha());
    }

    public final long zzgw() throws IOException {
        return zzgr.zza(zzhb());
    }

    /* JADX WARNING: Missing block: B:29:0x0066, code:
            if (r2[r3] >= (byte) 0) goto L_0x0068;
     */
    private final int zzha() throws java.io.IOException {
        /*
        r5 = this;
        r0 = r5.pos;
        r1 = r5.zzwp;
        if (r1 == r0) goto L_0x006b;
    L_0x0006:
        r2 = r5.buffer;
        r3 = r0 + 1;
        r0 = r2[r0];
        if (r0 < 0) goto L_0x0011;
    L_0x000e:
        r5.pos = r3;
        return r0;
    L_0x0011:
        r1 = r1 - r3;
        r4 = 9;
        if (r1 < r4) goto L_0x006b;
    L_0x0016:
        r1 = r3 + 1;
        r3 = r2[r3];
        r3 = r3 << 7;
        r0 = r0 ^ r3;
        if (r0 >= 0) goto L_0x0022;
    L_0x001f:
        r0 = r0 ^ -128;
        goto L_0x0068;
    L_0x0022:
        r3 = r1 + 1;
        r1 = r2[r1];
        r1 = r1 << 14;
        r0 = r0 ^ r1;
        if (r0 < 0) goto L_0x002f;
    L_0x002b:
        r0 = r0 ^ 16256;
    L_0x002d:
        r1 = r3;
        goto L_0x0068;
    L_0x002f:
        r1 = r3 + 1;
        r3 = r2[r3];
        r3 = r3 << 21;
        r0 = r0 ^ r3;
        if (r0 >= 0) goto L_0x003d;
    L_0x0038:
        r2 = -2080896; // 0xffffffffffe03f80 float:NaN double:NaN;
        r0 = r0 ^ r2;
        goto L_0x0068;
    L_0x003d:
        r3 = r1 + 1;
        r1 = r2[r1];
        r4 = r1 << 28;
        r0 = r0 ^ r4;
        r4 = 266354560; // 0xfe03f80 float:2.2112565E-29 double:1.315966377E-315;
        r0 = r0 ^ r4;
        if (r1 >= 0) goto L_0x002d;
    L_0x004a:
        r1 = r3 + 1;
        r3 = r2[r3];
        if (r3 >= 0) goto L_0x0068;
    L_0x0050:
        r3 = r1 + 1;
        r1 = r2[r1];
        if (r1 >= 0) goto L_0x002d;
    L_0x0056:
        r1 = r3 + 1;
        r3 = r2[r3];
        if (r3 >= 0) goto L_0x0068;
    L_0x005c:
        r3 = r1 + 1;
        r1 = r2[r1];
        if (r1 >= 0) goto L_0x002d;
    L_0x0062:
        r1 = r3 + 1;
        r2 = r2[r3];
        if (r2 < 0) goto L_0x006b;
    L_0x0068:
        r5.pos = r1;
        return r0;
    L_0x006b:
        r0 = r5.zzgx();
        r1 = (int) r0;
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzgw.zzha():int");
    }

    /* JADX WARNING: Missing block: B:35:0x00b0, code:
            if (((long) r2[r0]) >= 0) goto L_0x00b4;
     */
    private final long zzhb() throws java.io.IOException {
        /*
        r11 = this;
        r0 = r11.pos;
        r1 = r11.zzwp;
        if (r1 == r0) goto L_0x00b8;
    L_0x0006:
        r2 = r11.buffer;
        r3 = r0 + 1;
        r0 = r2[r0];
        if (r0 < 0) goto L_0x0012;
    L_0x000e:
        r11.pos = r3;
        r0 = (long) r0;
        return r0;
    L_0x0012:
        r1 = r1 - r3;
        r4 = 9;
        if (r1 < r4) goto L_0x00b8;
    L_0x0017:
        r1 = r3 + 1;
        r3 = r2[r3];
        r3 = r3 << 7;
        r0 = r0 ^ r3;
        if (r0 >= 0) goto L_0x0025;
    L_0x0020:
        r0 = r0 ^ -128;
    L_0x0022:
        r2 = (long) r0;
        goto L_0x00b5;
    L_0x0025:
        r3 = r1 + 1;
        r1 = r2[r1];
        r1 = r1 << 14;
        r0 = r0 ^ r1;
        if (r0 < 0) goto L_0x0036;
    L_0x002e:
        r0 = r0 ^ 16256;
        r0 = (long) r0;
        r9 = r0;
        r1 = r3;
        r2 = r9;
        goto L_0x00b5;
    L_0x0036:
        r1 = r3 + 1;
        r3 = r2[r3];
        r3 = r3 << 21;
        r0 = r0 ^ r3;
        if (r0 >= 0) goto L_0x0044;
    L_0x003f:
        r2 = -2080896; // 0xffffffffffe03f80 float:NaN double:NaN;
        r0 = r0 ^ r2;
        goto L_0x0022;
    L_0x0044:
        r3 = (long) r0;
        r0 = r1 + 1;
        r1 = r2[r1];
        r5 = (long) r1;
        r1 = 28;
        r5 = r5 << r1;
        r3 = r3 ^ r5;
        r5 = 0;
        r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r1 < 0) goto L_0x005b;
    L_0x0054:
        r1 = 266354560; // 0xfe03f80 float:2.2112565E-29 double:1.315966377E-315;
    L_0x0057:
        r2 = r3 ^ r1;
        r1 = r0;
        goto L_0x00b5;
    L_0x005b:
        r1 = r0 + 1;
        r0 = r2[r0];
        r7 = (long) r0;
        r0 = 35;
        r7 = r7 << r0;
        r3 = r3 ^ r7;
        r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r0 >= 0) goto L_0x0070;
    L_0x0068:
        r5 = -34093383808; // 0xfffffff80fe03f80 float:2.2112565E-29 double:NaN;
    L_0x006d:
        r2 = r3 ^ r5;
        goto L_0x00b5;
    L_0x0070:
        r0 = r1 + 1;
        r1 = r2[r1];
        r7 = (long) r1;
        r1 = 42;
        r7 = r7 << r1;
        r3 = r3 ^ r7;
        r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r1 < 0) goto L_0x0083;
    L_0x007d:
        r1 = 4363953127296; // 0x3f80fe03f80 float:2.2112565E-29 double:2.1560793202584E-311;
        goto L_0x0057;
    L_0x0083:
        r1 = r0 + 1;
        r0 = r2[r0];
        r7 = (long) r0;
        r0 = 49;
        r7 = r7 << r0;
        r3 = r3 ^ r7;
        r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r0 >= 0) goto L_0x0096;
    L_0x0090:
        r5 = -558586000294016; // 0xfffe03f80fe03f80 float:2.2112565E-29 double:NaN;
        goto L_0x006d;
    L_0x0096:
        r0 = r1 + 1;
        r1 = r2[r1];
        r7 = (long) r1;
        r1 = 56;
        r7 = r7 << r1;
        r3 = r3 ^ r7;
        r7 = 71499008037633920; // 0xfe03f80fe03f80 float:2.2112565E-29 double:6.838959413692434E-304;
        r3 = r3 ^ r7;
        r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r1 >= 0) goto L_0x00b3;
    L_0x00a9:
        r1 = r0 + 1;
        r0 = r2[r0];
        r7 = (long) r0;
        r0 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1));
        if (r0 < 0) goto L_0x00b8;
    L_0x00b2:
        goto L_0x00b4;
    L_0x00b3:
        r1 = r0;
    L_0x00b4:
        r2 = r3;
    L_0x00b5:
        r11.pos = r1;
        return r2;
    L_0x00b8:
        r0 = r11.zzgx();
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzgw.zzhb():long");
    }

    final long zzgx() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzhf = zzhf();
            j |= ((long) (zzhf & 127)) << i;
            if ((zzhf & 128) == 0) {
                return j;
            }
        }
        throw zzic.zzit();
    }

    private final int zzhc() throws IOException {
        int i = this.pos;
        if (this.zzwp - i < 4) {
            zzy(4);
            i = this.pos;
        }
        byte[] bArr = this.buffer;
        this.pos = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8)) | ((bArr[i + 2] & 255) << 16));
    }

    private final long zzhd() throws IOException {
        int i = this.pos;
        if (this.zzwp - i < 8) {
            zzy(8);
            i = this.pos;
        }
        byte[] bArr = this.buffer;
        this.pos = i + 8;
        return ((((long) bArr[i + 7]) & 255) << 56) | (((((((((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8)) | ((((long) bArr[i + 2]) & 255) << 16)) | ((((long) bArr[i + 3]) & 255) << 24)) | ((((long) bArr[i + 4]) & 255) << 32)) | ((((long) bArr[i + 5]) & 255) << 40)) | ((((long) bArr[i + 6]) & 255) << 48));
    }

    public final int zzu(int i) throws zzic {
        if (i >= 0) {
            i += this.zzwq + this.pos;
            int i2 = this.zzwn;
            if (i <= i2) {
                this.zzwn = i;
                zzhe();
                return i2;
            }
            throw zzic.zzir();
        }
        throw zzic.zzis();
    }

    private final void zzhe() {
        this.zzwp += this.zzwk;
        int i = this.zzwq;
        int i2 = this.zzwp;
        i += i2;
        int i3 = this.zzwn;
        if (i > i3) {
            this.zzwk = i - i3;
            this.zzwp = i2 - this.zzwk;
            return;
        }
        this.zzwk = 0;
    }

    public final void zzv(int i) {
        this.zzwn = i;
        zzhe();
    }

    public final boolean zzgy() throws IOException {
        return this.pos == this.zzwp && !zzz(1);
    }

    public final int zzgz() {
        return this.zzwq + this.pos;
    }

    private final void zzy(int i) throws IOException {
        if (!zzz(i)) {
            if (i > (this.zzwg - this.zzwq) - this.pos) {
                throw zzic.zzix();
            }
            throw zzic.zzir();
        }
    }

    private final boolean zzz(int i) throws IOException {
        while (this.pos + i > this.zzwp) {
            int i2 = this.zzwg;
            int i3 = this.zzwq;
            i2 -= i3;
            int i4 = this.pos;
            if (i > i2 - i4 || (i3 + i4) + i > this.zzwn) {
                return false;
            }
            if (i4 > 0) {
                i2 = this.zzwp;
                if (i2 > i4) {
                    Object obj = this.buffer;
                    System.arraycopy(obj, i4, obj, 0, i2 - i4);
                }
                this.zzwq += i4;
                this.zzwp -= i4;
                this.pos = 0;
            }
            InputStream inputStream = this.zzwo;
            byte[] bArr = this.buffer;
            i4 = this.zzwp;
            i2 = inputStream.read(bArr, i4, Math.min(bArr.length - i4, (this.zzwg - this.zzwq) - this.zzwp));
            if (i2 == 0 || i2 < -1 || i2 > this.buffer.length) {
                String valueOf = String.valueOf(this.zzwo.getClass());
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 91);
                stringBuilder.append(valueOf);
                stringBuilder.append("#read(byte[]) returned invalid result: ");
                stringBuilder.append(i2);
                stringBuilder.append("\nThe InputStream implementation is buggy.");
                throw new IllegalStateException(stringBuilder.toString());
            } else if (i2 <= 0) {
                return false;
            } else {
                this.zzwp += i2;
                zzhe();
                if (this.zzwp >= i) {
                    return true;
                }
            }
        }
        StringBuilder stringBuilder2 = new StringBuilder(77);
        stringBuilder2.append("refillBuffer() called when ");
        stringBuilder2.append(i);
        stringBuilder2.append(" bytes were already available in buffer");
        throw new IllegalStateException(stringBuilder2.toString());
    }

    private final byte zzhf() throws IOException {
        if (this.pos == this.zzwp) {
            zzy(1);
        }
        byte[] bArr = this.buffer;
        int i = this.pos;
        this.pos = i + 1;
        return bArr[i];
    }

    private final byte[] zzb(int i, boolean z) throws IOException {
        byte[] zzaa = zzaa(i);
        if (zzaa != null) {
            return zzaa;
        }
        int i2 = this.pos;
        int i3 = this.zzwp;
        int i4 = i3 - i2;
        this.zzwq += i3;
        this.pos = 0;
        this.zzwp = 0;
        List<byte[]> zzab = zzab(i - i4);
        Object obj = new byte[i];
        System.arraycopy(this.buffer, i2, obj, 0, i4);
        for (byte[] bArr : zzab) {
            System.arraycopy(bArr, 0, obj, i4, bArr.length);
            i4 += bArr.length;
        }
        return obj;
    }

    private final byte[] zzaa(int i) throws IOException {
        if (i == 0) {
            return zzht.EMPTY_BYTE_ARRAY;
        }
        if (i >= 0) {
            int i2 = (this.zzwq + this.pos) + i;
            if (i2 - this.zzwg <= 0) {
                int i3 = this.zzwn;
                if (i2 <= i3) {
                    i2 = this.zzwp - this.pos;
                    i3 = i - i2;
                    if (i3 >= 4096 && i3 > this.zzwo.available()) {
                        return null;
                    }
                    Object obj = new byte[i];
                    System.arraycopy(this.buffer, this.pos, obj, 0, i2);
                    this.zzwq += this.zzwp;
                    this.pos = 0;
                    this.zzwp = 0;
                    while (i2 < obj.length) {
                        int read = this.zzwo.read(obj, i2, i - i2);
                        if (read != -1) {
                            this.zzwq += read;
                            i2 += read;
                        } else {
                            throw zzic.zzir();
                        }
                    }
                    return obj;
                }
                zzx((i3 - this.zzwq) - this.pos);
                throw zzic.zzir();
            }
            throw zzic.zzix();
        }
        throw zzic.zzis();
    }

    private final List<byte[]> zzab(int i) throws IOException {
        List<byte[]> arrayList = new ArrayList();
        while (i > 0) {
            Object obj = new byte[Math.min(i, 4096)];
            int i2 = 0;
            while (i2 < obj.length) {
                int read = this.zzwo.read(obj, i2, obj.length - i2);
                if (read != -1) {
                    this.zzwq += read;
                    i2 += read;
                } else {
                    throw zzic.zzir();
                }
            }
            i -= obj.length;
            arrayList.add(obj);
        }
        return arrayList;
    }

    private final void zzx(int i) throws IOException {
        int i2 = this.zzwp;
        int i3 = this.pos;
        if (i <= i2 - i3 && i >= 0) {
            this.pos = i3 + i;
        } else if (i >= 0) {
            i2 = this.zzwq;
            i3 = this.pos;
            int i4 = (i2 + i3) + i;
            int i5 = this.zzwn;
            if (i4 <= i5) {
                this.zzwq = i2 + i3;
                i2 = this.zzwp - i3;
                this.zzwp = 0;
                this.pos = 0;
                while (i2 < i) {
                    try {
                        long j = (long) (i - i2);
                        long skip = this.zzwo.skip(j);
                        int i6 = (skip > 0 ? 1 : (skip == 0 ? 0 : -1));
                        if (i6 >= 0 && skip <= j) {
                            if (i6 == 0) {
                                break;
                            }
                            i2 += (int) skip;
                        } else {
                            String valueOf = String.valueOf(this.zzwo.getClass());
                            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 92);
                            stringBuilder.append(valueOf);
                            stringBuilder.append("#skip returned invalid result: ");
                            stringBuilder.append(skip);
                            stringBuilder.append("\nThe InputStream implementation is buggy.");
                            throw new IllegalStateException(stringBuilder.toString());
                        }
                    } catch (Throwable th) {
                        this.zzwq += i2;
                        zzhe();
                    }
                }
                this.zzwq += i2;
                zzhe();
                if (i2 < i) {
                    i2 = this.zzwp;
                    i3 = i2 - this.pos;
                    this.pos = i2;
                    zzy(1);
                    while (true) {
                        i4 = i - i3;
                        i5 = this.zzwp;
                        if (i4 <= i5) {
                            break;
                        }
                        i3 += i5;
                        this.pos = i5;
                        zzy(1);
                    }
                    this.pos = i4;
                }
                return;
            }
            zzx((i5 - i2) - i3);
            throw zzic.zzir();
        } else {
            throw zzic.zzis();
        }
    }
}
