package cl.json.social;

import android.content.ActivityNotFoundException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;

public class FacebookPagesManagerShare extends SingleShareIntent {
    private static final String DEFAULT_WEB_LINK = "https://www.facebook.com/sharer/sharer.php?u={url}";
    private static final String PACKAGE = "com.facebook.pages.app";

    protected String getDefaultWebLink() {
        return DEFAULT_WEB_LINK;
    }

    protected String getPackage() {
        return PACKAGE;
    }

    protected String getPlayStoreLink() {
        return null;
    }

    public FacebookPagesManagerShare(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    public void open(ReadableMap readableMap) throws ActivityNotFoundException {
        super.open(readableMap);
        openIntentChooser();
    }
}
