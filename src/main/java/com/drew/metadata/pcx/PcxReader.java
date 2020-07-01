package com.drew.metadata.pcx;

import com.drew.imaging.ImageProcessingException;
import com.drew.lang.SequentialReader;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;

public class PcxReader {
    public void extract(@NotNull SequentialReader sequentialReader, @NotNull Metadata metadata) {
        sequentialReader.setMotorolaByteOrder(false);
        Directory pcxDirectory = new PcxDirectory();
        metadata.addDirectory(pcxDirectory);
        try {
            if (sequentialReader.getInt8() == (byte) 10) {
                pcxDirectory.setInt(1, sequentialReader.getInt8());
                if (sequentialReader.getInt8() == (byte) 1) {
                    pcxDirectory.setInt(2, sequentialReader.getUInt8());
                    pcxDirectory.setInt(3, sequentialReader.getUInt16());
                    pcxDirectory.setInt(4, sequentialReader.getUInt16());
                    pcxDirectory.setInt(5, sequentialReader.getUInt16());
                    pcxDirectory.setInt(6, sequentialReader.getUInt16());
                    pcxDirectory.setInt(7, sequentialReader.getUInt16());
                    pcxDirectory.setInt(8, sequentialReader.getUInt16());
                    pcxDirectory.setByteArray(9, sequentialReader.getBytes(48));
                    sequentialReader.skip(1);
                    pcxDirectory.setInt(10, sequentialReader.getUInt8());
                    pcxDirectory.setInt(11, sequentialReader.getUInt16());
                    int uInt16 = sequentialReader.getUInt16();
                    if (uInt16 != 0) {
                        pcxDirectory.setInt(12, uInt16);
                    }
                    uInt16 = sequentialReader.getUInt16();
                    if (uInt16 != 0) {
                        pcxDirectory.setInt(13, uInt16);
                    }
                    int uInt162 = sequentialReader.getUInt16();
                    if (uInt162 != 0) {
                        pcxDirectory.setInt(14, uInt162);
                        return;
                    }
                    return;
                }
                throw new ImageProcessingException("Invalid PCX encoding byte");
            }
            throw new ImageProcessingException("Invalid PCX identifier byte");
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception reading PCX file metadata: ");
            stringBuilder.append(e.getMessage());
            pcxDirectory.addError(stringBuilder.toString());
        }
    }
}
