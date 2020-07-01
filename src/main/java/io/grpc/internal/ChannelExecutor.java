package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
class ChannelExecutor {
    private static final Logger log = Logger.getLogger(ChannelExecutor.class.getName());
    @GuardedBy("lock")
    private boolean draining;
    private final Object lock = new Object();
    @GuardedBy("lock")
    private final Queue<Runnable> queue = new ArrayDeque();

    ChannelExecutor() {
    }

    /* JADX WARNING: Missing block: B:18:?, code:
            r3.run();
     */
    /* JADX WARNING: Missing block: B:20:0x0024, code:
            r2 = move-exception;
     */
    /* JADX WARNING: Missing block: B:21:0x0025, code:
            handleUncaughtThrowable(r2);
     */
    final void drain() {
        /*
        r4 = this;
        r0 = 0;
        r1 = 0;
    L_0x0002:
        r2 = r4.lock;
        monitor-enter(r2);
        r3 = 1;
        if (r1 != 0) goto L_0x0011;
    L_0x0008:
        r1 = r4.draining;	 Catch:{ all -> 0x0029 }
        if (r1 == 0) goto L_0x000e;
    L_0x000c:
        monitor-exit(r2);	 Catch:{ all -> 0x0029 }
        return;
    L_0x000e:
        r4.draining = r3;	 Catch:{ all -> 0x0029 }
        r1 = 1;
    L_0x0011:
        r3 = r4.queue;	 Catch:{ all -> 0x0029 }
        r3 = r3.poll();	 Catch:{ all -> 0x0029 }
        r3 = (java.lang.Runnable) r3;	 Catch:{ all -> 0x0029 }
        if (r3 != 0) goto L_0x001f;
    L_0x001b:
        r4.draining = r0;	 Catch:{ all -> 0x0029 }
        monitor-exit(r2);	 Catch:{ all -> 0x0029 }
        return;
    L_0x001f:
        monitor-exit(r2);	 Catch:{ all -> 0x0029 }
        r3.run();	 Catch:{ Throwable -> 0x0024 }
        goto L_0x0002;
    L_0x0024:
        r2 = move-exception;
        r4.handleUncaughtThrowable(r2);
        goto L_0x0002;
    L_0x0029:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0029 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.ChannelExecutor.drain():void");
    }

    final ChannelExecutor executeLater(Runnable runnable) {
        synchronized (this.lock) {
            this.queue.add(Preconditions.checkNotNull(runnable, "runnable is null"));
        }
        return this;
    }

    @VisibleForTesting
    final int numPendingTasks() {
        int size;
        synchronized (this.lock) {
            size = this.queue.size();
        }
        return size;
    }

    void handleUncaughtThrowable(Throwable th) {
        log.log(Level.WARNING, "Runnable threw exception in ChannelExecutor", th);
    }
}
