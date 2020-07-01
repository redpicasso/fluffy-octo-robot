package io.invertase.firebase.messaging;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.RemoteMessage.Builder;
import io.invertase.firebase.Utils;
import javax.annotation.Nonnull;

public class RNFirebaseMessaging extends ReactContextBaseJavaModule {
    private static final String TAG = "RNFirebaseMessaging";

    private class MessageReceiver extends BroadcastReceiver {
        private MessageReceiver() {
        }

        /* synthetic */ MessageReceiver(RNFirebaseMessaging rNFirebaseMessaging, AnonymousClass1 anonymousClass1) {
            this();
        }

        public void onReceive(Context context, Intent intent) {
            if (RNFirebaseMessaging.this.access$700().hasActiveCatalystInstance()) {
                Log.d(RNFirebaseMessaging.TAG, "Received new message");
                Utils.sendEvent(RNFirebaseMessaging.this.access$700(), "messaging_message_received", MessagingSerializer.parseRemoteMessage((RemoteMessage) intent.getParcelableExtra("message")));
            }
        }
    }

    private class RefreshTokenReceiver extends BroadcastReceiver {
        private RefreshTokenReceiver() {
        }

        /* synthetic */ RefreshTokenReceiver(RNFirebaseMessaging rNFirebaseMessaging, AnonymousClass1 anonymousClass1) {
            this();
        }

        public void onReceive(Context context, Intent intent) {
            if (RNFirebaseMessaging.this.access$700().hasActiveCatalystInstance()) {
                Log.d(RNFirebaseMessaging.TAG, "Received new messaging token.");
                new Thread(new Runnable() {
                    public void run() {
                        Object token;
                        String str = RNFirebaseMessaging.TAG;
                        try {
                            token = FirebaseInstanceId.getInstance().getToken(FirebaseApp.getInstance().getOptions().getGcmSenderId(), FirebaseMessaging.INSTANCE_ID_SCOPE);
                        } catch (Throwable e) {
                            Log.d(str, "onNewToken error", e);
                            token = null;
                        }
                        if (token != null) {
                            Log.d(str, "Sending new messaging token event.");
                            Utils.sendEvent(RNFirebaseMessaging.this.access$700(), "messaging_token_refreshed", token);
                        }
                    }
                }).start();
            }
        }
    }

    public String getName() {
        return TAG;
    }

    RNFirebaseMessaging(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(reactApplicationContext);
        instance.registerReceiver(new MessageReceiver(this, null), new IntentFilter(RNFirebaseMessagingService.MESSAGE_EVENT));
        instance.registerReceiver(new RefreshTokenReceiver(this, null), new IntentFilter(RNFirebaseMessagingService.NEW_TOKEN_EVENT));
    }

    @ReactMethod
    public void getToken(Promise promise) {
        try {
            promise.resolve(FirebaseInstanceId.getInstance().getToken(FirebaseApp.getInstance().getOptions().getGcmSenderId(), FirebaseMessaging.INSTANCE_ID_SCOPE));
        } catch (Throwable th) {
            th.printStackTrace();
            promise.reject("messaging/fcm-token-error", th.getMessage());
        }
    }

    @ReactMethod
    public void deleteToken(Promise promise) {
        try {
            FirebaseInstanceId.getInstance().deleteToken(FirebaseApp.getInstance().getOptions().getGcmSenderId(), FirebaseMessaging.INSTANCE_ID_SCOPE);
            promise.resolve(null);
        } catch (Throwable th) {
            th.printStackTrace();
            promise.reject("messaging/fcm-token-error", th.getMessage());
        }
    }

    @ReactMethod
    public void requestPermission(Promise promise) {
        promise.resolve(null);
    }

    @ReactMethod
    public void hasPermission(Promise promise) {
        promise.resolve(Boolean.valueOf(NotificationManagerCompat.from(access$700()).areNotificationsEnabled()));
    }

    @ReactMethod
    public void sendMessage(ReadableMap readableMap, Promise promise) {
        String str = "to";
        if (readableMap.hasKey(str)) {
            Builder builder = new Builder(readableMap.getString(str));
            str = "collapseKey";
            if (readableMap.hasKey(str)) {
                builder = builder.setCollapseKey(readableMap.getString(str));
            }
            str = "messageId";
            if (readableMap.hasKey(str)) {
                builder = builder.setMessageId(readableMap.getString(str));
            }
            str = "messageType";
            if (readableMap.hasKey(str)) {
                builder = builder.setMessageType(readableMap.getString(str));
            }
            str = "ttl";
            if (readableMap.hasKey(str)) {
                builder = builder.setTtl(readableMap.getInt(str));
            }
            str = "data";
            if (readableMap.hasKey(str)) {
                readableMap = readableMap.getMap(str);
                ReadableMapKeySetIterator keySetIterator = readableMap.keySetIterator();
                while (keySetIterator.hasNextKey()) {
                    String nextKey = keySetIterator.nextKey();
                    builder = builder.addData(nextKey, readableMap.getString(nextKey));
                }
            }
            FirebaseMessaging.getInstance().send(builder.build());
            promise.resolve(null);
            return;
        }
        promise.reject("messaging/invalid-message", "The supplied message is missing a 'to' field");
    }

    @ReactMethod
    public void subscribeToTopic(String str, final Promise promise) {
        FirebaseMessaging.getInstance().subscribeToTopic(str).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseMessaging.TAG;
                if (isSuccessful) {
                    Log.d(str, "subscribeToTopic:onComplete:success");
                    promise.resolve(null);
                    return;
                }
                Throwable exception = task.getException();
                Log.e(str, "subscribeToTopic:onComplete:failure", exception);
                promise.reject(exception);
            }
        });
    }

    @ReactMethod
    public void unsubscribeFromTopic(String str, final Promise promise) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(str).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseMessaging.TAG;
                if (isSuccessful) {
                    Log.d(str, "unsubscribeFromTopic:onComplete:success");
                    promise.resolve(null);
                    return;
                }
                Throwable exception = task.getException();
                Log.e(str, "unsubscribeFromTopic:onComplete:failure", exception);
                promise.reject(exception);
            }
        });
    }
}
