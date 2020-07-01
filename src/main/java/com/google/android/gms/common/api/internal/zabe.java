package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.AnyClientKey;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zac;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zabe implements zabr, zar {
    private final Context mContext;
    private final AbstractClientBuilder<? extends zac, SignInOptions> zacf;
    final zaaw zaeh;
    private final Lock zaer;
    private final Map<Api<?>, Boolean> zaew;
    private final GoogleApiAvailabilityLight zaey;
    private final ClientSettings zafa;
    final Map<AnyClientKey<?>, Client> zahd;
    private final Condition zahr;
    private final zabg zahs;
    final Map<AnyClientKey<?>, ConnectionResult> zaht = new HashMap();
    private volatile zabb zahu;
    private ConnectionResult zahv = null;
    int zahw;
    final zabs zahx;

    public zabe(Context context, zaaw zaaw, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<AnyClientKey<?>, Client> map, ClientSettings clientSettings, Map<Api<?>, Boolean> map2, AbstractClientBuilder<? extends zac, SignInOptions> abstractClientBuilder, ArrayList<zap> arrayList, zabs zabs) {
        this.mContext = context;
        this.zaer = lock;
        this.zaey = googleApiAvailabilityLight;
        this.zahd = map;
        this.zafa = clientSettings;
        this.zaew = map2;
        this.zacf = abstractClientBuilder;
        this.zaeh = zaaw;
        this.zahx = zabs;
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            ((zap) obj).zaa(this);
        }
        this.zahs = new zabg(this, looper);
        this.zahr = lock.newCondition();
        this.zahu = new zaat(this);
    }

    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        return false;
    }

    public final void maybeSignOut() {
    }

    @GuardedBy("mLock")
    public final <A extends AnyClient, R extends Result, T extends ApiMethodImpl<R, A>> T enqueue(@NonNull T t) {
        t.zar();
        return this.zahu.enqueue(t);
    }

    @GuardedBy("mLock")
    public final <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t) {
        t.zar();
        return this.zahu.execute(t);
    }

    @GuardedBy("mLock")
    public final void connect() {
        this.zahu.connect();
    }

    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect() {
        connect();
        while (isConnecting()) {
            try {
                this.zahr.await();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        if (isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        ConnectionResult connectionResult = this.zahv;
        if (connectionResult != null) {
            return connectionResult;
        }
        return new ConnectionResult(13, null);
    }

    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect(long j, TimeUnit timeUnit) {
        connect();
        j = timeUnit.toNanos(j);
        while (isConnecting()) {
            if (j <= 0) {
                try {
                    disconnect();
                    return new ConnectionResult(14, null);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                    return new ConnectionResult(15, null);
                }
            }
            j = this.zahr.awaitNanos(j);
        }
        if (isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        ConnectionResult connectionResult = this.zahv;
        if (connectionResult != null) {
            return connectionResult;
        }
        return new ConnectionResult(13, null);
    }

    @GuardedBy("mLock")
    public final void disconnect() {
        if (this.zahu.disconnect()) {
            this.zaht.clear();
        }
    }

    @GuardedBy("mLock")
    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        AnyClientKey clientKey = api.getClientKey();
        if (this.zahd.containsKey(clientKey)) {
            if (((Client) this.zahd.get(clientKey)).isConnected()) {
                return ConnectionResult.RESULT_SUCCESS;
            }
            if (this.zaht.containsKey(clientKey)) {
                return (ConnectionResult) this.zaht.get(clientKey);
            }
        }
        return null;
    }

    final void zaax() {
        this.zaer.lock();
        try {
            this.zahu = new zaak(this, this.zafa, this.zaew, this.zaey, this.zacf, this.zaer, this.mContext);
            this.zahu.begin();
            this.zahr.signalAll();
        } finally {
            this.zaer.unlock();
        }
    }

    final void zaay() {
        this.zaer.lock();
        try {
            this.zaeh.zaau();
            this.zahu = new zaaf(this);
            this.zahu.begin();
            this.zahr.signalAll();
        } finally {
            this.zaer.unlock();
        }
    }

    final void zaf(ConnectionResult connectionResult) {
        this.zaer.lock();
        try {
            this.zahv = connectionResult;
            this.zahu = new zaat(this);
            this.zahu.begin();
            this.zahr.signalAll();
        } finally {
            this.zaer.unlock();
        }
    }

    public final boolean isConnected() {
        return this.zahu instanceof zaaf;
    }

    public final boolean isConnecting() {
        return this.zahu instanceof zaak;
    }

    @GuardedBy("mLock")
    public final void zau() {
        if (isConnected()) {
            ((zaaf) this.zahu).zaak();
        }
    }

    public final void zaa(@NonNull ConnectionResult connectionResult, @NonNull Api<?> api, boolean z) {
        this.zaer.lock();
        try {
            this.zahu.zaa(connectionResult, api, z);
        } finally {
            this.zaer.unlock();
        }
    }

    public final void onConnected(@Nullable Bundle bundle) {
        this.zaer.lock();
        try {
            this.zahu.onConnected(bundle);
        } finally {
            this.zaer.unlock();
        }
    }

    public final void onConnectionSuspended(int i) {
        this.zaer.lock();
        try {
            this.zahu.onConnectionSuspended(i);
        } finally {
            this.zaer.unlock();
        }
    }

    final void zaa(zabd zabd) {
        this.zahs.sendMessage(this.zahs.obtainMessage(1, zabd));
    }

    final void zab(RuntimeException runtimeException) {
        this.zahs.sendMessage(this.zahs.obtainMessage(2, runtimeException));
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String concat = String.valueOf(str).concat("  ");
        printWriter.append(str).append("mState=").println(this.zahu);
        for (Api api : this.zaew.keySet()) {
            printWriter.append(str).append(api.getName()).println(":");
            ((Client) this.zahd.get(api.getClientKey())).dump(concat, fileDescriptor, printWriter, strArr);
        }
    }
}
