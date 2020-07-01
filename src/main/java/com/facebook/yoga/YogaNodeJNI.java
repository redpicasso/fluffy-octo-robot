package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;

@DoNotStrip
public class YogaNodeJNI extends YogaNodeJNIBase {
    @DoNotStrip
    private float mBorderBottom = 0.0f;
    @DoNotStrip
    private float mBorderLeft = 0.0f;
    @DoNotStrip
    private float mBorderRight = 0.0f;
    @DoNotStrip
    private float mBorderTop = 0.0f;
    @DoNotStrip
    private boolean mDoesLegacyStretchFlagAffectsLayout = false;
    @DoNotStrip
    private boolean mHasNewLayout = true;
    @DoNotStrip
    private float mHeight = Float.NaN;
    @DoNotStrip
    private int mLayoutDirection = 0;
    @DoNotStrip
    private float mLeft = Float.NaN;
    @DoNotStrip
    private float mMarginBottom = 0.0f;
    @DoNotStrip
    private float mMarginLeft = 0.0f;
    @DoNotStrip
    private float mMarginRight = 0.0f;
    @DoNotStrip
    private float mMarginTop = 0.0f;
    @DoNotStrip
    private float mPaddingBottom = 0.0f;
    @DoNotStrip
    private float mPaddingLeft = 0.0f;
    @DoNotStrip
    private float mPaddingRight = 0.0f;
    @DoNotStrip
    private float mPaddingTop = 0.0f;
    @DoNotStrip
    private float mTop = Float.NaN;
    @DoNotStrip
    private float mWidth = Float.NaN;

    public YogaNodeJNI(YogaConfig yogaConfig) {
        super(yogaConfig);
    }

    public void reset() {
        super.reset();
        this.mHasNewLayout = true;
        this.mWidth = Float.NaN;
        this.mHeight = Float.NaN;
        this.mTop = Float.NaN;
        this.mLeft = Float.NaN;
        this.mMarginLeft = 0.0f;
        this.mMarginTop = 0.0f;
        this.mMarginRight = 0.0f;
        this.mMarginBottom = 0.0f;
        this.mPaddingLeft = 0.0f;
        this.mPaddingTop = 0.0f;
        this.mPaddingRight = 0.0f;
        this.mPaddingBottom = 0.0f;
        this.mBorderLeft = 0.0f;
        this.mBorderTop = 0.0f;
        this.mBorderRight = 0.0f;
        this.mBorderBottom = 0.0f;
        this.mLayoutDirection = 0;
        this.mDoesLegacyStretchFlagAffectsLayout = false;
    }

    public boolean hasNewLayout() {
        return this.mHasNewLayout;
    }

    public void markLayoutSeen() {
        this.mHasNewLayout = false;
    }

    public float getLayoutX() {
        return this.mLeft;
    }

    public float getLayoutY() {
        return this.mTop;
    }

    public float getLayoutWidth() {
        return this.mWidth;
    }

    public float getLayoutHeight() {
        return this.mHeight;
    }

    public boolean getDoesLegacyStretchFlagAffectsLayout() {
        return this.mDoesLegacyStretchFlagAffectsLayout;
    }

    public float getLayoutMargin(YogaEdge yogaEdge) {
        switch (yogaEdge) {
            case LEFT:
                return this.mMarginLeft;
            case TOP:
                return this.mMarginTop;
            case RIGHT:
                return this.mMarginRight;
            case BOTTOM:
                return this.mMarginBottom;
            case START:
                return getLayoutDirection() == YogaDirection.RTL ? this.mMarginRight : this.mMarginLeft;
            case END:
                return getLayoutDirection() == YogaDirection.RTL ? this.mMarginLeft : this.mMarginRight;
            default:
                throw new IllegalArgumentException("Cannot get layout margins of multi-edge shorthands");
        }
    }

    public float getLayoutPadding(YogaEdge yogaEdge) {
        switch (yogaEdge) {
            case LEFT:
                return this.mPaddingLeft;
            case TOP:
                return this.mPaddingTop;
            case RIGHT:
                return this.mPaddingRight;
            case BOTTOM:
                return this.mPaddingBottom;
            case START:
                return getLayoutDirection() == YogaDirection.RTL ? this.mPaddingRight : this.mPaddingLeft;
            case END:
                return getLayoutDirection() == YogaDirection.RTL ? this.mPaddingLeft : this.mPaddingRight;
            default:
                throw new IllegalArgumentException("Cannot get layout paddings of multi-edge shorthands");
        }
    }

    public float getLayoutBorder(YogaEdge yogaEdge) {
        switch (yogaEdge) {
            case LEFT:
                return this.mBorderLeft;
            case TOP:
                return this.mBorderTop;
            case RIGHT:
                return this.mBorderRight;
            case BOTTOM:
                return this.mBorderBottom;
            case START:
                return getLayoutDirection() == YogaDirection.RTL ? this.mBorderRight : this.mBorderLeft;
            case END:
                return getLayoutDirection() == YogaDirection.RTL ? this.mBorderLeft : this.mBorderRight;
            default:
                throw new IllegalArgumentException("Cannot get layout border of multi-edge shorthands");
        }
    }

    public YogaDirection getLayoutDirection() {
        return YogaDirection.fromInt(this.mLayoutDirection);
    }
}
