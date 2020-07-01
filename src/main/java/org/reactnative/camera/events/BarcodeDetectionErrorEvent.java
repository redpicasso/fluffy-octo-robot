package org.reactnative.camera.events;

import androidx.core.util.Pools.SynchronizedPool;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import org.reactnative.barcodedetector.RNBarcodeDetector;
import org.reactnative.camera.CameraViewManager.Events;

public class BarcodeDetectionErrorEvent extends Event<BarcodeDetectionErrorEvent> {
    private static final SynchronizedPool<BarcodeDetectionErrorEvent> EVENTS_POOL = new SynchronizedPool(3);
    private RNBarcodeDetector mBarcodeDetector;

    public short getCoalescingKey() {
        return (short) 0;
    }

    private BarcodeDetectionErrorEvent() {
    }

    public static BarcodeDetectionErrorEvent obtain(int i, RNBarcodeDetector rNBarcodeDetector) {
        BarcodeDetectionErrorEvent barcodeDetectionErrorEvent = (BarcodeDetectionErrorEvent) EVENTS_POOL.acquire();
        if (barcodeDetectionErrorEvent == null) {
            barcodeDetectionErrorEvent = new BarcodeDetectionErrorEvent();
        }
        barcodeDetectionErrorEvent.init(i, rNBarcodeDetector);
        return barcodeDetectionErrorEvent;
    }

    private void init(int i, RNBarcodeDetector rNBarcodeDetector) {
        super.init(i);
        this.mBarcodeDetector = rNBarcodeDetector;
    }

    public String getEventName() {
        return Events.EVENT_ON_BARCODE_DETECTION_ERROR.toString();
    }

    public void dispatch(RCTEventEmitter rCTEventEmitter) {
        rCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap createMap = Arguments.createMap();
        RNBarcodeDetector rNBarcodeDetector = this.mBarcodeDetector;
        boolean z = rNBarcodeDetector != null && rNBarcodeDetector.isOperational();
        createMap.putBoolean("isOperational", z);
        return createMap;
    }
}
