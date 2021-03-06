package com.facebook.react.views.imagehelper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.Uri.Builder;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class ResourceDrawableIdHelper {
    private static final String LOCAL_RESOURCE_SCHEME = "res";
    private static volatile ResourceDrawableIdHelper sResourceDrawableIdHelper;
    private Map<String, Integer> mResourceDrawableIdMap = new HashMap();

    private ResourceDrawableIdHelper() {
    }

    public static ResourceDrawableIdHelper getInstance() {
        if (sResourceDrawableIdHelper == null) {
            synchronized (ResourceDrawableIdHelper.class) {
                if (sResourceDrawableIdHelper == null) {
                    sResourceDrawableIdHelper = new ResourceDrawableIdHelper();
                }
            }
        }
        return sResourceDrawableIdHelper;
    }

    public synchronized void clear() {
        this.mResourceDrawableIdMap.clear();
    }

    public int getResourceDrawableId(Context context, @Nullable String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        str = str.toLowerCase().replace("-", "_");
        try {
            context = Integer.parseInt(str);
            return context;
        } catch (NumberFormatException unused) {
            synchronized (this) {
                if (this.mResourceDrawableIdMap.containsKey(str)) {
                    return ((Integer) this.mResourceDrawableIdMap.get(str)).intValue();
                }
                int identifier = context.getResources().getIdentifier(str, "drawable", context.getPackageName());
                this.mResourceDrawableIdMap.put(str, Integer.valueOf(identifier));
                return identifier;
            }
        }
    }

    @Nullable
    public Drawable getResourceDrawable(Context context, @Nullable String str) {
        int resourceDrawableId = getResourceDrawableId(context, str);
        return resourceDrawableId > 0 ? context.getResources().getDrawable(resourceDrawableId) : null;
    }

    public Uri getResourceDrawableUri(Context context, @Nullable String str) {
        int resourceDrawableId = getResourceDrawableId(context, str);
        return resourceDrawableId > 0 ? new Builder().scheme("res").path(String.valueOf(resourceDrawableId)).build() : Uri.EMPTY;
    }
}
