package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

@GwtIncompatible
final class FuturesGetChecked {
    private static final Ordering<Constructor<?>> WITH_STRING_PARAM_FIRST = Ordering.natural().onResultOf(new Function<Constructor<?>, Boolean>() {
        public Boolean apply(Constructor<?> constructor) {
            return Boolean.valueOf(Arrays.asList(constructor.getParameterTypes()).contains(String.class));
        }
    }).reverse();

    @VisibleForTesting
    interface GetCheckedTypeValidator {
        void validateClass(Class<? extends Exception> cls);
    }

    @VisibleForTesting
    static class GetCheckedTypeValidatorHolder {
        static final GetCheckedTypeValidator BEST_VALIDATOR = getBestValidator();
        static final String CLASS_VALUE_VALIDATOR_NAME;

        @IgnoreJRERequirement
        enum ClassValueValidator implements GetCheckedTypeValidator {
            INSTANCE;
            
            private static final ClassValue<Boolean> isValidClass = null;

            static {
                isValidClass = new ClassValue<Boolean>() {
                    protected Boolean computeValue(Class<?> cls) {
                        FuturesGetChecked.checkExceptionClassValidity(cls.asSubclass(Exception.class));
                        return Boolean.valueOf(true);
                    }
                };
            }

            public void validateClass(Class<? extends Exception> cls) {
                isValidClass.get(cls);
            }
        }

        enum WeakSetValidator implements GetCheckedTypeValidator {
            INSTANCE;
            
            private static final Set<WeakReference<Class<? extends Exception>>> validClasses = null;

            static {
                validClasses = new CopyOnWriteArraySet();
            }

            public void validateClass(Class<? extends Exception> cls) {
                for (WeakReference weakReference : validClasses) {
                    if (cls.equals(weakReference.get())) {
                        return;
                    }
                }
                FuturesGetChecked.checkExceptionClassValidity(cls);
                if (validClasses.size() > 1000) {
                    validClasses.clear();
                }
                validClasses.add(new WeakReference(cls));
            }
        }

        GetCheckedTypeValidatorHolder() {
        }

        static {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(GetCheckedTypeValidatorHolder.class.getName());
            stringBuilder.append("$ClassValueValidator");
            CLASS_VALUE_VALIDATOR_NAME = stringBuilder.toString();
        }

        static GetCheckedTypeValidator getBestValidator() {
            try {
                return (GetCheckedTypeValidator) Class.forName(CLASS_VALUE_VALIDATOR_NAME).getEnumConstants()[0];
            } catch (Throwable unused) {
                return FuturesGetChecked.weakSetValidator();
            }
        }
    }

    @CanIgnoreReturnValue
    static <V, X extends Exception> V getChecked(Future<V> future, Class<X> cls) throws Exception {
        return getChecked(bestGetCheckedTypeValidator(), future, cls);
    }

    @CanIgnoreReturnValue
    @VisibleForTesting
    static <V, X extends Exception> V getChecked(GetCheckedTypeValidator getCheckedTypeValidator, Future<V> future, Class<X> cls) throws Exception {
        getCheckedTypeValidator.validateClass(cls);
        try {
            return future.get();
        } catch (Throwable e) {
            Thread.currentThread().interrupt();
            throw newWithCause(cls, e);
        } catch (ExecutionException e2) {
            wrapAndThrowExceptionOrError(e2.getCause(), cls);
            throw new AssertionError();
        }
    }

    @CanIgnoreReturnValue
    static <V, X extends Exception> V getChecked(Future<V> future, Class<X> cls, long j, TimeUnit timeUnit) throws Exception {
        bestGetCheckedTypeValidator().validateClass(cls);
        try {
            return future.get(j, timeUnit);
        } catch (Throwable e) {
            Thread.currentThread().interrupt();
            throw newWithCause(cls, e);
        } catch (Throwable e2) {
            throw newWithCause(cls, e2);
        } catch (ExecutionException e3) {
            wrapAndThrowExceptionOrError(e3.getCause(), cls);
            throw new AssertionError();
        }
    }

    private static GetCheckedTypeValidator bestGetCheckedTypeValidator() {
        return GetCheckedTypeValidatorHolder.BEST_VALIDATOR;
    }

    @VisibleForTesting
    static GetCheckedTypeValidator weakSetValidator() {
        return WeakSetValidator.INSTANCE;
    }

    @VisibleForTesting
    static GetCheckedTypeValidator classValueValidator() {
        return ClassValueValidator.INSTANCE;
    }

    private static <X extends Exception> void wrapAndThrowExceptionOrError(Throwable th, Class<X> cls) throws Exception {
        if (th instanceof Error) {
            throw new ExecutionError((Error) th);
        } else if (th instanceof RuntimeException) {
            throw new UncheckedExecutionException(th);
        } else {
            throw newWithCause(cls, th);
        }
    }

    private static boolean hasConstructorUsableByGetChecked(Class<? extends Exception> cls) {
        try {
            newWithCause(cls, new Exception());
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private static <X extends Exception> X newWithCause(Class<X> cls, Throwable th) {
        for (Constructor newFromConstructor : preferringStrings(Arrays.asList(cls.getConstructors()))) {
            Exception exception = (Exception) newFromConstructor(newFromConstructor, th);
            if (exception != null) {
                if (exception.getCause() == null) {
                    exception.initCause(th);
                }
                return exception;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No appropriate constructor for exception of type ");
        stringBuilder.append(cls);
        stringBuilder.append(" in response to chained exception");
        throw new IllegalArgumentException(stringBuilder.toString(), th);
    }

    private static <X extends Exception> List<Constructor<X>> preferringStrings(List<Constructor<X>> list) {
        return WITH_STRING_PARAM_FIRST.sortedCopy(list);
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0030 A:{RETURN, ExcHandler: java.lang.IllegalArgumentException (unused java.lang.IllegalArgumentException), Splitter: B:11:0x002b} */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0030 A:{RETURN, ExcHandler: java.lang.IllegalArgumentException (unused java.lang.IllegalArgumentException), Splitter: B:11:0x002b} */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0030 A:{RETURN, ExcHandler: java.lang.IllegalArgumentException (unused java.lang.IllegalArgumentException), Splitter: B:11:0x002b} */
    /* JADX WARNING: Missing block: B:14:0x0030, code:
            return null;
     */
    @org.checkerframework.checker.nullness.compatqual.NullableDecl
    private static <X> X newFromConstructor(java.lang.reflect.Constructor<X> r6, java.lang.Throwable r7) {
        /*
        r0 = r6.getParameterTypes();
        r1 = r0.length;
        r1 = new java.lang.Object[r1];
        r2 = 0;
    L_0x0008:
        r3 = r0.length;
        r4 = 0;
        if (r2 >= r3) goto L_0x002b;
    L_0x000c:
        r3 = r0[r2];
        r5 = java.lang.String.class;
        r5 = r3.equals(r5);
        if (r5 == 0) goto L_0x001d;
    L_0x0016:
        r3 = r7.toString();
        r1[r2] = r3;
        goto L_0x0027;
    L_0x001d:
        r5 = java.lang.Throwable.class;
        r3 = r3.equals(r5);
        if (r3 == 0) goto L_0x002a;
    L_0x0025:
        r1[r2] = r7;
    L_0x0027:
        r2 = r2 + 1;
        goto L_0x0008;
    L_0x002a:
        return r4;
    L_0x002b:
        r6 = r6.newInstance(r1);	 Catch:{ IllegalArgumentException -> 0x0030, IllegalArgumentException -> 0x0030, IllegalArgumentException -> 0x0030, IllegalArgumentException -> 0x0030 }
        return r6;
    L_0x0030:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.FuturesGetChecked.newFromConstructor(java.lang.reflect.Constructor, java.lang.Throwable):X");
    }

    @VisibleForTesting
    static boolean isCheckedException(Class<? extends Exception> cls) {
        return RuntimeException.class.isAssignableFrom(cls) ^ 1;
    }

    @VisibleForTesting
    static void checkExceptionClassValidity(Class<? extends Exception> cls) {
        Preconditions.checkArgument(isCheckedException(cls), "Futures.getChecked exception type (%s) must not be a RuntimeException", (Object) cls);
        Preconditions.checkArgument(hasConstructorUsableByGetChecked(cls), "Futures.getChecked exception type (%s) must be an accessible class with an accessible constructor whose parameters (if any) must be of type String and/or Throwable", (Object) cls);
    }

    private FuturesGetChecked() {
    }
}
