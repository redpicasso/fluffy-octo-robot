package com.facebook.react.bridge;

public class DefaultNativeModuleCallExceptionHandler implements NativeModuleCallExceptionHandler {
    public void handleException(Exception exception) {
        if (exception instanceof RuntimeException) {
            throw ((RuntimeException) exception);
        }
        throw new RuntimeException(exception);
    }
}
