package com.facebook.react.modules.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import javax.annotation.Nullable;

public class AlertFragment extends DialogFragment implements OnClickListener {
    static final String ARG_BUTTON_NEGATIVE = "button_negative";
    static final String ARG_BUTTON_NEUTRAL = "button_neutral";
    static final String ARG_BUTTON_POSITIVE = "button_positive";
    static final String ARG_ITEMS = "items";
    static final String ARG_MESSAGE = "message";
    static final String ARG_TITLE = "title";
    @Nullable
    private final AlertFragmentListener mListener;

    public AlertFragment() {
        this.mListener = null;
    }

    @SuppressLint({"ValidFragment"})
    public AlertFragment(@Nullable AlertFragmentListener alertFragmentListener, Bundle bundle) {
        this.mListener = alertFragmentListener;
        setArguments(bundle);
    }

    public static Dialog createDialog(Context context, Bundle bundle, OnClickListener onClickListener) {
        Builder title = new Builder(context).setTitle(bundle.getString(ARG_TITLE));
        String str = ARG_BUTTON_POSITIVE;
        if (bundle.containsKey(str)) {
            title.setPositiveButton(bundle.getString(str), onClickListener);
        }
        str = ARG_BUTTON_NEGATIVE;
        if (bundle.containsKey(str)) {
            title.setNegativeButton(bundle.getString(str), onClickListener);
        }
        str = ARG_BUTTON_NEUTRAL;
        if (bundle.containsKey(str)) {
            title.setNeutralButton(bundle.getString(str), onClickListener);
        }
        str = ARG_MESSAGE;
        if (bundle.containsKey(str)) {
            title.setMessage(bundle.getString(str));
        }
        str = ARG_ITEMS;
        if (bundle.containsKey(str)) {
            title.setItems(bundle.getCharSequenceArray(str), onClickListener);
        }
        return title.create();
    }

    public Dialog onCreateDialog(Bundle bundle) {
        return createDialog(getLifecycleActivity(), getArguments(), this);
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        AlertFragmentListener alertFragmentListener = this.mListener;
        if (alertFragmentListener != null) {
            alertFragmentListener.onClick(dialogInterface, i);
        }
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        AlertFragmentListener alertFragmentListener = this.mListener;
        if (alertFragmentListener != null) {
            alertFragmentListener.onDismiss(dialogInterface);
        }
    }
}
