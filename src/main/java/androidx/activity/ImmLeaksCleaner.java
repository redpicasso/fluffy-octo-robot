package androidx.activity;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.MainThread;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleEventObserver;
import java.lang.reflect.Field;

@RequiresApi(19)
final class ImmLeaksCleaner implements LifecycleEventObserver {
    private static final int INIT_FAILED = 2;
    private static final int INIT_SUCCESS = 1;
    private static final int NOT_INITIALIAZED = 0;
    private static Field sHField;
    private static Field sNextServedViewField;
    private static int sReflectedFieldsInitialized;
    private static Field sServedViewField;
    private Activity mActivity;

    ImmLeaksCleaner(Activity activity) {
        this.mActivity = activity;
    }

    /* JADX WARNING: Missing block: B:28:0x0040, code:
            r3.isActive();
     */
    /* JADX WARNING: Missing block: B:31:0x0045, code:
            return;
     */
    public void onStateChanged(@androidx.annotation.NonNull androidx.lifecycle.LifecycleOwner r3, @androidx.annotation.NonNull androidx.lifecycle.Lifecycle.Event r4) {
        /*
        r2 = this;
        r3 = androidx.lifecycle.Lifecycle.Event.ON_DESTROY;
        if (r4 == r3) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        r3 = sReflectedFieldsInitialized;
        if (r3 != 0) goto L_0x000c;
    L_0x0009:
        initializeReflectiveFields();
    L_0x000c:
        r3 = sReflectedFieldsInitialized;
        r4 = 1;
        if (r3 != r4) goto L_0x004e;
    L_0x0011:
        r3 = r2.mActivity;
        r4 = "input_method";
        r3 = r3.getSystemService(r4);
        r3 = (android.view.inputmethod.InputMethodManager) r3;
        r4 = sHField;	 Catch:{ IllegalAccessException -> 0x004e }
        r4 = r4.get(r3);	 Catch:{ IllegalAccessException -> 0x004e }
        if (r4 != 0) goto L_0x0024;
    L_0x0023:
        return;
    L_0x0024:
        monitor-enter(r4);
        r0 = sServedViewField;	 Catch:{ IllegalAccessException -> 0x004a, ClassCastException -> 0x0048 }
        r0 = r0.get(r3);	 Catch:{ IllegalAccessException -> 0x004a, ClassCastException -> 0x0048 }
        r0 = (android.view.View) r0;	 Catch:{ IllegalAccessException -> 0x004a, ClassCastException -> 0x0048 }
        if (r0 != 0) goto L_0x0031;
    L_0x002f:
        monitor-exit(r4);	 Catch:{ all -> 0x0046 }
        return;
    L_0x0031:
        r0 = r0.isAttachedToWindow();	 Catch:{ all -> 0x0046 }
        if (r0 == 0) goto L_0x0039;
    L_0x0037:
        monitor-exit(r4);	 Catch:{ all -> 0x0046 }
        return;
    L_0x0039:
        r0 = sNextServedViewField;	 Catch:{ IllegalAccessException -> 0x0044 }
        r1 = 0;
        r0.set(r3, r1);	 Catch:{ IllegalAccessException -> 0x0044 }
        monitor-exit(r4);	 Catch:{ all -> 0x0046 }
        r3.isActive();
        goto L_0x004e;
    L_0x0044:
        monitor-exit(r4);	 Catch:{ all -> 0x0046 }
        return;
    L_0x0046:
        r3 = move-exception;
        goto L_0x004c;
    L_0x0048:
        monitor-exit(r4);	 Catch:{ all -> 0x0046 }
        return;
    L_0x004a:
        monitor-exit(r4);	 Catch:{ all -> 0x0046 }
        return;
    L_0x004c:
        monitor-exit(r4);	 Catch:{ all -> 0x0046 }
        throw r3;
    L_0x004e:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.activity.ImmLeaksCleaner.onStateChanged(androidx.lifecycle.LifecycleOwner, androidx.lifecycle.Lifecycle$Event):void");
    }

    @MainThread
    private static void initializeReflectiveFields() {
        try {
            sReflectedFieldsInitialized = 2;
            sServedViewField = InputMethodManager.class.getDeclaredField("mServedView");
            sServedViewField.setAccessible(true);
            sNextServedViewField = InputMethodManager.class.getDeclaredField("mNextServedView");
            sNextServedViewField.setAccessible(true);
            sHField = InputMethodManager.class.getDeclaredField("mH");
            sHField.setAccessible(true);
            sReflectedFieldsInitialized = 1;
        } catch (NoSuchFieldException unused) {
        }
    }
}
