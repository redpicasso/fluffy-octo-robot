package com.facebook.react.views.toolbar;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;
import androidx.core.view.ViewCompat;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.scroll.ReactScrollViewHelper;
import com.facebook.react.views.toolbar.events.ToolbarClickEvent;
import java.util.Map;
import javax.annotation.Nullable;

public class ReactToolbarManager extends ViewGroupManager<ReactToolbar> {
    private static final int COMMAND_DISMISS_POPUP_MENUS = 1;
    private static final String REACT_CLASS = "ToolbarAndroid";

    public String getName() {
        return REACT_CLASS;
    }

    public boolean needsCustomLayoutForChildren() {
        return true;
    }

    protected ReactToolbar createViewInstance(ThemedReactContext themedReactContext) {
        return new ReactToolbar(themedReactContext);
    }

    @ReactProp(name = "logo")
    public void setLogo(ReactToolbar reactToolbar, @Nullable ReadableMap readableMap) {
        reactToolbar.setLogoSource(readableMap);
    }

    @ReactProp(name = "navIcon")
    public void setNavIcon(ReactToolbar reactToolbar, @Nullable ReadableMap readableMap) {
        reactToolbar.setNavIconSource(readableMap);
    }

    @ReactProp(name = "overflowIcon")
    public void setOverflowIcon(ReactToolbar reactToolbar, @Nullable ReadableMap readableMap) {
        reactToolbar.setOverflowIconSource(readableMap);
    }

    @ReactProp(name = "rtl")
    public void setRtl(ReactToolbar reactToolbar, boolean z) {
        ViewCompat.setLayoutDirection(reactToolbar, z);
    }

    @ReactProp(name = "subtitle")
    public void setSubtitle(ReactToolbar reactToolbar, @Nullable String str) {
        reactToolbar.setSubtitle((CharSequence) str);
    }

    @ReactProp(customType = "Color", name = "subtitleColor")
    public void setSubtitleColor(ReactToolbar reactToolbar, @Nullable Integer num) {
        int[] defaultColors = getDefaultColors(reactToolbar.getContext());
        if (num != null) {
            reactToolbar.setSubtitleTextColor(num.intValue());
        } else {
            reactToolbar.setSubtitleTextColor(defaultColors[1]);
        }
    }

    @ReactProp(name = "title")
    public void setTitle(ReactToolbar reactToolbar, @Nullable String str) {
        reactToolbar.setTitle((CharSequence) str);
    }

    @ReactProp(customType = "Color", name = "titleColor")
    public void setTitleColor(ReactToolbar reactToolbar, @Nullable Integer num) {
        int[] defaultColors = getDefaultColors(reactToolbar.getContext());
        if (num != null) {
            reactToolbar.setTitleTextColor(num.intValue());
        } else {
            reactToolbar.setTitleTextColor(defaultColors[0]);
        }
    }

    @ReactProp(defaultFloat = Float.NaN, name = "contentInsetStart")
    public void setContentInsetStart(ReactToolbar reactToolbar, float f) {
        int i;
        if (Float.isNaN(f)) {
            i = getDefaultContentInsets(reactToolbar.getContext())[0];
        } else {
            i = Math.round(PixelUtil.toPixelFromDIP(f));
        }
        reactToolbar.setContentInsetsRelative(i, reactToolbar.getContentInsetEnd());
    }

    @ReactProp(defaultFloat = Float.NaN, name = "contentInsetEnd")
    public void setContentInsetEnd(ReactToolbar reactToolbar, float f) {
        int i;
        if (Float.isNaN(f)) {
            i = getDefaultContentInsets(reactToolbar.getContext())[1];
        } else {
            i = Math.round(PixelUtil.toPixelFromDIP(f));
        }
        reactToolbar.setContentInsetsRelative(reactToolbar.getContentInsetStart(), i);
    }

    @ReactProp(name = "nativeActions")
    public void setActions(ReactToolbar reactToolbar, @Nullable ReadableArray readableArray) {
        reactToolbar.setActions(readableArray);
    }

    protected void addEventEmitters(ThemedReactContext themedReactContext, final ReactToolbar reactToolbar) {
        final EventDispatcher eventDispatcher = ((UIManagerModule) themedReactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
        reactToolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                eventDispatcher.dispatchEvent(new ToolbarClickEvent(reactToolbar.getId(), -1));
            }
        });
        reactToolbar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem menuItem) {
                eventDispatcher.dispatchEvent(new ToolbarClickEvent(reactToolbar.getId(), menuItem.getOrder()));
                return true;
            }
        });
    }

    @Nullable
    public Map<String, Object> getExportedViewConstants() {
        return MapBuilder.of("ShowAsAction", MapBuilder.of(ReactScrollViewHelper.OVER_SCROLL_NEVER, Integer.valueOf(0), ReactScrollViewHelper.OVER_SCROLL_ALWAYS, Integer.valueOf(2), "ifRoom", Integer.valueOf(1)));
    }

    @Nullable
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of("dismissPopupMenus", Integer.valueOf(1));
    }

    public void receiveCommand(ReactToolbar reactToolbar, int i, @Nullable ReadableArray readableArray) {
        if (i == 1) {
            reactToolbar.dismissPopupMenus();
        } else {
            throw new IllegalArgumentException(String.format("Unsupported command %d received by %s.", new Object[]{Integer.valueOf(i), getClass().getSimpleName()}));
        }
    }

    private int[] getDefaultContentInsets(Context context) {
        Throwable th;
        Theme theme = context.getTheme();
        TypedArray typedArray = null;
        TypedArray obtainStyledAttributes;
        try {
            obtainStyledAttributes = theme.obtainStyledAttributes(new int[]{getIdentifier(context, "toolbarStyle")});
            try {
                typedArray = theme.obtainStyledAttributes(obtainStyledAttributes.getResourceId(0, 0), new int[]{getIdentifier(context, "contentInsetStart"), getIdentifier(context, "contentInsetEnd")});
                int dimensionPixelSize = typedArray.getDimensionPixelSize(0, 0);
                int dimensionPixelSize2 = typedArray.getDimensionPixelSize(1, 0);
                int[] iArr = new int[]{dimensionPixelSize, dimensionPixelSize2};
                recycleQuietly(obtainStyledAttributes);
                recycleQuietly(typedArray);
                return iArr;
            } catch (Throwable th2) {
                th = th2;
                recycleQuietly(obtainStyledAttributes);
                recycleQuietly(typedArray);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            obtainStyledAttributes = null;
            recycleQuietly(obtainStyledAttributes);
            recycleQuietly(typedArray);
            throw th;
        }
    }

    private static int[] getDefaultColors(Context context) {
        TypedArray obtainStyledAttributes;
        Throwable th;
        TypedArray typedArray;
        Theme theme = context.getTheme();
        TypedArray typedArray2 = null;
        TypedArray obtainStyledAttributes2;
        try {
            obtainStyledAttributes2 = theme.obtainStyledAttributes(new int[]{getIdentifier(context, "toolbarStyle")});
            try {
                obtainStyledAttributes = theme.obtainStyledAttributes(obtainStyledAttributes2.getResourceId(0, 0), new int[]{getIdentifier(context, "titleTextAppearance"), getIdentifier(context, "subtitleTextAppearance")});
            } catch (Throwable th2) {
                th = th2;
                obtainStyledAttributes = null;
                typedArray = obtainStyledAttributes;
                recycleQuietly(obtainStyledAttributes2);
                recycleQuietly(typedArray2);
                recycleQuietly(typedArray);
                recycleQuietly(obtainStyledAttributes);
                throw th;
            }
            try {
                int resourceId = obtainStyledAttributes.getResourceId(0, 0);
                int resourceId2 = obtainStyledAttributes.getResourceId(1, 0);
                typedArray = theme.obtainStyledAttributes(resourceId, new int[]{16842904});
                try {
                    typedArray2 = theme.obtainStyledAttributes(resourceId2, new int[]{16842904});
                    resourceId2 = typedArray.getColor(0, ViewCompat.MEASURED_STATE_MASK);
                    int color = typedArray2.getColor(0, ViewCompat.MEASURED_STATE_MASK);
                    int[] iArr = new int[]{resourceId2, color};
                    recycleQuietly(obtainStyledAttributes2);
                    recycleQuietly(obtainStyledAttributes);
                    recycleQuietly(typedArray);
                    recycleQuietly(typedArray2);
                    return iArr;
                } catch (Throwable th3) {
                    th = th3;
                    TypedArray typedArray3 = typedArray2;
                    typedArray2 = obtainStyledAttributes;
                    obtainStyledAttributes = typedArray3;
                    recycleQuietly(obtainStyledAttributes2);
                    recycleQuietly(typedArray2);
                    recycleQuietly(typedArray);
                    recycleQuietly(obtainStyledAttributes);
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                typedArray = null;
                typedArray2 = obtainStyledAttributes;
                obtainStyledAttributes = typedArray;
                recycleQuietly(obtainStyledAttributes2);
                recycleQuietly(typedArray2);
                recycleQuietly(typedArray);
                recycleQuietly(obtainStyledAttributes);
                throw th;
            }
        } catch (Throwable th5) {
            th = th5;
            obtainStyledAttributes = null;
            obtainStyledAttributes2 = obtainStyledAttributes;
            typedArray = obtainStyledAttributes2;
            recycleQuietly(obtainStyledAttributes2);
            recycleQuietly(typedArray2);
            recycleQuietly(typedArray);
            recycleQuietly(obtainStyledAttributes);
            throw th;
        }
    }

    private static void recycleQuietly(@Nullable TypedArray typedArray) {
        if (typedArray != null) {
            typedArray.recycle();
        }
    }

    private static int getIdentifier(Context context, String str) {
        return context.getResources().getIdentifier(str, "attr", context.getPackageName());
    }
}
