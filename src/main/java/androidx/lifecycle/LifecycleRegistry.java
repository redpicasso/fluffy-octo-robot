package androidx.lifecycle;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.Lifecycle.State;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

public class LifecycleRegistry extends Lifecycle {
    private int mAddingObserverCounter = 0;
    private boolean mHandlingEvent = false;
    private final WeakReference<LifecycleOwner> mLifecycleOwner;
    private boolean mNewEventOccurred = false;
    private FastSafeIterableMap<LifecycleObserver, ObserverWithState> mObserverMap = new FastSafeIterableMap();
    private ArrayList<State> mParentStates = new ArrayList();
    private State mState;

    /* renamed from: androidx.lifecycle.LifecycleRegistry$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$lifecycle$Lifecycle$State = new int[State.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:30:0x0091, code:
            return;
     */
        static {
            /*
            r0 = androidx.lifecycle.Lifecycle.State.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$androidx$lifecycle$Lifecycle$State = r0;
            r0 = 1;
            r1 = $SwitchMap$androidx$lifecycle$Lifecycle$State;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = androidx.lifecycle.Lifecycle.State.INITIALIZED;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = r2.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1[r2] = r0;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r1 = 2;
            r2 = $SwitchMap$androidx$lifecycle$Lifecycle$State;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = androidx.lifecycle.Lifecycle.State.CREATED;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2[r3] = r1;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r2 = 3;
            r3 = $SwitchMap$androidx$lifecycle$Lifecycle$State;	 Catch:{ NoSuchFieldError -> 0x002a }
            r4 = androidx.lifecycle.Lifecycle.State.STARTED;	 Catch:{ NoSuchFieldError -> 0x002a }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r3[r4] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r3 = 4;
            r4 = $SwitchMap$androidx$lifecycle$Lifecycle$State;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r5 = androidx.lifecycle.Lifecycle.State.RESUMED;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r4[r5] = r3;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            r4 = 5;
            r5 = $SwitchMap$androidx$lifecycle$Lifecycle$State;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r6 = androidx.lifecycle.Lifecycle.State.DESTROYED;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r6 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x0040 }
            r5[r6] = r4;	 Catch:{ NoSuchFieldError -> 0x0040 }
        L_0x0040:
            r5 = androidx.lifecycle.Lifecycle.Event.values();
            r5 = r5.length;
            r5 = new int[r5];
            $SwitchMap$androidx$lifecycle$Lifecycle$Event = r5;
            r5 = $SwitchMap$androidx$lifecycle$Lifecycle$Event;	 Catch:{ NoSuchFieldError -> 0x0053 }
            r6 = androidx.lifecycle.Lifecycle.Event.ON_CREATE;	 Catch:{ NoSuchFieldError -> 0x0053 }
            r6 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x0053 }
            r5[r6] = r0;	 Catch:{ NoSuchFieldError -> 0x0053 }
        L_0x0053:
            r0 = $SwitchMap$androidx$lifecycle$Lifecycle$Event;	 Catch:{ NoSuchFieldError -> 0x005d }
            r5 = androidx.lifecycle.Lifecycle.Event.ON_STOP;	 Catch:{ NoSuchFieldError -> 0x005d }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x005d }
            r0[r5] = r1;	 Catch:{ NoSuchFieldError -> 0x005d }
        L_0x005d:
            r0 = $SwitchMap$androidx$lifecycle$Lifecycle$Event;	 Catch:{ NoSuchFieldError -> 0x0067 }
            r1 = androidx.lifecycle.Lifecycle.Event.ON_START;	 Catch:{ NoSuchFieldError -> 0x0067 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0067 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0067 }
        L_0x0067:
            r0 = $SwitchMap$androidx$lifecycle$Lifecycle$Event;	 Catch:{ NoSuchFieldError -> 0x0071 }
            r1 = androidx.lifecycle.Lifecycle.Event.ON_PAUSE;	 Catch:{ NoSuchFieldError -> 0x0071 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0071 }
            r0[r1] = r3;	 Catch:{ NoSuchFieldError -> 0x0071 }
        L_0x0071:
            r0 = $SwitchMap$androidx$lifecycle$Lifecycle$Event;	 Catch:{ NoSuchFieldError -> 0x007b }
            r1 = androidx.lifecycle.Lifecycle.Event.ON_RESUME;	 Catch:{ NoSuchFieldError -> 0x007b }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x007b }
            r0[r1] = r4;	 Catch:{ NoSuchFieldError -> 0x007b }
        L_0x007b:
            r0 = $SwitchMap$androidx$lifecycle$Lifecycle$Event;	 Catch:{ NoSuchFieldError -> 0x0086 }
            r1 = androidx.lifecycle.Lifecycle.Event.ON_DESTROY;	 Catch:{ NoSuchFieldError -> 0x0086 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0086 }
            r2 = 6;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0086 }
        L_0x0086:
            r0 = $SwitchMap$androidx$lifecycle$Lifecycle$Event;	 Catch:{ NoSuchFieldError -> 0x0091 }
            r1 = androidx.lifecycle.Lifecycle.Event.ON_ANY;	 Catch:{ NoSuchFieldError -> 0x0091 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0091 }
            r2 = 7;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0091 }
        L_0x0091:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.LifecycleRegistry.1.<clinit>():void");
        }
    }

    static class ObserverWithState {
        LifecycleEventObserver mLifecycleObserver;
        State mState;

        ObserverWithState(LifecycleObserver lifecycleObserver, State state) {
            this.mLifecycleObserver = Lifecycling.lifecycleEventObserver(lifecycleObserver);
            this.mState = state;
        }

        void dispatchEvent(LifecycleOwner lifecycleOwner, Event event) {
            State stateAfter = LifecycleRegistry.getStateAfter(event);
            this.mState = LifecycleRegistry.min(this.mState, stateAfter);
            this.mLifecycleObserver.onStateChanged(lifecycleOwner, event);
            this.mState = stateAfter;
        }
    }

    public LifecycleRegistry(@NonNull LifecycleOwner lifecycleOwner) {
        this.mLifecycleOwner = new WeakReference(lifecycleOwner);
        this.mState = State.INITIALIZED;
    }

    @MainThread
    @Deprecated
    public void markState(@NonNull State state) {
        setCurrentState(state);
    }

    @MainThread
    public void setCurrentState(@NonNull State state) {
        moveToState(state);
    }

    public void handleLifecycleEvent(@NonNull Event event) {
        moveToState(getStateAfter(event));
    }

    private void moveToState(State state) {
        if (this.mState != state) {
            this.mState = state;
            if (this.mHandlingEvent || this.mAddingObserverCounter != 0) {
                this.mNewEventOccurred = true;
                return;
            }
            this.mHandlingEvent = true;
            sync();
            this.mHandlingEvent = false;
        }
    }

    private boolean isSynced() {
        boolean z = true;
        if (this.mObserverMap.size() == 0) {
            return true;
        }
        State state = ((ObserverWithState) this.mObserverMap.eldest().getValue()).mState;
        State state2 = ((ObserverWithState) this.mObserverMap.newest().getValue()).mState;
        if (!(state == state2 && this.mState == state2)) {
            z = false;
        }
        return z;
    }

    private State calculateTargetState(LifecycleObserver lifecycleObserver) {
        Entry ceil = this.mObserverMap.ceil(lifecycleObserver);
        State state = null;
        State state2 = ceil != null ? ((ObserverWithState) ceil.getValue()).mState : null;
        if (!this.mParentStates.isEmpty()) {
            ArrayList arrayList = this.mParentStates;
            state = (State) arrayList.get(arrayList.size() - 1);
        }
        return min(min(this.mState, state2), state);
    }

    public void addObserver(@NonNull LifecycleObserver lifecycleObserver) {
        ObserverWithState observerWithState = new ObserverWithState(lifecycleObserver, this.mState == State.DESTROYED ? State.DESTROYED : State.INITIALIZED);
        if (((ObserverWithState) this.mObserverMap.putIfAbsent(lifecycleObserver, observerWithState)) == null) {
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.mLifecycleOwner.get();
            if (lifecycleOwner != null) {
                Object obj = (this.mAddingObserverCounter != 0 || this.mHandlingEvent) ? 1 : null;
                Enum calculateTargetState = calculateTargetState(lifecycleObserver);
                this.mAddingObserverCounter++;
                while (observerWithState.mState.compareTo(calculateTargetState) < 0 && this.mObserverMap.contains(lifecycleObserver)) {
                    pushParentState(observerWithState.mState);
                    observerWithState.dispatchEvent(lifecycleOwner, upEvent(observerWithState.mState));
                    popParentState();
                    calculateTargetState = calculateTargetState(lifecycleObserver);
                }
                if (obj == null) {
                    sync();
                }
                this.mAddingObserverCounter--;
            }
        }
    }

    private void popParentState() {
        ArrayList arrayList = this.mParentStates;
        arrayList.remove(arrayList.size() - 1);
    }

    private void pushParentState(State state) {
        this.mParentStates.add(state);
    }

    public void removeObserver(@NonNull LifecycleObserver lifecycleObserver) {
        this.mObserverMap.remove(lifecycleObserver);
    }

    public int getObserverCount() {
        return this.mObserverMap.size();
    }

    @NonNull
    public State getCurrentState() {
        return this.mState;
    }

    static State getStateAfter(Event event) {
        switch (event) {
            case ON_CREATE:
            case ON_STOP:
                return State.CREATED;
            case ON_START:
            case ON_PAUSE:
                return State.STARTED;
            case ON_RESUME:
                return State.RESUMED;
            case ON_DESTROY:
                return State.DESTROYED;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected event value ");
                stringBuilder.append(event);
                throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    private static Event downEvent(State state) {
        int i = AnonymousClass1.$SwitchMap$androidx$lifecycle$Lifecycle$State[state.ordinal()];
        if (i == 1) {
            throw new IllegalArgumentException();
        } else if (i == 2) {
            return Event.ON_DESTROY;
        } else {
            if (i == 3) {
                return Event.ON_STOP;
            }
            if (i == 4) {
                return Event.ON_PAUSE;
            }
            if (i != 5) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected state value ");
                stringBuilder.append(state);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            throw new IllegalArgumentException();
        }
    }

    private static Event upEvent(State state) {
        int i = AnonymousClass1.$SwitchMap$androidx$lifecycle$Lifecycle$State[state.ordinal()];
        if (i != 1) {
            if (i == 2) {
                return Event.ON_START;
            }
            if (i == 3) {
                return Event.ON_RESUME;
            }
            if (i == 4) {
                throw new IllegalArgumentException();
            } else if (i != 5) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected state value ");
                stringBuilder.append(state);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        return Event.ON_CREATE;
    }

    private void forwardPass(LifecycleOwner lifecycleOwner) {
        Iterator iteratorWithAdditions = this.mObserverMap.iteratorWithAdditions();
        while (iteratorWithAdditions.hasNext() && !this.mNewEventOccurred) {
            Entry entry = (Entry) iteratorWithAdditions.next();
            ObserverWithState observerWithState = (ObserverWithState) entry.getValue();
            while (observerWithState.mState.compareTo(this.mState) < 0 && !this.mNewEventOccurred && this.mObserverMap.contains(entry.getKey())) {
                pushParentState(observerWithState.mState);
                observerWithState.dispatchEvent(lifecycleOwner, upEvent(observerWithState.mState));
                popParentState();
            }
        }
    }

    private void backwardPass(LifecycleOwner lifecycleOwner) {
        Iterator descendingIterator = this.mObserverMap.descendingIterator();
        while (descendingIterator.hasNext() && !this.mNewEventOccurred) {
            Entry entry = (Entry) descendingIterator.next();
            ObserverWithState observerWithState = (ObserverWithState) entry.getValue();
            while (observerWithState.mState.compareTo(this.mState) > 0 && !this.mNewEventOccurred && this.mObserverMap.contains(entry.getKey())) {
                Event downEvent = downEvent(observerWithState.mState);
                pushParentState(getStateAfter(downEvent));
                observerWithState.dispatchEvent(lifecycleOwner, downEvent);
                popParentState();
            }
        }
    }

    private void sync() {
        LifecycleOwner lifecycleOwner = (LifecycleOwner) this.mLifecycleOwner.get();
        if (lifecycleOwner != null) {
            while (!isSynced()) {
                this.mNewEventOccurred = false;
                if (this.mState.compareTo(((ObserverWithState) this.mObserverMap.eldest().getValue()).mState) < 0) {
                    backwardPass(lifecycleOwner);
                }
                Entry newest = this.mObserverMap.newest();
                if (!(this.mNewEventOccurred || newest == null || this.mState.compareTo(((ObserverWithState) newest.getValue()).mState) <= 0)) {
                    forwardPass(lifecycleOwner);
                }
            }
            this.mNewEventOccurred = false;
            return;
        }
        throw new IllegalStateException("LifecycleOwner of this LifecycleRegistry is alreadygarbage collected. It is too late to change lifecycle state.");
    }

    static State min(@NonNull State state, @Nullable State state2) {
        return (state2 == null || state2.compareTo(state) >= 0) ? state : state2;
    }
}
