package com.facebook.react.modules.datepicker;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.module.annotations.ReactModule;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@ReactModule(name = "DatePickerAndroid")
public class DatePickerDialogModule extends ReactContextBaseJavaModule {
    static final String ACTION_DATE_SET = "dateSetAction";
    static final String ACTION_DISMISSED = "dismissedAction";
    static final String ARG_DATE = "date";
    static final String ARG_MAXDATE = "maxDate";
    static final String ARG_MINDATE = "minDate";
    static final String ARG_MODE = "mode";
    private static final String ERROR_NO_ACTIVITY = "E_NO_ACTIVITY";
    @VisibleForTesting
    public static final String FRAGMENT_TAG = "DatePickerAndroid";

    private class DatePickerDialogListener implements OnDateSetListener, OnDismissListener {
        private final Promise mPromise;
        private boolean mPromiseResolved = false;

        public DatePickerDialogListener(Promise promise) {
            this.mPromise = promise;
        }

        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
            if (!this.mPromiseResolved && DatePickerDialogModule.this.access$100().hasActiveCatalystInstance()) {
                WritableMap writableNativeMap = new WritableNativeMap();
                writableNativeMap.putString("action", "dateSetAction");
                writableNativeMap.putInt("year", i);
                writableNativeMap.putInt("month", i2);
                writableNativeMap.putInt("day", i3);
                this.mPromise.resolve(writableNativeMap);
                this.mPromiseResolved = true;
            }
        }

        public void onDismiss(DialogInterface dialogInterface) {
            if (!this.mPromiseResolved && DatePickerDialogModule.this.access$100().hasActiveCatalystInstance()) {
                WritableMap writableNativeMap = new WritableNativeMap();
                writableNativeMap.putString("action", "dismissedAction");
                this.mPromise.resolve(writableNativeMap);
                this.mPromiseResolved = true;
            }
        }
    }

    @Nonnull
    public String getName() {
        return FRAGMENT_TAG;
    }

    public DatePickerDialogModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @ReactMethod
    public void open(@Nullable ReadableMap readableMap, Promise promise) {
        FragmentActivity fragmentActivity = (FragmentActivity) access$700();
        if (fragmentActivity == null) {
            promise.reject("E_NO_ACTIVITY", "Tried to open a DatePicker dialog while not attached to an Activity");
            return;
        }
        FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
        String str = FRAGMENT_TAG;
        DialogFragment dialogFragment = (DialogFragment) supportFragmentManager.findFragmentByTag(str);
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
        if (readableMap != null) {
            datePickerDialogFragment.setArguments(createFragmentArguments(readableMap));
        }
        Object datePickerDialogListener = new DatePickerDialogListener(promise);
        datePickerDialogFragment.setOnDismissListener(datePickerDialogListener);
        datePickerDialogFragment.setOnDateSetListener(datePickerDialogListener);
        datePickerDialogFragment.show(supportFragmentManager, str);
    }

    private Bundle createFragmentArguments(ReadableMap readableMap) {
        Bundle bundle = new Bundle();
        String str = ARG_DATE;
        if (readableMap.hasKey(str) && !readableMap.isNull(str)) {
            bundle.putLong(str, (long) readableMap.getDouble(str));
        }
        str = ARG_MINDATE;
        if (readableMap.hasKey(str) && !readableMap.isNull(str)) {
            bundle.putLong(str, (long) readableMap.getDouble(str));
        }
        str = ARG_MAXDATE;
        if (readableMap.hasKey(str) && !readableMap.isNull(str)) {
            bundle.putLong(str, (long) readableMap.getDouble(str));
        }
        str = ARG_MODE;
        if (readableMap.hasKey(str) && !readableMap.isNull(str)) {
            bundle.putString(str, readableMap.getString(str));
        }
        return bundle;
    }
}
