package io.opencensus.trace;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class SpanContext {
    public static final SpanContext INVALID = new SpanContext(TraceId.INVALID, SpanId.INVALID, TraceOptions.DEFAULT);
    private final SpanId spanId;
    private final TraceId traceId;
    private final TraceOptions traceOptions;

    public static SpanContext create(TraceId traceId, SpanId spanId, TraceOptions traceOptions) {
        return new SpanContext(traceId, spanId, traceOptions);
    }

    public TraceId getTraceId() {
        return this.traceId;
    }

    public SpanId getSpanId() {
        return this.spanId;
    }

    public TraceOptions getTraceOptions() {
        return this.traceOptions;
    }

    public boolean isValid() {
        return this.traceId.isValid() && this.spanId.isValid();
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SpanContext)) {
            return false;
        }
        SpanContext spanContext = (SpanContext) obj;
        if (!(this.traceId.equals(spanContext.traceId) && this.spanId.equals(spanContext.spanId) && this.traceOptions.equals(spanContext.traceOptions))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return Objects.hashCode(this.traceId, this.spanId, this.traceOptions);
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("traceId", this.traceId).add("spanId", this.spanId).add("traceOptions", this.traceOptions).toString();
    }

    private SpanContext(TraceId traceId, SpanId spanId, TraceOptions traceOptions) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.traceOptions = traceOptions;
    }
}
