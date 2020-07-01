package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.AnyClientKey;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.ClientSettings.OptionalApiSettings;
import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zac;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zav implements zabr {
    private final Looper zabl;
    private final GoogleApiManager zabo;
    private final Lock zaer;
    private final Map<AnyClientKey<?>, zaw<?>> zaeu = new HashMap();
    private final Map<AnyClientKey<?>, zaw<?>> zaev = new HashMap();
    private final Map<Api<?>, Boolean> zaew;
    private final zaaw zaex;
    private final GoogleApiAvailabilityLight zaey;
    private final Condition zaez;
    private final ClientSettings zafa;
    private final boolean zafb;
    private final boolean zafc;
    private final Queue<ApiMethodImpl<?, ?>> zafd = new LinkedList();
    @GuardedBy("mLock")
    private boolean zafe;
    @GuardedBy("mLock")
    private Map<ApiKey<?>, ConnectionResult> zaff;
    @GuardedBy("mLock")
    private Map<ApiKey<?>, ConnectionResult> zafg;
    @GuardedBy("mLock")
    private zaaa zafh;
    @GuardedBy("mLock")
    private ConnectionResult zafi;

    public zav(Context context, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<AnyClientKey<?>, Client> map, ClientSettings clientSettings, Map<Api<?>, Boolean> map2, AbstractClientBuilder<? extends zac, SignInOptions> abstractClientBuilder, ArrayList<zap> arrayList, zaaw zaaw, boolean z) {
        this.zaer = lock;
        this.zabl = looper;
        this.zaez = lock.newCondition();
        this.zaey = googleApiAvailabilityLight;
        this.zaex = zaaw;
        this.zaew = map2;
        this.zafa = clientSettings;
        this.zafb = z;
        Map hashMap = new HashMap();
        for (Api api : map2.keySet()) {
            hashMap.put(api.getClientKey(), api);
        }
        Map hashMap2 = new HashMap();
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            zap zap = (zap) obj;
            hashMap2.put(zap.mApi, zap);
        }
        boolean z2 = true;
        Object obj2 = null;
        Object obj3 = 1;
        Object obj4 = null;
        for (Entry entry : map.entrySet()) {
            Object obj5;
            Object obj6;
            Object obj7;
            Api api2 = (Api) hashMap.get(entry.getKey());
            Client client = (Client) entry.getValue();
            if (client.requiresGooglePlayServices()) {
                if (((Boolean) this.zaew.get(api2)).booleanValue()) {
                    obj5 = obj3;
                    obj6 = obj4;
                } else {
                    obj5 = obj3;
                    obj6 = 1;
                }
                obj7 = 1;
            } else {
                obj7 = obj2;
                obj6 = obj4;
                obj5 = null;
            }
            zaw zaw = r1;
            zaw zaw2 = new zaw(context, api2, looper, client, (zap) hashMap2.get(api2), clientSettings, abstractClientBuilder);
            this.zaeu.put((AnyClientKey) entry.getKey(), zaw);
            if (client.requiresSignIn()) {
                this.zaev.put((AnyClientKey) entry.getKey(), zaw);
            }
            obj4 = obj6;
            obj3 = obj5;
            obj2 = obj7;
        }
        if (!(obj2 != null && obj3 == null && obj4 == null)) {
            z2 = false;
        }
        this.zafc = z2;
        this.zabo = GoogleApiManager.zaba();
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public final void zau() {
    }

    public final <A extends AnyClient, R extends Result, T extends ApiMethodImpl<R, A>> T enqueue(@NonNull T t) {
        if (this.zafb && zab((ApiMethodImpl) t)) {
            return t;
        }
        if (isConnected()) {
            this.zaex.zahj.zac(t);
            return ((zaw) this.zaeu.get(t.getClientKey())).doRead((ApiMethodImpl) t);
        }
        this.zafd.add(t);
        return t;
    }

    public final <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t) {
        AnyClientKey clientKey = t.getClientKey();
        if (this.zafb && zab((ApiMethodImpl) t)) {
            return t;
        }
        this.zaex.zahj.zac(t);
        return ((zaw) this.zaeu.get(clientKey)).doWrite((ApiMethodImpl) t);
    }

    private final <T extends ApiMethodImpl<? extends Result, ? extends AnyClient>> boolean zab(@NonNull T t) {
        AnyClientKey clientKey = t.getClientKey();
        ConnectionResult zaa = zaa(clientKey);
        if (zaa == null || zaa.getErrorCode() != 4) {
            return false;
        }
        t.setFailedResult(new Status(4, null, this.zabo.zaa(((zaw) this.zaeu.get(clientKey)).getApiKey(), System.identityHashCode(this.zaex))));
        return true;
    }

    public final void connect() {
        this.zaer.lock();
        try {
            if (!this.zafe) {
                this.zafe = true;
                this.zaff = null;
                this.zafg = null;
                this.zafh = null;
                this.zafi = null;
                this.zabo.zam();
                this.zabo.zaa(this.zaeu.values()).addOnCompleteListener(new HandlerExecutor(this.zabl), new zax(this, null));
                this.zaer.unlock();
            }
        } finally {
            this.zaer.unlock();
        }
    }

    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect() {
        connect();
        while (isConnecting()) {
            try {
                this.zaez.await();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        if (isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        ConnectionResult connectionResult = this.zafi;
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
            j = this.zaez.awaitNanos(j);
        }
        if (isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        ConnectionResult connectionResult = this.zafi;
        if (connectionResult != null) {
            return connectionResult;
        }
        return new ConnectionResult(13, null);
    }

    public final void disconnect() {
        this.zaer.lock();
        try {
            this.zafe = false;
            this.zaff = null;
            this.zafg = null;
            if (this.zafh != null) {
                this.zafh.cancel();
                this.zafh = null;
            }
            this.zafi = null;
            while (!this.zafd.isEmpty()) {
                ApiMethodImpl apiMethodImpl = (ApiMethodImpl) this.zafd.remove();
                apiMethodImpl.zaa(null);
                apiMethodImpl.cancel();
            }
            this.zaez.signalAll();
        } finally {
            this.zaer.unlock();
        }
    }

    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        return zaa(api.getClientKey());
    }

    @Nullable
    private final ConnectionResult zaa(@NonNull AnyClientKey<?> anyClientKey) {
        this.zaer.lock();
        try {
            zaw zaw = (zaw) this.zaeu.get(anyClientKey);
            if (this.zaff == null || zaw == null) {
                this.zaer.unlock();
                return null;
            }
            ConnectionResult connectionResult = (ConnectionResult) this.zaff.get(zaw.getApiKey());
            return connectionResult;
        } finally {
            this.zaer.unlock();
        }
    }

    public final boolean isConnected() {
        this.zaer.lock();
        try {
            boolean z = this.zaff != null && this.zafi == null;
            this.zaer.unlock();
            return z;
        } catch (Throwable th) {
            this.zaer.unlock();
        }
    }

    public final boolean isConnecting() {
        this.zaer.lock();
        try {
            boolean z = this.zaff == null && this.zafe;
            this.zaer.unlock();
            return z;
        } catch (Throwable th) {
            this.zaer.unlock();
        }
    }

    private final boolean zaz() {
        this.zaer.lock();
        try {
            if (this.zafe && this.zafb) {
                for (AnyClientKey zaa : this.zaev.keySet()) {
                    ConnectionResult zaa2 = zaa(zaa);
                    if (zaa2 != null) {
                        if (!zaa2.isSuccess()) {
                        }
                    }
                    this.zaer.unlock();
                    return false;
                }
                this.zaer.unlock();
                return true;
            }
            this.zaer.unlock();
            return false;
        } catch (Throwable th) {
            this.zaer.unlock();
        }
    }

    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        this.zaer.lock();
        try {
            if (!this.zafe || zaz()) {
                this.zaer.unlock();
                return false;
            }
            this.zabo.zam();
            this.zafh = new zaaa(this, signInConnectionListener);
            this.zabo.zaa(this.zaev.values()).addOnCompleteListener(new HandlerExecutor(this.zabl), this.zafh);
            return true;
        } finally {
            this.zaer.unlock();
        }
    }

    public final void maybeSignOut() {
        this.zaer.lock();
        try {
            this.zabo.maybeSignOut();
            if (this.zafh != null) {
                this.zafh.cancel();
                this.zafh = null;
            }
            if (this.zafg == null) {
                this.zafg = new ArrayMap(this.zaev.size());
            }
            ConnectionResult connectionResult = new ConnectionResult(4);
            for (zaw apiKey : this.zaev.values()) {
                this.zafg.put(apiKey.getApiKey(), connectionResult);
            }
            if (this.zaff != null) {
                this.zaff.putAll(this.zafg);
            }
            this.zaer.unlock();
        } catch (Throwable th) {
            this.zaer.unlock();
        }
    }

    @GuardedBy("mLock")
    private final void zaaa() {
        ClientSettings clientSettings = this.zafa;
        if (clientSettings == null) {
            this.zaex.zahe = Collections.emptySet();
            return;
        }
        Set hashSet = new HashSet(clientSettings.getRequiredScopes());
        Map optionalApiSettings = this.zafa.getOptionalApiSettings();
        for (Api api : optionalApiSettings.keySet()) {
            ConnectionResult connectionResult = getConnectionResult(api);
            if (connectionResult != null && connectionResult.isSuccess()) {
                hashSet.addAll(((OptionalApiSettings) optionalApiSettings.get(api)).mScopes);
            }
        }
        this.zaex.zahe = hashSet;
    }

    @GuardedBy("mLock")
    private final void zaab() {
        while (!this.zafd.isEmpty()) {
            execute((ApiMethodImpl) this.zafd.remove());
        }
        this.zaex.zab(null);
    }

    private final boolean zaa(zaw<?> zaw, ConnectionResult connectionResult) {
        return !connectionResult.isSuccess() && !connectionResult.hasResolution() && ((Boolean) this.zaew.get(zaw.getApi())).booleanValue() && zaw.zaad().requiresGooglePlayServices() && this.zaey.isUserResolvableError(connectionResult.getErrorCode());
    }

    @GuardedBy("mLock")
    @Nullable
    private final ConnectionResult zaac() {
        ConnectionResult connectionResult = null;
        ConnectionResult connectionResult2 = null;
        int i = 0;
        int i2 = 0;
        for (zaw zaw : this.zaeu.values()) {
            Api api = zaw.getApi();
            ConnectionResult connectionResult3 = (ConnectionResult) this.zaff.get(zaw.getApiKey());
            if (!connectionResult3.isSuccess() && (!((Boolean) this.zaew.get(api)).booleanValue() || connectionResult3.hasResolution() || this.zaey.isUserResolvableError(connectionResult3.getErrorCode()))) {
                int priority;
                if (connectionResult3.getErrorCode() == 4 && this.zafb) {
                    priority = api.zah().getPriority();
                    if (connectionResult2 == null || i2 > priority) {
                        connectionResult2 = connectionResult3;
                        i2 = priority;
                    }
                } else {
                    priority = api.zah().getPriority();
                    if (connectionResult == null || i > priority) {
                        connectionResult = connectionResult3;
                        i = priority;
                    }
                }
            }
        }
        return (connectionResult == null || connectionResult2 == null || i <= i2) ? connectionResult : connectionResult2;
    }
}
