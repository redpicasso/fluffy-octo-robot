package com.reactcommunity.rndatetimepicker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.format.DateFormat;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.Locale;

public class RNTimePickerDialogFragment extends DialogFragment {
    @Nullable
    private static OnClickListener mOnNeutralButtonActionListener;
    private TimePickerDialog instance;
    @Nullable
    private OnDismissListener mOnDismissListener;
    @Nullable
    private OnTimeSetListener mOnTimeSetListener;

    /* renamed from: com.reactcommunity.rndatetimepicker.RNTimePickerDialogFragment$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$reactcommunity$rndatetimepicker$RNTimePickerDisplay = new int[RNTimePickerDisplay.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:5:0x001f, code:
            return;
     */
        static {
            /*
            r0 = com.reactcommunity.rndatetimepicker.RNTimePickerDisplay.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$reactcommunity$rndatetimepicker$RNTimePickerDisplay = r0;
            r0 = $SwitchMap$com$reactcommunity$rndatetimepicker$RNTimePickerDisplay;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.reactcommunity.rndatetimepicker.RNTimePickerDisplay.CLOCK;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$reactcommunity$rndatetimepicker$RNTimePickerDisplay;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.reactcommunity.rndatetimepicker.RNTimePickerDisplay.SPINNER;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.reactcommunity.rndatetimepicker.RNTimePickerDialogFragment.1.<clinit>():void");
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        this.instance = createDialog(getArguments(), getLifecycleActivity(), this.mOnTimeSetListener);
        return this.instance;
    }

    public void update(Bundle bundle) {
        RNDate rNDate = new RNDate(bundle);
        this.instance.updateTime(rNDate.hour(), rNDate.minute());
    }

    static TimePickerDialog getDialog(Bundle bundle, Context context, @Nullable OnTimeSetListener onTimeSetListener) {
        RNDate rNDate = new RNDate(bundle);
        int hour = rNDate.hour();
        int minute = rNDate.minute();
        boolean is24HourFormat = DateFormat.is24HourFormat(context);
        RNTimePickerDisplay rNTimePickerDisplay = RNTimePickerDisplay.DEFAULT;
        if (bundle != null) {
            String str = "display";
            if (bundle.getString(str, null) != null) {
                rNTimePickerDisplay = RNTimePickerDisplay.valueOf(bundle.getString(str).toUpperCase(Locale.US));
            }
        }
        RNTimePickerDisplay rNTimePickerDisplay2 = rNTimePickerDisplay;
        boolean z = bundle != null ? bundle.getBoolean(RNConstants.ARG_IS24HOUR, DateFormat.is24HourFormat(context)) : is24HourFormat;
        if (VERSION.SDK_INT >= 21) {
            int i = AnonymousClass1.$SwitchMap$com$reactcommunity$rndatetimepicker$RNTimePickerDisplay[rNTimePickerDisplay2.ordinal()];
            if (i == 1 || i == 2) {
                return new RNDismissableTimePickerDialog(context, context.getResources().getIdentifier(rNTimePickerDisplay2 == RNTimePickerDisplay.CLOCK ? "ClockTimePickerDialog" : "SpinnerTimePickerDialog", "style", context.getPackageName()), onTimeSetListener, hour, minute, z, rNTimePickerDisplay2);
            }
        }
        return new RNDismissableTimePickerDialog(context, onTimeSetListener, hour, minute, z, rNTimePickerDisplay2);
    }

    static TimePickerDialog createDialog(Bundle bundle, Context context, @Nullable OnTimeSetListener onTimeSetListener) {
        TimePickerDialog dialog = getDialog(bundle, context, onTimeSetListener);
        if (bundle != null) {
            String str = RNConstants.ARG_NEUTRAL_BUTTON_LABEL;
            if (bundle.containsKey(str)) {
                dialog.setButton(-3, bundle.getString(str), mOnNeutralButtonActionListener);
            }
        }
        return dialog;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialogInterface);
        }
    }

    public void setOnDismissListener(@Nullable OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public void setOnTimeSetListener(@Nullable OnTimeSetListener onTimeSetListener) {
        this.mOnTimeSetListener = onTimeSetListener;
    }

    void setOnNeutralButtonActionListener(@Nullable OnClickListener onClickListener) {
        mOnNeutralButtonActionListener = onClickListener;
    }
}
