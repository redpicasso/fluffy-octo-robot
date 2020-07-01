package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.RegistrationMethods.Builder;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zaca extends RegisterListenerMethod<A, L> {
    private final /* synthetic */ Builder zakk;

    zaca(Builder builder, ListenerHolder listenerHolder, Feature[] featureArr, boolean z) {
        this.zakk = builder;
        super(listenerHolder, featureArr, z);
    }

    protected final void registerListener(A a, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        this.zakk.zake.accept(a, taskCompletionSource);
    }
}
