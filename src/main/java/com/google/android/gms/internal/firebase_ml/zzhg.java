package com.google.android.gms.internal.firebase_ml;

import java.util.AbstractMap;
import java.util.Set;

final class zzhg extends AbstractMap<String, Object> {
    final Object object;
    final zzhd zztx;

    zzhg(Object obj, boolean z) {
        this.object = obj;
        this.zztx = zzhd.zza(obj.getClass(), z);
        zzks.checkArgument(this.zztx.isEnum() ^ 1);
    }

    public final boolean containsKey(Object obj) {
        return get(obj) != null;
    }

    public final Object get(Object obj) {
        if (!(obj instanceof String)) {
            return null;
        }
        zzhl zzao = this.zztx.zzao((String) obj);
        if (zzao == null) {
            return null;
        }
        return zzao.zzh(this.object);
    }

    public final /* synthetic */ Set entrySet() {
        return new zzhj(this);
    }

    public final /* synthetic */ Object put(Object obj, Object obj2) {
        String str = (String) obj;
        zzhl zzao = this.zztx.zzao(str);
        str = String.valueOf(str);
        String str2 = "no field of key ";
        zzks.checkNotNull(zzao, str.length() != 0 ? str2.concat(str) : new String(str2));
        obj = zzao.zzh(this.object);
        zzao.zzb(this.object, zzks.checkNotNull(obj2));
        return obj;
    }
}
