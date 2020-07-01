package io.invertase.firebase.notifications;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.ViewCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import io.invertase.firebase.Utils;
import io.invertase.firebase.messaging.BundleJSONConverter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

class RNFirebaseNotificationManager {
    private static final String PREFERENCES_KEY = "RNFNotifications";
    static final String SCHEDULED_NOTIFICATION_EVENT = "notifications-scheduled-notification";
    private static final String TAG = "RNFNotificationManager";
    private AlarmManager alarmManager;
    private Context context;
    private NotificationManager notificationManager;
    private SharedPreferences preferences;
    private ReactApplicationContext reactContext;

    RNFirebaseNotificationManager(ReactApplicationContext reactApplicationContext) {
        this(reactApplicationContext.getApplicationContext());
        this.reactContext = reactApplicationContext;
    }

    RNFirebaseNotificationManager(Context context) {
        this.alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService("notification");
        this.preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
    }

    static int getResourceId(Context context, String str, String str2) {
        return context.getResources().getIdentifier(str2, str, context.getPackageName());
    }

    static Uri getSound(Context context, String str) {
        if (str == null) {
            return null;
        }
        if (str.contains("://")) {
            return Uri.parse(str);
        }
        if (str.equalsIgnoreCase("default")) {
            return RingtoneManager.getDefaultUri(2);
        }
        String str2 = "raw";
        int resourceId = getResourceId(context, str2, str);
        if (resourceId == 0) {
            resourceId = getResourceId(context, str2, str.substring(0, str.lastIndexOf(46)));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("android.resource://");
        stringBuilder.append(context.getPackageName());
        stringBuilder.append("/");
        stringBuilder.append(resourceId);
        return Uri.parse(stringBuilder.toString());
    }

    void cancelAllNotifications(Promise promise) {
        try {
            for (String cancelAlarm : this.preferences.getAll().keySet()) {
                cancelAlarm(cancelAlarm);
            }
            this.preferences.edit().clear().apply();
            promise.resolve(null);
        } catch (Throwable e) {
            Log.e(TAG, e.getMessage());
            promise.reject("notification/cancel_notifications_error", "Could not cancel notifications", e);
        }
    }

    void cancelNotification(String str, Promise promise) {
        try {
            cancelAlarm(str);
            this.preferences.edit().remove(str).apply();
            promise.resolve(null);
        } catch (Throwable e) {
            Log.e(TAG, e.getMessage());
            promise.reject("notification/cancel_notification_error", "Could not cancel notifications", e);
        }
    }

    void createChannel(ReadableMap readableMap) {
        if (VERSION.SDK_INT >= 26) {
            this.notificationManager.createNotificationChannel(parseChannelMap(readableMap));
        }
    }

    void createChannelGroup(ReadableMap readableMap) {
        if (VERSION.SDK_INT >= 26) {
            this.notificationManager.createNotificationChannelGroup(parseChannelGroupMap(readableMap));
        }
    }

    void createChannelGroups(ReadableArray readableArray) {
        if (VERSION.SDK_INT >= 26) {
            List arrayList = new ArrayList();
            for (int i = 0; i < readableArray.size(); i++) {
                arrayList.add(parseChannelGroupMap(readableArray.getMap(i)));
            }
            this.notificationManager.createNotificationChannelGroups(arrayList);
        }
    }

    void createChannels(ReadableArray readableArray) {
        if (VERSION.SDK_INT >= 26) {
            List arrayList = new ArrayList();
            for (int i = 0; i < readableArray.size(); i++) {
                arrayList.add(parseChannelMap(readableArray.getMap(i)));
            }
            this.notificationManager.createNotificationChannels(arrayList);
        }
    }

    void deleteChannelGroup(String str) {
        if (VERSION.SDK_INT >= 26) {
            this.notificationManager.deleteNotificationChannelGroup(str);
        }
    }

    void deleteChannel(String str) {
        if (VERSION.SDK_INT >= 26) {
            this.notificationManager.deleteNotificationChannel(str);
        }
    }

    void displayNotification(ReadableMap readableMap, Promise promise) {
        displayNotification(Arguments.toBundle(readableMap), promise);
    }

    void displayScheduledNotification(Bundle bundle) {
        String str = "schedule";
        String str2 = "repeated";
        if (!(bundle.getBundle(str).containsKey(str2) && bundle.getBundle(str).getBoolean(str2))) {
            this.preferences.edit().remove(bundle.getString("notificationId")).apply();
        }
        if (Utils.isAppInForeground(this.context)) {
            Intent intent = new Intent(SCHEDULED_NOTIFICATION_EVENT);
            intent.putExtra("notification", bundle);
            LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);
            return;
        }
        displayNotification(bundle, null);
    }

    WritableMap getChannel(String str) {
        return VERSION.SDK_INT >= 26 ? createChannelMap(this.notificationManager.getNotificationChannel(str)) : null;
    }

    WritableArray getChannels() {
        return VERSION.SDK_INT >= 26 ? createChannelsArray(this.notificationManager.getNotificationChannels()) : null;
    }

    WritableMap getChannelGroup(String str) {
        return VERSION.SDK_INT >= 28 ? createChannelGroupMap(this.notificationManager.getNotificationChannelGroup(str)) : null;
    }

    WritableArray getChannelGroups() {
        return VERSION.SDK_INT >= 26 ? createChannelGroupsArray(this.notificationManager.getNotificationChannelGroups()) : null;
    }

    ArrayList<Bundle> getScheduledNotifications() {
        ArrayList<Bundle> arrayList = new ArrayList();
        Map all = this.preferences.getAll();
        for (String str : all.keySet()) {
            try {
                arrayList.add(BundleJSONConverter.convertToBundle(new JSONObject((String) all.get(str))));
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return arrayList;
    }

    void removeAllDeliveredNotifications(Promise promise) {
        this.notificationManager.cancelAll();
        promise.resolve(null);
    }

    void removeDeliveredNotification(String str, Promise promise) {
        this.notificationManager.cancel(str.hashCode());
        promise.resolve(null);
    }

    void removeDeliveredNotificationsByTag(String str, Promise promise) {
        if (VERSION.SDK_INT >= 23) {
            for (StatusBarNotification statusBarNotification : this.notificationManager.getActiveNotifications()) {
                if (str.equals(statusBarNotification.getTag())) {
                    this.notificationManager.cancel(statusBarNotification.getTag(), statusBarNotification.getId());
                }
            }
        }
        promise.resolve(null);
    }

    void rescheduleNotifications() {
        Iterator it = getScheduledNotifications().iterator();
        while (it.hasNext()) {
            scheduleNotification((Bundle) it.next(), null);
        }
    }

    void scheduleNotification(ReadableMap readableMap, Promise promise) {
        scheduleNotification(Arguments.toBundle(readableMap), promise);
    }

    private void cancelAlarm(String str) {
        this.alarmManager.cancel(PendingIntent.getBroadcast(this.context, str.hashCode(), new Intent(this.context, RNFirebaseNotificationReceiver.class), 134217728));
    }

    private void displayNotification(Bundle bundle, Promise promise) {
        new DisplayNotificationTask(this.context, this.reactContext, this.notificationManager, bundle, promise).execute(new Void[0]);
    }

    private NotificationChannelGroup parseChannelGroupMap(ReadableMap readableMap) {
        if (VERSION.SDK_INT < 26) {
            return null;
        }
        NotificationChannelGroup notificationChannelGroup = new NotificationChannelGroup(readableMap.getString("groupId"), readableMap.getString(ConditionalUserProperty.NAME));
        if (VERSION.SDK_INT >= 28) {
            String str = "description";
            if (readableMap.hasKey(str)) {
                notificationChannelGroup.setDescription(readableMap.getString(str));
            }
        }
        return notificationChannelGroup;
    }

    private String getFileName(Uri uri) {
        String str = null;
        if (uri.getScheme() == "content") {
            Cursor query = this.reactContext.getContentResolver().query(uri, null, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        str = query.getString(query.getColumnIndexOrThrow("_display_name"));
                    }
                } catch (Throwable th) {
                    if (query != null) {
                        query.close();
                    }
                }
            }
            if (query != null) {
                query.close();
            }
        }
        String str2 = "default";
        if (str == null) {
            str = uri.getPath();
            if (str != null) {
                int lastIndexOf = str.lastIndexOf(47);
                str = lastIndexOf != -1 ? str.substring(lastIndexOf + 1) : str2;
            }
        }
        return str.equals("notification_sound") ? str2 : str;
    }

    @RequiresApi(api = 26)
    private WritableArray createChannelsArray(List<NotificationChannel> list) {
        WritableArray createArray = Arguments.createArray();
        if (VERSION.SDK_INT >= 26) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                createArray.pushMap(createChannelMap((NotificationChannel) list.get(i)));
            }
        }
        return createArray;
    }

    @RequiresApi(api = 26)
    private WritableArray createChannelGroupsArray(List<NotificationChannelGroup> list) {
        WritableArray createArray = Arguments.createArray();
        if (VERSION.SDK_INT >= 26) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                createArray.pushMap(createChannelGroupMap((NotificationChannelGroup) list.get(i)));
            }
        }
        return createArray;
    }

    @RequiresApi(api = 26)
    private WritableMap createChannelGroupMap(NotificationChannelGroup notificationChannelGroup) {
        WritableMap createMap = Arguments.createMap();
        if (VERSION.SDK_INT >= 26) {
            createMap.putString("groupId", notificationChannelGroup.getId());
            createMap.putString(ConditionalUserProperty.NAME, notificationChannelGroup.getName().toString());
            createMap.putArray("channels", createChannelsArray(notificationChannelGroup.getChannels()));
            if (VERSION.SDK_INT >= 28) {
                createMap.putString("description", notificationChannelGroup.getDescription());
            }
        }
        return createMap;
    }

    @RequiresApi(api = 26)
    private WritableMap createChannelMap(NotificationChannel notificationChannel) {
        if (notificationChannel == null) {
            return null;
        }
        WritableMap createMap = Arguments.createMap();
        if (VERSION.SDK_INT >= 26) {
            createMap.putString("channelId", notificationChannel.getId());
            createMap.putString(ConditionalUserProperty.NAME, notificationChannel.getName().toString());
            createMap.putInt("importance", notificationChannel.getImportance());
            createMap.putString("description", notificationChannel.getDescription());
            createMap.putBoolean("bypassDnd", notificationChannel.canBypassDnd());
            createMap.putString("group", notificationChannel.getGroup());
            Object[] objArr = new Object[1];
            int i = 0;
            objArr[0] = Integer.valueOf(ViewCompat.MEASURED_SIZE_MASK & notificationChannel.getLightColor());
            createMap.putString("lightColor", String.format("#%06X", objArr));
            createMap.putBoolean("lightsEnabled", notificationChannel.shouldShowLights());
            int lockscreenVisibility = notificationChannel.getLockscreenVisibility();
            String str = "lockScreenVisibility";
            if (lockscreenVisibility == NotificationManagerCompat.IMPORTANCE_UNSPECIFIED) {
                createMap.putNull(str);
            } else {
                createMap.putInt(str, lockscreenVisibility);
            }
            createMap.putBoolean("showBadge", notificationChannel.canShowBadge());
            createMap.putString("sound", getFileName(notificationChannel.getSound()));
            createMap.putBoolean("vibrationEnabled", notificationChannel.shouldVibrate());
            long[] vibrationPattern = notificationChannel.getVibrationPattern();
            WritableArray createArray = Arguments.createArray();
            if (vibrationPattern != null) {
                int length = vibrationPattern.length;
                while (i < length) {
                    createArray.pushDouble((double) vibrationPattern[i]);
                    i++;
                }
            }
            createMap.putArray("vibrationPattern", createArray);
        }
        return createMap;
    }

    @RequiresApi(api = 26)
    private NotificationChannel parseChannelMap(ReadableMap readableMap) {
        if (VERSION.SDK_INT < 26) {
            return null;
        }
        NotificationChannel notificationChannel = new NotificationChannel(readableMap.getString("channelId"), readableMap.getString(ConditionalUserProperty.NAME), readableMap.getInt("importance"));
        String str = "bypassDnd";
        if (readableMap.hasKey(str)) {
            notificationChannel.setBypassDnd(readableMap.getBoolean(str));
        }
        str = "description";
        if (readableMap.hasKey(str)) {
            notificationChannel.setDescription(readableMap.getString(str));
        }
        str = "group";
        if (readableMap.hasKey(str)) {
            notificationChannel.setGroup(readableMap.getString(str));
        }
        str = "lightColor";
        if (readableMap.hasKey(str)) {
            notificationChannel.setLightColor(Color.parseColor(readableMap.getString(str)));
        }
        str = "lightsEnabled";
        if (readableMap.hasKey(str)) {
            notificationChannel.enableLights(readableMap.getBoolean(str));
        }
        str = "lockScreenVisibility";
        if (readableMap.hasKey(str)) {
            notificationChannel.setLockscreenVisibility(readableMap.getInt(str));
        }
        str = "showBadge";
        if (readableMap.hasKey(str)) {
            notificationChannel.setShowBadge(readableMap.getBoolean(str));
        }
        str = "sound";
        if (readableMap.hasKey(str)) {
            notificationChannel.setSound(getSound(this.context, readableMap.getString(str)), null);
        }
        str = "vibrationEnabled";
        if (readableMap.hasKey(str)) {
            notificationChannel.enableVibration(readableMap.getBoolean(str));
        }
        str = "vibrationPattern";
        if (readableMap.hasKey(str)) {
            ReadableArray array = readableMap.getArray(str);
            long[] jArr = new long[array.size()];
            for (int i = 0; i < array.size(); i++) {
                jArr[i] = (long) array.getDouble(i);
            }
            notificationChannel.setVibrationPattern(jArr);
        }
        return notificationChannel;
    }

    @SuppressLint({"ShortAlarm"})
    private void scheduleNotification(Bundle bundle, @Nullable Promise promise) {
        Bundle bundle2 = bundle;
        Promise promise2 = promise;
        String str = "notificationId";
        boolean containsKey = bundle2.containsKey(str);
        String str2 = "notification/schedule_notification_error";
        String str3 = TAG;
        if (containsKey) {
            String str4 = "schedule";
            String str5 = "Missing schedule information";
            if (bundle2.containsKey(str4)) {
                str = bundle2.getString(str);
                Bundle bundle3 = bundle2.getBundle(str4);
                Long valueOf = Long.valueOf(-1);
                Object obj = bundle3.get("fireDate");
                if (obj instanceof Long) {
                    valueOf = (Long) obj;
                } else if (obj instanceof Double) {
                    valueOf = Long.valueOf(((Double) obj).longValue());
                }
                if (valueOf.longValue() == -1) {
                    if (promise2 == null) {
                        Log.e(str3, str5);
                    } else {
                        promise2.reject(str2, "Missing fireDate information");
                    }
                    return;
                }
                try {
                    this.preferences.edit().putString(str, BundleJSONConverter.convertToJSON(bundle).toString()).apply();
                    Intent intent = new Intent(this.context, RNFirebaseNotificationReceiver.class);
                    intent.putExtras(bundle2);
                    PendingIntent broadcast = PendingIntent.getBroadcast(this.context, str.hashCode(), intent, 134217728);
                    str = "repeatInterval";
                    if (bundle3.containsKey(str)) {
                        if (valueOf.longValue() < System.currentTimeMillis()) {
                            Log.w(str3, "Scheduled notification date is in the past, will adjust it to be in future");
                            Calendar instance = Calendar.getInstance();
                            Calendar instance2 = Calendar.getInstance();
                            instance2.setTimeInMillis(valueOf.longValue());
                            instance.set(13, instance2.get(13));
                            String string = bundle3.getString(str);
                            Object obj2 = -1;
                            switch (string.hashCode()) {
                                case -1074026988:
                                    if (string.equals("minute")) {
                                        obj2 = null;
                                        break;
                                    }
                                    break;
                                case 99228:
                                    if (string.equals("day")) {
                                        obj2 = 2;
                                        break;
                                    }
                                    break;
                                case 3208676:
                                    if (string.equals("hour")) {
                                        obj2 = 1;
                                        break;
                                    }
                                    break;
                                case 3645428:
                                    if (string.equals("week")) {
                                        obj2 = 3;
                                        break;
                                    }
                                    break;
                            }
                            if (obj2 == null) {
                                instance.add(12, 1);
                            } else if (obj2 == 1) {
                                instance.set(12, instance2.get(12));
                                instance.add(10, 1);
                            } else if (obj2 == 2) {
                                instance.set(12, instance2.get(12));
                                instance.set(11, instance2.get(11));
                                instance.add(5, 1);
                            } else if (obj2 == 3) {
                                instance.set(12, instance2.get(12));
                                instance.set(11, instance2.get(11));
                                instance.set(5, instance2.get(5));
                                instance.add(5, 7);
                            }
                            valueOf = Long.valueOf(instance.getTimeInMillis());
                        }
                        Long l = null;
                        str = bundle3.getString(str);
                        Object obj3 = -1;
                        switch (str.hashCode()) {
                            case -1074026988:
                                if (str.equals("minute")) {
                                    obj3 = null;
                                    break;
                                }
                                break;
                            case 99228:
                                if (str.equals("day")) {
                                    obj3 = 2;
                                    break;
                                }
                                break;
                            case 3208676:
                                if (str.equals("hour")) {
                                    obj3 = 1;
                                    break;
                                }
                                break;
                            case 3645428:
                                if (str.equals("week")) {
                                    obj3 = 3;
                                    break;
                                }
                                break;
                        }
                        if (obj3 == null) {
                            l = Long.valueOf(60000);
                        } else if (obj3 == 1) {
                            l = Long.valueOf(3600000);
                        } else if (obj3 == 2) {
                            l = Long.valueOf(86400000);
                        } else if (obj3 != 3) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Invalid interval: ");
                            stringBuilder.append(bundle3.getString("interval"));
                            Log.e(str3, stringBuilder.toString());
                        } else {
                            l = Long.valueOf(604800000);
                        }
                        if (l == null) {
                            if (promise2 == null) {
                                Log.e(str3, "Invalid interval");
                            } else {
                                promise2.reject(str2, "Invalid interval");
                            }
                            return;
                        }
                        this.alarmManager.setRepeating(0, valueOf.longValue(), l.longValue(), broadcast);
                    } else if (bundle3.containsKey("exact") && bundle3.getBoolean("exact") && VERSION.SDK_INT >= 19) {
                        this.alarmManager.setExact(0, valueOf.longValue(), broadcast);
                    } else {
                        this.alarmManager.set(0, valueOf.longValue(), broadcast);
                    }
                    if (promise2 != null) {
                        promise2.resolve(null);
                    }
                    return;
                } catch (Throwable e) {
                    if (promise2 == null) {
                        Log.e(str3, "Failed to store notification");
                    } else {
                        promise2.reject(str2, "Failed to store notification", e);
                    }
                    return;
                }
            }
            if (promise2 == null) {
                Log.e(str3, str5);
            } else {
                promise2.reject(str2, str5);
            }
            return;
        }
        String str6 = "Missing notificationId";
        if (promise2 == null) {
            Log.e(str3, str6);
        } else {
            promise2.reject(str2, str6);
        }
    }
}
