package com.dylanvann.fastimage;

import android.app.Activity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

class FastImageViewModule extends ReactContextBaseJavaModule {
    private static final String REACT_CLASS = "FastImageView";

    public String getName() {
        return REACT_CLASS;
    }

    FastImageViewModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @ReactMethod
    public void preload(final ReadableArray readableArray) {
        final Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable() {
                public void run() {
                    for (int i = 0; i < readableArray.size(); i++) {
                        ReadableMap map = readableArray.getMap(i);
                        FastImageSource imageSource = FastImageViewConverter.getImageSource(currentActivity, map);
                        RequestManager with = Glide.with(currentActivity.getApplicationContext());
                        Object source = imageSource.isBase64Resource() ? imageSource.getSource() : imageSource.isResource() ? imageSource.getUri() : imageSource.getGlideUrl();
                        with.load(source).apply(FastImageViewConverter.getOptions(currentActivity, imageSource, map)).preload();
                    }
                }
            });
        }
    }
}
