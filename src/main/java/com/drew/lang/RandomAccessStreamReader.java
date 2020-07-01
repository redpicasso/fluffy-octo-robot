package com.drew.lang;

import com.drew.lang.annotations.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RandomAccessStreamReader extends RandomAccessReader {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DEFAULT_CHUNK_LENGTH = 2048;
    private final int _chunkLength;
    private final ArrayList<byte[]> _chunks;
    private boolean _isStreamFinished;
    @NotNull
    private final InputStream _stream;
    private long _streamLength;

    public int toUnshiftedOffset(int i) {
        return i;
    }

    public RandomAccessStreamReader(@NotNull InputStream inputStream) {
        this(inputStream, 2048, -1);
    }

    public RandomAccessStreamReader(@NotNull InputStream inputStream, int i) {
        this(inputStream, i, -1);
    }

    public RandomAccessStreamReader(@NotNull InputStream inputStream, int i, long j) {
        this._chunks = new ArrayList();
        if (inputStream == null) {
            throw new NullPointerException();
        } else if (i > 0) {
            this._chunkLength = i;
            this._stream = inputStream;
            this._streamLength = j;
        } else {
            throw new IllegalArgumentException("chunkLength must be greater than zero");
        }
    }

    public long getLength() throws IOException {
        long j = this._streamLength;
        if (j != -1) {
            return j;
        }
        isValidIndex(Integer.MAX_VALUE, 1);
        return this._streamLength;
    }

    protected void validateIndex(int i, int i2) throws IOException {
        if (i < 0) {
            throw new BufferBoundsException(String.format("Attempt to read from buffer using a negative index (%d)", new Object[]{Integer.valueOf(i)}));
        } else if (i2 < 0) {
            throw new BufferBoundsException("Number of requested bytes must be zero or greater");
        } else if ((((long) i) + ((long) i2)) - 1 > 2147483647L) {
            throw new BufferBoundsException(String.format("Number of requested bytes summed with starting index exceed maximum range of signed 32 bit integers (requested index: %d, requested count: %d)", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
        } else if (!isValidIndex(i, i2)) {
            throw new BufferBoundsException(i, i2, this._streamLength);
        }
    }

    protected boolean isValidIndex(int i, int i2) throws IOException {
        boolean z = false;
        if (i < 0 || i2 < 0) {
            return false;
        }
        long j = (((long) i) + ((long) i2)) - 1;
        if (j > 2147483647L) {
            return false;
        }
        i = (int) j;
        if (this._isStreamFinished) {
            if (((long) i) < this._streamLength) {
                z = true;
            }
            return z;
        }
        i2 = i / this._chunkLength;
        while (i2 >= this._chunks.size()) {
            Object obj = new byte[this._chunkLength];
            int i3 = 0;
            while (!this._isStreamFinished) {
                int i4 = this._chunkLength;
                if (i3 == i4) {
                    continue;
                    break;
                }
                i4 = this._stream.read(obj, i3, i4 - i3);
                if (i4 == -1) {
                    this._isStreamFinished = true;
                    i4 = (this._chunks.size() * this._chunkLength) + i3;
                    long j2 = this._streamLength;
                    if (j2 == -1) {
                        this._streamLength = (long) i4;
                    } else {
                        i4 = (j2 > ((long) i4) ? 1 : (j2 == ((long) i4) ? 0 : -1));
                    }
                    if (((long) i) >= this._streamLength) {
                        this._chunks.add(obj);
                        return false;
                    }
                } else {
                    i3 += i4;
                }
            }
            this._chunks.add(obj);
        }
        return true;
    }

    public byte getByte(int i) throws IOException {
        int i2 = this._chunkLength;
        int i3 = i / i2;
        return ((byte[]) this._chunks.get(i3))[i % i2];
    }

    @NotNull
    public byte[] getBytes(int i, int i2) throws IOException {
        validateIndex(i, i2);
        Object obj = new byte[i2];
        int i3 = 0;
        while (i2 != 0) {
            int i4 = this._chunkLength;
            int i5 = i / i4;
            int i6 = i % i4;
            i4 = Math.min(i2, i4 - i6);
            System.arraycopy((byte[]) this._chunks.get(i5), i6, obj, i3, i4);
            i2 -= i4;
            i += i4;
            i3 += i4;
        }
        return obj;
    }
}
