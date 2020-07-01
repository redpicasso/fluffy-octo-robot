package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;

public final class zzik extends zzer {
    zzik(zzil zzil) {
        super(zzil);
    }

    protected final void zza(zzem<?> zzem) throws IOException {
        super.zza(zzem);
    }

    static {
        Object obj = (zzeh.zzsg.intValue() != 1 || zzeh.zzsh.intValue() < 15) ? null : 1;
        Object[] objArr = new Object[]{zzeh.VERSION};
        if (obj == null) {
            throw new IllegalStateException(zzla.zzb("You are currently running with version %s of google-api-client. You need at least version 1.15 of google-api-client to run version 1.25.0-SNAPSHOT of the Cloud Vision API library.", objArr));
        }
    }
}
