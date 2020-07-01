package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.common.api.internal.IStatusCallback.Stub;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public class StatusCallback extends Stub {
    @KeepForSdk
    private final ResultHolder<Status> mResultHolder;

    @KeepForSdk
    public StatusCallback(ResultHolder<Status> resultHolder) {
        this.mResultHolder = resultHolder;
    }

    @KeepForSdk
    public void onResult(Status status) {
        this.mResultHolder.setResult(status);
    }
}
