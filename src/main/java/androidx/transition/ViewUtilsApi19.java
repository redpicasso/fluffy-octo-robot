package androidx.transition;

import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(19)
class ViewUtilsApi19 extends ViewUtilsBase {
    private static final String TAG = "ViewUtilsApi19";
    private static Method sGetTransitionAlphaMethod;
    private static boolean sGetTransitionAlphaMethodFetched;
    private static Method sSetTransitionAlphaMethod;
    private static boolean sSetTransitionAlphaMethodFetched;

    public void clearNonTransitionAlpha(@NonNull View view) {
    }

    public void saveNonTransitionAlpha(@NonNull View view) {
    }

    ViewUtilsApi19() {
    }

    public void setTransitionAlpha(@NonNull View view, float f) {
        fetchSetTransitionAlphaMethod();
        Method method = sSetTransitionAlphaMethod;
        if (method != null) {
            try {
                method.invoke(view, new Object[]{Float.valueOf(f)});
            } catch (IllegalAccessException unused) {
                return;
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        view.setAlpha(f);
    }

    public float getTransitionAlpha(@NonNull View view) {
        fetchGetTransitionAlphaMethod();
        Method method = sGetTransitionAlphaMethod;
        if (method != null) {
            try {
                view = ((Float) method.invoke(view, new Object[0])).floatValue();
                return view;
            } catch (IllegalAccessException unused) {
                return super.getTransitionAlpha(view);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }

    private void fetchSetTransitionAlphaMethod() {
        if (!sSetTransitionAlphaMethodFetched) {
            try {
                sSetTransitionAlphaMethod = View.class.getDeclaredMethod("setTransitionAlpha", new Class[]{Float.TYPE});
                sSetTransitionAlphaMethod.setAccessible(true);
            } catch (Throwable e) {
                Log.i(TAG, "Failed to retrieve setTransitionAlpha method", e);
            }
            sSetTransitionAlphaMethodFetched = true;
        }
    }

    private void fetchGetTransitionAlphaMethod() {
        if (!sGetTransitionAlphaMethodFetched) {
            try {
                sGetTransitionAlphaMethod = View.class.getDeclaredMethod("getTransitionAlpha", new Class[0]);
                sGetTransitionAlphaMethod.setAccessible(true);
            } catch (Throwable e) {
                Log.i(TAG, "Failed to retrieve getTransitionAlpha method", e);
            }
            sGetTransitionAlphaMethodFetched = true;
        }
    }
}
