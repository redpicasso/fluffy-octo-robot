package com.google.android.gms.internal.firebase_ml;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public class zzgx<K, V> extends AbstractMap<K, V> implements Cloneable {
    int size;
    private Object[] zzya;

    final class zza implements Entry<K, V> {
        private int index;

        zza(int i) {
            this.index = i;
        }

        public final K getKey() {
            return zzgx.this.zzae(this.index);
        }

        public final V getValue() {
            return zzgx.this.zzaf(this.index);
        }

        public final V setValue(V v) {
            return zzgx.this.set(this.index, v);
        }

        public final int hashCode() {
            Object key = getKey();
            Object value = getValue();
            int i = 0;
            int hashCode = key != null ? key.hashCode() : 0;
            if (value != null) {
                i = value.hashCode();
            }
            return hashCode ^ i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return zzkn.equal(getKey(), entry.getKey()) && zzkn.equal(getValue(), entry.getValue());
        }
    }

    final class zzb implements Iterator<Entry<K, V>> {
        private boolean zzyc;
        private int zzyd;

        zzb() {
        }

        public final boolean hasNext() {
            return this.zzyd < zzgx.this.size;
        }

        public final void remove() {
            int i = this.zzyd - 1;
            if (this.zzyc || i < 0) {
                throw new IllegalArgumentException();
            }
            zzgx.this.remove(i);
            this.zzyd--;
            this.zzyc = true;
        }

        public final /* synthetic */ Object next() {
            int i = this.zzyd;
            if (i != zzgx.this.size) {
                this.zzyd++;
                this.zzyc = false;
                return new zza(i);
            }
            throw new NoSuchElementException();
        }
    }

    final class zzc extends AbstractSet<Entry<K, V>> {
        zzc() {
        }

        public final Iterator<Entry<K, V>> iterator() {
            return new zzb();
        }

        public final int size() {
            return zzgx.this.size;
        }
    }

    public final int size() {
        return this.size;
    }

    public final K zzae(int i) {
        return (i < 0 || i >= this.size) ? null : this.zzya[i << 1];
    }

    public final V zzaf(int i) {
        return (i < 0 || i >= this.size) ? null : zzag((i << 1) + 1);
    }

    public final V set(int i, V v) {
        int i2 = this.size;
        if (i < 0 || i >= i2) {
            throw new IndexOutOfBoundsException();
        }
        i = (i << 1) + 1;
        V zzag = zzag(i);
        this.zzya[i] = v;
        return zzag;
    }

    public final V remove(int i) {
        return zzah(i << 1);
    }

    public final boolean containsKey(Object obj) {
        return -2 != zze(obj);
    }

    public final V get(Object obj) {
        return zzag(zze(obj) + 1);
    }

    public final V put(K k, V v) {
        int zze = zze(k) >> 1;
        if (zze == -1) {
            zze = this.size;
        }
        if (zze >= 0) {
            int i = zze + 1;
            if (i >= 0) {
                int i2;
                Object[] objArr = this.zzya;
                int i3 = i << 1;
                if (objArr == null) {
                    i2 = 0;
                } else {
                    i2 = objArr.length;
                }
                if (i3 > i2) {
                    i2 = ((i2 / 2) * 3) + 1;
                    if (i2 % 2 != 0) {
                        i2++;
                    }
                    if (i2 < i3) {
                        i2 = i3;
                    }
                    if (i2 == 0) {
                        this.zzya = null;
                    } else {
                        i3 = this.size;
                        Object obj = this.zzya;
                        if (i3 == 0 || i2 != obj.length) {
                            Object obj2 = new Object[i2];
                            this.zzya = obj2;
                            if (i3 != 0) {
                                System.arraycopy(obj, 0, obj2, 0, i3 << 1);
                            }
                        }
                    }
                }
                zze <<= 1;
                V zzag = zzag(zze + 1);
                zzb(zze, k, v);
                if (i > this.size) {
                    this.size = i;
                }
                return zzag;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IndexOutOfBoundsException();
    }

    public final V remove(Object obj) {
        return zzah(zze(obj));
    }

    private final void zzb(int i, K k, V v) {
        Object[] objArr = this.zzya;
        objArr[i] = k;
        objArr[i + 1] = v;
    }

    private final V zzag(int i) {
        return i < 0 ? null : this.zzya[i];
    }

    private final int zze(Object obj) {
        int i = this.size << 1;
        Object[] objArr = this.zzya;
        int i2 = 0;
        while (i2 < i) {
            Object obj2 = objArr[i2];
            if (obj == null) {
                if (obj2 != null) {
                    i2 += 2;
                }
            } else if (!obj.equals(obj2)) {
                i2 += 2;
            }
            return i2;
        }
        return -2;
    }

    private final V zzah(int i) {
        int i2 = this.size << 1;
        if (i < 0 || i >= i2) {
            return null;
        }
        V zzag = zzag(i + 1);
        Object obj = this.zzya;
        int i3 = (i2 - i) - 2;
        if (i3 != 0) {
            System.arraycopy(obj, i + 2, obj, i, i3);
        }
        this.size--;
        zzb(i2 - 2, null, null);
        return zzag;
    }

    public void clear() {
        this.size = 0;
        this.zzya = null;
    }

    public final boolean containsValue(Object obj) {
        int i = this.size << 1;
        Object[] objArr = this.zzya;
        int i2 = 1;
        while (i2 < i) {
            Object obj2 = objArr[i2];
            if (obj == null) {
                if (obj2 != null) {
                    i2 += 2;
                }
            } else if (!obj.equals(obj2)) {
                i2 += 2;
            }
            return true;
        }
        return false;
    }

    public final Set<Entry<K, V>> entrySet() {
        return new zzc();
    }

    private final zzgx<K, V> zzgz() {
        try {
            zzgx<K, V> zzgx = (zzgx) super.clone();
            Object obj = this.zzya;
            if (obj != null) {
                int length = obj.length;
                Object obj2 = new Object[length];
                zzgx.zzya = obj2;
                System.arraycopy(obj, 0, obj2, 0, length);
            }
            return zzgx;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
