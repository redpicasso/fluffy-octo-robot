package com.dylanvann.fastimage;

import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class FastImageRequestListener implements RequestListener<Drawable> {
    static final String REACT_ON_ERROR_EVENT = "onFastImageError";
    static final String REACT_ON_LOAD_END_EVENT = "onFastImageLoadEnd";
    static final String REACT_ON_LOAD_EVENT = "onFastImageLoad";
    private String key;

    FastImageRequestListener(String str) {
        this.key = str;
    }

    private static WritableMap mapFromResource(Drawable drawable) {
        WritableMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putInt("width", drawable.getIntrinsicWidth());
        writableNativeMap.putInt("height", drawable.getIntrinsicHeight());
        return writableNativeMap;
    }

    public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
        FastImageOkHttpProgressGlideModule.forget(this.key);
        if (!(target instanceof ImageViewTarget)) {
            return false;
        }
        FastImageViewWithUrl fastImageViewWithUrl = (FastImageViewWithUrl) ((ImageViewTarget) target).getView();
        RCTEventEmitter rCTEventEmitter = (RCTEventEmitter) ((ThemedReactContext) fastImageViewWithUrl.getContext()).getJSModule(RCTEventEmitter.class);
        int id = fastImageViewWithUrl.getId();
        rCTEventEmitter.receiveEvent(id, REACT_ON_ERROR_EVENT, new WritableNativeMap());
        rCTEventEmitter.receiveEvent(id, REACT_ON_LOAD_END_EVENT, new WritableNativeMap());
        return false;
    }

    public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
        if (!(target instanceof ImageViewTarget)) {
            return false;
        }
        FastImageViewWithUrl fastImageViewWithUrl = (FastImageViewWithUrl) ((ImageViewTarget) target).getView();
        RCTEventEmitter rCTEventEmitter = (RCTEventEmitter) ((ThemedReactContext) fastImageViewWithUrl.getContext()).getJSModule(RCTEventEmitter.class);
        int id = fastImageViewWithUrl.getId();
        rCTEventEmitter.receiveEvent(id, REACT_ON_LOAD_EVENT, mapFromResource(drawable));
        rCTEventEmitter.receiveEvent(id, REACT_ON_LOAD_END_EVENT, new WritableNativeMap());
        return false;
    }
}
