package com.drew.metadata.ico;

import com.drew.lang.SequentialReader;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import java.io.IOException;

public class IcoReader {
    public void extract(@NotNull SequentialReader sequentialReader, @NotNull Metadata metadata) {
        String str = "Exception reading ICO file metadata: ";
        int i = 0;
        sequentialReader.setMotorolaByteOrder(false);
        try {
            Directory icoDirectory;
            if (sequentialReader.getUInt16() != 0) {
                icoDirectory = new IcoDirectory();
                icoDirectory.addError("Invalid header bytes");
                metadata.addDirectory(icoDirectory);
                return;
            }
            int uInt16 = sequentialReader.getUInt16();
            if (uInt16 == 1 || uInt16 == 2) {
                int uInt162 = sequentialReader.getUInt16();
                if (uInt162 == 0) {
                    icoDirectory = new IcoDirectory();
                    icoDirectory.addError("Image count cannot be zero");
                    metadata.addDirectory(icoDirectory);
                    return;
                }
                while (i < uInt162) {
                    Directory icoDirectory2 = new IcoDirectory();
                    try {
                        icoDirectory2.setInt(1, uInt16);
                        icoDirectory2.setInt(2, sequentialReader.getUInt8());
                        icoDirectory2.setInt(3, sequentialReader.getUInt8());
                        icoDirectory2.setInt(4, sequentialReader.getUInt8());
                        sequentialReader.getUInt8();
                        if (uInt16 == 1) {
                            icoDirectory2.setInt(5, sequentialReader.getUInt16());
                            icoDirectory2.setInt(7, sequentialReader.getUInt16());
                        } else {
                            icoDirectory2.setInt(6, sequentialReader.getUInt16());
                            icoDirectory2.setInt(8, sequentialReader.getUInt16());
                        }
                        icoDirectory2.setLong(9, sequentialReader.getUInt32());
                        icoDirectory2.setLong(10, sequentialReader.getUInt32());
                    } catch (IOException e) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(str);
                        stringBuilder.append(e.getMessage());
                        icoDirectory2.addError(stringBuilder.toString());
                    }
                    metadata.addDirectory(icoDirectory2);
                    i++;
                }
                return;
            }
            icoDirectory = new IcoDirectory();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Invalid type ");
            stringBuilder2.append(uInt16);
            stringBuilder2.append(" -- expecting 1 or 2");
            icoDirectory.addError(stringBuilder2.toString());
            metadata.addDirectory(icoDirectory);
        } catch (IOException e2) {
            Directory icoDirectory3 = new IcoDirectory();
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(str);
            stringBuilder3.append(e2.getMessage());
            icoDirectory3.addError(stringBuilder3.toString());
            metadata.addDirectory(icoDirectory3);
        }
    }
}
