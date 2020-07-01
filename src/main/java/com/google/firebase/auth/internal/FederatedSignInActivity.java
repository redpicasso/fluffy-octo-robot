package com.google.firebase.auth.internal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.DefaultClock;

@KeepName
public class FederatedSignInActivity extends FragmentActivity {
    private static Handler handler;
    private static final zzak zziu = zzak.zzfn();
    private static long zzuj;
    private static Runnable zzul;
    private boolean zzuk = false;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String action = getIntent().getAction();
        String str = "IdpSignInActivity";
        if ("com.google.firebase.auth.internal.SIGN_IN".equals(action) || "com.google.firebase.auth.internal.LINK".equals(action) || "com.google.firebase.auth.internal.REAUTHENTICATE".equals(action)) {
            long currentTimeMillis = DefaultClock.getInstance().currentTimeMillis();
            if (currentTimeMillis - zzuj < 30000) {
                Log.e(str, "Could not start operation - already in progress");
                return;
            }
            zzuj = currentTimeMillis;
            if (bundle != null) {
                this.zzuk = bundle.getBoolean("com.google.firebase.auth.internal.KEY_STARTED_SIGN_IN");
            }
            return;
        }
        String str2 = "Could not do operation - unknown action: ";
        action = String.valueOf(action);
        Log.e(str, action.length() != 0 ? str2.concat(action) : new String(str2));
        zzfm();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("com.google.firebase.auth.internal.KEY_STARTED_SIGN_IN", this.zzuk);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x009f A:{RETURN} */
    protected void onResume() {
        /*
        r10 = this;
        super.onResume();
        r0 = r10.getIntent();
        r1 = r0.getAction();
        r2 = "com.google.firebase.auth.internal.WEB_SIGN_IN_FAILED";
        r1 = r2.equals(r1);
        r2 = "IdpSignInActivity";
        r3 = 0;
        r4 = 1;
        r5 = "com.google.firebase.auth.internal.OPERATION";
        if (r1 == 0) goto L_0x0031;
    L_0x0019:
        r1 = "Web sign-in failed, finishing";
        android.util.Log.e(r2, r1);
        r1 = com.google.firebase.auth.internal.zzaw.zzb(r0);
        if (r1 == 0) goto L_0x002c;
    L_0x0024:
        r0 = com.google.firebase.auth.internal.zzaw.zzc(r0);
        r10.zze(r0);
        goto L_0x002f;
    L_0x002c:
        r10.zzfm();
    L_0x002f:
        r3 = 1;
        goto L_0x009d;
    L_0x0031:
        r1 = r0.hasExtra(r5);
        if (r1 == 0) goto L_0x009d;
    L_0x0037:
        r1 = "com.google.firebase.auth.internal.VERIFY_ASSERTION_REQUEST";
        r6 = r0.hasExtra(r1);
        if (r6 == 0) goto L_0x009d;
    L_0x003f:
        r6 = r0.getStringExtra(r5);
        r7 = "com.google.firebase.auth.internal.SIGN_IN";
        r7 = r7.equals(r6);
        if (r7 != 0) goto L_0x005b;
    L_0x004b:
        r7 = "com.google.firebase.auth.internal.LINK";
        r7 = r7.equals(r6);
        if (r7 != 0) goto L_0x005b;
    L_0x0053:
        r7 = "com.google.firebase.auth.internal.REAUTHENTICATE";
        r7 = r7.equals(r6);
        if (r7 == 0) goto L_0x009d;
    L_0x005b:
        r7 = com.google.android.gms.internal.firebase_auth.zzfm.CREATOR;
        r7 = com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer.deserializeFromIntentExtra(r0, r1, r7);
        r7 = (com.google.android.gms.internal.firebase_auth.zzfm) r7;
        r8 = "com.google.firebase.auth.internal.EXTRA_TENANT_ID";
        r0 = r0.getStringExtra(r8);
        r7.zzcz(r0);
        r8 = 0;
        zzuj = r8;
        r10.zzuk = r3;
        r3 = new android.content.Intent;
        r3.<init>();
        com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer.serializeToIntentExtra(r7, r3, r1);
        r3.putExtra(r5, r6);
        r1 = "com.google.firebase.auth.ACTION_RECEIVE_FIREBASE_AUTH_INTENT";
        r3.setAction(r1);
        r1 = androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(r10);
        r1 = r1.sendBroadcast(r3);
        if (r1 != 0) goto L_0x0094;
    L_0x008c:
        r1 = r10.getApplicationContext();
        com.google.firebase.auth.internal.zzaq.zza(r1, r7, r6, r0);
        goto L_0x0099;
    L_0x0094:
        r0 = zziu;
        r0.zza(r10);
    L_0x0099:
        r10.finish();
        goto L_0x002f;
    L_0x009d:
        if (r3 == 0) goto L_0x00a0;
    L_0x009f:
        return;
    L_0x00a0:
        r0 = r10.zzuk;
        if (r0 != 0) goto L_0x00df;
    L_0x00a4:
        r0 = new android.content.Intent;
        r1 = "com.google.firebase.auth.api.gms.ui.START_WEB_SIGN_IN";
        r0.<init>(r1);
        r1 = "com.google.android.gms";
        r0.setPackage(r1);
        r1 = r10.getIntent();
        r1 = r1.getExtras();
        r0.putExtras(r1);
        r1 = r10.getIntent();
        r1 = r1.getAction();
        r0.putExtra(r5, r1);
        r1 = 40963; // 0xa003 float:5.7401E-41 double:2.02384E-319;
        r10.startActivityForResult(r0, r1);	 Catch:{ ActivityNotFoundException -> 0x00cd }
        goto L_0x00dc;
    L_0x00cd:
        r0 = "Could not launch web sign-in Intent. Google Play service is unavailable";
        android.util.Log.w(r2, r0);
        r1 = new com.google.android.gms.common.api.Status;
        r2 = 17499; // 0x445b float:2.4521E-41 double:8.6457E-320;
        r1.<init>(r2, r0);
        r10.zze(r1);
    L_0x00dc:
        r10.zzuk = r4;
        return;
    L_0x00df:
        r0 = new com.google.firebase.auth.internal.zzaj;
        r0.<init>(r10);
        zzul = r0;
        r0 = handler;
        if (r0 != 0) goto L_0x00f1;
    L_0x00ea:
        r0 = new com.google.android.gms.internal.firebase_auth.zzj;
        r0.<init>();
        handler = r0;
    L_0x00f1:
        r0 = handler;
        r1 = zzul;
        r2 = 800; // 0x320 float:1.121E-42 double:3.953E-321;
        r0.postDelayed(r1, r2);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.internal.FederatedSignInActivity.onResume():void");
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Handler handler = handler;
        if (handler != null) {
            Runnable runnable = zzul;
            if (runnable != null) {
                handler.removeCallbacks(runnable);
                zzul = null;
            }
        }
        setIntent(intent);
    }

    private final void zzfm() {
        zzuj = 0;
        this.zzuk = false;
        Intent intent = new Intent();
        intent.putExtra("com.google.firebase.auth.internal.EXTRA_CANCELED", true);
        intent.setAction("com.google.firebase.auth.ACTION_RECEIVE_FIREBASE_AUTH_INTENT");
        if (LocalBroadcastManager.getInstance(this).sendBroadcast(intent)) {
            zziu.zza(this);
        } else {
            zzaq.zza((Context) this, zzt.zzdc("WEB_CONTEXT_CANCELED"));
        }
        finish();
    }

    private final void zze(Status status) {
        zzuj = 0;
        this.zzuk = false;
        Intent intent = new Intent();
        zzaw.zza(intent, status);
        intent.setAction("com.google.firebase.auth.ACTION_RECEIVE_FIREBASE_AUTH_INTENT");
        if (LocalBroadcastManager.getInstance(this).sendBroadcast(intent)) {
            zziu.zza(this);
        } else {
            zzaq.zza(getApplicationContext(), status);
        }
        finish();
    }
}
