package com.drew.imaging.raf;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Metadata;
import com.drew.metadata.file.FileSystemMetadataReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RafMetadataReader {
    @NotNull
    public static Metadata readMetadata(@NotNull File file) throws JpegProcessingException, IOException {
        InputStream fileInputStream = new FileInputStream(file);
        try {
            Metadata readMetadata = readMetadata(fileInputStream);
            new FileSystemMetadataReader().read(file, readMetadata);
            return readMetadata;
        } finally {
            fileInputStream.close();
        }
    }

    @NotNull
    public static Metadata readMetadata(@NotNull InputStream inputStream) throws JpegProcessingException, IOException {
        if (inputStream.markSupported()) {
            inputStream.mark(512);
            byte[] bArr = new byte[512];
            int read = inputStream.read(bArr);
            if (read != -1) {
                inputStream.reset();
                int i = 0;
                while (i < read - 2) {
                    if (bArr[i] == (byte) -1 && bArr[i + 1] == (byte) -40 && bArr[i + 2] == (byte) -1) {
                        long j = (long) i;
                        if (inputStream.skip(j) != j) {
                            throw new IOException("Skipping stream bytes failed");
                        }
                        return JpegMetadataReader.readMetadata(inputStream);
                    }
                    i++;
                }
                return JpegMetadataReader.readMetadata(inputStream);
            }
            throw new IOException("Stream is empty");
        }
        throw new IOException("Stream must support mark/reset");
    }

    private RafMetadataReader() throws Exception {
        throw new Exception("Not intended for instantiation");
    }
}
