package com.google.firebase.database.connection;

import com.facebook.common.util.UriUtil;
import java.net.URI;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class HostInfo {
    private static final String LAST_SESSION_ID_PARAM = "ls";
    private static final String VERSION_PARAM = "v";
    private final String host;
    private final String namespace;
    private final boolean secure;

    public HostInfo(String str, String str2, boolean z) {
        this.host = str;
        this.namespace = str2;
        this.secure = z;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UriUtil.HTTP_SCHEME);
        stringBuilder.append(this.secure ? "s" : "");
        stringBuilder.append("://");
        stringBuilder.append(this.host);
        return stringBuilder.toString();
    }

    public static URI getConnectionUrl(String str, boolean z, String str2, String str3) {
        String str4 = z ? "wss" : "ws";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str4);
        stringBuilder.append("://");
        stringBuilder.append(str);
        stringBuilder.append("/.ws?ns=");
        stringBuilder.append(str2);
        stringBuilder.append("&");
        stringBuilder.append(VERSION_PARAM);
        stringBuilder.append("=");
        stringBuilder.append("5");
        str = stringBuilder.toString();
        if (str3 != null) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(str);
            stringBuilder2.append("&ls=");
            stringBuilder2.append(str3);
            str = stringBuilder2.toString();
        }
        return URI.create(str);
    }

    public String getHost() {
        return this.host;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public boolean isSecure() {
        return this.secure;
    }
}
