package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@GwtIncompatible
@Beta
public final class SimpleTimeLimiter implements TimeLimiter {
    private final ExecutorService executor;

    private SimpleTimeLimiter(ExecutorService executorService) {
        this.executor = (ExecutorService) Preconditions.checkNotNull(executorService);
    }

    public static SimpleTimeLimiter create(ExecutorService executorService) {
        return new SimpleTimeLimiter(executorService);
    }

    public <T> T newProxy(T t, Class<T> cls, long j, TimeUnit timeUnit) {
        Preconditions.checkNotNull(t);
        Preconditions.checkNotNull(cls);
        Preconditions.checkNotNull(timeUnit);
        checkPositiveTimeout(j);
        Preconditions.checkArgument(cls.isInterface(), "interfaceType must be an interface type");
        final Set findInterruptibleMethods = findInterruptibleMethods(cls);
        final T t2 = t;
        final long j2 = j;
        final TimeUnit timeUnit2 = timeUnit;
        return newProxy(cls, new InvocationHandler() {
            public Object invoke(Object obj, final Method method, final Object[] objArr) throws Throwable {
                return SimpleTimeLimiter.this.callWithTimeout(new Callable<Object>() {
                    public Object call() throws Exception {
                        try {
                            return method.invoke(t2, objArr);
                        } catch (Exception e) {
                            throw SimpleTimeLimiter.throwCause(e, false);
                        }
                    }
                }, j2, timeUnit2, findInterruptibleMethods.contains(method));
            }
        });
    }

    private static <T> T newProxy(Class<T> cls, InvocationHandler invocationHandler) {
        return cls.cast(Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, invocationHandler));
    }

    private <T> T callWithTimeout(Callable<T> callable, long j, TimeUnit timeUnit, boolean z) throws Exception {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeUnit);
        checkPositiveTimeout(j);
        Future submit = this.executor.submit(callable);
        if (!z) {
            return Uninterruptibles.getUninterruptibly(submit, j, timeUnit);
        }
        try {
            submit = submit.get(j, timeUnit);
            return submit;
        } catch (InterruptedException e) {
            submit.cancel(true);
            throw e;
        } catch (Exception e2) {
            throw throwCause(e2, true);
        } catch (Throwable e3) {
            submit.cancel(true);
            throw new UncheckedTimeoutException(e3);
        }
    }

    @CanIgnoreReturnValue
    public <T> T callWithTimeout(Callable<T> callable, long j, TimeUnit timeUnit) throws TimeoutException, InterruptedException, ExecutionException {
        TimeoutException e;
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeUnit);
        checkPositiveTimeout(j);
        Future submit = this.executor.submit(callable);
        try {
            submit = submit.get(j, timeUnit);
            return submit;
        } catch (InterruptedException e2) {
            e = e2;
            submit.cancel(true);
            throw e;
        } catch (TimeoutException e3) {
            e = e3;
            submit.cancel(true);
            throw e;
        } catch (ExecutionException e4) {
            wrapAndThrowExecutionExceptionOrError(e4.getCause());
            throw new AssertionError();
        }
    }

    @CanIgnoreReturnValue
    public <T> T callUninterruptiblyWithTimeout(Callable<T> callable, long j, TimeUnit timeUnit) throws TimeoutException, ExecutionException {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeUnit);
        checkPositiveTimeout(j);
        Future submit = this.executor.submit(callable);
        try {
            submit = Uninterruptibles.getUninterruptibly(submit, j, timeUnit);
            return submit;
        } catch (TimeoutException e) {
            submit.cancel(true);
            throw e;
        } catch (ExecutionException e2) {
            wrapAndThrowExecutionExceptionOrError(e2.getCause());
            throw new AssertionError();
        }
    }

    public void runWithTimeout(Runnable runnable, long j, TimeUnit timeUnit) throws TimeoutException, InterruptedException {
        TimeoutException e;
        Preconditions.checkNotNull(runnable);
        Preconditions.checkNotNull(timeUnit);
        checkPositiveTimeout(j);
        Future submit = this.executor.submit(runnable);
        try {
            submit.get(j, timeUnit);
        } catch (InterruptedException e2) {
            e = e2;
            submit.cancel(true);
            throw e;
        } catch (TimeoutException e3) {
            e = e3;
            submit.cancel(true);
            throw e;
        } catch (ExecutionException e4) {
            wrapAndThrowRuntimeExecutionExceptionOrError(e4.getCause());
            throw new AssertionError();
        }
    }

    public void runUninterruptiblyWithTimeout(Runnable runnable, long j, TimeUnit timeUnit) throws TimeoutException {
        Preconditions.checkNotNull(runnable);
        Preconditions.checkNotNull(timeUnit);
        checkPositiveTimeout(j);
        Future submit = this.executor.submit(runnable);
        try {
            Uninterruptibles.getUninterruptibly(submit, j, timeUnit);
        } catch (TimeoutException e) {
            submit.cancel(true);
            throw e;
        } catch (ExecutionException e2) {
            wrapAndThrowRuntimeExecutionExceptionOrError(e2.getCause());
            throw new AssertionError();
        }
    }

    private static Exception throwCause(Exception exception, boolean z) throws Exception {
        Throwable cause = exception.getCause();
        if (cause != null) {
            if (z) {
                cause.setStackTrace((StackTraceElement[]) ObjectArrays.concat(cause.getStackTrace(), exception.getStackTrace(), StackTraceElement.class));
            }
            if (cause instanceof Exception) {
                throw ((Exception) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw exception;
            }
        }
        throw exception;
    }

    private static Set<Method> findInterruptibleMethods(Class<?> cls) {
        Set<Method> newHashSet = Sets.newHashSet();
        for (Method method : cls.getMethods()) {
            if (declaresInterruptedEx(method)) {
                newHashSet.add(method);
            }
        }
        return newHashSet;
    }

    private static boolean declaresInterruptedEx(Method method) {
        for (Class cls : method.getExceptionTypes()) {
            if (cls == InterruptedException.class) {
                return true;
            }
        }
        return false;
    }

    private void wrapAndThrowExecutionExceptionOrError(Throwable th) throws ExecutionException {
        if (th instanceof Error) {
            throw new ExecutionError((Error) th);
        } else if (th instanceof RuntimeException) {
            throw new UncheckedExecutionException(th);
        } else {
            throw new ExecutionException(th);
        }
    }

    private void wrapAndThrowRuntimeExecutionExceptionOrError(Throwable th) {
        if (th instanceof Error) {
            throw new ExecutionError((Error) th);
        }
        throw new UncheckedExecutionException(th);
    }

    private static void checkPositiveTimeout(long j) {
        Preconditions.checkArgument(j > 0, "timeout must be positive: %s", j);
    }
}
