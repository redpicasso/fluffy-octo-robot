package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey;
import com.google.android.gms.tasks.TaskCompletionSource;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public abstract class UnregisterListenerMethod<A extends AnyClient, L> {
    private final ListenerKey<L> zajo;

    @KeepForSdk
    protected UnregisterListenerMethod(ListenerKey<L> listenerKey) {
        this.zajo = listenerKey;
    }

    @KeepForSdk
    protected abstract void unregisterListener(A a, TaskCompletionSource<Boolean> taskCompletionSource) throws RemoteException;

    @KeepForSdk
    public ListenerKey<L> getListenerKey() {
        return this.zajo;
    }
}
