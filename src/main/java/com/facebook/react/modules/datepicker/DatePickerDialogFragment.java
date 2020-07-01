package com.facebook.react.modules.datepicker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import javax.annotation.Nullable;

@SuppressLint({"ValidFragment"})
public class DatePickerDialogFragment extends DialogFragment {
    private static final long DEFAULT_MIN_DATE = -2208988800001L;
    @Nullable
    private OnDateSetListener mOnDateSetListener;
    @Nullable
    private OnDismissListener mOnDismissListener;

    /* renamed from: com.facebook.react.modules.datepicker.DatePickerDialogFragment$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$modules$datepicker$DatePickerMode = new int[DatePickerMode.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$facebook$react$modules$datepicker$DatePickerMode[com.facebook.react.modules.datepicker.DatePickerMode.DEFAULT.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.facebook.react.modules.datepicker.DatePickerMode.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$facebook$react$modules$datepicker$DatePickerMode = r0;
            r0 = $SwitchMap$com$facebook$react$modules$datepicker$DatePickerMode;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.facebook.react.modules.datepicker.DatePickerMode.CALENDAR;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$facebook$react$modules$datepicker$DatePickerMode;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.facebook.react.modules.datepicker.DatePickerMode.SPINNER;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$facebook$react$modules$datepicker$DatePickerMode;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.facebook.react.modules.datepicker.DatePickerMode.DEFAULT;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.modules.datepicker.DatePickerDialogFragment.1.<clinit>():void");
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        return createDialog(getArguments(), getLifecycleActivity(), this.mOnDateSetListener);
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0102  */
    static android.app.Dialog createDialog(android.os.Bundle r13, android.content.Context r14, @javax.annotation.Nullable android.app.DatePickerDialog.OnDateSetListener r15) {
        /*
        r0 = java.util.Calendar.getInstance();
        if (r13 == 0) goto L_0x0015;
    L_0x0006:
        r1 = "date";
        r2 = r13.containsKey(r1);
        if (r2 == 0) goto L_0x0015;
    L_0x000e:
        r1 = r13.getLong(r1);
        r0.setTimeInMillis(r1);
    L_0x0015:
        r1 = 1;
        r6 = r0.get(r1);
        r8 = 2;
        r7 = r0.get(r8);
        r2 = 5;
        r9 = r0.get(r2);
        r2 = com.facebook.react.modules.datepicker.DatePickerMode.DEFAULT;
        r3 = 0;
        if (r13 == 0) goto L_0x003f;
    L_0x0029:
        r4 = "mode";
        r5 = r13.getString(r4, r3);
        if (r5 == 0) goto L_0x003f;
    L_0x0031:
        r2 = r13.getString(r4);
        r4 = java.util.Locale.US;
        r2 = r2.toUpperCase(r4);
        r2 = com.facebook.react.modules.datepicker.DatePickerMode.valueOf(r2);
    L_0x003f:
        r10 = r2;
        r2 = android.os.Build.VERSION.SDK_INT;
        r4 = 21;
        r11 = 0;
        if (r2 < r4) goto L_0x0098;
    L_0x0047:
        r2 = com.facebook.react.modules.datepicker.DatePickerDialogFragment.AnonymousClass1.$SwitchMap$com$facebook$react$modules$datepicker$DatePickerMode;
        r4 = r10.ordinal();
        r2 = r2[r4];
        r4 = "style";
        if (r2 == r1) goto L_0x0080;
    L_0x0053:
        if (r2 == r8) goto L_0x0068;
    L_0x0055:
        r1 = 3;
        if (r2 == r1) goto L_0x005b;
    L_0x0058:
        r1 = r3;
        goto L_0x00c7;
    L_0x005b:
        r1 = new com.facebook.react.modules.datepicker.DismissableDatePickerDialog;
        r2 = r1;
        r3 = r14;
        r4 = r15;
        r5 = r6;
        r6 = r7;
        r7 = r9;
        r2.<init>(r3, r4, r5, r6, r7);
        goto L_0x00c7;
    L_0x0068:
        r1 = new com.facebook.react.modules.datepicker.DismissableDatePickerDialog;
        r2 = r14.getResources();
        r3 = r14.getPackageName();
        r5 = "SpinnerDatePickerDialog";
        r4 = r2.getIdentifier(r5, r4, r3);
        r2 = r1;
        r3 = r14;
        r5 = r15;
        r8 = r9;
        r2.<init>(r3, r4, r5, r6, r7, r8);
        goto L_0x00c7;
    L_0x0080:
        r1 = new com.facebook.react.modules.datepicker.DismissableDatePickerDialog;
        r2 = r14.getResources();
        r3 = r14.getPackageName();
        r5 = "CalendarDatePickerDialog";
        r4 = r2.getIdentifier(r5, r4, r3);
        r2 = r1;
        r3 = r14;
        r5 = r15;
        r8 = r9;
        r2.<init>(r3, r4, r5, r6, r7, r8);
        goto L_0x00c7;
    L_0x0098:
        r12 = new com.facebook.react.modules.datepicker.DismissableDatePickerDialog;
        r2 = r12;
        r3 = r14;
        r4 = r15;
        r5 = r6;
        r6 = r7;
        r7 = r9;
        r2.<init>(r3, r4, r5, r6, r7);
        r14 = com.facebook.react.modules.datepicker.DatePickerDialogFragment.AnonymousClass1.$SwitchMap$com$facebook$react$modules$datepicker$DatePickerMode;
        r15 = r10.ordinal();
        r14 = r14[r15];
        if (r14 == r1) goto L_0x00b8;
    L_0x00ad:
        if (r14 == r8) goto L_0x00b0;
    L_0x00af:
        goto L_0x00c6;
    L_0x00b0:
        r14 = r12.getDatePicker();
        r14.setCalendarViewShown(r11);
        goto L_0x00c6;
    L_0x00b8:
        r14 = r12.getDatePicker();
        r14.setCalendarViewShown(r1);
        r14 = r12.getDatePicker();
        r14.setSpinnersShown(r11);
    L_0x00c6:
        r1 = r12;
    L_0x00c7:
        r14 = r1.getDatePicker();
        r15 = 14;
        r2 = 13;
        r3 = 12;
        r4 = 11;
        if (r13 == 0) goto L_0x00f8;
    L_0x00d5:
        r5 = "minDate";
        r6 = r13.containsKey(r5);
        if (r6 == 0) goto L_0x00f8;
    L_0x00dd:
        r5 = r13.getLong(r5);
        r0.setTimeInMillis(r5);
        r0.set(r4, r11);
        r0.set(r3, r11);
        r0.set(r2, r11);
        r0.set(r15, r11);
        r5 = r0.getTimeInMillis();
        r14.setMinDate(r5);
        goto L_0x0100;
    L_0x00f8:
        r5 = -2208988800001; // 0xfffffdfdae01dbff float:-2.95266E-11 double:NaN;
        r14.setMinDate(r5);
    L_0x0100:
        if (r13 == 0) goto L_0x012a;
    L_0x0102:
        r5 = "maxDate";
        r6 = r13.containsKey(r5);
        if (r6 == 0) goto L_0x012a;
    L_0x010a:
        r5 = r13.getLong(r5);
        r0.setTimeInMillis(r5);
        r13 = 23;
        r0.set(r4, r13);
        r13 = 59;
        r0.set(r3, r13);
        r0.set(r2, r13);
        r13 = 999; // 0x3e7 float:1.4E-42 double:4.936E-321;
        r0.set(r15, r13);
        r2 = r0.getTimeInMillis();
        r14.setMaxDate(r2);
    L_0x012a:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.modules.datepicker.DatePickerDialogFragment.createDialog(android.os.Bundle, android.content.Context, android.app.DatePickerDialog$OnDateSetListener):android.app.Dialog");
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
}
