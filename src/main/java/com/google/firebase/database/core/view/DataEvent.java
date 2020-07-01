package com.google.firebase.database.core.view;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.core.EventRegistration;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.view.Event.EventType;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class DataEvent implements Event {
    private final EventRegistration eventRegistration;
    private final EventType eventType;
    private final String prevName;
    private final DataSnapshot snapshot;

    public DataEvent(EventType eventType, EventRegistration eventRegistration, DataSnapshot dataSnapshot, String str) {
        this.eventType = eventType;
        this.eventRegistration = eventRegistration;
        this.snapshot = dataSnapshot;
        this.prevName = str;
    }

    public Path getPath() {
        Path path = this.snapshot.getRef().getPath();
        if (this.eventType == EventType.VALUE) {
            return path;
        }
        return path.getParent();
    }

    public DataSnapshot getSnapshot() {
        return this.snapshot;
    }

    public String getPreviousName() {
        return this.prevName;
    }

    public EventType getEventType() {
        return this.eventType;
    }

    public void fire() {
        this.eventRegistration.fireEvent(this);
    }

    public String toString() {
        String str = ": ";
        StringBuilder stringBuilder;
        if (this.eventType == EventType.VALUE) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(getPath());
            stringBuilder.append(str);
            stringBuilder.append(this.eventType);
            stringBuilder.append(str);
            stringBuilder.append(this.snapshot.getValue(true));
            return stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(getPath());
        stringBuilder.append(str);
        stringBuilder.append(this.eventType);
        stringBuilder.append(": { ");
        stringBuilder.append(this.snapshot.getKey());
        stringBuilder.append(str);
        stringBuilder.append(this.snapshot.getValue(true));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
