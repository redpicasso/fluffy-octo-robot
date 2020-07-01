package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zaao extends zabd {
    private final /* synthetic */ ConnectionResult zagq;
    private final /* synthetic */ zaal zagr;

    zaao(zaal zaal, zabb zabb, ConnectionResult connectionResult) {
        this.zagr = zaal;
        this.zagq = connectionResult;
        super(zabb);
    }

    @GuardedBy("mLock")
    public final void zaal() {
        this.zagr.zafz.zae(this.zagq);
    }
}
