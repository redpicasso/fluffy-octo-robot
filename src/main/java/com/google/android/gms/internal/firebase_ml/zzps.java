package com.google.android.gms.internal.firebase_ml;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import java.io.IOException;

final class zzps extends zzip {
    private final /* synthetic */ FirebaseApp zzawx;

    zzps(zzpq zzpq, String str, FirebaseApp firebaseApp) {
        this.zzawx = firebaseApp;
        super(str);
    }

    protected final void zza(zzio<?> zzio) throws IOException {
        super.zza((zzio) zzio);
        Context applicationContext = this.zzawx.getApplicationContext();
        String packageName = applicationContext.getPackageName();
        zzio.zzeo().put("X-Android-Package", packageName);
        zzio.zzeo().put("X-Android-Cert", zzpq.zza(applicationContext, packageName));
    }
}
