package com.adobe.xmp.impl;

import com.adobe.xmp.XMPDateTime;
import com.adobe.xmp.XMPException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class ISO8601Converter {
    private ISO8601Converter() {
    }

    public static XMPDateTime parse(String str) throws XMPException {
        return parse(str, new XMPDateTimeImpl());
    }

    /* JADX WARNING: Removed duplicated region for block: B:128:0x021b  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x021a A:{RETURN} */
    public static com.adobe.xmp.XMPDateTime parse(java.lang.String r11, com.adobe.xmp.XMPDateTime r12) throws com.adobe.xmp.XMPException {
        /*
        if (r11 == 0) goto L_0x0223;
    L_0x0002:
        r0 = r11.length();
        if (r0 != 0) goto L_0x0009;
    L_0x0008:
        return r12;
    L_0x0009:
        r0 = new com.adobe.xmp.impl.ParseState;
        r0.<init>(r11);
        r11 = 0;
        r1 = r0.ch(r11);
        r2 = 45;
        if (r1 != r2) goto L_0x001a;
    L_0x0017:
        r0.skip();
    L_0x001a:
        r1 = 9999; // 0x270f float:1.4012E-41 double:4.94E-320;
        r3 = "Invalid year in date string";
        r1 = r0.gatherInt(r3, r1);
        r3 = r0.hasNext();
        r4 = 5;
        if (r3 == 0) goto L_0x0038;
    L_0x0029:
        r3 = r0.ch();
        if (r3 != r2) goto L_0x0030;
    L_0x002f:
        goto L_0x0038;
    L_0x0030:
        r11 = new com.adobe.xmp.XMPException;
        r12 = "Invalid date string, after year";
        r11.<init>(r12, r4);
        throw r11;
    L_0x0038:
        r3 = r0.ch(r11);
        if (r3 != r2) goto L_0x003f;
    L_0x003e:
        r1 = -r1;
    L_0x003f:
        r12.setYear(r1);
        r1 = r0.hasNext();
        if (r1 != 0) goto L_0x0049;
    L_0x0048:
        return r12;
    L_0x0049:
        r0.skip();
        r1 = 12;
        r3 = "Invalid month in date string";
        r1 = r0.gatherInt(r3, r1);
        r3 = r0.hasNext();
        if (r3 == 0) goto L_0x0069;
    L_0x005a:
        r3 = r0.ch();
        if (r3 != r2) goto L_0x0061;
    L_0x0060:
        goto L_0x0069;
    L_0x0061:
        r11 = new com.adobe.xmp.XMPException;
        r12 = "Invalid date string, after month";
        r11.<init>(r12, r4);
        throw r11;
    L_0x0069:
        r12.setMonth(r1);
        r1 = r0.hasNext();
        if (r1 != 0) goto L_0x0073;
    L_0x0072:
        return r12;
    L_0x0073:
        r0.skip();
        r1 = 31;
        r3 = "Invalid day in date string";
        r1 = r0.gatherInt(r3, r1);
        r3 = r0.hasNext();
        if (r3 == 0) goto L_0x0095;
    L_0x0084:
        r3 = r0.ch();
        r5 = 84;
        if (r3 != r5) goto L_0x008d;
    L_0x008c:
        goto L_0x0095;
    L_0x008d:
        r11 = new com.adobe.xmp.XMPException;
        r12 = "Invalid date string, after day";
        r11.<init>(r12, r4);
        throw r11;
    L_0x0095:
        r12.setDay(r1);
        r1 = r0.hasNext();
        if (r1 != 0) goto L_0x009f;
    L_0x009e:
        return r12;
    L_0x009f:
        r0.skip();
        r1 = 23;
        r3 = "Invalid hour in date string";
        r3 = r0.gatherInt(r3, r1);
        r12.setHour(r3);
        r3 = r0.hasNext();
        if (r3 != 0) goto L_0x00b4;
    L_0x00b3:
        return r12;
    L_0x00b4:
        r3 = r0.ch();
        r5 = 59;
        r6 = 58;
        r7 = 43;
        r8 = 90;
        if (r3 != r6) goto L_0x00f5;
    L_0x00c2:
        r0.skip();
        r3 = "Invalid minute in date string";
        r3 = r0.gatherInt(r3, r5);
        r9 = r0.hasNext();
        if (r9 == 0) goto L_0x00f2;
    L_0x00d1:
        r9 = r0.ch();
        if (r9 == r6) goto L_0x00f2;
    L_0x00d7:
        r9 = r0.ch();
        if (r9 == r8) goto L_0x00f2;
    L_0x00dd:
        r9 = r0.ch();
        if (r9 == r7) goto L_0x00f2;
    L_0x00e3:
        r9 = r0.ch();
        if (r9 != r2) goto L_0x00ea;
    L_0x00e9:
        goto L_0x00f2;
    L_0x00ea:
        r11 = new com.adobe.xmp.XMPException;
        r12 = "Invalid date string, after minute";
        r11.<init>(r12, r4);
        throw r11;
    L_0x00f2:
        r12.setMinute(r3);
    L_0x00f5:
        r3 = r0.hasNext();
        if (r3 != 0) goto L_0x00fc;
    L_0x00fb:
        return r12;
    L_0x00fc:
        r3 = r0.hasNext();
        if (r3 == 0) goto L_0x018d;
    L_0x0102:
        r3 = r0.ch();
        if (r3 != r6) goto L_0x018d;
    L_0x0108:
        r0.skip();
        r3 = "Invalid whole seconds in date string";
        r3 = r0.gatherInt(r3, r5);
        r9 = r0.hasNext();
        r10 = 46;
        if (r9 == 0) goto L_0x013a;
    L_0x0119:
        r9 = r0.ch();
        if (r9 == r10) goto L_0x013a;
    L_0x011f:
        r9 = r0.ch();
        if (r9 == r8) goto L_0x013a;
    L_0x0125:
        r9 = r0.ch();
        if (r9 == r7) goto L_0x013a;
    L_0x012b:
        r9 = r0.ch();
        if (r9 != r2) goto L_0x0132;
    L_0x0131:
        goto L_0x013a;
    L_0x0132:
        r11 = new com.adobe.xmp.XMPException;
        r12 = "Invalid date string, after whole seconds";
        r11.<init>(r12, r4);
        throw r11;
    L_0x013a:
        r12.setSecond(r3);
        r3 = r0.ch();
        if (r3 != r10) goto L_0x01a8;
    L_0x0143:
        r0.skip();
        r3 = r0.pos();
        r9 = 999999999; // 0x3b9ac9ff float:0.004723787 double:4.940656453E-315;
        r10 = "Invalid fractional seconds in date string";
        r9 = r0.gatherInt(r10, r9);
        r10 = r0.hasNext();
        if (r10 == 0) goto L_0x0174;
    L_0x0159:
        r10 = r0.ch();
        if (r10 == r8) goto L_0x0174;
    L_0x015f:
        r10 = r0.ch();
        if (r10 == r7) goto L_0x0174;
    L_0x0165:
        r10 = r0.ch();
        if (r10 != r2) goto L_0x016c;
    L_0x016b:
        goto L_0x0174;
    L_0x016c:
        r11 = new com.adobe.xmp.XMPException;
        r12 = "Invalid date string, after fractional second";
        r11.<init>(r12, r4);
        throw r11;
    L_0x0174:
        r10 = r0.pos();
        r10 = r10 - r3;
    L_0x0179:
        r3 = 9;
        if (r10 <= r3) goto L_0x0182;
    L_0x017d:
        r9 = r9 / 10;
        r10 = r10 + -1;
        goto L_0x0179;
    L_0x0182:
        if (r10 >= r3) goto L_0x0189;
    L_0x0184:
        r9 = r9 * 10;
        r10 = r10 + 1;
        goto L_0x0182;
    L_0x0189:
        r12.setNanoSecond(r9);
        goto L_0x01a8;
    L_0x018d:
        r3 = r0.ch();
        if (r3 == r8) goto L_0x01a8;
    L_0x0193:
        r3 = r0.ch();
        if (r3 == r7) goto L_0x01a8;
    L_0x0199:
        r3 = r0.ch();
        if (r3 != r2) goto L_0x01a0;
    L_0x019f:
        goto L_0x01a8;
    L_0x01a0:
        r11 = new com.adobe.xmp.XMPException;
        r12 = "Invalid date string, after time";
        r11.<init>(r12, r4);
        throw r11;
    L_0x01a8:
        r3 = r0.hasNext();
        if (r3 != 0) goto L_0x01af;
    L_0x01ae:
        return r12;
    L_0x01af:
        r3 = r0.ch();
        if (r3 != r8) goto L_0x01b9;
    L_0x01b5:
        r0.skip();
        goto L_0x01fd;
    L_0x01b9:
        r3 = r0.hasNext();
        if (r3 == 0) goto L_0x01fd;
    L_0x01bf:
        r3 = r0.ch();
        if (r3 != r7) goto L_0x01c7;
    L_0x01c5:
        r2 = 1;
        goto L_0x01ce;
    L_0x01c7:
        r3 = r0.ch();
        if (r3 != r2) goto L_0x01f5;
    L_0x01cd:
        r2 = -1;
    L_0x01ce:
        r0.skip();
        r3 = "Invalid time zone hour in date string";
        r1 = r0.gatherInt(r3, r1);
        r3 = r0.hasNext();
        if (r3 == 0) goto L_0x01ff;
    L_0x01dd:
        r11 = r0.ch();
        if (r11 != r6) goto L_0x01ed;
    L_0x01e3:
        r0.skip();
        r11 = "Invalid time zone minute in date string";
        r11 = r0.gatherInt(r11, r5);
        goto L_0x01ff;
    L_0x01ed:
        r11 = new com.adobe.xmp.XMPException;
        r12 = "Invalid date string, after time zone hour";
        r11.<init>(r12, r4);
        throw r11;
    L_0x01f5:
        r11 = new com.adobe.xmp.XMPException;
        r12 = "Time zone must begin with 'Z', '+', or '-'";
        r11.<init>(r12, r4);
        throw r11;
    L_0x01fd:
        r1 = 0;
        r2 = 0;
    L_0x01ff:
        r1 = r1 * 3600;
        r1 = r1 * 1000;
        r11 = r11 * 60;
        r11 = r11 * 1000;
        r1 = r1 + r11;
        r1 = r1 * r2;
        r11 = new java.util.SimpleTimeZone;
        r2 = "";
        r11.<init>(r1, r2);
        r12.setTimeZone(r11);
        r11 = r0.hasNext();
        if (r11 != 0) goto L_0x021b;
    L_0x021a:
        return r12;
    L_0x021b:
        r11 = new com.adobe.xmp.XMPException;
        r12 = "Invalid date string, extra chars at end";
        r11.<init>(r12, r4);
        throw r11;
    L_0x0223:
        r11 = new com.adobe.xmp.XMPException;
        r12 = 4;
        r0 = "Parameter must not be null";
        r11.<init>(r0, r12);
        throw r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.adobe.xmp.impl.ISO8601Converter.parse(java.lang.String, com.adobe.xmp.XMPDateTime):com.adobe.xmp.XMPDateTime");
    }

    public static String render(XMPDateTime xMPDateTime) {
        StringBuffer stringBuffer = new StringBuffer();
        if (xMPDateTime.hasDate()) {
            DecimalFormat decimalFormat = new DecimalFormat("0000", new DecimalFormatSymbols(Locale.ENGLISH));
            stringBuffer.append(decimalFormat.format((long) xMPDateTime.getYear()));
            if (xMPDateTime.getMonth() == 0) {
                return stringBuffer.toString();
            }
            decimalFormat.applyPattern("'-'00");
            stringBuffer.append(decimalFormat.format((long) xMPDateTime.getMonth()));
            if (xMPDateTime.getDay() == 0) {
                return stringBuffer.toString();
            }
            stringBuffer.append(decimalFormat.format((long) xMPDateTime.getDay()));
            if (xMPDateTime.hasTime()) {
                stringBuffer.append('T');
                decimalFormat.applyPattern("00");
                stringBuffer.append(decimalFormat.format((long) xMPDateTime.getHour()));
                stringBuffer.append(':');
                stringBuffer.append(decimalFormat.format((long) xMPDateTime.getMinute()));
                if (!(xMPDateTime.getSecond() == 0 && xMPDateTime.getNanoSecond() == 0)) {
                    double second = ((double) xMPDateTime.getSecond()) + (((double) xMPDateTime.getNanoSecond()) / 1.0E9d);
                    decimalFormat.applyPattern(":00.#########");
                    stringBuffer.append(decimalFormat.format(second));
                }
                if (xMPDateTime.hasTimeZone()) {
                    int offset = xMPDateTime.getTimeZone().getOffset(xMPDateTime.getCalendar().getTimeInMillis());
                    if (offset == 0) {
                        stringBuffer.append('Z');
                    } else {
                        int i = offset / 3600000;
                        offset = Math.abs((offset % 3600000) / 60000);
                        decimalFormat.applyPattern("+00;-00");
                        stringBuffer.append(decimalFormat.format((long) i));
                        decimalFormat.applyPattern(":00");
                        stringBuffer.append(decimalFormat.format((long) offset));
                    }
                }
            }
        }
        return stringBuffer.toString();
    }
}
