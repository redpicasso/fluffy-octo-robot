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
final class zaaa implements OnCompleteListener<Map<ApiKey<?>, String>> {
    private final /* synthetic */ zav zafl;
    private SignInConnectionListener zafo;

    zaaa(zav zav, SignInConnectionListener signInConnectionListener) {
        this.zafl = zav;
        this.zafo = signInConnectionListener;
    }

    final void cancel() {
        this.zafo.onComplete();
    }

    public final void onComplete(@NonNull Task<Map<ApiKey<?>, String>> task) {
        this.zafl.zaer.lock();
        try {
            if (this.zafl.zafe) {
                if (task.isSuccessful()) {
                    this.zafl.zafg = new ArrayMap(this.zafl.zaev.size());
                    for (zaw apiKey : this.zafl.zaev.values()) {
                        this.zafl.zafg.put(apiKey.getApiKey(), ConnectionResult.RESULT_SUCCESS);
                    }
                } else if (task.getException() instanceof AvailabilityException) {
                    AvailabilityException availabilityException = (AvailabilityException) task.getException();
                    if (this.zafl.zafc) {
                        this.zafl.zafg = new ArrayMap(this.zafl.zaev.size());
                        for (GoogleApi googleApi : this.zafl.zaev.values()) {
                            ApiKey apiKey2 = googleApi.getApiKey();
                            ConnectionResult connectionResult = availabilityException.getConnectionResult(googleApi);
                            if (this.zafl.zaa((zaw) googleApi, connectionResult)) {
                                this.zafl.zafg.put(apiKey2, new ConnectionResult(16));
                            } else {
                                this.zafl.zafg.put(apiKey2, connectionResult);
                            }
                        }
                    } else {
                        this.zafl.zafg = availabilityException.zaj();
                    }
                } else {
                    Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                    this.zafl.zafg = Collections.emptyMap();
                }
                if (this.zafl.isConnected()) {
                    this.zafl.zaff.putAll(this.zafl.zafg);
                    if (this.zafl.zaac() == null) {
                        this.zafl.zaaa();
                        this.zafl.zaab();
                        this.zafl.zaez.signalAll();
                    }
                }
                this.zafo.onComplete();
                this.zafl.zaer.unlock();
                return;
            }
            this.zafo.onComplete();
        } finally {
            this.zafl.zaer.unlock();
        }
    }
}
