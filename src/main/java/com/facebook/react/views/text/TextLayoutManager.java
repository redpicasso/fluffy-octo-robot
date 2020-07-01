package com.facebook.react.views.text;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.BoringLayout;
import android.text.BoringLayout.Metrics;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.StaticLayout.Builder;
import android.text.TextPaint;
import android.util.LruCache;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.yoga.YogaConstants;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import java.util.ArrayList;
import java.util.List;

public class TextLayoutManager {
    private static LruCache<String, Spannable> sSpannableCache = new LruCache(100);
    private static final Object sSpannableCacheLock = new Object();
    private static final TextPaint sTextPaintInstance = new TextPaint(1);
    private static final int spannableCacheSize = 100;

    public static class SetSpanOperation {
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

    private static void buildSpannableFromFragment(Context context, ReadableArray readableArray, SpannableStringBuilder spannableStringBuilder, List<SetSpanOperation> list) {
        int size = readableArray.size();
        for (int i = 0; i < size; i++) {
            ReadableMap map = readableArray.getMap(i);
            int length = spannableStringBuilder.length();
            TextAttributeProps textAttributeProps = new TextAttributeProps(new ReactStylesDiffMap(map.getMap("textAttributes")));
            spannableStringBuilder.append(TextTransform.apply(map.getString("string"), textAttributeProps.mTextTransform));
            int length2 = spannableStringBuilder.length();
            if (length2 >= length) {
                if (textAttributeProps.mIsColorSet) {
                    list.add(new SetSpanOperation(length, length2, new ReactForegroundColorSpan(textAttributeProps.mColor)));
                }
                if (textAttributeProps.mIsBackgroundColorSet) {
                    list.add(new SetSpanOperation(length, length2, new ReactBackgroundColorSpan(textAttributeProps.mBackgroundColor)));
                }
                if (VERSION.SDK_INT >= 21 && !Float.isNaN(textAttributeProps.mLetterSpacing)) {
                    list.add(new SetSpanOperation(length, length2, new CustomLetterSpacingSpan(textAttributeProps.mLetterSpacing)));
                }
                list.add(new SetSpanOperation(length, length2, new ReactAbsoluteSizeSpan(textAttributeProps.mFontSize)));
                if (!(textAttributeProps.mFontStyle == -1 && textAttributeProps.mFontWeight == -1 && textAttributeProps.mFontFamily == null)) {
                    list.add(new SetSpanOperation(length, length2, new CustomStyleSpan(textAttributeProps.mFontStyle, textAttributeProps.mFontWeight, textAttributeProps.mFontFamily, context.getAssets())));
                }
                if (textAttributeProps.mIsUnderlineTextDecorationSet) {
                    list.add(new SetSpanOperation(length, length2, new ReactUnderlineSpan()));
                }
                if (textAttributeProps.mIsLineThroughTextDecorationSet) {
                    list.add(new SetSpanOperation(length, length2, new ReactStrikethroughSpan()));
                }
                if (!(textAttributeProps.mTextShadowOffsetDx == 0.0f && textAttributeProps.mTextShadowOffsetDy == 0.0f)) {
                    list.add(new SetSpanOperation(length, length2, new ShadowStyleSpan(textAttributeProps.mTextShadowOffsetDx, textAttributeProps.mTextShadowOffsetDy, textAttributeProps.mTextShadowRadius, textAttributeProps.mTextShadowColor)));
                }
                if (!Float.isNaN(textAttributeProps.getEffectiveLineHeight())) {
                    list.add(new SetSpanOperation(length, length2, new CustomLineHeightSpan(textAttributeProps.getEffectiveLineHeight())));
                }
                list.add(new SetSpanOperation(length, length2, new ReactTagSpan(map.getInt("reactTag"))));
            }
        }
    }

    protected static Spannable getOrCreateSpannableForText(Context context, ReadableMap readableMap) {
        String obj = readableMap.toString();
        synchronized (sSpannableCacheLock) {
            Spannable spannable = (Spannable) sSpannableCache.get(obj);
            if (spannable != null) {
                return spannable;
            }
            Spannable createSpannableFromAttributedString = createSpannableFromAttributedString(context, readableMap);
            synchronized (sSpannableCacheLock) {
                sSpannableCache.put(obj, createSpannableFromAttributedString);
            }
            return createSpannableFromAttributedString;
        }
    }

    private static Spannable createSpannableFromAttributedString(Context context, ReadableMap readableMap) {
        Spannable spannableStringBuilder = new SpannableStringBuilder();
        List<SetSpanOperation> arrayList = new ArrayList();
        buildSpannableFromFragment(context, readableMap.getArray("fragments"), spannableStringBuilder, arrayList);
        int i = 0;
        for (SetSpanOperation execute : arrayList) {
            execute.execute(spannableStringBuilder, i);
            i++;
        }
        return spannableStringBuilder;
    }

    public static long measureText(Context context, ReadableMap readableMap, ReadableMap readableMap2, float f, YogaMeasureMode yogaMeasureMode, float f2, YogaMeasureMode yogaMeasureMode2) {
        TextPaint textPaint = sTextPaintInstance;
        CharSequence orCreateSpannableForText = getOrCreateSpannableForText(context, readableMap);
        if (orCreateSpannableForText != null) {
            Layout build;
            int height;
            Metrics isBoring = BoringLayout.isBoring(orCreateSpannableForText, textPaint);
            float desiredWidth = isBoring == null ? Layout.getDesiredWidth(orCreateSpannableForText, textPaint) : Float.NaN;
            Object obj = (yogaMeasureMode == YogaMeasureMode.UNDEFINED || f < 0.0f) ? 1 : null;
            Layout staticLayout;
            if (isBoring == null && (obj != null || (!YogaConstants.isUndefined(desiredWidth) && desiredWidth <= f))) {
                int ceil = (int) Math.ceil((double) desiredWidth);
                if (VERSION.SDK_INT < 23) {
                    staticLayout = new StaticLayout(orCreateSpannableForText, textPaint, ceil, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
                } else {
                    build = Builder.obtain(orCreateSpannableForText, 0, orCreateSpannableForText.length(), textPaint, ceil).setAlignment(Alignment.ALIGN_NORMAL).setLineSpacing(0.0f, 1.0f).setIncludePad(true).setBreakStrategy(1).setHyphenationFrequency(1).build();
                }
            } else if (isBoring != null && (obj != null || ((float) isBoring.width) <= f)) {
                build = BoringLayout.make(orCreateSpannableForText, textPaint, isBoring.width, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, isBoring, true);
            } else if (VERSION.SDK_INT < 23) {
                staticLayout = new StaticLayout(orCreateSpannableForText, textPaint, (int) f, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
            } else {
                build = Builder.obtain(orCreateSpannableForText, 0, orCreateSpannableForText.length(), textPaint, (int) f).setAlignment(Alignment.ALIGN_NORMAL).setLineSpacing(0.0f, 1.0f).setIncludePad(true).setBreakStrategy(1).setHyphenationFrequency(1).build();
            }
            String str = "maximumNumberOfLines";
            int i = readableMap2.hasKey(str) ? readableMap2.getInt(str) : -1;
            float width = (float) build.getWidth();
            if (i == -1 || i == 0 || i >= build.getLineCount()) {
                height = build.getHeight();
            } else {
                height = build.getLineBottom(i - 1);
            }
            return YogaMeasureOutput.make(PixelUtil.toSPFromPixel(width), PixelUtil.toSPFromPixel((float) height));
        }
        throw new IllegalStateException("Spannable element has not been prepared in onBeforeLayout");
    }
}
