package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

@GwtIncompatible
final class SequentialExecutor implements Executor {
    private static final Logger log = Logger.getLogger(SequentialExecutor.class.getName());
    private final Executor executor;
    @GuardedBy("queue")
    private final Deque<Runnable> queue = new ArrayDeque();
    private final QueueWorker worker = new QueueWorker(this, null);
    @GuardedBy("queue")
    private long workerRunCount = 0;
    @GuardedBy("queue")
    private WorkerRunningState workerRunningState = WorkerRunningState.IDLE;

    private final class QueueWorker implements Runnable {
        private QueueWorker() {
        }

        /* synthetic */ QueueWorker(SequentialExecutor sequentialExecutor, AnonymousClass1 anonymousClass1) {
            this();
        }

        public void run() {
            try {
                workOnQueue();
            } catch (Error e) {
                synchronized (SequentialExecutor.this.queue) {
                    SequentialExecutor.this.workerRunningState = WorkerRunningState.IDLE;
                    throw e;
                }
            }
        }

        /* JADX WARNING: Missing block: B:9:0x0016, code:
            if (r1 == 0) goto L_0x001f;
     */
        /* JADX WARNING: Missing block: B:10:0x0018, code:
            java.lang.Thread.currentThread().interrupt();
     */
        /* JADX WARNING: Missing block: B:11:0x001f, code:
            return;
     */
        /* JADX WARNING: Missing block: B:18:0x0043, code:
            if (r1 == 0) goto L_0x004c;
     */
        /* JADX WARNING: Missing block: B:19:0x0045, code:
            java.lang.Thread.currentThread().interrupt();
     */
        /* JADX WARNING: Missing block: B:20:0x004c, code:
            return;
     */
        /* JADX WARNING: Missing block: B:25:0x0052, code:
            r1 = r1 | java.lang.Thread.interrupted();
     */
        /* JADX WARNING: Missing block: B:27:?, code:
            r3.run();
     */
        private void workOnQueue() {
            /*
            r8 = this;
            r0 = 0;
            r1 = 0;
        L_0x0002:
            r2 = com.google.common.util.concurrent.SequentialExecutor.this;	 Catch:{ all -> 0x0076 }
            r2 = r2.queue;	 Catch:{ all -> 0x0076 }
            monitor-enter(r2);	 Catch:{ all -> 0x0076 }
            if (r0 != 0) goto L_0x002d;
        L_0x000b:
            r0 = com.google.common.util.concurrent.SequentialExecutor.this;	 Catch:{ all -> 0x0073 }
            r0 = r0.workerRunningState;	 Catch:{ all -> 0x0073 }
            r3 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.RUNNING;	 Catch:{ all -> 0x0073 }
            if (r0 != r3) goto L_0x0020;
        L_0x0015:
            monitor-exit(r2);	 Catch:{ all -> 0x0073 }
            if (r1 == 0) goto L_0x001f;
        L_0x0018:
            r0 = java.lang.Thread.currentThread();
            r0.interrupt();
        L_0x001f:
            return;
        L_0x0020:
            r0 = com.google.common.util.concurrent.SequentialExecutor.this;	 Catch:{ all -> 0x0073 }
            r0.workerRunCount = 1 + r0.workerRunCount;	 Catch:{ all -> 0x0073 }
            r0 = com.google.common.util.concurrent.SequentialExecutor.this;	 Catch:{ all -> 0x0073 }
            r3 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.RUNNING;	 Catch:{ all -> 0x0073 }
            r0.workerRunningState = r3;	 Catch:{ all -> 0x0073 }
            r0 = 1;
        L_0x002d:
            r3 = com.google.common.util.concurrent.SequentialExecutor.this;	 Catch:{ all -> 0x0073 }
            r3 = r3.queue;	 Catch:{ all -> 0x0073 }
            r3 = r3.poll();	 Catch:{ all -> 0x0073 }
            r3 = (java.lang.Runnable) r3;	 Catch:{ all -> 0x0073 }
            if (r3 != 0) goto L_0x004d;
        L_0x003b:
            r0 = com.google.common.util.concurrent.SequentialExecutor.this;	 Catch:{ all -> 0x0073 }
            r3 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.IDLE;	 Catch:{ all -> 0x0073 }
            r0.workerRunningState = r3;	 Catch:{ all -> 0x0073 }
            monitor-exit(r2);	 Catch:{ all -> 0x0073 }
            if (r1 == 0) goto L_0x004c;
        L_0x0045:
            r0 = java.lang.Thread.currentThread();
            r0.interrupt();
        L_0x004c:
            return;
        L_0x004d:
            monitor-exit(r2);	 Catch:{ all -> 0x0073 }
            r2 = java.lang.Thread.interrupted();	 Catch:{ all -> 0x0076 }
            r1 = r1 | r2;
            r3.run();	 Catch:{ RuntimeException -> 0x0057 }
            goto L_0x0002;
        L_0x0057:
            r2 = move-exception;
            r4 = com.google.common.util.concurrent.SequentialExecutor.log;	 Catch:{ all -> 0x0076 }
            r5 = java.util.logging.Level.SEVERE;	 Catch:{ all -> 0x0076 }
            r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0076 }
            r6.<init>();	 Catch:{ all -> 0x0076 }
            r7 = "Exception while executing runnable ";
            r6.append(r7);	 Catch:{ all -> 0x0076 }
            r6.append(r3);	 Catch:{ all -> 0x0076 }
            r3 = r6.toString();	 Catch:{ all -> 0x0076 }
            r4.log(r5, r3, r2);	 Catch:{ all -> 0x0076 }
            goto L_0x0002;
        L_0x0073:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0073 }
            throw r0;	 Catch:{ all -> 0x0076 }
        L_0x0076:
            r0 = move-exception;
            if (r1 == 0) goto L_0x0080;
        L_0x0079:
            r1 = java.lang.Thread.currentThread();
            r1.interrupt();
        L_0x0080:
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.SequentialExecutor.QueueWorker.workOnQueue():void");
        }
    }

    enum WorkerRunningState {
        IDLE,
        QUEUING,
        QUEUED,
        RUNNING
    }

    SequentialExecutor(Executor executor) {
        this.executor = (Executor) Preconditions.checkNotNull(executor);
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0056 A:{SYNTHETIC} */
    /* JADX WARNING: Missing block: B:9:0x0024, code:
            r8 = 1;
     */
    /* JADX WARNING: Missing block: B:11:?, code:
            r7.executor.execute(r7.worker);
     */
    /* JADX WARNING: Missing block: B:13:0x0031, code:
            if (r7.workerRunningState == com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUING) goto L_0x0034;
     */
    /* JADX WARNING: Missing block: B:14:0x0034, code:
            r8 = null;
     */
    /* JADX WARNING: Missing block: B:15:0x0035, code:
            if (r8 == null) goto L_0x0038;
     */
    /* JADX WARNING: Missing block: B:16:0x0037, code:
            return;
     */
    /* JADX WARNING: Missing block: B:17:0x0038, code:
            r4 = r7.queue;
     */
    /* JADX WARNING: Missing block: B:18:0x003a, code:
            monitor-enter(r4);
     */
    /* JADX WARNING: Missing block: B:21:0x003f, code:
            if (r7.workerRunCount != r1) goto L_0x004b;
     */
    /* JADX WARNING: Missing block: B:23:0x0045, code:
            if (r7.workerRunningState != com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUING) goto L_0x004b;
     */
    /* JADX WARNING: Missing block: B:24:0x0047, code:
            r7.workerRunningState = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUED;
     */
    /* JADX WARNING: Missing block: B:25:0x004b, code:
            monitor-exit(r4);
     */
    /* JADX WARNING: Missing block: B:26:0x004c, code:
            return;
     */
    /* JADX WARNING: Missing block: B:30:0x0050, code:
            r1 = e;
     */
    /* JADX WARNING: Missing block: B:31:0x0052, code:
            r1 = e;
     */
    /* JADX WARNING: Missing block: B:33:0x0055, code:
            monitor-enter(r7.queue);
     */
    /* JADX WARNING: Missing block: B:41:0x006b, code:
            r8 = null;
     */
    /* JADX WARNING: Missing block: B:43:0x006e, code:
            if ((r1 instanceof java.util.concurrent.RejectedExecutionException) == false) goto L_0x0074;
     */
    /* JADX WARNING: Missing block: B:46:0x0073, code:
            return;
     */
    /* JADX WARNING: Missing block: B:47:0x0074, code:
            throw r1;
     */
    public void execute(final java.lang.Runnable r8) {
        /*
        r7 = this;
        com.google.common.base.Preconditions.checkNotNull(r8);
        r0 = r7.queue;
        monitor-enter(r0);
        r1 = r7.workerRunningState;	 Catch:{ all -> 0x007f }
        r2 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.RUNNING;	 Catch:{ all -> 0x007f }
        if (r1 == r2) goto L_0x0078;
    L_0x000c:
        r1 = r7.workerRunningState;	 Catch:{ all -> 0x007f }
        r2 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUED;	 Catch:{ all -> 0x007f }
        if (r1 != r2) goto L_0x0013;
    L_0x0012:
        goto L_0x0078;
    L_0x0013:
        r1 = r7.workerRunCount;	 Catch:{ all -> 0x007f }
        r3 = new com.google.common.util.concurrent.SequentialExecutor$1;	 Catch:{ all -> 0x007f }
        r3.<init>(r8);	 Catch:{ all -> 0x007f }
        r8 = r7.queue;	 Catch:{ all -> 0x007f }
        r8.add(r3);	 Catch:{ all -> 0x007f }
        r8 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUING;	 Catch:{ all -> 0x007f }
        r7.workerRunningState = r8;	 Catch:{ all -> 0x007f }
        monitor-exit(r0);	 Catch:{ all -> 0x007f }
        r8 = 1;
        r0 = 0;
        r4 = r7.executor;	 Catch:{ RuntimeException -> 0x0052, Error -> 0x0050 }
        r5 = r7.worker;	 Catch:{ RuntimeException -> 0x0052, Error -> 0x0050 }
        r4.execute(r5);	 Catch:{ RuntimeException -> 0x0052, Error -> 0x0050 }
        r3 = r7.workerRunningState;
        r4 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUING;
        if (r3 == r4) goto L_0x0034;
    L_0x0033:
        goto L_0x0035;
    L_0x0034:
        r8 = 0;
    L_0x0035:
        if (r8 == 0) goto L_0x0038;
    L_0x0037:
        return;
    L_0x0038:
        r4 = r7.queue;
        monitor-enter(r4);
        r5 = r7.workerRunCount;	 Catch:{ all -> 0x004d }
        r8 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1));
        if (r8 != 0) goto L_0x004b;
    L_0x0041:
        r8 = r7.workerRunningState;	 Catch:{ all -> 0x004d }
        r0 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUING;	 Catch:{ all -> 0x004d }
        if (r8 != r0) goto L_0x004b;
    L_0x0047:
        r8 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUED;	 Catch:{ all -> 0x004d }
        r7.workerRunningState = r8;	 Catch:{ all -> 0x004d }
    L_0x004b:
        monitor-exit(r4);	 Catch:{ all -> 0x004d }
        return;
    L_0x004d:
        r8 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x004d }
        throw r8;
    L_0x0050:
        r1 = move-exception;
        goto L_0x0053;
    L_0x0052:
        r1 = move-exception;
    L_0x0053:
        r2 = r7.queue;
        monitor-enter(r2);
        r4 = r7.workerRunningState;	 Catch:{ all -> 0x0075 }
        r5 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.IDLE;	 Catch:{ all -> 0x0075 }
        if (r4 == r5) goto L_0x0062;
    L_0x005c:
        r4 = r7.workerRunningState;	 Catch:{ all -> 0x0075 }
        r5 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUING;	 Catch:{ all -> 0x0075 }
        if (r4 != r5) goto L_0x006b;
    L_0x0062:
        r4 = r7.queue;	 Catch:{ all -> 0x0075 }
        r3 = r4.removeLastOccurrence(r3);	 Catch:{ all -> 0x0075 }
        if (r3 == 0) goto L_0x006b;
    L_0x006a:
        goto L_0x006c;
    L_0x006b:
        r8 = 0;
    L_0x006c:
        r0 = r1 instanceof java.util.concurrent.RejectedExecutionException;	 Catch:{ all -> 0x0075 }
        if (r0 == 0) goto L_0x0074;
    L_0x0070:
        if (r8 != 0) goto L_0x0074;
    L_0x0072:
        monitor-exit(r2);	 Catch:{ all -> 0x0075 }
        return;
    L_0x0074:
        throw r1;	 Catch:{ all -> 0x0075 }
    L_0x0075:
        r8 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0075 }
        throw r8;
    L_0x0078:
        r1 = r7.queue;	 Catch:{ all -> 0x007f }
        r1.add(r8);	 Catch:{ all -> 0x007f }
        monitor-exit(r0);	 Catch:{ all -> 0x007f }
        return;
    L_0x007f:
        r8 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x007f }
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.SequentialExecutor.execute(java.lang.Runnable):void");
    }
}
