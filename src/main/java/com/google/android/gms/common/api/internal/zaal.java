package com.google.android.gms.common.api.internal;

import androidx.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zaal extends zaau {
    final /* synthetic */ zaak zafz;
    private final Map<Client, zaam> zagn;

    public zaal(zaak zaak, Map<Client, zaam> map) {
        this.zafz = zaak;
        super(zaak, null);
        this.zagn = map;
    }

    @GuardedBy("mLock")
    @WorkerThread
    public final void zaal() {
        GoogleApiAvailabilityCache googleApiAvailabilityCache = new GoogleApiAvailabilityCache(this.zafz.zaey);
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        for (Client client : this.zagn.keySet()) {
            if (!client.requiresGooglePlayServices() || ((zaam) this.zagn.get(client)).zaee) {
                arrayList2.add(client);
            } else {
                arrayList.add(client);
            }
        }
        int i = -1;
        int i2 = 0;
        Object obj;
        if (!arrayList.isEmpty()) {
            ArrayList arrayList3 = (ArrayList) arrayList;
            int size = arrayList3.size();
            while (i2 < size) {
                obj = arrayList3.get(i2);
                i2++;
                i = googleApiAvailabilityCache.getClientAvailability(this.zafz.mContext, (Client) obj);
                if (i != 0) {
                    break;
                }
            }
        }
        ArrayList arrayList4 = (ArrayList) arrayList2;
        int size2 = arrayList4.size();
        while (i2 < size2) {
            obj = arrayList4.get(i2);
            i2++;
            i = googleApiAvailabilityCache.getClientAvailability(this.zafz.mContext, (Client) obj);
            if (i == 0) {
                break;
            }
        }
        if (i != 0) {
            this.zafz.zafv.zaa(new zaao(this, this.zafz, new ConnectionResult(i, null)));
            return;
        }
        if (this.zafz.zagh && this.zafz.zagf != null) {
            this.zafz.zagf.connect();
        }
        for (Client client2 : this.zagn.keySet()) {
            ConnectionProgressReportCallbacks connectionProgressReportCallbacks = (ConnectionProgressReportCallbacks) this.zagn.get(client2);
            if (!client2.requiresGooglePlayServices() || googleApiAvailabilityCache.getClientAvailability(this.zafz.mContext, client2) == 0) {
                client2.connect(connectionProgressReportCallbacks);
            } else {
                this.zafz.zafv.zaa(new zaan(this, this.zafz, connectionProgressReportCallbacks));
            }
        }
    }
}
