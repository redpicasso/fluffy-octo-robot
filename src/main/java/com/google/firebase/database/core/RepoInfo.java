package com.google.firebase.database.core;

import com.facebook.common.util.UriUtil;
import java.net.URI;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class RepoInfo {
    private static final String LAST_SESSION_ID_PARAM = "ls";
    private static final String VERSION_PARAM = "v";
    public String host;
    public String internalHost;
    public String namespace;
    public boolean secure;

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UriUtil.HTTP_SCHEME);
        stringBuilder.append(this.secure ? "s" : "");
        stringBuilder.append("://");
        stringBuilder.append(this.host);
        return stringBuilder.toString();
    }

    public String toDebugString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(host=");
        stringBuilder.append(this.host);
        stringBuilder.append(", secure=");
        stringBuilder.append(this.secure);
        stringBuilder.append(", ns=");
        stringBuilder.append(this.namespace);
        stringBuilder.append(" internal=");
        stringBuilder.append(this.internalHost);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public URI getConnectionURL(String str) {
        String str2 = this.secure ? "wss" : "ws";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append("://");
        stringBuilder.append(this.internalHost);
        stringBuilder.append("/.ws?ns=");
        stringBuilder.append(this.namespace);
        stringBuilder.append("&");
        stringBuilder.append(VERSION_PARAM);
        stringBuilder.append("=");
        stringBuilder.append("5");
        str2 = stringBuilder.toString();
        if (str != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append("&ls=");
            stringBuilder.append(str);
            str2 = stringBuilder.toString();
        }
        return URI.create(str2);
    }

    public boolean isCacheableHost() {
        return this.internalHost.startsWith("s-");
    }

    public boolean isSecure() {
        return this.secure;
    }

    public boolean isDemoHost() {
        return this.host.contains(".firebaseio-demo.com");
    }

    public boolean isCustomHost() {
        return (this.host.contains(".firebaseio.com") || this.host.contains(".firebaseio-demo.com")) ? false : true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RepoInfo repoInfo = (RepoInfo) obj;
        if (this.secure == repoInfo.secure && this.host.equals(repoInfo.host)) {
            return this.namespace.equals(repoInfo.namespace);
        }
        return false;
    }

    public int hashCode() {
        return (((this.host.hashCode() * 31) + this.secure) * 31) + this.namespace.hashCode();
    }
}
