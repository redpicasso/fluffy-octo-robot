package com.drew.metadata.gif;

import com.drew.lang.ByteArrayReader;
import com.drew.lang.Charsets;
import com.drew.lang.SequentialReader;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.Directory;
import com.drew.metadata.ErrorDirectory;
import com.drew.metadata.Metadata;
import com.drew.metadata.StringValue;
import com.drew.metadata.gif.GifControlDirectory.DisposalMethod;
import com.drew.metadata.icc.IccReader;
import com.drew.metadata.xmp.XmpReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GifReader {
    private static final String GIF_87A_VERSION_IDENTIFIER = "87a";
    private static final String GIF_89A_VERSION_IDENTIFIER = "89a";

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:12:?, code:
            r6.addDirectory(new com.drew.metadata.ErrorDirectory("GIF did not had hasGlobalColorTable bit."));
     */
    /* JADX WARNING: Missing block: B:22:0x0046, code:
            if (r1 == (byte) 59) goto L_0x0052;
     */
    /* JADX WARNING: Missing block: B:24:?, code:
            r6.addDirectory(new com.drew.metadata.ErrorDirectory("Unknown gif block marker found."));
     */
    /* JADX WARNING: Missing block: B:25:0x0052, code:
            return;
     */
    /* JADX WARNING: Missing block: B:30:0x0063, code:
            r6.addDirectory(new com.drew.metadata.ErrorDirectory(r0));
     */
    /* JADX WARNING: Missing block: B:31:0x006b, code:
            return;
     */
    public void extract(@com.drew.lang.annotations.NotNull com.drew.lang.SequentialReader r5, @com.drew.lang.annotations.NotNull com.drew.metadata.Metadata r6) {
        /*
        r4 = this;
        r0 = "IOException processing GIF data";
        r1 = 0;
        r5.setMotorolaByteOrder(r1);
        r1 = readGifHeader(r5);	 Catch:{ IOException -> 0x006c }
        r6.addDirectory(r1);	 Catch:{ IOException -> 0x006c }
        r2 = r1.hasErrors();
        if (r2 == 0) goto L_0x0014;
    L_0x0013:
        return;
    L_0x0014:
        r2 = 0;
        r3 = 7;
        r3 = r1.getBoolean(r3);	 Catch:{ MetadataException -> 0x0022 }
        if (r3 == 0) goto L_0x002c;
    L_0x001c:
        r3 = 4;
        r2 = r1.getInteger(r3);	 Catch:{ MetadataException -> 0x0022 }
        goto L_0x002c;
    L_0x0022:
        r1 = new com.drew.metadata.ErrorDirectory;	 Catch:{ IOException -> 0x0063 }
        r3 = "GIF did not had hasGlobalColorTable bit.";
        r1.<init>(r3);	 Catch:{ IOException -> 0x0063 }
        r6.addDirectory(r1);	 Catch:{ IOException -> 0x0063 }
    L_0x002c:
        if (r2 == 0) goto L_0x0038;
    L_0x002e:
        r1 = r2.intValue();	 Catch:{ IOException -> 0x0063 }
        r1 = r1 * 3;
        r1 = (long) r1;	 Catch:{ IOException -> 0x0063 }
        r5.skip(r1);	 Catch:{ IOException -> 0x0063 }
    L_0x0038:
        r1 = r5.getInt8();	 Catch:{ IOException -> 0x0062 }
        r2 = 33;
        if (r1 == r2) goto L_0x005e;
    L_0x0040:
        r2 = 44;
        if (r1 == r2) goto L_0x0053;
    L_0x0044:
        r5 = 59;
        if (r1 == r5) goto L_0x0052;
    L_0x0048:
        r5 = new com.drew.metadata.ErrorDirectory;	 Catch:{ IOException -> 0x0063 }
        r1 = "Unknown gif block marker found.";
        r5.<init>(r1);	 Catch:{ IOException -> 0x0063 }
        r6.addDirectory(r5);	 Catch:{ IOException -> 0x0063 }
    L_0x0052:
        return;
    L_0x0053:
        r1 = readImageBlock(r5);	 Catch:{ IOException -> 0x0063 }
        r6.addDirectory(r1);	 Catch:{ IOException -> 0x0063 }
        skipBlocks(r5);	 Catch:{ IOException -> 0x0063 }
        goto L_0x0038;
    L_0x005e:
        readGifExtensionBlock(r5, r6);	 Catch:{ IOException -> 0x0063 }
        goto L_0x0038;
    L_0x0062:
        return;
    L_0x0063:
        r5 = new com.drew.metadata.ErrorDirectory;
        r5.<init>(r0);
        r6.addDirectory(r5);
        return;
    L_0x006c:
        r5 = new com.drew.metadata.ErrorDirectory;
        r5.<init>(r0);
        r6.addDirectory(r5);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.drew.metadata.gif.GifReader.extract(com.drew.lang.SequentialReader, com.drew.metadata.Metadata):void");
    }

    private static GifHeaderDirectory readGifHeader(@NotNull SequentialReader sequentialReader) throws IOException {
        GifHeaderDirectory gifHeaderDirectory = new GifHeaderDirectory();
        if (sequentialReader.getString(3).equals("GIF")) {
            String string = sequentialReader.getString(3);
            boolean equals = string.equals(GIF_87A_VERSION_IDENTIFIER);
            String str = GIF_89A_VERSION_IDENTIFIER;
            if (equals || string.equals(str)) {
                equals = true;
                gifHeaderDirectory.setString(1, string);
                gifHeaderDirectory.setInt(2, sequentialReader.getUInt16());
                gifHeaderDirectory.setInt(3, sequentialReader.getUInt16());
                short uInt8 = sequentialReader.getUInt8();
                int i = 1 << ((uInt8 & 7) + 1);
                int i2 = ((uInt8 & 112) >> 4) + 1;
                boolean z = (uInt8 >> 7) != 0;
                gifHeaderDirectory.setInt(4, i);
                if (string.equals(str)) {
                    if ((uInt8 & 8) == 0) {
                        equals = false;
                    }
                    gifHeaderDirectory.setBoolean(5, equals);
                }
                gifHeaderDirectory.setInt(6, i2);
                gifHeaderDirectory.setBoolean(7, z);
                gifHeaderDirectory.setInt(8, sequentialReader.getUInt8());
                short uInt82 = sequentialReader.getUInt8();
                if (uInt82 != (short) 0) {
                    gifHeaderDirectory.setFloat(9, (float) ((((double) uInt82) + 15.0d) / 64.0d));
                }
                return gifHeaderDirectory;
            }
            gifHeaderDirectory.addError("Unexpected GIF version");
            return gifHeaderDirectory;
        }
        gifHeaderDirectory.addError("Invalid GIF file signature");
        return gifHeaderDirectory;
    }

    private static void readGifExtensionBlock(SequentialReader sequentialReader, Metadata metadata) throws IOException {
        byte int8 = sequentialReader.getInt8();
        short uInt8 = sequentialReader.getUInt8();
        long position = sequentialReader.getPosition();
        if (int8 == (byte) -7) {
            metadata.addDirectory(readControlBlock(sequentialReader, uInt8));
        } else if (int8 == (byte) 1) {
            Directory readPlainTextBlock = readPlainTextBlock(sequentialReader, uInt8);
            if (readPlainTextBlock != null) {
                metadata.addDirectory(readPlainTextBlock);
            }
        } else if (int8 == (byte) -2) {
            metadata.addDirectory(readCommentBlock(sequentialReader, uInt8));
        } else if (int8 != (byte) -1) {
            metadata.addDirectory(new ErrorDirectory(String.format("Unsupported GIF extension block with type 0x%02X.", new Object[]{Byte.valueOf(int8)})));
        } else {
            readApplicationExtensionBlock(sequentialReader, uInt8, metadata);
        }
        position = (position + ((long) uInt8)) - sequentialReader.getPosition();
        if (position > 0) {
            sequentialReader.skip(position);
        }
    }

    @Nullable
    private static Directory readPlainTextBlock(SequentialReader sequentialReader, int i) throws IOException {
        if (i != 12) {
            return new ErrorDirectory(String.format("Invalid GIF plain text block size. Expected 12, got %d.", new Object[]{Integer.valueOf(i)}));
        }
        sequentialReader.skip(12);
        skipBlocks(sequentialReader);
        return null;
    }

    private static GifCommentDirectory readCommentBlock(SequentialReader sequentialReader, int i) throws IOException {
        return new GifCommentDirectory(new StringValue(gatherBytes(sequentialReader, i), Charsets.ASCII));
    }

    private static void readApplicationExtensionBlock(SequentialReader sequentialReader, int i, Metadata metadata) throws IOException {
        if (i != 11) {
            metadata.addDirectory(new ErrorDirectory(String.format("Invalid GIF application extension block size. Expected 11, got %d.", new Object[]{Integer.valueOf(i)})));
            return;
        }
        String string = sequentialReader.getString(i, Charsets.UTF_8);
        if (string.equals("XMP DataXMP")) {
            byte[] gatherBytes = gatherBytes(sequentialReader);
            new XmpReader().extract(gatherBytes, 0, gatherBytes.length - 257, metadata, null);
        } else if (string.equals("ICCRGBG1012")) {
            byte[] gatherBytes2 = gatherBytes(sequentialReader, sequentialReader.getByte() & 255);
            if (gatherBytes2.length != 0) {
                new IccReader().extract(new ByteArrayReader(gatherBytes2), metadata);
            }
        } else if (string.equals("NETSCAPE2.0")) {
            sequentialReader.skip(2);
            i = sequentialReader.getUInt16();
            sequentialReader.skip(1);
            Directory gifAnimationDirectory = new GifAnimationDirectory();
            gifAnimationDirectory.setInt(1, i);
            metadata.addDirectory(gifAnimationDirectory);
        } else {
            skipBlocks(sequentialReader);
        }
    }

    private static GifControlDirectory readControlBlock(SequentialReader sequentialReader, int i) throws IOException {
        GifControlDirectory gifControlDirectory = new GifControlDirectory();
        short uInt8 = sequentialReader.getUInt8();
        gifControlDirectory.setObject(2, DisposalMethod.typeOf((uInt8 >> 2) & 7));
        boolean z = false;
        gifControlDirectory.setBoolean(3, ((uInt8 & 2) >> 1) == 1);
        if ((uInt8 & 1) == 1) {
            z = true;
        }
        gifControlDirectory.setBoolean(4, z);
        gifControlDirectory.setInt(1, sequentialReader.getUInt16());
        gifControlDirectory.setInt(5, sequentialReader.getUInt8());
        sequentialReader.skip(1);
        return gifControlDirectory;
    }

    private static GifImageDirectory readImageBlock(SequentialReader sequentialReader) throws IOException {
        GifImageDirectory gifImageDirectory = new GifImageDirectory();
        boolean z = true;
        gifImageDirectory.setInt(1, sequentialReader.getUInt16());
        gifImageDirectory.setInt(2, sequentialReader.getUInt16());
        gifImageDirectory.setInt(3, sequentialReader.getUInt16());
        gifImageDirectory.setInt(4, sequentialReader.getUInt16());
        byte b = sequentialReader.getByte();
        boolean z2 = (b >> 7) != 0;
        boolean z3 = (b & 64) != 0;
        gifImageDirectory.setBoolean(5, z2);
        gifImageDirectory.setBoolean(6, z3);
        if (z2) {
            if ((b & 32) == 0) {
                z = false;
            }
            gifImageDirectory.setBoolean(7, z);
            int i = b & 7;
            gifImageDirectory.setInt(8, i + 1);
            sequentialReader.skip((long) ((2 << i) * 3));
        }
        sequentialReader.getByte();
        return gifImageDirectory;
    }

    private static byte[] gatherBytes(SequentialReader sequentialReader) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[257];
        while (true) {
            byte b = sequentialReader.getByte();
            if (b == (byte) 0) {
                return byteArrayOutputStream.toByteArray();
            }
            int i = b & 255;
            bArr[0] = b;
            sequentialReader.getBytes(bArr, 1, i);
            byteArrayOutputStream.write(bArr, 0, i + 1);
        }
    }

    private static byte[] gatherBytes(SequentialReader sequentialReader, int i) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (i > 0) {
            byteArrayOutputStream.write(sequentialReader.getBytes(i), 0, i);
            i = sequentialReader.getByte() & 255;
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static void skipBlocks(SequentialReader sequentialReader) throws IOException {
        while (true) {
            short uInt8 = sequentialReader.getUInt8();
            if (uInt8 != (short) 0) {
                sequentialReader.skip((long) uInt8);
            } else {
                return;
            }
        }
    }
}
