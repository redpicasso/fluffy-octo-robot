package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zap implements ConnectionCallbacks, OnConnectionFailedListener {
    public final Api<?> mApi;
    private final boolean zaee;
    private zar zaef;

    public zap(Api<?> api, boolean z) {
        this.mApi = api;
        this.zaee = z;
    }

    public final void onConnected(@Nullable Bundle bundle) {
        zat();
        this.zaef.onConnected(bundle);
    }

    public final void onConnectionSuspended(int i) {
        zat();
        this.zaef.onConnectionSuspended(i);
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        zat();
        this.zaef.zaa(connectionResult, this.mApi, this.zaee);
    }

    public final void zaa(zar zar) {
        this.zaef = zar;
    }

    private final void zat() {
        Preconditions.checkNotNull(this.zaef, "Callbacks must be attached to a ClientConnectionHelper instance before connecting the client.");
    }
}
