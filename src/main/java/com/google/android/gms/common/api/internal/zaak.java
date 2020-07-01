package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.AnyClientKey;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.ClientSettings.OptionalApiSettings;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.internal.zak;
import com.google.android.gms.signin.zac;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zaak implements zabb {
    private final Context mContext;
    private final AbstractClientBuilder<? extends zac, SignInOptions> zacf;
    private final Lock zaer;
    private final Map<Api<?>, Boolean> zaew;
    private final GoogleApiAvailabilityLight zaey;
    private final ClientSettings zafa;
    private ConnectionResult zafi;
    private final zabe zafv;
    private int zaga;
    private int zagb = 0;
    private int zagc;
    private final Bundle zagd = new Bundle();
    private final Set<AnyClientKey> zage = new HashSet();
    private zac zagf;
    private boolean zagg;
    private boolean zagh;
    private boolean zagi;
    private IAccountAccessor zagj;
    private boolean zagk;
    private boolean zagl;
    private ArrayList<Future<?>> zagm = new ArrayList();

    public zaak(zabe zabe, ClientSettings clientSettings, Map<Api<?>, Boolean> map, GoogleApiAvailabilityLight googleApiAvailabilityLight, AbstractClientBuilder<? extends zac, SignInOptions> abstractClientBuilder, Lock lock, Context context) {
        this.zafv = zabe;
        this.zafa = clientSettings;
        this.zaew = map;
        this.zaey = googleApiAvailabilityLight;
        this.zacf = abstractClientBuilder;
        this.zaer = lock;
        this.mContext = context;
    }

    private static String zad(int i) {
        return i != 0 ? i != 1 ? "UNKNOWN" : "STEP_GETTING_REMOTE_SERVICE" : "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
    }

    public final void connect() {
    }

    @GuardedBy("mLock")
    public final void begin() {
        this.zafv.zaht.clear();
        this.zagh = false;
        this.zafi = null;
        this.zagb = 0;
        this.zagg = true;
        this.zagi = false;
        this.zagk = false;
        Map hashMap = new HashMap();
        int i = 0;
        for (Api api : this.zaew.keySet()) {
            Client client = (Client) this.zafv.zahd.get(api.getClientKey());
            i |= api.zah().getPriority() == 1 ? 1 : 0;
            boolean booleanValue = ((Boolean) this.zaew.get(api)).booleanValue();
            if (client.requiresSignIn()) {
                this.zagh = true;
                if (booleanValue) {
                    this.zage.add(api.getClientKey());
                } else {
                    this.zagg = false;
                }
            }
            hashMap.put(client, new zaam(this, api, booleanValue));
        }
        if (i != 0) {
            this.zagh = false;
        }
        if (this.zagh) {
            this.zafa.setClientSessionId(Integer.valueOf(System.identityHashCode(this.zafv.zaeh)));
            OnConnectionFailedListener zaar = new zaar(this, null);
            AbstractClientBuilder abstractClientBuilder = this.zacf;
            Context context = this.mContext;
            Looper looper = this.zafv.zaeh.getLooper();
            ClientSettings clientSettings = this.zafa;
            this.zagf = (zac) abstractClientBuilder.buildClient(context, looper, clientSettings, clientSettings.getSignInOptions(), (ConnectionCallbacks) zaar, zaar);
        }
        this.zagc = this.zafv.zahd.size();
        this.zagm.add(zabf.zaaz().submit(new zaal(this, hashMap)));
    }

    @GuardedBy("mLock")
    private final boolean zaam() {
        this.zagc--;
        int i = this.zagc;
        if (i > 0) {
            return false;
        }
        if (i < 0) {
            String str = "GACConnecting";
            Log.w(str, this.zafv.zaeh.zaaw());
            Log.wtf(str, "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
            zae(new ConnectionResult(8, null));
            return false;
        }
        ConnectionResult connectionResult = this.zafi;
        if (connectionResult == null) {
            return true;
        }
        this.zafv.zahw = this.zaga;
        zae(connectionResult);
        return false;
    }

    @GuardedBy("mLock")
    private final void zaa(zak zak) {
        if (zac(0)) {
            ConnectionResult connectionResult = zak.getConnectionResult();
            if (connectionResult.isSuccess()) {
                ResolveAccountResponse zacv = zak.zacv();
                connectionResult = zacv.getConnectionResult();
                if (connectionResult.isSuccess()) {
                    this.zagi = true;
                    this.zagj = zacv.getAccountAccessor();
                    this.zagk = zacv.getSaveDefaultAccount();
                    this.zagl = zacv.isFromCrossClientAuth();
                    zaan();
                    return;
                }
                String valueOf = String.valueOf(connectionResult);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 48);
                stringBuilder.append("Sign-in succeeded with resolve account failure: ");
                stringBuilder.append(valueOf);
                Log.wtf("GACConnecting", stringBuilder.toString(), new Exception());
                zae(connectionResult);
            } else if (zad(connectionResult)) {
                zaap();
                zaan();
            } else {
                zae(connectionResult);
            }
        }
    }

    @GuardedBy("mLock")
    private final void zaan() {
        if (this.zagc == 0) {
            if (!this.zagh || this.zagi) {
                ArrayList arrayList = new ArrayList();
                this.zagb = 1;
                this.zagc = this.zafv.zahd.size();
                for (AnyClientKey anyClientKey : this.zafv.zahd.keySet()) {
                    if (!this.zafv.zaht.containsKey(anyClientKey)) {
                        arrayList.add((Client) this.zafv.zahd.get(anyClientKey));
                    } else if (zaam()) {
                        zaao();
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.zagm.add(zabf.zaaz().submit(new zaaq(this, arrayList)));
                }
            }
        }
    }

    @GuardedBy("mLock")
    public final void onConnected(Bundle bundle) {
        if (zac(1)) {
            if (bundle != null) {
                this.zagd.putAll(bundle);
            }
            if (zaam()) {
                zaao();
            }
        }
    }

    @GuardedBy("mLock")
    public final void zaa(ConnectionResult connectionResult, Api<?> api, boolean z) {
        if (zac(1)) {
            zab(connectionResult, api, z);
            if (zaam()) {
                zaao();
            }
        }
    }

    @GuardedBy("mLock")
    private final void zaao() {
        this.zafv.zaay();
        zabf.zaaz().execute(new zaaj(this));
        zac zac = this.zagf;
        if (zac != null) {
            if (this.zagk) {
                zac.zaa(this.zagj, this.zagl);
            }
            zab(false);
        }
        for (AnyClientKey anyClientKey : this.zafv.zaht.keySet()) {
            ((Client) this.zafv.zahd.get(anyClientKey)).disconnect();
        }
        this.zafv.zahx.zab(this.zagd.isEmpty() ? null : this.zagd);
    }

    public final <A extends AnyClient, R extends Result, T extends ApiMethodImpl<R, A>> T enqueue(T t) {
        this.zafv.zaeh.zafd.add(t);
        return t;
    }

    public final <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T execute(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    @GuardedBy("mLock")
    public final boolean disconnect() {
        zaaq();
        zab(true);
        this.zafv.zaf(null);
        return true;
    }

    @GuardedBy("mLock")
    public final void onConnectionSuspended(int i) {
        zae(new ConnectionResult(8, null));
    }

    /* JADX WARNING: Missing block: B:8:0x0022, code:
            if (r7 != null) goto L_0x0024;
     */
    @javax.annotation.concurrent.GuardedBy("mLock")
    private final void zab(com.google.android.gms.common.ConnectionResult r5, com.google.android.gms.common.api.Api<?> r6, boolean r7) {
        /*
        r4 = this;
        r0 = r6.zah();
        r0 = r0.getPriority();
        r1 = 0;
        r2 = 1;
        if (r7 == 0) goto L_0x0024;
    L_0x000c:
        r7 = r5.hasResolution();
        if (r7 == 0) goto L_0x0014;
    L_0x0012:
        r7 = 1;
        goto L_0x0022;
    L_0x0014:
        r7 = r4.zaey;
        r3 = r5.getErrorCode();
        r7 = r7.getErrorResolutionIntent(r3);
        if (r7 == 0) goto L_0x0021;
    L_0x0020:
        goto L_0x0012;
    L_0x0021:
        r7 = 0;
    L_0x0022:
        if (r7 == 0) goto L_0x002d;
    L_0x0024:
        r7 = r4.zafi;
        if (r7 == 0) goto L_0x002c;
    L_0x0028:
        r7 = r4.zaga;
        if (r0 >= r7) goto L_0x002d;
    L_0x002c:
        r1 = 1;
    L_0x002d:
        if (r1 == 0) goto L_0x0033;
    L_0x002f:
        r4.zafi = r5;
        r4.zaga = r0;
    L_0x0033:
        r7 = r4.zafv;
        r7 = r7.zaht;
        r6 = r6.getClientKey();
        r7.put(r6, r5);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zaak.zab(com.google.android.gms.common.ConnectionResult, com.google.android.gms.common.api.Api, boolean):void");
    }

    @GuardedBy("mLock")
    private final void zaap() {
        this.zagh = false;
        this.zafv.zaeh.zahe = Collections.emptySet();
        for (AnyClientKey anyClientKey : this.zage) {
            if (!this.zafv.zaht.containsKey(anyClientKey)) {
                this.zafv.zaht.put(anyClientKey, new ConnectionResult(17, null));
            }
        }
    }

    @GuardedBy("mLock")
    private final boolean zad(ConnectionResult connectionResult) {
        return this.zagg && !connectionResult.hasResolution();
    }

    @GuardedBy("mLock")
    private final void zae(ConnectionResult connectionResult) {
        zaaq();
        zab(connectionResult.hasResolution() ^ 1);
        this.zafv.zaf(connectionResult);
        this.zafv.zahx.zac(connectionResult);
    }

    @GuardedBy("mLock")
    private final void zab(boolean z) {
        zac zac = this.zagf;
        if (zac != null) {
            if (zac.isConnected() && z) {
                this.zagf.zacu();
            }
            this.zagf.disconnect();
            if (this.zafa.isSignInClientDisconnectFixEnabled()) {
                this.zagf = null;
            }
            this.zagj = null;
        }
    }

    private final void zaaq() {
        ArrayList arrayList = this.zagm;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Future) obj).cancel(true);
        }
        this.zagm.clear();
    }

    private final Set<Scope> zaar() {
        ClientSettings clientSettings = this.zafa;
        if (clientSettings == null) {
            return Collections.emptySet();
        }
        Set<Scope> hashSet = new HashSet(clientSettings.getRequiredScopes());
        Map optionalApiSettings = this.zafa.getOptionalApiSettings();
        for (Api api : optionalApiSettings.keySet()) {
            if (!this.zafv.zaht.containsKey(api.getClientKey())) {
                hashSet.addAll(((OptionalApiSettings) optionalApiSettings.get(api)).mScopes);
            }
        }
        return hashSet;
    }

    @GuardedBy("mLock")
    private final boolean zac(int i) {
        if (this.zagb == i) {
            return true;
        }
        String str = "GACConnecting";
        Log.w(str, this.zafv.zaeh.zaaw());
        String valueOf = String.valueOf(this);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 23);
        stringBuilder.append("Unexpected callback in ");
        stringBuilder.append(valueOf);
        Log.w(str, stringBuilder.toString());
        int i2 = this.zagc;
        stringBuilder = new StringBuilder(33);
        stringBuilder.append("mRemainingConnections=");
        stringBuilder.append(i2);
        Log.w(str, stringBuilder.toString());
        valueOf = zad(this.zagb);
        String zad = zad(i);
        stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 70) + String.valueOf(zad).length());
        stringBuilder.append("GoogleApiClient connecting is in step ");
        stringBuilder.append(valueOf);
        stringBuilder.append(" but received callback for step ");
        stringBuilder.append(zad);
        Log.e(str, stringBuilder.toString(), new Exception());
        zae(new ConnectionResult(8, null));
        return false;
    }
}
