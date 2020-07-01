package com.google.android.gms.internal.firebase_ml;

import com.google.common.net.HttpHeaders;
import io.grpc.internal.GrpcUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

final class zzfx extends zzfp {
    private final HttpURLConnection zzvy;

    zzfx(HttpURLConnection httpURLConnection) {
        this.zzvy = httpURLConnection;
        httpURLConnection.setInstanceFollowRedirects(false);
    }

    public final void addHeader(String str, String str2) {
        this.zzvy.addRequestProperty(str, str2);
    }

    public final void zza(int i, int i2) {
        this.zzvy.setReadTimeout(i2);
        this.zzvy.setConnectTimeout(i);
    }

    public final zzfq zzfo() throws IOException {
        HttpURLConnection httpURLConnection = this.zzvy;
        if (zzfn() != null) {
            String contentType = getContentType();
            if (contentType != null) {
                addHeader(HttpHeaders.CONTENT_TYPE, contentType);
            }
            contentType = getContentEncoding();
            if (contentType != null) {
                addHeader(HttpHeaders.CONTENT_ENCODING, contentType);
            }
            long contentLength = getContentLength();
            int i = (contentLength > 0 ? 1 : (contentLength == 0 ? 0 : -1));
            if (i >= 0) {
                httpURLConnection.setRequestProperty(HttpHeaders.CONTENT_LENGTH, Long.toString(contentLength));
            }
            String requestMethod = httpURLConnection.getRequestMethod();
            if (GrpcUtil.HTTP_METHOD.equals(requestMethod) || "PUT".equals(requestMethod)) {
                httpURLConnection.setDoOutput(true);
                if (i < 0 || contentLength > 2147483647L) {
                    httpURLConnection.setChunkedStreamingMode(0);
                } else {
                    httpURLConnection.setFixedLengthStreamingMode((int) contentLength);
                }
                OutputStream outputStream = httpURLConnection.getOutputStream();
                try {
                    zzfn().writeTo(outputStream);
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        throw e;
                    }
                } catch (Throwable th) {
                    try {
                        outputStream.close();
                    } catch (IOException unused) {
                        throw th;
                    }
                }
            }
            Object[] objArr = new Object[]{requestMethod};
            if ((i == 0 ? 1 : null) == null) {
                throw new IllegalArgumentException(zzla.zzb("%s with non-zero content length is not supported", objArr));
            }
        }
        try {
            httpURLConnection.connect();
            return new zzfy(httpURLConnection);
        } catch (Throwable th2) {
            httpURLConnection.disconnect();
        }
    }
}
