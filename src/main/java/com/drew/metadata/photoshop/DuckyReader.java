package com.drew.metadata.photoshop;

import com.drew.imaging.jpeg.JpegSegmentMetadataReader;
import com.drew.imaging.jpeg.JpegSegmentType;
import com.drew.lang.Charsets;
import com.drew.lang.SequentialByteArrayReader;
import com.drew.lang.SequentialReader;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import java.io.IOException;
import java.util.Collections;

public class DuckyReader implements JpegSegmentMetadataReader {
    @NotNull
    private static final String JPEG_SEGMENT_PREAMBLE = "Ducky";

    @NotNull
    public Iterable<JpegSegmentType> getSegmentTypes() {
        return Collections.singletonList(JpegSegmentType.APPC);
    }

    public void readJpegSegments(@NotNull Iterable<byte[]> iterable, @NotNull Metadata metadata, @NotNull JpegSegmentType jpegSegmentType) {
        for (byte[] bArr : iterable) {
            if (bArr.length >= 5) {
                if (JPEG_SEGMENT_PREAMBLE.equals(new String(bArr, 0, 5))) {
                    extract(new SequentialByteArrayReader(bArr, 5), metadata);
                }
            }
        }
    }

    public void extract(@NotNull SequentialReader sequentialReader, @NotNull Metadata metadata) {
        Directory duckyDirectory = new DuckyDirectory();
        metadata.addDirectory(duckyDirectory);
        while (true) {
            try {
                int uInt16 = sequentialReader.getUInt16();
                if (uInt16 == 0) {
                    break;
                }
                int uInt162 = sequentialReader.getUInt16();
                if (uInt16 != 1) {
                    if (uInt16 == 2 || uInt16 == 3) {
                        sequentialReader.skip(4);
                        duckyDirectory.setStringValue(uInt16, sequentialReader.getStringValue(uInt162 - 4, Charsets.UTF_16BE));
                    } else {
                        duckyDirectory.setByteArray(uInt16, sequentialReader.getBytes(uInt162));
                    }
                } else if (uInt162 != 4) {
                    duckyDirectory.addError("Unexpected length for the quality tag");
                    return;
                } else {
                    duckyDirectory.setInt(uInt16, sequentialReader.getInt32());
                }
            } catch (IOException e) {
                duckyDirectory.addError(e.getMessage());
            }
        }
    }
}
