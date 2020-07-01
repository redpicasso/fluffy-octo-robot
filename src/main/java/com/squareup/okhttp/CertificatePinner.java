package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.SSLPeerUnverifiedException;
import okio.ByteString;

public final class CertificatePinner {
    public static final CertificatePinner DEFAULT = new Builder().build();
    private final Map<String, Set<ByteString>> hostnameToPins;

    public static final class Builder {
        private final Map<String, Set<ByteString>> hostnameToPins = new LinkedHashMap();

        public Builder add(String str, String... strArr) {
            if (str != null) {
                Set linkedHashSet = new LinkedHashSet();
                Set set = (Set) this.hostnameToPins.put(str, Collections.unmodifiableSet(linkedHashSet));
                if (set != null) {
                    linkedHashSet.addAll(set);
                }
                int length = strArr.length;
                int i = 0;
                while (i < length) {
                    String str2 = strArr[i];
                    StringBuilder stringBuilder;
                    if (str2.startsWith("sha1/")) {
                        ByteString decodeBase64 = ByteString.decodeBase64(str2.substring(5));
                        if (decodeBase64 != null) {
                            linkedHashSet.add(decodeBase64);
                            i++;
                        } else {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("pins must be base64: ");
                            stringBuilder.append(str2);
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                    }
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("pins must start with 'sha1/': ");
                    stringBuilder.append(str2);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                return this;
            }
            throw new IllegalArgumentException("hostname == null");
        }

        public CertificatePinner build() {
            return new CertificatePinner(this);
        }
    }

    private CertificatePinner(Builder builder) {
        this.hostnameToPins = Util.immutableMap(builder.hostnameToPins);
    }

    public void check(String str, List<Certificate> list) throws SSLPeerUnverifiedException {
        Set<ByteString> findMatchingPins = findMatchingPins(str);
        if (findMatchingPins != null) {
            int size = list.size();
            int i = 0;
            while (i < size) {
                if (!findMatchingPins.contains(sha1((X509Certificate) list.get(i)))) {
                    i++;
                } else {
                    return;
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Certificate pinning failure!");
            stringBuilder.append("\n  Peer certificate chain:");
            i = list.size();
            for (int i2 = 0; i2 < i; i2++) {
                X509Certificate x509Certificate = (X509Certificate) list.get(i2);
                stringBuilder.append("\n    ");
                stringBuilder.append(pin(x509Certificate));
                stringBuilder.append(": ");
                stringBuilder.append(x509Certificate.getSubjectDN().getName());
            }
            stringBuilder.append("\n  Pinned certificates for ");
            stringBuilder.append(str);
            stringBuilder.append(":");
            for (ByteString byteString : findMatchingPins) {
                stringBuilder.append("\n    sha1/");
                stringBuilder.append(byteString.base64());
            }
            throw new SSLPeerUnverifiedException(stringBuilder.toString());
        }
    }

    public void check(String str, Certificate... certificateArr) throws SSLPeerUnverifiedException {
        check(str, Arrays.asList(certificateArr));
    }

    Set<ByteString> findMatchingPins(String str) {
        Object obj;
        Set set = (Set) this.hostnameToPins.get(str);
        int indexOf = str.indexOf(46);
        if (indexOf != str.lastIndexOf(46)) {
            Map map = this.hostnameToPins;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("*.");
            stringBuilder.append(str.substring(indexOf + 1));
            obj = (Set) map.get(stringBuilder.toString());
        } else {
            obj = null;
        }
        if (set == null && obj == null) {
            return null;
        }
        if (set == null || obj == null) {
            return set != null ? set : obj;
        } else {
            Set<ByteString> linkedHashSet = new LinkedHashSet();
            linkedHashSet.addAll(set);
            linkedHashSet.addAll(obj);
            return linkedHashSet;
        }
    }

    public static String pin(Certificate certificate) {
        if (certificate instanceof X509Certificate) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sha1/");
            stringBuilder.append(sha1((X509Certificate) certificate).base64());
            return stringBuilder.toString();
        }
        throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
    }

    private static ByteString sha1(X509Certificate x509Certificate) {
        return Util.sha1(ByteString.of(x509Certificate.getPublicKey().getEncoded()));
    }
}
