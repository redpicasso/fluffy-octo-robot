package com.facebook.react.views.text;

import android.os.Build.VERSION;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.yoga.YogaDirection;
import javax.annotation.Nullable;

public class TextAttributeProps {
    private static final int DEFAULT_TEXT_SHADOW_COLOR = 1426063360;
    private static final String INLINE_IMAGE_PLACEHOLDER = "I";
    private static final String PROP_SHADOW_COLOR = "textShadowColor";
    private static final String PROP_SHADOW_OFFSET = "textShadowOffset";
    private static final String PROP_SHADOW_OFFSET_HEIGHT = "height";
    private static final String PROP_SHADOW_OFFSET_WIDTH = "width";
    private static final String PROP_SHADOW_RADIUS = "textShadowRadius";
    private static final String PROP_TEXT_TRANSFORM = "textTransform";
    public static final int UNSET = -1;
    protected boolean mAllowFontScaling = true;
    protected int mBackgroundColor;
    protected int mColor;
    protected boolean mContainsImages;
    @Nullable
    protected String mFontFamily;
    protected int mFontSize = -1;
    protected float mFontSizeInput = -1.0f;
    protected int mFontStyle;
    protected int mFontWeight;
    protected float mHeightOfTallestInlineImage;
    protected boolean mIncludeFontPadding;
    protected boolean mIsBackgroundColorSet = false;
    protected boolean mIsColorSet = false;
    protected boolean mIsLineThroughTextDecorationSet;
    protected boolean mIsUnderlineTextDecorationSet;
    protected int mJustificationMode;
    protected float mLetterSpacing = Float.NaN;
    protected float mLetterSpacingInput = Float.NaN;
    protected float mLineHeight = Float.NaN;
    protected float mLineHeightInput = -1.0f;
    protected int mNumberOfLines = -1;
    private final ReactStylesDiffMap mProps;
    protected int mTextAlign = 0;
    protected int mTextBreakStrategy;
    protected int mTextShadowColor;
    protected float mTextShadowOffsetDx;
    protected float mTextShadowOffsetDy;
    protected float mTextShadowRadius;
    protected TextTransform mTextTransform;

    public TextAttributeProps(ReactStylesDiffMap reactStylesDiffMap) {
        this.mTextBreakStrategy = VERSION.SDK_INT < 23 ? 0 : 1;
        int i = VERSION.SDK_INT;
        this.mJustificationMode = 0;
        this.mTextTransform = TextTransform.UNSET;
        this.mTextShadowOffsetDx = 0.0f;
        this.mTextShadowOffsetDy = 0.0f;
        this.mTextShadowRadius = 1.0f;
        this.mTextShadowColor = 1426063360;
        this.mIsUnderlineTextDecorationSet = false;
        this.mIsLineThroughTextDecorationSet = false;
        this.mIncludeFontPadding = true;
        this.mFontStyle = -1;
        this.mFontWeight = -1;
        ReadableMap readableMap = null;
        this.mFontFamily = null;
        this.mContainsImages = false;
        this.mHeightOfTallestInlineImage = Float.NaN;
        this.mProps = reactStylesDiffMap;
        setNumberOfLines(getIntProp(ViewProps.NUMBER_OF_LINES, -1));
        setLineHeight(getFloatProp(ViewProps.LINE_HEIGHT, -1.0f));
        setLetterSpacing(getFloatProp(ViewProps.LETTER_SPACING, Float.NaN));
        setAllowFontScaling(getBooleanProp(ViewProps.ALLOW_FONT_SCALING, true));
        setTextAlign(getStringProp(ViewProps.TEXT_ALIGN));
        setFontSize(getFloatProp(ViewProps.FONT_SIZE, -1.0f));
        String str = ViewProps.COLOR;
        setColor(reactStylesDiffMap.hasKey(str) ? Integer.valueOf(reactStylesDiffMap.getInt(str, 0)) : null);
        str = "foregroundColor";
        setColor(reactStylesDiffMap.hasKey(str) ? Integer.valueOf(reactStylesDiffMap.getInt(str, 0)) : null);
        str = ViewProps.BACKGROUND_COLOR;
        setBackgroundColor(reactStylesDiffMap.hasKey(str) ? Integer.valueOf(reactStylesDiffMap.getInt(str, 0)) : null);
        setFontFamily(getStringProp(ViewProps.FONT_FAMILY));
        setFontWeight(getStringProp(ViewProps.FONT_WEIGHT));
        setFontStyle(getStringProp(ViewProps.FONT_STYLE));
        setIncludeFontPadding(getBooleanProp(ViewProps.INCLUDE_FONT_PADDING, true));
        setTextDecorationLine(getStringProp(ViewProps.TEXT_DECORATION_LINE));
        setTextBreakStrategy(getStringProp(ViewProps.TEXT_BREAK_STRATEGY));
        str = "textShadowOffset";
        if (reactStylesDiffMap.hasKey(str)) {
            readableMap = reactStylesDiffMap.getMap(str);
        }
        setTextShadowOffset(readableMap);
        setTextShadowRadius((float) getIntProp("textShadowRadius", 1));
        setTextShadowColor(getIntProp("textShadowColor", 1426063360));
        setTextTransform(getStringProp("textTransform"));
    }

    private boolean getBooleanProp(String str, boolean z) {
        return this.mProps.hasKey(str) ? this.mProps.getBoolean(str, z) : z;
    }

    private String getStringProp(String str) {
        return this.mProps.hasKey(str) ? this.mProps.getString(str) : null;
    }

    private int getIntProp(String str, int i) {
        return this.mProps.hasKey(str) ? this.mProps.getInt(str, i) : i;
    }

    private float getFloatProp(String str, float f) {
        return this.mProps.hasKey(str) ? this.mProps.getFloat(str, f) : f;
    }

    public float getEffectiveLineHeight() {
        Object obj = (Float.isNaN(this.mLineHeight) || Float.isNaN(this.mHeightOfTallestInlineImage) || this.mHeightOfTallestInlineImage <= this.mLineHeight) ? null : 1;
        return obj != null ? this.mHeightOfTallestInlineImage : this.mLineHeight;
    }

    public int getTextAlign() {
        int i = this.mTextAlign;
        if (getLayoutDirection() != YogaDirection.RTL) {
            return i;
        }
        if (i == 5) {
            return 3;
        }
        return i == 3 ? 5 : i;
    }

    public void setNumberOfLines(int i) {
        if (i == 0) {
            i = -1;
        }
        this.mNumberOfLines = i;
    }

    public void setLineHeight(float f) {
        this.mLineHeightInput = f;
        if (f == -1.0f) {
            this.mLineHeight = Float.NaN;
            return;
        }
        if (this.mAllowFontScaling) {
            f = PixelUtil.toPixelFromSP(f);
        } else {
            f = PixelUtil.toPixelFromDIP(f);
        }
        this.mLineHeight = f;
    }

    public void setLetterSpacing(float f) {
        this.mLetterSpacingInput = f;
        if (this.mAllowFontScaling) {
            f = PixelUtil.toPixelFromSP(this.mLetterSpacingInput);
        } else {
            f = PixelUtil.toPixelFromDIP(this.mLetterSpacingInput);
        }
        this.mLetterSpacing = f;
    }

    public void setAllowFontScaling(boolean z) {
        if (z != this.mAllowFontScaling) {
            this.mAllowFontScaling = z;
            setFontSize(this.mFontSizeInput);
            setLineHeight(this.mLineHeightInput);
            setLetterSpacing(this.mLetterSpacingInput);
        }
    }

    public void setTextAlign(@Nullable String str) {
        if ("justify".equals(str)) {
            if (VERSION.SDK_INT >= 26) {
                this.mJustificationMode = 1;
            }
            this.mTextAlign = 3;
            return;
        }
        if (VERSION.SDK_INT >= 26) {
            this.mJustificationMode = 0;
        }
        if (str == null || "auto".equals(str)) {
            this.mTextAlign = 0;
        } else if (ViewProps.LEFT.equals(str)) {
            this.mTextAlign = 3;
        } else if (ViewProps.RIGHT.equals(str)) {
            this.mTextAlign = 5;
        } else if ("center".equals(str)) {
            this.mTextAlign = 1;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid textAlign: ");
            stringBuilder.append(str);
            throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
        }
    }

    public void setFontSize(float f) {
        this.mFontSizeInput = f;
        if (f != -1.0f) {
            double ceil;
            if (this.mAllowFontScaling) {
                ceil = Math.ceil((double) PixelUtil.toPixelFromSP(f));
            } else {
                ceil = Math.ceil((double) PixelUtil.toPixelFromDIP(f));
            }
            f = (float) ceil;
        }
        this.mFontSize = (int) f;
    }

    public void setColor(@Nullable Integer num) {
        this.mIsColorSet = num != null;
        if (this.mIsColorSet) {
            this.mColor = num.intValue();
        }
    }

    public void setBackgroundColor(Integer num) {
        this.mIsBackgroundColorSet = num != null;
        if (this.mIsBackgroundColorSet) {
            this.mBackgroundColor = num.intValue();
        }
    }

    public void setFontFamily(@Nullable String str) {
        this.mFontFamily = str;
    }

    public void setFontWeight(@Nullable String str) {
        int i = -1;
        int parseNumericFontWeight = str != null ? parseNumericFontWeight(str) : -1;
        if (parseNumericFontWeight >= 500 || "bold".equals(str)) {
            i = 1;
        } else if ("normal".equals(str) || (parseNumericFontWeight != -1 && parseNumericFontWeight < 500)) {
            i = 0;
        }
        if (i != this.mFontWeight) {
            this.mFontWeight = i;
        }
    }

    public void setFontStyle(@Nullable String str) {
        int i = "italic".equals(str) ? 2 : "normal".equals(str) ? 0 : -1;
        if (i != this.mFontStyle) {
            this.mFontStyle = i;
        }
    }

    public void setIncludeFontPadding(boolean z) {
        this.mIncludeFontPadding = z;
    }

    public void setTextDecorationLine(@Nullable String str) {
        int i = 0;
        this.mIsUnderlineTextDecorationSet = false;
        this.mIsLineThroughTextDecorationSet = false;
        if (str != null) {
            String[] split = str.split(" ");
            int length = split.length;
            while (i < length) {
                Object obj = split[i];
                if ("underline".equals(obj)) {
                    this.mIsUnderlineTextDecorationSet = true;
                } else if ("line-through".equals(obj)) {
                    this.mIsLineThroughTextDecorationSet = true;
                }
                i++;
            }
        }
    }

    public void setTextBreakStrategy(@Nullable String str) {
        if (VERSION.SDK_INT >= 23) {
            if (str == null || "highQuality".equals(str)) {
                this.mTextBreakStrategy = 1;
            } else if ("simple".equals(str)) {
                this.mTextBreakStrategy = 0;
            } else if ("balanced".equals(str)) {
                this.mTextBreakStrategy = 2;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid textBreakStrategy: ");
                stringBuilder.append(str);
                throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
            }
        }
    }

    public void setTextShadowOffset(ReadableMap readableMap) {
        this.mTextShadowOffsetDx = 0.0f;
        this.mTextShadowOffsetDy = 0.0f;
        if (readableMap != null) {
            String str = "width";
            if (readableMap.hasKey(str) && !readableMap.isNull(str)) {
                this.mTextShadowOffsetDx = PixelUtil.toPixelFromDIP(readableMap.getDouble(str));
            }
            str = "height";
            if (readableMap.hasKey(str) && !readableMap.isNull(str)) {
                this.mTextShadowOffsetDy = PixelUtil.toPixelFromDIP(readableMap.getDouble(str));
            }
        }
    }

    public void setTextShadowRadius(float f) {
        if (f != this.mTextShadowRadius) {
            this.mTextShadowRadius = f;
        }
    }

    public void setTextShadowColor(int i) {
        if (i != this.mTextShadowColor) {
            this.mTextShadowColor = i;
        }
    }

    public void setTextTransform(@Nullable String str) {
        if (str == null || ViewProps.NONE.equals(str)) {
            this.mTextTransform = TextTransform.NONE;
        } else if ("uppercase".equals(str)) {
            this.mTextTransform = TextTransform.UPPERCASE;
        } else if ("lowercase".equals(str)) {
            this.mTextTransform = TextTransform.LOWERCASE;
        } else if ("capitalize".equals(str)) {
            this.mTextTransform = TextTransform.CAPITALIZE;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid textTransform: ");
            stringBuilder.append(str);
            throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
        }
    }

    private static int parseNumericFontWeight(String str) {
        return (str.length() != 3 || !str.endsWith("00") || str.charAt(0) > '9' || str.charAt(0) < '1') ? -1 : (str.charAt(0) - 48) * 100;
    }

    private YogaDirection getLayoutDirection() {
        return YogaDirection.LTR;
    }

    public float getBottomPadding() {
        return getPaddingProp(ViewProps.PADDING_BOTTOM);
    }

    public float getLeftPadding() {
        return getPaddingProp(ViewProps.PADDING_LEFT);
    }

    public float getStartPadding() {
        return getPaddingProp(ViewProps.PADDING_START);
    }

    public float getEndPadding() {
        return getPaddingProp(ViewProps.PADDING_END);
    }

    public float getTopPadding() {
        return getPaddingProp(ViewProps.PADDING_TOP);
    }

    public float getRightPadding() {
        return getPaddingProp(ViewProps.PADDING_RIGHT);
    }

    private float getPaddingProp(String str) {
        ReactStylesDiffMap reactStylesDiffMap = this.mProps;
        String str2 = ViewProps.PADDING;
        if (reactStylesDiffMap.hasKey(str2)) {
            return PixelUtil.toPixelFromDIP(getFloatProp(str2, 0.0f));
        }
        return PixelUtil.toPixelFromDIP(getFloatProp(str, 0.0f));
    }
}
