package com.google.android.gms.internal.firebase_ml;

public final class zzek extends zzfl {
    private final transient zzej zzsk;

    private zzek(zzfm zzfm, zzej zzej) {
        super(zzfm);
        this.zzsk = zzej;
    }

    public final zzej zzei() {
        return this.zzsk;
    }

    /* JADX WARNING: Removed duplicated region for block: B:67:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0082 A:{Catch:{ IOException -> 0x0080 }} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x007c A:{SYNTHETIC, Splitter: B:30:0x007c} */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00af A:{Catch:{ IOException -> 0x00b3 }} */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a9 A:{Catch:{ IOException -> 0x00b3 }} */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x009e A:{Catch:{ IOException -> 0x00b3 }} */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x009a A:{SYNTHETIC, Splitter: B:46:0x009a} */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a9 A:{Catch:{ IOException -> 0x00b3 }} */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00af A:{Catch:{ IOException -> 0x00b3 }} */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x009a A:{SYNTHETIC, Splitter: B:46:0x009a} */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x009e A:{Catch:{ IOException -> 0x00b3 }} */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00af A:{Catch:{ IOException -> 0x00b3 }} */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a9 A:{Catch:{ IOException -> 0x00b3 }} */
    public static com.google.android.gms.internal.firebase_ml.zzek zza(com.google.android.gms.internal.firebase_ml.zzge r5, com.google.android.gms.internal.firebase_ml.zzfk r6) {
        /*
        r0 = new com.google.android.gms.internal.firebase_ml.zzfm;
        r1 = r6.getStatusCode();
        r2 = r6.getStatusMessage();
        r3 = r6.zzfe();
        r0.<init>(r1, r2, r3);
        com.google.android.gms.internal.firebase_ml.zzks.checkNotNull(r5);
        r1 = 0;
        r2 = r6.zzfk();	 Catch:{ IOException -> 0x00bd }
        if (r2 != 0) goto L_0x00b7;
    L_0x001b:
        r2 = "application/json; charset=UTF-8";
        r3 = r6.getContentType();	 Catch:{ IOException -> 0x00bd }
        r2 = com.google.android.gms.internal.firebase_ml.zzfg.zzb(r2, r3);	 Catch:{ IOException -> 0x00bd }
        if (r2 == 0) goto L_0x00b7;
    L_0x0027:
        r2 = r6.getContent();	 Catch:{ IOException -> 0x00bd }
        if (r2 == 0) goto L_0x00b7;
    L_0x002d:
        r2 = r6.getContent();	 Catch:{ IOException -> 0x0092, all -> 0x008e }
        r5 = r5.zza(r2);	 Catch:{ IOException -> 0x0092, all -> 0x008e }
        r2 = r5.zzgi();	 Catch:{ IOException -> 0x008b, all -> 0x0088 }
        if (r2 != 0) goto L_0x003f;
    L_0x003b:
        r2 = r5.zzgh();	 Catch:{ IOException -> 0x008b, all -> 0x0088 }
    L_0x003f:
        if (r2 == 0) goto L_0x0079;
    L_0x0041:
        r2 = "error";
        r2 = java.util.Collections.singleton(r2);	 Catch:{ IOException -> 0x008b, all -> 0x0088 }
        r5.zza(r2);	 Catch:{ IOException -> 0x008b, all -> 0x0088 }
        r2 = r5.zzgi();	 Catch:{ IOException -> 0x008b, all -> 0x0088 }
        r3 = com.google.android.gms.internal.firebase_ml.zzgm.VALUE_STRING;	 Catch:{ IOException -> 0x008b, all -> 0x0088 }
        if (r2 != r3) goto L_0x0057;
    L_0x0052:
        r2 = r5.getText();	 Catch:{ IOException -> 0x008b, all -> 0x0088 }
        goto L_0x007a;
    L_0x0057:
        r2 = r5.zzgi();	 Catch:{ IOException -> 0x008b, all -> 0x0088 }
        r3 = com.google.android.gms.internal.firebase_ml.zzgm.START_OBJECT;	 Catch:{ IOException -> 0x008b, all -> 0x0088 }
        if (r2 != r3) goto L_0x0079;
    L_0x005f:
        r2 = com.google.android.gms.internal.firebase_ml.zzej.class;
        r2 = r5.zza(r2, r1);	 Catch:{ IOException -> 0x008b, all -> 0x0088 }
        r2 = (com.google.android.gms.internal.firebase_ml.zzej) r2;	 Catch:{ IOException -> 0x008b, all -> 0x0088 }
        r1 = r2.zzfx();	 Catch:{ IOException -> 0x0074, all -> 0x006f }
        r4 = r2;
        r2 = r1;
        r1 = r4;
        goto L_0x007a;
    L_0x006f:
        r3 = move-exception;
        r4 = r3;
        r3 = r2;
        r2 = r4;
        goto L_0x00a7;
    L_0x0074:
        r3 = move-exception;
        r4 = r3;
        r3 = r2;
        r2 = r4;
        goto L_0x0095;
    L_0x0079:
        r2 = r1;
    L_0x007a:
        if (r5 != 0) goto L_0x0082;
    L_0x007c:
        r6.ignore();	 Catch:{ IOException -> 0x0080 }
        goto L_0x00c2;
    L_0x0080:
        r5 = move-exception;
        goto L_0x00bf;
    L_0x0082:
        if (r1 != 0) goto L_0x00c2;
    L_0x0084:
        r5.close();	 Catch:{ IOException -> 0x0080 }
        goto L_0x00c2;
    L_0x0088:
        r2 = move-exception;
        r3 = r1;
        goto L_0x00a7;
    L_0x008b:
        r2 = move-exception;
        r3 = r1;
        goto L_0x0095;
    L_0x008e:
        r2 = move-exception;
        r5 = r1;
        r3 = r5;
        goto L_0x00a7;
    L_0x0092:
        r2 = move-exception;
        r5 = r1;
        r3 = r5;
    L_0x0095:
        com.google.android.gms.internal.firebase_ml.zzlx.zzb(r2);	 Catch:{ all -> 0x00a6 }
        if (r5 != 0) goto L_0x009e;
    L_0x009a:
        r6.ignore();	 Catch:{ IOException -> 0x00b3 }
        goto L_0x00a3;
    L_0x009e:
        if (r3 != 0) goto L_0x00a3;
    L_0x00a0:
        r5.close();	 Catch:{ IOException -> 0x00b3 }
    L_0x00a3:
        r2 = r1;
        r1 = r3;
        goto L_0x00c2;
    L_0x00a6:
        r2 = move-exception;
    L_0x00a7:
        if (r5 == 0) goto L_0x00af;
    L_0x00a9:
        if (r3 != 0) goto L_0x00b2;
    L_0x00ab:
        r5.close();	 Catch:{ IOException -> 0x00b3 }
        goto L_0x00b2;
    L_0x00af:
        r6.ignore();	 Catch:{ IOException -> 0x00b3 }
    L_0x00b2:
        throw r2;	 Catch:{ IOException -> 0x00b3 }
    L_0x00b3:
        r5 = move-exception;
        r2 = r1;
        r1 = r3;
        goto L_0x00bf;
    L_0x00b7:
        r5 = r6.zzfl();	 Catch:{ IOException -> 0x00bd }
        r2 = r5;
        goto L_0x00c2;
    L_0x00bd:
        r5 = move-exception;
        r2 = r1;
    L_0x00bf:
        com.google.android.gms.internal.firebase_ml.zzlx.zzb(r5);
    L_0x00c2:
        r5 = com.google.android.gms.internal.firebase_ml.zzfl.zzc(r6);
        r6 = com.google.android.gms.internal.firebase_ml.zzla.zzbb(r2);
        if (r6 != 0) goto L_0x00d7;
    L_0x00cc:
        r6 = com.google.android.gms.internal.firebase_ml.zzhz.zzaae;
        r5.append(r6);
        r5.append(r2);
        r0.zzai(r2);
    L_0x00d7:
        r5 = r5.toString();
        r0.zzah(r5);
        r5 = new com.google.android.gms.internal.firebase_ml.zzek;
        r5.<init>(r0, r1);
        return r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzek.zza(com.google.android.gms.internal.firebase_ml.zzge, com.google.android.gms.internal.firebase_ml.zzfk):com.google.android.gms.internal.firebase_ml.zzek");
    }
}
