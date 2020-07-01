package com.google.firebase.firestore.util;

import com.google.firebase.firestore.util.AsyncQueue.DelayedTask;
import com.google.firebase.firestore.util.AsyncQueue.TimerId;
import java.util.Date;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class ExponentialBackoff {
    private final double backoffFactor;
    private long currentBaseMs;
    private final long initialDelayMs;
    private long lastAttemptTime = new Date().getTime();
    private final long maxDelayMs;
    private final AsyncQueue queue;
    private final TimerId timerId;
    private DelayedTask timerTask;

    public ExponentialBackoff(AsyncQueue asyncQueue, TimerId timerId, long j, double d, long j2) {
        this.queue = asyncQueue;
        this.timerId = timerId;
        this.initialDelayMs = j;
        this.backoffFactor = d;
        this.maxDelayMs = j2;
        reset();
    }

    public void reset() {
        this.currentBaseMs = 0;
    }

    public void resetToMax() {
        this.currentBaseMs = this.maxDelayMs;
    }

    public void backoffAndRun(Runnable runnable) {
        cancel();
        long max = Math.max(0, (this.currentBaseMs + jitterDelayMs()) - Math.max(0, new Date().getTime() - this.lastAttemptTime));
        if (this.currentBaseMs > 0) {
            Logger.debug(getClass().getSimpleName(), "Backing off for %d ms (base delay: %d ms, delay with jitter: %d ms, last attempt: %d ms ago)", Long.valueOf(max), Long.valueOf(this.currentBaseMs), Long.valueOf(r0), Long.valueOf(r2));
        }
        this.timerTask = this.queue.enqueueAfterDelay(this.timerId, max, ExponentialBackoff$$Lambda$1.lambdaFactory$(this, runnable));
        this.currentBaseMs = (long) (((double) this.currentBaseMs) * this.backoffFactor);
        long j = this.currentBaseMs;
        long j2 = this.initialDelayMs;
        if (j < j2) {
            this.currentBaseMs = j2;
            return;
        }
        j2 = this.maxDelayMs;
        if (j > j2) {
            this.currentBaseMs = j2;
        }
    }

    static /* synthetic */ void lambda$backoffAndRun$0(ExponentialBackoff exponentialBackoff, Runnable runnable) {
        exponentialBackoff.lastAttemptTime = new Date().getTime();
        runnable.run();
    }

    public void cancel() {
        DelayedTask delayedTask = this.timerTask;
        if (delayedTask != null) {
            delayedTask.cancel();
            this.timerTask = null;
        }
    }

    private long jitterDelayMs() {
        return (long) ((Math.random() - 0.5d) * ((double) this.currentBaseMs));
    }
}
