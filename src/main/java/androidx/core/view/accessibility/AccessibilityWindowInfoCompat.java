package androidx.core.view.accessibility;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityWindowInfo;

public class AccessibilityWindowInfoCompat {
    public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
    public static final int TYPE_APPLICATION = 1;
    public static final int TYPE_INPUT_METHOD = 2;
    public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
    public static final int TYPE_SYSTEM = 3;
    private static final int UNDEFINED = -1;
    private Object mInfo;

    private static String typeToString(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? "<UNKNOWN>" : "TYPE_ACCESSIBILITY_OVERLAY" : "TYPE_SYSTEM" : "TYPE_INPUT_METHOD" : "TYPE_APPLICATION";
    }

    static AccessibilityWindowInfoCompat wrapNonNullInstance(Object obj) {
        return obj != null ? new AccessibilityWindowInfoCompat(obj) : null;
    }

    private AccessibilityWindowInfoCompat(Object obj) {
        this.mInfo = obj;
    }

    public int getType() {
        return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo) this.mInfo).getType() : -1;
    }

    public int getLayer() {
        return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo) this.mInfo).getLayer() : -1;
    }

    public AccessibilityNodeInfoCompat getRoot() {
        return VERSION.SDK_INT >= 21 ? AccessibilityNodeInfoCompat.wrapNonNullInstance(((AccessibilityWindowInfo) this.mInfo).getRoot()) : null;
    }

    public AccessibilityWindowInfoCompat getParent() {
        return VERSION.SDK_INT >= 21 ? wrapNonNullInstance(((AccessibilityWindowInfo) this.mInfo).getParent()) : null;
    }

    public int getId() {
        return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo) this.mInfo).getId() : -1;
    }

    public void getBoundsInScreen(Rect rect) {
        if (VERSION.SDK_INT >= 21) {
            ((AccessibilityWindowInfo) this.mInfo).getBoundsInScreen(rect);
        }
    }

    public boolean isActive() {
        return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo) this.mInfo).isActive() : true;
    }

    public boolean isFocused() {
        return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo) this.mInfo).isFocused() : true;
    }

    public boolean isAccessibilityFocused() {
        return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo) this.mInfo).isAccessibilityFocused() : true;
    }

    public int getChildCount() {
        return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo) this.mInfo).getChildCount() : 0;
    }

    public AccessibilityWindowInfoCompat getChild(int i) {
        return VERSION.SDK_INT >= 21 ? wrapNonNullInstance(((AccessibilityWindowInfo) this.mInfo).getChild(i)) : null;
    }

    public CharSequence getTitle() {
        return VERSION.SDK_INT >= 24 ? ((AccessibilityWindowInfo) this.mInfo).getTitle() : null;
    }

    public AccessibilityNodeInfoCompat getAnchor() {
        return VERSION.SDK_INT >= 24 ? AccessibilityNodeInfoCompat.wrapNonNullInstance(((AccessibilityWindowInfo) this.mInfo).getAnchor()) : null;
    }

    public static AccessibilityWindowInfoCompat obtain() {
        return VERSION.SDK_INT >= 21 ? wrapNonNullInstance(AccessibilityWindowInfo.obtain()) : null;
    }

    public static AccessibilityWindowInfoCompat obtain(AccessibilityWindowInfoCompat accessibilityWindowInfoCompat) {
        if (VERSION.SDK_INT < 21 || accessibilityWindowInfoCompat == null) {
            return null;
        }
        return wrapNonNullInstance(AccessibilityWindowInfo.obtain((AccessibilityWindowInfo) accessibilityWindowInfoCompat.mInfo));
    }

    public void recycle() {
        if (VERSION.SDK_INT >= 21) {
            ((AccessibilityWindowInfo) this.mInfo).recycle();
        }
    }

    public int hashCode() {
        Object obj = this.mInfo;
        return obj == null ? 0 : obj.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AccessibilityWindowInfoCompat accessibilityWindowInfoCompat = (AccessibilityWindowInfoCompat) obj;
        Object obj2 = this.mInfo;
        if (obj2 == null) {
            if (accessibilityWindowInfoCompat.mInfo != null) {
                return false;
            }
        } else if (!obj2.equals(accessibilityWindowInfoCompat.mInfo)) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Rect rect = new Rect();
        getBoundsInScreen(rect);
        stringBuilder.append("AccessibilityWindowInfo[");
        stringBuilder.append("id=");
        stringBuilder.append(getId());
        stringBuilder.append(", type=");
        stringBuilder.append(typeToString(getType()));
        stringBuilder.append(", layer=");
        stringBuilder.append(getLayer());
        stringBuilder.append(", bounds=");
        stringBuilder.append(rect);
        stringBuilder.append(", focused=");
        stringBuilder.append(isFocused());
        stringBuilder.append(", active=");
        stringBuilder.append(isActive());
        stringBuilder.append(", hasParent=");
        boolean z = true;
        stringBuilder.append(getParent() != null);
        stringBuilder.append(", hasChildren=");
        if (getChildCount() <= 0) {
            z = false;
        }
        stringBuilder.append(z);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}
