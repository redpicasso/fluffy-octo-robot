package com.squareup.okhttp;

import com.google.common.net.HttpHeaders;
import com.squareup.okhttp.internal.http.HttpMethod;
import io.grpc.internal.GrpcUtil;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;

public final class Request {
    private final RequestBody body;
    private volatile CacheControl cacheControl;
    private final Headers headers;
    private volatile URI javaNetUri;
    private volatile URL javaNetUrl;
    private final String method;
    private final Object tag;
    private final HttpUrl url;

    public static class Builder {
        private RequestBody body;
        private com.squareup.okhttp.Headers.Builder headers;
        private String method;
        private Object tag;
        private HttpUrl url;

        public Builder() {
            this.method = "GET";
            this.headers = new com.squareup.okhttp.Headers.Builder();
        }

        private Builder(Request request) {
            this.url = request.url;
            this.method = request.method;
            this.body = request.body;
            this.tag = request.tag;
            this.headers = request.headers.newBuilder();
        }

        public Builder url(HttpUrl httpUrl) {
            if (httpUrl != null) {
                this.url = httpUrl;
                return this;
            }
            throw new IllegalArgumentException("url == null");
        }

        public Builder url(String str) {
            if (str != null) {
                StringBuilder stringBuilder;
                if (str.regionMatches(true, 0, "ws:", 0, 3)) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("http:");
                    stringBuilder.append(str.substring(3));
                    str = stringBuilder.toString();
                } else {
                    if (str.regionMatches(true, 0, "wss:", 0, 4)) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("https:");
                        stringBuilder.append(str.substring(4));
                        str = stringBuilder.toString();
                    }
                }
                HttpUrl parse = HttpUrl.parse(str);
                if (parse != null) {
                    return url(parse);
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("unexpected url: ");
                stringBuilder2.append(str);
                throw new IllegalArgumentException(stringBuilder2.toString());
            }
            throw new IllegalArgumentException("url == null");
        }

        public Builder url(URL url) {
            if (url != null) {
                HttpUrl httpUrl = HttpUrl.get(url);
                if (httpUrl != null) {
                    return url(httpUrl);
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unexpected url: ");
                stringBuilder.append(url);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            throw new IllegalArgumentException("url == null");
        }

        public Builder header(String str, String str2) {
            this.headers.set(str, str2);
            return this;
        }

        public Builder addHeader(String str, String str2) {
            this.headers.add(str, str2);
            return this;
        }

        public Builder removeHeader(String str) {
            this.headers.removeAll(str);
            return this;
        }

        public Builder headers(Headers headers) {
            this.headers = headers.newBuilder();
            return this;
        }

        public Builder cacheControl(CacheControl cacheControl) {
            String cacheControl2 = cacheControl.toString();
            boolean isEmpty = cacheControl2.isEmpty();
            String str = HttpHeaders.CACHE_CONTROL;
            if (isEmpty) {
                return removeHeader(str);
            }
            return header(str, cacheControl2);
        }

        public Builder get() {
            return method("GET", null);
        }

        public Builder head() {
            return method("HEAD", null);
        }

        public Builder post(RequestBody requestBody) {
            return method(GrpcUtil.HTTP_METHOD, requestBody);
        }

        public Builder delete(RequestBody requestBody) {
            return method("DELETE", requestBody);
        }

        public Builder delete() {
            return delete(RequestBody.create(null, new byte[0]));
        }

        public Builder put(RequestBody requestBody) {
            return method("PUT", requestBody);
        }

        public Builder patch(RequestBody requestBody) {
            return method("PATCH", requestBody);
        }

        public Builder method(String str, RequestBody requestBody) {
            if (str == null || str.length() == 0) {
                throw new IllegalArgumentException("method == null || method.length() == 0");
            }
            String str2 = "method ";
            StringBuilder stringBuilder;
            if (requestBody != null && !HttpMethod.permitsRequestBody(str)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str2);
                stringBuilder.append(str);
                stringBuilder.append(" must not have a request body.");
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (requestBody == null && HttpMethod.requiresRequestBody(str)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str2);
                stringBuilder.append(str);
                stringBuilder.append(" must have a request body.");
                throw new IllegalArgumentException(stringBuilder.toString());
            } else {
                this.method = str;
                this.body = requestBody;
                return this;
            }
        }

        public Builder tag(Object obj) {
            this.tag = obj;
            return this;
        }

        public Request build() {
            if (this.url != null) {
                return new Request(this);
            }
            throw new IllegalStateException("url == null");
        }
    }

    private Request(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers.build();
        this.body = builder.body;
        this.tag = builder.tag != null ? builder.tag : this;
    }

    public HttpUrl httpUrl() {
        return this.url;
    }

    public URL url() {
        URL url = this.javaNetUrl;
        if (url != null) {
            return url;
        }
        url = this.url.url();
        this.javaNetUrl = url;
        return url;
    }

    public URI uri() throws IOException {
        try {
            URI uri = this.javaNetUri;
            if (uri != null) {
                return uri;
            }
            uri = this.url.uri();
            this.javaNetUri = uri;
            return uri;
        } catch (IllegalStateException e) {
            throw new IOException(e.getMessage());
        }
    }

    public String urlString() {
        return this.url.toString();
    }

    public String method() {
        return this.method;
    }

    public Headers headers() {
        return this.headers;
    }

    public String header(String str) {
        return this.headers.get(str);
    }

    public List<String> headers(String str) {
        return this.headers.values(str);
    }

    public RequestBody body() {
        return this.body;
    }

    public Object tag() {
        return this.tag;
    }

    public Builder newBuilder() {
        return new Builder();
    }

    public CacheControl cacheControl() {
        CacheControl cacheControl = this.cacheControl;
        if (cacheControl != null) {
            return cacheControl;
        }
        cacheControl = CacheControl.parse(this.headers);
        this.cacheControl = cacheControl;
        return cacheControl;
    }

    public boolean isHttps() {
        return this.url.isHttps();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Request{method=");
        stringBuilder.append(this.method);
        stringBuilder.append(", url=");
        stringBuilder.append(this.url);
        stringBuilder.append(", tag=");
        Object obj = this.tag;
        if (obj == this) {
            obj = null;
        }
        stringBuilder.append(obj);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
