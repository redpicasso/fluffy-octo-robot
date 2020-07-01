package com.facebook.react.modules.network;

import java.util.List;
import javax.annotation.Nullable;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class ReactCookieJarContainer implements CookieJarContainer {
    @Nullable
    private CookieJar cookieJar = null;

    public void setCookieJar(CookieJar cookieJar) {
        this.cookieJar = cookieJar;
    }

    public void removeCookieJar() {
        this.cookieJar = null;
    }

    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        CookieJar cookieJar = this.cookieJar;
        if (cookieJar != null) {
            cookieJar.saveFromResponse(httpUrl, list);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for R(6:5|6|7|8|15|3) */
    /* JADX WARNING: Missing block: B:4:0x0015, code:
            if (r6.hasNext() != false) goto L_0x0017;
     */
    /* JADX WARNING: Missing block: B:5:0x0017, code:
            r1 = (okhttp3.Cookie) r6.next();
     */
    /* JADX WARNING: Missing block: B:7:?, code:
            new okhttp3.Headers.Builder().add(r1.name(), r1.value());
            r0.add(r1);
     */
    /* JADX WARNING: Missing block: B:9:0x0031, code:
            return r0;
     */
    public java.util.List<okhttp3.Cookie> loadForRequest(okhttp3.HttpUrl r6) {
        /*
        r5 = this;
        r0 = r5.cookieJar;
        if (r0 == 0) goto L_0x0032;
    L_0x0004:
        r6 = r0.loadForRequest(r6);
        r0 = new java.util.ArrayList;
        r0.<init>();
        r6 = r6.iterator();
    L_0x0011:
        r1 = r6.hasNext();
        if (r1 == 0) goto L_0x0031;
    L_0x0017:
        r1 = r6.next();
        r1 = (okhttp3.Cookie) r1;
        r2 = new okhttp3.Headers$Builder;	 Catch:{ IllegalArgumentException -> 0x0011 }
        r2.<init>();	 Catch:{ IllegalArgumentException -> 0x0011 }
        r3 = r1.name();	 Catch:{ IllegalArgumentException -> 0x0011 }
        r4 = r1.value();	 Catch:{ IllegalArgumentException -> 0x0011 }
        r2.add(r3, r4);	 Catch:{ IllegalArgumentException -> 0x0011 }
        r0.add(r1);	 Catch:{ IllegalArgumentException -> 0x0011 }
        goto L_0x0011;
    L_0x0031:
        return r0;
    L_0x0032:
        r6 = java.util.Collections.emptyList();
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.modules.network.ReactCookieJarContainer.loadForRequest(okhttp3.HttpUrl):java.util.List<okhttp3.Cookie>");
    }
}
