package com.google.firebase.auth.internal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.api.internal.zzdr;
import java.lang.ref.WeakReference;

final class zzah extends BroadcastReceiver {
    private final FirebaseAuth zzjr;
    private final FirebaseUser zzpr;
    private final WeakReference<Activity> zzuf;
    private final TaskCompletionSource<AuthResult> zzug;
    private final /* synthetic */ zzac zzuh;

    zzah(zzac zzac, Activity activity, TaskCompletionSource<AuthResult> taskCompletionSource, FirebaseAuth firebaseAuth, FirebaseUser firebaseUser) {
        this.zzuh = zzac;
        this.zzuf = new WeakReference(activity);
        this.zzug = taskCompletionSource;
        this.zzjr = firebaseAuth;
        this.zzpr = firebaseUser;
    }

    public final void onReceive(Context context, Intent intent) {
        Activity activity = (Activity) this.zzuf.get();
        if (activity == null) {
            Log.e("FederatedAuthReceiver", "Failed to unregister BroadcastReceiver because the Activity that launched this flow has been garbage collected; please do not finish() your Activity while performing a FederatedAuthProvider operation.");
            this.zzug.setException(zzdr.zzb(new Status(FirebaseError.ERROR_INTERNAL_ERROR, "Activity that started the web operation is no longer alive; see logcat for details")));
            zzac.zzfl();
            return;
        }
        LocalBroadcastManager.getInstance(activity).unregisterReceiver(this);
        String str = "com.google.firebase.auth.internal.OPERATION";
        if (intent.hasExtra(str)) {
            str = intent.getStringExtra(str);
            if ("com.google.firebase.auth.internal.SIGN_IN".equals(str)) {
                this.zzuh.zza(intent, this.zzug, this.zzjr);
            } else if ("com.google.firebase.auth.internal.LINK".equals(str)) {
                this.zzuh.zza(intent, this.zzug, this.zzpr);
            } else if ("com.google.firebase.auth.internal.REAUTHENTICATE".equals(str)) {
                this.zzuh.zzb(intent, this.zzug, this.zzpr);
            } else {
                TaskCompletionSource taskCompletionSource = this.zzug;
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 50);
                stringBuilder.append("WEB_CONTEXT_CANCELED:Unknown operation received (");
                stringBuilder.append(str);
                stringBuilder.append(")");
                taskCompletionSource.setException(zzdr.zzb(zzt.zzdc(stringBuilder.toString())));
            }
        } else if (zzaw.zzb(intent)) {
            this.zzug.setException(zzdr.zzb(zzaw.zzc(intent)));
            zzac.zzfl();
        } else {
            if (intent.hasExtra("com.google.firebase.auth.internal.EXTRA_CANCELED")) {
                this.zzug.setException(zzdr.zzb(zzt.zzdc("WEB_CONTEXT_CANCELED")));
                zzac.zzfl();
            }
        }
    }
}
