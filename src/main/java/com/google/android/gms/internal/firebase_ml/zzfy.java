package com.google.android.gms.internal.firebase_ml;

import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

final class zzfy extends zzfq {
    private final int responseCode;
    private final String responseMessage;
    private final HttpURLConnection zzvy;
    private final ArrayList<String> zzvz = new ArrayList();
    private final ArrayList<String> zzwa = new ArrayList();

    zzfy(HttpURLConnection httpURLConnection) throws IOException {
        this.zzvy = httpURLConnection;
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == -1) {
            responseCode = 0;
        }
        this.responseCode = responseCode;
        this.responseMessage = httpURLConnection.getResponseMessage();
        List list = this.zzvz;
        List list2 = this.zzwa;
        for (Entry entry : httpURLConnection.getHeaderFields().entrySet()) {
            String str = (String) entry.getKey();
            if (str != null) {
                for (String str2 : (List) entry.getValue()) {
                    if (str2 != null) {
                        list.add(str);
                        list2.add(str2);
                    }
                }
            }
        }
    }

    public final int getStatusCode() {
        return this.responseCode;
    }

    public final InputStream getContent() throws IOException {
        InputStream inputStream;
        try {
            inputStream = this.zzvy.getInputStream();
        } catch (IOException unused) {
            inputStream = this.zzvy.getErrorStream();
        }
        if (inputStream == null) {
            return null;
        }
        return new zzfz(this, inputStream);
    }

    public final String getContentEncoding() {
        return this.zzvy.getContentEncoding();
    }

    public final long getContentLength() {
        String headerField = this.zzvy.getHeaderField(HttpHeaders.CONTENT_LENGTH);
        if (headerField == null) {
            return -1;
        }
        return Long.parseLong(headerField);
    }

    public final String getContentType() {
        return this.zzvy.getHeaderField(HttpHeaders.CONTENT_TYPE);
    }

    public final String getReasonPhrase() {
        return this.responseMessage;
    }

    public final String zzfp() {
        String headerField = this.zzvy.getHeaderField(0);
        return (headerField == null || !headerField.startsWith("HTTP/1.")) ? null : headerField;
    }

    public final int zzfq() {
        return this.zzvz.size();
    }

    public final String zzab(int i) {
        return (String) this.zzvz.get(i);
    }

    public final String zzac(int i) {
        return (String) this.zzwa.get(i);
    }

    public final void disconnect() {
        this.zzvy.disconnect();
    }
}
