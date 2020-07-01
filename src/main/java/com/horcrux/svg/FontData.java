package com.horcrux.svg;

import com.drew.metadata.iptc.IptcDirectory;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.uimanager.ViewProps;
import com.google.logging.type.LogSeverity;

class FontData {
    static final double DEFAULT_FONT_SIZE = 12.0d;
    private static final double DEFAULT_KERNING = 0.0d;
    private static final double DEFAULT_LETTER_SPACING = 0.0d;
    private static final double DEFAULT_WORD_SPACING = 0.0d;
    static final FontData Defaults = new FontData();
    private static final String FONT_DATA = "fontData";
    private static final String FONT_FEATURE_SETTINGS = "fontFeatureSettings";
    private static final String FONT_VARIANT_LIGATURES = "fontVariantLigatures";
    private static final String FONT_VARIATION_SETTINGS = "fontVariationSettings";
    private static final String KERNING = "kerning";
    private static final String LETTER_SPACING = "letterSpacing";
    private static final String TEXT_ANCHOR = "textAnchor";
    private static final String TEXT_DECORATION = "textDecoration";
    private static final String WORD_SPACING = "wordSpacing";
    int absoluteFontWeight;
    final ReadableMap fontData;
    final String fontFamily;
    final String fontFeatureSettings;
    final double fontSize;
    final FontStyle fontStyle;
    final FontVariantLigatures fontVariantLigatures;
    final String fontVariationSettings;
    FontWeight fontWeight;
    final double kerning;
    final double letterSpacing;
    final boolean manualKerning;
    final TextAnchor textAnchor;
    private final TextDecoration textDecoration;
    final double wordSpacing;

    static class AbsoluteFontWeight {
        private static final FontWeight[] WEIGHTS = new FontWeight[]{FontWeight.w100, FontWeight.w100, FontWeight.w200, FontWeight.w300, FontWeight.Normal, FontWeight.w500, FontWeight.w600, FontWeight.Bold, FontWeight.w800, FontWeight.w900, FontWeight.w900};
        private static final int[] absoluteFontWeights = new int[]{400, 700, 100, LogSeverity.INFO_VALUE, 300, 400, 500, LogSeverity.CRITICAL_VALUE, 700, 800, 900};
        static final int normal = 400;

        private static int bolder(int i) {
            return i < 350 ? 400 : i < IptcDirectory.TAG_EXPIRATION_TIME ? 700 : i < 900 ? 900 : i;
        }

        private static int lighter(int i) {
            return i < 100 ? i : i < IptcDirectory.TAG_EXPIRATION_TIME ? 100 : i < 750 ? 400 : 700;
        }

        AbsoluteFontWeight() {
        }

        static FontWeight nearestFontWeight(int i) {
            return WEIGHTS[Math.round(((float) i) / 100.0f)];
        }

        static int from(FontWeight fontWeight, FontData fontData) {
            if (fontWeight == FontWeight.Bolder) {
                return bolder(fontData.absoluteFontWeight);
            }
            if (fontWeight == FontWeight.Lighter) {
                return lighter(fontData.absoluteFontWeight);
            }
            return absoluteFontWeights[fontWeight.ordinal()];
        }
    }

    private FontData() {
        this.fontData = null;
        String str = "";
        this.fontFamily = str;
        this.fontStyle = FontStyle.normal;
        this.fontWeight = FontWeight.Normal;
        this.absoluteFontWeight = 400;
        this.fontFeatureSettings = str;
        this.fontVariationSettings = str;
        this.fontVariantLigatures = FontVariantLigatures.normal;
        this.textAnchor = TextAnchor.start;
        this.textDecoration = TextDecoration.None;
        this.manualKerning = false;
        this.kerning = 0.0d;
        this.fontSize = DEFAULT_FONT_SIZE;
        this.wordSpacing = 0.0d;
        this.letterSpacing = 0.0d;
    }

    private double toAbsolute(ReadableMap readableMap, String str, double d, double d2, double d3) {
        if (readableMap.getType(str) == ReadableType.Number) {
            return readableMap.getDouble(str);
        }
        return PropHelper.fromRelative(readableMap.getString(str), d3, d, d2);
    }

    private void setInheritedWeight(FontData fontData) {
        this.absoluteFontWeight = fontData.absoluteFontWeight;
        this.fontWeight = fontData.fontWeight;
    }

    private void handleNumericWeight(FontData fontData, double d) {
        long round = Math.round(d);
        if (round < 1 || round > 1000) {
            setInheritedWeight(fontData);
            return;
        }
        this.absoluteFontWeight = (int) round;
        this.fontWeight = AbsoluteFontWeight.nearestFontWeight(this.absoluteFontWeight);
    }

    FontData(ReadableMap readableMap, FontData fontData, double d) {
        double d2 = fontData.fontSize;
        if (readableMap.hasKey(ViewProps.FONT_SIZE)) {
            this.fontSize = toAbsolute(readableMap, ViewProps.FONT_SIZE, 1.0d, d2, d2);
        } else {
            this.fontSize = d2;
        }
        String str = ViewProps.FONT_WEIGHT;
        if (!readableMap.hasKey(str)) {
            setInheritedWeight(fontData);
        } else if (readableMap.getType(str) == ReadableType.Number) {
            handleNumericWeight(fontData, readableMap.getDouble(str));
        } else {
            str = readableMap.getString(str);
            if (FontWeight.hasEnum(str)) {
                this.absoluteFontWeight = AbsoluteFontWeight.from(FontWeight.get(str), fontData);
                this.fontWeight = AbsoluteFontWeight.nearestFontWeight(this.absoluteFontWeight);
            } else if (str != null) {
                handleNumericWeight(fontData, Double.parseDouble(str));
            } else {
                setInheritedWeight(fontData);
            }
        }
        str = FONT_DATA;
        this.fontData = readableMap.hasKey(str) ? readableMap.getMap(str) : fontData.fontData;
        str = ViewProps.FONT_FAMILY;
        this.fontFamily = readableMap.hasKey(str) ? readableMap.getString(str) : fontData.fontFamily;
        str = ViewProps.FONT_STYLE;
        this.fontStyle = readableMap.hasKey(str) ? FontStyle.valueOf(readableMap.getString(str)) : fontData.fontStyle;
        str = FONT_FEATURE_SETTINGS;
        this.fontFeatureSettings = readableMap.hasKey(str) ? readableMap.getString(str) : fontData.fontFeatureSettings;
        str = FONT_VARIATION_SETTINGS;
        this.fontVariationSettings = readableMap.hasKey(str) ? readableMap.getString(str) : fontData.fontVariationSettings;
        str = FONT_VARIANT_LIGATURES;
        this.fontVariantLigatures = readableMap.hasKey(str) ? FontVariantLigatures.valueOf(readableMap.getString(str)) : fontData.fontVariantLigatures;
        str = TEXT_ANCHOR;
        this.textAnchor = readableMap.hasKey(str) ? TextAnchor.valueOf(readableMap.getString(str)) : fontData.textAnchor;
        str = TEXT_DECORATION;
        this.textDecoration = readableMap.hasKey(str) ? TextDecoration.getEnum(readableMap.getString(str)) : fontData.textDecoration;
        boolean hasKey = readableMap.hasKey(KERNING);
        boolean z = hasKey || fontData.manualKerning;
        this.manualKerning = z;
        this.kerning = hasKey ? toAbsolute(readableMap, KERNING, d, this.fontSize, 0.0d) : fontData.kerning;
        this.wordSpacing = readableMap.hasKey(WORD_SPACING) ? toAbsolute(readableMap, WORD_SPACING, d, this.fontSize, 0.0d) : fontData.wordSpacing;
        this.letterSpacing = readableMap.hasKey("letterSpacing") ? toAbsolute(readableMap, "letterSpacing", d, this.fontSize, 0.0d) : fontData.letterSpacing;
    }
}
