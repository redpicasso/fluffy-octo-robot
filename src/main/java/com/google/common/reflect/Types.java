package com.google.common.reflect;

import com.adobe.xmp.XMPConst;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.security.AccessControlException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Types {
    private static final Joiner COMMA_JOINER = Joiner.on(", ").useForNull("null");
    private static final Function<Type, String> TYPE_NAME = new Function<Type, String>() {
        public String apply(Type type) {
            return JavaVersion.CURRENT.typeName(type);
        }
    };

    private enum ClassOwnership {
        OWNED_BY_ENCLOSING_CLASS {
            @NullableDecl
            Class<?> getOwnerType(Class<?> cls) {
                return cls.getEnclosingClass();
            }
        },
        LOCAL_CLASS_HAS_NO_OWNER {
            @NullableDecl
            Class<?> getOwnerType(Class<?> cls) {
                if (cls.isLocalClass()) {
                    return null;
                }
                return cls.getEnclosingClass();
            }
        };
        
        static final ClassOwnership JVM_BEHAVIOR = null;

        @NullableDecl
        abstract Class<?> getOwnerType(Class<?> cls);

        static {
            JVM_BEHAVIOR = detectJvmBehavior();
        }

        private static ClassOwnership detectJvmBehavior() {
            ParameterizedType parameterizedType = (ParameterizedType) new AnonymousClass1LocalClass<String>() {
            }.getClass().getGenericSuperclass();
            for (ClassOwnership classOwnership : values()) {
                if (classOwnership.getOwnerType(AnonymousClass1LocalClass.class) == parameterizedType.getOwnerType()) {
                    return classOwnership;
                }
            }
            throw new AssertionError();
        }
    }

    private static final class GenericArrayTypeImpl implements GenericArrayType, Serializable {
        private static final long serialVersionUID = 0;
        private final Type componentType;

        GenericArrayTypeImpl(Type type) {
            this.componentType = JavaVersion.CURRENT.usedInGenericType(type);
        }

        public Type getGenericComponentType() {
            return this.componentType;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Types.toString(this.componentType));
            stringBuilder.append(XMPConst.ARRAY_ITEM_NAME);
            return stringBuilder.toString();
        }

        public int hashCode() {
            return this.componentType.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof GenericArrayType)) {
                return false;
            }
            return Objects.equal(getGenericComponentType(), ((GenericArrayType) obj).getGenericComponentType());
        }
    }

    enum JavaVersion {
        JAVA6 {
            GenericArrayType newArrayType(Type type) {
                return new GenericArrayTypeImpl(type);
            }

            Type usedInGenericType(Type type) {
                Preconditions.checkNotNull(type);
                if (!(type instanceof Class)) {
                    return type;
                }
                Class cls = (Class) type;
                return cls.isArray() ? new GenericArrayTypeImpl(cls.getComponentType()) : type;
            }
        },
        JAVA7 {
            Type newArrayType(Type type) {
                if (type instanceof Class) {
                    return Types.getArrayClass((Class) type);
                }
                return new GenericArrayTypeImpl(type);
            }

            Type usedInGenericType(Type type) {
                return (Type) Preconditions.checkNotNull(type);
            }
        },
        JAVA8 {
            Type newArrayType(Type type) {
                return JAVA7.newArrayType(type);
            }

            Type usedInGenericType(Type type) {
                return JAVA7.usedInGenericType(type);
            }

            String typeName(Type type) {
                Throwable e;
                try {
                    return (String) Type.class.getMethod("getTypeName", new Class[0]).invoke(type, new Object[0]);
                } catch (NoSuchMethodException unused) {
                    throw new AssertionError("Type.getTypeName should be available in Java 8");
                } catch (InvocationTargetException e2) {
                    e = e2;
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e3) {
                    e = e3;
                    throw new RuntimeException(e);
                }
            }
        },
        JAVA9 {
            boolean jdkTypeDuplicatesOwnerName() {
                return false;
            }

            Type newArrayType(Type type) {
                return JAVA8.newArrayType(type);
            }

            Type usedInGenericType(Type type) {
                return JAVA8.usedInGenericType(type);
            }

            String typeName(Type type) {
                return JAVA8.typeName(type);
            }
        };
        
        static final JavaVersion CURRENT = null;

        boolean jdkTypeDuplicatesOwnerName() {
            return true;
        }

        abstract Type newArrayType(Type type);

        abstract Type usedInGenericType(Type type);

        final ImmutableList<Type> usedInGenericType(Type[] typeArr) {
            Builder builder = ImmutableList.builder();
            for (Type usedInGenericType : typeArr) {
                builder.add(usedInGenericType(usedInGenericType));
            }
            return builder.build();
        }

        String typeName(Type type) {
            return Types.toString(type);
        }
    }

    static final class NativeTypeVariableEquals<X> {
        static final boolean NATIVE_TYPE_VARIABLE_ONLY = (NativeTypeVariableEquals.class.getTypeParameters()[0].equals(Types.newArtificialTypeVariable(NativeTypeVariableEquals.class, "X", new Type[0])) ^ 1);

        NativeTypeVariableEquals() {
        }
    }

    private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable {
        private static final long serialVersionUID = 0;
        private final ImmutableList<Type> argumentsList;
        @NullableDecl
        private final Type ownerType;
        private final Class<?> rawType;

        ParameterizedTypeImpl(@NullableDecl Type type, Class<?> cls, Type[] typeArr) {
            Preconditions.checkNotNull(cls);
            Preconditions.checkArgument(typeArr.length == cls.getTypeParameters().length);
            Types.disallowPrimitiveType(typeArr, "type parameter");
            this.ownerType = type;
            this.rawType = cls;
            this.argumentsList = JavaVersion.CURRENT.usedInGenericType(typeArr);
        }

        public Type[] getActualTypeArguments() {
            return Types.toArray(this.argumentsList);
        }

        public Type getRawType() {
            return this.rawType;
        }

        public Type getOwnerType() {
            return this.ownerType;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            if (this.ownerType != null && JavaVersion.CURRENT.jdkTypeDuplicatesOwnerName()) {
                stringBuilder.append(JavaVersion.CURRENT.typeName(this.ownerType));
                stringBuilder.append('.');
            }
            stringBuilder.append(this.rawType.getName());
            stringBuilder.append('<');
            stringBuilder.append(Types.COMMA_JOINER.join(Iterables.transform(this.argumentsList, Types.TYPE_NAME)));
            stringBuilder.append('>');
            return stringBuilder.toString();
        }

        public int hashCode() {
            Type type = this.ownerType;
            return ((type == null ? 0 : type.hashCode()) ^ this.argumentsList.hashCode()) ^ this.rawType.hashCode();
        }

        public boolean equals(Object obj) {
            boolean z = false;
            if (!(obj instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType parameterizedType = (ParameterizedType) obj;
            if (getRawType().equals(parameterizedType.getRawType()) && Objects.equal(getOwnerType(), parameterizedType.getOwnerType()) && Arrays.equals(getActualTypeArguments(), parameterizedType.getActualTypeArguments())) {
                z = true;
            }
            return z;
        }
    }

    private static final class TypeVariableImpl<D extends GenericDeclaration> {
        private final ImmutableList<Type> bounds;
        private final D genericDeclaration;
        private final String name;

        TypeVariableImpl(D d, String str, Type[] typeArr) {
            Types.disallowPrimitiveType(typeArr, "bound for type variable");
            this.genericDeclaration = (GenericDeclaration) Preconditions.checkNotNull(d);
            this.name = (String) Preconditions.checkNotNull(str);
            this.bounds = ImmutableList.copyOf((Object[]) typeArr);
        }

        public Type[] getBounds() {
            return Types.toArray(this.bounds);
        }

        public D getGenericDeclaration() {
            return this.genericDeclaration;
        }

        public String getName() {
            return this.name;
        }

        public String getTypeName() {
            return this.name;
        }

        public String toString() {
            return this.name;
        }

        public int hashCode() {
            return this.genericDeclaration.hashCode() ^ this.name.hashCode();
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (NativeTypeVariableEquals.NATIVE_TYPE_VARIABLE_ONLY) {
                if (obj == null || !Proxy.isProxyClass(obj.getClass()) || !(Proxy.getInvocationHandler(obj) instanceof TypeVariableInvocationHandler)) {
                    return false;
                }
                TypeVariableImpl access$600 = ((TypeVariableInvocationHandler) Proxy.getInvocationHandler(obj)).typeVariableImpl;
                if (!(this.name.equals(access$600.getName()) && this.genericDeclaration.equals(access$600.getGenericDeclaration()) && this.bounds.equals(access$600.bounds))) {
                    z = false;
                }
                return z;
            } else if (!(obj instanceof TypeVariable)) {
                return false;
            } else {
                TypeVariable typeVariable = (TypeVariable) obj;
                if (!(this.name.equals(typeVariable.getName()) && this.genericDeclaration.equals(typeVariable.getGenericDeclaration()))) {
                    z = false;
                }
                return z;
            }
        }
    }

    private static final class TypeVariableInvocationHandler implements InvocationHandler {
        private static final ImmutableMap<String, Method> typeVariableMethods;
        private final TypeVariableImpl<?> typeVariableImpl;

        static {
            ImmutableMap.Builder builder = ImmutableMap.builder();
            Method[] methods = TypeVariableImpl.class.getMethods();
            int length = methods.length;
            int i = 0;
            while (i < length) {
                Method method = methods[i];
                if (method.getDeclaringClass().equals(TypeVariableImpl.class)) {
                    try {
                        method.setAccessible(true);
                    } catch (AccessControlException unused) {
                        builder.put(method.getName(), method);
                    }
                } else {
                    i++;
                }
            }
            typeVariableMethods = builder.build();
        }

        TypeVariableInvocationHandler(TypeVariableImpl<?> typeVariableImpl) {
            this.typeVariableImpl = typeVariableImpl;
        }

        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            String name = method.getName();
            method = (Method) typeVariableMethods.get(name);
            if (method != null) {
                try {
                    return method.invoke(this.typeVariableImpl, objArr);
                } catch (InvocationTargetException e) {
                    throw e.getCause();
                }
            }
            throw new UnsupportedOperationException(name);
        }
    }

    static final class WildcardTypeImpl implements WildcardType, Serializable {
        private static final long serialVersionUID = 0;
        private final ImmutableList<Type> lowerBounds;
        private final ImmutableList<Type> upperBounds;

        WildcardTypeImpl(Type[] typeArr, Type[] typeArr2) {
            Types.disallowPrimitiveType(typeArr, "lower bound for wildcard");
            Types.disallowPrimitiveType(typeArr2, "upper bound for wildcard");
            this.lowerBounds = JavaVersion.CURRENT.usedInGenericType(typeArr);
            this.upperBounds = JavaVersion.CURRENT.usedInGenericType(typeArr2);
        }

        public Type[] getLowerBounds() {
            return Types.toArray(this.lowerBounds);
        }

        public Type[] getUpperBounds() {
            return Types.toArray(this.upperBounds);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof WildcardType)) {
                return false;
            }
            WildcardType wildcardType = (WildcardType) obj;
            if (this.lowerBounds.equals(Arrays.asList(wildcardType.getLowerBounds())) && this.upperBounds.equals(Arrays.asList(wildcardType.getUpperBounds()))) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.lowerBounds.hashCode() ^ this.upperBounds.hashCode();
        }

        public String toString() {
            Type type;
            StringBuilder stringBuilder = new StringBuilder("?");
            Iterator it = this.lowerBounds.iterator();
            while (it.hasNext()) {
                type = (Type) it.next();
                stringBuilder.append(" super ");
                stringBuilder.append(JavaVersion.CURRENT.typeName(type));
            }
            for (Type type2 : Types.filterUpperBounds(this.upperBounds)) {
                stringBuilder.append(" extends ");
                stringBuilder.append(JavaVersion.CURRENT.typeName(type2));
            }
            return stringBuilder.toString();
        }
    }

    static Type newArrayType(Type type) {
        if (!(type instanceof WildcardType)) {
            return JavaVersion.CURRENT.newArrayType(type);
        }
        WildcardType wildcardType = (WildcardType) type;
        Type[] lowerBounds = wildcardType.getLowerBounds();
        boolean z = true;
        Preconditions.checkArgument(lowerBounds.length <= 1, "Wildcard cannot have more than one lower bounds.");
        if (lowerBounds.length == 1) {
            return supertypeOf(newArrayType(lowerBounds[0]));
        }
        Type[] upperBounds = wildcardType.getUpperBounds();
        if (upperBounds.length != 1) {
            z = false;
        }
        Preconditions.checkArgument(z, "Wildcard should have only one upper bound.");
        return subtypeOf(newArrayType(upperBounds[0]));
    }

    static ParameterizedType newParameterizedTypeWithOwner(@NullableDecl Type type, Class<?> cls, Type... typeArr) {
        if (type == null) {
            return newParameterizedType(cls, typeArr);
        }
        Preconditions.checkNotNull(typeArr);
        Preconditions.checkArgument(cls.getEnclosingClass() != null, "Owner type for unenclosed %s", (Object) cls);
        return new ParameterizedTypeImpl(type, cls, typeArr);
    }

    static ParameterizedType newParameterizedType(Class<?> cls, Type... typeArr) {
        return new ParameterizedTypeImpl(ClassOwnership.JVM_BEHAVIOR.getOwnerType(cls), cls, typeArr);
    }

    static <D extends GenericDeclaration> TypeVariable<D> newArtificialTypeVariable(D d, String str, Type... typeArr) {
        if (typeArr.length == 0) {
            typeArr = new Type[]{Object.class};
        }
        return newTypeVariableImpl(d, str, typeArr);
    }

    @VisibleForTesting
    static WildcardType subtypeOf(Type type) {
        return new WildcardTypeImpl(new Type[0], new Type[]{type});
    }

    @VisibleForTesting
    static WildcardType supertypeOf(Type type) {
        return new WildcardTypeImpl(new Type[]{type}, new Type[]{Object.class});
    }

    static String toString(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    @NullableDecl
    static Type getComponentType(Type type) {
        Preconditions.checkNotNull(type);
        final AtomicReference atomicReference = new AtomicReference();
        new TypeVisitor() {
            void visitTypeVariable(TypeVariable<?> typeVariable) {
                atomicReference.set(Types.subtypeOfComponentType(typeVariable.getBounds()));
            }

            void visitWildcardType(WildcardType wildcardType) {
                atomicReference.set(Types.subtypeOfComponentType(wildcardType.getUpperBounds()));
            }

            void visitGenericArrayType(GenericArrayType genericArrayType) {
                atomicReference.set(genericArrayType.getGenericComponentType());
            }

            void visitClass(Class<?> cls) {
                atomicReference.set(cls.getComponentType());
            }
        }.visit(type);
        return (Type) atomicReference.get();
    }

    @NullableDecl
    private static Type subtypeOfComponentType(Type[] typeArr) {
        for (Type componentType : typeArr) {
            Type componentType2 = getComponentType(componentType2);
            if (componentType2 != null) {
                if (componentType2 instanceof Class) {
                    Class cls = (Class) componentType2;
                    if (cls.isPrimitive()) {
                        return cls;
                    }
                }
                return subtypeOf(componentType2);
            }
        }
        return null;
    }

    private static <D extends GenericDeclaration> TypeVariable<D> newTypeVariableImpl(D d, String str, Type[] typeArr) {
        return (TypeVariable) Reflection.newProxy(TypeVariable.class, new TypeVariableInvocationHandler(new TypeVariableImpl(d, str, typeArr)));
    }

    private static Type[] toArray(Collection<Type> collection) {
        return (Type[]) collection.toArray(new Type[collection.size()]);
    }

    private static Iterable<Type> filterUpperBounds(Iterable<Type> iterable) {
        return Iterables.filter((Iterable) iterable, Predicates.not(Predicates.equalTo(Object.class)));
    }

    private static void disallowPrimitiveType(Type[] typeArr, String str) {
        for (Type type : typeArr) {
            if (type instanceof Class) {
                Object obj = (Class) type;
                Preconditions.checkArgument(obj.isPrimitive() ^ 1, "Primitive type '%s' used as %s", obj, (Object) str);
            }
        }
    }

    static Class<?> getArrayClass(Class<?> cls) {
        return Array.newInstance(cls, 0).getClass();
    }

    private Types() {
    }
}
