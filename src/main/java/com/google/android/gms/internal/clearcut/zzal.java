package com.google.android.gms.internal.clearcut;

import android.content.SharedPreferences;
import android.util.Log;

final class zzal extends zzae<T> {
    private final Object lock = new Object();
    private String zzec;
    private T zzed;
    private final /* synthetic */ zzan zzee;

    zzal(zzao zzao, String str, Object obj, zzan zzan) {
        this.zzee = zzan;
        super(zzao, str, obj, null);
    }

    protected final T zza(SharedPreferences sharedPreferences) {
        try {
            return zzb(sharedPreferences.getString(this.zzds, ""));
        } catch (Throwable e) {
            String str = "Invalid byte[] value in SharedPreferences for ";
            String valueOf = String.valueOf(this.zzds);
            Log.e("PhenotypeFlag", valueOf.length() != 0 ? str.concat(valueOf) : new String(str), e);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0021 A:{ExcHandler: java.io.IOException (unused java.io.IOException), Splitter: B:0:0x0000} */
    /* JADX WARNING: Missing block: B:14:0x0021, code:
            r0 = r3.zzds;
            r2 = new java.lang.StringBuilder((java.lang.String.valueOf(r0).length() + 27) + java.lang.String.valueOf(r4).length());
            r2.append("Invalid byte[] value for ");
            r2.append(r0);
            r2.append(": ");
            r2.append(r4);
            android.util.Log.e("PhenotypeFlag", r2.toString());
     */
    /* JADX WARNING: Missing block: B:15:0x0055, code:
            return null;
     */
    protected final T zzb(java.lang.String r4) {
        /*
        r3 = this;
        r0 = r3.lock;	 Catch:{ IOException -> 0x0021, IOException -> 0x0021 }
        monitor-enter(r0);	 Catch:{ IOException -> 0x0021, IOException -> 0x0021 }
        r1 = r3.zzec;	 Catch:{ all -> 0x001e }
        r1 = r4.equals(r1);	 Catch:{ all -> 0x001e }
        if (r1 != 0) goto L_0x001a;
    L_0x000b:
        r1 = r3.zzee;	 Catch:{ all -> 0x001e }
        r2 = 3;
        r2 = android.util.Base64.decode(r4, r2);	 Catch:{ all -> 0x001e }
        r1 = r1.zzb(r2);	 Catch:{ all -> 0x001e }
        r3.zzec = r4;	 Catch:{ all -> 0x001e }
        r3.zzed = r1;	 Catch:{ all -> 0x001e }
    L_0x001a:
        r1 = r3.zzed;	 Catch:{ all -> 0x001e }
        monitor-exit(r0);	 Catch:{ all -> 0x001e }
        return r1;
    L_0x001e:
        r1 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x001e }
        throw r1;	 Catch:{ IOException -> 0x0021, IOException -> 0x0021 }
    L_0x0021:
        r0 = r3.zzds;
        r1 = java.lang.String.valueOf(r0);
        r1 = r1.length();
        r1 = r1 + 27;
        r2 = java.lang.String.valueOf(r4);
        r2 = r2.length();
        r1 = r1 + r2;
        r2 = new java.lang.StringBuilder;
        r2.<init>(r1);
        r1 = "Invalid byte[] value for ";
        r2.append(r1);
        r2.append(r0);
        r0 = ": ";
        r2.append(r0);
        r2.append(r4);
        r4 = r2.toString();
        r0 = "PhenotypeFlag";
        android.util.Log.e(r0, r4);
        r4 = 0;
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzal.zzb(java.lang.String):T");
    }
}
