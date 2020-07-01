package cl.json.social;

import android.content.ActivityNotFoundException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;

public class PinterestShare extends SingleShareIntent {
    private static final String DEFAULT_WEB_LINK = "https://pinterest.com/pin/create/button/?url={url}&media=$media&description={message}";
    private static final String PACKAGE = "com.pinterest";
    private static final String PLAY_STORE_LINK = "market://details?id=com.pinterest";

    protected String getDefaultWebLink() {
        return DEFAULT_WEB_LINK;
    }

    protected String getPackage() {
        return PACKAGE;
    }

    protected String getPlayStoreLink() {
        return PLAY_STORE_LINK;
    }

    public PinterestShare(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    public void open(ReadableMap readableMap) throws ActivityNotFoundException {
        super.open(readableMap);
        openIntentChooser();
    }
}
