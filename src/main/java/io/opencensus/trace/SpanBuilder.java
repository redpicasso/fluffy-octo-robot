package io.opencensus.trace;

import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.MustBeClosed;
import io.opencensus.common.Scope;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.Nullable;

public abstract class SpanBuilder {

    static final class NoopSpanBuilder extends SpanBuilder {
        public SpanBuilder setParentLinks(List<Span> list) {
            return this;
        }

        public SpanBuilder setRecordEvents(boolean z) {
            return this;
        }

        public SpanBuilder setSampler(@Nullable Sampler sampler) {
            return this;
        }

        static NoopSpanBuilder createWithParent(String str, @Nullable Span span) {
            return new NoopSpanBuilder(str);
        }

        static NoopSpanBuilder createWithRemoteParent(String str, @Nullable SpanContext spanContext) {
            return new NoopSpanBuilder(str);
        }

        public Span startSpan() {
            return BlankSpan.INSTANCE;
        }

        private NoopSpanBuilder(String str) {
            Preconditions.checkNotNull(str, ConditionalUserProperty.NAME);
        }
    }

    public abstract SpanBuilder setParentLinks(List<Span> list);

    public abstract SpanBuilder setRecordEvents(boolean z);

    public abstract SpanBuilder setSampler(Sampler sampler);

    public abstract Span startSpan();

    @MustBeClosed
    public final Scope startScopedSpan() {
        return CurrentSpanUtils.withSpan(startSpan(), true);
    }

    public final void startSpanAndRun(Runnable runnable) {
        CurrentSpanUtils.withSpan(startSpan(), true, runnable).run();
    }

    public final <V> V startSpanAndCall(Callable<V> callable) throws Exception {
        return CurrentSpanUtils.withSpan(startSpan(), true, (Callable) callable).call();
    }
}
