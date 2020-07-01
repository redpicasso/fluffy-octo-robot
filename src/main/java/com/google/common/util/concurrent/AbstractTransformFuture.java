package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.ForOverride;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
abstract class AbstractTransformFuture<I, O, F, T> extends TrustedFuture<O> implements Runnable {
    @NullableDecl
    F function;
    @NullableDecl
    ListenableFuture<? extends I> inputFuture;

    private static final class AsyncTransformFuture<I, O> extends AbstractTransformFuture<I, O, AsyncFunction<? super I, ? extends O>, ListenableFuture<? extends O>> {
        AsyncTransformFuture(ListenableFuture<? extends I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction) {
            super(listenableFuture, asyncFunction);
        }

        ListenableFuture<? extends O> doTransform(AsyncFunction<? super I, ? extends O> asyncFunction, @NullableDecl I i) throws Exception {
            ListenableFuture<? extends O> apply = asyncFunction.apply(i);
            Preconditions.checkNotNull(apply, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)?");
            return apply;
        }

        void setResult(ListenableFuture<? extends O> listenableFuture) {
            setFuture(listenableFuture);
        }
    }

    private static final class TransformFuture<I, O> extends AbstractTransformFuture<I, O, Function<? super I, ? extends O>, O> {
        TransformFuture(ListenableFuture<? extends I> listenableFuture, Function<? super I, ? extends O> function) {
            super(listenableFuture, function);
        }

        @NullableDecl
        O doTransform(Function<? super I, ? extends O> function, @NullableDecl I i) {
            return function.apply(i);
        }

        void setResult(@NullableDecl O o) {
            set(o);
        }
    }

    @ForOverride
    @NullableDecl
    abstract T doTransform(F f, @NullableDecl I i) throws Exception;

    @ForOverride
    abstract void setResult(@NullableDecl T t);

    static <I, O> ListenableFuture<O> create(ListenableFuture<I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction, Executor executor) {
        Preconditions.checkNotNull(executor);
        ListenableFuture<O> asyncTransformFuture = new AsyncTransformFuture(listenableFuture, asyncFunction);
        listenableFuture.addListener(asyncTransformFuture, MoreExecutors.rejectionPropagatingExecutor(executor, asyncTransformFuture));
        return asyncTransformFuture;
    }

    static <I, O> ListenableFuture<O> create(ListenableFuture<I> listenableFuture, Function<? super I, ? extends O> function, Executor executor) {
        Preconditions.checkNotNull(function);
        ListenableFuture<O> transformFuture = new TransformFuture(listenableFuture, function);
        listenableFuture.addListener(transformFuture, MoreExecutors.rejectionPropagatingExecutor(executor, transformFuture));
        return transformFuture;
    }

    AbstractTransformFuture(ListenableFuture<? extends I> listenableFuture, F f) {
        this.inputFuture = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
        this.function = Preconditions.checkNotNull(f);
    }

    public final void run() {
        Future future = this.inputFuture;
        Object obj = this.function;
        int i = 1;
        int isCancelled = isCancelled() | (future == null ? 1 : 0);
        if (obj != null) {
            i = 0;
        }
        if ((isCancelled | i) == 0) {
            this.inputFuture = null;
            try {
                Object doTransform;
                try {
                    doTransform = doTransform(obj, Futures.getDone(future));
                    setResult(doTransform);
                } catch (Throwable th) {
                    doTransform = th;
                    setException(doTransform);
                } finally {
                    this.function = null;
                }
            } catch (CancellationException unused) {
                cancel(false);
            } catch (ExecutionException e) {
                setException(e.getCause());
            } catch (Throwable e2) {
                setException(e2);
            } catch (Throwable e22) {
                setException(e22);
            }
        }
    }

    protected final void afterDone() {
        maybePropagateCancellationTo(this.inputFuture);
        this.inputFuture = null;
        this.function = null;
    }

    protected String pendingToString() {
        String stringBuilder;
        ListenableFuture listenableFuture = this.inputFuture;
        Object obj = this.function;
        String pendingToString = super.pendingToString();
        if (listenableFuture != null) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("inputFuture=[");
            stringBuilder2.append(listenableFuture);
            stringBuilder2.append("], ");
            stringBuilder = stringBuilder2.toString();
        } else {
            stringBuilder = "";
        }
        if (obj != null) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(stringBuilder);
            stringBuilder3.append("function=[");
            stringBuilder3.append(obj);
            stringBuilder3.append("]");
            return stringBuilder3.toString();
        } else if (pendingToString == null) {
            return null;
        } else {
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append(stringBuilder);
            stringBuilder4.append(pendingToString);
            return stringBuilder4.toString();
        }
    }
}
