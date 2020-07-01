package com.google.android.gms.internal.firebase_ml;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class zzia {
    private static ParameterizedType zza(Type type, Class<?> cls) {
        if ((type instanceof Class) || (type instanceof ParameterizedType)) {
            while (type != null && type != Object.class) {
                Class cls2;
                if (type instanceof Class) {
                    cls2 = (Class) type;
                } else {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    Class<?> zza = zza(parameterizedType);
                    if (zza == cls) {
                        return parameterizedType;
                    }
                    if (cls.isInterface()) {
                        for (Type type2 : zza.getGenericInterfaces()) {
                            if (cls.isAssignableFrom(type2 instanceof Class ? (Class) type2 : zza((ParameterizedType) type2))) {
                                type = type2;
                                break;
                            }
                        }
                    }
                    cls2 = zza;
                }
                type = cls2.getGenericSuperclass();
            }
        }
        return null;
    }

    public static boolean zza(Class<?> cls, Class<?> cls2) {
        return cls.isAssignableFrom(cls2) || cls2.isAssignableFrom(cls);
    }

    public static <T> T zzf(Class<T> cls) {
        Class cls2;
        try {
            cls2 = cls2.newInstance();
            return cls2;
        } catch (Exception e) {
            throw zza(e, cls2);
        } catch (Exception e2) {
            throw zza(e2, cls2);
        }
    }

    private static IllegalArgumentException zza(Exception exception, Class<?> cls) {
        StringBuilder stringBuilder = new StringBuilder("unable to create new instance of class ");
        stringBuilder.append(cls.getName());
        ArrayList arrayList = new ArrayList();
        int i = 0;
        if (cls.isArray()) {
            arrayList.add("because it is an array");
        } else if (cls.isPrimitive()) {
            arrayList.add("because it is primitive");
        } else if (cls == Void.class) {
            arrayList.add("because it is void");
        } else {
            if (Modifier.isInterface(cls.getModifiers())) {
                arrayList.add("because it is an interface");
            } else if (Modifier.isAbstract(cls.getModifiers())) {
                arrayList.add("because it is abstract");
            }
            if (!(cls.getEnclosingClass() == null || Modifier.isStatic(cls.getModifiers()))) {
                arrayList.add("because it is not static");
            }
            if (Modifier.isPublic(cls.getModifiers())) {
                try {
                    cls.getConstructor(new Class[0]);
                } catch (NoSuchMethodException unused) {
                    arrayList.add("because it has no accessible default constructor");
                }
            } else {
                arrayList.add("possibly because it is not public");
            }
        }
        arrayList = arrayList;
        int size = arrayList.size();
        Object obj = null;
        while (i < size) {
            Object obj2 = arrayList.get(i);
            i++;
            String str = (String) obj2;
            if (obj != null) {
                stringBuilder.append(" and");
            } else {
                obj = 1;
            }
            stringBuilder.append(" ");
            stringBuilder.append(str);
        }
        return new IllegalArgumentException(stringBuilder.toString(), exception);
    }

    public static boolean zzc(Type type) {
        return (type instanceof GenericArrayType) || ((type instanceof Class) && ((Class) type).isArray());
    }

    public static Type zzd(Type type) {
        if (type instanceof GenericArrayType) {
            return ((GenericArrayType) type).getGenericComponentType();
        }
        return ((Class) type).getComponentType();
    }

    public static Class<?> zza(ParameterizedType parameterizedType) {
        return (Class) parameterizedType.getRawType();
    }

    public static Type zza(WildcardType wildcardType) {
        Type[] lowerBounds = wildcardType.getLowerBounds();
        if (lowerBounds.length != 0) {
            return lowerBounds[0];
        }
        return wildcardType.getUpperBounds()[0];
    }

    public static Type zza(List<Type> list, TypeVariable<?> typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
            Class cls = (Class) genericDeclaration;
            int size = list.size();
            ParameterizedType parameterizedType = null;
            while (parameterizedType == null) {
                size--;
                if (size < 0) {
                    break;
                }
                parameterizedType = zza((Type) list.get(size), cls);
            }
            if (parameterizedType != null) {
                TypeVariable[] typeParameters = genericDeclaration.getTypeParameters();
                int i = 0;
                while (i < typeParameters.length && !typeParameters[i].equals(typeVariable)) {
                    i++;
                }
                Type type = parameterizedType.getActualTypeArguments()[i];
                if (type instanceof TypeVariable) {
                    Type zza = zza((List) list, (TypeVariable) type);
                    if (zza != null) {
                        return zza;
                    }
                }
                return type;
            }
        }
        return null;
    }

    public static Class<?> zzb(List<Type> list, Type type) {
        if (type instanceof TypeVariable) {
            type = zza((List) list, (TypeVariable) type);
        }
        if (type instanceof GenericArrayType) {
            return Array.newInstance(zzb(list, zzd(type)), 0).getClass();
        }
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return zza((ParameterizedType) type);
        }
        zzhv.checkArgument(type == null, "wildcard type is not supported: %s", type);
        return Object.class;
    }

    public static Type zze(Type type) {
        return zza(type, Iterable.class, 0);
    }

    public static Type zzf(Type type) {
        return zza(type, Map.class, 1);
    }

    private static Type zza(Type type, Class<?> cls, int i) {
        ParameterizedType zza = zza(type, (Class) cls);
        if (zza == null) {
            return null;
        }
        Type type2 = zza.getActualTypeArguments()[i];
        if (type2 instanceof TypeVariable) {
            type = zza(Arrays.asList(new Type[]{type}), (TypeVariable) type2);
            if (type != null) {
                return type;
            }
        }
        return type2;
    }

    public static <T> Iterable<T> zzi(Object obj) {
        if (obj instanceof Iterable) {
            return (Iterable) obj;
        }
        Class cls = obj.getClass();
        zzhv.checkArgument(cls.isArray(), "not an array or Iterable: %s", cls);
        if (cls.getComponentType().isPrimitive()) {
            return new zzib(obj);
        }
        return Arrays.asList((Object[]) obj);
    }

    public static Object zza(Collection<?> collection, Class<?> cls) {
        if (!cls.isPrimitive()) {
            return collection.toArray((Object[]) Array.newInstance(cls, collection.size()));
        }
        Object newInstance = Array.newInstance(cls, collection.size());
        int i = 0;
        for (Object obj : collection) {
            int i2 = i + 1;
            Array.set(newInstance, i, obj);
            i = i2;
        }
        return newInstance;
    }
}
