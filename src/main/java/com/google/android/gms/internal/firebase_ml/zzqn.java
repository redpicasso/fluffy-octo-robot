package com.google.android.gms.internal.firebase_ml;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

public final class zzqn implements Closeable {
    private static final char[] zzazx = ")]}'\n".toCharArray();
    private final Reader in;
    private int limit = 0;
    private int pos = 0;
    private boolean zzazy = false;
    private final char[] zzazz = new char[1024];
    private int zzbaa = 0;
    private int zzbab = 0;
    private int zzbac = 0;
    private long zzbad;
    private int zzbae;
    private String zzbaf;
    private int[] zzbag = new int[32];
    private int zzbah = 0;
    private String[] zzbai;
    private int[] zzbaj;

    public zzqn(Reader reader) {
        int[] iArr = this.zzbag;
        int i = this.zzbah;
        this.zzbah = i + 1;
        iArr[i] = 6;
        this.zzbai = new String[32];
        this.zzbaj = new int[32];
        if (reader != null) {
            this.in = reader;
            return;
        }
        throw new NullPointerException("in == null");
    }

    public final void setLenient(boolean z) {
        this.zzazy = true;
    }

    public final void beginArray() throws IOException {
        int i = this.zzbac;
        if (i == 0) {
            i = zznr();
        }
        if (i == 3) {
            zzbs(1);
            this.zzbaj[this.zzbah - 1] = 0;
            this.zzbac = 0;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder("Expected BEGIN_ARRAY but was ");
        stringBuilder.append(zznq());
        stringBuilder.append(zznv());
        throw new IllegalStateException(stringBuilder.toString());
    }

    public final void endArray() throws IOException {
        int i = this.zzbac;
        if (i == 0) {
            i = zznr();
        }
        if (i == 4) {
            this.zzbah--;
            int[] iArr = this.zzbaj;
            int i2 = this.zzbah - 1;
            iArr[i2] = iArr[i2] + 1;
            this.zzbac = 0;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder("Expected END_ARRAY but was ");
        stringBuilder.append(zznq());
        stringBuilder.append(zznv());
        throw new IllegalStateException(stringBuilder.toString());
    }

    public final void beginObject() throws IOException {
        int i = this.zzbac;
        if (i == 0) {
            i = zznr();
        }
        if (i == 1) {
            zzbs(3);
            this.zzbac = 0;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder("Expected BEGIN_OBJECT but was ");
        stringBuilder.append(zznq());
        stringBuilder.append(zznv());
        throw new IllegalStateException(stringBuilder.toString());
    }

    public final void endObject() throws IOException {
        int i = this.zzbac;
        if (i == 0) {
            i = zznr();
        }
        if (i == 2) {
            this.zzbah--;
            String[] strArr = this.zzbai;
            int i2 = this.zzbah;
            strArr[i2] = null;
            int[] iArr = this.zzbaj;
            i2--;
            iArr[i2] = iArr[i2] + 1;
            this.zzbac = 0;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder("Expected END_OBJECT but was ");
        stringBuilder.append(zznq());
        stringBuilder.append(zznv());
        throw new IllegalStateException(stringBuilder.toString());
    }

    public final zzqp zznq() throws IOException {
        int i = this.zzbac;
        if (i == 0) {
            i = zznr();
        }
        switch (i) {
            case 1:
                return zzqp.BEGIN_OBJECT;
            case 2:
                return zzqp.END_OBJECT;
            case 3:
                return zzqp.BEGIN_ARRAY;
            case 4:
                return zzqp.END_ARRAY;
            case 5:
            case 6:
                return zzqp.BOOLEAN;
            case 7:
                return zzqp.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return zzqp.STRING;
            case 12:
            case 13:
            case 14:
                return zzqp.NAME;
            case 15:
            case 16:
                return zzqp.NUMBER;
            case 17:
                return zzqp.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:98:0x017b  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x017a A:{RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x017a A:{RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x017b  */
    /* JADX WARNING: Missing block: B:106:0x019d, code:
            r1 = 2;
     */
    /* JADX WARNING: Missing block: B:145:0x020d, code:
            if (zze(r5) == false) goto L_0x019d;
     */
    /* JADX WARNING: Missing block: B:146:0x0210, code:
            if (r7 != 2) goto L_0x022f;
     */
    /* JADX WARNING: Missing block: B:147:0x0212, code:
            if (r8 == 0) goto L_0x022e;
     */
    /* JADX WARNING: Missing block: B:149:0x0218, code:
            if (r12 != Long.MIN_VALUE) goto L_0x021c;
     */
    /* JADX WARNING: Missing block: B:150:0x021a, code:
            if (r9 == null) goto L_0x022e;
     */
    /* JADX WARNING: Missing block: B:151:0x021c, code:
            if (r9 == null) goto L_0x021f;
     */
    /* JADX WARNING: Missing block: B:152:0x021f, code:
            r12 = -r12;
     */
    /* JADX WARNING: Missing block: B:153:0x0220, code:
            r0.zzbad = r12;
            r0.pos += r3;
            r0.zzbac = 15;
            r16 = 15;
     */
    /* JADX WARNING: Missing block: B:154:0x022e, code:
            r1 = 2;
     */
    /* JADX WARNING: Missing block: B:155:0x022f, code:
            if (r7 == r1) goto L_0x023b;
     */
    /* JADX WARNING: Missing block: B:157:0x0232, code:
            if (r7 == 4) goto L_0x023b;
     */
    /* JADX WARNING: Missing block: B:159:0x0235, code:
            if (r7 != 7) goto L_0x0238;
     */
    /* JADX WARNING: Missing block: B:161:0x023b, code:
            r0.zzbae = r3;
            r0.zzbac = 16;
            r16 = 16;
     */
    private final int zznr() throws java.io.IOException {
        /*
        r19 = this;
        r0 = r19;
        r1 = r0.zzbag;
        r2 = r0.zzbah;
        r3 = r2 + -1;
        r3 = r1[r3];
        r4 = 8;
        r7 = 93;
        r8 = 59;
        r9 = 44;
        r10 = 3;
        r11 = 6;
        r12 = 7;
        r13 = 4;
        r14 = 5;
        r15 = 2;
        r5 = 0;
        r6 = 1;
        if (r3 != r6) goto L_0x0021;
    L_0x001c:
        r2 = r2 - r6;
        r1[r2] = r15;
        goto L_0x00d2;
    L_0x0021:
        if (r3 != r15) goto L_0x003c;
    L_0x0023:
        r1 = r0.zzaf(r6);
        if (r1 == r9) goto L_0x00d2;
    L_0x0029:
        if (r1 == r8) goto L_0x0037;
    L_0x002b:
        if (r1 != r7) goto L_0x0030;
    L_0x002d:
        r0.zzbac = r13;
        return r13;
    L_0x0030:
        r1 = "Unterminated array";
        r1 = r0.zzci(r1);
        throw r1;
    L_0x0037:
        r19.zznt();
        goto L_0x00d2;
    L_0x003c:
        if (r3 == r10) goto L_0x02c0;
    L_0x003e:
        if (r3 != r14) goto L_0x0042;
    L_0x0040:
        goto L_0x02c0;
    L_0x0042:
        if (r3 != r13) goto L_0x0077;
    L_0x0044:
        r2 = r2 - r6;
        r1[r2] = r14;
        r1 = r0.zzaf(r6);
        r2 = 58;
        if (r1 == r2) goto L_0x00d2;
    L_0x004f:
        r2 = 61;
        if (r1 != r2) goto L_0x0070;
    L_0x0053:
        r19.zznt();
        r1 = r0.pos;
        r2 = r0.limit;
        if (r1 < r2) goto L_0x0062;
    L_0x005c:
        r1 = r0.zzbt(r6);
        if (r1 == 0) goto L_0x00d2;
    L_0x0062:
        r1 = r0.zzazz;
        r2 = r0.pos;
        r1 = r1[r2];
        r13 = 62;
        if (r1 != r13) goto L_0x00d2;
    L_0x006c:
        r2 = r2 + r6;
        r0.pos = r2;
        goto L_0x00d2;
    L_0x0070:
        r1 = "Expected ':'";
        r1 = r0.zzci(r1);
        throw r1;
    L_0x0077:
        if (r3 != r11) goto L_0x00b9;
    L_0x0079:
        r1 = r0.zzazy;
        if (r1 == 0) goto L_0x00b1;
    L_0x007d:
        r0.zzaf(r6);
        r1 = r0.pos;
        r1 = r1 - r6;
        r0.pos = r1;
        r1 = r0.pos;
        r2 = zzazx;
        r13 = r2.length;
        r1 = r1 + r13;
        r13 = r0.limit;
        if (r1 <= r13) goto L_0x0096;
    L_0x008f:
        r1 = r2.length;
        r1 = r0.zzbt(r1);
        if (r1 == 0) goto L_0x00b1;
    L_0x0096:
        r1 = 0;
    L_0x0097:
        r2 = zzazx;
        r13 = r2.length;
        if (r1 >= r13) goto L_0x00ab;
    L_0x009c:
        r13 = r0.zzazz;
        r11 = r0.pos;
        r11 = r11 + r1;
        r11 = r13[r11];
        r2 = r2[r1];
        if (r11 != r2) goto L_0x00b1;
    L_0x00a7:
        r1 = r1 + 1;
        r11 = 6;
        goto L_0x0097;
    L_0x00ab:
        r1 = r0.pos;
        r2 = r2.length;
        r1 = r1 + r2;
        r0.pos = r1;
    L_0x00b1:
        r1 = r0.zzbag;
        r2 = r0.zzbah;
        r2 = r2 - r6;
        r1[r2] = r12;
        goto L_0x00d2;
    L_0x00b9:
        if (r3 != r12) goto L_0x00d0;
    L_0x00bb:
        r1 = r0.zzaf(r5);
        r2 = -1;
        if (r1 != r2) goto L_0x00c7;
    L_0x00c2:
        r1 = 17;
        r0.zzbac = r1;
        return r1;
    L_0x00c7:
        r19.zznt();
        r1 = r0.pos;
        r1 = r1 - r6;
        r0.pos = r1;
        goto L_0x00d2;
    L_0x00d0:
        if (r3 == r4) goto L_0x02b8;
    L_0x00d2:
        r1 = r0.zzaf(r6);
        r2 = 34;
        if (r1 == r2) goto L_0x02b3;
    L_0x00da:
        r2 = 39;
        if (r1 == r2) goto L_0x02ad;
    L_0x00de:
        if (r1 == r9) goto L_0x0294;
    L_0x00e0:
        if (r1 == r8) goto L_0x0294;
    L_0x00e2:
        r2 = 91;
        if (r1 == r2) goto L_0x0291;
    L_0x00e6:
        if (r1 == r7) goto L_0x028b;
    L_0x00e8:
        r2 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        if (r1 == r2) goto L_0x0288;
    L_0x00ec:
        r1 = r0.pos;
        r1 = r1 - r6;
        r0.pos = r1;
        r1 = r0.zzazz;
        r2 = r0.pos;
        r1 = r1[r2];
        r2 = 116; // 0x74 float:1.63E-43 double:5.73E-322;
        if (r1 == r2) goto L_0x011f;
    L_0x00fb:
        r2 = 84;
        if (r1 != r2) goto L_0x0100;
    L_0x00ff:
        goto L_0x011f;
    L_0x0100:
        r2 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        if (r1 == r2) goto L_0x0118;
    L_0x0104:
        r2 = 70;
        if (r1 != r2) goto L_0x0109;
    L_0x0108:
        goto L_0x0118;
    L_0x0109:
        r2 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        if (r1 == r2) goto L_0x0111;
    L_0x010d:
        r2 = 78;
        if (r1 != r2) goto L_0x016f;
    L_0x0111:
        r1 = "null";
        r2 = "NULL";
        r3 = r2;
        r2 = 7;
        goto L_0x0125;
    L_0x0118:
        r1 = "false";
        r2 = "FALSE";
        r3 = r2;
        r2 = 6;
        goto L_0x0125;
    L_0x011f:
        r1 = "true";
        r2 = "TRUE";
        r3 = r2;
        r2 = 5;
    L_0x0125:
        r4 = r1.length();
        r7 = 1;
    L_0x012a:
        if (r7 >= r4) goto L_0x0153;
    L_0x012c:
        r8 = r0.pos;
        r8 = r8 + r7;
        r9 = r0.limit;
        if (r8 < r9) goto L_0x013c;
    L_0x0133:
        r8 = r7 + 1;
        r8 = r0.zzbt(r8);
        if (r8 != 0) goto L_0x013c;
    L_0x013b:
        goto L_0x016f;
    L_0x013c:
        r8 = r0.zzazz;
        r9 = r0.pos;
        r9 = r9 + r7;
        r8 = r8[r9];
        r9 = r1.charAt(r7);
        if (r8 == r9) goto L_0x0150;
    L_0x0149:
        r9 = r3.charAt(r7);
        if (r8 == r9) goto L_0x0150;
    L_0x014f:
        goto L_0x016f;
    L_0x0150:
        r7 = r7 + 1;
        goto L_0x012a;
    L_0x0153:
        r1 = r0.pos;
        r1 = r1 + r4;
        r3 = r0.limit;
        if (r1 < r3) goto L_0x0162;
    L_0x015a:
        r1 = r4 + 1;
        r1 = r0.zzbt(r1);
        if (r1 == 0) goto L_0x0171;
    L_0x0162:
        r1 = r0.zzazz;
        r3 = r0.pos;
        r3 = r3 + r4;
        r1 = r1[r3];
        r1 = r0.zze(r1);
        if (r1 == 0) goto L_0x0171;
    L_0x016f:
        r2 = 0;
        goto L_0x0178;
    L_0x0171:
        r1 = r0.pos;
        r1 = r1 + r4;
        r0.pos = r1;
        r0.zzbac = r2;
    L_0x0178:
        if (r2 == 0) goto L_0x017b;
    L_0x017a:
        return r2;
    L_0x017b:
        r1 = r0.zzazz;
        r2 = r0.pos;
        r3 = r0.limit;
        r7 = 0;
        r4 = r3;
        r12 = r7;
        r3 = 0;
        r7 = 0;
        r8 = 1;
        r9 = 0;
    L_0x0189:
        r5 = r2 + r3;
        if (r5 != r4) goto L_0x01a0;
    L_0x018d:
        r2 = r1.length;
        if (r3 == r2) goto L_0x0238;
    L_0x0190:
        r2 = r3 + 1;
        r2 = r0.zzbt(r2);
        if (r2 == 0) goto L_0x019d;
    L_0x0198:
        r2 = r0.pos;
        r4 = r0.limit;
        goto L_0x01a0;
    L_0x019d:
        r1 = 2;
        goto L_0x0210;
    L_0x01a0:
        r5 = r2 + r3;
        r5 = r1[r5];
        r11 = 43;
        if (r5 == r11) goto L_0x025f;
    L_0x01a8:
        r11 = 69;
        if (r5 == r11) goto L_0x0255;
    L_0x01ac:
        r11 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r5 == r11) goto L_0x0255;
    L_0x01b0:
        r11 = 45;
        if (r5 == r11) goto L_0x024a;
    L_0x01b4:
        r11 = 46;
        if (r5 == r11) goto L_0x0244;
    L_0x01b8:
        r11 = 48;
        if (r5 < r11) goto L_0x0209;
    L_0x01bc:
        r11 = 57;
        if (r5 <= r11) goto L_0x01c1;
    L_0x01c0:
        goto L_0x0209;
    L_0x01c1:
        if (r7 == r6) goto L_0x0200;
    L_0x01c3:
        if (r7 != 0) goto L_0x01c6;
    L_0x01c5:
        goto L_0x0200;
    L_0x01c6:
        if (r7 != r15) goto L_0x01ef;
    L_0x01c8:
        r17 = 0;
        r11 = (r12 > r17 ? 1 : (r12 == r17 ? 0 : -1));
        if (r11 == 0) goto L_0x0238;
    L_0x01ce:
        r17 = 10;
        r17 = r17 * r12;
        r5 = r5 + -48;
        r14 = (long) r5;
        r17 = r17 - r14;
        r14 = -922337203685477580; // 0xf333333333333334 float:4.1723254E-8 double:-8.390303882365713E246;
        r5 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r5 > 0) goto L_0x01e9;
    L_0x01e0:
        if (r5 != 0) goto L_0x01e7;
    L_0x01e2:
        r5 = (r17 > r12 ? 1 : (r17 == r12 ? 0 : -1));
        if (r5 >= 0) goto L_0x01e7;
    L_0x01e6:
        goto L_0x01e9;
    L_0x01e7:
        r5 = 0;
        goto L_0x01ea;
    L_0x01e9:
        r5 = 1;
    L_0x01ea:
        r5 = r5 & r8;
        r8 = r5;
        r12 = r17;
        goto L_0x01f2;
    L_0x01ef:
        if (r7 != r10) goto L_0x01f5;
    L_0x01f1:
        r7 = 4;
    L_0x01f2:
        r14 = 6;
        goto L_0x0264;
    L_0x01f5:
        r5 = 5;
        if (r7 == r5) goto L_0x01fc;
    L_0x01f8:
        r14 = 6;
        if (r7 != r14) goto L_0x0264;
    L_0x01fb:
        goto L_0x01fd;
    L_0x01fc:
        r14 = 6;
    L_0x01fd:
        r7 = 7;
        goto L_0x0264;
    L_0x0200:
        r14 = 6;
        r5 = r5 + -48;
        r5 = -r5;
        r11 = (long) r5;
        r12 = r11;
        r7 = 2;
        goto L_0x0264;
    L_0x0209:
        r1 = r0.zze(r5);
        if (r1 == 0) goto L_0x019d;
    L_0x020f:
        goto L_0x0238;
    L_0x0210:
        if (r7 != r1) goto L_0x022f;
    L_0x0212:
        if (r8 == 0) goto L_0x022e;
    L_0x0214:
        r1 = -9223372036854775808;
        r4 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1));
        if (r4 != 0) goto L_0x021c;
    L_0x021a:
        if (r9 == 0) goto L_0x022e;
    L_0x021c:
        if (r9 == 0) goto L_0x021f;
    L_0x021e:
        goto L_0x0220;
    L_0x021f:
        r12 = -r12;
    L_0x0220:
        r0.zzbad = r12;
        r1 = r0.pos;
        r1 = r1 + r3;
        r0.pos = r1;
        r5 = 15;
        r0.zzbac = r5;
        r16 = 15;
        goto L_0x026a;
    L_0x022e:
        r1 = 2;
    L_0x022f:
        if (r7 == r1) goto L_0x023b;
    L_0x0231:
        r1 = 4;
        if (r7 == r1) goto L_0x023b;
    L_0x0234:
        r1 = 7;
        if (r7 != r1) goto L_0x0238;
    L_0x0237:
        goto L_0x023b;
    L_0x0238:
        r16 = 0;
        goto L_0x026a;
    L_0x023b:
        r0.zzbae = r3;
        r5 = 16;
        r0.zzbac = r5;
        r16 = 16;
        goto L_0x026a;
    L_0x0244:
        r5 = 2;
        r14 = 6;
        if (r7 != r5) goto L_0x0238;
    L_0x0248:
        r7 = 3;
        goto L_0x0264;
    L_0x024a:
        r5 = 2;
        r14 = 6;
        if (r7 != 0) goto L_0x0251;
    L_0x024e:
        r7 = 1;
        r9 = 1;
        goto L_0x0264;
    L_0x0251:
        r15 = 5;
        if (r7 != r15) goto L_0x0238;
    L_0x0254:
        goto L_0x0263;
    L_0x0255:
        r5 = 2;
        r14 = 6;
        r15 = 5;
        if (r7 == r5) goto L_0x025d;
    L_0x025a:
        r5 = 4;
        if (r7 != r5) goto L_0x0238;
    L_0x025d:
        r7 = 5;
        goto L_0x0264;
    L_0x025f:
        r14 = 6;
        r15 = 5;
        if (r7 != r15) goto L_0x0238;
    L_0x0263:
        r7 = 6;
    L_0x0264:
        r3 = r3 + 1;
        r14 = 5;
        r15 = 2;
        goto L_0x0189;
    L_0x026a:
        if (r16 == 0) goto L_0x026d;
    L_0x026c:
        return r16;
    L_0x026d:
        r1 = r0.zzazz;
        r2 = r0.pos;
        r1 = r1[r2];
        r1 = r0.zze(r1);
        if (r1 == 0) goto L_0x0281;
    L_0x0279:
        r19.zznt();
        r1 = 10;
        r0.zzbac = r1;
        return r1;
    L_0x0281:
        r1 = "Expected value";
        r1 = r0.zzci(r1);
        throw r1;
    L_0x0288:
        r0.zzbac = r6;
        return r6;
    L_0x028b:
        if (r3 != r6) goto L_0x0294;
    L_0x028d:
        r1 = 4;
        r0.zzbac = r1;
        return r1;
    L_0x0291:
        r0.zzbac = r10;
        return r10;
    L_0x0294:
        if (r3 == r6) goto L_0x02a1;
    L_0x0296:
        r1 = 2;
        if (r3 != r1) goto L_0x029a;
    L_0x0299:
        goto L_0x02a1;
    L_0x029a:
        r1 = "Unexpected value";
        r1 = r0.zzci(r1);
        throw r1;
    L_0x02a1:
        r19.zznt();
        r1 = r0.pos;
        r1 = r1 - r6;
        r0.pos = r1;
        r1 = 7;
        r0.zzbac = r1;
        return r1;
    L_0x02ad:
        r19.zznt();
        r0.zzbac = r4;
        return r4;
    L_0x02b3:
        r1 = 9;
        r0.zzbac = r1;
        return r1;
    L_0x02b8:
        r1 = new java.lang.IllegalStateException;
        r2 = "JsonReader is closed";
        r1.<init>(r2);
        throw r1;
    L_0x02c0:
        r1 = r0.zzbag;
        r2 = r0.zzbah;
        r2 = r2 - r6;
        r4 = 4;
        r1[r2] = r4;
        r1 = 5;
        if (r3 != r1) goto L_0x02e5;
    L_0x02cb:
        r1 = r0.zzaf(r6);
        if (r1 == r9) goto L_0x02e5;
    L_0x02d1:
        if (r1 == r8) goto L_0x02e2;
    L_0x02d3:
        r2 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        if (r1 != r2) goto L_0x02db;
    L_0x02d7:
        r1 = 2;
        r0.zzbac = r1;
        return r1;
    L_0x02db:
        r1 = "Unterminated object";
        r1 = r0.zzci(r1);
        throw r1;
    L_0x02e2:
        r19.zznt();
    L_0x02e5:
        r1 = r0.zzaf(r6);
        r2 = 34;
        if (r1 == r2) goto L_0x0326;
    L_0x02ed:
        r2 = 39;
        if (r1 == r2) goto L_0x031e;
    L_0x02f1:
        r2 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        if (r1 == r2) goto L_0x0310;
    L_0x02f5:
        r19.zznt();
        r2 = r0.pos;
        r2 = r2 - r6;
        r0.pos = r2;
        r1 = (char) r1;
        r1 = r0.zze(r1);
        if (r1 == 0) goto L_0x0309;
    L_0x0304:
        r1 = 14;
        r0.zzbac = r1;
        return r1;
    L_0x0309:
        r1 = "Expected name";
        r1 = r0.zzci(r1);
        throw r1;
    L_0x0310:
        r1 = 5;
        if (r3 == r1) goto L_0x0317;
    L_0x0313:
        r1 = 2;
        r0.zzbac = r1;
        return r1;
    L_0x0317:
        r1 = "Expected name";
        r1 = r0.zzci(r1);
        throw r1;
    L_0x031e:
        r19.zznt();
        r1 = 12;
        r0.zzbac = r1;
        return r1;
    L_0x0326:
        r1 = 13;
        r0.zzbac = r1;
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzqn.zznr():int");
    }

    private final boolean zze(char c) throws IOException {
        if (!(c == 9 || c == 10 || c == 12 || c == 13 || c == ' ')) {
            if (c != '#') {
                if (c != ',') {
                    if (!(c == '/' || c == '=')) {
                        if (!(c == '{' || c == '}' || c == ':')) {
                            if (c != ';') {
                                switch (c) {
                                    case '[':
                                    case ']':
                                        break;
                                    case '\\':
                                        break;
                                    default:
                                        return true;
                                }
                            }
                        }
                    }
                }
            }
            zznt();
        }
        return false;
    }

    public final String nextName() throws IOException {
        String zzns;
        int i = this.zzbac;
        if (i == 0) {
            i = zznr();
        }
        if (i == 14) {
            zzns = zzns();
        } else if (i == 12) {
            zzns = zzf('\'');
        } else if (i == 13) {
            zzns = zzf('\"');
        } else {
            StringBuilder stringBuilder = new StringBuilder("Expected a name but was ");
            stringBuilder.append(zznq());
            stringBuilder.append(zznv());
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.zzbac = 0;
        this.zzbai[this.zzbah - 1] = zzns;
        return zzns;
    }

    public final String nextString() throws IOException {
        int i = this.zzbac;
        if (i == 0) {
            i = zznr();
        }
        String str = null;
        if (i == 10) {
            str = zzns();
        } else if (i == 8) {
            str = zzf('\'');
        } else if (i == 9) {
            str = zzf('\"');
        } else if (i == 11) {
            this.zzbaf = null;
        } else if (i == 15) {
            str = Long.toString(this.zzbad);
        } else if (i == 16) {
            str = new String(this.zzazz, this.pos, this.zzbae);
            this.pos += this.zzbae;
        } else {
            StringBuilder stringBuilder = new StringBuilder("Expected a string but was ");
            stringBuilder.append(zznq());
            stringBuilder.append(zznv());
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.zzbac = 0;
        int[] iArr = this.zzbaj;
        int i2 = this.zzbah - 1;
        iArr[i2] = iArr[i2] + 1;
        return str;
    }

    public final boolean nextBoolean() throws IOException {
        int i = this.zzbac;
        if (i == 0) {
            i = zznr();
        }
        int[] iArr;
        int i2;
        if (i == 5) {
            this.zzbac = 0;
            iArr = this.zzbaj;
            i2 = this.zzbah - 1;
            iArr[i2] = iArr[i2] + 1;
            return true;
        } else if (i == 6) {
            this.zzbac = 0;
            iArr = this.zzbaj;
            i2 = this.zzbah - 1;
            iArr[i2] = iArr[i2] + 1;
            return false;
        } else {
            StringBuilder stringBuilder = new StringBuilder("Expected a boolean but was ");
            stringBuilder.append(zznq());
            stringBuilder.append(zznv());
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public final void nextNull() throws IOException {
        int i = this.zzbac;
        if (i == 0) {
            i = zznr();
        }
        if (i == 7) {
            this.zzbac = 0;
            int[] iArr = this.zzbaj;
            int i2 = this.zzbah - 1;
            iArr[i2] = iArr[i2] + 1;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder("Expected null but was ");
        stringBuilder.append(zznq());
        stringBuilder.append(zznv());
        throw new IllegalStateException(stringBuilder.toString());
    }

    private final String zzf(char c) throws IOException {
        char[] cArr = this.zzazz;
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            int i = this.pos;
            int i2 = this.limit;
            int i3 = i;
            while (i3 < i2) {
                int i4 = i3 + 1;
                char c2 = cArr[i3];
                if (c2 == c) {
                    this.pos = i4;
                    stringBuilder.append(cArr, i, (i4 - i) - 1);
                    return stringBuilder.toString();
                } else if (c2 == '\\') {
                    this.pos = i4;
                    stringBuilder.append(cArr, i, (i4 - i) - 1);
                    stringBuilder.append(zznw());
                    break;
                } else {
                    if (c2 == 10) {
                        this.zzbaa++;
                        this.zzbab = i4;
                    }
                    i3 = i4;
                }
            }
            stringBuilder.append(cArr, i, i3 - i);
            this.pos = i3;
            if (!zzbt(1)) {
                throw zzci("Unterminated string");
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x007b  */
    /* JADX WARNING: Missing block: B:32:0x004b, code:
            zznt();
     */
    private final java.lang.String zzns() throws java.io.IOException {
        /*
        r6 = this;
        r0 = 0;
        r1 = 0;
        r2 = r1;
    L_0x0003:
        r1 = 0;
    L_0x0004:
        r3 = r6.pos;
        r4 = r3 + r1;
        r5 = r6.limit;
        if (r4 >= r5) goto L_0x004f;
    L_0x000c:
        r4 = r6.zzazz;
        r3 = r3 + r1;
        r3 = r4[r3];
        r4 = 9;
        if (r3 == r4) goto L_0x005d;
    L_0x0015:
        r4 = 10;
        if (r3 == r4) goto L_0x005d;
    L_0x0019:
        r4 = 12;
        if (r3 == r4) goto L_0x005d;
    L_0x001d:
        r4 = 13;
        if (r3 == r4) goto L_0x005d;
    L_0x0021:
        r4 = 32;
        if (r3 == r4) goto L_0x005d;
    L_0x0025:
        r4 = 35;
        if (r3 == r4) goto L_0x004b;
    L_0x0029:
        r4 = 44;
        if (r3 == r4) goto L_0x005d;
    L_0x002d:
        r4 = 47;
        if (r3 == r4) goto L_0x004b;
    L_0x0031:
        r4 = 61;
        if (r3 == r4) goto L_0x004b;
    L_0x0035:
        r4 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        if (r3 == r4) goto L_0x005d;
    L_0x0039:
        r4 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        if (r3 == r4) goto L_0x005d;
    L_0x003d:
        r4 = 58;
        if (r3 == r4) goto L_0x005d;
    L_0x0041:
        r4 = 59;
        if (r3 == r4) goto L_0x004b;
    L_0x0045:
        switch(r3) {
            case 91: goto L_0x005d;
            case 92: goto L_0x004b;
            case 93: goto L_0x005d;
            default: goto L_0x0048;
        };
    L_0x0048:
        r1 = r1 + 1;
        goto L_0x0004;
    L_0x004b:
        r6.zznt();
        goto L_0x005d;
    L_0x004f:
        r3 = r6.zzazz;
        r3 = r3.length;
        if (r1 >= r3) goto L_0x005f;
    L_0x0054:
        r3 = r1 + 1;
        r3 = r6.zzbt(r3);
        if (r3 == 0) goto L_0x005d;
    L_0x005c:
        goto L_0x0004;
    L_0x005d:
        r0 = r1;
        goto L_0x0079;
    L_0x005f:
        if (r2 != 0) goto L_0x0066;
    L_0x0061:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
    L_0x0066:
        r3 = r6.zzazz;
        r4 = r6.pos;
        r2.append(r3, r4, r1);
        r3 = r6.pos;
        r3 = r3 + r1;
        r6.pos = r3;
        r1 = 1;
        r1 = r6.zzbt(r1);
        if (r1 != 0) goto L_0x0003;
    L_0x0079:
        if (r2 != 0) goto L_0x0085;
    L_0x007b:
        r1 = new java.lang.String;
        r2 = r6.zzazz;
        r3 = r6.pos;
        r1.<init>(r2, r3, r0);
        goto L_0x0090;
    L_0x0085:
        r1 = r6.zzazz;
        r3 = r6.pos;
        r2.append(r1, r3, r0);
        r1 = r2.toString();
    L_0x0090:
        r2 = r6.pos;
        r2 = r2 + r0;
        r6.pos = r2;
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzqn.zzns():java.lang.String");
    }

    private final void zzg(char c) throws IOException {
        char[] cArr = this.zzazz;
        while (true) {
            int i = this.pos;
            int i2 = this.limit;
            while (i < i2) {
                int i3 = i + 1;
                char c2 = cArr[i];
                if (c2 == c) {
                    this.pos = i3;
                    return;
                } else if (c2 == '\\') {
                    this.pos = i3;
                    zznw();
                    break;
                } else {
                    if (c2 == 10) {
                        this.zzbaa++;
                        this.zzbab = i3;
                    }
                    i = i3;
                }
            }
            this.pos = i;
            if (!zzbt(1)) {
                throw zzci("Unterminated string");
            }
        }
    }

    public final void close() throws IOException {
        this.zzbac = 0;
        this.zzbag[0] = 8;
        this.zzbah = 1;
        this.in.close();
    }

    public final void skipValue() throws IOException {
        int i;
        int i2 = 0;
        do {
            i = this.zzbac;
            if (i == 0) {
                i = zznr();
            }
            if (i == 3) {
                zzbs(1);
            } else if (i == 1) {
                zzbs(3);
            } else {
                if (i == 4) {
                    this.zzbah--;
                } else if (i == 2) {
                    this.zzbah--;
                } else if (i == 14 || i == 10) {
                    do {
                        i = 0;
                        while (true) {
                            int i3 = this.pos;
                            if (i3 + i < this.limit) {
                                char c = this.zzazz[i3 + i];
                                if (!(c == 9 || c == 10 || c == 12 || c == 13 || c == ' ')) {
                                    if (c != '#') {
                                        if (c != ',') {
                                            if (!(c == '/' || c == '=')) {
                                                if (!(c == '{' || c == '}' || c == ':')) {
                                                    if (c != ';') {
                                                        switch (c) {
                                                            case '[':
                                                            case ']':
                                                                break;
                                                            case '\\':
                                                                break;
                                                            default:
                                                                i++;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                this.pos = i3 + i;
                            }
                        }
                        zznt();
                        this.pos += i;
                        this.zzbac = 0;
                    } while (zzbt(1));
                    this.zzbac = 0;
                } else if (i == 8 || i == 12) {
                    zzg('\'');
                    this.zzbac = 0;
                } else if (i == 9 || i == 13) {
                    zzg('\"');
                    this.zzbac = 0;
                } else {
                    if (i == 16) {
                        this.pos += this.zzbae;
                    }
                    this.zzbac = 0;
                }
                i2--;
                this.zzbac = 0;
            }
            i2++;
            this.zzbac = 0;
        } while (i2 != 0);
        int[] iArr = this.zzbaj;
        i2 = this.zzbah;
        i = i2 - 1;
        iArr[i] = iArr[i] + 1;
        this.zzbai[i2 - 1] = "null";
    }

    private final void zzbs(int i) {
        int i2 = this.zzbah;
        Object obj = this.zzbag;
        if (i2 == obj.length) {
            Object obj2 = new int[(i2 << 1)];
            Object obj3 = new int[(i2 << 1)];
            Object obj4 = new String[(i2 << 1)];
            System.arraycopy(obj, 0, obj2, 0, i2);
            System.arraycopy(this.zzbaj, 0, obj3, 0, this.zzbah);
            System.arraycopy(this.zzbai, 0, obj4, 0, this.zzbah);
            this.zzbag = obj2;
            this.zzbaj = obj3;
            this.zzbai = obj4;
        }
        int[] iArr = this.zzbag;
        int i3 = this.zzbah;
        this.zzbah = i3 + 1;
        iArr[i3] = i;
    }

    private final boolean zzbt(int i) throws IOException {
        Object obj = this.zzazz;
        int i2 = this.zzbab;
        int i3 = this.pos;
        this.zzbab = i2 - i3;
        i2 = this.limit;
        if (i2 != i3) {
            this.limit = i2 - i3;
            System.arraycopy(obj, i3, obj, 0, this.limit);
        } else {
            this.limit = 0;
        }
        this.pos = 0;
        do {
            Reader reader = this.in;
            i3 = this.limit;
            i2 = reader.read(obj, i3, obj.length - i3);
            if (i2 == -1) {
                return false;
            }
            this.limit += i2;
            if (this.zzbaa == 0) {
                i2 = this.zzbab;
                if (i2 == 0 && this.limit > 0 && obj[0] == 65279) {
                    this.pos++;
                    this.zzbab = i2 + 1;
                    i++;
                }
            }
        } while (this.limit < i);
        return true;
    }

    private final int zzaf(boolean z) throws IOException {
        char[] cArr = this.zzazz;
        int i = this.pos;
        int i2 = this.limit;
        while (true) {
            int i3 = 1;
            if (i == i2) {
                this.pos = i;
                if (zzbt(1)) {
                    i = this.pos;
                    i2 = this.limit;
                } else if (!z) {
                    return -1;
                } else {
                    StringBuilder stringBuilder = new StringBuilder("End of input");
                    stringBuilder.append(zznv());
                    throw new EOFException(stringBuilder.toString());
                }
            }
            int i4 = i + 1;
            char c = cArr[i];
            if (c == 10) {
                this.zzbaa++;
                this.zzbab = i4;
            } else if (!(c == ' ' || c == 13 || c == 9)) {
                if (c == '/') {
                    this.pos = i4;
                    if (i4 == i2) {
                        this.pos--;
                        boolean zzbt = zzbt(2);
                        this.pos++;
                        if (!zzbt) {
                            return c;
                        }
                    }
                    zznt();
                    i2 = this.pos;
                    char c2 = cArr[i2];
                    if (c2 == '*') {
                        this.pos = i2 + 1;
                        while (true) {
                            i4 = 0;
                            if (this.pos + 2 > this.limit && !zzbt(2)) {
                                i3 = 0;
                                break;
                            }
                            char[] cArr2 = this.zzazz;
                            i2 = this.pos;
                            if (cArr2[i2] != 10) {
                                while (i4 < 2) {
                                    if (this.zzazz[this.pos + i4] == "*/".charAt(i4)) {
                                        i4++;
                                    }
                                }
                                break;
                            }
                            this.zzbaa++;
                            this.zzbab = i2 + 1;
                            this.pos++;
                        }
                        if (i3 != 0) {
                            i = this.pos + 2;
                            i2 = this.limit;
                        } else {
                            throw zzci("Unterminated comment");
                        }
                    } else if (c2 != '/') {
                        return c;
                    } else {
                        this.pos = i2 + 1;
                        zznu();
                        i = this.pos;
                        i2 = this.limit;
                    }
                } else if (c == '#') {
                    this.pos = i4;
                    zznt();
                    zznu();
                    i = this.pos;
                    i2 = this.limit;
                } else {
                    this.pos = i4;
                    return c;
                }
            }
            i = i4;
        }
    }

    private final void zznt() throws IOException {
        if (!this.zzazy) {
            throw zzci("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private final void zznu() throws IOException {
        char c;
        do {
            if (this.pos >= this.limit && !zzbt(1)) {
                break;
            }
            char[] cArr = this.zzazz;
            int i = this.pos;
            this.pos = i + 1;
            c = cArr[i];
            if (c == 10) {
                this.zzbaa++;
                this.zzbab = this.pos;
                return;
            }
        } while (c != 13);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append(zznv());
        return stringBuilder.toString();
    }

    private final String zznv() {
        int i = this.zzbaa + 1;
        int i2 = (this.pos - this.zzbab) + 1;
        StringBuilder stringBuilder = new StringBuilder(" at line ");
        stringBuilder.append(i);
        stringBuilder.append(" column ");
        stringBuilder.append(i2);
        stringBuilder.append(" path ");
        StringBuilder stringBuilder2 = new StringBuilder("$");
        i2 = this.zzbah;
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = this.zzbag[i3];
            if (i4 == 1 || i4 == 2) {
                stringBuilder2.append('[');
                stringBuilder2.append(this.zzbaj[i3]);
                stringBuilder2.append(']');
            } else if (i4 == 3 || i4 == 4 || i4 == 5) {
                stringBuilder2.append('.');
                String[] strArr = this.zzbai;
                if (strArr[i3] != null) {
                    stringBuilder2.append(strArr[i3]);
                }
            }
        }
        stringBuilder.append(stringBuilder2.toString());
        return stringBuilder.toString();
    }

    private final char zznw() throws IOException {
        String str = "Unterminated escape sequence";
        if (this.pos != this.limit || zzbt(1)) {
            char[] cArr = this.zzazz;
            int i = this.pos;
            this.pos = i + 1;
            char c = cArr[i];
            if (c == 10) {
                this.zzbaa++;
                this.zzbab = this.pos;
            } else if (!(c == '\"' || c == '\'' || c == '/' || c == '\\')) {
                if (c == 'b') {
                    return 8;
                }
                if (c == 'f') {
                    return 12;
                }
                if (c == 'n') {
                    return 10;
                }
                if (c == 'r') {
                    return 13;
                }
                if (c == 't') {
                    return 9;
                }
                if (c != 'u') {
                    throw zzci("Invalid escape sequence");
                } else if (this.pos + 4 <= this.limit || zzbt(4)) {
                    c = 0;
                    int i2 = this.pos;
                    int i3 = i2 + 4;
                    while (i2 < i3) {
                        int i4;
                        char c2 = this.zzazz[i2];
                        c = (char) (c << 4);
                        if (c2 < '0' || c2 > '9') {
                            if (c2 >= 'a' && c2 <= 'f') {
                                i4 = c2 - 97;
                            } else if (c2 < 'A' || c2 > 'F') {
                                StringBuilder stringBuilder = new StringBuilder("\\u");
                                stringBuilder.append(new String(this.zzazz, this.pos, 4));
                                throw new NumberFormatException(stringBuilder.toString());
                            } else {
                                i4 = c2 - 65;
                            }
                            i4 += 10;
                        } else {
                            i4 = c2 - 48;
                        }
                        c = (char) (c + i4);
                        i2++;
                    }
                    this.pos += 4;
                    return c;
                } else {
                    throw zzci(str);
                }
            }
            return c;
        }
        throw zzci(str);
    }

    private final IOException zzci(String str) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(zznv());
        throw new zzqr(stringBuilder.toString());
    }

    static {
        zzqm.zzazw = new zzqo();
    }
}
