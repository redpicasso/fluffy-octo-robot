package com.drew.metadata.bmp;

import com.drew.lang.ByteArrayReader;
import com.drew.lang.Charsets;
import com.drew.lang.SequentialReader;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import com.drew.metadata.ErrorDirectory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.bmp.BmpHeaderDirectory.ColorSpaceType;
import com.drew.metadata.icc.IccReader;
import java.io.IOException;

public class BmpReader {
    public static final int BITMAP = 19778;
    public static final int OS2_BITMAP_ARRAY = 16706;
    public static final int OS2_COLOR_ICON = 18755;
    public static final int OS2_COLOR_POINTER = 20547;
    public static final int OS2_ICON = 17225;
    public static final int OS2_POINTER = 21584;

    public void extract(@NotNull SequentialReader sequentialReader, @NotNull Metadata metadata) {
        sequentialReader.setMotorolaByteOrder(false);
        readFileHeader(sequentialReader, metadata, true);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.drew.metadata.bmp.BmpReader.readFileHeader(com.drew.lang.SequentialReader, com.drew.metadata.Metadata, boolean):void, dom blocks: []
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
    protected void readFileHeader(@com.drew.lang.annotations.NotNull com.drew.lang.SequentialReader r7, @com.drew.lang.annotations.NotNull com.drew.metadata.Metadata r8, boolean r9) {
        /*
        r6 = this;
        r0 = r7.getUInt16();	 Catch:{ IOException -> 0x009d }
        r1 = 0;
        r2 = 16706; // 0x4142 float:2.341E-41 double:8.254E-320;
        if (r0 == r2) goto L_0x0057;
    L_0x0009:
        r9 = 17225; // 0x4349 float:2.4137E-41 double:8.5103E-320;
        if (r0 == r9) goto L_0x003d;
    L_0x000d:
        r9 = 18755; // 0x4943 float:2.6281E-41 double:9.266E-320;
        if (r0 == r9) goto L_0x003d;
    L_0x0011:
        r9 = 19778; // 0x4d42 float:2.7715E-41 double:9.7716E-320;
        if (r0 == r9) goto L_0x003d;
    L_0x0015:
        r9 = 20547; // 0x5043 float:2.8792E-41 double:1.01516E-319;
        if (r0 == r9) goto L_0x003d;
    L_0x0019:
        r9 = 21584; // 0x5450 float:3.0246E-41 double:1.0664E-319;
        if (r0 == r9) goto L_0x003d;
    L_0x001d:
        r7 = new com.drew.metadata.ErrorDirectory;	 Catch:{ IOException -> 0x003b }
        r9 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x003b }
        r9.<init>();	 Catch:{ IOException -> 0x003b }
        r2 = "Invalid BMP magic number 0x";	 Catch:{ IOException -> 0x003b }
        r9.append(r2);	 Catch:{ IOException -> 0x003b }
        r0 = java.lang.Integer.toHexString(r0);	 Catch:{ IOException -> 0x003b }
        r9.append(r0);	 Catch:{ IOException -> 0x003b }
        r9 = r9.toString();	 Catch:{ IOException -> 0x003b }
        r7.<init>(r9);	 Catch:{ IOException -> 0x003b }
        r8.addDirectory(r7);	 Catch:{ IOException -> 0x003b }
        return;	 Catch:{ IOException -> 0x003b }
        goto L_0x0091;	 Catch:{ IOException -> 0x003b }
    L_0x003d:
        r9 = new com.drew.metadata.bmp.BmpHeaderDirectory;	 Catch:{ IOException -> 0x003b }
        r9.<init>();	 Catch:{ IOException -> 0x003b }
        r8.addDirectory(r9);	 Catch:{ IOException -> 0x0055 }
        r1 = -2;	 Catch:{ IOException -> 0x0055 }
        r9.setInt(r1, r0);	 Catch:{ IOException -> 0x0055 }
        r0 = 12;	 Catch:{ IOException -> 0x0055 }
        r7.skip(r0);	 Catch:{ IOException -> 0x0055 }
        r0 = r9;	 Catch:{ IOException -> 0x0055 }
        r0 = (com.drew.metadata.bmp.BmpHeaderDirectory) r0;	 Catch:{ IOException -> 0x0055 }
        r6.readBitmapHeader(r7, r0, r8);	 Catch:{ IOException -> 0x0055 }
        goto L_0x009c;
    L_0x0055:
        r1 = r9;
        goto L_0x0091;
    L_0x0057:
        if (r9 != 0) goto L_0x005f;
    L_0x0059:
        r7 = "Invalid bitmap file - nested arrays not allowed";	 Catch:{ IOException -> 0x003b }
        r6.addError(r7, r8);	 Catch:{ IOException -> 0x003b }
        return;	 Catch:{ IOException -> 0x003b }
    L_0x005f:
        r2 = 4;	 Catch:{ IOException -> 0x003b }
        r7.skip(r2);	 Catch:{ IOException -> 0x003b }
        r4 = r7.getUInt32();	 Catch:{ IOException -> 0x003b }
        r7.skip(r2);	 Catch:{ IOException -> 0x003b }
        r9 = 0;	 Catch:{ IOException -> 0x003b }
        r6.readFileHeader(r7, r8, r9);	 Catch:{ IOException -> 0x003b }
        r2 = 0;	 Catch:{ IOException -> 0x003b }
        r9 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));	 Catch:{ IOException -> 0x003b }
        if (r9 != 0) goto L_0x0076;	 Catch:{ IOException -> 0x003b }
    L_0x0075:
        return;	 Catch:{ IOException -> 0x003b }
    L_0x0076:
        r2 = r7.getPosition();	 Catch:{ IOException -> 0x003b }
        r9 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));	 Catch:{ IOException -> 0x003b }
        if (r9 <= 0) goto L_0x0084;	 Catch:{ IOException -> 0x003b }
    L_0x007e:
        r7 = "Invalid next header offset";	 Catch:{ IOException -> 0x003b }
        r6.addError(r7, r8);	 Catch:{ IOException -> 0x003b }
        return;	 Catch:{ IOException -> 0x003b }
    L_0x0084:
        r2 = r7.getPosition();	 Catch:{ IOException -> 0x003b }
        r4 = r4 - r2;	 Catch:{ IOException -> 0x003b }
        r7.skip(r4);	 Catch:{ IOException -> 0x003b }
        r9 = 1;	 Catch:{ IOException -> 0x003b }
        r6.readFileHeader(r7, r8, r9);	 Catch:{ IOException -> 0x003b }
        goto L_0x009c;
    L_0x0091:
        r7 = "Unable to read BMP file header";
        if (r1 != 0) goto L_0x0099;
    L_0x0095:
        r6.addError(r7, r8);
        goto L_0x009c;
    L_0x0099:
        r1.addError(r7);
    L_0x009c:
        return;
    L_0x009d:
        r7 = move-exception;
        r9 = new com.drew.metadata.ErrorDirectory;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "Couldn't determine bitmap type: ";
        r0.append(r1);
        r7 = r7.getMessage();
        r0.append(r7);
        r7 = r0.toString();
        r9.<init>(r7);
        r8.addDirectory(r9);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.drew.metadata.bmp.BmpReader.readFileHeader(com.drew.lang.SequentialReader, com.drew.metadata.Metadata, boolean):void");
    }

    protected void readBitmapHeader(@NotNull SequentialReader sequentialReader, @NotNull BmpHeaderDirectory bmpHeaderDirectory, @NotNull Metadata metadata) {
        SequentialReader sequentialReader2 = sequentialReader;
        Directory directory = bmpHeaderDirectory;
        try {
            int i = directory.getInt(-2);
            long position = sequentialReader.getPosition();
            int int32 = sequentialReader.getInt32();
            directory.setInt(-1, int32);
            if (int32 == 12 && i == BITMAP) {
                directory.setInt(2, sequentialReader.getInt16());
                directory.setInt(1, sequentialReader.getInt16());
                directory.setInt(3, sequentialReader.getUInt16());
                directory.setInt(4, sequentialReader.getUInt16());
            }
            StringBuilder stringBuilder;
            if (int32 == 12) {
                directory.setInt(2, sequentialReader.getUInt16());
                directory.setInt(1, sequentialReader.getUInt16());
                directory.setInt(3, sequentialReader.getUInt16());
                directory.setInt(4, sequentialReader.getUInt16());
            } else if (int32 == 16 || int32 == 64) {
                directory.setInt(2, sequentialReader.getInt32());
                directory.setInt(1, sequentialReader.getInt32());
                directory.setInt(3, sequentialReader.getUInt16());
                directory.setInt(4, sequentialReader.getUInt16());
                if (int32 > 16) {
                    directory.setInt(5, sequentialReader.getInt32());
                    sequentialReader2.skip(4);
                    directory.setInt(6, sequentialReader.getInt32());
                    directory.setInt(7, sequentialReader.getInt32());
                    directory.setInt(8, sequentialReader.getInt32());
                    directory.setInt(9, sequentialReader.getInt32());
                    sequentialReader2.skip(6);
                    directory.setInt(10, sequentialReader.getUInt16());
                    sequentialReader2.skip(8);
                    directory.setInt(11, sequentialReader.getInt32());
                    sequentialReader2.skip(4);
                }
            } else if (int32 == 40 || int32 == 52 || int32 == 56 || int32 == 108 || int32 == 124) {
                directory.setInt(2, sequentialReader.getInt32());
                directory.setInt(1, sequentialReader.getInt32());
                directory.setInt(3, sequentialReader.getUInt16());
                directory.setInt(4, sequentialReader.getUInt16());
                directory.setInt(5, sequentialReader.getInt32());
                sequentialReader2.skip(4);
                directory.setInt(6, sequentialReader.getInt32());
                directory.setInt(7, sequentialReader.getInt32());
                directory.setInt(8, sequentialReader.getInt32());
                directory.setInt(9, sequentialReader.getInt32());
                if (int32 != 40) {
                    directory.setLong(12, sequentialReader.getUInt32());
                    directory.setLong(13, sequentialReader.getUInt32());
                    directory.setLong(14, sequentialReader.getUInt32());
                    if (int32 != 52) {
                        directory.setLong(15, sequentialReader.getUInt32());
                        if (int32 != 56) {
                            long uInt32 = sequentialReader.getUInt32();
                            directory.setLong(16, uInt32);
                            sequentialReader2.skip(36);
                            directory.setLong(17, sequentialReader.getUInt32());
                            directory.setLong(18, sequentialReader.getUInt32());
                            directory.setLong(19, sequentialReader.getUInt32());
                            if (int32 != 108) {
                                directory.setInt(20, sequentialReader.getInt32());
                                if (uInt32 == ColorSpaceType.PROFILE_EMBEDDED.getValue() || uInt32 == ColorSpaceType.PROFILE_LINKED.getValue()) {
                                    long uInt322 = sequentialReader.getUInt32();
                                    i = sequentialReader.getInt32();
                                    position += uInt322;
                                    if (sequentialReader.getPosition() > position) {
                                        stringBuilder = new StringBuilder();
                                        stringBuilder.append("Invalid profile data offset 0x");
                                        stringBuilder.append(Long.toHexString(position));
                                        directory.addError(stringBuilder.toString());
                                        return;
                                    }
                                    sequentialReader2.skip(position - sequentialReader.getPosition());
                                    if (uInt32 == ColorSpaceType.PROFILE_LINKED.getValue()) {
                                        directory.setString(21, sequentialReader2.getNullTerminatedString(i, Charsets.WINDOWS_1252));
                                    } else {
                                        new IccReader().extract(new ByteArrayReader(sequentialReader2.getBytes(i)), metadata, directory);
                                    }
                                } else {
                                    sequentialReader2.skip(12);
                                }
                            }
                        }
                    }
                }
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected DIB header size: ");
                stringBuilder.append(int32);
                directory.addError(stringBuilder.toString());
            }
        } catch (IOException unused) {
            directory.addError("Unable to read BMP header");
        } catch (MetadataException unused2) {
            directory.addError("Internal error");
        }
    }

    protected void addError(@NotNull String str, @NotNull Metadata metadata) {
        ErrorDirectory errorDirectory = (ErrorDirectory) metadata.getFirstDirectoryOfType(ErrorDirectory.class);
        if (errorDirectory == null) {
            metadata.addDirectory(new ErrorDirectory(str));
        } else {
            errorDirectory.addError(str);
        }
    }
}
