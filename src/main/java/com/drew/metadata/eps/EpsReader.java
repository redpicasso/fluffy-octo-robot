package com.drew.metadata.eps;

import com.drew.imaging.tiff.TiffProcessingException;
import com.drew.imaging.tiff.TiffReader;
import com.drew.lang.ByteArrayReader;
import com.drew.lang.Charsets;
import com.drew.lang.RandomAccessStreamReader;
import com.drew.lang.SequentialByteArrayReader;
import com.drew.lang.SequentialReader;
import com.drew.lang.StreamReader;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.icc.IccReader;
import com.drew.metadata.photoshop.PhotoshopReader;
import com.drew.metadata.photoshop.PhotoshopTiffHandler;
import com.drew.metadata.xmp.XmpReader;
import com.google.common.base.Ascii;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EpsReader {
    private int _previousTag;

    private static int tryHexToInt(byte b) {
        if (b >= (byte) 48 && b <= (byte) 57) {
            return b - 48;
        }
        int i = 65;
        if (b < (byte) 65 || b > (byte) 70) {
            i = 97;
            if (b < (byte) 97 || b > (byte) 102) {
                return -1;
            }
        }
        return (b - i) + 10;
    }

    public void extract(@NotNull InputStream inputStream, @NotNull Metadata metadata) throws IOException {
        RandomAccessStreamReader randomAccessStreamReader = new RandomAccessStreamReader(inputStream);
        Directory epsDirectory = new EpsDirectory();
        metadata.addDirectory(epsDirectory);
        int int32 = randomAccessStreamReader.getInt32(0);
        if (int32 == -976170042) {
            randomAccessStreamReader.setMotorolaByteOrder(false);
            int int322 = randomAccessStreamReader.getInt32(4);
            int32 = randomAccessStreamReader.getInt32(8);
            int int323 = randomAccessStreamReader.getInt32(12);
            int int324 = randomAccessStreamReader.getInt32(16);
            int int325 = randomAccessStreamReader.getInt32(20);
            int int326 = randomAccessStreamReader.getInt32(24);
            if (int326 != 0) {
                epsDirectory.setInt(32, int326);
                epsDirectory.setInt(33, int325);
                try {
                    new TiffReader().processTiff(new ByteArrayReader(randomAccessStreamReader.getBytes(int325, int326)), new PhotoshopTiffHandler(metadata, null), 0);
                } catch (TiffProcessingException e) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unable to process TIFF data: ");
                    stringBuilder.append(e.getMessage());
                    epsDirectory.addError(stringBuilder.toString());
                }
            } else if (int324 != 0) {
                epsDirectory.setInt(34, int324);
                epsDirectory.setInt(35, int323);
            }
            extract(epsDirectory, metadata, new SequentialByteArrayReader(randomAccessStreamReader.getBytes(int322, int32)));
        } else if (int32 != 622940243) {
            epsDirectory.addError("File type not supported.");
        } else {
            inputStream.reset();
            extract(epsDirectory, metadata, new StreamReader(inputStream));
        }
    }

    private void extract(@NotNull EpsDirectory epsDirectory, @NotNull Metadata metadata, @NotNull SequentialReader sequentialReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            stringBuilder.setLength(0);
            while (true) {
                char c = (char) sequentialReader.getByte();
                if (c != 13 && c != 10) {
                    stringBuilder.append(c);
                }
            }
            if (stringBuilder.length() == 0 || stringBuilder.charAt(0) == '%') {
                String trim;
                int indexOf = stringBuilder.indexOf(":");
                if (indexOf != -1) {
                    trim = stringBuilder.substring(0, indexOf).trim();
                    addToDirectory(epsDirectory, trim, stringBuilder.substring(indexOf + 1).trim());
                } else {
                    trim = stringBuilder.toString().trim();
                }
                if (trim.equals("%BeginPhotoshop")) {
                    extractPhotoshopData(metadata, sequentialReader);
                } else if (trim.equals("%%BeginICCProfile")) {
                    extractIccData(metadata, sequentialReader);
                } else if (trim.equals("%begin_xml_packet")) {
                    extractXmpData(metadata, sequentialReader);
                }
            } else {
                return;
            }
        }
    }

    private void addToDirectory(@NotNull EpsDirectory epsDirectory, String str, String str2) throws IOException {
        Integer num = (Integer) EpsDirectory._tagIntegerMap.get(str);
        if (num != null) {
            int intValue = num.intValue();
            if (intValue == 8) {
                extractImageData(epsDirectory, str2);
            } else if (intValue == 36) {
                intValue = this._previousTag;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(epsDirectory.getString(this._previousTag));
                stringBuilder.append(" ");
                stringBuilder.append(str2);
                epsDirectory.setString(intValue, stringBuilder.toString());
            } else if (!EpsDirectory._tagNameMap.containsKey(num) || epsDirectory.containsTag(num.intValue())) {
                this._previousTag = 0;
            } else {
                epsDirectory.setString(num.intValue(), str2);
                this._previousTag = num.intValue();
            }
            this._previousTag = num.intValue();
        }
    }

    private static void extractImageData(@NotNull EpsDirectory epsDirectory, String str) throws IOException {
        epsDirectory.setString(8, str.trim());
        String[] split = str.split(" ");
        int parseInt = Integer.parseInt(split[0]);
        int parseInt2 = Integer.parseInt(split[1]);
        int i = 3;
        int parseInt3 = Integer.parseInt(split[3]);
        if (!epsDirectory.containsTag(28)) {
            epsDirectory.setInt(28, parseInt);
        }
        if (!epsDirectory.containsTag(29)) {
            epsDirectory.setInt(29, parseInt2);
        }
        if (!epsDirectory.containsTag(30)) {
            epsDirectory.setInt(30, parseInt3);
        }
        if (!epsDirectory.containsTag(31)) {
            if (parseInt3 == 1) {
                i = 1;
            } else if (!(parseInt3 == 2 || parseInt3 == 3 || parseInt3 == 4)) {
                i = 0;
            }
            if (i != 0) {
                epsDirectory.setInt(31, (i * parseInt) * parseInt2);
            }
        }
    }

    private static void extractPhotoshopData(@NotNull Metadata metadata, @NotNull SequentialReader sequentialReader) throws IOException {
        byte[] decodeHexCommentBlock = decodeHexCommentBlock(sequentialReader);
        if (decodeHexCommentBlock != null) {
            new PhotoshopReader().extract(new SequentialByteArrayReader(decodeHexCommentBlock), decodeHexCommentBlock.length, metadata);
        }
    }

    private static void extractIccData(@NotNull Metadata metadata, @NotNull SequentialReader sequentialReader) throws IOException {
        byte[] decodeHexCommentBlock = decodeHexCommentBlock(sequentialReader);
        if (decodeHexCommentBlock != null) {
            new IccReader().extract(new ByteArrayReader(decodeHexCommentBlock), metadata);
        }
    }

    private static void extractXmpData(@NotNull Metadata metadata, @NotNull SequentialReader sequentialReader) throws IOException {
        new XmpReader().extract(new String(readUntil(sequentialReader, "<?xpacket end=\"w\"?>".getBytes()), Charsets.UTF_8), metadata);
    }

    private static byte[] readUntil(@NotNull SequentialReader sequentialReader, @NotNull byte[] bArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int length = bArr.length;
        int i = 0;
        while (i != length) {
            byte b = sequentialReader.getByte();
            i = b == bArr[i] ? i + 1 : 0;
            byteArrayOutputStream.write(b);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Nullable
    private static byte[] decodeHexCommentBlock(@NotNull SequentialReader sequentialReader) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Object obj = null;
        int i = 0;
        byte b = (byte) 0;
        int i2 = 0;
        while (obj == null) {
            b = sequentialReader.getByte();
            if (i != 0) {
                if (i != 1) {
                    if (i == 2) {
                        i = tryHexToInt(b);
                        if (i != -1) {
                            i2 = i * 16;
                            i = 3;
                        } else if (b != Ascii.CR && b != (byte) 10) {
                            return null;
                        } else {
                            i = 0;
                        }
                    } else if (i == 3) {
                        i = tryHexToInt(b);
                        if (i == -1) {
                            return null;
                        }
                        byteArrayOutputStream.write(i + i2);
                    }
                } else if (b != (byte) 32) {
                    obj = 1;
                }
                i = 2;
            } else if (!(b == (byte) 10 || b == Ascii.CR || b == (byte) 32)) {
                if (b != (byte) 37) {
                    return null;
                }
                i = 1;
            }
        }
        while (b != (byte) 10) {
            b = sequentialReader.getByte();
        }
        return byteArrayOutputStream.toByteArray();
    }
}
