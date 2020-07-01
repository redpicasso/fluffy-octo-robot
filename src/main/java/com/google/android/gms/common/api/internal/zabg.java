package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.internal.base.zar;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zabg extends zar {
    private final /* synthetic */ zabe zahz;

    zabg(zabe zabe, Looper looper) {
        this.zahz = zabe;
        super(looper);
    }

    public final void handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            ((zabd) message.obj).zaa(this.zahz);
        } else if (i != 2) {
            int i2 = message.what;
            StringBuilder stringBuilder = new StringBuilder(31);
            stringBuilder.append("Unknown message id: ");
            stringBuilder.append(i2);
            Log.w("GACStateManager", stringBuilder.toString());
        } else {
            throw ((RuntimeException) message.obj);
        }
    }
}
