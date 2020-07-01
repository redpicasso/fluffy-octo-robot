package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.AnyClientKey;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClientEventManager;
import com.google.android.gms.common.internal.GmsClientEventManager.GmsClientEventState;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zac;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zaaw extends GoogleApiClient implements zabs {
    private final Context mContext;
    private final Looper zabl;
    private final int zacc;
    private final GoogleApiAvailability zace;
    private final AbstractClientBuilder<? extends zac, SignInOptions> zacf;
    private boolean zaci;
    private final Lock zaer;
    private final Map<Api<?>, Boolean> zaew;
    private final ClientSettings zafa;
    @VisibleForTesting
    final Queue<ApiMethodImpl<?, ?>> zafd = new LinkedList();
    private final GmsClientEventManager zagw;
    private zabr zagx = null;
    private volatile boolean zagy;
    private long zagz;
    private long zaha;
    private final zaaz zahb;
    @VisibleForTesting
    private zabq zahc;
    final Map<AnyClientKey<?>, Client> zahd;
    Set<Scope> zahe;
    private final ListenerHolders zahf;
    private final ArrayList<zap> zahg;
    private Integer zahh;
    Set<zack> zahi;
    final zacp zahj;
    private final GmsClientEventState zahk;

    public zaaw(Context context, Lock lock, Looper looper, ClientSettings clientSettings, GoogleApiAvailability googleApiAvailability, AbstractClientBuilder<? extends zac, SignInOptions> abstractClientBuilder, Map<Api<?>, Boolean> map, List<ConnectionCallbacks> list, List<OnConnectionFailedListener> list2, Map<AnyClientKey<?>, Client> map2, int i, int i2, ArrayList<zap> arrayList, boolean z) {
        Looper looper2 = looper;
        this.zagz = ClientLibraryUtils.isPackageSide() ? 10000 : 120000;
        this.zaha = 5000;
        this.zahe = new HashSet();
        this.zahf = new ListenerHolders();
        this.zahh = null;
        this.zahi = null;
        this.zahk = new zaav(this);
        this.mContext = context;
        this.zaer = lock;
        this.zaci = false;
        this.zagw = new GmsClientEventManager(looper, this.zahk);
        this.zabl = looper2;
        this.zahb = new zaaz(this, looper);
        this.zace = googleApiAvailability;
        this.zacc = i;
        if (this.zacc >= 0) {
            this.zahh = Integer.valueOf(i2);
        }
        this.zaew = map;
        this.zahd = map2;
        this.zahg = arrayList;
        this.zahj = new zacp(this.zahd);
        for (ConnectionCallbacks registerConnectionCallbacks : list) {
            this.zagw.registerConnectionCallbacks(registerConnectionCallbacks);
        }
        for (OnConnectionFailedListener registerConnectionFailedListener : list2) {
            this.zagw.registerConnectionFailedListener(registerConnectionFailedListener);
        }
        this.zafa = clientSettings;
        this.zacf = abstractClientBuilder;
    }

    private static String zaf(int i) {
        return i != 1 ? i != 2 ? i != 3 ? "UNKNOWN" : "SIGN_IN_MODE_NONE" : "SIGN_IN_MODE_OPTIONAL" : "SIGN_IN_MODE_REQUIRED";
    }

    public final <A extends AnyClient, R extends Result, T extends ApiMethodImpl<R, A>> T enqueue(@NonNull T t) {
        Preconditions.checkArgument(t.getClientKey() != null, "This task can not be enqueued (it's probably a Batch or malformed)");
        boolean containsKey = this.zahd.containsKey(t.getClientKey());
        String name = t.getApi() != null ? t.getApi().getName() : "the API";
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(name).length() + 65);
        stringBuilder.append("GoogleApiClient is not configured to use ");
        stringBuilder.append(name);
        stringBuilder.append(" required for this call.");
        Preconditions.checkArgument(containsKey, stringBuilder.toString());
        this.zaer.lock();
        try {
            if (this.zagx == null) {
                this.zafd.add(t);
                return t;
            }
            t = this.zagx.enqueue(t);
            this.zaer.unlock();
            return t;
        } finally {
            this.zaer.unlock();
        }
    }

    public final <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t) {
        Preconditions.checkArgument(t.getClientKey() != null, "This task can not be executed (it's probably a Batch or malformed)");
        boolean containsKey = this.zahd.containsKey(t.getClientKey());
        String name = t.getApi() != null ? t.getApi().getName() : "the API";
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(name).length() + 65);
        stringBuilder.append("GoogleApiClient is not configured to use ");
        stringBuilder.append(name);
        stringBuilder.append(" required for this call.");
        Preconditions.checkArgument(containsKey, stringBuilder.toString());
        this.zaer.lock();
        try {
            if (this.zagx == null) {
                throw new IllegalStateException("GoogleApiClient is not connected yet.");
            } else if (this.zagy) {
                this.zafd.add(t);
                while (!this.zafd.isEmpty()) {
                    ApiMethodImpl apiMethodImpl = (ApiMethodImpl) this.zafd.remove();
                    this.zahj.zac(apiMethodImpl);
                    apiMethodImpl.setFailedResult(Status.RESULT_INTERNAL_ERROR);
                }
                return t;
            } else {
                t = this.zagx.execute(t);
                this.zaer.unlock();
                return t;
            }
        } finally {
            this.zaer.unlock();
        }
    }

    public final <L> ListenerHolder<L> registerListener(@NonNull L l) {
        this.zaer.lock();
        try {
            ListenerHolder<L> zaa = this.zahf.zaa(l, this.zabl, "NO_TYPE");
            return zaa;
        } finally {
            this.zaer.unlock();
        }
    }

    @NonNull
    public final <C extends Client> C getClient(@NonNull AnyClientKey<C> anyClientKey) {
        Client client = (Client) this.zahd.get(anyClientKey);
        Preconditions.checkNotNull(client, "Appropriate Api was not requested.");
        return client;
    }

    public final boolean hasApi(@NonNull Api<?> api) {
        return this.zahd.containsKey(api.getClientKey());
    }

    public final boolean hasConnectedApi(@NonNull Api<?> api) {
        if (!isConnected()) {
            return false;
        }
        Client client = (Client) this.zahd.get(api.getClientKey());
        if (client == null || !client.isConnected()) {
            return false;
        }
        return true;
    }

    @NonNull
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        String str = "GoogleApiClientImpl";
        this.zaer.lock();
        try {
            if (!isConnected() && !this.zagy) {
                throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
            } else if (this.zahd.containsKey(api.getClientKey())) {
                ConnectionResult connectionResult = this.zagx.getConnectionResult(api);
                ConnectionResult connectionResult2;
                if (connectionResult != null) {
                    this.zaer.unlock();
                    return connectionResult;
                } else if (this.zagy) {
                    connectionResult2 = ConnectionResult.RESULT_SUCCESS;
                    return connectionResult2;
                } else {
                    Log.w(str, zaaw());
                    Log.wtf(str, String.valueOf(api.getName()).concat(" requested in getConnectionResult is not connected but is not present in the failed  connections map"), new Exception());
                    connectionResult2 = new ConnectionResult(8, null);
                    this.zaer.unlock();
                    return connectionResult2;
                }
            } else {
                throw new IllegalArgumentException(String.valueOf(api.getName()).concat(" was never registered with GoogleApiClient"));
            }
        } finally {
            this.zaer.unlock();
        }
    }

    public final void connect() {
        this.zaer.lock();
        try {
            boolean z = false;
            if (this.zacc >= 0) {
                if (this.zahh != null) {
                    z = true;
                }
                Preconditions.checkState(z, "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zahh == null) {
                this.zahh = Integer.valueOf(zaa(this.zahd.values(), false));
            } else if (this.zahh.intValue() == 2) {
                throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            connect(this.zahh.intValue());
        } finally {
            this.zaer.unlock();
        }
    }

    public final void connect(int i) {
        this.zaer.lock();
        boolean z = true;
        if (!(i == 3 || i == 1 || i == 2)) {
            z = false;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder(33);
            stringBuilder.append("Illegal sign-in mode: ");
            stringBuilder.append(i);
            Preconditions.checkArgument(z, stringBuilder.toString());
            zae(i);
            zaas();
        } finally {
            this.zaer.unlock();
        }
    }

    public final ConnectionResult blockingConnect() {
        boolean z = true;
        Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        this.zaer.lock();
        try {
            if (this.zacc >= 0) {
                if (this.zahh == null) {
                    z = false;
                }
                Preconditions.checkState(z, "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zahh == null) {
                this.zahh = Integer.valueOf(zaa(this.zahd.values(), false));
            } else if (this.zahh.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zae(this.zahh.intValue());
            this.zagw.enableCallbacks();
            ConnectionResult blockingConnect = this.zagx.blockingConnect();
            return blockingConnect;
        } finally {
            this.zaer.unlock();
        }
    }

    public final ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        Preconditions.checkNotNull(timeUnit, "TimeUnit must not be null");
        this.zaer.lock();
        try {
            if (this.zahh == null) {
                this.zahh = Integer.valueOf(zaa(this.zahd.values(), false));
            } else if (this.zahh.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zae(this.zahh.intValue());
            this.zagw.enableCallbacks();
            ConnectionResult blockingConnect = this.zagx.blockingConnect(j, timeUnit);
            return blockingConnect;
        } finally {
            this.zaer.unlock();
        }
    }

    public final void disconnect() {
        this.zaer.lock();
        try {
            this.zahj.release();
            if (this.zagx != null) {
                this.zagx.disconnect();
            }
            this.zahf.release();
            for (ApiMethodImpl apiMethodImpl : this.zafd) {
                apiMethodImpl.zaa(null);
                apiMethodImpl.cancel();
            }
            this.zafd.clear();
            if (this.zagx != null) {
                zaau();
                this.zagw.disableCallbacks();
                this.zaer.unlock();
            }
        } finally {
            this.zaer.unlock();
        }
    }

    public final void reconnect() {
        disconnect();
        connect();
    }

    public final PendingResult<Status> clearDefaultAccountAndReconnect() {
        Preconditions.checkState(isConnected(), "GoogleApiClient is not connected yet.");
        Preconditions.checkState(this.zahh.intValue() != 2, "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        PendingResult statusPendingResult = new StatusPendingResult((GoogleApiClient) this);
        if (this.zahd.containsKey(Common.CLIENT_KEY)) {
            zaa(this, statusPendingResult, false);
        } else {
            AtomicReference atomicReference = new AtomicReference();
            GoogleApiClient build = new Builder(this.mContext).addApi(Common.API).addConnectionCallbacks(new zaay(this, atomicReference, statusPendingResult)).addOnConnectionFailedListener(new zaax(this, statusPendingResult)).setHandler(this.zahb).build();
            atomicReference.set(build);
            build.connect();
        }
        return statusPendingResult;
    }

    private final void zaa(GoogleApiClient googleApiClient, StatusPendingResult statusPendingResult, boolean z) {
        Common.zapw.zaa(googleApiClient).setResultCallback(new zaba(this, statusPendingResult, z, googleApiClient));
    }

    public final void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        LifecycleActivity lifecycleActivity = new LifecycleActivity((Activity) fragmentActivity);
        if (this.zacc >= 0) {
            zai.zaa(lifecycleActivity).zaa(this.zacc);
            return;
        }
        throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
    }

    public final boolean isConnected() {
        zabr zabr = this.zagx;
        return zabr != null && zabr.isConnected();
    }

    public final boolean isConnecting() {
        zabr zabr = this.zagx;
        return zabr != null && zabr.isConnecting();
    }

    private final void zae(int i) {
        Integer num = this.zahh;
        if (num == null) {
            this.zahh = Integer.valueOf(i);
        } else if (num.intValue() != i) {
            String zaf = zaf(i);
            String zaf2 = zaf(this.zahh.intValue());
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(zaf).length() + 51) + String.valueOf(zaf2).length());
            stringBuilder.append("Cannot use sign-in mode: ");
            stringBuilder.append(zaf);
            stringBuilder.append(". Mode was already set to ");
            stringBuilder.append(zaf2);
            throw new IllegalStateException(stringBuilder.toString());
        }
        if (this.zagx == null) {
            Object obj = null;
            Object obj2 = null;
            for (Client client : this.zahd.values()) {
                if (client.requiresSignIn()) {
                    obj = 1;
                }
                if (client.providesSignIn()) {
                    obj2 = 1;
                }
            }
            int intValue = this.zahh.intValue();
            if (intValue != 1) {
                if (intValue == 2) {
                    if (obj != null) {
                        if (this.zaci) {
                            this.zagx = new zav(this.mContext, this.zaer, this.zabl, this.zace, this.zahd, this.zafa, this.zaew, this.zacf, this.zahg, this, true);
                            return;
                        }
                        this.zagx = zaq.zaa(this.mContext, this, this.zaer, this.zabl, this.zace, this.zahd, this.zafa, this.zaew, this.zacf, this.zahg);
                        return;
                    }
                }
            } else if (obj == null) {
                throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
            } else if (obj2 != null) {
                throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            if (this.zaci && obj2 == null) {
                this.zagx = new zav(this.mContext, this.zaer, this.zabl, this.zace, this.zahd, this.zafa, this.zaew, this.zacf, this.zahg, this, false);
                return;
            }
            this.zagx = new zabe(this.mContext, this, this.zaer, this.zabl, this.zace, this.zahd, this.zafa, this.zaew, this.zacf, this.zahg, this);
        }
    }

    @GuardedBy("mLock")
    private final void zaas() {
        this.zagw.enableCallbacks();
        this.zagx.connect();
    }

    private final void resume() {
        this.zaer.lock();
        try {
            if (this.zagy) {
                zaas();
            }
            this.zaer.unlock();
        } catch (Throwable th) {
            this.zaer.unlock();
        }
    }

    private final void zaat() {
        this.zaer.lock();
        try {
            if (zaau()) {
                zaas();
            }
            this.zaer.unlock();
        } catch (Throwable th) {
            this.zaer.unlock();
        }
    }

    @GuardedBy("mLock")
    final boolean zaau() {
        if (!this.zagy) {
            return false;
        }
        this.zagy = false;
        this.zahb.removeMessages(2);
        this.zahb.removeMessages(1);
        zabq zabq = this.zahc;
        if (zabq != null) {
            zabq.unregister();
            this.zahc = null;
        }
        return true;
    }

    public final void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        this.zagw.registerConnectionCallbacks(connectionCallbacks);
    }

    public final boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks) {
        return this.zagw.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    public final void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        this.zagw.unregisterConnectionCallbacks(connectionCallbacks);
    }

    public final void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        this.zagw.registerConnectionFailedListener(onConnectionFailedListener);
    }

    public final boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        return this.zagw.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    public final void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        this.zagw.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    @GuardedBy("mLock")
    public final void zab(Bundle bundle) {
        while (!this.zafd.isEmpty()) {
            execute((ApiMethodImpl) this.zafd.remove());
        }
        this.zagw.onConnectionSuccess(bundle);
    }

    @GuardedBy("mLock")
    public final void zac(ConnectionResult connectionResult) {
        if (!this.zace.isPlayServicesPossiblyUpdating(this.mContext, connectionResult.getErrorCode())) {
            zaau();
        }
        if (!this.zagy) {
            this.zagw.onConnectionFailure(connectionResult);
            this.zagw.disableCallbacks();
        }
    }

    @GuardedBy("mLock")
    public final void zab(int i, boolean z) {
        if (!(i != 1 || z || this.zagy)) {
            this.zagy = true;
            if (this.zahc == null && !ClientLibraryUtils.isPackageSide()) {
                try {
                    this.zahc = this.zace.zaa(this.mContext.getApplicationContext(), new zabc(this));
                } catch (SecurityException unused) {
                    zaaz zaaz = this.zahb;
                    zaaz.sendMessageDelayed(zaaz.obtainMessage(1), this.zagz);
                    zaaz = this.zahb;
                    zaaz.sendMessageDelayed(zaaz.obtainMessage(2), this.zaha);
                }
            }
        }
        this.zahj.zabv();
        this.zagw.onUnintentionalDisconnection(i);
        this.zagw.disableCallbacks();
        if (i == 2) {
            zaas();
        }
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Looper getLooper() {
        return this.zabl;
    }

    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        zabr zabr = this.zagx;
        return zabr != null && zabr.maybeSignIn(signInConnectionListener);
    }

    public final void maybeSignOut() {
        zabr zabr = this.zagx;
        if (zabr != null) {
            zabr.maybeSignOut();
        }
    }

    public final void zaa(zack zack) {
        this.zaer.lock();
        try {
            if (this.zahi == null) {
                this.zahi = new HashSet();
            }
            this.zahi.add(zack);
        } finally {
            this.zaer.unlock();
        }
    }

    public final void zab(zack zack) {
        this.zaer.lock();
        try {
            String str = "GoogleApiClientImpl";
            if (this.zahi == null) {
                Log.wtf(str, "Attempted to remove pending transform when no transforms are registered.", new Exception());
            } else if (!this.zahi.remove(zack)) {
                Log.wtf(str, "Failed to remove pending transform - this may lead to memory leaks!", new Exception());
            } else if (!zaav()) {
                this.zagx.zau();
            }
            this.zaer.unlock();
        } catch (Throwable th) {
            this.zaer.unlock();
        }
    }

    final boolean zaav() {
        this.zaer.lock();
        try {
            if (this.zahi == null) {
                return false;
            }
            boolean isEmpty = this.zahi.isEmpty() ^ 1;
            this.zaer.unlock();
            return isEmpty;
        } finally {
            this.zaer.unlock();
        }
    }

    final String zaaw() {
        Writer stringWriter = new StringWriter();
        dump("", null, new PrintWriter(stringWriter), null);
        return stringWriter.toString();
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append(str).append("mContext=").println(this.mContext);
        printWriter.append(str).append("mResuming=").print(this.zagy);
        printWriter.append(" mWorkQueue.size()=").print(this.zafd.size());
        printWriter.append(" mUnconsumedApiCalls.size()=").println(this.zahj.zald.size());
        zabr zabr = this.zagx;
        if (zabr != null) {
            zabr.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    public static int zaa(Iterable<Client> iterable, boolean z) {
        Object obj = null;
        Object obj2 = null;
        for (Client client : iterable) {
            if (client.requiresSignIn()) {
                obj = 1;
            }
            if (client.providesSignIn()) {
                obj2 = 1;
            }
        }
        if (obj == null) {
            return 3;
        }
        if (obj2 == null || !z) {
            return 1;
        }
        return 2;
    }
}
