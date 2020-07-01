package io.invertase.firebase.notifications;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.core.app.RemoteInput;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ViewProps;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.RemoteMessage.Notification;
import io.invertase.firebase.Utils;
import io.invertase.firebase.messaging.RNFirebaseMessagingService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import me.leolin.shortcutbadger.ShortcutBadger;

public class RNFirebaseNotifications extends ReactContextBaseJavaModule implements ActivityEventListener {
    private static final String BADGE_FILE = "BadgeCountFile";
    private static final String BADGE_KEY = "BadgeCount";
    private static final String TAG = "RNFirebaseNotifications";
    private RNFirebaseNotificationManager notificationManager;
    private SharedPreferences sharedPreferences = null;

    private class RemoteNotificationReceiver extends BroadcastReceiver {
        private RemoteNotificationReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (RNFirebaseNotifications.this.access$700().hasActiveCatalystInstance()) {
                Log.d(RNFirebaseNotifications.TAG, "Received new remote notification");
                Utils.sendEvent(RNFirebaseNotifications.this.access$700(), "notifications_notification_received", RNFirebaseNotifications.this.parseRemoteMessage((RemoteMessage) intent.getParcelableExtra("notification")));
            }
        }
    }

    private class ScheduledNotificationReceiver extends BroadcastReceiver {
        private ScheduledNotificationReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (RNFirebaseNotifications.this.access$700().hasActiveCatalystInstance()) {
                Log.d(RNFirebaseNotifications.TAG, "Received new scheduled notification");
                Utils.sendEvent(RNFirebaseNotifications.this.access$700(), "notifications_notification_received", RNFirebaseNotifications.this.parseNotificationBundle(intent.getBundleExtra("notification")));
            }
        }
    }

    public String getName() {
        return TAG;
    }

    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
    }

    RNFirebaseNotifications(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        reactApplicationContext.addActivityEventListener(this);
        this.notificationManager = new RNFirebaseNotificationManager(reactApplicationContext);
        this.sharedPreferences = reactApplicationContext.getSharedPreferences(BADGE_FILE, 0);
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(reactApplicationContext);
        instance.registerReceiver(new RemoteNotificationReceiver(), new IntentFilter(RNFirebaseMessagingService.REMOTE_NOTIFICATION_EVENT));
        instance.registerReceiver(new ScheduledNotificationReceiver(), new IntentFilter("notifications-scheduled-notification"));
    }

    @ReactMethod
    public void cancelAllNotifications(Promise promise) {
        this.notificationManager.cancelAllNotifications(promise);
    }

    @ReactMethod
    public void cancelNotification(String str, Promise promise) {
        this.notificationManager.cancelNotification(str, promise);
    }

    @ReactMethod
    public void displayNotification(ReadableMap readableMap, Promise promise) {
        this.notificationManager.displayNotification(readableMap, promise);
    }

    @ReactMethod
    public void getBadge(Promise promise) {
        int i = this.sharedPreferences.getInt(BADGE_KEY, 0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Got badge count: ");
        stringBuilder.append(i);
        Log.d(TAG, stringBuilder.toString());
        promise.resolve(Integer.valueOf(i));
    }

    @ReactMethod
    public void getInitialNotification(Promise promise) {
        promise.resolve(access$700() != null ? parseIntentForNotification(access$700().getIntent()) : null);
    }

    @ReactMethod
    public void getScheduledNotifications(Promise promise) {
        ArrayList scheduledNotifications = this.notificationManager.getScheduledNotifications();
        WritableArray createArray = Arguments.createArray();
        Iterator it = scheduledNotifications.iterator();
        while (it.hasNext()) {
            createArray.pushMap(parseNotificationBundle((Bundle) it.next()));
        }
        promise.resolve(createArray);
    }

    @ReactMethod
    public void removeAllDeliveredNotifications(Promise promise) {
        this.notificationManager.removeAllDeliveredNotifications(promise);
    }

    @ReactMethod
    public void removeDeliveredNotification(String str, Promise promise) {
        this.notificationManager.removeDeliveredNotification(str, promise);
    }

    @ReactMethod
    public void removeDeliveredNotificationsByTag(String str, Promise promise) {
        this.notificationManager.removeDeliveredNotificationsByTag(str, promise);
    }

    @ReactMethod
    public void setBadge(int i, Promise promise) {
        this.sharedPreferences.edit().putInt(BADGE_KEY, i).apply();
        String str = TAG;
        if (i == 0) {
            Log.d(str, "Remove badge count");
            ShortcutBadger.removeCount(access$700());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Apply badge count: ");
            stringBuilder.append(i);
            Log.d(str, stringBuilder.toString());
            ShortcutBadger.applyCount(access$700(), i);
        }
        promise.resolve(null);
    }

    @ReactMethod
    public void scheduleNotification(ReadableMap readableMap, Promise promise) {
        this.notificationManager.scheduleNotification(readableMap, promise);
    }

    @ReactMethod
    public void createChannel(ReadableMap readableMap, Promise promise) {
        try {
            this.notificationManager.createChannel(readableMap);
        } catch (Throwable unused) {
            promise.resolve(null);
        }
    }

    @ReactMethod
    public void createChannelGroup(ReadableMap readableMap, Promise promise) {
        try {
            this.notificationManager.createChannelGroup(readableMap);
        } catch (Throwable unused) {
            promise.resolve(null);
        }
    }

    @ReactMethod
    public void createChannelGroups(ReadableArray readableArray, Promise promise) {
        try {
            this.notificationManager.createChannelGroups(readableArray);
        } catch (Throwable unused) {
            promise.resolve(null);
        }
    }

    @ReactMethod
    public void createChannels(ReadableArray readableArray, Promise promise) {
        try {
            this.notificationManager.createChannels(readableArray);
        } catch (Throwable unused) {
            promise.resolve(null);
        }
    }

    @ReactMethod
    public void deleteChannelGroup(String str, Promise promise) {
        try {
            this.notificationManager.deleteChannelGroup(str);
            promise.resolve(null);
        } catch (NullPointerException unused) {
            promise.reject("notifications/channel-group-not-found", "The requested NotificationChannelGroup does not exist, have you created it?");
        }
    }

    @ReactMethod
    public void deleteChannel(String str, Promise promise) {
        try {
            this.notificationManager.deleteChannel(str);
        } catch (Throwable unused) {
            promise.resolve(null);
        }
    }

    @ReactMethod
    public void getChannel(String str, Promise promise) {
        try {
            promise.resolve(this.notificationManager.getChannel(str));
        } catch (Throwable unused) {
            promise.resolve(null);
        }
    }

    @ReactMethod
    public void getChannels(Promise promise) {
        try {
            promise.resolve(this.notificationManager.getChannels());
        } catch (Throwable unused) {
            promise.resolve(Collections.emptyList());
        }
    }

    @ReactMethod
    public void getChannelGroup(String str, Promise promise) {
        try {
            promise.resolve(this.notificationManager.getChannelGroup(str));
        } catch (Throwable unused) {
            promise.resolve(null);
        }
    }

    @ReactMethod
    public void getChannelGroups(Promise promise) {
        try {
            promise.resolve(this.notificationManager.getChannelGroups());
        } catch (Throwable unused) {
            promise.resolve(Collections.emptyList());
        }
    }

    public void onNewIntent(Intent intent) {
        WritableMap parseIntentForNotification = parseIntentForNotification(intent);
        if (parseIntentForNotification != null) {
            Utils.sendEvent(access$700(), "notifications_notification_opened", parseIntentForNotification);
        }
    }

    private WritableMap parseIntentForNotification(Intent intent) {
        WritableMap parseIntentForRemoteNotification = parseIntentForRemoteNotification(intent);
        return parseIntentForRemoteNotification == null ? parseIntentForLocalNotification(intent) : parseIntentForRemoteNotification;
    }

    private WritableMap parseIntentForLocalNotification(Intent intent) {
        if (intent.getExtras() == null || !intent.hasExtra("notificationId")) {
            return null;
        }
        WritableMap makeNativeMap = Arguments.makeNativeMap(intent.getExtras());
        WritableMap createMap = Arguments.createMap();
        createMap.putString("action", intent.getAction());
        createMap.putMap("notification", makeNativeMap);
        Bundle resultsFromIntent = RemoteInput.getResultsFromIntent(intent);
        if (resultsFromIntent != null) {
            createMap.putMap("results", Arguments.makeNativeMap(resultsFromIntent));
        }
        return createMap;
    }

    private WritableMap parseIntentForRemoteNotification(Intent intent) {
        if (intent.getExtras() != null) {
            String str = "google.message_id";
            if (intent.hasExtra(str)) {
                Bundle extras = intent.getExtras();
                WritableMap createMap = Arguments.createMap();
                WritableMap createMap2 = Arguments.createMap();
                for (String str2 : extras.keySet()) {
                    if (str2.equals(str)) {
                        createMap.putString("notificationId", extras.getString(str2));
                    } else if (!(str2.equals("collapse_key") || str2.equals("from") || str2.equals("google.sent_time") || str2.equals("google.ttl"))) {
                        if (!str2.equals("_fbSourceApplicationHasBeenSet")) {
                            createMap2.putString(str2, extras.getString(str2));
                        }
                    }
                }
                createMap.putMap("data", createMap2);
                WritableMap createMap3 = Arguments.createMap();
                createMap3.putString("action", intent.getAction());
                createMap3.putMap("notification", createMap);
                return createMap3;
            }
        }
        return null;
    }

    private WritableMap parseNotificationBundle(Bundle bundle) {
        return Arguments.makeNativeMap(bundle);
    }

    private WritableMap parseRemoteMessage(RemoteMessage remoteMessage) {
        Notification notification = remoteMessage.getNotification();
        WritableMap createMap = Arguments.createMap();
        WritableMap createMap2 = Arguments.createMap();
        String notificationBody = getNotificationBody(notification);
        if (notificationBody != null) {
            createMap.putString("body", notificationBody);
        }
        if (remoteMessage.getData() != null) {
            for (Entry entry : remoteMessage.getData().entrySet()) {
                createMap2.putString((String) entry.getKey(), (String) entry.getValue());
            }
        }
        createMap.putMap("data", createMap2);
        if (remoteMessage.getMessageId() != null) {
            createMap.putString("notificationId", remoteMessage.getMessageId());
        }
        if (notification.getSound() != null) {
            createMap.putString("sound", notification.getSound());
        }
        String notificationTitle = getNotificationTitle(notification);
        if (notificationTitle != null) {
            createMap.putString("title", notificationTitle);
        }
        WritableMap createMap3 = Arguments.createMap();
        if (notification.getClickAction() != null) {
            createMap3.putString("clickAction", notification.getClickAction());
        }
        if (notification.getColor() != null) {
            createMap3.putString(ViewProps.COLOR, notification.getColor());
        }
        if (notification.getIcon() != null) {
            createMap2 = Arguments.createMap();
            createMap2.putString("icon", notification.getIcon());
            createMap3.putMap("smallIcon", createMap2);
        }
        if (notification.getTag() != null) {
            createMap3.putString("group", notification.getTag());
            createMap3.putString("tag", notification.getTag());
        }
        createMap.putMap("android", createMap3);
        return createMap;
    }

    @Nullable
    private String getNotificationBody(Notification notification) {
        String body = notification.getBody();
        String bodyLocalizationKey = notification.getBodyLocalizationKey();
        if (bodyLocalizationKey == null) {
            return body;
        }
        String[] bodyLocalizationArgs = notification.getBodyLocalizationArgs();
        Context reactApplicationContext = access$700();
        return reactApplicationContext.getResources().getString(Utils.getResId(reactApplicationContext, bodyLocalizationKey), (Object[]) bodyLocalizationArgs);
    }

    @Nullable
    private String getNotificationTitle(Notification notification) {
        String title = notification.getTitle();
        String titleLocalizationKey = notification.getTitleLocalizationKey();
        if (titleLocalizationKey == null) {
            return title;
        }
        String[] titleLocalizationArgs = notification.getTitleLocalizationArgs();
        Context reactApplicationContext = access$700();
        return reactApplicationContext.getResources().getString(Utils.getResId(reactApplicationContext, titleLocalizationKey), (Object[]) titleLocalizationArgs);
    }
}
