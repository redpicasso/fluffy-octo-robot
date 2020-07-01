package com.squareup.okhttp.internal.http;

import java.io.IOException;
import java.lang.reflect.Method;

public final class RouteException extends Exception {
    private static final Method addSuppressedExceptionMethod;
    private IOException lastException;

    static {
        Method declaredMethod;
        try {
            declaredMethod = Throwable.class.getDeclaredMethod("addSuppressed", new Class[]{Throwable.class});
        } catch (Exception unused) {
            declaredMethod = null;
        }
        addSuppressedExceptionMethod = declaredMethod;
    }

    public RouteException(IOException iOException) {
        super(iOException);
        this.lastException = iOException;
    }

    public IOException getLastConnectException() {
        return this.lastException;
    }

    public void addConnectException(IOException iOException) {
        addSuppressedIfPossible(iOException, this.lastException);
        this.lastException = iOException;
    }

    /* JADX WARNING: Removed duplicated region for block: B:5:0x000d A:{RETURN, ExcHandler: java.lang.reflect.InvocationTargetException (unused java.lang.reflect.InvocationTargetException), Splitter: B:3:0x0005} */
    /* JADX WARNING: Missing block: B:5:0x000d, code:
            return;
     */
    private void addSuppressedIfPossible(java.io.IOException r4, java.io.IOException r5) {
        /*
        r3 = this;
        r0 = addSuppressedExceptionMethod;
        if (r0 == 0) goto L_0x000d;
    L_0x0004:
        r1 = 1;
        r1 = new java.lang.Object[r1];	 Catch:{ InvocationTargetException -> 0x000d, InvocationTargetException -> 0x000d }
        r2 = 0;
        r1[r2] = r5;	 Catch:{ InvocationTargetException -> 0x000d, InvocationTargetException -> 0x000d }
        r0.invoke(r4, r1);	 Catch:{ InvocationTargetException -> 0x000d, InvocationTargetException -> 0x000d }
    L_0x000d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.RouteException.addSuppressedIfPossible(java.io.IOException, java.io.IOException):void");
    }
}
