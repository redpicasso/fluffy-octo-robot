package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey;
import com.google.android.gms.common.api.internal.RegistrationMethods.Builder;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zabz extends UnregisterListenerMethod<A, L> {
    private final /* synthetic */ Builder zakk;

    zabz(Builder builder, ListenerKey listenerKey) {
        this.zakk = builder;
        super(listenerKey);
    }

    protected final void unregisterListener(A a, TaskCompletionSource<Boolean> taskCompletionSource) throws RemoteException {
        this.zakk.zakf.accept(a, taskCompletionSource);
    }
}
