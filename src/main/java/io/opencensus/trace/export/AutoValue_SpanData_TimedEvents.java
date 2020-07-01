package io.opencensus.trace.export;

import io.opencensus.trace.export.SpanData.TimedEvent;
import io.opencensus.trace.export.SpanData.TimedEvents;
import java.util.List;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_SpanData_TimedEvents<T> extends TimedEvents<T> {
    private final int droppedEventsCount;
    private final List<TimedEvent<T>> events;

    AutoValue_SpanData_TimedEvents(List<TimedEvent<T>> list, int i) {
        if (list != null) {
            this.events = list;
            this.droppedEventsCount = i;
            return;
        }
        throw new NullPointerException("Null events");
    }

    public List<TimedEvent<T>> getEvents() {
        return this.events;
    }

    public int getDroppedEventsCount() {
        return this.droppedEventsCount;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TimedEvents{events=");
        stringBuilder.append(this.events);
        stringBuilder.append(", droppedEventsCount=");
        stringBuilder.append(this.droppedEventsCount);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TimedEvents)) {
            return false;
        }
        TimedEvents timedEvents = (TimedEvents) obj;
        if (!(this.events.equals(timedEvents.getEvents()) && this.droppedEventsCount == timedEvents.getDroppedEventsCount())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.events.hashCode() ^ 1000003) * 1000003) ^ this.droppedEventsCount;
    }
}
