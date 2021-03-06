package com.facebook.react.uimanager;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import androidx.core.view.ViewCompat;
import com.facebook.react.R;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.MatrixMathHelper.MatrixDecompositionContext;
import com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.util.ReactFindViewUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BaseViewManager<T extends View, C extends LayoutShadowNode> extends ViewManager<T, C> {
    private static final float CAMERA_DISTANCE_NORMALIZATION_MULTIPLIER = ((float) Math.sqrt(5.0d));
    private static final int PERSPECTIVE_ARRAY_INVERTED_CAMERA_DISTANCE_INDEX = 2;
    private static final String PROP_ACCESSIBILITY_ACTIONS = "accessibilityActions";
    private static final String PROP_ACCESSIBILITY_HINT = "accessibilityHint";
    private static final String PROP_ACCESSIBILITY_LABEL = "accessibilityLabel";
    private static final String PROP_ACCESSIBILITY_LIVE_REGION = "accessibilityLiveRegion";
    private static final String PROP_ACCESSIBILITY_ROLE = "accessibilityRole";
    private static final String PROP_ACCESSIBILITY_STATES = "accessibilityStates";
    private static final String PROP_BACKGROUND_COLOR = "backgroundColor";
    private static final String PROP_ELEVATION = "elevation";
    private static final String PROP_IMPORTANT_FOR_ACCESSIBILITY = "importantForAccessibility";
    public static final String PROP_NATIVE_ID = "nativeID";
    private static final String PROP_RENDER_TO_HARDWARE_TEXTURE = "renderToHardwareTextureAndroid";
    private static final String PROP_ROTATION = "rotation";
    private static final String PROP_SCALE_X = "scaleX";
    private static final String PROP_SCALE_Y = "scaleY";
    public static final String PROP_TEST_ID = "testID";
    private static final String PROP_TRANSFORM = "transform";
    private static final String PROP_TRANSLATE_X = "translateX";
    private static final String PROP_TRANSLATE_Y = "translateY";
    private static final String PROP_Z_INDEX = "zIndex";
    private static MatrixDecompositionContext sMatrixDecompositionContext = new MatrixDecompositionContext();
    public static final HashMap<String, Integer> sStateDescription = new HashMap();
    private static double[] sTransformDecompositionArray = new double[16];

    static {
        sStateDescription.put("busy", Integer.valueOf(R.string.state_busy_description));
        sStateDescription.put("expanded", Integer.valueOf(R.string.state_expanded_description));
        sStateDescription.put("collapsed", Integer.valueOf(R.string.state_collapsed_description));
    }

    @ReactProp(customType = "Color", defaultInt = 0, name = "backgroundColor")
    public void setBackgroundColor(@Nonnull T t, int i) {
        t.setBackgroundColor(i);
    }

    @ReactProp(name = "transform")
    public void setTransform(@Nonnull T t, @Nullable ReadableArray readableArray) {
        if (readableArray == null) {
            resetTransformProperty(t);
        } else {
            setTransformProperty(t, readableArray);
        }
    }

    @ReactProp(defaultFloat = 1.0f, name = "opacity")
    public void setOpacity(@Nonnull T t, float f) {
        t.setAlpha(f);
    }

    @ReactProp(name = "elevation")
    public void setElevation(@Nonnull T t, float f) {
        ViewCompat.setElevation(t, PixelUtil.toPixelFromDIP(f));
    }

    @ReactProp(name = "zIndex")
    public void setZIndex(@Nonnull T t, float f) {
        ViewGroupManager.setViewZIndex(t, Math.round(f));
        ViewParent parent = t.getParent();
        if (parent != null && (parent instanceof ReactZIndexedViewGroup)) {
            ((ReactZIndexedViewGroup) parent).updateDrawingOrder();
        }
    }

    @ReactProp(name = "renderToHardwareTextureAndroid")
    public void setRenderToHardwareTexture(@Nonnull T t, boolean z) {
        t.setLayerType(z ? 2 : 0, null);
    }

    @ReactProp(name = "testID")
    public void setTestId(@Nonnull T t, String str) {
        t.setTag(R.id.react_test_id, str);
        t.setTag(str);
    }

    @ReactProp(name = "nativeID")
    public void setNativeId(@Nonnull T t, String str) {
        t.setTag(R.id.view_tag_native_id, str);
        ReactFindViewUtil.notifyViewRendered(t);
    }

    @ReactProp(name = "accessibilityLabel")
    public void setAccessibilityLabel(@Nonnull T t, String str) {
        t.setTag(R.id.accessibility_label, str);
        updateViewContentDescription(t);
    }

    @ReactProp(name = "accessibilityHint")
    public void setAccessibilityHint(@Nonnull T t, String str) {
        t.setTag(R.id.accessibility_hint, str);
        updateViewContentDescription(t);
    }

    @ReactProp(name = "accessibilityRole")
    public void setAccessibilityRole(@Nonnull T t, @Nullable String str) {
        if (str != null) {
            t.setTag(R.id.accessibility_role, AccessibilityRole.fromValue(str));
        }
    }

    @ReactProp(name = "accessibilityStates")
    public void setViewStates(@Nonnull T t, @Nullable ReadableArray readableArray) {
        if (readableArray != null) {
            t.setTag(R.id.accessibility_states, readableArray);
            t.setSelected(false);
            t.setEnabled(true);
            Object obj = null;
            for (int i = 0; i < readableArray.size(); i++) {
                String string = readableArray.getString(i);
                if (sStateDescription.containsKey(string)) {
                    obj = 1;
                }
                if (string.equals("selected")) {
                    t.setSelected(true);
                } else if (string.equals("disabled")) {
                    t.setEnabled(false);
                }
            }
            if (obj != null) {
                updateViewContentDescription(t);
            }
        }
    }

    private void updateViewContentDescription(@Nonnull T t) {
        String str = (String) t.getTag(R.id.accessibility_label);
        ReadableArray readableArray = (ReadableArray) t.getTag(R.id.accessibility_states);
        String str2 = (String) t.getTag(R.id.accessibility_hint);
        Iterable arrayList = new ArrayList();
        if (str != null) {
            arrayList.add(str);
        }
        if (readableArray != null) {
            for (int i = 0; i < readableArray.size(); i++) {
                String string = readableArray.getString(i);
                if (sStateDescription.containsKey(string)) {
                    arrayList.add(t.getContext().getString(((Integer) sStateDescription.get(string)).intValue()));
                }
            }
        }
        if (str2 != null) {
            arrayList.add(str2);
        }
        if (arrayList.size() > 0) {
            t.setContentDescription(TextUtils.join(", ", arrayList));
        }
    }

    @ReactProp(name = "accessibilityActions")
    public void setAccessibilityActions(T t, ReadableArray readableArray) {
        if (readableArray != null) {
            t.setTag(R.id.accessibility_actions, readableArray);
        }
    }

    @ReactProp(name = "importantForAccessibility")
    public void setImportantForAccessibility(@Nonnull T t, @Nullable String str) {
        if (str == null || str.equals("auto")) {
            ViewCompat.setImportantForAccessibility(t, 0);
        } else if (str.equals("yes")) {
            ViewCompat.setImportantForAccessibility(t, 1);
        } else if (str.equals("no")) {
            ViewCompat.setImportantForAccessibility(t, 2);
        } else if (str.equals("no-hide-descendants")) {
            ViewCompat.setImportantForAccessibility(t, 4);
        }
    }

    @ReactProp(name = "rotation")
    @Deprecated
    public void setRotation(@Nonnull T t, float f) {
        t.setRotation(f);
    }

    @ReactProp(defaultFloat = 1.0f, name = "scaleX")
    @Deprecated
    public void setScaleX(@Nonnull T t, float f) {
        t.setScaleX(f);
    }

    @ReactProp(defaultFloat = 1.0f, name = "scaleY")
    @Deprecated
    public void setScaleY(@Nonnull T t, float f) {
        t.setScaleY(f);
    }

    @ReactProp(defaultFloat = 0.0f, name = "translateX")
    @Deprecated
    public void setTranslateX(@Nonnull T t, float f) {
        t.setTranslationX(PixelUtil.toPixelFromDIP(f));
    }

    @ReactProp(defaultFloat = 0.0f, name = "translateY")
    @Deprecated
    public void setTranslateY(@Nonnull T t, float f) {
        t.setTranslationY(PixelUtil.toPixelFromDIP(f));
    }

    @ReactProp(name = "accessibilityLiveRegion")
    public void setAccessibilityLiveRegion(@Nonnull T t, @Nullable String str) {
        if (str == null || str.equals(ViewProps.NONE)) {
            ViewCompat.setAccessibilityLiveRegion(t, 0);
        } else if (str.equals("polite")) {
            ViewCompat.setAccessibilityLiveRegion(t, 1);
        } else if (str.equals("assertive")) {
            ViewCompat.setAccessibilityLiveRegion(t, 2);
        }
    }

    private static void setTransformProperty(@Nonnull View view, ReadableArray readableArray) {
        TransformHelper.processTransform(readableArray, sTransformDecompositionArray);
        MatrixMathHelper.decomposeMatrix(sTransformDecompositionArray, sMatrixDecompositionContext);
        view.setTranslationX(PixelUtil.toPixelFromDIP((float) sMatrixDecompositionContext.translation[0]));
        view.setTranslationY(PixelUtil.toPixelFromDIP((float) sMatrixDecompositionContext.translation[1]));
        view.setRotation((float) sMatrixDecompositionContext.rotationDegrees[2]);
        view.setRotationX((float) sMatrixDecompositionContext.rotationDegrees[0]);
        view.setRotationY((float) sMatrixDecompositionContext.rotationDegrees[1]);
        view.setScaleX((float) sMatrixDecompositionContext.scale[0]);
        view.setScaleY((float) sMatrixDecompositionContext.scale[1]);
        double[] dArr = sMatrixDecompositionContext.perspective;
        if (dArr.length > 2) {
            float f = (float) dArr[2];
            if (f == 0.0f) {
                f = 7.8125E-4f;
            }
            float f2 = -1.0f / f;
            f = DisplayMetricsHolder.getScreenDisplayMetrics().density;
            view.setCameraDistance(((f * f) * f2) * CAMERA_DISTANCE_NORMALIZATION_MULTIPLIER);
        }
    }

    private static void resetTransformProperty(@Nonnull View view) {
        view.setTranslationX(PixelUtil.toPixelFromDIP(0.0f));
        view.setTranslationY(PixelUtil.toPixelFromDIP(0.0f));
        view.setRotation(0.0f);
        view.setRotationX(0.0f);
        view.setRotationY(0.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        view.setCameraDistance(0.0f);
    }

    private void updateViewAccessibility(@Nonnull T t) {
        ReactAccessibilityDelegate.setDelegate(t);
    }

    protected void onAfterUpdateTransaction(@Nonnull T t) {
        super.onAfterUpdateTransaction(t);
        updateViewAccessibility(t);
    }

    @Nullable
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.builder().put("performAction", MapBuilder.of("registrationName", "onAccessibilityAction")).build();
    }
}
