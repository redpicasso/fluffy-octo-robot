package com.google.firebase.iid;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
final /* synthetic */ class zzaa implements Runnable {
    private final zzw zza;

    zzaa(zzw zzw) {
        this.zza = zzw;
    }

    /* JADX WARNING: Missing block: B:15:0x0040, code:
            if (android.util.Log.isLoggable("MessengerIpcClient", 3) == false) goto L_0x0066;
     */
    /* JADX WARNING: Missing block: B:16:0x0042, code:
            r3 = java.lang.String.valueOf(r1);
            r5 = new java.lang.StringBuilder(java.lang.String.valueOf(r3).length() + 8);
            r5.append("Sending ");
            r5.append(r3);
            android.util.Log.d("MessengerIpcClient", r5.toString());
     */
    /* JADX WARNING: Missing block: B:17:0x0066, code:
            r3 = com.google.firebase.iid.zzv.zza(r0.zzf);
            r4 = r0.zzb;
            r5 = android.os.Message.obtain();
            r5.what = r1.zzc;
            r5.arg1 = r1.zza;
            r5.replyTo = r4;
            r4 = new android.os.Bundle();
            r4.putBoolean("oneWay", r1.zza());
            r4.putString("pkg", r3.getPackageName());
            r4.putBundle("data", r1.zzd);
            r5.setData(r4);
     */
    /* JADX WARNING: Missing block: B:19:?, code:
            r0.zzc.zza(r5);
     */
    /* JADX WARNING: Missing block: B:21:0x00a4, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:22:0x00a5, code:
            r0.zza(2, r1.getMessage());
     */
    public final void run() {
        /*
        r8 = this;
        r0 = r8.zza;
    L_0x0002:
        monitor-enter(r0);
        r1 = r0.zza;	 Catch:{ all -> 0x00ae }
        r2 = 2;
        if (r1 == r2) goto L_0x000a;
    L_0x0008:
        monitor-exit(r0);	 Catch:{ all -> 0x00ae }
        return;
    L_0x000a:
        r1 = r0.zzd;	 Catch:{ all -> 0x00ae }
        r1 = r1.isEmpty();	 Catch:{ all -> 0x00ae }
        if (r1 == 0) goto L_0x0017;
    L_0x0012:
        r0.zzb();	 Catch:{ all -> 0x00ae }
        monitor-exit(r0);	 Catch:{ all -> 0x00ae }
        return;
    L_0x0017:
        r1 = r0.zzd;	 Catch:{ all -> 0x00ae }
        r1 = r1.poll();	 Catch:{ all -> 0x00ae }
        r1 = (com.google.firebase.iid.zzah) r1;	 Catch:{ all -> 0x00ae }
        r3 = r0.zze;	 Catch:{ all -> 0x00ae }
        r4 = r1.zza;	 Catch:{ all -> 0x00ae }
        r3.put(r4, r1);	 Catch:{ all -> 0x00ae }
        r3 = r0.zzf;	 Catch:{ all -> 0x00ae }
        r3 = r3.zzc;	 Catch:{ all -> 0x00ae }
        r4 = new com.google.firebase.iid.zzac;	 Catch:{ all -> 0x00ae }
        r4.<init>(r0, r1);	 Catch:{ all -> 0x00ae }
        r5 = 30;
        r7 = java.util.concurrent.TimeUnit.SECONDS;	 Catch:{ all -> 0x00ae }
        r3.schedule(r4, r5, r7);	 Catch:{ all -> 0x00ae }
        monitor-exit(r0);	 Catch:{ all -> 0x00ae }
        r3 = 3;
        r4 = "MessengerIpcClient";
        r3 = android.util.Log.isLoggable(r4, r3);
        if (r3 == 0) goto L_0x0066;
    L_0x0042:
        r3 = java.lang.String.valueOf(r1);
        r4 = java.lang.String.valueOf(r3);
        r4 = r4.length();
        r4 = r4 + 8;
        r5 = new java.lang.StringBuilder;
        r5.<init>(r4);
        r4 = "Sending ";
        r5.append(r4);
        r5.append(r3);
        r3 = r5.toString();
        r4 = "MessengerIpcClient";
        android.util.Log.d(r4, r3);
    L_0x0066:
        r3 = r0.zzf;
        r3 = r3.zzb;
        r4 = r0.zzb;
        r5 = android.os.Message.obtain();
        r6 = r1.zzc;
        r5.what = r6;
        r6 = r1.zza;
        r5.arg1 = r6;
        r5.replyTo = r4;
        r4 = new android.os.Bundle;
        r4.<init>();
        r6 = r1.zza();
        r7 = "oneWay";
        r4.putBoolean(r7, r6);
        r3 = r3.getPackageName();
        r6 = "pkg";
        r4.putString(r6, r3);
        r1 = r1.zzd;
        r3 = "data";
        r4.putBundle(r3, r1);
        r5.setData(r4);
        r1 = r0.zzc;	 Catch:{ RemoteException -> 0x00a4 }
        r1.zza(r5);	 Catch:{ RemoteException -> 0x00a4 }
        goto L_0x0002;
    L_0x00a4:
        r1 = move-exception;
        r1 = r1.getMessage();
        r0.zza(r2, r1);
        goto L_0x0002;
    L_0x00ae:
        r1 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x00ae }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzaa.run():void");
    }
}
