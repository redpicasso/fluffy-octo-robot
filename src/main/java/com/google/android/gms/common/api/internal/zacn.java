package com.google.android.gms.common.api.internal;

import androidx.annotation.WorkerThread;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zacn implements Runnable {
    private final /* synthetic */ zack zaky;
    private final /* synthetic */ Result zakz;

    zacn(zack zack, Result result) {
        this.zaky = zack;
        this.zakz = result;
    }

    @WorkerThread
    public final void run() {
        GoogleApiClient googleApiClient;
        try {
            BasePendingResult.zado.set(Boolean.valueOf(true));
            this.zaky.zakw.sendMessage(this.zaky.zakw.obtainMessage(0, this.zaky.zakr.onSuccess(this.zakz)));
            BasePendingResult.zado.set(Boolean.valueOf(false));
            zack.zab(this.zakz);
            googleApiClient = (GoogleApiClient) this.zaky.zadr.get();
            if (googleApiClient != null) {
                googleApiClient.zab(this.zaky);
            }
        } catch (RuntimeException e) {
            this.zaky.zakw.sendMessage(this.zaky.zakw.obtainMessage(1, e));
            BasePendingResult.zado.set(Boolean.valueOf(false));
            zack.zab(this.zakz);
            googleApiClient = (GoogleApiClient) this.zaky.zadr.get();
            if (googleApiClient != null) {
                googleApiClient.zab(this.zaky);
            }
        } catch (Throwable th) {
            BasePendingResult.zado.set(Boolean.valueOf(false));
            zack.zab(this.zakz);
            GoogleApiClient googleApiClient2 = (GoogleApiClient) this.zaky.zadr.get();
            if (googleApiClient2 != null) {
                googleApiClient2.zab(this.zaky);
            }
            throw th;
        }
    }
}
