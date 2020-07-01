package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResult.StatusListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.PendingResultUtil.ResultConverter;
import com.google.android.gms.common.internal.PendingResultUtil.zaa;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zak implements StatusListener {
    private final /* synthetic */ PendingResult zaph;
    private final /* synthetic */ TaskCompletionSource zapi;
    private final /* synthetic */ ResultConverter zapj;
    private final /* synthetic */ zaa zapk;

    zak(PendingResult pendingResult, TaskCompletionSource taskCompletionSource, ResultConverter resultConverter, zaa zaa) {
        this.zaph = pendingResult;
        this.zapi = taskCompletionSource;
        this.zapj = resultConverter;
        this.zapk = zaa;
    }

    public final void onComplete(Status status) {
        if (status.isSuccess()) {
            this.zapi.setResult(this.zapj.convert(this.zaph.await(0, TimeUnit.MILLISECONDS)));
            return;
        }
        this.zapi.setException(this.zapk.zaf(status));
    }
}
