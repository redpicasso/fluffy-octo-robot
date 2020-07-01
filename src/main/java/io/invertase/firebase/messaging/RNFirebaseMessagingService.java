package io.invertase.firebase.messaging;

import android.content.Intent;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.react.HeadlessJsTaskService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import io.invertase.firebase.Utils;

public class RNFirebaseMessagingService extends FirebaseMessagingService {
    public static final String MESSAGE_EVENT = "messaging-message";
    public static final String NEW_TOKEN_EVENT = "messaging-token-refresh";
    public static final String REMOTE_NOTIFICATION_EVENT = "notifications-remote-notification";
    private static final String TAG = "RNFMessagingService";

    public void onNewToken(String str) {
        Log.d(TAG, "onNewToken event received");
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(NEW_TOKEN_EVENT));
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        String str = TAG;
        Log.d(str, "onMessageReceived event received");
        Intent intent;
        if (remoteMessage.getNotification() != null) {
            intent = new Intent(REMOTE_NOTIFICATION_EVENT);
            intent.putExtra("notification", remoteMessage);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            return;
        }
        String str2 = "message";
        if (Utils.isAppInForeground(getApplicationContext())) {
            intent = new Intent(MESSAGE_EVENT);
            intent.putExtra(str2, remoteMessage);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            return;
        }
        try {
            Intent intent2 = new Intent(getApplicationContext(), RNFirebaseBackgroundMessagingService.class);
            intent2.putExtra(str2, remoteMessage);
            if (getApplicationContext().startService(intent2) != null) {
                HeadlessJsTaskService.acquireWakeLockNow(getApplicationContext());
            }
        } catch (Throwable e) {
            Log.e(str, "Background messages will only work if the message priority is set to 'high'", e);
        }
    }
}
