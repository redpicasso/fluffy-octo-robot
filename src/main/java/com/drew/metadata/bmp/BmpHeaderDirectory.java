package com.drew.metadata.bmp;

import androidx.exifinterface.media.ExifInterface;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.Directory;
import java.util.HashMap;

public class BmpHeaderDirectory extends Directory {
    public static final int TAG_ALPHA_MASK = 15;
    public static final int TAG_BITMAP_TYPE = -2;
    public static final int TAG_BITS_PER_PIXEL = 4;
    public static final int TAG_BLUE_MASK = 14;
    public static final int TAG_COLOR_ENCODING = 11;
    public static final int TAG_COLOR_SPACE_TYPE = 16;
    public static final int TAG_COLOUR_PLANES = 3;
    public static final int TAG_COMPRESSION = 5;
    public static final int TAG_GAMMA_BLUE = 19;
    public static final int TAG_GAMMA_GREEN = 18;
    public static final int TAG_GAMMA_RED = 17;
    public static final int TAG_GREEN_MASK = 13;
    public static final int TAG_HEADER_SIZE = -1;
    public static final int TAG_IMAGE_HEIGHT = 1;
    public static final int TAG_IMAGE_WIDTH = 2;
    public static final int TAG_IMPORTANT_COLOUR_COUNT = 9;
    public static final int TAG_INTENT = 20;
    public static final int TAG_LINKED_PROFILE = 21;
    public static final int TAG_PALETTE_COLOUR_COUNT = 8;
    public static final int TAG_RED_MASK = 12;
    public static final int TAG_RENDERING = 10;
    public static final int TAG_X_PIXELS_PER_METER = 6;
    public static final int TAG_Y_PIXELS_PER_METER = 7;
    @NotNull
    protected static final HashMap<Integer, String> _tagNameMap = new HashMap();

    /* renamed from: com.drew.metadata.bmp.BmpHeaderDirectory$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$ColorSpaceType = new int[ColorSpaceType.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$RenderingHalftoningAlgorithm = new int[RenderingHalftoningAlgorithm.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$RenderingIntent = new int[RenderingIntent.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:20:?, code:
            $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$ColorSpaceType[com.drew.metadata.bmp.BmpHeaderDirectory.ColorSpaceType.PROFILE_LINKED.ordinal()] = 4;
     */
        /* JADX WARNING: Missing block: B:32:?, code:
            $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$RenderingHalftoningAlgorithm[com.drew.metadata.bmp.BmpHeaderDirectory.RenderingHalftoningAlgorithm.SUPER_CIRCLE.ordinal()] = 4;
     */
        /* JADX WARNING: Missing block: B:43:?, code:
            $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$Compression[com.drew.metadata.bmp.BmpHeaderDirectory.Compression.BI_HUFFMAN_1D.ordinal()] = 5;
     */
        /* JADX WARNING: Missing block: B:59:0x012f, code:
            $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$BitmapType = new int[com.drew.metadata.bmp.BmpHeaderDirectory.BitmapType.values().length];
     */
        /* JADX WARNING: Missing block: B:61:?, code:
            $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$BitmapType[com.drew.metadata.bmp.BmpHeaderDirectory.BitmapType.BITMAP.ordinal()] = 1;
     */
        /* JADX WARNING: Missing block: B:63:?, code:
            $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$BitmapType[com.drew.metadata.bmp.BmpHeaderDirectory.BitmapType.OS2_BITMAP_ARRAY.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:65:?, code:
            $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$BitmapType[com.drew.metadata.bmp.BmpHeaderDirectory.BitmapType.OS2_COLOR_ICON.ordinal()] = 3;
     */
        /* JADX WARNING: Missing block: B:67:?, code:
            $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$BitmapType[com.drew.metadata.bmp.BmpHeaderDirectory.BitmapType.OS2_COLOR_POINTER.ordinal()] = 4;
     */
        /* JADX WARNING: Missing block: B:69:?, code:
            $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$BitmapType[com.drew.metadata.bmp.BmpHeaderDirectory.BitmapType.OS2_ICON.ordinal()] = 5;
     */
        /* JADX WARNING: Missing block: B:71:?, code:
            $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$BitmapType[com.drew.metadata.bmp.BmpHeaderDirectory.BitmapType.OS2_POINTER.ordinal()] = 6;
     */
        /* JADX WARNING: Missing block: B:72:0x0174, code:
            return;
     */
        static {
            /*
            r0 = com.drew.metadata.bmp.BmpHeaderDirectory.RenderingIntent.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$RenderingIntent = r0;
            r0 = 1;
            r1 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$RenderingIntent;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = com.drew.metadata.bmp.BmpHeaderDirectory.RenderingIntent.LCS_GM_BUSINESS;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = r2.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1[r2] = r0;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r1 = 2;
            r2 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$RenderingIntent;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = com.drew.metadata.bmp.BmpHeaderDirectory.RenderingIntent.LCS_GM_GRAPHICS;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2[r3] = r1;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r2 = 3;
            r3 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$RenderingIntent;	 Catch:{ NoSuchFieldError -> 0x002a }
            r4 = com.drew.metadata.bmp.BmpHeaderDirectory.RenderingIntent.LCS_GM_IMAGES;	 Catch:{ NoSuchFieldError -> 0x002a }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r3[r4] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r3 = 4;
            r4 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$RenderingIntent;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r5 = com.drew.metadata.bmp.BmpHeaderDirectory.RenderingIntent.LCS_GM_ABS_COLORIMETRIC;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r4[r5] = r3;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            r4 = com.drew.metadata.bmp.BmpHeaderDirectory.ColorSpaceType.values();
            r4 = r4.length;
            r4 = new int[r4];
            $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$ColorSpaceType = r4;
            r4 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$ColorSpaceType;	 Catch:{ NoSuchFieldError -> 0x0048 }
            r5 = com.drew.metadata.bmp.BmpHeaderDirectory.ColorSpaceType.LCS_CALIBRATED_RGB;	 Catch:{ NoSuchFieldError -> 0x0048 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0048 }
            r4[r5] = r0;	 Catch:{ NoSuchFieldError -> 0x0048 }
        L_0x0048:
            r4 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$ColorSpaceType;	 Catch:{ NoSuchFieldError -> 0x0052 }
            r5 = com.drew.metadata.bmp.BmpHeaderDirectory.ColorSpaceType.LCS_sRGB;	 Catch:{ NoSuchFieldError -> 0x0052 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0052 }
            r4[r5] = r1;	 Catch:{ NoSuchFieldError -> 0x0052 }
        L_0x0052:
            r4 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$ColorSpaceType;	 Catch:{ NoSuchFieldError -> 0x005c }
            r5 = com.drew.metadata.bmp.BmpHeaderDirectory.ColorSpaceType.LCS_WINDOWS_COLOR_SPACE;	 Catch:{ NoSuchFieldError -> 0x005c }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x005c }
            r4[r5] = r2;	 Catch:{ NoSuchFieldError -> 0x005c }
        L_0x005c:
            r4 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$ColorSpaceType;	 Catch:{ NoSuchFieldError -> 0x0066 }
            r5 = com.drew.metadata.bmp.BmpHeaderDirectory.ColorSpaceType.PROFILE_LINKED;	 Catch:{ NoSuchFieldError -> 0x0066 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0066 }
            r4[r5] = r3;	 Catch:{ NoSuchFieldError -> 0x0066 }
        L_0x0066:
            r4 = 5;
            r5 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$ColorSpaceType;	 Catch:{ NoSuchFieldError -> 0x0071 }
            r6 = com.drew.metadata.bmp.BmpHeaderDirectory.ColorSpaceType.PROFILE_EMBEDDED;	 Catch:{ NoSuchFieldError -> 0x0071 }
            r6 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x0071 }
            r5[r6] = r4;	 Catch:{ NoSuchFieldError -> 0x0071 }
        L_0x0071:
            r5 = com.drew.metadata.bmp.BmpHeaderDirectory.RenderingHalftoningAlgorithm.values();
            r5 = r5.length;
            r5 = new int[r5];
            $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$RenderingHalftoningAlgorithm = r5;
            r5 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$RenderingHalftoningAlgorithm;	 Catch:{ NoSuchFieldError -> 0x0084 }
            r6 = com.drew.metadata.bmp.BmpHeaderDirectory.RenderingHalftoningAlgorithm.NONE;	 Catch:{ NoSuchFieldError -> 0x0084 }
            r6 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x0084 }
            r5[r6] = r0;	 Catch:{ NoSuchFieldError -> 0x0084 }
        L_0x0084:
            r5 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$RenderingHalftoningAlgorithm;	 Catch:{ NoSuchFieldError -> 0x008e }
            r6 = com.drew.metadata.bmp.BmpHeaderDirectory.RenderingHalftoningAlgorithm.ERROR_DIFFUSION;	 Catch:{ NoSuchFieldError -> 0x008e }
            r6 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x008e }
            r5[r6] = r1;	 Catch:{ NoSuchFieldError -> 0x008e }
        L_0x008e:
            r5 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$RenderingHalftoningAlgorithm;	 Catch:{ NoSuchFieldError -> 0x0098 }
            r6 = com.drew.metadata.bmp.BmpHeaderDirectory.RenderingHalftoningAlgorithm.PANDA;	 Catch:{ NoSuchFieldError -> 0x0098 }
            r6 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x0098 }
            r5[r6] = r2;	 Catch:{ NoSuchFieldError -> 0x0098 }
        L_0x0098:
            r5 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$RenderingHalftoningAlgorithm;	 Catch:{ NoSuchFieldError -> 0x00a2 }
            r6 = com.drew.metadata.bmp.BmpHeaderDirectory.RenderingHalftoningAlgorithm.SUPER_CIRCLE;	 Catch:{ NoSuchFieldError -> 0x00a2 }
            r6 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x00a2 }
            r5[r6] = r3;	 Catch:{ NoSuchFieldError -> 0x00a2 }
        L_0x00a2:
            r5 = com.drew.metadata.bmp.BmpHeaderDirectory.Compression.values();
            r5 = r5.length;
            r5 = new int[r5];
            $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$Compression = r5;
            r5 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$Compression;	 Catch:{ NoSuchFieldError -> 0x00b5 }
            r6 = com.drew.metadata.bmp.BmpHeaderDirectory.Compression.BI_RGB;	 Catch:{ NoSuchFieldError -> 0x00b5 }
            r6 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x00b5 }
            r5[r6] = r0;	 Catch:{ NoSuchFieldError -> 0x00b5 }
        L_0x00b5:
            r5 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$Compression;	 Catch:{ NoSuchFieldError -> 0x00bf }
            r6 = com.drew.metadata.bmp.BmpHeaderDirectory.Compression.BI_RLE8;	 Catch:{ NoSuchFieldError -> 0x00bf }
            r6 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x00bf }
            r5[r6] = r1;	 Catch:{ NoSuchFieldError -> 0x00bf }
        L_0x00bf:
            r5 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$Compression;	 Catch:{ NoSuchFieldError -> 0x00c9 }
            r6 = com.drew.metadata.bmp.BmpHeaderDirectory.Compression.BI_RLE4;	 Catch:{ NoSuchFieldError -> 0x00c9 }
            r6 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x00c9 }
            r5[r6] = r2;	 Catch:{ NoSuchFieldError -> 0x00c9 }
        L_0x00c9:
            r5 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$Compression;	 Catch:{ NoSuchFieldError -> 0x00d3 }
            r6 = com.drew.metadata.bmp.BmpHeaderDirectory.Compression.BI_BITFIELDS;	 Catch:{ NoSuchFieldError -> 0x00d3 }
            r6 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x00d3 }
            r5[r6] = r3;	 Catch:{ NoSuchFieldError -> 0x00d3 }
        L_0x00d3:
            r5 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$Compression;	 Catch:{ NoSuchFieldError -> 0x00dd }
            r6 = com.drew.metadata.bmp.BmpHeaderDirectory.Compression.BI_HUFFMAN_1D;	 Catch:{ NoSuchFieldError -> 0x00dd }
            r6 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x00dd }
            r5[r6] = r4;	 Catch:{ NoSuchFieldError -> 0x00dd }
        L_0x00dd:
            r5 = 6;
            r6 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$Compression;	 Catch:{ NoSuchFieldError -> 0x00e8 }
            r7 = com.drew.metadata.bmp.BmpHeaderDirectory.Compression.BI_JPEG;	 Catch:{ NoSuchFieldError -> 0x00e8 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x00e8 }
            r6[r7] = r5;	 Catch:{ NoSuchFieldError -> 0x00e8 }
        L_0x00e8:
            r6 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$Compression;	 Catch:{ NoSuchFieldError -> 0x00f3 }
            r7 = com.drew.metadata.bmp.BmpHeaderDirectory.Compression.BI_RLE24;	 Catch:{ NoSuchFieldError -> 0x00f3 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x00f3 }
            r8 = 7;
            r6[r7] = r8;	 Catch:{ NoSuchFieldError -> 0x00f3 }
        L_0x00f3:
            r6 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$Compression;	 Catch:{ NoSuchFieldError -> 0x00ff }
            r7 = com.drew.metadata.bmp.BmpHeaderDirectory.Compression.BI_PNG;	 Catch:{ NoSuchFieldError -> 0x00ff }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x00ff }
            r8 = 8;
            r6[r7] = r8;	 Catch:{ NoSuchFieldError -> 0x00ff }
        L_0x00ff:
            r6 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$Compression;	 Catch:{ NoSuchFieldError -> 0x010b }
            r7 = com.drew.metadata.bmp.BmpHeaderDirectory.Compression.BI_ALPHABITFIELDS;	 Catch:{ NoSuchFieldError -> 0x010b }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x010b }
            r8 = 9;
            r6[r7] = r8;	 Catch:{ NoSuchFieldError -> 0x010b }
        L_0x010b:
            r6 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$Compression;	 Catch:{ NoSuchFieldError -> 0x0117 }
            r7 = com.drew.metadata.bmp.BmpHeaderDirectory.Compression.BI_CMYK;	 Catch:{ NoSuchFieldError -> 0x0117 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0117 }
            r8 = 10;
            r6[r7] = r8;	 Catch:{ NoSuchFieldError -> 0x0117 }
        L_0x0117:
            r6 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$Compression;	 Catch:{ NoSuchFieldError -> 0x0123 }
            r7 = com.drew.metadata.bmp.BmpHeaderDirectory.Compression.BI_CMYKRLE8;	 Catch:{ NoSuchFieldError -> 0x0123 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0123 }
            r8 = 11;
            r6[r7] = r8;	 Catch:{ NoSuchFieldError -> 0x0123 }
        L_0x0123:
            r6 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$Compression;	 Catch:{ NoSuchFieldError -> 0x012f }
            r7 = com.drew.metadata.bmp.BmpHeaderDirectory.Compression.BI_CMYKRLE4;	 Catch:{ NoSuchFieldError -> 0x012f }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x012f }
            r8 = 12;
            r6[r7] = r8;	 Catch:{ NoSuchFieldError -> 0x012f }
        L_0x012f:
            r6 = com.drew.metadata.bmp.BmpHeaderDirectory.BitmapType.values();
            r6 = r6.length;
            r6 = new int[r6];
            $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$BitmapType = r6;
            r6 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$BitmapType;	 Catch:{ NoSuchFieldError -> 0x0142 }
            r7 = com.drew.metadata.bmp.BmpHeaderDirectory.BitmapType.BITMAP;	 Catch:{ NoSuchFieldError -> 0x0142 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0142 }
            r6[r7] = r0;	 Catch:{ NoSuchFieldError -> 0x0142 }
        L_0x0142:
            r0 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$BitmapType;	 Catch:{ NoSuchFieldError -> 0x014c }
            r6 = com.drew.metadata.bmp.BmpHeaderDirectory.BitmapType.OS2_BITMAP_ARRAY;	 Catch:{ NoSuchFieldError -> 0x014c }
            r6 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x014c }
            r0[r6] = r1;	 Catch:{ NoSuchFieldError -> 0x014c }
        L_0x014c:
            r0 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$BitmapType;	 Catch:{ NoSuchFieldError -> 0x0156 }
            r1 = com.drew.metadata.bmp.BmpHeaderDirectory.BitmapType.OS2_COLOR_ICON;	 Catch:{ NoSuchFieldError -> 0x0156 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0156 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0156 }
        L_0x0156:
            r0 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$BitmapType;	 Catch:{ NoSuchFieldError -> 0x0160 }
            r1 = com.drew.metadata.bmp.BmpHeaderDirectory.BitmapType.OS2_COLOR_POINTER;	 Catch:{ NoSuchFieldError -> 0x0160 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0160 }
            r0[r1] = r3;	 Catch:{ NoSuchFieldError -> 0x0160 }
        L_0x0160:
            r0 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$BitmapType;	 Catch:{ NoSuchFieldError -> 0x016a }
            r1 = com.drew.metadata.bmp.BmpHeaderDirectory.BitmapType.OS2_ICON;	 Catch:{ NoSuchFieldError -> 0x016a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x016a }
            r0[r1] = r4;	 Catch:{ NoSuchFieldError -> 0x016a }
        L_0x016a:
            r0 = $SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$BitmapType;	 Catch:{ NoSuchFieldError -> 0x0174 }
            r1 = com.drew.metadata.bmp.BmpHeaderDirectory.BitmapType.OS2_POINTER;	 Catch:{ NoSuchFieldError -> 0x0174 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0174 }
            r0[r1] = r5;	 Catch:{ NoSuchFieldError -> 0x0174 }
        L_0x0174:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.drew.metadata.bmp.BmpHeaderDirectory.1.<clinit>():void");
        }
    }

    public enum BitmapType {
        BITMAP(BmpReader.BITMAP),
        OS2_BITMAP_ARRAY(BmpReader.OS2_BITMAP_ARRAY),
        OS2_ICON(BmpReader.OS2_ICON),
        OS2_COLOR_ICON(BmpReader.OS2_COLOR_ICON),
        OS2_COLOR_POINTER(BmpReader.OS2_COLOR_POINTER),
        OS2_POINTER(BmpReader.OS2_POINTER);
        
        private final int value;

        private BitmapType(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }

        @Nullable
        public static BitmapType typeOf(int i) {
            for (BitmapType bitmapType : values()) {
                if (bitmapType.value == i) {
                    return bitmapType;
                }
            }
            return null;
        }

        @NotNull
        public String toString() {
            switch (this) {
                case BITMAP:
                    return "Standard";
                case OS2_BITMAP_ARRAY:
                    return "Bitmap Array";
                case OS2_COLOR_ICON:
                    return "Color Icon";
                case OS2_COLOR_POINTER:
                    return "Color Pointer";
                case OS2_ICON:
                    return "Monochrome Icon";
                case OS2_POINTER:
                    return "Monochrome Pointer";
                default:
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unimplemented bitmap type ");
                    stringBuilder.append(super.toString());
                    throw new IllegalStateException(stringBuilder.toString());
            }
        }
    }

    public enum ColorEncoding {
        RGB(0);
        
        private final int value;

        private ColorEncoding(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }

        @Nullable
        public static ColorEncoding typeOf(int i) {
            return i == 0 ? RGB : null;
        }
    }

    public enum ColorSpaceType {
        LCS_CALIBRATED_RGB(0),
        LCS_sRGB(1934772034),
        LCS_WINDOWS_COLOR_SPACE(1466527264),
        PROFILE_LINKED(1279872587),
        PROFILE_EMBEDDED(1296188740);
        
        private final long value;

        private ColorSpaceType(long j) {
            this.value = j;
        }

        public long getValue() {
            return this.value;
        }

        @Nullable
        public static ColorSpaceType typeOf(long j) {
            for (ColorSpaceType colorSpaceType : values()) {
                if (colorSpaceType.value == j) {
                    return colorSpaceType;
                }
            }
            return null;
        }

        @NotNull
        public String toString() {
            int i = AnonymousClass1.$SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$ColorSpaceType[ordinal()];
            if (i == 1) {
                return "Calibrated RGB";
            }
            if (i == 2) {
                return "sRGB Color Space";
            }
            if (i == 3) {
                return "System Default Color Space, sRGB";
            }
            if (i == 4) {
                return "Linked Profile";
            }
            if (i == 5) {
                return "Embedded Profile";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unimplemented color space type ");
            stringBuilder.append(super.toString());
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public enum Compression {
        BI_RGB(0),
        BI_RLE8(1),
        BI_RLE4(2),
        BI_BITFIELDS(3),
        BI_HUFFMAN_1D(3),
        BI_JPEG(4),
        BI_RLE24(4),
        BI_PNG(5),
        BI_ALPHABITFIELDS(6),
        BI_CMYK(11),
        BI_CMYKRLE8(12),
        BI_CMYKRLE4(13);
        
        private final int value;

        private Compression(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }

        @Nullable
        public static Compression typeOf(@NotNull BmpHeaderDirectory bmpHeaderDirectory) {
            Integer integer = bmpHeaderDirectory.getInteger(5);
            if (integer == null) {
                return null;
            }
            Integer integer2 = bmpHeaderDirectory.getInteger(-1);
            if (integer2 == null) {
                return null;
            }
            return typeOf(integer.intValue(), integer2.intValue());
        }

        @Nullable
        public static Compression typeOf(int i, int i2) {
            switch (i) {
                case 0:
                    return BI_RGB;
                case 1:
                    return BI_RLE8;
                case 2:
                    return BI_RLE4;
                case 3:
                    return i2 == 64 ? BI_HUFFMAN_1D : BI_BITFIELDS;
                case 4:
                    return i2 == 64 ? BI_RLE24 : BI_JPEG;
                case 5:
                    return BI_PNG;
                case 6:
                    return BI_ALPHABITFIELDS;
                case 11:
                    return BI_CMYK;
                case 12:
                    return BI_CMYKRLE8;
                case 13:
                    return BI_CMYKRLE4;
                default:
                    return null;
            }
        }

        @NotNull
        public String toString() {
            switch (this) {
                case BI_RGB:
                    return "None";
                case BI_RLE8:
                    return "RLE 8-bit/pixel";
                case BI_RLE4:
                    return "RLE 4-bit/pixel";
                case BI_BITFIELDS:
                    return "Bit Fields";
                case BI_HUFFMAN_1D:
                    return "Huffman 1D";
                case BI_JPEG:
                    return "JPEG";
                case BI_RLE24:
                    return "RLE 24-bit/pixel";
                case BI_PNG:
                    return "PNG";
                case BI_ALPHABITFIELDS:
                    return "RGBA Bit Fields";
                case BI_CMYK:
                    return "CMYK Uncompressed";
                case BI_CMYKRLE8:
                    return "CMYK RLE-8";
                case BI_CMYKRLE4:
                    return "CMYK RLE-4";
                default:
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unimplemented compression type ");
                    stringBuilder.append(super.toString());
                    throw new IllegalStateException(stringBuilder.toString());
            }
        }
    }

    public enum RenderingHalftoningAlgorithm {
        NONE(0),
        ERROR_DIFFUSION(1),
        PANDA(2),
        SUPER_CIRCLE(3);
        
        private final int value;

        private RenderingHalftoningAlgorithm(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }

        @Nullable
        public static RenderingHalftoningAlgorithm typeOf(int i) {
            for (RenderingHalftoningAlgorithm renderingHalftoningAlgorithm : values()) {
                if (renderingHalftoningAlgorithm.value == i) {
                    return renderingHalftoningAlgorithm;
                }
            }
            return null;
        }

        @NotNull
        public String toString() {
            int i = AnonymousClass1.$SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$RenderingHalftoningAlgorithm[ordinal()];
            if (i == 1) {
                return "No Halftoning Algorithm";
            }
            if (i == 2) {
                return "Error Diffusion Halftoning";
            }
            if (i == 3) {
                return "Processing Algorithm for Noncoded Document Acquisition";
            }
            if (i == 4) {
                return "Super-circle Halftoning";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unimplemented rendering halftoning algorithm type ");
            stringBuilder.append(super.toString());
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public enum RenderingIntent {
        LCS_GM_BUSINESS(1),
        LCS_GM_GRAPHICS(2),
        LCS_GM_IMAGES(4),
        LCS_GM_ABS_COLORIMETRIC(8);
        
        private final int value;

        private RenderingIntent(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }

        @Nullable
        public static RenderingIntent typeOf(long j) {
            for (RenderingIntent renderingIntent : values()) {
                if (((long) renderingIntent.value) == j) {
                    return renderingIntent;
                }
            }
            return null;
        }

        @NotNull
        public String toString() {
            int i = AnonymousClass1.$SwitchMap$com$drew$metadata$bmp$BmpHeaderDirectory$RenderingIntent[ordinal()];
            if (i == 1) {
                return "Graphic, Saturation";
            }
            if (i == 2) {
                return "Proof, Relative Colorimetric";
            }
            if (i == 3) {
                return "Picture, Perceptual";
            }
            if (i == 4) {
                return "Match, Absolute Colorimetric";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unimplemented rendering intent ");
            stringBuilder.append(super.toString());
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    @NotNull
    public String getName() {
        return "BMP Header";
    }

    static {
        _tagNameMap.put(Integer.valueOf(-2), "Bitmap type");
        _tagNameMap.put(Integer.valueOf(-1), "Header Size");
        _tagNameMap.put(Integer.valueOf(1), "Image Height");
        _tagNameMap.put(Integer.valueOf(2), "Image Width");
        _tagNameMap.put(Integer.valueOf(3), "Planes");
        _tagNameMap.put(Integer.valueOf(4), "Bits Per Pixel");
        _tagNameMap.put(Integer.valueOf(5), ExifInterface.TAG_COMPRESSION);
        _tagNameMap.put(Integer.valueOf(6), "X Pixels per Meter");
        _tagNameMap.put(Integer.valueOf(7), "Y Pixels per Meter");
        _tagNameMap.put(Integer.valueOf(8), "Palette Colour Count");
        _tagNameMap.put(Integer.valueOf(9), "Important Colour Count");
        _tagNameMap.put(Integer.valueOf(10), "Rendering");
        _tagNameMap.put(Integer.valueOf(11), "Color Encoding");
        _tagNameMap.put(Integer.valueOf(12), "Red Mask");
        _tagNameMap.put(Integer.valueOf(13), "Green Mask");
        _tagNameMap.put(Integer.valueOf(14), "Blue Mask");
        _tagNameMap.put(Integer.valueOf(15), "Alpha Mask");
        _tagNameMap.put(Integer.valueOf(16), "Color Space Type");
        _tagNameMap.put(Integer.valueOf(17), "Red Gamma Curve");
        _tagNameMap.put(Integer.valueOf(18), "Green Gamma Curve");
        _tagNameMap.put(Integer.valueOf(19), "Blue Gamma Curve");
        _tagNameMap.put(Integer.valueOf(20), "Rendering Intent");
        _tagNameMap.put(Integer.valueOf(21), "Linked Profile File Name");
    }

    public BmpHeaderDirectory() {
        setDescriptor(new BmpHeaderDescriptor(this));
    }

    @Nullable
    public BitmapType getBitmapType() {
        Integer integer = getInteger(-2);
        if (integer == null) {
            return null;
        }
        return BitmapType.typeOf(integer.intValue());
    }

    @Nullable
    public Compression getCompression() {
        return Compression.typeOf(this);
    }

    @Nullable
    public RenderingHalftoningAlgorithm getRendering() {
        Integer integer = getInteger(10);
        if (integer == null) {
            return null;
        }
        return RenderingHalftoningAlgorithm.typeOf(integer.intValue());
    }

    @Nullable
    public ColorEncoding getColorEncoding() {
        Integer integer = getInteger(11);
        if (integer == null) {
            return null;
        }
        return ColorEncoding.typeOf(integer.intValue());
    }

    @Nullable
    public ColorSpaceType getColorSpaceType() {
        Long longObject = getLongObject(16);
        if (longObject == null) {
            return null;
        }
        return ColorSpaceType.typeOf(longObject.longValue());
    }

    @Nullable
    public RenderingIntent getRenderingIntent() {
        Integer integer = getInteger(20);
        if (integer == null) {
            return null;
        }
        return RenderingIntent.typeOf((long) integer.intValue());
    }

    @NotNull
    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }
}
