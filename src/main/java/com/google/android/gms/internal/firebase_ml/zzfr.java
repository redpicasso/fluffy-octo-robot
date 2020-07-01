package com.google.android.gms.internal.firebase_ml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class zzfr {
    static final Map<Character, zzfs> zzvi = new HashMap();

    private static Map<String, Object> zzb(Object obj) {
        Map<String, Object> linkedHashMap = new LinkedHashMap();
        for (Entry entry : zzhf.zzf(obj).entrySet()) {
            Object value = entry.getValue();
            if (!(value == null || zzhf.isNull(value))) {
                linkedHashMap.put((String) entry.getKey(), value);
            }
        }
        return linkedHashMap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x006a  */
    public static java.lang.String zza(java.lang.String r12, java.lang.String r13, java.lang.Object r14, boolean r15) {
        /*
        r15 = "/";
        r15 = r13.startsWith(r15);
        if (r15 == 0) goto L_0x002f;
    L_0x0008:
        r15 = new com.google.android.gms.internal.firebase_ml.zzez;
        r15.<init>(r12);
        r12 = 0;
        r15.zzv(r12);
        r12 = r15.zzew();
        r12 = java.lang.String.valueOf(r12);
        r13 = java.lang.String.valueOf(r13);
        r15 = r13.length();
        if (r15 == 0) goto L_0x0028;
    L_0x0023:
        r12 = r12.concat(r13);
        goto L_0x0059;
    L_0x0028:
        r13 = new java.lang.String;
        r13.<init>(r12);
    L_0x002d:
        r12 = r13;
        goto L_0x0059;
    L_0x002f:
        r15 = "http://";
        r15 = r13.startsWith(r15);
        if (r15 != 0) goto L_0x002d;
    L_0x0037:
        r15 = "https://";
        r15 = r13.startsWith(r15);
        if (r15 == 0) goto L_0x0040;
    L_0x003f:
        goto L_0x002d;
    L_0x0040:
        r12 = java.lang.String.valueOf(r12);
        r13 = java.lang.String.valueOf(r13);
        r15 = r13.length();
        if (r15 == 0) goto L_0x0053;
    L_0x004e:
        r13 = r12.concat(r13);
        goto L_0x002d;
    L_0x0053:
        r13 = new java.lang.String;
        r13.<init>(r12);
        goto L_0x002d;
    L_0x0059:
        r13 = zzb(r14);
        r14 = new java.lang.StringBuilder;
        r14.<init>();
        r15 = r12.length();
        r0 = 0;
        r1 = 0;
    L_0x0068:
        if (r1 >= r15) goto L_0x0201;
    L_0x006a:
        r2 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        r2 = r12.indexOf(r2, r1);
        r3 = -1;
        if (r2 != r3) goto L_0x007c;
    L_0x0073:
        r12 = r12.substring(r1);
        r14.append(r12);
        goto L_0x0201;
    L_0x007c:
        r1 = r12.substring(r1, r2);
        r14.append(r1);
        r1 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        r3 = r2 + 2;
        r1 = r12.indexOf(r1, r3);
        r3 = r1 + 1;
        r2 = r2 + 1;
        r1 = r12.substring(r2, r1);
        r2 = zzvi;
        r4 = r1.charAt(r0);
        r4 = java.lang.Character.valueOf(r4);
        r2 = r2.get(r4);
        r2 = (com.google.android.gms.internal.firebase_ml.zzfs) r2;
        if (r2 != 0) goto L_0x00a7;
    L_0x00a5:
        r2 = com.google.android.gms.internal.firebase_ml.zzfs.SIMPLE;
    L_0x00a7:
        r4 = 44;
        r4 = com.google.android.gms.internal.firebase_ml.zzkc.zza(r4);
        r4 = com.google.android.gms.internal.firebase_ml.zzku.zza(r4);
        r1 = r4.zza(r1);
        r1 = r1.listIterator();
        r4 = 1;
        r5 = 1;
    L_0x00bb:
        r6 = r1.hasNext();
        if (r6 == 0) goto L_0x01fe;
    L_0x00c1:
        r6 = r1.next();
        r6 = (java.lang.String) r6;
        r7 = "*";
        r7 = r6.endsWith(r7);
        r8 = r1.nextIndex();
        if (r8 != r4) goto L_0x00d8;
    L_0x00d3:
        r8 = r2.zzfu();
        goto L_0x00d9;
    L_0x00d8:
        r8 = 0;
    L_0x00d9:
        r9 = r6.length();
        if (r7 == 0) goto L_0x00e1;
    L_0x00df:
        r9 = r9 + -1;
    L_0x00e1:
        r6 = r6.substring(r8, r9);
        r8 = r13.remove(r6);
        if (r8 == 0) goto L_0x00bb;
    L_0x00eb:
        if (r5 != 0) goto L_0x00f5;
    L_0x00ed:
        r9 = r2.zzfs();
        r14.append(r9);
        goto L_0x00fd;
    L_0x00f5:
        r5 = r2.zzfr();
        r14.append(r5);
        r5 = 0;
    L_0x00fd:
        r9 = r8 instanceof java.util.Iterator;
        if (r9 == 0) goto L_0x0109;
    L_0x0101:
        r8 = (java.util.Iterator) r8;
        r6 = zza(r6, r8, r7, r2);
        goto L_0x01f9;
    L_0x0109:
        r9 = r8 instanceof java.lang.Iterable;
        if (r9 != 0) goto L_0x01ed;
    L_0x010d:
        r9 = r8.getClass();
        r9 = r9.isArray();
        if (r9 == 0) goto L_0x0119;
    L_0x0117:
        goto L_0x01ed;
    L_0x0119:
        r9 = r8.getClass();
        r9 = r9.isEnum();
        r10 = "%s=%s";
        r11 = 2;
        if (r9 == 0) goto L_0x014e;
    L_0x0126:
        r7 = r8;
        r7 = (java.lang.Enum) r7;
        r7 = com.google.android.gms.internal.firebase_ml.zzhl.zza(r7);
        r7 = r7.getName();
        if (r7 == 0) goto L_0x014b;
    L_0x0133:
        r7 = r2.zzft();
        if (r7 == 0) goto L_0x0143;
    L_0x0139:
        r7 = new java.lang.Object[r11];
        r7[r0] = r6;
        r7[r4] = r8;
        r8 = java.lang.String.format(r10, r7);
    L_0x0143:
        r6 = r8.toString();
        r8 = com.google.android.gms.internal.firebase_ml.zzie.zzas(r6);
    L_0x014b:
        r6 = r8;
        goto L_0x01f9;
    L_0x014e:
        r9 = com.google.android.gms.internal.firebase_ml.zzhf.zzg(r8);
        if (r9 != 0) goto L_0x01c5;
    L_0x0154:
        r8 = zzb(r8);
        r9 = r8.isEmpty();
        if (r9 == 0) goto L_0x0162;
    L_0x015e:
        r6 = "";
        goto L_0x01f9;
    L_0x0162:
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "=";
        r11 = ",";
        if (r7 == 0) goto L_0x0172;
    L_0x016d:
        r11 = r2.zzfs();
        goto L_0x0183;
    L_0x0172:
        r7 = r2.zzft();
        if (r7 == 0) goto L_0x0182;
    L_0x0178:
        r6 = com.google.android.gms.internal.firebase_ml.zzie.zzas(r6);
        r9.append(r6);
        r9.append(r10);
    L_0x0182:
        r10 = r11;
    L_0x0183:
        r6 = r8.entrySet();
        r6 = r6.iterator();
    L_0x018b:
        r7 = r6.hasNext();
        if (r7 == 0) goto L_0x01c0;
    L_0x0191:
        r7 = r6.next();
        r7 = (java.util.Map.Entry) r7;
        r8 = r7.getKey();
        r8 = (java.lang.String) r8;
        r8 = r2.zzak(r8);
        r7 = r7.getValue();
        r7 = r7.toString();
        r7 = r2.zzak(r7);
        r9.append(r8);
        r9.append(r10);
        r9.append(r7);
        r7 = r6.hasNext();
        if (r7 == 0) goto L_0x018b;
    L_0x01bc:
        r9.append(r11);
        goto L_0x018b;
    L_0x01c0:
        r6 = r9.toString();
        goto L_0x01f9;
    L_0x01c5:
        r7 = r2.zzft();
        if (r7 == 0) goto L_0x01d5;
    L_0x01cb:
        r7 = new java.lang.Object[r11];
        r7[r0] = r6;
        r7[r4] = r8;
        r8 = java.lang.String.format(r10, r7);
    L_0x01d5:
        r6 = r2.zzfv();
        if (r6 == 0) goto L_0x01e4;
    L_0x01db:
        r6 = r8.toString();
        r6 = com.google.android.gms.internal.firebase_ml.zzie.zzat(r6);
        goto L_0x01f9;
    L_0x01e4:
        r6 = r8.toString();
        r6 = com.google.android.gms.internal.firebase_ml.zzie.zzas(r6);
        goto L_0x01f9;
    L_0x01ed:
        r8 = com.google.android.gms.internal.firebase_ml.zzia.zzi(r8);
        r8 = r8.iterator();
        r6 = zza(r6, r8, r7, r2);
    L_0x01f9:
        r14.append(r6);
        goto L_0x00bb;
    L_0x01fe:
        r1 = r3;
        goto L_0x0068;
    L_0x0201:
        r12 = r13.entrySet();
        com.google.android.gms.internal.firebase_ml.zzez.zza(r12, r14);
        r12 = r14.toString();
        return r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzfr.zza(java.lang.String, java.lang.String, java.lang.Object, boolean):java.lang.String");
    }

    private static String zza(String str, Iterator<?> it, boolean z, zzfs zzfs) {
        if (!it.hasNext()) {
            return "";
        }
        String zzfs2;
        StringBuilder stringBuilder = new StringBuilder();
        String str2 = "=";
        if (z) {
            zzfs2 = zzfs.zzfs();
        } else {
            if (zzfs.zzft()) {
                stringBuilder.append(zzie.zzas(str));
                stringBuilder.append(str2);
            }
            zzfs2 = ",";
        }
        while (it.hasNext()) {
            if (z && zzfs.zzft()) {
                stringBuilder.append(zzie.zzas(str));
                stringBuilder.append(str2);
            }
            stringBuilder.append(zzfs.zzak(it.next().toString()));
            if (it.hasNext()) {
                stringBuilder.append(zzfs2);
            }
        }
        return stringBuilder.toString();
    }

    static {
        zzfs.values();
    }
}
