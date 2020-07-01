package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.BinderThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.internal.zad;
import com.google.android.gms.signin.internal.zak;
import com.google.android.gms.signin.zab;
import com.google.android.gms.signin.zac;
import java.util.Set;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zace extends zad implements ConnectionCallbacks, OnConnectionFailedListener {
    private static AbstractClientBuilder<? extends zac, SignInOptions> zakm = zab.zapv;
    private final Context mContext;
    private final Handler mHandler;
    private Set<Scope> mScopes;
    private final AbstractClientBuilder<? extends zac, SignInOptions> zaaw;
    private ClientSettings zafa;
    private zac zagf;
    private zacf zakn;

    @WorkerThread
    public zace(Context context, Handler handler, @NonNull ClientSettings clientSettings) {
        this(context, handler, clientSettings, zakm);
    }

    @WorkerThread
    public zace(Context context, Handler handler, @NonNull ClientSettings clientSettings, AbstractClientBuilder<? extends zac, SignInOptions> abstractClientBuilder) {
        this.mContext = context;
        this.mHandler = handler;
        this.zafa = (ClientSettings) Preconditions.checkNotNull(clientSettings, "ClientSettings must not be null");
        this.mScopes = clientSettings.getRequiredScopes();
        this.zaaw = abstractClientBuilder;
    }

    @WorkerThread
    public final void zaa(zacf zacf) {
        zac zac = this.zagf;
        if (zac != null) {
            zac.disconnect();
        }
        this.zafa.setClientSessionId(Integer.valueOf(System.identityHashCode(this)));
        AbstractClientBuilder abstractClientBuilder = this.zaaw;
        Context context = this.mContext;
        Looper looper = this.mHandler.getLooper();
        ClientSettings clientSettings = this.zafa;
        this.zagf = (zac) abstractClientBuilder.buildClient(context, looper, clientSettings, clientSettings.getSignInOptions(), (ConnectionCallbacks) this, (OnConnectionFailedListener) this);
        this.zakn = zacf;
        Set set = this.mScopes;
        if (set == null || set.isEmpty()) {
            this.mHandler.post(new zacd(this));
        } else {
            this.zagf.connect();
        }
    }

    public final zac zabo() {
        return this.zagf;
    }

    public final void zabq() {
        zac zac = this.zagf;
        if (zac != null) {
            zac.disconnect();
        }
    }

    @WorkerThread
    public final void onConnected(@Nullable Bundle bundle) {
        this.zagf.zaa(this);
    }

    @WorkerThread
    public final void onConnectionSuspended(int i) {
        this.zagf.disconnect();
    }

    @WorkerThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zakn.zag(connectionResult);
    }

    @BinderThread
    public final void zab(zak zak) {
        this.mHandler.post(new zacg(this, zak));
    }

    @WorkerThread
    private final void zac(zak zak) {
        ConnectionResult connectionResult = zak.getConnectionResult();
        if (connectionResult.isSuccess()) {
            ResolveAccountResponse zacv = zak.zacv();
            connectionResult = zacv.getConnectionResult();
            if (connectionResult.isSuccess()) {
                this.zakn.zaa(zacv.getAccountAccessor(), this.mScopes);
            } else {
                String valueOf = String.valueOf(connectionResult);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 48);
                stringBuilder.append("Sign-in succeeded with resolve account failure: ");
                stringBuilder.append(valueOf);
                Log.wtf("SignInCoordinator", stringBuilder.toString(), new Exception());
                this.zakn.zag(connectionResult);
                this.zagf.disconnect();
                return;
            }
        }
        this.zakn.zag(connectionResult);
        this.zagf.disconnect();
    }
}
