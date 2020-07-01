package com.facebook.react.modules.network;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

public class ProgressRequestBody extends RequestBody {
    private long mContentLength = 0;
    private final ProgressListener mProgressListener;
    private final RequestBody mRequestBody;

    public ProgressRequestBody(RequestBody requestBody, ProgressListener progressListener) {
        this.mRequestBody = requestBody;
        this.mProgressListener = progressListener;
    }

    public MediaType contentType() {
        return this.mRequestBody.contentType();
    }

    public long contentLength() throws IOException {
        if (this.mContentLength == 0) {
            this.mContentLength = this.mRequestBody.contentLength();
        }
        return this.mContentLength;
    }

    public void writeTo(BufferedSink bufferedSink) throws IOException {
        bufferedSink = Okio.buffer(outputStreamSink(bufferedSink));
        contentLength();
        this.mRequestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    private Sink outputStreamSink(BufferedSink bufferedSink) {
        return Okio.sink(new CountingOutputStream(bufferedSink.outputStream()) {
            public void write(byte[] bArr, int i, int i2) throws IOException {
                super.write(bArr, i, i2);
                sendProgressUpdate();
            }

            public void write(int i) throws IOException {
                super.write(i);
                sendProgressUpdate();
            }

            private void sendProgressUpdate() throws IOException {
                long count = getCount();
                long contentLength = ProgressRequestBody.this.contentLength();
                ProgressRequestBody.this.mProgressListener.onProgress(count, contentLength, count == contentLength);
            }
        });
    }
}