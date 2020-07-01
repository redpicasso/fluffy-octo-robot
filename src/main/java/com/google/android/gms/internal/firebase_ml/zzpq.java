package com.google.android.gms.internal.firebase_ml;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.auth.api.AuthProxy;
import com.google.android.gms.auth.api.proxy.ProxyApi.SpatulaHeaderResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.util.AndroidUtilsLight;
import com.google.android.gms.common.util.Hex;
import com.google.android.gms.internal.firebase_ml.zzej.zza;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class zzpq implements zznm<zzir, zzpn> {
    private static final GmsLogger zzaoz = new GmsLogger("ImageAnnotatorTask", "");
    private static final zzfo zzawr = new zzga();
    private static final zzge zzaws = zzgn.zzgx();
    private static final Map<FirebaseApp, zzpq> zzax = new HashMap();
    private final GoogleApiClient zzawp;
    private String zzawt;
    private final boolean zzawu;
    private final zzip zzawv;
    private final String zzsq;

    public static synchronized zzpq zza(@NonNull FirebaseApp firebaseApp, boolean z, @Nullable GoogleApiClient googleApiClient) {
        zzpq zzpq;
        synchronized (zzpq.class) {
            zzpq = (zzpq) zzax.get(firebaseApp);
            if (zzpq == null) {
                zzpq = new zzpq(firebaseApp, z, googleApiClient);
                zzax.put(firebaseApp, zzpq);
            }
        }
        return zzpq;
    }

    public final zznw zzlm() {
        return null;
    }

    private zzpq(FirebaseApp firebaseApp, boolean z, GoogleApiClient googleApiClient) {
        this.zzawu = z;
        if (z) {
            this.zzawp = googleApiClient;
            this.zzawv = new zzpr(this);
        } else {
            this.zzawp = null;
            this.zzawv = new zzps(this, zzi(firebaseApp), firebaseApp);
        }
        this.zzsq = String.format("FirebaseML_%s", new Object[]{firebaseApp.getName()});
    }

    private static String zzi(FirebaseApp firebaseApp) {
        String apiKey = firebaseApp.getOptions().getApiKey();
        Context applicationContext = firebaseApp.getApplicationContext();
        try {
            ApplicationInfo applicationInfo = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 128);
            if (applicationInfo == null) {
                return apiKey;
            }
            Bundle bundle = applicationInfo.metaData;
            if (bundle == null) {
                return apiKey;
            }
            CharSequence string = bundle.getString("com.firebase.ml.cloud.ApiKeyForDebug");
            if (TextUtils.isEmpty(string)) {
                return apiKey;
            }
            return string;
        } catch (Throwable e) {
            GmsLogger gmsLogger = zzaoz;
            String str = "No such package: ";
            String valueOf = String.valueOf(applicationContext.getPackageName());
            gmsLogger.e("ImageAnnotatorTask", valueOf.length() != 0 ? str.concat(valueOf) : new String(str), e);
            return apiKey;
        }
    }

    private static String zza(Context context, String str) {
        String str2 = "ImageAnnotatorTask";
        String valueOf;
        try {
            byte[] packageCertificateHashBytes = AndroidUtilsLight.getPackageCertificateHashBytes(context, str);
            if (packageCertificateHashBytes != null) {
                return Hex.bytesToStringUppercase(packageCertificateHashBytes, false);
            }
            GmsLogger gmsLogger = zzaoz;
            String str3 = "Could not get fingerprint hash: ";
            valueOf = String.valueOf(str);
            gmsLogger.e(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return null;
        } catch (Throwable e) {
            GmsLogger gmsLogger2 = zzaoz;
            valueOf = "No such package: ";
            str = String.valueOf(str);
            gmsLogger2.e(str2, str.length() != 0 ? valueOf.concat(str) : new String(valueOf), e);
            return null;
        }
    }

    @WorkerThread
    private final String getSpatulaHeader() throws FirebaseMLException {
        if (!this.zzawp.isConnected()) {
            this.zzawp.blockingConnect(3, TimeUnit.SECONDS);
        }
        try {
            return ((SpatulaHeaderResult) AuthProxy.ProxyApi.getSpatulaHeader(this.zzawp).await(3, TimeUnit.SECONDS)).getSpatulaHeader();
        } catch (SecurityException unused) {
            return null;
        }
    }

    @WorkerThread
    public final zzir zza(zzpn zzpn) throws FirebaseMLException {
        String str = "ImageAnnotatorTask";
        zzis zzc = new zzis().zzc(Collections.singletonList(new zziq().zzb(zzpn.features).zza(new zzjd().zze(zzpn.zzawk)).zza(zzpn.imageContext)));
        int i = 14;
        try {
            zzik zzhl = ((zzil) new zzil(zzawr, zzaws, new zzpt(this)).zzn(this.zzsq)).zza(this.zzawv).zzhl();
            if (this.zzawu) {
                this.zzawt = getSpatulaHeader();
                if (TextUtils.isEmpty(this.zzawt)) {
                    String str2 = "Failed to contact Google Play services for certificate fingerprint matching. Please ensure you have latest Google Play services installed";
                    zzaoz.e(str, str2);
                    throw new FirebaseMLException(str2, 14);
                }
            }
            List zzhp = ((zzit) new zzim(zzhl).zza(zzc).zzep()).zzhp();
            if (zzhp != null && zzhp.size() > 0) {
                return (zzir) zzhp.get(0);
            }
            throw new FirebaseMLException("Empty response from cloud vision api.", 13);
        } catch (zzfl e) {
            GmsLogger gmsLogger = zzaoz;
            String valueOf = String.valueOf(e.zzei());
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 44);
            stringBuilder.append("batchAnnotateImages call failed with error: ");
            stringBuilder.append(valueOf);
            gmsLogger.e(str, stringBuilder.toString());
            if (this.zzawu) {
                Log.d(str, "If you are developing / testing on a simulator, either register your development app on Firebase console or turn off enforceCertFingerprintMatch()");
            }
            str = e.getMessage();
            if (e.getStatusCode() != 400) {
                if (e.zzei() != null && e.zzei().zzef() != null) {
                    i = 13;
                    for (zza reason : e.zzei().zzef()) {
                        String reason2 = reason.getReason();
                        if (reason2 != null) {
                            if (reason2.equals("rateLimitExceeded") || reason2.equals("dailyLimitExceeded") || reason2.equals("userRateLimitExceeded")) {
                                i = 8;
                            } else {
                                if (!reason2.equals("accessNotConfigured")) {
                                    if (reason2.equals("forbidden") || reason2.equals("inactiveBillingState")) {
                                        str = String.format("If you haven't set up billing, please go to Firebase console to set up billing: %s. If you are specifying a debug Api Key override and turned on Api Key restrictions, make sure the restrictions are set up correctly", new Object[]{"https://firebase.corp.google.com/u/0/project/_/overview?purchaseBillingPlan=true"});
                                    }
                                }
                                i = 7;
                            }
                        }
                        if (i != 13) {
                            break;
                        }
                    }
                } else {
                    i = 13;
                }
            }
            throw new FirebaseMLException(str, i);
        } catch (Throwable e2) {
            zzaoz.e(str, "batchAnnotateImages call failed with exception: ", e2);
            throw new FirebaseMLException("Cloud Vision batchAnnotateImages call failure", 13, e2);
        }
    }
}
