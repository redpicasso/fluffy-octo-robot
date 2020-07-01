package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public final class zzoy {
    private final URL url;

    zzoy(String str) throws MalformedURLException {
        this.url = new URL(str);
    }

    public final URLConnection openConnection() throws IOException {
        return this.url.openConnection();
    }
}
