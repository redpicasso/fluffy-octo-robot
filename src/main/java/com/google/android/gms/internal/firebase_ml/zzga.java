package com.google.android.gms.internal.firebase_ml;

import io.grpc.internal.GrpcUtil;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.Arrays;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public final class zzga extends zzfo {
    private static final String[] zzvf;
    private final HostnameVerifier hostnameVerifier;
    private final zzfv zzwd;
    private final SSLSocketFactory zzwe;

    public zzga() {
        this(null, null, null);
    }

    private zzga(zzfv zzfv, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier) {
        if (System.getProperty("com.google.api.client.should_use_proxy") != null) {
            zzfv = new zzfw(new Proxy(Type.HTTP, new InetSocketAddress(System.getProperty("https.proxyHost"), Integer.parseInt(System.getProperty("https.proxyPort")))));
        } else {
            zzfv = new zzfw();
        }
        this.zzwd = zzfv;
        this.zzwe = null;
        this.hostnameVerifier = null;
    }

    public final boolean zzaj(String str) {
        return Arrays.binarySearch(zzvf, str) >= 0;
    }

    protected final /* synthetic */ zzfp zzc(String str, String str2) throws IOException {
        Object[] objArr = new Object[]{str};
        if (zzaj(str)) {
            HttpURLConnection zza = this.zzwd.zza(new URL(str2));
            zza.setRequestMethod(str);
            boolean z = zza instanceof HttpsURLConnection;
            return new zzfx(zza);
        }
        throw new IllegalArgumentException(zzla.zzb("HTTP method %s not supported", objArr));
    }

    static {
        String[] strArr = new String[]{"DELETE", "GET", "HEAD", "OPTIONS", GrpcUtil.HTTP_METHOD, "PUT", "TRACE"};
        zzvf = strArr;
        Arrays.sort(strArr);
    }
}
