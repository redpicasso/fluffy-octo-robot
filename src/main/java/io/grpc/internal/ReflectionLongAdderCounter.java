package io.grpc.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public final class ReflectionLongAdderCounter implements LongCounter {
    private static final Method addMethod;
    private static final Constructor<?> defaultConstructor;
    private static final RuntimeException initializationException;
    private static final Logger logger = Logger.getLogger(ReflectionLongAdderCounter.class.getName());
    private static final Method sumMethod;
    private final Object instance;

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0054 A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0054 A:{SKIP} */
    static {
        /*
        r0 = io.grpc.internal.ReflectionLongAdderCounter.class;
        r0 = r0.getName();
        r0 = java.util.logging.Logger.getLogger(r0);
        logger = r0;
        r0 = 0;
        r1 = "java.util.concurrent.atomic.LongAdder";
        r1 = java.lang.Class.forName(r1);	 Catch:{ Throwable -> 0x0045 }
        r2 = "add";
        r3 = 1;
        r3 = new java.lang.Class[r3];	 Catch:{ Throwable -> 0x0045 }
        r4 = java.lang.Long.TYPE;	 Catch:{ Throwable -> 0x0045 }
        r5 = 0;
        r3[r5] = r4;	 Catch:{ Throwable -> 0x0045 }
        r2 = r1.getMethod(r2, r3);	 Catch:{ Throwable -> 0x0045 }
        r3 = "sum";
        r4 = new java.lang.Class[r5];	 Catch:{ Throwable -> 0x0042 }
        r3 = r1.getMethod(r3, r4);	 Catch:{ Throwable -> 0x0042 }
        r1 = r1.getConstructors();	 Catch:{ Throwable -> 0x0040 }
        r4 = r1.length;	 Catch:{ Throwable -> 0x0040 }
    L_0x002e:
        if (r5 >= r4) goto L_0x003d;
    L_0x0030:
        r6 = r1[r5];	 Catch:{ Throwable -> 0x0040 }
        r7 = r6.getParameterTypes();	 Catch:{ Throwable -> 0x0040 }
        r7 = r7.length;	 Catch:{ Throwable -> 0x0040 }
        if (r7 != 0) goto L_0x003a;
    L_0x0039:
        goto L_0x003e;
    L_0x003a:
        r5 = r5 + 1;
        goto L_0x002e;
    L_0x003d:
        r6 = r0;
    L_0x003e:
        r1 = r0;
        goto L_0x0052;
    L_0x0040:
        r1 = move-exception;
        goto L_0x0048;
    L_0x0042:
        r1 = move-exception;
        r3 = r0;
        goto L_0x0048;
    L_0x0045:
        r1 = move-exception;
        r2 = r0;
        r3 = r2;
    L_0x0048:
        r4 = logger;
        r5 = java.util.logging.Level.FINE;
        r6 = "LongAdder can not be found via reflection, this is normal for JDK7 and below";
        r4.log(r5, r6, r1);
        r6 = r0;
    L_0x0052:
        if (r1 != 0) goto L_0x005f;
    L_0x0054:
        if (r6 == 0) goto L_0x005f;
    L_0x0056:
        defaultConstructor = r6;
        addMethod = r2;
        sumMethod = r3;
        initializationException = r0;
        goto L_0x006c;
    L_0x005f:
        defaultConstructor = r0;
        addMethod = r0;
        sumMethod = r0;
        r0 = new java.lang.RuntimeException;
        r0.<init>(r1);
        initializationException = r0;
    L_0x006c:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.ReflectionLongAdderCounter.<clinit>():void");
    }

    ReflectionLongAdderCounter() {
        RuntimeException runtimeException = initializationException;
        if (runtimeException == null) {
            try {
                this.instance = defaultConstructor.newInstance(new Object[0]);
                return;
            } catch (Throwable e) {
                throw new RuntimeException(e);
            } catch (Throwable e2) {
                throw new RuntimeException(e2);
            } catch (Throwable e22) {
                throw new RuntimeException(e22);
            }
        }
        throw runtimeException;
    }

    static boolean isAvailable() {
        return initializationException == null;
    }

    public void add(long j) {
        try {
            addMethod.invoke(this.instance, new Object[]{Long.valueOf(j)});
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    public long value() {
        try {
            return ((Long) sumMethod.invoke(this.instance, new Object[0])).longValue();
        } catch (IllegalAccessException unused) {
            throw new RuntimeException();
        } catch (InvocationTargetException unused2) {
            throw new RuntimeException();
        }
    }
}
