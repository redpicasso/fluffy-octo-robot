package com.facebook.imageformat;

import com.facebook.common.internal.Ints;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.webp.WebpSupportStatus;
import com.facebook.imageformat.ImageFormat.FormatChecker;
import com.google.common.base.Ascii;
import javax.annotation.Nullable;

public class DefaultImageFormatChecker implements FormatChecker {
    private static final byte[] BMP_HEADER = ImageFormatCheckerUtils.asciiBytes("BM");
    private static final int BMP_HEADER_LENGTH = BMP_HEADER.length;
    private static final int EXTENDED_WEBP_HEADER_LENGTH = 21;
    private static final byte[] GIF_HEADER_87A = ImageFormatCheckerUtils.asciiBytes("GIF87a");
    private static final byte[] GIF_HEADER_89A = ImageFormatCheckerUtils.asciiBytes("GIF89a");
    private static final int GIF_HEADER_LENGTH = 6;
    private static final int HEIF_HEADER_LENGTH;
    private static final String HEIF_HEADER_PREFIX = "ftyp";
    private static final String[] HEIF_HEADER_SUFFIXES = new String[]{"heic", "heix", "hevc", "hevx", "mif1", "msf1"};
    private static final byte[] ICO_HEADER = new byte[]{(byte) 0, (byte) 0, (byte) 1, (byte) 0};
    private static final int ICO_HEADER_LENGTH = ICO_HEADER.length;
    private static final byte[] JPEG_HEADER = new byte[]{(byte) -1, (byte) -40, (byte) -1};
    private static final int JPEG_HEADER_LENGTH = JPEG_HEADER.length;
    private static final byte[] PNG_HEADER = new byte[]{(byte) -119, (byte) 80, (byte) 78, (byte) 71, Ascii.CR, (byte) 10, Ascii.SUB, (byte) 10};
    private static final int PNG_HEADER_LENGTH = PNG_HEADER.length;
    private static final int SIMPLE_WEBP_HEADER_LENGTH = 20;
    final int MAX_HEADER_LENGTH = Ints.max(21, 20, JPEG_HEADER_LENGTH, PNG_HEADER_LENGTH, 6, BMP_HEADER_LENGTH, ICO_HEADER_LENGTH, HEIF_HEADER_LENGTH);

    public int getHeaderSize() {
        return this.MAX_HEADER_LENGTH;
    }

    @Nullable
    public final ImageFormat determineFormat(byte[] bArr, int i) {
        Preconditions.checkNotNull(bArr);
        if (WebpSupportStatus.isWebpHeader(bArr, 0, i)) {
            return getWebpFormat(bArr, i);
        }
        if (isJpegHeader(bArr, i)) {
            return DefaultImageFormats.JPEG;
        }
        if (isPngHeader(bArr, i)) {
            return DefaultImageFormats.PNG;
        }
        if (isGifHeader(bArr, i)) {
            return DefaultImageFormats.GIF;
        }
        if (isBmpHeader(bArr, i)) {
            return DefaultImageFormats.BMP;
        }
        if (isIcoHeader(bArr, i)) {
            return DefaultImageFormats.ICO;
        }
        if (isHeifHeader(bArr, i)) {
            return DefaultImageFormats.HEIF;
        }
        return ImageFormat.UNKNOWN;
    }

    private static ImageFormat getWebpFormat(byte[] bArr, int i) {
        Preconditions.checkArgument(WebpSupportStatus.isWebpHeader(bArr, 0, i));
        if (WebpSupportStatus.isSimpleWebpHeader(bArr, 0)) {
            return DefaultImageFormats.WEBP_SIMPLE;
        }
        if (WebpSupportStatus.isLosslessWebpHeader(bArr, 0)) {
            return DefaultImageFormats.WEBP_LOSSLESS;
        }
        if (!WebpSupportStatus.isExtendedWebpHeader(bArr, 0, i)) {
            return ImageFormat.UNKNOWN;
        }
        if (WebpSupportStatus.isAnimatedWebpHeader(bArr, 0)) {
            return DefaultImageFormats.WEBP_ANIMATED;
        }
        if (WebpSupportStatus.isExtendedWebpHeaderWithAlpha(bArr, 0)) {
            return DefaultImageFormats.WEBP_EXTENDED_WITH_ALPHA;
        }
        return DefaultImageFormats.WEBP_EXTENDED;
    }

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ftyp");
        stringBuilder.append(HEIF_HEADER_SUFFIXES[0]);
        HEIF_HEADER_LENGTH = ImageFormatCheckerUtils.asciiBytes(stringBuilder.toString()).length;
    }

    private static boolean isJpegHeader(byte[] bArr, int i) {
        byte[] bArr2 = JPEG_HEADER;
        return i >= bArr2.length && ImageFormatCheckerUtils.startsWithPattern(bArr, bArr2);
    }

    private static boolean isPngHeader(byte[] bArr, int i) {
        byte[] bArr2 = PNG_HEADER;
        return i >= bArr2.length && ImageFormatCheckerUtils.startsWithPattern(bArr, bArr2);
    }

    private static boolean isGifHeader(byte[] bArr, int i) {
        boolean z = false;
        if (i < 6) {
            return false;
        }
        if (ImageFormatCheckerUtils.startsWithPattern(bArr, GIF_HEADER_87A) || ImageFormatCheckerUtils.startsWithPattern(bArr, GIF_HEADER_89A)) {
            z = true;
        }
        return z;
    }

    private static boolean isBmpHeader(byte[] bArr, int i) {
        byte[] bArr2 = BMP_HEADER;
        if (i < bArr2.length) {
            return false;
        }
        return ImageFormatCheckerUtils.startsWithPattern(bArr, bArr2);
    }

    private static boolean isIcoHeader(byte[] bArr, int i) {
        byte[] bArr2 = ICO_HEADER;
        if (i < bArr2.length) {
            return false;
        }
        return ImageFormatCheckerUtils.startsWithPattern(bArr, bArr2);
    }

    private static boolean isHeifHeader(byte[] bArr, int i) {
        if (i < HEIF_HEADER_LENGTH || bArr[3] < (byte) 8) {
            return false;
        }
        for (String str : HEIF_HEADER_SUFFIXES) {
            int length = bArr.length;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ftyp");
            stringBuilder.append(str);
            if (ImageFormatCheckerUtils.indexOfPattern(bArr, length, ImageFormatCheckerUtils.asciiBytes(stringBuilder.toString()), HEIF_HEADER_LENGTH) > -1) {
                return true;
            }
        }
        return false;
    }
}