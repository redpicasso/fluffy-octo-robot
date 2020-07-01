package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zas implements zabs {
    private final /* synthetic */ zaq zaet;

    private zas(zaq zaq) {
        this.zaet = zaq;
    }

    public final void zab(@Nullable Bundle bundle) {
        this.zaet.zaer.lock();
        try {
            this.zaet.zaa(bundle);
            this.zaet.zaeo = ConnectionResult.RESULT_SUCCESS;
            this.zaet.zav();
        } finally {
            this.zaet.zaer.unlock();
        }
    }

    public final void zac(@NonNull ConnectionResult connectionResult) {
        this.zaet.zaer.lock();
        try {
            this.zaet.zaeo = connectionResult;
            this.zaet.zav();
        } finally {
            this.zaet.zaer.unlock();
        }
    }

    public final void zab(int i, boolean z) {
        this.zaet.zaer.lock();
        try {
            if (this.zaet.zaeq || this.zaet.zaep == null || !this.zaet.zaep.isSuccess()) {
                this.zaet.zaeq = false;
                this.zaet.zaa(i, z);
                this.zaet.zaer.unlock();
                return;
            }
            this.zaet.zaeq = true;
            this.zaet.zaej.onConnectionSuspended(i);
        } finally {
            this.zaet.zaer.unlock();
        }
    }

    /* synthetic */ zas(zaq zaq, zat zat) {
        this(zaq);
    }
}
