package com.google.android.gms.internal.firebase_ml;

import com.google.android.gms.internal.firebase_ml.zzue.zzd;
import com.google.android.gms.internal.firebase_ml.zzue.zze;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

final class zzvu<T> implements zzwe<T> {
    private final zzvo zzbrb;
    private final boolean zzbrc;
    private final zzww<?, ?> zzbrl;
    private final zztt<?> zzbrm;

    private zzvu(zzww<?, ?> zzww, zztt<?> zztt, zzvo zzvo) {
        this.zzbrl = zzww;
        this.zzbrc = zztt.zze(zzvo);
        this.zzbrm = zztt;
        this.zzbrb = zzvo;
    }

    static <T> zzvu<T> zza(zzww<?, ?> zzww, zztt<?> zztt, zzvo zzvo) {
        return new zzvu(zzww, zztt, zzvo);
    }

    public final T newInstance() {
        return this.zzbrb.zzrd().zzri();
    }

    public final boolean equals(T t, T t2) {
        if (this.zzbrl.zzae(t).equals(this.zzbrl.zzae(t2))) {
            return this.zzbrc ? this.zzbrm.zzo(t).equals(this.zzbrm.zzo(t2)) : true;
        } else {
            return false;
        }
    }

    public final int hashCode(T t) {
        int hashCode = this.zzbrl.zzae(t).hashCode();
        return this.zzbrc ? (hashCode * 53) + this.zzbrm.zzo(t).hashCode() : hashCode;
    }

    public final void zzg(T t, T t2) {
        zzwg.zza(this.zzbrl, (Object) t, (Object) t2);
        if (this.zzbrc) {
            zzwg.zza(this.zzbrm, (Object) t, (Object) t2);
        }
    }

    public final void zza(T t, zzxr zzxr) throws IOException {
        Iterator it = this.zzbrm.zzo(t).iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            zzty zzty = (zzty) entry.getKey();
            if (zzty.zzqs() != zzxq.MESSAGE || zzty.zzqt() || zzty.zzqu()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            } else if (entry instanceof zzut) {
                zzxr.zza(zzty.zzo(), ((zzut) entry).zzru().zzpp());
            } else {
                zzxr.zza(zzty.zzo(), entry.getValue());
            }
        }
        zzww zzww = this.zzbrl;
        zzww.zzc(zzww.zzae(t), zzxr);
    }

    public final void zza(T t, byte[] bArr, int i, int i2, zzst zzst) throws IOException {
        zzue zzue = (zzue) t;
        zzwx zzwx = zzue.zzboh;
        if (zzwx == zzwx.zztg()) {
            zzwx = zzwx.zzth();
            zzue.zzboh = zzwx;
        }
        ((zzd) t).zzrk();
        zze zze = null;
        while (i < i2) {
            int zza = zzss.zza(bArr, i, zzst);
            int i3 = zzst.zzbkg;
            if (i3 == 11) {
                i = 0;
                Object obj = null;
                while (zza < i2) {
                    zza = zzss.zza(bArr, zza, zzst);
                    int i4 = zzst.zzbkg;
                    int i5 = i4 >>> 3;
                    int i6 = i4 & 7;
                    if (i5 != 2) {
                        if (i5 == 3) {
                            if (zze != null) {
                                zzwb.zzso();
                                throw new NoSuchMethodError();
                            } else if (i6 == 2) {
                                zza = zzss.zze(bArr, zza, zzst);
                                obj = (zzsw) zzst.zzbki;
                            }
                        }
                    } else if (i6 == 0) {
                        zza = zzss.zza(bArr, zza, zzst);
                        i = zzst.zzbkg;
                        zze = (zze) this.zzbrm.zza(zzst.zzix, this.zzbrb, i);
                    }
                    if (i4 == 12) {
                        break;
                    }
                    zza = zzss.zza(i4, bArr, zza, i2, zzst);
                }
                if (obj != null) {
                    zzwx.zzb((i << 3) | 2, obj);
                }
                i = zza;
            } else if ((i3 & 7) == 2) {
                zze = (zze) this.zzbrm.zza(zzst.zzix, this.zzbrb, i3 >>> 3);
                if (zze == null) {
                    i = zzss.zza(i3, bArr, zza, i2, zzwx, zzst);
                } else {
                    zzwb.zzso();
                    throw new NoSuchMethodError();
                }
            } else {
                i = zzss.zza(i3, bArr, zza, i2, zzst);
            }
        }
        if (i != i2) {
            throw zzuo.zzrq();
        }
    }

    public final void zzq(T t) {
        this.zzbrl.zzq(t);
        this.zzbrm.zzq(t);
    }

    public final boolean zzac(T t) {
        return this.zzbrm.zzo(t).isInitialized();
    }

    public final int zzaa(T t) {
        zzww zzww = this.zzbrl;
        int zzaf = zzww.zzaf(zzww.zzae(t)) + 0;
        return this.zzbrc ? zzaf + this.zzbrm.zzo(t).zzqq() : zzaf;
    }
}
