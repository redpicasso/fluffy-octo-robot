package com.google.common.base.internal;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class Finalizer implements Runnable {
    private static final String FINALIZABLE_REFERENCE = "com.google.common.base.FinalizableReference";
    @NullableDecl
    private static final Constructor<Thread> bigThreadConstructor = getBigThreadConstructor();
    @NullableDecl
    private static final Field inheritableThreadLocals = (bigThreadConstructor == null ? getInheritableThreadLocalsField() : null);
    private static final Logger logger = Logger.getLogger(Finalizer.class.getName());
    private final WeakReference<Class<?>> finalizableReferenceClassReference;
    private final PhantomReference<Object> frqReference;
    private final ReferenceQueue<Object> queue;

    /* JADX WARNING: Removed duplicated region for block: B:11:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x005e A:{Catch:{ Throwable -> 0x0064 }} */
    public static void startFinalizer(java.lang.Class<?> r7, java.lang.ref.ReferenceQueue<java.lang.Object> r8, java.lang.ref.PhantomReference<java.lang.Object> r9) {
        /*
        r0 = r7.getName();
        r1 = "com.google.common.base.FinalizableReference";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0072;
    L_0x000c:
        r0 = new com.google.common.base.internal.Finalizer;
        r0.<init>(r7, r8, r9);
        r7 = com.google.common.base.internal.Finalizer.class;
        r7 = r7.getName();
        r8 = bigThreadConstructor;
        r9 = 1;
        r1 = 0;
        if (r8 == 0) goto L_0x004c;
    L_0x001d:
        r2 = 0;
        r4 = 5;
        r4 = new java.lang.Object[r4];	 Catch:{ Throwable -> 0x0042 }
        r5 = r1;
        r5 = (java.lang.ThreadGroup) r5;	 Catch:{ Throwable -> 0x0042 }
        r6 = 0;
        r4[r6] = r5;	 Catch:{ Throwable -> 0x0042 }
        r4[r9] = r0;	 Catch:{ Throwable -> 0x0042 }
        r5 = 2;
        r4[r5] = r7;	 Catch:{ Throwable -> 0x0042 }
        r5 = 3;
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ Throwable -> 0x0042 }
        r4[r5] = r2;	 Catch:{ Throwable -> 0x0042 }
        r2 = 4;
        r3 = java.lang.Boolean.valueOf(r6);	 Catch:{ Throwable -> 0x0042 }
        r4[r2] = r3;	 Catch:{ Throwable -> 0x0042 }
        r8 = r8.newInstance(r4);	 Catch:{ Throwable -> 0x0042 }
        r8 = (java.lang.Thread) r8;	 Catch:{ Throwable -> 0x0042 }
        goto L_0x004d;
    L_0x0042:
        r8 = move-exception;
        r2 = logger;
        r3 = java.util.logging.Level.INFO;
        r4 = "Failed to create a thread without inherited thread-local values";
        r2.log(r3, r4, r8);
    L_0x004c:
        r8 = r1;
    L_0x004d:
        if (r8 != 0) goto L_0x0057;
    L_0x004f:
        r8 = new java.lang.Thread;
        r2 = r1;
        r2 = (java.lang.ThreadGroup) r2;
        r8.<init>(r2, r0, r7);
    L_0x0057:
        r8.setDaemon(r9);
        r7 = inheritableThreadLocals;	 Catch:{ Throwable -> 0x0064 }
        if (r7 == 0) goto L_0x006e;
    L_0x005e:
        r7 = inheritableThreadLocals;	 Catch:{ Throwable -> 0x0064 }
        r7.set(r8, r1);	 Catch:{ Throwable -> 0x0064 }
        goto L_0x006e;
    L_0x0064:
        r7 = move-exception;
        r9 = logger;
        r0 = java.util.logging.Level.INFO;
        r1 = "Failed to clear thread local values inherited by reference finalizer thread.";
        r9.log(r0, r1, r7);
    L_0x006e:
        r8.start();
        return;
    L_0x0072:
        r7 = new java.lang.IllegalArgumentException;
        r8 = "Expected com.google.common.base.FinalizableReference.";
        r7.<init>(r8);
        throw r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.internal.Finalizer.startFinalizer(java.lang.Class, java.lang.ref.ReferenceQueue, java.lang.ref.PhantomReference):void");
    }

    private Finalizer(Class<?> cls, ReferenceQueue<Object> referenceQueue, PhantomReference<Object> phantomReference) {
        this.queue = referenceQueue;
        this.finalizableReferenceClassReference = new WeakReference(cls);
        this.frqReference = phantomReference;
    }

    public void run() {
        while (true) {
            try {
            } catch (InterruptedException unused) {
                if (!cleanUp(this.queue.remove())) {
                    return;
                }
            }
        }
    }

    private boolean cleanUp(Reference<?> reference) {
        Method finalizeReferentMethod = getFinalizeReferentMethod();
        if (finalizeReferentMethod == null) {
            return false;
        }
        Reference reference2;
        do {
            reference2.clear();
            if (reference2 == this.frqReference) {
                return false;
            }
            try {
                finalizeReferentMethod.invoke(reference2, new Object[0]);
            } catch (Throwable th) {
                logger.log(Level.SEVERE, "Error cleaning up after reference.", th);
            }
            reference2 = this.queue.poll();
        } while (reference2 != null);
        return true;
    }

    @NullableDecl
    private Method getFinalizeReferentMethod() {
        Class cls = (Class) this.finalizableReferenceClassReference.get();
        if (cls == null) {
            return null;
        }
        try {
            return cls.getMethod("finalizeReferent", new Class[0]);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    @NullableDecl
    private static Field getInheritableThreadLocalsField() {
        try {
            Field declaredField = Thread.class.getDeclaredField("inheritableThreadLocals");
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Throwable unused) {
            logger.log(Level.INFO, "Couldn't access Thread.inheritableThreadLocals. Reference finalizer threads will inherit thread local values.");
            return null;
        }
    }

    @NullableDecl
    private static Constructor<Thread> getBigThreadConstructor() {
        try {
            return Thread.class.getConstructor(new Class[]{ThreadGroup.class, Runnable.class, String.class, Long.TYPE, Boolean.TYPE});
        } catch (Throwable unused) {
            return null;
        }
    }
}
