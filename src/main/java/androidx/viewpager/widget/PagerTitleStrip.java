package androidx.viewpager.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.text.method.SingleLineTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.viewpager.widget.ViewPager.DecorView;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.google.common.primitives.Ints;
import java.lang.ref.WeakReference;
import java.util.Locale;

@DecorView
public class PagerTitleStrip extends ViewGroup {
    private static final int[] ATTRS = new int[]{16842804, 16842901, 16842904, 16842927};
    private static final float SIDE_ALPHA = 0.6f;
    private static final int[] TEXT_ATTRS = new int[]{16843660};
    private static final int TEXT_SPACING = 16;
    TextView mCurrText;
    private int mGravity;
    private int mLastKnownCurrentPage;
    float mLastKnownPositionOffset;
    TextView mNextText;
    private int mNonPrimaryAlpha;
    private final PageListener mPageListener;
    ViewPager mPager;
    TextView mPrevText;
    private int mScaledTextSpacing;
    int mTextColor;
    private boolean mUpdatingPositions;
    private boolean mUpdatingText;
    private WeakReference<PagerAdapter> mWatchingAdapter;

    private static class SingleLineAllCapsTransform extends SingleLineTransformationMethod {
        private Locale mLocale;

        SingleLineAllCapsTransform(Context context) {
            this.mLocale = context.getResources().getConfiguration().locale;
        }

        public CharSequence getTransformation(CharSequence charSequence, View view) {
            charSequence = super.getTransformation(charSequence, view);
            return charSequence != null ? charSequence.toString().toUpperCase(this.mLocale) : null;
        }
    }

    private class PageListener extends DataSetObserver implements OnPageChangeListener, OnAdapterChangeListener {
        private int mScrollState;

        PageListener() {
        }

        public void onPageScrolled(int i, float f, int i2) {
            if (f > 0.5f) {
                i++;
            }
            PagerTitleStrip.this.updateTextPositions(i, f, false);
        }

        public void onPageSelected(int i) {
            if (this.mScrollState == 0) {
                PagerTitleStrip pagerTitleStrip = PagerTitleStrip.this;
                pagerTitleStrip.updateText(pagerTitleStrip.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
                float f = 0.0f;
                if (PagerTitleStrip.this.mLastKnownPositionOffset >= 0.0f) {
                    f = PagerTitleStrip.this.mLastKnownPositionOffset;
                }
                pagerTitleStrip = PagerTitleStrip.this;
                pagerTitleStrip.updateTextPositions(pagerTitleStrip.mPager.getCurrentItem(), f, true);
            }
        }

        public void onPageScrollStateChanged(int i) {
            this.mScrollState = i;
        }

        public void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
            PagerTitleStrip.this.updateAdapter(pagerAdapter, pagerAdapter2);
        }

        public void onChanged() {
            PagerTitleStrip pagerTitleStrip = PagerTitleStrip.this;
            pagerTitleStrip.updateText(pagerTitleStrip.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
            float f = 0.0f;
            if (PagerTitleStrip.this.mLastKnownPositionOffset >= 0.0f) {
                f = PagerTitleStrip.this.mLastKnownPositionOffset;
            }
            pagerTitleStrip = PagerTitleStrip.this;
            pagerTitleStrip.updateTextPositions(pagerTitleStrip.mPager.getCurrentItem(), f, true);
        }
    }

    private static void setSingleLineAllCaps(TextView textView) {
        textView.setTransformationMethod(new SingleLineAllCapsTransform(textView.getContext()));
    }

    public PagerTitleStrip(@NonNull Context context) {
        this(context, null);
    }

    public PagerTitleStrip(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLastKnownCurrentPage = -1;
        this.mLastKnownPositionOffset = -1.0f;
        this.mPageListener = new PageListener();
        View textView = new TextView(context);
        this.mPrevText = textView;
        addView(textView);
        textView = new TextView(context);
        this.mCurrText = textView;
        addView(textView);
        textView = new TextView(context);
        this.mNextText = textView;
        addView(textView);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS);
        boolean z = false;
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            TextViewCompat.setTextAppearance(this.mPrevText, resourceId);
            TextViewCompat.setTextAppearance(this.mCurrText, resourceId);
            TextViewCompat.setTextAppearance(this.mNextText, resourceId);
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(1, 0);
        if (dimensionPixelSize != 0) {
            setTextSize(0, (float) dimensionPixelSize);
        }
        if (obtainStyledAttributes.hasValue(2)) {
            dimensionPixelSize = obtainStyledAttributes.getColor(2, 0);
            this.mPrevText.setTextColor(dimensionPixelSize);
            this.mCurrText.setTextColor(dimensionPixelSize);
            this.mNextText.setTextColor(dimensionPixelSize);
        }
        this.mGravity = obtainStyledAttributes.getInteger(3, 80);
        obtainStyledAttributes.recycle();
        this.mTextColor = this.mCurrText.getTextColors().getDefaultColor();
        setNonPrimaryAlpha(SIDE_ALPHA);
        this.mPrevText.setEllipsize(TruncateAt.END);
        this.mCurrText.setEllipsize(TruncateAt.END);
        this.mNextText.setEllipsize(TruncateAt.END);
        if (resourceId != 0) {
            obtainStyledAttributes = context.obtainStyledAttributes(resourceId, TEXT_ATTRS);
            z = obtainStyledAttributes.getBoolean(0, false);
            obtainStyledAttributes.recycle();
        }
        if (z) {
            setSingleLineAllCaps(this.mPrevText);
            setSingleLineAllCaps(this.mCurrText);
            setSingleLineAllCaps(this.mNextText);
        } else {
            this.mPrevText.setSingleLine();
            this.mCurrText.setSingleLine();
            this.mNextText.setSingleLine();
        }
        this.mScaledTextSpacing = (int) (context.getResources().getDisplayMetrics().density * 16.0f);
    }

    public void setTextSpacing(int i) {
        this.mScaledTextSpacing = i;
        requestLayout();
    }

    public int getTextSpacing() {
        return this.mScaledTextSpacing;
    }

    public void setNonPrimaryAlpha(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        this.mNonPrimaryAlpha = ((int) (f * 255.0f)) & 255;
        int i = (this.mNonPrimaryAlpha << 24) | (this.mTextColor & ViewCompat.MEASURED_SIZE_MASK);
        this.mPrevText.setTextColor(i);
        this.mNextText.setTextColor(i);
    }

    public void setTextColor(@ColorInt int i) {
        this.mTextColor = i;
        this.mCurrText.setTextColor(i);
        i = (this.mNonPrimaryAlpha << 24) | (this.mTextColor & ViewCompat.MEASURED_SIZE_MASK);
        this.mPrevText.setTextColor(i);
        this.mNextText.setTextColor(i);
    }

    public void setTextSize(int i, float f) {
        this.mPrevText.setTextSize(i, f);
        this.mCurrText.setTextSize(i, f);
        this.mNextText.setTextSize(i, f);
    }

    public void setGravity(int i) {
        this.mGravity = i;
        requestLayout();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (parent instanceof ViewPager) {
            ViewPager viewPager = (ViewPager) parent;
            PagerAdapter adapter = viewPager.getAdapter();
            viewPager.setInternalPageChangeListener(this.mPageListener);
            viewPager.addOnAdapterChangeListener(this.mPageListener);
            this.mPager = viewPager;
            WeakReference weakReference = this.mWatchingAdapter;
            updateAdapter(weakReference != null ? (PagerAdapter) weakReference.get() : null, adapter);
            return;
        }
        throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ViewPager viewPager = this.mPager;
        if (viewPager != null) {
            updateAdapter(viewPager.getAdapter(), null);
            this.mPager.setInternalPageChangeListener(null);
            this.mPager.removeOnAdapterChangeListener(this.mPageListener);
            this.mPager = null;
        }
    }

    void updateText(int i, PagerAdapter pagerAdapter) {
        int count = pagerAdapter != null ? pagerAdapter.getCount() : 0;
        this.mUpdatingText = true;
        CharSequence charSequence = null;
        CharSequence pageTitle = (i < 1 || pagerAdapter == null) ? null : pagerAdapter.getPageTitle(i - 1);
        this.mPrevText.setText(pageTitle);
        TextView textView = this.mCurrText;
        CharSequence pageTitle2 = (pagerAdapter == null || i >= count) ? null : pagerAdapter.getPageTitle(i);
        textView.setText(pageTitle2);
        int i2 = i + 1;
        if (i2 < count && pagerAdapter != null) {
            charSequence = pagerAdapter.getPageTitle(i2);
        }
        this.mNextText.setText(charSequence);
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(0, (int) (((float) ((getWidth() - getPaddingLeft()) - getPaddingRight())) * 0.8f)), Integer.MIN_VALUE);
        count = MeasureSpec.makeMeasureSpec(Math.max(0, (getHeight() - getPaddingTop()) - getPaddingBottom()), Integer.MIN_VALUE);
        this.mPrevText.measure(makeMeasureSpec, count);
        this.mCurrText.measure(makeMeasureSpec, count);
        this.mNextText.measure(makeMeasureSpec, count);
        this.mLastKnownCurrentPage = i;
        if (!this.mUpdatingPositions) {
            updateTextPositions(i, this.mLastKnownPositionOffset, false);
        }
        this.mUpdatingText = false;
    }

    public void requestLayout() {
        if (!this.mUpdatingText) {
            super.requestLayout();
        }
    }

    void updateAdapter(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
        if (pagerAdapter != null) {
            pagerAdapter.unregisterDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = null;
        }
        if (pagerAdapter2 != null) {
            pagerAdapter2.registerDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = new WeakReference(pagerAdapter2);
        }
        ViewPager viewPager = this.mPager;
        if (viewPager != null) {
            this.mLastKnownCurrentPage = -1;
            this.mLastKnownPositionOffset = -1.0f;
            updateText(viewPager.getCurrentItem(), pagerAdapter2);
            requestLayout();
        }
    }

    void updateTextPositions(int i, float f, boolean z) {
        TextView textView;
        TextView textView2;
        int i2 = i;
        float f2 = f;
        if (i2 != this.mLastKnownCurrentPage) {
            updateText(i2, this.mPager.getAdapter());
        } else if (!z && f2 == this.mLastKnownPositionOffset) {
            return;
        }
        this.mUpdatingPositions = true;
        i2 = this.mPrevText.getMeasuredWidth();
        int measuredWidth = this.mCurrText.getMeasuredWidth();
        int measuredWidth2 = this.mNextText.getMeasuredWidth();
        int i3 = measuredWidth / 2;
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i4 = paddingRight + i3;
        int i5 = (width - (paddingLeft + i3)) - i4;
        float f3 = 0.5f + f2;
        if (f3 > 1.0f) {
            f3 -= 1.0f;
        }
        i4 = ((width - i4) - ((int) (((float) i5) * f3))) - i3;
        measuredWidth += i4;
        i3 = this.mPrevText.getBaseline();
        i5 = this.mCurrText.getBaseline();
        int baseline = this.mNextText.getBaseline();
        int max = Math.max(Math.max(i3, i5), baseline);
        i3 = max - i3;
        i5 = max - i5;
        max -= baseline;
        i = measuredWidth2;
        measuredWidth2 = this.mNextText.getMeasuredHeight() + max;
        int max2 = Math.max(Math.max(this.mPrevText.getMeasuredHeight() + i3, this.mCurrText.getMeasuredHeight() + i5), measuredWidth2);
        measuredWidth2 = this.mGravity & 112;
        if (measuredWidth2 == 16) {
            height = (((height - paddingTop) - paddingBottom) - max2) / 2;
        } else if (measuredWidth2 != 80) {
            i3 += paddingTop;
            i5 += paddingTop;
            paddingTop += max;
            textView = this.mCurrText;
            textView.layout(i4, i5, measuredWidth, textView.getMeasuredHeight() + i5);
            max2 = Math.min(paddingLeft, (i4 - this.mScaledTextSpacing) - i2);
            textView2 = this.mPrevText;
            textView2.layout(max2, i3, i2 + max2, textView2.getMeasuredHeight() + i3);
            i2 = Math.max((width - paddingRight) - i, measuredWidth + this.mScaledTextSpacing);
            textView = this.mNextText;
            textView.layout(i2, paddingTop, i2 + i, textView.getMeasuredHeight() + paddingTop);
            this.mLastKnownPositionOffset = f;
            this.mUpdatingPositions = false;
        } else {
            height = (height - paddingBottom) - max2;
        }
        i3 += height;
        i5 += height;
        paddingTop = height + max;
        textView = this.mCurrText;
        textView.layout(i4, i5, measuredWidth, textView.getMeasuredHeight() + i5);
        max2 = Math.min(paddingLeft, (i4 - this.mScaledTextSpacing) - i2);
        textView2 = this.mPrevText;
        textView2.layout(max2, i3, i2 + max2, textView2.getMeasuredHeight() + i3);
        i2 = Math.max((width - paddingRight) - i, measuredWidth + this.mScaledTextSpacing);
        textView = this.mNextText;
        textView.layout(i2, paddingTop, i2 + i, textView.getMeasuredHeight() + paddingTop);
        this.mLastKnownPositionOffset = f;
        this.mUpdatingPositions = false;
    }

    protected void onMeasure(int i, int i2) {
        if (MeasureSpec.getMode(i) == Ints.MAX_POWER_OF_TWO) {
            int paddingTop = getPaddingTop() + getPaddingBottom();
            int childMeasureSpec = getChildMeasureSpec(i2, paddingTop, -2);
            int size = MeasureSpec.getSize(i);
            i = getChildMeasureSpec(i, (int) (((float) size) * 0.2f), -2);
            this.mPrevText.measure(i, childMeasureSpec);
            this.mCurrText.measure(i, childMeasureSpec);
            this.mNextText.measure(i, childMeasureSpec);
            if (MeasureSpec.getMode(i2) == Ints.MAX_POWER_OF_TWO) {
                i = MeasureSpec.getSize(i2);
            } else {
                i = Math.max(getMinHeight(), this.mCurrText.getMeasuredHeight() + paddingTop);
            }
            setMeasuredDimension(size, View.resolveSizeAndState(i, i2, this.mCurrText.getMeasuredState() << 16));
            return;
        }
        throw new IllegalStateException("Must measure with an exact width");
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mPager != null) {
            float f = this.mLastKnownPositionOffset;
            if (f < 0.0f) {
                f = 0.0f;
            }
            updateTextPositions(this.mLastKnownCurrentPage, f, true);
        }
    }

    int getMinHeight() {
        Drawable background = getBackground();
        return background != null ? background.getIntrinsicHeight() : 0;
    }
}
