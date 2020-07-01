package com.reactcommunity.rndatetimepicker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

@SuppressLint({"ValidFragment"})
public class RNDatePickerDialogFragment extends DialogFragment {
    @Nullable
    private static OnClickListener mOnNeutralButtonActionListener;
    private DatePickerDialog instance;
    @Nullable
    private OnDateSetListener mOnDateSetListener;
    @Nullable
    private OnDismissListener mOnDismissListener;

    /* renamed from: com.reactcommunity.rndatetimepicker.RNDatePickerDialogFragment$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$reactcommunity$rndatetimepicker$RNDatePickerDisplay = new int[RNDatePickerDisplay.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:5:0x001f, code:
            return;
     */
        static {
            /*
            r0 = com.reactcommunity.rndatetimepicker.RNDatePickerDisplay.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$reactcommunity$rndatetimepicker$RNDatePickerDisplay = r0;
            r0 = $SwitchMap$com$reactcommunity$rndatetimepicker$RNDatePickerDisplay;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.reactcommunity.rndatetimepicker.RNDatePickerDisplay.CALENDAR;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$reactcommunity$rndatetimepicker$RNDatePickerDisplay;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.reactcommunity.rndatetimepicker.RNDatePickerDisplay.SPINNER;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.reactcommunity.rndatetimepicker.RNDatePickerDialogFragment.1.<clinit>():void");
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        this.instance = createDialog(getArguments(), getLifecycleActivity(), this.mOnDateSetListener);
        return this.instance;
    }

    public void update(Bundle bundle) {
        RNDate rNDate = new RNDate(bundle);
        this.instance.updateDate(rNDate.year(), rNDate.month(), rNDate.day());
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0036  */
    @androidx.annotation.NonNull
    static android.app.DatePickerDialog getDialog(android.os.Bundle r11, android.content.Context r12, @androidx.annotation.Nullable android.app.DatePickerDialog.OnDateSetListener r13) {
        /*
        r0 = new com.reactcommunity.rndatetimepicker.RNDate;
        r0.<init>(r11);
        r5 = r0.year();
        r6 = r0.month();
        r0 = r0.day();
        r1 = com.reactcommunity.rndatetimepicker.RNDatePickerDisplay.DEFAULT;
        if (r11 == 0) goto L_0x002d;
    L_0x0015:
        r2 = 0;
        r3 = "display";
        r2 = r11.getString(r3, r2);
        if (r2 == 0) goto L_0x002d;
    L_0x001e:
        r11 = r11.getString(r3);
        r1 = java.util.Locale.US;
        r11 = r11.toUpperCase(r1);
        r11 = com.reactcommunity.rndatetimepicker.RNDatePickerDisplay.valueOf(r11);
        goto L_0x002e;
    L_0x002d:
        r11 = r1;
    L_0x002e:
        r1 = android.os.Build.VERSION.SDK_INT;
        r2 = 21;
        r8 = 2;
        r9 = 1;
        if (r1 < r2) goto L_0x0071;
    L_0x0036:
        r1 = com.reactcommunity.rndatetimepicker.RNDatePickerDialogFragment.AnonymousClass1.$SwitchMap$com$reactcommunity$rndatetimepicker$RNDatePickerDisplay;
        r2 = r11.ordinal();
        r1 = r1[r2];
        if (r1 == r9) goto L_0x004f;
    L_0x0040:
        if (r1 == r8) goto L_0x004f;
    L_0x0042:
        r8 = new com.reactcommunity.rndatetimepicker.RNDismissableDatePickerDialog;
        r1 = r8;
        r2 = r12;
        r3 = r13;
        r4 = r5;
        r5 = r6;
        r6 = r0;
        r7 = r11;
        r1.<init>(r2, r3, r4, r5, r6, r7);
        return r8;
    L_0x004f:
        r1 = com.reactcommunity.rndatetimepicker.RNDatePickerDisplay.CALENDAR;
        if (r11 != r1) goto L_0x0056;
    L_0x0053:
        r1 = "CalendarDatePickerDialog";
        goto L_0x0058;
    L_0x0056:
        r1 = "SpinnerDatePickerDialog";
    L_0x0058:
        r9 = new com.reactcommunity.rndatetimepicker.RNDismissableDatePickerDialog;
        r2 = r12.getResources();
        r3 = r12.getPackageName();
        r4 = "style";
        r3 = r2.getIdentifier(r1, r4, r3);
        r1 = r9;
        r2 = r12;
        r4 = r13;
        r7 = r0;
        r8 = r11;
        r1.<init>(r2, r3, r4, r5, r6, r7, r8);
        return r9;
    L_0x0071:
        r10 = new com.reactcommunity.rndatetimepicker.RNDismissableDatePickerDialog;
        r1 = r10;
        r2 = r12;
        r3 = r13;
        r4 = r5;
        r5 = r6;
        r6 = r0;
        r7 = r11;
        r1.<init>(r2, r3, r4, r5, r6, r7);
        r12 = com.reactcommunity.rndatetimepicker.RNDatePickerDialogFragment.AnonymousClass1.$SwitchMap$com$reactcommunity$rndatetimepicker$RNDatePickerDisplay;
        r11 = r11.ordinal();
        r11 = r12[r11];
        r12 = 0;
        if (r11 == r9) goto L_0x0093;
    L_0x0088:
        if (r11 == r8) goto L_0x008b;
    L_0x008a:
        goto L_0x00a1;
    L_0x008b:
        r11 = r10.getDatePicker();
        r11.setCalendarViewShown(r12);
        goto L_0x00a1;
    L_0x0093:
        r11 = r10.getDatePicker();
        r11.setCalendarViewShown(r9);
        r11 = r10.getDatePicker();
        r11.setSpinnersShown(r12);
    L_0x00a1:
        return r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.reactcommunity.rndatetimepicker.RNDatePickerDialogFragment.getDialog(android.os.Bundle, android.content.Context, android.app.DatePickerDialog$OnDateSetListener):android.app.DatePickerDialog");
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0058  */
    static android.app.DatePickerDialog createDialog(android.os.Bundle r7, android.content.Context r8, @androidx.annotation.Nullable android.app.DatePickerDialog.OnDateSetListener r9) {
        /*
        r0 = java.util.Calendar.getInstance();
        r8 = getDialog(r7, r8, r9);
        if (r7 == 0) goto L_0x001c;
    L_0x000a:
        r9 = "neutralButtonLabel";
        r1 = r7.containsKey(r9);
        if (r1 == 0) goto L_0x001c;
    L_0x0012:
        r1 = -3;
        r9 = r7.getString(r9);
        r2 = mOnNeutralButtonActionListener;
        r8.setButton(r1, r9, r2);
    L_0x001c:
        r9 = r8.getDatePicker();
        r1 = 14;
        r2 = 13;
        r3 = 12;
        r4 = 11;
        if (r7 == 0) goto L_0x004e;
    L_0x002a:
        r5 = "minimumDate";
        r6 = r7.containsKey(r5);
        if (r6 == 0) goto L_0x004e;
    L_0x0032:
        r5 = r7.getLong(r5);
        r0.setTimeInMillis(r5);
        r5 = 0;
        r0.set(r4, r5);
        r0.set(r3, r5);
        r0.set(r2, r5);
        r0.set(r1, r5);
        r5 = r0.getTimeInMillis();
        r9.setMinDate(r5);
        goto L_0x0056;
    L_0x004e:
        r5 = -2208988800001; // 0xfffffdfdae01dbff float:-2.95266E-11 double:NaN;
        r9.setMinDate(r5);
    L_0x0056:
        if (r7 == 0) goto L_0x0080;
    L_0x0058:
        r5 = "maximumDate";
        r6 = r7.containsKey(r5);
        if (r6 == 0) goto L_0x0080;
    L_0x0060:
        r5 = r7.getLong(r5);
        r0.setTimeInMillis(r5);
        r7 = 23;
        r0.set(r4, r7);
        r7 = 59;
        r0.set(r3, r7);
        r0.set(r2, r7);
        r7 = 999; // 0x3e7 float:1.4E-42 double:4.936E-321;
        r0.set(r1, r7);
        r0 = r0.getTimeInMillis();
        r9.setMaxDate(r0);
    L_0x0080:
        return r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.reactcommunity.rndatetimepicker.RNDatePickerDialogFragment.createDialog(android.os.Bundle, android.content.Context, android.app.DatePickerDialog$OnDateSetListener):android.app.DatePickerDialog");
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialogInterface);
        }
    }

    void setOnDateSetListener(@Nullable OnDateSetListener onDateSetListener) {
        this.mOnDateSetListener = onDateSetListener;
    }

    void setOnDismissListener(@Nullable OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    void setOnNeutralButtonActionListener(@Nullable OnClickListener onClickListener) {
        mOnNeutralButtonActionListener = onClickListener;
    }
}
