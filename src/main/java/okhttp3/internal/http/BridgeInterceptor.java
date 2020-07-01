package okhttp3.internal.http;

import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.Version;
import okio.GzipSource;
import okio.Okio;
import okio.Source;

public final class BridgeInterceptor implements Interceptor {
    private final CookieJar cookieJar;

    public BridgeInterceptor(CookieJar cookieJar) {
        this.cookieJar = cookieJar;
    }

    public Response intercept(Chain chain) throws IOException {
        String str;
        Request request = chain.request();
        Builder newBuilder = request.newBuilder();
        RequestBody body = request.body();
        String str2 = HttpHeaders.CONTENT_TYPE;
        String str3 = HttpHeaders.CONTENT_LENGTH;
        if (body != null) {
            MediaType contentType = body.contentType();
            if (contentType != null) {
                newBuilder.header(str2, contentType.toString());
            }
            long contentLength = body.contentLength();
            str = HttpHeaders.TRANSFER_ENCODING;
            if (contentLength != -1) {
                newBuilder.header(str3, Long.toString(contentLength));
                newBuilder.removeHeader(str);
            } else {
                newBuilder.header(str, "chunked");
                newBuilder.removeHeader(str3);
            }
        }
        str = HttpHeaders.HOST;
        boolean z = false;
        if (request.header(str) == null) {
            newBuilder.header(str, Util.hostHeader(request.url(), false));
        }
        str = HttpHeaders.CONNECTION;
        if (request.header(str) == null) {
            newBuilder.header(str, "Keep-Alive");
        }
        str = HttpHeaders.ACCEPT_ENCODING;
        String str4 = "gzip";
        if (request.header(str) == null && request.header(HttpHeaders.RANGE) == null) {
            z = true;
            newBuilder.header(str, str4);
        }
        List loadForRequest = this.cookieJar.loadForRequest(request.url());
        if (!loadForRequest.isEmpty()) {
            newBuilder.header(HttpHeaders.COOKIE, cookieHeader(loadForRequest));
        }
        str = HttpHeaders.USER_AGENT;
        if (request.header(str) == null) {
            newBuilder.header(str, Version.userAgent());
        }
        Response proceed = chain.proceed(newBuilder.build());
        HttpHeaders.receiveHeaders(this.cookieJar, request.url(), proceed.headers());
        Response.Builder request2 = proceed.newBuilder().request(request);
        if (z) {
            String str5 = HttpHeaders.CONTENT_ENCODING;
            if (str4.equalsIgnoreCase(proceed.header(str5)) && HttpHeaders.hasBody(proceed)) {
                Source gzipSource = new GzipSource(proceed.body().source());
                request2.headers(proceed.headers().newBuilder().removeAll(str5).removeAll(str3).build());
                request2.body(new RealResponseBody(proceed.header(str2), -1, Okio.buffer(gzipSource)));
            }
        }
        return request2.build();
    }

    private String cookieHeader(List<Cookie> list) {
        StringBuilder stringBuilder = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                stringBuilder.append("; ");
            }
            Cookie cookie = (Cookie) list.get(i);
            stringBuilder.append(cookie.name());
            stringBuilder.append('=');
            stringBuilder.append(cookie.value());
        }
        return stringBuilder.toString();
    }
}
