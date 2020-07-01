package com.google.android.gms.internal.firebase_ml;

import com.drew.metadata.photoshop.PhotoshopDirectory;

public final class zzfh {
    private zzhu zzsr;
    private final zzfo zzsu;
    private String zzsx;
    private zzfb zztu;
    private zzfd zzug;
    private zzfe zzuh = new zzfe();
    private zzfe zzui = new zzfe();
    private int zzuj = 10;
    private int zzuk = 16384;
    private boolean zzul = true;
    private boolean zzum = true;
    private zzfa zzun;
    private zzez zzuo;
    private int zzup = 20000;
    private int zzuq = 20000;
    private zzfn zzur;
    private boolean zzus = true;
    private boolean zzut = true;
    @Deprecated
    private boolean zzuu = false;
    private zzhw zzuv = zzhw.zzaad;

    zzfh(zzfo zzfo, String str) {
        this.zzsu = zzfo;
        zzag(null);
    }

    public final zzfo zzez() {
        return this.zzsu;
    }

    public final String getRequestMethod() {
        return this.zzsx;
    }

    public final zzfh zzag(String str) {
        boolean z = str == null || zzfg.zzaf(str);
        zzks.checkArgument(z);
        this.zzsx = str;
        return this;
    }

    public final zzez zzfa() {
        return this.zzuo;
    }

    public final zzfh zza(zzez zzez) {
        this.zzuo = (zzez) zzks.checkNotNull(zzez);
        return this;
    }

    public final zzfa zzfb() {
        return this.zzun;
    }

    public final zzfh zza(zzfa zzfa) {
        this.zzun = zzfa;
        return this;
    }

    public final zzfh zza(zzfb zzfb) {
        this.zztu = zzfb;
        return this;
    }

    public final int zzfc() {
        return this.zzuk;
    }

    public final boolean zzfd() {
        return this.zzul;
    }

    public final zzfh zzz(int i) {
        zzks.checkArgument(true);
        this.zzup = 5000;
        return this;
    }

    public final zzfh zzaa(int i) {
        zzks.checkArgument(true);
        this.zzuq = PhotoshopDirectory.TAG_PRINT_FLAGS_INFO;
        return this;
    }

    public final zzfe zzfe() {
        return this.zzuh;
    }

    public final zzfe zzff() {
        return this.zzui;
    }

    public final zzfh zza(zzfd zzfd) {
        this.zzug = zzfd;
        return this;
    }

    public final zzfn zzfg() {
        return this.zzur;
    }

    public final zzfh zza(zzfn zzfn) {
        this.zzur = zzfn;
        return this;
    }

    public final zzfh zza(zzhu zzhu) {
        this.zzsr = zzhu;
        return this;
    }

    public final zzhu zzfh() {
        return this.zzsr;
    }

    public final boolean zzfi() {
        return this.zzut;
    }

    /* JADX WARNING: Removed duplicated region for block: B:105:0x0282 A:{Catch:{ all -> 0x02ad }} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01d0  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x01d6  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x020a  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0208  */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0286  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0223 A:{Catch:{ all -> 0x02ad }} */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x02aa A:{LOOP_END, LOOP:0: B:5:0x001a->B:123:0x02aa} */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x028c A:{SYNTHETIC} */
    public final com.google.android.gms.internal.firebase_ml.zzfk zzfj() throws java.io.IOException {
        /*
        r20 = this;
        r1 = r20;
        r0 = r1.zzuj;
        if (r0 < 0) goto L_0x0008;
    L_0x0006:
        r0 = 1;
        goto L_0x0009;
    L_0x0008:
        r0 = 0;
    L_0x0009:
        com.google.android.gms.internal.firebase_ml.zzks.checkArgument(r0);
        r0 = r1.zzuj;
        r4 = r1.zzsx;
        com.google.android.gms.internal.firebase_ml.zzks.checkNotNull(r4);
        r4 = r1.zzuo;
        com.google.android.gms.internal.firebase_ml.zzks.checkNotNull(r4);
        r5 = r0;
        r0 = 0;
    L_0x001a:
        if (r0 == 0) goto L_0x001f;
    L_0x001c:
        r0.ignore();
    L_0x001f:
        r0 = r1.zzug;
        if (r0 == 0) goto L_0x0026;
    L_0x0023:
        r0.zzb(r1);
    L_0x0026:
        r0 = r1.zzuo;
        r0 = r0.zzew();
        r6 = r1.zzsu;
        r7 = r1.zzsx;
        r6 = r6.zzc(r7, r0);
        r7 = com.google.android.gms.internal.firebase_ml.zzfo.zzve;
        r8 = r1.zzul;
        if (r8 == 0) goto L_0x0044;
    L_0x003a:
        r8 = java.util.logging.Level.CONFIG;
        r8 = r7.isLoggable(r8);
        if (r8 == 0) goto L_0x0044;
    L_0x0042:
        r8 = 1;
        goto L_0x0045;
    L_0x0044:
        r8 = 0;
    L_0x0045:
        r9 = "GET";
        if (r8 == 0) goto L_0x0088;
    L_0x0049:
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "-------------- REQUEST  --------------";
        r10.append(r11);
        r11 = com.google.android.gms.internal.firebase_ml.zzhz.zzaae;
        r10.append(r11);
        r11 = r1.zzsx;
        r10.append(r11);
        r11 = 32;
        r10.append(r11);
        r10.append(r0);
        r11 = com.google.android.gms.internal.firebase_ml.zzhz.zzaae;
        r10.append(r11);
        r11 = r1.zzum;
        if (r11 == 0) goto L_0x0089;
    L_0x006e:
        r11 = new java.lang.StringBuilder;
        r12 = "curl -v --compressed";
        r11.<init>(r12);
        r12 = r1.zzsx;
        r12 = r12.equals(r9);
        if (r12 != 0) goto L_0x008a;
    L_0x007d:
        r12 = " -X ";
        r11.append(r12);
        r12 = r1.zzsx;
        r11.append(r12);
        goto L_0x008a;
    L_0x0088:
        r10 = 0;
    L_0x0089:
        r11 = 0;
    L_0x008a:
        r12 = r1.zzuh;
        r12 = r12.zzex();
        if (r12 != 0) goto L_0x009a;
    L_0x0092:
        r13 = r1.zzuh;
        r14 = "Google-HTTP-Java-Client/1.26.0-SNAPSHOT (gzip)";
        r13.zzae(r14);
        goto L_0x00ba;
    L_0x009a:
        r13 = r1.zzuh;
        r14 = java.lang.String.valueOf(r12);
        r14 = r14.length();
        r14 = r14 + 47;
        r15 = new java.lang.StringBuilder;
        r15.<init>(r14);
        r15.append(r12);
        r14 = " Google-HTTP-Java-Client/1.26.0-SNAPSHOT (gzip)";
        r15.append(r14);
        r14 = r15.toString();
        r13.zzae(r14);
    L_0x00ba:
        r13 = r1.zzuh;
        com.google.android.gms.internal.firebase_ml.zzfe.zza(r13, r10, r11, r7, r6);
        r13 = r1.zzuh;
        r13.zzae(r12);
        r12 = r1.zzun;
        if (r12 == 0) goto L_0x00cb;
    L_0x00c8:
        r12.zzev();
    L_0x00cb:
        r13 = "'";
        if (r12 == 0) goto L_0x01d0;
    L_0x00cf:
        r14 = r1.zzun;
        r14 = r14.getType();
        if (r8 == 0) goto L_0x00e3;
    L_0x00d7:
        r15 = new com.google.android.gms.internal.firebase_ml.zzhs;
        r2 = com.google.android.gms.internal.firebase_ml.zzfo.zzve;
        r3 = java.util.logging.Level.CONFIG;
        r4 = r1.zzuk;
        r15.<init>(r12, r2, r3, r4);
        goto L_0x00e4;
    L_0x00e3:
        r15 = r12;
    L_0x00e4:
        r2 = r1.zztu;
        if (r2 != 0) goto L_0x00f1;
    L_0x00e8:
        r2 = r1.zzun;
        r2 = r2.getLength();
        r12 = r15;
        r4 = 0;
        goto L_0x0103;
    L_0x00f1:
        r4 = r2.getName();
        r2 = new com.google.android.gms.internal.firebase_ml.zzfc;
        r3 = r1.zztu;
        r2.<init>(r15, r3);
        r16 = com.google.android.gms.internal.firebase_ml.zzhn.zzb(r2);
        r12 = r2;
        r2 = r16;
    L_0x0103:
        if (r8 == 0) goto L_0x01b8;
    L_0x0105:
        r15 = " -H '";
        if (r14 == 0) goto L_0x0150;
    L_0x0109:
        r16 = r9;
        r9 = "Content-Type: ";
        r1 = java.lang.String.valueOf(r14);
        r17 = r1.length();
        if (r17 == 0) goto L_0x011c;
    L_0x0117:
        r1 = r9.concat(r1);
        goto L_0x0121;
    L_0x011c:
        r1 = new java.lang.String;
        r1.<init>(r9);
    L_0x0121:
        r10.append(r1);
        r9 = com.google.android.gms.internal.firebase_ml.zzhz.zzaae;
        r10.append(r9);
        if (r11 == 0) goto L_0x014d;
    L_0x012b:
        r9 = java.lang.String.valueOf(r1);
        r9 = r9.length();
        r9 = r9 + 6;
        r17 = r5;
        r5 = new java.lang.StringBuilder;
        r5.<init>(r9);
        r5.append(r15);
        r5.append(r1);
        r5.append(r13);
        r1 = r5.toString();
        r11.append(r1);
        goto L_0x0154;
    L_0x014d:
        r17 = r5;
        goto L_0x0154;
    L_0x0150:
        r17 = r5;
        r16 = r9;
    L_0x0154:
        if (r4 == 0) goto L_0x0196;
    L_0x0156:
        r1 = "Content-Encoding: ";
        r5 = java.lang.String.valueOf(r4);
        r9 = r5.length();
        if (r9 == 0) goto L_0x0167;
    L_0x0162:
        r1 = r1.concat(r5);
        goto L_0x016d;
    L_0x0167:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5;
    L_0x016d:
        r10.append(r1);
        r5 = com.google.android.gms.internal.firebase_ml.zzhz.zzaae;
        r10.append(r5);
        if (r11 == 0) goto L_0x0196;
    L_0x0177:
        r5 = java.lang.String.valueOf(r1);
        r5 = r5.length();
        r5 = r5 + 6;
        r9 = new java.lang.StringBuilder;
        r9.<init>(r5);
        r9.append(r15);
        r9.append(r1);
        r9.append(r13);
        r1 = r9.toString();
        r11.append(r1);
    L_0x0196:
        r18 = 0;
        r1 = (r2 > r18 ? 1 : (r2 == r18 ? 0 : -1));
        if (r1 < 0) goto L_0x01bc;
    L_0x019c:
        r1 = 36;
        r5 = new java.lang.StringBuilder;
        r5.<init>(r1);
        r1 = "Content-Length: ";
        r5.append(r1);
        r5.append(r2);
        r1 = r5.toString();
        r10.append(r1);
        r1 = com.google.android.gms.internal.firebase_ml.zzhz.zzaae;
        r10.append(r1);
        goto L_0x01bc;
    L_0x01b8:
        r17 = r5;
        r16 = r9;
    L_0x01bc:
        if (r11 == 0) goto L_0x01c3;
    L_0x01be:
        r1 = " -d '@-'";
        r11.append(r1);
    L_0x01c3:
        r6.setContentType(r14);
        r6.setContentEncoding(r4);
        r6.setContentLength(r2);
        r6.zza(r12);
        goto L_0x01d4;
    L_0x01d0:
        r17 = r5;
        r16 = r9;
    L_0x01d4:
        if (r8 == 0) goto L_0x0206;
    L_0x01d6:
        r1 = java.util.logging.Level.CONFIG;
        r2 = r10.toString();
        r3 = "execute";
        r4 = "com.google.api.client.http.HttpRequest";
        r7.logp(r1, r4, r3, r2);
        if (r11 == 0) goto L_0x0206;
    L_0x01e5:
        r1 = " -- '";
        r11.append(r1);
        r1 = "'\"'\"'";
        r0 = r0.replaceAll(r13, r1);
        r11.append(r0);
        r11.append(r13);
        if (r12 == 0) goto L_0x01fd;
    L_0x01f8:
        r0 = " << $$$";
        r11.append(r0);
    L_0x01fd:
        r0 = java.util.logging.Level.CONFIG;
        r1 = r11.toString();
        r7.logp(r0, r4, r3, r1);
    L_0x0206:
        if (r17 <= 0) goto L_0x020a;
    L_0x0208:
        r0 = 1;
        goto L_0x020b;
    L_0x020a:
        r0 = 0;
    L_0x020b:
        r1 = r20;
        r2 = r1.zzup;
        r3 = r1.zzuq;
        r6.zza(r2, r3);
        r2 = r6.zzfo();	 Catch:{ IOException -> 0x02bd }
        r3 = new com.google.android.gms.internal.firebase_ml.zzfk;	 Catch:{ all -> 0x02b2 }
        r3.<init>(r1, r2);	 Catch:{ all -> 0x02b2 }
        r2 = r3.zzfk();	 Catch:{ all -> 0x02ad }
        if (r2 != 0) goto L_0x0286;
    L_0x0223:
        r2 = r3.getStatusCode();	 Catch:{ all -> 0x02ad }
        r4 = r3.zzfe();	 Catch:{ all -> 0x02ad }
        r4 = r4.getLocation();	 Catch:{ all -> 0x02ad }
        r5 = r1.zzus;	 Catch:{ all -> 0x02ad }
        if (r5 == 0) goto L_0x027d;
    L_0x0233:
        r5 = 307; // 0x133 float:4.3E-43 double:1.517E-321;
        if (r2 == r5) goto L_0x023c;
    L_0x0237:
        switch(r2) {
            case 301: goto L_0x023c;
            case 302: goto L_0x023c;
            case 303: goto L_0x023c;
            default: goto L_0x023a;
        };	 Catch:{ all -> 0x02ad }
    L_0x023a:
        r5 = 0;
        goto L_0x023d;
    L_0x023c:
        r5 = 1;
    L_0x023d:
        if (r5 == 0) goto L_0x027d;
    L_0x023f:
        if (r4 == 0) goto L_0x027d;
    L_0x0241:
        r5 = new com.google.android.gms.internal.firebase_ml.zzez;	 Catch:{ all -> 0x02ad }
        r6 = r1.zzuo;	 Catch:{ all -> 0x02ad }
        r4 = r6.zzu(r4);	 Catch:{ all -> 0x02ad }
        r5.<init>(r4);	 Catch:{ all -> 0x02ad }
        r1.zza(r5);	 Catch:{ all -> 0x02ad }
        r4 = 303; // 0x12f float:4.25E-43 double:1.497E-321;
        if (r2 != r4) goto L_0x025c;
    L_0x0253:
        r2 = r16;
        r1.zzag(r2);	 Catch:{ all -> 0x02ad }
        r2 = 0;
        r1.zzun = r2;	 Catch:{ all -> 0x02ad }
        goto L_0x025d;
    L_0x025c:
        r2 = 0;
    L_0x025d:
        r4 = r1.zzuh;	 Catch:{ all -> 0x02ad }
        r4.zzy(r2);	 Catch:{ all -> 0x02ad }
        r4 = r1.zzuh;	 Catch:{ all -> 0x02ad }
        r4.zzaa(r2);	 Catch:{ all -> 0x02ad }
        r4 = r1.zzuh;	 Catch:{ all -> 0x02ad }
        r4.zzab(r2);	 Catch:{ all -> 0x02ad }
        r4 = r1.zzuh;	 Catch:{ all -> 0x02ad }
        r4.zzz(r2);	 Catch:{ all -> 0x02ad }
        r4 = r1.zzuh;	 Catch:{ all -> 0x02ad }
        r4.zzac(r2);	 Catch:{ all -> 0x02ad }
        r4 = r1.zzuh;	 Catch:{ all -> 0x02ad }
        r4.zzad(r2);	 Catch:{ all -> 0x02ad }
        r4 = 1;
        goto L_0x027f;
    L_0x027d:
        r2 = 0;
        r4 = 0;
    L_0x027f:
        r0 = r0 & r4;
        if (r0 == 0) goto L_0x0288;
    L_0x0282:
        r3.ignore();	 Catch:{ all -> 0x02ad }
        goto L_0x0288;
    L_0x0286:
        r2 = 0;
        r0 = 0;
    L_0x0288:
        r5 = r17 + -1;
        if (r0 != 0) goto L_0x02aa;
    L_0x028c:
        r0 = r1.zzur;
        if (r0 == 0) goto L_0x0293;
    L_0x0290:
        r0.zzb(r3);
    L_0x0293:
        r0 = r1.zzut;
        if (r0 == 0) goto L_0x02a9;
    L_0x0297:
        r0 = r3.zzfk();
        if (r0 == 0) goto L_0x029e;
    L_0x029d:
        goto L_0x02a9;
    L_0x029e:
        r0 = new com.google.android.gms.internal.firebase_ml.zzfl;	 Catch:{ all -> 0x02a4 }
        r0.<init>(r3);	 Catch:{ all -> 0x02a4 }
        throw r0;	 Catch:{ all -> 0x02a4 }
    L_0x02a4:
        r0 = move-exception;
        r3.disconnect();
        throw r0;
    L_0x02a9:
        return r3;
    L_0x02aa:
        r0 = r3;
        goto L_0x001a;
    L_0x02ad:
        r0 = move-exception;
        r3.disconnect();
        throw r0;
    L_0x02b2:
        r0 = move-exception;
        r2 = r2.getContent();	 Catch:{ IOException -> 0x02bd }
        if (r2 == 0) goto L_0x02bc;
    L_0x02b9:
        r2.close();	 Catch:{ IOException -> 0x02bd }
    L_0x02bc:
        throw r0;	 Catch:{ IOException -> 0x02bd }
    L_0x02bd:
        r0 = move-exception;
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzfh.zzfj():com.google.android.gms.internal.firebase_ml.zzfk");
    }
}
