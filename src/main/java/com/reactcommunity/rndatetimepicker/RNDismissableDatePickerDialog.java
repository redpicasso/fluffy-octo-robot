package com.reactcommunity.rndatetimepicker;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.DatePicker;
import androidx.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RNDismissableDatePickerDialog extends DatePickerDialog {
    public RNDismissableDatePickerDialog(Context context, @Nullable OnDateSetListener onDateSetListener, int i, int i2, int i3, RNDatePickerDisplay rNDatePickerDisplay) {
        super(context, onDateSetListener, i, i2, i3);
        fixSpinner(context, i, i2, i3, rNDatePickerDisplay);
    }

    public RNDismissableDatePickerDialog(Context context, int i, @Nullable OnDateSetListener onDateSetListener, int i2, int i3, int i4, RNDatePickerDisplay rNDatePickerDisplay) {
        super(context, i, onDateSetListener, i2, i3, i4);
        fixSpinner(context, i2, i3, i4, rNDatePickerDisplay);
    }

    protected void onStop() {
        if (VERSION.SDK_INT > 19) {
            super.onStop();
        }
    }

    private void fixSpinner(Context context, int i, int i2, int i3, RNDatePickerDisplay rNDatePickerDisplay) {
        Context context2 = context;
        String str = "android.widget.DatePickerSpinnerDelegate";
        if (VERSION.SDK_INT == 24 && rNDatePickerDisplay == RNDatePickerDisplay.SPINNER) {
            try {
                context2.obtainStyledAttributes(null, (int[]) Class.forName("com.android.internal.R$styleable").getField("DatePicker").get(null), 16843612, 0).recycle();
                DatePicker datePicker = (DatePicker) ReflectionHelper.findField(DatePickerDialog.class, DatePicker.class, "mDatePicker").get(this);
                Field findField = ReflectionHelper.findField(DatePicker.class, Class.forName(str), "mDelegate");
                Object obj = findField.get(datePicker);
                if (obj.getClass() != Class.forName(str)) {
                    findField.set(datePicker, null);
                    datePicker.removeAllViews();
                    Method declaredMethod = DatePicker.class.getDeclaredMethod("createSpinnerUIDelegate", new Class[]{Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE});
                    declaredMethod.setAccessible(true);
                    findField.set(datePicker, declaredMethod.invoke(datePicker, new Object[]{context2, null, Integer.valueOf(16843612), Integer.valueOf(0)}));
                    datePicker.setCalendarViewShown(false);
                    datePicker.init(i, i2, i3, this);
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
}
