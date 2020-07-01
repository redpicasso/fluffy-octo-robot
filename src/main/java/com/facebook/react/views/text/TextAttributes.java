package com.facebook.react.views.text;

import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.uimanager.PixelUtil;

public class TextAttributes {
    public static final float DEFAULT_MAX_FONT_SIZE_MULTIPLIER = 0.0f;
    private boolean mAllowFontScaling = true;
    private float mFontSize = Float.NaN;
    private float mHeightOfTallestInlineViewOrImage = Float.NaN;
    private float mLetterSpacing = Float.NaN;
    private float mLineHeight = Float.NaN;
    private float mMaxFontSizeMultiplier = Float.NaN;
    private TextTransform mTextTransform = TextTransform.UNSET;

    public TextAttributes applyChild(TextAttributes textAttributes) {
        TextAttributes textAttributes2 = new TextAttributes();
        textAttributes2.mAllowFontScaling = this.mAllowFontScaling;
        textAttributes2.mFontSize = !Float.isNaN(textAttributes.mFontSize) ? textAttributes.mFontSize : this.mFontSize;
        textAttributes2.mLineHeight = !Float.isNaN(textAttributes.mLineHeight) ? textAttributes.mLineHeight : this.mLineHeight;
        textAttributes2.mLetterSpacing = !Float.isNaN(textAttributes.mLetterSpacing) ? textAttributes.mLetterSpacing : this.mLetterSpacing;
        textAttributes2.mMaxFontSizeMultiplier = !Float.isNaN(textAttributes.mMaxFontSizeMultiplier) ? textAttributes.mMaxFontSizeMultiplier : this.mMaxFontSizeMultiplier;
        textAttributes2.mHeightOfTallestInlineViewOrImage = !Float.isNaN(textAttributes.mHeightOfTallestInlineViewOrImage) ? textAttributes.mHeightOfTallestInlineViewOrImage : this.mHeightOfTallestInlineViewOrImage;
        textAttributes2.mTextTransform = textAttributes.mTextTransform != TextTransform.UNSET ? textAttributes.mTextTransform : this.mTextTransform;
        return textAttributes2;
    }

    public boolean getAllowFontScaling() {
        return this.mAllowFontScaling;
    }

    public void setAllowFontScaling(boolean z) {
        this.mAllowFontScaling = z;
    }

    public float getFontSize() {
        return this.mFontSize;
    }

    public void setFontSize(float f) {
        this.mFontSize = f;
    }

    public float getLineHeight() {
        return this.mLineHeight;
    }

    public void setLineHeight(float f) {
        this.mLineHeight = f;
    }

    public float getLetterSpacing() {
        return this.mLetterSpacing;
    }

    public void setLetterSpacing(float f) {
        this.mLetterSpacing = f;
    }

    public float getMaxFontSizeMultiplier() {
        return this.mMaxFontSizeMultiplier;
    }

    public void setMaxFontSizeMultiplier(float f) {
        if (f == 0.0f || f >= 1.0f) {
            this.mMaxFontSizeMultiplier = f;
            return;
        }
        throw new JSApplicationIllegalArgumentException("maxFontSizeMultiplier must be NaN, 0, or >= 1");
    }

    public float getHeightOfTallestInlineViewOrImage() {
        return this.mHeightOfTallestInlineViewOrImage;
    }

    public void setHeightOfTallestInlineViewOrImage(float f) {
        this.mHeightOfTallestInlineViewOrImage = f;
    }

    public TextTransform getTextTransform() {
        return this.mTextTransform;
    }

    public void setTextTransform(TextTransform textTransform) {
        this.mTextTransform = textTransform;
    }

    public int getEffectiveFontSize() {
        double ceil;
        float f = !Float.isNaN(this.mFontSize) ? this.mFontSize : 14.0f;
        if (this.mAllowFontScaling) {
            ceil = Math.ceil((double) PixelUtil.toPixelFromSP(f, getEffectiveMaxFontSizeMultiplier()));
        } else {
            ceil = Math.ceil((double) PixelUtil.toPixelFromDIP(f));
        }
        return (int) ceil;
    }

    public float getEffectiveLineHeight() {
        if (Float.isNaN(this.mLineHeight)) {
            return Float.NaN;
        }
        float toPixelFromSP;
        if (this.mAllowFontScaling) {
            toPixelFromSP = PixelUtil.toPixelFromSP(this.mLineHeight, getEffectiveMaxFontSizeMultiplier());
        } else {
            toPixelFromSP = PixelUtil.toPixelFromDIP(this.mLineHeight);
        }
        Object obj = (Float.isNaN(this.mHeightOfTallestInlineViewOrImage) || this.mHeightOfTallestInlineViewOrImage <= toPixelFromSP) ? null : 1;
        if (obj != null) {
            toPixelFromSP = this.mHeightOfTallestInlineViewOrImage;
        }
        return toPixelFromSP;
    }

    public float getEffectiveLetterSpacing() {
        if (Float.isNaN(this.mLetterSpacing)) {
            return Float.NaN;
        }
        float toPixelFromSP;
        if (this.mAllowFontScaling) {
            toPixelFromSP = PixelUtil.toPixelFromSP(this.mLetterSpacing, getEffectiveMaxFontSizeMultiplier());
        } else {
            toPixelFromSP = PixelUtil.toPixelFromDIP(this.mLetterSpacing);
        }
        return toPixelFromSP / ((float) getEffectiveFontSize());
    }

    public float getEffectiveMaxFontSizeMultiplier() {
        return !Float.isNaN(this.mMaxFontSizeMultiplier) ? this.mMaxFontSizeMultiplier : 0.0f;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TextAttributes {\n  getAllowFontScaling(): ");
        stringBuilder.append(getAllowFontScaling());
        stringBuilder.append("\n  getFontSize(): ");
        stringBuilder.append(getFontSize());
        stringBuilder.append("\n  getEffectiveFontSize(): ");
        stringBuilder.append(getEffectiveFontSize());
        stringBuilder.append("\n  getHeightOfTallestInlineViewOrImage(): ");
        stringBuilder.append(getHeightOfTallestInlineViewOrImage());
        stringBuilder.append("\n  getLetterSpacing(): ");
        stringBuilder.append(getLetterSpacing());
        stringBuilder.append("\n  getEffectiveLetterSpacing(): ");
        stringBuilder.append(getEffectiveLetterSpacing());
        stringBuilder.append("\n  getLineHeight(): ");
        stringBuilder.append(getLineHeight());
        stringBuilder.append("\n  getEffectiveLineHeight(): ");
        stringBuilder.append(getEffectiveLineHeight());
        stringBuilder.append("\n  getTextTransform(): ");
        stringBuilder.append(getTextTransform());
        stringBuilder.append("\n  getMaxFontSizeMultiplier(): ");
        stringBuilder.append(getMaxFontSizeMultiplier());
        stringBuilder.append("\n  getEffectiveMaxFontSizeMultiplier(): ");
        stringBuilder.append(getEffectiveMaxFontSizeMultiplier());
        stringBuilder.append("\n}");
        return stringBuilder.toString();
    }
}
