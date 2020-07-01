package com.facebook.react.views.text;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.text.BoringLayout;
import android.text.BoringLayout.Metrics;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.StaticLayout.Builder;
import android.text.TextPaint;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.NativeViewHierarchyOptimizer;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.yoga.YogaConstants;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNode;
import java.util.ArrayList;
import javax.annotation.Nullable;

@TargetApi(23)
public class ReactTextShadowNode extends ReactBaseTextShadowNode {
    private static final TextPaint sTextPaintInstance = new TextPaint(1);
    @Nullable
    private Spannable mPreparedSpannableText;
    private boolean mShouldNotifyOnTextLayout;
    private final YogaMeasureFunction mTextMeasureFunction = new YogaMeasureFunction() {
        public long measure(YogaNode yogaNode, float f, YogaMeasureMode yogaMeasureMode, float f2, YogaMeasureMode yogaMeasureMode2) {
            Layout build;
            TextPaint access$000 = ReactTextShadowNode.sTextPaintInstance;
            access$000.setTextSize((float) ReactTextShadowNode.this.mTextAttributes.getEffectiveFontSize());
            Spanned spanned = (Spanned) Assertions.assertNotNull(ReactTextShadowNode.this.mPreparedSpannableText, "Spannable element has not been prepared in onBeforeLayout");
            Metrics isBoring = BoringLayout.isBoring(spanned, access$000);
            f2 = isBoring == null ? Layout.getDesiredWidth(spanned, access$000) : Float.NaN;
            Object obj = (yogaMeasureMode == YogaMeasureMode.UNDEFINED || f < 0.0f) ? 1 : null;
            Alignment alignment = Alignment.ALIGN_NORMAL;
            int access$200 = ReactTextShadowNode.this.getTextAlign();
            if (access$200 == 1) {
                alignment = Alignment.ALIGN_CENTER;
            } else if (access$200 == 3) {
                alignment = Alignment.ALIGN_NORMAL;
            } else if (access$200 == 5) {
                alignment = Alignment.ALIGN_OPPOSITE;
            }
            Alignment alignment2 = alignment;
            Layout staticLayout;
            Builder hyphenationFrequency;
            if (isBoring == null && (obj != null || (!YogaConstants.isUndefined(f2) && f2 <= f))) {
                int ceil = (int) Math.ceil((double) f2);
                if (VERSION.SDK_INT < 23) {
                    staticLayout = new StaticLayout(spanned, access$000, ceil, alignment2, 1.0f, 0.0f, ReactTextShadowNode.this.mIncludeFontPadding);
                } else {
                    hyphenationFrequency = Builder.obtain(spanned, 0, spanned.length(), access$000, ceil).setAlignment(alignment2).setLineSpacing(0.0f, 1.0f).setIncludePad(ReactTextShadowNode.this.mIncludeFontPadding).setBreakStrategy(ReactTextShadowNode.this.mTextBreakStrategy).setHyphenationFrequency(1);
                    if (VERSION.SDK_INT >= 26) {
                        hyphenationFrequency.setJustificationMode(ReactTextShadowNode.this.mJustificationMode);
                    }
                    if (VERSION.SDK_INT >= 28) {
                        hyphenationFrequency.setUseLineSpacingFromFallbacks(true);
                    }
                    build = hyphenationFrequency.build();
                }
            } else if (isBoring != null && (obj != null || ((float) isBoring.width) <= f)) {
                build = BoringLayout.make(spanned, access$000, isBoring.width, alignment2, 1.0f, 0.0f, isBoring, ReactTextShadowNode.this.mIncludeFontPadding);
            } else if (VERSION.SDK_INT < 23) {
                staticLayout = new StaticLayout(spanned, access$000, (int) f, alignment2, 1.0f, 0.0f, ReactTextShadowNode.this.mIncludeFontPadding);
            } else {
                hyphenationFrequency = Builder.obtain(spanned, 0, spanned.length(), access$000, (int) f).setAlignment(alignment2).setLineSpacing(0.0f, 1.0f).setIncludePad(ReactTextShadowNode.this.mIncludeFontPadding).setBreakStrategy(ReactTextShadowNode.this.mTextBreakStrategy).setHyphenationFrequency(1);
                if (VERSION.SDK_INT >= 28) {
                    hyphenationFrequency.setUseLineSpacingFromFallbacks(true);
                }
                build = hyphenationFrequency.build();
            }
            if (ReactTextShadowNode.this.mShouldNotifyOnTextLayout) {
                WritableArray fontMetrics = FontMetricsUtil.getFontMetrics(spanned, build, ReactTextShadowNode.sTextPaintInstance, ReactTextShadowNode.this.getThemedContext());
                WritableMap createMap = Arguments.createMap();
                createMap.putArray("lines", fontMetrics);
                ((RCTEventEmitter) ReactTextShadowNode.this.getThemedContext().getJSModule(RCTEventEmitter.class)).receiveEvent(ReactTextShadowNode.this.getReactTag(), "topTextLayout", createMap);
            }
            if (ReactTextShadowNode.this.mNumberOfLines == -1 || ReactTextShadowNode.this.mNumberOfLines >= build.getLineCount()) {
                return YogaMeasureOutput.make(build.getWidth(), build.getHeight());
            }
            return YogaMeasureOutput.make(build.getWidth(), build.getLineBottom(ReactTextShadowNode.this.mNumberOfLines - 1));
        }
    };

    public boolean hoistNativeChildren() {
        return true;
    }

    public boolean isVirtualAnchor() {
        return false;
    }

    public ReactTextShadowNode() {
        initMeasureFunction();
    }

    private void initMeasureFunction() {
        if (!isVirtual()) {
            setMeasureFunction(this.mTextMeasureFunction);
        }
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

    public void onBeforeLayout(NativeViewHierarchyOptimizer nativeViewHierarchyOptimizer) {
        this.mPreparedSpannableText = ReactBaseTextShadowNode.spannedFromShadowNode(this, null, true, nativeViewHierarchyOptimizer);
        markUpdated();
    }

    public void markUpdated() {
        super.markUpdated();
        super.dirty();
    }

    public void onCollectExtraUpdates(UIViewOperationQueue uIViewOperationQueue) {
        super.onCollectExtraUpdates(uIViewOperationQueue);
        Spannable spannable = this.mPreparedSpannableText;
        if (spannable != null) {
            uIViewOperationQueue.enqueueUpdateExtraData(getReactTag(), new ReactTextUpdate(spannable, -1, this.mContainsImages, getPadding(4), getPadding(1), getPadding(5), getPadding(3), getTextAlign(), this.mTextBreakStrategy, this.mJustificationMode));
        }
    }

    @ReactProp(name = "onTextLayout")
    public void setShouldNotifyOnTextLayout(boolean z) {
        this.mShouldNotifyOnTextLayout = z;
    }

    public Iterable<? extends ReactShadowNode> calculateLayoutOnChildren() {
        if (this.mInlineViews == null || this.mInlineViews.isEmpty()) {
            return null;
        }
        Spanned spanned = (Spanned) Assertions.assertNotNull(this.mPreparedSpannableText, "Spannable element has not been prepared in onBeforeLayout");
        int i = 0;
        TextInlineViewPlaceholderSpan[] textInlineViewPlaceholderSpanArr = (TextInlineViewPlaceholderSpan[]) spanned.getSpans(0, spanned.length(), TextInlineViewPlaceholderSpan.class);
        Iterable arrayList = new ArrayList(textInlineViewPlaceholderSpanArr.length);
        int length = textInlineViewPlaceholderSpanArr.length;
        while (i < length) {
            ReactShadowNode reactShadowNode = (ReactShadowNode) this.mInlineViews.get(Integer.valueOf(textInlineViewPlaceholderSpanArr[i].getReactTag()));
            reactShadowNode.calculateLayout();
            arrayList.add(reactShadowNode);
            i++;
        }
        return arrayList;
    }
}
