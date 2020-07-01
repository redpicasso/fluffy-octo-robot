package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zacj extends TaskApiCall<A, ResultT> {
    private final /* synthetic */ Builder zakq;

    zacj(Builder builder, Feature[] featureArr, boolean z) {
        this.zakq = builder;
        super(featureArr, z, null);
    }

    protected final void doExecute(A a, TaskCompletionSource<ResultT> taskCompletionSource) throws RemoteException {
        this.zakq.zakp.accept(a, taskCompletionSource);
    }
}
