package com.google.firebase.firestore.util;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.ThrowOnExtraProperties;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class CustomClassMapper {
    private static final int MAX_DEPTH = 500;
    private static final ConcurrentMap<Class<?>, BeanMapper<?>> mappers = new ConcurrentHashMap();

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    private static class BeanMapper<T> {
        private final Class<T> clazz;
        private final Constructor<T> constructor;
        private final Map<String, Field> fields = new HashMap();
        private final Map<String, Method> getters = new HashMap();
        private final Map<String, String> properties = new HashMap();
        private final HashSet<String> serverTimestamps = new HashSet();
        private final Map<String, Method> setters = new HashMap();
        private final boolean throwOnUnknownProperties;
        private final boolean warnOnUnknownProperties;

        BeanMapper(Class<T> cls) {
            Constructor declaredConstructor;
            StringBuilder stringBuilder;
            this.clazz = cls;
            this.throwOnUnknownProperties = cls.isAnnotationPresent(ThrowOnExtraProperties.class);
            this.warnOnUnknownProperties = cls.isAnnotationPresent(IgnoreExtraProperties.class) ^ true;
            try {
                declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
                declaredConstructor.setAccessible(true);
            } catch (NoSuchMethodException unused) {
                declaredConstructor = null;
            }
            this.constructor = declaredConstructor;
            for (Method method : cls.getMethods()) {
                if (shouldIncludeGetter(method)) {
                    String propertyName = propertyName(method);
                    addProperty(propertyName);
                    method.setAccessible(true);
                    if (this.getters.containsKey(propertyName)) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Found conflicting getters for name ");
                        stringBuilder.append(method.getName());
                        stringBuilder.append(" on class ");
                        stringBuilder.append(cls.getName());
                        throw new RuntimeException(stringBuilder.toString());
                    }
                    this.getters.put(propertyName, method);
                    applyGetterAnnotations(method);
                }
            }
            for (Field field : cls.getFields()) {
                if (shouldIncludeField(field)) {
                    addProperty(propertyName(field));
                    applyFieldAnnotations(field);
                }
            }
            Class<T> cls2 = cls;
            do {
                String propertyName2;
                for (Method method2 : cls2.getDeclaredMethods()) {
                    if (shouldIncludeSetter(method2)) {
                        propertyName2 = propertyName(method2);
                        String str = (String) this.properties.get(propertyName2.toLowerCase(Locale.US));
                        StringBuilder stringBuilder2;
                        if (str == null) {
                            continue;
                        } else if (str.equals(propertyName2)) {
                            Method method3 = (Method) this.setters.get(propertyName2);
                            if (method3 == null) {
                                method2.setAccessible(true);
                                this.setters.put(propertyName2, method2);
                                applySetterAnnotations(method2);
                            } else if (!isSetterOverride(method2, method3)) {
                                if (cls2 == cls) {
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("Class ");
                                    stringBuilder.append(cls.getName());
                                    stringBuilder.append(" has multiple setter overloads with name ");
                                    stringBuilder.append(method2.getName());
                                    throw new RuntimeException(stringBuilder.toString());
                                }
                                stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("Found conflicting setters with name: ");
                                stringBuilder2.append(method2.getName());
                                stringBuilder2.append(" (conflicts with ");
                                stringBuilder2.append(method3.getName());
                                stringBuilder2.append(" defined on ");
                                stringBuilder2.append(method3.getDeclaringClass().getName());
                                stringBuilder2.append(")");
                                throw new RuntimeException(stringBuilder2.toString());
                            }
                        } else {
                            stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("Found setter on ");
                            stringBuilder2.append(cls2.getName());
                            stringBuilder2.append(" with invalid case-sensitive name: ");
                            stringBuilder2.append(method2.getName());
                            throw new RuntimeException(stringBuilder2.toString());
                        }
                    }
                }
                for (Field field2 : cls2.getDeclaredFields()) {
                    propertyName2 = propertyName(field2);
                    if (this.properties.containsKey(propertyName2.toLowerCase(Locale.US)) && !this.fields.containsKey(propertyName2)) {
                        field2.setAccessible(true);
                        this.fields.put(propertyName2, field2);
                        applyFieldAnnotations(field2);
                    }
                }
                cls2 = cls2.getSuperclass();
                if (cls2 == null) {
                    break;
                }
            } while (!cls2.equals(Object.class));
            if (this.properties.isEmpty()) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("No properties to serialize found on class ");
                stringBuilder.append(cls.getName());
                throw new RuntimeException(stringBuilder.toString());
            }
        }

        private void addProperty(String str) {
            String str2 = (String) this.properties.put(str.toLowerCase(Locale.US), str);
            if (str2 != null && !str.equals(str2)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Found two getters or fields with conflicting case sensitivity for property: ");
                stringBuilder.append(str.toLowerCase(Locale.US));
                throw new RuntimeException(stringBuilder.toString());
            }
        }

        T deserialize(Map<String, Object> map, ErrorPath errorPath) {
            return deserialize(map, Collections.emptyMap(), errorPath);
        }

        T deserialize(Map<String, Object> map, Map<TypeVariable<Class<T>>, Type> map2, ErrorPath errorPath) {
            Constructor constructor = this.constructor;
            if (constructor != null) {
                T newInstance = ApiUtil.newInstance(constructor);
                for (Entry entry : map.entrySet()) {
                    String str = (String) entry.getKey();
                    ErrorPath child = errorPath.child(str);
                    if (this.setters.containsKey(str)) {
                        Method method = (Method) this.setters.get(str);
                        Type[] genericParameterTypes = method.getGenericParameterTypes();
                        if (genericParameterTypes.length == 1) {
                            Object access$300 = CustomClassMapper.deserializeToType(entry.getValue(), resolveType(genericParameterTypes[0], map2), child);
                            ApiUtil.invoke(method, newInstance, access$300);
                        } else {
                            throw CustomClassMapper.deserializeError(child, "Setter does not have exactly one parameter");
                        }
                    } else if (this.fields.containsKey(str)) {
                        Field field = (Field) this.fields.get(str);
                        try {
                            field.set(newInstance, CustomClassMapper.deserializeToType(entry.getValue(), resolveType(field.getGenericType(), map2), child));
                        } catch (Throwable e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("No setter/field for ");
                        stringBuilder.append(str);
                        stringBuilder.append(" found on class ");
                        stringBuilder.append(this.clazz.getName());
                        String stringBuilder2 = stringBuilder.toString();
                        if (this.properties.containsKey(str.toLowerCase(Locale.US))) {
                            StringBuilder stringBuilder3 = new StringBuilder();
                            stringBuilder3.append(stringBuilder2);
                            stringBuilder3.append(" (fields/setters are case sensitive!)");
                            stringBuilder2 = stringBuilder3.toString();
                        }
                        if (this.throwOnUnknownProperties) {
                            throw new RuntimeException(stringBuilder2);
                        } else if (this.warnOnUnknownProperties) {
                            Logger.warn(CustomClassMapper.class.getSimpleName(), "%s", stringBuilder2);
                        }
                    }
                }
                return newInstance;
            }
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("Class ");
            stringBuilder4.append(this.clazz.getName());
            stringBuilder4.append(" does not define a no-argument constructor. If you are using ProGuard, make sure these constructors are not stripped");
            throw CustomClassMapper.deserializeError(errorPath, stringBuilder4.toString());
        }

        private Type resolveType(Type type, Map<TypeVariable<Class<T>>, Type> map) {
            if (!(type instanceof TypeVariable)) {
                return type;
            }
            Type type2 = (Type) map.get(type);
            if (type2 != null) {
                return type2;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not resolve type ");
            stringBuilder.append(type);
            throw new IllegalStateException(stringBuilder.toString());
        }

        Map<String, Object> serialize(T t, ErrorPath errorPath) {
            if (this.clazz.isAssignableFrom(t.getClass())) {
                Map<String, Object> hashMap = new HashMap();
                for (String str : this.properties.values()) {
                    Object invoke;
                    if (this.getters.containsKey(str)) {
                        invoke = ApiUtil.invoke((Method) this.getters.get(str), t, new Object[0]);
                    } else {
                        Field field = (Field) this.fields.get(str);
                        if (field != null) {
                            try {
                                invoke = field.get(t);
                            } catch (Throwable e) {
                                throw new RuntimeException(e);
                            }
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Bean property without field or getter: ");
                        stringBuilder.append(str);
                        throw new IllegalStateException(stringBuilder.toString());
                    }
                    if (this.serverTimestamps.contains(str) && invoke == null) {
                        invoke = FieldValue.serverTimestamp();
                    } else {
                        invoke = CustomClassMapper.serialize(invoke, errorPath.child(str));
                    }
                    hashMap.put(str, invoke);
                }
                return hashMap;
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Can't serialize object of class ");
            stringBuilder2.append(t.getClass());
            stringBuilder2.append(" with BeanMapper for class ");
            stringBuilder2.append(this.clazz);
            throw new IllegalArgumentException(stringBuilder2.toString());
        }

        private void applyFieldAnnotations(Field field) {
            if (field.isAnnotationPresent(ServerTimestamp.class)) {
                Class type = field.getType();
                if (type == Date.class || type == Timestamp.class) {
                    this.serverTimestamps.add(propertyName(field));
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Field ");
                stringBuilder.append(field.getName());
                stringBuilder.append(" is annotated with @ServerTimestamp but is ");
                stringBuilder.append(type);
                stringBuilder.append(" instead of Date or Timestamp.");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }

        private void applyGetterAnnotations(Method method) {
            if (method.isAnnotationPresent(ServerTimestamp.class)) {
                Class returnType = method.getReturnType();
                if (returnType == Date.class || returnType == Timestamp.class) {
                    this.serverTimestamps.add(propertyName(method));
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Method ");
                stringBuilder.append(method.getName());
                stringBuilder.append(" is annotated with @ServerTimestamp but returns ");
                stringBuilder.append(returnType);
                stringBuilder.append(" instead of Date or Timestamp.");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }

        private void applySetterAnnotations(Method method) {
            if (method.isAnnotationPresent(ServerTimestamp.class)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Method ");
                stringBuilder.append(method.getName());
                stringBuilder.append(" is annotated with @ServerTimestamp but should not be. @ServerTimestamp can only be applied to fields and getters, not setters.");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }

        private static boolean shouldIncludeGetter(Method method) {
            if ((!method.getName().startsWith("get") && !method.getName().startsWith("is")) || method.getDeclaringClass().equals(Object.class) || !Modifier.isPublic(method.getModifiers()) || Modifier.isStatic(method.getModifiers()) || method.getReturnType().equals(Void.TYPE) || method.getParameterTypes().length != 0 || method.isAnnotationPresent(Exclude.class)) {
                return false;
            }
            return true;
        }

        private static boolean shouldIncludeSetter(Method method) {
            if (method.getName().startsWith("set") && !method.getDeclaringClass().equals(Object.class) && !Modifier.isStatic(method.getModifiers()) && method.getReturnType().equals(Void.TYPE) && method.getParameterTypes().length == 1 && !method.isAnnotationPresent(Exclude.class)) {
                return true;
            }
            return false;
        }

        private static boolean shouldIncludeField(Field field) {
            if (field.getDeclaringClass().equals(Object.class) || !Modifier.isPublic(field.getModifiers()) || Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers()) || field.isAnnotationPresent(Exclude.class)) {
                return false;
            }
            return true;
        }

        private static boolean isSetterOverride(Method method, Method method2) {
            CustomClassMapper.hardAssert(method.getDeclaringClass().isAssignableFrom(method2.getDeclaringClass()), "Expected override from a base class");
            String str = "Expected void return type";
            CustomClassMapper.hardAssert(method.getReturnType().equals(Void.TYPE), str);
            CustomClassMapper.hardAssert(method2.getReturnType().equals(Void.TYPE), str);
            Class[] parameterTypes = method.getParameterTypes();
            Class[] parameterTypes2 = method2.getParameterTypes();
            String str2 = "Expected exactly one parameter";
            CustomClassMapper.hardAssert(parameterTypes.length == 1, str2);
            CustomClassMapper.hardAssert(parameterTypes2.length == 1, str2);
            if (method.getName().equals(method2.getName()) && parameterTypes[0].equals(parameterTypes2[0])) {
                return true;
            }
            return false;
        }

        private static String propertyName(Field field) {
            String annotatedName = annotatedName(field);
            return annotatedName != null ? annotatedName : field.getName();
        }

        private static String propertyName(Method method) {
            String annotatedName = annotatedName(method);
            return annotatedName != null ? annotatedName : serializedName(method.getName());
        }

        private static String annotatedName(AccessibleObject accessibleObject) {
            return accessibleObject.isAnnotationPresent(PropertyName.class) ? ((PropertyName) accessibleObject.getAnnotation(PropertyName.class)).value() : null;
        }

        private static String serializedName(String str) {
            int i = 0;
            String str2 = null;
            for (String str3 : new String[]{"get", "set", "is"}) {
                if (str.startsWith(str3)) {
                    str2 = str3;
                }
            }
            if (str2 != null) {
                char[] toCharArray = str.substring(str2.length()).toCharArray();
                while (i < toCharArray.length && Character.isUpperCase(toCharArray[i])) {
                    toCharArray[i] = Character.toLowerCase(toCharArray[i]);
                    i++;
                }
                return new String(toCharArray);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown Bean prefix for method: ");
            stringBuilder.append(str);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static class ErrorPath {
        static final ErrorPath EMPTY = new ErrorPath(null, null, 0);
        private final int length;
        private final String name;
        private final ErrorPath parent;

        ErrorPath(ErrorPath errorPath, String str, int i) {
            this.parent = errorPath;
            this.name = str;
            this.length = i;
        }

        int getLength() {
            return this.length;
        }

        ErrorPath child(String str) {
            return new ErrorPath(this, str, this.length + 1);
        }

        public String toString() {
            int i = this.length;
            if (i == 0) {
                return "";
            }
            if (i == 1) {
                return this.name;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.parent.toString());
            stringBuilder.append(".");
            stringBuilder.append(this.name);
            return stringBuilder.toString();
        }
    }

    private static void hardAssert(boolean z) {
        hardAssert(z, "Internal inconsistency");
    }

    private static void hardAssert(boolean z, String str) {
        if (!z) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Hard assert failed: ");
            stringBuilder.append(str);
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    public static Object convertToPlainJavaTypes(Object obj) {
        return serialize(obj);
    }

    public static Map<String, Object> convertToPlainJavaTypes(Map<?, Object> map) {
        Object serialize = serialize(map);
        hardAssert(serialize instanceof Map);
        return (Map) serialize;
    }

    public static <T> T convertToCustomClass(Object obj, Class<T> cls) {
        return deserializeToClass(obj, cls, ErrorPath.EMPTY);
    }

    private static <T> Object serialize(T t) {
        return serialize(t, ErrorPath.EMPTY);
    }

    private static <T> Object serialize(T t, ErrorPath errorPath) {
        if (errorPath.getLength() > 500) {
            throw serializeError(errorPath, "Exceeded maximum depth of 500, which likely indicates there's an object cycle");
        } else if (t == null) {
            return null;
        } else {
            int i = 0;
            if (t instanceof Number) {
                if ((t instanceof Long) || (t instanceof Integer) || (t instanceof Double) || (t instanceof Float)) {
                    return t;
                }
                throw serializeError(errorPath, String.format("Numbers of type %s are not supported, please use an int, long, float or double", new Object[]{t.getClass().getSimpleName()}));
            } else if ((t instanceof String) || (t instanceof Boolean)) {
                return t;
            } else {
                Object key;
                if (t instanceof Character) {
                    throw serializeError(errorPath, "Characters are not supported, please use Strings");
                } else if (t instanceof Map) {
                    Map hashMap = new HashMap();
                    for (Entry entry : ((Map) t).entrySet()) {
                        key = entry.getKey();
                        if (key instanceof String) {
                            String str = (String) key;
                            hashMap.put(str, serialize(entry.getValue(), errorPath.child(str)));
                        } else {
                            throw serializeError(errorPath, "Maps with non-string keys are not supported");
                        }
                    }
                    return hashMap;
                } else if (t instanceof Collection) {
                    if (t instanceof List) {
                        List list = (List) t;
                        List arrayList = new ArrayList(list.size());
                        while (i < list.size()) {
                            key = list.get(i);
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("[");
                            stringBuilder.append(i);
                            stringBuilder.append("]");
                            arrayList.add(serialize(key, errorPath.child(stringBuilder.toString())));
                            i++;
                        }
                        return arrayList;
                    }
                    throw serializeError(errorPath, "Serializing Collections is not supported, please use Lists instead");
                } else if (t.getClass().isArray()) {
                    throw serializeError(errorPath, "Serializing Arrays is not supported, please use Lists instead");
                } else if (t instanceof Enum) {
                    String name = ((Enum) t).name();
                    try {
                        return BeanMapper.propertyName(t.getClass().getField(name));
                    } catch (NoSuchFieldException unused) {
                        return name;
                    }
                } else {
                    Object t2;
                    if (!((t2 instanceof Date) || (t2 instanceof Timestamp) || (t2 instanceof GeoPoint) || (t2 instanceof Blob) || (t2 instanceof DocumentReference) || (t2 instanceof FieldValue))) {
                        t2 = loadOrCreateBeanMapperForClass(t2.getClass()).serialize(t2, errorPath);
                    }
                    return t2;
                }
            }
        }
    }

    private static <T> T deserializeToType(Object obj, Type type, ErrorPath errorPath) {
        if (obj == null) {
            return null;
        }
        if (type instanceof ParameterizedType) {
            return deserializeToParameterizedType(obj, (ParameterizedType) type, errorPath);
        }
        if (type instanceof Class) {
            return deserializeToClass(obj, (Class) type, errorPath);
        }
        boolean z = true;
        Type[] upperBounds;
        StringBuilder stringBuilder;
        if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            if (wildcardType.getLowerBounds().length <= 0) {
                upperBounds = wildcardType.getUpperBounds();
                if (upperBounds.length <= 0) {
                    z = false;
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected type bounds on wildcard ");
                stringBuilder.append(type);
                hardAssert(z, stringBuilder.toString());
                return deserializeToType(obj, upperBounds[0], errorPath);
            }
            throw deserializeError(errorPath, "Generic lower-bounded wildcard types are not supported");
        } else if (type instanceof TypeVariable) {
            upperBounds = ((TypeVariable) type).getBounds();
            if (upperBounds.length <= 0) {
                z = false;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected type bounds on type variable ");
            stringBuilder.append(type);
            hardAssert(z, stringBuilder.toString());
            return deserializeToType(obj, upperBounds[0], errorPath);
        } else if (type instanceof GenericArrayType) {
            throw deserializeError(errorPath, "Generic Arrays are not supported, please use Lists instead");
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Unknown type encountered: ");
            stringBuilder2.append(type);
            throw deserializeError(errorPath, stringBuilder2.toString());
        }
    }

    private static <T> T deserializeToClass(Object obj, Class<T> cls, ErrorPath errorPath) {
        if (obj == null) {
            return null;
        }
        if (cls.isPrimitive() || Number.class.isAssignableFrom(cls) || Boolean.class.isAssignableFrom(cls) || Character.class.isAssignableFrom(cls)) {
            return deserializeToPrimitive(obj, cls, errorPath);
        }
        if (String.class.isAssignableFrom(cls)) {
            return convertString(obj, errorPath);
        }
        if (Date.class.isAssignableFrom(cls)) {
            return convertDate(obj, errorPath);
        }
        if (Timestamp.class.isAssignableFrom(cls)) {
            return convertTimestamp(obj, errorPath);
        }
        if (Blob.class.isAssignableFrom(cls)) {
            return convertBlob(obj, errorPath);
        }
        if (GeoPoint.class.isAssignableFrom(cls)) {
            return convertGeoPoint(obj, errorPath);
        }
        if (DocumentReference.class.isAssignableFrom(cls)) {
            return convertDocumentReference(obj, errorPath);
        }
        if (cls.isArray()) {
            throw deserializeError(errorPath, "Converting to Arrays is not supported, please use Lists instead");
        } else if (cls.getTypeParameters().length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Class ");
            stringBuilder.append(cls.getName());
            stringBuilder.append(" has generic type parameters, please use GenericTypeIndicator instead");
            throw deserializeError(errorPath, stringBuilder.toString());
        } else if (cls.equals(Object.class)) {
            return obj;
        } else {
            if (cls.isEnum()) {
                return deserializeToEnum(obj, cls, errorPath);
            }
            return convertBean(obj, cls, errorPath);
        }
    }

    private static <T> T deserializeToParameterizedType(Object obj, ParameterizedType parameterizedType, ErrorPath errorPath) {
        Class cls = (Class) parameterizedType.getRawType();
        int i = 0;
        Type type;
        T arrayList;
        Map expectMap;
        if (List.class.isAssignableFrom(cls)) {
            type = parameterizedType.getActualTypeArguments()[0];
            if (obj instanceof List) {
                List list = (List) obj;
                arrayList = new ArrayList(list.size());
                while (i < list.size()) {
                    Object obj2 = list.get(i);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("[");
                    stringBuilder.append(i);
                    stringBuilder.append("]");
                    arrayList.add(deserializeToType(obj2, type, errorPath.child(stringBuilder.toString())));
                    i++;
                }
                return arrayList;
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Expected a List, but got a ");
            stringBuilder2.append(obj.getClass());
            throw deserializeError(errorPath, stringBuilder2.toString());
        } else if (Map.class.isAssignableFrom(cls)) {
            Object obj3 = parameterizedType.getActualTypeArguments()[0];
            type = parameterizedType.getActualTypeArguments()[1];
            if (obj3.equals(String.class)) {
                expectMap = expectMap(obj, errorPath);
                arrayList = new HashMap();
                for (Entry entry : expectMap.entrySet()) {
                    arrayList.put((String) entry.getKey(), deserializeToType(entry.getValue(), type, errorPath.child((String) entry.getKey())));
                }
                return arrayList;
            }
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Only Maps with string keys are supported, but found Map with key type ");
            stringBuilder3.append(obj3);
            throw deserializeError(errorPath, stringBuilder3.toString());
        } else if (Collection.class.isAssignableFrom(cls)) {
            throw deserializeError(errorPath, "Collections are not supported, please use Lists instead");
        } else {
            expectMap = expectMap(obj, errorPath);
            BeanMapper loadOrCreateBeanMapperForClass = loadOrCreateBeanMapperForClass(cls);
            Map hashMap = new HashMap();
            TypeVariable[] typeParameters = loadOrCreateBeanMapperForClass.clazz.getTypeParameters();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length == typeParameters.length) {
                while (i < typeParameters.length) {
                    hashMap.put(typeParameters[i], actualTypeArguments[i]);
                    i++;
                }
                return loadOrCreateBeanMapperForClass.deserialize(expectMap, hashMap, errorPath);
            }
            throw new IllegalStateException("Mismatched lengths for type variables and actual types");
        }
    }

    private static <T> T deserializeToPrimitive(Object obj, Class<T> cls, ErrorPath errorPath) {
        if (Integer.class.isAssignableFrom(cls) || Integer.TYPE.isAssignableFrom(cls)) {
            return convertInteger(obj, errorPath);
        }
        if (Boolean.class.isAssignableFrom(cls) || Boolean.TYPE.isAssignableFrom(cls)) {
            return convertBoolean(obj, errorPath);
        }
        if (Double.class.isAssignableFrom(cls) || Double.TYPE.isAssignableFrom(cls)) {
            return convertDouble(obj, errorPath);
        }
        if (Long.class.isAssignableFrom(cls) || Long.TYPE.isAssignableFrom(cls)) {
            return convertLong(obj, errorPath);
        }
        if (Float.class.isAssignableFrom(cls) || Float.TYPE.isAssignableFrom(cls)) {
            return Float.valueOf(convertDouble(obj, errorPath).floatValue());
        }
        throw deserializeError(errorPath, String.format("Deserializing values to %s is not supported", new Object[]{cls.getSimpleName()}));
    }

    private static <T> T deserializeToEnum(Object obj, Class<T> cls, ErrorPath errorPath) {
        StringBuilder stringBuilder;
        if (obj instanceof String) {
            String str = (String) obj;
            for (Field field : cls.getFields()) {
                if (field.isEnumConstant() && str.equals(BeanMapper.propertyName(field))) {
                    str = field.getName();
                    break;
                }
            }
            try {
                str = Enum.valueOf(cls, str);
                return str;
            } catch (IllegalArgumentException unused) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Could not find enum value of ");
                stringBuilder.append(cls.getName());
                stringBuilder.append(" for value \"");
                stringBuilder.append(str);
                stringBuilder.append("\"");
                throw deserializeError(errorPath, stringBuilder.toString());
            }
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Expected a String while deserializing to enum ");
        stringBuilder.append(cls);
        stringBuilder.append(" but got a ");
        stringBuilder.append(obj.getClass());
        throw deserializeError(errorPath, stringBuilder.toString());
    }

    private static <T> BeanMapper<T> loadOrCreateBeanMapperForClass(Class<T> cls) {
        BeanMapper<T> beanMapper = (BeanMapper) mappers.get(cls);
        if (beanMapper != null) {
            return beanMapper;
        }
        beanMapper = new BeanMapper(cls);
        mappers.put(cls, beanMapper);
        return beanMapper;
    }

    private static Map<String, Object> expectMap(Object obj, ErrorPath errorPath) {
        if (obj instanceof Map) {
            return (Map) obj;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected a Map while deserializing, but got a ");
        stringBuilder.append(obj.getClass());
        throw deserializeError(errorPath, stringBuilder.toString());
    }

    private static Integer convertInteger(Object obj, ErrorPath errorPath) {
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        if ((obj instanceof Long) || (obj instanceof Double)) {
            Number number = (Number) obj;
            double doubleValue = number.doubleValue();
            if (doubleValue >= -2.147483648E9d && doubleValue <= 2.147483647E9d) {
                return Integer.valueOf(number.intValue());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Numeric value out of 32-bit integer range: ");
            stringBuilder.append(doubleValue);
            stringBuilder.append(". Did you mean to use a long or double instead of an int?");
            throw deserializeError(errorPath, stringBuilder.toString());
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Failed to convert a value of type ");
        stringBuilder2.append(obj.getClass().getName());
        stringBuilder2.append(" to int");
        throw deserializeError(errorPath, stringBuilder2.toString());
    }

    private static Long convertLong(Object obj, ErrorPath errorPath) {
        if (obj instanceof Integer) {
            return Long.valueOf(((Integer) obj).longValue());
        }
        if (obj instanceof Long) {
            return (Long) obj;
        }
        StringBuilder stringBuilder;
        if (obj instanceof Double) {
            Double d = (Double) obj;
            if (d.doubleValue() >= -9.223372036854776E18d && d.doubleValue() <= 9.223372036854776E18d) {
                return Long.valueOf(d.longValue());
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Numeric value out of 64-bit long range: ");
            stringBuilder.append(d);
            stringBuilder.append(". Did you mean to use a double instead of a long?");
            throw deserializeError(errorPath, stringBuilder.toString());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to convert a value of type ");
        stringBuilder.append(obj.getClass().getName());
        stringBuilder.append(" to long");
        throw deserializeError(errorPath, stringBuilder.toString());
    }

    private static Double convertDouble(Object obj, ErrorPath errorPath) {
        if (obj instanceof Integer) {
            return Double.valueOf(((Integer) obj).doubleValue());
        }
        StringBuilder stringBuilder;
        if (obj instanceof Long) {
            Long l = (Long) obj;
            Double valueOf = Double.valueOf(l.doubleValue());
            if (valueOf.longValue() == l.longValue()) {
                return valueOf;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Loss of precision while converting number to double: ");
            stringBuilder.append(obj);
            stringBuilder.append(". Did you mean to use a 64-bit long instead?");
            throw deserializeError(errorPath, stringBuilder.toString());
        } else if (obj instanceof Double) {
            return (Double) obj;
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to convert a value of type ");
            stringBuilder.append(obj.getClass().getName());
            stringBuilder.append(" to double");
            throw deserializeError(errorPath, stringBuilder.toString());
        }
    }

    private static Boolean convertBoolean(Object obj, ErrorPath errorPath) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to convert value of type ");
        stringBuilder.append(obj.getClass().getName());
        stringBuilder.append(" to boolean");
        throw deserializeError(errorPath, stringBuilder.toString());
    }

    private static String convertString(Object obj, ErrorPath errorPath) {
        if (obj instanceof String) {
            return (String) obj;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to convert value of type ");
        stringBuilder.append(obj.getClass().getName());
        stringBuilder.append(" to String");
        throw deserializeError(errorPath, stringBuilder.toString());
    }

    private static Date convertDate(Object obj, ErrorPath errorPath) {
        if (obj instanceof Date) {
            return (Date) obj;
        }
        if (obj instanceof Timestamp) {
            return ((Timestamp) obj).toDate();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to convert value of type ");
        stringBuilder.append(obj.getClass().getName());
        stringBuilder.append(" to Date");
        throw deserializeError(errorPath, stringBuilder.toString());
    }

    private static Timestamp convertTimestamp(Object obj, ErrorPath errorPath) {
        if (obj instanceof Timestamp) {
            return (Timestamp) obj;
        }
        if (obj instanceof Date) {
            return new Timestamp((Date) obj);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to convert value of type ");
        stringBuilder.append(obj.getClass().getName());
        stringBuilder.append(" to Timestamp");
        throw deserializeError(errorPath, stringBuilder.toString());
    }

    private static Blob convertBlob(Object obj, ErrorPath errorPath) {
        if (obj instanceof Blob) {
            return (Blob) obj;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to convert value of type ");
        stringBuilder.append(obj.getClass().getName());
        stringBuilder.append(" to Blob");
        throw deserializeError(errorPath, stringBuilder.toString());
    }

    private static GeoPoint convertGeoPoint(Object obj, ErrorPath errorPath) {
        if (obj instanceof GeoPoint) {
            return (GeoPoint) obj;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to convert value of type ");
        stringBuilder.append(obj.getClass().getName());
        stringBuilder.append(" to GeoPoint");
        throw deserializeError(errorPath, stringBuilder.toString());
    }

    private static DocumentReference convertDocumentReference(Object obj, ErrorPath errorPath) {
        if (obj instanceof DocumentReference) {
            return (DocumentReference) obj;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to convert value of type ");
        stringBuilder.append(obj.getClass().getName());
        stringBuilder.append(" to DocumentReference");
        throw deserializeError(errorPath, stringBuilder.toString());
    }

    private static <T> T convertBean(Object obj, Class<T> cls, ErrorPath errorPath) {
        BeanMapper loadOrCreateBeanMapperForClass = loadOrCreateBeanMapperForClass(cls);
        if (obj instanceof Map) {
            return loadOrCreateBeanMapperForClass.deserialize(expectMap(obj, errorPath), errorPath);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Can't convert object of type ");
        stringBuilder.append(obj.getClass().getName());
        stringBuilder.append(" to type ");
        stringBuilder.append(cls.getName());
        throw deserializeError(errorPath, stringBuilder.toString());
    }

    private static IllegalArgumentException serializeError(ErrorPath errorPath, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not serialize object. ");
        stringBuilder.append(str);
        str = stringBuilder.toString();
        if (errorPath.getLength() > 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(" (found in field '");
            stringBuilder.append(errorPath.toString());
            stringBuilder.append("')");
            str = stringBuilder.toString();
        }
        return new IllegalArgumentException(str);
    }

    private static RuntimeException deserializeError(ErrorPath errorPath, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not deserialize object. ");
        stringBuilder.append(str);
        str = stringBuilder.toString();
        if (errorPath.getLength() > 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(" (found in field '");
            stringBuilder.append(errorPath.toString());
            stringBuilder.append("')");
            str = stringBuilder.toString();
        }
        return new RuntimeException(str);
    }
}
