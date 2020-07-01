package com.google.android.gms.iid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

@Deprecated
public class InstanceIDListenerService extends zze {
    public void onTokenRefresh() {
    }

    public void handleIntent(Intent intent) {
        if ("com.google.android.gms.iid.InstanceID".equals(intent.getAction())) {
            Bundle bundle = null;
            String str = "subtype";
            String stringExtra = intent.getStringExtra(str);
            if (stringExtra != null) {
                bundle = new Bundle();
                bundle.putString(str, stringExtra);
            }
            InstanceID instance = InstanceID.getInstance(this, bundle);
            String stringExtra2 = intent.getStringExtra("CMD");
            String str2 = "InstanceID";
            if (Log.isLoggable(str2, 3)) {
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(stringExtra).length() + 34) + String.valueOf(stringExtra2).length());
                stringBuilder.append("Service command. subtype:");
                stringBuilder.append(stringExtra);
                stringBuilder.append(" command:");
                stringBuilder.append(stringExtra2);
                Log.d(str2, stringBuilder.toString());
            }
            if ("RST".equals(stringExtra2)) {
                instance.zzo();
                onTokenRefresh();
                return;
            }
            if ("RST_FULL".equals(stringExtra2)) {
                if (!InstanceID.zzp().isEmpty()) {
                    InstanceID.zzp().zzz();
                    onTokenRefresh();
                }
            } else if ("SYNC".equals(stringExtra2)) {
                zzak zzp = InstanceID.zzp();
                String valueOf = String.valueOf(stringExtra);
                str = "|T|";
                zzp.zzi(str.length() != 0 ? valueOf.concat(str) : new String(valueOf));
                valueOf = String.valueOf(stringExtra);
                str = "|T-timestamp|";
                zzp.zzi(str.length() != 0 ? valueOf.concat(str) : new String(valueOf));
                onTokenRefresh();
            }
        }
    }

    static void zzd(Context context, zzak zzak) {
        zzak.zzz();
        Intent intent = new Intent("com.google.android.gms.iid.InstanceID");
        intent.putExtra("CMD", "RST");
        intent.setClassName(context, "com.google.android.gms.gcm.GcmReceiver");
        context.sendBroadcast(intent);
    }
}
