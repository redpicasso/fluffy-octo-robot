package com.dylanvann.fastimage;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader.Factory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.LibraryGlideModule;
import com.facebook.react.modules.network.OkHttpClientProvider;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

@GlideModule
public class FastImageOkHttpProgressGlideModule extends LibraryGlideModule {
    private static DispatchingProgressListener progressListener = new DispatchingProgressListener();

    private interface ResponseProgressListener {
        void update(String str, long j, long j2);
    }

    private static class DispatchingProgressListener implements ResponseProgressListener {
        private final Map<String, FastImageProgressListener> LISTENERS = new WeakHashMap();
        private final Map<String, Long> PROGRESSES = new HashMap();
        private final Handler handler = new Handler(Looper.getMainLooper());

        DispatchingProgressListener() {
        }

        void forget(String str) {
            this.LISTENERS.remove(str);
            this.PROGRESSES.remove(str);
        }

        void expect(String str, FastImageProgressListener fastImageProgressListener) {
            this.LISTENERS.put(str, fastImageProgressListener);
        }

        public void update(String str, long j, long j2) {
            String str2 = str;
            FastImageProgressListener fastImageProgressListener = (FastImageProgressListener) this.LISTENERS.get(str);
            if (fastImageProgressListener != null) {
                if (j2 <= j) {
                    forget(str);
                }
                if (needsDispatch(str, j, j2, fastImageProgressListener.getGranularityPercentage())) {
                    final FastImageProgressListener fastImageProgressListener2 = fastImageProgressListener;
                    final String str3 = str;
                    final long j3 = j;
                    final long j4 = j2;
                    this.handler.post(new Runnable() {
                        public void run() {
                            fastImageProgressListener2.onProgress(str3, j3, j4);
                        }
                    });
                }
            }
        }

        private boolean needsDispatch(String str, long j, long j2, float f) {
            if (!(f == 0.0f || j == 0 || j2 == j)) {
                j = (long) (((((float) j) * 100.0f) / ((float) j2)) / f);
                Long l = (Long) this.PROGRESSES.get(str);
                if (l != null && j == l.longValue()) {
                    return false;
                }
                this.PROGRESSES.put(str, Long.valueOf(j));
            }
            return true;
        }
    }

    private static class OkHttpProgressResponseBody extends ResponseBody {
        private BufferedSource bufferedSource;
        private final String key;
        private final ResponseProgressListener progressListener;
        private final ResponseBody responseBody;

        OkHttpProgressResponseBody(String str, ResponseBody responseBody, ResponseProgressListener responseProgressListener) {
            this.key = str;
            this.responseBody = responseBody;
            this.progressListener = responseProgressListener;
        }

        public MediaType contentType() {
            return this.responseBody.contentType();
        }

        public long contentLength() {
            return this.responseBody.contentLength();
        }

        public BufferedSource source() {
            if (this.bufferedSource == null) {
                this.bufferedSource = Okio.buffer(source(this.responseBody.source()));
            }
            return this.bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0;

                public long read(Buffer buffer, long j) throws IOException {
                    long read = super.read(buffer, j);
                    long contentLength = OkHttpProgressResponseBody.this.responseBody.contentLength();
                    if (read == -1) {
                        this.totalBytesRead = contentLength;
                    } else {
                        this.totalBytesRead += read;
                    }
                    OkHttpProgressResponseBody.this.progressListener.update(OkHttpProgressResponseBody.this.key, this.totalBytesRead, contentLength);
                    return read;
                }
            };
        }
    }

    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new Factory(OkHttpClientProvider.getOkHttpClient().newBuilder().addInterceptor(createInterceptor(progressListener)).build()));
    }

    private static Interceptor createInterceptor(final ResponseProgressListener responseProgressListener) {
        return new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response proceed = chain.proceed(request);
                return proceed.newBuilder().body(new OkHttpProgressResponseBody(request.url().toString(), proceed.body(), responseProgressListener)).build();
            }
        };
    }

    static void forget(String str) {
        progressListener.forget(str);
    }

    static void expect(String str, FastImageProgressListener fastImageProgressListener) {
        progressListener.expect(str, fastImageProgressListener);
    }
}
