package io.grpc;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class Deadline implements Comparable<Deadline> {
    private static final long MAX_OFFSET = TimeUnit.DAYS.toNanos(36500);
    private static final long MIN_OFFSET = (-MAX_OFFSET);
    private static final SystemTicker SYSTEM_TICKER = new SystemTicker();
    private final long deadlineNanos;
    private volatile boolean expired;
    private final Ticker ticker;

    static abstract class Ticker {
        public abstract long read();

        Ticker() {
        }
    }

    private static class SystemTicker extends Ticker {
        private SystemTicker() {
        }

        public long read() {
            return System.nanoTime();
        }
    }

    public static Deadline after(long j, TimeUnit timeUnit) {
        return after(j, timeUnit, SYSTEM_TICKER);
    }

    static Deadline after(long j, TimeUnit timeUnit, Ticker ticker) {
        checkNotNull(timeUnit, "units");
        return new Deadline(ticker, timeUnit.toNanos(j), true);
    }

    private Deadline(Ticker ticker, long j, boolean z) {
        this(ticker, ticker.read(), j, z);
    }

    private Deadline(Ticker ticker, long j, long j2, boolean z) {
        this.ticker = ticker;
        j2 = Math.min(MAX_OFFSET, Math.max(MIN_OFFSET, j2));
        this.deadlineNanos = j + j2;
        boolean z2 = z && j2 <= 0;
        this.expired = z2;
    }

    public boolean isExpired() {
        if (!this.expired) {
            if (this.deadlineNanos - this.ticker.read() > 0) {
                return false;
            }
            this.expired = true;
        }
        return true;
    }

    public boolean isBefore(Deadline deadline) {
        return this.deadlineNanos - deadline.deadlineNanos < 0;
    }

    public Deadline minimum(Deadline this_) {
        return isBefore(this_) ? this : this_;
    }

    public Deadline offset(long j, TimeUnit timeUnit) {
        return j == 0 ? this : new Deadline(this.ticker, this.deadlineNanos, timeUnit.toNanos(j), isExpired());
    }

    public long timeRemaining(TimeUnit timeUnit) {
        long read = this.ticker.read();
        if (!this.expired && this.deadlineNanos - read <= 0) {
            this.expired = true;
        }
        return timeUnit.convert(this.deadlineNanos - read, TimeUnit.NANOSECONDS);
    }

    public ScheduledFuture<?> runOnExpiration(Runnable runnable, ScheduledExecutorService scheduledExecutorService) {
        checkNotNull(runnable, "task");
        checkNotNull(scheduledExecutorService, "scheduler");
        return scheduledExecutorService.schedule(runnable, this.deadlineNanos - this.ticker.read(), TimeUnit.NANOSECONDS);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(timeRemaining(TimeUnit.NANOSECONDS));
        stringBuilder.append(" ns from now");
        return stringBuilder.toString();
    }

    public int compareTo(Deadline deadline) {
        int i = ((this.deadlineNanos - deadline.deadlineNanos) > 0 ? 1 : ((this.deadlineNanos - deadline.deadlineNanos) == 0 ? 0 : -1));
        if (i < 0) {
            return -1;
        }
        return i > 0 ? 1 : 0;
    }

    private static <T> T checkNotNull(T t, Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.valueOf(obj));
    }
}
