package com.drew.imaging.jpeg;

import com.drew.lang.SequentialReader;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JpegSegmentReader {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final byte MARKER_EOI = (byte) -39;
    private static final byte SEGMENT_IDENTIFIER = (byte) -1;
    private static final byte SEGMENT_SOS = (byte) -38;

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0019  */
    @com.drew.lang.annotations.NotNull
    public static com.drew.imaging.jpeg.JpegSegmentData readSegments(@com.drew.lang.annotations.NotNull java.io.File r2, @com.drew.lang.annotations.Nullable java.lang.Iterable<com.drew.imaging.jpeg.JpegSegmentType> r3) throws com.drew.imaging.jpeg.JpegProcessingException, java.io.IOException {
        /*
        r0 = 0;
        r1 = new java.io.FileInputStream;	 Catch:{ all -> 0x0015 }
        r1.<init>(r2);	 Catch:{ all -> 0x0015 }
        r2 = new com.drew.lang.StreamReader;	 Catch:{ all -> 0x0013 }
        r2.<init>(r1);	 Catch:{ all -> 0x0013 }
        r2 = readSegments(r2, r3);	 Catch:{ all -> 0x0013 }
        r1.close();
        return r2;
    L_0x0013:
        r2 = move-exception;
        goto L_0x0017;
    L_0x0015:
        r2 = move-exception;
        r1 = r0;
    L_0x0017:
        if (r1 == 0) goto L_0x001c;
    L_0x0019:
        r1.close();
    L_0x001c:
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.drew.imaging.jpeg.JpegSegmentReader.readSegments(java.io.File, java.lang.Iterable):com.drew.imaging.jpeg.JpegSegmentData");
    }

    @NotNull
    public static JpegSegmentData readSegments(@NotNull SequentialReader sequentialReader, @Nullable Iterable<JpegSegmentType> iterable) throws JpegProcessingException, IOException {
        int uInt16 = sequentialReader.getUInt16();
        if (uInt16 == 65496) {
            Set set = null;
            if (iterable != null) {
                set = new HashSet();
                for (JpegSegmentType jpegSegmentType : iterable) {
                    set.add(Byte.valueOf(jpegSegmentType.byteValue));
                }
            }
            Set set2 = set;
            JpegSegmentData jpegSegmentData = new JpegSegmentData();
            while (true) {
                byte int8 = sequentialReader.getInt8();
                byte int82 = sequentialReader.getInt8();
                while (true) {
                    if (int8 == (byte) -1 && int82 != (byte) -1 && int82 != (byte) 0) {
                        break;
                    }
                    byte b = int82;
                    int82 = sequentialReader.getInt8();
                    int8 = b;
                }
                if (int82 == SEGMENT_SOS || int82 == MARKER_EOI) {
                    return jpegSegmentData;
                }
                uInt16 = sequentialReader.getUInt16() - 2;
                if (uInt16 < 0) {
                    throw new JpegProcessingException("JPEG segment size would be less than zero");
                } else if (set2 == null || set2.contains(Byte.valueOf(int82))) {
                    jpegSegmentData.addSegment(int82, sequentialReader.getBytes(uInt16));
                } else if (!sequentialReader.trySkip((long) uInt16)) {
                    return jpegSegmentData;
                }
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("JPEG data is expected to begin with 0xFFD8 (ÿØ) not 0x");
            stringBuilder.append(Integer.toHexString(uInt16));
            throw new JpegProcessingException(stringBuilder.toString());
        }
    }

    private JpegSegmentReader() throws Exception {
        throw new Exception("Not intended for instantiation.");
    }
}
