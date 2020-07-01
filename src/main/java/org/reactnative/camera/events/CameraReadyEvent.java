package org.reactnative.camera.events;

import androidx.core.util.Pools.SynchronizedPool;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import org.reactnative.camera.CameraViewManager.Events;

public class CameraReadyEvent extends Event<CameraReadyEvent> {
    private static final SynchronizedPool<CameraReadyEvent> EVENTS_POOL = new SynchronizedPool(3);

    public short getCoalescingKey() {
        return (short) 0;
    }

    private CameraReadyEvent() {
    }

    public static CameraReadyEvent obtain(int i) {
        CameraReadyEvent cameraReadyEvent = (CameraReadyEvent) EVENTS_POOL.acquire();
        if (cameraReadyEvent == null) {
            cameraReadyEvent = new CameraReadyEvent();
        }
        cameraReadyEvent.init(i);
        return cameraReadyEvent;
    }

    public String getEventName() {
        return Events.EVENT_CAMERA_READY.toString();
    }

    public void dispatch(RCTEventEmitter rCTEventEmitter) {
        rCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        return Arguments.createMap();
    }
}
