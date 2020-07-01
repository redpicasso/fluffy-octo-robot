package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.base.zar;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zacm extends zar {
    private final /* synthetic */ zack zaky;

    public zacm(zack zack, Looper looper) {
        this.zaky = zack;
        super(looper);
    }

    public final void handleMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            PendingResult pendingResult = (PendingResult) message.obj;
            synchronized (this.zaky.zadp) {
                if (pendingResult == null) {
                    this.zaky.zaks.zad(new Status(13, "Transform returned null"));
                } else if (pendingResult instanceof zacc) {
                    this.zaky.zaks.zad(((zacc) pendingResult).getStatus());
                } else {
                    this.zaky.zaks.zaa(pendingResult);
                }
            }
        } else if (i != 1) {
            int i2 = message.what;
            StringBuilder stringBuilder = new StringBuilder(70);
            stringBuilder.append("TransformationResultHandler received unknown message type: ");
            stringBuilder.append(i2);
            Log.e("TransformedResultImpl", stringBuilder.toString());
        } else {
            RuntimeException runtimeException = (RuntimeException) message.obj;
            String str = "Runtime exception on the transformation worker thread: ";
            String valueOf = String.valueOf(runtimeException.getMessage());
            Log.e("TransformedResultImpl", valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            throw runtimeException;
        }
    }
}
