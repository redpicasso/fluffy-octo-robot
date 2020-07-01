package io.opencensus.trace;

import io.opencensus.trace.Link.Type;
import java.util.Map;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_Link extends Link {
    private final Map<String, AttributeValue> attributes;
    private final SpanId spanId;
    private final TraceId traceId;
    private final Type type;

    AutoValue_Link(TraceId traceId, SpanId spanId, Type type, Map<String, AttributeValue> map) {
        if (traceId != null) {
            this.traceId = traceId;
            if (spanId != null) {
                this.spanId = spanId;
                if (type != null) {
                    this.type = type;
                    if (map != null) {
                        this.attributes = map;
                        return;
                    }
                    throw new NullPointerException("Null attributes");
                }
                throw new NullPointerException("Null type");
            }
            throw new NullPointerException("Null spanId");
        }
        throw new NullPointerException("Null traceId");
    }

    public TraceId getTraceId() {
        return this.traceId;
    }

    public SpanId getSpanId() {
        return this.spanId;
    }

    public Type getType() {
        return this.type;
    }

    public Map<String, AttributeValue> getAttributes() {
        return this.attributes;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Link{traceId=");
        stringBuilder.append(this.traceId);
        stringBuilder.append(", spanId=");
        stringBuilder.append(this.spanId);
        stringBuilder.append(", type=");
        stringBuilder.append(this.type);
        stringBuilder.append(", attributes=");
        stringBuilder.append(this.attributes);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Link)) {
            return false;
        }
        Link link = (Link) obj;
        if (!(this.traceId.equals(link.getTraceId()) && this.spanId.equals(link.getSpanId()) && this.type.equals(link.getType()) && this.attributes.equals(link.getAttributes()))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((((((this.traceId.hashCode() ^ 1000003) * 1000003) ^ this.spanId.hashCode()) * 1000003) ^ this.type.hashCode()) * 1000003) ^ this.attributes.hashCode();
    }
}
