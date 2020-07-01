package com.drew.imaging;

import com.drew.imaging.avi.AviMetadataReader;
import com.drew.imaging.bmp.BmpMetadataReader;
import com.drew.imaging.eps.EpsMetadataReader;
import com.drew.imaging.gif.GifMetadataReader;
import com.drew.imaging.ico.IcoMetadataReader;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.mp4.Mp4MetadataReader;
import com.drew.imaging.pcx.PcxMetadataReader;
import com.drew.imaging.png.PngMetadataReader;
import com.drew.imaging.psd.PsdMetadataReader;
import com.drew.imaging.quicktime.QuickTimeMetadataReader;
import com.drew.imaging.raf.RafMetadataReader;
import com.drew.imaging.tiff.TiffMetadataReader;
import com.drew.imaging.wav.WavMetadataReader;
import com.drew.imaging.webp.WebpMetadataReader;
import com.drew.lang.RandomAccessStreamReader;
import com.drew.lang.StringUtil;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.file.FileSystemMetadataReader;
import com.drew.metadata.file.FileTypeDirectory;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ImageMetadataReader {
    @NotNull
    public static Metadata readMetadata(@NotNull InputStream inputStream) throws ImageProcessingException, IOException {
        return readMetadata(inputStream, -1);
    }

    @NotNull
    public static Metadata readMetadata(@NotNull InputStream inputStream, long j) throws ImageProcessingException, IOException {
        inputStream = inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream);
        FileType detectFileType = FileTypeDetector.detectFileType(inputStream);
        Metadata readMetadata = readMetadata(inputStream, j, detectFileType);
        readMetadata.addDirectory(new FileTypeDirectory(detectFileType));
        return readMetadata;
    }

    @NotNull
    public static Metadata readMetadata(@NotNull InputStream inputStream, long j, FileType fileType) throws IOException, ImageProcessingException {
        switch (fileType) {
            case Jpeg:
                return JpegMetadataReader.readMetadata(inputStream);
            case Tiff:
            case Arw:
            case Cr2:
            case Nef:
            case Orf:
            case Rw2:
                return TiffMetadataReader.readMetadata(new RandomAccessStreamReader(inputStream, 2048, j));
            case Psd:
                return PsdMetadataReader.readMetadata(inputStream);
            case Png:
                return PngMetadataReader.readMetadata(inputStream);
            case Bmp:
                return BmpMetadataReader.readMetadata(inputStream);
            case Gif:
                return GifMetadataReader.readMetadata(inputStream);
            case Ico:
                return IcoMetadataReader.readMetadata(inputStream);
            case Pcx:
                return PcxMetadataReader.readMetadata(inputStream);
            case WebP:
                return WebpMetadataReader.readMetadata(inputStream);
            case Raf:
                return RafMetadataReader.readMetadata(inputStream);
            case Avi:
                return AviMetadataReader.readMetadata(inputStream);
            case Wav:
                return WavMetadataReader.readMetadata(inputStream);
            case Mov:
                return QuickTimeMetadataReader.readMetadata(inputStream);
            case Mp4:
                return Mp4MetadataReader.readMetadata(inputStream);
            case Eps:
                return EpsMetadataReader.readMetadata(inputStream);
            case Unknown:
                throw new ImageProcessingException("File format could not be determined");
            default:
                return new Metadata();
        }
    }

    @NotNull
    public static Metadata readMetadata(@NotNull File file) throws ImageProcessingException, IOException {
        InputStream fileInputStream = new FileInputStream(file);
        try {
            Metadata readMetadata = readMetadata(fileInputStream, file.length());
            new FileSystemMetadataReader().read(file, readMetadata);
            return readMetadata;
        } finally {
            fileInputStream.close();
        }
    }

    private ImageMetadataReader() throws Exception {
        throw new Exception("Not intended for instantiation");
    }

    public static void main(@NotNull String[] strArr) throws MetadataException, IOException {
        Collection<String> arrayList = new ArrayList(Arrays.asList(strArr));
        boolean remove = arrayList.remove("-markdown");
        boolean remove2 = arrayList.remove("-hex");
        if (arrayList.size() < 1) {
            String implementationVersion = ImageMetadataReader.class.getPackage().getImplementationVersion();
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("metadata-extractor version ");
            stringBuilder.append(implementationVersion);
            printStream.println(stringBuilder.toString());
            System.out.println();
            printStream = System.out;
            Object[] objArr = new Object[1];
            if (implementationVersion == null) {
                implementationVersion = "a.b.c";
            }
            objArr[0] = implementationVersion;
            printStream.println(String.format("Usage: java -jar metadata-extractor-%s.jar <filename> [<filename>] [-thumb] [-markdown] [-hex]", objArr));
            System.exit(1);
        }
        for (String str : arrayList) {
            String str2;
            String str3;
            long nanoTime = System.nanoTime();
            File file = new File(str2);
            if (!remove && arrayList.size() > 1) {
                System.out.printf("\n***** PROCESSING: %s%n%n", new Object[]{str2});
            }
            Metadata metadata = null;
            try {
                metadata = readMetadata(file);
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
            long nanoTime2 = System.nanoTime() - nanoTime;
            if (!remove) {
                System.out.printf("Processed %.3f MB file in %.2f ms%n%n", new Object[]{Double.valueOf(((double) file.length()) / 1048576.0d), Double.valueOf(((double) nanoTime2) / 1000000.0d)});
            }
            if (remove) {
                String name = file.getName();
                str2 = StringUtil.urlEncode(str2);
                ExifIFD0Directory exifIFD0Directory = (ExifIFD0Directory) metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
                String str4 = "";
                if (exifIFD0Directory == null) {
                    str3 = str4;
                } else {
                    str3 = exifIFD0Directory.getString(271);
                }
                if (exifIFD0Directory != null) {
                    str4 = exifIFD0Directory.getString(272);
                }
                System.out.println();
                System.out.println("---");
                System.out.println();
                System.out.printf("# %s - %s%n", new Object[]{str3, str4});
                System.out.println();
                System.out.printf("<a href=\"https://raw.githubusercontent.com/drewnoakes/metadata-extractor-images/master/%s\">%n", new Object[]{str2});
                System.out.printf("<img src=\"https://raw.githubusercontent.com/drewnoakes/metadata-extractor-images/master/%s\" width=\"300\"/><br/>%n", new Object[]{str2});
                System.out.println(name);
                System.out.println("</a>");
                System.out.println();
                System.out.println("Directory | Tag Id | Tag Name | Extracted Value");
                System.out.println(":--------:|-------:|----------|----------------");
            }
            for (Directory directory : metadata.getDirectories()) {
                String name2 = directory.getName();
                for (Tag tag : directory.getTags()) {
                    str3 = tag.getTagName();
                    String description = tag.getDescription();
                    if (description != null && description.length() > 1024) {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(description.substring(0, 1024));
                        stringBuilder2.append("...");
                        description = stringBuilder2.toString();
                    }
                    if (remove) {
                        System.out.printf("%s|0x%s|%s|%s%n", new Object[]{name2, Integer.toHexString(tag.getTagType()), str3, description});
                    } else if (remove2) {
                        System.out.printf("[%s - %s] %s = %s%n", new Object[]{name2, tag.getTagTypeHex(), str3, description});
                    } else {
                        System.out.printf("[%s] %s = %s%n", new Object[]{name2, str3, description});
                    }
                }
                for (String name22 : directory.getErrors()) {
                    PrintStream printStream2 = System.err;
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("ERROR: ");
                    stringBuilder3.append(name22);
                    printStream2.println(stringBuilder3.toString());
                }
            }
        }
    }
}
