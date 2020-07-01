package cl.json.social;

import android.content.ActivityNotFoundException;
import android.os.Build.VERSION;
import android.provider.Telephony.Sms;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;

public class SMSShare extends SingleShareIntent {
    private static final String PACKAGE = "com.android.mms";
    private static final String PLAY_STORE_LINK = "market://details?id=com.android.mms";
    private ReactApplicationContext reactContext = null;

    protected String getDefaultWebLink() {
        return null;
    }

    protected String getPlayStoreLink() {
        return PLAY_STORE_LINK;
    }

    public SMSShare(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.reactContext = reactApplicationContext;
    }

    public void open(ReadableMap readableMap) throws ActivityNotFoundException {
        super.open(readableMap);
        openIntentChooser();
    }

    protected String getPackage() {
        return VERSION.SDK_INT >= 19 ? Sms.getDefaultSmsPackage(this.reactContext) : PACKAGE;
    }
}
