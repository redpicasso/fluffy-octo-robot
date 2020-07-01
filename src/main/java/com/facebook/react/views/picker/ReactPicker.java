package com.facebook.react.views.picker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SpinnerAdapter;
import androidx.appcompat.widget.AppCompatSpinner;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.google.common.primitives.Ints;
import javax.annotation.Nullable;

public class ReactPicker extends AppCompatSpinner {
    private final OnItemSelectedListener mItemSelectedListener = new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
            if (ReactPicker.this.mOnSelectListener != null) {
                ReactPicker.this.mOnSelectListener.onItemSelected(i);
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            if (ReactPicker.this.mOnSelectListener != null) {
                ReactPicker.this.mOnSelectListener.onItemSelected(-1);
            }
        }
    };
    private int mMode = 0;
    @Nullable
    private OnSelectListener mOnSelectListener;
    @Nullable
    private Integer mPrimaryColor;
    @Nullable
    private SpinnerAdapter mStagedAdapter;
    @Nullable
    private Integer mStagedSelection;
    private final Runnable measureAndLayout = new Runnable() {
        public void run() {
            ReactPicker reactPicker = ReactPicker.this;
            reactPicker.measure(MeasureSpec.makeMeasureSpec(reactPicker.getWidth(), Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(ReactPicker.this.getHeight(), Ints.MAX_POWER_OF_TWO));
            reactPicker = ReactPicker.this;
            reactPicker.layout(reactPicker.getLeft(), ReactPicker.this.getTop(), ReactPicker.this.getRight(), ReactPicker.this.getBottom());
        }
    };

    public interface OnSelectListener {
        void onItemSelected(int i);
    }

    public ReactPicker(Context context) {
        super(context);
    }

    public ReactPicker(Context context, int i) {
        super(context, i);
        this.mMode = i;
    }

    public ReactPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ReactPicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public ReactPicker(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMode = i2;
    }

    public void requestLayout() {
        super.requestLayout();
        post(this.measureAndLayout);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (getOnItemSelectedListener() == null) {
            setOnItemSelectedListener(this.mItemSelectedListener);
        }
    }

    public void setOnSelectListener(@Nullable OnSelectListener onSelectListener) {
        this.mOnSelectListener = onSelectListener;
    }

    @Nullable
    public OnSelectListener getOnSelectListener() {
        return this.mOnSelectListener;
    }

    void setStagedAdapter(SpinnerAdapter spinnerAdapter) {
        this.mStagedAdapter = spinnerAdapter;
    }

    void setStagedSelection(int i) {
        this.mStagedSelection = Integer.valueOf(i);
    }

    void commitStagedData() {
        setOnItemSelectedListener(null);
        int selectedItemPosition = getSelectedItemPosition();
        SpinnerAdapter spinnerAdapter = this.mStagedAdapter;
        if (!(spinnerAdapter == null || spinnerAdapter == getAdapter())) {
            setAdapter(this.mStagedAdapter);
            setSelection(selectedItemPosition, false);
            this.mStagedAdapter = null;
        }
        Integer num = this.mStagedSelection;
        if (!(num == null || num.intValue() == selectedItemPosition)) {
            setSelection(this.mStagedSelection.intValue(), false);
            this.mStagedSelection = null;
        }
        setOnItemSelectedListener(this.mItemSelectedListener);
    }

    @Nullable
    public Integer getPrimaryColor() {
        return this.mPrimaryColor;
    }

    public void setPrimaryColor(@Nullable Integer num) {
        this.mPrimaryColor = num;
    }

    @VisibleForTesting
    public int getMode() {
        return this.mMode;
    }
}
