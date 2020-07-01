package io.grpc.internal;

import com.google.common.base.Preconditions;
import io.grpc.InternalChannelz.ChannelStats;
import io.grpc.InternalChannelz.ChannelTrace;
import io.grpc.InternalChannelz.ChannelTrace.Event;
import io.grpc.InternalChannelz.ChannelTrace.Event.Builder;
import io.grpc.InternalChannelz.ChannelTrace.Event.Severity;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.concurrent.GuardedBy;

final class ChannelTracer {
    private final long channelCreationTimeNanos;
    @GuardedBy("lock")
    private final Collection<Event> events;
    @GuardedBy("lock")
    private int eventsLogged;
    private final Object lock = new Object();

    ChannelTracer(final int i, long j, String str) {
        Preconditions.checkArgument(i > 0, "maxEvents must be greater than zero");
        Preconditions.checkNotNull(str, "channelType");
        this.events = new ArrayDeque<Event>() {
            @GuardedBy("lock")
            public boolean add(Event event) {
                if (size() == i) {
                    removeFirst();
                }
                ChannelTracer.this.eventsLogged = ChannelTracer.this.eventsLogged + 1;
                return super.add(event);
            }
        };
        this.channelCreationTimeNanos = j;
        Builder builder = new Builder();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(" created");
        reportEvent(builder.setDescription(stringBuilder.toString()).setSeverity(Severity.CT_INFO).setTimestampNanos(j).build());
    }

    void reportEvent(Event event) {
        synchronized (this.lock) {
            this.events.add(event);
        }
    }

    void updateBuilder(ChannelStats.Builder builder) {
        int i;
        List arrayList;
        synchronized (this.lock) {
            i = this.eventsLogged;
            arrayList = new ArrayList(this.events);
        }
        builder.setChannelTrace(new ChannelTrace.Builder().setNumEventsLogged((long) i).setCreationTimeNanos(this.channelCreationTimeNanos).setEvents(arrayList).build());
    }
}
