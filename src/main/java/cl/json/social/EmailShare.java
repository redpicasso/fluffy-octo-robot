package cl.json.social;

import android.content.ActivityNotFoundException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;

public class EmailShare extends SingleShareIntent {
    private static final String PACKAGE = "com.google.android.gm";

    protected String getDefaultWebLink() {
        return null;
    }

    protected String getPackage() {
        return PACKAGE;
    }

    protected String getPlayStoreLink() {
        return null;
    }

    public EmailShare(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    public void open(ReadableMap readableMap) throws ActivityNotFoundException {
        super.open(readableMap);
        openIntentChooser();
    }
}
