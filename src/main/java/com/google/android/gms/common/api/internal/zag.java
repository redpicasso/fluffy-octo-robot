package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.internal.GoogleApiManager.zaa;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zag extends zae<Void> {
    private final RegisterListenerMethod<AnyClient, ?> zact;
    private final UnregisterListenerMethod<AnyClient, ?> zacu;

    public zag(zabv zabv, TaskCompletionSource<Void> taskCompletionSource) {
        super(3, taskCompletionSource);
        this.zact = zabv.zakc;
        this.zacu = zabv.zakd;
    }

    public final /* bridge */ /* synthetic */ void zaa(@NonNull zaz zaz, boolean z) {
    }

    public final void zad(zaa<?> zaa) throws RemoteException {
        this.zact.registerListener(zaa.zaad(), this.zacq);
        if (this.zact.getListenerKey() != null) {
            zaa.zabi().put(this.zact.getListenerKey(), new zabv(this.zact, this.zacu));
        }
    }

    @Nullable
    public final Feature[] zaa(zaa<?> zaa) {
        return this.zact.getRequiredFeatures();
    }

    public final boolean zab(zaa<?> zaa) {
        return this.zact.shouldAutoResolveMissingFeatures();
    }
}
