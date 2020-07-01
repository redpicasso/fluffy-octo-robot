package org.reactnative.camera.events;

import androidx.core.util.Pools.SynchronizedPool;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import org.reactnative.camera.CameraViewManager.Events;

public class RecordingStartEvent extends Event<RecordingStartEvent> {
    private static final SynchronizedPool<RecordingStartEvent> EVENTS_POOL = new SynchronizedPool(3);
    private WritableMap mResponse;

    private RecordingStartEvent() {
    }

    public static RecordingStartEvent obtain(int i, WritableMap writableMap) {
        RecordingStartEvent recordingStartEvent = (RecordingStartEvent) EVENTS_POOL.acquire();
        if (recordingStartEvent == null) {
            recordingStartEvent = new RecordingStartEvent();
        }
        recordingStartEvent.init(i, writableMap);
        return recordingStartEvent;
    }

    private void init(int i, WritableMap writableMap) {
        super.init(i);
        this.mResponse = writableMap;
    }

    public String getEventName() {
        return Events.EVENT_ON_RECORDING_START.toString();
    }

    public void dispatch(RCTEventEmitter rCTEventEmitter) {
        rCTEventEmitter.receiveEvent(getViewTag(), getEventName(), this.mResponse);
    }
}
