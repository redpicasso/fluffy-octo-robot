package com.google.android.gms.internal.firebase_ml;

import java.lang.reflect.Method;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class zzlb {
    @NullableDecl
    private static final Object zzacs;
    @NullableDecl
    private static final Method zzact;
    @NullableDecl
    private static final Method zzacu;

    @Deprecated
    public static RuntimeException zza(Throwable th) {
        zzks.checkNotNull(th);
        if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        } else if (th instanceof Error) {
            throw ((Error) th);
        } else {
            throw new RuntimeException(th);
        }
    }

    @NullableDecl
    private static Object zzih() {
        Object obj = null;
        try {
            obj = Class.forName("sun.misc.SharedSecrets", false, null).getMethod("getJavaLangAccess", new Class[0]).invoke(null, new Object[0]);
        } catch (ThreadDeath e) {
            throw e;
        } catch (Throwable unused) {
            return obj;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0023 A:{RETURN, ExcHandler: java.lang.UnsupportedOperationException (unused java.lang.UnsupportedOperationException), Splitter: B:1:0x0001} */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0023 A:{RETURN, ExcHandler: java.lang.UnsupportedOperationException (unused java.lang.UnsupportedOperationException), Splitter: B:1:0x0001} */
    /* JADX WARNING: Missing block: B:7:0x0023, code:
            return null;
     */
    @org.checkerframework.checker.nullness.compatqual.NullableDecl
    private static java.lang.reflect.Method zzii() {
        /*
        r0 = 0;
        r1 = "getStackTraceDepth";
        r2 = 1;
        r3 = new java.lang.Class[r2];	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        r4 = java.lang.Throwable.class;
        r5 = 0;
        r3[r5] = r4;	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        r1 = zza(r1, r3);	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        if (r1 != 0) goto L_0x0012;
    L_0x0011:
        return r0;
    L_0x0012:
        r3 = zzih();	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        r2 = new java.lang.Object[r2];	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        r4 = new java.lang.Throwable;	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        r4.<init>();	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        r2[r5] = r4;	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        r1.invoke(r3, r2);	 Catch:{ UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023, UnsupportedOperationException -> 0x0023 }
        return r1;
    L_0x0023:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzlb.zzii():java.lang.reflect.Method");
    }

    @NullableDecl
    private static Method zza(String str, Class<?>... clsArr) throws ThreadDeath {
        try {
            return Class.forName("sun.misc.JavaLangAccess", false, null).getMethod(str, clsArr);
        } catch (ThreadDeath e) {
            throw e;
        } catch (Throwable unused) {
            return null;
        }
    }

    static {
        Method method;
        Object zzih = zzih();
        zzacs = zzih;
        Method method2 = null;
        if (zzih == null) {
            method = null;
        } else {
            method = zza("getStackTraceElement", Throwable.class, Integer.TYPE);
        }
        zzact = method;
        if (zzacs != null) {
            method2 = zzii();
        }
        zzacu = method2;
    }
}
