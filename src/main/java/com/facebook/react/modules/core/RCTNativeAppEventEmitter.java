package com.facebook.react.modules.core;

import com.facebook.react.bridge.JavaScriptModule;
import javax.annotation.Nullable;

public interface RCTNativeAppEventEmitter extends JavaScriptModule {
    void emit(String str, @Nullable Object obj);
}
