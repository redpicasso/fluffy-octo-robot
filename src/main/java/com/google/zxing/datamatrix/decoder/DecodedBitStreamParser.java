package com.google.zxing.datamatrix.decoder;

import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.makernotes.CanonMakernoteDirectory;
import com.google.common.base.Ascii;
import com.google.firebase.storage.internal.ExponentialBackoffSender;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.DecoderResult;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class DecodedBitStreamParser {
    private static final char[] C40_BASIC_SET_CHARS = new char[]{'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] C40_SHIFT2_SET_CHARS = new char[]{'!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_'};
    private static final char[] TEXT_BASIC_SET_CHARS = new char[]{'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] TEXT_SHIFT2_SET_CHARS = C40_SHIFT2_SET_CHARS;
    private static final char[] TEXT_SHIFT3_SET_CHARS = new char[]{'`', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '{', '|', '}', '~', Ascii.MAX};

    /* renamed from: com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode = new int[Mode.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:11:0x0040, code:
            return;
     */
        static {
            /*
            r0 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode = r0;
            r0 = $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.C40_ENCODE;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.TEXT_ENCODE;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.ANSIX12_ENCODE;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r0 = $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.EDIFACT_ENCODE;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            r0 = $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r1 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.BASE256_ENCODE;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0040 }
            r2 = 5;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0040 }
        L_0x0040:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.1.<clinit>():void");
        }
    }

    private enum Mode {
        PAD_ENCODE,
        ASCII_ENCODE,
        C40_ENCODE,
        TEXT_ENCODE,
        ANSIX12_ENCODE,
        EDIFACT_ENCODE,
        BASE256_ENCODE
    }

    private DecodedBitStreamParser() {
    }

    static DecoderResult decode(byte[] bArr) throws FormatException {
        BitSource bitSource = new BitSource(bArr);
        StringBuilder stringBuilder = new StringBuilder(100);
        CharSequence stringBuilder2 = new StringBuilder(0);
        List arrayList = new ArrayList(1);
        Mode mode = Mode.ASCII_ENCODE;
        do {
            if (mode == Mode.ASCII_ENCODE) {
                mode = decodeAsciiSegment(bitSource, stringBuilder, stringBuilder2);
            } else {
                int i = AnonymousClass1.$SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[mode.ordinal()];
                if (i == 1) {
                    decodeC40Segment(bitSource, stringBuilder);
                } else if (i == 2) {
                    decodeTextSegment(bitSource, stringBuilder);
                } else if (i == 3) {
                    decodeAnsiX12Segment(bitSource, stringBuilder);
                } else if (i == 4) {
                    decodeEdifactSegment(bitSource, stringBuilder);
                } else if (i == 5) {
                    decodeBase256Segment(bitSource, stringBuilder, arrayList);
                } else {
                    throw FormatException.getFormatInstance();
                }
                mode = Mode.ASCII_ENCODE;
            }
            if (mode == Mode.PAD_ENCODE) {
                break;
            }
        } while (bitSource.available() > 0);
        if (stringBuilder2.length() > 0) {
            stringBuilder.append(stringBuilder2);
        }
        String stringBuilder3 = stringBuilder.toString();
        if (arrayList.isEmpty()) {
            arrayList = null;
        }
        return new DecoderResult(bArr, stringBuilder3, arrayList, null);
    }

    private static Mode decodeAsciiSegment(BitSource bitSource, StringBuilder stringBuilder, StringBuilder stringBuilder2) throws FormatException {
        Object obj = null;
        do {
            int readBits = bitSource.readBits(8);
            if (readBits == 0) {
                throw FormatException.getFormatInstance();
            } else if (readBits <= 128) {
                if (obj != null) {
                    readBits += 128;
                }
                stringBuilder.append((char) (readBits - 1));
                return Mode.ASCII_ENCODE;
            } else if (readBits == 129) {
                return Mode.PAD_ENCODE;
            } else {
                if (readBits <= 229) {
                    readBits -= 130;
                    if (readBits < 10) {
                        stringBuilder.append('0');
                    }
                    stringBuilder.append(readBits);
                } else {
                    String str = "\u001e\u0004";
                    switch (readBits) {
                        case 230:
                            return Mode.C40_ENCODE;
                        case 231:
                            return Mode.BASE256_ENCODE;
                        case 232:
                            stringBuilder.append(29);
                            break;
                        case 233:
                        case 234:
                        case 241:
                            break;
                        case 235:
                            obj = 1;
                            break;
                        case 236:
                            stringBuilder.append("[)>\u001e05\u001d");
                            stringBuilder2.insert(0, str);
                            break;
                        case 237:
                            stringBuilder.append("[)>\u001e06\u001d");
                            stringBuilder2.insert(0, str);
                            break;
                        case 238:
                            return Mode.ANSIX12_ENCODE;
                        case 239:
                            return Mode.TEXT_ENCODE;
                        case 240:
                            return Mode.EDIFACT_ENCODE;
                        default:
                            if (!(readBits == ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE && bitSource.available() == 0)) {
                                throw FormatException.getFormatInstance();
                            }
                    }
                }
            }
        } while (bitSource.available() > 0);
        return Mode.ASCII_ENCODE;
    }

    private static void decodeC40Segment(BitSource bitSource, StringBuilder stringBuilder) throws FormatException {
        int[] iArr = new int[3];
        Object obj = null;
        int i = 0;
        while (bitSource.available() != 8) {
            int readBits = bitSource.readBits(8);
            if (readBits != ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE) {
                parseTwoBytes(readBits, bitSource.readBits(8), iArr);
                Object obj2 = obj;
                for (int i2 = 0; i2 < 3; i2++) {
                    int i3 = iArr[i2];
                    if (i != 0) {
                        if (i != 1) {
                            if (i == 2) {
                                char[] cArr = C40_SHIFT2_SET_CHARS;
                                if (i3 < cArr.length) {
                                    char c = cArr[i3];
                                    if (obj2 != null) {
                                        stringBuilder.append((char) (c + 128));
                                    } else {
                                        stringBuilder.append(c);
                                    }
                                } else if (i3 == 27) {
                                    stringBuilder.append(29);
                                } else if (i3 == 30) {
                                    obj2 = 1;
                                } else {
                                    throw FormatException.getFormatInstance();
                                }
                                i = 0;
                            } else if (i != 3) {
                                throw FormatException.getFormatInstance();
                            } else if (obj2 != null) {
                                stringBuilder.append((char) (i3 + CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY));
                            } else {
                                stringBuilder.append((char) (i3 + 96));
                                i = 0;
                            }
                        } else if (obj2 != null) {
                            stringBuilder.append((char) (i3 + 128));
                        } else {
                            stringBuilder.append((char) i3);
                            i = 0;
                        }
                        obj2 = null;
                        i = 0;
                    } else if (i3 < 3) {
                        i = i3 + 1;
                    } else {
                        char[] cArr2 = C40_BASIC_SET_CHARS;
                        if (i3 < cArr2.length) {
                            char c2 = cArr2[i3];
                            if (obj2 != null) {
                                stringBuilder.append((char) (c2 + 128));
                                obj2 = null;
                            } else {
                                stringBuilder.append(c2);
                            }
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                    }
                }
                if (bitSource.available() > 0) {
                    obj = obj2;
                } else {
                    return;
                }
            }
            return;
        }
    }

    private static void decodeTextSegment(BitSource bitSource, StringBuilder stringBuilder) throws FormatException {
        int[] iArr = new int[3];
        Object obj = null;
        int i = 0;
        while (bitSource.available() != 8) {
            int readBits = bitSource.readBits(8);
            if (readBits != ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE) {
                parseTwoBytes(readBits, bitSource.readBits(8), iArr);
                Object obj2 = obj;
                for (int i2 = 0; i2 < 3; i2++) {
                    int i3 = iArr[i2];
                    if (i != 0) {
                        if (i != 1) {
                            char[] cArr;
                            char c;
                            if (i == 2) {
                                cArr = TEXT_SHIFT2_SET_CHARS;
                                if (i3 < cArr.length) {
                                    c = cArr[i3];
                                    if (obj2 != null) {
                                        stringBuilder.append((char) (c + 128));
                                    } else {
                                        stringBuilder.append(c);
                                    }
                                } else if (i3 == 27) {
                                    stringBuilder.append(29);
                                } else if (i3 == 30) {
                                    obj2 = 1;
                                } else {
                                    throw FormatException.getFormatInstance();
                                }
                                i = 0;
                            } else if (i == 3) {
                                cArr = TEXT_SHIFT3_SET_CHARS;
                                if (i3 < cArr.length) {
                                    c = cArr[i3];
                                    if (obj2 != null) {
                                        stringBuilder.append((char) (c + 128));
                                    } else {
                                        stringBuilder.append(c);
                                        i = 0;
                                    }
                                } else {
                                    throw FormatException.getFormatInstance();
                                }
                            } else {
                                throw FormatException.getFormatInstance();
                            }
                        } else if (obj2 != null) {
                            stringBuilder.append((char) (i3 + 128));
                        } else {
                            stringBuilder.append((char) i3);
                            i = 0;
                        }
                        obj2 = null;
                        i = 0;
                    } else if (i3 < 3) {
                        i = i3 + 1;
                    } else {
                        char[] cArr2 = TEXT_BASIC_SET_CHARS;
                        if (i3 < cArr2.length) {
                            char c2 = cArr2[i3];
                            if (obj2 != null) {
                                stringBuilder.append((char) (c2 + 128));
                                obj2 = null;
                            } else {
                                stringBuilder.append(c2);
                            }
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                    }
                }
                if (bitSource.available() > 0) {
                    obj = obj2;
                } else {
                    return;
                }
            }
            return;
        }
    }

    private static void decodeAnsiX12Segment(BitSource bitSource, StringBuilder stringBuilder) throws FormatException {
        int[] iArr = new int[3];
        while (bitSource.available() != 8) {
            int readBits = bitSource.readBits(8);
            if (readBits != ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE) {
                parseTwoBytes(readBits, bitSource.readBits(8), iArr);
                for (readBits = 0; readBits < 3; readBits++) {
                    int i = iArr[readBits];
                    if (i == 0) {
                        stringBuilder.append(13);
                    } else if (i == 1) {
                        stringBuilder.append('*');
                    } else if (i == 2) {
                        stringBuilder.append('>');
                    } else if (i == 3) {
                        stringBuilder.append(' ');
                    } else if (i < 14) {
                        stringBuilder.append((char) (i + 44));
                    } else if (i < 40) {
                        stringBuilder.append((char) (i + 51));
                    } else {
                        throw FormatException.getFormatInstance();
                    }
                }
                if (bitSource.available() <= 0) {
                    return;
                }
            }
            return;
        }
    }

    private static void parseTwoBytes(int i, int i2, int[] iArr) {
        i = ((i << 8) + i2) - 1;
        int i3 = i / 1600;
        iArr[0] = i3;
        i -= i3 * 1600;
        i3 = i / 40;
        iArr[1] = i3;
        iArr[2] = i - (i3 * 40);
    }

    private static void decodeEdifactSegment(BitSource bitSource, StringBuilder stringBuilder) {
        while (bitSource.available() > 16) {
            for (int i = 0; i < 4; i++) {
                int readBits = bitSource.readBits(6);
                if (readBits == 31) {
                    int bitOffset = 8 - bitSource.getBitOffset();
                    if (bitOffset != 8) {
                        bitSource.readBits(bitOffset);
                    }
                    return;
                }
                if ((readBits & 32) == 0) {
                    readBits |= 64;
                }
                stringBuilder.append((char) readBits);
            }
            if (bitSource.available() <= 0) {
                return;
            }
        }
    }

    private static void decodeBase256Segment(BitSource bitSource, StringBuilder stringBuilder, Collection<byte[]> collection) throws FormatException {
        int byteOffset = bitSource.getByteOffset() + 1;
        int i = byteOffset + 1;
        byteOffset = unrandomize255State(bitSource.readBits(8), byteOffset);
        if (byteOffset == 0) {
            byteOffset = bitSource.available() / 8;
        } else if (byteOffset >= ExponentialBackoffSender.RND_MAX) {
            byteOffset = ((byteOffset - 249) * ExponentialBackoffSender.RND_MAX) + unrandomize255State(bitSource.readBits(8), i);
            i++;
        }
        if (byteOffset >= 0) {
            Object obj = new byte[byteOffset];
            int i2 = 0;
            while (i2 < byteOffset) {
                if (bitSource.available() >= 8) {
                    int i3 = i + 1;
                    obj[i2] = (byte) unrandomize255State(bitSource.readBits(8), i);
                    i2++;
                    i = i3;
                } else {
                    throw FormatException.getFormatInstance();
                }
            }
            collection.add(obj);
            try {
                stringBuilder.append(new String(obj, "ISO8859_1"));
                return;
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException("Platform does not support required encoding: ".concat(String.valueOf(e)));
            }
        }
        throw FormatException.getFormatInstance();
    }

    private static int unrandomize255State(int i, int i2) {
        i -= ((i2 * 149) % 255) + 1;
        return i >= 0 ? i : i + 256;
    }
}
