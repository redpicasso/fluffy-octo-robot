package com.horcrux.svg;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.SpannableString;
import android.text.StaticLayout;
import android.text.StaticLayout.Builder;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewParent;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import java.util.ArrayList;
import javax.annotation.Nullable;

@SuppressLint({"ViewConstructor"})
class TSpanView extends TextView {
    private static final String FONTS = "fonts/";
    private static final String OTF = ".otf";
    private static final String TTF = ".ttf";
    static final String additionalLigatures = "'hlig', 'cala', ";
    static final String defaultFeatures = "'rlig', 'liga', 'clig', 'calt', 'locl', 'ccmp', 'mark', 'mkmk','kern', ";
    static final String disableDiscretionaryLigatures = "'liga' 0, 'clig' 0, 'dlig' 0, 'hlig' 0, 'cala' 0, ";
    static final String fontWeightTag = "'wght' ";
    private static final double radToDeg = 57.29577951308232d;
    static final String requiredFontFeatures = "'rlig', 'liga', 'clig', 'calt', 'locl', 'ccmp', 'mark', 'mkmk',";
    private static final double tau = 6.283185307179586d;
    private final AssetManager assets = this.mContext.getResources().getAssets();
    private final ArrayList<String> emoji = new ArrayList();
    private final ArrayList<Matrix> emojiTransforms = new ArrayList();
    private Path mCachedPath;
    @Nullable
    String mContent;
    private TextPathView textPath;

    /* renamed from: com.horcrux.svg.TSpanView$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor = new int[TextAnchor.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$horcrux$svg$TextProperties$TextLengthAdjust = new int[TextLengthAdjust.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:34:?, code:
            $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline[com.horcrux.svg.TextProperties.AlignmentBaseline.top.ordinal()] = 16;
     */
        /* JADX WARNING: Missing block: B:39:?, code:
            $SwitchMap$com$horcrux$svg$TextProperties$TextLengthAdjust[com.horcrux.svg.TextProperties.TextLengthAdjust.spacingAndGlyphs.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:46:?, code:
            $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor[com.horcrux.svg.TextProperties.TextAnchor.end.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.horcrux.svg.TextProperties.AlignmentBaseline.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline = r0;
            r0 = 1;
            r1 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = com.horcrux.svg.TextProperties.AlignmentBaseline.baseline;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = r2.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1[r2] = r0;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r1 = 2;
            r2 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = com.horcrux.svg.TextProperties.AlignmentBaseline.textBottom;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2[r3] = r1;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r2 = 3;
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;	 Catch:{ NoSuchFieldError -> 0x002a }
            r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.afterEdge;	 Catch:{ NoSuchFieldError -> 0x002a }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r3[r4] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.textAfterEdge;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r5 = 4;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.alphabetic;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x0040 }
            r5 = 5;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x0040 }
        L_0x0040:
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;	 Catch:{ NoSuchFieldError -> 0x004b }
            r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.ideographic;	 Catch:{ NoSuchFieldError -> 0x004b }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x004b }
            r5 = 6;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x004b }
        L_0x004b:
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;	 Catch:{ NoSuchFieldError -> 0x0056 }
            r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.middle;	 Catch:{ NoSuchFieldError -> 0x0056 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x0056 }
            r5 = 7;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x0056 }
        L_0x0056:
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;	 Catch:{ NoSuchFieldError -> 0x0062 }
            r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.central;	 Catch:{ NoSuchFieldError -> 0x0062 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x0062 }
            r5 = 8;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x0062 }
        L_0x0062:
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;	 Catch:{ NoSuchFieldError -> 0x006e }
            r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.mathematical;	 Catch:{ NoSuchFieldError -> 0x006e }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x006e }
            r5 = 9;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x006e }
        L_0x006e:
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;	 Catch:{ NoSuchFieldError -> 0x007a }
            r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.hanging;	 Catch:{ NoSuchFieldError -> 0x007a }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x007a }
            r5 = 10;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x007a }
        L_0x007a:
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;	 Catch:{ NoSuchFieldError -> 0x0086 }
            r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.textTop;	 Catch:{ NoSuchFieldError -> 0x0086 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x0086 }
            r5 = 11;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x0086 }
        L_0x0086:
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;	 Catch:{ NoSuchFieldError -> 0x0092 }
            r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.beforeEdge;	 Catch:{ NoSuchFieldError -> 0x0092 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x0092 }
            r5 = 12;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x0092 }
        L_0x0092:
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;	 Catch:{ NoSuchFieldError -> 0x009e }
            r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.textBeforeEdge;	 Catch:{ NoSuchFieldError -> 0x009e }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x009e }
            r5 = 13;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x009e }
        L_0x009e:
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;	 Catch:{ NoSuchFieldError -> 0x00aa }
            r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.bottom;	 Catch:{ NoSuchFieldError -> 0x00aa }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x00aa }
            r5 = 14;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x00aa }
        L_0x00aa:
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;	 Catch:{ NoSuchFieldError -> 0x00b6 }
            r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.center;	 Catch:{ NoSuchFieldError -> 0x00b6 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x00b6 }
            r5 = 15;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x00b6 }
        L_0x00b6:
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;	 Catch:{ NoSuchFieldError -> 0x00c2 }
            r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.top;	 Catch:{ NoSuchFieldError -> 0x00c2 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x00c2 }
            r5 = 16;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x00c2 }
        L_0x00c2:
            r3 = com.horcrux.svg.TextProperties.TextLengthAdjust.values();
            r3 = r3.length;
            r3 = new int[r3];
            $SwitchMap$com$horcrux$svg$TextProperties$TextLengthAdjust = r3;
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$TextLengthAdjust;	 Catch:{ NoSuchFieldError -> 0x00d5 }
            r4 = com.horcrux.svg.TextProperties.TextLengthAdjust.spacing;	 Catch:{ NoSuchFieldError -> 0x00d5 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x00d5 }
            r3[r4] = r0;	 Catch:{ NoSuchFieldError -> 0x00d5 }
        L_0x00d5:
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$TextLengthAdjust;	 Catch:{ NoSuchFieldError -> 0x00df }
            r4 = com.horcrux.svg.TextProperties.TextLengthAdjust.spacingAndGlyphs;	 Catch:{ NoSuchFieldError -> 0x00df }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x00df }
            r3[r4] = r1;	 Catch:{ NoSuchFieldError -> 0x00df }
        L_0x00df:
            r3 = com.horcrux.svg.TextProperties.TextAnchor.values();
            r3 = r3.length;
            r3 = new int[r3];
            $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor = r3;
            r3 = $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor;	 Catch:{ NoSuchFieldError -> 0x00f2 }
            r4 = com.horcrux.svg.TextProperties.TextAnchor.start;	 Catch:{ NoSuchFieldError -> 0x00f2 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x00f2 }
            r3[r4] = r0;	 Catch:{ NoSuchFieldError -> 0x00f2 }
        L_0x00f2:
            r0 = $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor;	 Catch:{ NoSuchFieldError -> 0x00fc }
            r3 = com.horcrux.svg.TextProperties.TextAnchor.middle;	 Catch:{ NoSuchFieldError -> 0x00fc }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x00fc }
            r0[r3] = r1;	 Catch:{ NoSuchFieldError -> 0x00fc }
        L_0x00fc:
            r0 = $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor;	 Catch:{ NoSuchFieldError -> 0x0106 }
            r1 = com.horcrux.svg.TextProperties.TextAnchor.end;	 Catch:{ NoSuchFieldError -> 0x0106 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0106 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0106 }
        L_0x0106:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.TSpanView.1.<clinit>():void");
        }
    }

    public TSpanView(ReactContext reactContext) {
        super(reactContext);
    }

    @ReactProp(name = "content")
    public void setContent(@Nullable String str) {
        this.mContent = str;
        invalidate();
    }

    public void invalidate() {
        this.mCachedPath = null;
        super.invalidate();
    }

    void clearCache() {
        this.mCachedPath = null;
        super.clearCache();
    }

    void draw(Canvas canvas, Paint paint, float f) {
        if (this.mContent == null) {
            clip(canvas, paint);
            drawGroup(canvas, paint, f);
        } else if (this.mInlineSize == null || this.mInlineSize.value == 0.0d) {
            int size = this.emoji.size();
            if (size > 0) {
                applyTextPropertiesToPaint(paint, getTextRootGlyphContext().getFont());
                for (int i = 0; i < size; i++) {
                    String str = (String) this.emoji.get(i);
                    Matrix matrix = (Matrix) this.emojiTransforms.get(i);
                    canvas.save();
                    canvas.concat(matrix);
                    canvas.drawText(str, 0.0f, 0.0f, paint);
                    canvas.restore();
                }
            }
            drawPath(canvas, paint, f);
        } else {
            drawWrappedText(canvas, paint);
        }
    }

    private void drawWrappedText(Canvas canvas, Paint paint) {
        Alignment alignment;
        StaticLayout build;
        Canvas canvas2 = canvas;
        GlyphContext textRootGlyphContext = getTextRootGlyphContext();
        pushGlyphContext();
        FontData font = textRootGlyphContext.getFont();
        Paint textPaint = new TextPaint(paint);
        applyTextPropertiesToPaint(textPaint, font);
        applySpacingAndFeatuers(textPaint, font);
        double fontSize = textRootGlyphContext.getFontSize();
        int i = AnonymousClass1.$SwitchMap$com$horcrux$svg$TextProperties$TextAnchor[font.textAnchor.ordinal()];
        if (i == 2) {
            alignment = Alignment.ALIGN_CENTER;
        } else if (i != 3) {
            alignment = Alignment.ALIGN_NORMAL;
        } else {
            alignment = Alignment.ALIGN_OPPOSITE;
        }
        CharSequence spannableString = new SpannableString(this.mContent);
        double fromRelative = PropHelper.fromRelative(this.mInlineSize, (double) canvas.getWidth(), 0.0d, (double) this.mScale, fontSize);
        if (VERSION.SDK_INT < 23) {
            StaticLayout staticLayout = new StaticLayout(spannableString, textPaint, (int) fromRelative, alignment, 1.0f, 0.0f, true);
        } else {
            build = Builder.obtain(spannableString, 0, spannableString.length(), textPaint, (int) fromRelative).setAlignment(alignment).setLineSpacing(0.0f, 1.0f).setIncludePad(true).setBreakStrategy(1).setHyphenationFrequency(1).build();
        }
        float nextX = (float) textRootGlyphContext.nextX(0.0d);
        float nextY = (float) (textRootGlyphContext.nextY() + ((double) build.getLineAscent(0)));
        popGlyphContext();
        canvas.save();
        canvas2.translate(nextX, nextY);
        build.draw(canvas2);
        canvas.restore();
    }

    Path getPath(Canvas canvas, Paint paint) {
        Path path = this.mCachedPath;
        if (path != null) {
            return path;
        }
        if (this.mContent == null) {
            this.mCachedPath = getGroupPath(canvas, paint);
            return this.mCachedPath;
        }
        setupTextPath();
        pushGlyphContext();
        this.mCachedPath = getLinePath(this.mContent, paint, canvas);
        popGlyphContext();
        return this.mCachedPath;
    }

    double getSubtreeTextChunksTotalAdvance(Paint paint) {
        if (!Double.isNaN(this.cachedAdvance)) {
            return this.cachedAdvance;
        }
        String str = this.mContent;
        double d = 0.0d;
        if (str == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (childAt instanceof TextView) {
                    d += ((TextView) childAt).getSubtreeTextChunksTotalAdvance(paint);
                }
            }
            this.cachedAdvance = d;
            return d;
        } else if (str.length() == 0) {
            this.cachedAdvance = 0.0d;
            return 0.0d;
        } else {
            FontData font = getTextRootGlyphContext().getFont();
            applyTextPropertiesToPaint(paint, font);
            applySpacingAndFeatuers(paint, font);
            this.cachedAdvance = (double) paint.measureText(str);
            return this.cachedAdvance;
        }
    }

    private void applySpacingAndFeatuers(Paint paint, FontData fontData) {
        if (VERSION.SDK_INT >= 21) {
            StringBuilder stringBuilder;
            double d = fontData.letterSpacing;
            paint.setLetterSpacing((float) (d / (fontData.fontSize * ((double) this.mScale))));
            Object obj = (d == 0.0d && fontData.fontVariantLigatures == FontVariantLigatures.normal) ? 1 : null;
            if (obj != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("'rlig', 'liga', 'clig', 'calt', 'locl', 'ccmp', 'mark', 'mkmk','kern', 'hlig', 'cala', ");
                stringBuilder.append(fontData.fontFeatureSettings);
                paint.setFontFeatureSettings(stringBuilder.toString());
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("'rlig', 'liga', 'clig', 'calt', 'locl', 'ccmp', 'mark', 'mkmk','kern', 'liga' 0, 'clig' 0, 'dlig' 0, 'hlig' 0, 'cala' 0, ");
                stringBuilder.append(fontData.fontFeatureSettings);
                paint.setFontFeatureSettings(stringBuilder.toString());
            }
            if (VERSION.SDK_INT >= 26) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(fontWeightTag);
                stringBuilder.append(fontData.absoluteFontWeight);
                stringBuilder.append(fontData.fontVariationSettings);
                paint.setFontVariationSettings(stringBuilder.toString());
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:114:0x02ba A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0269  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0269  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x02ba A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x02ba A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0269  */
    /* JADX WARNING: Missing block: B:68:0x01f4, code:
            r0 = r0 * r2;
     */
    private android.graphics.Path getLinePath(java.lang.String r73, android.graphics.Paint r74, android.graphics.Canvas r75) {
        /*
        r72 = this;
        r6 = r72;
        r14 = r74;
        r15 = r75;
        r13 = r73.length();
        r12 = new android.graphics.Path;
        r12.<init>();
        r0 = r6.emoji;
        r0.clear();
        r0 = r6.emojiTransforms;
        r0.clear();
        if (r13 != 0) goto L_0x001c;
    L_0x001b:
        return r12;
    L_0x001c:
        r0 = 0;
        r1 = r6.textPath;
        r11 = 0;
        if (r1 == 0) goto L_0x0025;
    L_0x0022:
        r16 = 1;
        goto L_0x0027;
    L_0x0025:
        r16 = 0;
    L_0x0027:
        r17 = 0;
        if (r16 == 0) goto L_0x0048;
    L_0x002b:
        r0 = new android.graphics.PathMeasure;
        r1 = r6.textPath;
        r1 = r1.getTextPath(r15, r14);
        r0.<init>(r1, r11);
        r1 = r0.getLength();
        r1 = (double) r1;
        r3 = r0.isClosed();
        r4 = (r1 > r17 ? 1 : (r1 == r17 ? 0 : -1));
        if (r4 != 0) goto L_0x0044;
    L_0x0043:
        return r12;
    L_0x0044:
        r4 = r0;
        r8 = r1;
        r7 = r3;
        goto L_0x004c;
    L_0x0048:
        r4 = r0;
        r8 = r17;
        r7 = 0;
    L_0x004c:
        r5 = r72.getTextRootGlyphContext();
        r0 = r5.getFont();
        r6.applyTextPropertiesToPaint(r14, r0);
        r2 = new com.horcrux.svg.GlyphPathBag;
        r2.<init>(r14);
        r3 = new boolean[r13];
        r19 = r73.toCharArray();
        r20 = r12;
        r11 = r0.kerning;
        r22 = r11;
        r11 = r0.wordSpacing;
        r25 = r11;
        r10 = r0.letterSpacing;
        r1 = r0.manualKerning;
        r12 = 1;
        r27 = r1 ^ 1;
        r1 = (r10 > r17 ? 1 : (r10 == r17 ? 0 : -1));
        if (r1 != 0) goto L_0x007f;
    L_0x0077:
        r1 = r0.fontVariantLigatures;
        r12 = com.horcrux.svg.TextProperties.FontVariantLigatures.normal;
        if (r1 != r12) goto L_0x007f;
    L_0x007d:
        r1 = 1;
        goto L_0x0080;
    L_0x007f:
        r1 = 0;
    L_0x0080:
        r12 = android.os.Build.VERSION.SDK_INT;
        r28 = r2;
        r2 = 21;
        if (r12 < r2) goto L_0x00d8;
    L_0x0088:
        if (r1 == 0) goto L_0x00a1;
    L_0x008a:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "'rlig', 'liga', 'clig', 'calt', 'locl', 'ccmp', 'mark', 'mkmk','kern', 'hlig', 'cala', ";
        r1.append(r2);
        r2 = r0.fontFeatureSettings;
        r1.append(r2);
        r1 = r1.toString();
        r14.setFontFeatureSettings(r1);
        goto L_0x00b7;
    L_0x00a1:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "'rlig', 'liga', 'clig', 'calt', 'locl', 'ccmp', 'mark', 'mkmk','kern', 'liga' 0, 'clig' 0, 'dlig' 0, 'hlig' 0, 'cala' 0, ";
        r1.append(r2);
        r2 = r0.fontFeatureSettings;
        r1.append(r2);
        r1 = r1.toString();
        r14.setFontFeatureSettings(r1);
    L_0x00b7:
        r1 = android.os.Build.VERSION.SDK_INT;
        r2 = 26;
        if (r1 < r2) goto L_0x00d8;
    L_0x00bd:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "'wght' ";
        r1.append(r2);
        r2 = r0.absoluteFontWeight;
        r1.append(r2);
        r2 = r0.fontVariationSettings;
        r1.append(r2);
        r1 = r1.toString();
        r14.setFontVariationSettings(r1);
    L_0x00d8:
        r12 = r0.fontData;
        r2 = new float[r13];
        r1 = r73;
        r14.getTextWidths(r1, r2);
        r1 = r0.textAnchor;
        r0 = r72.getTextAnchorRoot();
        r29 = r10;
        r10 = r0.getSubtreeTextChunksTotalAdvance(r14);
        r31 = r6.getTextAnchorOffset(r1, r10);
        r42 = r5.getFontSize();
        r44 = -1;
        r45 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        if (r16 == 0) goto L_0x0151;
    L_0x00fb:
        r0 = r6.textPath;
        r0 = r0.getMidLine();
        r33 = r1;
        r1 = com.horcrux.svg.TextProperties.TextPathMidLine.sharp;
        if (r0 != r1) goto L_0x010a;
    L_0x0107:
        r34 = 1;
        goto L_0x010c;
    L_0x010a:
        r34 = 0;
    L_0x010c:
        r0 = r6.textPath;
        r0 = r0.getSide();
        r1 = com.horcrux.svg.TextProperties.TextPathSide.right;
        if (r0 != r1) goto L_0x0119;
    L_0x0116:
        r35 = -1;
        goto L_0x011b;
    L_0x0119:
        r35 = 1;
    L_0x011b:
        r0 = r6.textPath;
        r1 = r0.getStartOffset();
        r0 = r72;
        r15 = r33;
        r48 = r2;
        r47 = r28;
        r28 = r3;
        r2 = r8;
        r50 = r4;
        r49 = r5;
        r4 = r42;
        r0 = r0.getAbsoluteStartOffset(r1, r2, r4);
        r31 = r31 + r0;
        if (r7 == 0) goto L_0x0149;
    L_0x013a:
        r2 = r8 / r45;
        r4 = com.horcrux.svg.TextProperties.TextAnchor.middle;
        if (r15 != r4) goto L_0x0142;
    L_0x0140:
        r2 = -r2;
        goto L_0x0144;
    L_0x0142:
        r2 = r17;
    L_0x0144:
        r0 = r0 + r2;
        r2 = r0 + r8;
        r4 = r0;
        goto L_0x014c;
    L_0x0149:
        r2 = r8;
        r4 = r17;
    L_0x014c:
        r1 = r34;
        r0 = r35;
        goto L_0x0160;
    L_0x0151:
        r48 = r2;
        r50 = r4;
        r49 = r5;
        r47 = r28;
        r28 = r3;
        r2 = r8;
        r4 = r17;
        r0 = 1;
        r1 = 0;
    L_0x0160:
        r51 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r7 = r6.mTextLength;
        if (r7 == 0) goto L_0x01a8;
    L_0x0166:
        r7 = r6.mTextLength;
        r15 = r75.getWidth();
        r53 = r8;
        r8 = (double) r15;
        r36 = 0;
        r15 = r6.mScale;
        r55 = r4;
        r4 = (double) r15;
        r33 = r7;
        r34 = r8;
        r38 = r4;
        r40 = r42;
        r4 = com.horcrux.svg.PropHelper.fromRelative(r33, r34, r36, r38, r40);
        r7 = (r4 > r17 ? 1 : (r4 == r17 ? 0 : -1));
        if (r7 < 0) goto L_0x01a0;
    L_0x0186:
        r7 = com.horcrux.svg.TSpanView.AnonymousClass1.$SwitchMap$com$horcrux$svg$TextProperties$TextLengthAdjust;
        r8 = r6.mLengthAdjust;
        r8 = r8.ordinal();
        r7 = r7[r8];
        r8 = 2;
        if (r7 == r8) goto L_0x019d;
    L_0x0193:
        r4 = r4 - r10;
        r7 = r13 + -1;
        r7 = (double) r7;
        r4 = r4 / r7;
        r10 = r29 + r4;
        r29 = r10;
        goto L_0x01ac;
    L_0x019d:
        r51 = r4 / r10;
        goto L_0x01ac;
    L_0x01a0:
        r0 = new java.lang.IllegalArgumentException;
        r1 = "Negative textLength value";
        r0.<init>(r1);
        throw r0;
    L_0x01a8:
        r55 = r4;
        r53 = r8;
    L_0x01ac:
        r4 = (double) r0;
        r10 = r51 * r4;
        r7 = r74.getFontMetrics();
        r8 = r7.descent;
        r8 = (double) r8;
        r15 = r7.leading;
        r40 = r10;
        r10 = (double) r15;
        r10 = r10 + r8;
        r15 = r7.ascent;
        r15 = -r15;
        r57 = r0;
        r0 = r7.leading;
        r15 = r15 + r0;
        r58 = r1;
        r0 = (double) r15;
        r7 = r7.top;
        r7 = -r7;
        r59 = r2;
        r2 = (double) r7;
        r33 = r2 + r10;
        r7 = r72.getBaselineShift();
        r15 = r72.getAlignmentBaseline();
        if (r15 == 0) goto L_0x0213;
    L_0x01d9:
        r35 = com.horcrux.svg.TSpanView.AnonymousClass1.$SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;
        r36 = r15.ordinal();
        r35 = r35[r36];
        switch(r35) {
            case 2: goto L_0x0210;
            case 3: goto L_0x0210;
            case 4: goto L_0x0210;
            case 5: goto L_0x0213;
            case 6: goto L_0x0210;
            case 7: goto L_0x01fc;
            case 8: goto L_0x01f7;
            case 9: goto L_0x01f2;
            case 10: goto L_0x01ec;
            case 11: goto L_0x01fa;
            case 12: goto L_0x01fa;
            case 13: goto L_0x01fa;
            case 14: goto L_0x01ea;
            case 15: goto L_0x01e7;
            case 16: goto L_0x01e5;
            default: goto L_0x01e4;
        };
    L_0x01e4:
        goto L_0x0213;
    L_0x01e5:
        r0 = r2;
        goto L_0x01fa;
    L_0x01e7:
        r0 = r33 / r45;
        goto L_0x01fa;
    L_0x01ea:
        r0 = r10;
        goto L_0x01fa;
    L_0x01ec:
        r2 = 4605380978949069210; // 0x3fe999999999999a float:-1.5881868E-23 double:0.8;
        goto L_0x01f4;
    L_0x01f2:
        r2 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
    L_0x01f4:
        r0 = r0 * r2;
        goto L_0x01fa;
    L_0x01f7:
        r0 = r0 - r8;
        r0 = r0 / r45;
    L_0x01fa:
        r2 = 0;
        goto L_0x0216;
    L_0x01fc:
        r0 = new android.graphics.Rect;
        r0.<init>();
        r1 = "x";
        r2 = 0;
        r3 = 1;
        r14.getTextBounds(r1, r2, r3, r0);
        r0 = r0.height();
        r0 = (double) r0;
        r0 = r0 / r45;
        goto L_0x0216;
    L_0x0210:
        r2 = 0;
        r0 = -r8;
        goto L_0x0216;
    L_0x0213:
        r2 = 0;
        r0 = r17;
    L_0x0216:
        if (r7 == 0) goto L_0x02f0;
    L_0x0218:
        r3 = r7.isEmpty();
        if (r3 != 0) goto L_0x02f0;
    L_0x021e:
        r3 = com.horcrux.svg.TSpanView.AnonymousClass1.$SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline;
        r8 = r15.ordinal();
        r3 = r3[r8];
        r8 = 14;
        if (r3 == r8) goto L_0x02f0;
    L_0x022a:
        r8 = 16;
        if (r3 == r8) goto L_0x02f0;
    L_0x022e:
        r3 = r7.hashCode();
        r8 = -1720785339; // 0xffffffff996ee645 float:-1.2350814E-23 double:NaN;
        if (r3 == r8) goto L_0x0256;
    L_0x0237:
        r8 = 114240; // 0x1be40 float:1.60084E-40 double:5.6442E-319;
        if (r3 == r8) goto L_0x024c;
    L_0x023c:
        r8 = 109801339; // 0x68b6f7b float:5.2449795E-35 double:5.42490695E-316;
        if (r3 == r8) goto L_0x0242;
    L_0x0241:
        goto L_0x0260;
    L_0x0242:
        r3 = "super";
        r3 = r7.equals(r3);
        if (r3 == 0) goto L_0x0260;
    L_0x024a:
        r3 = 1;
        goto L_0x0261;
    L_0x024c:
        r3 = "sub";
        r3 = r7.equals(r3);
        if (r3 == 0) goto L_0x0260;
    L_0x0254:
        r3 = 0;
        goto L_0x0261;
    L_0x0256:
        r3 = "baseline";
        r3 = r7.equals(r3);
        if (r3 == 0) goto L_0x0260;
    L_0x025e:
        r3 = 2;
        goto L_0x0261;
    L_0x0260:
        r3 = -1;
    L_0x0261:
        r8 = "os2";
        r9 = "unitsPerEm";
        r10 = "tables";
        if (r3 == 0) goto L_0x02ba;
    L_0x0269:
        r11 = 1;
        if (r3 == r11) goto L_0x0283;
    L_0x026c:
        r11 = 2;
        if (r3 == r11) goto L_0x02f0;
    L_0x026f:
        r3 = r6.mScale;
        r8 = (double) r3;
        r34 = r8 * r42;
        r3 = r6.mScale;
        r8 = (double) r3;
        r33 = r7;
        r36 = r8;
        r38 = r42;
        r7 = com.horcrux.svg.PropHelper.fromRelative(r33, r34, r36, r38);
        r0 = r0 - r7;
        goto L_0x02f0;
    L_0x0283:
        if (r12 == 0) goto L_0x02f0;
    L_0x0285:
        r3 = r12.hasKey(r10);
        if (r3 == 0) goto L_0x02f0;
    L_0x028b:
        r3 = r12.hasKey(r9);
        if (r3 == 0) goto L_0x02f0;
    L_0x0291:
        r3 = r12.getInt(r9);
        r7 = r12.getMap(r10);
        r9 = r7.hasKey(r8);
        if (r9 == 0) goto L_0x02f0;
    L_0x029f:
        r7 = r7.getMap(r8);
        r8 = "ySuperscriptYOffset";
        r9 = r7.hasKey(r8);
        if (r9 == 0) goto L_0x02f0;
    L_0x02ab:
        r7 = r7.getDouble(r8);
        r9 = r6.mScale;
        r9 = (double) r9;
        r9 = r9 * r42;
        r9 = r9 * r7;
        r7 = (double) r3;
        r9 = r9 / r7;
        r0 = r0 - r9;
        goto L_0x02f0;
    L_0x02ba:
        if (r12 == 0) goto L_0x02f0;
    L_0x02bc:
        r3 = r12.hasKey(r10);
        if (r3 == 0) goto L_0x02f0;
    L_0x02c2:
        r3 = r12.hasKey(r9);
        if (r3 == 0) goto L_0x02f0;
    L_0x02c8:
        r3 = r12.getInt(r9);
        r7 = r12.getMap(r10);
        r9 = r7.hasKey(r8);
        if (r9 == 0) goto L_0x02f0;
    L_0x02d6:
        r7 = r7.getMap(r8);
        r8 = "ySubscriptYOffset";
        r9 = r7.hasKey(r8);
        if (r9 == 0) goto L_0x02f0;
    L_0x02e2:
        r7 = r7.getDouble(r8);
        r9 = r6.mScale;
        r9 = (double) r9;
        r9 = r9 * r42;
        r9 = r9 * r7;
        r7 = (double) r3;
        r9 = r9 / r7;
        r0 = r0 + r9;
    L_0x02f0:
        r3 = new android.graphics.Matrix;
        r3.<init>();
        r15 = new android.graphics.Matrix;
        r15.<init>();
        r12 = new android.graphics.Matrix;
        r12.<init>();
        r7 = 9;
        r11 = new float[r7];
        r10 = new float[r7];
        r9 = 0;
    L_0x0306:
        if (r9 >= r13) goto L_0x0587;
    L_0x0308:
        r7 = r19[r9];
        r8 = java.lang.String.valueOf(r7);
        r21 = r28[r9];
        r2 = 0;
        if (r21 == 0) goto L_0x031b;
    L_0x0313:
        r8 = "";
        r2 = r8;
        r36 = r13;
        r34 = 0;
        goto L_0x0352;
    L_0x031b:
        r61 = r8;
        r8 = r9;
        r24 = 1;
        r34 = 0;
    L_0x0322:
        r8 = r8 + 1;
        if (r8 >= r13) goto L_0x034d;
    L_0x0326:
        r35 = r48[r8];
        r35 = (r35 > r2 ? 1 : (r35 == r2 ? 0 : -1));
        if (r35 <= 0) goto L_0x032d;
    L_0x032c:
        goto L_0x034d;
    L_0x032d:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r36 = r13;
        r13 = r61;
        r2.append(r13);
        r13 = r19[r8];
        r2.append(r13);
        r61 = r2.toString();
        r2 = 1;
        r28[r8] = r2;
        r13 = r36;
        r2 = 0;
        r24 = 1;
        r34 = 1;
        goto L_0x0322;
    L_0x034d:
        r36 = r13;
        r13 = r61;
        r2 = r13;
    L_0x0352:
        r8 = r14.measureText(r2);
        r13 = (double) r8;
        r13 = r13 * r51;
        if (r27 == 0) goto L_0x0366;
    L_0x035b:
        r8 = r48[r9];
        r37 = r9;
        r8 = (double) r8;
        r8 = r8 * r51;
        r8 = r8 - r13;
        r22 = r8;
        goto L_0x0368;
    L_0x0366:
        r37 = r9;
    L_0x0368:
        r8 = 32;
        if (r7 != r8) goto L_0x036e;
    L_0x036c:
        r8 = 1;
        goto L_0x036f;
    L_0x036e:
        r8 = 0;
    L_0x036f:
        if (r8 == 0) goto L_0x0374;
    L_0x0371:
        r38 = r25;
        goto L_0x0376;
    L_0x0374:
        r38 = r17;
    L_0x0376:
        r38 = r38 + r29;
        r38 = r13 + r38;
        if (r21 == 0) goto L_0x0383;
    L_0x037c:
        r42 = r7;
        r6 = r17;
        r9 = r49;
        goto L_0x038d;
    L_0x0383:
        r42 = r22 + r38;
        r9 = r49;
        r70 = r42;
        r42 = r7;
        r6 = r70;
    L_0x038d:
        r6 = r9.nextX(r6);
        r43 = r0;
        r0 = r9.nextY();
        r61 = r9.nextDeltaX();
        r63 = r9.nextDeltaY();
        r65 = r0;
        r0 = r9.nextRotation();
        if (r21 != 0) goto L_0x0541;
    L_0x03a7:
        if (r8 == 0) goto L_0x03ab;
    L_0x03a9:
        goto L_0x0541;
    L_0x03ab:
        r38 = r38 * r4;
        r13 = r13 * r4;
        r6 = r6 + r61;
        r6 = r6 * r4;
        r6 = r31 + r6;
        r6 = r6 - r38;
        if (r16 == 0) goto L_0x0481;
    L_0x03b9:
        r49 = r9;
        r8 = r6 + r13;
        r13 = r13 / r45;
        r38 = r0;
        r0 = r6 + r13;
        r21 = (r0 > r59 ? 1 : (r0 == r59 ? 0 : -1));
        if (r21 <= 0) goto L_0x03ed;
    L_0x03c7:
        r8 = r75;
        r14 = r10;
        r6 = r11;
        r1 = r15;
        r9 = r20;
        r24 = r25;
        r33 = r36;
        r0 = r47;
        r34 = r50;
        r15 = r57;
        r2 = 1;
        r26 = 0;
        r10 = r72;
        r11 = r74;
        r20 = r12;
        r70 = r40;
        r41 = r37;
        r37 = r49;
        r39 = r53;
        r49 = r70;
        goto L_0x0563;
    L_0x03ed:
        r21 = (r0 > r55 ? 1 : (r0 == r55 ? 0 : -1));
        if (r21 >= 0) goto L_0x03f2;
    L_0x03f1:
        goto L_0x03c7;
    L_0x03f2:
        r21 = r2;
        r2 = 3;
        if (r58 == 0) goto L_0x0402;
    L_0x03f7:
        r0 = (float) r0;
        r1 = r50;
        r1.getMatrix(r0, r15, r2);
        r2 = r1;
        r1 = r15;
        r67 = r53;
        goto L_0x0468;
    L_0x0402:
        r2 = r50;
        r61 = (r6 > r17 ? 1 : (r6 == r17 ? 0 : -1));
        if (r61 >= 0) goto L_0x0415;
    L_0x0408:
        r61 = r13;
        r13 = 3;
        r14 = 0;
        r2.getMatrix(r14, r3, r13);
        r6 = (float) r6;
        r3.preTranslate(r6, r14);
        r13 = 1;
        goto L_0x041c;
    L_0x0415:
        r61 = r13;
        r6 = (float) r6;
        r13 = 1;
        r2.getMatrix(r6, r3, r13);
    L_0x041c:
        r0 = (float) r0;
        r2.getMatrix(r0, r15, r13);
        r0 = (r8 > r53 ? 1 : (r8 == r53 ? 0 : -1));
        if (r0 <= 0) goto L_0x0432;
    L_0x0424:
        r0 = r53;
        r6 = (float) r0;
        r7 = 3;
        r2.getMatrix(r6, r12, r7);
        r8 = r8 - r0;
        r6 = (float) r8;
        r7 = 0;
        r12.preTranslate(r6, r7);
        goto L_0x0438;
    L_0x0432:
        r0 = r53;
        r6 = (float) r8;
        r2.getMatrix(r6, r12, r13);
    L_0x0438:
        r3.getValues(r11);
        r12.getValues(r10);
        r14 = 2;
        r6 = r11[r14];
        r6 = (double) r6;
        r8 = 5;
        r9 = r11[r8];
        r8 = (double) r9;
        r13 = r10[r14];
        r50 = r15;
        r14 = (double) r13;
        r13 = 5;
        r13 = r10[r13];
        r67 = r0;
        r0 = (double) r13;
        r14 = r14 - r6;
        r0 = r0 - r8;
        r0 = java.lang.Math.atan2(r0, r14);
        r6 = 4633260481411531256; // 0x404ca5dc1a63c1f8 float:4.7099186E-23 double:57.29577951308232;
        r0 = r0 * r6;
        r0 = r0 * r4;
        r0 = (float) r0;
        r1 = r50;
        r1.preRotate(r0);
        r13 = r61;
    L_0x0468:
        r6 = -r13;
        r0 = (float) r6;
        r6 = r63 + r43;
        r6 = (float) r6;
        r1.preTranslate(r0, r6);
        r13 = r40;
        r0 = (float) r13;
        r15 = r57;
        r6 = (float) r15;
        r1.preScale(r0, r6);
        r8 = r65;
        r0 = (float) r8;
        r6 = 0;
        r1.postTranslate(r6, r0);
        goto L_0x049b;
    L_0x0481:
        r38 = r0;
        r21 = r2;
        r49 = r9;
        r1 = r15;
        r13 = r40;
        r2 = r50;
        r67 = r53;
        r15 = r57;
        r8 = r65;
        r0 = (float) r6;
        r6 = r8 + r63;
        r6 = r6 + r43;
        r6 = (float) r6;
        r1.setTranslate(r0, r6);
    L_0x049b:
        r6 = r38;
        r0 = (float) r6;
        r1.preRotate(r0);
        if (r34 == 0) goto L_0x04dc;
    L_0x04a3:
        r0 = new android.graphics.Path;
        r0.<init>();
        r9 = 0;
        r6 = r21.length();
        r34 = 0;
        r38 = 0;
        r7 = r74;
        r39 = r67;
        r8 = r21;
        r41 = r37;
        r37 = r49;
        r49 = r13;
        r13 = 1;
        r14 = r10;
        r10 = r6;
        r6 = r11;
        r24 = r25;
        r26 = 0;
        r11 = r34;
        r69 = r20;
        r20 = r12;
        r12 = r38;
        r34 = r2;
        r33 = r36;
        r2 = 1;
        r13 = r0;
        r7.getTextPath(r8, r9, r10, r11, r12, r13);
        r7 = r0;
        r13 = r21;
        r0 = r47;
        goto L_0x04fd;
    L_0x04dc:
        r34 = r2;
        r6 = r11;
        r69 = r20;
        r24 = r25;
        r33 = r36;
        r41 = r37;
        r7 = r42;
        r0 = r47;
        r37 = r49;
        r39 = r67;
        r2 = 1;
        r26 = 0;
        r20 = r12;
        r49 = r13;
        r13 = r21;
        r14 = r10;
        r7 = r0.getOrCreateAndCache(r7, r13);
    L_0x04fd:
        r8 = new android.graphics.RectF;
        r8.<init>();
        r7.computeBounds(r8, r2);
        r8 = r8.width();
        r9 = 0;
        r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1));
        if (r8 != 0) goto L_0x0532;
    L_0x050e:
        r75.save();
        r8 = r75;
        r8.concat(r1);
        r10 = r72;
        r7 = r10.emoji;
        r7.add(r13);
        r7 = r10.emojiTransforms;
        r11 = new android.graphics.Matrix;
        r11.<init>(r1);
        r7.add(r11);
        r11 = r74;
        r8.drawText(r13, r9, r9, r11);
        r75.restore();
        r9 = r69;
        goto L_0x0563;
    L_0x0532:
        r10 = r72;
        r11 = r74;
        r8 = r75;
        r7.transform(r1);
        r9 = r69;
        r9.addPath(r7);
        goto L_0x0563;
    L_0x0541:
        r8 = r75;
        r14 = r10;
        r6 = r11;
        r1 = r15;
        r24 = r25;
        r33 = r36;
        r0 = r47;
        r34 = r50;
        r15 = r57;
        r2 = 1;
        r26 = 0;
        r10 = r72;
        r11 = r74;
        r49 = r40;
        r39 = r53;
        r41 = r37;
        r37 = r9;
        r9 = r20;
        r20 = r12;
    L_0x0563:
        r7 = r41 + 1;
        r47 = r0;
        r57 = r15;
        r12 = r20;
        r25 = r24;
        r13 = r33;
        r53 = r39;
        r40 = r49;
        r2 = 0;
        r15 = r1;
        r20 = r9;
        r50 = r34;
        r49 = r37;
        r0 = r43;
        r9 = r7;
        r70 = r11;
        r11 = r6;
        r6 = r10;
        r10 = r14;
        r14 = r70;
        goto L_0x0306;
    L_0x0587:
        r10 = r6;
        r9 = r20;
        return r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.TSpanView.getLinePath(java.lang.String, android.graphics.Paint, android.graphics.Canvas):android.graphics.Path");
    }

    private double getAbsoluteStartOffset(SVGLength sVGLength, double d, double d2) {
        return PropHelper.fromRelative(sVGLength, d, 0.0d, (double) this.mScale, d2);
    }

    private double getTextAnchorOffset(TextAnchor textAnchor, double d) {
        int i = AnonymousClass1.$SwitchMap$com$horcrux$svg$TextProperties$TextAnchor[textAnchor.ordinal()];
        if (i != 2) {
            return i != 3 ? 0.0d : -d;
        } else {
            return (-d) / 2.0d;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.horcrux.svg.TSpanView.applyTextPropertiesToPaint(android.graphics.Paint, com.horcrux.svg.FontData):void, dom blocks: []
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
    private void applyTextPropertiesToPaint(android.graphics.Paint r11, com.horcrux.svg.FontData r12) {
        /*
        r10 = this;
        r0 = r12.fontWeight;
        r1 = com.horcrux.svg.TextProperties.FontWeight.Bold;
        r2 = 0;
        r3 = 1;
        if (r0 == r1) goto L_0x0011;
    L_0x0008:
        r0 = r12.absoluteFontWeight;
        r1 = 550; // 0x226 float:7.71E-43 double:2.717E-321;
        if (r0 < r1) goto L_0x000f;
    L_0x000e:
        goto L_0x0011;
    L_0x000f:
        r0 = 0;
        goto L_0x0012;
    L_0x0011:
        r0 = 1;
    L_0x0012:
        r1 = r12.fontStyle;
        r4 = com.horcrux.svg.TextProperties.FontStyle.italic;
        if (r1 != r4) goto L_0x001a;
    L_0x0018:
        r1 = 1;
        goto L_0x001b;
    L_0x001a:
        r1 = 0;
    L_0x001b:
        if (r0 == 0) goto L_0x0021;
    L_0x001d:
        if (r1 == 0) goto L_0x0021;
    L_0x001f:
        r2 = 3;
        goto L_0x0028;
    L_0x0021:
        if (r0 == 0) goto L_0x0025;
    L_0x0023:
        r2 = 1;
        goto L_0x0028;
    L_0x0025:
        if (r1 == 0) goto L_0x0028;
    L_0x0027:
        r2 = 2;
    L_0x0028:
        r0 = 0;
        r4 = r12.absoluteFontWeight;
        r5 = r12.fontFamily;
        if (r5 == 0) goto L_0x00d1;
    L_0x002f:
        r6 = r5.length();
        if (r6 <= 0) goto L_0x00d1;
    L_0x0035:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "fonts/";
        r6.append(r7);
        r6.append(r5);
        r8 = ".otf";
        r6.append(r8);
        r6 = r6.toString();
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r8.append(r7);
        r8.append(r5);
        r7 = ".ttf";
        r8.append(r7);
        r7 = r8.toString();
        r8 = android.os.Build.VERSION.SDK_INT;
        r9 = 26;
        if (r8 < r9) goto L_0x00ba;
    L_0x0065:
        r0 = new android.graphics.Typeface$Builder;
        r8 = r10.assets;
        r0.<init>(r8, r6);
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r8 = "'wght' ";
        r6.append(r8);
        r6.append(r4);
        r9 = r12.fontVariationSettings;
        r6.append(r9);
        r6 = r6.toString();
        r0.setFontVariationSettings(r6);
        r0.setWeight(r4);
        r0.setItalic(r1);
        r0 = r0.build();
        if (r0 != 0) goto L_0x00d1;
    L_0x0091:
        r0 = new android.graphics.Typeface$Builder;
        r6 = r10.assets;
        r0.<init>(r6, r7);
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r6.append(r8);
        r6.append(r4);
        r7 = r12.fontVariationSettings;
        r6.append(r7);
        r6 = r6.toString();
        r0.setFontVariationSettings(r6);
        r0.setWeight(r4);
        r0.setItalic(r1);
        r0 = r0.build();
        goto L_0x00d1;
    L_0x00ba:
        r8 = r10.assets;	 Catch:{ Exception -> 0x00c5 }
        r0 = android.graphics.Typeface.createFromAsset(r8, r6);	 Catch:{ Exception -> 0x00c5 }
        r0 = android.graphics.Typeface.create(r0, r2);	 Catch:{ Exception -> 0x00c5 }
        goto L_0x00d1;
    L_0x00c5:
        r6 = r10.assets;	 Catch:{ Exception -> 0x00d0 }
        r0 = android.graphics.Typeface.createFromAsset(r6, r7);	 Catch:{ Exception -> 0x00d0 }
        r0 = android.graphics.Typeface.create(r0, r2);	 Catch:{ Exception -> 0x00d0 }
        goto L_0x00d1;
    L_0x00d1:
        if (r0 != 0) goto L_0x00dd;
    L_0x00d3:
        r6 = com.facebook.react.views.text.ReactFontManager.getInstance();	 Catch:{ Exception -> 0x00dd }
        r7 = r10.assets;	 Catch:{ Exception -> 0x00dd }
        r0 = r6.getTypeface(r5, r2, r7);	 Catch:{ Exception -> 0x00dd }
    L_0x00dd:
        r2 = android.os.Build.VERSION.SDK_INT;
        r5 = 28;
        if (r2 < r5) goto L_0x00e7;
    L_0x00e3:
        r0 = android.graphics.Typeface.create(r0, r4, r1);
    L_0x00e7:
        r11.setLinearText(r3);
        r11.setSubpixelText(r3);
        r11.setTypeface(r0);
        r0 = r12.fontSize;
        r12 = r10.mScale;
        r2 = (double) r12;
        r0 = r0 * r2;
        r12 = (float) r0;
        r11.setTextSize(r12);
        r12 = android.os.Build.VERSION.SDK_INT;
        r0 = 21;
        if (r12 < r0) goto L_0x0105;
    L_0x0101:
        r12 = 0;
        r11.setLetterSpacing(r12);
    L_0x0105:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.TSpanView.applyTextPropertiesToPaint(android.graphics.Paint, com.horcrux.svg.FontData):void");
    }

    private void setupTextPath() {
        ViewParent parent = getParent();
        while (parent != null) {
            if (parent.getClass() == TextPathView.class) {
                this.textPath = (TextPathView) parent;
                return;
            } else if (parent instanceof TextView) {
                parent = parent.getParent();
            } else {
                return;
            }
        }
    }

    int hitTest(float[] fArr) {
        if (this.mContent == null) {
            return super.hitTest(fArr);
        }
        if (this.mPath != null && this.mInvertible && this.mTransformInvertible) {
            float[] fArr2 = new float[2];
            this.mInvMatrix.mapPoints(fArr2, fArr);
            this.mInvTransform.mapPoints(fArr2);
            int round = Math.round(fArr2[0]);
            int round2 = Math.round(fArr2[1]);
            if (this.mRegion == null && this.mFillPath != null) {
                this.mRegion = getRegion(this.mFillPath);
            }
            if (this.mRegion == null && this.mPath != null) {
                this.mRegion = getRegion(this.mPath);
            }
            if (this.mStrokeRegion == null && this.mStrokePath != null) {
                this.mStrokeRegion = getRegion(this.mStrokePath);
            }
            if ((this.mRegion != null && this.mRegion.contains(round, round2)) || (this.mStrokeRegion != null && this.mStrokeRegion.contains(round, round2))) {
                Path clipPath = getClipPath();
                if (clipPath != null) {
                    if (this.mClipRegionPath != clipPath) {
                        this.mClipRegionPath = clipPath;
                        this.mClipRegion = getRegion(clipPath);
                    }
                    if (!this.mClipRegion.contains(round, round2)) {
                        return -1;
                    }
                }
                return getId();
            }
        }
        return -1;
    }
}
