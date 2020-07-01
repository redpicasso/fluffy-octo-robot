package io.invertase.firebase.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.RemoteInput;
import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.ReactApplication;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import io.invertase.firebase.Utils;

public class RNFirebaseBackgroundNotificationActionReceiver extends BroadcastReceiver {
    static boolean isBackgroundNotficationIntent(Intent intent) {
        return intent.getExtras() != null && intent.hasExtra("action") && intent.hasExtra("notification");
    }

    static WritableMap toNotificationOpenMap(Intent intent) {
        Bundle extras = intent.getExtras();
        String str = "notification";
        WritableMap makeNativeMap = Arguments.makeNativeMap(extras.getBundle(str));
        WritableMap createMap = Arguments.createMap();
        String str2 = "action";
        createMap.putString(str2, extras.getString(str2));
        createMap.putMap(str, makeNativeMap);
        str = "results";
        extras = extras.getBundle(str);
        if (extras != null) {
            createMap.putMap(str, Arguments.makeNativeMap(extras));
        }
        return createMap;
    }

    public void onReceive(Context context, Intent intent) {
        if (isBackgroundNotficationIntent(intent)) {
            if (Utils.isAppInForeground(context)) {
                Utils.sendEvent(((ReactApplication) context.getApplicationContext()).getReactNativeHost().getReactInstanceManager().getCurrentReactContext(), "notifications_notification_opened", toNotificationOpenMap(intent));
            } else {
                Intent intent2 = new Intent(context, RNFirebaseBackgroundNotificationActionsService.class);
                intent2.putExtras(intent.getExtras());
                Bundle resultsFromIntent = RemoteInput.getResultsFromIntent(intent);
                if (resultsFromIntent != null) {
                    intent2.putExtra("results", resultsFromIntent);
                }
                if (context.startService(intent2) != null) {
                    HeadlessJsTaskService.acquireWakeLockNow(context);
                }
            }
        }
    }
}
