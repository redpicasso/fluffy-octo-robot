package com.google.android.gms.internal.base;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public class zar extends Handler {
    private static volatile zaq zasi;

    public zar(Looper looper) {
        super(looper);
    }

    public zar(Looper looper, Callback callback) {
        super(looper, callback);
    }

    public final void dispatchMessage(Message message) {
        super.dispatchMessage(message);
    }
}
