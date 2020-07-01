package com.drew.metadata.adobe;

import com.drew.imaging.jpeg.JpegSegmentMetadataReader;
import com.drew.imaging.jpeg.JpegSegmentType;
import com.drew.lang.SequentialByteArrayReader;
import com.drew.lang.SequentialReader;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import java.io.IOException;
import java.util.Collections;

public class AdobeJpegReader implements JpegSegmentMetadataReader {
    public static final String PREAMBLE = "Adobe";

    @NotNull
    public Iterable<JpegSegmentType> getSegmentTypes() {
        return Collections.singletonList(JpegSegmentType.APPE);
    }

    public void readJpegSegments(@NotNull Iterable<byte[]> iterable, @NotNull Metadata metadata, @NotNull JpegSegmentType jpegSegmentType) {
        for (byte[] bArr : iterable) {
            if (bArr.length == 12) {
                if (PREAMBLE.equalsIgnoreCase(new String(bArr, 0, 5))) {
                    extract(new SequentialByteArrayReader(bArr), metadata);
                }
            }
        }
    }

    public void extract(@NotNull SequentialReader sequentialReader, @NotNull Metadata metadata) {
        Directory adobeJpegDirectory = new AdobeJpegDirectory();
        metadata.addDirectory(adobeJpegDirectory);
        try {
            sequentialReader.setMotorolaByteOrder(false);
            if (sequentialReader.getString(5).equals(PREAMBLE)) {
                adobeJpegDirectory.setInt(0, sequentialReader.getUInt16());
                adobeJpegDirectory.setInt(1, sequentialReader.getUInt16());
                adobeJpegDirectory.setInt(2, sequentialReader.getUInt16());
                adobeJpegDirectory.setInt(3, sequentialReader.getInt8());
                return;
            }
            adobeJpegDirectory.addError("Invalid Adobe JPEG data header.");
        } catch (IOException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IO exception processing data: ");
            stringBuilder.append(e.getMessage());
            adobeJpegDirectory.addError(stringBuilder.toString());
        }
    }
}
