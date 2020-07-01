package com.google.android.gms.internal.firebase_ml;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

public final class zzgy {
    private final Map<String, zzgz> zzye = new zzgx();
    private final Map<Field, zzgz> zzyf = new zzgx();
    private final Object zzyg;

    public zzgy(Object obj) {
        this.zzyg = obj;
    }

    public final void zzha() {
        for (Entry entry : this.zzye.entrySet()) {
            ((Map) this.zzyg).put((String) entry.getKey(), ((zzgz) entry.getValue()).zzhb());
        }
        for (Entry entry2 : this.zzyf.entrySet()) {
            zzhl.zza((Field) entry2.getKey(), this.zzyg, ((zzgz) entry2.getValue()).zzhb());
        }
    }

    public final void zza(Field field, Class<?> cls, Object obj) {
        zzgz zzgz = (zzgz) this.zzyf.get(field);
        if (zzgz == null) {
            zzgz = new zzgz(cls);
            this.zzyf.put(field, zzgz);
        }
        zzks.checkArgument(cls == zzgz.zzyh);
        zzgz.zzyi.add(obj);
    }
}
