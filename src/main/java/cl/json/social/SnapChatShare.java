package cl.json.social;

import android.content.ActivityNotFoundException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;

public class SnapChatShare extends SingleShareIntent {
    private static final String CLASS = "com.snapchat.android.LandingPageActivity";
    private static final String PACKAGE = "com.snapchat.android";
    private static final String PLAY_STORE_LINK = "market://details?id=com.snapchat.android";

    protected String getComponentClass() {
        return CLASS;
    }

    protected String getDefaultWebLink() {
        return null;
    }

    protected String getPackage() {
        return PACKAGE;
    }

    protected String getPlayStoreLink() {
        return PLAY_STORE_LINK;
    }

    public SnapChatShare(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    public void open(ReadableMap readableMap) throws ActivityNotFoundException {
        super.open(readableMap);
        openIntentChooser();
    }
}
