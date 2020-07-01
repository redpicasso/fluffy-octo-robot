package com.facebook.common.executors;

import com.facebook.common.logging.FLog;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConstrainedExecutorService extends AbstractExecutorService {
    private static final Class<?> TAG = ConstrainedExecutorService.class;
    private final Executor mExecutor;
    private volatile int mMaxConcurrency;
    private final AtomicInteger mMaxQueueSize;
    private final String mName;
    private final AtomicInteger mPendingWorkers;
    private final Worker mTaskRunner;
    private final BlockingQueue<Runnable> mWorkQueue;

    private class Worker implements Runnable {
        private Worker() {
        }

        /* JADX WARNING: Missing block: B:17:?, code:
            return;
     */
        public void run() {
            /*
            r5 = this;
            r0 = "%s: worker finished; %d workers left";
            r1 = com.facebook.common.executors.ConstrainedExecutorService.this;	 Catch:{ all -> 0x0051 }
            r1 = r1.mWorkQueue;	 Catch:{ all -> 0x0051 }
            r1 = r1.poll();	 Catch:{ all -> 0x0051 }
            r1 = (java.lang.Runnable) r1;	 Catch:{ all -> 0x0051 }
            if (r1 == 0) goto L_0x0014;
        L_0x0010:
            r1.run();	 Catch:{ all -> 0x0051 }
            goto L_0x0023;
        L_0x0014:
            r1 = com.facebook.common.executors.ConstrainedExecutorService.TAG;	 Catch:{ all -> 0x0051 }
            r2 = "%s: Worker has nothing to run";
            r3 = com.facebook.common.executors.ConstrainedExecutorService.this;	 Catch:{ all -> 0x0051 }
            r3 = r3.mName;	 Catch:{ all -> 0x0051 }
            com.facebook.common.logging.FLog.v(r1, r2, r3);	 Catch:{ all -> 0x0051 }
        L_0x0023:
            r1 = com.facebook.common.executors.ConstrainedExecutorService.this;
            r1 = r1.mPendingWorkers;
            r1 = r1.decrementAndGet();
            r2 = com.facebook.common.executors.ConstrainedExecutorService.this;
            r2 = r2.mWorkQueue;
            r2 = r2.isEmpty();
            if (r2 != 0) goto L_0x003f;
        L_0x0039:
            r0 = com.facebook.common.executors.ConstrainedExecutorService.this;
            r0.startWorkerIfNeeded();
            goto L_0x0050;
        L_0x003f:
            r2 = com.facebook.common.executors.ConstrainedExecutorService.TAG;
            r3 = com.facebook.common.executors.ConstrainedExecutorService.this;
            r3 = r3.mName;
            r1 = java.lang.Integer.valueOf(r1);
            com.facebook.common.logging.FLog.v(r2, r0, r3, r1);
        L_0x0050:
            return;
        L_0x0051:
            r1 = move-exception;
            r2 = com.facebook.common.executors.ConstrainedExecutorService.this;
            r2 = r2.mPendingWorkers;
            r2 = r2.decrementAndGet();
            r3 = com.facebook.common.executors.ConstrainedExecutorService.this;
            r3 = r3.mWorkQueue;
            r3 = r3.isEmpty();
            if (r3 != 0) goto L_0x006e;
        L_0x0068:
            r0 = com.facebook.common.executors.ConstrainedExecutorService.this;
            r0.startWorkerIfNeeded();
            goto L_0x007f;
        L_0x006e:
            r3 = com.facebook.common.executors.ConstrainedExecutorService.TAG;
            r4 = com.facebook.common.executors.ConstrainedExecutorService.this;
            r4 = r4.mName;
            r2 = java.lang.Integer.valueOf(r2);
            com.facebook.common.logging.FLog.v(r3, r0, r4, r2);
        L_0x007f:
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.common.executors.ConstrainedExecutorService.Worker.run():void");
        }
    }

    public boolean isShutdown() {
        return false;
    }

    public boolean isTerminated() {
        return false;
    }

    public ConstrainedExecutorService(String str, int i, Executor executor, BlockingQueue<Runnable> blockingQueue) {
        if (i > 0) {
            this.mName = str;
            this.mExecutor = executor;
            this.mMaxConcurrency = i;
            this.mWorkQueue = blockingQueue;
            this.mTaskRunner = new Worker();
            this.mPendingWorkers = new AtomicInteger(0);
            this.mMaxQueueSize = new AtomicInteger(0);
            return;
        }
        throw new IllegalArgumentException("max concurrency must be > 0");
    }

    public static ConstrainedExecutorService newConstrainedExecutor(String str, int i, int i2, Executor executor) {
        return new ConstrainedExecutorService(str, i, executor, new LinkedBlockingQueue(i2));
    }

    public boolean isIdle() {
        return this.mWorkQueue.isEmpty() && this.mPendingWorkers.get() == 0;
    }

    public void execute(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("runnable parameter is null");
        } else if (this.mWorkQueue.offer(runnable)) {
            int size = this.mWorkQueue.size();
            int i = this.mMaxQueueSize.get();
            if (size > i && this.mMaxQueueSize.compareAndSet(i, size)) {
                FLog.v(TAG, "%s: max pending work in queue = %d", this.mName, Integer.valueOf(size));
            }
            startWorkerIfNeeded();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.mName);
            stringBuilder.append(" queue is full, size=");
            stringBuilder.append(this.mWorkQueue.size());
            throw new RejectedExecutionException(stringBuilder.toString());
        }
    }

    private void startWorkerIfNeeded() {
        int i = this.mPendingWorkers.get();
        while (i < this.mMaxConcurrency) {
            int i2 = i + 1;
            if (this.mPendingWorkers.compareAndSet(i, i2)) {
                FLog.v(TAG, "%s: starting worker %d of %d", this.mName, Integer.valueOf(i2), Integer.valueOf(this.mMaxConcurrency));
                this.mExecutor.execute(this.mTaskRunner);
                return;
            }
            FLog.v(TAG, "%s: race in startWorkerIfNeeded; retrying", this.mName);
            i = this.mPendingWorkers.get();
        }
    }

    public void shutdown() {
        throw new UnsupportedOperationException();
    }

    public List<Runnable> shutdownNow() {
        throw new UnsupportedOperationException();
    }

    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }
}
