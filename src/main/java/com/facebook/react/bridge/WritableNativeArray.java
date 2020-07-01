package com.facebook.react.bridge;

import com.facebook.infer.annotation.Assertions;
import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import javax.annotation.Nullable;

@DoNotStrip
public class WritableNativeArray extends ReadableNativeArray implements WritableArray {
    private static native HybridData initHybrid();

    private native void pushNativeArray(WritableNativeArray writableNativeArray);

    private native void pushNativeMap(WritableNativeMap writableNativeMap);

    public native void pushBoolean(boolean z);

    public native void pushDouble(double d);

    public native void pushInt(int i);

    public native void pushNull();

    public native void pushString(@Nullable String str);

    static {
        ReactBridge.staticInit();
    }

    public WritableNativeArray() {
        super(initHybrid());
    }

    public void pushArray(@Nullable WritableArray writableArray) {
        boolean z = writableArray == null || (writableArray instanceof WritableNativeArray);
        Assertions.assertCondition(z, "Illegal type provided");
        pushNativeArray((WritableNativeArray) writableArray);
    }

    public void pushMap(@Nullable WritableMap writableMap) {
        boolean z = writableMap == null || (writableMap instanceof WritableNativeMap);
        Assertions.assertCondition(z, "Illegal type provided");
        pushNativeMap((WritableNativeMap) writableMap);
    }
}
