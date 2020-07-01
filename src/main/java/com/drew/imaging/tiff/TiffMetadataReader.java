package com.drew.imaging.tiff;

import com.drew.lang.RandomAccessFileReader;
import com.drew.lang.RandomAccessReader;
import com.drew.lang.RandomAccessStreamReader;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifTiffHandler;
import com.drew.metadata.file.FileSystemMetadataReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class TiffMetadataReader {
    @NotNull
    public static Metadata readMetadata(@NotNull File file) throws IOException, TiffProcessingException {
        Metadata metadata = "r";
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, metadata);
        try {
            metadata = readMetadata(new RandomAccessFileReader(randomAccessFile));
            new FileSystemMetadataReader().read(file, metadata);
            return metadata;
        } finally {
            randomAccessFile.close();
        }
    }

    @NotNull
    public static Metadata readMetadata(@NotNull InputStream inputStream) throws IOException, TiffProcessingException {
        return readMetadata(new RandomAccessStreamReader(inputStream));
    }

    @NotNull
    public static Metadata readMetadata(@NotNull RandomAccessReader randomAccessReader) throws IOException, TiffProcessingException {
        Metadata metadata = new Metadata();
        new TiffReader().processTiff(randomAccessReader, new ExifTiffHandler(metadata, null), 0);
        return metadata;
    }
}
