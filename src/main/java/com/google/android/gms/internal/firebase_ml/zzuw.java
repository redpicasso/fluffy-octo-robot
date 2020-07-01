package com.google.android.gms.internal.firebase_ml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

public final class zzuw extends zzsq<String> implements zzux, RandomAccess {
    private static final zzuw zzbqb;
    private static final zzux zzbqc = zzbqb;
    private final List<Object> zzbqd;

    public zzuw() {
        this(10);
    }

    public zzuw(int i) {
        this(new ArrayList(i));
    }

    private zzuw(ArrayList<Object> arrayList) {
        this.zzbqd = arrayList;
    }

    public final int size() {
        return this.zzbqd.size();
    }

    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    public final boolean addAll(int i, Collection<? extends String> collection) {
        Collection collection2;
        zzpu();
        if (collection2 instanceof zzux) {
            collection2 = ((zzux) collection2).zzrv();
        }
        boolean addAll = this.zzbqd.addAll(i, collection2);
        this.modCount++;
        return addAll;
    }

    public final void clear() {
        zzpu();
        this.zzbqd.clear();
        this.modCount++;
    }

    public final void zzc(zzsw zzsw) {
        zzpu();
        this.zzbqd.add(zzsw);
        this.modCount++;
    }

    public final Object getRaw(int i) {
        return this.zzbqd.get(i);
    }

    private static String zzs(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzsw) {
            return ((zzsw) obj).zzpx();
        }
        return zzug.zzj((byte[]) obj);
    }

    public final List<?> zzrv() {
        return Collections.unmodifiableList(this.zzbqd);
    }

    public final zzux zzrw() {
        return zzps() ? new zzwz(this) : this;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        String str = (String) obj;
        zzpu();
        return zzs(this.zzbqd.set(i, str));
    }

    public final /* synthetic */ void add(int i, Object obj) {
        String str = (String) obj;
        zzpu();
        this.zzbqd.add(i, str);
        this.modCount++;
    }

    public final /* synthetic */ zzun zzck(int i) {
        if (i >= size()) {
            ArrayList arrayList = new ArrayList(i);
            arrayList.addAll(this.zzbqd);
            return new zzuw(arrayList);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        Object obj = this.zzbqd.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        String zzpx;
        if (obj instanceof zzsw) {
            zzsw zzsw = (zzsw) obj;
            zzpx = zzsw.zzpx();
            if (zzsw.zzpy()) {
                this.zzbqd.set(i, zzpx);
            }
            return zzpx;
        }
        byte[] bArr = (byte[]) obj;
        zzpx = zzug.zzj(bArr);
        if (zzug.zzi(bArr)) {
            this.zzbqd.set(i, zzpx);
        }
        return zzpx;
    }

    static {
        zzsq zzuw = new zzuw();
        zzbqb = zzuw;
        zzuw.zzpt();
    }
}
