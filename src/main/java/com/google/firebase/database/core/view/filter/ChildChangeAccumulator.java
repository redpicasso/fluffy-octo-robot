package com.google.firebase.database.core.view.filter;

import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.Event.EventType;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ChildChangeAccumulator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Map<ChildKey, Change> changeMap = new HashMap();

    public void trackChildChange(Change change) {
        EventType eventType = change.getEventType();
        ChildKey childKey = change.getChildKey();
        if (this.changeMap.containsKey(childKey)) {
            Change change2 = (Change) this.changeMap.get(childKey);
            EventType eventType2 = change2.getEventType();
            if (eventType == EventType.CHILD_ADDED && eventType2 == EventType.CHILD_REMOVED) {
                this.changeMap.put(change.getChildKey(), Change.childChangedChange(childKey, change.getIndexedNode(), change2.getIndexedNode()));
                return;
            } else if (eventType == EventType.CHILD_REMOVED && eventType2 == EventType.CHILD_ADDED) {
                this.changeMap.remove(childKey);
                return;
            } else if (eventType == EventType.CHILD_REMOVED && eventType2 == EventType.CHILD_CHANGED) {
                this.changeMap.put(childKey, Change.childRemovedChange(childKey, change2.getOldIndexedNode()));
                return;
            } else if (eventType == EventType.CHILD_CHANGED && eventType2 == EventType.CHILD_ADDED) {
                this.changeMap.put(childKey, Change.childAddedChange(childKey, change.getIndexedNode()));
                return;
            } else if (eventType == EventType.CHILD_CHANGED && eventType2 == EventType.CHILD_CHANGED) {
                this.changeMap.put(childKey, Change.childChangedChange(childKey, change.getIndexedNode(), change2.getOldIndexedNode()));
                return;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Illegal combination of changes: ");
                stringBuilder.append(change);
                stringBuilder.append(" occurred after ");
                stringBuilder.append(change2);
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        this.changeMap.put(change.getChildKey(), change);
    }

    public List<Change> getChanges() {
        return new ArrayList(this.changeMap.values());
    }
}
