package com.google.android.gms.internal.firebase_ml;

import com.google.android.gms.common.internal.Objects;
import java.util.concurrent.Callable;

final class zznz implements Callable<Void> {
    private final /* synthetic */ zznx zzaqd;
    private final zznw zzaqe;
    private final String zzaqf;

    zznz(zznx zznx, zznw zznw, String str) {
        this.zzaqd = zznx;
        this.zzaqe = zznw;
        this.zzaqf = str;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0046  */
    private final java.lang.Void zzls() {
        /*
        r4 = this;
        r0 = r4.zzaqf;
        r1 = r0.hashCode();
        r2 = 97847535; // 0x5d508ef float:2.0033705E-35 double:4.83431056E-316;
        r3 = 1;
        if (r1 == r2) goto L_0x001c;
    L_0x000c:
        r2 = 710591710; // 0x2a5ac4de float:1.9430592E-13 double:3.51078952E-315;
        if (r1 == r2) goto L_0x0012;
    L_0x0011:
        goto L_0x0026;
    L_0x0012:
        r1 = "OPERATION_LOAD";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0026;
    L_0x001a:
        r0 = 0;
        goto L_0x0027;
    L_0x001c:
        r1 = "OPERATION_RELEASE";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0026;
    L_0x0024:
        r0 = 1;
        goto L_0x0027;
    L_0x0026:
        r0 = -1;
    L_0x0027:
        r1 = "ModelResourceManager";
        if (r0 == 0) goto L_0x0046;
    L_0x002b:
        if (r0 == r3) goto L_0x002e;
    L_0x002d:
        goto L_0x0058;
    L_0x002e:
        r0 = r4.zzaqe;
        r2 = com.google.android.gms.internal.firebase_ml.zznx.zzape;
        r3 = "Releasing modelResource";
        r2.v(r1, r3);
        r0.release();
        r1 = r4.zzaqd;
        r1 = r1.zzaqa;
        r1.remove(r0);
        goto L_0x0058;
    L_0x0046:
        r0 = r4.zzaqe;
        r2 = r4.zzaqd;	 Catch:{ FirebaseMLException -> 0x004e }
        r2.zzf(r0);	 Catch:{ FirebaseMLException -> 0x004e }
        goto L_0x0058;
    L_0x004e:
        r0 = move-exception;
        r2 = com.google.android.gms.internal.firebase_ml.zznx.zzape;
        r3 = "Error preloading model resource";
        r2.e(r1, r3, r0);
    L_0x0058:
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zznz.zzls():java.lang.Void");
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zznz)) {
            return false;
        }
        zznz zznz = (zznz) obj;
        if (Objects.equal(this.zzaqe, zznz.zzaqe) && Objects.equal(this.zzaqf, zznz.zzaqf)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzaqe, this.zzaqf);
    }
}
