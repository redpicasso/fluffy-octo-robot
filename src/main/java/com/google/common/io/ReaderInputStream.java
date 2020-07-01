package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;

@GwtIncompatible
final class ReaderInputStream extends InputStream {
    private ByteBuffer byteBuffer;
    private CharBuffer charBuffer;
    private boolean doneFlushing;
    private boolean draining;
    private final CharsetEncoder encoder;
    private boolean endOfInput;
    private final Reader reader;
    private final byte[] singleByte;

    ReaderInputStream(Reader reader, Charset charset, int i) {
        this(reader, charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE), i);
    }

    ReaderInputStream(Reader reader, CharsetEncoder charsetEncoder, int i) {
        boolean z = true;
        this.singleByte = new byte[1];
        this.reader = (Reader) Preconditions.checkNotNull(reader);
        this.encoder = (CharsetEncoder) Preconditions.checkNotNull(charsetEncoder);
        if (i <= 0) {
            z = false;
        }
        Preconditions.checkArgument(z, "bufferSize must be positive: %s", i);
        charsetEncoder.reset();
        this.charBuffer = CharBuffer.allocate(i);
        this.charBuffer.flip();
        this.byteBuffer = ByteBuffer.allocate(i);
    }

    public void close() throws IOException {
        this.reader.close();
    }

    public int read() throws IOException {
        return read(this.singleByte) == 1 ? UnsignedBytes.toInt(this.singleByte[0]) : -1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002d  */
    public int read(byte[] r8, int r9, int r10) throws java.io.IOException {
        /*
        r7 = this;
        r0 = r9 + r10;
        r1 = r8.length;
        com.google.common.base.Preconditions.checkPositionIndexes(r9, r0, r1);
        r0 = 0;
        if (r10 != 0) goto L_0x000a;
    L_0x0009:
        return r0;
    L_0x000a:
        r1 = r7.endOfInput;
        r2 = r1;
        r1 = 0;
    L_0x000e:
        r3 = r7.draining;
        if (r3 == 0) goto L_0x002f;
    L_0x0012:
        r3 = r9 + r1;
        r4 = r10 - r1;
        r3 = r7.drain(r8, r3, r4);
        r1 = r1 + r3;
        if (r1 == r10) goto L_0x002a;
    L_0x001d:
        r3 = r7.doneFlushing;
        if (r3 == 0) goto L_0x0022;
    L_0x0021:
        goto L_0x002a;
    L_0x0022:
        r7.draining = r0;
        r3 = r7.byteBuffer;
        r3.clear();
        goto L_0x002f;
    L_0x002a:
        if (r1 <= 0) goto L_0x002d;
    L_0x002c:
        goto L_0x002e;
    L_0x002d:
        r1 = -1;
    L_0x002e:
        return r1;
    L_0x002f:
        r3 = r7.doneFlushing;
        if (r3 == 0) goto L_0x0036;
    L_0x0033:
        r3 = java.nio.charset.CoderResult.UNDERFLOW;
        goto L_0x004d;
    L_0x0036:
        if (r2 == 0) goto L_0x0041;
    L_0x0038:
        r3 = r7.encoder;
        r4 = r7.byteBuffer;
        r3 = r3.flush(r4);
        goto L_0x004d;
    L_0x0041:
        r3 = r7.encoder;
        r4 = r7.charBuffer;
        r5 = r7.byteBuffer;
        r6 = r7.endOfInput;
        r3 = r3.encode(r4, r5, r6);
    L_0x004d:
        r4 = r3.isOverflow();
        r5 = 1;
        if (r4 == 0) goto L_0x0058;
    L_0x0054:
        r7.startDraining(r5);
        goto L_0x000e;
    L_0x0058:
        r4 = r3.isUnderflow();
        if (r4 == 0) goto L_0x0070;
    L_0x005e:
        if (r2 == 0) goto L_0x0066;
    L_0x0060:
        r7.doneFlushing = r5;
        r7.startDraining(r0);
        goto L_0x000e;
    L_0x0066:
        r3 = r7.endOfInput;
        if (r3 == 0) goto L_0x006c;
    L_0x006a:
        r2 = 1;
        goto L_0x002f;
    L_0x006c:
        r7.readMoreChars();
        goto L_0x002f;
    L_0x0070:
        r4 = r3.isError();
        if (r4 == 0) goto L_0x002f;
    L_0x0076:
        r3.throwException();
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.io.ReaderInputStream.read(byte[], int, int):int");
    }

    private static CharBuffer grow(CharBuffer charBuffer) {
        CharBuffer wrap = CharBuffer.wrap(Arrays.copyOf(charBuffer.array(), charBuffer.capacity() * 2));
        wrap.position(charBuffer.position());
        wrap.limit(charBuffer.limit());
        return wrap;
    }

    private void readMoreChars() throws IOException {
        if (availableCapacity(this.charBuffer) == 0) {
            if (this.charBuffer.position() > 0) {
                this.charBuffer.compact().flip();
            } else {
                this.charBuffer = grow(this.charBuffer);
            }
        }
        int limit = this.charBuffer.limit();
        int read = this.reader.read(this.charBuffer.array(), limit, availableCapacity(this.charBuffer));
        if (read == -1) {
            this.endOfInput = true;
        } else {
            this.charBuffer.limit(limit + read);
        }
    }

    private static int availableCapacity(Buffer buffer) {
        return buffer.capacity() - buffer.limit();
    }

    private void startDraining(boolean z) {
        this.byteBuffer.flip();
        if (z && this.byteBuffer.remaining() == 0) {
            this.byteBuffer = ByteBuffer.allocate(this.byteBuffer.capacity() * 2);
        } else {
            this.draining = true;
        }
    }

    private int drain(byte[] bArr, int i, int i2) {
        i2 = Math.min(i2, this.byteBuffer.remaining());
        this.byteBuffer.get(bArr, i, i2);
        return i2;
    }
}
