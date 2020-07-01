package io.opencensus.trace.export;

import io.opencensus.common.Timestamp;
import io.opencensus.trace.export.SpanData.TimedEvent;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_SpanData_TimedEvent<T> extends TimedEvent<T> {
    private final T event;
    private final Timestamp timestamp;

    AutoValue_SpanData_TimedEvent(Timestamp timestamp, T t) {
        if (timestamp != null) {
            this.timestamp = timestamp;
            if (t != null) {
                this.event = t;
                return;
            }
            throw new NullPointerException("Null event");
        }
        throw new NullPointerException("Null timestamp");
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public T getEvent() {
        return this.event;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TimedEvent{timestamp=");
        stringBuilder.append(this.timestamp);
        stringBuilder.append(", event=");
        stringBuilder.append(this.event);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TimedEvent)) {
            return false;
        }
        TimedEvent timedEvent = (TimedEvent) obj;
        if (!(this.timestamp.equals(timedEvent.getTimestamp()) && this.event.equals(timedEvent.getEvent()))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.timestamp.hashCode() ^ 1000003) * 1000003) ^ this.event.hashCode();
    }
}
