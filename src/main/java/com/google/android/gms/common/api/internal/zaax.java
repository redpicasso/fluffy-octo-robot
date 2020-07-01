package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zaax implements OnConnectionFailedListener {
    private final /* synthetic */ StatusPendingResult zahl;

    zaax(zaaw zaaw, StatusPendingResult statusPendingResult) {
        this.zahl = statusPendingResult;
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zahl.setResult(new Status(8));
    }
}
