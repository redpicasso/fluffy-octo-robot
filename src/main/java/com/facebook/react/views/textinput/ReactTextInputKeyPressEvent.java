package com.facebook.react.views.textinput;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ReactTextInputKeyPressEvent extends Event<ReactTextInputEvent> {
    public static final String EVENT_NAME = "topKeyPress";
    private String mKey;

    public boolean canCoalesce() {
        return false;
    }

    public String getEventName() {
        return EVENT_NAME;
    }

    ReactTextInputKeyPressEvent(int i, String str) {
        super(i);
        this.mKey = str;
    }

    public void dispatch(RCTEventEmitter rCTEventEmitter) {
        rCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("key", this.mKey);
        return createMap;
    }
}
