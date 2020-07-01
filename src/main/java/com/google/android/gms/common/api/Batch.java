package com.google.android.gms.common.api;

import com.google.android.gms.common.api.internal.BasePendingResult;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class Batch extends BasePendingResult<BatchResult> {
    private final Object mLock;
    private int zabc;
    private boolean zabd;
    private boolean zabe;
    private final PendingResult<?>[] zabf;

    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    public static final class Builder {
        private List<PendingResult<?>> zabg = new ArrayList();
        private GoogleApiClient zabh;

        public Builder(GoogleApiClient googleApiClient) {
            this.zabh = googleApiClient;
        }

        public final <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken<R> batchResultToken = new BatchResultToken(this.zabg.size());
            this.zabg.add(pendingResult);
            return batchResultToken;
        }

        public final Batch build() {
            return new Batch(this.zabg, this.zabh, null);
        }
    }

    private Batch(List<PendingResult<?>> list, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.mLock = new Object();
        this.zabc = list.size();
        this.zabf = new PendingResult[this.zabc];
        if (list.isEmpty()) {
            setResult(new BatchResult(Status.RESULT_SUCCESS, this.zabf));
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            PendingResult pendingResult = (PendingResult) list.get(i);
            this.zabf[i] = pendingResult;
            pendingResult.addStatusListener(new zaa(this));
        }
    }

    public final void cancel() {
        super.zae();
        for (PendingResult cancel : this.zabf) {
            cancel.cancel();
        }
    }

    public final BatchResult createFailedResult(Status status) {
        return new BatchResult(status, this.zabf);
    }

    /* synthetic */ Batch(List list, GoogleApiClient googleApiClient, zaa zaa) {
        this(list, googleApiClient);
    }
}
