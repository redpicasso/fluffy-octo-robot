package io.opencensus.trace;

import io.grpc.Context;
import io.opencensus.common.Scope;
import io.opencensus.trace.unsafe.ContextUtils;
import java.util.concurrent.Callable;

final class CurrentSpanUtils {

    private static final class CallableInSpan<V> implements Callable<V> {
        private final Callable<V> callable;
        private final boolean endSpan;
        private final Span span;

        private CallableInSpan(Span span, Callable<V> callable, boolean z) {
            this.span = span;
            this.callable = callable;
            this.endSpan = z;
        }

        public V call() throws Exception {
            Context attach = Context.current().withValue(ContextUtils.CONTEXT_SPAN_KEY, this.span).attach();
            try {
                V call = this.callable.call();
                Context.current().detach(attach);
                if (this.endSpan) {
                    this.span.end();
                }
                return call;
            } catch (Throwable e) {
                CurrentSpanUtils.setErrorStatus(this.span, e);
                throw e;
            } catch (Throwable th) {
                Context.current().detach(attach);
                if (this.endSpan) {
                    this.span.end();
                }
            }
        }
    }

    private static final class RunnableInSpan implements Runnable {
        private final boolean endSpan;
        private final Runnable runnable;
        private final Span span;

        private RunnableInSpan(Span span, Runnable runnable, boolean z) {
            this.span = span;
            this.runnable = runnable;
            this.endSpan = z;
        }

        public void run() {
            Context attach = Context.current().withValue(ContextUtils.CONTEXT_SPAN_KEY, this.span).attach();
            try {
                this.runnable.run();
                Context.current().detach(attach);
                if (this.endSpan) {
                    this.span.end();
                }
            } catch (Throwable th) {
                Context.current().detach(attach);
                if (this.endSpan) {
                    this.span.end();
                }
            }
        }
    }

    private static final class ScopeInSpan implements Scope {
        private boolean endSpan;
        private final Context origContext;
        private final Span span;

        private ScopeInSpan(Span span, boolean z) {
            this.span = span;
            this.endSpan = z;
            this.origContext = Context.current().withValue(ContextUtils.CONTEXT_SPAN_KEY, span).attach();
        }

        public void close() {
            Context.current().detach(this.origContext);
            if (this.endSpan) {
                this.span.end();
            }
        }
    }

    private CurrentSpanUtils() {
    }

    static Span getCurrentSpan() {
        return (Span) ContextUtils.CONTEXT_SPAN_KEY.get();
    }

    static Scope withSpan(Span span, boolean z) {
        return new ScopeInSpan(span, z);
    }

    static Runnable withSpan(Span span, boolean z, Runnable runnable) {
        return new RunnableInSpan(span, runnable, z);
    }

    static <C> Callable<C> withSpan(Span span, boolean z, Callable<C> callable) {
        return new CallableInSpan(span, callable, z);
    }

    private static void setErrorStatus(Span span, Throwable th) {
        span.setStatus(Status.UNKNOWN.withDescription(th.getMessage() == null ? th.getClass().getSimpleName() : th.getMessage()));
    }
}
