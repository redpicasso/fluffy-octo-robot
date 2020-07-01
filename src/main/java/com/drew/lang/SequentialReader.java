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

public abstract class SequentialReader {
    private boolean _isMotorolaByteOrder = true;

    public abstract int available();

    public abstract byte getByte() throws IOException;

    public abstract void getBytes(@NotNull byte[] bArr, int i, int i2) throws IOException;

    @NotNull
    public abstract byte[] getBytes(int i) throws IOException;

    public abstract long getPosition() throws IOException;

    public abstract void skip(long j) throws IOException;

    public abstract boolean trySkip(long j) throws IOException;

    public void setMotorolaByteOrder(boolean z) {
        this._isMotorolaByteOrder = z;
    }

    public boolean isMotorolaByteOrder() {
        return this._isMotorolaByteOrder;
    }

    public short getUInt8() throws IOException {
        return (short) (getByte() & 255);
    }

    public byte getInt8() throws IOException {
        return getByte();
    }

    public int getUInt16() throws IOException {
        int i;
        int i2;
        if (this._isMotorolaByteOrder) {
            i = (getByte() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
            i2 = getByte() & 255;
        } else {
            i = getByte() & 255;
            i2 = MotionEventCompat.ACTION_POINTER_INDEX_MASK & (getByte() << 8);
        }
        return i | i2;
    }

    public short getInt16() throws IOException {
        int i;
        int i2;
        if (this._isMotorolaByteOrder) {
            i = (((short) getByte()) << 8) & InputDeviceCompat.SOURCE_ANY;
            i2 = ((short) getByte()) & 255;
        } else {
            i = ((short) getByte()) & 255;
            i2 = (((short) getByte()) << 8) & InputDeviceCompat.SOURCE_ANY;
        }
        return (short) (i | i2);
    }

    public long getUInt32() throws IOException {
        if (this._isMotorolaByteOrder) {
            return ((((((long) getByte()) << 24) & 4278190080L) | ((((long) getByte()) << 16) & 16711680)) | ((((long) getByte()) << 8) & 65280)) | (((long) getByte()) & 255);
        }
        return ((((long) getByte()) << 24) & 4278190080L) | ((16711680 & (((long) getByte()) << 16)) | ((65280 & (((long) getByte()) << 8)) | (255 & ((long) getByte()))));
    }

    public int getInt32() throws IOException {
        int i;
        int i2;
        if (this._isMotorolaByteOrder) {
            i = (((getByte() << 24) & ViewCompat.MEASURED_STATE_MASK) | ((getByte() << 16) & 16711680)) | ((getByte() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
            i2 = getByte() & 255;
        } else {
            i = ((getByte() & 255) | (MotionEventCompat.ACTION_POINTER_INDEX_MASK & (getByte() << 8))) | (16711680 & (getByte() << 16));
            i2 = ViewCompat.MEASURED_STATE_MASK & (getByte() << 24);
        }
        return i | i2;
    }

    public long getInt64() throws IOException {
        long j;
        long j2;
        if (this._isMotorolaByteOrder) {
            j = (((((((((long) getByte()) << 56) & -72057594037927936L) | ((((long) getByte()) << 48) & 71776119061217280L)) | ((((long) getByte()) << 40) & 280375465082880L)) | ((((long) getByte()) << 32) & 1095216660480L)) | ((((long) getByte()) << 24) & 4278190080L)) | ((((long) getByte()) << 16) & 16711680)) | ((((long) getByte()) << 8) & 65280);
            j2 = ((long) getByte()) & 255;
        } else {
            j = (((((((((long) getByte()) << 8) & 65280) | (((long) getByte()) & 255)) | ((((long) getByte()) << 16) & 16711680)) | ((((long) getByte()) << 24) & 4278190080L)) | (1095216660480L & (((long) getByte()) << 32))) | (280375465082880L & (((long) getByte()) << 40))) | (71776119061217280L & (((long) getByte()) << 48));
            j2 = (((long) getByte()) << 56) & -72057594037927936L;
        }
        return j | j2;
    }

    public float getS15Fixed16() throws IOException {
        if (this._isMotorolaByteOrder) {
            return (float) (((double) ((float) (((getByte() & 255) << 8) | (getByte() & 255)))) + (((double) (((getByte() & 255) << 8) | (getByte() & 255))) / 65536.0d));
        }
        return (float) (((double) ((float) ((getByte() & 255) | ((getByte() & 255) << 8)))) + (((double) ((getByte() & 255) | ((getByte() & 255) << 8))) / 65536.0d));
    }

    public float getFloat32() throws IOException {
        return Float.intBitsToFloat(getInt32());
    }

    public double getDouble64() throws IOException {
        return Double.longBitsToDouble(getInt64());
    }

    @NotNull
    public String getString(int i) throws IOException {
        return new String(getBytes(i));
    }

    @NotNull
    public String getString(int i, String str) throws IOException {
        byte[] bytes = getBytes(i);
        try {
            return new String(bytes, str);
        } catch (UnsupportedEncodingException unused) {
            return new String(bytes);
        }
    }

    @NotNull
    public String getString(int i, @NotNull Charset charset) throws IOException {
        return new String(getBytes(i), charset);
    }

    @NotNull
    public StringValue getStringValue(int i, @Nullable Charset charset) throws IOException {
        return new StringValue(getBytes(i), charset);
    }

    @NotNull
    public String getNullTerminatedString(int i, Charset charset) throws IOException {
        return getNullTerminatedStringValue(i, charset).toString();
    }

    @NotNull
    public StringValue getNullTerminatedStringValue(int i, Charset charset) throws IOException {
        return new StringValue(getNullTerminatedBytes(i), charset);
    }

    @NotNull
    public byte[] getNullTerminatedBytes(int i) throws IOException {
        Object obj = new byte[i];
        int i2 = 0;
        while (i2 < obj.length) {
            byte b = getByte();
            obj[i2] = b;
            if (b == (byte) 0) {
                break;
            }
            i2++;
        }
        if (i2 == i) {
            return obj;
        }
        Object obj2 = new byte[i2];
        if (i2 > 0) {
            System.arraycopy(obj, 0, obj2, 0, i2);
        }
        return obj2;
    }
}
