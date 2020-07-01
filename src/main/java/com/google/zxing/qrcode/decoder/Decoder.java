package com.google.zxing.qrcode.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import java.util.Map;

public final class Decoder {
    private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

    public DecoderResult decode(boolean[][] zArr) throws ChecksumException, FormatException {
        return decode(zArr, null);
    }

    public DecoderResult decode(boolean[][] zArr, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException {
        return decode(BitMatrix.parse(zArr), (Map) map);
    }

    public DecoderResult decode(BitMatrix bitMatrix) throws ChecksumException, FormatException {
        return decode(bitMatrix, null);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.google.zxing.qrcode.decoder.Decoder.decode(com.google.zxing.common.BitMatrix, java.util.Map):com.google.zxing.common.DecoderResult, dom blocks: []
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:89)
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:293)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:201)
        */
    public com.google.zxing.common.DecoderResult decode(com.google.zxing.common.BitMatrix r5, java.util.Map<com.google.zxing.DecodeHintType, ?> r6) throws com.google.zxing.FormatException, com.google.zxing.ChecksumException {
        /*
        r4 = this;
        r0 = new com.google.zxing.qrcode.decoder.BitMatrixParser;
        r0.<init>(r5);
        r5 = 0;
        r5 = r4.decode(r0, r6);	 Catch:{ FormatException -> 0x000d, ChecksumException -> 0x000b }
        return r5;
    L_0x000b:
        r1 = move-exception;
        goto L_0x0011;
    L_0x000d:
        r1 = move-exception;
        r3 = r1;
        r1 = r5;
        r5 = r3;
    L_0x0011:
        r0.remask();	 Catch:{ FormatException -> 0x002e, FormatException -> 0x002e }
        r2 = 1;	 Catch:{ FormatException -> 0x002e, FormatException -> 0x002e }
        r0.setMirror(r2);	 Catch:{ FormatException -> 0x002e, FormatException -> 0x002e }
        r0.readVersion();	 Catch:{ FormatException -> 0x002e, FormatException -> 0x002e }
        r0.readFormatInformation();	 Catch:{ FormatException -> 0x002e, FormatException -> 0x002e }
        r0.mirror();	 Catch:{ FormatException -> 0x002e, FormatException -> 0x002e }
        r6 = r4.decode(r0, r6);	 Catch:{ FormatException -> 0x002e, FormatException -> 0x002e }
        r0 = new com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;	 Catch:{ FormatException -> 0x002e, FormatException -> 0x002e }
        r0.<init>(r2);	 Catch:{ FormatException -> 0x002e, FormatException -> 0x002e }
        r6.setOther(r0);	 Catch:{ FormatException -> 0x002e, FormatException -> 0x002e }
        return r6;
        if (r5 == 0) goto L_0x0032;
    L_0x0031:
        throw r5;
    L_0x0032:
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.decoder.Decoder.decode(com.google.zxing.common.BitMatrix, java.util.Map):com.google.zxing.common.DecoderResult");
    }

    private DecoderResult decode(BitMatrixParser bitMatrixParser, Map<DecodeHintType, ?> map) throws FormatException, ChecksumException {
        Version readVersion = bitMatrixParser.readVersion();
        ErrorCorrectionLevel errorCorrectionLevel = bitMatrixParser.readFormatInformation().getErrorCorrectionLevel();
        DataBlock[] dataBlocks = DataBlock.getDataBlocks(bitMatrixParser.readCodewords(), readVersion, errorCorrectionLevel);
        int i = 0;
        for (DataBlock numDataCodewords : dataBlocks) {
            i += numDataCodewords.getNumDataCodewords();
        }
        byte[] bArr = new byte[i];
        int length = dataBlocks.length;
        i = 0;
        int i2 = 0;
        while (i < length) {
            DataBlock dataBlock = dataBlocks[i];
            byte[] codewords = dataBlock.getCodewords();
            int numDataCodewords2 = dataBlock.getNumDataCodewords();
            correctErrors(codewords, numDataCodewords2);
            int i3 = i2;
            i2 = 0;
            while (i2 < numDataCodewords2) {
                int i4 = i3 + 1;
                bArr[i3] = codewords[i2];
                i2++;
                i3 = i4;
            }
            i++;
            i2 = i3;
        }
        return DecodedBitStreamParser.decode(bArr, readVersion, errorCorrectionLevel, map);
    }

    private void correctErrors(byte[] bArr, int i) throws ChecksumException {
        int length = bArr.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = bArr[i2] & 255;
        }
        try {
            this.rsDecoder.decode(iArr, bArr.length - i);
            for (int i3 = 0; i3 < i; i3++) {
                bArr[i3] = (byte) iArr[i3];
            }
        } catch (ReedSolomonException unused) {
            throw ChecksumException.getChecksumInstance();
        }
    }
}
