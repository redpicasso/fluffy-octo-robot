package io.grpc.okhttp.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OptionalMethod<T> {
    private final String methodName;
    private final Class[] methodParams;
    private final Class<?> returnType;

    public OptionalMethod(Class<?> cls, String str, Class... clsArr) {
        this.returnType = cls;
        this.methodName = str;
        this.methodParams = clsArr;
    }

    public boolean isSupported(T t) {
        return getMethod(t.getClass()) != null;
    }

    public Object invokeOptional(T t, Object... objArr) throws InvocationTargetException {
        Method method = getMethod(t.getClass());
        if (method == null) {
            return null;
        }
        try {
            return method.invoke(t, objArr);
        } catch (IllegalAccessException unused) {
            return null;
        }
    }

    public Object invokeOptionalWithoutCheckedException(T t, Object... objArr) {
        try {
            return invokeOptional(t, objArr);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw ((RuntimeException) targetException);
            }
            AssertionError assertionError = new AssertionError("Unexpected exception");
            assertionError.initCause(targetException);
            throw assertionError;
        }
    }

    public Object invoke(T t, Object... objArr) throws InvocationTargetException {
        Method method = getMethod(t.getClass());
        if (method != null) {
            try {
                return method.invoke(t, objArr);
            } catch (Throwable e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpectedly could not call: ");
                stringBuilder.append(method);
                AssertionError assertionError = new AssertionError(stringBuilder.toString());
                assertionError.initCause(e);
                throw assertionError;
            }
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Method ");
        stringBuilder2.append(this.methodName);
        stringBuilder2.append(" not supported for object ");
        stringBuilder2.append(t);
        throw new AssertionError(stringBuilder2.toString());
    }

    public Object invokeWithoutCheckedException(T t, Object... objArr) {
        try {
            return invoke(t, objArr);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw ((RuntimeException) targetException);
            }
            AssertionError assertionError = new AssertionError("Unexpected exception");
            assertionError.initCause(targetException);
            throw assertionError;
        }
    }

    private Method getMethod(Class<?> cls) {
        String str = this.methodName;
        if (str == null) {
            return null;
        }
        Method publicMethod = getPublicMethod(cls, str, this.methodParams);
        if (publicMethod != null) {
            Class cls2 = this.returnType;
            if (!(cls2 == null || cls2.isAssignableFrom(publicMethod.getReturnType()))) {
                return null;
            }
        }
        return publicMethod;
    }

    /* JADX WARNING: Missing block: B:12:0x001f, code:
            if ((r2.getModifiers() & 1) == 0) goto L_0x0021;
     */
    private static java.lang.reflect.Method getPublicMethod(java.lang.Class<?> r2, java.lang.String r3, java.lang.Class[] r4) {
        /*
        r0 = 0;
        if (r2 != 0) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = r2.getModifiers();	 Catch:{ NoSuchMethodException -> 0x0021 }
        r1 = r1 & 1;
        if (r1 != 0) goto L_0x0015;
    L_0x000c:
        r2 = r2.getSuperclass();	 Catch:{ NoSuchMethodException -> 0x0021 }
        r2 = getPublicMethod(r2, r3, r4);	 Catch:{ NoSuchMethodException -> 0x0021 }
        return r2;
    L_0x0015:
        r2 = r2.getMethod(r3, r4);	 Catch:{ NoSuchMethodException -> 0x0021 }
        r3 = r2.getModifiers();	 Catch:{ NoSuchMethodException -> 0x0022 }
        r3 = r3 & 1;
        if (r3 != 0) goto L_0x0022;
    L_0x0021:
        r2 = r0;
    L_0x0022:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.okhttp.internal.OptionalMethod.getPublicMethod(java.lang.Class, java.lang.String, java.lang.Class[]):java.lang.reflect.Method");
    }
}
