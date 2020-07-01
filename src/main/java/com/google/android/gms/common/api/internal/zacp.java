package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.AnyClientKey;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zacp {
    public static final Status zalb = new Status(8, "The connection to Google Play services was lost");
    private static final BasePendingResult<?>[] zalc = new BasePendingResult[0];
    private final Map<AnyClientKey<?>, Client> zahd;
    @VisibleForTesting
    final Set<BasePendingResult<?>> zald = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
    private final zacq zale = new zaco(this);

    public zacp(Map<AnyClientKey<?>, Client> map) {
        this.zahd = map;
    }

    final void zac(BasePendingResult<? extends Result> basePendingResult) {
        this.zald.add(basePendingResult);
        basePendingResult.zaa(this.zale);
    }

    public final void release() {
        for (PendingResult pendingResult : (BasePendingResult[]) this.zald.toArray(zalc)) {
            zacq zacq = null;
            pendingResult.zaa(zacq);
            if (pendingResult.zal() != null) {
                pendingResult.setResultCallback(zacq);
                IBinder serviceBrokerBinder = ((Client) this.zahd.get(((ApiMethodImpl) pendingResult).getClientKey())).getServiceBrokerBinder();
                if (pendingResult.isReady()) {
                    pendingResult.zaa(new zacr(pendingResult, zacq, serviceBrokerBinder, zacq));
                } else if (serviceBrokerBinder == null || !serviceBrokerBinder.isBinderAlive()) {
                    pendingResult.zaa(zacq);
                    pendingResult.cancel();
                    zacq.remove(pendingResult.zal().intValue());
                } else {
                    zacq zacr = new zacr(pendingResult, zacq, serviceBrokerBinder, zacq);
                    pendingResult.zaa(zacr);
                    try {
                        serviceBrokerBinder.linkToDeath(zacr, 0);
                    } catch (RemoteException unused) {
                        pendingResult.cancel();
                        zacq.remove(pendingResult.zal().intValue());
                    }
                }
                this.zald.remove(pendingResult);
            } else if (pendingResult.zaq()) {
                this.zald.remove(pendingResult);
            }
        }
    }

    public final void zabv() {
        for (BasePendingResult zab : (BasePendingResult[]) this.zald.toArray(zalc)) {
            zab.zab(zalb);
        }
    }
}
