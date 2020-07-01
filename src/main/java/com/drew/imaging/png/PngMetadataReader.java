package com.drew.imaging.png;

import com.drew.lang.ByteConvert;
import com.drew.lang.Charsets;
import com.drew.lang.DateUtil;
import com.drew.lang.KeyValuePair;
import com.drew.lang.RandomAccessStreamReader;
import com.drew.lang.SequentialByteArrayReader;
import com.drew.lang.SequentialReader;
import com.drew.lang.StreamReader;
import com.drew.lang.StreamUtil;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.StringValue;
import com.drew.metadata.file.FileSystemMetadataReader;
import com.drew.metadata.icc.IccReader;
import com.drew.metadata.png.PngChromaticitiesDirectory;
import com.drew.metadata.png.PngDirectory;
import com.drew.metadata.xmp.XmpReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipException;

public class PngMetadataReader {
    private static Set<PngChunkType> _desiredChunkTypes;
    private static Charset _latin1Encoding = Charsets.ISO_8859_1;

    static {
        Set hashSet = new HashSet();
        hashSet.add(PngChunkType.IHDR);
        hashSet.add(PngChunkType.PLTE);
        hashSet.add(PngChunkType.tRNS);
        hashSet.add(PngChunkType.cHRM);
        hashSet.add(PngChunkType.sRGB);
        hashSet.add(PngChunkType.gAMA);
        hashSet.add(PngChunkType.iCCP);
        hashSet.add(PngChunkType.bKGD);
        hashSet.add(PngChunkType.tEXt);
        hashSet.add(PngChunkType.zTXt);
        hashSet.add(PngChunkType.iTXt);
        hashSet.add(PngChunkType.tIME);
        hashSet.add(PngChunkType.pHYs);
        hashSet.add(PngChunkType.sBIT);
        _desiredChunkTypes = Collections.unmodifiableSet(hashSet);
    }

    @NotNull
    public static Metadata readMetadata(@NotNull File file) throws PngProcessingException, IOException {
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
    public static Metadata readMetadata(@NotNull InputStream inputStream) throws PngProcessingException, IOException {
        Iterable<PngChunk> extract = new PngChunkReader().extract(new StreamReader(inputStream), _desiredChunkTypes);
        Metadata metadata = new Metadata();
        for (PngChunk processChunk : extract) {
            try {
                processChunk(metadata, processChunk);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
        return metadata;
    }

    private static void processChunk(@NotNull Metadata metadata, @NotNull PngChunk pngChunk) throws PngProcessingException, IOException {
        String stringValue;
        Metadata metadata2 = metadata;
        PngChunkType type = pngChunk.getType();
        byte[] bytes = pngChunk.getBytes();
        Directory pngDirectory;
        Directory pngDirectory2;
        byte b;
        if (type.equals(PngChunkType.IHDR)) {
            PngHeader pngHeader = new PngHeader(bytes);
            pngDirectory = new PngDirectory(PngChunkType.IHDR);
            pngDirectory.setInt(1, pngHeader.getImageWidth());
            pngDirectory.setInt(2, pngHeader.getImageHeight());
            pngDirectory.setInt(3, pngHeader.getBitsPerSample());
            pngDirectory.setInt(4, pngHeader.getColorType().getNumericValue());
            pngDirectory.setInt(5, pngHeader.getCompressionType() & 255);
            pngDirectory.setInt(6, pngHeader.getFilterMethod());
            pngDirectory.setInt(7, pngHeader.getInterlaceMethod());
            metadata2.addDirectory(pngDirectory);
        } else if (type.equals(PngChunkType.PLTE)) {
            pngDirectory2 = new PngDirectory(PngChunkType.PLTE);
            pngDirectory2.setInt(8, bytes.length / 3);
            metadata2.addDirectory(pngDirectory2);
        } else if (type.equals(PngChunkType.tRNS)) {
            pngDirectory2 = new PngDirectory(PngChunkType.tRNS);
            pngDirectory2.setInt(9, 1);
            metadata2.addDirectory(pngDirectory2);
        } else if (type.equals(PngChunkType.sRGB)) {
            b = bytes[0];
            pngDirectory = new PngDirectory(PngChunkType.sRGB);
            pngDirectory.setInt(10, b);
            metadata2.addDirectory(pngDirectory);
        } else if (type.equals(PngChunkType.cHRM)) {
            PngChromaticities pngChromaticities = new PngChromaticities(bytes);
            pngDirectory = new PngChromaticitiesDirectory();
            pngDirectory.setInt(1, pngChromaticities.getWhitePointX());
            pngDirectory.setInt(2, pngChromaticities.getWhitePointY());
            pngDirectory.setInt(3, pngChromaticities.getRedX());
            pngDirectory.setInt(4, pngChromaticities.getRedY());
            pngDirectory.setInt(5, pngChromaticities.getGreenX());
            pngDirectory.setInt(6, pngChromaticities.getGreenY());
            pngDirectory.setInt(7, pngChromaticities.getBlueX());
            pngDirectory.setInt(8, pngChromaticities.getBlueY());
            metadata2.addDirectory(pngDirectory);
        } else if (type.equals(PngChunkType.gAMA)) {
            int toInt32BigEndian = ByteConvert.toInt32BigEndian(bytes);
            new SequentialByteArrayReader(bytes).getInt32();
            pngDirectory = new PngDirectory(PngChunkType.gAMA);
            pngDirectory.setDouble(11, ((double) toInt32BigEndian) / 100000.0d);
            metadata2.addDirectory(pngDirectory);
        } else {
            String str = "Invalid compression method value";
            SequentialReader sequentialByteArrayReader;
            StringValue nullTerminatedStringValue;
            if (type.equals(PngChunkType.iCCP)) {
                sequentialByteArrayReader = new SequentialByteArrayReader(bytes);
                byte[] nullTerminatedBytes = sequentialByteArrayReader.getNullTerminatedBytes(80);
                Directory pngDirectory3 = new PngDirectory(PngChunkType.iCCP);
                pngDirectory3.setStringValue(12, new StringValue(nullTerminatedBytes, _latin1Encoding));
                if (sequentialByteArrayReader.getInt8() == (byte) 0) {
                    try {
                        InputStream inflaterInputStream = new InflaterInputStream(new ByteArrayInputStream(sequentialByteArrayReader.getBytes(bytes.length - ((nullTerminatedBytes.length + 1) + 1))));
                        new IccReader().extract(new RandomAccessStreamReader(inflaterInputStream), metadata2, pngDirectory3);
                        inflaterInputStream.close();
                    } catch (ZipException e) {
                        pngDirectory3.addError(String.format("Exception decompressing PNG iCCP chunk : %s", new Object[]{e.getMessage()}));
                        metadata2.addDirectory(pngDirectory3);
                    }
                } else {
                    pngDirectory3.addError(str);
                }
                metadata2.addDirectory(pngDirectory3);
            } else if (type.equals(PngChunkType.bKGD)) {
                pngDirectory2 = new PngDirectory(PngChunkType.bKGD);
                pngDirectory2.setByteArray(15, bytes);
                metadata2.addDirectory(pngDirectory2);
            } else if (type.equals(PngChunkType.tEXt)) {
                sequentialByteArrayReader = new SequentialByteArrayReader(bytes);
                nullTerminatedStringValue = sequentialByteArrayReader.getNullTerminatedStringValue(80, _latin1Encoding);
                str = nullTerminatedStringValue.toString();
                StringValue nullTerminatedStringValue2 = sequentialByteArrayReader.getNullTerminatedStringValue(bytes.length - (nullTerminatedStringValue.getBytes().length + 1), _latin1Encoding);
                List arrayList = new ArrayList();
                arrayList.add(new KeyValuePair(str, nullTerminatedStringValue2));
                pngDirectory2 = new PngDirectory(PngChunkType.tEXt);
                pngDirectory2.setObject(13, arrayList);
                metadata2.addDirectory(pngDirectory2);
            } else {
                String str2 = "XML:com.adobe.xmp";
                byte[] readAllBytes;
                List arrayList2;
                SequentialByteArrayReader sequentialByteArrayReader2;
                int uInt16;
                if (type.equals(PngChunkType.zTXt)) {
                    sequentialByteArrayReader = new SequentialByteArrayReader(bytes);
                    nullTerminatedStringValue = sequentialByteArrayReader.getNullTerminatedStringValue(80, _latin1Encoding);
                    stringValue = nullTerminatedStringValue.toString();
                    int length = bytes.length - ((nullTerminatedStringValue.getBytes().length + 1) + 1);
                    if (sequentialByteArrayReader.getInt8() == (byte) 0) {
                        try {
                            readAllBytes = StreamUtil.readAllBytes(new InflaterInputStream(new ByteArrayInputStream(bytes, bytes.length - length, length)));
                        } catch (ZipException e2) {
                            pngDirectory = new PngDirectory(PngChunkType.zTXt);
                            pngDirectory.addError(String.format("Exception decompressing PNG zTXt chunk with keyword \"%s\": %s", new Object[]{stringValue, e2.getMessage()}));
                            metadata2.addDirectory(pngDirectory);
                        }
                    } else {
                        pngDirectory2 = new PngDirectory(PngChunkType.zTXt);
                        pngDirectory2.addError(str);
                        metadata2.addDirectory(pngDirectory2);
                        readAllBytes = null;
                    }
                    if (readAllBytes == null) {
                        return;
                    }
                    if (stringValue.equals(str2)) {
                        new XmpReader().extract(readAllBytes, metadata2);
                        return;
                    }
                    arrayList2 = new ArrayList();
                    arrayList2.add(new KeyValuePair(stringValue, new StringValue(readAllBytes, _latin1Encoding)));
                    pngDirectory = new PngDirectory(PngChunkType.zTXt);
                    pngDirectory.setObject(13, arrayList2);
                    metadata2.addDirectory(pngDirectory);
                } else if (type.equals(PngChunkType.iTXt)) {
                    sequentialByteArrayReader = new SequentialByteArrayReader(bytes);
                    nullTerminatedStringValue = sequentialByteArrayReader.getNullTerminatedStringValue(80, _latin1Encoding);
                    stringValue = nullTerminatedStringValue.toString();
                    byte int8 = sequentialByteArrayReader.getInt8();
                    byte int82 = sequentialByteArrayReader.getInt8();
                    int length2 = bytes.length - (((((((nullTerminatedStringValue.getBytes().length + 1) + 1) + 1) + sequentialByteArrayReader.getNullTerminatedBytes(bytes.length).length) + 1) + sequentialByteArrayReader.getNullTerminatedBytes(bytes.length).length) + 1);
                    if (int8 == (byte) 0) {
                        readAllBytes = sequentialByteArrayReader.getNullTerminatedBytes(length2);
                    } else {
                        if (int8 != (byte) 1) {
                            pngDirectory2 = new PngDirectory(PngChunkType.iTXt);
                            pngDirectory2.addError("Invalid compression flag value");
                            metadata2.addDirectory(pngDirectory2);
                        } else if (int82 == (byte) 0) {
                            try {
                                readAllBytes = StreamUtil.readAllBytes(new InflaterInputStream(new ByteArrayInputStream(bytes, bytes.length - length2, length2)));
                            } catch (ZipException e22) {
                                pngDirectory = new PngDirectory(PngChunkType.iTXt);
                                pngDirectory.addError(String.format("Exception decompressing PNG iTXt chunk with keyword \"%s\": %s", new Object[]{stringValue, e22.getMessage()}));
                                metadata2.addDirectory(pngDirectory);
                            }
                        } else {
                            pngDirectory2 = new PngDirectory(PngChunkType.iTXt);
                            pngDirectory2.addError(str);
                            metadata2.addDirectory(pngDirectory2);
                        }
                        readAllBytes = null;
                    }
                    if (readAllBytes == null) {
                        return;
                    }
                    if (stringValue.equals(str2)) {
                        new XmpReader().extract(readAllBytes, metadata2);
                        return;
                    }
                    arrayList2 = new ArrayList();
                    arrayList2.add(new KeyValuePair(stringValue, new StringValue(readAllBytes, _latin1Encoding)));
                    pngDirectory = new PngDirectory(PngChunkType.iTXt);
                    pngDirectory.setObject(13, arrayList2);
                    metadata2.addDirectory(pngDirectory);
                } else if (type.equals(PngChunkType.tIME)) {
                    sequentialByteArrayReader2 = new SequentialByteArrayReader(bytes);
                    uInt16 = sequentialByteArrayReader2.getUInt16();
                    short uInt8 = sequentialByteArrayReader2.getUInt8();
                    short uInt82 = sequentialByteArrayReader2.getUInt8();
                    short uInt83 = sequentialByteArrayReader2.getUInt8();
                    short uInt84 = sequentialByteArrayReader2.getUInt8();
                    short uInt85 = sequentialByteArrayReader2.getUInt8();
                    Directory pngDirectory4 = new PngDirectory(PngChunkType.tIME);
                    if (DateUtil.isValidDate(uInt16, uInt8 - 1, uInt82) && DateUtil.isValidTime(uInt83, uInt84, uInt85)) {
                        pngDirectory4.setString(14, String.format("%04d:%02d:%02d %02d:%02d:%02d", new Object[]{Integer.valueOf(uInt16), Integer.valueOf(uInt8), Integer.valueOf(uInt82), Integer.valueOf(uInt83), Integer.valueOf(uInt84), Integer.valueOf(uInt85)}));
                    } else {
                        pngDirectory4.addError(String.format("PNG tIME data describes an invalid date/time: year=%d month=%d day=%d hour=%d minute=%d second=%d", new Object[]{Integer.valueOf(uInt16), Integer.valueOf(uInt8), Integer.valueOf(uInt82), Integer.valueOf(uInt83), Integer.valueOf(uInt84), Integer.valueOf(uInt85)}));
                    }
                    metadata2.addDirectory(pngDirectory4);
                } else if (type.equals(PngChunkType.pHYs)) {
                    sequentialByteArrayReader2 = new SequentialByteArrayReader(bytes);
                    uInt16 = sequentialByteArrayReader2.getInt32();
                    int int32 = sequentialByteArrayReader2.getInt32();
                    b = sequentialByteArrayReader2.getInt8();
                    Directory pngDirectory5 = new PngDirectory(PngChunkType.pHYs);
                    pngDirectory5.setInt(16, uInt16);
                    pngDirectory5.setInt(17, int32);
                    pngDirectory5.setInt(18, b);
                    metadata2.addDirectory(pngDirectory5);
                } else if (type.equals(PngChunkType.sBIT)) {
                    pngDirectory2 = new PngDirectory(PngChunkType.sBIT);
                    pngDirectory2.setByteArray(19, bytes);
                    metadata2.addDirectory(pngDirectory2);
                }
            }
        }
    }
}
