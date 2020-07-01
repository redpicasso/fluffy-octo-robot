package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.util.List;
import java.util.Map;

final class zzgy implements zzjp {
    private int tag;
    private final zzgr zzwt;
    private int zzwu;
    private int zzwv = 0;

    public static zzgy zza(zzgr zzgr) {
        if (zzgr.zzwh != null) {
            return zzgr.zzwh;
        }
        return new zzgy(zzgr);
    }

    private zzgy(zzgr zzgr) {
        this.zzwt = (zzgr) zzht.zza(zzgr, "input");
        this.zzwt.zzwh = this;
    }

    public final int zzhg() throws IOException {
        int i = this.zzwv;
        if (i != 0) {
            this.tag = i;
            this.zzwv = 0;
        } else {
            this.tag = this.zzwt.zzgi();
        }
        i = this.tag;
        return (i == 0 || i == this.zzwu) ? Integer.MAX_VALUE : i >>> 3;
    }

    public final int getTag() {
        return this.tag;
    }

    public final boolean zzhh() throws IOException {
        if (!this.zzwt.zzgy()) {
            int i = this.tag;
            if (i != this.zzwu) {
                return this.zzwt.zzt(i);
            }
        }
        return false;
    }

    private final void zzac(int i) throws IOException {
        if ((this.tag & 7) != i) {
            throw zzic.zziw();
        }
    }

    public final double readDouble() throws IOException {
        zzac(1);
        return this.zzwt.readDouble();
    }

    public final float readFloat() throws IOException {
        zzac(5);
        return this.zzwt.readFloat();
    }

    public final long zzgj() throws IOException {
        zzac(0);
        return this.zzwt.zzgj();
    }

    public final long zzgk() throws IOException {
        zzac(0);
        return this.zzwt.zzgk();
    }

    public final int zzgl() throws IOException {
        zzac(0);
        return this.zzwt.zzgl();
    }

    public final long zzgm() throws IOException {
        zzac(1);
        return this.zzwt.zzgm();
    }

    public final int zzgn() throws IOException {
        zzac(5);
        return this.zzwt.zzgn();
    }

    public final boolean zzgo() throws IOException {
        zzac(0);
        return this.zzwt.zzgo();
    }

    public final String readString() throws IOException {
        zzac(2);
        return this.zzwt.readString();
    }

    public final String zzgp() throws IOException {
        zzac(2);
        return this.zzwt.zzgp();
    }

    public final <T> T zza(zzjs<T> zzjs, zzhf zzhf) throws IOException {
        zzac(2);
        return zzc(zzjs, zzhf);
    }

    public final <T> T zzb(zzjs<T> zzjs, zzhf zzhf) throws IOException {
        zzac(3);
        return zzd(zzjs, zzhf);
    }

    private final <T> T zzc(zzjs<T> zzjs, zzhf zzhf) throws IOException {
        int zzgr = this.zzwt.zzgr();
        if (this.zzwt.zzwe < this.zzwt.zzwf) {
            zzgr = this.zzwt.zzu(zzgr);
            T newInstance = zzjs.newInstance();
            zzgr zzgr2 = this.zzwt;
            zzgr2.zzwe++;
            zzjs.zza(newInstance, this, zzhf);
            zzjs.zzf(newInstance);
            this.zzwt.zzs(0);
            zzgr zzgr3 = this.zzwt;
            zzgr3.zzwe--;
            this.zzwt.zzv(zzgr);
            return newInstance;
        }
        throw new zzic("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }

    private final <T> T zzd(zzjs<T> zzjs, zzhf zzhf) throws IOException {
        int i = this.zzwu;
        T t = ((this.tag >>> 3) << 3) | 4;
        this.zzwu = t;
        try {
            t = zzjs.newInstance();
            zzjs.zza(t, this, zzhf);
            zzjs.zzf(t);
            if (this.tag == this.zzwu) {
                return t;
            }
            throw zzic.zziy();
        } finally {
            this.zzwu = i;
        }
    }

    public final zzgf zzgq() throws IOException {
        zzac(2);
        return this.zzwt.zzgq();
    }

    public final int zzgr() throws IOException {
        zzac(0);
        return this.zzwt.zzgr();
    }

    public final int zzgs() throws IOException {
        zzac(0);
        return this.zzwt.zzgs();
    }

    public final int zzgt() throws IOException {
        zzac(5);
        return this.zzwt.zzgt();
    }

    public final long zzgu() throws IOException {
        zzac(1);
        return this.zzwt.zzgu();
    }

    public final int zzgv() throws IOException {
        zzac(0);
        return this.zzwt.zzgv();
    }

    public final long zzgw() throws IOException {
        zzac(0);
        return this.zzwt.zzgw();
    }

    public final void zzh(List<Double> list) throws IOException {
        int zzgz;
        if (list instanceof zzhb) {
            zzhb zzhb = (zzhb) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zzhb.zzc(this.zzwt.readDouble());
                    if (!this.zzwt.zzgy()) {
                        i = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (i == this.tag);
                this.zzwv = i;
                return;
            } else if (i == 2) {
                i = this.zzwt.zzgr();
                zzad(i);
                zzgz = this.zzwt.zzgz() + i;
                do {
                    zzhb.zzc(this.zzwt.readDouble());
                } while (this.zzwt.zzgz() < zzgz);
                return;
            } else {
                throw zzic.zziw();
            }
        }
        int i2 = this.tag & 7;
        if (i2 == 1) {
            do {
                list.add(Double.valueOf(this.zzwt.readDouble()));
                if (!this.zzwt.zzgy()) {
                    i2 = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (i2 == this.tag);
            this.zzwv = i2;
        } else if (i2 == 2) {
            i2 = this.zzwt.zzgr();
            zzad(i2);
            zzgz = this.zzwt.zzgz() + i2;
            do {
                list.add(Double.valueOf(this.zzwt.readDouble()));
            } while (this.zzwt.zzgz() < zzgz);
        } else {
            throw zzic.zziw();
        }
    }

    public final void zzi(List<Float> list) throws IOException {
        if (list instanceof zzho) {
            zzho zzho = (zzho) list;
            int i = this.tag & 7;
            if (i == 2) {
                i = this.zzwt.zzgr();
                zzae(i);
                int zzgz = this.zzwt.zzgz() + i;
                do {
                    zzho.zzc(this.zzwt.readFloat());
                } while (this.zzwt.zzgz() < zzgz);
                return;
            } else if (i == 5) {
                do {
                    zzho.zzc(this.zzwt.readFloat());
                    if (!this.zzwt.zzgy()) {
                        i = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (i == this.tag);
                this.zzwv = i;
                return;
            } else {
                throw zzic.zziw();
            }
        }
        int i2 = this.tag & 7;
        if (i2 == 2) {
            i2 = this.zzwt.zzgr();
            zzae(i2);
            int zzgz2 = this.zzwt.zzgz() + i2;
            do {
                list.add(Float.valueOf(this.zzwt.readFloat()));
            } while (this.zzwt.zzgz() < zzgz2);
        } else if (i2 == 5) {
            do {
                list.add(Float.valueOf(this.zzwt.readFloat()));
                if (!this.zzwt.zzgy()) {
                    i2 = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (i2 == this.tag);
            this.zzwv = i2;
        } else {
            throw zzic.zziw();
        }
    }

    public final void zzj(List<Long> list) throws IOException {
        int zzgz;
        if (list instanceof zziq) {
            zziq zziq = (zziq) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zziq.zzl(this.zzwt.zzgj());
                    if (!this.zzwt.zzgy()) {
                        i = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (i == this.tag);
                this.zzwv = i;
                return;
            } else if (i == 2) {
                zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    zziq.zzl(this.zzwt.zzgj());
                } while (this.zzwt.zzgz() < zzgz);
                zzaf(zzgz);
                return;
            } else {
                throw zzic.zziw();
            }
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Long.valueOf(this.zzwt.zzgj()));
                if (!this.zzwt.zzgy()) {
                    i2 = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (i2 == this.tag);
            this.zzwv = i2;
        } else if (i2 == 2) {
            zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
            do {
                list.add(Long.valueOf(this.zzwt.zzgj()));
            } while (this.zzwt.zzgz() < zzgz);
            zzaf(zzgz);
        } else {
            throw zzic.zziw();
        }
    }

    public final void zzk(List<Long> list) throws IOException {
        int zzgz;
        if (list instanceof zziq) {
            zziq zziq = (zziq) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zziq.zzl(this.zzwt.zzgk());
                    if (!this.zzwt.zzgy()) {
                        i = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (i == this.tag);
                this.zzwv = i;
                return;
            } else if (i == 2) {
                zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    zziq.zzl(this.zzwt.zzgk());
                } while (this.zzwt.zzgz() < zzgz);
                zzaf(zzgz);
                return;
            } else {
                throw zzic.zziw();
            }
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Long.valueOf(this.zzwt.zzgk()));
                if (!this.zzwt.zzgy()) {
                    i2 = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (i2 == this.tag);
            this.zzwv = i2;
        } else if (i2 == 2) {
            zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
            do {
                list.add(Long.valueOf(this.zzwt.zzgk()));
            } while (this.zzwt.zzgz() < zzgz);
            zzaf(zzgz);
        } else {
            throw zzic.zziw();
        }
    }

    public final void zzl(List<Integer> list) throws IOException {
        int zzgz;
        if (list instanceof zzhu) {
            zzhu zzhu = (zzhu) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzhu.zzaw(this.zzwt.zzgl());
                    if (!this.zzwt.zzgy()) {
                        i = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (i == this.tag);
                this.zzwv = i;
                return;
            } else if (i == 2) {
                zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    zzhu.zzaw(this.zzwt.zzgl());
                } while (this.zzwt.zzgz() < zzgz);
                zzaf(zzgz);
                return;
            } else {
                throw zzic.zziw();
            }
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Integer.valueOf(this.zzwt.zzgl()));
                if (!this.zzwt.zzgy()) {
                    i2 = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (i2 == this.tag);
            this.zzwv = i2;
        } else if (i2 == 2) {
            zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
            do {
                list.add(Integer.valueOf(this.zzwt.zzgl()));
            } while (this.zzwt.zzgz() < zzgz);
            zzaf(zzgz);
        } else {
            throw zzic.zziw();
        }
    }

    public final void zzm(List<Long> list) throws IOException {
        int zzgz;
        if (list instanceof zziq) {
            zziq zziq = (zziq) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zziq.zzl(this.zzwt.zzgm());
                    if (!this.zzwt.zzgy()) {
                        i = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (i == this.tag);
                this.zzwv = i;
                return;
            } else if (i == 2) {
                i = this.zzwt.zzgr();
                zzad(i);
                zzgz = this.zzwt.zzgz() + i;
                do {
                    zziq.zzl(this.zzwt.zzgm());
                } while (this.zzwt.zzgz() < zzgz);
                return;
            } else {
                throw zzic.zziw();
            }
        }
        int i2 = this.tag & 7;
        if (i2 == 1) {
            do {
                list.add(Long.valueOf(this.zzwt.zzgm()));
                if (!this.zzwt.zzgy()) {
                    i2 = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (i2 == this.tag);
            this.zzwv = i2;
        } else if (i2 == 2) {
            i2 = this.zzwt.zzgr();
            zzad(i2);
            zzgz = this.zzwt.zzgz() + i2;
            do {
                list.add(Long.valueOf(this.zzwt.zzgm()));
            } while (this.zzwt.zzgz() < zzgz);
        } else {
            throw zzic.zziw();
        }
    }

    public final void zzn(List<Integer> list) throws IOException {
        if (list instanceof zzhu) {
            zzhu zzhu = (zzhu) list;
            int i = this.tag & 7;
            if (i == 2) {
                i = this.zzwt.zzgr();
                zzae(i);
                int zzgz = this.zzwt.zzgz() + i;
                do {
                    zzhu.zzaw(this.zzwt.zzgn());
                } while (this.zzwt.zzgz() < zzgz);
                return;
            } else if (i == 5) {
                do {
                    zzhu.zzaw(this.zzwt.zzgn());
                    if (!this.zzwt.zzgy()) {
                        i = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (i == this.tag);
                this.zzwv = i;
                return;
            } else {
                throw zzic.zziw();
            }
        }
        int i2 = this.tag & 7;
        if (i2 == 2) {
            i2 = this.zzwt.zzgr();
            zzae(i2);
            int zzgz2 = this.zzwt.zzgz() + i2;
            do {
                list.add(Integer.valueOf(this.zzwt.zzgn()));
            } while (this.zzwt.zzgz() < zzgz2);
        } else if (i2 == 5) {
            do {
                list.add(Integer.valueOf(this.zzwt.zzgn()));
                if (!this.zzwt.zzgy()) {
                    i2 = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (i2 == this.tag);
            this.zzwv = i2;
        } else {
            throw zzic.zziw();
        }
    }

    public final void zzo(List<Boolean> list) throws IOException {
        int zzgz;
        if (list instanceof zzgd) {
            zzgd zzgd = (zzgd) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzgd.addBoolean(this.zzwt.zzgo());
                    if (!this.zzwt.zzgy()) {
                        i = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (i == this.tag);
                this.zzwv = i;
                return;
            } else if (i == 2) {
                zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    zzgd.addBoolean(this.zzwt.zzgo());
                } while (this.zzwt.zzgz() < zzgz);
                zzaf(zzgz);
                return;
            } else {
                throw zzic.zziw();
            }
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Boolean.valueOf(this.zzwt.zzgo()));
                if (!this.zzwt.zzgy()) {
                    i2 = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (i2 == this.tag);
            this.zzwv = i2;
        } else if (i2 == 2) {
            zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
            do {
                list.add(Boolean.valueOf(this.zzwt.zzgo()));
            } while (this.zzwt.zzgz() < zzgz);
            zzaf(zzgz);
        } else {
            throw zzic.zziw();
        }
    }

    public final void readStringList(List<String> list) throws IOException {
        zza((List) list, false);
    }

    public final void zzp(List<String> list) throws IOException {
        zza((List) list, true);
    }

    private final void zza(List<String> list, boolean z) throws IOException {
        if ((this.tag & 7) != 2) {
            throw zzic.zziw();
        } else if (!(list instanceof zzij) || z) {
            int zzgi;
            do {
                list.add(z ? zzgp() : readString());
                if (!this.zzwt.zzgy()) {
                    zzgi = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (zzgi == this.tag);
            this.zzwv = zzgi;
        } else {
            int zzgi2;
            zzij zzij = (zzij) list;
            do {
                zzij.zzc(zzgq());
                if (!this.zzwt.zzgy()) {
                    zzgi2 = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (zzgi2 == this.tag);
            this.zzwv = zzgi2;
        }
    }

    public final <T> void zza(List<T> list, zzjs<T> zzjs, zzhf zzhf) throws IOException {
        int i = this.tag;
        if ((i & 7) == 2) {
            int zzgi;
            do {
                list.add(zzc(zzjs, zzhf));
                if (!this.zzwt.zzgy() && this.zzwv == 0) {
                    zzgi = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (zzgi == i);
            this.zzwv = zzgi;
            return;
        }
        throw zzic.zziw();
    }

    public final <T> void zzb(List<T> list, zzjs<T> zzjs, zzhf zzhf) throws IOException {
        int i = this.tag;
        if ((i & 7) == 3) {
            int zzgi;
            do {
                list.add(zzd(zzjs, zzhf));
                if (!this.zzwt.zzgy() && this.zzwv == 0) {
                    zzgi = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (zzgi == i);
            this.zzwv = zzgi;
            return;
        }
        throw zzic.zziw();
    }

    public final void zzq(List<zzgf> list) throws IOException {
        if ((this.tag & 7) == 2) {
            int zzgi;
            do {
                list.add(zzgq());
                if (!this.zzwt.zzgy()) {
                    zzgi = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (zzgi == this.tag);
            this.zzwv = zzgi;
            return;
        }
        throw zzic.zziw();
    }

    public final void zzr(List<Integer> list) throws IOException {
        int zzgz;
        if (list instanceof zzhu) {
            zzhu zzhu = (zzhu) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzhu.zzaw(this.zzwt.zzgr());
                    if (!this.zzwt.zzgy()) {
                        i = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (i == this.tag);
                this.zzwv = i;
                return;
            } else if (i == 2) {
                zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    zzhu.zzaw(this.zzwt.zzgr());
                } while (this.zzwt.zzgz() < zzgz);
                zzaf(zzgz);
                return;
            } else {
                throw zzic.zziw();
            }
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Integer.valueOf(this.zzwt.zzgr()));
                if (!this.zzwt.zzgy()) {
                    i2 = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (i2 == this.tag);
            this.zzwv = i2;
        } else if (i2 == 2) {
            zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
            do {
                list.add(Integer.valueOf(this.zzwt.zzgr()));
            } while (this.zzwt.zzgz() < zzgz);
            zzaf(zzgz);
        } else {
            throw zzic.zziw();
        }
    }

    public final void zzs(List<Integer> list) throws IOException {
        int zzgz;
        if (list instanceof zzhu) {
            zzhu zzhu = (zzhu) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzhu.zzaw(this.zzwt.zzgs());
                    if (!this.zzwt.zzgy()) {
                        i = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (i == this.tag);
                this.zzwv = i;
                return;
            } else if (i == 2) {
                zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    zzhu.zzaw(this.zzwt.zzgs());
                } while (this.zzwt.zzgz() < zzgz);
                zzaf(zzgz);
                return;
            } else {
                throw zzic.zziw();
            }
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Integer.valueOf(this.zzwt.zzgs()));
                if (!this.zzwt.zzgy()) {
                    i2 = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (i2 == this.tag);
            this.zzwv = i2;
        } else if (i2 == 2) {
            zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
            do {
                list.add(Integer.valueOf(this.zzwt.zzgs()));
            } while (this.zzwt.zzgz() < zzgz);
            zzaf(zzgz);
        } else {
            throw zzic.zziw();
        }
    }

    public final void zzt(List<Integer> list) throws IOException {
        if (list instanceof zzhu) {
            zzhu zzhu = (zzhu) list;
            int i = this.tag & 7;
            if (i == 2) {
                i = this.zzwt.zzgr();
                zzae(i);
                int zzgz = this.zzwt.zzgz() + i;
                do {
                    zzhu.zzaw(this.zzwt.zzgt());
                } while (this.zzwt.zzgz() < zzgz);
                return;
            } else if (i == 5) {
                do {
                    zzhu.zzaw(this.zzwt.zzgt());
                    if (!this.zzwt.zzgy()) {
                        i = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (i == this.tag);
                this.zzwv = i;
                return;
            } else {
                throw zzic.zziw();
            }
        }
        int i2 = this.tag & 7;
        if (i2 == 2) {
            i2 = this.zzwt.zzgr();
            zzae(i2);
            int zzgz2 = this.zzwt.zzgz() + i2;
            do {
                list.add(Integer.valueOf(this.zzwt.zzgt()));
            } while (this.zzwt.zzgz() < zzgz2);
        } else if (i2 == 5) {
            do {
                list.add(Integer.valueOf(this.zzwt.zzgt()));
                if (!this.zzwt.zzgy()) {
                    i2 = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (i2 == this.tag);
            this.zzwv = i2;
        } else {
            throw zzic.zziw();
        }
    }

    public final void zzu(List<Long> list) throws IOException {
        int zzgz;
        if (list instanceof zziq) {
            zziq zziq = (zziq) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zziq.zzl(this.zzwt.zzgu());
                    if (!this.zzwt.zzgy()) {
                        i = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (i == this.tag);
                this.zzwv = i;
                return;
            } else if (i == 2) {
                i = this.zzwt.zzgr();
                zzad(i);
                zzgz = this.zzwt.zzgz() + i;
                do {
                    zziq.zzl(this.zzwt.zzgu());
                } while (this.zzwt.zzgz() < zzgz);
                return;
            } else {
                throw zzic.zziw();
            }
        }
        int i2 = this.tag & 7;
        if (i2 == 1) {
            do {
                list.add(Long.valueOf(this.zzwt.zzgu()));
                if (!this.zzwt.zzgy()) {
                    i2 = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (i2 == this.tag);
            this.zzwv = i2;
        } else if (i2 == 2) {
            i2 = this.zzwt.zzgr();
            zzad(i2);
            zzgz = this.zzwt.zzgz() + i2;
            do {
                list.add(Long.valueOf(this.zzwt.zzgu()));
            } while (this.zzwt.zzgz() < zzgz);
        } else {
            throw zzic.zziw();
        }
    }

    public final void zzv(List<Integer> list) throws IOException {
        int zzgz;
        if (list instanceof zzhu) {
            zzhu zzhu = (zzhu) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzhu.zzaw(this.zzwt.zzgv());
                    if (!this.zzwt.zzgy()) {
                        i = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (i == this.tag);
                this.zzwv = i;
                return;
            } else if (i == 2) {
                zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    zzhu.zzaw(this.zzwt.zzgv());
                } while (this.zzwt.zzgz() < zzgz);
                zzaf(zzgz);
                return;
            } else {
                throw zzic.zziw();
            }
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Integer.valueOf(this.zzwt.zzgv()));
                if (!this.zzwt.zzgy()) {
                    i2 = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (i2 == this.tag);
            this.zzwv = i2;
        } else if (i2 == 2) {
            zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
            do {
                list.add(Integer.valueOf(this.zzwt.zzgv()));
            } while (this.zzwt.zzgz() < zzgz);
            zzaf(zzgz);
        } else {
            throw zzic.zziw();
        }
    }

    public final void zzw(List<Long> list) throws IOException {
        int zzgz;
        if (list instanceof zziq) {
            zziq zziq = (zziq) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zziq.zzl(this.zzwt.zzgw());
                    if (!this.zzwt.zzgy()) {
                        i = this.zzwt.zzgi();
                    } else {
                        return;
                    }
                } while (i == this.tag);
                this.zzwv = i;
                return;
            } else if (i == 2) {
                zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
                do {
                    zziq.zzl(this.zzwt.zzgw());
                } while (this.zzwt.zzgz() < zzgz);
                zzaf(zzgz);
                return;
            } else {
                throw zzic.zziw();
            }
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Long.valueOf(this.zzwt.zzgw()));
                if (!this.zzwt.zzgy()) {
                    i2 = this.zzwt.zzgi();
                } else {
                    return;
                }
            } while (i2 == this.tag);
            this.zzwv = i2;
        } else if (i2 == 2) {
            zzgz = this.zzwt.zzgz() + this.zzwt.zzgr();
            do {
                list.add(Long.valueOf(this.zzwt.zzgw()));
            } while (this.zzwt.zzgz() < zzgz);
            zzaf(zzgz);
        } else {
            throw zzic.zziw();
        }
    }

    private static void zzad(int i) throws IOException {
        if ((i & 7) != 0) {
            throw zzic.zziy();
        }
    }

    public final <K, V> void zza(Map<K, V> map, zzit<K, V> zzit, zzhf zzhf) throws IOException {
        zzac(2);
        int zzu = this.zzwt.zzu(this.zzwt.zzgr());
        Object obj = zzit.zzacl;
        Object obj2 = zzit.zzacn;
        while (true) {
            String str;
            try {
                int zzhg = zzhg();
                if (zzhg == Integer.MAX_VALUE || this.zzwt.zzgy()) {
                    map.put(obj, obj2);
                } else {
                    str = "Unable to parse map entry.";
                    if (zzhg == 1) {
                        obj = zza(zzit.zzack, null, null);
                    } else if (zzhg == 2) {
                        obj2 = zza(zzit.zzacm, zzit.zzacn.getClass(), zzhf);
                    } else if (!zzhh()) {
                        throw new zzic(str);
                    }
                }
            } catch (zzib unused) {
                if (!zzhh()) {
                    throw new zzic(str);
                }
            } catch (Throwable th) {
                this.zzwt.zzv(zzu);
            }
        }
        map.put(obj, obj2);
        this.zzwt.zzv(zzu);
    }

    private final Object zza(zzlb zzlb, Class<?> cls, zzhf zzhf) throws IOException {
        switch (zzgx.zzws[zzlb.ordinal()]) {
            case 1:
                return Boolean.valueOf(zzgo());
            case 2:
                return zzgq();
            case 3:
                return Double.valueOf(readDouble());
            case 4:
                return Integer.valueOf(zzgs());
            case 5:
                return Integer.valueOf(zzgn());
            case 6:
                return Long.valueOf(zzgm());
            case 7:
                return Float.valueOf(readFloat());
            case 8:
                return Integer.valueOf(zzgl());
            case 9:
                return Long.valueOf(zzgk());
            case 10:
                zzac(2);
                return zzc(zzjo.zzjv().zzf(cls), zzhf);
            case 11:
                return Integer.valueOf(zzgt());
            case 12:
                return Long.valueOf(zzgu());
            case 13:
                return Integer.valueOf(zzgv());
            case 14:
                return Long.valueOf(zzgw());
            case 15:
                return zzgp();
            case 16:
                return Integer.valueOf(zzgr());
            case 17:
                return Long.valueOf(zzgj());
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private static void zzae(int i) throws IOException {
        if ((i & 3) != 0) {
            throw zzic.zziy();
        }
    }

    private final void zzaf(int i) throws IOException {
        if (this.zzwt.zzgz() != i) {
            throw zzic.zzir();
        }
    }
}
