package androidx.core.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface.OnKeyListener;
import android.os.Build.VERSION;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.Window.Callback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
public class KeyEventDispatcher {
    private static boolean sActionBarFieldsFetched = false;
    private static Method sActionBarOnMenuKeyMethod = null;
    private static boolean sDialogFieldsFetched = false;
    private static Field sDialogKeyListenerField;

    public interface Component {
        boolean superDispatchKeyEvent(KeyEvent keyEvent);
    }

    private KeyEventDispatcher() {
    }

    public static boolean dispatchBeforeHierarchy(@NonNull View view, @NonNull KeyEvent keyEvent) {
        return ViewCompat.dispatchUnhandledKeyEventBeforeHierarchy(view, keyEvent);
    }

    public static boolean dispatchKeyEvent(@NonNull Component component, @Nullable View view, @Nullable Callback callback, @NonNull KeyEvent keyEvent) {
        boolean z = false;
        if (component == null) {
            return false;
        }
        if (VERSION.SDK_INT >= 28) {
            return component.superDispatchKeyEvent(keyEvent);
        }
        if (callback instanceof Activity) {
            return activitySuperDispatchKeyEventPre28((Activity) callback, keyEvent);
        }
        if (callback instanceof Dialog) {
            return dialogSuperDispatchKeyEventPre28((Dialog) callback, keyEvent);
        }
        if ((view != null && ViewCompat.dispatchUnhandledKeyEventBeforeCallback(view, keyEvent)) || component.superDispatchKeyEvent(keyEvent)) {
            z = true;
        }
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d A:{RETURN, ExcHandler: java.lang.IllegalAccessException (unused java.lang.IllegalAccessException), Splitter: B:7:0x001e} */
    /* JADX WARNING: Missing block: B:10:0x002d, code:
            return false;
     */
    private static boolean actionBarOnMenuKeyEventPre28(android.app.ActionBar r6, android.view.KeyEvent r7) {
        /*
        r0 = sActionBarFieldsFetched;
        r1 = 1;
        r2 = 0;
        if (r0 != 0) goto L_0x001a;
    L_0x0006:
        r0 = r6.getClass();	 Catch:{ NoSuchMethodException -> 0x0018 }
        r3 = "onMenuKeyEvent";
        r4 = new java.lang.Class[r1];	 Catch:{ NoSuchMethodException -> 0x0018 }
        r5 = android.view.KeyEvent.class;
        r4[r2] = r5;	 Catch:{ NoSuchMethodException -> 0x0018 }
        r0 = r0.getMethod(r3, r4);	 Catch:{ NoSuchMethodException -> 0x0018 }
        sActionBarOnMenuKeyMethod = r0;	 Catch:{ NoSuchMethodException -> 0x0018 }
    L_0x0018:
        sActionBarFieldsFetched = r1;
    L_0x001a:
        r0 = sActionBarOnMenuKeyMethod;
        if (r0 == 0) goto L_0x002d;
    L_0x001e:
        r1 = new java.lang.Object[r1];	 Catch:{ IllegalAccessException -> 0x002d, IllegalAccessException -> 0x002d }
        r1[r2] = r7;	 Catch:{ IllegalAccessException -> 0x002d, IllegalAccessException -> 0x002d }
        r6 = r0.invoke(r6, r1);	 Catch:{ IllegalAccessException -> 0x002d, IllegalAccessException -> 0x002d }
        r6 = (java.lang.Boolean) r6;	 Catch:{ IllegalAccessException -> 0x002d, IllegalAccessException -> 0x002d }
        r6 = r6.booleanValue();	 Catch:{ IllegalAccessException -> 0x002d, IllegalAccessException -> 0x002d }
        return r6;
    L_0x002d:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.view.KeyEventDispatcher.actionBarOnMenuKeyEventPre28(android.app.ActionBar, android.view.KeyEvent):boolean");
    }

    private static boolean activitySuperDispatchKeyEventPre28(Activity activity, KeyEvent keyEvent) {
        activity.onUserInteraction();
        Window window = activity.getWindow();
        if (window.hasFeature(8)) {
            ActionBar actionBar = activity.getActionBar();
            if (keyEvent.getKeyCode() == 82 && actionBar != null && actionBarOnMenuKeyEventPre28(actionBar, keyEvent)) {
                return true;
            }
        }
        if (window.superDispatchKeyEvent(keyEvent)) {
            return true;
        }
        View decorView = window.getDecorView();
        if (ViewCompat.dispatchUnhandledKeyEventBeforeCallback(decorView, keyEvent)) {
            return true;
        }
        return keyEvent.dispatch(activity, decorView != null ? decorView.getKeyDispatcherState() : null, activity);
    }

    private static OnKeyListener getDialogKeyListenerPre28(Dialog dialog) {
        if (!sDialogFieldsFetched) {
            try {
                sDialogKeyListenerField = Dialog.class.getDeclaredField("mOnKeyListener");
                sDialogKeyListenerField.setAccessible(true);
            } catch (NoSuchFieldException unused) {
                sDialogFieldsFetched = true;
            }
        }
        Field field = sDialogKeyListenerField;
        if (field != null) {
            try {
                return (OnKeyListener) field.get(dialog);
            } catch (IllegalAccessException unused2) {
                return null;
            }
        }
    }

    private static boolean dialogSuperDispatchKeyEventPre28(Dialog dialog, KeyEvent keyEvent) {
        OnKeyListener dialogKeyListenerPre28 = getDialogKeyListenerPre28(dialog);
        if (dialogKeyListenerPre28 != null && dialogKeyListenerPre28.onKey(dialog, keyEvent.getKeyCode(), keyEvent)) {
            return true;
        }
        Window window = dialog.getWindow();
        if (window.superDispatchKeyEvent(keyEvent)) {
            return true;
        }
        View decorView = window.getDecorView();
        if (ViewCompat.dispatchUnhandledKeyEventBeforeCallback(decorView, keyEvent)) {
            return true;
        }
        return keyEvent.dispatch(dialog, decorView != null ? decorView.getKeyDispatcherState() : null, dialog);
    }
}
