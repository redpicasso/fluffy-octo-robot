package io.invertase.firebase.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RNFirebaseNotificationsRebootReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Log.i("RNFNotifRebootReceiver", "Received reboot event");
        new RNFirebaseNotificationManager(context).rescheduleNotifications();
    }
}
