package com.google.android.gms.iid;

final /* synthetic */ class zzw implements Runnable {
    private final zzt zzcm;

    zzw(zzt zzt) {
        this.zzcm = zzt;
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
            r3 = r0.zzcl.zzl;
            r4 = r0.zzch;
            r5 = android.os.Message.obtain();
            r5.what = r1.what;
            r5.arg1 = r1.zzcp;
            r5.replyTo = r4;
            r4 = new android.os.Bundle();
            r4.putBoolean("oneWay", r1.zzw());
            r4.putString("pkg", r3.getPackageName());
            r4.putBundle("data", r1.zzcr);
            r5.setData(r4);
     */
    /* JADX WARNING: Missing block: B:19:?, code:
            r1 = r0.zzci;
     */
    /* JADX WARNING: Missing block: B:20:0x00a1, code:
            if (r1.zzad == null) goto L_0x00aa;
     */
    /* JADX WARNING: Missing block: B:21:0x00a3, code:
            r1.zzad.send(r5);
     */
    /* JADX WARNING: Missing block: B:23:0x00ac, code:
            if (r1.zzco == null) goto L_0x00b5;
     */
    /* JADX WARNING: Missing block: B:24:0x00ae, code:
            r1.zzco.send(r5);
     */
    /* JADX WARNING: Missing block: B:26:0x00bc, code:
            throw new java.lang.IllegalStateException("Both messengers are null");
     */
    /* JADX WARNING: Missing block: B:27:0x00bd, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:28:0x00be, code:
            r0.zzd(2, r1.getMessage());
     */
    public final void run() {
        /*
        r8 = this;
        r0 = r8.zzcm;
    L_0x0002:
        monitor-enter(r0);
        r1 = r0.state;	 Catch:{ all -> 0x00c7 }
        r2 = 2;
        if (r1 == r2) goto L_0x000a;
    L_0x0008:
        monitor-exit(r0);	 Catch:{ all -> 0x00c7 }
        return;
    L_0x000a:
        r1 = r0.zzcj;	 Catch:{ all -> 0x00c7 }
        r1 = r1.isEmpty();	 Catch:{ all -> 0x00c7 }
        if (r1 == 0) goto L_0x0017;
    L_0x0012:
        r0.zzu();	 Catch:{ all -> 0x00c7 }
        monitor-exit(r0);	 Catch:{ all -> 0x00c7 }
        return;
    L_0x0017:
        r1 = r0.zzcj;	 Catch:{ all -> 0x00c7 }
        r1 = r1.poll();	 Catch:{ all -> 0x00c7 }
        r1 = (com.google.android.gms.iid.zzz) r1;	 Catch:{ all -> 0x00c7 }
        r3 = r0.zzck;	 Catch:{ all -> 0x00c7 }
        r4 = r1.zzcp;	 Catch:{ all -> 0x00c7 }
        r3.put(r4, r1);	 Catch:{ all -> 0x00c7 }
        r3 = r0.zzcl;	 Catch:{ all -> 0x00c7 }
        r3 = r3.zzce;	 Catch:{ all -> 0x00c7 }
        r4 = new com.google.android.gms.iid.zzx;	 Catch:{ all -> 0x00c7 }
        r4.<init>(r0, r1);	 Catch:{ all -> 0x00c7 }
        r5 = 30;
        r7 = java.util.concurrent.TimeUnit.SECONDS;	 Catch:{ all -> 0x00c7 }
        r3.schedule(r4, r5, r7);	 Catch:{ all -> 0x00c7 }
        monitor-exit(r0);	 Catch:{ all -> 0x00c7 }
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
        r3 = r0.zzcl;
        r3 = r3.zzl;
        r4 = r0.zzch;
        r5 = android.os.Message.obtain();
        r6 = r1.what;
        r5.what = r6;
        r6 = r1.zzcp;
        r5.arg1 = r6;
        r5.replyTo = r4;
        r4 = new android.os.Bundle;
        r4.<init>();
        r6 = r1.zzw();
        r7 = "oneWay";
        r4.putBoolean(r7, r6);
        r3 = r3.getPackageName();
        r6 = "pkg";
        r4.putString(r6, r3);
        r1 = r1.zzcr;
        r3 = "data";
        r4.putBundle(r3, r1);
        r5.setData(r4);
        r1 = r0.zzci;	 Catch:{ RemoteException -> 0x00bd }
        r3 = r1.zzad;	 Catch:{ RemoteException -> 0x00bd }
        if (r3 == 0) goto L_0x00aa;
    L_0x00a3:
        r1 = r1.zzad;	 Catch:{ RemoteException -> 0x00bd }
        r1.send(r5);	 Catch:{ RemoteException -> 0x00bd }
        goto L_0x0002;
    L_0x00aa:
        r3 = r1.zzco;	 Catch:{ RemoteException -> 0x00bd }
        if (r3 == 0) goto L_0x00b5;
    L_0x00ae:
        r1 = r1.zzco;	 Catch:{ RemoteException -> 0x00bd }
        r1.send(r5);	 Catch:{ RemoteException -> 0x00bd }
        goto L_0x0002;
    L_0x00b5:
        r1 = new java.lang.IllegalStateException;	 Catch:{ RemoteException -> 0x00bd }
        r3 = "Both messengers are null";
        r1.<init>(r3);	 Catch:{ RemoteException -> 0x00bd }
        throw r1;	 Catch:{ RemoteException -> 0x00bd }
    L_0x00bd:
        r1 = move-exception;
        r1 = r1.getMessage();
        r0.zzd(r2, r1);
        goto L_0x0002;
    L_0x00c7:
        r1 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x00c7 }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.iid.zzw.run():void");
    }
}
