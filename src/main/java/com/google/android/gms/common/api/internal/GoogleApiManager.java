package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.UnsupportedApiCallException;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey;
import com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.SimpleClientAdapter;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.internal.base.zar;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public class GoogleApiManager implements Callback {
    private static final Object lock = new Object();
    public static final Status zaib = new Status(4, "Sign-out occurred while this API call was in progress.");
    private static final Status zaic = new Status(4, "The user must be signed in to make this API call.");
    @GuardedBy("lock")
    private static GoogleApiManager zaig;
    private final Handler handler;
    private long zaid = 5000;
    private long zaie = 120000;
    private long zaif = 10000;
    private final Context zaih;
    private final GoogleApiAvailability zaii;
    private final GoogleApiAvailabilityCache zaij;
    private final AtomicInteger zaik = new AtomicInteger(1);
    private final AtomicInteger zail = new AtomicInteger(0);
    private final Map<ApiKey<?>, zaa<?>> zaim = new ConcurrentHashMap(5, 0.75f, 1);
    @GuardedBy("lock")
    private zaad zain = null;
    @GuardedBy("lock")
    private final Set<ApiKey<?>> zaio = new ArraySet();
    private final Set<ApiKey<?>> zaip = new ArraySet();

    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    private static class zac {
        private final ApiKey<?> zajh;
        private final Feature zaji;

        private zac(ApiKey<?> apiKey, Feature feature) {
            this.zajh = apiKey;
            this.zaji = feature;
        }

        public final boolean equals(Object obj) {
            if (obj != null && (obj instanceof zac)) {
                zac zac = (zac) obj;
                if (Objects.equal(this.zajh, zac.zajh) && Objects.equal(this.zaji, zac.zaji)) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hashCode(this.zajh, this.zaji);
        }

        public final String toString() {
            return Objects.toStringHelper(this).add("key", this.zajh).add("feature", this.zaji).toString();
        }

        /* synthetic */ zac(ApiKey apiKey, Feature feature, zabh zabh) {
            this(apiKey, feature);
        }
    }

    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    private class zab implements zacf, ConnectionProgressReportCallbacks {
        private final ApiKey<?> zaft;
        private final Client zais;
        private IAccountAccessor zaje = null;
        private Set<Scope> zajf = null;
        private boolean zajg = false;

        public zab(Client client, ApiKey<?> apiKey) {
            this.zais = client;
            this.zaft = apiKey;
        }

        public final void onReportServiceBinding(@NonNull ConnectionResult connectionResult) {
            GoogleApiManager.this.handler.post(new zabo(this, connectionResult));
        }

        @WorkerThread
        public final void zag(ConnectionResult connectionResult) {
            ((zaa) GoogleApiManager.this.zaim.get(this.zaft)).zag(connectionResult);
        }

        @WorkerThread
        public final void zaa(IAccountAccessor iAccountAccessor, Set<Scope> set) {
            if (iAccountAccessor == null || set == null) {
                Log.wtf("GoogleApiManager", "Received null response from onSignInSuccess", new Exception());
                zag(new ConnectionResult(4));
                return;
            }
            this.zaje = iAccountAccessor;
            this.zajf = set;
            zabp();
        }

        @WorkerThread
        private final void zabp() {
            if (this.zajg) {
                IAccountAccessor iAccountAccessor = this.zaje;
                if (iAccountAccessor != null) {
                    this.zais.getRemoteService(iAccountAccessor, this.zajf);
                }
            }
        }
    }

    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    public class zaa<O extends ApiOptions> implements ConnectionCallbacks, OnConnectionFailedListener, zar {
        private final ApiKey<O> zaft;
        private final Queue<zac> zair = new LinkedList();
        private final Client zais;
        private final AnyClient zait;
        private final zaz zaiu;
        private final Set<zaj> zaiv = new HashSet();
        private final Map<ListenerKey<?>, zabv> zaiw = new HashMap();
        private final int zaix;
        private final zace zaiy;
        private boolean zaiz;
        private final List<zac> zaja = new ArrayList();
        private ConnectionResult zajb = null;

        @WorkerThread
        public zaa(GoogleApi<O> googleApi) {
            this.zais = googleApi.zaa(GoogleApiManager.this.handler.getLooper(), this);
            AnyClient anyClient = this.zais;
            if (anyClient instanceof SimpleClientAdapter) {
                this.zait = ((SimpleClientAdapter) anyClient).getClient();
            } else {
                this.zait = anyClient;
            }
            this.zaft = googleApi.getApiKey();
            this.zaiu = new zaz();
            this.zaix = googleApi.getInstanceId();
            if (this.zais.requiresSignIn()) {
                this.zaiy = googleApi.zaa(GoogleApiManager.this.zaih, GoogleApiManager.this.handler);
            } else {
                this.zaiy = null;
            }
        }

        public final void onConnected(@Nullable Bundle bundle) {
            if (Looper.myLooper() == GoogleApiManager.this.handler.getLooper()) {
                zabe();
            } else {
                GoogleApiManager.this.handler.post(new zabi(this));
            }
        }

        @WorkerThread
        private final void zabe() {
            zabj();
            zai(ConnectionResult.RESULT_SUCCESS);
            zabl();
            Iterator it = this.zaiw.values().iterator();
            while (it.hasNext()) {
                zabv zabv = (zabv) it.next();
                if (zaa(zabv.zakc.getRequiredFeatures()) != null) {
                    it.remove();
                } else {
                    try {
                        zabv.zakc.registerListener(this.zait, new TaskCompletionSource());
                    } catch (DeadObjectException unused) {
                        onConnectionSuspended(1);
                        this.zais.disconnect();
                    } catch (RemoteException unused2) {
                        it.remove();
                    }
                }
            }
            zabg();
            zabm();
        }

        public final void onConnectionSuspended(int i) {
            if (Looper.myLooper() == GoogleApiManager.this.handler.getLooper()) {
                zabf();
            } else {
                GoogleApiManager.this.handler.post(new zabk(this));
            }
        }

        @WorkerThread
        private final void zabf() {
            zabj();
            this.zaiz = true;
            this.zaiu.zaag();
            GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 9, this.zaft), GoogleApiManager.this.zaid);
            GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 11, this.zaft), GoogleApiManager.this.zaie);
            GoogleApiManager.this.zaij.flush();
        }

        @WorkerThread
        public final void zag(@NonNull ConnectionResult connectionResult) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            this.zais.disconnect();
            onConnectionFailed(connectionResult);
        }

        @WorkerThread
        private final boolean zah(@NonNull ConnectionResult connectionResult) {
            synchronized (GoogleApiManager.lock) {
                if (GoogleApiManager.this.zain == null || !GoogleApiManager.this.zaio.contains(this.zaft)) {
                    return false;
                }
                GoogleApiManager.this.zain.zab(connectionResult, this.zaix);
                return true;
            }
        }

        public final void zaa(ConnectionResult connectionResult, Api<?> api, boolean z) {
            if (Looper.myLooper() == GoogleApiManager.this.handler.getLooper()) {
                onConnectionFailed(connectionResult);
            } else {
                GoogleApiManager.this.handler.post(new zabj(this, connectionResult));
            }
        }

        @WorkerThread
        public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            zace zace = this.zaiy;
            if (zace != null) {
                zace.zabq();
            }
            zabj();
            GoogleApiManager.this.zaij.flush();
            zai(connectionResult);
            if (connectionResult.getErrorCode() == 4) {
                zac(GoogleApiManager.zaic);
            } else if (this.zair.isEmpty()) {
                this.zajb = connectionResult;
            } else {
                if (!(zah(connectionResult) || GoogleApiManager.this.zac(connectionResult, this.zaix))) {
                    if (connectionResult.getErrorCode() == 18) {
                        this.zaiz = true;
                    }
                    if (this.zaiz) {
                        GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 9, this.zaft), GoogleApiManager.this.zaid);
                        return;
                    }
                    String apiName = this.zaft.getApiName();
                    String valueOf = String.valueOf(connectionResult);
                    StringBuilder stringBuilder = new StringBuilder((String.valueOf(apiName).length() + 63) + String.valueOf(valueOf).length());
                    stringBuilder.append("API: ");
                    stringBuilder.append(apiName);
                    stringBuilder.append(" is not available on this device. Connection failed with: ");
                    stringBuilder.append(valueOf);
                    zac(new Status(17, stringBuilder.toString()));
                }
            }
        }

        @WorkerThread
        private final void zabg() {
            ArrayList arrayList = new ArrayList(this.zair);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                zac zac = (zac) obj;
                if (!this.zais.isConnected()) {
                    return;
                }
                if (zab(zac)) {
                    this.zair.remove(zac);
                }
            }
        }

        @WorkerThread
        public final void zaa(zac zac) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            if (!this.zais.isConnected()) {
                this.zair.add(zac);
                ConnectionResult connectionResult = this.zajb;
                if (connectionResult == null || !connectionResult.hasResolution()) {
                    connect();
                } else {
                    onConnectionFailed(this.zajb);
                }
            } else if (zab(zac)) {
                zabm();
            } else {
                this.zair.add(zac);
            }
        }

        @WorkerThread
        public final void zabh() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            zac(GoogleApiManager.zaib);
            this.zaiu.zaaf();
            for (ListenerKey zah : (ListenerKey[]) this.zaiw.keySet().toArray(new ListenerKey[this.zaiw.size()])) {
                zaa(new zah(zah, new TaskCompletionSource()));
            }
            zai(new ConnectionResult(4));
            if (this.zais.isConnected()) {
                this.zais.onUserSignOut(new zabm(this));
            }
        }

        public final Client zaad() {
            return this.zais;
        }

        public final Map<ListenerKey<?>, zabv> zabi() {
            return this.zaiw;
        }

        @WorkerThread
        public final void zabj() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            this.zajb = null;
        }

        @WorkerThread
        public final ConnectionResult zabk() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            return this.zajb;
        }

        @WorkerThread
        private final boolean zab(zac zac) {
            if (zac instanceof zab) {
                zab zab = (zab) zac;
                Feature zaa = zaa(zab.zaa(this));
                if (zaa == null) {
                    zac(zac);
                    return true;
                }
                if (zab.zab(this)) {
                    zac zac2 = new zac(this.zaft, zaa, null);
                    int indexOf = this.zaja.indexOf(zac2);
                    if (indexOf >= 0) {
                        zac2 = (zac) this.zaja.get(indexOf);
                        GoogleApiManager.this.handler.removeMessages(15, zac2);
                        GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 15, zac2), GoogleApiManager.this.zaid);
                    } else {
                        this.zaja.add(zac2);
                        GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 15, zac2), GoogleApiManager.this.zaid);
                        GoogleApiManager.this.handler.sendMessageDelayed(Message.obtain(GoogleApiManager.this.handler, 16, zac2), GoogleApiManager.this.zaie);
                        ConnectionResult connectionResult = new ConnectionResult(2, null);
                        if (!zah(connectionResult)) {
                            GoogleApiManager.this.zac(connectionResult, this.zaix);
                        }
                    }
                } else {
                    zab.zaa(new UnsupportedApiCallException(zaa));
                }
                return false;
            }
            zac(zac);
            return true;
        }

        @WorkerThread
        private final void zac(zac zac) {
            zac.zaa(this.zaiu, requiresSignIn());
            try {
                zac.zac(this);
            } catch (DeadObjectException unused) {
                onConnectionSuspended(1);
                this.zais.disconnect();
            }
        }

        @WorkerThread
        public final void zac(Status status) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            for (zac zaa : this.zair) {
                zaa.zaa(status);
            }
            this.zair.clear();
        }

        @WorkerThread
        public final void resume() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            if (this.zaiz) {
                connect();
            }
        }

        @WorkerThread
        private final void zabl() {
            if (this.zaiz) {
                GoogleApiManager.this.handler.removeMessages(11, this.zaft);
                GoogleApiManager.this.handler.removeMessages(9, this.zaft);
                this.zaiz = false;
            }
        }

        @WorkerThread
        public final void zaat() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            if (this.zaiz) {
                Status status;
                zabl();
                if (GoogleApiManager.this.zaii.isGooglePlayServicesAvailable(GoogleApiManager.this.zaih) == 18) {
                    status = new Status(8, "Connection timed out while waiting for Google Play services update to complete.");
                } else {
                    status = new Status(8, "API failed to connect while resuming due to an unknown error.");
                }
                zac(status);
                this.zais.disconnect();
            }
        }

        private final void zabm() {
            GoogleApiManager.this.handler.removeMessages(12, this.zaft);
            GoogleApiManager.this.handler.sendMessageDelayed(GoogleApiManager.this.handler.obtainMessage(12, this.zaft), GoogleApiManager.this.zaif);
        }

        @WorkerThread
        public final boolean zabn() {
            return zac(true);
        }

        @WorkerThread
        private final boolean zac(boolean z) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            if (!this.zais.isConnected() || this.zaiw.size() != 0) {
                return false;
            }
            if (this.zaiu.zaae()) {
                if (z) {
                    zabm();
                }
                return false;
            }
            this.zais.disconnect();
            return true;
        }

        @WorkerThread
        public final void connect() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            if (!(this.zais.isConnected() || this.zais.isConnecting())) {
                int clientAvailability = GoogleApiManager.this.zaij.getClientAvailability(GoogleApiManager.this.zaih, this.zais);
                if (clientAvailability != 0) {
                    onConnectionFailed(new ConnectionResult(clientAvailability, null));
                    return;
                }
                zacf zab = new zab(this.zais, this.zaft);
                if (this.zais.requiresSignIn()) {
                    this.zaiy.zaa(zab);
                }
                this.zais.connect(zab);
            }
        }

        @WorkerThread
        public final void zaa(zaj zaj) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.handler);
            this.zaiv.add(zaj);
        }

        @WorkerThread
        private final void zai(ConnectionResult connectionResult) {
            for (zaj zaj : this.zaiv) {
                String str = null;
                if (Objects.equal(connectionResult, ConnectionResult.RESULT_SUCCESS)) {
                    str = this.zais.getEndpointPackageName();
                }
                zaj.zaa(this.zaft, connectionResult, str);
            }
            this.zaiv.clear();
        }

        final boolean isConnected() {
            return this.zais.isConnected();
        }

        public final boolean requiresSignIn() {
            return this.zais.requiresSignIn();
        }

        public final int getInstanceId() {
            return this.zaix;
        }

        final com.google.android.gms.signin.zac zabo() {
            zace zace = this.zaiy;
            return zace == null ? null : zace.zabo();
        }

        @WorkerThread
        @Nullable
        private final Feature zaa(@Nullable Feature[] featureArr) {
            if (!(featureArr == null || featureArr.length == 0)) {
                Feature[] availableFeatures = this.zais.getAvailableFeatures();
                int i = 0;
                if (availableFeatures == null) {
                    availableFeatures = new Feature[0];
                }
                Map arrayMap = new ArrayMap(availableFeatures.length);
                for (Feature feature : availableFeatures) {
                    arrayMap.put(feature.getName(), Long.valueOf(feature.getVersion()));
                }
                int length = featureArr.length;
                while (i < length) {
                    Feature feature2 = featureArr[i];
                    if (!arrayMap.containsKey(feature2.getName()) || ((Long) arrayMap.get(feature2.getName())).longValue() < feature2.getVersion()) {
                        return feature2;
                    }
                    i++;
                }
            }
            return null;
        }

        @WorkerThread
        private final void zaa(zac zac) {
            if (this.zaja.contains(zac) && !this.zaiz) {
                if (this.zais.isConnected()) {
                    zabg();
                } else {
                    connect();
                }
            }
        }

        @WorkerThread
        private final void zab(zac zac) {
            if (this.zaja.remove(zac)) {
                GoogleApiManager.this.handler.removeMessages(15, zac);
                GoogleApiManager.this.handler.removeMessages(16, zac);
                Object zad = zac.zaji;
                ArrayList arrayList = new ArrayList(this.zair.size());
                for (zac zac2 : this.zair) {
                    if (zac2 instanceof zab) {
                        Object[] zaa = ((zab) zac2).zaa(this);
                        if (zaa != null && ArrayUtils.contains(zaa, zad)) {
                            arrayList.add(zac2);
                        }
                    }
                }
                arrayList = arrayList;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    zac zac3 = (zac) obj;
                    this.zair.remove(zac3);
                    zac3.zaa(new UnsupportedApiCallException(zad));
                }
            }
        }
    }

    public static GoogleApiManager zab(Context context) {
        GoogleApiManager googleApiManager;
        synchronized (lock) {
            if (zaig == null) {
                HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
                handlerThread.start();
                zaig = new GoogleApiManager(context.getApplicationContext(), handlerThread.getLooper(), GoogleApiAvailability.getInstance());
            }
            googleApiManager = zaig;
        }
        return googleApiManager;
    }

    public static GoogleApiManager zaba() {
        GoogleApiManager googleApiManager;
        synchronized (lock) {
            Preconditions.checkNotNull(zaig, "Must guarantee manager is non-null before using getInstance");
            googleApiManager = zaig;
        }
        return googleApiManager;
    }

    @KeepForSdk
    public static void reportSignOut() {
        synchronized (lock) {
            if (zaig != null) {
                GoogleApiManager googleApiManager = zaig;
                googleApiManager.zail.incrementAndGet();
                googleApiManager.handler.sendMessageAtFrontOfQueue(googleApiManager.handler.obtainMessage(10));
            }
        }
    }

    @KeepForSdk
    private GoogleApiManager(Context context, Looper looper, GoogleApiAvailability googleApiAvailability) {
        this.zaih = context;
        this.handler = new zar(looper, this);
        this.zaii = googleApiAvailability;
        this.zaij = new GoogleApiAvailabilityCache(googleApiAvailability);
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(6));
    }

    public final int zabb() {
        return this.zaik.getAndIncrement();
    }

    public final void zaa(GoogleApi<?> googleApi) {
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(7, googleApi));
    }

    @WorkerThread
    private final void zab(GoogleApi<?> googleApi) {
        ApiKey apiKey = googleApi.getApiKey();
        zaa zaa = (zaa) this.zaim.get(apiKey);
        if (zaa == null) {
            zaa = new zaa(googleApi);
            this.zaim.put(apiKey, zaa);
        }
        if (zaa.requiresSignIn()) {
            this.zaip.add(apiKey);
        }
        zaa.connect();
    }

    public final void zaa(@NonNull zaad zaad) {
        synchronized (lock) {
            if (this.zain != zaad) {
                this.zain = zaad;
                this.zaio.clear();
            }
            this.zaio.addAll(zaad.zaah());
        }
    }

    final void zab(@NonNull zaad zaad) {
        synchronized (lock) {
            if (this.zain == zaad) {
                this.zain = null;
                this.zaio.clear();
            }
        }
    }

    public final Task<Map<ApiKey<?>, String>> zaa(Iterable<? extends HasApiKey<?>> iterable) {
        zaj zaj = new zaj(iterable);
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(2, zaj));
        return zaj.getTask();
    }

    public final void zam() {
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(3));
    }

    final void maybeSignOut() {
        this.zail.incrementAndGet();
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(10));
    }

    public final Task<Boolean> zac(GoogleApi<?> googleApi) {
        zaae zaae = new zaae(googleApi.getApiKey());
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(14, zaae));
        return zaae.zaaj().getTask();
    }

    public final <O extends ApiOptions> void zaa(GoogleApi<O> googleApi, int i, ApiMethodImpl<? extends Result, AnyClient> apiMethodImpl) {
        zac zad = new zad(i, apiMethodImpl);
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(4, new zabu(zad, this.zail.get(), googleApi)));
    }

    public final <O extends ApiOptions, ResultT> void zaa(GoogleApi<O> googleApi, int i, TaskApiCall<AnyClient, ResultT> taskApiCall, TaskCompletionSource<ResultT> taskCompletionSource, StatusExceptionMapper statusExceptionMapper) {
        zac zaf = new zaf(i, taskApiCall, taskCompletionSource, statusExceptionMapper);
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(4, new zabu(zaf, this.zail.get(), googleApi)));
    }

    public final <O extends ApiOptions> Task<Void> zaa(@NonNull GoogleApi<O> googleApi, @NonNull RegisterListenerMethod<AnyClient, ?> registerListenerMethod, @NonNull UnregisterListenerMethod<AnyClient, ?> unregisterListenerMethod) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        zac zag = new zag(new zabv(registerListenerMethod, unregisterListenerMethod), taskCompletionSource);
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(8, new zabu(zag, this.zail.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    public final <O extends ApiOptions> Task<Boolean> zaa(@NonNull GoogleApi<O> googleApi, @NonNull ListenerKey<?> listenerKey) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        zac zah = new zah(listenerKey, taskCompletionSource);
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(13, new zabu(zah, this.zail.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x01b8  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0172  */
    @androidx.annotation.WorkerThread
    public boolean handleMessage(android.os.Message r8) {
        /*
        r7 = this;
        r0 = r8.what;
        r1 = 1;
        r2 = 300000; // 0x493e0 float:4.2039E-40 double:1.482197E-318;
        r4 = "GoogleApiManager";
        r5 = 0;
        r6 = 0;
        switch(r0) {
            case 1: goto L_0x0297;
            case 2: goto L_0x0241;
            case 3: goto L_0x0224;
            case 4: goto L_0x01da;
            case 5: goto L_0x014c;
            case 6: goto L_0x0117;
            case 7: goto L_0x010e;
            case 8: goto L_0x01da;
            case 9: goto L_0x00f5;
            case 10: goto L_0x00d0;
            case 11: goto L_0x00b7;
            case 12: goto L_0x009e;
            case 13: goto L_0x01da;
            case 14: goto L_0x0068;
            case 15: goto L_0x0047;
            case 16: goto L_0x0026;
            default: goto L_0x000d;
        };
    L_0x000d:
        r8 = r8.what;
        r0 = 31;
        r1 = new java.lang.StringBuilder;
        r1.<init>(r0);
        r0 = "Unknown message id: ";
        r1.append(r0);
        r1.append(r8);
        r8 = r1.toString();
        android.util.Log.w(r4, r8);
        return r6;
    L_0x0026:
        r8 = r8.obj;
        r8 = (com.google.android.gms.common.api.internal.GoogleApiManager.zac) r8;
        r0 = r7.zaim;
        r2 = r8.zajh;
        r0 = r0.containsKey(r2);
        if (r0 == 0) goto L_0x02ce;
    L_0x0036:
        r0 = r7.zaim;
        r2 = r8.zajh;
        r0 = r0.get(r2);
        r0 = (com.google.android.gms.common.api.internal.GoogleApiManager.zaa) r0;
        r0.zab(r8);
        goto L_0x02ce;
    L_0x0047:
        r8 = r8.obj;
        r8 = (com.google.android.gms.common.api.internal.GoogleApiManager.zac) r8;
        r0 = r7.zaim;
        r2 = r8.zajh;
        r0 = r0.containsKey(r2);
        if (r0 == 0) goto L_0x02ce;
    L_0x0057:
        r0 = r7.zaim;
        r2 = r8.zajh;
        r0 = r0.get(r2);
        r0 = (com.google.android.gms.common.api.internal.GoogleApiManager.zaa) r0;
        r0.zaa(r8);
        goto L_0x02ce;
    L_0x0068:
        r8 = r8.obj;
        r8 = (com.google.android.gms.common.api.internal.zaae) r8;
        r0 = r8.getApiKey();
        r2 = r7.zaim;
        r2 = r2.containsKey(r0);
        if (r2 != 0) goto L_0x0085;
    L_0x0078:
        r8 = r8.zaaj();
        r0 = java.lang.Boolean.valueOf(r6);
        r8.setResult(r0);
        goto L_0x02ce;
    L_0x0085:
        r2 = r7.zaim;
        r0 = r2.get(r0);
        r0 = (com.google.android.gms.common.api.internal.GoogleApiManager.zaa) r0;
        r0 = r0.zac(false);
        r8 = r8.zaaj();
        r0 = java.lang.Boolean.valueOf(r0);
        r8.setResult(r0);
        goto L_0x02ce;
    L_0x009e:
        r0 = r7.zaim;
        r2 = r8.obj;
        r0 = r0.containsKey(r2);
        if (r0 == 0) goto L_0x02ce;
    L_0x00a8:
        r0 = r7.zaim;
        r8 = r8.obj;
        r8 = r0.get(r8);
        r8 = (com.google.android.gms.common.api.internal.GoogleApiManager.zaa) r8;
        r8.zabn();
        goto L_0x02ce;
    L_0x00b7:
        r0 = r7.zaim;
        r2 = r8.obj;
        r0 = r0.containsKey(r2);
        if (r0 == 0) goto L_0x02ce;
    L_0x00c1:
        r0 = r7.zaim;
        r8 = r8.obj;
        r8 = r0.get(r8);
        r8 = (com.google.android.gms.common.api.internal.GoogleApiManager.zaa) r8;
        r8.zaat();
        goto L_0x02ce;
    L_0x00d0:
        r8 = r7.zaip;
        r8 = r8.iterator();
    L_0x00d6:
        r0 = r8.hasNext();
        if (r0 == 0) goto L_0x00ee;
    L_0x00dc:
        r0 = r8.next();
        r0 = (com.google.android.gms.common.api.internal.ApiKey) r0;
        r2 = r7.zaim;
        r0 = r2.remove(r0);
        r0 = (com.google.android.gms.common.api.internal.GoogleApiManager.zaa) r0;
        r0.zabh();
        goto L_0x00d6;
    L_0x00ee:
        r8 = r7.zaip;
        r8.clear();
        goto L_0x02ce;
    L_0x00f5:
        r0 = r7.zaim;
        r2 = r8.obj;
        r0 = r0.containsKey(r2);
        if (r0 == 0) goto L_0x02ce;
    L_0x00ff:
        r0 = r7.zaim;
        r8 = r8.obj;
        r8 = r0.get(r8);
        r8 = (com.google.android.gms.common.api.internal.GoogleApiManager.zaa) r8;
        r8.resume();
        goto L_0x02ce;
    L_0x010e:
        r8 = r8.obj;
        r8 = (com.google.android.gms.common.api.GoogleApi) r8;
        r7.zab(r8);
        goto L_0x02ce;
    L_0x0117:
        r8 = com.google.android.gms.common.util.PlatformVersion.isAtLeastIceCreamSandwich();
        if (r8 == 0) goto L_0x02ce;
    L_0x011d:
        r8 = r7.zaih;
        r8 = r8.getApplicationContext();
        r8 = r8 instanceof android.app.Application;
        if (r8 == 0) goto L_0x02ce;
    L_0x0127:
        r8 = r7.zaih;
        r8 = r8.getApplicationContext();
        r8 = (android.app.Application) r8;
        com.google.android.gms.common.api.internal.BackgroundDetector.initialize(r8);
        r8 = com.google.android.gms.common.api.internal.BackgroundDetector.getInstance();
        r0 = new com.google.android.gms.common.api.internal.zabh;
        r0.<init>(r7);
        r8.addListener(r0);
        r8 = com.google.android.gms.common.api.internal.BackgroundDetector.getInstance();
        r8 = r8.readCurrentStateIfPossible(r1);
        if (r8 != 0) goto L_0x02ce;
    L_0x0148:
        r7.zaif = r2;
        goto L_0x02ce;
    L_0x014c:
        r0 = r8.arg1;
        r8 = r8.obj;
        r8 = (com.google.android.gms.common.ConnectionResult) r8;
        r2 = r7.zaim;
        r2 = r2.values();
        r2 = r2.iterator();
    L_0x015c:
        r3 = r2.hasNext();
        if (r3 == 0) goto L_0x016f;
    L_0x0162:
        r3 = r2.next();
        r3 = (com.google.android.gms.common.api.internal.GoogleApiManager.zaa) r3;
        r6 = r3.getInstanceId();
        if (r6 != r0) goto L_0x015c;
    L_0x016e:
        goto L_0x0170;
    L_0x016f:
        r3 = r5;
    L_0x0170:
        if (r3 == 0) goto L_0x01b8;
    L_0x0172:
        r0 = new com.google.android.gms.common.api.Status;
        r2 = 17;
        r4 = r7.zaii;
        r5 = r8.getErrorCode();
        r4 = r4.getErrorString(r5);
        r8 = r8.getErrorMessage();
        r5 = java.lang.String.valueOf(r4);
        r5 = r5.length();
        r5 = r5 + 69;
        r6 = java.lang.String.valueOf(r8);
        r6 = r6.length();
        r5 = r5 + r6;
        r6 = new java.lang.StringBuilder;
        r6.<init>(r5);
        r5 = "Error resolution was canceled by the user, original error message: ";
        r6.append(r5);
        r6.append(r4);
        r4 = ": ";
        r6.append(r4);
        r6.append(r8);
        r8 = r6.toString();
        r0.<init>(r2, r8);
        r3.zac(r0);
        goto L_0x02ce;
    L_0x01b8:
        r8 = 76;
        r2 = new java.lang.StringBuilder;
        r2.<init>(r8);
        r8 = "Could not find API instance ";
        r2.append(r8);
        r2.append(r0);
        r8 = " while trying to fail enqueued calls.";
        r2.append(r8);
        r8 = r2.toString();
        r0 = new java.lang.Exception;
        r0.<init>();
        android.util.Log.wtf(r4, r8, r0);
        goto L_0x02ce;
    L_0x01da:
        r8 = r8.obj;
        r8 = (com.google.android.gms.common.api.internal.zabu) r8;
        r0 = r7.zaim;
        r2 = r8.zajz;
        r2 = r2.getApiKey();
        r0 = r0.get(r2);
        r0 = (com.google.android.gms.common.api.internal.GoogleApiManager.zaa) r0;
        if (r0 != 0) goto L_0x0201;
    L_0x01ee:
        r0 = r8.zajz;
        r7.zab(r0);
        r0 = r7.zaim;
        r2 = r8.zajz;
        r2 = r2.getApiKey();
        r0 = r0.get(r2);
        r0 = (com.google.android.gms.common.api.internal.GoogleApiManager.zaa) r0;
    L_0x0201:
        r2 = r0.requiresSignIn();
        if (r2 == 0) goto L_0x021d;
    L_0x0207:
        r2 = r7.zail;
        r2 = r2.get();
        r3 = r8.zajy;
        if (r2 == r3) goto L_0x021d;
    L_0x0211:
        r8 = r8.zajx;
        r2 = zaib;
        r8.zaa(r2);
        r0.zabh();
        goto L_0x02ce;
    L_0x021d:
        r8 = r8.zajx;
        r0.zaa(r8);
        goto L_0x02ce;
    L_0x0224:
        r8 = r7.zaim;
        r8 = r8.values();
        r8 = r8.iterator();
    L_0x022e:
        r0 = r8.hasNext();
        if (r0 == 0) goto L_0x02ce;
    L_0x0234:
        r0 = r8.next();
        r0 = (com.google.android.gms.common.api.internal.GoogleApiManager.zaa) r0;
        r0.zabj();
        r0.connect();
        goto L_0x022e;
    L_0x0241:
        r8 = r8.obj;
        r8 = (com.google.android.gms.common.api.internal.zaj) r8;
        r0 = r8.zan();
        r0 = r0.iterator();
    L_0x024d:
        r2 = r0.hasNext();
        if (r2 == 0) goto L_0x02ce;
    L_0x0253:
        r2 = r0.next();
        r2 = (com.google.android.gms.common.api.internal.ApiKey) r2;
        r3 = r7.zaim;
        r3 = r3.get(r2);
        r3 = (com.google.android.gms.common.api.internal.GoogleApiManager.zaa) r3;
        if (r3 != 0) goto L_0x026e;
    L_0x0263:
        r0 = new com.google.android.gms.common.ConnectionResult;
        r3 = 13;
        r0.<init>(r3);
        r8.zaa(r2, r0, r5);
        goto L_0x02ce;
    L_0x026e:
        r4 = r3.isConnected();
        if (r4 == 0) goto L_0x0282;
    L_0x0274:
        r4 = com.google.android.gms.common.ConnectionResult.RESULT_SUCCESS;
        r3 = r3.zaad();
        r3 = r3.getEndpointPackageName();
        r8.zaa(r2, r4, r3);
        goto L_0x024d;
    L_0x0282:
        r4 = r3.zabk();
        if (r4 == 0) goto L_0x0290;
    L_0x0288:
        r3 = r3.zabk();
        r8.zaa(r2, r3, r5);
        goto L_0x024d;
    L_0x0290:
        r3.zaa(r8);
        r3.connect();
        goto L_0x024d;
    L_0x0297:
        r8 = r8.obj;
        r8 = (java.lang.Boolean) r8;
        r8 = r8.booleanValue();
        if (r8 == 0) goto L_0x02a3;
    L_0x02a1:
        r2 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
    L_0x02a3:
        r7.zaif = r2;
        r8 = r7.handler;
        r0 = 12;
        r8.removeMessages(r0);
        r8 = r7.zaim;
        r8 = r8.keySet();
        r8 = r8.iterator();
    L_0x02b6:
        r2 = r8.hasNext();
        if (r2 == 0) goto L_0x02ce;
    L_0x02bc:
        r2 = r8.next();
        r2 = (com.google.android.gms.common.api.internal.ApiKey) r2;
        r3 = r7.handler;
        r2 = r3.obtainMessage(r0, r2);
        r4 = r7.zaif;
        r3.sendMessageDelayed(r2, r4);
        goto L_0x02b6;
    L_0x02ce:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.GoogleApiManager.handleMessage(android.os.Message):boolean");
    }

    final PendingIntent zaa(ApiKey<?> apiKey, int i) {
        zaa zaa = (zaa) this.zaim.get(apiKey);
        if (zaa == null) {
            return null;
        }
        com.google.android.gms.signin.zac zabo = zaa.zabo();
        if (zabo == null) {
            return null;
        }
        return PendingIntent.getActivity(this.zaih, i, zabo.getSignInIntent(), 134217728);
    }

    final boolean zac(ConnectionResult connectionResult, int i) {
        return this.zaii.zaa(this.zaih, connectionResult, i);
    }

    public final void zaa(ConnectionResult connectionResult, int i) {
        if (!zac(connectionResult, i)) {
            Handler handler = this.handler;
            handler.sendMessage(handler.obtainMessage(5, i, 0, connectionResult));
        }
    }
}
