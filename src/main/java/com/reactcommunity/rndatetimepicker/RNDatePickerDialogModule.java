package com.reactcommunity.rndatetimepicker;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
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

@ReactModule(name = "RNDatePickerAndroid")
public class RNDatePickerDialogModule extends ReactContextBaseJavaModule {
    @VisibleForTesting
    public static final String FRAGMENT_TAG = "RNDatePickerAndroid";

    private class DatePickerDialogListener implements OnDateSetListener, OnDismissListener, OnClickListener {
        private final Promise mPromise;
        private boolean mPromiseResolved = false;

        public DatePickerDialogListener(Promise promise) {
            this.mPromise = promise;
        }

        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
            if (!this.mPromiseResolved && RNDatePickerDialogModule.this.access$100().hasActiveCatalystInstance()) {
                WritableMap writableNativeMap = new WritableNativeMap();
                writableNativeMap.putString("action", RNConstants.ACTION_DATE_SET);
                writableNativeMap.putInt("year", i);
                writableNativeMap.putInt("month", i2);
                writableNativeMap.putInt("day", i3);
                this.mPromise.resolve(writableNativeMap);
                this.mPromiseResolved = true;
            }
        }

        public void onDismiss(DialogInterface dialogInterface) {
            if (!this.mPromiseResolved && RNDatePickerDialogModule.this.access$100().hasActiveCatalystInstance()) {
                WritableMap writableNativeMap = new WritableNativeMap();
                writableNativeMap.putString("action", RNConstants.ACTION_DISMISSED);
                this.mPromise.resolve(writableNativeMap);
                this.mPromiseResolved = true;
            }
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            if (!this.mPromiseResolved && RNDatePickerDialogModule.this.access$100().hasActiveCatalystInstance()) {
                WritableMap writableNativeMap = new WritableNativeMap();
                writableNativeMap.putString("action", RNConstants.ACTION_NEUTRAL_BUTTON);
                this.mPromise.resolve(writableNativeMap);
                this.mPromiseResolved = true;
            }
        }
    }

    @NonNull
    public String getName() {
        return FRAGMENT_TAG;
    }

    public RNDatePickerDialogModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @ReactMethod
    public void open(@Nullable final ReadableMap readableMap, Promise promise) {
        FragmentActivity fragmentActivity = (FragmentActivity) access$700();
        if (fragmentActivity == null) {
            promise.reject(RNConstants.ERROR_NO_ACTIVITY, "Tried to open a DatePicker dialog while not attached to an Activity");
            return;
        }
        FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
        String str = FRAGMENT_TAG;
        final RNDatePickerDialogFragment rNDatePickerDialogFragment = (RNDatePickerDialogFragment) supportFragmentManager.findFragmentByTag(str);
        if (rNDatePickerDialogFragment == null || readableMap == null) {
            rNDatePickerDialogFragment = new RNDatePickerDialogFragment();
            if (readableMap != null) {
                rNDatePickerDialogFragment.setArguments(createFragmentArguments(readableMap));
            }
            OnClickListener datePickerDialogListener = new DatePickerDialogListener(promise);
            rNDatePickerDialogFragment.setOnDismissListener(datePickerDialogListener);
            rNDatePickerDialogFragment.setOnDateSetListener(datePickerDialogListener);
            rNDatePickerDialogFragment.setOnNeutralButtonActionListener(datePickerDialogListener);
            rNDatePickerDialogFragment.show(supportFragmentManager, str);
            return;
        }
        UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
                rNDatePickerDialogFragment.update(RNDatePickerDialogModule.this.createFragmentArguments(readableMap));
            }
        });
    }

    private Bundle createFragmentArguments(ReadableMap readableMap) {
        Bundle bundle = new Bundle();
        String str = "value";
        if (readableMap.hasKey(str) && !readableMap.isNull(str)) {
            bundle.putLong(str, (long) readableMap.getDouble(str));
        }
        str = RNConstants.ARG_MINDATE;
        if (readableMap.hasKey(str) && !readableMap.isNull(str)) {
            bundle.putLong(str, (long) readableMap.getDouble(str));
        }
        str = RNConstants.ARG_MAXDATE;
        if (readableMap.hasKey(str) && !readableMap.isNull(str)) {
            bundle.putLong(str, (long) readableMap.getDouble(str));
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
