package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public final class zzfw implements zzfv {
    private final Proxy zzvx;

    public zzfw() {
        this(null);
    }

    public zzfw(Proxy proxy) {
        this.zzvx = proxy;
    }

    public final HttpURLConnection zza(URL url) throws IOException {
        Proxy proxy = this.zzvx;
        return (HttpURLConnection) (proxy == null ? url.openConnection() : url.openConnection(proxy));
    }
}
