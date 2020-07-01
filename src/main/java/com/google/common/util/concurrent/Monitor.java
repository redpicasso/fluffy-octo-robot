package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.google.j2objc.annotations.Weak;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
@Beta
public final class Monitor {
    @GuardedBy("lock")
    private Guard activeGuards;
    private final boolean fair;
    private final ReentrantLock lock;

    @Beta
    public static abstract class Guard {
        final Condition condition;
        @Weak
        final Monitor monitor;
        @NullableDecl
        @GuardedBy("monitor.lock")
        Guard next;
        @GuardedBy("monitor.lock")
        int waiterCount = 0;

        public abstract boolean isSatisfied();

        protected Guard(Monitor monitor) {
            this.monitor = (Monitor) Preconditions.checkNotNull(monitor, "monitor");
            this.condition = monitor.lock.newCondition();
        }
    }

    public Monitor() {
        this(false);
    }

    public Monitor(boolean z) {
        this.activeGuards = null;
        this.fair = z;
        this.lock = new ReentrantLock(z);
    }

    public void enter() {
        this.lock.lock();
    }

    /* JADX WARNING: Missing block: B:11:0x0021, code:
            if (r0 == false) goto L_0x002a;
     */
    /* JADX WARNING: Missing block: B:12:0x0023, code:
            java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Missing block: B:13:0x002a, code:
            return r8;
     */
    public boolean enter(long r8, java.util.concurrent.TimeUnit r10) {
        /*
        r7 = this;
        r8 = toSafeNanos(r8, r10);
        r10 = r7.lock;
        r0 = r7.fair;
        r1 = 1;
        if (r0 != 0) goto L_0x0012;
    L_0x000b:
        r0 = r10.tryLock();
        if (r0 == 0) goto L_0x0012;
    L_0x0011:
        return r1;
    L_0x0012:
        r0 = java.lang.Thread.interrupted();
        r2 = java.lang.System.nanoTime();	 Catch:{ all -> 0x0034 }
        r4 = r8;
    L_0x001b:
        r6 = java.util.concurrent.TimeUnit.NANOSECONDS;	 Catch:{ InterruptedException -> 0x002b }
        r8 = r10.tryLock(r4, r6);	 Catch:{ InterruptedException -> 0x002b }
        if (r0 == 0) goto L_0x002a;
    L_0x0023:
        r9 = java.lang.Thread.currentThread();
        r9.interrupt();
    L_0x002a:
        return r8;
    L_0x002b:
        r4 = remainingNanos(r2, r8);	 Catch:{ all -> 0x0031 }
        r0 = 1;
        goto L_0x001b;
    L_0x0031:
        r8 = move-exception;
        r0 = 1;
        goto L_0x0035;
    L_0x0034:
        r8 = move-exception;
    L_0x0035:
        if (r0 == 0) goto L_0x003e;
    L_0x0037:
        r9 = java.lang.Thread.currentThread();
        r9.interrupt();
    L_0x003e:
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.enter(long, java.util.concurrent.TimeUnit):boolean");
    }

    public void enterInterruptibly() throws InterruptedException {
        this.lock.lockInterruptibly();
    }

    public boolean enterInterruptibly(long j, TimeUnit timeUnit) throws InterruptedException {
        return this.lock.tryLock(j, timeUnit);
    }

    public boolean tryEnter() {
        return this.lock.tryLock();
    }

    public void enterWhen(Guard guard) throws InterruptedException {
        if (guard.monitor == this) {
            ReentrantLock reentrantLock = this.lock;
            boolean isHeldByCurrentThread = reentrantLock.isHeldByCurrentThread();
            reentrantLock.lockInterruptibly();
            try {
                if (!guard.isSatisfied()) {
                    await(guard, isHeldByCurrentThread);
                }
            } catch (Throwable th) {
                leave();
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x003a A:{Catch:{ all -> 0x0050, all -> 0x0057 }} */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004c  */
    /* JADX WARNING: Missing block: B:21:0x0047, code:
            if (awaitNanos(r11, r0, r3) != false) goto L_0x0049;
     */
    public boolean enterWhen(com.google.common.util.concurrent.Monitor.Guard r11, long r12, java.util.concurrent.TimeUnit r14) throws java.lang.InterruptedException {
        /*
        r10 = this;
        r0 = toSafeNanos(r12, r14);
        r2 = r11.monitor;
        if (r2 != r10) goto L_0x0060;
    L_0x0008:
        r2 = r10.lock;
        r3 = r2.isHeldByCurrentThread();
        r4 = r10.fair;
        r5 = 0;
        r6 = 0;
        if (r4 != 0) goto L_0x0029;
    L_0x0015:
        r4 = java.lang.Thread.interrupted();
        if (r4 != 0) goto L_0x0023;
    L_0x001b:
        r4 = r2.tryLock();
        if (r4 == 0) goto L_0x0029;
    L_0x0021:
        r8 = r6;
        goto L_0x0034;
    L_0x0023:
        r11 = new java.lang.InterruptedException;
        r11.<init>();
        throw r11;
    L_0x0029:
        r8 = initNanoTime(r0);
        r12 = r2.tryLock(r12, r14);
        if (r12 != 0) goto L_0x0034;
    L_0x0033:
        return r5;
    L_0x0034:
        r12 = r11.isSatisfied();	 Catch:{ all -> 0x0050 }
        if (r12 != 0) goto L_0x0049;
    L_0x003a:
        r12 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r12 != 0) goto L_0x003f;
    L_0x003e:
        goto L_0x0043;
    L_0x003f:
        r0 = remainingNanos(r8, r0);	 Catch:{ all -> 0x0050 }
    L_0x0043:
        r11 = r10.awaitNanos(r11, r0, r3);	 Catch:{ all -> 0x0050 }
        if (r11 == 0) goto L_0x004a;
    L_0x0049:
        r5 = 1;
    L_0x004a:
        if (r5 != 0) goto L_0x004f;
    L_0x004c:
        r2.unlock();
    L_0x004f:
        return r5;
    L_0x0050:
        r11 = move-exception;
        if (r3 != 0) goto L_0x005c;
    L_0x0053:
        r10.signalNextWaiter();	 Catch:{ all -> 0x0057 }
        goto L_0x005c;
    L_0x0057:
        r11 = move-exception;
        r2.unlock();
        throw r11;
    L_0x005c:
        r2.unlock();
        throw r11;
    L_0x0060:
        r11 = new java.lang.IllegalMonitorStateException;
        r11.<init>();
        throw r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.enterWhen(com.google.common.util.concurrent.Monitor$Guard, long, java.util.concurrent.TimeUnit):boolean");
    }

    public void enterWhenUninterruptibly(Guard guard) {
        if (guard.monitor == this) {
            ReentrantLock reentrantLock = this.lock;
            boolean isHeldByCurrentThread = reentrantLock.isHeldByCurrentThread();
            reentrantLock.lock();
            try {
                if (!guard.isSatisfied()) {
                    awaitUninterruptibly(guard, isHeldByCurrentThread);
                }
            } catch (Throwable th) {
                leave();
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    /* JADX WARNING: Missing block: B:13:0x002e, code:
            if (r15.tryLock(r9, java.util.concurrent.TimeUnit.NANOSECONDS) == false) goto L_0x0060;
     */
    /* JADX WARNING: Missing block: B:33:0x0060, code:
            if (r1 == false) goto L_0x0069;
     */
    /* JADX WARNING: Missing block: B:34:0x0062, code:
            java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Missing block: B:35:0x0069, code:
            return false;
     */
    public boolean enterWhenUninterruptibly(com.google.common.util.concurrent.Monitor.Guard r12, long r13, java.util.concurrent.TimeUnit r15) {
        /*
        r11 = this;
        r13 = toSafeNanos(r13, r15);
        r15 = r12.monitor;
        if (r15 != r11) goto L_0x007e;
    L_0x0008:
        r15 = r11.lock;
        r0 = r15.isHeldByCurrentThread();
        r1 = java.lang.Thread.interrupted();
        r2 = 1;
        r3 = r11.fair;	 Catch:{ all -> 0x0073 }
        r4 = 0;
        r5 = 0;
        if (r3 != 0) goto L_0x0023;
    L_0x001a:
        r3 = r15.tryLock();	 Catch:{ all -> 0x0073 }
        if (r3 != 0) goto L_0x0021;
    L_0x0020:
        goto L_0x0023;
    L_0x0021:
        r7 = r5;
        goto L_0x0030;
    L_0x0023:
        r7 = initNanoTime(r13);	 Catch:{ all -> 0x0073 }
        r9 = r13;
    L_0x0028:
        r3 = java.util.concurrent.TimeUnit.NANOSECONDS;	 Catch:{ InterruptedException -> 0x006a }
        r3 = r15.tryLock(r9, r3);	 Catch:{ InterruptedException -> 0x006a }
        if (r3 == 0) goto L_0x0060;
    L_0x0030:
        r3 = r12.isSatisfied();	 Catch:{ InterruptedException -> 0x005d, all -> 0x0058 }
        if (r3 == 0) goto L_0x0037;
    L_0x0036:
        goto L_0x0049;
    L_0x0037:
        r3 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1));
        if (r3 != 0) goto L_0x0041;
    L_0x003b:
        r7 = initNanoTime(r13);	 Catch:{ InterruptedException -> 0x005d, all -> 0x0058 }
        r9 = r13;
        goto L_0x0045;
    L_0x0041:
        r9 = remainingNanos(r7, r13);	 Catch:{ InterruptedException -> 0x005d, all -> 0x0058 }
    L_0x0045:
        r2 = r11.awaitNanos(r12, r9, r0);	 Catch:{ InterruptedException -> 0x005d, all -> 0x0058 }
    L_0x0049:
        if (r2 != 0) goto L_0x004e;
    L_0x004b:
        r15.unlock();	 Catch:{ all -> 0x0073 }
    L_0x004e:
        if (r1 == 0) goto L_0x0057;
    L_0x0050:
        r12 = java.lang.Thread.currentThread();
        r12.interrupt();
    L_0x0057:
        return r2;
    L_0x0058:
        r12 = move-exception;
        r15.unlock();	 Catch:{ all -> 0x0073 }
        throw r12;	 Catch:{ all -> 0x0073 }
    L_0x005d:
        r0 = 0;
        r1 = 1;
        goto L_0x0030;
    L_0x0060:
        if (r1 == 0) goto L_0x0069;
    L_0x0062:
        r12 = java.lang.Thread.currentThread();
        r12.interrupt();
    L_0x0069:
        return r4;
    L_0x006a:
        r9 = remainingNanos(r7, r13);	 Catch:{ all -> 0x0070 }
        r1 = 1;
        goto L_0x0028;
    L_0x0070:
        r12 = move-exception;
        r1 = 1;
        goto L_0x0074;
    L_0x0073:
        r12 = move-exception;
    L_0x0074:
        if (r1 == 0) goto L_0x007d;
    L_0x0076:
        r13 = java.lang.Thread.currentThread();
        r13.interrupt();
    L_0x007d:
        throw r12;
    L_0x007e:
        r12 = new java.lang.IllegalMonitorStateException;
        r12.<init>();
        throw r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.enterWhenUninterruptibly(com.google.common.util.concurrent.Monitor$Guard, long, java.util.concurrent.TimeUnit):boolean");
    }

    public boolean enterIf(Guard guard) {
        if (guard.monitor == this) {
            ReentrantLock reentrantLock = this.lock;
            reentrantLock.lock();
            try {
                boolean isSatisfied = guard.isSatisfied();
                if (!isSatisfied) {
                }
                return isSatisfied;
            } finally {
                reentrantLock.unlock();
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean enterIf(Guard guard, long j, TimeUnit timeUnit) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        } else if (!enter(j, timeUnit)) {
            return false;
        } else {
            try {
                boolean isSatisfied = guard.isSatisfied();
                if (!isSatisfied) {
                }
                return isSatisfied;
            } finally {
                this.lock.unlock();
            }
        }
    }

    public boolean enterIfInterruptibly(Guard guard) throws InterruptedException {
        if (guard.monitor == this) {
            ReentrantLock reentrantLock = this.lock;
            reentrantLock.lockInterruptibly();
            try {
                boolean isSatisfied = guard.isSatisfied();
                if (!isSatisfied) {
                }
                return isSatisfied;
            } finally {
                reentrantLock.unlock();
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean enterIfInterruptibly(Guard guard, long j, TimeUnit timeUnit) throws InterruptedException {
        if (guard.monitor == this) {
            ReentrantLock reentrantLock = this.lock;
            if (!reentrantLock.tryLock(j, timeUnit)) {
                return false;
            }
            try {
                boolean isSatisfied = guard.isSatisfied();
                if (!isSatisfied) {
                }
                return isSatisfied;
            } finally {
                reentrantLock.unlock();
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean tryEnterIf(Guard guard) {
        if (guard.monitor == this) {
            ReentrantLock reentrantLock = this.lock;
            if (!reentrantLock.tryLock()) {
                return false;
            }
            try {
                boolean isSatisfied = guard.isSatisfied();
                if (!isSatisfied) {
                }
                return isSatisfied;
            } finally {
                reentrantLock.unlock();
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public void waitFor(Guard guard) throws InterruptedException {
        if (((guard.monitor == this ? 1 : 0) & this.lock.isHeldByCurrentThread()) == 0) {
            throw new IllegalMonitorStateException();
        } else if (!guard.isSatisfied()) {
            await(guard, true);
        }
    }

    public boolean waitFor(Guard guard, long j, TimeUnit timeUnit) throws InterruptedException {
        j = toSafeNanos(j, timeUnit);
        if (((guard.monitor == this ? 1 : 0) & this.lock.isHeldByCurrentThread()) == 0) {
            throw new IllegalMonitorStateException();
        } else if (guard.isSatisfied()) {
            return true;
        } else {
            if (!Thread.interrupted()) {
                return awaitNanos(guard, j, true);
            }
            throw new InterruptedException();
        }
    }

    public void waitForUninterruptibly(Guard guard) {
        if (((guard.monitor == this ? 1 : 0) & this.lock.isHeldByCurrentThread()) == 0) {
            throw new IllegalMonitorStateException();
        } else if (!guard.isSatisfied()) {
            awaitUninterruptibly(guard, true);
        }
    }

    /* JADX WARNING: Missing block: B:12:0x002c, code:
            if (r6 == false) goto L_0x0035;
     */
    /* JADX WARNING: Missing block: B:13:0x002e, code:
            java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Missing block: B:14:0x0035, code:
            return r8;
     */
    public boolean waitForUninterruptibly(com.google.common.util.concurrent.Monitor.Guard r8, long r9, java.util.concurrent.TimeUnit r11) {
        /*
        r7 = this;
        r9 = toSafeNanos(r9, r11);
        r11 = r8.monitor;
        r0 = 0;
        r1 = 1;
        if (r11 != r7) goto L_0x000c;
    L_0x000a:
        r11 = 1;
        goto L_0x000d;
    L_0x000c:
        r11 = 0;
    L_0x000d:
        r2 = r7.lock;
        r2 = r2.isHeldByCurrentThread();
        r11 = r11 & r2;
        if (r11 == 0) goto L_0x0059;
    L_0x0016:
        r11 = r8.isSatisfied();
        if (r11 == 0) goto L_0x001d;
    L_0x001c:
        return r1;
    L_0x001d:
        r2 = initNanoTime(r9);
        r11 = java.lang.Thread.interrupted();
        r4 = r9;
        r6 = r11;
        r11 = 1;
    L_0x0028:
        r8 = r7.awaitNanos(r8, r4, r11);	 Catch:{ InterruptedException -> 0x0039, all -> 0x0036 }
        if (r6 == 0) goto L_0x0035;
    L_0x002e:
        r9 = java.lang.Thread.currentThread();
        r9.interrupt();
    L_0x0035:
        return r8;
    L_0x0036:
        r8 = move-exception;
        r1 = r6;
        goto L_0x004f;
    L_0x0039:
        r11 = r8.isSatisfied();	 Catch:{ all -> 0x004e }
        if (r11 == 0) goto L_0x0047;
    L_0x003f:
        r8 = java.lang.Thread.currentThread();
        r8.interrupt();
        return r1;
    L_0x0047:
        r4 = remainingNanos(r2, r9);	 Catch:{ all -> 0x004e }
        r11 = 0;
        r6 = 1;
        goto L_0x0028;
    L_0x004e:
        r8 = move-exception;
    L_0x004f:
        if (r1 == 0) goto L_0x0058;
    L_0x0051:
        r9 = java.lang.Thread.currentThread();
        r9.interrupt();
    L_0x0058:
        throw r8;
    L_0x0059:
        r8 = new java.lang.IllegalMonitorStateException;
        r8.<init>();
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.waitForUninterruptibly(com.google.common.util.concurrent.Monitor$Guard, long, java.util.concurrent.TimeUnit):boolean");
    }

    public void leave() {
        ReentrantLock reentrantLock = this.lock;
        try {
            if (reentrantLock.getHoldCount() == 1) {
                signalNextWaiter();
            }
            reentrantLock.unlock();
        } catch (Throwable th) {
            reentrantLock.unlock();
        }
    }

    public boolean isFair() {
        return this.fair;
    }

    public boolean isOccupied() {
        return this.lock.isLocked();
    }

    public boolean isOccupiedByCurrentThread() {
        return this.lock.isHeldByCurrentThread();
    }

    public int getOccupiedDepth() {
        return this.lock.getHoldCount();
    }

    public int getQueueLength() {
        return this.lock.getQueueLength();
    }

    public boolean hasQueuedThreads() {
        return this.lock.hasQueuedThreads();
    }

    public boolean hasQueuedThread(Thread thread) {
        return this.lock.hasQueuedThread(thread);
    }

    public boolean hasWaiters(Guard guard) {
        return getWaitQueueLength(guard) > 0;
    }

    public int getWaitQueueLength(Guard guard) {
        if (guard.monitor == this) {
            this.lock.lock();
            try {
                int i = guard.waiterCount;
                return i;
            } finally {
                this.lock.unlock();
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    private static long toSafeNanos(long j, TimeUnit timeUnit) {
        j = timeUnit.toNanos(j);
        if (j <= 0) {
            return 0;
        }
        return j > 6917529027641081853L ? 6917529027641081853L : j;
    }

    private static long initNanoTime(long j) {
        if (j <= 0) {
            return 0;
        }
        j = System.nanoTime();
        if (j == 0) {
            j = 1;
        }
        return j;
    }

    private static long remainingNanos(long j, long j2) {
        return j2 <= 0 ? 0 : j2 - (System.nanoTime() - j);
    }

    @GuardedBy("lock")
    private void signalNextWaiter() {
        for (Guard guard = this.activeGuards; guard != null; guard = guard.next) {
            if (isSatisfied(guard)) {
                guard.condition.signal();
                return;
            }
        }
    }

    @GuardedBy("lock")
    private boolean isSatisfied(Guard guard) {
        try {
            return guard.isSatisfied();
        } catch (Throwable th) {
            signalAllWaiters();
            RuntimeException propagate = Throwables.propagate(th);
        }
    }

    @GuardedBy("lock")
    private void signalAllWaiters() {
        for (Guard guard = this.activeGuards; guard != null; guard = guard.next) {
            guard.condition.signalAll();
        }
    }

    @GuardedBy("lock")
    private void beginWaitingFor(Guard guard) {
        int i = guard.waiterCount;
        guard.waiterCount = i + 1;
        if (i == 0) {
            guard.next = this.activeGuards;
            this.activeGuards = guard;
        }
    }

    @GuardedBy("lock")
    private void endWaitingFor(Guard guard) {
        int i = guard.waiterCount - 1;
        guard.waiterCount = i;
        if (i == 0) {
            Guard guard2 = this.activeGuards;
            Guard guard3 = null;
            while (guard2 != guard) {
                guard3 = guard2;
                guard2 = guard2.next;
            }
            if (guard3 == null) {
                this.activeGuards = guard2.next;
            } else {
                guard3.next = guard2.next;
            }
            guard2.next = null;
        }
    }

    @GuardedBy("lock")
    private void await(Guard guard, boolean z) throws InterruptedException {
        if (z) {
            signalNextWaiter();
        }
        beginWaitingFor(guard);
        while (true) {
            try {
                guard.condition.await();
                if (guard.isSatisfied()) {
                    break;
                }
            } finally {
                endWaitingFor(guard);
            }
        }
    }

    @GuardedBy("lock")
    private void awaitUninterruptibly(Guard guard, boolean z) {
        if (z) {
            signalNextWaiter();
        }
        beginWaitingFor(guard);
        while (true) {
            try {
                guard.condition.awaitUninterruptibly();
                if (guard.isSatisfied()) {
                    break;
                }
            } finally {
                endWaitingFor(guard);
            }
        }
    }

    @GuardedBy("lock")
    private boolean awaitNanos(Guard guard, long j, boolean z) throws InterruptedException {
        Object obj = 1;
        while (j > 0) {
            if (obj != null) {
                if (z) {
                    try {
                        signalNextWaiter();
                    } catch (Throwable th) {
                        if (obj == null) {
                            endWaitingFor(guard);
                        }
                    }
                }
                beginWaitingFor(guard);
                obj = null;
            }
            j = guard.condition.awaitNanos(j);
            if (guard.isSatisfied()) {
                if (obj == null) {
                    endWaitingFor(guard);
                }
                return true;
            }
        }
        if (obj == null) {
            endWaitingFor(guard);
        }
        return false;
    }
}
