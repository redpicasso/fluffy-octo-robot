package io.grpc.internal;

import java.util.ArrayDeque;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

class SerializeReentrantCallsDirectExecutor implements Executor {
    private static final Logger log = Logger.getLogger(SerializeReentrantCallsDirectExecutor.class.getName());
    private boolean executing;
    private ArrayDeque<Runnable> taskQueue;

    SerializeReentrantCallsDirectExecutor() {
    }

    /* JADX WARNING: Missing block: B:6:0x0012, code:
            if (r6.taskQueue != null) goto L_0x0014;
     */
    /* JADX WARNING: Missing block: B:7:0x0014, code:
            completeQueuedTasks();
     */
    /* JADX WARNING: Missing block: B:8:0x0017, code:
            r6.executing = false;
     */
    /* JADX WARNING: Missing block: B:14:0x0037, code:
            if (r6.taskQueue == null) goto L_0x0017;
     */
    /* JADX WARNING: Missing block: B:22:?, code:
            return;
     */
    public void execute(java.lang.Runnable r7) {
        /*
        r6 = this;
        r0 = "'task' must not be null.";
        com.google.common.base.Preconditions.checkNotNull(r7, r0);
        r0 = r6.executing;
        if (r0 != 0) goto L_0x0044;
    L_0x0009:
        r0 = 1;
        r6.executing = r0;
        r0 = 0;
        r7.run();	 Catch:{ Throwable -> 0x001c }
        r7 = r6.taskQueue;
        if (r7 == 0) goto L_0x0017;
    L_0x0014:
        r6.completeQueuedTasks();
    L_0x0017:
        r6.executing = r0;
        goto L_0x0047;
    L_0x001a:
        r7 = move-exception;
        goto L_0x003a;
    L_0x001c:
        r1 = move-exception;
        r2 = log;	 Catch:{ all -> 0x001a }
        r3 = java.util.logging.Level.SEVERE;	 Catch:{ all -> 0x001a }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x001a }
        r4.<init>();	 Catch:{ all -> 0x001a }
        r5 = "Exception while executing runnable ";
        r4.append(r5);	 Catch:{ all -> 0x001a }
        r4.append(r7);	 Catch:{ all -> 0x001a }
        r7 = r4.toString();	 Catch:{ all -> 0x001a }
        r2.log(r3, r7, r1);	 Catch:{ all -> 0x001a }
        r7 = r6.taskQueue;
        if (r7 == 0) goto L_0x0017;
    L_0x0039:
        goto L_0x0014;
    L_0x003a:
        r1 = r6.taskQueue;
        if (r1 == 0) goto L_0x0041;
    L_0x003e:
        r6.completeQueuedTasks();
    L_0x0041:
        r6.executing = r0;
        throw r7;
    L_0x0044:
        r6.enqueue(r7);
    L_0x0047:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.SerializeReentrantCallsDirectExecutor.execute(java.lang.Runnable):void");
    }

    private void completeQueuedTasks() {
        while (true) {
            Runnable runnable = (Runnable) this.taskQueue.poll();
            if (runnable != null) {
                try {
                    runnable.run();
                } catch (Throwable th) {
                    Logger logger = log;
                    Level level = Level.SEVERE;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Exception while executing runnable ");
                    stringBuilder.append(runnable);
                    logger.log(level, stringBuilder.toString(), th);
                }
            } else {
                return;
            }
        }
    }

    private void enqueue(Runnable runnable) {
        if (this.taskQueue == null) {
            this.taskQueue = new ArrayDeque(4);
        }
        this.taskQueue.add(runnable);
    }
}
