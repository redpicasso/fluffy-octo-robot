package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

@GwtIncompatible
@Beta
public abstract class RateLimiter {
    @MonotonicNonNullDecl
    private volatile Object mutexDoNotUseDirectly;
    private final SleepingStopwatch stopwatch;

    static abstract class SleepingStopwatch {
        protected abstract long readMicros();

        protected abstract void sleepMicrosUninterruptibly(long j);

        protected SleepingStopwatch() {
        }

        public static SleepingStopwatch createFromSystemTimer() {
            return new SleepingStopwatch() {
                final Stopwatch stopwatch = Stopwatch.createStarted();

                protected long readMicros() {
                    return this.stopwatch.elapsed(TimeUnit.MICROSECONDS);
                }

                protected void sleepMicrosUninterruptibly(long j) {
                    if (j > 0) {
                        Uninterruptibles.sleepUninterruptibly(j, TimeUnit.MICROSECONDS);
                    }
                }
            };
        }
    }

    abstract double doGetRate();

    abstract void doSetRate(double d, long j);

    abstract long queryEarliestAvailable(long j);

    abstract long reserveEarliestAvailable(int i, long j);

    public static RateLimiter create(double d) {
        return create(d, SleepingStopwatch.createFromSystemTimer());
    }

    @VisibleForTesting
    static RateLimiter create(double d, SleepingStopwatch sleepingStopwatch) {
        RateLimiter smoothBursty = new SmoothBursty(sleepingStopwatch, 1.0d);
        smoothBursty.setRate(d);
        return smoothBursty;
    }

    public static RateLimiter create(double d, long j, TimeUnit timeUnit) {
        Preconditions.checkArgument(j >= 0, "warmupPeriod must not be negative: %s", j);
        return create(d, j, timeUnit, 3.0d, SleepingStopwatch.createFromSystemTimer());
    }

    @VisibleForTesting
    static RateLimiter create(double d, long j, TimeUnit timeUnit, double d2, SleepingStopwatch sleepingStopwatch) {
        RateLimiter smoothWarmingUp = new SmoothWarmingUp(sleepingStopwatch, j, timeUnit, d2);
        smoothWarmingUp.setRate(d);
        return smoothWarmingUp;
    }

    private Object mutex() {
        Object obj = this.mutexDoNotUseDirectly;
        if (obj == null) {
            synchronized (this) {
                obj = this.mutexDoNotUseDirectly;
                if (obj == null) {
                    obj = new Object();
                    this.mutexDoNotUseDirectly = obj;
                }
            }
        }
        return obj;
    }

    RateLimiter(SleepingStopwatch sleepingStopwatch) {
        this.stopwatch = (SleepingStopwatch) Preconditions.checkNotNull(sleepingStopwatch);
    }

    public final void setRate(double d) {
        boolean z = d > 0.0d && !Double.isNaN(d);
        Preconditions.checkArgument(z, "rate must be positive");
        synchronized (mutex()) {
            doSetRate(d, this.stopwatch.readMicros());
        }
    }

    public final double getRate() {
        double doGetRate;
        synchronized (mutex()) {
            doGetRate = doGetRate();
        }
        return doGetRate;
    }

    @CanIgnoreReturnValue
    public double acquire() {
        return acquire(1);
    }

    @CanIgnoreReturnValue
    public double acquire(int i) {
        long reserve = reserve(i);
        this.stopwatch.sleepMicrosUninterruptibly(reserve);
        return (((double) reserve) * 1.0d) / ((double) TimeUnit.SECONDS.toMicros(1));
    }

    final long reserve(int i) {
        long reserveAndGetWaitLength;
        checkPermits(i);
        synchronized (mutex()) {
            reserveAndGetWaitLength = reserveAndGetWaitLength(i, this.stopwatch.readMicros());
        }
        return reserveAndGetWaitLength;
    }

    public boolean tryAcquire(long j, TimeUnit timeUnit) {
        return tryAcquire(1, j, timeUnit);
    }

    public boolean tryAcquire(int i) {
        return tryAcquire(i, 0, TimeUnit.MICROSECONDS);
    }

    public boolean tryAcquire() {
        return tryAcquire(1, 0, TimeUnit.MICROSECONDS);
    }

    public boolean tryAcquire(int i, long j, TimeUnit timeUnit) {
        j = Math.max(timeUnit.toMicros(j), 0);
        checkPermits(i);
        synchronized (mutex()) {
            long readMicros = this.stopwatch.readMicros();
            if (canAcquire(readMicros, j)) {
                long reserveAndGetWaitLength = reserveAndGetWaitLength(i, readMicros);
                this.stopwatch.sleepMicrosUninterruptibly(reserveAndGetWaitLength);
                return true;
            }
            return false;
        }
    }

    private boolean canAcquire(long j, long j2) {
        return queryEarliestAvailable(j) - j2 <= j;
    }

    final long reserveAndGetWaitLength(int i, long j) {
        return Math.max(reserveEarliestAvailable(i, j) - j, 0);
    }

    public String toString() {
        return String.format(Locale.ROOT, "RateLimiter[stableRate=%3.1fqps]", new Object[]{Double.valueOf(getRate())});
    }

    private static void checkPermits(int i) {
        Preconditions.checkArgument(i > 0, "Requested permits (%s) must be positive", i);
    }
}