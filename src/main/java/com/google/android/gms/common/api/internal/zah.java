package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.GoogleApiManager.zaa;
import com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zah extends zae<Boolean> {
    private final ListenerKey<?> zacv;

    public zah(ListenerKey<?> listenerKey, TaskCompletionSource<Boolean> taskCompletionSource) {
        super(4, taskCompletionSource);
        this.zacv = listenerKey;
    }

    public final /* bridge */ /* synthetic */ void zaa(@NonNull zaz zaz, boolean z) {
    }

    public final void zad(zaa<?> zaa) throws RemoteException {
        zabv zabv = (zabv) zaa.zabi().remove(this.zacv);
        if (zabv != null) {
            zabv.zakd.unregisterListener(zaa.zaad(), this.zacq);
            zabv.zakc.clearListener();
            return;
        }
        this.zacq.trySetResult(Boolean.valueOf(false));
    }

    @Nullable
    public final Feature[] zaa(zaa<?> zaa) {
        zabv zabv = (zabv) zaa.zabi().get(this.zacv);
        if (zabv == null) {
            return null;
        }
        return zabv.zakc.getRequiredFeatures();
    }

    public final boolean zab(zaa<?> zaa) {
        zabv zabv = (zabv) zaa.zabi().get(this.zacv);
        return zabv != null && zabv.zakc.shouldAutoResolveMissingFeatures();
    }
}
