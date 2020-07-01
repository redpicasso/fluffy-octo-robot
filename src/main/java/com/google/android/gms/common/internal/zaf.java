package com.google.android.gms.common.internal;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zaf implements BaseConnectionCallbacks {
    private final /* synthetic */ ConnectionCallbacks zaou;

    zaf(ConnectionCallbacks connectionCallbacks) {
        this.zaou = connectionCallbacks;
    }

    public final void onConnected(@Nullable Bundle bundle) {
        this.zaou.onConnected(bundle);
    }

    public final void onConnectionSuspended(int i) {
        this.zaou.onConnectionSuspended(i);
    }
}
