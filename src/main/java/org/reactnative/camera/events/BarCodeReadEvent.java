package org.reactnative.camera.events;

import androidx.core.util.Pools.SynchronizedPool;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.Formatter;
import org.reactnative.camera.CameraViewManager.Events;

public class BarCodeReadEvent extends Event<BarCodeReadEvent> {
    private static final SynchronizedPool<BarCodeReadEvent> EVENTS_POOL = new SynchronizedPool(3);
    private Result mBarCode;
    private int mHeight;
    private int mWidth;

    private BarCodeReadEvent() {
    }

    public static BarCodeReadEvent obtain(int i, Result result, int i2, int i3) {
        BarCodeReadEvent barCodeReadEvent = (BarCodeReadEvent) EVENTS_POOL.acquire();
        if (barCodeReadEvent == null) {
            barCodeReadEvent = new BarCodeReadEvent();
        }
        barCodeReadEvent.init(i, result, i2, i3);
        return barCodeReadEvent;
    }

    private void init(int i, Result result, int i2, int i3) {
        super.init(i);
        this.mBarCode = result;
        this.mWidth = i2;
        this.mHeight = i3;
    }

    public short getCoalescingKey() {
        return (short) (this.mBarCode.getText().hashCode() % 32767);
    }

    public String getEventName() {
        return Events.EVENT_ON_BAR_CODE_READ.toString();
    }

    public void dispatch(RCTEventEmitter rCTEventEmitter) {
        rCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        int length;
        WritableMap createMap = Arguments.createMap();
        WritableMap createMap2 = Arguments.createMap();
        createMap.putInt("target", getViewTag());
        createMap.putString("data", this.mBarCode.getText());
        byte[] rawBytes = this.mBarCode.getRawBytes();
        int i = 0;
        if (rawBytes != null && rawBytes.length > 0) {
            Formatter formatter = new Formatter();
            length = rawBytes.length;
            for (int i2 = 0; i2 < length; i2++) {
                formatter.format("%02x", new Object[]{Byte.valueOf(rawBytes[i2])});
            }
            createMap.putString("rawData", formatter.toString());
            formatter.close();
        }
        createMap.putString("type", this.mBarCode.getBarcodeFormat().toString());
        WritableArray createArray = Arguments.createArray();
        ResultPoint[] resultPoints = this.mBarCode.getResultPoints();
        length = resultPoints.length;
        while (i < length) {
            ResultPoint resultPoint = resultPoints[i];
            if (resultPoint != null) {
                WritableMap createMap3 = Arguments.createMap();
                createMap3.putString("x", String.valueOf(resultPoint.getX()));
                createMap3.putString("y", String.valueOf(resultPoint.getY()));
                createArray.pushMap(createMap3);
            }
            i++;
        }
        createMap2.putArray("origin", createArray);
        createMap2.putInt("height", this.mHeight);
        createMap2.putInt("width", this.mWidth);
        createMap.putMap("bounds", createMap2);
        return createMap;
    }
}
