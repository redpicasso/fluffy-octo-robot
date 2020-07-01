package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import java.util.Random;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import sun.misc.Unsafe;

@GwtIncompatible
abstract class Striped64 extends Number {
    static final int NCPU = Runtime.getRuntime().availableProcessors();
    private static final Unsafe UNSAFE;
    private static final long baseOffset;
    private static final long busyOffset;
    static final Random rng = new Random();
    static final ThreadLocal<int[]> threadHashCode = new ThreadLocal();
    volatile transient long base;
    volatile transient int busy;
    @NullableDecl
    volatile transient Cell[] cells;

    static final class Cell {
        private static final Unsafe UNSAFE;
        private static final long valueOffset;
        volatile long p0;
        volatile long p1;
        volatile long p2;
        volatile long p3;
        volatile long p4;
        volatile long p5;
        volatile long p6;
        volatile long q0;
        volatile long q1;
        volatile long q2;
        volatile long q3;
        volatile long q4;
        volatile long q5;
        volatile long q6;
        volatile long value;

        Cell(long j) {
            this.value = j;
        }

        final boolean cas(long j, long j2) {
            return UNSAFE.compareAndSwapLong(this, valueOffset, j, j2);
        }

        static {
            try {
                UNSAFE = Striped64.getUnsafe();
                valueOffset = UNSAFE.objectFieldOffset(Cell.class.getDeclaredField("value"));
            } catch (Throwable e) {
                throw new Error(e);
            }
        }
    }

    abstract long fn(long j, long j2);

    static {
        try {
            UNSAFE = getUnsafe();
            Class cls = Striped64.class;
            baseOffset = UNSAFE.objectFieldOffset(cls.getDeclaredField("base"));
            busyOffset = UNSAFE.objectFieldOffset(cls.getDeclaredField("busy"));
        } catch (Throwable e) {
            throw new Error(e);
        }
    }

    Striped64() {
    }

    final boolean casBase(long j, long j2) {
        return UNSAFE.compareAndSwapLong(this, baseOffset, j, j2);
    }

    final boolean casBusy() {
        return UNSAFE.compareAndSwapInt(this, busyOffset, 0, 1);
    }

    /* JADX WARNING: Removed duplicated region for block: B:89:0x0022 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x00ec A:{SYNTHETIC} */
    /* JADX WARNING: Missing block: B:53:0x008d, code:
            if (r1.cells != r9) goto L_0x009f;
     */
    /* JADX WARNING: Missing block: B:54:0x008f, code:
            r8 = new com.google.common.cache.Striped64.Cell[(r10 << 1)];
            r11 = 0;
     */
    /* JADX WARNING: Missing block: B:55:0x0094, code:
            if (r11 >= r10) goto L_0x009d;
     */
    /* JADX WARNING: Missing block: B:56:0x0096, code:
            r8[r11] = r9[r11];
            r11 = r11 + 1;
     */
    /* JADX WARNING: Missing block: B:57:0x009d, code:
            r1.cells = r8;
     */
    final void retryUpdate(long r17, int[] r19, boolean r20) {
        /*
        r16 = this;
        r1 = r16;
        r2 = r17;
        r0 = 1;
        r4 = 0;
        if (r19 != 0) goto L_0x001b;
    L_0x0008:
        r5 = threadHashCode;
        r6 = new int[r0];
        r5.set(r6);
        r5 = rng;
        r5 = r5.nextInt();
        if (r5 != 0) goto L_0x0018;
    L_0x0017:
        r5 = 1;
    L_0x0018:
        r6[r4] = r5;
        goto L_0x001f;
    L_0x001b:
        r5 = r19[r4];
        r6 = r19;
    L_0x001f:
        r7 = r20;
    L_0x0021:
        r8 = 0;
    L_0x0022:
        r9 = r1.cells;
        if (r9 == 0) goto L_0x00b4;
    L_0x0026:
        r10 = r9.length;
        if (r10 <= 0) goto L_0x00b4;
    L_0x0029:
        r11 = r10 + -1;
        r11 = r11 & r5;
        r11 = r9[r11];
        if (r11 != 0) goto L_0x0062;
    L_0x0030:
        r9 = r1.busy;
        if (r9 != 0) goto L_0x0060;
    L_0x0034:
        r9 = new com.google.common.cache.Striped64$Cell;
        r9.<init>(r2);
        r10 = r1.busy;
        if (r10 != 0) goto L_0x0060;
    L_0x003d:
        r10 = r16.casBusy();
        if (r10 == 0) goto L_0x0060;
    L_0x0043:
        r10 = r1.cells;	 Catch:{ all -> 0x005c }
        if (r10 == 0) goto L_0x0055;
    L_0x0047:
        r11 = r10.length;	 Catch:{ all -> 0x005c }
        if (r11 <= 0) goto L_0x0055;
    L_0x004a:
        r11 = r11 + -1;
        r11 = r11 & r5;
        r12 = r10[r11];	 Catch:{ all -> 0x005c }
        if (r12 != 0) goto L_0x0055;
    L_0x0051:
        r10[r11] = r9;	 Catch:{ all -> 0x005c }
        r9 = 1;
        goto L_0x0056;
    L_0x0055:
        r9 = 0;
    L_0x0056:
        r1.busy = r4;
        if (r9 == 0) goto L_0x0022;
    L_0x005a:
        goto L_0x00ec;
    L_0x005c:
        r0 = move-exception;
        r1.busy = r4;
        throw r0;
    L_0x0060:
        r8 = 0;
        goto L_0x00a7;
    L_0x0062:
        if (r7 != 0) goto L_0x0066;
    L_0x0064:
        r7 = 1;
        goto L_0x00a7;
    L_0x0066:
        r12 = r11.value;
        r14 = r1.fn(r12, r2);
        r11 = r11.cas(r12, r14);
        if (r11 == 0) goto L_0x0074;
    L_0x0072:
        goto L_0x00ec;
    L_0x0074:
        r11 = NCPU;
        if (r10 >= r11) goto L_0x0060;
    L_0x0078:
        r11 = r1.cells;
        if (r11 == r9) goto L_0x007d;
    L_0x007c:
        goto L_0x0060;
    L_0x007d:
        if (r8 != 0) goto L_0x0081;
    L_0x007f:
        r8 = 1;
        goto L_0x00a7;
    L_0x0081:
        r11 = r1.busy;
        if (r11 != 0) goto L_0x00a7;
    L_0x0085:
        r11 = r16.casBusy();
        if (r11 == 0) goto L_0x00a7;
    L_0x008b:
        r8 = r1.cells;	 Catch:{ all -> 0x00a3 }
        if (r8 != r9) goto L_0x009f;
    L_0x008f:
        r8 = r10 << 1;
        r8 = new com.google.common.cache.Striped64.Cell[r8];	 Catch:{ all -> 0x00a3 }
        r11 = 0;
    L_0x0094:
        if (r11 >= r10) goto L_0x009d;
    L_0x0096:
        r12 = r9[r11];	 Catch:{ all -> 0x00a3 }
        r8[r11] = r12;	 Catch:{ all -> 0x00a3 }
        r11 = r11 + 1;
        goto L_0x0094;
    L_0x009d:
        r1.cells = r8;	 Catch:{ all -> 0x00a3 }
    L_0x009f:
        r1.busy = r4;
        goto L_0x0021;
    L_0x00a3:
        r0 = move-exception;
        r1.busy = r4;
        throw r0;
    L_0x00a7:
        r9 = r5 << 13;
        r5 = r5 ^ r9;
        r9 = r5 >>> 17;
        r5 = r5 ^ r9;
        r9 = r5 << 5;
        r5 = r5 ^ r9;
        r6[r4] = r5;
        goto L_0x0022;
    L_0x00b4:
        r10 = r1.busy;
        if (r10 != 0) goto L_0x00e0;
    L_0x00b8:
        r10 = r1.cells;
        if (r10 != r9) goto L_0x00e0;
    L_0x00bc:
        r10 = r16.casBusy();
        if (r10 == 0) goto L_0x00e0;
    L_0x00c2:
        r10 = r1.cells;	 Catch:{ all -> 0x00dc }
        if (r10 != r9) goto L_0x00d6;
    L_0x00c6:
        r9 = 2;
        r9 = new com.google.common.cache.Striped64.Cell[r9];	 Catch:{ all -> 0x00dc }
        r10 = r5 & 1;
        r11 = new com.google.common.cache.Striped64$Cell;	 Catch:{ all -> 0x00dc }
        r11.<init>(r2);	 Catch:{ all -> 0x00dc }
        r9[r10] = r11;	 Catch:{ all -> 0x00dc }
        r1.cells = r9;	 Catch:{ all -> 0x00dc }
        r9 = 1;
        goto L_0x00d7;
    L_0x00d6:
        r9 = 0;
    L_0x00d7:
        r1.busy = r4;
        if (r9 == 0) goto L_0x0022;
    L_0x00db:
        goto L_0x00ec;
    L_0x00dc:
        r0 = move-exception;
        r1.busy = r4;
        throw r0;
    L_0x00e0:
        r9 = r1.base;
        r11 = r1.fn(r9, r2);
        r9 = r1.casBase(r9, r11);
        if (r9 == 0) goto L_0x0022;
    L_0x00ec:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.Striped64.retryUpdate(long, int[], boolean):void");
    }

    final void internalReset(long j) {
        Cell[] cellArr = this.cells;
        this.base = j;
        if (cellArr != null) {
            for (Cell cell : cellArr) {
                if (cell != null) {
                    cell.value = j;
                }
            }
        }
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:5:0x0010, code:
            return (sun.misc.Unsafe) java.security.AccessController.doPrivileged(new com.google.common.cache.Striped64.AnonymousClass1());
     */
    /* JADX WARNING: Missing block: B:6:0x0011, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:8:0x001d, code:
            throw new java.lang.RuntimeException("Could not initialize intrinsics", r0.getCause());
     */
    private static sun.misc.Unsafe getUnsafe() {
        /*
        r0 = sun.misc.Unsafe.getUnsafe();	 Catch:{ SecurityException -> 0x0005 }
        return r0;
    L_0x0005:
        r0 = new com.google.common.cache.Striped64$1;	 Catch:{ PrivilegedActionException -> 0x0011 }
        r0.<init>();	 Catch:{ PrivilegedActionException -> 0x0011 }
        r0 = java.security.AccessController.doPrivileged(r0);	 Catch:{ PrivilegedActionException -> 0x0011 }
        r0 = (sun.misc.Unsafe) r0;	 Catch:{ PrivilegedActionException -> 0x0011 }
        return r0;
    L_0x0011:
        r0 = move-exception;
        r1 = new java.lang.RuntimeException;
        r0 = r0.getCause();
        r2 = "Could not initialize intrinsics";
        r1.<init>(r2, r0);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.Striped64.getUnsafe():sun.misc.Unsafe");
    }
}
