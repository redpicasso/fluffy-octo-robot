package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zaan extends zabd {
    private final /* synthetic */ ConnectionProgressReportCallbacks zagp;

    zaan(zaal zaal, zabb zabb, ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        this.zagp = connectionProgressReportCallbacks;
        super(zabb);
    }

    @GuardedBy("mLock")
    public final void zaal() {
        this.zagp.onReportServiceBinding(new ConnectionResult(16, null));
    }
}
