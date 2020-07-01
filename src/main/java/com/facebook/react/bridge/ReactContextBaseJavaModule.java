package com.facebook.react.bridge;

import android.app.Activity;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class ReactContextBaseJavaModule extends BaseJavaModule {
    private final ReactApplicationContext mReactApplicationContext;

    public ReactContextBaseJavaModule(@Nonnull ReactApplicationContext reactApplicationContext) {
        this.mReactApplicationContext = reactApplicationContext;
    }

    /* renamed from: getReactApplicationContext */
    protected final ReactApplicationContext access$900() {
        return this.mReactApplicationContext;
    }

    @Nullable
    /* renamed from: getCurrentActivity */
    protected final Activity access$700() {
        return this.mReactApplicationContext.getCurrentActivity();
    }
}
