package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@GwtIncompatible
@Beta
public abstract class AbstractExecutionThreadService implements Service {
    private static final Logger logger = Logger.getLogger(AbstractExecutionThreadService.class.getName());
    private final Service delegate = new AbstractService() {
        protected final void doStart() {
            MoreExecutors.renamingDecorator(AbstractExecutionThreadService.this.executor(), new Supplier<String>() {
                public String get() {
                    return AbstractExecutionThreadService.this.serviceName();
                }
            }).execute(new Runnable() {
                public void run() {
                    try {
                        AbstractExecutionThreadService.this.startUp();
                        AnonymousClass1.this.notifyStarted();
                        if (AnonymousClass1.this.isRunning()) {
                            AbstractExecutionThreadService.this.run();
                        }
                        AbstractExecutionThreadService.this.shutDown();
                        AnonymousClass1.this.notifyStopped();
                    } catch (Throwable th) {
                        AnonymousClass1.this.notifyFailed(th);
                    }
                }
            });
        }

        protected void doStop() {
            AbstractExecutionThreadService.this.triggerShutdown();
        }

        public String toString() {
            return AbstractExecutionThreadService.this.toString();
        }
    };

    protected abstract void run() throws Exception;

    protected void shutDown() throws Exception {
    }

    protected void startUp() throws Exception {
    }

    protected void triggerShutdown() {
    }

    protected AbstractExecutionThreadService() {
    }

    protected Executor executor() {
        return new Executor() {
            public void execute(Runnable runnable) {
                MoreExecutors.newThread(AbstractExecutionThreadService.this.serviceName(), runnable).start();
            }
        };
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(serviceName());
        stringBuilder.append(" [");
        stringBuilder.append(state());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public final boolean isRunning() {
        return this.delegate.isRunning();
    }

    public final State state() {
        return this.delegate.state();
    }

    public final void addListener(Listener listener, Executor executor) {
        this.delegate.addListener(listener, executor);
    }

    public final Throwable failureCause() {
        return this.delegate.failureCause();
    }

    @CanIgnoreReturnValue
    public final Service startAsync() {
        this.delegate.startAsync();
        return this;
    }

    @CanIgnoreReturnValue
    public final Service stopAsync() {
        this.delegate.stopAsync();
        return this;
    }

    public final void awaitRunning() {
        this.delegate.awaitRunning();
    }

    public final void awaitRunning(long j, TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitRunning(j, timeUnit);
    }

    public final void awaitTerminated() {
        this.delegate.awaitTerminated();
    }

    public final void awaitTerminated(long j, TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitTerminated(j, timeUnit);
    }

    protected String serviceName() {
        return getClass().getSimpleName();
    }
}
