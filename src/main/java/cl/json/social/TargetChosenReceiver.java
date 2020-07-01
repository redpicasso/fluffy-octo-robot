package cl.json.social;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Build.VERSION;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;

public class TargetChosenReceiver extends BroadcastReceiver {
    private static final String EXTRA_RECEIVER_TOKEN = "receiver_token";
    private static final Object LOCK = new Object();
    private static Callback failureCallback;
    private static TargetChosenReceiver sLastRegisteredReceiver;
    private static String sTargetChosenReceiveAction;
    private static Callback successCallback;

    public static boolean isSupported() {
        return VERSION.SDK_INT >= 22;
    }

    public static void registerCallbacks(Callback callback, Callback callback2) {
        successCallback = callback;
        failureCallback = callback2;
    }

    @TargetApi(22)
    public static IntentSender getSharingSenderIntent(ReactContext reactContext) {
        synchronized (LOCK) {
            if (sTargetChosenReceiveAction == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(reactContext.getPackageName());
                stringBuilder.append("/");
                stringBuilder.append(TargetChosenReceiver.class.getName());
                stringBuilder.append("_ACTION");
                sTargetChosenReceiveAction = stringBuilder.toString();
            }
            Context applicationContext = reactContext.getApplicationContext();
            if (sLastRegisteredReceiver != null) {
                applicationContext.unregisterReceiver(sLastRegisteredReceiver);
            }
            sLastRegisteredReceiver = new TargetChosenReceiver();
            applicationContext.registerReceiver(sLastRegisteredReceiver, new IntentFilter(sTargetChosenReceiveAction));
        }
        Intent intent = new Intent(sTargetChosenReceiveAction);
        intent.setPackage(reactContext.getPackageName());
        intent.putExtra(EXTRA_RECEIVER_TOKEN, sLastRegisteredReceiver.hashCode());
        return PendingIntent.getBroadcast(reactContext, 0, intent, 1342177280).getIntentSender();
    }

    /* JADX WARNING: Missing block: B:10:0x001c, code:
            if (r5.hasExtra(EXTRA_RECEIVER_TOKEN) == false) goto L_0x0059;
     */
    /* JADX WARNING: Missing block: B:12:0x0029, code:
            if (r5.getIntExtra(EXTRA_RECEIVER_TOKEN, 0) == hashCode()) goto L_0x002c;
     */
    /* JADX WARNING: Missing block: B:14:0x0036, code:
            if (((android.content.ComponentName) r5.getParcelableExtra("android.intent.extra.CHOSEN_COMPONENT")) == null) goto L_0x004a;
     */
    /* JADX WARNING: Missing block: B:15:0x0038, code:
            sendCallback(true, java.lang.Boolean.valueOf(true), ((android.content.ComponentName) r5.getParcelableExtra("android.intent.extra.CHOSEN_COMPONENT")).flattenToString());
     */
    /* JADX WARNING: Missing block: B:16:0x004a, code:
            sendCallback(true, java.lang.Boolean.valueOf(true), "OK");
     */
    /* JADX WARNING: Missing block: B:17:0x0059, code:
            return;
     */
    public void onReceive(android.content.Context r4, android.content.Intent r5) {
        /*
        r3 = this;
        r0 = LOCK;
        monitor-enter(r0);
        r1 = sLastRegisteredReceiver;	 Catch:{ all -> 0x005a }
        if (r1 == r3) goto L_0x0009;
    L_0x0007:
        monitor-exit(r0);	 Catch:{ all -> 0x005a }
        return;
    L_0x0009:
        r4 = r4.getApplicationContext();	 Catch:{ all -> 0x005a }
        r1 = sLastRegisteredReceiver;	 Catch:{ all -> 0x005a }
        r4.unregisterReceiver(r1);	 Catch:{ all -> 0x005a }
        r4 = 0;
        sLastRegisteredReceiver = r4;	 Catch:{ all -> 0x005a }
        monitor-exit(r0);	 Catch:{ all -> 0x005a }
        r4 = "receiver_token";
        r4 = r5.hasExtra(r4);
        if (r4 == 0) goto L_0x0059;
    L_0x001e:
        r4 = 0;
        r0 = "receiver_token";
        r0 = r5.getIntExtra(r0, r4);
        r1 = r3.hashCode();
        if (r0 == r1) goto L_0x002c;
    L_0x002b:
        goto L_0x0059;
    L_0x002c:
        r0 = "android.intent.extra.CHOSEN_COMPONENT";
        r5 = r5.getParcelableExtra(r0);
        r5 = (android.content.ComponentName) r5;
        r0 = 2;
        r1 = 1;
        if (r5 == 0) goto L_0x004a;
    L_0x0038:
        r0 = new java.lang.Object[r0];
        r2 = java.lang.Boolean.valueOf(r1);
        r0[r4] = r2;
        r4 = r5.flattenToString();
        r0[r1] = r4;
        sendCallback(r1, r0);
        goto L_0x0059;
    L_0x004a:
        r5 = new java.lang.Object[r0];
        r0 = java.lang.Boolean.valueOf(r1);
        r5[r4] = r0;
        r4 = "OK";
        r5[r1] = r4;
        sendCallback(r1, r5);
    L_0x0059:
        return;
    L_0x005a:
        r4 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x005a }
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: cl.json.social.TargetChosenReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }

    public static void sendCallback(boolean z, Object... objArr) {
        Callback callback;
        if (z) {
            callback = successCallback;
            if (callback != null) {
                callback.invoke(objArr);
            }
        } else {
            callback = failureCallback;
            if (callback != null) {
                callback.invoke(objArr);
            }
        }
        successCallback = null;
        failureCallback = null;
    }
}
