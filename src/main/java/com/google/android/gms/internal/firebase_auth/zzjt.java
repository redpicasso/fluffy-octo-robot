package com.google.android.gms.internal.firebase_auth;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

class zzjt<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private final int zzadq;
    private List<zzkc> zzadr;
    private Map<K, V> zzads;
    private volatile zzke zzadt;
    private Map<K, V> zzadu;
    private volatile zzjy zzadv;
    private boolean zzxi;

    static <FieldDescriptorType extends zzhk<FieldDescriptorType>> zzjt<FieldDescriptorType, Object> zzbe(int i) {
        return new zzjw(i);
    }

    private zzjt(int i) {
        this.zzadq = i;
        this.zzadr = Collections.emptyList();
        this.zzads = Collections.emptyMap();
        this.zzadu = Collections.emptyMap();
    }

    public void zzfy() {
        if (!this.zzxi) {
            Map emptyMap;
            if (this.zzads.isEmpty()) {
                emptyMap = Collections.emptyMap();
            } else {
                emptyMap = Collections.unmodifiableMap(this.zzads);
            }
            this.zzads = emptyMap;
            if (this.zzadu.isEmpty()) {
                emptyMap = Collections.emptyMap();
            } else {
                emptyMap = Collections.unmodifiableMap(this.zzadu);
            }
            this.zzadu = emptyMap;
            this.zzxi = true;
        }
    }

    public final boolean isImmutable() {
        return this.zzxi;
    }

    public final int zzjy() {
        return this.zzadr.size();
    }

    public final Entry<K, V> zzbf(int i) {
        return (Entry) this.zzadr.get(i);
    }

    public final Iterable<Entry<K, V>> zzjz() {
        if (this.zzads.isEmpty()) {
            return zzjx.zzkj();
        }
        return this.zzads.entrySet();
    }

    public int size() {
        return this.zzadr.size() + this.zzads.size();
    }

    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza(comparable) >= 0 || this.zzads.containsKey(comparable);
    }

    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int zza = zza(comparable);
        if (zza >= 0) {
            return ((zzkc) this.zzadr.get(zza)).getValue();
        }
        return this.zzads.get(comparable);
    }

    /* renamed from: zza */
    public final V put(K k, V v) {
        zzkb();
        int zza = zza((Comparable) k);
        if (zza >= 0) {
            return ((zzkc) this.zzadr.get(zza)).setValue(v);
        }
        zzkb();
        if (this.zzadr.isEmpty() && !(this.zzadr instanceof ArrayList)) {
            this.zzadr = new ArrayList(this.zzadq);
        }
        zza = -(zza + 1);
        if (zza >= this.zzadq) {
            return zzkc().put(k, v);
        }
        int size = this.zzadr.size();
        int i = this.zzadq;
        if (size == i) {
            zzkc zzkc = (zzkc) this.zzadr.remove(i - 1);
            zzkc().put((Comparable) zzkc.getKey(), zzkc.getValue());
        }
        this.zzadr.add(zza, new zzkc(this, k, v));
        return null;
    }

    public void clear() {
        zzkb();
        if (!this.zzadr.isEmpty()) {
            this.zzadr.clear();
        }
        if (!this.zzads.isEmpty()) {
            this.zzads.clear();
        }
    }

    public V remove(Object obj) {
        zzkb();
        Comparable comparable = (Comparable) obj;
        int zza = zza(comparable);
        if (zza >= 0) {
            return zzbg(zza);
        }
        if (this.zzads.isEmpty()) {
            return null;
        }
        return this.zzads.remove(comparable);
    }

    private final V zzbg(int i) {
        zzkb();
        V value = ((zzkc) this.zzadr.remove(i)).getValue();
        if (!this.zzads.isEmpty()) {
            Iterator it = zzkc().entrySet().iterator();
            this.zzadr.add(new zzkc(this, (Entry) it.next()));
            it.remove();
        }
        return value;
    }

    private final int zza(K k) {
        int compareTo;
        int size = this.zzadr.size() - 1;
        if (size >= 0) {
            compareTo = k.compareTo((Comparable) ((zzkc) this.zzadr.get(size)).getKey());
            if (compareTo > 0) {
                return -(size + 2);
            }
            if (compareTo == 0) {
                return size;
            }
        }
        compareTo = 0;
        while (compareTo <= size) {
            int i = (compareTo + size) / 2;
            int compareTo2 = k.compareTo((Comparable) ((zzkc) this.zzadr.get(i)).getKey());
            if (compareTo2 < 0) {
                size = i - 1;
            } else if (compareTo2 <= 0) {
                return i;
            } else {
                compareTo = i + 1;
            }
        }
        return -(compareTo + 1);
    }

    public Set<Entry<K, V>> entrySet() {
        if (this.zzadt == null) {
            this.zzadt = new zzke(this, null);
        }
        return this.zzadt;
    }

    final Set<Entry<K, V>> zzka() {
        if (this.zzadv == null) {
            this.zzadv = new zzjy(this, null);
        }
        return this.zzadv;
    }

    private final void zzkb() {
        if (this.zzxi) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzkc() {
        zzkb();
        if (this.zzads.isEmpty() && !(this.zzads instanceof TreeMap)) {
            this.zzads = new TreeMap();
            this.zzadu = ((TreeMap) this.zzads).descendingMap();
        }
        return (SortedMap) this.zzads;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzjt)) {
            return super.equals(obj);
        }
        zzjt zzjt = (zzjt) obj;
        int size = size();
        if (size != zzjt.size()) {
            return false;
        }
        int zzjy = zzjy();
        if (zzjy != zzjt.zzjy()) {
            return entrySet().equals(zzjt.entrySet());
        }
        for (int i = 0; i < zzjy; i++) {
            if (!zzbf(i).equals(zzjt.zzbf(i))) {
                return false;
            }
        }
        if (zzjy != size) {
            return this.zzads.equals(zzjt.zzads);
        }
        return true;
    }

    public int hashCode() {
        int i = 0;
        for (int i2 = 0; i2 < zzjy(); i2++) {
            i += ((zzkc) this.zzadr.get(i2)).hashCode();
        }
        return this.zzads.size() > 0 ? i + this.zzads.hashCode() : i;
    }

    /* synthetic */ zzjt(int i, zzjw zzjw) {
        this(i);
    }
}