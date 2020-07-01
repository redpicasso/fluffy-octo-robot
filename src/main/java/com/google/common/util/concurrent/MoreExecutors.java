package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.ForwardingListenableFuture.SimpleForwardingListenableFuture;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@GwtCompatible(emulated = true)
public final class MoreExecutors {

    @GwtIncompatible
    @VisibleForTesting
    static class Application {
        Application() {
        }

        final ExecutorService getExitingExecutorService(ThreadPoolExecutor threadPoolExecutor, long j, TimeUnit timeUnit) {
            MoreExecutors.useDaemonThreadFactory(threadPoolExecutor);
            ExecutorService unconfigurableExecutorService = Executors.unconfigurableExecutorService(threadPoolExecutor);
            addDelayedShutdownHook(threadPoolExecutor, j, timeUnit);
            return unconfigurableExecutorService;
        }

        final ExecutorService getExitingExecutorService(ThreadPoolExecutor threadPoolExecutor) {
            return getExitingExecutorService(threadPoolExecutor, 120, TimeUnit.SECONDS);
        }

        final ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, long j, TimeUnit timeUnit) {
            MoreExecutors.useDaemonThreadFactory(scheduledThreadPoolExecutor);
            ScheduledExecutorService unconfigurableScheduledExecutorService = Executors.unconfigurableScheduledExecutorService(scheduledThreadPoolExecutor);
            addDelayedShutdownHook(scheduledThreadPoolExecutor, j, timeUnit);
            return unconfigurableScheduledExecutorService;
        }

        final ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
            return getExitingScheduledExecutorService(scheduledThreadPoolExecutor, 120, TimeUnit.SECONDS);
        }

        final void addDelayedShutdownHook(ExecutorService executorService, long j, TimeUnit timeUnit) {
            Preconditions.checkNotNull(executorService);
            Preconditions.checkNotNull(timeUnit);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DelayedShutdownHook-for-");
            stringBuilder.append(executorService);
            final ExecutorService executorService2 = executorService;
            final long j2 = j;
            final TimeUnit timeUnit2 = timeUnit;
            addShutdownHook(MoreExecutors.newThread(stringBuilder.toString(), new Runnable() {
                public void run() {
                    try {
                        executorService2.shutdown();
                        executorService2.awaitTermination(j2, timeUnit2);
                    } catch (InterruptedException unused) {
                    }
                }
            }));
        }

        @VisibleForTesting
        void addShutdownHook(Thread thread) {
            Runtime.getRuntime().addShutdownHook(thread);
        }
    }

    private enum DirectExecutor implements Executor {
        INSTANCE;

        public String toString() {
            return "MoreExecutors.directExecutor()";
        }

        public void execute(Runnable runnable) {
            runnable.run();
        }
    }

    @GwtIncompatible
    private static final class DirectExecutorService extends AbstractListeningExecutorService {
        private final Object lock;
        @GuardedBy("lock")
        private int runningTasks;
        @GuardedBy("lock")
        private boolean shutdown;

        private DirectExecutorService() {
            this.lock = new Object();
            this.runningTasks = 0;
            this.shutdown = false;
        }

        /* synthetic */ DirectExecutorService(AnonymousClass1 anonymousClass1) {
            this();
        }

        public void execute(Runnable runnable) {
            startTask();
            try {
                runnable.run();
            } finally {
                endTask();
            }
        }

        public boolean isShutdown() {
            boolean z;
            synchronized (this.lock) {
                z = this.shutdown;
            }
            return z;
        }

        public void shutdown() {
            synchronized (this.lock) {
                this.shutdown = true;
                if (this.runningTasks == 0) {
                    this.lock.notifyAll();
                }
            }
        }

        public List<Runnable> shutdownNow() {
            shutdown();
            return Collections.emptyList();
        }

        public boolean isTerminated() {
            boolean z;
            synchronized (this.lock) {
                z = this.shutdown && this.runningTasks == 0;
            }
            return z;
        }

        public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
            j = timeUnit.toNanos(j);
            synchronized (this.lock) {
                while (true) {
                    if (this.shutdown && this.runningTasks == 0) {
                        return true;
                    } else if (j <= 0) {
                        return false;
                    } else {
                        long nanoTime = System.nanoTime();
                        TimeUnit.NANOSECONDS.timedWait(this.lock, j);
                        j -= System.nanoTime() - nanoTime;
                    }
                }
            }
        }

        private void startTask() {
            synchronized (this.lock) {
                if (this.shutdown) {
                    throw new RejectedExecutionException("Executor already shutdown");
                }
                this.runningTasks++;
            }
        }

        private void endTask() {
            synchronized (this.lock) {
                int i = this.runningTasks - 1;
                this.runningTasks = i;
                if (i == 0) {
                    this.lock.notifyAll();
                }
            }
        }
    }

    @GwtIncompatible
    private static class ListeningDecorator extends AbstractListeningExecutorService {
        private final ExecutorService delegate;

        ListeningDecorator(ExecutorService executorService) {
            this.delegate = (ExecutorService) Preconditions.checkNotNull(executorService);
        }

        public final boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
            return this.delegate.awaitTermination(j, timeUnit);
        }

        public final boolean isShutdown() {
            return this.delegate.isShutdown();
        }

        public final boolean isTerminated() {
            return this.delegate.isTerminated();
        }

        public final void shutdown() {
            this.delegate.shutdown();
        }

        public final List<Runnable> shutdownNow() {
            return this.delegate.shutdownNow();
        }

        public final void execute(Runnable runnable) {
            this.delegate.execute(runnable);
        }
    }

    @GwtIncompatible
    private static final class ScheduledListeningDecorator extends ListeningDecorator implements ListeningScheduledExecutorService {
        final ScheduledExecutorService delegate;

        private static final class ListenableScheduledTask<V> extends SimpleForwardingListenableFuture<V> implements ListenableScheduledFuture<V> {
            private final ScheduledFuture<?> scheduledDelegate;

            public ListenableScheduledTask(ListenableFuture<V> listenableFuture, ScheduledFuture<?> scheduledFuture) {
                super(listenableFuture);
                this.scheduledDelegate = scheduledFuture;
            }

            public boolean cancel(boolean z) {
                boolean cancel = super.cancel(z);
                if (cancel) {
                    this.scheduledDelegate.cancel(z);
                }
                return cancel;
            }

            public long getDelay(TimeUnit timeUnit) {
                return this.scheduledDelegate.getDelay(timeUnit);
            }

            public int compareTo(Delayed delayed) {
                return this.scheduledDelegate.compareTo(delayed);
            }
        }

        @GwtIncompatible
        private static final class NeverSuccessfulListenableFutureTask extends AbstractFuture<Void> implements Runnable {
            private final Runnable delegate;

            public NeverSuccessfulListenableFutureTask(Runnable runnable) {
                this.delegate = (Runnable) Preconditions.checkNotNull(runnable);
            }

            public void run() {
                try {
                    this.delegate.run();
                } catch (Throwable th) {
                    setException(th);
                    RuntimeException propagate = Throwables.propagate(th);
                }
            }
        }

        ScheduledListeningDecorator(ScheduledExecutorService scheduledExecutorService) {
            super(scheduledExecutorService);
            this.delegate = (ScheduledExecutorService) Preconditions.checkNotNull(scheduledExecutorService);
        }

        public ListenableScheduledFuture<?> schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            Object create = TrustedListenableFutureTask.create(runnable, null);
            return new ListenableScheduledTask(create, this.delegate.schedule(create, j, timeUnit));
        }

        public <V> ListenableScheduledFuture<V> schedule(Callable<V> callable, long j, TimeUnit timeUnit) {
            Object create = TrustedListenableFutureTask.create((Callable) callable);
            return new ListenableScheduledTask(create, this.delegate.schedule(create, j, timeUnit));
        }

        public ListenableScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
            ListenableFuture neverSuccessfulListenableFutureTask = new NeverSuccessfulListenableFutureTask(runnable);
            return new ListenableScheduledTask(neverSuccessfulListenableFutureTask, this.delegate.scheduleAtFixedRate(neverSuccessfulListenableFutureTask, j, j2, timeUnit));
        }

        public ListenableScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
            ListenableFuture neverSuccessfulListenableFutureTask = new NeverSuccessfulListenableFutureTask(runnable);
            return new ListenableScheduledTask(neverSuccessfulListenableFutureTask, this.delegate.scheduleWithFixedDelay(neverSuccessfulListenableFutureTask, j, j2, timeUnit));
        }
    }

    private MoreExecutors() {
    }

    @GwtIncompatible
    @Beta
    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor threadPoolExecutor, long j, TimeUnit timeUnit) {
        return new Application().getExitingExecutorService(threadPoolExecutor, j, timeUnit);
    }

    @GwtIncompatible
    @Beta
    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor threadPoolExecutor) {
        return new Application().getExitingExecutorService(threadPoolExecutor);
    }

    @GwtIncompatible
    @Beta
    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, long j, TimeUnit timeUnit) {
        return new Application().getExitingScheduledExecutorService(scheduledThreadPoolExecutor, j, timeUnit);
    }

    @GwtIncompatible
    @Beta
    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        return new Application().getExitingScheduledExecutorService(scheduledThreadPoolExecutor);
    }

    @GwtIncompatible
    @Beta
    public static void addDelayedShutdownHook(ExecutorService executorService, long j, TimeUnit timeUnit) {
        new Application().addDelayedShutdownHook(executorService, j, timeUnit);
    }

    @GwtIncompatible
    private static void useDaemonThreadFactory(ThreadPoolExecutor threadPoolExecutor) {
        threadPoolExecutor.setThreadFactory(new ThreadFactoryBuilder().setDaemon(true).setThreadFactory(threadPoolExecutor.getThreadFactory()).build());
    }

    @GwtIncompatible
    public static ListeningExecutorService newDirectExecutorService() {
        return new DirectExecutorService();
    }

    public static Executor directExecutor() {
        return DirectExecutor.INSTANCE;
    }

    @GwtIncompatible
    @Beta
    public static Executor newSequentialExecutor(Executor executor) {
        return new SequentialExecutor(executor);
    }

    @GwtIncompatible
    public static ListeningExecutorService listeningDecorator(ExecutorService executorService) {
        if (executorService instanceof ListeningExecutorService) {
            return (ListeningExecutorService) executorService;
        }
        return executorService instanceof ScheduledExecutorService ? new ScheduledListeningDecorator((ScheduledExecutorService) executorService) : new ListeningDecorator(executorService);
    }

    @GwtIncompatible
    public static ListeningScheduledExecutorService listeningDecorator(ScheduledExecutorService scheduledExecutorService) {
        return scheduledExecutorService instanceof ListeningScheduledExecutorService ? (ListeningScheduledExecutorService) scheduledExecutorService : new ScheduledListeningDecorator(scheduledExecutorService);
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x00b8 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0093  */
    @com.google.common.annotations.GwtIncompatible
    static <T> T invokeAnyImpl(com.google.common.util.concurrent.ListeningExecutorService r16, java.util.Collection<? extends java.util.concurrent.Callable<T>> r17, boolean r18, long r19, java.util.concurrent.TimeUnit r21) throws java.lang.InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException {
        /*
        r1 = r16;
        com.google.common.base.Preconditions.checkNotNull(r16);
        com.google.common.base.Preconditions.checkNotNull(r21);
        r0 = r17.size();
        r2 = 1;
        if (r0 <= 0) goto L_0x0011;
    L_0x000f:
        r3 = 1;
        goto L_0x0012;
    L_0x0011:
        r3 = 0;
    L_0x0012:
        com.google.common.base.Preconditions.checkArgument(r3);
        r3 = com.google.common.collect.Lists.newArrayListWithCapacity(r0);
        r4 = com.google.common.collect.Queues.newLinkedBlockingQueue();
        r5 = r19;
        r7 = r21;
        r5 = r7.toNanos(r5);
        if (r18 == 0) goto L_0x002f;
    L_0x0027:
        r7 = java.lang.System.nanoTime();	 Catch:{ all -> 0x002c }
        goto L_0x0031;
    L_0x002c:
        r0 = move-exception;
        goto L_0x00bb;
    L_0x002f:
        r7 = 0;
    L_0x0031:
        r9 = r17.iterator();	 Catch:{ all -> 0x002c }
        r10 = r9.next();	 Catch:{ all -> 0x002c }
        r10 = (java.util.concurrent.Callable) r10;	 Catch:{ all -> 0x002c }
        r10 = submitAndAddQueueListener(r1, r10, r4);	 Catch:{ all -> 0x002c }
        r3.add(r10);	 Catch:{ all -> 0x002c }
        r0 = r0 + -1;
        r10 = 0;
        r11 = r7;
        r8 = r10;
        r6 = r5;
        r5 = 1;
    L_0x0049:
        r13 = r4.poll();	 Catch:{ all -> 0x002c }
        r13 = (java.util.concurrent.Future) r13;	 Catch:{ all -> 0x002c }
        if (r13 != 0) goto L_0x008f;
    L_0x0051:
        if (r0 <= 0) goto L_0x0065;
    L_0x0053:
        r0 = r0 + -1;
        r14 = r9.next();	 Catch:{ all -> 0x002c }
        r14 = (java.util.concurrent.Callable) r14;	 Catch:{ all -> 0x002c }
        r14 = submitAndAddQueueListener(r1, r14, r4);	 Catch:{ all -> 0x002c }
        r3.add(r14);	 Catch:{ all -> 0x002c }
        r5 = r5 + 1;
        goto L_0x008f;
    L_0x0065:
        if (r5 != 0) goto L_0x006f;
    L_0x0067:
        if (r8 != 0) goto L_0x006e;
    L_0x0069:
        r8 = new java.util.concurrent.ExecutionException;	 Catch:{ all -> 0x002c }
        r8.<init>(r10);	 Catch:{ all -> 0x002c }
    L_0x006e:
        throw r8;	 Catch:{ all -> 0x002c }
    L_0x006f:
        if (r18 == 0) goto L_0x0089;
    L_0x0071:
        r13 = java.util.concurrent.TimeUnit.NANOSECONDS;	 Catch:{ all -> 0x002c }
        r13 = r4.poll(r6, r13);	 Catch:{ all -> 0x002c }
        r13 = (java.util.concurrent.Future) r13;	 Catch:{ all -> 0x002c }
        if (r13 == 0) goto L_0x0083;
    L_0x007b:
        r14 = java.lang.System.nanoTime();	 Catch:{ all -> 0x002c }
        r11 = r14 - r11;
        r6 = r6 - r11;
        goto L_0x0090;
    L_0x0083:
        r0 = new java.util.concurrent.TimeoutException;	 Catch:{ all -> 0x002c }
        r0.<init>();	 Catch:{ all -> 0x002c }
        throw r0;	 Catch:{ all -> 0x002c }
    L_0x0089:
        r13 = r4.take();	 Catch:{ all -> 0x002c }
        r13 = (java.util.concurrent.Future) r13;	 Catch:{ all -> 0x002c }
    L_0x008f:
        r14 = r11;
    L_0x0090:
        r11 = r0;
        if (r13 == 0) goto L_0x00b8;
    L_0x0093:
        r5 = r5 + -1;
        r0 = r13.get();	 Catch:{ ExecutionException -> 0x00b6, RuntimeException -> 0x00ae }
        r1 = r3.iterator();
    L_0x009d:
        r3 = r1.hasNext();
        if (r3 == 0) goto L_0x00ad;
    L_0x00a3:
        r3 = r1.next();
        r3 = (java.util.concurrent.Future) r3;
        r3.cancel(r2);
        goto L_0x009d;
    L_0x00ad:
        return r0;
    L_0x00ae:
        r0 = move-exception;
        r8 = r0;
        r0 = new java.util.concurrent.ExecutionException;	 Catch:{ all -> 0x002c }
        r0.<init>(r8);	 Catch:{ all -> 0x002c }
        goto L_0x00b7;
    L_0x00b6:
        r0 = move-exception;
    L_0x00b7:
        r8 = r0;
    L_0x00b8:
        r0 = r11;
        r11 = r14;
        goto L_0x0049;
    L_0x00bb:
        r1 = r3.iterator();
    L_0x00bf:
        r3 = r1.hasNext();
        if (r3 == 0) goto L_0x00cf;
    L_0x00c5:
        r3 = r1.next();
        r3 = (java.util.concurrent.Future) r3;
        r3.cancel(r2);
        goto L_0x00bf;
    L_0x00cf:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.MoreExecutors.invokeAnyImpl(com.google.common.util.concurrent.ListeningExecutorService, java.util.Collection, boolean, long, java.util.concurrent.TimeUnit):T");
    }

    @GwtIncompatible
    private static <T> ListenableFuture<T> submitAndAddQueueListener(ListeningExecutorService listeningExecutorService, Callable<T> callable, final BlockingQueue<Future<T>> blockingQueue) {
        final ListenableFuture<T> submit = listeningExecutorService.submit((Callable) callable);
        submit.addListener(new Runnable() {
            public void run() {
                blockingQueue.add(submit);
            }
        }, directExecutor());
        return submit;
    }

    @GwtIncompatible
    @Beta
    public static ThreadFactory platformThreadFactory() {
        Throwable e;
        if (!isAppEngine()) {
            return Executors.defaultThreadFactory();
        }
        try {
            return (ThreadFactory) Class.forName("com.google.appengine.api.ThreadManager").getMethod("currentRequestThreadFactory", new Class[0]).invoke(null, new Object[0]);
        } catch (IllegalAccessException e2) {
            e = e2;
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e);
        } catch (ClassNotFoundException e3) {
            e = e3;
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e);
        } catch (NoSuchMethodException e4) {
            e = e4;
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e);
        } catch (InvocationTargetException e5) {
            throw Throwables.propagate(e5.getCause());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0022 A:{RETURN, PHI: r1 , ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:3:0x000a} */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0022 A:{RETURN, PHI: r1 , ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:3:0x000a} */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0022 A:{RETURN, PHI: r1 , ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:3:0x000a} */
    /* JADX WARNING: Missing block: B:7:0x0022, code:
            return r1;
     */
    @com.google.common.annotations.GwtIncompatible
    private static boolean isAppEngine() {
        /*
        r0 = "com.google.appengine.runtime.environment";
        r0 = java.lang.System.getProperty(r0);
        r1 = 0;
        if (r0 != 0) goto L_0x000a;
    L_0x0009:
        return r1;
    L_0x000a:
        r0 = "com.google.apphosting.api.ApiProxy";
        r0 = java.lang.Class.forName(r0);	 Catch:{ ClassNotFoundException -> 0x0022, ClassNotFoundException -> 0x0022, ClassNotFoundException -> 0x0022, ClassNotFoundException -> 0x0022 }
        r2 = "getCurrentEnvironment";
        r3 = new java.lang.Class[r1];	 Catch:{ ClassNotFoundException -> 0x0022, ClassNotFoundException -> 0x0022, ClassNotFoundException -> 0x0022, ClassNotFoundException -> 0x0022 }
        r0 = r0.getMethod(r2, r3);	 Catch:{ ClassNotFoundException -> 0x0022, ClassNotFoundException -> 0x0022, ClassNotFoundException -> 0x0022, ClassNotFoundException -> 0x0022 }
        r2 = 0;
        r3 = new java.lang.Object[r1];	 Catch:{ ClassNotFoundException -> 0x0022, ClassNotFoundException -> 0x0022, ClassNotFoundException -> 0x0022, ClassNotFoundException -> 0x0022 }
        r0 = r0.invoke(r2, r3);	 Catch:{ ClassNotFoundException -> 0x0022, ClassNotFoundException -> 0x0022, ClassNotFoundException -> 0x0022, ClassNotFoundException -> 0x0022 }
        if (r0 == 0) goto L_0x0022;
    L_0x0021:
        r1 = 1;
    L_0x0022:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.MoreExecutors.isAppEngine():boolean");
    }

    @GwtIncompatible
    static Thread newThread(String str, Runnable runnable) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(runnable);
        Thread newThread = platformThreadFactory().newThread(runnable);
        try {
            newThread.setName(str);
        } catch (SecurityException unused) {
            return newThread;
        }
    }

    @GwtIncompatible
    static Executor renamingDecorator(final Executor executor, final Supplier<String> supplier) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(supplier);
        if (isAppEngine()) {
            return executor;
        }
        return new Executor() {
            public void execute(Runnable runnable) {
                executor.execute(Callables.threadRenaming(runnable, supplier));
            }
        };
    }

    @GwtIncompatible
    static ExecutorService renamingDecorator(ExecutorService executorService, final Supplier<String> supplier) {
        Preconditions.checkNotNull(executorService);
        Preconditions.checkNotNull(supplier);
        if (isAppEngine()) {
            return executorService;
        }
        return new WrappingExecutorService(executorService) {
            protected <T> Callable<T> wrapTask(Callable<T> callable) {
                return Callables.threadRenaming((Callable) callable, supplier);
            }

            protected Runnable wrapTask(Runnable runnable) {
                return Callables.threadRenaming(runnable, supplier);
            }
        };
    }

    @GwtIncompatible
    static ScheduledExecutorService renamingDecorator(ScheduledExecutorService scheduledExecutorService, final Supplier<String> supplier) {
        Preconditions.checkNotNull(scheduledExecutorService);
        Preconditions.checkNotNull(supplier);
        if (isAppEngine()) {
            return scheduledExecutorService;
        }
        return new WrappingScheduledExecutorService(scheduledExecutorService) {
            protected <T> Callable<T> wrapTask(Callable<T> callable) {
                return Callables.threadRenaming((Callable) callable, supplier);
            }

            protected Runnable wrapTask(Runnable runnable) {
                return Callables.threadRenaming(runnable, supplier);
            }
        };
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    @Beta
    public static boolean shutdownAndAwaitTermination(ExecutorService executorService, long j, TimeUnit timeUnit) {
        j = timeUnit.toNanos(j) / 2;
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(j, TimeUnit.NANOSECONDS)) {
                executorService.shutdownNow();
                executorService.awaitTermination(j, TimeUnit.NANOSECONDS);
            }
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
        }
        return executorService.isTerminated();
    }

    static Executor rejectionPropagatingExecutor(final Executor executor, final AbstractFuture<?> abstractFuture) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(abstractFuture);
        if (executor == directExecutor()) {
            return executor;
        }
        return new Executor() {
            boolean thrownFromDelegate = true;

            public void execute(final Runnable runnable) {
                try {
                    executor.execute(new Runnable() {
                        public void run() {
                            AnonymousClass5.this.thrownFromDelegate = false;
                            runnable.run();
                        }
                    });
                } catch (Throwable e) {
                    if (this.thrownFromDelegate) {
                        abstractFuture.setException(e);
                    }
                }
            }
        };
    }
}
