package com.squareup.okhttp.internal.http;

import com.google.common.net.HttpHeaders;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class CacheStrategy {
    public final Response cacheResponse;
    public final Request networkRequest;

    public static class Factory {
        private int ageSeconds = -1;
        final Response cacheResponse;
        private String etag;
        private Date expires;
        private Date lastModified;
        private String lastModifiedString;
        final long nowMillis;
        private long receivedResponseMillis;
        final Request request;
        private long sentRequestMillis;
        private Date servedDate;
        private String servedDateString;

        public Factory(long j, Request request, Response response) {
            this.nowMillis = j;
            this.request = request;
            this.cacheResponse = response;
            if (response != null) {
                Headers headers = response.headers();
                int size = headers.size();
                for (int i = 0; i < size; i++) {
                    String name = headers.name(i);
                    String value = headers.value(i);
                    if (HttpHeaders.DATE.equalsIgnoreCase(name)) {
                        this.servedDate = HttpDate.parse(value);
                        this.servedDateString = value;
                    } else if (HttpHeaders.EXPIRES.equalsIgnoreCase(name)) {
                        this.expires = HttpDate.parse(value);
                    } else if (HttpHeaders.LAST_MODIFIED.equalsIgnoreCase(name)) {
                        this.lastModified = HttpDate.parse(value);
                        this.lastModifiedString = value;
                    } else if (HttpHeaders.ETAG.equalsIgnoreCase(name)) {
                        this.etag = value;
                    } else if (HttpHeaders.AGE.equalsIgnoreCase(name)) {
                        this.ageSeconds = HeaderParser.parseSeconds(value, -1);
                    } else if (OkHeaders.SENT_MILLIS.equalsIgnoreCase(name)) {
                        this.sentRequestMillis = Long.parseLong(value);
                    } else if (OkHeaders.RECEIVED_MILLIS.equalsIgnoreCase(name)) {
                        this.receivedResponseMillis = Long.parseLong(value);
                    }
                }
            }
        }

        public CacheStrategy get() {
            CacheStrategy candidate = getCandidate();
            return (candidate.networkRequest == null || !this.request.cacheControl().onlyIfCached()) ? candidate : new CacheStrategy(null, null);
        }

        private CacheStrategy getCandidate() {
            if (this.cacheResponse == null) {
                return new CacheStrategy(this.request, null);
            }
            if (this.request.isHttps() && this.cacheResponse.handshake() == null) {
                return new CacheStrategy(this.request, null);
            }
            if (!CacheStrategy.isCacheable(this.cacheResponse, this.request)) {
                return new CacheStrategy(this.request, null);
            }
            CacheControl cacheControl = this.request.cacheControl();
            if (cacheControl.noCache() || hasConditions(this.request)) {
                return new CacheStrategy(this.request, null);
            }
            long cacheResponseAge = cacheResponseAge();
            long computeFreshnessLifetime = computeFreshnessLifetime();
            if (cacheControl.maxAgeSeconds() != -1) {
                computeFreshnessLifetime = Math.min(computeFreshnessLifetime, TimeUnit.SECONDS.toMillis((long) cacheControl.maxAgeSeconds()));
            }
            long j = 0;
            long toMillis = cacheControl.minFreshSeconds() != -1 ? TimeUnit.SECONDS.toMillis((long) cacheControl.minFreshSeconds()) : 0;
            CacheControl cacheControl2 = this.cacheResponse.cacheControl();
            if (!(cacheControl2.mustRevalidate() || cacheControl.maxStaleSeconds() == -1)) {
                j = TimeUnit.SECONDS.toMillis((long) cacheControl.maxStaleSeconds());
            }
            if (!cacheControl2.noCache()) {
                toMillis += cacheResponseAge;
                if (toMillis < j + computeFreshnessLifetime) {
                    Builder newBuilder = this.cacheResponse.newBuilder();
                    String str = HttpHeaders.WARNING;
                    if (toMillis >= computeFreshnessLifetime) {
                        newBuilder.addHeader(str, "110 HttpURLConnection \"Response is stale\"");
                    }
                    if (cacheResponseAge > 86400000 && isFreshnessLifetimeHeuristic()) {
                        newBuilder.addHeader(str, "113 HttpURLConnection \"Heuristic expiration\"");
                    }
                    return new CacheStrategy(null, newBuilder.build());
                }
            }
            Request.Builder newBuilder2 = this.request.newBuilder();
            String str2 = this.etag;
            if (str2 != null) {
                newBuilder2.header(HttpHeaders.IF_NONE_MATCH, str2);
            } else {
                Date date = this.lastModified;
                String str3 = HttpHeaders.IF_MODIFIED_SINCE;
                if (date != null) {
                    newBuilder2.header(str3, this.lastModifiedString);
                } else if (this.servedDate != null) {
                    newBuilder2.header(str3, this.servedDateString);
                }
            }
            Request build = newBuilder2.build();
            return hasConditions(build) ? new CacheStrategy(build, this.cacheResponse) : new CacheStrategy(build, null);
        }

        private long computeFreshnessLifetime() {
            CacheControl cacheControl = this.cacheResponse.cacheControl();
            if (cacheControl.maxAgeSeconds() != -1) {
                return TimeUnit.SECONDS.toMillis((long) cacheControl.maxAgeSeconds());
            }
            long j = 0;
            Date date;
            long time;
            if (this.expires != null) {
                date = this.servedDate;
                time = this.expires.getTime() - (date != null ? date.getTime() : this.receivedResponseMillis);
                if (time > 0) {
                    j = time;
                }
                return j;
            }
            if (this.lastModified != null && this.cacheResponse.request().httpUrl().query() == null) {
                date = this.servedDate;
                time = (date != null ? date.getTime() : this.sentRequestMillis) - this.lastModified.getTime();
                if (time > 0) {
                    j = time / 10;
                }
            }
            return j;
        }

        private long cacheResponseAge() {
            Date date = this.servedDate;
            long j = 0;
            if (date != null) {
                j = Math.max(0, this.receivedResponseMillis - date.getTime());
            }
            if (this.ageSeconds != -1) {
                j = Math.max(j, TimeUnit.SECONDS.toMillis((long) this.ageSeconds));
            }
            long j2 = this.receivedResponseMillis;
            return (j + (j2 - this.sentRequestMillis)) + (this.nowMillis - j2);
        }

        private boolean isFreshnessLifetimeHeuristic() {
            return this.cacheResponse.cacheControl().maxAgeSeconds() == -1 && this.expires == null;
        }

        private static boolean hasConditions(Request request) {
            return (request.header(HttpHeaders.IF_MODIFIED_SINCE) == null && request.header(HttpHeaders.IF_NONE_MATCH) == null) ? false : true;
        }
    }

    private CacheStrategy(Request request, Response response) {
        this.networkRequest = request;
        this.cacheResponse = response;
    }

    /* JADX WARNING: Missing block: B:28:0x0056, code:
            if (r3.cacheControl().isPrivate() == false) goto L_0x0059;
     */
    /* JADX WARNING: Missing block: B:29:0x0059, code:
            return false;
     */
    public static boolean isCacheable(com.squareup.okhttp.Response r3, com.squareup.okhttp.Request r4) {
        /*
        r0 = r3.code();
        r1 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r2 = 0;
        if (r0 == r1) goto L_0x005a;
    L_0x0009:
        r1 = 410; // 0x19a float:5.75E-43 double:2.026E-321;
        if (r0 == r1) goto L_0x005a;
    L_0x000d:
        r1 = 414; // 0x19e float:5.8E-43 double:2.045E-321;
        if (r0 == r1) goto L_0x005a;
    L_0x0011:
        r1 = 501; // 0x1f5 float:7.02E-43 double:2.475E-321;
        if (r0 == r1) goto L_0x005a;
    L_0x0015:
        r1 = 203; // 0xcb float:2.84E-43 double:1.003E-321;
        if (r0 == r1) goto L_0x005a;
    L_0x0019:
        r1 = 204; // 0xcc float:2.86E-43 double:1.01E-321;
        if (r0 == r1) goto L_0x005a;
    L_0x001d:
        r1 = 307; // 0x133 float:4.3E-43 double:1.517E-321;
        if (r0 == r1) goto L_0x0031;
    L_0x0021:
        r1 = 308; // 0x134 float:4.32E-43 double:1.52E-321;
        if (r0 == r1) goto L_0x005a;
    L_0x0025:
        r1 = 404; // 0x194 float:5.66E-43 double:1.996E-321;
        if (r0 == r1) goto L_0x005a;
    L_0x0029:
        r1 = 405; // 0x195 float:5.68E-43 double:2.0E-321;
        if (r0 == r1) goto L_0x005a;
    L_0x002d:
        switch(r0) {
            case 300: goto L_0x005a;
            case 301: goto L_0x005a;
            case 302: goto L_0x0031;
            default: goto L_0x0030;
        };
    L_0x0030:
        goto L_0x0059;
    L_0x0031:
        r0 = "Expires";
        r0 = r3.header(r0);
        if (r0 != 0) goto L_0x005a;
    L_0x0039:
        r0 = r3.cacheControl();
        r0 = r0.maxAgeSeconds();
        r1 = -1;
        if (r0 != r1) goto L_0x005a;
    L_0x0044:
        r0 = r3.cacheControl();
        r0 = r0.isPublic();
        if (r0 != 0) goto L_0x005a;
    L_0x004e:
        r0 = r3.cacheControl();
        r0 = r0.isPrivate();
        if (r0 == 0) goto L_0x0059;
    L_0x0058:
        goto L_0x005a;
    L_0x0059:
        return r2;
    L_0x005a:
        r3 = r3.cacheControl();
        r3 = r3.noStore();
        if (r3 != 0) goto L_0x006f;
    L_0x0064:
        r3 = r4.cacheControl();
        r3 = r3.noStore();
        if (r3 != 0) goto L_0x006f;
    L_0x006e:
        r2 = 1;
    L_0x006f:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.CacheStrategy.isCacheable(com.squareup.okhttp.Response, com.squareup.okhttp.Request):boolean");
    }
}