package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzhi<FieldDescriptorType extends zzhk<FieldDescriptorType>> {
    private static final zzhi zzxk = new zzhi(true);
    final zzjt<FieldDescriptorType, Object> zzxh = zzjt.zzbe(16);
    private boolean zzxi;
    private boolean zzxj = false;

    private zzhi() {
    }

    private zzhi(boolean z) {
        zzfy();
    }

    public static <T extends zzhk<T>> zzhi<T> zzhs() {
        return zzxk;
    }

    public final void zzfy() {
        if (!this.zzxi) {
            this.zzxh.zzfy();
            this.zzxi = true;
        }
    }

    public final boolean isImmutable() {
        return this.zzxi;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzhi)) {
            return false;
        }
        return this.zzxh.equals(((zzhi) obj).zzxh);
    }

    public final int hashCode() {
        return this.zzxh.hashCode();
    }

    public final Iterator<Entry<FieldDescriptorType, Object>> iterator() {
        if (this.zzxj) {
            return new zzii(this.zzxh.entrySet().iterator());
        }
        return this.zzxh.entrySet().iterator();
    }

    final Iterator<Entry<FieldDescriptorType, Object>> descendingIterator() {
        if (this.zzxj) {
            return new zzii(this.zzxh.zzka().iterator());
        }
        return this.zzxh.zzka().iterator();
    }

    private final Object zza(FieldDescriptorType fieldDescriptorType) {
        Object obj = this.zzxh.get(fieldDescriptorType);
        return obj instanceof zzid ? zzid.zzja() : obj;
    }

    private final void zza(FieldDescriptorType fieldDescriptorType, Object obj) {
        if (!fieldDescriptorType.zzhz()) {
            zza(fieldDescriptorType.zzhx(), obj);
        } else if (obj instanceof List) {
            List arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            ArrayList arrayList2 = (ArrayList) arrayList;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList2.get(i);
                i++;
                zza(fieldDescriptorType.zzhx(), obj2);
            }
            obj = arrayList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (obj instanceof zzid) {
            this.zzxj = true;
        }
        this.zzxh.put((Comparable) fieldDescriptorType, obj);
    }

    /* JADX WARNING: Missing block: B:5:0x001b, code:
            if ((r3 instanceof com.google.android.gms.internal.firebase_auth.zzid) == false) goto L_0x0043;
     */
    /* JADX WARNING: Missing block: B:9:0x0024, code:
            if ((r3 instanceof com.google.android.gms.internal.firebase_auth.zzhw) == false) goto L_0x0043;
     */
    /* JADX WARNING: Missing block: B:10:0x0026, code:
            r1 = true;
     */
    /* JADX WARNING: Missing block: B:14:0x002e, code:
            if ((r3 instanceof byte[]) == false) goto L_0x0043;
     */
    private static void zza(com.google.android.gms.internal.firebase_auth.zzlb r2, java.lang.Object r3) {
        /*
        com.google.android.gms.internal.firebase_auth.zzht.checkNotNull(r3);
        r0 = com.google.android.gms.internal.firebase_auth.zzhl.zzxn;
        r2 = r2.zzkx();
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
        r2 = r3 instanceof com.google.android.gms.internal.firebase_auth.zzjc;
        if (r2 != 0) goto L_0x0026;
    L_0x0019:
        r2 = r3 instanceof com.google.android.gms.internal.firebase_auth.zzid;
        if (r2 == 0) goto L_0x0043;
    L_0x001d:
        goto L_0x0026;
    L_0x001e:
        r2 = r3 instanceof java.lang.Integer;
        if (r2 != 0) goto L_0x0026;
    L_0x0022:
        r2 = r3 instanceof com.google.android.gms.internal.firebase_auth.zzhw;
        if (r2 == 0) goto L_0x0043;
    L_0x0026:
        r1 = 1;
        goto L_0x0043;
    L_0x0028:
        r2 = r3 instanceof com.google.android.gms.internal.firebase_auth.zzgf;
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzhi.zza(com.google.android.gms.internal.firebase_auth.zzlb, java.lang.Object):void");
    }

    public final boolean isInitialized() {
        for (int i = 0; i < this.zzxh.zzjy(); i++) {
            if (!zzb(this.zzxh.zzbf(i))) {
                return false;
            }
        }
        for (Entry zzb : this.zzxh.zzjz()) {
            if (!zzb(zzb)) {
                return false;
            }
        }
        return true;
    }

    private static boolean zzb(Entry<FieldDescriptorType, Object> entry) {
        zzhk zzhk = (zzhk) entry.getKey();
        if (zzhk.zzhy() == zzle.MESSAGE) {
            if (zzhk.zzhz()) {
                for (zzjc isInitialized : (List) entry.getValue()) {
                    if (!isInitialized.isInitialized()) {
                        return false;
                    }
                }
            }
            Object value = entry.getValue();
            if (value instanceof zzjc) {
                if (!((zzjc) value).isInitialized()) {
                    return false;
                }
            } else if (value instanceof zzid) {
                return true;
            } else {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
        }
        return true;
    }

    public final void zza(zzhi<FieldDescriptorType> zzhi) {
        for (int i = 0; i < zzhi.zzxh.zzjy(); i++) {
            zzc(zzhi.zzxh.zzbf(i));
        }
        for (Entry zzc : zzhi.zzxh.zzjz()) {
            zzc(zzc);
        }
    }

    private static Object zzg(Object obj) {
        if (obj instanceof zzji) {
            return ((zzji) obj).zzfv();
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
        Comparable comparable = (zzhk) entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzid) {
            value = zzid.zzja();
        }
        Object zza;
        if (comparable.zzhz()) {
            zza = zza((zzhk) comparable);
            if (zza == null) {
                zza = new ArrayList();
            }
            for (Object zzg : (List) value) {
                ((List) zza).add(zzg(zzg));
            }
            this.zzxh.put(comparable, zza);
        } else if (comparable.zzhy() == zzle.MESSAGE) {
            zza = zza((zzhk) comparable);
            if (zza == null) {
                this.zzxh.put(comparable, zzg(value));
                return;
            }
            if (zza instanceof zzji) {
                value = comparable.zza((zzji) zza, (zzji) value);
            } else {
                value = comparable.zza(((zzjc) zza).zzin(), (zzjc) value).zzih();
            }
            this.zzxh.put(comparable, value);
        } else {
            this.zzxh.put(comparable, zzg(value));
        }
    }

    static void zza(zzha zzha, zzlb zzlb, int i, Object obj) throws IOException {
        if (zzlb == zzlb.GROUP) {
            zzjc zzjc = (zzjc) obj;
            zzht.zzg(zzjc);
            zzha.zze(i, 3);
            zzjc.zzb(zzha);
            zzha.zze(i, 4);
            return;
        }
        zzha.zze(i, zzlb.zzky());
        switch (zzhl.zzws[zzlb.ordinal()]) {
            case 1:
                zzha.zza(((Double) obj).doubleValue());
                break;
            case 2:
                zzha.zza(((Float) obj).floatValue());
                return;
            case 3:
                zzha.zzb(((Long) obj).longValue());
                return;
            case 4:
                zzha.zzb(((Long) obj).longValue());
                return;
            case 5:
                zzha.zzag(((Integer) obj).intValue());
                return;
            case 6:
                zzha.zzd(((Long) obj).longValue());
                return;
            case 7:
                zzha.zzaj(((Integer) obj).intValue());
                return;
            case 8:
                zzha.zzt(((Boolean) obj).booleanValue());
                return;
            case 9:
                ((zzjc) obj).zzb(zzha);
                return;
            case 10:
                zzha.zzc((zzjc) obj);
                return;
            case 11:
                if (obj instanceof zzgf) {
                    zzha.zza((zzgf) obj);
                    return;
                } else {
                    zzha.zzdi((String) obj);
                    return;
                }
            case 12:
                if (obj instanceof zzgf) {
                    zzha.zza((zzgf) obj);
                    return;
                }
                byte[] bArr = (byte[]) obj;
                zzha.zzd(bArr, 0, bArr.length);
                return;
            case 13:
                zzha.zzah(((Integer) obj).intValue());
                return;
            case 14:
                zzha.zzaj(((Integer) obj).intValue());
                return;
            case 15:
                zzha.zzd(((Long) obj).longValue());
                return;
            case 16:
                zzha.zzai(((Integer) obj).intValue());
                return;
            case 17:
                zzha.zzc(((Long) obj).longValue());
                return;
            case 18:
                if (!(obj instanceof zzhw)) {
                    zzha.zzag(((Integer) obj).intValue());
                    break;
                } else {
                    zzha.zzag(((zzhw) obj).zzbq());
                    return;
                }
        }
    }

    public final int zzht() {
        int i = 0;
        for (int i2 = 0; i2 < this.zzxh.zzjy(); i2++) {
            i += zzd(this.zzxh.zzbf(i2));
        }
        for (Entry zzd : this.zzxh.zzjz()) {
            i += zzd(zzd);
        }
        return i;
    }

    private static int zzd(Entry<FieldDescriptorType, Object> entry) {
        zzhk zzhk = (zzhk) entry.getKey();
        Object value = entry.getValue();
        if (zzhk.zzhy() != zzle.MESSAGE || zzhk.zzhz() || zzhk.zzia()) {
            return zzb(zzhk, value);
        }
        if (value instanceof zzid) {
            return zzha.zzb(((zzhk) entry.getKey()).zzbq(), (zzid) value);
        }
        return zzha.zzb(((zzhk) entry.getKey()).zzbq(), (zzjc) value);
    }

    static int zza(zzlb zzlb, int i, Object obj) {
        i = zzha.zzak(i);
        if (zzlb == zzlb.GROUP) {
            zzht.zzg((zzjc) obj);
            i <<= 1;
        }
        return i + zzb(zzlb, obj);
    }

    private static int zzb(zzlb zzlb, Object obj) {
        switch (zzhl.zzws[zzlb.ordinal()]) {
            case 1:
                return zzha.zzb(((Double) obj).doubleValue());
            case 2:
                return zzha.zzb(((Float) obj).floatValue());
            case 3:
                return zzha.zze(((Long) obj).longValue());
            case 4:
                return zzha.zzf(((Long) obj).longValue());
            case 5:
                return zzha.zzal(((Integer) obj).intValue());
            case 6:
                return zzha.zzh(((Long) obj).longValue());
            case 7:
                return zzha.zzao(((Integer) obj).intValue());
            case 8:
                return zzha.zzu(((Boolean) obj).booleanValue());
            case 9:
                return zzha.zze((zzjc) obj);
            case 10:
                if (obj instanceof zzid) {
                    return zzha.zza((zzid) obj);
                }
                return zzha.zzd((zzjc) obj);
            case 11:
                if (obj instanceof zzgf) {
                    return zzha.zzb((zzgf) obj);
                }
                return zzha.zzdj((String) obj);
            case 12:
                if (obj instanceof zzgf) {
                    return zzha.zzb((zzgf) obj);
                }
                return zzha.zzd((byte[]) obj);
            case 13:
                return zzha.zzam(((Integer) obj).intValue());
            case 14:
                return zzha.zzap(((Integer) obj).intValue());
            case 15:
                return zzha.zzi(((Long) obj).longValue());
            case 16:
                return zzha.zzan(((Integer) obj).intValue());
            case 17:
                return zzha.zzg(((Long) obj).longValue());
            case 18:
                if (obj instanceof zzhw) {
                    return zzha.zzaq(((zzhw) obj).zzbq());
                }
                return zzha.zzaq(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int zzb(zzhk<?> zzhk, Object obj) {
        zzlb zzhx = zzhk.zzhx();
        int zzbq = zzhk.zzbq();
        if (!zzhk.zzhz()) {
            return zza(zzhx, zzbq, obj);
        }
        int i = 0;
        if (zzhk.zzia()) {
            for (Object obj2 : (List) obj2) {
                i += zzb(zzhx, obj2);
            }
            return (zzha.zzak(zzbq) + i) + zzha.zzas(i);
        }
        for (Object obj22 : (List) obj22) {
            i += zza(zzhx, zzbq, obj22);
        }
        return i;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        Entry zzbf;
        zzhi zzhi = new zzhi();
        for (int i = 0; i < this.zzxh.zzjy(); i++) {
            zzbf = this.zzxh.zzbf(i);
            zzhi.zza((zzhk) zzbf.getKey(), zzbf.getValue());
        }
        for (Entry zzbf2 : this.zzxh.zzjz()) {
            zzhi.zza((zzhk) zzbf2.getKey(), zzbf2.getValue());
        }
        zzhi.zzxj = this.zzxj;
        return zzhi;
    }
}
