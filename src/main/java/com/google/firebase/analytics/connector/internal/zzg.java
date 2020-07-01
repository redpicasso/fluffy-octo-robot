package com.google.firebase.analytics.connector.internal;

import android.os.Bundle;
import com.google.android.gms.measurement.AppMeasurement.OnEventListener;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;

final class zzg implements OnEventListener {
    private final /* synthetic */ zze zzacv;

    public zzg(zze zze) {
        this.zzacv = zze;
    }

    public final void onEvent(String str, String str2, Bundle bundle, long j) {
        if (str != null && !str.equals("crash") && zzd.zzdk(str2)) {
            Bundle bundle2 = new Bundle();
            bundle2.putString(ConditionalUserProperty.NAME, str2);
            bundle2.putLong("timestampInMillis", j);
            bundle2.putBundle("params", bundle);
            this.zzacv.zzacj.onMessageTriggered(3, bundle2);
        }
    }
}
