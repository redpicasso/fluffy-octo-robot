package com.facebook.react.views.text;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.NativeViewHierarchyOptimizer;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaUnit;
import com.facebook.yoga.YogaValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

@TargetApi(23)
public abstract class ReactBaseTextShadowNode extends LayoutShadowNode {
    public static final int DEFAULT_TEXT_SHADOW_COLOR = 1426063360;
    private static final String INLINE_VIEW_PLACEHOLDER = "0";
    public static final String PROP_SHADOW_COLOR = "textShadowColor";
    public static final String PROP_SHADOW_OFFSET = "textShadowOffset";
    public static final String PROP_SHADOW_OFFSET_HEIGHT = "height";
    public static final String PROP_SHADOW_OFFSET_WIDTH = "width";
    public static final String PROP_SHADOW_RADIUS = "textShadowRadius";
    public static final String PROP_TEXT_TRANSFORM = "textTransform";
    public static final int UNSET = -1;
    protected int mBackgroundColor;
    protected int mColor;
    protected boolean mContainsImages;
    @Nullable
    protected String mFontFamily;
    protected int mFontStyle;
    protected int mFontWeight;
    protected boolean mIncludeFontPadding;
    protected Map<Integer, ReactShadowNode> mInlineViews;
    protected boolean mIsBackgroundColorSet = false;
    protected boolean mIsColorSet = false;
    protected boolean mIsLineThroughTextDecorationSet;
    protected boolean mIsUnderlineTextDecorationSet;
    protected int mJustificationMode;
    protected int mNumberOfLines = -1;
    protected int mTextAlign = 0;
    protected TextAttributes mTextAttributes;
    protected int mTextBreakStrategy;
    protected int mTextShadowColor;
    protected float mTextShadowOffsetDx;
    protected float mTextShadowOffsetDy;
    protected float mTextShadowRadius;
    protected TextTransform mTextTransform;

    private static class SetSpanOperation {
        protected int end;
        protected int start;
        protected ReactSpan what;

        SetSpanOperation(int i, int i2, ReactSpan reactSpan) {
            this.start = i;
            this.end = i2;
            this.what = reactSpan;
        }

        public void execute(SpannableStringBuilder spannableStringBuilder, int i) {
            spannableStringBuilder.setSpan(this.what, this.start, this.end, ((i << 16) & 16711680) | ((this.start == 0 ? 18 : 34) & -16711681));
        }
    }

    private static void buildSpannedFromShadowNode(ReactBaseTextShadowNode reactBaseTextShadowNode, SpannableStringBuilder spannableStringBuilder, List<SetSpanOperation> list, TextAttributes textAttributes, boolean z, Map<Integer, ReactShadowNode> map, int i) {
        TextAttributes applyChild;
        int reactTag;
        ReactBaseTextShadowNode reactBaseTextShadowNode2 = reactBaseTextShadowNode;
        SpannableStringBuilder spannableStringBuilder2 = spannableStringBuilder;
        List<SetSpanOperation> list2 = list;
        TextAttributes textAttributes2 = textAttributes;
        int i2 = i;
        if (textAttributes2 != null) {
            applyChild = textAttributes2.applyChild(reactBaseTextShadowNode2.mTextAttributes);
        } else {
            applyChild = reactBaseTextShadowNode2.mTextAttributes;
        }
        TextAttributes textAttributes3 = applyChild;
        int childCount = reactBaseTextShadowNode.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            ReactShadowNode childAt = reactBaseTextShadowNode2.getChildAt(i3);
            if (childAt instanceof ReactRawTextShadowNode) {
                spannableStringBuilder2.append(TextTransform.apply(((ReactRawTextShadowNode) childAt).getText(), textAttributes3.getTextTransform()));
            } else if (childAt instanceof ReactBaseTextShadowNode) {
                buildSpannedFromShadowNode((ReactBaseTextShadowNode) childAt, spannableStringBuilder, list, textAttributes3, z, map, spannableStringBuilder.length());
            } else {
                boolean z2 = childAt instanceof ReactTextInlineImageShadowNode;
                CharSequence charSequence = INLINE_VIEW_PLACEHOLDER;
                if (z2) {
                    spannableStringBuilder2.append(charSequence);
                    list2.add(new SetSpanOperation(spannableStringBuilder.length() - 1, spannableStringBuilder.length(), ((ReactTextInlineImageShadowNode) childAt).buildInlineImageSpan()));
                } else if (z) {
                    reactTag = childAt.getReactTag();
                    YogaValue styleWidth = childAt.getStyleWidth();
                    YogaValue styleHeight = childAt.getStyleHeight();
                    if (styleWidth.unit == YogaUnit.POINT && styleHeight.unit == YogaUnit.POINT) {
                        float f = styleWidth.value;
                        float f2 = styleHeight.value;
                        spannableStringBuilder2.append(charSequence);
                        list2.add(new SetSpanOperation(spannableStringBuilder.length() - 1, spannableStringBuilder.length(), new TextInlineViewPlaceholderSpan(reactTag, (int) f, (int) f2)));
                        map.put(Integer.valueOf(reactTag), childAt);
                        childAt.markUpdateSeen();
                    } else {
                        throw new IllegalViewOperationException("Views nested within a <Text> must have a width and height");
                    }
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected view type nested under a <Text> or <TextInput> node: ");
                    stringBuilder.append(childAt.getClass());
                    throw new IllegalViewOperationException(stringBuilder.toString());
                }
            }
            Map<Integer, ReactShadowNode> map2 = map;
            childAt.markUpdateSeen();
        }
        reactTag = spannableStringBuilder.length();
        if (reactTag >= i2) {
            float effectiveLetterSpacing;
            if (reactBaseTextShadowNode2.mIsColorSet) {
                list2.add(new SetSpanOperation(i2, reactTag, new ReactForegroundColorSpan(reactBaseTextShadowNode2.mColor)));
            }
            if (reactBaseTextShadowNode2.mIsBackgroundColorSet) {
                list2.add(new SetSpanOperation(i2, reactTag, new ReactBackgroundColorSpan(reactBaseTextShadowNode2.mBackgroundColor)));
            }
            if (VERSION.SDK_INT >= 21) {
                effectiveLetterSpacing = textAttributes3.getEffectiveLetterSpacing();
                if (!Float.isNaN(effectiveLetterSpacing) && (textAttributes2 == null || textAttributes.getEffectiveLetterSpacing() != effectiveLetterSpacing)) {
                    list2.add(new SetSpanOperation(i2, reactTag, new CustomLetterSpacingSpan(effectiveLetterSpacing)));
                }
            }
            int effectiveFontSize = textAttributes3.getEffectiveFontSize();
            if (textAttributes2 == null || textAttributes.getEffectiveFontSize() != effectiveFontSize) {
                list2.add(new SetSpanOperation(i2, reactTag, new ReactAbsoluteSizeSpan(effectiveFontSize)));
            }
            if (!(reactBaseTextShadowNode2.mFontStyle == -1 && reactBaseTextShadowNode2.mFontWeight == -1 && reactBaseTextShadowNode2.mFontFamily == null)) {
                list2.add(new SetSpanOperation(i2, reactTag, new CustomStyleSpan(reactBaseTextShadowNode2.mFontStyle, reactBaseTextShadowNode2.mFontWeight, reactBaseTextShadowNode2.mFontFamily, reactBaseTextShadowNode.getThemedContext().getAssets())));
            }
            if (reactBaseTextShadowNode2.mIsUnderlineTextDecorationSet) {
                list2.add(new SetSpanOperation(i2, reactTag, new ReactUnderlineSpan()));
            }
            if (reactBaseTextShadowNode2.mIsLineThroughTextDecorationSet) {
                list2.add(new SetSpanOperation(i2, reactTag, new ReactStrikethroughSpan()));
            }
            if (!((reactBaseTextShadowNode2.mTextShadowOffsetDx == 0.0f && reactBaseTextShadowNode2.mTextShadowOffsetDy == 0.0f && reactBaseTextShadowNode2.mTextShadowRadius == 0.0f) || Color.alpha(reactBaseTextShadowNode2.mTextShadowColor) == 0)) {
                list2.add(new SetSpanOperation(i2, reactTag, new ShadowStyleSpan(reactBaseTextShadowNode2.mTextShadowOffsetDx, reactBaseTextShadowNode2.mTextShadowOffsetDy, reactBaseTextShadowNode2.mTextShadowRadius, reactBaseTextShadowNode2.mTextShadowColor)));
            }
            effectiveLetterSpacing = textAttributes3.getEffectiveLineHeight();
            if (!Float.isNaN(effectiveLetterSpacing) && (textAttributes2 == null || textAttributes.getEffectiveLineHeight() != effectiveLetterSpacing)) {
                list2.add(new SetSpanOperation(i2, reactTag, new CustomLineHeightSpan(effectiveLetterSpacing)));
            }
            list2.add(new SetSpanOperation(i2, reactTag, new ReactTagSpan(reactBaseTextShadowNode.getReactTag())));
        }
    }

    protected static Spannable spannedFromShadowNode(ReactBaseTextShadowNode reactBaseTextShadowNode, String str, boolean z, NativeViewHierarchyOptimizer nativeViewHierarchyOptimizer) {
        int i = 0;
        boolean z2 = (z && nativeViewHierarchyOptimizer == null) ? false : true;
        Assertions.assertCondition(z2, "nativeViewHierarchyOptimizer is required when inline views are supported");
        Spannable spannableStringBuilder = new SpannableStringBuilder();
        List<SetSpanOperation> arrayList = new ArrayList();
        Map hashMap = z ? new HashMap() : null;
        if (str != null) {
            spannableStringBuilder.append(TextTransform.apply(str, reactBaseTextShadowNode.mTextAttributes.getTextTransform()));
        }
        buildSpannedFromShadowNode(reactBaseTextShadowNode, spannableStringBuilder, arrayList, null, z, hashMap, 0);
        reactBaseTextShadowNode.mContainsImages = false;
        reactBaseTextShadowNode.mInlineViews = hashMap;
        float f = Float.NaN;
        for (SetSpanOperation setSpanOperation : arrayList) {
            boolean z3 = setSpanOperation.what instanceof TextInlineImageSpan;
            if (z3 || (setSpanOperation.what instanceof TextInlineViewPlaceholderSpan)) {
                int height;
                if (z3) {
                    height = ((TextInlineImageSpan) setSpanOperation.what).getHeight();
                    reactBaseTextShadowNode.mContainsImages = true;
                } else {
                    TextInlineViewPlaceholderSpan textInlineViewPlaceholderSpan = (TextInlineViewPlaceholderSpan) setSpanOperation.what;
                    int height2 = textInlineViewPlaceholderSpan.getHeight();
                    ReactShadowNode reactShadowNode = (ReactShadowNode) hashMap.get(Integer.valueOf(textInlineViewPlaceholderSpan.getReactTag()));
                    nativeViewHierarchyOptimizer.handleForceViewToBeNonLayoutOnly(reactShadowNode);
                    reactShadowNode.setLayoutParent(reactBaseTextShadowNode);
                    height = height2;
                }
                if (Float.isNaN(f) || ((float) height) > f) {
                    f = (float) height;
                }
            }
            setSpanOperation.execute(spannableStringBuilder, i);
            i++;
        }
        reactBaseTextShadowNode.mTextAttributes.setHeightOfTallestInlineViewOrImage(f);
        return spannableStringBuilder;
    }

    private static int parseNumericFontWeight(String str) {
        return (str.length() != 3 || !str.endsWith("00") || str.charAt(0) > '9' || str.charAt(0) < '1') ? -1 : (str.charAt(0) - 48) * 100;
    }

    public ReactBaseTextShadowNode() {
        this.mTextBreakStrategy = VERSION.SDK_INT < 23 ? 0 : 1;
        int i = VERSION.SDK_INT;
        this.mJustificationMode = 0;
        this.mTextTransform = TextTransform.UNSET;
        this.mTextShadowOffsetDx = 0.0f;
        this.mTextShadowOffsetDy = 0.0f;
        this.mTextShadowRadius = 0.0f;
        this.mTextShadowColor = DEFAULT_TEXT_SHADOW_COLOR;
        this.mIsUnderlineTextDecorationSet = false;
        this.mIsLineThroughTextDecorationSet = false;
        this.mIncludeFontPadding = true;
        this.mFontStyle = -1;
        this.mFontWeight = -1;
        this.mFontFamily = null;
        this.mContainsImages = false;
        this.mTextAttributes = new TextAttributes();
    }

    private int getTextAlign() {
        int i = this.mTextAlign;
        if (getLayoutDirection() != YogaDirection.RTL) {
            return i;
        }
        if (i == 5) {
            return 3;
        }
        return i == 3 ? 5 : i;
    }

    @ReactProp(defaultInt = -1, name = "numberOfLines")
    public void setNumberOfLines(int i) {
        if (i == 0) {
            i = -1;
        }
        this.mNumberOfLines = i;
        markUpdated();
    }

    @ReactProp(defaultFloat = Float.NaN, name = "lineHeight")
    public void setLineHeight(float f) {
        this.mTextAttributes.setLineHeight(f);
        markUpdated();
    }

    @ReactProp(defaultFloat = Float.NaN, name = "letterSpacing")
    public void setLetterSpacing(float f) {
        this.mTextAttributes.setLetterSpacing(f);
        markUpdated();
    }

    @ReactProp(defaultBoolean = true, name = "allowFontScaling")
    public void setAllowFontScaling(boolean z) {
        if (z != this.mTextAttributes.getAllowFontScaling()) {
            this.mTextAttributes.setAllowFontScaling(z);
            markUpdated();
        }
    }

    @ReactProp(defaultFloat = Float.NaN, name = "maxFontSizeMultiplier")
    public void setMaxFontSizeMultiplier(float f) {
        if (f != this.mTextAttributes.getMaxFontSizeMultiplier()) {
            this.mTextAttributes.setMaxFontSizeMultiplier(f);
            markUpdated();
        }
    }

    @ReactProp(name = "textAlign")
    public void setTextAlign(@Nullable String str) {
        if ("justify".equals(str)) {
            if (VERSION.SDK_INT >= 26) {
                this.mJustificationMode = 1;
            }
            this.mTextAlign = 3;
        } else {
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
        markUpdated();
    }

    @ReactProp(defaultFloat = Float.NaN, name = "fontSize")
    public void setFontSize(float f) {
        this.mTextAttributes.setFontSize(f);
        markUpdated();
    }

    @ReactProp(name = "color")
    public void setColor(@Nullable Integer num) {
        this.mIsColorSet = num != null;
        if (this.mIsColorSet) {
            this.mColor = num.intValue();
        }
        markUpdated();
    }

    @ReactProp(customType = "Color", name = "backgroundColor")
    public void setBackgroundColor(@Nullable Integer num) {
        if (isVirtual()) {
            this.mIsBackgroundColorSet = num != null;
            if (this.mIsBackgroundColorSet) {
                this.mBackgroundColor = num.intValue();
            }
            markUpdated();
        }
    }

    @ReactProp(name = "fontFamily")
    public void setFontFamily(@Nullable String str) {
        this.mFontFamily = str;
        markUpdated();
    }

    @ReactProp(name = "fontWeight")
    public void setFontWeight(@Nullable String str) {
        int parseNumericFontWeight = str != null ? parseNumericFontWeight(str) : -1;
        int i = 0;
        if (parseNumericFontWeight == -1) {
            parseNumericFontWeight = 0;
        }
        if (parseNumericFontWeight == 700 || "bold".equals(str)) {
            i = 1;
        } else if (!(parseNumericFontWeight == 400 || "normal".equals(str))) {
            i = parseNumericFontWeight;
        }
        if (i != this.mFontWeight) {
            this.mFontWeight = i;
            markUpdated();
        }
    }

    @ReactProp(name = "fontStyle")
    public void setFontStyle(@Nullable String str) {
        int i = "italic".equals(str) ? 2 : "normal".equals(str) ? 0 : -1;
        if (i != this.mFontStyle) {
            this.mFontStyle = i;
            markUpdated();
        }
    }

    @ReactProp(defaultBoolean = true, name = "includeFontPadding")
    public void setIncludeFontPadding(boolean z) {
        this.mIncludeFontPadding = z;
    }

    @ReactProp(name = "textDecorationLine")
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
        markUpdated();
    }

    @ReactProp(name = "textBreakStrategy")
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
            markUpdated();
        }
    }

    @ReactProp(name = "textShadowOffset")
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
        markUpdated();
    }

    @ReactProp(defaultInt = 1, name = "textShadowRadius")
    public void setTextShadowRadius(float f) {
        if (f != this.mTextShadowRadius) {
            this.mTextShadowRadius = f;
            markUpdated();
        }
    }

    @ReactProp(customType = "Color", defaultInt = 1426063360, name = "textShadowColor")
    public void setTextShadowColor(int i) {
        if (i != this.mTextShadowColor) {
            this.mTextShadowColor = i;
            markUpdated();
        }
    }

    @ReactProp(name = "textTransform")
    public void setTextTransform(@Nullable String str) {
        if (str == null) {
            this.mTextAttributes.setTextTransform(TextTransform.UNSET);
        } else if (ViewProps.NONE.equals(str)) {
            this.mTextAttributes.setTextTransform(TextTransform.NONE);
        } else if ("uppercase".equals(str)) {
            this.mTextAttributes.setTextTransform(TextTransform.UPPERCASE);
        } else if ("lowercase".equals(str)) {
            this.mTextAttributes.setTextTransform(TextTransform.LOWERCASE);
        } else if ("capitalize".equals(str)) {
            this.mTextAttributes.setTextTransform(TextTransform.CAPITALIZE);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid textTransform: ");
            stringBuilder.append(str);
            throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
        }
        markUpdated();
    }
}
