package com.google.firebase.auth.api.internal;

import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskApiCall.Builder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.firebase_auth.zzct;
import com.google.android.gms.internal.firebase_auth.zze;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.internal.zza;

@VisibleForTesting
final class zzch extends zzen<Void, zza> {
    @NonNull
    private final zzct zznw;
    private final String zznx;

    public zzch(String str, ActionCodeSettings actionCodeSettings, @Nullable String str2, String str3) {
        super(4);
        Preconditions.checkNotEmpty(str, "email cannot be null or empty");
        this.zznw = new zzct(str, actionCodeSettings, str2);
        this.zznx = str3;
    }

    public final String zzdu() {
        return this.zznx;
    }

    public final TaskApiCall<zzdp, Void> zzdv() {
        Feature[] featureArr;
        Builder autoResolveMissingFeatures = TaskApiCall.builder().setAutoResolveMissingFeatures(false);
        if (this.zzqh) {
            featureArr = null;
        } else {
            featureArr = new Feature[]{zze.zzf};
        }
        return autoResolveMissingFeatures.setFeatures(featureArr).run(new zzcg(this)).build();
    }

    public final void zzdx() {
        zzc(null);
    }

    final /* synthetic */ void zzs(zzdp zzdp, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzpu = new zzeu(this, taskCompletionSource);
        if (this.zzqh) {
            zzdp.zzeb().zzc(this.zznw.getEmail(), this.zznw.zzdj(), this.zzpq);
        } else {
            zzdp.zzeb().zza(this.zznw, this.zzpq);
        }
    }
}
