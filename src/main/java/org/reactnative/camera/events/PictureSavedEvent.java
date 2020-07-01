package org.reactnative.camera.events;

import androidx.core.util.Pools.SynchronizedPool;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import org.reactnative.camera.CameraViewManager.Events;

public class PictureSavedEvent extends Event<PictureSavedEvent> {
    private static final SynchronizedPool<PictureSavedEvent> EVENTS_POOL = new SynchronizedPool(5);
    private WritableMap mResponse;

    private PictureSavedEvent() {
    }

    public static PictureSavedEvent obtain(int i, WritableMap writableMap) {
        PictureSavedEvent pictureSavedEvent = (PictureSavedEvent) EVENTS_POOL.acquire();
        if (pictureSavedEvent == null) {
            pictureSavedEvent = new PictureSavedEvent();
        }
        pictureSavedEvent.init(i, writableMap);
        return pictureSavedEvent;
    }

    private void init(int i, WritableMap writableMap) {
        super.init(i);
        this.mResponse = writableMap;
    }

    public short getCoalescingKey() {
        return (short) (this.mResponse.getMap("data").getString("uri").hashCode() % 32767);
    }

    public String getEventName() {
        return Events.EVENT_ON_PICTURE_SAVED.toString();
    }

    public void dispatch(RCTEventEmitter rCTEventEmitter) {
        rCTEventEmitter.receiveEvent(getViewTag(), getEventName(), this.mResponse);
    }
}
