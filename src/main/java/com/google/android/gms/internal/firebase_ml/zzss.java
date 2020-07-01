package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;

final class zzss {
    static int zza(byte[] bArr, int i, zzst zzst) {
        int i2 = i + 1;
        i = bArr[i];
        if (i < (byte) 0) {
            return zza(i, bArr, i2, zzst);
        }
        zzst.zzbkg = i;
        return i2;
    }

    static int zza(int i, byte[] bArr, int i2, zzst zzst) {
        i &= 127;
        int i3 = i2 + 1;
        byte b = bArr[i2];
        if (b >= (byte) 0) {
            zzst.zzbkg = i | (b << 7);
            return i3;
        }
        i |= (b & 127) << 7;
        i2 = i3 + 1;
        byte b2 = bArr[i3];
        if (b2 >= (byte) 0) {
            zzst.zzbkg = i | (b2 << 14);
            return i2;
        }
        i |= (b2 & 127) << 14;
        i3 = i2 + 1;
        b = bArr[i2];
        if (b >= (byte) 0) {
            zzst.zzbkg = i | (b << 21);
            return i3;
        }
        i |= (b & 127) << 21;
        i2 = i3 + 1;
        b2 = bArr[i3];
        if (b2 >= (byte) 0) {
            zzst.zzbkg = i | (b2 << 28);
            return i2;
        }
        i |= (b2 & 127) << 28;
        while (true) {
            i3 = i2 + 1;
            if (bArr[i2] >= (byte) 0) {
                zzst.zzbkg = i;
                return i3;
            }
            i2 = i3;
        }
    }

    static int zzb(byte[] bArr, int i, zzst zzst) {
        int i2 = i + 1;
        long j = (long) bArr[i];
        if (j >= 0) {
            zzst.zzbkh = j;
            return i2;
        }
        j &= 127;
        i = i2 + 1;
        byte b = bArr[i2];
        j |= ((long) (b & 127)) << 7;
        int i3 = 7;
        while (b < (byte) 0) {
            i2 = i + 1;
            byte b2 = bArr[i];
            i3 += 7;
            j |= ((long) (b2 & 127)) << i3;
            int i4 = i2;
            b = b2;
            i = i4;
        }
        zzst.zzbkh = j;
        return i;
    }

    static int zza(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8)) | ((bArr[i + 2] & 255) << 16));
    }

    static long zzb(byte[] bArr, int i) {
        return ((((long) bArr[i + 7]) & 255) << 56) | (((((((((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8)) | ((((long) bArr[i + 2]) & 255) << 16)) | ((((long) bArr[i + 3]) & 255) << 24)) | ((((long) bArr[i + 4]) & 255) << 32)) | ((((long) bArr[i + 5]) & 255) << 40)) | ((((long) bArr[i + 6]) & 255) << 48));
    }

    static double zzc(byte[] bArr, int i) {
        return Double.longBitsToDouble(zzb(bArr, i));
    }

    static float zzd(byte[] bArr, int i) {
        return Float.intBitsToFloat(zza(bArr, i));
    }

    static int zzc(byte[] bArr, int i, zzst zzst) throws zzuo {
        i = zza(bArr, i, zzst);
        int i2 = zzst.zzbkg;
        if (i2 < 0) {
            throw zzuo.zzrn();
        } else if (i2 == 0) {
            zzst.zzbki = "";
            return i;
        } else {
            zzst.zzbki = new String(bArr, i, i2, zzug.UTF_8);
            return i + i2;
        }
    }

    static int zzd(byte[] bArr, int i, zzst zzst) throws zzuo {
        i = zza(bArr, i, zzst);
        int i2 = zzst.zzbkg;
        if (i2 < 0) {
            throw zzuo.zzrn();
        } else if (i2 == 0) {
            zzst.zzbki = "";
            return i;
        } else {
            zzst.zzbki = zzxe.zzh(bArr, i, i2);
            return i + i2;
        }
    }

    static int zze(byte[] bArr, int i, zzst zzst) throws zzuo {
        i = zza(bArr, i, zzst);
        int i2 = zzst.zzbkg;
        if (i2 < 0) {
            throw zzuo.zzrn();
        } else if (i2 > bArr.length - i) {
            throw zzuo.zzrm();
        } else if (i2 == 0) {
            zzst.zzbki = zzsw.zzbkl;
            return i;
        } else {
            zzst.zzbki = zzsw.zzc(bArr, i, i2);
            return i + i2;
        }
    }

    static int zza(zzwe zzwe, byte[] bArr, int i, int i2, zzst zzst) throws IOException {
        int i3 = i + 1;
        i = bArr[i];
        if (i < 0) {
            i3 = zza(i, bArr, i3, zzst);
            i = zzst.zzbkg;
        }
        int i4 = i3;
        if (i < 0 || i > i2 - i4) {
            throw zzuo.zzrm();
        }
        Object newInstance = zzwe.newInstance();
        i += i4;
        zzwe.zza(newInstance, bArr, i4, i, zzst);
        zzwe.zzq(newInstance);
        zzst.zzbki = newInstance;
        return i;
    }

    static int zza(zzwe zzwe, byte[] bArr, int i, int i2, int i3, zzst zzst) throws IOException {
        zzvs zzvs = (zzvs) zzwe;
        Object newInstance = zzvs.newInstance();
        int zza = zzvs.zza(newInstance, bArr, i, i2, i3, zzst);
        zzvs.zzq(newInstance);
        zzst.zzbki = newInstance;
        return zza;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzun<?> zzun, zzst zzst) {
        zzuf zzuf = (zzuf) zzun;
        i2 = zza(bArr, i2, zzst);
        zzuf.zzdh(zzst.zzbkg);
        while (i2 < i3) {
            int zza = zza(bArr, i2, zzst);
            if (i != zzst.zzbkg) {
                break;
            }
            i2 = zza(bArr, zza, zzst);
            zzuf.zzdh(zzst.zzbkg);
        }
        return i2;
    }

    static int zza(byte[] bArr, int i, zzun<?> zzun, zzst zzst) throws IOException {
        zzuf zzuf = (zzuf) zzun;
        i = zza(bArr, i, zzst);
        int i2 = zzst.zzbkg + i;
        while (i < i2) {
            i = zza(bArr, i, zzst);
            zzuf.zzdh(zzst.zzbkg);
        }
        if (i == i2) {
            return i;
        }
        throw zzuo.zzrm();
    }

    static int zza(zzwe<?> zzwe, int i, byte[] bArr, int i2, int i3, zzun<?> zzun, zzst zzst) throws IOException {
        i2 = zza((zzwe) zzwe, bArr, i2, i3, zzst);
        zzun.add(zzst.zzbki);
        while (i2 < i3) {
            int zza = zza(bArr, i2, zzst);
            if (i != zzst.zzbkg) {
                break;
            }
            i2 = zza((zzwe) zzwe, bArr, zza, i3, zzst);
            zzun.add(zzst.zzbki);
        }
        return i2;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzwx zzwx, zzst zzst) throws zzuo {
        if ((i >>> 3) != 0) {
            int i4 = i & 7;
            if (i4 == 0) {
                int zzb = zzb(bArr, i2, zzst);
                zzwx.zzb(i, Long.valueOf(zzst.zzbkh));
                return zzb;
            } else if (i4 == 1) {
                zzwx.zzb(i, Long.valueOf(zzb(bArr, i2)));
                return i2 + 8;
            } else if (i4 == 2) {
                i2 = zza(bArr, i2, zzst);
                i3 = zzst.zzbkg;
                if (i3 < 0) {
                    throw zzuo.zzrn();
                } else if (i3 <= bArr.length - i2) {
                    if (i3 == 0) {
                        zzwx.zzb(i, zzsw.zzbkl);
                    } else {
                        zzwx.zzb(i, zzsw.zzc(bArr, i2, i3));
                    }
                    return i2 + i3;
                } else {
                    throw zzuo.zzrm();
                }
            } else if (i4 == 3) {
                Object zzth = zzwx.zzth();
                int i5 = (i & -8) | 4;
                i4 = 0;
                while (i2 < i3) {
                    int zza = zza(bArr, i2, zzst);
                    i2 = zzst.zzbkg;
                    if (i2 == i5) {
                        i4 = i2;
                        i2 = zza;
                        break;
                    }
                    i4 = i2;
                    i2 = zza(i2, bArr, zza, i3, (zzwx) zzth, zzst);
                }
                if (i2 > i3 || r0 != i5) {
                    throw zzuo.zzrq();
                }
                zzwx.zzb(i, zzth);
                return i2;
            } else if (i4 == 5) {
                zzwx.zzb(i, Integer.valueOf(zza(bArr, i2)));
                return i2 + 4;
            } else {
                throw zzuo.zzro();
            }
        }
        throw zzuo.zzro();
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzst zzst) throws zzuo {
        if ((i >>> 3) != 0) {
            int i4 = i & 7;
            if (i4 == 0) {
                return zzb(bArr, i2, zzst);
            }
            if (i4 == 1) {
                return i2 + 8;
            }
            if (i4 == 2) {
                return zza(bArr, i2, zzst) + zzst.zzbkg;
            }
            if (i4 == 3) {
                i = (i & -8) | 4;
                i4 = 0;
                while (i2 < i3) {
                    i2 = zza(bArr, i2, zzst);
                    i4 = zzst.zzbkg;
                    if (i4 == i) {
                        break;
                    }
                    i2 = zza(i4, bArr, i2, i3, zzst);
                }
                if (i2 <= i3 && r0 == i) {
                    return i2;
                }
                throw zzuo.zzrq();
            } else if (i4 == 5) {
                return i2 + 4;
            } else {
                throw zzuo.zzro();
            }
        }
        throw zzuo.zzro();
    }
}
