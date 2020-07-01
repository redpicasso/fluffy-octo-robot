package com.drew.metadata.icc;

import androidx.core.view.ViewCompat;
import androidx.exifinterface.media.ExifInterface;
import com.drew.lang.ByteArrayReader;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.TagDescriptor;
import java.io.IOException;

public class IccDescriptor extends TagDescriptor<IccDirectory> {
    private static final int ICC_TAG_TYPE_CURV = 1668641398;
    private static final int ICC_TAG_TYPE_DESC = 1684370275;
    private static final int ICC_TAG_TYPE_MEAS = 1835360627;
    private static final int ICC_TAG_TYPE_MLUC = 1835824483;
    private static final int ICC_TAG_TYPE_SIG = 1936287520;
    private static final int ICC_TAG_TYPE_TEXT = 1952807028;
    private static final int ICC_TAG_TYPE_XYZ_ARRAY = 1482250784;

    public IccDescriptor(@NotNull IccDirectory iccDirectory) {
        super(iccDirectory);
    }

    public String getDescription(int i) {
        if (i == 8) {
            return getProfileVersionDescription();
        }
        if (i == 12) {
            return getProfileClassDescription();
        }
        if (i == 40) {
            return getPlatformDescription();
        }
        if (i == 64) {
            return getRenderingIntentDescription();
        }
        if (i <= 538976288 || i >= 2054847098) {
            return super.getDescription(i);
        }
        return getTagDataString(i);
    }

    /* JADX WARNING: Missing block: B:16:0x004b, code:
            return new java.lang.String(r2, 8, (r2.length - 8) - 1);
     */
    @com.drew.lang.annotations.Nullable
    private java.lang.String getTagDataString(int r18) {
        /*
        r17 = this;
        r0 = r17;
        r1 = r18;
        r2 = r0._directory;	 Catch:{ IOException -> 0x020a }
        r2 = (com.drew.metadata.icc.IccDirectory) r2;	 Catch:{ IOException -> 0x020a }
        r2 = r2.getByteArray(r1);	 Catch:{ IOException -> 0x020a }
        if (r2 != 0) goto L_0x0017;
    L_0x000e:
        r2 = r0._directory;	 Catch:{ IOException -> 0x020a }
        r2 = (com.drew.metadata.icc.IccDirectory) r2;	 Catch:{ IOException -> 0x020a }
        r1 = r2.getString(r1);	 Catch:{ IOException -> 0x020a }
        return r1;
    L_0x0017:
        r1 = new com.drew.lang.ByteArrayReader;	 Catch:{ IOException -> 0x020a }
        r1.<init>(r2);	 Catch:{ IOException -> 0x020a }
        r3 = 0;
        r4 = r1.getInt32(r3);	 Catch:{ IOException -> 0x020a }
        r5 = ")";
        r6 = "(";
        r7 = 7;
        r8 = 16;
        r9 = 3;
        r10 = ", ";
        r11 = 2;
        r12 = 12;
        r13 = 1;
        r14 = 8;
        switch(r4) {
            case 1482250784: goto L_0x019b;
            case 1668641398: goto L_0x016c;
            case 1684370275: goto L_0x0161;
            case 1835360627: goto L_0x00a0;
            case 1835824483: goto L_0x0055;
            case 1936287520: goto L_0x004c;
            case 1952807028: goto L_0x0038;
            default: goto L_0x0034;
        };
    L_0x0034:
        r1 = "%s (0x%08X): %d bytes";
        goto L_0x01f0;
    L_0x0038:
        r1 = new java.lang.String;	 Catch:{ UnsupportedEncodingException -> 0x0043 }
        r3 = r2.length;	 Catch:{ UnsupportedEncodingException -> 0x0043 }
        r3 = r3 - r14;
        r3 = r3 - r13;
        r4 = "ASCII";
        r1.<init>(r2, r14, r3, r4);	 Catch:{ UnsupportedEncodingException -> 0x0043 }
        return r1;
    L_0x0043:
        r1 = new java.lang.String;	 Catch:{ IOException -> 0x020a }
        r3 = r2.length;	 Catch:{ IOException -> 0x020a }
        r3 = r3 - r14;
        r3 = r3 - r13;
        r1.<init>(r2, r14, r3);	 Catch:{ IOException -> 0x020a }
        return r1;
    L_0x004c:
        r1 = r1.getInt32(r14);	 Catch:{ IOException -> 0x020a }
        r1 = com.drew.metadata.icc.IccReader.getStringFromInt32(r1);	 Catch:{ IOException -> 0x020a }
        return r1;
    L_0x0055:
        r4 = r1.getInt32(r14);	 Catch:{ IOException -> 0x020a }
        r7 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x020a }
        r7.<init>();	 Catch:{ IOException -> 0x020a }
        r7.append(r4);	 Catch:{ IOException -> 0x020a }
    L_0x0061:
        if (r3 >= r4) goto L_0x009b;
    L_0x0063:
        r9 = r3 * 12;
        r9 = r9 + r8;
        r10 = r1.getInt32(r9);	 Catch:{ IOException -> 0x020a }
        r10 = com.drew.metadata.icc.IccReader.getStringFromInt32(r10);	 Catch:{ IOException -> 0x020a }
        r11 = r9 + 4;
        r11 = r1.getInt32(r11);	 Catch:{ IOException -> 0x020a }
        r9 = r9 + 8;
        r9 = r1.getInt32(r9);	 Catch:{ IOException -> 0x020a }
        r12 = new java.lang.String;	 Catch:{ UnsupportedEncodingException -> 0x0082 }
        r13 = "UTF-16BE";
        r12.<init>(r2, r9, r11, r13);	 Catch:{ UnsupportedEncodingException -> 0x0082 }
        goto L_0x0087;
    L_0x0082:
        r12 = new java.lang.String;	 Catch:{ IOException -> 0x020a }
        r12.<init>(r2, r9, r11);	 Catch:{ IOException -> 0x020a }
    L_0x0087:
        r9 = " ";
        r7.append(r9);	 Catch:{ IOException -> 0x020a }
        r7.append(r10);	 Catch:{ IOException -> 0x020a }
        r7.append(r6);	 Catch:{ IOException -> 0x020a }
        r7.append(r12);	 Catch:{ IOException -> 0x020a }
        r7.append(r5);	 Catch:{ IOException -> 0x020a }
        r3 = r3 + 1;
        goto L_0x0061;
    L_0x009b:
        r1 = r7.toString();	 Catch:{ IOException -> 0x020a }
        return r1;
    L_0x00a0:
        r2 = r1.getInt32(r14);	 Catch:{ IOException -> 0x020a }
        r4 = r1.getS15Fixed16(r12);	 Catch:{ IOException -> 0x020a }
        r5 = r1.getS15Fixed16(r8);	 Catch:{ IOException -> 0x020a }
        r6 = 20;
        r6 = r1.getS15Fixed16(r6);	 Catch:{ IOException -> 0x020a }
        r8 = 24;
        r8 = r1.getInt32(r8);	 Catch:{ IOException -> 0x020a }
        r10 = 28;
        r10 = r1.getS15Fixed16(r10);	 Catch:{ IOException -> 0x020a }
        r12 = 32;
        r1 = r1.getInt32(r12);	 Catch:{ IOException -> 0x020a }
        r12 = "Unknown";
        r14 = "Unknown %d";
        if (r2 == 0) goto L_0x00e1;
    L_0x00ca:
        if (r2 == r13) goto L_0x00de;
    L_0x00cc:
        if (r2 == r11) goto L_0x00db;
    L_0x00ce:
        r15 = new java.lang.Object[r13];	 Catch:{ IOException -> 0x020a }
        r16 = java.lang.Integer.valueOf(r2);	 Catch:{ IOException -> 0x020a }
        r15[r3] = r16;	 Catch:{ IOException -> 0x020a }
        r15 = java.lang.String.format(r14, r15);	 Catch:{ IOException -> 0x020a }
        goto L_0x00e2;
    L_0x00db:
        r15 = "1964 10°";
        goto L_0x00e2;
    L_0x00de:
        r15 = "1931 2°";
        goto L_0x00e2;
    L_0x00e1:
        r15 = r12;
    L_0x00e2:
        if (r8 == 0) goto L_0x00fa;
    L_0x00e4:
        if (r8 == r13) goto L_0x00f8;
    L_0x00e6:
        if (r8 == r11) goto L_0x00f5;
    L_0x00e8:
        r8 = new java.lang.Object[r13];	 Catch:{ IOException -> 0x020a }
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ IOException -> 0x020a }
        r8[r3] = r2;	 Catch:{ IOException -> 0x020a }
        r12 = java.lang.String.format(r14, r8);	 Catch:{ IOException -> 0x020a }
        goto L_0x00fa;
    L_0x00f5:
        r12 = "0/d or d/0";
        goto L_0x00fa;
    L_0x00f8:
        r12 = "0/45 or 45/0";
    L_0x00fa:
        switch(r1) {
            case 0: goto L_0x0116;
            case 1: goto L_0x0113;
            case 2: goto L_0x0110;
            case 3: goto L_0x010d;
            case 4: goto L_0x010a;
            case 5: goto L_0x0107;
            case 6: goto L_0x0104;
            case 7: goto L_0x0101;
            case 8: goto L_0x00fe;
            default: goto L_0x00fd;
        };	 Catch:{ IOException -> 0x020a }
    L_0x00fd:
        goto L_0x0119;
    L_0x00fe:
        r1 = "F8";
        goto L_0x0125;
    L_0x0101:
        r1 = "Equi-Power (E)";
        goto L_0x0125;
    L_0x0104:
        r1 = "A";
        goto L_0x0125;
    L_0x0107:
        r1 = "D55";
        goto L_0x0125;
    L_0x010a:
        r1 = "F2";
        goto L_0x0125;
    L_0x010d:
        r1 = "D93";
        goto L_0x0125;
    L_0x0110:
        r1 = "D65";
        goto L_0x0125;
    L_0x0113:
        r1 = "D50";
        goto L_0x0125;
    L_0x0116:
        r1 = "unknown";
        goto L_0x0125;
    L_0x0119:
        r2 = new java.lang.Object[r13];	 Catch:{ IOException -> 0x020a }
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ IOException -> 0x020a }
        r2[r3] = r1;	 Catch:{ IOException -> 0x020a }
        r1 = java.lang.String.format(r14, r2);	 Catch:{ IOException -> 0x020a }
    L_0x0125:
        r2 = new java.text.DecimalFormat;	 Catch:{ IOException -> 0x020a }
        r8 = "0.###";
        r2.<init>(r8);	 Catch:{ IOException -> 0x020a }
        r8 = "%s Observer, Backing (%s, %s, %s), Geometry %s, Flare %d%%, Illuminant %s";
        r7 = new java.lang.Object[r7];	 Catch:{ IOException -> 0x020a }
        r7[r3] = r15;	 Catch:{ IOException -> 0x020a }
        r3 = (double) r4;	 Catch:{ IOException -> 0x020a }
        r3 = r2.format(r3);	 Catch:{ IOException -> 0x020a }
        r7[r13] = r3;	 Catch:{ IOException -> 0x020a }
        r3 = (double) r5;	 Catch:{ IOException -> 0x020a }
        r3 = r2.format(r3);	 Catch:{ IOException -> 0x020a }
        r7[r11] = r3;	 Catch:{ IOException -> 0x020a }
        r3 = (double) r6;	 Catch:{ IOException -> 0x020a }
        r2 = r2.format(r3);	 Catch:{ IOException -> 0x020a }
        r7[r9] = r2;	 Catch:{ IOException -> 0x020a }
        r2 = 4;
        r7[r2] = r12;	 Catch:{ IOException -> 0x020a }
        r2 = 5;
        r3 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r10 = r10 * r3;
        r3 = java.lang.Math.round(r10);	 Catch:{ IOException -> 0x020a }
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ IOException -> 0x020a }
        r7[r2] = r3;	 Catch:{ IOException -> 0x020a }
        r2 = 6;
        r7[r2] = r1;	 Catch:{ IOException -> 0x020a }
        r1 = java.lang.String.format(r8, r7);	 Catch:{ IOException -> 0x020a }
        return r1;
    L_0x0161:
        r1 = r1.getInt32(r14);	 Catch:{ IOException -> 0x020a }
        r3 = new java.lang.String;	 Catch:{ IOException -> 0x020a }
        r1 = r1 - r13;
        r3.<init>(r2, r12, r1);	 Catch:{ IOException -> 0x020a }
        return r3;
    L_0x016c:
        r2 = r1.getInt32(r14);	 Catch:{ IOException -> 0x020a }
        r4 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x020a }
        r4.<init>();	 Catch:{ IOException -> 0x020a }
        r5 = 0;
    L_0x0176:
        if (r5 >= r2) goto L_0x0196;
    L_0x0178:
        if (r5 == 0) goto L_0x017d;
    L_0x017a:
        r4.append(r10);	 Catch:{ IOException -> 0x020a }
    L_0x017d:
        r6 = r5 * 2;
        r6 = r6 + r12;
        r6 = r1.getUInt16(r6);	 Catch:{ IOException -> 0x020a }
        r6 = (float) r6;	 Catch:{ IOException -> 0x020a }
        r8 = (double) r6;	 Catch:{ IOException -> 0x020a }
        r13 = 4679239875398991872; // 0x40efffe000000000 float:0.0 double:65535.0;
        r8 = r8 / r13;
        r6 = formatDoubleAsString(r8, r7, r3);	 Catch:{ IOException -> 0x020a }
        r4.append(r6);	 Catch:{ IOException -> 0x020a }
        r5 = r5 + 1;
        goto L_0x0176;
    L_0x0196:
        r1 = r4.toString();	 Catch:{ IOException -> 0x020a }
        return r1;
    L_0x019b:
        r4 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x020a }
        r4.<init>();	 Catch:{ IOException -> 0x020a }
        r7 = new java.text.DecimalFormat;	 Catch:{ IOException -> 0x020a }
        r8 = "0.####";
        r7.<init>(r8);	 Catch:{ IOException -> 0x020a }
        r2 = r2.length;	 Catch:{ IOException -> 0x020a }
        r2 = r2 - r14;
        r2 = r2 / r12;
    L_0x01aa:
        if (r3 >= r2) goto L_0x01eb;
    L_0x01ac:
        r8 = r3 * 12;
        r8 = r8 + r14;
        r9 = r1.getS15Fixed16(r8);	 Catch:{ IOException -> 0x020a }
        r11 = r8 + 4;
        r11 = r1.getS15Fixed16(r11);	 Catch:{ IOException -> 0x020a }
        r8 = r8 + 8;
        r8 = r1.getS15Fixed16(r8);	 Catch:{ IOException -> 0x020a }
        if (r3 <= 0) goto L_0x01c4;
    L_0x01c1:
        r4.append(r10);	 Catch:{ IOException -> 0x020a }
    L_0x01c4:
        r4.append(r6);	 Catch:{ IOException -> 0x020a }
        r12 = (double) r9;	 Catch:{ IOException -> 0x020a }
        r9 = r7.format(r12);	 Catch:{ IOException -> 0x020a }
        r4.append(r9);	 Catch:{ IOException -> 0x020a }
        r4.append(r10);	 Catch:{ IOException -> 0x020a }
        r11 = (double) r11;	 Catch:{ IOException -> 0x020a }
        r9 = r7.format(r11);	 Catch:{ IOException -> 0x020a }
        r4.append(r9);	 Catch:{ IOException -> 0x020a }
        r4.append(r10);	 Catch:{ IOException -> 0x020a }
        r8 = (double) r8;	 Catch:{ IOException -> 0x020a }
        r8 = r7.format(r8);	 Catch:{ IOException -> 0x020a }
        r4.append(r8);	 Catch:{ IOException -> 0x020a }
        r4.append(r5);	 Catch:{ IOException -> 0x020a }
        r3 = r3 + 1;
        goto L_0x01aa;
    L_0x01eb:
        r1 = r4.toString();	 Catch:{ IOException -> 0x020a }
        return r1;
    L_0x01f0:
        r5 = new java.lang.Object[r9];	 Catch:{ IOException -> 0x020a }
        r6 = com.drew.metadata.icc.IccReader.getStringFromInt32(r4);	 Catch:{ IOException -> 0x020a }
        r5[r3] = r6;	 Catch:{ IOException -> 0x020a }
        r3 = java.lang.Integer.valueOf(r4);	 Catch:{ IOException -> 0x020a }
        r5[r13] = r3;	 Catch:{ IOException -> 0x020a }
        r2 = r2.length;	 Catch:{ IOException -> 0x020a }
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ IOException -> 0x020a }
        r5[r11] = r2;	 Catch:{ IOException -> 0x020a }
        r1 = java.lang.String.format(r1, r5);	 Catch:{ IOException -> 0x020a }
        return r1;
    L_0x020a:
        r1 = 0;
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.drew.metadata.icc.IccDescriptor.getTagDataString(int):java.lang.String");
    }

    @NotNull
    public static String formatDoubleAsString(double d, int i, boolean z) {
        double d2 = d;
        int i2 = i;
        String str = "";
        Object obj = 1;
        if (i2 < 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(Math.round(d));
            return stringBuilder.toString();
        }
        long abs = Math.abs((long) d2);
        long round = (long) ((int) Math.round((Math.abs(d) - ((double) abs)) * Math.pow(10.0d, (double) i2)));
        String str2 = str;
        long j = round;
        while (i2 > 0) {
            byte abs2 = (byte) ((int) Math.abs(j % 10));
            j /= 10;
            if (str2.length() > 0 || z || abs2 != (byte) 0 || i2 == 1) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(abs2);
                stringBuilder2.append(str2);
                str2 = stringBuilder2.toString();
            }
            i2--;
        }
        abs += j;
        if (d2 >= 0.0d || (abs == 0 && round == 0)) {
            obj = null;
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        if (obj != null) {
            str = "-";
        }
        stringBuilder3.append(str);
        stringBuilder3.append(abs);
        stringBuilder3.append(".");
        stringBuilder3.append(str2);
        return stringBuilder3.toString();
    }

    @Nullable
    private String getRenderingIntentDescription() {
        return getIndexedDescription(64, "Perceptual", "Media-Relative Colorimetric", ExifInterface.TAG_SATURATION, "ICC-Absolute Colorimetric");
    }

    @Nullable
    private String getPlatformDescription() {
        String string = ((IccDirectory) this._directory).getString(40);
        if (string == null) {
            return null;
        }
        try {
            switch (getInt32FromString(string)) {
                case 1095782476:
                    string = "Apple Computer, Inc.";
                case 1297303124:
                    return "Microsoft Corporation";
                case 1397180704:
                    return "Silicon Graphics, Inc.";
                case 1398099543:
                    return "Sun Microsystems, Inc.";
                case 1413959252:
                    return "Taligent, Inc.";
                default:
                    return String.format("Unknown (%s)", new Object[]{string});
            }
        } catch (IOException unused) {
            return string;
        }
        return string;
    }

    @Nullable
    private String getProfileClassDescription() {
        String string = ((IccDirectory) this._directory).getString(12);
        if (string == null) {
            return null;
        }
        try {
            switch (getInt32FromString(string)) {
                case 1633842036:
                    string = "Abstract";
                case 1818848875:
                    return "DeviceLink";
                case 1835955314:
                    return "Display Device";
                case 1852662636:
                    return "Named Color";
                case 1886549106:
                    return "Output Device";
                case 1935896178:
                    return "Input Device";
                case 1936744803:
                    return "ColorSpace Conversion";
                default:
                    return String.format("Unknown (%s)", new Object[]{string});
            }
        } catch (IOException unused) {
            return string;
        }
        return string;
    }

    @Nullable
    private String getProfileVersionDescription() {
        Integer integer = ((IccDirectory) this._directory).getInteger(8);
        if (integer == null) {
            return null;
        }
        int intValue = (integer.intValue() & ViewCompat.MEASURED_STATE_MASK) >> 24;
        int intValue2 = (integer.intValue() & 15728640) >> 20;
        int intValue3 = (integer.intValue() & 983040) >> 16;
        return String.format("%d.%d.%d", new Object[]{Integer.valueOf(intValue), Integer.valueOf(intValue2), Integer.valueOf(intValue3)});
    }

    private static int getInt32FromString(@NotNull String str) throws IOException {
        return new ByteArrayReader(str.getBytes()).getInt32(0);
    }
}
