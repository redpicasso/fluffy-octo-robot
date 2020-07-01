package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.errorprone.annotations.ForOverride;
import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
abstract class AggregateFuture<InputT, OutputT> extends TrustedFuture<OutputT> {
    private static final Logger logger = Logger.getLogger(AggregateFuture.class.getName());
    @NullableDecl
    private RunningState runningState;

    abstract class RunningState extends AggregateFutureState implements Runnable {
        private final boolean allMustSucceed;
        private final boolean collectsValues;
        private ImmutableCollection<? extends ListenableFuture<? extends InputT>> futures;

        abstract void collectOneValue(boolean z, int i, @NullableDecl InputT inputT);

        abstract void handleAllCompleted();

        void interruptTask() {
        }

        RunningState(ImmutableCollection<? extends ListenableFuture<? extends InputT>> immutableCollection, boolean z, boolean z2) {
            super(immutableCollection.size());
            this.futures = (ImmutableCollection) Preconditions.checkNotNull(immutableCollection);
            this.allMustSucceed = z;
            this.collectsValues = z2;
        }

        public final void run() {
            decrementCountAndMaybeComplete();
        }

        private void init() {
            if (this.futures.isEmpty()) {
                handleAllCompleted();
                return;
            }
            if (this.allMustSucceed) {
                int i = 0;
                Iterator it = this.futures.iterator();
                while (it.hasNext()) {
                    final ListenableFuture listenableFuture = (ListenableFuture) it.next();
                    int i2 = i + 1;
                    listenableFuture.addListener(new Runnable() {
                        public void run() {
                            try {
                                RunningState.this.handleOneInputDone(i, listenableFuture);
                            } finally {
                                RunningState.this.decrementCountAndMaybeComplete();
                            }
                        }
                    }, MoreExecutors.directExecutor());
                    i = i2;
                }
            } else {
                Iterator it2 = this.futures.iterator();
                while (it2.hasNext()) {
                    ((ListenableFuture) it2.next()).addListener(this, MoreExecutors.directExecutor());
                }
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:10:0x0027  */
        /* JADX WARNING: Removed duplicated region for block: B:18:? A:{SYNTHETIC, RETURN} */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x002e  */
        private void handleException(java.lang.Throwable r7) {
            /*
            r6 = this;
            com.google.common.base.Preconditions.checkNotNull(r7);
            r0 = r6.allMustSucceed;
            r1 = 1;
            r2 = 0;
            if (r0 == 0) goto L_0x001e;
        L_0x0009:
            r0 = com.google.common.util.concurrent.AggregateFuture.this;
            r0 = r0.setException(r7);
            if (r0 == 0) goto L_0x0015;
        L_0x0011:
            r6.releaseResourcesAfterFailure();
            goto L_0x001f;
        L_0x0015:
            r3 = r6.getOrInitSeenExceptions();
            r3 = com.google.common.util.concurrent.AggregateFuture.addCausalChain(r3, r7);
            goto L_0x0020;
        L_0x001e:
            r0 = 0;
        L_0x001f:
            r3 = 1;
        L_0x0020:
            r4 = r7 instanceof java.lang.Error;
            r5 = r6.allMustSucceed;
            if (r0 != 0) goto L_0x0027;
        L_0x0026:
            goto L_0x0028;
        L_0x0027:
            r1 = 0;
        L_0x0028:
            r0 = r5 & r1;
            r0 = r0 & r3;
            r0 = r0 | r4;
            if (r0 == 0) goto L_0x003e;
        L_0x002e:
            if (r4 == 0) goto L_0x0033;
        L_0x0030:
            r0 = "Input Future failed with Error";
            goto L_0x0035;
        L_0x0033:
            r0 = "Got more than one input Future failure. Logging failures after the first";
        L_0x0035:
            r1 = com.google.common.util.concurrent.AggregateFuture.logger;
            r2 = java.util.logging.Level.SEVERE;
            r1.log(r2, r0, r7);
        L_0x003e:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AggregateFuture.RunningState.handleException(java.lang.Throwable):void");
        }

        final void addInitialException(Set<Throwable> set) {
            if (!AggregateFuture.this.isCancelled()) {
                AggregateFuture.addCausalChain(set, AggregateFuture.this.trustedGetException());
            }
        }

        private void handleOneInputDone(int i, Future<? extends InputT> future) {
            boolean z = this.allMustSucceed || !AggregateFuture.this.isDone() || AggregateFuture.this.isCancelled();
            Preconditions.checkState(z, "Future was done before all dependencies completed");
            try {
                Preconditions.checkState(future.isDone(), "Tried to set value from future which is not done");
                if (this.allMustSucceed) {
                    if (future.isCancelled()) {
                        AggregateFuture.this.runningState = null;
                        AggregateFuture.this.cancel(false);
                        return;
                    }
                    Object done = Futures.getDone(future);
                    if (this.collectsValues) {
                        collectOneValue(this.allMustSucceed, i, done);
                    }
                } else if (this.collectsValues && !future.isCancelled()) {
                    collectOneValue(this.allMustSucceed, i, Futures.getDone(future));
                }
            } catch (ExecutionException e) {
                handleException(e.getCause());
            } catch (Throwable th) {
                handleException(th);
            }
        }

        private void decrementCountAndMaybeComplete() {
            int decrementRemainingAndGet = decrementRemainingAndGet();
            Preconditions.checkState(decrementRemainingAndGet >= 0, "Less than 0 remaining futures");
            if (decrementRemainingAndGet == 0) {
                processCompleted();
            }
        }

        private void processCompleted() {
            if ((this.collectsValues & (this.allMustSucceed ^ 1)) != 0) {
                int i = 0;
                Iterator it = this.futures.iterator();
                while (it.hasNext()) {
                    int i2 = i + 1;
                    handleOneInputDone(i, (ListenableFuture) it.next());
                    i = i2;
                }
            }
            handleAllCompleted();
        }

        @ForOverride
        @OverridingMethodsMustInvokeSuper
        void releaseResourcesAfterFailure() {
            this.futures = null;
        }
    }

    AggregateFuture() {
    }

    protected final void afterDone() {
        super.afterDone();
        RunningState runningState = this.runningState;
        if (runningState != null) {
            this.runningState = null;
            ImmutableCollection access$000 = runningState.futures;
            boolean wasInterrupted = wasInterrupted();
            if (wasInterrupted) {
                runningState.interruptTask();
            }
            if ((isCancelled() & (access$000 != null ? 1 : 0)) != 0) {
                Iterator it = access$000.iterator();
                while (it.hasNext()) {
                    ((ListenableFuture) it.next()).cancel(wasInterrupted);
                }
            }
        }
    }

    protected String pendingToString() {
        RunningState runningState = this.runningState;
        if (runningState == null) {
            return null;
        }
        ImmutableCollection access$000 = runningState.futures;
        if (access$000 == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("futures=[");
        stringBuilder.append(access$000);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    final void init(RunningState runningState) {
        this.runningState = runningState;
        runningState.init();
    }

    private static boolean addCausalChain(Set<Throwable> set, Throwable th) {
        while (th != null) {
            if (!set.add(th)) {
                return false;
            }
            th = th.getCause();
        }
        return true;
    }
}
