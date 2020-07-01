package com.drew.metadata.xmp;

import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPMetaFactory;
import com.drew.imaging.jpeg.JpegSegmentMetadataReader;
import com.drew.imaging.jpeg.JpegSegmentType;
import com.drew.lang.SequentialByteArrayReader;
import com.drew.lang.SequentialReader;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.StringValue;
import java.io.IOException;
import java.util.Collections;

public class XmpReader implements JpegSegmentMetadataReader {
    @NotNull
    private static final String ATTRIBUTE_EXTENDED_XMP = "xmpNote:HasExtendedXMP";
    private static final int EXTENDED_XMP_GUID_LENGTH = 32;
    private static final int EXTENDED_XMP_INT_LENGTH = 4;
    @NotNull
    private static final String SCHEMA_XMP_NOTES = "http://ns.adobe.com/xmp/note/";
    @NotNull
    private static final String XMP_EXTENSION_JPEG_PREAMBLE = "http://ns.adobe.com/xmp/extension/\u0000";
    @NotNull
    private static final String XMP_JPEG_PREAMBLE = "http://ns.adobe.com/xap/1.0/\u0000";

    @NotNull
    public Iterable<JpegSegmentType> getSegmentTypes() {
        return Collections.singletonList(JpegSegmentType.APP1);
    }

    /* JADX WARNING: Missing block: B:8:0x0031, code:
            if ("XMP".equalsIgnoreCase(new java.lang.String(r1, 0, 3)) != false) goto L_0x0033;
     */
    public void readJpegSegments(@com.drew.lang.annotations.NotNull java.lang.Iterable<byte[]> r7, @com.drew.lang.annotations.NotNull com.drew.metadata.Metadata r8, @com.drew.lang.annotations.NotNull com.drew.imaging.jpeg.JpegSegmentType r9) {
        /*
        r6 = this;
        r7 = r7.iterator();
        r9 = 0;
        r0 = r9;
    L_0x0006:
        r1 = r7.hasNext();
        if (r1 == 0) goto L_0x005c;
    L_0x000c:
        r1 = r7.next();
        r1 = (byte[]) r1;
        r2 = r1.length;
        r3 = 0;
        r4 = 29;
        if (r2 < r4) goto L_0x0043;
    L_0x0018:
        r2 = new java.lang.String;
        r2.<init>(r1, r3, r4);
        r5 = "http://ns.adobe.com/xap/1.0/\u0000";
        r2 = r5.equalsIgnoreCase(r2);
        if (r2 != 0) goto L_0x0033;
    L_0x0025:
        r2 = new java.lang.String;
        r5 = 3;
        r2.<init>(r1, r3, r5);
        r5 = "XMP";
        r2 = r5.equalsIgnoreCase(r2);
        if (r2 == 0) goto L_0x0043;
    L_0x0033:
        r0 = r1.length;
        r0 = r0 - r4;
        r0 = new byte[r0];
        r2 = r0.length;
        java.lang.System.arraycopy(r1, r4, r0, r3, r2);
        r6.extract(r0, r8);
        r0 = getExtendedXMPGUID(r8);
        goto L_0x0006;
    L_0x0043:
        if (r0 == 0) goto L_0x0006;
    L_0x0045:
        r2 = r1.length;
        r4 = 35;
        if (r2 < r4) goto L_0x0006;
    L_0x004a:
        r2 = new java.lang.String;
        r2.<init>(r1, r3, r4);
        r3 = "http://ns.adobe.com/xmp/extension/\u0000";
        r2 = r3.equalsIgnoreCase(r2);
        if (r2 == 0) goto L_0x0006;
    L_0x0057:
        r9 = processExtendedXMPChunk(r8, r1, r0, r9);
        goto L_0x0006;
    L_0x005c:
        if (r9 == 0) goto L_0x0061;
    L_0x005e:
        r6.extract(r9, r8);
    L_0x0061:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.drew.metadata.xmp.XmpReader.readJpegSegments(java.lang.Iterable, com.drew.metadata.Metadata, com.drew.imaging.jpeg.JpegSegmentType):void");
    }

    public void extract(@NotNull byte[] bArr, @NotNull Metadata metadata) {
        extract(bArr, metadata, null);
    }

    public void extract(@NotNull byte[] bArr, @NotNull Metadata metadata, @Nullable Directory directory) {
        extract(bArr, 0, bArr.length, metadata, directory);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0044  */
    public void extract(@com.drew.lang.annotations.NotNull byte[] r2, int r3, int r4, @com.drew.lang.annotations.NotNull com.drew.metadata.Metadata r5, @com.drew.lang.annotations.Nullable com.drew.metadata.Directory r6) {
        /*
        r1 = this;
        r0 = new com.drew.metadata.xmp.XmpDirectory;
        r0.<init>();
        if (r6 == 0) goto L_0x000a;
    L_0x0007:
        r0.setParent(r6);
    L_0x000a:
        if (r3 != 0) goto L_0x0014;
    L_0x000c:
        r6 = r2.length;	 Catch:{ XMPException -> 0x0025 }
        if (r4 != r6) goto L_0x0014;
    L_0x000f:
        r2 = com.adobe.xmp.XMPMetaFactory.parseFromBuffer(r2);	 Catch:{ XMPException -> 0x0025 }
        goto L_0x0021;
    L_0x0014:
        r6 = new com.adobe.xmp.impl.ByteBuffer;	 Catch:{ XMPException -> 0x0025 }
        r6.<init>(r2, r3, r4);	 Catch:{ XMPException -> 0x0025 }
        r2 = r6.getByteStream();	 Catch:{ XMPException -> 0x0025 }
        r2 = com.adobe.xmp.XMPMetaFactory.parse(r2);	 Catch:{ XMPException -> 0x0025 }
    L_0x0021:
        r0.setXMPMeta(r2);	 Catch:{ XMPException -> 0x0025 }
        goto L_0x003e;
    L_0x0025:
        r2 = move-exception;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Error processing XMP data: ";
        r3.append(r4);
        r2 = r2.getMessage();
        r3.append(r2);
        r2 = r3.toString();
        r0.addError(r2);
    L_0x003e:
        r2 = r0.isEmpty();
        if (r2 != 0) goto L_0x0047;
    L_0x0044:
        r5.addDirectory(r0);
    L_0x0047:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.drew.metadata.xmp.XmpReader.extract(byte[], int, int, com.drew.metadata.Metadata, com.drew.metadata.Directory):void");
    }

    public void extract(@NotNull String str, @NotNull Metadata metadata) {
        extract(str, metadata, null);
    }

    public void extract(@NotNull StringValue stringValue, @NotNull Metadata metadata) {
        extract(stringValue.getBytes(), metadata, null);
    }

    public void extract(@NotNull String str, @NotNull Metadata metadata, @Nullable Directory directory) {
        Directory xmpDirectory = new XmpDirectory();
        if (directory != null) {
            xmpDirectory.setParent(directory);
        }
        try {
            xmpDirectory.setXMPMeta(XMPMetaFactory.parseFromString(str));
        } catch (XMPException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error processing XMP data: ");
            stringBuilder.append(e.getMessage());
            xmpDirectory.addError(stringBuilder.toString());
        }
        if (!xmpDirectory.isEmpty()) {
            metadata.addDirectory(xmpDirectory);
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.drew.metadata.xmp.XmpReader.getExtendedXMPGUID(com.drew.metadata.Metadata):java.lang.String, dom blocks: []
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:89)
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:51)
        	at java.lang.Iterable.forEach(Unknown Source)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:51)
        	at jadx.core.ProcessClass.process(ProcessClass.java:37)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:293)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:201)
        */
    @com.drew.lang.annotations.Nullable
    private static java.lang.String getExtendedXMPGUID(@com.drew.lang.annotations.NotNull com.drew.metadata.Metadata r4) {
        /*
        r0 = com.drew.metadata.xmp.XmpDirectory.class;
        r4 = r4.getDirectoriesOfType(r0);
        r4 = r4.iterator();
    L_0x000a:
        r0 = r4.hasNext();
        r1 = 0;
        if (r0 == 0) goto L_0x0043;
    L_0x0011:
        r0 = r4.next();
        r0 = (com.drew.metadata.xmp.XmpDirectory) r0;
        r0 = r0.getXMPMeta();
        r2 = "http://ns.adobe.com/xmp/note/";	 Catch:{ XMPException -> 0x0041 }
        r0 = r0.iterator(r2, r1, r1);	 Catch:{ XMPException -> 0x0041 }
        if (r0 != 0) goto L_0x0024;	 Catch:{ XMPException -> 0x0041 }
    L_0x0023:
        goto L_0x000a;	 Catch:{ XMPException -> 0x0041 }
    L_0x0024:
        r1 = r0.hasNext();	 Catch:{ XMPException -> 0x0041 }
        if (r1 == 0) goto L_0x000a;	 Catch:{ XMPException -> 0x0041 }
    L_0x002a:
        r1 = r0.next();	 Catch:{ XMPException -> 0x0041 }
        r1 = (com.adobe.xmp.properties.XMPPropertyInfo) r1;	 Catch:{ XMPException -> 0x0041 }
        r2 = "xmpNote:HasExtendedXMP";	 Catch:{ XMPException -> 0x0041 }
        r3 = r1.getPath();	 Catch:{ XMPException -> 0x0041 }
        r2 = r2.equals(r3);	 Catch:{ XMPException -> 0x0041 }
        if (r2 == 0) goto L_0x0024;	 Catch:{ XMPException -> 0x0041 }
    L_0x003c:
        r4 = r1.getValue();	 Catch:{ XMPException -> 0x0041 }
        return r4;
        goto L_0x000a;
    L_0x0043:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.drew.metadata.xmp.XmpReader.getExtendedXMPGUID(com.drew.metadata.Metadata):java.lang.String");
    }

    @Nullable
    private static byte[] processExtendedXMPChunk(@NotNull Metadata metadata, @NotNull byte[] bArr, @NotNull String str, @Nullable byte[] bArr2) {
        int length = bArr.length;
        if (length >= 75) {
            try {
                SequentialReader sequentialByteArrayReader = new SequentialByteArrayReader(bArr);
                sequentialByteArrayReader.skip((long) 35);
                if (str.equals(sequentialByteArrayReader.getString(32))) {
                    int uInt32 = (int) sequentialByteArrayReader.getUInt32();
                    int uInt322 = (int) sequentialByteArrayReader.getUInt32();
                    if (bArr2 == null) {
                        bArr2 = new byte[uInt32];
                    }
                    if (bArr2.length == uInt32) {
                        System.arraycopy(bArr, 75, bArr2, uInt322, length - 75);
                    } else {
                        Directory xmpDirectory = new XmpDirectory();
                        xmpDirectory.addError(String.format("Inconsistent length for the Extended XMP buffer: %d instead of %d", new Object[]{Integer.valueOf(uInt32), Integer.valueOf(bArr2.length)}));
                        metadata.addDirectory(xmpDirectory);
                    }
                }
            } catch (IOException e) {
                Directory xmpDirectory2 = new XmpDirectory();
                xmpDirectory2.addError(e.getMessage());
                metadata.addDirectory(xmpDirectory2);
            }
        }
        return bArr2;
    }
}
