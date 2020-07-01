package com.reactcommunity.rndatetimepicker;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.TimePicker;
import androidx.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class RNDismissableTimePickerDialog extends TimePickerDialog {
    public RNDismissableTimePickerDialog(Context context, @Nullable OnTimeSetListener onTimeSetListener, int i, int i2, boolean z, RNTimePickerDisplay rNTimePickerDisplay) {
        super(context, onTimeSetListener, i, i2, z);
        fixSpinner(context, i, i2, z, rNTimePickerDisplay);
    }

    public RNDismissableTimePickerDialog(Context context, int i, @Nullable OnTimeSetListener onTimeSetListener, int i2, int i3, boolean z, RNTimePickerDisplay rNTimePickerDisplay) {
        super(context, i, onTimeSetListener, i2, i3, z);
        fixSpinner(context, i2, i3, z, rNTimePickerDisplay);
    }

    protected void onStop() {
        if (VERSION.SDK_INT > 19) {
            super.onStop();
        }
    }

    private void fixSpinner(Context context, int i, int i2, boolean z, RNTimePickerDisplay rNTimePickerDisplay) {
        Context context2 = context;
        if (VERSION.SDK_INT == 24 && rNTimePickerDisplay == RNTimePickerDisplay.SPINNER) {
            try {
                context2.obtainStyledAttributes(null, (int[]) Class.forName("com.android.internal.R$styleable").getField("TimePicker").get(null), 16843933, 0).recycle();
                TimePicker timePicker = (TimePicker) ReflectionHelper.findField(TimePickerDialog.class, TimePicker.class, "mTimePicker").get(this);
                Field findField = ReflectionHelper.findField(TimePicker.class, Class.forName("android.widget.TimePicker$TimePickerDelegate"), "mDelegate");
                Object obj = findField.get(timePicker);
                Class cls = Class.forName("android.widget.TimePickerSpinnerDelegate");
                if (obj.getClass() != cls) {
                    findField.set(timePicker, null);
                    timePicker.removeAllViews();
                    Constructor constructor = cls.getConstructor(new Class[]{TimePicker.class, Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE});
                    constructor.setAccessible(true);
                    findField.set(timePicker, constructor.newInstance(new Object[]{timePicker, context2, null, Integer.valueOf(16843933), Integer.valueOf(0)}));
                    timePicker.setIs24HourView(Boolean.valueOf(z));
                    timePicker.setCurrentHour(Integer.valueOf(i));
                    timePicker.setCurrentMinute(Integer.valueOf(i2));
                    timePicker.setOnTimeChangedListener(this);
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
}
