package com.google.android.gms.internal.firebase_ml;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.WeakHashMap;

public final class zzhl {
    private static final Map<Field, zzhl> zzyk = new WeakHashMap();
    private final String name;
    private final boolean zzzp;
    private final Field zzzq;

    public static zzhl zza(Enum<?> enumR) {
        try {
            zzhl zza = zza(enumR.getClass().getField(enumR.name()));
            String str = "enum constant missing @Value or @NullValue annotation: %s";
            Object[] objArr = new Object[]{enumR};
            if ((zza != null ? 1 : null) != null) {
                return zza;
            }
            throw new IllegalArgumentException(zzla.zzb(str, objArr));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARNING: Missing block: B:29:0x006a, code:
            return r2;
     */
    public static com.google.android.gms.internal.firebase_ml.zzhl zza(java.lang.reflect.Field r5) {
        /*
        r0 = 0;
        if (r5 != 0) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = zzyk;
        monitor-enter(r1);
        r2 = zzyk;	 Catch:{ all -> 0x006b }
        r2 = r2.get(r5);	 Catch:{ all -> 0x006b }
        r2 = (com.google.android.gms.internal.firebase_ml.zzhl) r2;	 Catch:{ all -> 0x006b }
        r3 = r5.isEnumConstant();	 Catch:{ all -> 0x006b }
        if (r2 != 0) goto L_0x0069;
    L_0x0015:
        if (r3 != 0) goto L_0x0021;
    L_0x0017:
        r4 = r5.getModifiers();	 Catch:{ all -> 0x006b }
        r4 = java.lang.reflect.Modifier.isStatic(r4);	 Catch:{ all -> 0x006b }
        if (r4 != 0) goto L_0x0069;
    L_0x0021:
        if (r3 == 0) goto L_0x003f;
    L_0x0023:
        r2 = com.google.android.gms.internal.firebase_ml.zzid.class;
        r2 = r5.getAnnotation(r2);	 Catch:{ all -> 0x006b }
        r2 = (com.google.android.gms.internal.firebase_ml.zzid) r2;	 Catch:{ all -> 0x006b }
        if (r2 == 0) goto L_0x0032;
    L_0x002d:
        r0 = r2.value();	 Catch:{ all -> 0x006b }
        goto L_0x0053;
    L_0x0032:
        r2 = com.google.android.gms.internal.firebase_ml.zzht.class;
        r2 = r5.getAnnotation(r2);	 Catch:{ all -> 0x006b }
        r2 = (com.google.android.gms.internal.firebase_ml.zzht) r2;	 Catch:{ all -> 0x006b }
        if (r2 == 0) goto L_0x003d;
    L_0x003c:
        goto L_0x0053;
    L_0x003d:
        monitor-exit(r1);	 Catch:{ all -> 0x006b }
        return r0;
    L_0x003f:
        r2 = com.google.android.gms.internal.firebase_ml.zzho.class;
        r2 = r5.getAnnotation(r2);	 Catch:{ all -> 0x006b }
        r2 = (com.google.android.gms.internal.firebase_ml.zzho) r2;	 Catch:{ all -> 0x006b }
        if (r2 != 0) goto L_0x004b;
    L_0x0049:
        monitor-exit(r1);	 Catch:{ all -> 0x006b }
        return r0;
    L_0x004b:
        r0 = r2.value();	 Catch:{ all -> 0x006b }
        r2 = 1;
        r5.setAccessible(r2);	 Catch:{ all -> 0x006b }
    L_0x0053:
        r2 = "##default";
        r2 = r2.equals(r0);	 Catch:{ all -> 0x006b }
        if (r2 == 0) goto L_0x005f;
    L_0x005b:
        r0 = r5.getName();	 Catch:{ all -> 0x006b }
    L_0x005f:
        r2 = new com.google.android.gms.internal.firebase_ml.zzhl;	 Catch:{ all -> 0x006b }
        r2.<init>(r5, r0);	 Catch:{ all -> 0x006b }
        r0 = zzyk;	 Catch:{ all -> 0x006b }
        r0.put(r5, r2);	 Catch:{ all -> 0x006b }
    L_0x0069:
        monitor-exit(r1);	 Catch:{ all -> 0x006b }
        return r2;
    L_0x006b:
        r5 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x006b }
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzhl.zza(java.lang.reflect.Field):com.google.android.gms.internal.firebase_ml.zzhl");
    }

    private zzhl(Field field, String str) {
        String str2;
        this.zzzq = field;
        if (str == null) {
            str2 = null;
        } else {
            str2 = str.intern();
        }
        this.name = str2;
        this.zzzp = zzhf.zza(this.zzzq.getType());
    }

    public final Field zzhf() {
        return this.zzzq;
    }

    public final String getName() {
        return this.name;
    }

    public final Type getGenericType() {
        return this.zzzq.getGenericType();
    }

    public final boolean zzhg() {
        return Modifier.isFinal(this.zzzq.getModifiers());
    }

    public final boolean isPrimitive() {
        return this.zzzp;
    }

    public final Object zzh(Object obj) {
        return zza(this.zzzq, obj);
    }

    public final void zzb(Object obj, Object obj2) {
        zza(this.zzzq, obj, obj2);
    }

    public final <T extends Enum<T>> T zzhh() {
        return Enum.valueOf(this.zzzq.getDeclaringClass(), this.zzzq.getName());
    }

    private static Object zza(Field field, Object obj) {
        try {
            return field.get(obj);
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void zza(Field field, Object obj, Object obj2) {
        if (Modifier.isFinal(field.getModifiers())) {
            Object zza = zza(field, obj);
            if (obj2 != null ? !obj2.equals(zza) : zza != null) {
                String valueOf = String.valueOf(zza);
                String valueOf2 = String.valueOf(obj2);
                String name = field.getName();
                String name2 = obj.getClass().getName();
                StringBuilder stringBuilder = new StringBuilder((((String.valueOf(valueOf).length() + 48) + String.valueOf(valueOf2).length()) + String.valueOf(name).length()) + String.valueOf(name2).length());
                stringBuilder.append("expected final value <");
                stringBuilder.append(valueOf);
                stringBuilder.append("> but was <");
                stringBuilder.append(valueOf2);
                stringBuilder.append("> on ");
                stringBuilder.append(name);
                stringBuilder.append(" field in ");
                stringBuilder.append(name2);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            return;
        }
        try {
            field.set(obj, obj2);
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        } catch (Throwable e2) {
            throw new IllegalArgumentException(e2);
        }
    }
}
