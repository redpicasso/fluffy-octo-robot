package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zam {
    private final int zadm;
    private final ConnectionResult zadn;

    zam(ConnectionResult connectionResult, int i) {
        Preconditions.checkNotNull(connectionResult);
        this.zadn = connectionResult;
        this.zadm = i;
    }

    final int zap() {
        return this.zadm;
    }

    final ConnectionResult getConnectionResult() {
        return this.zadn;
    }
}