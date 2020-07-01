package com.google.android.gms.internal.firebase_ml;

import java.util.Locale;
import java.util.Map.Entry;

final class zzhh implements Entry<String, Object> {
    private Object zzzd;
    private final zzhl zzze;
    private final /* synthetic */ zzhg zzzf;

    zzhh(zzhg zzhg, zzhl zzhl, Object obj) {
        this.zzzf = zzhg;
        this.zzze = zzhl;
        this.zzzd = zzks.checkNotNull(obj);
    }

    public final Object getValue() {
        return this.zzzd;
    }

    public final Object setValue(Object obj) {
        Object obj2 = this.zzzd;
        this.zzzd = zzks.checkNotNull(obj);
        this.zzze.zzb(this.zzzf.object, obj);
        return obj2;
    }

    public final int hashCode() {
        return ((String) getKey()).hashCode() ^ getValue().hashCode();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Entry)) {
            return false;
        }
        Entry entry = (Entry) obj;
        return ((String) getKey()).equals(entry.getKey()) && getValue().equals(entry.getValue());
    }

    public final /* synthetic */ Object getKey() {
        Object name = this.zzze.getName();
        return this.zzzf.zztx.zzhc() ? name.toLowerCase(Locale.US) : name;
    }
}
