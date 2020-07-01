package com.reactcommunity.rndatetimepicker;

import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.widget.TimePicker;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = "RNTimePickerAndroid")
public class RNTimePickerDialogModule extends ReactContextBaseJavaModule {
    @VisibleForTesting
    public static final String FRAGMENT_TAG = "RNTimePickerAndroid";

    private class TimePickerDialogListener implements OnTimeSetListener, OnDismissListener, OnClickListener {
        private final Promise mPromise;
        private boolean mPromiseResolved = false;

        public TimePickerDialogListener(Promise promise) {
            this.mPromise = promise;
        }

        public void onTimeSet(TimePicker timePicker, int i, int i2) {
            if (!this.mPromiseResolved && RNTimePickerDialogModule.this.access$100().hasActiveCatalystInstance()) {
                WritableMap writableNativeMap = new WritableNativeMap();
                writableNativeMap.putString("action", RNConstants.ACTION_TIME_SET);
                writableNativeMap.putInt("hour", i);
                writableNativeMap.putInt("minute", i2);
                this.mPromise.resolve(writableNativeMap);
                this.mPromiseResolved = true;
            }
        }

        public void onDismiss(DialogInterface dialogInterface) {
            if (!this.mPromiseResolved && RNTimePickerDialogModule.this.access$100().hasActiveCatalystInstance()) {
                WritableMap writableNativeMap = new WritableNativeMap();
                writableNativeMap.putString("action", RNConstants.ACTION_DISMISSED);
                this.mPromise.resolve(writableNativeMap);
                this.mPromiseResolved = true;
            }
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            if (!this.mPromiseResolved && RNTimePickerDialogModule.this.access$100().hasActiveCatalystInstance()) {
                WritableMap writableNativeMap = new WritableNativeMap();
                writableNativeMap.putString("action", RNConstants.ACTION_NEUTRAL_BUTTON);
                this.mPromise.resolve(writableNativeMap);
                this.mPromiseResolved = true;
            }
        }
    }

    public String getName() {
        return FRAGMENT_TAG;
    }

    public RNTimePickerDialogModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @ReactMethod
    public void open(@Nullable final ReadableMap readableMap, Promise promise) {
        FragmentActivity fragmentActivity = (FragmentActivity) access$700();
        if (fragmentActivity == null) {
            promise.reject(RNConstants.ERROR_NO_ACTIVITY, "Tried to open a TimePicker dialog while not attached to an Activity");
            return;
        }
        FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
        String str = FRAGMENT_TAG;
        final RNTimePickerDialogFragment rNTimePickerDialogFragment = (RNTimePickerDialogFragment) supportFragmentManager.findFragmentByTag(str);
        if (rNTimePickerDialogFragment == null || readableMap == null) {
            rNTimePickerDialogFragment = new RNTimePickerDialogFragment();
            if (readableMap != null) {
                rNTimePickerDialogFragment.setArguments(createFragmentArguments(readableMap));
            }
            OnClickListener timePickerDialogListener = new TimePickerDialogListener(promise);
            rNTimePickerDialogFragment.setOnDismissListener(timePickerDialogListener);
            rNTimePickerDialogFragment.setOnTimeSetListener(timePickerDialogListener);
            rNTimePickerDialogFragment.setOnNeutralButtonActionListener(timePickerDialogListener);
            rNTimePickerDialogFragment.show(supportFragmentManager, str);
            return;
        }
        UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
                rNTimePickerDialogFragment.update(RNTimePickerDialogModule.this.createFragmentArguments(readableMap));
            }
        });
    }

    private Bundle createFragmentArguments(ReadableMap readableMap) {
        Bundle bundle = new Bundle();
        String str = "value";
        if (readableMap.hasKey(str) && !readableMap.isNull(str)) {
            bundle.putLong(str, (long) readableMap.getDouble(str));
        }
        str = RNConstants.ARG_IS24HOUR;
        if (readableMap.hasKey(str) && !readableMap.isNull(str)) {
            bundle.putBoolean(str, readableMap.getBoolean(str));
        }
        str = "display";
        if (readableMap.hasKey(str) && !readableMap.isNull(str)) {
            bundle.putString(str, readableMap.getString(str));
        }
        str = RNConstants.ARG_NEUTRAL_BUTTON_LABEL;
        if (readableMap.hasKey(str) && !readableMap.isNull(str)) {
            bundle.putString(str, readableMap.getString(str));
        }
        return bundle;
    }
}
