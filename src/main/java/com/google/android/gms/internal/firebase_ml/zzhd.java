package com.google.android.gms.internal.firebase_ml;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.WeakHashMap;

public final class zzhd {
    private static final Map<Class<?>, zzhd> zzyk = new WeakHashMap();
    private static final Map<Class<?>, zzhd> zzyl = new WeakHashMap();
    private final Class<?> zzym;
    private final boolean zzyn;
    private final IdentityHashMap<String, zzhl> zzyo = new IdentityHashMap();
    final List<String> zzyp;

    public static zzhd zzc(Class<?> cls) {
        return zza(cls, false);
    }

    public static zzhd zza(Class<?> cls, boolean z) {
        if (cls == null) {
            return null;
        }
        zzhd zzhd;
        Map map = z ? zzyl : zzyk;
        synchronized (map) {
            zzhd = (zzhd) map.get(cls);
            if (zzhd == null) {
                zzhd = new zzhd(cls, z);
                map.put(cls, zzhd);
            }
        }
        return zzhd;
    }

    public final boolean zzhc() {
        return this.zzyn;
    }

    public final zzhl zzao(String str) {
        Object str2;
        if (str2 != null) {
            if (this.zzyn) {
                str2 = str2.toLowerCase(Locale.US);
            }
            str2 = str2.intern();
        }
        return (zzhl) this.zzyo.get(str2);
    }

    public final boolean isEnum() {
        return this.zzym.isEnum();
    }

    private zzhd(Class<?> cls, boolean z) {
        this.zzym = cls;
        this.zzyn = z;
        boolean z2 = (z && cls.isEnum()) ? false : true;
        String valueOf = String.valueOf(cls);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 31);
        stringBuilder.append("cannot ignore case on an enum: ");
        stringBuilder.append(valueOf);
        zzks.checkArgument(z2, stringBuilder.toString());
        Collection treeSet = new TreeSet(new zzhe(this));
        for (Field field : cls.getDeclaredFields()) {
            zzhl zza = zzhl.zza(field);
            if (zza != null) {
                Field field2;
                Object name = zza.getName();
                if (z) {
                    name = name.toLowerCase(Locale.US).intern();
                }
                zzhl zzhl = (zzhl) this.zzyo.get(name);
                Object obj = zzhl == null ? 1 : null;
                Object[] objArr = new Object[4];
                objArr[0] = z ? "case-insensitive " : "";
                objArr[1] = name;
                objArr[2] = field;
                if (zzhl == null) {
                    field2 = null;
                } else {
                    field2 = zzhl.zzhf();
                }
                objArr[3] = field2;
                if (obj != null) {
                    this.zzyo.put(name, zza);
                    treeSet.add(name);
                } else {
                    throw new IllegalArgumentException(zzla.zzb("two fields have the same %sname <%s>: %s and %s", objArr));
                }
            }
        }
        Class superclass = cls.getSuperclass();
        if (superclass != null) {
            zzhd zza2 = zza(superclass, z);
            treeSet.addAll(zza2.zzyp);
            for (Entry entry : zza2.zzyo.entrySet()) {
                String str = (String) entry.getKey();
                if (!this.zzyo.containsKey(str)) {
                    this.zzyo.put(str, (zzhl) entry.getValue());
                }
            }
        }
        this.zzyp = treeSet.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList(treeSet));
    }

    public final Collection<zzhl> zzhd() {
        return Collections.unmodifiableCollection(this.zzyo.values());
    }
}
