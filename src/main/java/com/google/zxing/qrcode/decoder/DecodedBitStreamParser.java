package com.google.zxing.qrcode.decoder;

import com.drew.metadata.exif.makernotes.CanonMakernoteDirectory;
import com.facebook.imageutils.JfifUtil;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

final class DecodedBitStreamParser {
    private static final char[] ALPHANUMERIC_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ $%*+-./:".toCharArray();
    private static final int GB2312_SUBSET = 1;

    private DecodedBitStreamParser() {
    }

    static DecoderResult decode(byte[] bArr, Version version, ErrorCorrectionLevel errorCorrectionLevel, Map<DecodeHintType, ?> map) throws FormatException {
        Version version2 = version;
        BitSource bitSource = new BitSource(bArr);
        StringBuilder stringBuilder = new StringBuilder(50);
        int i = 1;
        List arrayList = new ArrayList(1);
        CharacterSetECI characterSetECI = null;
        boolean z = false;
        int i2 = -1;
        int i3 = -1;
        while (true) {
            try {
                Mode mode;
                Mode mode2;
                if (bitSource.available() < 4) {
                    mode = Mode.TERMINATOR;
                } else {
                    mode = Mode.forBits(bitSource.readBits(4));
                }
                Mode mode3 = mode;
                int readBits;
                int readBits2;
                switch (mode3) {
                    case TERMINATOR:
                        break;
                    case FNC1_FIRST_POSITION:
                    case FNC1_SECOND_POSITION:
                        mode2 = mode3;
                        z = true;
                        break;
                    case STRUCTURED_APPEND:
                        if (bitSource.available() >= 16) {
                            readBits = bitSource.readBits(8);
                            i3 = bitSource.readBits(8);
                            i2 = readBits;
                            break;
                        }
                        throw FormatException.getFormatInstance();
                    case ECI:
                        characterSetECI = CharacterSetECI.getCharacterSetECIByValue(parseECIValue(bitSource));
                        if (characterSetECI == null) {
                            throw FormatException.getFormatInstance();
                        }
                        break;
                    case HANZI:
                        readBits2 = bitSource.readBits(4);
                        readBits = bitSource.readBits(mode3.getCharacterCountBits(version2));
                        if (readBits2 == i) {
                            decodeHanziSegment(bitSource, stringBuilder, readBits);
                            break;
                        }
                        break;
                    default:
                        int readBits3 = bitSource.readBits(mode3.getCharacterCountBits(version2));
                        readBits2 = AnonymousClass1.$SwitchMap$com$google$zxing$qrcode$decoder$Mode[mode3.ordinal()];
                        if (readBits2 != i) {
                            if (readBits2 != 2) {
                                if (readBits2 == 3) {
                                    mode2 = mode3;
                                    decodeByteSegment(bitSource, stringBuilder, readBits3, characterSetECI, arrayList, map);
                                    break;
                                } else if (readBits2 == 4) {
                                    decodeKanjiSegment(bitSource, stringBuilder, readBits3);
                                    break;
                                } else {
                                    throw FormatException.getFormatInstance();
                                }
                            }
                            mode2 = mode3;
                            decodeAlphanumericSegment(bitSource, stringBuilder, readBits3, z);
                            break;
                        }
                        mode2 = mode3;
                        decodeNumericSegment(bitSource, stringBuilder, readBits3);
                        break;
                }
                mode2 = mode3;
                if (mode2 == Mode.TERMINATOR) {
                    String str;
                    String stringBuilder2 = stringBuilder.toString();
                    List list = arrayList.isEmpty() ? null : arrayList;
                    if (errorCorrectionLevel == null) {
                        str = null;
                    } else {
                        str = errorCorrectionLevel.toString();
                    }
                    return new DecoderResult(bArr, stringBuilder2, list, str, i2, i3);
                }
                i = 1;
            } catch (IllegalArgumentException unused) {
                throw FormatException.getFormatInstance();
            }
        }
    }

    private static void decodeHanziSegment(BitSource bitSource, StringBuilder stringBuilder, int i) throws FormatException {
        if (i * 13 <= bitSource.available()) {
            byte[] bArr = new byte[(i * 2)];
            int i2 = 0;
            while (i > 0) {
                int readBits = bitSource.readBits(13);
                readBits = (readBits % 96) | ((readBits / 96) << 8);
                readBits += readBits < 959 ? 41377 : 42657;
                bArr[i2] = (byte) (readBits >> 8);
                bArr[i2 + 1] = (byte) readBits;
                i2 += 2;
                i--;
            }
            try {
                stringBuilder.append(new String(bArr, StringUtils.GB2312));
                return;
            } catch (UnsupportedEncodingException unused) {
                throw FormatException.getFormatInstance();
            }
        }
        throw FormatException.getFormatInstance();
    }

    private static void decodeKanjiSegment(BitSource bitSource, StringBuilder stringBuilder, int i) throws FormatException {
        if (i * 13 <= bitSource.available()) {
            byte[] bArr = new byte[(i * 2)];
            int i2 = 0;
            while (i > 0) {
                int readBits = bitSource.readBits(13);
                readBits = (readBits % JfifUtil.MARKER_SOFn) | ((readBits / JfifUtil.MARKER_SOFn) << 8);
                readBits += readBits < 7936 ? 33088 : 49472;
                bArr[i2] = (byte) (readBits >> 8);
                bArr[i2 + 1] = (byte) readBits;
                i2 += 2;
                i--;
            }
            try {
                stringBuilder.append(new String(bArr, StringUtils.SHIFT_JIS));
                return;
            } catch (UnsupportedEncodingException unused) {
                throw FormatException.getFormatInstance();
            }
        }
        throw FormatException.getFormatInstance();
    }

    private static void decodeByteSegment(BitSource bitSource, StringBuilder stringBuilder, int i, CharacterSetECI characterSetECI, Collection<byte[]> collection, Map<DecodeHintType, ?> map) throws FormatException {
        if ((i << 3) <= bitSource.available()) {
            String guessEncoding;
            Object obj = new byte[i];
            for (int i2 = 0; i2 < i; i2++) {
                obj[i2] = (byte) bitSource.readBits(8);
            }
            if (characterSetECI == null) {
                guessEncoding = StringUtils.guessEncoding(obj, map);
            } else {
                guessEncoding = characterSetECI.name();
            }
            try {
                stringBuilder.append(new String(obj, guessEncoding));
                collection.add(obj);
                return;
            } catch (UnsupportedEncodingException unused) {
                throw FormatException.getFormatInstance();
            }
        }
        throw FormatException.getFormatInstance();
    }

    private static char toAlphaNumericChar(int i) throws FormatException {
        char[] cArr = ALPHANUMERIC_CHARS;
        if (i < cArr.length) {
            return cArr[i];
        }
        throw FormatException.getFormatInstance();
    }

    private static void decodeAlphanumericSegment(BitSource bitSource, StringBuilder stringBuilder, int i, boolean z) throws FormatException {
        while (i > 1) {
            if (bitSource.available() >= 11) {
                int readBits = bitSource.readBits(11);
                stringBuilder.append(toAlphaNumericChar(readBits / 45));
                stringBuilder.append(toAlphaNumericChar(readBits % 45));
                i -= 2;
            } else {
                throw FormatException.getFormatInstance();
            }
        }
        if (i == 1) {
            if (bitSource.available() >= 6) {
                stringBuilder.append(toAlphaNumericChar(bitSource.readBits(6)));
            } else {
                throw FormatException.getFormatInstance();
            }
        }
        if (z) {
            for (int length = stringBuilder.length(); length < stringBuilder.length(); length++) {
                if (stringBuilder.charAt(length) == '%') {
                    if (length < stringBuilder.length() - 1) {
                        int i2 = length + 1;
                        if (stringBuilder.charAt(i2) == '%') {
                            stringBuilder.deleteCharAt(i2);
                        }
                    }
                    stringBuilder.setCharAt(length, 29);
                }
            }
        }
    }

    private static void decodeNumericSegment(BitSource bitSource, StringBuilder stringBuilder, int i) throws FormatException {
        while (i >= 3) {
            if (bitSource.available() >= 10) {
                int readBits = bitSource.readBits(10);
                if (readBits < 1000) {
                    stringBuilder.append(toAlphaNumericChar(readBits / 100));
                    stringBuilder.append(toAlphaNumericChar((readBits / 10) % 10));
                    stringBuilder.append(toAlphaNumericChar(readBits % 10));
                    i -= 3;
                } else {
                    throw FormatException.getFormatInstance();
                }
            }
            throw FormatException.getFormatInstance();
        }
        int readBits2;
        if (i != 2) {
            if (i == 1) {
                if (bitSource.available() >= 4) {
                    readBits2 = bitSource.readBits(4);
                    if (readBits2 < 10) {
                        stringBuilder.append(toAlphaNumericChar(readBits2));
                    } else {
                        throw FormatException.getFormatInstance();
                    }
                }
                throw FormatException.getFormatInstance();
            }
        } else if (bitSource.available() >= 7) {
            readBits2 = bitSource.readBits(7);
            if (readBits2 < 100) {
                stringBuilder.append(toAlphaNumericChar(readBits2 / 10));
                stringBuilder.append(toAlphaNumericChar(readBits2 % 10));
                return;
            }
            throw FormatException.getFormatInstance();
        } else {
            throw FormatException.getFormatInstance();
        }
    }

    private static int parseECIValue(BitSource bitSource) throws FormatException {
        int readBits = bitSource.readBits(8);
        if ((readBits & 128) == 0) {
            return readBits & 127;
        }
        if ((readBits & JfifUtil.MARKER_SOFn) == 128) {
            return bitSource.readBits(8) | ((readBits & 63) << 8);
        }
        if ((readBits & CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY) == JfifUtil.MARKER_SOFn) {
            return bitSource.readBits(16) | ((readBits & 31) << 16);
        }
        throw FormatException.getFormatInstance();
    }
}
