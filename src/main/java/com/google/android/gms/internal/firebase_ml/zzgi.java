package com.google.android.gms.internal.firebase_ml;

import com.google.android.gms.internal.firebase_ml.zzgk.zza;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class zzgi {
    private static WeakHashMap<Class<?>, Field> zzwj = new WeakHashMap();
    private static final Lock zzwk = new ReentrantLock();

    public abstract void close() throws IOException;

    public abstract int getIntValue() throws IOException;

    public abstract String getText() throws IOException;

    public abstract zzge zzgg();

    public abstract zzgm zzgh() throws IOException;

    public abstract zzgm zzgi();

    public abstract String zzgj() throws IOException;

    public abstract zzgi zzgk() throws IOException;

    public abstract byte zzgl() throws IOException;

    public abstract short zzgm() throws IOException;

    public abstract float zzgn() throws IOException;

    public abstract long zzgo() throws IOException;

    public abstract double zzgp() throws IOException;

    public abstract BigInteger zzgq() throws IOException;

    public abstract BigDecimal zzgr() throws IOException;

    public final <T> T zza(Class<T> cls, zzgc zzgc) throws IOException {
        try {
            T zza = zza(cls, false, null);
            return zza;
        } finally {
            close();
        }
    }

    public final String zza(Set<String> set) throws IOException {
        zzgm zzgt = zzgt();
        while (zzgt == zzgm.FIELD_NAME) {
            String text = getText();
            zzgh();
            if (set.contains(text)) {
                return text;
            }
            zzgk();
            zzgt = zzgh();
        }
        return null;
    }

    private final zzgm zzgs() throws IOException {
        zzgm zzgi = zzgi();
        if (zzgi == null) {
            zzgi = zzgh();
        }
        zzks.checkArgument(zzgi != null, "no JSON input found");
        return zzgi;
    }

    private final zzgm zzgt() throws IOException {
        zzgm zzgs = zzgs();
        int i = zzgj.zzwl[zzgs.ordinal()];
        boolean z = true;
        if (i == 1) {
            zzgs = zzgh();
            if (!(zzgs == zzgm.FIELD_NAME || zzgs == zzgm.END_OBJECT)) {
                z = false;
            }
            zzks.checkArgument(z, zzgs);
            return zzgs;
        } else if (i != 2) {
            return zzgs;
        } else {
            return zzgh();
        }
    }

    public final Object zza(Type type, boolean z, zzgc zzgc) throws IOException {
        try {
            if (!Void.class.equals(type)) {
                zzgs();
            }
            Object zza = zza(null, type, new ArrayList(), null, null, true);
            return zza;
        } finally {
            if (z) {
                close();
            }
        }
    }

    private final void zza(Field field, Map<String, Object> map, Type type, ArrayList<Type> arrayList, zzgc zzgc) throws IOException {
        zzgm zzgt = zzgt();
        while (zzgt == zzgm.FIELD_NAME) {
            String text = getText();
            zzgh();
            if (zzgc == null) {
                map.put(text, zza(field, type, arrayList, map, zzgc, true));
                zzgt = zzgh();
            } else {
                throw new NoSuchMethodError();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:243:0x03da A:{Catch:{ IllegalArgumentException -> 0x043c }} */
    /* JADX WARNING: Removed duplicated region for block: B:247:0x03ec A:{Catch:{ IllegalArgumentException -> 0x043c }} */
    /* JADX WARNING: Removed duplicated region for block: B:246:0x03e7 A:{Catch:{ IllegalArgumentException -> 0x043c }} */
    private final java.lang.Object zza(java.lang.reflect.Field r20, java.lang.reflect.Type r21, java.util.ArrayList<java.lang.reflect.Type> r22, java.lang.Object r23, com.google.android.gms.internal.firebase_ml.zzgc r24, boolean r25) throws java.io.IOException {
        /*
        r19 = this;
        r8 = r20;
        r0 = r22;
        r1 = r21;
        r9 = com.google.android.gms.internal.firebase_ml.zzhf.zza(r0, r1);
        r1 = r9 instanceof java.lang.Class;
        r10 = 0;
        if (r1 == 0) goto L_0x0013;
    L_0x000f:
        r1 = r9;
        r1 = (java.lang.Class) r1;
        goto L_0x0014;
    L_0x0013:
        r1 = r10;
    L_0x0014:
        r2 = r9 instanceof java.lang.reflect.ParameterizedType;
        if (r2 == 0) goto L_0x001f;
    L_0x0018:
        r1 = r9;
        r1 = (java.lang.reflect.ParameterizedType) r1;
        r1 = com.google.android.gms.internal.firebase_ml.zzia.zza(r1);
    L_0x001f:
        r2 = java.lang.Void.class;
        if (r1 != r2) goto L_0x0027;
    L_0x0023:
        r19.zzgk();
        return r10;
    L_0x0027:
        r2 = r19.zzgi();
        r3 = com.google.android.gms.internal.firebase_ml.zzgj.zzwl;	 Catch:{ IllegalArgumentException -> 0x043c }
        r4 = r19.zzgi();	 Catch:{ IllegalArgumentException -> 0x043c }
        r4 = r4.ordinal();	 Catch:{ IllegalArgumentException -> 0x043c }
        r3 = r3[r4];	 Catch:{ IllegalArgumentException -> 0x043c }
        r11 = 0;
        r12 = 1;
        switch(r3) {
            case 1: goto L_0x024c;
            case 2: goto L_0x01d1;
            case 3: goto L_0x01d1;
            case 4: goto L_0x024c;
            case 5: goto L_0x024c;
            case 6: goto L_0x01a1;
            case 7: goto L_0x01a1;
            case 8: goto L_0x00e4;
            case 9: goto L_0x00e4;
            case 10: goto L_0x008b;
            case 11: goto L_0x0040;
            default: goto L_0x003c;
        };	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x003c:
        r0 = new java.lang.IllegalArgumentException;	 Catch:{ IllegalArgumentException -> 0x043c }
        goto L_0x0419;
    L_0x0040:
        if (r1 == 0) goto L_0x0048;
    L_0x0042:
        r2 = r1.isPrimitive();	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r2 != 0) goto L_0x0049;
    L_0x0048:
        r11 = 1;
    L_0x0049:
        r2 = "primitive number field but found a JSON null";
        com.google.android.gms.internal.firebase_ml.zzks.checkArgument(r11, r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 == 0) goto L_0x0082;
    L_0x0050:
        r2 = r1.getModifiers();	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = r2 & 1536;
        if (r2 == 0) goto L_0x0082;
    L_0x0058:
        r2 = java.util.Collection.class;
        r2 = com.google.android.gms.internal.firebase_ml.zzia.zza(r1, r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r2 == 0) goto L_0x006d;
    L_0x0060:
        r0 = com.google.android.gms.internal.firebase_ml.zzhf.zzb(r9);	 Catch:{ IllegalArgumentException -> 0x043c }
        r0 = r0.getClass();	 Catch:{ IllegalArgumentException -> 0x043c }
        r0 = com.google.android.gms.internal.firebase_ml.zzhf.zzd(r0);	 Catch:{ IllegalArgumentException -> 0x043c }
        return r0;
    L_0x006d:
        r2 = java.util.Map.class;
        r2 = com.google.android.gms.internal.firebase_ml.zzia.zza(r1, r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r2 == 0) goto L_0x0082;
    L_0x0075:
        r0 = com.google.android.gms.internal.firebase_ml.zzhf.zze(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        r0 = r0.getClass();	 Catch:{ IllegalArgumentException -> 0x043c }
        r0 = com.google.android.gms.internal.firebase_ml.zzhf.zzd(r0);	 Catch:{ IllegalArgumentException -> 0x043c }
        return r0;
    L_0x0082:
        r0 = com.google.android.gms.internal.firebase_ml.zzia.zzb(r0, r9);	 Catch:{ IllegalArgumentException -> 0x043c }
        r0 = com.google.android.gms.internal.firebase_ml.zzhf.zzd(r0);	 Catch:{ IllegalArgumentException -> 0x043c }
        return r0;
    L_0x008b:
        r0 = r19.getText();	 Catch:{ IllegalArgumentException -> 0x043c }
        r0 = r0.trim();	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = java.util.Locale.US;	 Catch:{ IllegalArgumentException -> 0x043c }
        r0 = r0.toLowerCase(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = java.lang.Float.TYPE;	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 == r2) goto L_0x00a9;
    L_0x009d:
        r2 = java.lang.Float.class;
        if (r1 == r2) goto L_0x00a9;
    L_0x00a1:
        r2 = java.lang.Double.TYPE;	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 == r2) goto L_0x00a9;
    L_0x00a5:
        r2 = java.lang.Double.class;
        if (r1 != r2) goto L_0x00c1;
    L_0x00a9:
        r2 = "nan";
        r2 = r0.equals(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r2 != 0) goto L_0x00db;
    L_0x00b1:
        r2 = "infinity";
        r2 = r0.equals(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r2 != 0) goto L_0x00db;
    L_0x00b9:
        r2 = "-infinity";
        r0 = r0.equals(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r0 != 0) goto L_0x00db;
    L_0x00c1:
        if (r1 == 0) goto L_0x00d5;
    L_0x00c3:
        r0 = java.lang.Number.class;
        r0 = r0.isAssignableFrom(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r0 == 0) goto L_0x00d5;
    L_0x00cb:
        if (r8 == 0) goto L_0x00d6;
    L_0x00cd:
        r0 = com.google.android.gms.internal.firebase_ml.zzgl.class;
        r0 = r8.getAnnotation(r0);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r0 == 0) goto L_0x00d6;
    L_0x00d5:
        r11 = 1;
    L_0x00d6:
        r0 = "number field formatted as a JSON string must use the @JsonString annotation";
        com.google.android.gms.internal.firebase_ml.zzks.checkArgument(r11, r0);	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x00db:
        r0 = r19.getText();	 Catch:{ IllegalArgumentException -> 0x043c }
        r0 = com.google.android.gms.internal.firebase_ml.zzhf.zza(r9, r0);	 Catch:{ IllegalArgumentException -> 0x043c }
        return r0;
    L_0x00e4:
        if (r8 == 0) goto L_0x00ee;
    L_0x00e6:
        r0 = com.google.android.gms.internal.firebase_ml.zzgl.class;
        r0 = r8.getAnnotation(r0);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r0 != 0) goto L_0x00ef;
    L_0x00ee:
        r11 = 1;
    L_0x00ef:
        r0 = "number type formatted as a JSON number cannot use @JsonString annotation";
        com.google.android.gms.internal.firebase_ml.zzks.checkArgument(r11, r0);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 == 0) goto L_0x019c;
    L_0x00f6:
        r0 = java.math.BigDecimal.class;
        r0 = r1.isAssignableFrom(r0);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r0 == 0) goto L_0x0100;
    L_0x00fe:
        goto L_0x019c;
    L_0x0100:
        r0 = java.math.BigInteger.class;
        if (r1 != r0) goto L_0x0109;
    L_0x0104:
        r0 = r19.zzgq();	 Catch:{ IllegalArgumentException -> 0x043c }
        return r0;
    L_0x0109:
        r0 = java.lang.Double.class;
        if (r1 == r0) goto L_0x0193;
    L_0x010d:
        r0 = java.lang.Double.TYPE;	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 != r0) goto L_0x0113;
    L_0x0111:
        goto L_0x0193;
    L_0x0113:
        r0 = java.lang.Long.class;
        if (r1 == r0) goto L_0x018a;
    L_0x0117:
        r0 = java.lang.Long.TYPE;	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 != r0) goto L_0x011d;
    L_0x011b:
        goto L_0x018a;
    L_0x011d:
        r0 = java.lang.Float.class;
        if (r1 == r0) goto L_0x0181;
    L_0x0121:
        r0 = java.lang.Float.TYPE;	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 != r0) goto L_0x0126;
    L_0x0125:
        goto L_0x0181;
    L_0x0126:
        r0 = java.lang.Integer.class;
        if (r1 == r0) goto L_0x0178;
    L_0x012a:
        r0 = java.lang.Integer.TYPE;	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 != r0) goto L_0x012f;
    L_0x012e:
        goto L_0x0178;
    L_0x012f:
        r0 = java.lang.Short.class;
        if (r1 == r0) goto L_0x016f;
    L_0x0133:
        r0 = java.lang.Short.TYPE;	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 != r0) goto L_0x0138;
    L_0x0137:
        goto L_0x016f;
    L_0x0138:
        r0 = java.lang.Byte.class;
        if (r1 == r0) goto L_0x0166;
    L_0x013c:
        r0 = java.lang.Byte.TYPE;	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 != r0) goto L_0x0141;
    L_0x0140:
        goto L_0x0166;
    L_0x0141:
        r0 = new java.lang.IllegalArgumentException;	 Catch:{ IllegalArgumentException -> 0x043c }
        r1 = java.lang.String.valueOf(r9);	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = java.lang.String.valueOf(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = r2.length();	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = r2 + 30;
        r3 = new java.lang.StringBuilder;	 Catch:{ IllegalArgumentException -> 0x043c }
        r3.<init>(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = "expected numeric type but got ";
        r3.append(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        r3.append(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        r1 = r3.toString();	 Catch:{ IllegalArgumentException -> 0x043c }
        r0.<init>(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        throw r0;	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x0166:
        r0 = r19.zzgl();	 Catch:{ IllegalArgumentException -> 0x043c }
        r0 = java.lang.Byte.valueOf(r0);	 Catch:{ IllegalArgumentException -> 0x043c }
        return r0;
    L_0x016f:
        r0 = r19.zzgm();	 Catch:{ IllegalArgumentException -> 0x043c }
        r0 = java.lang.Short.valueOf(r0);	 Catch:{ IllegalArgumentException -> 0x043c }
        return r0;
    L_0x0178:
        r0 = r19.getIntValue();	 Catch:{ IllegalArgumentException -> 0x043c }
        r0 = java.lang.Integer.valueOf(r0);	 Catch:{ IllegalArgumentException -> 0x043c }
        return r0;
    L_0x0181:
        r0 = r19.zzgn();	 Catch:{ IllegalArgumentException -> 0x043c }
        r0 = java.lang.Float.valueOf(r0);	 Catch:{ IllegalArgumentException -> 0x043c }
        return r0;
    L_0x018a:
        r0 = r19.zzgo();	 Catch:{ IllegalArgumentException -> 0x043c }
        r0 = java.lang.Long.valueOf(r0);	 Catch:{ IllegalArgumentException -> 0x043c }
        return r0;
    L_0x0193:
        r0 = r19.zzgp();	 Catch:{ IllegalArgumentException -> 0x043c }
        r0 = java.lang.Double.valueOf(r0);	 Catch:{ IllegalArgumentException -> 0x043c }
        return r0;
    L_0x019c:
        r0 = r19.zzgr();	 Catch:{ IllegalArgumentException -> 0x043c }
        return r0;
    L_0x01a1:
        if (r9 == 0) goto L_0x01b4;
    L_0x01a3:
        r0 = java.lang.Boolean.TYPE;	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 == r0) goto L_0x01b4;
    L_0x01a7:
        if (r1 == 0) goto L_0x01b2;
    L_0x01a9:
        r0 = java.lang.Boolean.class;
        r0 = r1.isAssignableFrom(r0);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r0 == 0) goto L_0x01b2;
    L_0x01b1:
        goto L_0x01b4;
    L_0x01b2:
        r0 = 0;
        goto L_0x01b5;
    L_0x01b4:
        r0 = 1;
    L_0x01b5:
        r1 = "expected type Boolean or boolean but got %s";
        r3 = new java.lang.Object[r12];	 Catch:{ IllegalArgumentException -> 0x043c }
        r3[r11] = r9;	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r0 == 0) goto L_0x01c7;
    L_0x01bd:
        r0 = com.google.android.gms.internal.firebase_ml.zzgm.VALUE_TRUE;	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r2 != r0) goto L_0x01c4;
    L_0x01c1:
        r0 = java.lang.Boolean.TRUE;	 Catch:{ IllegalArgumentException -> 0x043c }
        return r0;
    L_0x01c4:
        r0 = java.lang.Boolean.FALSE;	 Catch:{ IllegalArgumentException -> 0x043c }
        return r0;
    L_0x01c7:
        r0 = new java.lang.IllegalArgumentException;	 Catch:{ IllegalArgumentException -> 0x043c }
        r1 = com.google.android.gms.internal.firebase_ml.zzla.zzb(r1, r3);	 Catch:{ IllegalArgumentException -> 0x043c }
        r0.<init>(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        throw r0;	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x01d1:
        r13 = com.google.android.gms.internal.firebase_ml.zzia.zzc(r9);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r9 == 0) goto L_0x01e6;
    L_0x01d7:
        if (r13 != 0) goto L_0x01e6;
    L_0x01d9:
        if (r1 == 0) goto L_0x01e4;
    L_0x01db:
        r2 = java.util.Collection.class;
        r2 = com.google.android.gms.internal.firebase_ml.zzia.zza(r1, r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r2 == 0) goto L_0x01e4;
    L_0x01e3:
        goto L_0x01e6;
    L_0x01e4:
        r2 = 0;
        goto L_0x01e7;
    L_0x01e6:
        r2 = 1;
    L_0x01e7:
        r3 = "expected collection or array type but got %s";
        r4 = new java.lang.Object[r12];	 Catch:{ IllegalArgumentException -> 0x043c }
        r4[r11] = r9;	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r2 == 0) goto L_0x0242;
    L_0x01ef:
        if (r24 == 0) goto L_0x01fa;
    L_0x01f1:
        if (r8 != 0) goto L_0x01f4;
    L_0x01f3:
        goto L_0x01fa;
    L_0x01f4:
        r0 = new java.lang.NoSuchMethodError;	 Catch:{ IllegalArgumentException -> 0x043c }
        r0.<init>();	 Catch:{ IllegalArgumentException -> 0x043c }
        throw r0;	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x01fa:
        r11 = com.google.android.gms.internal.firebase_ml.zzhf.zzb(r9);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r13 == 0) goto L_0x0205;
    L_0x0200:
        r10 = com.google.android.gms.internal.firebase_ml.zzia.zzd(r9);	 Catch:{ IllegalArgumentException -> 0x043c }
        goto L_0x0213;
    L_0x0205:
        if (r1 == 0) goto L_0x0213;
    L_0x0207:
        r2 = java.lang.Iterable.class;
        r1 = r2.isAssignableFrom(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 == 0) goto L_0x0213;
    L_0x020f:
        r10 = com.google.android.gms.internal.firebase_ml.zzia.zze(r9);	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x0213:
        r9 = com.google.android.gms.internal.firebase_ml.zzhf.zza(r0, r10);	 Catch:{ IllegalArgumentException -> 0x043c }
        r1 = r19.zzgt();	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x021b:
        r2 = com.google.android.gms.internal.firebase_ml.zzgm.END_ARRAY;	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 == r2) goto L_0x0236;
    L_0x021f:
        r7 = 1;
        r1 = r19;
        r2 = r20;
        r3 = r9;
        r4 = r22;
        r5 = r11;
        r6 = r24;
        r1 = r1.zza(r2, r3, r4, r5, r6, r7);	 Catch:{ IllegalArgumentException -> 0x043c }
        r11.add(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        r1 = r19.zzgh();	 Catch:{ IllegalArgumentException -> 0x043c }
        goto L_0x021b;
    L_0x0236:
        if (r13 == 0) goto L_0x0241;
    L_0x0238:
        r0 = com.google.android.gms.internal.firebase_ml.zzia.zzb(r0, r9);	 Catch:{ IllegalArgumentException -> 0x043c }
        r0 = com.google.android.gms.internal.firebase_ml.zzia.zza(r11, r0);	 Catch:{ IllegalArgumentException -> 0x043c }
        return r0;
    L_0x0241:
        return r11;
    L_0x0242:
        r0 = new java.lang.IllegalArgumentException;	 Catch:{ IllegalArgumentException -> 0x043c }
        r1 = com.google.android.gms.internal.firebase_ml.zzla.zzb(r3, r4);	 Catch:{ IllegalArgumentException -> 0x043c }
        r0.<init>(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        throw r0;	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x024c:
        r2 = com.google.android.gms.internal.firebase_ml.zzia.zzc(r9);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r2 != 0) goto L_0x0254;
    L_0x0252:
        r2 = 1;
        goto L_0x0255;
    L_0x0254:
        r2 = 0;
    L_0x0255:
        r3 = "expected object or map type but got %s";
        r4 = new java.lang.Object[r12];	 Catch:{ IllegalArgumentException -> 0x043c }
        r4[r11] = r9;	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r2 == 0) goto L_0x040f;
    L_0x025d:
        if (r25 == 0) goto L_0x0265;
    L_0x025f:
        r2 = zzb(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        r13 = r2;
        goto L_0x0266;
    L_0x0265:
        r13 = r10;
    L_0x0266:
        if (r1 == 0) goto L_0x0271;
    L_0x0268:
        if (r24 != 0) goto L_0x026b;
    L_0x026a:
        goto L_0x0271;
    L_0x026b:
        r0 = new java.lang.NoSuchMethodError;	 Catch:{ IllegalArgumentException -> 0x043c }
        r0.<init>();	 Catch:{ IllegalArgumentException -> 0x043c }
        throw r0;	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x0271:
        if (r1 == 0) goto L_0x027d;
    L_0x0273:
        r2 = java.util.Map.class;
        r2 = com.google.android.gms.internal.firebase_ml.zzia.zza(r1, r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r2 == 0) goto L_0x027d;
    L_0x027b:
        r2 = 1;
        goto L_0x027e;
    L_0x027d:
        r2 = 0;
    L_0x027e:
        if (r13 == 0) goto L_0x0286;
    L_0x0280:
        r3 = new com.google.android.gms.internal.firebase_ml.zzgd;	 Catch:{ IllegalArgumentException -> 0x043c }
        r3.<init>();	 Catch:{ IllegalArgumentException -> 0x043c }
        goto L_0x0294;
    L_0x0286:
        if (r2 != 0) goto L_0x0290;
    L_0x0288:
        if (r1 != 0) goto L_0x028b;
    L_0x028a:
        goto L_0x0290;
    L_0x028b:
        r3 = com.google.android.gms.internal.firebase_ml.zzia.zzf(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        goto L_0x0294;
    L_0x0290:
        r3 = com.google.android.gms.internal.firebase_ml.zzhf.zze(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x0294:
        r14 = r3;
        r15 = r22.size();	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r9 == 0) goto L_0x029e;
    L_0x029b:
        r0.add(r9);	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x029e:
        if (r2 == 0) goto L_0x02c8;
    L_0x02a0:
        r2 = com.google.android.gms.internal.firebase_ml.zzhm.class;
        r2 = r2.isAssignableFrom(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r2 != 0) goto L_0x02c8;
    L_0x02a8:
        r2 = java.util.Map.class;
        r1 = r2.isAssignableFrom(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 == 0) goto L_0x02b6;
    L_0x02b0:
        r1 = com.google.android.gms.internal.firebase_ml.zzia.zzf(r9);	 Catch:{ IllegalArgumentException -> 0x043c }
        r4 = r1;
        goto L_0x02b7;
    L_0x02b6:
        r4 = r10;
    L_0x02b7:
        if (r4 == 0) goto L_0x02c8;
    L_0x02b9:
        r3 = r14;
        r3 = (java.util.Map) r3;	 Catch:{ IllegalArgumentException -> 0x043c }
        r1 = r19;
        r2 = r20;
        r5 = r22;
        r6 = r24;
        r1.zza(r2, r3, r4, r5, r6);	 Catch:{ IllegalArgumentException -> 0x043c }
        return r14;
    L_0x02c8:
        r1 = r14 instanceof com.google.android.gms.internal.firebase_ml.zzgd;	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 == 0) goto L_0x02d6;
    L_0x02cc:
        r1 = r14;
        r1 = (com.google.android.gms.internal.firebase_ml.zzgd) r1;	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = r19.zzgg();	 Catch:{ IllegalArgumentException -> 0x043c }
        r1.zza(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x02d6:
        r1 = r19.zzgt();	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = r14.getClass();	 Catch:{ IllegalArgumentException -> 0x043c }
        r7 = com.google.android.gms.internal.firebase_ml.zzhd.zzc(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        r3 = com.google.android.gms.internal.firebase_ml.zzhm.class;
        r16 = r3.isAssignableFrom(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r16 != 0) goto L_0x0307;
    L_0x02ea:
        r3 = java.util.Map.class;
        r3 = r3.isAssignableFrom(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r3 == 0) goto L_0x0307;
    L_0x02f2:
        r3 = r14;
        r3 = (java.util.Map) r3;	 Catch:{ IllegalArgumentException -> 0x043c }
        r4 = 0;
        r5 = com.google.android.gms.internal.firebase_ml.zzia.zzf(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        r1 = r19;
        r2 = r4;
        r4 = r5;
        r5 = r22;
        r6 = r24;
        r1.zza(r2, r3, r4, r5, r6);	 Catch:{ IllegalArgumentException -> 0x043c }
        goto L_0x0390;
    L_0x0307:
        r2 = com.google.android.gms.internal.firebase_ml.zzgm.FIELD_NAME;	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 != r2) goto L_0x0390;
    L_0x030b:
        r6 = r19.getText();	 Catch:{ IllegalArgumentException -> 0x043c }
        r19.zzgh();	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r24 != 0) goto L_0x038a;
    L_0x0314:
        r5 = r7.zzao(r6);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r5 == 0) goto L_0x035c;
    L_0x031a:
        r1 = r5.zzhg();	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 == 0) goto L_0x032f;
    L_0x0320:
        r1 = r5.isPrimitive();	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 == 0) goto L_0x0327;
    L_0x0326:
        goto L_0x032f;
    L_0x0327:
        r0 = new java.lang.IllegalArgumentException;	 Catch:{ IllegalArgumentException -> 0x043c }
        r1 = "final array/object fields are not supported";
        r0.<init>(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        throw r0;	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x032f:
        r2 = r5.zzhf();	 Catch:{ IllegalArgumentException -> 0x043c }
        r6 = r22.size();	 Catch:{ IllegalArgumentException -> 0x043c }
        r1 = r2.getGenericType();	 Catch:{ IllegalArgumentException -> 0x043c }
        r0.add(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        r3 = r5.getGenericType();	 Catch:{ IllegalArgumentException -> 0x043c }
        r17 = 1;
        r1 = r19;
        r4 = r22;
        r10 = r5;
        r5 = r14;
        r11 = r6;
        r6 = r24;
        r18 = r7;
        r7 = r17;
        r1 = r1.zza(r2, r3, r4, r5, r6, r7);	 Catch:{ IllegalArgumentException -> 0x043c }
        r0.remove(r11);	 Catch:{ IllegalArgumentException -> 0x043c }
        r10.zzb(r14, r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        goto L_0x037b;
    L_0x035c:
        r18 = r7;
        if (r16 == 0) goto L_0x0376;
    L_0x0360:
        r10 = r14;
        r10 = (com.google.android.gms.internal.firebase_ml.zzhm) r10;	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = 0;
        r3 = 0;
        r7 = 1;
        r1 = r19;
        r4 = r22;
        r5 = r14;
        r11 = r6;
        r6 = r24;
        r1 = r1.zza(r2, r3, r4, r5, r6, r7);	 Catch:{ IllegalArgumentException -> 0x043c }
        r10.zzb(r11, r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        goto L_0x037b;
    L_0x0376:
        if (r24 != 0) goto L_0x0384;
    L_0x0378:
        r19.zzgk();	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x037b:
        r1 = r19.zzgh();	 Catch:{ IllegalArgumentException -> 0x043c }
        r7 = r18;
        r10 = 0;
        r11 = 0;
        goto L_0x0307;
    L_0x0384:
        r0 = new java.lang.NoSuchMethodError;	 Catch:{ IllegalArgumentException -> 0x043c }
        r0.<init>();	 Catch:{ IllegalArgumentException -> 0x043c }
        throw r0;	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x038a:
        r0 = new java.lang.NoSuchMethodError;	 Catch:{ IllegalArgumentException -> 0x043c }
        r0.<init>();	 Catch:{ IllegalArgumentException -> 0x043c }
        throw r0;	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x0390:
        if (r9 == 0) goto L_0x0395;
    L_0x0392:
        r0.remove(r15);	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x0395:
        if (r13 != 0) goto L_0x0398;
    L_0x0397:
        return r14;
    L_0x0398:
        r1 = r14;
        r1 = (com.google.android.gms.internal.firebase_ml.zzgd) r1;	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = r13.getName();	 Catch:{ IllegalArgumentException -> 0x043c }
        r1 = r1.get(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r1 == 0) goto L_0x03a7;
    L_0x03a5:
        r2 = 1;
        goto L_0x03a8;
    L_0x03a7:
        r2 = 0;
    L_0x03a8:
        r3 = "No value specified for @JsonPolymorphicTypeMap field";
        com.google.android.gms.internal.firebase_ml.zzks.checkArgument(r2, r3);	 Catch:{ IllegalArgumentException -> 0x043c }
        r1 = r1.toString();	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = com.google.android.gms.internal.firebase_ml.zzgk.class;
        r2 = r13.getAnnotation(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = (com.google.android.gms.internal.firebase_ml.zzgk) r2;	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = r2.zzgu();	 Catch:{ IllegalArgumentException -> 0x043c }
        r3 = r2.length;	 Catch:{ IllegalArgumentException -> 0x043c }
        r4 = 0;
    L_0x03bf:
        if (r4 >= r3) goto L_0x03d6;
    L_0x03c1:
        r5 = r2[r4];	 Catch:{ IllegalArgumentException -> 0x043c }
        r6 = r5.zzgv();	 Catch:{ IllegalArgumentException -> 0x043c }
        r6 = r6.equals(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r6 == 0) goto L_0x03d3;
    L_0x03cd:
        r2 = r5.zzgw();	 Catch:{ IllegalArgumentException -> 0x043c }
        r3 = r2;
        goto L_0x03d7;
    L_0x03d3:
        r4 = r4 + 1;
        goto L_0x03bf;
    L_0x03d6:
        r3 = 0;
    L_0x03d7:
        if (r3 == 0) goto L_0x03da;
    L_0x03d9:
        goto L_0x03db;
    L_0x03da:
        r12 = 0;
    L_0x03db:
        r2 = "No TypeDef annotation found with key: ";
        r1 = java.lang.String.valueOf(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        r4 = r1.length();	 Catch:{ IllegalArgumentException -> 0x043c }
        if (r4 == 0) goto L_0x03ec;
    L_0x03e7:
        r1 = r2.concat(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        goto L_0x03f1;
    L_0x03ec:
        r1 = new java.lang.String;	 Catch:{ IllegalArgumentException -> 0x043c }
        r1.<init>(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x03f1:
        com.google.android.gms.internal.firebase_ml.zzks.checkArgument(r12, r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        r1 = r19.zzgg();	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = r1.toString(r14);	 Catch:{ IllegalArgumentException -> 0x043c }
        r1 = r1.zzam(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        r1.zzgs();	 Catch:{ IllegalArgumentException -> 0x043c }
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r2 = r20;
        r4 = r22;
        r0 = r1.zza(r2, r3, r4, r5, r6, r7);	 Catch:{ IllegalArgumentException -> 0x043c }
        return r0;
    L_0x040f:
        r0 = new java.lang.IllegalArgumentException;	 Catch:{ IllegalArgumentException -> 0x043c }
        r1 = com.google.android.gms.internal.firebase_ml.zzla.zzb(r3, r4);	 Catch:{ IllegalArgumentException -> 0x043c }
        r0.<init>(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        throw r0;	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x0419:
        r1 = java.lang.String.valueOf(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = java.lang.String.valueOf(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = r2.length();	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = r2 + 27;
        r3 = new java.lang.StringBuilder;	 Catch:{ IllegalArgumentException -> 0x043c }
        r3.<init>(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        r2 = "unexpected JSON node type: ";
        r3.append(r2);	 Catch:{ IllegalArgumentException -> 0x043c }
        r3.append(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        r1 = r3.toString();	 Catch:{ IllegalArgumentException -> 0x043c }
        r0.<init>(r1);	 Catch:{ IllegalArgumentException -> 0x043c }
        throw r0;	 Catch:{ IllegalArgumentException -> 0x043c }
    L_0x043c:
        r0 = move-exception;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = r19.zzgj();
        if (r2 == 0) goto L_0x0450;
    L_0x0448:
        r3 = "key ";
        r1.append(r3);
        r1.append(r2);
    L_0x0450:
        if (r8 == 0) goto L_0x0461;
    L_0x0452:
        if (r2 == 0) goto L_0x0459;
    L_0x0454:
        r2 = ", ";
        r1.append(r2);
    L_0x0459:
        r2 = "field ";
        r1.append(r2);
        r1.append(r8);
    L_0x0461:
        r2 = new java.lang.IllegalArgumentException;
        r1 = r1.toString();
        r2.<init>(r1, r0);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzgi.zza(java.lang.reflect.Field, java.lang.reflect.Type, java.util.ArrayList, java.lang.Object, com.google.android.gms.internal.firebase_ml.zzgc, boolean):java.lang.Object");
    }

    private static Field zzb(Class<?> cls) {
        Field field = null;
        if (cls == null) {
            return null;
        }
        zzwk.lock();
        try {
            if (zzwj.containsKey(cls)) {
                Field field2 = (Field) zzwj.get(cls);
                return field2;
            }
            for (zzhl zzhf : zzhd.zzc(cls).zzhd()) {
                Field zzhf2 = zzhf.zzhf();
                zzgk zzgk = (zzgk) zzhf2.getAnnotation(zzgk.class);
                if (zzgk != null) {
                    String str = "Class contains more than one field with @JsonPolymorphicTypeMap annotation: %s";
                    Object[] objArr = new Object[]{cls};
                    if ((field == null ? 1 : null) != null) {
                        str = "Field which has the @JsonPolymorphicTypeMap, %s, is not a supported type: %s";
                        objArr = new Object[]{cls, zzhf2.getType()};
                        if (zzhf.zza(zzhf2.getType())) {
                            zza[] zzgu = zzgk.zzgu();
                            HashSet hashSet = new HashSet();
                            zzks.checkArgument(zzgu.length > 0, "@JsonPolymorphicTypeMap must have at least one @TypeDef");
                            int length = zzgu.length;
                            int i = 0;
                            while (i < length) {
                                String str2 = "Class contains two @TypeDef annotations with identical key: %s";
                                Object[] objArr2 = new Object[]{r8.zzgv()};
                                if (hashSet.add(zzgu[i].zzgv())) {
                                    i++;
                                } else {
                                    throw new IllegalArgumentException(zzla.zzb(str2, objArr2));
                                }
                            }
                            field = zzhf2;
                        } else {
                            throw new IllegalArgumentException(zzla.zzb(str, objArr));
                        }
                    }
                    throw new IllegalArgumentException(zzla.zzb(str, objArr));
                }
            }
            zzwj.put(cls, field);
            zzwk.unlock();
            return field;
        } finally {
            zzwk.unlock();
        }
    }
}
