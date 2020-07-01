package com.google.android.gms.internal.vision;

import java.io.IOException;

public final class zzdi extends zzjn<zzdi> {
    private int[] zzoe;

    public zzdi() {
        this.zzoe = zzjw.zzzb;
        this.zzadp = -1;
    }

    public final void zza(zzjl zzjl) throws IOException {
        int[] iArr = this.zzoe;
        if (iArr != null && iArr.length > 0) {
            int i = 0;
            while (true) {
                int[] iArr2 = this.zzoe;
                if (i >= iArr2.length) {
                    break;
                }
                zzjl.zze(1, iArr2[i]);
                i++;
            }
        }
        super.zza(zzjl);
    }

    protected final int zzt() {
        int zzt = super.zzt();
        int[] iArr = this.zzoe;
        if (iArr == null || iArr.length <= 0) {
            return zzt;
        }
        int i = 0;
        int i2 = 0;
        while (true) {
            int[] iArr2 = this.zzoe;
            if (i >= iArr2.length) {
                return (zzt + i2) + (iArr2.length * 1);
            }
            i2 += zzjl.zzaw(iArr2[i]);
            i++;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.google.android.gms.internal.vision.zzdi.zzb(com.google.android.gms.internal.vision.zzjk):com.google.android.gms.internal.vision.zzdi, dom blocks: []
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:89)
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:293)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:201)
        */
    private final com.google.android.gms.internal.vision.zzdi zzb(com.google.android.gms.internal.vision.zzjk r9) throws java.io.IOException {
        /*
        r8 = this;
    L_0x0000:
        r0 = r9.zzdq();
        if (r0 == 0) goto L_0x00bc;
    L_0x0006:
        r1 = 8;
        r2 = 0;
        if (r0 == r1) goto L_0x006f;
    L_0x000b:
        r3 = 10;
        if (r0 == r3) goto L_0x0016;
    L_0x000f:
        r0 = super.zza(r9, r0);
        if (r0 != 0) goto L_0x0000;
    L_0x0015:
        return r8;
    L_0x0016:
        r0 = r9.zzdt();
        r0 = r9.zzan(r0);
        r3 = r9.getPosition();
        r4 = 0;
    L_0x0023:
        r5 = r9.zzhq();
        if (r5 <= 0) goto L_0x0035;
    L_0x0029:
        r5 = r9.zzdt();	 Catch:{ IllegalArgumentException -> 0x0033 }
        com.google.android.gms.internal.vision.zzeb.zzx(r5);	 Catch:{ IllegalArgumentException -> 0x0033 }
        r4 = r4 + 1;
        goto L_0x0023;
        goto L_0x0023;
    L_0x0035:
        if (r4 == 0) goto L_0x006b;
    L_0x0037:
        r9.zzbt(r3);
        r3 = r8.zzoe;
        if (r3 != 0) goto L_0x0040;
    L_0x003e:
        r3 = 0;
        goto L_0x0041;
    L_0x0040:
        r3 = r3.length;
    L_0x0041:
        r4 = r4 + r3;
        r4 = new int[r4];
        if (r3 == 0) goto L_0x004b;
    L_0x0046:
        r5 = r8.zzoe;
        java.lang.System.arraycopy(r5, r2, r4, r2, r3);
    L_0x004b:
        r2 = r9.zzhq();
        if (r2 <= 0) goto L_0x0069;
    L_0x0051:
        r2 = r9.getPosition();
        r5 = r9.zzdt();	 Catch:{ IllegalArgumentException -> 0x0062 }
        r5 = com.google.android.gms.internal.vision.zzeb.zzx(r5);	 Catch:{ IllegalArgumentException -> 0x0062 }
        r4[r3] = r5;	 Catch:{ IllegalArgumentException -> 0x0062 }
        r3 = r3 + 1;
        goto L_0x004b;
    L_0x0062:
        r9.zzbt(r2);
        r8.zza(r9, r1);
        goto L_0x004b;
    L_0x0069:
        r8.zzoe = r4;
    L_0x006b:
        r9.zzao(r0);
        goto L_0x0000;
    L_0x006f:
        r1 = com.google.android.gms.internal.vision.zzjw.zzb(r9, r1);
        r3 = new int[r1];
        r4 = 0;
        r5 = 0;
    L_0x0077:
        if (r4 >= r1) goto L_0x0098;
    L_0x0079:
        if (r4 == 0) goto L_0x007e;
    L_0x007b:
        r9.zzdq();
    L_0x007e:
        r6 = r9.getPosition();
        r7 = r9.zzdt();	 Catch:{ IllegalArgumentException -> 0x008f }
        r7 = com.google.android.gms.internal.vision.zzeb.zzx(r7);	 Catch:{ IllegalArgumentException -> 0x008f }
        r3[r5] = r7;	 Catch:{ IllegalArgumentException -> 0x008f }
        r5 = r5 + 1;
        goto L_0x0095;
    L_0x008f:
        r9.zzbt(r6);
        r8.zza(r9, r0);
    L_0x0095:
        r4 = r4 + 1;
        goto L_0x0077;
    L_0x0098:
        if (r5 == 0) goto L_0x0000;
    L_0x009a:
        r0 = r8.zzoe;
        if (r0 != 0) goto L_0x00a0;
    L_0x009e:
        r0 = 0;
        goto L_0x00a1;
    L_0x00a0:
        r0 = r0.length;
    L_0x00a1:
        if (r0 != 0) goto L_0x00aa;
    L_0x00a3:
        r1 = r3.length;
        if (r5 != r1) goto L_0x00aa;
    L_0x00a6:
        r8.zzoe = r3;
        goto L_0x0000;
    L_0x00aa:
        r1 = r0 + r5;
        r1 = new int[r1];
        if (r0 == 0) goto L_0x00b5;
    L_0x00b0:
        r4 = r8.zzoe;
        java.lang.System.arraycopy(r4, r2, r1, r2, r0);
    L_0x00b5:
        java.lang.System.arraycopy(r3, r2, r1, r0, r5);
        r8.zzoe = r1;
        goto L_0x0000;
    L_0x00bc:
        return r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzdi.zzb(com.google.android.gms.internal.vision.zzjk):com.google.android.gms.internal.vision.zzdi");
    }
}
