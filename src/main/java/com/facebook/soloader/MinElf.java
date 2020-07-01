package com.facebook.soloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public final class MinElf {
    public static final int DT_NEEDED = 1;
    public static final int DT_NULL = 0;
    public static final int DT_STRTAB = 5;
    public static final int ELF_MAGIC = 1179403647;
    public static final int PN_XNUM = 65535;
    public static final int PT_DYNAMIC = 2;
    public static final int PT_LOAD = 1;

    private static class ElfError extends RuntimeException {
        ElfError(String str) {
            super(str);
        }
    }

    public static String[] extract_DT_NEEDED(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            String[] extract_DT_NEEDED = extract_DT_NEEDED(fileInputStream.getChannel());
            return extract_DT_NEEDED;
        } finally {
            fileInputStream.close();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:121:0x01fb  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x019c  */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x0215  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00ca  */
    public static java.lang.String[] extract_DT_NEEDED(java.nio.channels.FileChannel r35) throws java.io.IOException {
        /*
        r0 = r35;
        r1 = 8;
        r1 = java.nio.ByteBuffer.allocate(r1);
        r2 = java.nio.ByteOrder.LITTLE_ENDIAN;
        r1.order(r2);
        r2 = 0;
        r4 = getu32(r0, r1, r2);
        r6 = 1179403647; // 0x464c457f float:13073.374 double:5.827028246E-315;
        r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r8 != 0) goto L_0x021d;
    L_0x001a:
        r4 = 4;
        r6 = getu8(r0, r1, r4);
        r8 = 1;
        if (r6 != r8) goto L_0x0024;
    L_0x0023:
        goto L_0x0025;
    L_0x0024:
        r8 = 0;
    L_0x0025:
        r9 = 5;
        r6 = getu8(r0, r1, r9);
        r11 = 2;
        if (r6 != r11) goto L_0x0033;
    L_0x002e:
        r6 = java.nio.ByteOrder.BIG_ENDIAN;
        r1.order(r6);
    L_0x0033:
        r11 = 28;
        r13 = 32;
        if (r8 == 0) goto L_0x003e;
    L_0x0039:
        r15 = getu32(r0, r1, r11);
        goto L_0x0042;
    L_0x003e:
        r15 = get64(r0, r1, r13);
    L_0x0042:
        r9 = 44;
        if (r8 == 0) goto L_0x004c;
    L_0x0046:
        r6 = getu16(r0, r1, r9);
        r4 = (long) r6;
        goto L_0x0053;
    L_0x004c:
        r4 = 56;
        r4 = getu16(r0, r1, r4);
        r4 = (long) r4;
    L_0x0053:
        if (r8 == 0) goto L_0x005c;
    L_0x0055:
        r2 = 42;
        r2 = getu16(r0, r1, r2);
        goto L_0x0062;
    L_0x005c:
        r2 = 54;
        r2 = getu16(r0, r1, r2);
    L_0x0062:
        r23 = 65535; // 0xffff float:9.1834E-41 double:3.23786E-319;
        r9 = 40;
        r3 = (r4 > r23 ? 1 : (r4 == r23 ? 0 : -1));
        if (r3 != 0) goto L_0x0086;
    L_0x006b:
        if (r8 == 0) goto L_0x0072;
    L_0x006d:
        r3 = getu32(r0, r1, r13);
        goto L_0x0076;
    L_0x0072:
        r3 = get64(r0, r1, r9);
    L_0x0076:
        if (r8 == 0) goto L_0x007e;
    L_0x0078:
        r3 = r3 + r11;
        r3 = getu32(r0, r1, r3);
        goto L_0x0085;
    L_0x007e:
        r5 = 44;
        r3 = r3 + r5;
        r3 = getu32(r0, r1, r3);
    L_0x0085:
        r4 = r3;
    L_0x0086:
        r13 = r15;
        r11 = 0;
    L_0x0089:
        r23 = 1;
        r25 = 8;
        r3 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1));
        if (r3 >= 0) goto L_0x00c2;
    L_0x0091:
        if (r8 == 0) goto L_0x009c;
    L_0x0093:
        r21 = 0;
        r9 = r13 + r21;
        r9 = getu32(r0, r1, r9);
        goto L_0x00a4;
    L_0x009c:
        r21 = 0;
        r9 = r13 + r21;
        r9 = getu32(r0, r1, r9);
    L_0x00a4:
        r29 = 2;
        r3 = (r9 > r29 ? 1 : (r9 == r29 ? 0 : -1));
        if (r3 != 0) goto L_0x00bb;
    L_0x00aa:
        if (r8 == 0) goto L_0x00b4;
    L_0x00ac:
        r9 = 4;
        r13 = r13 + r9;
        r9 = getu32(r0, r1, r13);
        goto L_0x00c4;
    L_0x00b4:
        r13 = r13 + r25;
        r9 = get64(r0, r1, r13);
        goto L_0x00c4;
    L_0x00bb:
        r9 = (long) r2;
        r13 = r13 + r9;
        r11 = r11 + r23;
        r9 = 40;
        goto L_0x0089;
    L_0x00c2:
        r9 = 0;
    L_0x00c4:
        r21 = 0;
        r3 = (r9 > r21 ? 1 : (r9 == r21 ? 0 : -1));
        if (r3 == 0) goto L_0x0215;
    L_0x00ca:
        r11 = r9;
        r13 = r21;
        r3 = 0;
    L_0x00ce:
        if (r8 == 0) goto L_0x00d9;
    L_0x00d0:
        r29 = r8;
        r7 = r11 + r21;
        r7 = getu32(r0, r1, r7);
        goto L_0x00e1;
    L_0x00d9:
        r29 = r8;
        r7 = r11 + r21;
        r7 = get64(r0, r1, r7);
    L_0x00e1:
        r6 = "malformed DT_NEEDED section";
        r31 = (r7 > r23 ? 1 : (r7 == r23 ? 0 : -1));
        if (r31 != 0) goto L_0x00f7;
    L_0x00e7:
        r31 = r9;
        r9 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        if (r3 == r9) goto L_0x00f1;
    L_0x00ee:
        r3 = r3 + 1;
        goto L_0x0111;
    L_0x00f1:
        r0 = new com.facebook.soloader.MinElf$ElfError;
        r0.<init>(r6);
        throw r0;
    L_0x00f7:
        r31 = r9;
        r9 = 5;
        r17 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1));
        if (r17 != 0) goto L_0x0111;
    L_0x00ff:
        if (r29 == 0) goto L_0x010a;
    L_0x0101:
        r13 = 4;
        r9 = r11 + r13;
        r9 = getu32(r0, r1, r9);
        goto L_0x0110;
    L_0x010a:
        r9 = r11 + r25;
        r9 = get64(r0, r1, r9);
    L_0x0110:
        r13 = r9;
    L_0x0111:
        r9 = 16;
        if (r29 == 0) goto L_0x0118;
    L_0x0115:
        r33 = r25;
        goto L_0x011a;
    L_0x0118:
        r33 = r9;
    L_0x011a:
        r11 = r11 + r33;
        r21 = 0;
        r33 = (r7 > r21 ? 1 : (r7 == r21 ? 0 : -1));
        if (r33 != 0) goto L_0x020b;
    L_0x0122:
        r7 = (r13 > r21 ? 1 : (r13 == r21 ? 0 : -1));
        if (r7 == 0) goto L_0x0203;
    L_0x0126:
        r7 = 0;
    L_0x0127:
        r11 = (long) r7;
        r8 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1));
        if (r8 >= 0) goto L_0x0194;
    L_0x012c:
        if (r29 == 0) goto L_0x0135;
    L_0x012e:
        r11 = r15 + r21;
        r11 = getu32(r0, r1, r11);
        goto L_0x013b;
    L_0x0135:
        r11 = r15 + r21;
        r11 = getu32(r0, r1, r11);
    L_0x013b:
        r8 = (r11 > r23 ? 1 : (r11 == r23 ? 0 : -1));
        if (r8 != 0) goto L_0x0185;
    L_0x013f:
        if (r29 == 0) goto L_0x0148;
    L_0x0141:
        r11 = r15 + r25;
        r11 = getu32(r0, r1, r11);
        goto L_0x014e;
    L_0x0148:
        r11 = r15 + r9;
        r11 = get64(r0, r1, r11);
    L_0x014e:
        if (r29 == 0) goto L_0x015e;
    L_0x0150:
        r17 = 20;
        r9 = r15 + r17;
        r8 = getu32(r0, r1, r9);
        r27 = r4;
        r4 = r8;
        r8 = 40;
        goto L_0x0168;
    L_0x015e:
        r27 = r4;
        r8 = 40;
        r4 = r15 + r8;
        r4 = get64(r0, r1, r4);
    L_0x0168:
        r10 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1));
        if (r10 > 0) goto L_0x0189;
    L_0x016c:
        r4 = r4 + r11;
        r10 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1));
        if (r10 >= 0) goto L_0x0189;
    L_0x0171:
        if (r29 == 0) goto L_0x017c;
    L_0x0173:
        r4 = 4;
        r7 = r15 + r4;
        r4 = getu32(r0, r1, r7);
        goto L_0x0182;
    L_0x017c:
        r4 = r15 + r25;
        r4 = get64(r0, r1, r4);
    L_0x0182:
        r13 = r13 - r11;
        r4 = r4 + r13;
        goto L_0x0196;
    L_0x0185:
        r27 = r4;
        r8 = 40;
    L_0x0189:
        r4 = (long) r2;
        r15 = r15 + r4;
        r7 = r7 + 1;
        r4 = r27;
        r9 = 16;
        r21 = 0;
        goto L_0x0127;
    L_0x0194:
        r4 = 0;
    L_0x0196:
        r7 = 0;
        r2 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1));
        if (r2 == 0) goto L_0x01fb;
    L_0x019c:
        r2 = new java.lang.String[r3];
        r3 = 0;
    L_0x019f:
        if (r29 == 0) goto L_0x01a8;
    L_0x01a1:
        r9 = r31 + r7;
        r9 = getu32(r0, r1, r9);
        goto L_0x01ae;
    L_0x01a8:
        r9 = r31 + r7;
        r9 = get64(r0, r1, r9);
    L_0x01ae:
        r7 = (r9 > r23 ? 1 : (r9 == r23 ? 0 : -1));
        if (r7 != 0) goto L_0x01da;
    L_0x01b2:
        if (r29 == 0) goto L_0x01bd;
    L_0x01b4:
        r19 = 4;
        r7 = r31 + r19;
        r7 = getu32(r0, r1, r7);
        goto L_0x01c5;
    L_0x01bd:
        r19 = 4;
        r7 = r31 + r25;
        r7 = get64(r0, r1, r7);
    L_0x01c5:
        r7 = r7 + r4;
        r7 = getSz(r0, r1, r7);
        r2[r3] = r7;
        r7 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        if (r3 == r7) goto L_0x01d4;
    L_0x01d1:
        r3 = r3 + 1;
        goto L_0x01df;
    L_0x01d4:
        r0 = new com.facebook.soloader.MinElf$ElfError;
        r0.<init>(r6);
        throw r0;
    L_0x01da:
        r7 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r19 = 4;
    L_0x01df:
        if (r29 == 0) goto L_0x01e4;
    L_0x01e1:
        r11 = r25;
        goto L_0x01e6;
    L_0x01e4:
        r11 = 16;
    L_0x01e6:
        r31 = r31 + r11;
        r21 = 0;
        r8 = (r9 > r21 ? 1 : (r9 == r21 ? 0 : -1));
        if (r8 != 0) goto L_0x01f8;
    L_0x01ee:
        r0 = r2.length;
        if (r3 != r0) goto L_0x01f2;
    L_0x01f1:
        return r2;
    L_0x01f2:
        r0 = new com.facebook.soloader.MinElf$ElfError;
        r0.<init>(r6);
        throw r0;
    L_0x01f8:
        r7 = r21;
        goto L_0x019f;
    L_0x01fb:
        r0 = new com.facebook.soloader.MinElf$ElfError;
        r1 = "did not find file offset of DT_STRTAB table";
        r0.<init>(r1);
        throw r0;
    L_0x0203:
        r0 = new com.facebook.soloader.MinElf$ElfError;
        r1 = "Dynamic section string-table not found";
        r0.<init>(r1);
        throw r0;
    L_0x020b:
        r8 = 40;
        r19 = 4;
        r8 = r29;
        r9 = r31;
        goto L_0x00ce;
    L_0x0215:
        r0 = new com.facebook.soloader.MinElf$ElfError;
        r1 = "ELF file does not contain dynamic linking information";
        r0.<init>(r1);
        throw r0;
    L_0x021d:
        r0 = new com.facebook.soloader.MinElf$ElfError;
        r1 = "file is not ELF";
        r0.<init>(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.MinElf.extract_DT_NEEDED(java.nio.channels.FileChannel):java.lang.String[]");
    }

    private static String getSz(FileChannel fileChannel, ByteBuffer byteBuffer, long j) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            long j2 = 1 + j;
            short u8Var = getu8(fileChannel, byteBuffer, j);
            if (u8Var == (short) 0) {
                return stringBuilder.toString();
            }
            stringBuilder.append((char) u8Var);
            j = j2;
        }
    }

    private static void read(FileChannel fileChannel, ByteBuffer byteBuffer, int i, long j) throws IOException {
        byteBuffer.position(0);
        byteBuffer.limit(i);
        while (byteBuffer.remaining() > 0) {
            i = fileChannel.read(byteBuffer, j);
            if (i == -1) {
                break;
            }
            j += (long) i;
        }
        if (byteBuffer.remaining() <= 0) {
            byteBuffer.position(0);
            return;
        }
        throw new ElfError("ELF file truncated");
    }

    private static long get64(FileChannel fileChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(fileChannel, byteBuffer, 8, j);
        return byteBuffer.getLong();
    }

    private static long getu32(FileChannel fileChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(fileChannel, byteBuffer, 4, j);
        return ((long) byteBuffer.getInt()) & 4294967295L;
    }

    private static int getu16(FileChannel fileChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(fileChannel, byteBuffer, 2, j);
        return byteBuffer.getShort() & 65535;
    }

    private static short getu8(FileChannel fileChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(fileChannel, byteBuffer, 1, j);
        return (short) (byteBuffer.get() & 255);
    }
}
