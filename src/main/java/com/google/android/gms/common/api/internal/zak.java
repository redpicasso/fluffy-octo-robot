package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.base.zar;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public abstract class zak extends LifecycleCallback implements OnCancelListener {
    protected final GoogleApiAvailability zace;
    protected volatile boolean zadh;
    protected final AtomicReference<zam> zadi;
    private final Handler zadj;

    protected zak(LifecycleFragment lifecycleFragment) {
        this(lifecycleFragment, GoogleApiAvailability.getInstance());
    }

    protected abstract void zaa(ConnectionResult connectionResult, int i);

    protected abstract void zam();

    @VisibleForTesting
    private zak(LifecycleFragment lifecycleFragment, GoogleApiAvailability googleApiAvailability) {
        super(lifecycleFragment);
        this.zadi = new AtomicReference(null);
        this.zadj = new zar(Looper.getMainLooper());
        this.zace = googleApiAvailability;
    }

    public void onCancel(DialogInterface dialogInterface) {
        zaa(new ConnectionResult(13, null), zaa((zam) this.zadi.get()));
        zao();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.zadi.set(bundle.getBoolean("resolving_error", false) ? new zam(new ConnectionResult(bundle.getInt("failed_status"), (PendingIntent) bundle.getParcelable("failed_resolution")), bundle.getInt("failed_client_id", -1)) : null);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        zam zam = (zam) this.zadi.get();
        if (zam != null) {
            bundle.putBoolean("resolving_error", true);
            bundle.putInt("failed_client_id", zam.zap());
            bundle.putInt("failed_status", zam.getConnectionResult().getErrorCode());
            bundle.putParcelable("failed_resolution", zam.getConnectionResult().getResolution());
        }
    }

    public void onStart() {
        super.onStart();
        this.zadh = true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0064  */
    public void onActivityResult(int r5, int r6, android.content.Intent r7) {
        /*
        r4 = this;
        r0 = r4.zadi;
        r0 = r0.get();
        r0 = (com.google.android.gms.common.api.internal.zam) r0;
        r1 = 1;
        r2 = 0;
        if (r5 == r1) goto L_0x0030;
    L_0x000c:
        r6 = 2;
        if (r5 == r6) goto L_0x0010;
    L_0x000f:
        goto L_0x005d;
    L_0x0010:
        r5 = r4.zace;
        r6 = r4.getActivity();
        r5 = r5.isGooglePlayServicesAvailable(r6);
        if (r5 != 0) goto L_0x001d;
    L_0x001c:
        goto L_0x001e;
    L_0x001d:
        r1 = 0;
    L_0x001e:
        if (r0 != 0) goto L_0x0021;
    L_0x0020:
        return;
    L_0x0021:
        r6 = r0.getConnectionResult();
        r6 = r6.getErrorCode();
        r7 = 18;
        if (r6 != r7) goto L_0x005e;
    L_0x002d:
        if (r5 != r7) goto L_0x005e;
    L_0x002f:
        return;
    L_0x0030:
        r5 = -1;
        if (r6 != r5) goto L_0x0034;
    L_0x0033:
        goto L_0x005e;
    L_0x0034:
        if (r6 != 0) goto L_0x005d;
    L_0x0036:
        r5 = 13;
        if (r7 == 0) goto L_0x0040;
    L_0x003a:
        r6 = "<<ResolutionFailureErrorDetail>>";
        r5 = r7.getIntExtra(r6, r5);
    L_0x0040:
        r6 = new com.google.android.gms.common.api.internal.zam;
        r7 = new com.google.android.gms.common.ConnectionResult;
        r1 = 0;
        r3 = r0.getConnectionResult();
        r3 = r3.toString();
        r7.<init>(r5, r1, r3);
        r5 = zaa(r0);
        r6.<init>(r7, r5);
        r5 = r4.zadi;
        r5.set(r6);
        r0 = r6;
    L_0x005d:
        r1 = 0;
    L_0x005e:
        if (r1 == 0) goto L_0x0064;
    L_0x0060:
        r4.zao();
        return;
    L_0x0064:
        if (r0 == 0) goto L_0x0071;
    L_0x0066:
        r5 = r0.getConnectionResult();
        r6 = r0.zap();
        r4.zaa(r5, r6);
    L_0x0071:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zak.onActivityResult(int, int, android.content.Intent):void");
    }

    public void onStop() {
        super.onStop();
        this.zadh = false;
    }

    protected final void zao() {
        this.zadi.set(null);
        zam();
    }

    public final void zab(ConnectionResult connectionResult, int i) {
        zam zam = new zam(connectionResult, i);
        if (this.zadi.compareAndSet(null, zam)) {
            this.zadj.post(new zal(this, zam));
        }
    }

    private static int zaa(@Nullable zam zam) {
        return zam == null ? -1 : zam.zap();
    }
}
