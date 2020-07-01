package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.ForOverride;
import com.google.j2objc.annotations.ReflectionSupport;
import com.google.j2objc.annotations.ReflectionSupport.Level;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import sun.misc.Unsafe;

@GwtCompatible(emulated = true)
@ReflectionSupport(Level.FULL)
public abstract class AbstractFuture<V> extends FluentFuture<V> {
    private static final AtomicHelper ATOMIC_HELPER;
    private static final boolean GENERATE_CANCELLATION_CAUSES = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
    private static final Object NULL = new Object();
    private static final long SPIN_THRESHOLD_NANOS = 1000;
    private static final Logger log = Logger.getLogger(AbstractFuture.class.getName());
    @NullableDecl
    private volatile Listener listeners;
    @NullableDecl
    private volatile Object value;
    @NullableDecl
    private volatile Waiter waiters;

    private static abstract class AtomicHelper {
        abstract boolean casListeners(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2);

        abstract boolean casValue(AbstractFuture<?> abstractFuture, Object obj, Object obj2);

        abstract boolean casWaiters(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2);

        abstract void putNext(Waiter waiter, Waiter waiter2);

        abstract void putThread(Waiter waiter, Thread thread);

        private AtomicHelper() {
        }
    }

    private static final class Cancellation {
        static final Cancellation CAUSELESS_CANCELLED;
        static final Cancellation CAUSELESS_INTERRUPTED;
        @NullableDecl
        final Throwable cause;
        final boolean wasInterrupted;

        static {
            if (AbstractFuture.GENERATE_CANCELLATION_CAUSES) {
                CAUSELESS_CANCELLED = null;
                CAUSELESS_INTERRUPTED = null;
                return;
            }
            CAUSELESS_CANCELLED = new Cancellation(false, null);
            CAUSELESS_INTERRUPTED = new Cancellation(true, null);
        }

        Cancellation(boolean z, @NullableDecl Throwable th) {
            this.wasInterrupted = z;
            this.cause = th;
        }
    }

    private static final class Failure {
        static final Failure FALLBACK_INSTANCE = new Failure(new Throwable("Failure occurred while trying to finish a future.") {
            public synchronized Throwable fillInStackTrace() {
                return this;
            }
        });
        final Throwable exception;

        Failure(Throwable th) {
            this.exception = (Throwable) Preconditions.checkNotNull(th);
        }
    }

    private static final class Listener {
        static final Listener TOMBSTONE = new Listener(null, null);
        final Executor executor;
        @NullableDecl
        Listener next;
        final Runnable task;

        Listener(Runnable runnable, Executor executor) {
            this.task = runnable;
            this.executor = executor;
        }
    }

    private static final class SetFuture<V> implements Runnable {
        final ListenableFuture<? extends V> future;
        final AbstractFuture<V> owner;

        SetFuture(AbstractFuture<V> abstractFuture, ListenableFuture<? extends V> listenableFuture) {
            this.owner = abstractFuture;
            this.future = listenableFuture;
        }

        public void run() {
            if (this.owner.value == this) {
                if (AbstractFuture.ATOMIC_HELPER.casValue(this.owner, this, AbstractFuture.getFutureValue(this.future))) {
                    AbstractFuture.complete(this.owner);
                }
            }
        }
    }

    private static final class Waiter {
        static final Waiter TOMBSTONE = new Waiter(false);
        @NullableDecl
        volatile Waiter next;
        @NullableDecl
        volatile Thread thread;

        Waiter(boolean z) {
        }

        Waiter() {
            AbstractFuture.ATOMIC_HELPER.putThread(this, Thread.currentThread());
        }

        void setNext(Waiter waiter) {
            AbstractFuture.ATOMIC_HELPER.putNext(this, waiter);
        }

        void unpark() {
            Thread thread = this.thread;
            if (thread != null) {
                this.thread = null;
                LockSupport.unpark(thread);
            }
        }
    }

    private static final class SafeAtomicHelper extends AtomicHelper {
        final AtomicReferenceFieldUpdater<AbstractFuture, Listener> listenersUpdater;
        final AtomicReferenceFieldUpdater<AbstractFuture, Object> valueUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Waiter> waiterNextUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Thread> waiterThreadUpdater;
        final AtomicReferenceFieldUpdater<AbstractFuture, Waiter> waitersUpdater;

        SafeAtomicHelper(AtomicReferenceFieldUpdater<Waiter, Thread> atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater<Waiter, Waiter> atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater<AbstractFuture, Waiter> atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater<AbstractFuture, Listener> atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater<AbstractFuture, Object> atomicReferenceFieldUpdater5) {
            super();
            this.waiterThreadUpdater = atomicReferenceFieldUpdater;
            this.waiterNextUpdater = atomicReferenceFieldUpdater2;
            this.waitersUpdater = atomicReferenceFieldUpdater3;
            this.listenersUpdater = atomicReferenceFieldUpdater4;
            this.valueUpdater = atomicReferenceFieldUpdater5;
        }

        void putThread(Waiter waiter, Thread thread) {
            this.waiterThreadUpdater.lazySet(waiter, thread);
        }

        void putNext(Waiter waiter, Waiter waiter2) {
            this.waiterNextUpdater.lazySet(waiter, waiter2);
        }

        boolean casWaiters(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2) {
            return this.waitersUpdater.compareAndSet(abstractFuture, waiter, waiter2);
        }

        boolean casListeners(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2) {
            return this.listenersUpdater.compareAndSet(abstractFuture, listener, listener2);
        }

        boolean casValue(AbstractFuture<?> abstractFuture, Object obj, Object obj2) {
            return this.valueUpdater.compareAndSet(abstractFuture, obj, obj2);
        }
    }

    private static final class SynchronizedHelper extends AtomicHelper {
        private SynchronizedHelper() {
            super();
        }

        void putThread(Waiter waiter, Thread thread) {
            waiter.thread = thread;
        }

        void putNext(Waiter waiter, Waiter waiter2) {
            waiter.next = waiter2;
        }

        boolean casWaiters(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2) {
            synchronized (abstractFuture) {
                if (abstractFuture.waiters == waiter) {
                    abstractFuture.waiters = waiter2;
                    return true;
                }
                return false;
            }
        }

        boolean casListeners(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2) {
            synchronized (abstractFuture) {
                if (abstractFuture.listeners == listener) {
                    abstractFuture.listeners = listener2;
                    return true;
                }
                return false;
            }
        }

        boolean casValue(AbstractFuture<?> abstractFuture, Object obj, Object obj2) {
            synchronized (abstractFuture) {
                if (abstractFuture.value == obj) {
                    abstractFuture.value = obj2;
                    return true;
                }
                return false;
            }
        }
    }

    private static final class UnsafeAtomicHelper extends AtomicHelper {
        static final long LISTENERS_OFFSET;
        static final Unsafe UNSAFE;
        static final long VALUE_OFFSET;
        static final long WAITERS_OFFSET;
        static final long WAITER_NEXT_OFFSET;
        static final long WAITER_THREAD_OFFSET;

        private UnsafeAtomicHelper() {
            super();
        }

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:3:?, code:
            r0 = (sun.misc.Unsafe) java.security.AccessController.doPrivileged(new com.google.common.util.concurrent.AbstractFuture.UnsafeAtomicHelper.AnonymousClass1());
     */
        /* JADX WARNING: Missing block: B:5:?, code:
            r1 = com.google.common.util.concurrent.AbstractFuture.class;
            WAITERS_OFFSET = r0.objectFieldOffset(r1.getDeclaredField("waiters"));
            LISTENERS_OFFSET = r0.objectFieldOffset(r1.getDeclaredField("listeners"));
            VALUE_OFFSET = r0.objectFieldOffset(r1.getDeclaredField("value"));
            WAITER_THREAD_OFFSET = r0.objectFieldOffset(com.google.common.util.concurrent.AbstractFuture.Waiter.class.getDeclaredField("thread"));
            WAITER_NEXT_OFFSET = r0.objectFieldOffset(com.google.common.util.concurrent.AbstractFuture.Waiter.class.getDeclaredField("next"));
            UNSAFE = r0;
     */
        /* JADX WARNING: Missing block: B:6:0x0054, code:
            return;
     */
        /* JADX WARNING: Missing block: B:7:0x0055, code:
            r0 = move-exception;
     */
        /* JADX WARNING: Missing block: B:8:0x0056, code:
            com.google.common.base.Throwables.throwIfUnchecked(r0);
     */
        /* JADX WARNING: Missing block: B:9:0x005e, code:
            throw new java.lang.RuntimeException(r0);
     */
        static {
            /*
            r0 = sun.misc.Unsafe.getUnsafe();	 Catch:{ SecurityException -> 0x0005 }
            goto L_0x0010;
        L_0x0005:
            r0 = new com.google.common.util.concurrent.AbstractFuture$UnsafeAtomicHelper$1;	 Catch:{ PrivilegedActionException -> 0x005f }
            r0.<init>();	 Catch:{ PrivilegedActionException -> 0x005f }
            r0 = java.security.AccessController.doPrivileged(r0);	 Catch:{ PrivilegedActionException -> 0x005f }
            r0 = (sun.misc.Unsafe) r0;	 Catch:{ PrivilegedActionException -> 0x005f }
        L_0x0010:
            r1 = com.google.common.util.concurrent.AbstractFuture.class;
            r2 = "waiters";
            r2 = r1.getDeclaredField(r2);	 Catch:{ Exception -> 0x0055 }
            r2 = r0.objectFieldOffset(r2);	 Catch:{ Exception -> 0x0055 }
            WAITERS_OFFSET = r2;	 Catch:{ Exception -> 0x0055 }
            r2 = "listeners";
            r2 = r1.getDeclaredField(r2);	 Catch:{ Exception -> 0x0055 }
            r2 = r0.objectFieldOffset(r2);	 Catch:{ Exception -> 0x0055 }
            LISTENERS_OFFSET = r2;	 Catch:{ Exception -> 0x0055 }
            r2 = "value";
            r1 = r1.getDeclaredField(r2);	 Catch:{ Exception -> 0x0055 }
            r1 = r0.objectFieldOffset(r1);	 Catch:{ Exception -> 0x0055 }
            VALUE_OFFSET = r1;	 Catch:{ Exception -> 0x0055 }
            r1 = com.google.common.util.concurrent.AbstractFuture.Waiter.class;
            r2 = "thread";
            r1 = r1.getDeclaredField(r2);	 Catch:{ Exception -> 0x0055 }
            r1 = r0.objectFieldOffset(r1);	 Catch:{ Exception -> 0x0055 }
            WAITER_THREAD_OFFSET = r1;	 Catch:{ Exception -> 0x0055 }
            r1 = com.google.common.util.concurrent.AbstractFuture.Waiter.class;
            r2 = "next";
            r1 = r1.getDeclaredField(r2);	 Catch:{ Exception -> 0x0055 }
            r1 = r0.objectFieldOffset(r1);	 Catch:{ Exception -> 0x0055 }
            WAITER_NEXT_OFFSET = r1;	 Catch:{ Exception -> 0x0055 }
            UNSAFE = r0;	 Catch:{ Exception -> 0x0055 }
            return;
        L_0x0055:
            r0 = move-exception;
            com.google.common.base.Throwables.throwIfUnchecked(r0);
            r1 = new java.lang.RuntimeException;
            r1.<init>(r0);
            throw r1;
        L_0x005f:
            r0 = move-exception;
            r1 = new java.lang.RuntimeException;
            r0 = r0.getCause();
            r2 = "Could not initialize intrinsics";
            r1.<init>(r2, r0);
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractFuture.UnsafeAtomicHelper.<clinit>():void");
        }

        void putThread(Waiter waiter, Thread thread) {
            UNSAFE.putObject(waiter, WAITER_THREAD_OFFSET, thread);
        }

        void putNext(Waiter waiter, Waiter waiter2) {
            UNSAFE.putObject(waiter, WAITER_NEXT_OFFSET, waiter2);
        }

        boolean casWaiters(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, WAITERS_OFFSET, waiter, waiter2);
        }

        boolean casListeners(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, LISTENERS_OFFSET, listener, listener2);
        }

        boolean casValue(AbstractFuture<?> abstractFuture, Object obj, Object obj2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, VALUE_OFFSET, obj, obj2);
        }
    }

    static abstract class TrustedFuture<V> extends AbstractFuture<V> {
        TrustedFuture() {
        }

        @CanIgnoreReturnValue
        public final V get() throws InterruptedException, ExecutionException {
            return super.get();
        }

        @CanIgnoreReturnValue
        public final V get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
            return super.get(j, timeUnit);
        }

        public final boolean isDone() {
            return super.isDone();
        }

        public final boolean isCancelled() {
            return super.isCancelled();
        }

        public final void addListener(Runnable runnable, Executor executor) {
            super.addListener(runnable, executor);
        }

        @CanIgnoreReturnValue
        public final boolean cancel(boolean z) {
            return super.cancel(z);
        }
    }

    @ForOverride
    @Beta
    protected void afterDone() {
    }

    protected void interruptTask() {
    }

    static {
        AtomicHelper unsafeAtomicHelper;
        Throwable th;
        Throwable th2 = null;
        try {
            unsafeAtomicHelper = new UnsafeAtomicHelper();
            th = null;
        } catch (Throwable th3) {
            th2 = th3;
            th3 = th;
            unsafeAtomicHelper = new SynchronizedHelper();
        }
        ATOMIC_HELPER = unsafeAtomicHelper;
        Class cls = LockSupport.class;
        if (th2 != null) {
            log.log(java.util.logging.Level.SEVERE, "UnsafeAtomicHelper is broken!", th3);
            log.log(java.util.logging.Level.SEVERE, "SafeAtomicHelper is broken!", th2);
        }
    }

    private void removeWaiter(Waiter waiter) {
        waiter.thread = null;
        while (true) {
            waiter = this.waiters;
            if (waiter != Waiter.TOMBSTONE) {
                Waiter waiter2 = null;
                while (waiter != null) {
                    Waiter waiter3 = waiter.next;
                    if (waiter.thread != null) {
                        waiter2 = waiter;
                    } else if (waiter2 != null) {
                        waiter2.next = waiter3;
                        if (waiter2.thread == null) {
                        }
                    } else if (ATOMIC_HELPER.casWaiters(this, waiter, waiter3)) {
                    }
                    waiter = waiter3;
                }
                return;
            }
            return;
        }
    }

    protected AbstractFuture() {
    }

    @CanIgnoreReturnValue
    public V get(long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException, ExecutionException {
        long j2 = j;
        long toNanos = timeUnit.toNanos(j2);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object obj = this.value;
        if (((obj != null ? 1 : 0) & (!(obj instanceof SetFuture) ? 1 : 0)) != 0) {
            return getDoneValue(obj);
        }
        Object obj2;
        long nanoTime = toNanos > 0 ? System.nanoTime() + toNanos : 0;
        if (toNanos >= SPIN_THRESHOLD_NANOS) {
            Waiter waiter = this.waiters;
            if (waiter != Waiter.TOMBSTONE) {
                Waiter waiter2 = new Waiter();
                do {
                    waiter2.setNext(waiter);
                    if (ATOMIC_HELPER.casWaiters(this, waiter, waiter2)) {
                        do {
                            LockSupport.parkNanos(this, toNanos);
                            if (Thread.interrupted()) {
                                removeWaiter(waiter2);
                                throw new InterruptedException();
                            }
                            obj2 = this.value;
                            if (((obj2 != null ? 1 : 0) & (!(obj2 instanceof SetFuture) ? 1 : 0)) != 0) {
                                return getDoneValue(obj2);
                            }
                            toNanos = nanoTime - System.nanoTime();
                        } while (toNanos >= SPIN_THRESHOLD_NANOS);
                        removeWaiter(waiter2);
                    } else {
                        waiter = this.waiters;
                    }
                } while (waiter != Waiter.TOMBSTONE);
            }
            return getDoneValue(this.value);
        }
        while (toNanos > 0) {
            obj2 = this.value;
            if (((obj2 != null ? 1 : 0) & (!(obj2 instanceof SetFuture) ? 1 : 0)) != 0) {
                return getDoneValue(obj2);
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            toNanos = nanoTime - System.nanoTime();
        }
        String abstractFuture = toString();
        String str = " ";
        String str2 = "Waited ";
        if (isDone()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(j2);
            stringBuilder.append(str);
            stringBuilder.append(timeUnit.toString().toLowerCase(Locale.ROOT));
            stringBuilder.append(" but future completed as timeout expired");
            throw new TimeoutException(stringBuilder.toString());
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str2);
        stringBuilder2.append(j2);
        stringBuilder2.append(str);
        stringBuilder2.append(timeUnit.toString().toLowerCase(Locale.ROOT));
        stringBuilder2.append(" for ");
        stringBuilder2.append(abstractFuture);
        throw new TimeoutException(stringBuilder2.toString());
    }

    @CanIgnoreReturnValue
    public V get() throws InterruptedException, ExecutionException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object obj = this.value;
        if (((obj != null ? 1 : 0) & (!(obj instanceof SetFuture) ? 1 : 0)) != 0) {
            return getDoneValue(obj);
        }
        Waiter waiter = this.waiters;
        if (waiter != Waiter.TOMBSTONE) {
            Waiter waiter2 = new Waiter();
            do {
                waiter2.setNext(waiter);
                if (ATOMIC_HELPER.casWaiters(this, waiter, waiter2)) {
                    do {
                        LockSupport.park(this);
                        if (Thread.interrupted()) {
                            removeWaiter(waiter2);
                            throw new InterruptedException();
                        }
                        obj = this.value;
                    } while (((obj != null ? 1 : 0) & (!(obj instanceof SetFuture) ? 1 : 0)) == 0);
                    return getDoneValue(obj);
                }
                waiter = this.waiters;
            } while (waiter != Waiter.TOMBSTONE);
        }
        return getDoneValue(this.value);
    }

    private V getDoneValue(Object obj) throws ExecutionException {
        if (obj instanceof Cancellation) {
            throw cancellationExceptionWithCause("Task was cancelled.", ((Cancellation) obj).cause);
        } else if (!(obj instanceof Failure)) {
            return obj == NULL ? null : obj;
        } else {
            throw new ExecutionException(((Failure) obj).exception);
        }
    }

    public boolean isDone() {
        Object obj = this.value;
        int i = 1;
        int i2 = obj != null ? 1 : 0;
        if (obj instanceof SetFuture) {
            i = 0;
        }
        return i2 & i;
    }

    public boolean isCancelled() {
        return this.value instanceof Cancellation;
    }

    @CanIgnoreReturnValue
    public boolean cancel(boolean z) {
        Object obj = this.value;
        if (((obj == null ? 1 : 0) | (obj instanceof SetFuture)) == 0) {
            return false;
        }
        Object cancellation = GENERATE_CANCELLATION_CAUSES ? new Cancellation(z, new CancellationException("Future.cancel() was called.")) : z ? Cancellation.CAUSELESS_INTERRUPTED : Cancellation.CAUSELESS_CANCELLED;
        boolean z2 = false;
        Object obj2 = obj;
        AbstractFuture abstractFuture = this;
        while (true) {
            if (ATOMIC_HELPER.casValue(abstractFuture, obj2, cancellation)) {
                if (z) {
                    abstractFuture.interruptTask();
                }
                complete(abstractFuture);
                if (!(obj2 instanceof SetFuture)) {
                    return true;
                }
                ListenableFuture listenableFuture = ((SetFuture) obj2).future;
                if (listenableFuture instanceof TrustedFuture) {
                    abstractFuture = (AbstractFuture) listenableFuture;
                    obj2 = abstractFuture.value;
                    if (((obj2 == null ? 1 : 0) | (obj2 instanceof SetFuture)) == 0) {
                        return true;
                    }
                    z2 = true;
                } else {
                    listenableFuture.cancel(z);
                    return true;
                }
            }
            obj2 = abstractFuture.value;
            if (!(obj2 instanceof SetFuture)) {
                return z2;
            }
        }
    }

    protected final boolean wasInterrupted() {
        Object obj = this.value;
        return (obj instanceof Cancellation) && ((Cancellation) obj).wasInterrupted;
    }

    public void addListener(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        Listener listener = this.listeners;
        if (listener != Listener.TOMBSTONE) {
            Listener listener2 = new Listener(runnable, executor);
            do {
                listener2.next = listener;
                if (!ATOMIC_HELPER.casListeners(this, listener, listener2)) {
                    listener = this.listeners;
                } else {
                    return;
                }
            } while (listener != Listener.TOMBSTONE);
        }
        executeListener(runnable, executor);
    }

    @CanIgnoreReturnValue
    protected boolean set(@NullableDecl V v) {
        Object v2;
        if (v2 == null) {
            v2 = NULL;
        }
        if (!ATOMIC_HELPER.casValue(this, null, v2)) {
            return false;
        }
        complete(this);
        return true;
    }

    @CanIgnoreReturnValue
    protected boolean setException(Throwable th) {
        if (!ATOMIC_HELPER.casValue(this, null, new Failure((Throwable) Preconditions.checkNotNull(th)))) {
            return false;
        }
        complete(this);
        return true;
    }

    @CanIgnoreReturnValue
    @Beta
    protected boolean setFuture(ListenableFuture<? extends V> listenableFuture) {
        Runnable setFuture;
        Object failure;
        Preconditions.checkNotNull(listenableFuture);
        Object obj = this.value;
        if (obj == null) {
            if (listenableFuture.isDone()) {
                if (!ATOMIC_HELPER.casValue(this, null, getFutureValue(listenableFuture))) {
                    return false;
                }
                complete(this);
                return true;
            }
            setFuture = new SetFuture(this, listenableFuture);
            if (ATOMIC_HELPER.casValue(this, null, setFuture)) {
                try {
                    listenableFuture.addListener(setFuture, MoreExecutors.directExecutor());
                } catch (Throwable unused) {
                    failure = Failure.FALLBACK_INSTANCE;
                }
                return true;
            }
            obj = this.value;
        }
        if (obj instanceof Cancellation) {
            listenableFuture.cancel(((Cancellation) obj).wasInterrupted);
        }
        return false;
        ATOMIC_HELPER.casValue(this, setFuture, failure);
        return true;
    }

    private static Object getFutureValue(ListenableFuture<?> listenableFuture) {
        Object obj;
        if (listenableFuture instanceof TrustedFuture) {
            obj = ((AbstractFuture) listenableFuture).value;
            if (obj instanceof Cancellation) {
                Cancellation cancellation = (Cancellation) obj;
                if (cancellation.wasInterrupted) {
                    obj = cancellation.cause != null ? new Cancellation(false, cancellation.cause) : Cancellation.CAUSELESS_CANCELLED;
                }
            }
            return obj;
        }
        Object obj2;
        try {
            obj = Futures.getDone(listenableFuture);
            if (obj == null) {
                obj = NULL;
            }
            obj2 = obj;
        } catch (ExecutionException e) {
            obj2 = new Failure(e.getCause());
        } catch (Throwable e2) {
            obj2 = new Cancellation(false, e2);
        } catch (Throwable e22) {
            obj2 = new Failure(e22);
        }
        return obj2;
    }

    private static void complete(AbstractFuture<?> abstractFuture) {
        Listener listener = null;
        while (true) {
            AbstractFuture abstractFuture2;
            abstractFuture2.releaseWaiters();
            abstractFuture2.afterDone();
            Listener clearListeners = abstractFuture2.clearListeners(listener);
            while (clearListeners != null) {
                listener = clearListeners.next;
                Runnable runnable = clearListeners.task;
                if (runnable instanceof SetFuture) {
                    SetFuture setFuture = (SetFuture) runnable;
                    abstractFuture2 = setFuture.owner;
                    if (abstractFuture2.value == setFuture) {
                        if (ATOMIC_HELPER.casValue(abstractFuture2, setFuture, getFutureValue(setFuture.future))) {
                        }
                    } else {
                        continue;
                    }
                } else {
                    executeListener(runnable, clearListeners.executor);
                }
                clearListeners = listener;
            }
            return;
        }
    }

    final Throwable trustedGetException() {
        return ((Failure) this.value).exception;
    }

    final void maybePropagateCancellationTo(@NullableDecl Future<?> future) {
        if (((future != null ? 1 : 0) & isCancelled()) != 0) {
            future.cancel(wasInterrupted());
        }
    }

    private void releaseWaiters() {
        Waiter waiter;
        do {
            waiter = this.waiters;
        } while (!ATOMIC_HELPER.casWaiters(this, waiter, Waiter.TOMBSTONE));
        while (waiter != null) {
            waiter.unpark();
            waiter = waiter.next;
        }
    }

    private Listener clearListeners(Listener listener) {
        Listener listener2;
        do {
            listener2 = this.listeners;
        } while (!ATOMIC_HELPER.casListeners(this, listener2, Listener.TOMBSTONE));
        Listener listener3 = listener2;
        listener2 = listener;
        listener = listener3;
        while (listener != null) {
            Listener listener4 = listener.next;
            listener.next = listener2;
            listener2 = listener;
            listener = listener4;
        }
        return listener2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("[status=");
        String str = "]";
        if (isCancelled()) {
            stringBuilder.append("CANCELLED");
        } else if (isDone()) {
            addDoneString(stringBuilder);
        } else {
            String pendingToString;
            try {
                pendingToString = pendingToString();
            } catch (RuntimeException e) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Exception thrown from implementation: ");
                stringBuilder2.append(e.getClass());
                pendingToString = stringBuilder2.toString();
            }
            if (!Strings.isNullOrEmpty(pendingToString)) {
                stringBuilder.append("PENDING, info=[");
                stringBuilder.append(pendingToString);
                stringBuilder.append(str);
            } else if (isDone()) {
                addDoneString(stringBuilder);
            } else {
                stringBuilder.append("PENDING");
            }
        }
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    @NullableDecl
    protected String pendingToString() {
        Object obj = this.value;
        if (obj instanceof SetFuture) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setFuture=[");
            stringBuilder.append(userObjectToString(((SetFuture) obj).future));
            stringBuilder.append("]");
            return stringBuilder.toString();
        } else if (!(this instanceof ScheduledFuture)) {
            return null;
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("remaining delay=[");
            stringBuilder2.append(((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS));
            stringBuilder2.append(" ms]");
            return stringBuilder2.toString();
        }
    }

    private void addDoneString(StringBuilder stringBuilder) {
        String str = "]";
        try {
            Object done = Futures.getDone(this);
            stringBuilder.append("SUCCESS, result=[");
            stringBuilder.append(userObjectToString(done));
            stringBuilder.append(str);
        } catch (ExecutionException e) {
            stringBuilder.append("FAILURE, cause=[");
            stringBuilder.append(e.getCause());
            stringBuilder.append(str);
        } catch (CancellationException unused) {
            stringBuilder.append("CANCELLED");
        } catch (RuntimeException e2) {
            stringBuilder.append("UNKNOWN, cause=[");
            stringBuilder.append(e2.getClass());
            stringBuilder.append(" thrown from get()]");
        }
    }

    private String userObjectToString(Object obj) {
        return obj == this ? "this future" : String.valueOf(obj);
    }

    private static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (Throwable e) {
            Logger logger = log;
            java.util.logging.Level level = java.util.logging.Level.SEVERE;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RuntimeException while executing runnable ");
            stringBuilder.append(runnable);
            stringBuilder.append(" with executor ");
            stringBuilder.append(executor);
            logger.log(level, stringBuilder.toString(), e);
        }
    }

    private static CancellationException cancellationExceptionWithCause(@NullableDecl String str, @NullableDecl Throwable th) {
        CancellationException cancellationException = new CancellationException(str);
        cancellationException.initCause(th);
        return cancellationException;
    }
}
