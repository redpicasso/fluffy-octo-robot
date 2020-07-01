package com.google.android.gms.measurement.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import androidx.annotation.MainThread;

@TargetApi(14)
@MainThread
final class zzhj implements ActivityLifecycleCallbacks {
    private final /* synthetic */ zzgp zzpt;

    private zzhj(zzgp zzgp) {
        this.zzpt = zzgp;
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }

    /* JADX WARNING: Removed duplicated region for block: B:57:0x0123 A:{Catch:{ Exception -> 0x01d6, all -> 0x01d4 }} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00d3 A:{Catch:{ Exception -> 0x01d6, all -> 0x01d4 }} */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x015f A:{SYNTHETIC, Splitter: B:71:0x015f} */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0155  */
    /* JADX WARNING: Missing block: B:21:0x0070, code:
            if (r1.zzpt.zzad().zza(com.google.android.gms.measurement.internal.zzak.zzjg) != false) goto L_0x0077;
     */
    public final void onActivityCreated(android.app.Activity r20, android.os.Bundle r21) {
        /*
        r19 = this;
        r1 = r19;
        r2 = r20;
        r3 = r21;
        r0 = "referrer";
        r4 = r1.zzpt;	 Catch:{ Exception -> 0x01d6 }
        r4 = r4.zzab();	 Catch:{ Exception -> 0x01d6 }
        r4 = r4.zzgs();	 Catch:{ Exception -> 0x01d6 }
        r5 = "onActivityCreated";
        r4.zzao(r5);	 Catch:{ Exception -> 0x01d6 }
        r4 = r20.getIntent();	 Catch:{ Exception -> 0x01d6 }
        if (r4 != 0) goto L_0x0027;
    L_0x001d:
        r0 = r1.zzpt;
        r0 = r0.zzt();
        r0.onActivityCreated(r2, r3);
        return;
    L_0x0027:
        r5 = r4.getData();	 Catch:{ Exception -> 0x01d6 }
        if (r5 == 0) goto L_0x01ca;
    L_0x002d:
        r6 = r5.isHierarchical();	 Catch:{ Exception -> 0x01d6 }
        if (r6 != 0) goto L_0x0035;
    L_0x0033:
        goto L_0x01ca;
    L_0x0035:
        r6 = r1.zzpt;	 Catch:{ Exception -> 0x01d6 }
        r6.zzz();	 Catch:{ Exception -> 0x01d6 }
        r4 = com.google.android.gms.measurement.internal.zzjs.zzc(r4);	 Catch:{ Exception -> 0x01d6 }
        r6 = "auto";
        if (r4 == 0) goto L_0x0045;
    L_0x0042:
        r4 = "gs";
        goto L_0x0046;
    L_0x0045:
        r4 = r6;
    L_0x0046:
        r7 = r5.getQueryParameter(r0);	 Catch:{ Exception -> 0x01d6 }
        r8 = r1.zzpt;	 Catch:{ Exception -> 0x01d6 }
        r8 = r8.zzad();	 Catch:{ Exception -> 0x01d6 }
        r9 = com.google.android.gms.measurement.internal.zzak.zzje;	 Catch:{ Exception -> 0x01d6 }
        r8 = r8.zza(r9);	 Catch:{ Exception -> 0x01d6 }
        r9 = "Activity created with data 'referrer' without required params";
        r10 = "utm_medium";
        r11 = "_cis";
        r12 = "utm_source";
        r13 = "utm_campaign";
        r15 = "gclid";
        if (r8 != 0) goto L_0x0077;
    L_0x0064:
        r8 = r1.zzpt;	 Catch:{ Exception -> 0x01d6 }
        r8 = r8.zzad();	 Catch:{ Exception -> 0x01d6 }
        r14 = com.google.android.gms.measurement.internal.zzak.zzjg;	 Catch:{ Exception -> 0x01d6 }
        r8 = r8.zza(r14);	 Catch:{ Exception -> 0x01d6 }
        if (r8 == 0) goto L_0x0073;
    L_0x0072:
        goto L_0x0077;
    L_0x0073:
        r17 = r9;
        r14 = 0;
        goto L_0x00d0;
    L_0x0077:
        r8 = android.text.TextUtils.isEmpty(r7);	 Catch:{ Exception -> 0x01d6 }
        if (r8 == 0) goto L_0x007e;
    L_0x007d:
        goto L_0x0073;
    L_0x007e:
        r8 = r7.contains(r15);	 Catch:{ Exception -> 0x01d6 }
        if (r8 != 0) goto L_0x00a4;
    L_0x0084:
        r8 = r7.contains(r13);	 Catch:{ Exception -> 0x01d6 }
        if (r8 != 0) goto L_0x00a4;
    L_0x008a:
        r8 = r7.contains(r12);	 Catch:{ Exception -> 0x01d6 }
        if (r8 != 0) goto L_0x00a4;
    L_0x0090:
        r8 = r7.contains(r10);	 Catch:{ Exception -> 0x01d6 }
        if (r8 != 0) goto L_0x00a4;
    L_0x0096:
        r0 = r1.zzpt;	 Catch:{ Exception -> 0x01d6 }
        r0 = r0.zzab();	 Catch:{ Exception -> 0x01d6 }
        r0 = r0.zzgr();	 Catch:{ Exception -> 0x01d6 }
        r0.zzao(r9);	 Catch:{ Exception -> 0x01d6 }
        goto L_0x0073;
    L_0x00a4:
        r8 = r1.zzpt;	 Catch:{ Exception -> 0x01d6 }
        r8 = r8.zzz();	 Catch:{ Exception -> 0x01d6 }
        r14 = "https://google.com/search?";
        r17 = r9;
        r9 = java.lang.String.valueOf(r7);	 Catch:{ Exception -> 0x01d6 }
        r18 = r9.length();	 Catch:{ Exception -> 0x01d6 }
        if (r18 == 0) goto L_0x00bd;
    L_0x00b8:
        r9 = r14.concat(r9);	 Catch:{ Exception -> 0x01d6 }
        goto L_0x00c2;
    L_0x00bd:
        r9 = new java.lang.String;	 Catch:{ Exception -> 0x01d6 }
        r9.<init>(r14);	 Catch:{ Exception -> 0x01d6 }
    L_0x00c2:
        r9 = android.net.Uri.parse(r9);	 Catch:{ Exception -> 0x01d6 }
        r8 = r8.zza(r9);	 Catch:{ Exception -> 0x01d6 }
        if (r8 == 0) goto L_0x00cf;
    L_0x00cc:
        r8.putString(r11, r0);	 Catch:{ Exception -> 0x01d6 }
    L_0x00cf:
        r14 = r8;
    L_0x00d0:
        r8 = 1;
        if (r3 != 0) goto L_0x0123;
    L_0x00d3:
        r9 = r1.zzpt;	 Catch:{ Exception -> 0x01d6 }
        r9 = r9.zzz();	 Catch:{ Exception -> 0x01d6 }
        r5 = r9.zza(r5);	 Catch:{ Exception -> 0x01d6 }
        if (r5 == 0) goto L_0x0120;
    L_0x00df:
        r9 = "intent";
        r5.putString(r11, r9);	 Catch:{ Exception -> 0x01d6 }
        r9 = r1.zzpt;	 Catch:{ Exception -> 0x01d6 }
        r9 = r9.zzad();	 Catch:{ Exception -> 0x01d6 }
        r11 = com.google.android.gms.measurement.internal.zzak.zzje;	 Catch:{ Exception -> 0x01d6 }
        r9 = r9.zza(r11);	 Catch:{ Exception -> 0x01d6 }
        if (r9 == 0) goto L_0x0116;
    L_0x00f2:
        r9 = r5.containsKey(r15);	 Catch:{ Exception -> 0x01d6 }
        if (r9 != 0) goto L_0x0116;
    L_0x00f8:
        if (r14 == 0) goto L_0x0116;
    L_0x00fa:
        r9 = r14.containsKey(r15);	 Catch:{ Exception -> 0x01d6 }
        if (r9 == 0) goto L_0x0116;
    L_0x0100:
        r9 = "_cer";
        r11 = "gclid=%s";
        r0 = new java.lang.Object[r8];	 Catch:{ Exception -> 0x01d6 }
        r16 = r14.getString(r15);	 Catch:{ Exception -> 0x01d6 }
        r18 = 0;
        r0[r18] = r16;	 Catch:{ Exception -> 0x01d6 }
        r0 = java.lang.String.format(r11, r0);	 Catch:{ Exception -> 0x01d6 }
        r5.putString(r9, r0);	 Catch:{ Exception -> 0x01d6 }
        goto L_0x0118;
    L_0x0116:
        r18 = 0;
    L_0x0118:
        r0 = r1.zzpt;	 Catch:{ Exception -> 0x01d6 }
        r9 = "_cmp";
        r0.logEvent(r4, r9, r5);	 Catch:{ Exception -> 0x01d6 }
        goto L_0x0126;
    L_0x0120:
        r18 = 0;
        goto L_0x0126;
    L_0x0123:
        r18 = 0;
        r5 = 0;
    L_0x0126:
        r0 = r1.zzpt;	 Catch:{ Exception -> 0x01d6 }
        r0 = r0.zzad();	 Catch:{ Exception -> 0x01d6 }
        r4 = com.google.android.gms.measurement.internal.zzak.zzjg;	 Catch:{ Exception -> 0x01d6 }
        r0 = r0.zza(r4);	 Catch:{ Exception -> 0x01d6 }
        if (r0 == 0) goto L_0x014f;
    L_0x0134:
        if (r14 == 0) goto L_0x014f;
    L_0x0136:
        r0 = r14.containsKey(r15);	 Catch:{ Exception -> 0x01d6 }
        if (r0 == 0) goto L_0x014f;
    L_0x013c:
        if (r5 == 0) goto L_0x0144;
    L_0x013e:
        r0 = r5.containsKey(r15);	 Catch:{ Exception -> 0x01d6 }
        if (r0 != 0) goto L_0x014f;
    L_0x0144:
        r0 = r1.zzpt;	 Catch:{ Exception -> 0x01d6 }
        r4 = "_lgclid";
        r5 = r14.getString(r15);	 Catch:{ Exception -> 0x01d6 }
        r0.zzb(r6, r4, r5, r8);	 Catch:{ Exception -> 0x01d6 }
    L_0x014f:
        r0 = android.text.TextUtils.isEmpty(r7);	 Catch:{ Exception -> 0x01d6 }
        if (r0 == 0) goto L_0x015f;
    L_0x0155:
        r0 = r1.zzpt;
        r0 = r0.zzt();
        r0.onActivityCreated(r2, r3);
        return;
    L_0x015f:
        r0 = r7.contains(r15);	 Catch:{ Exception -> 0x01d6 }
        if (r0 == 0) goto L_0x0189;
    L_0x0165:
        r0 = r7.contains(r13);	 Catch:{ Exception -> 0x01d6 }
        if (r0 != 0) goto L_0x0187;
    L_0x016b:
        r0 = r7.contains(r12);	 Catch:{ Exception -> 0x01d6 }
        if (r0 != 0) goto L_0x0187;
    L_0x0171:
        r0 = r7.contains(r10);	 Catch:{ Exception -> 0x01d6 }
        if (r0 != 0) goto L_0x0187;
    L_0x0177:
        r0 = "utm_term";
        r0 = r7.contains(r0);	 Catch:{ Exception -> 0x01d6 }
        if (r0 != 0) goto L_0x0187;
    L_0x017f:
        r0 = "utm_content";
        r0 = r7.contains(r0);	 Catch:{ Exception -> 0x01d6 }
        if (r0 == 0) goto L_0x0189;
    L_0x0187:
        r18 = 1;
    L_0x0189:
        if (r18 != 0) goto L_0x01a4;
    L_0x018b:
        r0 = r1.zzpt;	 Catch:{ Exception -> 0x01d6 }
        r0 = r0.zzab();	 Catch:{ Exception -> 0x01d6 }
        r0 = r0.zzgr();	 Catch:{ Exception -> 0x01d6 }
        r4 = r17;
        r0.zzao(r4);	 Catch:{ Exception -> 0x01d6 }
        r0 = r1.zzpt;
        r0 = r0.zzt();
        r0.onActivityCreated(r2, r3);
        return;
    L_0x01a4:
        r0 = r1.zzpt;	 Catch:{ Exception -> 0x01d6 }
        r0 = r0.zzab();	 Catch:{ Exception -> 0x01d6 }
        r0 = r0.zzgr();	 Catch:{ Exception -> 0x01d6 }
        r4 = "Activity created with referrer";
        r0.zza(r4, r7);	 Catch:{ Exception -> 0x01d6 }
        r0 = android.text.TextUtils.isEmpty(r7);	 Catch:{ Exception -> 0x01d6 }
        if (r0 != 0) goto L_0x01c0;
    L_0x01b9:
        r0 = r1.zzpt;	 Catch:{ Exception -> 0x01d6 }
        r4 = "_ldl";
        r0.zzb(r6, r4, r7, r8);	 Catch:{ Exception -> 0x01d6 }
    L_0x01c0:
        r0 = r1.zzpt;
        r0 = r0.zzt();
        r0.onActivityCreated(r2, r3);
        return;
    L_0x01ca:
        r0 = r1.zzpt;
        r0 = r0.zzt();
        r0.onActivityCreated(r2, r3);
        return;
    L_0x01d4:
        r0 = move-exception;
        goto L_0x01f0;
    L_0x01d6:
        r0 = move-exception;
        r4 = r1.zzpt;	 Catch:{ all -> 0x01d4 }
        r4 = r4.zzab();	 Catch:{ all -> 0x01d4 }
        r4 = r4.zzgk();	 Catch:{ all -> 0x01d4 }
        r5 = "Throwable caught in onActivityCreated";
        r4.zza(r5, r0);	 Catch:{ all -> 0x01d4 }
        r0 = r1.zzpt;
        r0 = r0.zzt();
        r0.onActivityCreated(r2, r3);
        return;
    L_0x01f0:
        r4 = r1.zzpt;
        r4 = r4.zzt();
        r4.onActivityCreated(r2, r3);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzhj.onActivityCreated(android.app.Activity, android.os.Bundle):void");
    }

    public final void onActivityDestroyed(Activity activity) {
        this.zzpt.zzt().onActivityDestroyed(activity);
    }

    @MainThread
    public final void onActivityPaused(Activity activity) {
        this.zzpt.zzt().onActivityPaused(activity);
        zzgf zzv = this.zzpt.zzv();
        zzv.zzaa().zza(new zzja(zzv, zzv.zzx().elapsedRealtime()));
    }

    @MainThread
    public final void onActivityResumed(Activity activity) {
        this.zzpt.zzt().onActivityResumed(activity);
        zzgf zzv = this.zzpt.zzv();
        zzv.zzaa().zza(new zzjb(zzv, zzv.zzx().elapsedRealtime()));
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.zzpt.zzt().onActivitySaveInstanceState(activity, bundle);
    }

    /* synthetic */ zzhj(zzgp zzgp, zzgo zzgo) {
        this(zzgp);
    }
}
