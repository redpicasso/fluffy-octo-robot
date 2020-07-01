package okhttp3;

import com.facebook.common.util.UriUtil;
import io.grpc.internal.GrpcUtil;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import okhttp3.internal.URLFilter;
import okhttp3.internal.annotations.EverythingIsNonNull;
import okhttp3.internal.huc.OkHttpURLConnection;
import okhttp3.internal.huc.OkHttpsURLConnection;

@EverythingIsNonNull
public final class OkUrlFactory implements URLStreamHandlerFactory, Cloneable {
    private OkHttpClient client;
    private URLFilter urlFilter;

    public OkUrlFactory(OkHttpClient okHttpClient) {
        this.client = okHttpClient;
    }

    public OkHttpClient client() {
        return this.client;
    }

    public OkUrlFactory setClient(OkHttpClient okHttpClient) {
        this.client = okHttpClient;
        return this;
    }

    void setUrlFilter(URLFilter uRLFilter) {
        this.urlFilter = uRLFilter;
    }

    public OkUrlFactory clone() {
        return new OkUrlFactory(this.client);
    }

    public HttpURLConnection open(URL url) {
        return open(url, this.client.proxy());
    }

    HttpURLConnection open(URL url, Proxy proxy) {
        String protocol = url.getProtocol();
        OkHttpClient build = this.client.newBuilder().proxy(proxy).build();
        if (protocol.equals(UriUtil.HTTP_SCHEME)) {
            return new OkHttpURLConnection(url, build, this.urlFilter);
        }
        if (protocol.equals(UriUtil.HTTPS_SCHEME)) {
            return new OkHttpsURLConnection(url, build, this.urlFilter);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected protocol: ");
        stringBuilder.append(protocol);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public URLStreamHandler createURLStreamHandler(final String str) {
        if (str.equals(UriUtil.HTTP_SCHEME) || str.equals(UriUtil.HTTPS_SCHEME)) {
            return new URLStreamHandler() {
                protected URLConnection openConnection(URL url) {
                    return OkUrlFactory.this.open(url);
                }

                protected URLConnection openConnection(URL url, Proxy proxy) {
                    return OkUrlFactory.this.open(url, proxy);
                }

                protected int getDefaultPort() {
                    if (str.equals(UriUtil.HTTP_SCHEME)) {
                        return 80;
                    }
                    if (str.equals(UriUtil.HTTPS_SCHEME)) {
                        return GrpcUtil.DEFAULT_PORT_SSL;
                    }
                    throw new AssertionError();
                }
            };
        }
        return null;
    }
}
