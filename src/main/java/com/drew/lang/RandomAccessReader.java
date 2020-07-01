package com.drew.lang;

import androidx.core.view.InputDeviceCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.StringValue;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public abstract class RandomAccessReader {
    private boolean _isMotorolaByteOrder = true;

    public abstract byte getByte(int i) throws IOException;

    @NotNull
    public abstract byte[] getBytes(int i, int i2) throws IOException;

    public abstract long getLength() throws IOException;

    protected abstract boolean isValidIndex(int i, int i2) throws IOException;

    public abstract int toUnshiftedOffset(int i);

    protected abstract void validateIndex(int i, int i2) throws IOException;

    public void setMotorolaByteOrder(boolean z) {
        this._isMotorolaByteOrder = z;
    }

    public boolean isMotorolaByteOrder() {
        return this._isMotorolaByteOrder;
    }

    public boolean getBit(int i) throws IOException {
        int i2 = i / 8;
        i %= 8;
        validateIndex(i2, 1);
        if (((getByte(i2) >> i) & 1) == 1) {
            return true;
        }
        return false;
    }

    public short getUInt8(int i) throws IOException {
        validateIndex(i, 1);
        return (short) (getByte(i) & 255);
    }

    public byte getInt8(int i) throws IOException {
        validateIndex(i, 1);
        return getByte(i);
    }

    public int getUInt16(int i) throws IOException {
        int i2;
        validateIndex(i, 2);
        if (this._isMotorolaByteOrder) {
            i2 = (getByte(i) << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
            i = getByte(i + 1);
        } else {
            i2 = (getByte(i + 1) << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
            i = getByte(i);
        }
        return (i & 255) | i2;
    }

    public short getInt16(int i) throws IOException {
        int i2;
        validateIndex(i, 2);
        if (this._isMotorolaByteOrder) {
            i2 = (((short) getByte(i)) << 8) & InputDeviceCompat.SOURCE_ANY;
            i = getByte(i + 1);
        } else {
            i2 = (((short) getByte(i + 1)) << 8) & InputDeviceCompat.SOURCE_ANY;
            i = getByte(i);
        }
        return (short) ((((short) i) & 255) | i2);
    }

    public int getInt24(int i) throws IOException {
        int i2;
        validateIndex(i, 3);
        if (this._isMotorolaByteOrder) {
            i2 = ((getByte(i) << 16) & 16711680) | (MotionEventCompat.ACTION_POINTER_INDEX_MASK & (getByte(i + 1) << 8));
            i = getByte(i + 2);
        } else {
            i2 = ((getByte(i + 2) << 16) & 16711680) | (MotionEventCompat.ACTION_POINTER_INDEX_MASK & (getByte(i + 1) << 8));
            i = getByte(i);
        }
        return (i & 255) | i2;
    }

    public long getUInt32(int i) throws IOException {
        long j;
        validateIndex(i, 4);
        if (this._isMotorolaByteOrder) {
            j = (65280 & (((long) getByte(i + 2)) << 8)) | ((16711680 & (((long) getByte(i + 1)) << 16)) | (4278190080L & (((long) getByte(i)) << 24)));
            i = getByte(i + 3);
        } else {
            j = (65280 & (((long) getByte(i + 1)) << 8)) | ((16711680 & (((long) getByte(i + 2)) << 16)) | (4278190080L & (((long) getByte(i + 3)) << 24)));
            i = getByte(i);
        }
        return (((long) i) & 255) | j;
    }

    public int getInt32(int i) throws IOException {
        int i2;
        validateIndex(i, 4);
        if (this._isMotorolaByteOrder) {
            i2 = (((getByte(i) << 24) & ViewCompat.MEASURED_STATE_MASK) | (16711680 & (getByte(i + 1) << 16))) | (MotionEventCompat.ACTION_POINTER_INDEX_MASK & (getByte(i + 2) << 8));
            i = getByte(i + 3);
        } else {
            i2 = (((getByte(i + 3) << 24) & ViewCompat.MEASURED_STATE_MASK) | (16711680 & (getByte(i + 2) << 16))) | (MotionEventCompat.ACTION_POINTER_INDEX_MASK & (getByte(i + 1) << 8));
            i = getByte(i);
        }
        return (i & 255) | i2;
    }

    public long getInt64(int i) throws IOException {
        long j;
        int i2 = i;
        validateIndex(i2, 8);
        if (this._isMotorolaByteOrder) {
            j = (((((((((long) getByte(i)) << 56) & -72057594037927936L) | (71776119061217280L & (((long) getByte(i2 + 1)) << 48))) | (280375465082880L & (((long) getByte(i2 + 2)) << 40))) | (1095216660480L & (((long) getByte(i2 + 3)) << 32))) | ((((long) getByte(i2 + 4)) << 24) & 4278190080L)) | ((((long) getByte(i2 + 5)) << 16) & 16711680)) | ((((long) getByte(i2 + 6)) << 8) & 65280);
            i2 = getByte(i2 + 7);
        } else {
            j = (((((((((long) getByte(i2 + 7)) << 56) & -72057594037927936L) | (71776119061217280L & (((long) getByte(i2 + 6)) << 48))) | (280375465082880L & (((long) getByte(i2 + 5)) << 40))) | (1095216660480L & (((long) getByte(i2 + 4)) << 32))) | ((((long) getByte(i2 + 3)) << 24) & 4278190080L)) | ((((long) getByte(i2 + 2)) << 16) & 16711680)) | ((((long) getByte(i2 + 1)) << 8) & 65280);
            i2 = getByte(i);
        }
        return j | (((long) i2) & 255);
    }

    public float getS15Fixed16(int i) throws IOException {
        float f;
        int i2;
        validateIndex(i, 4);
        if (this._isMotorolaByteOrder) {
            f = (float) (((getByte(i) & 255) << 8) | (getByte(i + 1) & 255));
            i2 = (getByte(i + 2) & 255) << 8;
            i = getByte(i + 3);
        } else {
            f = (float) (((getByte(i + 3) & 255) << 8) | (getByte(i + 2) & 255));
            i2 = (getByte(i + 1) & 255) << 8;
            i = getByte(i);
        }
        return (float) (((double) f) + (((double) ((i & 255) | i2)) / 65536.0d));
    }

    public float getFloat32(int i) throws IOException {
        return Float.intBitsToFloat(getInt32(i));
    }

    public double getDouble64(int i) throws IOException {
        return Double.longBitsToDouble(getInt64(i));
    }

    @NotNull
    public StringValue getStringValue(int i, int i2, @Nullable Charset charset) throws IOException {
        return new StringValue(getBytes(i, i2), charset);
    }

    @NotNull
    public String getString(int i, int i2, @NotNull Charset charset) throws IOException {
        return new String(getBytes(i, i2), charset.name());
    }

    @NotNull
    public String getString(int i, int i2, @NotNull String str) throws IOException {
        byte[] bytes = getBytes(i, i2);
        try {
            return new String(bytes, str);
        } catch (UnsupportedEncodingException unused) {
            return new String(bytes);
        }
    }

    @NotNull
    public String getNullTerminatedString(int i, int i2, @NotNull Charset charset) throws IOException {
        return new String(getNullTerminatedBytes(i, i2), charset.name());
    }

    @NotNull
    public StringValue getNullTerminatedStringValue(int i, int i2, @Nullable Charset charset) throws IOException {
        return new StringValue(getNullTerminatedBytes(i, i2), charset);
    }

    @NotNull
    public byte[] getNullTerminatedBytes(int i, int i2) throws IOException {
        Object bytes = getBytes(i, i2);
        int i3 = 0;
        while (i3 < bytes.length && bytes[i3] != (byte) 0) {
            i3++;
        }
        if (i3 == i2) {
            return bytes;
        }
        Object obj = new byte[i3];
        if (i3 > 0) {
            System.arraycopy(bytes, 0, obj, 0, i3);
        }
        return obj;
    }
}
