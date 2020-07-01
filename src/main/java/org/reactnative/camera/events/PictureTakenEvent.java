package org.reactnative.camera.events;

import androidx.core.util.Pools.SynchronizedPool;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import org.reactnative.camera.CameraViewManager.Events;

public class PictureTakenEvent extends Event<PictureTakenEvent> {
    private static final SynchronizedPool<PictureTakenEvent> EVENTS_POOL = new SynchronizedPool(3);

    public short getCoalescingKey() {
        return (short) 0;
    }

    private PictureTakenEvent() {
    }

    public static PictureTakenEvent obtain(int i) {
        PictureTakenEvent pictureTakenEvent = (PictureTakenEvent) EVENTS_POOL.acquire();
        if (pictureTakenEvent == null) {
            pictureTakenEvent = new PictureTakenEvent();
        }
        pictureTakenEvent.init(i);
        return pictureTakenEvent;
    }

    public String getEventName() {
        return Events.EVENT_ON_PICTURE_TAKEN.toString();
    }

    public void dispatch(RCTEventEmitter rCTEventEmitter) {
        rCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        return Arguments.createMap();
    }
}
