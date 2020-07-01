package co.apptailor.googlesignin;

import com.facebook.react.bridge.WritableMap;

public class PendingAuthRecovery {
    private WritableMap userProperties;

    public PendingAuthRecovery(WritableMap writableMap) {
        this.userProperties = writableMap;
    }

    public WritableMap getUserProperties() {
        return this.userProperties;
    }
}
