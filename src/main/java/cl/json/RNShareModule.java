package cl.json;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import cl.json.social.EmailShare;
import cl.json.social.FacebookPagesManagerShare;
import cl.json.social.FacebookShare;
import cl.json.social.GenericShare;
import cl.json.social.GooglePlusShare;
import cl.json.social.InstagramShare;
import cl.json.social.LinkedinShare;
import cl.json.social.MessengerShare;
import cl.json.social.PinterestShare;
import cl.json.social.SMSShare;
import cl.json.social.ShareIntent;
import cl.json.social.SnapChatShare;
import cl.json.social.TargetChosenReceiver;
import cl.json.social.TwitterShare;
import cl.json.social.WhatsAppShare;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

public class RNShareModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    public static final int SHARE_REQUEST_CODE = 16845;
    private final ReactApplicationContext reactContext;

    private enum SHARES {
        facebook,
        generic,
        pagesmanager,
        twitter,
        whatsapp,
        instagram,
        googleplus,
        email,
        pinterest,
        messenger,
        snapchat,
        sms,
        linkedin;

        public static ShareIntent getShareClass(String str, ReactApplicationContext reactApplicationContext) {
            switch (valueOf(str)) {
                case generic:
                    return new GenericShare(reactApplicationContext);
                case facebook:
                    return new FacebookShare(reactApplicationContext);
                case pagesmanager:
                    return new FacebookPagesManagerShare(reactApplicationContext);
                case twitter:
                    return new TwitterShare(reactApplicationContext);
                case whatsapp:
                    return new WhatsAppShare(reactApplicationContext);
                case instagram:
                    return new InstagramShare(reactApplicationContext);
                case googleplus:
                    return new GooglePlusShare(reactApplicationContext);
                case email:
                    return new EmailShare(reactApplicationContext);
                case pinterest:
                    return new PinterestShare(reactApplicationContext);
                case sms:
                    return new SMSShare(reactApplicationContext);
                case snapchat:
                    return new SnapChatShare(reactApplicationContext);
                case messenger:
                    return new MessengerShare(reactApplicationContext);
                case linkedin:
                    return new LinkedinShare(reactApplicationContext);
                default:
                    return null;
            }
        }
    }

    public String getName() {
        return "RNShare";
    }

    public void onNewIntent(Intent intent) {
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == SHARE_REQUEST_CODE && i2 == 0) {
            TargetChosenReceiver.sendCallback(true, Boolean.valueOf(false), "CANCELED");
        }
    }

    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
        onActivityResult(i, i2, intent);
    }

    public RNShareModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        reactApplicationContext.addActivityEventListener(this);
        this.reactContext = reactApplicationContext;
    }

    @Nullable
    public Map<String, Object> getConstants() {
        Map<String, Object> hashMap = new HashMap();
        for (SHARES shares : SHARES.values()) {
            hashMap.put(shares.toString().toUpperCase(), shares.toString());
        }
        return hashMap;
    }

    @ReactMethod
    public void open(ReadableMap readableMap, @androidx.annotation.Nullable Callback callback, @androidx.annotation.Nullable Callback callback2) {
        PrintStream printStream;
        StringBuilder stringBuilder;
        String str = "ERROR ";
        TargetChosenReceiver.registerCallbacks(callback2, callback);
        try {
            new GenericShare(this.reactContext).open(readableMap);
        } catch (ActivityNotFoundException e) {
            printStream = System.out;
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(e.getMessage());
            printStream.println(stringBuilder.toString());
            e.printStackTrace(System.out);
            TargetChosenReceiver.sendCallback(false, "not_available");
        } catch (Exception e2) {
            printStream = System.out;
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(e2.getMessage());
            printStream.println(stringBuilder.toString());
            e2.printStackTrace(System.out);
            TargetChosenReceiver.sendCallback(false, e2.getMessage());
        }
    }

    @ReactMethod
    public void shareSingle(ReadableMap readableMap, @androidx.annotation.Nullable Callback callback, @androidx.annotation.Nullable Callback callback2) {
        PrintStream printStream;
        StringBuilder stringBuilder;
        String str = "ERROR ";
        System.out.println("SHARE SINGLE METHOD");
        TargetChosenReceiver.registerCallbacks(callback2, callback);
        String str2 = NotificationCompat.CATEGORY_SOCIAL;
        if (ShareIntent.hasValidKey(str2, readableMap)) {
            try {
                ShareIntent shareClass = SHARES.getShareClass(readableMap.getString(str2), this.reactContext);
                if (shareClass == null || !(shareClass instanceof ShareIntent)) {
                    throw new ActivityNotFoundException("Invalid share activity");
                }
                shareClass.open(readableMap);
                return;
            } catch (ActivityNotFoundException e) {
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(e.getMessage());
                printStream.println(stringBuilder.toString());
                e.printStackTrace(System.out);
                TargetChosenReceiver.sendCallback(false, e.getMessage());
                return;
            } catch (Exception e2) {
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(e2.getMessage());
                printStream.println(stringBuilder.toString());
                e2.printStackTrace(System.out);
                TargetChosenReceiver.sendCallback(false, e2.getMessage());
                return;
            }
        }
        TargetChosenReceiver.sendCallback(false, "key 'social' missing in options");
    }

    @ReactMethod
    public void isPackageInstalled(String str, @androidx.annotation.Nullable Callback callback, @androidx.annotation.Nullable Callback callback2) {
        try {
            boolean isPackageInstalled = ShareIntent.isPackageInstalled(str, this.reactContext);
            callback2.invoke(Boolean.valueOf(isPackageInstalled));
        } catch (Exception e) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error: ");
            stringBuilder.append(e.getMessage());
            printStream.println(stringBuilder.toString());
            callback.invoke(e.getMessage());
        }
    }

    @ReactMethod
    public void isBase64File(String str, @androidx.annotation.Nullable Callback callback, @androidx.annotation.Nullable Callback callback2) {
        try {
            str = Uri.parse(str).getScheme();
            if (str == null || !str.equals("data")) {
                callback2.invoke(Boolean.valueOf(false));
                return;
            }
            callback2.invoke(Boolean.valueOf(true));
        } catch (Exception e) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ERROR ");
            stringBuilder.append(e.getMessage());
            printStream.println(stringBuilder.toString());
            e.printStackTrace(System.out);
            callback.invoke(e.getMessage());
        }
    }
}
