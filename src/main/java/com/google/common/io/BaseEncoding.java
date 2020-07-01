package com.google.common.io;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.RoundingMode;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
public abstract class BaseEncoding {
    private static final BaseEncoding BASE16 = new Base16Encoding("base16()", "0123456789ABCDEF");
    private static final BaseEncoding BASE32;
    private static final BaseEncoding BASE32_HEX;
    private static final BaseEncoding BASE64;
    private static final BaseEncoding BASE64_URL;

    private static final class Alphabet {
        final int bitsPerChar;
        final int bytesPerChunk;
        private final char[] chars;
        final int charsPerChunk;
        private final byte[] decodabet;
        final int mask;
        private final String name;
        private final boolean[] validPadding;

        Alphabet(String str, char[] cArr) {
            StringBuilder stringBuilder;
            this.name = (String) Preconditions.checkNotNull(str);
            this.chars = (char[]) Preconditions.checkNotNull(cArr);
            try {
                this.bitsPerChar = IntMath.log2(cArr.length, RoundingMode.UNNECESSARY);
                int min = Math.min(8, Integer.lowestOneBit(this.bitsPerChar));
                try {
                    this.charsPerChunk = 8 / min;
                    this.bytesPerChunk = this.bitsPerChar / min;
                    this.mask = cArr.length - 1;
                    byte[] bArr = new byte[128];
                    Arrays.fill(bArr, (byte) -1);
                    for (int i = 0; i < cArr.length; i++) {
                        char c = cArr[i];
                        Preconditions.checkArgument(c < bArr.length, "Non-ASCII character: %s", c);
                        Preconditions.checkArgument(bArr[c] == (byte) -1, "Duplicate character: %s", c);
                        bArr[c] = (byte) i;
                    }
                    this.decodabet = bArr;
                    boolean[] zArr = new boolean[this.charsPerChunk];
                    for (int i2 = 0; i2 < this.bytesPerChunk; i2++) {
                        zArr[IntMath.divide(i2 * 8, this.bitsPerChar, RoundingMode.CEILING)] = true;
                    }
                    this.validPadding = zArr;
                } catch (Throwable e) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Illegal alphabet ");
                    stringBuilder.append(new String(cArr));
                    throw new IllegalArgumentException(stringBuilder.toString(), e);
                }
            } catch (Throwable e2) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Illegal alphabet length ");
                stringBuilder.append(cArr.length);
                throw new IllegalArgumentException(stringBuilder.toString(), e2);
            }
        }

        char encode(int i) {
            return this.chars[i];
        }

        boolean isValidPaddingStartPosition(int i) {
            return this.validPadding[i % this.charsPerChunk];
        }

        boolean canDecode(char c) {
            return c <= Ascii.MAX && this.decodabet[c] != (byte) -1;
        }

        int decode(char c) throws DecodingException {
            String str = "Unrecognized character: 0x";
            StringBuilder stringBuilder;
            if (c <= Ascii.MAX) {
                byte b = this.decodabet[c];
                if (b != (byte) -1) {
                    return b;
                }
                if (c <= ' ' || c == Ascii.MAX) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str);
                    stringBuilder.append(Integer.toHexString(c));
                    throw new DecodingException(stringBuilder.toString());
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Unrecognized character: ");
                stringBuilder2.append(c);
                throw new DecodingException(stringBuilder2.toString());
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(Integer.toHexString(c));
            throw new DecodingException(stringBuilder.toString());
        }

        private boolean hasLowerCase() {
            for (char isLowerCase : this.chars) {
                if (Ascii.isLowerCase(isLowerCase)) {
                    return true;
                }
            }
            return false;
        }

        private boolean hasUpperCase() {
            for (char isUpperCase : this.chars) {
                if (Ascii.isUpperCase(isUpperCase)) {
                    return true;
                }
            }
            return false;
        }

        Alphabet upperCase() {
            if (!hasLowerCase()) {
                return this;
            }
            Preconditions.checkState(hasUpperCase() ^ 1, "Cannot call upperCase() on a mixed-case alphabet");
            char[] cArr = new char[this.chars.length];
            int i = 0;
            while (true) {
                char[] cArr2 = this.chars;
                if (i < cArr2.length) {
                    cArr[i] = Ascii.toUpperCase(cArr2[i]);
                    i++;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(this.name);
                    stringBuilder.append(".upperCase()");
                    return new Alphabet(stringBuilder.toString(), cArr);
                }
            }
        }

        Alphabet lowerCase() {
            if (!hasUpperCase()) {
                return this;
            }
            Preconditions.checkState(hasLowerCase() ^ 1, "Cannot call lowerCase() on a mixed-case alphabet");
            char[] cArr = new char[this.chars.length];
            int i = 0;
            while (true) {
                char[] cArr2 = this.chars;
                if (i < cArr2.length) {
                    cArr[i] = Ascii.toLowerCase(cArr2[i]);
                    i++;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(this.name);
                    stringBuilder.append(".lowerCase()");
                    return new Alphabet(stringBuilder.toString(), cArr);
                }
            }
        }

        public boolean matches(char c) {
            byte[] bArr = this.decodabet;
            return c < bArr.length && bArr[c] != (byte) -1;
        }

        public String toString() {
            return this.name;
        }

        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof Alphabet)) {
                return false;
            }
            return Arrays.equals(this.chars, ((Alphabet) obj).chars);
        }

        public int hashCode() {
            return Arrays.hashCode(this.chars);
        }
    }

    public static final class DecodingException extends IOException {
        DecodingException(String str) {
            super(str);
        }

        DecodingException(Throwable th) {
            super(th);
        }
    }

    static final class SeparatedBaseEncoding extends BaseEncoding {
        private final int afterEveryChars;
        private final BaseEncoding delegate;
        private final String separator;

        SeparatedBaseEncoding(BaseEncoding baseEncoding, String str, int i) {
            this.delegate = (BaseEncoding) Preconditions.checkNotNull(baseEncoding);
            this.separator = (String) Preconditions.checkNotNull(str);
            this.afterEveryChars = i;
            Preconditions.checkArgument(i > 0, "Cannot add a separator after every %s chars", i);
        }

        CharSequence trimTrailingPadding(CharSequence charSequence) {
            return this.delegate.trimTrailingPadding(charSequence);
        }

        int maxEncodedSize(int i) {
            i = this.delegate.maxEncodedSize(i);
            return i + (this.separator.length() * IntMath.divide(Math.max(0, i - 1), this.afterEveryChars, RoundingMode.FLOOR));
        }

        @GwtIncompatible
        public OutputStream encodingStream(Writer writer) {
            return this.delegate.encodingStream(BaseEncoding.separatingWriter(writer, this.separator, this.afterEveryChars));
        }

        void encodeTo(Appendable appendable, byte[] bArr, int i, int i2) throws IOException {
            this.delegate.encodeTo(BaseEncoding.separatingAppendable(appendable, this.separator, this.afterEveryChars), bArr, i, i2);
        }

        int maxDecodedSize(int i) {
            return this.delegate.maxDecodedSize(i);
        }

        public boolean canDecode(CharSequence charSequence) {
            CharSequence stringBuilder = new StringBuilder();
            for (int i = 0; i < charSequence.length(); i++) {
                char charAt = charSequence.charAt(i);
                if (this.separator.indexOf(charAt) < 0) {
                    stringBuilder.append(charAt);
                }
            }
            return this.delegate.canDecode(stringBuilder);
        }

        int decodeTo(byte[] bArr, CharSequence charSequence) throws DecodingException {
            CharSequence stringBuilder = new StringBuilder(charSequence.length());
            for (int i = 0; i < charSequence.length(); i++) {
                char charAt = charSequence.charAt(i);
                if (this.separator.indexOf(charAt) < 0) {
                    stringBuilder.append(charAt);
                }
            }
            return this.delegate.decodeTo(bArr, stringBuilder);
        }

        @GwtIncompatible
        public InputStream decodingStream(Reader reader) {
            return this.delegate.decodingStream(BaseEncoding.ignoringReader(reader, this.separator));
        }

        public BaseEncoding omitPadding() {
            return this.delegate.omitPadding().withSeparator(this.separator, this.afterEveryChars);
        }

        public BaseEncoding withPadChar(char c) {
            return this.delegate.withPadChar(c).withSeparator(this.separator, this.afterEveryChars);
        }

        public BaseEncoding withSeparator(String str, int i) {
            throw new UnsupportedOperationException("Already have a separator");
        }

        public BaseEncoding upperCase() {
            return this.delegate.upperCase().withSeparator(this.separator, this.afterEveryChars);
        }

        public BaseEncoding lowerCase() {
            return this.delegate.lowerCase().withSeparator(this.separator, this.afterEveryChars);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.delegate);
            stringBuilder.append(".withSeparator(\"");
            stringBuilder.append(this.separator);
            stringBuilder.append("\", ");
            stringBuilder.append(this.afterEveryChars);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    static class StandardBaseEncoding extends BaseEncoding {
        final Alphabet alphabet;
        @MonotonicNonNullDecl
        private transient BaseEncoding lowerCase;
        @NullableDecl
        final Character paddingChar;
        @MonotonicNonNullDecl
        private transient BaseEncoding upperCase;

        StandardBaseEncoding(String str, String str2, @NullableDecl Character ch) {
            this(new Alphabet(str, str2.toCharArray()), ch);
        }

        StandardBaseEncoding(Alphabet alphabet, @NullableDecl Character ch) {
            this.alphabet = (Alphabet) Preconditions.checkNotNull(alphabet);
            boolean z = ch == null || !alphabet.matches(ch.charValue());
            Preconditions.checkArgument(z, "Padding character %s was already in alphabet", (Object) ch);
            this.paddingChar = ch;
        }

        int maxEncodedSize(int i) {
            return this.alphabet.charsPerChunk * IntMath.divide(i, this.alphabet.bytesPerChunk, RoundingMode.CEILING);
        }

        @GwtIncompatible
        public OutputStream encodingStream(final Writer writer) {
            Preconditions.checkNotNull(writer);
            return new OutputStream() {
                int bitBuffer = 0;
                int bitBufferLength = 0;
                int writtenChars = 0;

                public void write(int i) throws IOException {
                    this.bitBuffer <<= 8;
                    this.bitBuffer = (i & 255) | this.bitBuffer;
                    this.bitBufferLength += 8;
                    while (this.bitBufferLength >= StandardBaseEncoding.this.alphabet.bitsPerChar) {
                        writer.write(StandardBaseEncoding.this.alphabet.encode((this.bitBuffer >> (this.bitBufferLength - StandardBaseEncoding.this.alphabet.bitsPerChar)) & StandardBaseEncoding.this.alphabet.mask));
                        this.writtenChars++;
                        this.bitBufferLength -= StandardBaseEncoding.this.alphabet.bitsPerChar;
                    }
                }

                public void flush() throws IOException {
                    writer.flush();
                }

                public void close() throws IOException {
                    if (this.bitBufferLength > 0) {
                        writer.write(StandardBaseEncoding.this.alphabet.encode((this.bitBuffer << (StandardBaseEncoding.this.alphabet.bitsPerChar - this.bitBufferLength)) & StandardBaseEncoding.this.alphabet.mask));
                        this.writtenChars++;
                        if (StandardBaseEncoding.this.paddingChar != null) {
                            while (this.writtenChars % StandardBaseEncoding.this.alphabet.charsPerChunk != 0) {
                                writer.write(StandardBaseEncoding.this.paddingChar.charValue());
                                this.writtenChars++;
                            }
                        }
                    }
                    writer.close();
                }
            };
        }

        void encodeTo(Appendable appendable, byte[] bArr, int i, int i2) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
            int i3 = 0;
            while (i3 < i2) {
                encodeChunkTo(appendable, bArr, i + i3, Math.min(this.alphabet.bytesPerChunk, i2 - i3));
                i3 += this.alphabet.bytesPerChunk;
            }
        }

        void encodeChunkTo(Appendable appendable, byte[] bArr, int i, int i2) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
            int i3 = 0;
            Preconditions.checkArgument(i2 <= this.alphabet.bytesPerChunk);
            long j = 0;
            for (int i4 = 0; i4 < i2; i4++) {
                j = (j | ((long) (bArr[i + i4] & 255))) << 8;
            }
            int i5 = ((i2 + 1) * 8) - this.alphabet.bitsPerChar;
            while (i3 < i2 * 8) {
                appendable.append(this.alphabet.encode(((int) (j >>> (i5 - i3))) & this.alphabet.mask));
                i3 += this.alphabet.bitsPerChar;
            }
            if (this.paddingChar != null) {
                while (i3 < this.alphabet.bytesPerChunk * 8) {
                    appendable.append(this.paddingChar.charValue());
                    i3 += this.alphabet.bitsPerChar;
                }
            }
        }

        int maxDecodedSize(int i) {
            return (int) (((((long) this.alphabet.bitsPerChar) * ((long) i)) + 7) / 8);
        }

        CharSequence trimTrailingPadding(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            Character ch = this.paddingChar;
            if (ch == null) {
                return charSequence;
            }
            char charValue = ch.charValue();
            int length = charSequence.length() - 1;
            while (length >= 0 && charSequence.charAt(length) == charValue) {
                length--;
            }
            return charSequence.subSequence(0, length + 1);
        }

        public boolean canDecode(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            charSequence = trimTrailingPadding(charSequence);
            if (!this.alphabet.isValidPaddingStartPosition(charSequence.length())) {
                return false;
            }
            for (int i = 0; i < charSequence.length(); i++) {
                if (!this.alphabet.canDecode(charSequence.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

        int decodeTo(byte[] bArr, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(bArr);
            charSequence = trimTrailingPadding(charSequence);
            if (this.alphabet.isValidPaddingStartPosition(charSequence.length())) {
                int i = 0;
                int i2 = 0;
                while (i < charSequence.length()) {
                    int i3;
                    long j = 0;
                    int i4 = 0;
                    for (i3 = 0; i3 < this.alphabet.charsPerChunk; i3++) {
                        j <<= this.alphabet.bitsPerChar;
                        if (i + i3 < charSequence.length()) {
                            j |= (long) this.alphabet.decode(charSequence.charAt(i4 + i));
                            i4++;
                        }
                    }
                    i3 = (this.alphabet.bytesPerChunk * 8) - (i4 * this.alphabet.bitsPerChar);
                    i4 = (this.alphabet.bytesPerChunk - 1) * 8;
                    while (i4 >= i3) {
                        int i5 = i2 + 1;
                        bArr[i2] = (byte) ((int) ((j >>> i4) & 255));
                        i4 -= 8;
                        i2 = i5;
                    }
                    i += this.alphabet.charsPerChunk;
                }
                return i2;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid input length ");
            stringBuilder.append(charSequence.length());
            throw new DecodingException(stringBuilder.toString());
        }

        @GwtIncompatible
        public InputStream decodingStream(final Reader reader) {
            Preconditions.checkNotNull(reader);
            return new InputStream() {
                int bitBuffer = 0;
                int bitBufferLength = 0;
                boolean hitPadding = false;
                int readChars = 0;

                public int read() throws IOException {
                    StringBuilder stringBuilder;
                    while (true) {
                        int read = reader.read();
                        if (read != -1) {
                            this.readChars++;
                            char c = (char) read;
                            if (StandardBaseEncoding.this.paddingChar == null || StandardBaseEncoding.this.paddingChar.charValue() != c) {
                                if (this.hitPadding) {
                                    StringBuilder stringBuilder2 = new StringBuilder();
                                    stringBuilder2.append("Expected padding character but found '");
                                    stringBuilder2.append(c);
                                    stringBuilder2.append("' at index ");
                                    stringBuilder2.append(this.readChars);
                                    throw new DecodingException(stringBuilder2.toString());
                                }
                                this.bitBuffer <<= StandardBaseEncoding.this.alphabet.bitsPerChar;
                                this.bitBuffer = StandardBaseEncoding.this.alphabet.decode(c) | this.bitBuffer;
                                this.bitBufferLength += StandardBaseEncoding.this.alphabet.bitsPerChar;
                                read = this.bitBufferLength;
                                if (read >= 8) {
                                    this.bitBufferLength = read - 8;
                                    return (this.bitBuffer >> this.bitBufferLength) & 255;
                                }
                            } else if (this.hitPadding || (this.readChars != 1 && StandardBaseEncoding.this.alphabet.isValidPaddingStartPosition(this.readChars - 1))) {
                                this.hitPadding = true;
                            }
                        } else if (this.hitPadding || StandardBaseEncoding.this.alphabet.isValidPaddingStartPosition(this.readChars)) {
                            return -1;
                        } else {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("Invalid input length ");
                            stringBuilder.append(this.readChars);
                            throw new DecodingException(stringBuilder.toString());
                        }
                    }
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Padding cannot start at index ");
                    stringBuilder.append(this.readChars);
                    throw new DecodingException(stringBuilder.toString());
                }

                public void close() throws IOException {
                    reader.close();
                }
            };
        }

        public BaseEncoding omitPadding() {
            return this.paddingChar == null ? this : newInstance(this.alphabet, null);
        }

        public BaseEncoding withPadChar(char c) {
            if (8 % this.alphabet.bitsPerChar != 0) {
                Character ch = this.paddingChar;
                if (ch == null || ch.charValue() != c) {
                    return newInstance(this.alphabet, Character.valueOf(c));
                }
            }
            return this;
        }

        public BaseEncoding withSeparator(String str, int i) {
            boolean z = false;
            for (int i2 = 0; i2 < str.length(); i2++) {
                Preconditions.checkArgument(this.alphabet.matches(str.charAt(i2)) ^ true, "Separator (%s) cannot contain alphabet characters", (Object) str);
            }
            Character ch = this.paddingChar;
            if (ch != null) {
                if (str.indexOf(ch.charValue()) < 0) {
                    z = true;
                }
                Preconditions.checkArgument(z, "Separator (%s) cannot contain padding character", (Object) str);
            }
            return new SeparatedBaseEncoding(this, str, i);
        }

        public BaseEncoding upperCase() {
            BaseEncoding baseEncoding = this.upperCase;
            if (baseEncoding == null) {
                Alphabet upperCase = this.alphabet.upperCase();
                baseEncoding = upperCase == this.alphabet ? this : newInstance(upperCase, this.paddingChar);
                this.upperCase = baseEncoding;
            }
            return baseEncoding;
        }

        public BaseEncoding lowerCase() {
            BaseEncoding baseEncoding = this.lowerCase;
            if (baseEncoding == null) {
                Alphabet lowerCase = this.alphabet.lowerCase();
                baseEncoding = lowerCase == this.alphabet ? this : newInstance(lowerCase, this.paddingChar);
                this.lowerCase = baseEncoding;
            }
            return baseEncoding;
        }

        BaseEncoding newInstance(Alphabet alphabet, @NullableDecl Character ch) {
            return new StandardBaseEncoding(alphabet, ch);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("BaseEncoding.");
            stringBuilder.append(this.alphabet.toString());
            if (8 % this.alphabet.bitsPerChar != 0) {
                if (this.paddingChar == null) {
                    stringBuilder.append(".omitPadding()");
                } else {
                    stringBuilder.append(".withPadChar('");
                    stringBuilder.append(this.paddingChar);
                    stringBuilder.append("')");
                }
            }
            return stringBuilder.toString();
        }

        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof StandardBaseEncoding)) {
                return false;
            }
            StandardBaseEncoding standardBaseEncoding = (StandardBaseEncoding) obj;
            if (this.alphabet.equals(standardBaseEncoding.alphabet) && Objects.equal(this.paddingChar, standardBaseEncoding.paddingChar)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.alphabet.hashCode() ^ Objects.hashCode(this.paddingChar);
        }
    }

    static final class Base16Encoding extends StandardBaseEncoding {
        final char[] encoding;

        Base16Encoding(String str, String str2) {
            this(new Alphabet(str, str2.toCharArray()));
        }

        private Base16Encoding(Alphabet alphabet) {
            super(alphabet, null);
            this.encoding = new char[512];
            Preconditions.checkArgument(alphabet.chars.length == 16);
            for (int i = 0; i < 256; i++) {
                this.encoding[i] = alphabet.encode(i >>> 4);
                this.encoding[i | 256] = alphabet.encode(i & 15);
            }
        }

        void encodeTo(Appendable appendable, byte[] bArr, int i, int i2) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
            for (int i3 = 0; i3 < i2; i3++) {
                int i4 = bArr[i + i3] & 255;
                appendable.append(this.encoding[i4]);
                appendable.append(this.encoding[i4 | 256]);
            }
        }

        int decodeTo(byte[] bArr, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(bArr);
            if (charSequence.length() % 2 != 1) {
                int i = 0;
                int i2 = 0;
                while (i < charSequence.length()) {
                    int i3 = i2 + 1;
                    bArr[i2] = (byte) ((this.alphabet.decode(charSequence.charAt(i)) << 4) | this.alphabet.decode(charSequence.charAt(i + 1)));
                    i += 2;
                    i2 = i3;
                }
                return i2;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid input length ");
            stringBuilder.append(charSequence.length());
            throw new DecodingException(stringBuilder.toString());
        }

        BaseEncoding newInstance(Alphabet alphabet, @NullableDecl Character ch) {
            return new Base16Encoding(alphabet);
        }
    }

    static final class Base64Encoding extends StandardBaseEncoding {
        Base64Encoding(String str, String str2, @NullableDecl Character ch) {
            this(new Alphabet(str, str2.toCharArray()), ch);
        }

        private Base64Encoding(Alphabet alphabet, @NullableDecl Character ch) {
            super(alphabet, ch);
            Preconditions.checkArgument(alphabet.chars.length == 64);
        }

        void encodeTo(Appendable appendable, byte[] bArr, int i, int i2) throws IOException {
            Preconditions.checkNotNull(appendable);
            int i3 = i + i2;
            Preconditions.checkPositionIndexes(i, i3, bArr.length);
            while (i2 >= 3) {
                int i4 = i + 1;
                int i5 = i4 + 1;
                i = ((bArr[i] & 255) << 16) | ((bArr[i4] & 255) << 8);
                i4 = i5 + 1;
                i |= bArr[i5] & 255;
                appendable.append(this.alphabet.encode(i >>> 18));
                appendable.append(this.alphabet.encode((i >>> 12) & 63));
                appendable.append(this.alphabet.encode((i >>> 6) & 63));
                appendable.append(this.alphabet.encode(i & 63));
                i2 -= 3;
                i = i4;
            }
            if (i < i3) {
                encodeChunkTo(appendable, bArr, i, i3 - i);
            }
        }

        int decodeTo(byte[] bArr, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(bArr);
            charSequence = trimTrailingPadding(charSequence);
            if (this.alphabet.isValidPaddingStartPosition(charSequence.length())) {
                int i = 0;
                int i2 = 0;
                while (i < charSequence.length()) {
                    int i3 = i + 1;
                    int i4 = i3 + 1;
                    i = (this.alphabet.decode(charSequence.charAt(i)) << 18) | (this.alphabet.decode(charSequence.charAt(i3)) << 12);
                    int i5 = i2 + 1;
                    bArr[i2] = (byte) (i >>> 16);
                    if (i4 < charSequence.length()) {
                        i3 = i4 + 1;
                        i |= this.alphabet.decode(charSequence.charAt(i4)) << 6;
                        i2 = i5 + 1;
                        bArr[i5] = (byte) ((i >>> 8) & 255);
                        if (i3 < charSequence.length()) {
                            i4 = i3 + 1;
                            i5 = i2 + 1;
                            bArr[i2] = (byte) ((i | this.alphabet.decode(charSequence.charAt(i3))) & 255);
                        } else {
                            i = i3;
                        }
                    }
                    i2 = i5;
                    i = i4;
                }
                return i2;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid input length ");
            stringBuilder.append(charSequence.length());
            throw new DecodingException(stringBuilder.toString());
        }

        BaseEncoding newInstance(Alphabet alphabet, @NullableDecl Character ch) {
            return new Base64Encoding(alphabet, ch);
        }
    }

    public abstract boolean canDecode(CharSequence charSequence);

    abstract int decodeTo(byte[] bArr, CharSequence charSequence) throws DecodingException;

    @GwtIncompatible
    public abstract InputStream decodingStream(Reader reader);

    abstract void encodeTo(Appendable appendable, byte[] bArr, int i, int i2) throws IOException;

    @GwtIncompatible
    public abstract OutputStream encodingStream(Writer writer);

    public abstract BaseEncoding lowerCase();

    abstract int maxDecodedSize(int i);

    abstract int maxEncodedSize(int i);

    public abstract BaseEncoding omitPadding();

    public abstract BaseEncoding upperCase();

    public abstract BaseEncoding withPadChar(char c);

    public abstract BaseEncoding withSeparator(String str, int i);

    BaseEncoding() {
    }

    public String encode(byte[] bArr) {
        return encode(bArr, 0, bArr.length);
    }

    public final String encode(byte[] bArr, int i, int i2) {
        Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
        Appendable stringBuilder = new StringBuilder(maxEncodedSize(i2));
        try {
            encodeTo(stringBuilder, bArr, i, i2);
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    @GwtIncompatible
    public final ByteSink encodingSink(final CharSink charSink) {
        Preconditions.checkNotNull(charSink);
        return new ByteSink() {
            public OutputStream openStream() throws IOException {
                return BaseEncoding.this.encodingStream(charSink.openStream());
            }
        };
    }

    private static byte[] extract(byte[] bArr, int i) {
        if (i == bArr.length) {
            return bArr;
        }
        Object obj = new byte[i];
        System.arraycopy(bArr, 0, obj, 0, i);
        return obj;
    }

    public final byte[] decode(CharSequence charSequence) {
        try {
            return decodeChecked(charSequence);
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    final byte[] decodeChecked(CharSequence charSequence) throws DecodingException {
        charSequence = trimTrailingPadding(charSequence);
        byte[] bArr = new byte[maxDecodedSize(charSequence.length())];
        return extract(bArr, decodeTo(bArr, charSequence));
    }

    @GwtIncompatible
    public final ByteSource decodingSource(final CharSource charSource) {
        Preconditions.checkNotNull(charSource);
        return new ByteSource() {
            public InputStream openStream() throws IOException {
                return BaseEncoding.this.decodingStream(charSource.openStream());
            }
        };
    }

    CharSequence trimTrailingPadding(CharSequence charSequence) {
        return (CharSequence) Preconditions.checkNotNull(charSequence);
    }

    static {
        Character valueOf = Character.valueOf('=');
        BASE64 = new Base64Encoding("base64()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", valueOf);
        BASE64_URL = new Base64Encoding("base64Url()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", valueOf);
        BASE32 = new StandardBaseEncoding("base32()", "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", valueOf);
        BASE32_HEX = new StandardBaseEncoding("base32Hex()", "0123456789ABCDEFGHIJKLMNOPQRSTUV", valueOf);
    }

    public static BaseEncoding base64() {
        return BASE64;
    }

    public static BaseEncoding base64Url() {
        return BASE64_URL;
    }

    public static BaseEncoding base32() {
        return BASE32;
    }

    public static BaseEncoding base32Hex() {
        return BASE32_HEX;
    }

    public static BaseEncoding base16() {
        return BASE16;
    }

    @GwtIncompatible
    static Reader ignoringReader(final Reader reader, final String str) {
        Preconditions.checkNotNull(reader);
        Preconditions.checkNotNull(str);
        return new Reader() {
            public int read() throws IOException {
                int read;
                do {
                    read = reader.read();
                    if (read == -1) {
                        break;
                    }
                } while (str.indexOf((char) read) >= 0);
                return read;
            }

            public int read(char[] cArr, int i, int i2) throws IOException {
                throw new UnsupportedOperationException();
            }

            public void close() throws IOException {
                reader.close();
            }
        };
    }

    static Appendable separatingAppendable(final Appendable appendable, final String str, final int i) {
        Preconditions.checkNotNull(appendable);
        Preconditions.checkNotNull(str);
        Preconditions.checkArgument(i > 0);
        return new Appendable() {
            int charsUntilSeparator = i;

            public Appendable append(char c) throws IOException {
                if (this.charsUntilSeparator == 0) {
                    appendable.append(str);
                    this.charsUntilSeparator = i;
                }
                appendable.append(c);
                this.charsUntilSeparator--;
                return this;
            }

            public Appendable append(@NullableDecl CharSequence charSequence, int i, int i2) throws IOException {
                throw new UnsupportedOperationException();
            }

            public Appendable append(@NullableDecl CharSequence charSequence) throws IOException {
                throw new UnsupportedOperationException();
            }
        };
    }

    @GwtIncompatible
    static Writer separatingWriter(final Writer writer, String str, int i) {
        final Appendable separatingAppendable = separatingAppendable(writer, str, i);
        return new Writer() {
            public void write(int i) throws IOException {
                separatingAppendable.append((char) i);
            }

            public void write(char[] cArr, int i, int i2) throws IOException {
                throw new UnsupportedOperationException();
            }

            public void flush() throws IOException {
                writer.flush();
            }

            public void close() throws IOException {
                writer.close();
            }
        };
    }
}
