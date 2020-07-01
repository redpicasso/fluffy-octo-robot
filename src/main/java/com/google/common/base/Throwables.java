package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
public final class Throwables {
    @GwtIncompatible
    private static final String JAVA_LANG_ACCESS_CLASSNAME = "sun.misc.JavaLangAccess";
    @GwtIncompatible
    @VisibleForTesting
    static final String SHARED_SECRETS_CLASSNAME = "sun.misc.SharedSecrets";
    @NullableDecl
    @GwtIncompatible
    private static final Method getStackTraceDepthMethod;
    @NullableDecl
    @GwtIncompatible
    private static final Method getStackTraceElementMethod = (jla == null ? null : getGetMethod());
    @NullableDecl
    @GwtIncompatible
    private static final Object jla = getJLA();

    private Throwables() {
    }

    @GwtIncompatible
    public static <X extends Throwable> void throwIfInstanceOf(Throwable th, Class<X> cls) throws Throwable {
        Preconditions.checkNotNull(th);
        if (cls.isInstance(th)) {
            throw ((Throwable) cls.cast(th));
        }
    }

    @GwtIncompatible
    @Deprecated
    public static <X extends Throwable> void propagateIfInstanceOf(@NullableDecl Throwable th, Class<X> cls) throws Throwable {
        if (th != null) {
            throwIfInstanceOf(th, cls);
        }
    }

    public static void throwIfUnchecked(Throwable th) {
        Preconditions.checkNotNull(th);
        if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        } else if (th instanceof Error) {
            throw ((Error) th);
        }
    }

    @GwtIncompatible
    @Deprecated
    public static void propagateIfPossible(@NullableDecl Throwable th) {
        if (th != null) {
            throwIfUnchecked(th);
        }
    }

    @GwtIncompatible
    public static <X extends Throwable> void propagateIfPossible(@NullableDecl Throwable th, Class<X> cls) throws Throwable {
        propagateIfInstanceOf(th, cls);
        propagateIfPossible(th);
    }

    @GwtIncompatible
    public static <X1 extends Throwable, X2 extends Throwable> void propagateIfPossible(@NullableDecl Throwable th, Class<X1> cls, Class<X2> cls2) throws Throwable, Throwable {
        Preconditions.checkNotNull(cls2);
        propagateIfInstanceOf(th, cls);
        propagateIfPossible(th, cls2);
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    @Deprecated
    public static RuntimeException propagate(Throwable th) {
        throwIfUnchecked(th);
        throw new RuntimeException(th);
    }

    public static Throwable getRootCause(Throwable th) {
        int i = 0;
        Throwable th2 = th;
        while (true) {
            Throwable cause = th.getCause();
            if (cause == null) {
                return th;
            }
            if (cause != th2) {
                if (i != 0) {
                    th2 = th2.getCause();
                }
                i ^= 1;
                th = cause;
            } else {
                throw new IllegalArgumentException("Loop in causal chain detected.", cause);
            }
        }
    }

    @Beta
    public static List<Throwable> getCausalChain(Throwable th) {
        Preconditions.checkNotNull(th);
        List arrayList = new ArrayList(4);
        arrayList.add(th);
        int i = 0;
        Throwable th2 = th;
        while (true) {
            th = th.getCause();
            if (th == null) {
                return Collections.unmodifiableList(arrayList);
            }
            arrayList.add(th);
            if (th != th2) {
                if (i != 0) {
                    th2 = th2.getCause();
                }
                i ^= 1;
            } else {
                throw new IllegalArgumentException("Loop in causal chain detected.", th);
            }
        }
    }

    @GwtIncompatible
    @Beta
    public static <X extends Throwable> X getCauseAs(Throwable th, Class<X> cls) {
        try {
            return (Throwable) cls.cast(th.getCause());
        } catch (ClassCastException e) {
            e.initCause(th);
            throw e;
        }
    }

    @GwtIncompatible
    public static String getStackTraceAsString(Throwable th) {
        Writer stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    @GwtIncompatible
    @Beta
    public static List<StackTraceElement> lazyStackTrace(Throwable th) {
        if (lazyStackTraceIsLazy()) {
            return jlaStackTrace(th);
        }
        return Collections.unmodifiableList(Arrays.asList(th.getStackTrace()));
    }

    @GwtIncompatible
    @Beta
    public static boolean lazyStackTraceIsLazy() {
        return (getStackTraceElementMethod == null || getStackTraceDepthMethod == null) ? false : true;
    }

    @GwtIncompatible
    private static List<StackTraceElement> jlaStackTrace(final Throwable th) {
        Preconditions.checkNotNull(th);
        return new AbstractList<StackTraceElement>() {
            public StackTraceElement get(int i) {
                return (StackTraceElement) Throwables.invokeAccessibleNonThrowingMethod(Throwables.getStackTraceElementMethod, Throwables.jla, th, Integer.valueOf(i));
            }

            public int size() {
                return ((Integer) Throwables.invokeAccessibleNonThrowingMethod(Throwables.getStackTraceDepthMethod, Throwables.jla, th)).intValue();
            }
        };
    }

    @GwtIncompatible
    private static Object invokeAccessibleNonThrowingMethod(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e2) {
            throw propagate(e2.getCause());
        }
    }

    static {
        Method method = null;
        if (jla != null) {
            method = getSizeMethod();
        }
        getStackTraceDepthMethod = method;
    }

    @NullableDecl
    @GwtIncompatible
    private static Object getJLA() {
        Object obj = null;
        try {
            obj = Class.forName(SHARED_SECRETS_CLASSNAME, false, null).getMethod("getJavaLangAccess", new Class[0]).invoke(null, new Object[0]);
        } catch (ThreadDeath e) {
            throw e;
        } catch (Throwable unused) {
            return obj;
        }
    }

    @NullableDecl
    @GwtIncompatible
    private static Method getGetMethod() {
        return getJlaMethod("getStackTraceElement", Throwable.class, Integer.TYPE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0023 A:{RETURN, ExcHandler: java.lang.UnsupportedOperationException (unused java.lang.UnsupportedOperationException), Splitter: B:1:0x0001} */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0023 A:{RETURN, ExcHandler: java.lang.UnsupportedOperationException (unused java.lang.UnsupportedOperationException), Splitter: B:1:0x0001} */
    /* JADX WARNING: Missing block: B:7:0x0023, code:
            return null;
     */
    @org.checkerframework.checker.nullness.compatqual.NullableDecl
    @com.google.common.annotations.GwtIncompatible
    private static java.lang.reflect.Method getSizeMethod() {
        /*
        r0 = 0;
        r1 = "getStackTraceDepth";
        r2 = 1;
        r3 = new java.lang.Class[r2];	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        r4 = java.lang.Throwable.class;
        r5 = 0;
        r3[r5] = r4;	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        r1 = getJlaMethod(r1, r3);	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        if (r1 != 0) goto L_0x0012;
    L_0x0011:
        return r0;
    L_0x0012:
        r3 = getJLA();	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        r2 = new java.lang.Object[r2];	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        r4 = new java.lang.Throwable;	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        r4.<init>();	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        r2[r5] = r4;	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        r1.invoke(r3, r2);	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        return r1;
    L_0x0023:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.Throwables.getSizeMethod():java.lang.reflect.Method");
    }

    @NullableDecl
    @GwtIncompatible
    private static Method getJlaMethod(String str, Class<?>... clsArr) throws ThreadDeath {
        try {
            return Class.forName(JAVA_LANG_ACCESS_CLASSNAME, false, null).getMethod(str, clsArr);
        } catch (ThreadDeath e) {
            throw e;
        } catch (Throwable unused) {
            return null;
        }
    }
}
