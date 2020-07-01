package com.drew.metadata.iptc;

import com.drew.imaging.jpeg.JpegSegmentMetadataReader;
import com.drew.imaging.jpeg.JpegSegmentType;
import com.drew.lang.SequentialByteArrayReader;
import com.drew.lang.SequentialReader;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import java.io.IOException;
import java.util.Collections;

public class IptcReader implements JpegSegmentMetadataReader {
    private static final byte IptcMarkerByte = (byte) 28;

    @NotNull
    public Iterable<JpegSegmentType> getSegmentTypes() {
        return Collections.singletonList(JpegSegmentType.APPD);
    }

    public void readJpegSegments(@NotNull Iterable<byte[]> iterable, @NotNull Metadata metadata, @NotNull JpegSegmentType jpegSegmentType) {
        for (byte[] bArr : iterable) {
            if (bArr.length != 0 && bArr[0] == (byte) 28) {
                extract(new SequentialByteArrayReader(bArr), metadata, (long) bArr.length);
            }
        }
    }

    public void extract(@NotNull SequentialReader sequentialReader, @NotNull Metadata metadata, long j) {
        extract(sequentialReader, metadata, j, null);
    }

    public void extract(@NotNull SequentialReader sequentialReader, @NotNull Metadata metadata, long j, @Nullable Directory directory) {
        Directory iptcDirectory = new IptcDirectory();
        metadata.addDirectory(iptcDirectory);
        if (directory != null) {
            iptcDirectory.setParent(directory);
        }
        int i = 0;
        while (((long) i) < j) {
            try {
                short uInt8 = sequentialReader.getUInt8();
                i++;
                if (uInt8 != (short) 28) {
                    if (((long) i) != j) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Invalid IPTC tag marker at offset ");
                        stringBuilder.append(i - 1);
                        stringBuilder.append(". Expected '0x");
                        stringBuilder.append(Integer.toHexString(28));
                        stringBuilder.append("' but got '0x");
                        stringBuilder.append(Integer.toHexString(uInt8));
                        stringBuilder.append("'.");
                        iptcDirectory.addError(stringBuilder.toString());
                    }
                    return;
                } else if (((long) (i + 4)) > j) {
                    iptcDirectory.addError("Too few bytes remain for a valid IPTC tag");
                    return;
                } else {
                    try {
                        short uInt82 = sequentialReader.getUInt8();
                        short uInt83 = sequentialReader.getUInt8();
                        int uInt16 = sequentialReader.getUInt16();
                        if (uInt16 > 32767) {
                            uInt16 = ((uInt16 & 32767) << 16) | sequentialReader.getUInt16();
                            i += 2;
                        }
                        int i2 = uInt16;
                        i = (i + 4) + i2;
                        if (((long) i) > j) {
                            iptcDirectory.addError("Data for tag extends beyond end of IPTC segment");
                            return;
                        }
                        try {
                            processTag(sequentialReader, iptcDirectory, uInt82, uInt83, i2);
                        } catch (IOException unused) {
                            iptcDirectory.addError("Error processing IPTC tag");
                            return;
                        }
                    } catch (IOException unused2) {
                        iptcDirectory.addError("IPTC data segment ended mid-way through tag descriptor");
                        return;
                    }
                }
            } catch (IOException unused3) {
                iptcDirectory.addError("Unable to read starting byte of IPTC tag");
            }
        }
    }

    /* JADX WARNING: Missing block: B:16:0x0026, code:
            if (r6 != com.drew.metadata.iptc.IptcDirectory.TAG_PROGRAM_VERSION) goto L_0x0059;
     */
    private void processTag(@com.drew.lang.annotations.NotNull com.drew.lang.SequentialReader r4, @com.drew.lang.annotations.NotNull com.drew.metadata.Directory r5, int r6, int r7, int r8) throws java.io.IOException {
        /*
        r3 = this;
        r6 = r6 << 8;
        r6 = r6 | r7;
        if (r8 != 0) goto L_0x000b;
    L_0x0005:
        r4 = "";
        r5.setString(r6, r4);
        return;
    L_0x000b:
        r7 = 256; // 0x100 float:3.59E-43 double:1.265E-321;
        r0 = 346; // 0x15a float:4.85E-43 double:1.71E-321;
        r1 = 1;
        if (r6 == r7) goto L_0x0049;
    L_0x0012:
        r7 = 278; // 0x116 float:3.9E-43 double:1.374E-321;
        if (r6 == r7) goto L_0x0049;
    L_0x0016:
        if (r6 == r0) goto L_0x0036;
    L_0x0018:
        r7 = 378; // 0x17a float:5.3E-43 double:1.87E-321;
        if (r6 == r7) goto L_0x0049;
    L_0x001c:
        r7 = 512; // 0x200 float:7.175E-43 double:2.53E-321;
        if (r6 == r7) goto L_0x0049;
    L_0x0020:
        r7 = 522; // 0x20a float:7.31E-43 double:2.58E-321;
        if (r6 == r7) goto L_0x0029;
    L_0x0024:
        r7 = 582; // 0x246 float:8.16E-43 double:2.875E-321;
        if (r6 == r7) goto L_0x0049;
    L_0x0028:
        goto L_0x0059;
    L_0x0029:
        r7 = r4.getUInt8();
        r5.setInt(r6, r7);
        r8 = r8 - r1;
        r5 = (long) r8;
        r4.skip(r5);
        return;
    L_0x0036:
        r4 = r4.getBytes(r8);
        r7 = com.drew.metadata.iptc.Iso2022Converter.convertISO2022CharsetToJavaCharset(r4);
        if (r7 != 0) goto L_0x0045;
    L_0x0040:
        r7 = new java.lang.String;
        r7.<init>(r4);
    L_0x0045:
        r5.setString(r6, r7);
        return;
    L_0x0049:
        r7 = 2;
        if (r8 < r7) goto L_0x0059;
    L_0x004c:
        r0 = r4.getUInt16();
        r8 = r8 - r7;
        r7 = (long) r8;
        r4.skip(r7);
        r5.setInt(r6, r0);
        return;
    L_0x0059:
        r7 = r5.getString(r0);
        r0 = 0;
        if (r7 == 0) goto L_0x0065;
    L_0x0060:
        r2 = java.nio.charset.Charset.forName(r7);	 Catch:{ Throwable -> 0x0065 }
        goto L_0x0066;
    L_0x0065:
        r2 = r0;
    L_0x0066:
        if (r7 == 0) goto L_0x006d;
    L_0x0068:
        r4 = r4.getStringValue(r8, r2);
        goto L_0x0084;
    L_0x006d:
        r4 = r4.getBytes(r8);
        r7 = com.drew.metadata.iptc.Iso2022Converter.guessCharSet(r4);
        if (r7 == 0) goto L_0x007e;
    L_0x0077:
        r8 = new com.drew.metadata.StringValue;
        r8.<init>(r4, r7);
        r4 = r8;
        goto L_0x0084;
    L_0x007e:
        r7 = new com.drew.metadata.StringValue;
        r7.<init>(r4, r0);
        r4 = r7;
    L_0x0084:
        r7 = r5.containsTag(r6);
        if (r7 == 0) goto L_0x00a5;
    L_0x008a:
        r7 = r5.getStringValueArray(r6);
        if (r7 != 0) goto L_0x0093;
    L_0x0090:
        r7 = new com.drew.metadata.StringValue[r1];
        goto L_0x009d;
    L_0x0093:
        r8 = r7.length;
        r8 = r8 + r1;
        r8 = new com.drew.metadata.StringValue[r8];
        r0 = r7.length;
        r2 = 0;
        java.lang.System.arraycopy(r7, r2, r8, r2, r0);
        r7 = r8;
    L_0x009d:
        r8 = r7.length;
        r8 = r8 - r1;
        r7[r8] = r4;
        r5.setStringValueArray(r6, r7);
        goto L_0x00a8;
    L_0x00a5:
        r5.setStringValue(r6, r4);
    L_0x00a8:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.drew.metadata.iptc.IptcReader.processTag(com.drew.lang.SequentialReader, com.drew.metadata.Directory, int, int, int):void");
    }
}
