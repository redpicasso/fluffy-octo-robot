package com.drew.imaging.pcx;

import com.drew.lang.StreamReader;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Metadata;
import com.drew.metadata.file.FileSystemMetadataReader;
import com.drew.metadata.pcx.PcxReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PcxMetadataReader {
    @NotNull
    public static Metadata readMetadata(@NotNull File file) throws IOException {
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
    public static Metadata readMetadata(@NotNull InputStream inputStream) {
        Metadata metadata = new Metadata();
        new PcxReader().extract(new StreamReader(inputStream), metadata);
        return metadata;
    }
}
