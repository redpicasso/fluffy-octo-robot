package cl.json.social;

import android.content.ActivityNotFoundException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;

public class WhatsAppShare extends SingleShareIntent {
    private static final String PACKAGE = "com.whatsapp";
    private static final String PLAY_STORE_LINK = "market://details?id=com.whatsapp";

    protected String getDefaultWebLink() {
        return null;
    }

    protected String getPackage() {
        return PACKAGE;
    }

    protected String getPlayStoreLink() {
        return PLAY_STORE_LINK;
    }

    public WhatsAppShare(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    public void open(ReadableMap readableMap) throws ActivityNotFoundException {
        super.open(readableMap);
        openIntentChooser();
    }
}
