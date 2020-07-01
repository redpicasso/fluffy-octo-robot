package org.reactnative.camera.events;

import androidx.core.util.Pools.SynchronizedPool;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import org.reactnative.camera.CameraViewManager.Events;

public class RecordingEndEvent extends Event<RecordingEndEvent> {
    private static final SynchronizedPool<RecordingEndEvent> EVENTS_POOL = new SynchronizedPool(3);

    public short getCoalescingKey() {
        return (short) 0;
    }

    private RecordingEndEvent() {
    }

    public static RecordingEndEvent obtain(int i) {
        RecordingEndEvent recordingEndEvent = (RecordingEndEvent) EVENTS_POOL.acquire();
        if (recordingEndEvent == null) {
            recordingEndEvent = new RecordingEndEvent();
        }
        recordingEndEvent.init(i);
        return recordingEndEvent;
    }

    public String getEventName() {
        return Events.EVENT_ON_RECORDING_END.toString();
    }

    public void dispatch(RCTEventEmitter rCTEventEmitter) {
        rCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        return Arguments.createMap();
    }
}
