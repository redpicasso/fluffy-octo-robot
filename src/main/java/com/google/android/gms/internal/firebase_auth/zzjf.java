package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

final class zzjf<T> implements zzjs<T> {
    private final zzjc zzacr;
    private final zzkk<?, ?> zzacs;
    private final boolean zzact;
    private final zzhh<?> zzacu;

    private zzjf(zzkk<?, ?> zzkk, zzhh<?> zzhh, zzjc zzjc) {
        this.zzacs = zzkk;
        this.zzact = zzhh.zzf(zzjc);
        this.zzacu = zzhh;
        this.zzacr = zzjc;
    }

    static <T> zzjf<T> zza(zzkk<?, ?> zzkk, zzhh<?> zzhh, zzjc zzjc) {
        return new zzjf(zzkk, zzhh, zzjc);
    }

    public final T newInstance() {
        return this.zzacr.zzio().zzig();
    }

    public final boolean equals(T t, T t2) {
        if (this.zzacs.zzs(t).equals(this.zzacs.zzs(t2))) {
            return this.zzact ? this.zzacu.zzd(t).equals(this.zzacu.zzd(t2)) : true;
        } else {
            return false;
        }
    }

    public final int hashCode(T t) {
        int hashCode = this.zzacs.zzs(t).hashCode();
        return this.zzact ? (hashCode * 53) + this.zzacu.zzd(t).hashCode() : hashCode;
    }

    public final void zzd(T t, T t2) {
        zzju.zza(this.zzacs, (Object) t, (Object) t2);
        if (this.zzact) {
            zzju.zza(this.zzacu, (Object) t, (Object) t2);
        }
    }

    public final void zza(T t, zzlh zzlh) throws IOException {
        Iterator it = this.zzacu.zzd(t).iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            zzhk zzhk = (zzhk) entry.getKey();
            if (zzhk.zzhy() != zzle.MESSAGE || zzhk.zzhz() || zzhk.zzia()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            } else if (entry instanceof zzif) {
                zzlh.zza(zzhk.zzbq(), ((zzif) entry).zzjc().zzft());
            } else {
                zzlh.zza(zzhk.zzbq(), entry.getValue());
            }
        }
        zzkk zzkk = this.zzacs;
        zzkk.zzc(zzkk.zzs(t), zzlh);
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x0085 A:{SYNTHETIC} */
    public final void zza(T r11, com.google.android.gms.internal.firebase_auth.zzjp r12, com.google.android.gms.internal.firebase_auth.zzhf r13) throws java.io.IOException {
        /*
        r10 = this;
        r0 = r10.zzacs;
        r1 = r10.zzacu;
        r2 = r0.zzt(r11);
        r3 = r1.zze(r11);
    L_0x000c:
        r4 = r12.zzhg();	 Catch:{ all -> 0x008e }
        r5 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        if (r4 != r5) goto L_0x0019;
    L_0x0015:
        r0.zzg(r11, r2);
        return;
    L_0x0019:
        r4 = r12.getTag();	 Catch:{ all -> 0x008e }
        r6 = 11;
        if (r4 == r6) goto L_0x003e;
    L_0x0021:
        r5 = r4 & 7;
        r6 = 2;
        if (r5 != r6) goto L_0x0039;
    L_0x0026:
        r5 = r10.zzacr;	 Catch:{ all -> 0x008e }
        r4 = r4 >>> 3;
        r4 = r1.zza(r13, r5, r4);	 Catch:{ all -> 0x008e }
        if (r4 == 0) goto L_0x0034;
    L_0x0030:
        r1.zza(r12, r4, r13, r3);	 Catch:{ all -> 0x008e }
        goto L_0x0082;
    L_0x0034:
        r4 = r0.zza(r2, r12);	 Catch:{ all -> 0x008e }
        goto L_0x0083;
    L_0x0039:
        r4 = r12.zzhh();	 Catch:{ all -> 0x008e }
        goto L_0x0083;
    L_0x003e:
        r4 = 0;
        r6 = 0;
        r7 = r6;
    L_0x0041:
        r8 = r12.zzhg();	 Catch:{ all -> 0x008e }
        if (r8 == r5) goto L_0x006f;
    L_0x0047:
        r8 = r12.getTag();	 Catch:{ all -> 0x008e }
        r9 = 16;
        if (r8 != r9) goto L_0x005a;
    L_0x004f:
        r4 = r12.zzgr();	 Catch:{ all -> 0x008e }
        r6 = r10.zzacr;	 Catch:{ all -> 0x008e }
        r6 = r1.zza(r13, r6, r4);	 Catch:{ all -> 0x008e }
        goto L_0x0041;
    L_0x005a:
        r9 = 26;
        if (r8 != r9) goto L_0x0069;
    L_0x005e:
        if (r6 == 0) goto L_0x0064;
    L_0x0060:
        r1.zza(r12, r6, r13, r3);	 Catch:{ all -> 0x008e }
        goto L_0x0041;
    L_0x0064:
        r7 = r12.zzgq();	 Catch:{ all -> 0x008e }
        goto L_0x0041;
    L_0x0069:
        r8 = r12.zzhh();	 Catch:{ all -> 0x008e }
        if (r8 != 0) goto L_0x0041;
    L_0x006f:
        r5 = r12.getTag();	 Catch:{ all -> 0x008e }
        r8 = 12;
        if (r5 != r8) goto L_0x0089;
    L_0x0077:
        if (r7 == 0) goto L_0x0082;
    L_0x0079:
        if (r6 == 0) goto L_0x007f;
    L_0x007b:
        r1.zza(r7, r6, r13, r3);	 Catch:{ all -> 0x008e }
        goto L_0x0082;
    L_0x007f:
        r0.zza(r2, r4, r7);	 Catch:{ all -> 0x008e }
    L_0x0082:
        r4 = 1;
    L_0x0083:
        if (r4 != 0) goto L_0x000c;
    L_0x0085:
        r0.zzg(r11, r2);
        return;
    L_0x0089:
        r12 = com.google.android.gms.internal.firebase_auth.zzic.zziv();	 Catch:{ all -> 0x008e }
        throw r12;	 Catch:{ all -> 0x008e }
    L_0x008e:
        r12 = move-exception;
        r0.zzg(r11, r2);
        throw r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzjf.zza(java.lang.Object, com.google.android.gms.internal.firebase_auth.zzjp, com.google.android.gms.internal.firebase_auth.zzhf):void");
    }

    public final void zzf(T t) {
        this.zzacs.zzf(t);
        this.zzacu.zzf((Object) t);
    }

    public final boolean zzp(T t) {
        return this.zzacu.zzd(t).isInitialized();
    }

    public final int zzq(T t) {
        zzkk zzkk = this.zzacs;
        int zzu = zzkk.zzu(zzkk.zzs(t)) + 0;
        return this.zzact ? zzu + this.zzacu.zzd(t).zzht() : zzu;
    }
}
