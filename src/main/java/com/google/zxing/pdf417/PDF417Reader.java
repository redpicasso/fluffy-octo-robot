package com.google.zxing.pdf417;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.multi.MultipleBarcodeReader;
import com.google.zxing.pdf417.decoder.PDF417ScanningDecoder;
import com.google.zxing.pdf417.detector.Detector;
import com.google.zxing.pdf417.detector.PDF417DetectorResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class PDF417Reader implements Reader, MultipleBarcodeReader {
    public void reset() {
    }

    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException, ChecksumException {
        return decode(binaryBitmap, null);
    }

    public Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException, ChecksumException {
        Result[] decode = decode(binaryBitmap, map, false);
        if (decode != null && decode.length != 0 && decode[0] != null) {
            return decode[0];
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return decodeMultiple(binaryBitmap, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:4:0x0006 A:{ExcHandler: com.google.zxing.FormatException (unused com.google.zxing.FormatException), Splitter: B:1:0x0001} */
    /* JADX WARNING: Missing block: B:5:0x000a, code:
            throw com.google.zxing.NotFoundException.getNotFoundInstance();
     */
    public com.google.zxing.Result[] decodeMultiple(com.google.zxing.BinaryBitmap r2, java.util.Map<com.google.zxing.DecodeHintType, ?> r3) throws com.google.zxing.NotFoundException {
        /*
        r1 = this;
        r0 = 1;
        r2 = decode(r2, r3, r0);	 Catch:{ FormatException -> 0x0006, FormatException -> 0x0006 }
        return r2;
    L_0x0006:
        r2 = com.google.zxing.NotFoundException.getNotFoundInstance();
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.PDF417Reader.decodeMultiple(com.google.zxing.BinaryBitmap, java.util.Map):com.google.zxing.Result[]");
    }

    private static Result[] decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, boolean z) throws NotFoundException, FormatException, ChecksumException {
        List arrayList = new ArrayList();
        PDF417DetectorResult detect = Detector.detect(binaryBitmap, map, z);
        for (ResultPoint[] resultPointArr : detect.getPoints()) {
            DecoderResult decode = PDF417ScanningDecoder.decode(detect.getBits(), resultPointArr[4], resultPointArr[5], resultPointArr[6], resultPointArr[7], getMinCodewordWidth(resultPointArr), getMaxCodewordWidth(resultPointArr));
            Result result = new Result(decode.getText(), decode.getRawBytes(), resultPointArr, BarcodeFormat.PDF_417);
            result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, decode.getECLevel());
            PDF417ResultMetadata pDF417ResultMetadata = (PDF417ResultMetadata) decode.getOther();
            if (pDF417ResultMetadata != null) {
                result.putMetadata(ResultMetadataType.PDF417_EXTRA_METADATA, pDF417ResultMetadata);
            }
            arrayList.add(result);
        }
        return (Result[]) arrayList.toArray(new Result[arrayList.size()]);
    }

    private static int getMaxWidth(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return (resultPoint == null || resultPoint2 == null) ? 0 : (int) Math.abs(resultPoint.getX() - resultPoint2.getX());
    }

    private static int getMinWidth(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return (resultPoint == null || resultPoint2 == null) ? Integer.MAX_VALUE : (int) Math.abs(resultPoint.getX() - resultPoint2.getX());
    }

    private static int getMaxCodewordWidth(ResultPoint[] resultPointArr) {
        return Math.max(Math.max(getMaxWidth(resultPointArr[0], resultPointArr[4]), (getMaxWidth(resultPointArr[6], resultPointArr[2]) * 17) / 18), Math.max(getMaxWidth(resultPointArr[1], resultPointArr[5]), (getMaxWidth(resultPointArr[7], resultPointArr[3]) * 17) / 18));
    }

    private static int getMinCodewordWidth(ResultPoint[] resultPointArr) {
        return Math.min(Math.min(getMinWidth(resultPointArr[0], resultPointArr[4]), (getMinWidth(resultPointArr[6], resultPointArr[2]) * 17) / 18), Math.min(getMinWidth(resultPointArr[1], resultPointArr[5]), (getMinWidth(resultPointArr[7], resultPointArr[3]) * 17) / 18));
    }
}
