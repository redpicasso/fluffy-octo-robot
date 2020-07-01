package com.google.firebase.iid;

import android.os.Binder;
import android.os.Process;
import android.util.Log;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
public final class zzaz extends Binder {
    private final zzbb zza;

    public zzaz(zzbb zzbb) {
        this.zza = zzbb;
    }

    final void zza(zzbd zzbd) {
        if (Binder.getCallingUid() == Process.myUid()) {
            String str = "FirebaseInstanceId";
            if (Log.isLoggable(str, 3)) {
                Log.d(str, "service received new intent via bind strategy");
            }
            this.zza.zza(zzbd.zza).addOnCompleteListener(zza.zza(), new zzay(zzbd));
            return;
        }
        throw new SecurityException("Binding only allowed within app");
    }
}
