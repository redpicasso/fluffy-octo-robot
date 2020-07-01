package com.airbnb.lottie.parser.moshi;

import androidx.annotation.Nullable;
import com.airbnb.lottie.parser.moshi.JsonReader.Options;
import com.airbnb.lottie.parser.moshi.JsonReader.Token;
import java.io.EOFException;
import java.io.IOException;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

final class JsonUtf8Reader extends JsonReader {
    private static final ByteString CLOSING_BLOCK_COMMENT = ByteString.encodeUtf8("*/");
    private static final ByteString DOUBLE_QUOTE_OR_SLASH = ByteString.encodeUtf8("\"\\");
    private static final ByteString LINEFEED_OR_CARRIAGE_RETURN = ByteString.encodeUtf8("\n\r");
    private static final long MIN_INCOMPLETE_INTEGER = -922337203685477580L;
    private static final int NUMBER_CHAR_DECIMAL = 3;
    private static final int NUMBER_CHAR_DIGIT = 2;
    private static final int NUMBER_CHAR_EXP_DIGIT = 7;
    private static final int NUMBER_CHAR_EXP_E = 5;
    private static final int NUMBER_CHAR_EXP_SIGN = 6;
    private static final int NUMBER_CHAR_FRACTION_DIGIT = 4;
    private static final int NUMBER_CHAR_NONE = 0;
    private static final int NUMBER_CHAR_SIGN = 1;
    private static final int PEEKED_BEGIN_ARRAY = 3;
    private static final int PEEKED_BEGIN_OBJECT = 1;
    private static final int PEEKED_BUFFERED = 11;
    private static final int PEEKED_BUFFERED_NAME = 15;
    private static final int PEEKED_DOUBLE_QUOTED = 9;
    private static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
    private static final int PEEKED_END_ARRAY = 4;
    private static final int PEEKED_END_OBJECT = 2;
    private static final int PEEKED_EOF = 18;
    private static final int PEEKED_FALSE = 6;
    private static final int PEEKED_LONG = 16;
    private static final int PEEKED_NONE = 0;
    private static final int PEEKED_NULL = 7;
    private static final int PEEKED_NUMBER = 17;
    private static final int PEEKED_SINGLE_QUOTED = 8;
    private static final int PEEKED_SINGLE_QUOTED_NAME = 12;
    private static final int PEEKED_TRUE = 5;
    private static final int PEEKED_UNQUOTED = 10;
    private static final int PEEKED_UNQUOTED_NAME = 14;
    private static final ByteString SINGLE_QUOTE_OR_SLASH = ByteString.encodeUtf8("'\\");
    private static final ByteString UNQUOTED_STRING_TERMINALS = ByteString.encodeUtf8("{}[]:, \n\t\r\f/\\;#=");
    private final Buffer buffer;
    private int peeked = 0;
    private long peekedLong;
    private int peekedNumberLength;
    @Nullable
    private String peekedString;
    private final BufferedSource source;

    JsonUtf8Reader(BufferedSource bufferedSource) {
        if (bufferedSource != null) {
            this.source = bufferedSource;
            this.buffer = bufferedSource.getBuffer();
            pushScope(6);
            return;
        }
        throw new NullPointerException("source == null");
    }

    public void beginArray() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 3) {
            pushScope(1);
            this.pathIndices[this.stackSize - 1] = 0;
            this.peeked = 0;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected BEGIN_ARRAY but was ");
        stringBuilder.append(peek());
        stringBuilder.append(" at path ");
        stringBuilder.append(getPath());
        throw new JsonDataException(stringBuilder.toString());
    }

    public void endArray() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 4) {
            this.stackSize--;
            int[] iArr = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr[i2] = iArr[i2] + 1;
            this.peeked = 0;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected END_ARRAY but was ");
        stringBuilder.append(peek());
        stringBuilder.append(" at path ");
        stringBuilder.append(getPath());
        throw new JsonDataException(stringBuilder.toString());
    }

    public void beginObject() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 1) {
            pushScope(3);
            this.peeked = 0;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected BEGIN_OBJECT but was ");
        stringBuilder.append(peek());
        stringBuilder.append(" at path ");
        stringBuilder.append(getPath());
        throw new JsonDataException(stringBuilder.toString());
    }

    public void endObject() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 2) {
            this.stackSize--;
            this.pathNames[this.stackSize] = null;
            int[] iArr = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr[i2] = iArr[i2] + 1;
            this.peeked = 0;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected END_OBJECT but was ");
        stringBuilder.append(peek());
        stringBuilder.append(" at path ");
        stringBuilder.append(getPath());
        throw new JsonDataException(stringBuilder.toString());
    }

    public boolean hasNext() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        return (i == 2 || i == 4 || i == 18) ? false : true;
    }

    public Token peek() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        switch (i) {
            case 1:
                return Token.BEGIN_OBJECT;
            case 2:
                return Token.END_OBJECT;
            case 3:
                return Token.BEGIN_ARRAY;
            case 4:
                return Token.END_ARRAY;
            case 5:
            case 6:
                return Token.BOOLEAN;
            case 7:
                return Token.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return Token.STRING;
            case 12:
            case 13:
            case 14:
            case 15:
                return Token.NAME;
            case 16:
            case 17:
                return Token.NUMBER;
            case 18:
                return Token.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    private int doPeek() throws IOException {
        int nextNonWhitespace;
        int i = this.scopes[this.stackSize - 1];
        if (i == 1) {
            this.scopes[this.stackSize - 1] = 2;
        } else if (i == 2) {
            nextNonWhitespace = nextNonWhitespace(true);
            this.buffer.readByte();
            if (nextNonWhitespace != 44) {
                if (nextNonWhitespace == 59) {
                    checkLenient();
                } else if (nextNonWhitespace == 93) {
                    this.peeked = 4;
                    return 4;
                } else {
                    throw syntaxError("Unterminated array");
                }
            }
        } else if (i == 3 || i == 5) {
            this.scopes[this.stackSize - 1] = 4;
            if (i == 5) {
                int nextNonWhitespace2 = nextNonWhitespace(true);
                this.buffer.readByte();
                if (nextNonWhitespace2 != 44) {
                    if (nextNonWhitespace2 == 59) {
                        checkLenient();
                    } else if (nextNonWhitespace2 == 125) {
                        this.peeked = 2;
                        return 2;
                    } else {
                        throw syntaxError("Unterminated object");
                    }
                }
            }
            int nextNonWhitespace3 = nextNonWhitespace(true);
            if (nextNonWhitespace3 == 34) {
                this.buffer.readByte();
                this.peeked = 13;
                return 13;
            } else if (nextNonWhitespace3 != 39) {
                String str = "Expected name";
                if (nextNonWhitespace3 != 125) {
                    checkLenient();
                    if (isLiteral((char) nextNonWhitespace3)) {
                        this.peeked = 14;
                        return 14;
                    }
                    throw syntaxError(str);
                } else if (i != 5) {
                    this.buffer.readByte();
                    this.peeked = 2;
                    return 2;
                } else {
                    throw syntaxError(str);
                }
            } else {
                this.buffer.readByte();
                checkLenient();
                this.peeked = 12;
                return 12;
            }
        } else if (i == 4) {
            this.scopes[this.stackSize - 1] = 5;
            nextNonWhitespace = nextNonWhitespace(true);
            this.buffer.readByte();
            if (nextNonWhitespace != 58) {
                if (nextNonWhitespace == 61) {
                    checkLenient();
                    if (this.source.request(1) && this.buffer.getByte(0) == (byte) 62) {
                        this.buffer.readByte();
                    }
                } else {
                    throw syntaxError("Expected ':'");
                }
            }
        } else if (i == 6) {
            this.scopes[this.stackSize - 1] = 7;
        } else if (i == 7) {
            if (nextNonWhitespace(false) == -1) {
                this.peeked = 18;
                return 18;
            }
            checkLenient();
        } else if (i == 8) {
            throw new IllegalStateException("JsonReader is closed");
        }
        nextNonWhitespace = nextNonWhitespace(true);
        if (nextNonWhitespace == 34) {
            this.buffer.readByte();
            this.peeked = 9;
            return 9;
        } else if (nextNonWhitespace != 39) {
            if (!(nextNonWhitespace == 44 || nextNonWhitespace == 59)) {
                if (nextNonWhitespace == 91) {
                    this.buffer.readByte();
                    this.peeked = 3;
                    return 3;
                } else if (nextNonWhitespace != 93) {
                    if (nextNonWhitespace != 123) {
                        i = peekKeyword();
                        if (i != 0) {
                            return i;
                        }
                        i = peekNumber();
                        if (i != 0) {
                            return i;
                        }
                        if (isLiteral(this.buffer.getByte(0))) {
                            checkLenient();
                            this.peeked = 10;
                            return 10;
                        }
                        throw syntaxError("Expected value");
                    }
                    this.buffer.readByte();
                    this.peeked = 1;
                    return 1;
                } else if (i == 1) {
                    this.buffer.readByte();
                    this.peeked = 4;
                    return 4;
                }
            }
            if (i == 1 || i == 2) {
                checkLenient();
                this.peeked = 7;
                return 7;
            }
            throw syntaxError("Unexpected value");
        } else {
            checkLenient();
            this.buffer.readByte();
            this.peeked = 8;
            return 8;
        }
    }

    private int peekKeyword() throws IOException {
        int i;
        String str;
        byte b = this.buffer.getByte(0);
        String str2;
        if (b == (byte) 116 || b == (byte) 84) {
            i = 5;
            str = "true";
            str2 = "TRUE";
        } else if (b == (byte) 102 || b == (byte) 70) {
            i = 6;
            str = "false";
            str2 = "FALSE";
        } else if (b != (byte) 110 && b != (byte) 78) {
            return 0;
        } else {
            i = 7;
            str = "null";
            str2 = "NULL";
        }
        int length = str.length();
        int i2 = 1;
        while (i2 < length) {
            int i3 = i2 + 1;
            if (!this.source.request((long) i3)) {
                return 0;
            }
            char c = this.buffer.getByte((long) i2);
            if (c != str.charAt(i2) && c != str2.charAt(i2)) {
                return 0;
            }
            i2 = i3;
        }
        if (this.source.request((long) (length + 1)) && isLiteral(this.buffer.getByte((long) length))) {
            return 0;
        }
        this.buffer.skip((long) length);
        this.peeked = i;
        return i;
    }

    private int peekNumber() throws IOException {
        byte b;
        int i = 1;
        int i2 = 0;
        long j = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 1;
        Object obj = null;
        while (true) {
            int i6 = i3 + 1;
            if (!this.source.request((long) i6)) {
                break;
            }
            b = this.buffer.getByte((long) i3);
            if (b != (byte) 43) {
                if (b != (byte) 69 && b != (byte) 101) {
                    if (b != (byte) 45) {
                        if (b != (byte) 46) {
                            if (b >= (byte) 48 && b <= (byte) 57) {
                                if (i4 == i || i4 == 0) {
                                    j = (long) (-(b - 48));
                                    i2 = 0;
                                    i4 = 2;
                                } else {
                                    if (i4 == 2) {
                                        if (j == 0) {
                                            return i2;
                                        }
                                        long j2 = (10 * j) - ((long) (b - 48));
                                        i3 = (j > MIN_INCOMPLETE_INTEGER ? 1 : (j == MIN_INCOMPLETE_INTEGER ? 0 : -1));
                                        i = (i3 > 0 || (i3 == 0 && j2 < j)) ? 1 : 0;
                                        i5 = i & i5;
                                        j = j2;
                                    } else if (i4 == 3) {
                                        i2 = 0;
                                        i4 = 4;
                                    } else if (i4 == 5 || i4 == 6) {
                                        i2 = 0;
                                        i4 = 7;
                                    }
                                    i2 = 0;
                                }
                            }
                        } else if (i4 != 2) {
                            return i2;
                        } else {
                            i4 = 3;
                        }
                    } else if (i4 == 0) {
                        i4 = 1;
                        obj = 1;
                    } else if (i4 != 5) {
                        return i2;
                    }
                    i3 = i6;
                    i = 1;
                } else if (i4 != 2 && i4 != 4) {
                    return i2;
                } else {
                    i4 = 5;
                    i3 = i6;
                    i = 1;
                }
            } else if (i4 != 5) {
                return i2;
            }
            i4 = 6;
            i3 = i6;
            i = 1;
        }
        if (isLiteral(b)) {
            return 0;
        }
        if (i4 == 2 && i5 != 0 && ((j != Long.MIN_VALUE || obj != null) && (j != 0 || obj == null))) {
            if (obj == null) {
                j = -j;
            }
            this.peekedLong = j;
            this.buffer.skip((long) i3);
            this.peeked = 16;
            return 16;
        } else if (i4 != 2 && i4 != 4 && i4 != 7) {
            return 0;
        } else {
            this.peekedNumberLength = i3;
            this.peeked = 17;
            return 17;
        }
    }

    private boolean isLiteral(int i) throws IOException {
        if (!(i == 9 || i == 10 || i == 12 || i == 13 || i == 32)) {
            if (i != 35) {
                if (i != 44) {
                    if (!(i == 47 || i == 61)) {
                        if (!(i == 123 || i == 125 || i == 58)) {
                            if (i != 59) {
                                switch (i) {
                                    case 91:
                                    case 93:
                                        break;
                                    case 92:
                                        break;
                                    default:
                                        return true;
                                }
                            }
                        }
                    }
                }
            }
            checkLenient();
        }
        return false;
    }

    public String nextName() throws IOException {
        String nextUnquotedValue;
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 14) {
            nextUnquotedValue = nextUnquotedValue();
        } else if (i == 13) {
            nextUnquotedValue = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (i == 12) {
            nextUnquotedValue = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (i == 15) {
            nextUnquotedValue = this.peekedString;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected a name but was ");
            stringBuilder.append(peek());
            stringBuilder.append(" at path ");
            stringBuilder.append(getPath());
            throw new JsonDataException(stringBuilder.toString());
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = nextUnquotedValue;
        return nextUnquotedValue;
    }

    public int selectName(Options options) throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i < 12 || i > 15) {
            return -1;
        }
        if (i == 15) {
            return findName(this.peekedString, options);
        }
        i = this.source.select(options.doubleQuoteSuffix);
        if (i != -1) {
            this.peeked = 0;
            this.pathNames[this.stackSize - 1] = options.strings[i];
            return i;
        }
        String str = this.pathNames[this.stackSize - 1];
        String nextName = nextName();
        int findName = findName(nextName, options);
        if (findName == -1) {
            this.peeked = 15;
            this.peekedString = nextName;
            this.pathNames[this.stackSize - 1] = str;
        }
        return findName;
    }

    public void skipName() throws IOException {
        StringBuilder stringBuilder;
        if (this.failOnUnknown) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot skip unexpected ");
            stringBuilder.append(peek());
            stringBuilder.append(" at ");
            stringBuilder.append(getPath());
            throw new JsonDataException(stringBuilder.toString());
        }
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 14) {
            skipUnquotedValue();
        } else if (i == 13) {
            skipQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (i == 12) {
            skipQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (i != 15) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Expected a name but was ");
            stringBuilder.append(peek());
            stringBuilder.append(" at path ");
            stringBuilder.append(getPath());
            throw new JsonDataException(stringBuilder.toString());
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = "null";
    }

    private int findName(String str, Options options) {
        int length = options.strings.length;
        for (int i = 0; i < length; i++) {
            if (str.equals(options.strings[i])) {
                this.peeked = 0;
                this.pathNames[this.stackSize - 1] = str;
                return i;
            }
        }
        return -1;
    }

    public String nextString() throws IOException {
        String nextUnquotedValue;
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 10) {
            nextUnquotedValue = nextUnquotedValue();
        } else if (i == 9) {
            nextUnquotedValue = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (i == 8) {
            nextUnquotedValue = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (i == 11) {
            nextUnquotedValue = this.peekedString;
            this.peekedString = null;
        } else if (i == 16) {
            nextUnquotedValue = Long.toString(this.peekedLong);
        } else if (i == 17) {
            nextUnquotedValue = this.buffer.readUtf8((long) this.peekedNumberLength);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected a string but was ");
            stringBuilder.append(peek());
            stringBuilder.append(" at path ");
            stringBuilder.append(getPath());
            throw new JsonDataException(stringBuilder.toString());
        }
        this.peeked = 0;
        int[] iArr = this.pathIndices;
        int i2 = this.stackSize - 1;
        iArr[i2] = iArr[i2] + 1;
        return nextUnquotedValue;
    }

    private int findString(String str, Options options) {
        int length = options.strings.length;
        for (int i = 0; i < length; i++) {
            if (str.equals(options.strings[i])) {
                this.peeked = 0;
                int[] iArr = this.pathIndices;
                int i2 = this.stackSize - 1;
                iArr[i2] = iArr[i2] + 1;
                return i;
            }
        }
        return -1;
    }

    public boolean nextBoolean() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        int[] iArr;
        int i2;
        if (i == 5) {
            this.peeked = 0;
            iArr = this.pathIndices;
            i2 = this.stackSize - 1;
            iArr[i2] = iArr[i2] + 1;
            return true;
        } else if (i == 6) {
            this.peeked = 0;
            iArr = this.pathIndices;
            i2 = this.stackSize - 1;
            iArr[i2] = iArr[i2] + 1;
            return false;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected a boolean but was ");
            stringBuilder.append(peek());
            stringBuilder.append(" at path ");
            stringBuilder.append(getPath());
            throw new JsonDataException(stringBuilder.toString());
        }
    }

    public double nextDouble() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 16) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr[i2] = iArr[i2] + 1;
            return (double) this.peekedLong;
        }
        StringBuilder stringBuilder;
        String str = "Expected a double but was ";
        String str2 = " at path ";
        if (i == 17) {
            this.peekedString = this.buffer.readUtf8((long) this.peekedNumberLength);
        } else if (i == 9) {
            this.peekedString = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (i == 8) {
            this.peekedString = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (i == 10) {
            this.peekedString = nextUnquotedValue();
        } else if (i != 11) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(peek());
            stringBuilder.append(str2);
            stringBuilder.append(getPath());
            throw new JsonDataException(stringBuilder.toString());
        }
        this.peeked = 11;
        try {
            double parseDouble = Double.parseDouble(this.peekedString);
            if (this.lenient || !(Double.isNaN(parseDouble) || Double.isInfinite(parseDouble))) {
                this.peekedString = null;
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i3 = this.stackSize - 1;
                iArr2[i3] = iArr2[i3] + 1;
                return parseDouble;
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("JSON forbids NaN and infinities: ");
            stringBuilder2.append(parseDouble);
            stringBuilder2.append(str2);
            stringBuilder2.append(getPath());
            throw new JsonEncodingException(stringBuilder2.toString());
        } catch (NumberFormatException unused) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(this.peekedString);
            stringBuilder.append(str2);
            stringBuilder.append(getPath());
            throw new JsonDataException(stringBuilder.toString());
        }
    }

    private String nextQuotedValue(ByteString byteString) throws IOException {
        StringBuilder stringBuilder = null;
        while (true) {
            long indexOfElement = this.source.indexOfElement(byteString);
            if (indexOfElement == -1) {
                throw syntaxError("Unterminated string");
            } else if (this.buffer.getByte(indexOfElement) == (byte) 92) {
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder();
                }
                stringBuilder.append(this.buffer.readUtf8(indexOfElement));
                this.buffer.readByte();
                stringBuilder.append(readEscapeCharacter());
            } else if (stringBuilder == null) {
                String readUtf8 = this.buffer.readUtf8(indexOfElement);
                this.buffer.readByte();
                return readUtf8;
            } else {
                stringBuilder.append(this.buffer.readUtf8(indexOfElement));
                this.buffer.readByte();
                return stringBuilder.toString();
            }
        }
    }

    private String nextUnquotedValue() throws IOException {
        long indexOfElement = this.source.indexOfElement(UNQUOTED_STRING_TERMINALS);
        return indexOfElement != -1 ? this.buffer.readUtf8(indexOfElement) : this.buffer.readUtf8();
    }

    private void skipQuotedValue(ByteString byteString) throws IOException {
        while (true) {
            long indexOfElement = this.source.indexOfElement(byteString);
            if (indexOfElement == -1) {
                throw syntaxError("Unterminated string");
            } else if (this.buffer.getByte(indexOfElement) == (byte) 92) {
                this.buffer.skip(indexOfElement + 1);
                readEscapeCharacter();
            } else {
                this.buffer.skip(indexOfElement + 1);
                return;
            }
        }
    }

    private void skipUnquotedValue() throws IOException {
        long indexOfElement = this.source.indexOfElement(UNQUOTED_STRING_TERMINALS);
        Buffer buffer = this.buffer;
        if (indexOfElement == -1) {
            indexOfElement = buffer.size();
        }
        buffer.skip(indexOfElement);
    }

    public int nextInt() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        String str = " at path ";
        String str2 = "Expected an int but was ";
        int i2;
        int[] iArr;
        int i3;
        StringBuilder stringBuilder;
        if (i == 16) {
            long j = this.peekedLong;
            i2 = (int) j;
            if (j == ((long) i2)) {
                this.peeked = 0;
                iArr = this.pathIndices;
                i3 = this.stackSize - 1;
                iArr[i3] = iArr[i3] + 1;
                return i2;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(this.peekedLong);
            stringBuilder.append(str);
            stringBuilder.append(getPath());
            throw new JsonDataException(stringBuilder.toString());
        } else if (i == 17) {
            this.peekedString = this.buffer.readUtf8((long) this.peekedNumberLength);
        } else if (i == 9 || i == 8) {
            String nextQuotedValue;
            if (i == 9) {
                nextQuotedValue = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
            } else {
                nextQuotedValue = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
            }
            this.peekedString = nextQuotedValue;
            try {
                i = Integer.parseInt(this.peekedString);
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i4 = this.stackSize - 1;
                iArr2[i4] = iArr2[i4] + 1;
                return i;
            } catch (NumberFormatException unused) {
                this.peeked = 11;
                try {
                    double parseDouble = Double.parseDouble(this.peekedString);
                    i2 = (int) parseDouble;
                    if (((double) i2) == parseDouble) {
                        this.peekedString = null;
                        this.peeked = 0;
                        iArr = this.pathIndices;
                        i3 = this.stackSize - 1;
                        iArr[i3] = iArr[i3] + 1;
                        return i2;
                    }
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str2);
                    stringBuilder.append(this.peekedString);
                    stringBuilder.append(str);
                    stringBuilder.append(getPath());
                    throw new JsonDataException(stringBuilder.toString());
                } catch (NumberFormatException unused2) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str2);
                    stringBuilder.append(this.peekedString);
                    stringBuilder.append(str);
                    stringBuilder.append(getPath());
                    throw new JsonDataException(stringBuilder.toString());
                }
            }
        } else if (i != 11) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(peek());
            stringBuilder.append(str);
            stringBuilder.append(getPath());
            throw new JsonDataException(stringBuilder.toString());
        }
    }

    public void close() throws IOException {
        this.peeked = 0;
        this.scopes[0] = 8;
        this.stackSize = 1;
        this.buffer.clear();
        this.source.close();
    }

    public void skipValue() throws IOException {
        StringBuilder stringBuilder;
        if (this.failOnUnknown) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot skip unexpected ");
            stringBuilder.append(peek());
            stringBuilder.append(" at ");
            stringBuilder.append(getPath());
            throw new JsonDataException(stringBuilder.toString());
        }
        int i = 0;
        do {
            int i2 = this.peeked;
            if (i2 == 0) {
                i2 = doPeek();
            }
            if (i2 == 3) {
                pushScope(1);
            } else if (i2 == 1) {
                pushScope(3);
            } else {
                String str = " at path ";
                String str2 = "Expected a value but was ";
                if (i2 == 4) {
                    i--;
                    if (i >= 0) {
                        this.stackSize--;
                    } else {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(str2);
                        stringBuilder.append(peek());
                        stringBuilder.append(str);
                        stringBuilder.append(getPath());
                        throw new JsonDataException(stringBuilder.toString());
                    }
                } else if (i2 == 2) {
                    i--;
                    if (i >= 0) {
                        this.stackSize--;
                    } else {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(str2);
                        stringBuilder.append(peek());
                        stringBuilder.append(str);
                        stringBuilder.append(getPath());
                        throw new JsonDataException(stringBuilder.toString());
                    }
                } else if (i2 == 14 || i2 == 10) {
                    skipUnquotedValue();
                } else if (i2 == 9 || i2 == 13) {
                    skipQuotedValue(DOUBLE_QUOTE_OR_SLASH);
                } else if (i2 == 8 || i2 == 12) {
                    skipQuotedValue(SINGLE_QUOTE_OR_SLASH);
                } else if (i2 == 17) {
                    this.buffer.skip((long) this.peekedNumberLength);
                } else if (i2 == 18) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str2);
                    stringBuilder.append(peek());
                    stringBuilder.append(str);
                    stringBuilder.append(getPath());
                    throw new JsonDataException(stringBuilder.toString());
                }
                this.peeked = 0;
            }
            i++;
            this.peeked = 0;
        } while (i != 0);
        int[] iArr = this.pathIndices;
        i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        this.pathNames[this.stackSize - 1] = "null";
    }

    /* JADX WARNING: Missing block: B:12:0x0025, code:
            r6.buffer.skip((long) (r3 - 1));
     */
    /* JADX WARNING: Missing block: B:13:0x002f, code:
            if (r1 != (byte) 47) goto L_0x0074;
     */
    /* JADX WARNING: Missing block: B:15:0x0039, code:
            if (r6.source.request(2) != false) goto L_0x003c;
     */
    /* JADX WARNING: Missing block: B:16:0x003b, code:
            return r1;
     */
    /* JADX WARNING: Missing block: B:17:0x003c, code:
            checkLenient();
            r3 = r6.buffer.getByte(1);
     */
    /* JADX WARNING: Missing block: B:18:0x0049, code:
            if (r3 == (byte) 42) goto L_0x005c;
     */
    /* JADX WARNING: Missing block: B:19:0x004b, code:
            if (r3 == (byte) 47) goto L_0x004e;
     */
    /* JADX WARNING: Missing block: B:20:0x004d, code:
            return r1;
     */
    /* JADX WARNING: Missing block: B:21:0x004e, code:
            r6.buffer.readByte();
            r6.buffer.readByte();
            skipToEndOfLine();
     */
    /* JADX WARNING: Missing block: B:22:0x005c, code:
            r6.buffer.readByte();
            r6.buffer.readByte();
     */
    /* JADX WARNING: Missing block: B:23:0x006a, code:
            if (skipToEndOfBlockComment() == false) goto L_0x006d;
     */
    /* JADX WARNING: Missing block: B:26:0x0073, code:
            throw syntaxError("Unterminated comment");
     */
    /* JADX WARNING: Missing block: B:28:0x0076, code:
            if (r1 != (byte) 35) goto L_0x007f;
     */
    /* JADX WARNING: Missing block: B:29:0x0078, code:
            checkLenient();
            skipToEndOfLine();
     */
    /* JADX WARNING: Missing block: B:30:0x007f, code:
            return r1;
     */
    private int nextNonWhitespace(boolean r7) throws java.io.IOException {
        /*
        r6 = this;
        r0 = 0;
    L_0x0001:
        r1 = 0;
    L_0x0002:
        r2 = r6.source;
        r3 = r1 + 1;
        r4 = (long) r3;
        r2 = r2.request(r4);
        if (r2 == 0) goto L_0x0082;
    L_0x000d:
        r2 = r6.buffer;
        r4 = (long) r1;
        r1 = r2.getByte(r4);
        r2 = 10;
        if (r1 == r2) goto L_0x0080;
    L_0x0018:
        r2 = 32;
        if (r1 == r2) goto L_0x0080;
    L_0x001c:
        r2 = 13;
        if (r1 == r2) goto L_0x0080;
    L_0x0020:
        r2 = 9;
        if (r1 != r2) goto L_0x0025;
    L_0x0024:
        goto L_0x0080;
    L_0x0025:
        r2 = r6.buffer;
        r3 = r3 + -1;
        r3 = (long) r3;
        r2.skip(r3);
        r2 = 47;
        if (r1 != r2) goto L_0x0074;
    L_0x0031:
        r3 = r6.source;
        r4 = 2;
        r3 = r3.request(r4);
        if (r3 != 0) goto L_0x003c;
    L_0x003b:
        return r1;
    L_0x003c:
        r6.checkLenient();
        r3 = r6.buffer;
        r4 = 1;
        r3 = r3.getByte(r4);
        r4 = 42;
        if (r3 == r4) goto L_0x005c;
    L_0x004b:
        if (r3 == r2) goto L_0x004e;
    L_0x004d:
        return r1;
    L_0x004e:
        r1 = r6.buffer;
        r1.readByte();
        r1 = r6.buffer;
        r1.readByte();
        r6.skipToEndOfLine();
        goto L_0x0001;
    L_0x005c:
        r1 = r6.buffer;
        r1.readByte();
        r1 = r6.buffer;
        r1.readByte();
        r1 = r6.skipToEndOfBlockComment();
        if (r1 == 0) goto L_0x006d;
    L_0x006c:
        goto L_0x0001;
    L_0x006d:
        r7 = "Unterminated comment";
        r7 = r6.syntaxError(r7);
        throw r7;
    L_0x0074:
        r2 = 35;
        if (r1 != r2) goto L_0x007f;
    L_0x0078:
        r6.checkLenient();
        r6.skipToEndOfLine();
        goto L_0x0001;
    L_0x007f:
        return r1;
    L_0x0080:
        r1 = r3;
        goto L_0x0002;
    L_0x0082:
        if (r7 != 0) goto L_0x0086;
    L_0x0084:
        r7 = -1;
        return r7;
    L_0x0086:
        r7 = new java.io.EOFException;
        r0 = "End of input";
        r7.<init>(r0);
        throw r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.moshi.JsonUtf8Reader.nextNonWhitespace(boolean):int");
    }

    private void checkLenient() throws IOException {
        if (!this.lenient) {
            throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private void skipToEndOfLine() throws IOException {
        long indexOfElement = this.source.indexOfElement(LINEFEED_OR_CARRIAGE_RETURN);
        Buffer buffer = this.buffer;
        buffer.skip(indexOfElement != -1 ? indexOfElement + 1 : buffer.size());
    }

    private boolean skipToEndOfBlockComment() throws IOException {
        long indexOf = this.source.indexOf(CLOSING_BLOCK_COMMENT);
        boolean z = indexOf != -1;
        Buffer buffer = this.buffer;
        buffer.skip(z ? indexOf + ((long) CLOSING_BLOCK_COMMENT.size()) : buffer.size());
        return z;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("JsonReader(");
        stringBuilder.append(this.source);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private char readEscapeCharacter() throws IOException {
        if (this.source.request(1)) {
            byte readByte = this.buffer.readByte();
            if (readByte == (byte) 10 || readByte == (byte) 34 || readByte == (byte) 39 || readByte == (byte) 47 || readByte == (byte) 92) {
                return (char) readByte;
            }
            if (readByte == (byte) 98) {
                return 8;
            }
            if (readByte == (byte) 102) {
                return 12;
            }
            if (readByte == (byte) 110) {
                return 10;
            }
            if (readByte == (byte) 114) {
                return 13;
            }
            if (readByte == (byte) 116) {
                return 9;
            }
            StringBuilder stringBuilder;
            if (readByte != (byte) 117) {
                if (this.lenient) {
                    return (char) readByte;
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid escape sequence: \\");
                stringBuilder.append((char) readByte);
                throw syntaxError(stringBuilder.toString());
            } else if (this.source.request(4)) {
                char c = 0;
                for (int i = 0; i < 4; i++) {
                    int i2;
                    byte b = this.buffer.getByte((long) i);
                    c = (char) (c << 4);
                    if (b < (byte) 48 || b > (byte) 57) {
                        if (b >= (byte) 97 && b <= (byte) 102) {
                            i2 = b - 97;
                        } else if (b < (byte) 65 || b > (byte) 70) {
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("\\u");
                            stringBuilder2.append(this.buffer.readUtf8(4));
                            throw syntaxError(stringBuilder2.toString());
                        } else {
                            i2 = b - 65;
                        }
                        i2 += 10;
                    } else {
                        i2 = b - 48;
                    }
                    c = (char) (c + i2);
                }
                this.buffer.skip(4);
                return c;
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unterminated escape sequence at path ");
                stringBuilder.append(getPath());
                throw new EOFException(stringBuilder.toString());
            }
        }
        throw syntaxError("Unterminated escape sequence");
    }
}
