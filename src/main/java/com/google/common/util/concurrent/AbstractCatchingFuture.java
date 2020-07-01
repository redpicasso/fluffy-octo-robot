package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.ForOverride;
import java.util.concurrent.Executor;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
abstract class AbstractCatchingFuture<V, X extends Throwable, F, T> extends TrustedFuture<V> implements Runnable {
    @NullableDecl
    Class<X> exceptionType;
    @NullableDecl
    F fallback;
    @NullableDecl
    ListenableFuture<? extends V> inputFuture;

    private static final class AsyncCatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, AsyncFunction<? super X, ? extends V>, ListenableFuture<? extends V>> {
        AsyncCatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> cls, AsyncFunction<? super X, ? extends V> asyncFunction) {
            super(listenableFuture, cls, asyncFunction);
        }

        ListenableFuture<? extends V> doFallback(AsyncFunction<? super X, ? extends V> asyncFunction, X x) throws Exception {
            ListenableFuture<? extends V> apply = asyncFunction.apply(x);
            Preconditions.checkNotNull(apply, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)?");
            return apply;
        }

        void setResult(ListenableFuture<? extends V> listenableFuture) {
            setFuture(listenableFuture);
        }
    }

    private static final class CatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, Function<? super X, ? extends V>, V> {
        CatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> cls, Function<? super X, ? extends V> function) {
            super(listenableFuture, cls, function);
        }

        @NullableDecl
        V doFallback(Function<? super X, ? extends V> function, X x) throws Exception {
            return function.apply(x);
        }

        void setResult(@NullableDecl V v) {
            set(v);
        }
    }

    @ForOverride
    @NullableDecl
    abstract T doFallback(F f, X x) throws Exception;

    @ForOverride
    abstract void setResult(@NullableDecl T t);

    static <V, X extends Throwable> ListenableFuture<V> create(ListenableFuture<? extends V> listenableFuture, Class<X> cls, Function<? super X, ? extends V> function, Executor executor) {
        ListenableFuture<V> catchingFuture = new CatchingFuture(listenableFuture, cls, function);
        listenableFuture.addListener(catchingFuture, MoreExecutors.rejectionPropagatingExecutor(executor, catchingFuture));
        return catchingFuture;
    }

    static <X extends Throwable, V> ListenableFuture<V> create(ListenableFuture<? extends V> listenableFuture, Class<X> cls, AsyncFunction<? super X, ? extends V> asyncFunction, Executor executor) {
        ListenableFuture<V> asyncCatchingFuture = new AsyncCatchingFuture(listenableFuture, cls, asyncFunction);
        listenableFuture.addListener(asyncCatchingFuture, MoreExecutors.rejectionPropagatingExecutor(executor, asyncCatchingFuture));
        return asyncCatchingFuture;
    }

    AbstractCatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> cls, F f) {
        this.inputFuture = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
        this.exceptionType = (Class) Preconditions.checkNotNull(cls);
        this.fallback = Preconditions.checkNotNull(f);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x003e  */
    public final void run() {
        /*
        r7 = this;
        r0 = r7.inputFuture;
        r1 = r7.exceptionType;
        r2 = r7.fallback;
        r3 = 1;
        r4 = 0;
        if (r0 != 0) goto L_0x000c;
    L_0x000a:
        r5 = 1;
        goto L_0x000d;
    L_0x000c:
        r5 = 0;
    L_0x000d:
        if (r1 != 0) goto L_0x0011;
    L_0x000f:
        r6 = 1;
        goto L_0x0012;
    L_0x0011:
        r6 = 0;
    L_0x0012:
        r5 = r5 | r6;
        if (r2 != 0) goto L_0x0016;
    L_0x0015:
        goto L_0x0017;
    L_0x0016:
        r3 = 0;
    L_0x0017:
        r3 = r3 | r5;
        r4 = r7.isCancelled();
        r3 = r3 | r4;
        if (r3 == 0) goto L_0x0020;
    L_0x001f:
        return;
    L_0x0020:
        r3 = 0;
        r7.inputFuture = r3;
        r0 = com.google.common.util.concurrent.Futures.getDone(r0);	 Catch:{ ExecutionException -> 0x002c, Throwable -> 0x002a }
        r4 = r0;
        r0 = r3;
        goto L_0x0038;
    L_0x002a:
        r0 = move-exception;
        goto L_0x0037;
    L_0x002c:
        r0 = move-exception;
        r0 = r0.getCause();
        r0 = com.google.common.base.Preconditions.checkNotNull(r0);
        r0 = (java.lang.Throwable) r0;
    L_0x0037:
        r4 = r3;
    L_0x0038:
        if (r0 != 0) goto L_0x003e;
    L_0x003a:
        r7.set(r4);
        return;
    L_0x003e:
        r1 = com.google.common.util.concurrent.Platform.isInstanceOfThrowableClass(r0, r1);
        if (r1 != 0) goto L_0x0048;
    L_0x0044:
        r7.setException(r0);
        return;
    L_0x0048:
        r0 = r7.doFallback(r2, r0);	 Catch:{ Throwable -> 0x0056 }
        r7.exceptionType = r3;
        r7.fallback = r3;
        r7.setResult(r0);
        return;
    L_0x0054:
        r0 = move-exception;
        goto L_0x005f;
    L_0x0056:
        r0 = move-exception;
        r7.setException(r0);	 Catch:{ all -> 0x0054 }
        r7.exceptionType = r3;
        r7.fallback = r3;
        return;
    L_0x005f:
        r7.exceptionType = r3;
        r7.fallback = r3;
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractCatchingFuture.run():void");
    }

    protected String pendingToString() {
        String stringBuilder;
        ListenableFuture listenableFuture = this.inputFuture;
        Class cls = this.exceptionType;
        Object obj = this.fallback;
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
        if (cls != null && obj != null) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(stringBuilder);
            stringBuilder3.append("exceptionType=[");
            stringBuilder3.append(cls);
            stringBuilder3.append("], fallback=[");
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

    protected final void afterDone() {
        maybePropagateCancellationTo(this.inputFuture);
        this.inputFuture = null;
        this.exceptionType = null;
        this.fallback = null;
    }
}
