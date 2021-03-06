package com.google.firebase.database.core.view;

import com.google.firebase.database.core.EventRegistration;
import com.google.firebase.database.core.view.Event.EventType;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class EventGenerator {
    private final Index index;
    private final QuerySpec query;

    public EventGenerator(QuerySpec querySpec) {
        this.query = querySpec;
        this.index = querySpec.getIndex();
    }

    private void generateEventsForType(List<DataEvent> list, EventType eventType, List<Change> list2, List<EventRegistration> list3, IndexedNode indexedNode) {
        List<Change> arrayList = new ArrayList();
        for (Change change : list2) {
            if (change.getEventType().equals(eventType)) {
                arrayList.add(change);
            }
        }
        Collections.sort(arrayList, changeComparator());
        for (Change change2 : arrayList) {
            for (EventRegistration eventRegistration : list3) {
                if (eventRegistration.respondsTo(eventType)) {
                    list.add(generateEvent(change2, eventRegistration, indexedNode));
                }
            }
        }
    }

    private DataEvent generateEvent(Change change, EventRegistration eventRegistration, IndexedNode indexedNode) {
        if (!(change.getEventType().equals(EventType.VALUE) || change.getEventType().equals(EventType.CHILD_REMOVED))) {
            change = change.changeWithPrevName(indexedNode.getPredecessorChildName(change.getChildKey(), change.getIndexedNode().getNode(), this.index));
        }
        return eventRegistration.createEvent(change, this.query);
    }

    public List<DataEvent> generateEventsForChanges(List<Change> list, IndexedNode indexedNode, List<EventRegistration> list2) {
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        for (Change change : list) {
            if (change.getEventType().equals(EventType.CHILD_CHANGED) && this.index.indexedValueChanged(change.getOldIndexedNode().getNode(), change.getIndexedNode().getNode())) {
                arrayList2.add(Change.childMovedChange(change.getChildKey(), change.getIndexedNode()));
            }
        }
        List list3 = arrayList;
        List<Change> list4 = list;
        List<EventRegistration> list5 = list2;
        IndexedNode indexedNode2 = indexedNode;
        generateEventsForType(list3, EventType.CHILD_REMOVED, list4, list5, indexedNode2);
        generateEventsForType(list3, EventType.CHILD_ADDED, list4, list5, indexedNode2);
        generateEventsForType(list3, EventType.CHILD_MOVED, arrayList2, list5, indexedNode2);
        list4 = list;
        generateEventsForType(list3, EventType.CHILD_CHANGED, list4, list5, indexedNode2);
        generateEventsForType(list3, EventType.VALUE, list4, list5, indexedNode2);
        return arrayList;
    }

    private Comparator<Change> changeComparator() {
        return new Comparator<Change>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class cls = EventGenerator.class;
            }

            public int compare(Change change, Change change2) {
                return EventGenerator.this.index.compare(new NamedNode(change.getChildKey(), change.getIndexedNode().getNode()), new NamedNode(change2.getChildKey(), change2.getIndexedNode().getNode()));
            }
        };
    }
}
