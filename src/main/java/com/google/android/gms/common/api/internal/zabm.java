package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.GoogleApiManager.zaa;
import com.google.android.gms.common.internal.BaseGmsClient.SignOutCallbacks;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zabm implements SignOutCallbacks {
    final /* synthetic */ zaa zaiq;

    zabm(zaa zaa) {
        this.zaiq = zaa;
    }

    public final void onSignOutComplete() {
        GoogleApiManager.this.handler.post(new zabl(this));
    }
}
