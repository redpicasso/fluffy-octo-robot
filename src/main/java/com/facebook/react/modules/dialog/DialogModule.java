package com.facebook.react.modules.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.SoftAssertions;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@ReactModule(name = "DialogManagerAndroid")
public class DialogModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    static final String ACTION_BUTTON_CLICKED = "buttonClicked";
    static final String ACTION_DISMISSED = "dismissed";
    static final Map<String, Object> CONSTANTS = MapBuilder.of(ACTION_BUTTON_CLICKED, ACTION_BUTTON_CLICKED, "dismissed", "dismissed", KEY_BUTTON_POSITIVE, Integer.valueOf(-1), KEY_BUTTON_NEGATIVE, Integer.valueOf(-2), KEY_BUTTON_NEUTRAL, Integer.valueOf(-3));
    static final String FRAGMENT_TAG = "com.facebook.catalyst.react.dialog.DialogModule";
    static final String KEY_BUTTON_NEGATIVE = "buttonNegative";
    static final String KEY_BUTTON_NEUTRAL = "buttonNeutral";
    static final String KEY_BUTTON_POSITIVE = "buttonPositive";
    static final String KEY_CANCELABLE = "cancelable";
    static final String KEY_ITEMS = "items";
    static final String KEY_MESSAGE = "message";
    static final String KEY_TITLE = "title";
    public static final String NAME = "DialogManagerAndroid";
    private boolean mIsInForeground;

    class AlertFragmentListener implements OnClickListener, OnDismissListener {
        private final Callback mCallback;
        private boolean mCallbackConsumed = false;

        public AlertFragmentListener(Callback callback) {
            this.mCallback = callback;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            if (!this.mCallbackConsumed && DialogModule.this.access$200().hasActiveCatalystInstance()) {
                this.mCallback.invoke(DialogModule.ACTION_BUTTON_CLICKED, Integer.valueOf(i));
                this.mCallbackConsumed = true;
            }
        }

        public void onDismiss(DialogInterface dialogInterface) {
            if (!this.mCallbackConsumed && DialogModule.this.access$200().hasActiveCatalystInstance()) {
                this.mCallback.invoke("dismissed");
                this.mCallbackConsumed = true;
            }
        }
    }

    private class FragmentManagerHelper {
        @Nonnull
        private final FragmentManager mFragmentManager;
        @Nullable
        private Object mFragmentToShow;

        public FragmentManagerHelper(@Nonnull FragmentManager fragmentManager) {
            this.mFragmentManager = fragmentManager;
        }

        public void showPendingAlert() {
            UiThreadUtil.assertOnUiThread();
            SoftAssertions.assertCondition(DialogModule.this.mIsInForeground, "showPendingAlert() called in background");
            if (this.mFragmentToShow != null) {
                dismissExisting();
                ((AlertFragment) this.mFragmentToShow).show(this.mFragmentManager, DialogModule.FRAGMENT_TAG);
                this.mFragmentToShow = null;
            }
        }

        private void dismissExisting() {
            if (DialogModule.this.mIsInForeground) {
                AlertFragment alertFragment = (AlertFragment) this.mFragmentManager.findFragmentByTag(DialogModule.FRAGMENT_TAG);
                if (alertFragment != null && alertFragment.isResumed()) {
                    alertFragment.dismiss();
                }
            }
        }

        public void showNewAlert(Bundle bundle, Callback callback) {
            UiThreadUtil.assertOnUiThread();
            dismissExisting();
            AlertFragment alertFragment = new AlertFragment(callback != null ? new AlertFragmentListener(callback) : null, bundle);
            if (!DialogModule.this.mIsInForeground || this.mFragmentManager.isStateSaved()) {
                this.mFragmentToShow = alertFragment;
                return;
            }
            String str = DialogModule.KEY_CANCELABLE;
            if (bundle.containsKey(str)) {
                alertFragment.setCancelable(bundle.getBoolean(str));
            }
            alertFragment.show(this.mFragmentManager, DialogModule.FRAGMENT_TAG);
        }
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

    public void onHostDestroy() {
    }

    public DialogModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    public Map<String, Object> getConstants() {
        return CONSTANTS;
    }

    public void initialize() {
        access$200().addLifecycleEventListener(this);
    }

    public void onHostPause() {
        this.mIsInForeground = false;
    }

    public void onHostResume() {
        this.mIsInForeground = true;
        FragmentManagerHelper fragmentManagerHelper = getFragmentManagerHelper();
        if (fragmentManagerHelper != null) {
            fragmentManagerHelper.showPendingAlert();
        } else {
            FLog.w(DialogModule.class, "onHostResume called but no FragmentManager found");
        }
    }

    @ReactMethod
    public void showAlert(ReadableMap readableMap, Callback callback, final Callback callback2) {
        final FragmentManagerHelper fragmentManagerHelper = getFragmentManagerHelper();
        int i = 0;
        if (fragmentManagerHelper == null) {
            callback.invoke("Tried to show an alert while not attached to an Activity");
            return;
        }
        final Bundle bundle = new Bundle();
        String str = KEY_TITLE;
        if (readableMap.hasKey(str)) {
            bundle.putString(str, readableMap.getString(str));
        }
        str = KEY_MESSAGE;
        if (readableMap.hasKey(str)) {
            bundle.putString(str, readableMap.getString(str));
        }
        str = KEY_BUTTON_POSITIVE;
        if (readableMap.hasKey(str)) {
            bundle.putString("button_positive", readableMap.getString(str));
        }
        str = KEY_BUTTON_NEGATIVE;
        if (readableMap.hasKey(str)) {
            bundle.putString("button_negative", readableMap.getString(str));
        }
        str = KEY_BUTTON_NEUTRAL;
        if (readableMap.hasKey(str)) {
            bundle.putString("button_neutral", readableMap.getString(str));
        }
        str = KEY_ITEMS;
        if (readableMap.hasKey(str)) {
            ReadableArray array = readableMap.getArray(str);
            CharSequence[] charSequenceArr = new CharSequence[array.size()];
            while (i < array.size()) {
                charSequenceArr[i] = array.getString(i);
                i++;
            }
            bundle.putCharSequenceArray(str, charSequenceArr);
        }
        String str2 = KEY_CANCELABLE;
        if (readableMap.hasKey(str2)) {
            bundle.putBoolean(str2, readableMap.getBoolean(str2));
        }
        UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
                fragmentManagerHelper.showNewAlert(bundle, callback2);
            }
        });
    }

    @Nullable
    private FragmentManagerHelper getFragmentManagerHelper() {
        Activity currentActivity = access$700();
        if (currentActivity == null) {
            return null;
        }
        return new FragmentManagerHelper(((FragmentActivity) currentActivity).getSupportFragmentManager());
    }
}
