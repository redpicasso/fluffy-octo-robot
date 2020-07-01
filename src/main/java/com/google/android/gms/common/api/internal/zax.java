package com.google.android.gms.common.api.internal;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Collections;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zax implements OnCompleteListener<Map<ApiKey<?>, String>> {
    private final /* synthetic */ zav zafl;

    private zax(zav zav) {
        this.zafl = zav;
    }

    public final void onComplete(@NonNull Task<Map<ApiKey<?>, String>> task) {
        this.zafl.zaer.lock();
        try {
            if (this.zafl.zafe) {
                if (task.isSuccessful()) {
                    this.zafl.zaff = new ArrayMap(this.zafl.zaeu.size());
                    for (zaw apiKey : this.zafl.zaeu.values()) {
                        this.zafl.zaff.put(apiKey.getApiKey(), ConnectionResult.RESULT_SUCCESS);
                    }
                } else if (task.getException() instanceof AvailabilityException) {
                    AvailabilityException availabilityException = (AvailabilityException) task.getException();
                    if (this.zafl.zafc) {
                        this.zafl.zaff = new ArrayMap(this.zafl.zaeu.size());
                        for (GoogleApi googleApi : this.zafl.zaeu.values()) {
                            ApiKey apiKey2 = googleApi.getApiKey();
                            ConnectionResult connectionResult = availabilityException.getConnectionResult(googleApi);
                            if (this.zafl.zaa((zaw) googleApi, connectionResult)) {
                                this.zafl.zaff.put(apiKey2, new ConnectionResult(16));
                            } else {
                                this.zafl.zaff.put(apiKey2, connectionResult);
                            }
                        }
                    } else {
                        this.zafl.zaff = availabilityException.zaj();
                    }
                    this.zafl.zafi = this.zafl.zaac();
                } else {
                    Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                    this.zafl.zaff = Collections.emptyMap();
                    this.zafl.zafi = new ConnectionResult(8);
                }
                if (this.zafl.zafg != null) {
                    this.zafl.zaff.putAll(this.zafl.zafg);
                    this.zafl.zafi = this.zafl.zaac();
                }
                if (this.zafl.zafi == null) {
                    this.zafl.zaaa();
                    this.zafl.zaab();
                } else {
                    this.zafl.zafe = false;
                    this.zafl.zaex.zac(this.zafl.zafi);
                }
                this.zafl.zaez.signalAll();
                this.zafl.zaer.unlock();
            }
        } finally {
            this.zafl.zaer.unlock();
        }
    }
}
