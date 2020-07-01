package org.reactnative.camera.events;

import androidx.core.util.Pools.SynchronizedPool;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import org.reactnative.camera.CameraViewManager.Events;

public class CameraMountErrorEvent extends Event<CameraMountErrorEvent> {
    private static final SynchronizedPool<CameraMountErrorEvent> EVENTS_POOL = new SynchronizedPool(3);
    private String mError;

    public short getCoalescingKey() {
        return (short) 0;
    }

    private CameraMountErrorEvent() {
    }

    public static CameraMountErrorEvent obtain(int i, String str) {
        CameraMountErrorEvent cameraMountErrorEvent = (CameraMountErrorEvent) EVENTS_POOL.acquire();
        if (cameraMountErrorEvent == null) {
            cameraMountErrorEvent = new CameraMountErrorEvent();
        }
        cameraMountErrorEvent.init(i, str);
        return cameraMountErrorEvent;
    }

    private void init(int i, String str) {
        super.init(i);
        this.mError = str;
    }

    public String getEventName() {
        return Events.EVENT_ON_MOUNT_ERROR.toString();
    }

    public void dispatch(RCTEventEmitter rCTEventEmitter) {
        rCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("message", this.mError);
        return createMap;
    }
}
