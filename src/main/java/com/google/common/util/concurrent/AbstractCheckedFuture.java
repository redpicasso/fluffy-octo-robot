package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.util.concurrent.ForwardingListenableFuture.SimpleForwardingListenableFuture;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@GwtIncompatible
@Deprecated
@Beta
public abstract class AbstractCheckedFuture<V, X extends Exception> extends SimpleForwardingListenableFuture<V> implements CheckedFuture<V, X> {
    protected abstract X mapException(Exception exception);

    protected AbstractCheckedFuture(ListenableFuture<V> listenableFuture) {
        super(listenableFuture);
    }

    @CanIgnoreReturnValue
    public V checkedGet() throws Exception {
        Exception e;
        try {
            return get();
        } catch (Exception e2) {
            Thread.currentThread().interrupt();
            throw mapException(e2);
        } catch (CancellationException e3) {
            e2 = e3;
            throw mapException(e2);
        } catch (ExecutionException e4) {
            e2 = e4;
            throw mapException(e2);
        }
    }

    @CanIgnoreReturnValue
    public V checkedGet(long j, TimeUnit timeUnit) throws TimeoutException, Exception {
        Exception e;
        try {
            return get(j, timeUnit);
        } catch (Exception e2) {
            Thread.currentThread().interrupt();
            throw mapException(e2);
        } catch (CancellationException e3) {
            e2 = e3;
            throw mapException(e2);
        } catch (ExecutionException e4) {
            e2 = e4;
            throw mapException(e2);
        }
    }
}
