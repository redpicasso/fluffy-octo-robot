package io.opencensus.trace.export;

import io.opencensus.common.Timestamp;
import io.opencensus.trace.Annotation;
import io.opencensus.trace.MessageEvent;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.SpanId;
import io.opencensus.trace.Status;
import io.opencensus.trace.export.SpanData.Attributes;
import io.opencensus.trace.export.SpanData.Links;
import io.opencensus.trace.export.SpanData.TimedEvents;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_SpanData extends SpanData {
    private final TimedEvents<Annotation> annotations;
    private final Attributes attributes;
    private final Integer childSpanCount;
    private final SpanContext context;
    private final Timestamp endTimestamp;
    private final Boolean hasRemoteParent;
    private final Links links;
    private final TimedEvents<MessageEvent> messageEvents;
    private final String name;
    private final SpanId parentSpanId;
    private final Timestamp startTimestamp;
    private final Status status;

    AutoValue_SpanData(SpanContext spanContext, @Nullable SpanId spanId, @Nullable Boolean bool, String str, Timestamp timestamp, Attributes attributes, TimedEvents<Annotation> timedEvents, TimedEvents<MessageEvent> timedEvents2, Links links, @Nullable Integer num, @Nullable Status status, @Nullable Timestamp timestamp2) {
        if (spanContext != null) {
            this.context = spanContext;
            this.parentSpanId = spanId;
            this.hasRemoteParent = bool;
            if (str != null) {
                this.name = str;
                if (timestamp != null) {
                    this.startTimestamp = timestamp;
                    if (attributes != null) {
                        this.attributes = attributes;
                        if (timedEvents != null) {
                            this.annotations = timedEvents;
                            if (timedEvents2 != null) {
                                this.messageEvents = timedEvents2;
                                if (links != null) {
                                    this.links = links;
                                    this.childSpanCount = num;
                                    this.status = status;
                                    this.endTimestamp = timestamp2;
                                    return;
                                }
                                throw new NullPointerException("Null links");
                            }
                            throw new NullPointerException("Null messageEvents");
                        }
                        throw new NullPointerException("Null annotations");
                    }
                    throw new NullPointerException("Null attributes");
                }
                throw new NullPointerException("Null startTimestamp");
            }
            throw new NullPointerException("Null name");
        }
        throw new NullPointerException("Null context");
    }

    public SpanContext getContext() {
        return this.context;
    }

    @Nullable
    public SpanId getParentSpanId() {
        return this.parentSpanId;
    }

    @Nullable
    public Boolean getHasRemoteParent() {
        return this.hasRemoteParent;
    }

    public String getName() {
        return this.name;
    }

    public Timestamp getStartTimestamp() {
        return this.startTimestamp;
    }

    public Attributes getAttributes() {
        return this.attributes;
    }

    public TimedEvents<Annotation> getAnnotations() {
        return this.annotations;
    }

    public TimedEvents<MessageEvent> getMessageEvents() {
        return this.messageEvents;
    }

    public Links getLinks() {
        return this.links;
    }

    @Nullable
    public Integer getChildSpanCount() {
        return this.childSpanCount;
    }

    @Nullable
    public Status getStatus() {
        return this.status;
    }

    @Nullable
    public Timestamp getEndTimestamp() {
        return this.endTimestamp;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SpanData{context=");
        stringBuilder.append(this.context);
        stringBuilder.append(", parentSpanId=");
        stringBuilder.append(this.parentSpanId);
        stringBuilder.append(", hasRemoteParent=");
        stringBuilder.append(this.hasRemoteParent);
        stringBuilder.append(", name=");
        stringBuilder.append(this.name);
        stringBuilder.append(", startTimestamp=");
        stringBuilder.append(this.startTimestamp);
        stringBuilder.append(", attributes=");
        stringBuilder.append(this.attributes);
        stringBuilder.append(", annotations=");
        stringBuilder.append(this.annotations);
        stringBuilder.append(", messageEvents=");
        stringBuilder.append(this.messageEvents);
        stringBuilder.append(", links=");
        stringBuilder.append(this.links);
        stringBuilder.append(", childSpanCount=");
        stringBuilder.append(this.childSpanCount);
        stringBuilder.append(", status=");
        stringBuilder.append(this.status);
        stringBuilder.append(", endTimestamp=");
        stringBuilder.append(this.endTimestamp);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SpanData)) {
            return false;
        }
        SpanData spanData = (SpanData) obj;
        if (this.context.equals(spanData.getContext())) {
            SpanId spanId = this.parentSpanId;
            if (spanId != null ? !spanId.equals(spanData.getParentSpanId()) : spanData.getParentSpanId() != null) {
                Boolean bool = this.hasRemoteParent;
                if (bool != null ? !bool.equals(spanData.getHasRemoteParent()) : spanData.getHasRemoteParent() != null) {
                    if (this.name.equals(spanData.getName()) && this.startTimestamp.equals(spanData.getStartTimestamp()) && this.attributes.equals(spanData.getAttributes()) && this.annotations.equals(spanData.getAnnotations()) && this.messageEvents.equals(spanData.getMessageEvents()) && this.links.equals(spanData.getLinks())) {
                        Integer num = this.childSpanCount;
                        if (num != null ? !num.equals(spanData.getChildSpanCount()) : spanData.getChildSpanCount() != null) {
                            Status status = this.status;
                            if (status != null ? !status.equals(spanData.getStatus()) : spanData.getStatus() != null) {
                                Timestamp timestamp = this.endTimestamp;
                                if (timestamp != null) {
                                }
                            }
                        }
                    }
                }
            }
        }
        z = false;
        return z;
    }

    public int hashCode() {
        int hashCode = (this.context.hashCode() ^ 1000003) * 1000003;
        SpanId spanId = this.parentSpanId;
        int i = 0;
        hashCode = (hashCode ^ (spanId == null ? 0 : spanId.hashCode())) * 1000003;
        Boolean bool = this.hasRemoteParent;
        hashCode = (((((((((((((hashCode ^ (bool == null ? 0 : bool.hashCode())) * 1000003) ^ this.name.hashCode()) * 1000003) ^ this.startTimestamp.hashCode()) * 1000003) ^ this.attributes.hashCode()) * 1000003) ^ this.annotations.hashCode()) * 1000003) ^ this.messageEvents.hashCode()) * 1000003) ^ this.links.hashCode()) * 1000003;
        Integer num = this.childSpanCount;
        hashCode = (hashCode ^ (num == null ? 0 : num.hashCode())) * 1000003;
        Status status = this.status;
        hashCode = (hashCode ^ (status == null ? 0 : status.hashCode())) * 1000003;
        Timestamp timestamp = this.endTimestamp;
        if (timestamp != null) {
            i = timestamp.hashCode();
        }
        return hashCode ^ i;
    }
}
