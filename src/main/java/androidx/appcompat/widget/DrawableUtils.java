package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.graphics.Insets;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build.VERSION;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.WrappedDrawable;
import com.facebook.react.uimanager.ViewProps;
import java.lang.reflect.Field;

@SuppressLint({"RestrictedAPI"})
@RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
public class DrawableUtils {
    private static final int[] CHECKED_STATE_SET = new int[]{16842912};
    private static final int[] EMPTY_STATE_SET = new int[0];
    public static final Rect INSETS_NONE = new Rect();
    private static final String TAG = "DrawableUtils";
    private static final String VECTOR_DRAWABLE_CLAZZ_NAME = "android.graphics.drawable.VectorDrawable";
    private static Class<?> sInsetsClazz;

    static {
        if (VERSION.SDK_INT >= 18) {
            try {
                sInsetsClazz = Class.forName("android.graphics.Insets");
            } catch (ClassNotFoundException unused) {
            }
        }
    }

    private DrawableUtils() {
    }

    public static Rect getOpticalBounds(Drawable drawable) {
        Rect rect;
        if (VERSION.SDK_INT >= 29) {
            Insets opticalInsets = drawable.getOpticalInsets();
            rect = new Rect();
            rect.left = opticalInsets.left;
            rect.right = opticalInsets.right;
            rect.top = opticalInsets.top;
            rect.bottom = opticalInsets.bottom;
            return rect;
        }
        if (sInsetsClazz != null) {
            try {
                drawable = DrawableCompat.unwrap(drawable);
                Object invoke = drawable.getClass().getMethod("getOpticalInsets", new Class[0]).invoke(drawable, new Object[0]);
                if (invoke != null) {
                    rect = new Rect();
                    for (Field field : sInsetsClazz.getFields()) {
                        String name = field.getName();
                        int i = -1;
                        switch (name.hashCode()) {
                            case -1383228885:
                                if (name.equals(ViewProps.BOTTOM)) {
                                    i = 3;
                                    break;
                                }
                                break;
                            case 115029:
                                if (name.equals(ViewProps.TOP)) {
                                    i = 1;
                                    break;
                                }
                                break;
                            case 3317767:
                                if (name.equals(ViewProps.LEFT)) {
                                    i = 0;
                                    break;
                                }
                                break;
                            case 108511772:
                                if (name.equals(ViewProps.RIGHT)) {
                                    i = 2;
                                    break;
                                }
                                break;
                        }
                        if (i == 0) {
                            rect.left = field.getInt(invoke);
                        } else if (i == 1) {
                            rect.top = field.getInt(invoke);
                        } else if (i == 2) {
                            rect.right = field.getInt(invoke);
                        } else if (i == 3) {
                            rect.bottom = field.getInt(invoke);
                        }
                    }
                    return rect;
                }
            } catch (Exception unused) {
                Log.e(TAG, "Couldn't obtain the optical insets. Ignoring.");
            }
        }
        return INSETS_NONE;
    }

    static void fixDrawable(@NonNull Drawable drawable) {
        if (VERSION.SDK_INT == 21) {
            if (VECTOR_DRAWABLE_CLAZZ_NAME.equals(drawable.getClass().getName())) {
                fixVectorDrawableTinting(drawable);
            }
        }
    }

    public static boolean canSafelyMutateDrawable(@NonNull Drawable drawable) {
        if (VERSION.SDK_INT < 15 && (drawable instanceof InsetDrawable)) {
            return false;
        }
        if (VERSION.SDK_INT < 15 && (drawable instanceof GradientDrawable)) {
            return false;
        }
        if (VERSION.SDK_INT < 17 && (drawable instanceof LayerDrawable)) {
            return false;
        }
        if (drawable instanceof DrawableContainer) {
            ConstantState constantState = drawable.getConstantState();
            if (constantState instanceof DrawableContainerState) {
                for (Drawable canSafelyMutateDrawable : ((DrawableContainerState) constantState).getChildren()) {
                    if (!canSafelyMutateDrawable(canSafelyMutateDrawable)) {
                        return false;
                    }
                }
            }
        } else if (drawable instanceof WrappedDrawable) {
            return canSafelyMutateDrawable(((WrappedDrawable) drawable).getWrappedDrawable());
        } else {
            if (drawable instanceof DrawableWrapper) {
                return canSafelyMutateDrawable(((DrawableWrapper) drawable).getWrappedDrawable());
            }
            if (drawable instanceof ScaleDrawable) {
                return canSafelyMutateDrawable(((ScaleDrawable) drawable).getDrawable());
            }
        }
        return true;
    }

    private static void fixVectorDrawableTinting(Drawable drawable) {
        int[] state = drawable.getState();
        if (state == null || state.length == 0) {
            drawable.setState(CHECKED_STATE_SET);
        } else {
            drawable.setState(EMPTY_STATE_SET);
        }
        drawable.setState(state);
    }

    public static Mode parseTintMode(int i, Mode mode) {
        if (i == 3) {
            return Mode.SRC_OVER;
        }
        if (i == 5) {
            return Mode.SRC_IN;
        }
        if (i == 9) {
            return Mode.SRC_ATOP;
        }
        switch (i) {
            case 14:
                return Mode.MULTIPLY;
            case 15:
                return Mode.SCREEN;
            case 16:
                return Mode.ADD;
            default:
                return mode;
        }
    }
}
