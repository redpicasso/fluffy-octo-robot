package com.google.android.gms.internal.firebase_ml;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class zzhm extends AbstractMap<String, Object> implements Cloneable {
    final zzhd zztx;
    Map<String, Object> zzzr;

    final class zza implements Iterator<Entry<String, Object>> {
        private boolean zzzs;
        private final Iterator<Entry<String, Object>> zzzt;
        private final Iterator<Entry<String, Object>> zzzu;

        zza(zzhm zzhm, zzhj zzhj) {
            this.zzzt = (zzhi) zzhj.iterator();
            this.zzzu = zzhm.zzzr.entrySet().iterator();
        }

        public final boolean hasNext() {
            return this.zzzt.hasNext() || this.zzzu.hasNext();
        }

        public final void remove() {
            if (this.zzzs) {
                this.zzzu.remove();
            }
            this.zzzt.remove();
        }

        public final /* synthetic */ Object next() {
            if (!this.zzzs) {
                if (this.zzzt.hasNext()) {
                    return (Entry) this.zzzt.next();
                }
                this.zzzs = true;
            }
            return (Entry) this.zzzu.next();
        }
    }

    final class zzb extends AbstractSet<Entry<String, Object>> {
        private final zzhj zzzv;

        zzb() {
            this.zzzv = (zzhj) new zzhg(zzhm.this, zzhm.this.zztx.zzhc()).entrySet();
        }

        public final Iterator<Entry<String, Object>> iterator() {
            return new zza(zzhm.this, this.zzzv);
        }

        public final int size() {
            return zzhm.this.zzzr.size() + this.zzzv.size();
        }

        public final void clear() {
            zzhm.this.zzzr.clear();
            this.zzzv.clear();
        }
    }

    public enum zzc {
        IGNORE_CASE
    }

    public zzhm() {
        this(EnumSet.noneOf(zzc.class));
    }

    public zzhm(EnumSet<zzc> enumSet) {
        this.zzzr = new zzgx();
        this.zztx = zzhd.zza(getClass(), enumSet.contains(zzc.IGNORE_CASE));
    }

    public final Object get(Object obj) {
        if (!(obj instanceof String)) {
            return null;
        }
        obj = (String) obj;
        zzhl zzao = this.zztx.zzao(obj);
        if (zzao != null) {
            return zzao.zzh(this);
        }
        if (this.zztx.zzhc()) {
            obj = obj.toLowerCase(Locale.US);
        }
        return this.zzzr.get(obj);
    }

    /* renamed from: zzf */
    public final Object put(String str, Object obj) {
        zzhl zzao = this.zztx.zzao(str);
        Object zzh;
        if (zzao != null) {
            zzh = zzao.zzh(this);
            zzao.zzb(this, obj);
            return zzh;
        }
        if (this.zztx.zzhc()) {
            zzh = str.toLowerCase(Locale.US);
        }
        return this.zzzr.put(zzh, obj);
    }

    public zzhm zzb(String str, Object obj) {
        zzhl zzao = this.zztx.zzao(str);
        if (zzao != null) {
            zzao.zzb(this, obj);
        } else {
            Object str2;
            if (this.zztx.zzhc()) {
                str2 = str2.toLowerCase(Locale.US);
            }
            this.zzzr.put(str2, obj);
        }
        return this;
    }

    public final void putAll(Map<? extends String, ?> map) {
        for (Entry entry : map.entrySet()) {
            zzb((String) entry.getKey(), entry.getValue());
        }
    }

    public final Object remove(Object obj) {
        if (!(obj instanceof String)) {
            return null;
        }
        obj = (String) obj;
        if (this.zztx.zzao(obj) == null) {
            if (this.zztx.zzhc()) {
                obj = obj.toLowerCase(Locale.US);
            }
            return this.zzzr.remove(obj);
        }
        throw new UnsupportedOperationException();
    }

    public Set<Entry<String, Object>> entrySet() {
        return new zzb();
    }

    /* renamed from: zzeh */
    public zzhm clone() {
        try {
            Object obj = (zzhm) super.clone();
            zzhf.zza((Object) this, obj);
            obj.zzzr = (Map) zzhf.clone(this.zzzr);
            return obj;
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public final zzhd zzhi() {
        return this.zztx;
    }
}
