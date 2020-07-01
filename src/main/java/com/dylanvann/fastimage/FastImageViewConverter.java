package com.dylanvann.fastimage;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView.ScaleType;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaders.Builder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ApplicationVersionSignature;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.NoSuchKeyException;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.lwansbrough.RCTCamera.RCTCameraModule;
import java.util.HashMap;
import java.util.Map;

class FastImageViewConverter {
    private static final Map<String, FastImageCacheControl> FAST_IMAGE_CACHE_CONTROL_MAP = new HashMap<String, FastImageCacheControl>() {
        {
            put("immutable", FastImageCacheControl.IMMUTABLE);
            put("web", FastImageCacheControl.WEB);
            put("cacheOnly", FastImageCacheControl.CACHE_ONLY);
        }
    };
    private static final Map<String, Priority> FAST_IMAGE_PRIORITY_MAP = new HashMap<String, Priority>() {
        {
            put(RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_LOW, Priority.LOW);
            put("normal", Priority.NORMAL);
            put(RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_HIGH, Priority.HIGH);
        }
    };
    private static final Map<String, ScaleType> FAST_IMAGE_RESIZE_MODE_MAP = new HashMap<String, ScaleType>() {
        {
            put("contain", ScaleType.FIT_CENTER);
            put("cover", ScaleType.CENTER_CROP);
            put("stretch", ScaleType.FIT_XY);
            put("center", ScaleType.CENTER);
        }
    };
    private static final Drawable TRANSPARENT_DRAWABLE = new ColorDrawable(0);

    /* renamed from: com.dylanvann.fastimage.FastImageViewConverter$4 */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$dylanvann$fastimage$FastImageCacheControl = new int[FastImageCacheControl.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$dylanvann$fastimage$FastImageCacheControl[com.dylanvann.fastimage.FastImageCacheControl.IMMUTABLE.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.dylanvann.fastimage.FastImageCacheControl.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$dylanvann$fastimage$FastImageCacheControl = r0;
            r0 = $SwitchMap$com$dylanvann$fastimage$FastImageCacheControl;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.dylanvann.fastimage.FastImageCacheControl.WEB;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$dylanvann$fastimage$FastImageCacheControl;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.dylanvann.fastimage.FastImageCacheControl.CACHE_ONLY;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$dylanvann$fastimage$FastImageCacheControl;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.dylanvann.fastimage.FastImageCacheControl.IMMUTABLE;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.dylanvann.fastimage.FastImageViewConverter.4.<clinit>():void");
        }
    }

    FastImageViewConverter() {
    }

    static FastImageSource getImageSource(Context context, ReadableMap readableMap) {
        return new FastImageSource(context, readableMap.getString("uri"), getHeaders(readableMap));
    }

    static Headers getHeaders(ReadableMap readableMap) {
        Headers headers = Headers.DEFAULT;
        String str = "headers";
        if (!readableMap.hasKey(str)) {
            return headers;
        }
        readableMap = readableMap.getMap(str);
        ReadableMapKeySetIterator keySetIterator = readableMap.keySetIterator();
        Builder builder = new Builder();
        while (keySetIterator.hasNextKey()) {
            String nextKey = keySetIterator.nextKey();
            builder.addHeader(nextKey, readableMap.getString(nextKey));
        }
        return builder.build();
    }

    static RequestOptions getOptions(Context context, FastImageSource fastImageSource, ReadableMap readableMap) {
        Boolean valueOf;
        Priority priority = getPriority(readableMap);
        FastImageCacheControl cacheControl = getCacheControl(readableMap);
        DiskCacheStrategy diskCacheStrategy = DiskCacheStrategy.AUTOMATIC;
        Boolean valueOf2 = Boolean.valueOf(false);
        int i = AnonymousClass4.$SwitchMap$com$dylanvann$fastimage$FastImageCacheControl[cacheControl.ordinal()];
        if (i == 1) {
            diskCacheStrategy = DiskCacheStrategy.NONE;
            valueOf = Boolean.valueOf(true);
        } else if (i != 2) {
            valueOf = valueOf2;
        } else {
            Boolean bool = valueOf2;
            valueOf2 = Boolean.valueOf(true);
            valueOf = bool;
        }
        RequestOptions requestOptions = (RequestOptions) ((RequestOptions) ((RequestOptions) ((RequestOptions) ((RequestOptions) new RequestOptions().diskCacheStrategy(diskCacheStrategy)).onlyRetrieveFromCache(valueOf2.booleanValue())).skipMemoryCache(valueOf.booleanValue())).priority(priority)).placeholder(TRANSPARENT_DRAWABLE);
        return fastImageSource.isResource() ? (RequestOptions) requestOptions.apply(RequestOptions.signatureOf(ApplicationVersionSignature.obtain(context))) : requestOptions;
    }

    private static FastImageCacheControl getCacheControl(ReadableMap readableMap) {
        return (FastImageCacheControl) getValueFromSource("cache", "immutable", FAST_IMAGE_CACHE_CONTROL_MAP, readableMap);
    }

    private static Priority getPriority(ReadableMap readableMap) {
        return (Priority) getValueFromSource("priority", "normal", FAST_IMAGE_PRIORITY_MAP, readableMap);
    }

    static ScaleType getScaleType(String str) {
        return (ScaleType) getValue("resizeMode", "cover", FAST_IMAGE_RESIZE_MODE_MAP, str);
    }

    private static <T> T getValue(String str, String str2, Map<String, T> map, String str3) {
        if (str3 != null) {
            str2 = str3;
        }
        T t = map.get(str2);
        if (t != null) {
            return t;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FastImage, invalid ");
        stringBuilder.append(str);
        stringBuilder.append(" : ");
        stringBuilder.append(str2);
        throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    }

    private static <T> T getValueFromSource(String str, String str2, Map<String, T> map, ReadableMap readableMap) {
        String str3 = null;
        if (readableMap != null) {
            try {
                str3 = readableMap.getString(str);
            } catch (NoSuchKeyException unused) {
                return getValue(str, str2, map, str3);
            }
        }
    }
}
