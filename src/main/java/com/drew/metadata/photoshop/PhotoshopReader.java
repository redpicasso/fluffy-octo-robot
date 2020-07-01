package com.drew.metadata.photoshop;

import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.jpeg.JpegSegmentMetadataReader;
import com.drew.imaging.jpeg.JpegSegmentType;
import com.drew.lang.ByteArrayReader;
import com.drew.lang.SequentialByteArrayReader;
import com.drew.lang.SequentialReader;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifReader;
import com.drew.metadata.icc.IccReader;
import com.drew.metadata.iptc.IptcReader;
import com.drew.metadata.xmp.XmpReader;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class PhotoshopReader implements JpegSegmentMetadataReader {
    @NotNull
    private static final String JPEG_SEGMENT_PREAMBLE = "Photoshop 3.0";

    @NotNull
    public Iterable<JpegSegmentType> getSegmentTypes() {
        return Collections.singletonList(JpegSegmentType.APPD);
    }

    public void readJpegSegments(@NotNull Iterable<byte[]> iterable, @NotNull Metadata metadata, @NotNull JpegSegmentType jpegSegmentType) {
        for (byte[] bArr : iterable) {
            if (bArr.length >= 14) {
                if (JPEG_SEGMENT_PREAMBLE.equals(new String(bArr, 0, 13))) {
                    extract(new SequentialByteArrayReader(bArr, 14), (bArr.length - 13) - 1, metadata);
                }
            }
        }
    }

    public void extract(@NotNull SequentialReader sequentialReader, int i, @NotNull Metadata metadata) {
        SequentialReader sequentialReader2 = sequentialReader;
        int i2 = i;
        Metadata metadata2 = metadata;
        Directory photoshopDirectory = new PhotoshopDirectory();
        metadata2.addDirectory(photoshopDirectory);
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            try {
                String string = sequentialReader2.getString(4);
                i3 += 4;
                int uInt16 = sequentialReader.getUInt16();
                i3 += 2;
                short uInt8 = sequentialReader.getUInt8();
                i3++;
                if (uInt8 >= (short) 0) {
                    int i5 = uInt8 + i3;
                    if (i5 <= i2) {
                        StringBuilder stringBuilder = new StringBuilder();
                        uInt8 = (short) i5;
                        while (i3 < uInt8) {
                            stringBuilder.append((char) sequentialReader.getUInt8());
                            i3++;
                        }
                        if (i3 % 2 != 0) {
                            sequentialReader2.skip(1);
                            i3++;
                        }
                        i5 = sequentialReader.getInt32();
                        i3 += 4;
                        byte[] bytes = sequentialReader2.getBytes(i5);
                        i3 += i5;
                        if (i3 % 2 != 0) {
                            sequentialReader2.skip(1);
                            i3++;
                        }
                        int i6 = i3;
                        if (string.equals("8BIM")) {
                            if (uInt16 == 1028) {
                                new IptcReader().extract(new SequentialByteArrayReader(bytes), metadata, (long) bytes.length, photoshopDirectory);
                            } else if (uInt16 == PhotoshopDirectory.TAG_ICC_PROFILE_BYTES) {
                                new IccReader().extract(new ByteArrayReader(bytes), metadata2, photoshopDirectory);
                            } else if (uInt16 == PhotoshopDirectory.TAG_EXIF_DATA_1 || uInt16 == PhotoshopDirectory.TAG_EXIF_DATA_3) {
                                new ExifReader().extract(new ByteArrayReader(bytes), metadata2, 0, photoshopDirectory);
                            } else if (uInt16 == PhotoshopDirectory.TAG_XMP_DATA) {
                                new XmpReader().extract(bytes, metadata2, photoshopDirectory);
                            } else if (uInt16 < CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE || uInt16 > 2998) {
                                photoshopDirectory.setByteArray(uInt16, bytes);
                            } else {
                                i4++;
                                byte[] copyOf = Arrays.copyOf(bytes, (bytes.length + stringBuilder.length()) + 1);
                                for (int length = (copyOf.length - stringBuilder.length()) - 1; length < copyOf.length; length++) {
                                    if (length % (((copyOf.length - stringBuilder.length()) - 1) + stringBuilder.length()) == 0) {
                                        copyOf[length] = (byte) stringBuilder.length();
                                    } else {
                                        copyOf[length] = (byte) stringBuilder.charAt(length - ((copyOf.length - stringBuilder.length()) - 1));
                                    }
                                }
                                HashMap hashMap = PhotoshopDirectory._tagNameMap;
                                int i7 = i4 + 1999;
                                Integer valueOf = Integer.valueOf(i7);
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("Path Info ");
                                stringBuilder.append(i4);
                                hashMap.put(valueOf, stringBuilder.toString());
                                photoshopDirectory.setByteArray(i7, copyOf);
                            }
                            if (uInt16 >= 4000 && uInt16 <= 4999) {
                                PhotoshopDirectory._tagNameMap.put(Integer.valueOf(uInt16), String.format("Plug-in %d Data", new Object[]{Integer.valueOf((uInt16 - 4000) + 1)}));
                            }
                        }
                        i3 = i6;
                    }
                }
                throw new ImageProcessingException("Invalid string length");
            } catch (Exception e) {
                photoshopDirectory.addError(e.getMessage());
                return;
            }
        }
    }
}
