package cl.json.social;

import android.content.ActivityNotFoundException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;

public class GenericShare extends ShareIntent {
    protected String getDefaultWebLink() {
        return null;
    }

    protected String getPackage() {
        return null;
    }

    protected String getPlayStoreLink() {
        return null;
    }

    public GenericShare(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    public void open(ReadableMap readableMap) throws ActivityNotFoundException {
        super.open(readableMap);
        openIntentChooser();
    }
}
