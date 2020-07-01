package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
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
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.base.zar;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zac;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zaq implements zabr {
    private final Context mContext;
    private final Looper zabl;
    private final zaaw zaeh;
    private final zabe zaei;
    private final zabe zaej;
    private final Map<AnyClientKey<?>, zabe> zaek;
    private final Set<SignInConnectionListener> zael = Collections.newSetFromMap(new WeakHashMap());
    private final Client zaem;
    private Bundle zaen;
    private ConnectionResult zaeo = null;
    private ConnectionResult zaep = null;
    private boolean zaeq = false;
    private final Lock zaer;
    @GuardedBy("mLock")
    private int zaes = 0;

    public static zaq zaa(Context context, zaaw zaaw, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<AnyClientKey<?>, Client> map, ClientSettings clientSettings, Map<Api<?>, Boolean> map2, AbstractClientBuilder<? extends zac, SignInOptions> abstractClientBuilder, ArrayList<zap> arrayList) {
        Map<Api<?>, Boolean> map3 = map2;
        Map arrayMap = new ArrayMap();
        Map arrayMap2 = new ArrayMap();
        Client client = null;
        for (Entry entry : map.entrySet()) {
            Client client2 = (Client) entry.getValue();
            if (client2.providesSignIn()) {
                client = client2;
            }
            if (client2.requiresSignIn()) {
                arrayMap.put((AnyClientKey) entry.getKey(), client2);
            } else {
                arrayMap2.put((AnyClientKey) entry.getKey(), client2);
            }
        }
        Preconditions.checkState(arrayMap.isEmpty() ^ 1, "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        Map arrayMap3 = new ArrayMap();
        Map arrayMap4 = new ArrayMap();
        for (Api api : map2.keySet()) {
            AnyClientKey clientKey = api.getClientKey();
            if (arrayMap.containsKey(clientKey)) {
                arrayMap3.put(api, (Boolean) map3.get(api));
            } else if (arrayMap2.containsKey(clientKey)) {
                arrayMap4.put(api, (Boolean) map3.get(api));
            } else {
                throw new IllegalStateException("Each API in the isOptionalMap must have a corresponding client in the clients map.");
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = arrayList;
        int size = arrayList4.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList4.get(i);
            i++;
            zap zap = (zap) obj;
            if (arrayMap3.containsKey(zap.mApi)) {
                arrayList2.add(zap);
            } else if (arrayMap4.containsKey(zap.mApi)) {
                arrayList3.add(zap);
            } else {
                throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the isOptionalMap");
            }
        }
        return new zaq(context, zaaw, lock, looper, googleApiAvailabilityLight, arrayMap, arrayMap2, clientSettings, abstractClientBuilder, client, arrayList2, arrayList3, arrayMap3, arrayMap4);
    }

    private zaq(Context context, zaaw zaaw, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<AnyClientKey<?>, Client> map, Map<AnyClientKey<?>, Client> map2, ClientSettings clientSettings, AbstractClientBuilder<? extends zac, SignInOptions> abstractClientBuilder, Client client, ArrayList<zap> arrayList, ArrayList<zap> arrayList2, Map<Api<?>, Boolean> map3, Map<Api<?>, Boolean> map4) {
        this.mContext = context;
        this.zaeh = zaaw;
        this.zaer = lock;
        this.zabl = looper;
        this.zaem = client;
        Context context2 = context;
        Lock lock2 = lock;
        Looper looper2 = looper;
        GoogleApiAvailabilityLight googleApiAvailabilityLight2 = googleApiAvailabilityLight;
        zabe zabe = r3;
        zabe zabe2 = new zabe(context2, this.zaeh, lock2, looper2, googleApiAvailabilityLight2, map2, null, map4, null, arrayList2, new zas(this, null));
        this.zaei = zabe;
        this.zaej = new zabe(context2, this.zaeh, lock2, looper2, googleApiAvailabilityLight2, map, clientSettings, map3, abstractClientBuilder, arrayList, new zau(this, null));
        Map arrayMap = new ArrayMap();
        for (AnyClientKey put : map2.keySet()) {
            arrayMap.put(put, this.zaei);
        }
        for (AnyClientKey put2 : map.keySet()) {
            arrayMap.put(put2, this.zaej);
        }
        this.zaek = Collections.unmodifiableMap(arrayMap);
    }

    @GuardedBy("mLock")
    public final <A extends AnyClient, R extends Result, T extends ApiMethodImpl<R, A>> T enqueue(@NonNull T t) {
        if (!zaa((ApiMethodImpl) t)) {
            return this.zaei.enqueue(t);
        }
        if (!zax()) {
            return this.zaej.enqueue(t);
        }
        t.setFailedResult(new Status(4, null, zay()));
        return t;
    }

    @GuardedBy("mLock")
    public final <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t) {
        if (!zaa((ApiMethodImpl) t)) {
            return this.zaei.execute(t);
        }
        if (!zax()) {
            return this.zaej.execute(t);
        }
        t.setFailedResult(new Status(4, null, zay()));
        return t;
    }

    @GuardedBy("mLock")
    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        if (!((zabe) this.zaek.get(api.getClientKey())).equals(this.zaej)) {
            return this.zaei.getConnectionResult(api);
        }
        if (zax()) {
            return new ConnectionResult(4, zay());
        }
        return this.zaej.getConnectionResult(api);
    }

    @GuardedBy("mLock")
    public final void connect() {
        this.zaes = 2;
        this.zaeq = false;
        this.zaep = null;
        this.zaeo = null;
        this.zaei.connect();
        this.zaej.connect();
    }

    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("mLock")
    public final void disconnect() {
        this.zaep = null;
        this.zaeo = null;
        this.zaes = 0;
        this.zaei.disconnect();
        this.zaej.disconnect();
        zaw();
    }

    public final boolean isConnected() {
        this.zaer.lock();
        try {
            boolean z = true;
            if (!(this.zaei.isConnected() && (this.zaej.isConnected() || zax() || this.zaes == 1))) {
                z = false;
            }
            this.zaer.unlock();
            return z;
        } catch (Throwable th) {
            this.zaer.unlock();
        }
    }

    public final boolean isConnecting() {
        this.zaer.lock();
        try {
            boolean z = this.zaes == 2;
            this.zaer.unlock();
            return z;
        } catch (Throwable th) {
            this.zaer.unlock();
        }
    }

    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        this.zaer.lock();
        boolean z;
        try {
            if ((isConnecting() || isConnected()) && !this.zaej.isConnected()) {
                this.zael.add(signInConnectionListener);
                z = true;
                if (this.zaes == 0) {
                    this.zaes = 1;
                }
                this.zaep = null;
                this.zaej.connect();
                return z;
            }
            this.zaer.unlock();
            return false;
        } finally {
            z = this.zaer;
            z.unlock();
        }
    }

    @GuardedBy("mLock")
    public final void zau() {
        this.zaei.zau();
        this.zaej.zau();
    }

    public final void maybeSignOut() {
        this.zaer.lock();
        try {
            boolean isConnecting = isConnecting();
            this.zaej.disconnect();
            this.zaep = new ConnectionResult(4);
            if (isConnecting) {
                new zar(this.zabl).post(new zat(this));
            } else {
                zaw();
            }
            this.zaer.unlock();
        } catch (Throwable th) {
            this.zaer.unlock();
        }
    }

    @GuardedBy("mLock")
    private final void zav() {
        ConnectionResult connectionResult;
        if (zab(this.zaeo)) {
            if (zab(this.zaep) || zax()) {
                int i = this.zaes;
                if (i != 1) {
                    if (i != 2) {
                        Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
                        this.zaes = 0;
                        return;
                    }
                    this.zaeh.zab(this.zaen);
                }
                zaw();
                this.zaes = 0;
                return;
            }
            connectionResult = this.zaep;
            if (connectionResult != null) {
                if (this.zaes == 1) {
                    zaw();
                    return;
                }
                zaa(connectionResult);
                this.zaei.disconnect();
            }
        } else if (this.zaeo == null || !zab(this.zaep)) {
            connectionResult = this.zaeo;
            if (!(connectionResult == null || this.zaep == null)) {
                if (this.zaej.zahw < this.zaei.zahw) {
                    connectionResult = this.zaep;
                }
                zaa(connectionResult);
            }
        } else {
            this.zaej.disconnect();
            zaa(this.zaeo);
        }
    }

    @GuardedBy("mLock")
    private final void zaa(ConnectionResult connectionResult) {
        int i = this.zaes;
        if (i != 1) {
            if (i != 2) {
                Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
                this.zaes = 0;
            }
            this.zaeh.zac(connectionResult);
        }
        zaw();
        this.zaes = 0;
    }

    @GuardedBy("mLock")
    private final void zaw() {
        for (SignInConnectionListener onComplete : this.zael) {
            onComplete.onComplete();
        }
        this.zael.clear();
    }

    @GuardedBy("mLock")
    private final void zaa(int i, boolean z) {
        this.zaeh.zab(i, z);
        this.zaep = null;
        this.zaeo = null;
    }

    @GuardedBy("mLock")
    private final boolean zax() {
        ConnectionResult connectionResult = this.zaep;
        return connectionResult != null && connectionResult.getErrorCode() == 4;
    }

    private final boolean zaa(ApiMethodImpl<? extends Result, ? extends AnyClient> apiMethodImpl) {
        AnyClientKey clientKey = apiMethodImpl.getClientKey();
        Preconditions.checkArgument(this.zaek.containsKey(clientKey), "GoogleApiClient is not configured to use the API required for this call.");
        return ((zabe) this.zaek.get(clientKey)).equals(this.zaej);
    }

    @Nullable
    private final PendingIntent zay() {
        if (this.zaem == null) {
            return null;
        }
        return PendingIntent.getActivity(this.mContext, System.identityHashCode(this.zaeh), this.zaem.getSignInIntent(), 134217728);
    }

    private final void zaa(Bundle bundle) {
        Bundle bundle2 = this.zaen;
        if (bundle2 == null) {
            this.zaen = bundle;
            return;
        }
        if (bundle != null) {
            bundle2.putAll(bundle);
        }
    }

    private static boolean zab(ConnectionResult connectionResult) {
        return connectionResult != null && connectionResult.isSuccess();
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str2 = ":";
        printWriter.append(str).append("authClient").println(str2);
        String str3 = "  ";
        this.zaej.dump(String.valueOf(str).concat(str3), fileDescriptor, printWriter, strArr);
        printWriter.append(str).append("anonClient").println(str2);
        this.zaei.dump(String.valueOf(str).concat(str3), fileDescriptor, printWriter, strArr);
    }
}
