package com.google.firebase.iid;

import android.os.Looper;
import android.os.Message;
import com.google.android.gms.internal.firebase_messaging.zze;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
final class zzar extends zze {
    private final /* synthetic */ zzao zza;

    zzar(zzao zzao, Looper looper) {
        this.zza = zzao;
        super(looper);
    }

    public final void handleMessage(Message message) {
        this.zza.zza(message);
    }
}
