package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

final class Rescheduler {
    private boolean enabled;
    private long runAtNanos;
    private final Runnable runnable;
    private final ScheduledExecutorService scheduler;
    private final Executor serializingExecutor;
    private final Stopwatch stopwatch;
    private ScheduledFuture<?> wakeUp;

    private final class ChannelFutureRunnable implements Runnable {
        private ChannelFutureRunnable() {
        }

        public void run() {
            if (Rescheduler.this.enabled) {
                long access$500 = Rescheduler.this.nanoTime();
                if (Rescheduler.this.runAtNanos - access$500 > 0) {
                    Rescheduler rescheduler = Rescheduler.this;
                    rescheduler.wakeUp = rescheduler.scheduler.schedule(new FutureRunnable(), Rescheduler.this.runAtNanos - access$500, TimeUnit.NANOSECONDS);
                } else {
                    Rescheduler.this.enabled = false;
                    Rescheduler.this.wakeUp = null;
                    Rescheduler.this.runnable.run();
                }
                return;
            }
            Rescheduler.this.wakeUp = null;
        }
    }

    private final class FutureRunnable implements Runnable {
        private FutureRunnable() {
        }

        public void run() {
            Rescheduler.this.serializingExecutor.execute(new ChannelFutureRunnable());
        }

        private boolean isEnabled() {
            return Rescheduler.this.enabled;
        }
    }

    Rescheduler(Runnable runnable, Executor executor, ScheduledExecutorService scheduledExecutorService, Stopwatch stopwatch) {
        this.runnable = runnable;
        this.serializingExecutor = executor;
        this.scheduler = scheduledExecutorService;
        this.stopwatch = stopwatch;
        stopwatch.start();
    }

    void reschedule(long j, TimeUnit timeUnit) {
        j = timeUnit.toNanos(j);
        long nanoTime = nanoTime() + j;
        this.enabled = true;
        if (nanoTime - this.runAtNanos < 0 || this.wakeUp == null) {
            ScheduledFuture scheduledFuture = this.wakeUp;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
            this.wakeUp = this.scheduler.schedule(new FutureRunnable(), j, TimeUnit.NANOSECONDS);
        }
        this.runAtNanos = nanoTime;
    }

    void cancel(boolean z) {
        this.enabled = false;
        if (z) {
            ScheduledFuture scheduledFuture = this.wakeUp;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
                this.wakeUp = null;
            }
        }
    }

    @VisibleForTesting
    static boolean isEnabled(Runnable runnable) {
        return ((FutureRunnable) runnable).isEnabled();
    }

    private long nanoTime() {
        return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
    }
}
