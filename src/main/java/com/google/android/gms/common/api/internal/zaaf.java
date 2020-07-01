package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.SimpleClientAdapter;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zaaf implements zabb {
    private final zabe zafv;
    private boolean zafw = false;

    public zaaf(zabe zabe) {
        this.zafv = zabe;
    }

    public final void begin() {
    }

    public final void onConnected(Bundle bundle) {
    }

    public final void zaa(ConnectionResult connectionResult, Api<?> api, boolean z) {
    }

    public final <A extends AnyClient, R extends Result, T extends ApiMethodImpl<R, A>> T enqueue(T t) {
        return execute(t);
    }

    public final <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T execute(T t) {
        try {
            this.zafv.zaeh.zahj.zac(t);
            zaaw zaaw = this.zafv.zaeh;
            AnyClient anyClient = (Client) zaaw.zahd.get(t.getClientKey());
            Preconditions.checkNotNull(anyClient, "Appropriate Api was not requested.");
            if (anyClient.isConnected() || !this.zafv.zaht.containsKey(t.getClientKey())) {
                if (anyClient instanceof SimpleClientAdapter) {
                    anyClient = ((SimpleClientAdapter) anyClient).getClient();
                }
                t.run(anyClient);
                return t;
            }
            t.setFailedResult(new Status(17));
            return t;
        } catch (DeadObjectException unused) {
            this.zafv.zaa(new zaai(this, this));
        }
    }

    public final boolean disconnect() {
        if (this.zafw) {
            return false;
        }
        if (this.zafv.zaeh.zaav()) {
            this.zafw = true;
            for (zack zabt : this.zafv.zaeh.zahi) {
                zabt.zabt();
            }
            return false;
        }
        this.zafv.zaf(null);
        return true;
    }

    public final void connect() {
        if (this.zafw) {
            this.zafw = false;
            this.zafv.zaa(new zaah(this, this));
        }
    }

    public final void onConnectionSuspended(int i) {
        this.zafv.zaf(null);
        this.zafv.zahx.zab(i, this.zafw);
    }

    final void zaak() {
        if (this.zafw) {
            this.zafw = false;
            this.zafv.zaeh.zahj.release();
            disconnect();
        }
    }
}
