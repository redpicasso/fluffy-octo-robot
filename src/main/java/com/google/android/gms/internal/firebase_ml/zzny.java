package com.google.android.gms.internal.firebase_ml;

import com.google.android.gms.common.api.internal.BackgroundDetector.BackgroundStateChangeListener;
import com.google.android.gms.common.internal.GmsLogger;

final class zzny implements BackgroundStateChangeListener {
    private final /* synthetic */ zznx zzaqd;

    zzny(zznx zznx) {
        this.zzaqd = zznx;
    }

    public final void onBackgroundStateChanged(boolean z) {
        GmsLogger zzlr = zznx.zzape;
        StringBuilder stringBuilder = new StringBuilder(34);
        stringBuilder.append("Background state changed to: ");
        stringBuilder.append(z);
        zzlr.v("ModelResourceManager", stringBuilder.toString());
        this.zzaqd.zzapy.set(z ? 2000 : 300000);
        this.zzaqd.zzlq();
    }
}
