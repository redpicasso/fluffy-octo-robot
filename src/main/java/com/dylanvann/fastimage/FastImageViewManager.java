package com.dylanvann.fastimage;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.model.GlideUrl;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.common.MapBuilder.Builder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import javax.annotation.Nullable;

class FastImageViewManager extends SimpleViewManager<FastImageViewWithUrl> implements FastImageProgressListener {
    private static final String REACT_CLASS = "FastImageView";
    private static final String REACT_ON_LOAD_START_EVENT = "onFastImageLoadStart";
    private static final String REACT_ON_PROGRESS_EVENT = "onFastImageProgress";
    private static final Map<String, List<FastImageViewWithUrl>> VIEWS_FOR_URLS = new WeakHashMap();
    @Nullable
    private RequestManager requestManager = null;

    public float getGranularityPercentage() {
        return 0.5f;
    }

    public String getName() {
        return REACT_CLASS;
    }

    FastImageViewManager() {
    }

    protected FastImageViewWithUrl createViewInstance(ThemedReactContext themedReactContext) {
        if (isValidContextForGlide(themedReactContext)) {
            this.requestManager = Glide.with((Context) themedReactContext);
        }
        return new FastImageViewWithUrl(themedReactContext);
    }

    @ReactProp(name = "source")
    public void setSrc(FastImageViewWithUrl fastImageViewWithUrl, @Nullable ReadableMap readableMap) {
        if (readableMap != null) {
            String str = "uri";
            if (readableMap.hasKey(str) && !isNullOrEmpty(readableMap.getString(str))) {
                FastImageSource imageSource = FastImageViewConverter.getImageSource(fastImageViewWithUrl.getContext(), readableMap);
                GlideUrl glideUrl = imageSource.getGlideUrl();
                fastImageViewWithUrl.glideUrl = glideUrl;
                RequestManager requestManager = this.requestManager;
                if (requestManager != null) {
                    requestManager.clear((View) fastImageViewWithUrl);
                }
                String toStringUrl = glideUrl.toStringUrl();
                FastImageOkHttpProgressGlideModule.expect(toStringUrl, this);
                List list = (List) VIEWS_FOR_URLS.get(toStringUrl);
                if (list != null && !list.contains(fastImageViewWithUrl)) {
                    list.add(fastImageViewWithUrl);
                } else if (list == null) {
                    VIEWS_FOR_URLS.put(toStringUrl, new ArrayList(Collections.singletonList(fastImageViewWithUrl)));
                }
                ThemedReactContext themedReactContext = (ThemedReactContext) fastImageViewWithUrl.getContext();
                ((RCTEventEmitter) themedReactContext.getJSModule(RCTEventEmitter.class)).receiveEvent(fastImageViewWithUrl.getId(), REACT_ON_LOAD_START_EVENT, new WritableNativeMap());
                RequestManager requestManager2 = this.requestManager;
                if (requestManager2 != null) {
                    requestManager2.load(imageSource.getSourceForLoad()).apply(FastImageViewConverter.getOptions(themedReactContext, imageSource, readableMap)).listener(new FastImageRequestListener(toStringUrl)).into((ImageView) fastImageViewWithUrl);
                }
                return;
            }
        }
        RequestManager requestManager3 = this.requestManager;
        if (requestManager3 != null) {
            requestManager3.clear((View) fastImageViewWithUrl);
        }
        if (fastImageViewWithUrl.glideUrl != null) {
            FastImageOkHttpProgressGlideModule.forget(fastImageViewWithUrl.glideUrl.toStringUrl());
        }
        fastImageViewWithUrl.setImageDrawable(null);
    }

    @ReactProp(customType = "Color", name = "tintColor")
    public void setTintColor(FastImageViewWithUrl fastImageViewWithUrl, @Nullable Integer num) {
        if (num == null) {
            fastImageViewWithUrl.clearColorFilter();
        } else {
            fastImageViewWithUrl.setColorFilter(num.intValue(), Mode.SRC_IN);
        }
    }

    @ReactProp(name = "resizeMode")
    public void setResizeMode(FastImageViewWithUrl fastImageViewWithUrl, String str) {
        fastImageViewWithUrl.setScaleType(FastImageViewConverter.getScaleType(str));
    }

    public void onDropViewInstance(FastImageViewWithUrl fastImageViewWithUrl) {
        RequestManager requestManager = this.requestManager;
        if (requestManager != null) {
            requestManager.clear((View) fastImageViewWithUrl);
        }
        if (fastImageViewWithUrl.glideUrl != null) {
            String glideUrl = fastImageViewWithUrl.glideUrl.toString();
            FastImageOkHttpProgressGlideModule.forget(glideUrl);
            List list = (List) VIEWS_FOR_URLS.get(glideUrl);
            if (list != null) {
                list.remove(fastImageViewWithUrl);
                if (list.size() == 0) {
                    VIEWS_FOR_URLS.remove(glideUrl);
                }
            }
        }
        super.onDropViewInstance(fastImageViewWithUrl);
    }

    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        Builder builder = MapBuilder.builder();
        String str = REACT_ON_LOAD_START_EVENT;
        String str2 = "registrationName";
        builder = builder.put(str, MapBuilder.of(str2, str));
        str = REACT_ON_PROGRESS_EVENT;
        builder = builder.put(str, MapBuilder.of(str2, str));
        str = "onFastImageLoad";
        builder = builder.put(str, MapBuilder.of(str2, str));
        str = "onFastImageError";
        builder = builder.put(str, MapBuilder.of(str2, str));
        str = "onFastImageLoadEnd";
        return builder.put(str, MapBuilder.of(str2, str)).build();
    }

    public void onProgress(String str, long j, long j2) {
        List<FastImageViewWithUrl> list = (List) VIEWS_FOR_URLS.get(str);
        if (list != null) {
            for (FastImageViewWithUrl fastImageViewWithUrl : list) {
                WritableMap writableNativeMap = new WritableNativeMap();
                writableNativeMap.putInt("loaded", (int) j);
                writableNativeMap.putInt("total", (int) j2);
                ((RCTEventEmitter) ((ThemedReactContext) fastImageViewWithUrl.getContext()).getJSModule(RCTEventEmitter.class)).receiveEvent(fastImageViewWithUrl.getId(), REACT_ON_PROGRESS_EVENT, writableNativeMap);
            }
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private static boolean isValidContextForGlide(Context context) {
        Activity activityFromContext = getActivityFromContext(context);
        if (activityFromContext == null) {
            return false;
        }
        return isActivityDestroyed(activityFromContext) ^ 1;
    }

    private static Activity getActivityFromContext(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ThemedReactContext) {
            context = ((ThemedReactContext) context).getBaseContext();
            if (context instanceof Activity) {
                return (Activity) context;
            }
            if (context instanceof ContextWrapper) {
                context = ((ContextWrapper) context).getBaseContext();
                if (context instanceof Activity) {
                    return (Activity) context;
                }
            }
        }
        return null;
    }

    private static boolean isActivityDestroyed(Activity activity) {
        boolean z = false;
        if (VERSION.SDK_INT >= 17) {
            if (activity.isDestroyed() || activity.isFinishing()) {
                z = true;
            }
            return z;
        }
        if (activity.isDestroyed() || activity.isFinishing() || activity.isChangingConfigurations()) {
            z = true;
        }
        return z;
    }
}
