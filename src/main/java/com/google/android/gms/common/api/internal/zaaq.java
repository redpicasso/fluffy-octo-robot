package com.google.android.gms.common.api.internal;

import androidx.annotation.WorkerThread;
import com.google.android.gms.common.api.Api.Client;
import java.util.ArrayList;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zaaq extends zaau {
    private final /* synthetic */ zaak zafz;
    private final ArrayList<Client> zags;

    public zaaq(zaak zaak, ArrayList<Client> arrayList) {
        this.zafz = zaak;
        super(zaak, null);
        this.zags = arrayList;
    }

    @WorkerThread
    public final void zaal() {
        this.zafz.zafv.zaeh.zahe = this.zafz.zaar();
        ArrayList arrayList = this.zags;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Client) obj).getRemoteService(this.zafz.zagj, this.zafz.zafv.zaeh.zahe);
        }
    }
}
