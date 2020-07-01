package com.google.android.gms.internal.firebase_ml;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public final class zzhf {
    private static final Boolean zzyq = new Boolean(true);
    private static final String zzyr = new String();
    private static final Character zzys = new Character(0);
    private static final Byte zzyt = new Byte((byte) 0);
    private static final Short zzyu = new Short((short) 0);
    private static final Integer zzyv = new Integer(0);
    private static final Float zzyw = new Float(0.0f);
    private static final Long zzyx = new Long(0);
    private static final Double zzyy = new Double(0.0d);
    private static final BigInteger zzyz;
    private static final BigDecimal zzza;
    private static final zzhk zzzb = new zzhk(0);
    private static final ConcurrentHashMap<Class<?>, Object> zzzc;

    public static <T> T zzd(Class<?> cls) {
        T t = zzzc.get(cls);
        if (t == null) {
            synchronized (zzzc) {
                t = zzzc.get(cls);
                if (t == null) {
                    int i = 0;
                    if (cls.isArray()) {
                        Class cls2 = cls;
                        do {
                            cls2 = cls2.getComponentType();
                            i++;
                        } while (cls2.isArray());
                        t = Array.newInstance(cls2, new int[i]);
                    } else if (cls.isEnum()) {
                        zzhl zzao = zzhd.zzc(cls).zzao(null);
                        String str = "enum missing constant with @NullValue annotation: %s";
                        Object[] objArr = new Object[]{cls};
                        if (zzao != null) {
                            t = zzao.zzhh();
                        } else {
                            throw new NullPointerException(zzla.zzb(str, objArr));
                        }
                    } else {
                        t = zzia.zzf((Class) cls);
                    }
                    zzzc.put(cls, t);
                }
            }
        }
        return t;
    }

    public static boolean isNull(Object obj) {
        return obj != null && obj == zzzc.get(obj.getClass());
    }

    public static Map<String, Object> zzf(Object obj) {
        if (obj == null || isNull(obj)) {
            return Collections.emptyMap();
        }
        if (obj instanceof Map) {
            return (Map) obj;
        }
        return new zzhg(obj, false);
    }

    public static <T> T clone(T t) {
        if (t == null || zza(t.getClass())) {
            return t;
        }
        if (t instanceof zzhm) {
            return (zzhm) ((zzhm) t).clone();
        }
        Object newInstance;
        Class cls = t.getClass();
        if (cls.isArray()) {
            newInstance = Array.newInstance(cls.getComponentType(), Array.getLength(t));
        } else if (t instanceof zzgx) {
            newInstance = (zzgx) ((zzgx) t).clone();
        } else {
            if ("java.util.Arrays$ArrayList".equals(cls.getName())) {
                Object toArray = ((List) t).toArray();
                zza(toArray, toArray);
                return Arrays.asList(toArray);
            }
            newInstance = zzia.zzf(cls);
        }
        zza((Object) t, newInstance);
        return newInstance;
    }

    public static void zza(Object obj, Object obj2) {
        Class cls = obj.getClass();
        boolean z = true;
        int i = 0;
        zzks.checkArgument(cls == obj2.getClass());
        if (cls.isArray()) {
            if (Array.getLength(obj) != Array.getLength(obj2)) {
                z = false;
            }
            zzks.checkArgument(z);
            for (Object clone : zzia.zzi(obj)) {
                int i2 = i + 1;
                Array.set(obj2, i, clone(clone));
                i = i2;
            }
        } else if (Collection.class.isAssignableFrom(cls)) {
            Collection<Object> collection = (Collection) obj;
            if (ArrayList.class.isAssignableFrom(cls)) {
                ((ArrayList) obj2).ensureCapacity(collection.size());
            }
            Collection collection2 = (Collection) obj2;
            for (Object clone2 : collection) {
                collection2.add(clone(clone2));
            }
        } else {
            boolean isAssignableFrom = zzhm.class.isAssignableFrom(cls);
            if (isAssignableFrom || !Map.class.isAssignableFrom(cls)) {
                zzhd zzc = isAssignableFrom ? ((zzhm) obj).zztx : zzhd.zzc(cls);
                for (String zzao : zzc.zzyp) {
                    zzhl zzao2 = zzc.zzao(zzao);
                    if (!(zzao2.zzhg() || (isAssignableFrom && zzao2.isPrimitive()))) {
                        Object zzh = zzao2.zzh(obj);
                        if (zzh != null) {
                            zzao2.zzb(obj2, clone(zzh));
                        }
                    }
                }
            } else if (zzgx.class.isAssignableFrom(cls)) {
                zzgx zzgx = (zzgx) obj2;
                zzgx zzgx2 = (zzgx) obj;
                int size = zzgx2.size();
                while (i < size) {
                    zzgx.set(i, clone(zzgx2.zzaf(i)));
                    i++;
                }
            } else {
                Map map = (Map) obj2;
                for (Entry entry : ((Map) obj).entrySet()) {
                    map.put((String) entry.getKey(), clone(entry.getValue()));
                }
            }
        }
    }

    public static boolean zza(Type type) {
        if (type instanceof WildcardType) {
            type = zzia.zza((WildcardType) type);
        }
        if (!(type instanceof Class)) {
            return false;
        }
        Class cls = (Class) type;
        if (cls.isPrimitive() || cls == Character.class || cls == String.class || cls == Integer.class || cls == Long.class || cls == Short.class || cls == Byte.class || cls == Float.class || cls == Double.class || cls == BigInteger.class || cls == BigDecimal.class || cls == zzhk.class || cls == Boolean.class) {
            return true;
        }
        return false;
    }

    public static boolean zzg(Object obj) {
        return obj == null || zza(obj.getClass());
    }

    public static Object zza(Type type, String str) {
        StringBuilder stringBuilder;
        Class cls = type instanceof Class ? (Class) type : null;
        if (type == null || cls != null) {
            if (cls == Void.class) {
                return null;
            }
            if (str == null || cls == null || cls.isAssignableFrom(String.class)) {
                return str;
            }
            if (cls == Character.class || cls == Character.TYPE) {
                if (str.length() == 1) {
                    return Character.valueOf(str.charAt(0));
                }
                str = String.valueOf(cls);
                stringBuilder = new StringBuilder(String.valueOf(str).length() + 37);
                stringBuilder.append("expected type Character/char but got ");
                stringBuilder.append(str);
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (cls == Boolean.class || cls == Boolean.TYPE) {
                return Boolean.valueOf(str);
            } else {
                if (cls == Byte.class || cls == Byte.TYPE) {
                    return Byte.valueOf(str);
                }
                if (cls == Short.class || cls == Short.TYPE) {
                    return Short.valueOf(str);
                }
                if (cls == Integer.class || cls == Integer.TYPE) {
                    return Integer.valueOf(str);
                }
                if (cls == Long.class || cls == Long.TYPE) {
                    return Long.valueOf(str);
                }
                if (cls == Float.class || cls == Float.TYPE) {
                    return Float.valueOf(str);
                }
                if (cls == Double.class || cls == Double.TYPE) {
                    return Double.valueOf(str);
                }
                if (cls == zzhk.class) {
                    return zzhk.zzap(str);
                }
                if (cls == BigInteger.class) {
                    return new BigInteger(str);
                }
                if (cls == BigDecimal.class) {
                    return new BigDecimal(str);
                }
                if (cls.isEnum()) {
                    if (zzhd.zzc(cls).zzyp.contains(str)) {
                        return zzhd.zzc(cls).zzao(str).zzhh();
                    }
                    throw new IllegalArgumentException(String.format("given enum name %s not part of enumeration", new Object[]{str}));
                }
            }
        }
        String valueOf = String.valueOf(type);
        stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 35);
        stringBuilder.append("expected primitive class, but got: ");
        stringBuilder.append(valueOf);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static Collection<Object> zzb(Type type) {
        Object type2;
        if (type2 instanceof WildcardType) {
            type2 = zzia.zza((WildcardType) type2);
        }
        if (type2 instanceof ParameterizedType) {
            type2 = ((ParameterizedType) type2).getRawType();
        }
        Class cls = type2 instanceof Class ? (Class) type2 : null;
        if (type2 == null || (type2 instanceof GenericArrayType) || (cls != null && (cls.isArray() || cls.isAssignableFrom(ArrayList.class)))) {
            return new ArrayList();
        }
        if (cls == null) {
            String valueOf = String.valueOf(type2);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 39);
            stringBuilder.append("unable to create new instance of type: ");
            stringBuilder.append(valueOf);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (cls.isAssignableFrom(HashSet.class)) {
            return new HashSet();
        } else {
            if (cls.isAssignableFrom(TreeSet.class)) {
                return new TreeSet();
            }
            return (Collection) zzia.zzf(cls);
        }
    }

    public static Map<String, Object> zze(Class<?> cls) {
        if (cls == null || cls.isAssignableFrom(zzgx.class)) {
            return new zzgx();
        }
        if (cls.isAssignableFrom(TreeMap.class)) {
            return new TreeMap();
        }
        return (Map) zzia.zzf((Class) cls);
    }

    public static Type zza(List<Type> list, Type type) {
        if (type instanceof WildcardType) {
            type = zzia.zza((WildcardType) type);
        }
        while (type instanceof TypeVariable) {
            Type zza = zzia.zza((List) list, (TypeVariable) type);
            if (zza != null) {
                type = zza;
            }
            if (type instanceof TypeVariable) {
                type = ((TypeVariable) type).getBounds()[0];
            }
        }
        return type;
    }

    static {
        String str = "0";
        zzyz = new BigInteger(str);
        zzza = new BigDecimal(str);
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        zzzc = concurrentHashMap;
        concurrentHashMap.put(Boolean.class, zzyq);
        zzzc.put(String.class, zzyr);
        zzzc.put(Character.class, zzys);
        zzzc.put(Byte.class, zzyt);
        zzzc.put(Short.class, zzyu);
        zzzc.put(Integer.class, zzyv);
        zzzc.put(Float.class, zzyw);
        zzzc.put(Long.class, zzyx);
        zzzc.put(Double.class, zzyy);
        zzzc.put(BigInteger.class, zzyz);
        zzzc.put(BigDecimal.class, zzza);
        zzzc.put(zzhk.class, zzzb);
    }
}
