package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.Monitor.Guard;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.ForOverride;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
@Beta
public abstract class AbstractService implements Service {
    private static final Event<Listener> RUNNING_EVENT = new Event<Listener>() {
        public String toString() {
            return "running()";
        }

        public void call(Listener listener) {
            listener.running();
        }
    };
    private static final Event<Listener> STARTING_EVENT = new Event<Listener>() {
        public String toString() {
            return "starting()";
        }

        public void call(Listener listener) {
            listener.starting();
        }
    };
    private static final Event<Listener> STOPPING_FROM_RUNNING_EVENT = stoppingEvent(State.RUNNING);
    private static final Event<Listener> STOPPING_FROM_STARTING_EVENT = stoppingEvent(State.STARTING);
    private static final Event<Listener> TERMINATED_FROM_NEW_EVENT = terminatedEvent(State.NEW);
    private static final Event<Listener> TERMINATED_FROM_RUNNING_EVENT = terminatedEvent(State.RUNNING);
    private static final Event<Listener> TERMINATED_FROM_STOPPING_EVENT = terminatedEvent(State.STOPPING);
    private final Guard hasReachedRunning = new HasReachedRunningGuard();
    private final Guard isStartable = new IsStartableGuard();
    private final Guard isStoppable = new IsStoppableGuard();
    private final Guard isStopped = new IsStoppedGuard();
    private final ListenerCallQueue<Listener> listeners = new ListenerCallQueue();
    private final Monitor monitor = new Monitor();
    private volatile StateSnapshot snapshot = new StateSnapshot(State.NEW);

    private static final class StateSnapshot {
        @NullableDecl
        final Throwable failure;
        final boolean shutdownWhenStartupFinishes;
        final State state;

        StateSnapshot(State state) {
            this(state, false, null);
        }

        StateSnapshot(State state, boolean z, @NullableDecl Throwable th) {
            boolean z2 = false;
            boolean z3 = !z || state == State.STARTING;
            Preconditions.checkArgument(z3, "shutdownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.", (Object) state);
            if (((th != null ? 1 : 0) ^ (state == State.FAILED ? 1 : 0)) == 0) {
                z2 = true;
            }
            Preconditions.checkArgument(z2, "A failure cause should be set if and only if the state is failed.  Got %s and %s instead.", (Object) state, (Object) th);
            this.state = state;
            this.shutdownWhenStartupFinishes = z;
            this.failure = th;
        }

        State externalState() {
            if (this.shutdownWhenStartupFinishes && this.state == State.STARTING) {
                return State.STOPPING;
            }
            return this.state;
        }

        Throwable failureCause() {
            Preconditions.checkState(this.state == State.FAILED, "failureCause() is only valid if the service has failed, service is %s", this.state);
            return this.failure;
        }
    }

    private final class HasReachedRunningGuard extends Guard {
        HasReachedRunningGuard() {
            super(AbstractService.this.monitor);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(State.RUNNING) >= 0;
        }
    }

    private final class IsStartableGuard extends Guard {
        IsStartableGuard() {
            super(AbstractService.this.monitor);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state() == State.NEW;
        }
    }

    private final class IsStoppableGuard extends Guard {
        IsStoppableGuard() {
            super(AbstractService.this.monitor);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(State.RUNNING) <= 0;
        }
    }

    private final class IsStoppedGuard extends Guard {
        IsStoppedGuard() {
            super(AbstractService.this.monitor);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state().isTerminal();
        }
    }

    @ForOverride
    protected abstract void doStart();

    @ForOverride
    protected abstract void doStop();

    private static Event<Listener> terminatedEvent(final State state) {
        return new Event<Listener>() {
            public void call(Listener listener) {
                listener.terminated(state);
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("terminated({from = ");
                stringBuilder.append(state);
                stringBuilder.append("})");
                return stringBuilder.toString();
            }
        };
    }

    private static Event<Listener> stoppingEvent(final State state) {
        return new Event<Listener>() {
            public void call(Listener listener) {
                listener.stopping(state);
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("stopping({from = ");
                stringBuilder.append(state);
                stringBuilder.append("})");
                return stringBuilder.toString();
            }
        };
    }

    protected AbstractService() {
    }

    @CanIgnoreReturnValue
    public final Service startAsync() {
        if (this.monitor.enterIf(this.isStartable)) {
            try {
                this.snapshot = new StateSnapshot(State.STARTING);
                enqueueStartingEvent();
                doStart();
            } catch (Throwable th) {
                this.monitor.leave();
                dispatchListenerEvents();
            }
            this.monitor.leave();
            dispatchListenerEvents();
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Service ");
        stringBuilder.append(this);
        stringBuilder.append(" has already been started");
        throw new IllegalStateException(stringBuilder.toString());
    }

    @CanIgnoreReturnValue
    public final Service stopAsync() {
        if (this.monitor.enterIf(this.isStoppable)) {
            try {
                State state = state();
                StringBuilder stringBuilder;
                switch (state) {
                    case NEW:
                        this.snapshot = new StateSnapshot(State.TERMINATED);
                        enqueueTerminatedEvent(State.NEW);
                        break;
                    case STARTING:
                        this.snapshot = new StateSnapshot(State.STARTING, true, null);
                        enqueueStoppingEvent(State.STARTING);
                        break;
                    case RUNNING:
                        this.snapshot = new StateSnapshot(State.STOPPING);
                        enqueueStoppingEvent(State.RUNNING);
                        doStop();
                        break;
                    case STOPPING:
                    case TERMINATED:
                    case FAILED:
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("isStoppable is incorrectly implemented, saw: ");
                        stringBuilder.append(state);
                        throw new AssertionError(stringBuilder.toString());
                    default:
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Unexpected state: ");
                        stringBuilder.append(state);
                        throw new AssertionError(stringBuilder.toString());
                }
            } catch (Throwable th) {
                this.monitor.leave();
                dispatchListenerEvents();
            }
            this.monitor.leave();
            dispatchListenerEvents();
        }
        return this;
    }

    public final void awaitRunning() {
        this.monitor.enterWhenUninterruptibly(this.hasReachedRunning);
        try {
            checkCurrentState(State.RUNNING);
        } finally {
            this.monitor.leave();
        }
    }

    public final void awaitRunning(long j, TimeUnit timeUnit) throws TimeoutException {
        if (this.monitor.enterWhenUninterruptibly(this.hasReachedRunning, j, timeUnit)) {
            try {
                checkCurrentState(State.RUNNING);
            } finally {
                this.monitor.leave();
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Timed out waiting for ");
            stringBuilder.append(this);
            stringBuilder.append(" to reach the RUNNING state.");
            throw new TimeoutException(stringBuilder.toString());
        }
    }

    public final void awaitTerminated() {
        this.monitor.enterWhenUninterruptibly(this.isStopped);
        try {
            checkCurrentState(State.TERMINATED);
        } finally {
            this.monitor.leave();
        }
    }

    public final void awaitTerminated(long j, TimeUnit timeUnit) throws TimeoutException {
        if (this.monitor.enterWhenUninterruptibly(this.isStopped, j, timeUnit)) {
            try {
                checkCurrentState(State.TERMINATED);
            } finally {
                this.monitor.leave();
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Timed out waiting for ");
            stringBuilder.append(this);
            stringBuilder.append(" to reach a terminal state. Current state: ");
            stringBuilder.append(state());
            throw new TimeoutException(stringBuilder.toString());
        }
    }

    @GuardedBy("monitor")
    private void checkCurrentState(State state) {
        State state2 = state();
        if (state2 != state) {
            String str = " to be ";
            String str2 = "Expected the service ";
            if (state2 == State.FAILED) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(str2);
                stringBuilder.append(this);
                stringBuilder.append(str);
                stringBuilder.append(state);
                stringBuilder.append(", but the service has FAILED");
                throw new IllegalStateException(stringBuilder.toString(), failureCause());
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(str2);
            stringBuilder2.append(this);
            stringBuilder2.append(str);
            stringBuilder2.append(state);
            stringBuilder2.append(", but was ");
            stringBuilder2.append(state2);
            throw new IllegalStateException(stringBuilder2.toString());
        }
    }

    protected final void notifyStarted() {
        this.monitor.enter();
        try {
            if (this.snapshot.state == State.STARTING) {
                if (this.snapshot.shutdownWhenStartupFinishes) {
                    this.snapshot = new StateSnapshot(State.STOPPING);
                    doStop();
                } else {
                    this.snapshot = new StateSnapshot(State.RUNNING);
                    enqueueRunningEvent();
                }
                this.monitor.leave();
                dispatchListenerEvents();
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot notifyStarted() when the service is ");
            stringBuilder.append(this.snapshot.state);
            Throwable illegalStateException = new IllegalStateException(stringBuilder.toString());
            notifyFailed(illegalStateException);
            throw illegalStateException;
        } catch (Throwable th) {
            this.monitor.leave();
            dispatchListenerEvents();
        }
    }

    protected final void notifyStopped() {
        this.monitor.enter();
        try {
            State state = this.snapshot.state;
            if (state == State.STOPPING || state == State.RUNNING) {
                this.snapshot = new StateSnapshot(State.TERMINATED);
                enqueueTerminatedEvent(state);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot notifyStopped() when the service is ");
            stringBuilder.append(state);
            Throwable illegalStateException = new IllegalStateException(stringBuilder.toString());
            notifyFailed(illegalStateException);
            throw illegalStateException;
        } finally {
            this.monitor.leave();
            dispatchListenerEvents();
        }
    }

    protected final void notifyFailed(Throwable th) {
        Preconditions.checkNotNull(th);
        this.monitor.enter();
        try {
            State state = state();
            switch (state) {
                case NEW:
                case TERMINATED:
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed while in state:");
                    stringBuilder.append(state);
                    throw new IllegalStateException(stringBuilder.toString(), th);
                case STARTING:
                case RUNNING:
                case STOPPING:
                    this.snapshot = new StateSnapshot(State.FAILED, false, th);
                    enqueueFailedEvent(state, th);
                    break;
                case FAILED:
                    break;
                default:
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Unexpected state: ");
                    stringBuilder2.append(state);
                    throw new AssertionError(stringBuilder2.toString());
            }
            this.monitor.leave();
            dispatchListenerEvents();
        } catch (Throwable th2) {
            this.monitor.leave();
            dispatchListenerEvents();
        }
    }

    public final boolean isRunning() {
        return state() == State.RUNNING;
    }

    public final State state() {
        return this.snapshot.externalState();
    }

    public final Throwable failureCause() {
        return this.snapshot.failureCause();
    }

    public final void addListener(Listener listener, Executor executor) {
        this.listeners.addListener(listener, executor);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append(" [");
        stringBuilder.append(state());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private void dispatchListenerEvents() {
        if (!this.monitor.isOccupiedByCurrentThread()) {
            this.listeners.dispatch();
        }
    }

    private void enqueueStartingEvent() {
        this.listeners.enqueue(STARTING_EVENT);
    }

    private void enqueueRunningEvent() {
        this.listeners.enqueue(RUNNING_EVENT);
    }

    private void enqueueStoppingEvent(State state) {
        if (state == State.STARTING) {
            this.listeners.enqueue(STOPPING_FROM_STARTING_EVENT);
        } else if (state == State.RUNNING) {
            this.listeners.enqueue(STOPPING_FROM_RUNNING_EVENT);
        } else {
            throw new AssertionError();
        }
    }

    private void enqueueTerminatedEvent(State state) {
        int i = AnonymousClass6.$SwitchMap$com$google$common$util$concurrent$Service$State[state.ordinal()];
        if (i == 1) {
            this.listeners.enqueue(TERMINATED_FROM_NEW_EVENT);
        } else if (i == 3) {
            this.listeners.enqueue(TERMINATED_FROM_RUNNING_EVENT);
        } else if (i == 4) {
            this.listeners.enqueue(TERMINATED_FROM_STOPPING_EVENT);
        } else {
            throw new AssertionError();
        }
    }

    private void enqueueFailedEvent(final State state, final Throwable th) {
        this.listeners.enqueue(new Event<Listener>() {
            public void call(Listener listener) {
                listener.failed(state, th);
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("failed({from = ");
                stringBuilder.append(state);
                stringBuilder.append(", cause = ");
                stringBuilder.append(th);
                stringBuilder.append("})");
                return stringBuilder.toString();
            }
        });
    }
}
