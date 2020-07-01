package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.appcompat.R;
import androidx.core.content.res.ResourcesCompat.FontCallback;
import androidx.core.widget.AutoSizeableTextView;
import java.lang.ref.WeakReference;

class AppCompatTextHelper {
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int TEXT_FONT_WEIGHT_UNSPECIFIED = -1;
    private boolean mAsyncFontPending;
    @NonNull
    private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableEndTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableStartTint;
    private TintInfo mDrawableTint;
    private TintInfo mDrawableTopTint;
    private Typeface mFontTypeface;
    private int mFontWeight = -1;
    private int mStyle = 0;
    private final TextView mView;

    private static class ApplyTextViewCallback extends FontCallback {
        private final int mFontWeight;
        private final WeakReference<AppCompatTextHelper> mParent;
        private final int mStyle;

        private class TypefaceApplyCallback implements Runnable {
            private final WeakReference<AppCompatTextHelper> mParent;
            private final Typeface mTypeface;

            TypefaceApplyCallback(@NonNull WeakReference<AppCompatTextHelper> weakReference, @NonNull Typeface typeface) {
                this.mParent = weakReference;
                this.mTypeface = typeface;
            }

            public void run() {
                AppCompatTextHelper appCompatTextHelper = (AppCompatTextHelper) this.mParent.get();
                if (appCompatTextHelper != null) {
                    appCompatTextHelper.setTypefaceByCallback(this.mTypeface);
                }
            }
        }

        public void onFontRetrievalFailed(int i) {
        }

        ApplyTextViewCallback(@NonNull AppCompatTextHelper appCompatTextHelper, int i, int i2) {
            this.mParent = new WeakReference(appCompatTextHelper);
            this.mFontWeight = i;
            this.mStyle = i2;
        }

        public void onFontRetrieved(@NonNull Typeface typeface) {
            AppCompatTextHelper appCompatTextHelper = (AppCompatTextHelper) this.mParent.get();
            if (appCompatTextHelper != null) {
                if (VERSION.SDK_INT >= 28) {
                    int i = this.mFontWeight;
                    if (i != -1) {
                        typeface = Typeface.create(typeface, i, (this.mStyle & 2) != 0);
                    }
                }
                appCompatTextHelper.runOnUiThread(new TypefaceApplyCallback(this.mParent, typeface));
            }
        }
    }

    AppCompatTextHelper(TextView textView) {
        this.mView = textView;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(this.mView);
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x0117  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0110  */
    @android.annotation.SuppressLint({"NewApi"})
    void loadFromAttributes(android.util.AttributeSet r19, int r20) {
        /*
        r18 = this;
        r7 = r18;
        r0 = r19;
        r1 = r20;
        r2 = r7.mView;
        r2 = r2.getContext();
        r3 = androidx.appcompat.widget.AppCompatDrawableManager.get();
        r4 = androidx.appcompat.R.styleable.AppCompatTextHelper;
        r5 = 0;
        r4 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes(r2, r0, r4, r1, r5);
        r6 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_textAppearance;
        r8 = -1;
        r6 = r4.getResourceId(r6, r8);
        r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableLeft;
        r9 = r4.hasValue(r9);
        if (r9 == 0) goto L_0x0032;
    L_0x0026:
        r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableLeft;
        r9 = r4.getResourceId(r9, r5);
        r9 = createTintInfo(r2, r3, r9);
        r7.mDrawableLeftTint = r9;
    L_0x0032:
        r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableTop;
        r9 = r4.hasValue(r9);
        if (r9 == 0) goto L_0x0046;
    L_0x003a:
        r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableTop;
        r9 = r4.getResourceId(r9, r5);
        r9 = createTintInfo(r2, r3, r9);
        r7.mDrawableTopTint = r9;
    L_0x0046:
        r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableRight;
        r9 = r4.hasValue(r9);
        if (r9 == 0) goto L_0x005a;
    L_0x004e:
        r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableRight;
        r9 = r4.getResourceId(r9, r5);
        r9 = createTintInfo(r2, r3, r9);
        r7.mDrawableRightTint = r9;
    L_0x005a:
        r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableBottom;
        r9 = r4.hasValue(r9);
        if (r9 == 0) goto L_0x006e;
    L_0x0062:
        r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableBottom;
        r9 = r4.getResourceId(r9, r5);
        r9 = createTintInfo(r2, r3, r9);
        r7.mDrawableBottomTint = r9;
    L_0x006e:
        r9 = android.os.Build.VERSION.SDK_INT;
        r10 = 17;
        if (r9 < r10) goto L_0x009c;
    L_0x0074:
        r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableStart;
        r9 = r4.hasValue(r9);
        if (r9 == 0) goto L_0x0088;
    L_0x007c:
        r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableStart;
        r9 = r4.getResourceId(r9, r5);
        r9 = createTintInfo(r2, r3, r9);
        r7.mDrawableStartTint = r9;
    L_0x0088:
        r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableEnd;
        r9 = r4.hasValue(r9);
        if (r9 == 0) goto L_0x009c;
    L_0x0090:
        r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableEnd;
        r9 = r4.getResourceId(r9, r5);
        r9 = createTintInfo(r2, r3, r9);
        r7.mDrawableEndTint = r9;
    L_0x009c:
        r4.recycle();
        r4 = r7.mView;
        r4 = r4.getTransformationMethod();
        r4 = r4 instanceof android.text.method.PasswordTransformationMethod;
        r9 = 26;
        r11 = 23;
        if (r6 == r8) goto L_0x0130;
    L_0x00ad:
        r13 = androidx.appcompat.R.styleable.TextAppearance;
        r6 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes(r2, r6, r13);
        if (r4 != 0) goto L_0x00c6;
    L_0x00b5:
        r13 = androidx.appcompat.R.styleable.TextAppearance_textAllCaps;
        r13 = r6.hasValue(r13);
        if (r13 == 0) goto L_0x00c6;
    L_0x00bd:
        r13 = androidx.appcompat.R.styleable.TextAppearance_textAllCaps;
        r13 = r6.getBoolean(r13, r5);
        r14 = r13;
        r13 = 1;
        goto L_0x00c8;
    L_0x00c6:
        r13 = 0;
        r14 = 0;
    L_0x00c8:
        r7.updateTypefaceAndStyle(r2, r6);
        r15 = android.os.Build.VERSION.SDK_INT;
        if (r15 >= r11) goto L_0x0105;
    L_0x00cf:
        r15 = androidx.appcompat.R.styleable.TextAppearance_android_textColor;
        r15 = r6.hasValue(r15);
        if (r15 == 0) goto L_0x00de;
    L_0x00d7:
        r15 = androidx.appcompat.R.styleable.TextAppearance_android_textColor;
        r15 = r6.getColorStateList(r15);
        goto L_0x00df;
    L_0x00de:
        r15 = 0;
    L_0x00df:
        r10 = androidx.appcompat.R.styleable.TextAppearance_android_textColorHint;
        r10 = r6.hasValue(r10);
        if (r10 == 0) goto L_0x00ee;
    L_0x00e7:
        r10 = androidx.appcompat.R.styleable.TextAppearance_android_textColorHint;
        r10 = r6.getColorStateList(r10);
        goto L_0x00ef;
    L_0x00ee:
        r10 = 0;
    L_0x00ef:
        r12 = androidx.appcompat.R.styleable.TextAppearance_android_textColorLink;
        r12 = r6.hasValue(r12);
        if (r12 == 0) goto L_0x0103;
    L_0x00f7:
        r12 = androidx.appcompat.R.styleable.TextAppearance_android_textColorLink;
        r12 = r6.getColorStateList(r12);
        r17 = r15;
        r15 = r12;
        r12 = r17;
        goto L_0x0108;
    L_0x0103:
        r12 = r15;
        goto L_0x0107;
    L_0x0105:
        r10 = 0;
        r12 = 0;
    L_0x0107:
        r15 = 0;
    L_0x0108:
        r8 = androidx.appcompat.R.styleable.TextAppearance_textLocale;
        r8 = r6.hasValue(r8);
        if (r8 == 0) goto L_0x0117;
    L_0x0110:
        r8 = androidx.appcompat.R.styleable.TextAppearance_textLocale;
        r8 = r6.getString(r8);
        goto L_0x0118;
    L_0x0117:
        r8 = 0;
    L_0x0118:
        r11 = android.os.Build.VERSION.SDK_INT;
        if (r11 < r9) goto L_0x012b;
    L_0x011c:
        r11 = androidx.appcompat.R.styleable.TextAppearance_fontVariationSettings;
        r11 = r6.hasValue(r11);
        if (r11 == 0) goto L_0x012b;
    L_0x0124:
        r11 = androidx.appcompat.R.styleable.TextAppearance_fontVariationSettings;
        r11 = r6.getString(r11);
        goto L_0x012c;
    L_0x012b:
        r11 = 0;
    L_0x012c:
        r6.recycle();
        goto L_0x0137;
    L_0x0130:
        r8 = 0;
        r10 = 0;
        r11 = 0;
        r12 = 0;
        r13 = 0;
        r14 = 0;
        r15 = 0;
    L_0x0137:
        r6 = androidx.appcompat.R.styleable.TextAppearance;
        r6 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes(r2, r0, r6, r1, r5);
        if (r4 != 0) goto L_0x014e;
    L_0x013f:
        r9 = androidx.appcompat.R.styleable.TextAppearance_textAllCaps;
        r9 = r6.hasValue(r9);
        if (r9 == 0) goto L_0x014e;
    L_0x0147:
        r9 = androidx.appcompat.R.styleable.TextAppearance_textAllCaps;
        r14 = r6.getBoolean(r9, r5);
        r13 = 1;
    L_0x014e:
        r9 = android.os.Build.VERSION.SDK_INT;
        r5 = 23;
        if (r9 >= r5) goto L_0x017e;
    L_0x0154:
        r5 = androidx.appcompat.R.styleable.TextAppearance_android_textColor;
        r5 = r6.hasValue(r5);
        if (r5 == 0) goto L_0x0162;
    L_0x015c:
        r5 = androidx.appcompat.R.styleable.TextAppearance_android_textColor;
        r12 = r6.getColorStateList(r5);
    L_0x0162:
        r5 = androidx.appcompat.R.styleable.TextAppearance_android_textColorHint;
        r5 = r6.hasValue(r5);
        if (r5 == 0) goto L_0x0170;
    L_0x016a:
        r5 = androidx.appcompat.R.styleable.TextAppearance_android_textColorHint;
        r10 = r6.getColorStateList(r5);
    L_0x0170:
        r5 = androidx.appcompat.R.styleable.TextAppearance_android_textColorLink;
        r5 = r6.hasValue(r5);
        if (r5 == 0) goto L_0x017e;
    L_0x0178:
        r5 = androidx.appcompat.R.styleable.TextAppearance_android_textColorLink;
        r15 = r6.getColorStateList(r5);
    L_0x017e:
        r5 = androidx.appcompat.R.styleable.TextAppearance_textLocale;
        r5 = r6.hasValue(r5);
        if (r5 == 0) goto L_0x018c;
    L_0x0186:
        r5 = androidx.appcompat.R.styleable.TextAppearance_textLocale;
        r8 = r6.getString(r5);
    L_0x018c:
        r5 = android.os.Build.VERSION.SDK_INT;
        r9 = 26;
        if (r5 < r9) goto L_0x01a0;
    L_0x0192:
        r5 = androidx.appcompat.R.styleable.TextAppearance_fontVariationSettings;
        r5 = r6.hasValue(r5);
        if (r5 == 0) goto L_0x01a0;
    L_0x019a:
        r5 = androidx.appcompat.R.styleable.TextAppearance_fontVariationSettings;
        r11 = r6.getString(r5);
    L_0x01a0:
        r5 = android.os.Build.VERSION.SDK_INT;
        r9 = 28;
        if (r5 < r9) goto L_0x01c1;
    L_0x01a6:
        r5 = androidx.appcompat.R.styleable.TextAppearance_android_textSize;
        r5 = r6.hasValue(r5);
        if (r5 == 0) goto L_0x01c1;
    L_0x01ae:
        r5 = androidx.appcompat.R.styleable.TextAppearance_android_textSize;
        r9 = -1;
        r5 = r6.getDimensionPixelSize(r5, r9);
        if (r5 != 0) goto L_0x01c1;
    L_0x01b7:
        r5 = r7.mView;
        r9 = 0;
        r16 = r3;
        r3 = 0;
        r5.setTextSize(r3, r9);
        goto L_0x01c3;
    L_0x01c1:
        r16 = r3;
    L_0x01c3:
        r7.updateTypefaceAndStyle(r2, r6);
        r6.recycle();
        if (r12 == 0) goto L_0x01d0;
    L_0x01cb:
        r3 = r7.mView;
        r3.setTextColor(r12);
    L_0x01d0:
        if (r10 == 0) goto L_0x01d7;
    L_0x01d2:
        r3 = r7.mView;
        r3.setHintTextColor(r10);
    L_0x01d7:
        if (r15 == 0) goto L_0x01de;
    L_0x01d9:
        r3 = r7.mView;
        r3.setLinkTextColor(r15);
    L_0x01de:
        if (r4 != 0) goto L_0x01e5;
    L_0x01e0:
        if (r13 == 0) goto L_0x01e5;
    L_0x01e2:
        r7.setAllCaps(r14);
    L_0x01e5:
        r3 = r7.mFontTypeface;
        if (r3 == 0) goto L_0x01fb;
    L_0x01e9:
        r4 = r7.mFontWeight;
        r5 = -1;
        if (r4 != r5) goto L_0x01f6;
    L_0x01ee:
        r4 = r7.mView;
        r5 = r7.mStyle;
        r4.setTypeface(r3, r5);
        goto L_0x01fb;
    L_0x01f6:
        r4 = r7.mView;
        r4.setTypeface(r3);
    L_0x01fb:
        if (r11 == 0) goto L_0x0202;
    L_0x01fd:
        r3 = r7.mView;
        r3.setFontVariationSettings(r11);
    L_0x0202:
        if (r8 == 0) goto L_0x022e;
    L_0x0204:
        r3 = android.os.Build.VERSION.SDK_INT;
        r4 = 24;
        if (r3 < r4) goto L_0x0214;
    L_0x020a:
        r3 = r7.mView;
        r4 = android.os.LocaleList.forLanguageTags(r8);
        r3.setTextLocales(r4);
        goto L_0x022e;
    L_0x0214:
        r3 = android.os.Build.VERSION.SDK_INT;
        r4 = 21;
        if (r3 < r4) goto L_0x022e;
    L_0x021a:
        r3 = 44;
        r3 = r8.indexOf(r3);
        r4 = 0;
        r3 = r8.substring(r4, r3);
        r4 = r7.mView;
        r3 = java.util.Locale.forLanguageTag(r3);
        r4.setTextLocale(r3);
    L_0x022e:
        r3 = r7.mAutoSizeTextHelper;
        r3.loadFromAttributes(r0, r1);
        r1 = androidx.core.widget.AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE;
        if (r1 == 0) goto L_0x0274;
    L_0x0237:
        r1 = r7.mAutoSizeTextHelper;
        r1 = r1.getAutoSizeTextType();
        if (r1 == 0) goto L_0x0274;
    L_0x023f:
        r1 = r7.mAutoSizeTextHelper;
        r1 = r1.getAutoSizeTextAvailableSizes();
        r3 = r1.length;
        if (r3 <= 0) goto L_0x0274;
    L_0x0248:
        r3 = r7.mView;
        r3 = r3.getAutoSizeStepGranularity();
        r3 = (float) r3;
        r4 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1));
        if (r3 == 0) goto L_0x026e;
    L_0x0255:
        r1 = r7.mView;
        r3 = r7.mAutoSizeTextHelper;
        r3 = r3.getAutoSizeMinTextSize();
        r4 = r7.mAutoSizeTextHelper;
        r4 = r4.getAutoSizeMaxTextSize();
        r5 = r7.mAutoSizeTextHelper;
        r5 = r5.getAutoSizeStepGranularity();
        r6 = 0;
        r1.setAutoSizeTextTypeUniformWithConfiguration(r3, r4, r5, r6);
        goto L_0x0274;
    L_0x026e:
        r6 = 0;
        r3 = r7.mView;
        r3.setAutoSizeTextTypeUniformWithPresetSizes(r1, r6);
    L_0x0274:
        r1 = androidx.appcompat.R.styleable.AppCompatTextView;
        r8 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes(r2, r0, r1);
        r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableLeftCompat;
        r1 = -1;
        r0 = r8.getResourceId(r0, r1);
        if (r0 == r1) goto L_0x028b;
    L_0x0283:
        r3 = r16;
        r0 = r3.getDrawable(r2, r0);
        r4 = r0;
        goto L_0x028e;
    L_0x028b:
        r3 = r16;
        r4 = 0;
    L_0x028e:
        r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableTopCompat;
        r0 = r8.getResourceId(r0, r1);
        if (r0 == r1) goto L_0x029c;
    L_0x0296:
        r0 = r3.getDrawable(r2, r0);
        r5 = r0;
        goto L_0x029d;
    L_0x029c:
        r5 = 0;
    L_0x029d:
        r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableRightCompat;
        r0 = r8.getResourceId(r0, r1);
        if (r0 == r1) goto L_0x02ab;
    L_0x02a5:
        r0 = r3.getDrawable(r2, r0);
        r6 = r0;
        goto L_0x02ac;
    L_0x02ab:
        r6 = 0;
    L_0x02ac:
        r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableBottomCompat;
        r0 = r8.getResourceId(r0, r1);
        if (r0 == r1) goto L_0x02ba;
    L_0x02b4:
        r0 = r3.getDrawable(r2, r0);
        r9 = r0;
        goto L_0x02bb;
    L_0x02ba:
        r9 = 0;
    L_0x02bb:
        r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableStartCompat;
        r0 = r8.getResourceId(r0, r1);
        if (r0 == r1) goto L_0x02c9;
    L_0x02c3:
        r0 = r3.getDrawable(r2, r0);
        r10 = r0;
        goto L_0x02ca;
    L_0x02c9:
        r10 = 0;
    L_0x02ca:
        r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableEndCompat;
        r0 = r8.getResourceId(r0, r1);
        if (r0 == r1) goto L_0x02d8;
    L_0x02d2:
        r0 = r3.getDrawable(r2, r0);
        r11 = r0;
        goto L_0x02d9;
    L_0x02d8:
        r11 = 0;
    L_0x02d9:
        r0 = r18;
        r1 = r4;
        r2 = r5;
        r3 = r6;
        r4 = r9;
        r5 = r10;
        r6 = r11;
        r0.setCompoundDrawables(r1, r2, r3, r4, r5, r6);
        r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableTint;
        r0 = r8.hasValue(r0);
        if (r0 == 0) goto L_0x02f7;
    L_0x02ec:
        r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableTint;
        r0 = r8.getColorStateList(r0);
        r1 = r7.mView;
        androidx.core.widget.TextViewCompat.setCompoundDrawableTintList(r1, r0);
    L_0x02f7:
        r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableTintMode;
        r0 = r8.hasValue(r0);
        if (r0 == 0) goto L_0x0311;
    L_0x02ff:
        r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableTintMode;
        r1 = -1;
        r0 = r8.getInt(r0, r1);
        r2 = 0;
        r0 = androidx.appcompat.widget.DrawableUtils.parseTintMode(r0, r2);
        r2 = r7.mView;
        androidx.core.widget.TextViewCompat.setCompoundDrawableTintMode(r2, r0);
        goto L_0x0312;
    L_0x0311:
        r1 = -1;
    L_0x0312:
        r0 = androidx.appcompat.R.styleable.AppCompatTextView_firstBaselineToTopHeight;
        r0 = r8.getDimensionPixelSize(r0, r1);
        r2 = androidx.appcompat.R.styleable.AppCompatTextView_lastBaselineToBottomHeight;
        r2 = r8.getDimensionPixelSize(r2, r1);
        r3 = androidx.appcompat.R.styleable.AppCompatTextView_lineHeight;
        r3 = r8.getDimensionPixelSize(r3, r1);
        r8.recycle();
        if (r0 == r1) goto L_0x032e;
    L_0x0329:
        r4 = r7.mView;
        androidx.core.widget.TextViewCompat.setFirstBaselineToTopHeight(r4, r0);
    L_0x032e:
        if (r2 == r1) goto L_0x0335;
    L_0x0330:
        r0 = r7.mView;
        androidx.core.widget.TextViewCompat.setLastBaselineToBottomHeight(r0, r2);
    L_0x0335:
        if (r3 == r1) goto L_0x033c;
    L_0x0337:
        r0 = r7.mView;
        androidx.core.widget.TextViewCompat.setLineHeight(r0, r3);
    L_0x033c:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AppCompatTextHelper.loadFromAttributes(android.util.AttributeSet, int):void");
    }

    @RestrictTo({Scope.LIBRARY})
    public void setTypefaceByCallback(@NonNull Typeface typeface) {
        if (this.mAsyncFontPending) {
            this.mView.setTypeface(typeface);
            this.mFontTypeface = typeface;
        }
    }

    @RestrictTo({Scope.LIBRARY})
    public void runOnUiThread(@NonNull Runnable runnable) {
        this.mView.post(runnable);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:androidx.appcompat.widget.AppCompatTextHelper.updateTypefaceAndStyle(android.content.Context, androidx.appcompat.widget.TintTypedArray):void, dom blocks: []
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:89)
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:51)
        	at java.lang.Iterable.forEach(Unknown Source)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:51)
        	at jadx.core.ProcessClass.process(ProcessClass.java:37)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:293)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:201)
        */
    private void updateTypefaceAndStyle(android.content.Context r9, androidx.appcompat.widget.TintTypedArray r10) {
        /*
        r8 = this;
        r0 = androidx.appcompat.R.styleable.TextAppearance_android_textStyle;
        r1 = r8.mStyle;
        r0 = r10.getInt(r0, r1);
        r8.mStyle = r0;
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 28;
        r2 = 2;
        r3 = -1;
        r4 = 0;
        if (r0 < r1) goto L_0x0025;
    L_0x0013:
        r0 = androidx.appcompat.R.styleable.TextAppearance_android_textFontWeight;
        r0 = r10.getInt(r0, r3);
        r8.mFontWeight = r0;
        r0 = r8.mFontWeight;
        if (r0 == r3) goto L_0x0025;
    L_0x001f:
        r0 = r8.mStyle;
        r0 = r0 & r2;
        r0 = r0 | r4;
        r8.mStyle = r0;
    L_0x0025:
        r0 = androidx.appcompat.R.styleable.TextAppearance_android_fontFamily;
        r0 = r10.hasValue(r0);
        r5 = 1;
        if (r0 != 0) goto L_0x005e;
    L_0x002e:
        r0 = androidx.appcompat.R.styleable.TextAppearance_fontFamily;
        r0 = r10.hasValue(r0);
        if (r0 == 0) goto L_0x0037;
    L_0x0036:
        goto L_0x005e;
    L_0x0037:
        r9 = androidx.appcompat.R.styleable.TextAppearance_android_typeface;
        r9 = r10.hasValue(r9);
        if (r9 == 0) goto L_0x005d;
    L_0x003f:
        r8.mAsyncFontPending = r4;
        r9 = androidx.appcompat.R.styleable.TextAppearance_android_typeface;
        r9 = r10.getInt(r9, r5);
        if (r9 == r5) goto L_0x0059;
    L_0x0049:
        if (r9 == r2) goto L_0x0054;
    L_0x004b:
        r10 = 3;
        if (r9 == r10) goto L_0x004f;
    L_0x004e:
        goto L_0x005d;
    L_0x004f:
        r9 = android.graphics.Typeface.MONOSPACE;
        r8.mFontTypeface = r9;
        goto L_0x005d;
    L_0x0054:
        r9 = android.graphics.Typeface.SERIF;
        r8.mFontTypeface = r9;
        goto L_0x005d;
    L_0x0059:
        r9 = android.graphics.Typeface.SANS_SERIF;
        r8.mFontTypeface = r9;
    L_0x005d:
        return;
    L_0x005e:
        r0 = 0;
        r8.mFontTypeface = r0;
        r0 = androidx.appcompat.R.styleable.TextAppearance_fontFamily;
        r0 = r10.hasValue(r0);
        if (r0 == 0) goto L_0x006c;
    L_0x0069:
        r0 = androidx.appcompat.R.styleable.TextAppearance_fontFamily;
        goto L_0x006e;
    L_0x006c:
        r0 = androidx.appcompat.R.styleable.TextAppearance_android_fontFamily;
    L_0x006e:
        r6 = r8.mFontWeight;
        r7 = r8.mStyle;
        r9 = r9.isRestricted();
        if (r9 != 0) goto L_0x00af;
    L_0x0078:
        r9 = new androidx.appcompat.widget.AppCompatTextHelper$ApplyTextViewCallback;
        r9.<init>(r8, r6, r7);
        r6 = r8.mStyle;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
        r9 = r10.getFont(r0, r6, r9);	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
        if (r9 == 0) goto L_0x00a4;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
    L_0x0085:
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
        if (r6 < r1) goto L_0x00a2;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
    L_0x0089:
        r6 = r8.mFontWeight;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
        if (r6 == r3) goto L_0x00a2;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
    L_0x008d:
        r9 = android.graphics.Typeface.create(r9, r4);	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
        r6 = r8.mFontWeight;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
        r7 = r8.mStyle;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
        r7 = r7 & r2;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
        if (r7 == 0) goto L_0x009a;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
    L_0x0098:
        r7 = 1;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
        goto L_0x009b;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
    L_0x009a:
        r7 = 0;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
    L_0x009b:
        r9 = android.graphics.Typeface.create(r9, r6, r7);	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
        r8.mFontTypeface = r9;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
        goto L_0x00a4;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
    L_0x00a2:
        r8.mFontTypeface = r9;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
    L_0x00a4:
        r9 = r8.mFontTypeface;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
        if (r9 != 0) goto L_0x00aa;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
    L_0x00a8:
        r9 = 1;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
        goto L_0x00ab;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
    L_0x00aa:
        r9 = 0;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
    L_0x00ab:
        r8.mAsyncFontPending = r9;	 Catch:{ UnsupportedOperationException -> 0x00ae, UnsupportedOperationException -> 0x00ae }
        goto L_0x00af;
    L_0x00af:
        r9 = r8.mFontTypeface;
        if (r9 != 0) goto L_0x00dc;
    L_0x00b3:
        r9 = r10.getString(r0);
        if (r9 == 0) goto L_0x00dc;
    L_0x00b9:
        r10 = android.os.Build.VERSION.SDK_INT;
        if (r10 < r1) goto L_0x00d4;
    L_0x00bd:
        r10 = r8.mFontWeight;
        if (r10 == r3) goto L_0x00d4;
    L_0x00c1:
        r9 = android.graphics.Typeface.create(r9, r4);
        r10 = r8.mFontWeight;
        r0 = r8.mStyle;
        r0 = r0 & r2;
        if (r0 == 0) goto L_0x00cd;
    L_0x00cc:
        r4 = 1;
    L_0x00cd:
        r9 = android.graphics.Typeface.create(r9, r10, r4);
        r8.mFontTypeface = r9;
        goto L_0x00dc;
    L_0x00d4:
        r10 = r8.mStyle;
        r9 = android.graphics.Typeface.create(r9, r10);
        r8.mFontTypeface = r9;
    L_0x00dc:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AppCompatTextHelper.updateTypefaceAndStyle(android.content.Context, androidx.appcompat.widget.TintTypedArray):void");
    }

    void onSetTextAppearance(Context context, int i) {
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i, R.styleable.TextAppearance);
        if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            setAllCaps(obtainStyledAttributes.getBoolean(R.styleable.TextAppearance_textAllCaps, false));
        }
        if (VERSION.SDK_INT < 23 && obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColor)) {
            ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColor);
            if (colorStateList != null) {
                this.mView.setTextColor(colorStateList);
            }
        }
        if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textSize) && obtainStyledAttributes.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        updateTypefaceAndStyle(context, obtainStyledAttributes);
        if (VERSION.SDK_INT >= 26 && obtainStyledAttributes.hasValue(R.styleable.TextAppearance_fontVariationSettings)) {
            String string = obtainStyledAttributes.getString(R.styleable.TextAppearance_fontVariationSettings);
            if (string != null) {
                this.mView.setFontVariationSettings(string);
            }
        }
        obtainStyledAttributes.recycle();
        Typeface typeface = this.mFontTypeface;
        if (typeface != null) {
            this.mView.setTypeface(typeface, this.mStyle);
        }
    }

    void setAllCaps(boolean z) {
        this.mView.setAllCaps(z);
    }

    void onSetCompoundDrawables() {
        applyCompoundDrawablesTints();
    }

    void applyCompoundDrawablesTints() {
        Drawable[] compoundDrawables;
        if (!(this.mDrawableLeftTint == null && this.mDrawableTopTint == null && this.mDrawableRightTint == null && this.mDrawableBottomTint == null)) {
            compoundDrawables = this.mView.getCompoundDrawables();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint);
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint);
        }
        if (VERSION.SDK_INT < 17) {
            return;
        }
        if (this.mDrawableStartTint != null || this.mDrawableEndTint != null) {
            compoundDrawables = this.mView.getCompoundDrawablesRelative();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableStartTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableEndTint);
        }
    }

    private void applyCompoundDrawableTint(Drawable drawable, TintInfo tintInfo) {
        if (drawable != null && tintInfo != null) {
            AppCompatDrawableManager.tintDrawable(drawable, tintInfo, this.mView.getDrawableState());
        }
    }

    private static TintInfo createTintInfo(Context context, AppCompatDrawableManager appCompatDrawableManager, int i) {
        ColorStateList tintList = appCompatDrawableManager.getTintList(context, i);
        if (tintList == null) {
            return null;
        }
        TintInfo tintInfo = new TintInfo();
        tintInfo.mHasTintList = true;
        tintInfo.mTintList = tintList;
        return tintInfo;
    }

    @RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
    void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
            autoSizeText();
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
    void setTextSize(int i, float f) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && !isAutoSizeEnabled()) {
            setTextSizeInternal(i, f);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
    void autoSizeText() {
        this.mAutoSizeTextHelper.autoSizeText();
    }

    @RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
    boolean isAutoSizeEnabled() {
        return this.mAutoSizeTextHelper.isAutoSizeEnabled();
    }

    private void setTextSizeInternal(int i, float f) {
        this.mAutoSizeTextHelper.setTextSizeInternal(i, f);
    }

    void setAutoSizeTextTypeWithDefaults(int i) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(i);
    }

    void setAutoSizeTextTypeUniformWithConfiguration(int i, int i2, int i3, int i4) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
    }

    void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull int[] iArr, int i) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
    }

    int getAutoSizeTextType() {
        return this.mAutoSizeTextHelper.getAutoSizeTextType();
    }

    int getAutoSizeStepGranularity() {
        return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
    }

    int getAutoSizeMinTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
    }

    int getAutoSizeMaxTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
    }

    int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
    }

    @Nullable
    ColorStateList getCompoundDrawableTintList() {
        TintInfo tintInfo = this.mDrawableTint;
        return tintInfo != null ? tintInfo.mTintList : null;
    }

    void setCompoundDrawableTintList(@Nullable ColorStateList colorStateList) {
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        TintInfo tintInfo = this.mDrawableTint;
        tintInfo.mTintList = colorStateList;
        tintInfo.mHasTintList = colorStateList != null;
        setCompoundTints();
    }

    @Nullable
    Mode getCompoundDrawableTintMode() {
        TintInfo tintInfo = this.mDrawableTint;
        return tintInfo != null ? tintInfo.mTintMode : null;
    }

    void setCompoundDrawableTintMode(@Nullable Mode mode) {
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        TintInfo tintInfo = this.mDrawableTint;
        tintInfo.mTintMode = mode;
        tintInfo.mHasTintMode = mode != null;
        setCompoundTints();
    }

    private void setCompoundTints() {
        TintInfo tintInfo = this.mDrawableTint;
        this.mDrawableLeftTint = tintInfo;
        this.mDrawableTopTint = tintInfo;
        this.mDrawableRightTint = tintInfo;
        this.mDrawableBottomTint = tintInfo;
        this.mDrawableStartTint = tintInfo;
        this.mDrawableEndTint = tintInfo;
    }

    private void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5, Drawable drawable6) {
        if (VERSION.SDK_INT >= 17 && (drawable5 != null || drawable6 != null)) {
            Drawable[] compoundDrawablesRelative = this.mView.getCompoundDrawablesRelative();
            TextView textView = this.mView;
            if (drawable5 == null) {
                drawable5 = compoundDrawablesRelative[0];
            }
            if (drawable2 == null) {
                drawable2 = compoundDrawablesRelative[1];
            }
            if (drawable6 == null) {
                drawable6 = compoundDrawablesRelative[2];
            }
            if (drawable4 == null) {
                drawable4 = compoundDrawablesRelative[3];
            }
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable5, drawable2, drawable6, drawable4);
        } else if (!(drawable == null && drawable2 == null && drawable3 == null && drawable4 == null)) {
            Drawable[] compoundDrawablesRelative2;
            if (VERSION.SDK_INT >= 17) {
                compoundDrawablesRelative2 = this.mView.getCompoundDrawablesRelative();
                if (!(compoundDrawablesRelative2[0] == null && compoundDrawablesRelative2[2] == null)) {
                    TextView textView2 = this.mView;
                    drawable3 = compoundDrawablesRelative2[0];
                    if (drawable2 == null) {
                        drawable2 = compoundDrawablesRelative2[1];
                    }
                    drawable6 = compoundDrawablesRelative2[2];
                    if (drawable4 == null) {
                        drawable4 = compoundDrawablesRelative2[3];
                    }
                    textView2.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable3, drawable2, drawable6, drawable4);
                    return;
                }
            }
            compoundDrawablesRelative2 = this.mView.getCompoundDrawables();
            TextView textView3 = this.mView;
            if (drawable == null) {
                drawable = compoundDrawablesRelative2[0];
            }
            if (drawable2 == null) {
                drawable2 = compoundDrawablesRelative2[1];
            }
            if (drawable3 == null) {
                drawable3 = compoundDrawablesRelative2[2];
            }
            if (drawable4 == null) {
                drawable4 = compoundDrawablesRelative2[3];
            }
            textView3.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        }
    }
}
