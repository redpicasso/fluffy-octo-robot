package com.facebook.imageutils;

import com.facebook.common.logging.FLog;
import java.io.IOException;
import java.io.InputStream;

class TiffUtil {
    private static final Class<?> TAG = TiffUtil.class;
    public static final int TIFF_BYTE_ORDER_BIG_END = 1296891946;
    public static final int TIFF_BYTE_ORDER_LITTLE_END = 1229531648;
    public static final int TIFF_TAG_ORIENTATION = 274;
    public static final int TIFF_TYPE_SHORT = 3;

    private static class TiffHeader {
        int byteOrder;
        int firstIfdOffset;
        boolean isLittleEndian;

        private TiffHeader() {
        }
    }

    public static int getAutoRotateAngleFromOrientation(int i) {
        return (i == 0 || i == 1) ? 0 : i != 3 ? i != 6 ? i != 8 ? 0 : 270 : 90 : 180;
    }

    TiffUtil() {
    }

    public static int readOrientationFromTIFF(InputStream inputStream, int i) throws IOException {
        TiffHeader tiffHeader = new TiffHeader();
        i = readTiffHeader(inputStream, i, tiffHeader);
        int i2 = tiffHeader.firstIfdOffset - 8;
        if (i == 0 || i2 > i) {
            return 0;
        }
        inputStream.skip((long) i2);
        return getOrientationFromTiffEntry(inputStream, moveToTiffEntryWithTag(inputStream, i - i2, tiffHeader.isLittleEndian, 274), tiffHeader.isLittleEndian);
    }

    private static int readTiffHeader(InputStream inputStream, int i, TiffHeader tiffHeader) throws IOException {
        if (i <= 8) {
            return 0;
        }
        tiffHeader.byteOrder = StreamProcessor.readPackedInt(inputStream, 4, false);
        i -= 4;
        if (tiffHeader.byteOrder == TIFF_BYTE_ORDER_LITTLE_END || tiffHeader.byteOrder == TIFF_BYTE_ORDER_BIG_END) {
            tiffHeader.isLittleEndian = tiffHeader.byteOrder == TIFF_BYTE_ORDER_LITTLE_END;
            tiffHeader.firstIfdOffset = StreamProcessor.readPackedInt(inputStream, 4, tiffHeader.isLittleEndian);
            i -= 4;
            if (tiffHeader.firstIfdOffset >= 8 && tiffHeader.firstIfdOffset - 8 <= i) {
                return i;
            }
            FLog.e(TAG, "Invalid offset");
            return 0;
        }
        FLog.e(TAG, "Invalid TIFF header");
        return 0;
    }

    private static int moveToTiffEntryWithTag(InputStream inputStream, int i, boolean z, int i2) throws IOException {
        if (i < 14) {
            return 0;
        }
        int readPackedInt = StreamProcessor.readPackedInt(inputStream, 2, z);
        i -= 2;
        while (true) {
            int i3 = readPackedInt - 1;
            if (readPackedInt <= 0 || i < 12) {
                return 0;
            }
            i -= 2;
            if (StreamProcessor.readPackedInt(inputStream, 2, z) == i2) {
                return i;
            }
            inputStream.skip(10);
            i -= 10;
            readPackedInt = i3;
        }
        return 0;
    }

    private static int getOrientationFromTiffEntry(InputStream inputStream, int i, boolean z) throws IOException {
        if (i < 10 || StreamProcessor.readPackedInt(inputStream, 2, z) != 3 || StreamProcessor.readPackedInt(inputStream, 4, z) != 1) {
            return 0;
        }
        int readPackedInt = StreamProcessor.readPackedInt(inputStream, 2, z);
        StreamProcessor.readPackedInt(inputStream, 2, z);
        return readPackedInt;
    }
}
