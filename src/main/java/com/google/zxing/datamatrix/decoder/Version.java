package com.google.zxing.datamatrix.decoder;

import com.drew.metadata.exif.makernotes.NikonType2MakernoteDirectory;
import com.google.zxing.FormatException;

public final class Version {
    private static final Version[] VERSIONS = buildVersions();
    private final int dataRegionSizeColumns;
    private final int dataRegionSizeRows;
    private final ECBlocks ecBlocks;
    private final int symbolSizeColumns;
    private final int symbolSizeRows;
    private final int totalCodewords;
    private final int versionNumber;

    static final class ECB {
        private final int count;
        private final int dataCodewords;

        private ECB(int i, int i2) {
            this.count = i;
            this.dataCodewords = i2;
        }

        int getCount() {
            return this.count;
        }

        int getDataCodewords() {
            return this.dataCodewords;
        }
    }

    static final class ECBlocks {
        private final ECB[] ecBlocks;
        private final int ecCodewords;

        private ECBlocks(int i, ECB ecb) {
            this.ecCodewords = i;
            this.ecBlocks = new ECB[]{ecb};
        }

        private ECBlocks(int i, ECB ecb, ECB ecb2) {
            this.ecCodewords = i;
            this.ecBlocks = new ECB[]{ecb, ecb2};
        }

        int getECCodewords() {
            return this.ecCodewords;
        }

        ECB[] getECBlocks() {
            return this.ecBlocks;
        }
    }

    private Version(int i, int i2, int i3, int i4, int i5, ECBlocks eCBlocks) {
        this.versionNumber = i;
        this.symbolSizeRows = i2;
        this.symbolSizeColumns = i3;
        this.dataRegionSizeRows = i4;
        this.dataRegionSizeColumns = i5;
        this.ecBlocks = eCBlocks;
        i = eCBlocks.getECCodewords();
        i5 = 0;
        for (ECB ecb : eCBlocks.getECBlocks()) {
            i5 += ecb.getCount() * (ecb.getDataCodewords() + i);
        }
        this.totalCodewords = i5;
    }

    public int getVersionNumber() {
        return this.versionNumber;
    }

    public int getSymbolSizeRows() {
        return this.symbolSizeRows;
    }

    public int getSymbolSizeColumns() {
        return this.symbolSizeColumns;
    }

    public int getDataRegionSizeRows() {
        return this.dataRegionSizeRows;
    }

    public int getDataRegionSizeColumns() {
        return this.dataRegionSizeColumns;
    }

    public int getTotalCodewords() {
        return this.totalCodewords;
    }

    ECBlocks getECBlocks() {
        return this.ecBlocks;
    }

    public static Version getVersionForDimensions(int i, int i2) throws FormatException {
        if ((i & 1) == 0 && (i2 & 1) == 0) {
            for (Version version : VERSIONS) {
                if (version.symbolSizeRows == i && version.symbolSizeColumns == i2) {
                    return version;
                }
            }
            throw FormatException.getFormatInstance();
        }
        throw FormatException.getFormatInstance();
    }

    public String toString() {
        return String.valueOf(this.versionNumber);
    }

    private static Version[] buildVersions() {
        r0 = new Version[30];
        r0[5] = new Version(6, 20, 20, 18, 18, new ECBlocks(18, new ECB(1, 22), null));
        r0[6] = new Version(7, 22, 22, 20, 20, new ECBlocks(20, new ECB(1, 30), null));
        r0[7] = new Version(8, 24, 24, 22, 22, new ECBlocks(24, new ECB(1, 36), null));
        r0[8] = new Version(9, 26, 26, 24, 24, new ECBlocks(28, new ECB(1, 44), null));
        r0[9] = new Version(10, 32, 32, 14, 14, new ECBlocks(36, new ECB(1, 62), null));
        r0[10] = new Version(11, 36, 36, 16, 16, new ECBlocks(42, new ECB(1, 86), null));
        r0[11] = new Version(12, 40, 40, 18, 18, new ECBlocks(48, new ECB(1, 114), null));
        r0[12] = new Version(13, 44, 44, 20, 20, new ECBlocks(56, new ECB(1, 144), null));
        r0[13] = new Version(14, 48, 48, 22, 22, new ECBlocks(68, new ECB(1, 174), null));
        r0[14] = new Version(15, 52, 52, 24, 24, new ECBlocks(42, new ECB(2, 102), null));
        r0[15] = new Version(16, 64, 64, 14, 14, new ECBlocks(56, new ECB(2, 140), null));
        r0[16] = new Version(17, 72, 72, 16, 16, new ECBlocks(36, new ECB(4, 92), null));
        r0[17] = new Version(18, 80, 80, 18, 18, new ECBlocks(48, new ECB(4, 114), null));
        r0[18] = new Version(19, 88, 88, 20, 20, new ECBlocks(56, new ECB(4, 144), null));
        r0[19] = new Version(20, 96, 96, 22, 22, new ECBlocks(68, new ECB(4, 174), null));
        r0[20] = new Version(21, 104, 104, 24, 24, new ECBlocks(56, new ECB(6, 136), null));
        r0[21] = new Version(22, 120, 120, 18, 18, new ECBlocks(68, new ECB(6, NikonType2MakernoteDirectory.TAG_UNKNOWN_30), null));
        r0[22] = new Version(23, NikonType2MakernoteDirectory.TAG_LENS, NikonType2MakernoteDirectory.TAG_LENS, 20, 20, new ECBlocks(62, new ECB(8, 163), null));
        r0[23] = new Version(24, 144, 144, 22, 22, new ECBlocks(62, new ECB(8, NikonType2MakernoteDirectory.TAG_SCENE_ASSIST), new ECB(2, NikonType2MakernoteDirectory.TAG_UNKNOWN_10)));
        r0[24] = new Version(25, 8, 18, 6, 16, new ECBlocks(7, new ECB(1, 5), null));
        r0[25] = new Version(26, 8, 32, 6, 14, new ECBlocks(11, new ECB(1, 10), null));
        r0[26] = new Version(27, 12, 26, 10, 24, new ECBlocks(14, new ECB(1, 16), null));
        r0[27] = new Version(28, 12, 36, 10, 16, new ECBlocks(18, new ECB(1, 22), null));
        r0[28] = new Version(29, 16, 36, 14, 16, new ECBlocks(24, new ECB(1, 32), null));
        r0[29] = new Version(30, 16, 48, 14, 22, new ECBlocks(28, new ECB(1, 49), null));
        return r0;
    }
}
