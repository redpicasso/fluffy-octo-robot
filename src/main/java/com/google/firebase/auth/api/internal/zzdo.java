package com.google.firebase.auth.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.internal.firebase_auth.zze;

public final class zzdo extends GmsClient<zzdz> implements zzdp {
    private static Logger zzjt = new Logger("FirebaseAuth", "FirebaseAuth:");
    private final Context zzml;
    private final zzee zzos;

    public zzdo(Context context, Looper looper, ClientSettings clientSettings, zzee zzee, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 112, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zzml = (Context) Preconditions.checkNotNull(context);
        this.zzos = zzee;
    }

    public final int getMinApkVersion() {
        return GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    protected final String getServiceDescriptor() {
        return "com.google.firebase.auth.api.internal.IFirebaseAuthService";
    }

    protected final String getStartServiceAction() {
        return "com.google.firebase.auth.api.gms.service.START";
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0074  */
    protected final java.lang.String getStartServicePackage() {
        /*
        r9 = this;
        r0 = "firebear.preference";
        r0 = com.google.firebase.auth.api.internal.zzfe.getProperty(r0);
        r1 = android.text.TextUtils.isEmpty(r0);
        r2 = "default";
        if (r1 == 0) goto L_0x000f;
    L_0x000e:
        r0 = r2;
    L_0x000f:
        r1 = r0.hashCode();
        r3 = "local";
        r4 = 1;
        r5 = 103145323; // 0x625df6b float:3.1197192E-35 double:5.09605606E-316;
        r6 = -1;
        r7 = 0;
        if (r1 == r5) goto L_0x002b;
    L_0x001d:
        r8 = 1544803905; // 0x5c13d641 float:1.66449585E17 double:7.63234539E-315;
        if (r1 == r8) goto L_0x0023;
    L_0x0022:
        goto L_0x0033;
    L_0x0023:
        r1 = r0.equals(r2);
        if (r1 == 0) goto L_0x0033;
    L_0x0029:
        r1 = 1;
        goto L_0x0034;
    L_0x002b:
        r1 = r0.equals(r3);
        if (r1 == 0) goto L_0x0033;
    L_0x0031:
        r1 = 0;
        goto L_0x0034;
    L_0x0033:
        r1 = -1;
    L_0x0034:
        if (r1 == 0) goto L_0x0039;
    L_0x0036:
        if (r1 == r4) goto L_0x0039;
    L_0x0038:
        r0 = r2;
    L_0x0039:
        r1 = r0.hashCode();
        if (r1 == r5) goto L_0x0040;
    L_0x003f:
        goto L_0x0047;
    L_0x0040:
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x0047;
    L_0x0046:
        r6 = 0;
    L_0x0047:
        if (r6 == 0) goto L_0x0074;
    L_0x0049:
        r0 = zzjt;
        r1 = new java.lang.Object[r7];
        r2 = "Loading module via FirebaseOptions.";
        r0.i(r2, r1);
        r0 = r9.zzos;
        r0 = r0.zzmc;
        if (r0 == 0) goto L_0x0068;
    L_0x0058:
        r0 = zzjt;
        r1 = new java.lang.Object[r7];
        r2 = "Preparing to create service connection to fallback implementation";
        r0.i(r2, r1);
        r0 = r9.zzml;
        r0 = r0.getPackageName();
        return r0;
    L_0x0068:
        r0 = zzjt;
        r1 = new java.lang.Object[r7];
        r2 = "Preparing to create service connection to gms implementation";
        r0.i(r2, r1);
        r0 = "com.google.android.gms";
        return r0;
    L_0x0074:
        r0 = zzjt;
        r1 = new java.lang.Object[r7];
        r2 = "Loading fallback module override.";
        r0.i(r2, r1);
        r0 = r9.zzml;
        r0 = r0.getPackageName();
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.api.internal.zzdo.getStartServicePackage():java.lang.String");
    }

    public final boolean requiresGooglePlayServices() {
        return DynamiteModule.getLocalVersion(this.zzml, "com.google.firebase.auth") == 0;
    }

    protected final Bundle getGetServiceRequestExtraArgs() {
        Bundle getServiceRequestExtraArgs = super.getGetServiceRequestExtraArgs();
        if (getServiceRequestExtraArgs == null) {
            getServiceRequestExtraArgs = new Bundle();
        }
        zzee zzee = this.zzos;
        if (zzee != null) {
            getServiceRequestExtraArgs.putString("com.google.firebase.auth.API_KEY", zzee.getApiKey());
        }
        getServiceRequestExtraArgs.putString("com.google.firebase.auth.LIBRARY_VERSION", zzeg.zzek());
        return getServiceRequestExtraArgs;
    }

    public final Feature[] getApiFeatures() {
        return zze.zzh;
    }

    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.firebase.auth.api.internal.IFirebaseAuthService");
        if (queryLocalInterface instanceof zzdz) {
            return (zzdz) queryLocalInterface;
        }
        return new zzea(iBinder);
    }

    @KeepForSdk
    public final /* synthetic */ zzdz zzeb() throws DeadObjectException {
        return (zzdz) super.getService();
    }
}
