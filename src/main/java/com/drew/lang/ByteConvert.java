package com.drew.lang;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.drew.lang.annotations.NotNull;

public class ByteConvert {
    public static int toInt32BigEndian(@NotNull byte[] bArr) {
        return (bArr[3] & 255) | ((((bArr[0] << 24) & ViewCompat.MEASURED_STATE_MASK) | ((bArr[1] << 16) & 16711680)) | ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK));
    }

    public static int toInt32LittleEndian(@NotNull byte[] bArr) {
        return ((bArr[3] << 24) & ViewCompat.MEASURED_STATE_MASK) | (((bArr[0] & 255) | ((bArr[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | ((bArr[2] << 16) & 16711680));
    }
}
