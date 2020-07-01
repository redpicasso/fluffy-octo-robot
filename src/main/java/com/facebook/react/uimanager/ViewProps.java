package com.facebook.react.uimanager;

import java.util.Arrays;
import java.util.HashSet;

public class ViewProps {
    public static final String ALIGN_CONTENT = "alignContent";
    public static final String ALIGN_ITEMS = "alignItems";
    public static final String ALIGN_SELF = "alignSelf";
    public static final String ALLOW_FONT_SCALING = "allowFontScaling";
    public static final String ASPECT_RATIO = "aspectRatio";
    public static final String AUTO = "auto";
    public static final String BACKGROUND_COLOR = "backgroundColor";
    public static final String BORDER_BOTTOM_COLOR = "borderBottomColor";
    public static final String BORDER_BOTTOM_END_RADIUS = "borderBottomEndRadius";
    public static final String BORDER_BOTTOM_LEFT_RADIUS = "borderBottomLeftRadius";
    public static final String BORDER_BOTTOM_RIGHT_RADIUS = "borderBottomRightRadius";
    public static final String BORDER_BOTTOM_START_RADIUS = "borderBottomStartRadius";
    public static final String BORDER_BOTTOM_WIDTH = "borderBottomWidth";
    public static final String BORDER_COLOR = "borderColor";
    public static final String BORDER_END_COLOR = "borderEndColor";
    public static final String BORDER_END_WIDTH = "borderEndWidth";
    public static final String BORDER_LEFT_COLOR = "borderLeftColor";
    public static final String BORDER_LEFT_WIDTH = "borderLeftWidth";
    public static final String BORDER_RADIUS = "borderRadius";
    public static final String BORDER_RIGHT_COLOR = "borderRightColor";
    public static final String BORDER_RIGHT_WIDTH = "borderRightWidth";
    public static final int[] BORDER_SPACING_TYPES = new int[]{8, 4, 5, 1, 3, 0, 2};
    public static final String BORDER_START_COLOR = "borderStartColor";
    public static final String BORDER_START_WIDTH = "borderStartWidth";
    public static final String BORDER_TOP_COLOR = "borderTopColor";
    public static final String BORDER_TOP_END_RADIUS = "borderTopEndRadius";
    public static final String BORDER_TOP_LEFT_RADIUS = "borderTopLeftRadius";
    public static final String BORDER_TOP_RIGHT_RADIUS = "borderTopRightRadius";
    public static final String BORDER_TOP_START_RADIUS = "borderTopStartRadius";
    public static final String BORDER_TOP_WIDTH = "borderTopWidth";
    public static final String BORDER_WIDTH = "borderWidth";
    public static final String BOTTOM = "bottom";
    public static final String BOX_NONE = "box-none";
    public static final String COLLAPSABLE = "collapsable";
    public static final String COLOR = "color";
    public static final String DISPLAY = "display";
    public static final String ELLIPSIZE_MODE = "ellipsizeMode";
    public static final String ENABLED = "enabled";
    public static final String END = "end";
    public static final String FLEX = "flex";
    public static final String FLEX_BASIS = "flexBasis";
    public static final String FLEX_DIRECTION = "flexDirection";
    public static final String FLEX_GROW = "flexGrow";
    public static final String FLEX_SHRINK = "flexShrink";
    public static final String FLEX_WRAP = "flexWrap";
    public static final String FONT_FAMILY = "fontFamily";
    public static final String FONT_SIZE = "fontSize";
    public static final String FONT_STYLE = "fontStyle";
    public static final String FONT_WEIGHT = "fontWeight";
    public static final String HEIGHT = "height";
    public static final String HIDDEN = "hidden";
    public static final String INCLUDE_FONT_PADDING = "includeFontPadding";
    public static final String JUSTIFY_CONTENT = "justifyContent";
    private static final HashSet<String> LAYOUT_ONLY_PROPS = new HashSet(Arrays.asList(new String[]{ALIGN_SELF, ALIGN_ITEMS, COLLAPSABLE, FLEX, FLEX_BASIS, FLEX_DIRECTION, FLEX_GROW, FLEX_SHRINK, FLEX_WRAP, JUSTIFY_CONTENT, ALIGN_CONTENT, "display", POSITION, RIGHT, TOP, BOTTOM, LEFT, START, END, "width", "height", MIN_WIDTH, MAX_WIDTH, MIN_HEIGHT, MAX_HEIGHT, MARGIN, MARGIN_VERTICAL, MARGIN_HORIZONTAL, MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, MARGIN_BOTTOM, MARGIN_START, MARGIN_END, PADDING, PADDING_VERTICAL, PADDING_HORIZONTAL, PADDING_LEFT, PADDING_RIGHT, PADDING_TOP, PADDING_BOTTOM, PADDING_START, PADDING_END}));
    public static final String LEFT = "left";
    public static final String LETTER_SPACING = "letterSpacing";
    public static final String LINE_HEIGHT = "lineHeight";
    public static final String MARGIN = "margin";
    public static final String MARGIN_BOTTOM = "marginBottom";
    public static final String MARGIN_END = "marginEnd";
    public static final String MARGIN_HORIZONTAL = "marginHorizontal";
    public static final String MARGIN_LEFT = "marginLeft";
    public static final String MARGIN_RIGHT = "marginRight";
    public static final String MARGIN_START = "marginStart";
    public static final String MARGIN_TOP = "marginTop";
    public static final String MARGIN_VERTICAL = "marginVertical";
    public static final String MAX_FONT_SIZE_MULTIPLIER = "maxFontSizeMultiplier";
    public static final String MAX_HEIGHT = "maxHeight";
    public static final String MAX_WIDTH = "maxWidth";
    public static final String MIN_HEIGHT = "minHeight";
    public static final String MIN_WIDTH = "minWidth";
    public static final String NEEDS_OFFSCREEN_ALPHA_COMPOSITING = "needsOffscreenAlphaCompositing";
    public static final String NONE = "none";
    public static final String NUMBER_OF_LINES = "numberOfLines";
    public static final String ON = "on";
    public static final String ON_LAYOUT = "onLayout";
    public static final String OPACITY = "opacity";
    public static final String OVERFLOW = "overflow";
    public static final String PADDING = "padding";
    public static final String PADDING_BOTTOM = "paddingBottom";
    public static final String PADDING_END = "paddingEnd";
    public static final String PADDING_HORIZONTAL = "paddingHorizontal";
    public static final String PADDING_LEFT = "paddingLeft";
    public static final int[] PADDING_MARGIN_SPACING_TYPES = new int[]{8, 7, 6, 4, 5, 1, 3, 0, 2};
    public static final String PADDING_RIGHT = "paddingRight";
    public static final String PADDING_START = "paddingStart";
    public static final String PADDING_TOP = "paddingTop";
    public static final String PADDING_VERTICAL = "paddingVertical";
    public static final String POINTER_EVENTS = "pointerEvents";
    public static final String POSITION = "position";
    public static final int[] POSITION_SPACING_TYPES = new int[]{4, 5, 1, 3};
    public static final String RESIZE_METHOD = "resizeMethod";
    public static final String RESIZE_MODE = "resizeMode";
    public static final String RIGHT = "right";
    public static final String SCROLL = "scroll";
    public static final String START = "start";
    public static final String TEXT_ALIGN = "textAlign";
    public static final String TEXT_ALIGN_VERTICAL = "textAlignVertical";
    public static final String TEXT_BREAK_STRATEGY = "textBreakStrategy";
    public static final String TEXT_DECORATION_LINE = "textDecorationLine";
    public static final String TOP = "top";
    public static final String VIEW_CLASS_NAME = "RCTView";
    public static final String VISIBLE = "visible";
    public static final String WIDTH = "width";

    public static boolean isLayoutOnly(com.facebook.react.bridge.ReadableMap r18, java.lang.String r19) {
        /*
        r0 = r18;
        r1 = r19;
        r2 = LAYOUT_ONLY_PROPS;
        r2 = r2.contains(r1);
        r3 = 1;
        if (r2 == 0) goto L_0x000e;
    L_0x000d:
        return r3;
    L_0x000e:
        r2 = "pointerEvents";
        r2 = r2.equals(r1);
        r4 = 0;
        if (r2 == 0) goto L_0x002e;
    L_0x0017:
        r0 = r18.getString(r19);
        r1 = "auto";
        r1 = r1.equals(r0);
        if (r1 != 0) goto L_0x002d;
    L_0x0023:
        r1 = "box-none";
        r0 = r1.equals(r0);
        if (r0 == 0) goto L_0x002c;
    L_0x002b:
        goto L_0x002d;
    L_0x002c:
        r3 = 0;
    L_0x002d:
        return r3;
    L_0x002e:
        r5 = r19.hashCode();
        r6 = "overflow";
        r7 = "borderLeftWidth";
        r8 = "borderLeftColor";
        r9 = "opacity";
        r10 = "borderBottomWidth";
        r11 = "borderBottomColor";
        r12 = "borderTopWidth";
        r13 = "borderTopColor";
        r14 = "borderRightWidth";
        r15 = "borderRightColor";
        r2 = "borderWidth";
        switch(r5) {
            case -1989576717: goto L_0x00ac;
            case -1971292586: goto L_0x00a3;
            case -1470826662: goto L_0x009b;
            case -1452542531: goto L_0x0092;
            case -1308858324: goto L_0x008a;
            case -1290574193: goto L_0x0081;
            case -1267206133: goto L_0x0079;
            case -242276144: goto L_0x0071;
            case -223992013: goto L_0x0069;
            case 529642498: goto L_0x0060;
            case 741115130: goto L_0x0058;
            case 1349188574: goto L_0x004d;
            default: goto L_0x004b;
        };
    L_0x004b:
        goto L_0x00b4;
    L_0x004d:
        r5 = "borderRadius";
        r1 = r1.equals(r5);
        if (r1 == 0) goto L_0x00b4;
    L_0x0055:
        r1 = 1;
        goto L_0x00b5;
    L_0x0058:
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x00b4;
    L_0x005e:
        r1 = 6;
        goto L_0x00b5;
    L_0x0060:
        r1 = r1.equals(r6);
        if (r1 == 0) goto L_0x00b4;
    L_0x0066:
        r1 = 11;
        goto L_0x00b5;
    L_0x0069:
        r1 = r1.equals(r7);
        if (r1 == 0) goto L_0x00b4;
    L_0x006f:
        r1 = 7;
        goto L_0x00b5;
    L_0x0071:
        r1 = r1.equals(r8);
        if (r1 == 0) goto L_0x00b4;
    L_0x0077:
        r1 = 2;
        goto L_0x00b5;
    L_0x0079:
        r1 = r1.equals(r9);
        if (r1 == 0) goto L_0x00b4;
    L_0x007f:
        r1 = 0;
        goto L_0x00b5;
    L_0x0081:
        r1 = r1.equals(r10);
        if (r1 == 0) goto L_0x00b4;
    L_0x0087:
        r1 = 10;
        goto L_0x00b5;
    L_0x008a:
        r1 = r1.equals(r11);
        if (r1 == 0) goto L_0x00b4;
    L_0x0090:
        r1 = 5;
        goto L_0x00b5;
    L_0x0092:
        r1 = r1.equals(r12);
        if (r1 == 0) goto L_0x00b4;
    L_0x0098:
        r1 = 8;
        goto L_0x00b5;
    L_0x009b:
        r1 = r1.equals(r13);
        if (r1 == 0) goto L_0x00b4;
    L_0x00a1:
        r1 = 4;
        goto L_0x00b5;
    L_0x00a3:
        r1 = r1.equals(r14);
        if (r1 == 0) goto L_0x00b4;
    L_0x00a9:
        r1 = 9;
        goto L_0x00b5;
    L_0x00ac:
        r1 = r1.equals(r15);
        if (r1 == 0) goto L_0x00b4;
    L_0x00b2:
        r1 = 3;
        goto L_0x00b5;
    L_0x00b4:
        r1 = -1;
    L_0x00b5:
        r16 = 0;
        switch(r1) {
            case 0: goto L_0x0186;
            case 1: goto L_0x0161;
            case 2: goto L_0x0152;
            case 3: goto L_0x0143;
            case 4: goto L_0x0134;
            case 5: goto L_0x0125;
            case 6: goto L_0x0114;
            case 7: goto L_0x0103;
            case 8: goto L_0x00f2;
            case 9: goto L_0x00e1;
            case 10: goto L_0x00d0;
            case 11: goto L_0x00bb;
            default: goto L_0x00ba;
        };
    L_0x00ba:
        return r4;
    L_0x00bb:
        r1 = r0.isNull(r6);
        if (r1 != 0) goto L_0x00cf;
    L_0x00c1:
        r0 = r0.getString(r6);
        r1 = "visible";
        r0 = r1.equals(r0);
        if (r0 == 0) goto L_0x00ce;
    L_0x00cd:
        goto L_0x00cf;
    L_0x00ce:
        r3 = 0;
    L_0x00cf:
        return r3;
    L_0x00d0:
        r1 = r0.isNull(r10);
        if (r1 != 0) goto L_0x00e0;
    L_0x00d6:
        r0 = r0.getDouble(r10);
        r2 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1));
        if (r2 != 0) goto L_0x00df;
    L_0x00de:
        goto L_0x00e0;
    L_0x00df:
        r3 = 0;
    L_0x00e0:
        return r3;
    L_0x00e1:
        r1 = r0.isNull(r14);
        if (r1 != 0) goto L_0x00f1;
    L_0x00e7:
        r0 = r0.getDouble(r14);
        r2 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1));
        if (r2 != 0) goto L_0x00f0;
    L_0x00ef:
        goto L_0x00f1;
    L_0x00f0:
        r3 = 0;
    L_0x00f1:
        return r3;
    L_0x00f2:
        r1 = r0.isNull(r12);
        if (r1 != 0) goto L_0x0102;
    L_0x00f8:
        r0 = r0.getDouble(r12);
        r2 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1));
        if (r2 != 0) goto L_0x0101;
    L_0x0100:
        goto L_0x0102;
    L_0x0101:
        r3 = 0;
    L_0x0102:
        return r3;
    L_0x0103:
        r1 = r0.isNull(r7);
        if (r1 != 0) goto L_0x0113;
    L_0x0109:
        r0 = r0.getDouble(r7);
        r2 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1));
        if (r2 != 0) goto L_0x0112;
    L_0x0111:
        goto L_0x0113;
    L_0x0112:
        r3 = 0;
    L_0x0113:
        return r3;
    L_0x0114:
        r1 = r0.isNull(r2);
        if (r1 != 0) goto L_0x0124;
    L_0x011a:
        r0 = r0.getDouble(r2);
        r2 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1));
        if (r2 != 0) goto L_0x0123;
    L_0x0122:
        goto L_0x0124;
    L_0x0123:
        r3 = 0;
    L_0x0124:
        return r3;
    L_0x0125:
        r1 = r0.isNull(r11);
        if (r1 != 0) goto L_0x0132;
    L_0x012b:
        r0 = r0.getInt(r11);
        if (r0 != 0) goto L_0x0132;
    L_0x0131:
        goto L_0x0133;
    L_0x0132:
        r3 = 0;
    L_0x0133:
        return r3;
    L_0x0134:
        r1 = r0.isNull(r13);
        if (r1 != 0) goto L_0x0141;
    L_0x013a:
        r0 = r0.getInt(r13);
        if (r0 != 0) goto L_0x0141;
    L_0x0140:
        goto L_0x0142;
    L_0x0141:
        r3 = 0;
    L_0x0142:
        return r3;
    L_0x0143:
        r1 = r0.isNull(r15);
        if (r1 != 0) goto L_0x0150;
    L_0x0149:
        r0 = r0.getInt(r15);
        if (r0 != 0) goto L_0x0150;
    L_0x014f:
        goto L_0x0151;
    L_0x0150:
        r3 = 0;
    L_0x0151:
        return r3;
    L_0x0152:
        r1 = r0.isNull(r8);
        if (r1 != 0) goto L_0x015f;
    L_0x0158:
        r0 = r0.getInt(r8);
        if (r0 != 0) goto L_0x015f;
    L_0x015e:
        goto L_0x0160;
    L_0x015f:
        r3 = 0;
    L_0x0160:
        return r3;
    L_0x0161:
        r1 = "backgroundColor";
        r5 = r0.hasKey(r1);
        if (r5 == 0) goto L_0x0170;
    L_0x0169:
        r1 = r0.getInt(r1);
        if (r1 == 0) goto L_0x0170;
    L_0x016f:
        return r4;
    L_0x0170:
        r1 = r0.hasKey(r2);
        if (r1 == 0) goto L_0x0185;
    L_0x0176:
        r1 = r0.isNull(r2);
        if (r1 != 0) goto L_0x0185;
    L_0x017c:
        r0 = r0.getDouble(r2);
        r2 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1));
        if (r2 == 0) goto L_0x0185;
    L_0x0184:
        return r4;
    L_0x0185:
        return r3;
    L_0x0186:
        r1 = r0.isNull(r9);
        if (r1 != 0) goto L_0x0198;
    L_0x018c:
        r0 = r0.getDouble(r9);
        r5 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1));
        if (r2 != 0) goto L_0x0197;
    L_0x0196:
        goto L_0x0198;
    L_0x0197:
        r3 = 0;
    L_0x0198:
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.uimanager.ViewProps.isLayoutOnly(com.facebook.react.bridge.ReadableMap, java.lang.String):boolean");
    }
}
