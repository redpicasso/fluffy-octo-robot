package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zztw<FieldDescriptorType extends zzty<FieldDescriptorType>> {
    private static final zztw zzbln = new zztw(true);
    final zzwh<FieldDescriptorType, Object> zzblk = zzwh.zzdo(16);
    private boolean zzbll;
    private boolean zzblm = false;

    private zztw() {
    }

    private zztw(boolean z) {
        zzpt();
    }

    public static <T extends zzty<T>> zztw<T> zzqp() {
        return zzbln;
    }

    public final void zzpt() {
        if (!this.zzbll) {
            this.zzblk.zzpt();
            this.zzbll = true;
        }
    }

    public final boolean isImmutable() {
        return this.zzbll;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zztw)) {
            return false;
        }
        return this.zzblk.equals(((zztw) obj).zzblk);
    }

    public final int hashCode() {
        return this.zzblk.hashCode();
    }

    public final Iterator<Entry<FieldDescriptorType, Object>> iterator() {
        if (this.zzblm) {
            return new zzuu(this.zzblk.entrySet().iterator());
        }
        return this.zzblk.entrySet().iterator();
    }

    final Iterator<Entry<FieldDescriptorType, Object>> descendingIterator() {
        if (this.zzblm) {
            return new zzuu(this.zzblk.zzsz().iterator());
        }
        return this.zzblk.zzsz().iterator();
    }

    private final Object zza(FieldDescriptorType fieldDescriptorType) {
        Object obj = this.zzblk.get(fieldDescriptorType);
        return obj instanceof zzur ? zzur.zzrt() : obj;
    }

    private final void zza(FieldDescriptorType fieldDescriptorType, Object obj) {
        if (!fieldDescriptorType.zzqt()) {
            zza(fieldDescriptorType.zzqr(), obj);
        } else if (obj instanceof List) {
            List arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            ArrayList arrayList2 = (ArrayList) arrayList;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList2.get(i);
                i++;
                zza(fieldDescriptorType.zzqr(), obj2);
            }
            obj = arrayList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (obj instanceof zzur) {
            this.zzblm = true;
        }
        this.zzblk.put((Comparable) fieldDescriptorType, obj);
    }

    /* JADX WARNING: Missing block: B:5:0x001b, code:
            if ((r3 instanceof com.google.android.gms.internal.firebase_ml.zzur) == false) goto L_0x0043;
     */
    /* JADX WARNING: Missing block: B:9:0x0024, code:
            if ((r3 instanceof com.google.android.gms.internal.firebase_ml.zzuh) == false) goto L_0x0043;
     */
    /* JADX WARNING: Missing block: B:10:0x0026, code:
            r1 = true;
     */
    /* JADX WARNING: Missing block: B:14:0x002e, code:
            if ((r3 instanceof byte[]) == false) goto L_0x0043;
     */
    private static void zza(com.google.android.gms.internal.firebase_ml.zzxl r2, java.lang.Object r3) {
        /*
        com.google.android.gms.internal.firebase_ml.zzug.checkNotNull(r3);
        r0 = com.google.android.gms.internal.firebase_ml.zztx.zzblo;
        r2 = r2.zztp();
        r2 = r2.ordinal();
        r2 = r0[r2];
        r0 = 1;
        r1 = 0;
        switch(r2) {
            case 1: goto L_0x0040;
            case 2: goto L_0x003d;
            case 3: goto L_0x003a;
            case 4: goto L_0x0037;
            case 5: goto L_0x0034;
            case 6: goto L_0x0031;
            case 7: goto L_0x0028;
            case 8: goto L_0x001e;
            case 9: goto L_0x0015;
            default: goto L_0x0014;
        };
    L_0x0014:
        goto L_0x0043;
    L_0x0015:
        r2 = r3 instanceof com.google.android.gms.internal.firebase_ml.zzvo;
        if (r2 != 0) goto L_0x0026;
    L_0x0019:
        r2 = r3 instanceof com.google.android.gms.internal.firebase_ml.zzur;
        if (r2 == 0) goto L_0x0043;
    L_0x001d:
        goto L_0x0026;
    L_0x001e:
        r2 = r3 instanceof java.lang.Integer;
        if (r2 != 0) goto L_0x0026;
    L_0x0022:
        r2 = r3 instanceof com.google.android.gms.internal.firebase_ml.zzuh;
        if (r2 == 0) goto L_0x0043;
    L_0x0026:
        r1 = 1;
        goto L_0x0043;
    L_0x0028:
        r2 = r3 instanceof com.google.android.gms.internal.firebase_ml.zzsw;
        if (r2 != 0) goto L_0x0026;
    L_0x002c:
        r2 = r3 instanceof byte[];
        if (r2 == 0) goto L_0x0043;
    L_0x0030:
        goto L_0x0026;
    L_0x0031:
        r0 = r3 instanceof java.lang.String;
        goto L_0x0042;
    L_0x0034:
        r0 = r3 instanceof java.lang.Boolean;
        goto L_0x0042;
    L_0x0037:
        r0 = r3 instanceof java.lang.Double;
        goto L_0x0042;
    L_0x003a:
        r0 = r3 instanceof java.lang.Float;
        goto L_0x0042;
    L_0x003d:
        r0 = r3 instanceof java.lang.Long;
        goto L_0x0042;
    L_0x0040:
        r0 = r3 instanceof java.lang.Integer;
    L_0x0042:
        r1 = r0;
    L_0x0043:
        if (r1 == 0) goto L_0x0046;
    L_0x0045:
        return;
    L_0x0046:
        r2 = new java.lang.IllegalArgumentException;
        r3 = "Wrong object type used with protocol message reflection.";
        r2.<init>(r3);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zztw.zza(com.google.android.gms.internal.firebase_ml.zzxl, java.lang.Object):void");
    }

    public final boolean isInitialized() {
        for (int i = 0; i < this.zzblk.zzsx(); i++) {
            if (!zzb(this.zzblk.zzdp(i))) {
                return false;
            }
        }
        for (Entry zzb : this.zzblk.zzsy()) {
            if (!zzb(zzb)) {
                return false;
            }
        }
        return true;
    }

    private static boolean zzb(Entry<FieldDescriptorType, Object> entry) {
        zzty zzty = (zzty) entry.getKey();
        if (zzty.zzqs() == zzxq.MESSAGE) {
            if (zzty.zzqt()) {
                for (zzvo isInitialized : (List) entry.getValue()) {
                    if (!isInitialized.isInitialized()) {
                        return false;
                    }
                }
            }
            Object value = entry.getValue();
            if (value instanceof zzvo) {
                if (!((zzvo) value).isInitialized()) {
                    return false;
                }
            } else if (value instanceof zzur) {
                return true;
            } else {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
        }
        return true;
    }

    public final void zza(zztw<FieldDescriptorType> zztw) {
        for (int i = 0; i < zztw.zzblk.zzsx(); i++) {
            zzc(zztw.zzblk.zzdp(i));
        }
        for (Entry zzc : zztw.zzblk.zzsy()) {
            zzc(zzc);
        }
    }

    private static Object zzr(Object obj) {
        if (obj instanceof zzvv) {
            return ((zzvv) obj).zzsk();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        Object obj2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, obj2, 0, bArr.length);
        return obj2;
    }

    private final void zzc(Entry<FieldDescriptorType, Object> entry) {
        Comparable comparable = (zzty) entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzur) {
            value = zzur.zzrt();
        }
        Object zza;
        if (comparable.zzqt()) {
            zza = zza((zzty) comparable);
            if (zza == null) {
                zza = new ArrayList();
            }
            for (Object zzr : (List) value) {
                ((List) zza).add(zzr(zzr));
            }
            this.zzblk.put(comparable, zza);
        } else if (comparable.zzqs() == zzxq.MESSAGE) {
            zza = zza((zzty) comparable);
            if (zza == null) {
                this.zzblk.put(comparable, zzr(value));
                return;
            }
            if (zza instanceof zzvv) {
                value = comparable.zza((zzvv) zza, (zzvv) value);
            } else {
                value = comparable.zza(((zzvo) zza).zzrc(), (zzvo) value).zzrj();
            }
            this.zzblk.put(comparable, value);
        } else {
            this.zzblk.put(comparable, zzr(value));
        }
    }

    static void zza(zztl zztl, zzxl zzxl, int i, Object obj) throws IOException {
        if (zzxl == zzxl.GROUP) {
            zzvo zzvo = (zzvo) obj;
            zzug.zzf(zzvo);
            zztl.zzg(i, 3);
            zzvo.zzb(zztl);
            zztl.zzg(i, 4);
            return;
        }
        zztl.zzg(i, zzxl.zztq());
        switch (zztx.zzblp[zzxl.ordinal()]) {
            case 1:
                zztl.zzc(((Double) obj).doubleValue());
                break;
            case 2:
                zztl.zzt(((Float) obj).floatValue());
                return;
            case 3:
                zztl.zzq(((Long) obj).longValue());
                return;
            case 4:
                zztl.zzq(((Long) obj).longValue());
                return;
            case 5:
                zztl.zzcq(((Integer) obj).intValue());
                return;
            case 6:
                zztl.zzs(((Long) obj).longValue());
                return;
            case 7:
                zztl.zzct(((Integer) obj).intValue());
                return;
            case 8:
                zztl.zzah(((Boolean) obj).booleanValue());
                return;
            case 9:
                ((zzvo) obj).zzb(zztl);
                return;
            case 10:
                zztl.zzb((zzvo) obj);
                return;
            case 11:
                if (obj instanceof zzsw) {
                    zztl.zza((zzsw) obj);
                    return;
                } else {
                    zztl.zzco((String) obj);
                    return;
                }
            case 12:
                if (obj instanceof zzsw) {
                    zztl.zza((zzsw) obj);
                    return;
                }
                byte[] bArr = (byte[]) obj;
                zztl.zze(bArr, 0, bArr.length);
                return;
            case 13:
                zztl.zzcr(((Integer) obj).intValue());
                return;
            case 14:
                zztl.zzct(((Integer) obj).intValue());
                return;
            case 15:
                zztl.zzs(((Long) obj).longValue());
                return;
            case 16:
                zztl.zzcs(((Integer) obj).intValue());
                return;
            case 17:
                zztl.zzr(((Long) obj).longValue());
                return;
            case 18:
                if (!(obj instanceof zzuh)) {
                    zztl.zzcq(((Integer) obj).intValue());
                    break;
                } else {
                    zztl.zzcq(((zzuh) obj).zzo());
                    return;
                }
        }
    }

    public final int zzqq() {
        int i = 0;
        for (int i2 = 0; i2 < this.zzblk.zzsx(); i2++) {
            i += zzd(this.zzblk.zzdp(i2));
        }
        for (Entry zzd : this.zzblk.zzsy()) {
            i += zzd(zzd);
        }
        return i;
    }

    private static int zzd(Entry<FieldDescriptorType, Object> entry) {
        zzty zzty = (zzty) entry.getKey();
        Object value = entry.getValue();
        if (zzty.zzqs() != zzxq.MESSAGE || zzty.zzqt() || zzty.zzqu()) {
            return zzb(zzty, value);
        }
        if (value instanceof zzur) {
            return zztl.zzb(((zzty) entry.getKey()).zzo(), (zzur) value);
        }
        return zztl.zzb(((zzty) entry.getKey()).zzo(), (zzvo) value);
    }

    static int zza(zzxl zzxl, int i, Object obj) {
        i = zztl.zzcu(i);
        if (zzxl == zzxl.GROUP) {
            zzug.zzf((zzvo) obj);
            i <<= 1;
        }
        return i + zzb(zzxl, obj);
    }

    private static int zzb(zzxl zzxl, Object obj) {
        switch (zztx.zzblp[zzxl.ordinal()]) {
            case 1:
                return zztl.zzd(((Double) obj).doubleValue());
            case 2:
                return zztl.zzu(((Float) obj).floatValue());
            case 3:
                return zztl.zzt(((Long) obj).longValue());
            case 4:
                return zztl.zzu(((Long) obj).longValue());
            case 5:
                return zztl.zzcv(((Integer) obj).intValue());
            case 6:
                return zztl.zzw(((Long) obj).longValue());
            case 7:
                return zztl.zzcy(((Integer) obj).intValue());
            case 8:
                return zztl.zzai(((Boolean) obj).booleanValue());
            case 9:
                return zztl.zzd((zzvo) obj);
            case 10:
                if (obj instanceof zzur) {
                    return zztl.zza((zzur) obj);
                }
                return zztl.zzc((zzvo) obj);
            case 11:
                if (obj instanceof zzsw) {
                    return zztl.zzb((zzsw) obj);
                }
                return zztl.zzcp((String) obj);
            case 12:
                if (obj instanceof zzsw) {
                    return zztl.zzb((zzsw) obj);
                }
                return zztl.zzh((byte[]) obj);
            case 13:
                return zztl.zzcw(((Integer) obj).intValue());
            case 14:
                return zztl.zzcz(((Integer) obj).intValue());
            case 15:
                return zztl.zzx(((Long) obj).longValue());
            case 16:
                return zztl.zzcx(((Integer) obj).intValue());
            case 17:
                return zztl.zzv(((Long) obj).longValue());
            case 18:
                if (obj instanceof zzuh) {
                    return zztl.zzda(((zzuh) obj).zzo());
                }
                return zztl.zzda(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int zzb(zzty<?> zzty, Object obj) {
        zzxl zzqr = zzty.zzqr();
        int zzo = zzty.zzo();
        if (!zzty.zzqt()) {
            return zza(zzqr, zzo, obj);
        }
        int i = 0;
        if (zzty.zzqu()) {
            for (Object obj2 : (List) obj2) {
                i += zzb(zzqr, obj2);
            }
            return (zztl.zzcu(zzo) + i) + zztl.zzdd(i);
        }
        for (Object obj22 : (List) obj22) {
            i += zza(zzqr, zzo, obj22);
        }
        return i;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        Entry zzdp;
        zztw zztw = new zztw();
        for (int i = 0; i < this.zzblk.zzsx(); i++) {
            zzdp = this.zzblk.zzdp(i);
            zztw.zza((zzty) zzdp.getKey(), zzdp.getValue());
        }
        for (Entry zzdp2 : this.zzblk.zzsy()) {
            zztw.zza((zzty) zzdp2.getKey(), zzdp2.getValue());
        }
        zztw.zzblm = this.zzblm;
        return zztw;
    }
}
