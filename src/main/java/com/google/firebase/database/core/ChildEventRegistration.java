package com.google.firebase.database.core;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.InternalHelpers;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.DataEvent;
import com.google.firebase.database.core.view.Event.EventType;
import com.google.firebase.database.core.view.QuerySpec;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ChildEventRegistration extends EventRegistration {
    private final ChildEventListener eventListener;
    private final Repo repo;
    private final QuerySpec spec;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    /* renamed from: com.google.firebase.database.core.ChildEventRegistration$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$database$core$view$Event$EventType = new int[EventType.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:8:?, code:
            $SwitchMap$com$google$firebase$database$core$view$Event$EventType[com.google.firebase.database.core.view.Event.EventType.CHILD_REMOVED.ordinal()] = 4;
     */
        static {
            /*
            r0 = com.google.firebase.database.core.view.Event.EventType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firebase$database$core$view$Event$EventType = r0;
            r0 = $SwitchMap$com$google$firebase$database$core$view$Event$EventType;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.firebase.database.core.view.Event.EventType.CHILD_ADDED;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$firebase$database$core$view$Event$EventType;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.firebase.database.core.view.Event.EventType.CHILD_CHANGED;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$google$firebase$database$core$view$Event$EventType;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.google.firebase.database.core.view.Event.EventType.CHILD_MOVED;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r0 = $SwitchMap$com$google$firebase$database$core$view$Event$EventType;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = com.google.firebase.database.core.view.Event.EventType.CHILD_REMOVED;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.core.ChildEventRegistration.1.<clinit>():void");
        }
    }

    public String toString() {
        return "ChildEventRegistration";
    }

    public ChildEventRegistration(@NotNull Repo repo, @NotNull ChildEventListener childEventListener, @NotNull QuerySpec querySpec) {
        this.repo = repo;
        this.eventListener = childEventListener;
        this.spec = querySpec;
    }

    public boolean respondsTo(EventType eventType) {
        return eventType != EventType.VALUE;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ChildEventRegistration) {
            ChildEventRegistration childEventRegistration = (ChildEventRegistration) obj;
            if (childEventRegistration.eventListener.equals(this.eventListener) && childEventRegistration.repo.equals(this.repo) && childEventRegistration.spec.equals(this.spec)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((this.eventListener.hashCode() * 31) + this.repo.hashCode()) * 31) + this.spec.hashCode();
    }

    public DataEvent createEvent(Change change, QuerySpec querySpec) {
        return new DataEvent(change.getEventType(), this, InternalHelpers.createDataSnapshot(InternalHelpers.createReference(this.repo, querySpec.getPath().child(change.getChildKey())), change.getIndexedNode()), change.getPrevName() != null ? change.getPrevName().asString() : null);
    }

    public void fireEvent(DataEvent dataEvent) {
        if (!isZombied()) {
            int i = AnonymousClass1.$SwitchMap$com$google$firebase$database$core$view$Event$EventType[dataEvent.getEventType().ordinal()];
            if (i == 1) {
                this.eventListener.onChildAdded(dataEvent.getSnapshot(), dataEvent.getPreviousName());
            } else if (i == 2) {
                this.eventListener.onChildChanged(dataEvent.getSnapshot(), dataEvent.getPreviousName());
            } else if (i == 3) {
                this.eventListener.onChildMoved(dataEvent.getSnapshot(), dataEvent.getPreviousName());
            } else if (i == 4) {
                this.eventListener.onChildRemoved(dataEvent.getSnapshot());
            }
        }
    }

    public void fireCancelEvent(DatabaseError databaseError) {
        this.eventListener.onCancelled(databaseError);
    }

    public EventRegistration clone(QuerySpec querySpec) {
        return new ChildEventRegistration(this.repo, this.eventListener, querySpec);
    }

    public boolean isSameListener(EventRegistration eventRegistration) {
        return (eventRegistration instanceof ChildEventRegistration) && ((ChildEventRegistration) eventRegistration).eventListener.equals(this.eventListener);
    }

    @NotNull
    public QuerySpec getQuerySpec() {
        return this.spec;
    }

    Repo getRepo() {
        return this.repo;
    }
}
