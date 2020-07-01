package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
final class TimeoutFuture<V> extends TrustedFuture<V> {
    @NullableDecl
    private ListenableFuture<V> delegateRef;
    @NullableDecl
    private Future<?> timer;

    private static final class Fire<V> implements Runnable {
        @NullableDecl
        TimeoutFuture<V> timeoutFutureRef;

        Fire(TimeoutFuture<V> timeoutFuture) {
            this.timeoutFutureRef = timeoutFuture;
        }

        public void run() {
            TimeoutFuture timeoutFuture = this.timeoutFutureRef;
            if (timeoutFuture != null) {
                ListenableFuture access$000 = timeoutFuture.delegateRef;
                if (access$000 != null) {
                    this.timeoutFutureRef = null;
                    if (access$000.isDone()) {
                        timeoutFuture.setFuture(access$000);
                    } else {
                        try {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Future timed out: ");
                            stringBuilder.append(access$000);
                            timeoutFuture.setException(new TimeoutException(stringBuilder.toString()));
                        } finally {
                            access$000.cancel(true);
                        }
                    }
                }
            }
        }
    }

    static <V> ListenableFuture<V> create(ListenableFuture<V> listenableFuture, long j, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        ListenableFuture timeoutFuture = new TimeoutFuture(listenableFuture);
        Runnable fire = new Fire(timeoutFuture);
        timeoutFuture.timer = scheduledExecutorService.schedule(fire, j, timeUnit);
        listenableFuture.addListener(fire, MoreExecutors.directExecutor());
        return timeoutFuture;
    }

    private TimeoutFuture(ListenableFuture<V> listenableFuture) {
        this.delegateRef = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
    }

    protected String pendingToString() {
        ListenableFuture listenableFuture = this.delegateRef;
        if (listenableFuture == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("inputFuture=[");
        stringBuilder.append(listenableFuture);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    protected void afterDone() {
        maybePropagateCancellationTo(this.delegateRef);
        Future future = this.timer;
        if (future != null) {
            future.cancel(false);
        }
        this.delegateRef = null;
        this.timer = null;
    }
}
