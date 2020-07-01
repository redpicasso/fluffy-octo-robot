package com.drew.metadata.jpeg;

import com.drew.imaging.jpeg.JpegSegmentMetadataReader;
import com.drew.imaging.jpeg.JpegSegmentType;
import com.drew.lang.SequentialByteArrayReader;
import com.drew.lang.SequentialReader;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Metadata;
import com.drew.metadata.jpeg.HuffmanTablesDirectory.HuffmanTable;
import com.drew.metadata.jpeg.HuffmanTablesDirectory.HuffmanTable.HuffmanTableClass;
import java.io.IOException;
import java.util.Collections;

public class JpegDhtReader implements JpegSegmentMetadataReader {
    @NotNull
    public Iterable<JpegSegmentType> getSegmentTypes() {
        return Collections.singletonList(JpegSegmentType.DHT);
    }

    public void readJpegSegments(@NotNull Iterable<byte[]> iterable, @NotNull Metadata metadata, @NotNull JpegSegmentType jpegSegmentType) {
        for (byte[] sequentialByteArrayReader : iterable) {
            extract(new SequentialByteArrayReader(sequentialByteArrayReader), metadata);
        }
    }

    public void extract(@NotNull SequentialReader sequentialReader, @NotNull Metadata metadata) {
        HuffmanTablesDirectory huffmanTablesDirectory = (HuffmanTablesDirectory) metadata.getFirstDirectoryOfType(HuffmanTablesDirectory.class);
        if (huffmanTablesDirectory == null) {
            huffmanTablesDirectory = new HuffmanTablesDirectory();
            metadata.addDirectory(huffmanTablesDirectory);
        }
        while (sequentialReader.available() > 0) {
            try {
                byte b = sequentialReader.getByte();
                HuffmanTableClass typeOf = HuffmanTableClass.typeOf((b & 240) >> 4);
                int i = b & 15;
                byte[] bytes = getBytes(sequentialReader, 16);
                int i2 = 0;
                for (byte b2 : bytes) {
                    i2 += b2 & 255;
                }
                huffmanTablesDirectory.getTables().add(new HuffmanTable(typeOf, i, bytes, getBytes(sequentialReader, i2)));
            } catch (IOException e) {
                huffmanTablesDirectory.addError(e.getMessage());
            }
        }
        huffmanTablesDirectory.setInt(1, huffmanTablesDirectory.getTables().size());
    }

    private byte[] getBytes(@NotNull SequentialReader sequentialReader, int i) throws IOException {
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            byte b = sequentialReader.getByte();
            if ((b & 255) == 255) {
                byte b2 = sequentialReader.getByte();
                if (b2 != (byte) 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Marker ");
                    stringBuilder.append(JpegSegmentType.fromByte(b2));
                    stringBuilder.append(" found inside DHT segment");
                    throw new IOException(stringBuilder.toString());
                }
            }
            bArr[i2] = b;
        }
        return bArr;
    }
}
