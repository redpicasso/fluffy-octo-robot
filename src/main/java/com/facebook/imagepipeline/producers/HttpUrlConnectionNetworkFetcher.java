package com.facebook.imagepipeline.producers;

import android.net.Uri;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.time.MonotonicClock;
import com.facebook.common.time.RealtimeSinceBootClock;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.NetworkFetcher.Callback;
import com.google.common.net.HttpHeaders;
import com.google.logging.type.LogSeverity;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HttpUrlConnectionNetworkFetcher extends BaseNetworkFetcher<HttpUrlConnectionNetworkFetchState> {
    private static final String FETCH_TIME = "fetch_time";
    public static final int HTTP_DEFAULT_TIMEOUT = 30000;
    public static final int HTTP_PERMANENT_REDIRECT = 308;
    public static final int HTTP_TEMPORARY_REDIRECT = 307;
    private static final String IMAGE_SIZE = "image_size";
    private static final int MAX_REDIRECTS = 5;
    private static final int NUM_NETWORK_THREADS = 3;
    private static final String QUEUE_TIME = "queue_time";
    private static final String TOTAL_TIME = "total_time";
    private final ExecutorService mExecutorService;
    private int mHttpConnectionTimeout;
    private final MonotonicClock mMonotonicClock;

    public static class HttpUrlConnectionNetworkFetchState extends FetchState {
        private long fetchCompleteTime;
        private long responseTime;
        private long submitTime;

        public HttpUrlConnectionNetworkFetchState(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
            super(consumer, producerContext);
        }
    }

    private static boolean isHttpRedirect(int i) {
        if (!(i == 307 || i == 308)) {
            switch (i) {
                case 300:
                case ExifDirectoryBase.TAG_TRANSFER_FUNCTION /*301*/:
                case 302:
                case 303:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    private static boolean isHttpSuccess(int i) {
        return i >= LogSeverity.INFO_VALUE && i < 300;
    }

    public HttpUrlConnectionNetworkFetcher() {
        this(RealtimeSinceBootClock.get());
    }

    public HttpUrlConnectionNetworkFetcher(int i) {
        this(RealtimeSinceBootClock.get());
        this.mHttpConnectionTimeout = i;
    }

    @VisibleForTesting
    HttpUrlConnectionNetworkFetcher(MonotonicClock monotonicClock) {
        this.mExecutorService = Executors.newFixedThreadPool(3);
        this.mMonotonicClock = monotonicClock;
    }

    public HttpUrlConnectionNetworkFetchState createFetchState(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        return new HttpUrlConnectionNetworkFetchState(consumer, producerContext);
    }

    public void fetch(final HttpUrlConnectionNetworkFetchState httpUrlConnectionNetworkFetchState, final Callback callback) {
        httpUrlConnectionNetworkFetchState.submitTime = this.mMonotonicClock.now();
        final Future submit = this.mExecutorService.submit(new Runnable() {
            public void run() {
                HttpUrlConnectionNetworkFetcher.this.fetchSync(httpUrlConnectionNetworkFetchState, callback);
            }
        });
        httpUrlConnectionNetworkFetchState.getContext().addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
                if (submit.cancel(false)) {
                    callback.onCancellation();
                }
            }
        });
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher.fetchSync(com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher$HttpUrlConnectionNetworkFetchState, com.facebook.imagepipeline.producers.NetworkFetcher$Callback):void, dom blocks: []
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:89)
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:51)
        	at java.lang.Iterable.forEach(Unknown Source)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:51)
        	at jadx.core.ProcessClass.process(ProcessClass.java:37)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:293)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:201)
        */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0033 A:{SYNTHETIC, Splitter: B:20:0x0033} */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0041 A:{SYNTHETIC, Splitter: B:27:0x0041} */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0048  */
    @com.facebook.common.internal.VisibleForTesting
    void fetchSync(com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher.HttpUrlConnectionNetworkFetchState r5, com.facebook.imagepipeline.producers.NetworkFetcher.Callback r6) {
        /*
        r4 = this;
        r0 = 0;
        r1 = r5.getUri();	 Catch:{ IOException -> 0x002c, all -> 0x0029 }
        r2 = 5;	 Catch:{ IOException -> 0x002c, all -> 0x0029 }
        r1 = r4.downloadFrom(r1, r2);	 Catch:{ IOException -> 0x002c, all -> 0x0029 }
        r2 = r4.mMonotonicClock;	 Catch:{ IOException -> 0x0027 }
        r2 = r2.now();	 Catch:{ IOException -> 0x0027 }
        r5.responseTime = r2;	 Catch:{ IOException -> 0x0027 }
        if (r1 == 0) goto L_0x001d;	 Catch:{ IOException -> 0x0027 }
    L_0x0015:
        r0 = r1.getInputStream();	 Catch:{ IOException -> 0x0027 }
        r5 = -1;	 Catch:{ IOException -> 0x0027 }
        r6.onResponse(r0, r5);	 Catch:{ IOException -> 0x0027 }
    L_0x001d:
        if (r0 == 0) goto L_0x0024;
    L_0x001f:
        r0.close();	 Catch:{ IOException -> 0x0023 }
        goto L_0x0024;
    L_0x0024:
        if (r1 == 0) goto L_0x003d;
    L_0x0026:
        goto L_0x003a;
    L_0x0027:
        r5 = move-exception;
        goto L_0x002e;
    L_0x0029:
        r5 = move-exception;
        r1 = r0;
        goto L_0x003f;
    L_0x002c:
        r5 = move-exception;
        r1 = r0;
    L_0x002e:
        r6.onFailure(r5);	 Catch:{ all -> 0x003e }
        if (r0 == 0) goto L_0x0038;
    L_0x0033:
        r0.close();	 Catch:{ IOException -> 0x0037 }
        goto L_0x0038;
    L_0x0038:
        if (r1 == 0) goto L_0x003d;
    L_0x003a:
        r1.disconnect();
    L_0x003d:
        return;
    L_0x003e:
        r5 = move-exception;
    L_0x003f:
        if (r0 == 0) goto L_0x0046;
    L_0x0041:
        r0.close();	 Catch:{ IOException -> 0x0045 }
        goto L_0x0046;
    L_0x0046:
        if (r1 == 0) goto L_0x004b;
    L_0x0048:
        r1.disconnect();
    L_0x004b:
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher.fetchSync(com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher$HttpUrlConnectionNetworkFetchState, com.facebook.imagepipeline.producers.NetworkFetcher$Callback):void");
    }

    private HttpURLConnection downloadFrom(Uri uri, int i) throws IOException {
        HttpURLConnection openConnectionTo = openConnectionTo(uri);
        openConnectionTo.setConnectTimeout(this.mHttpConnectionTimeout);
        int responseCode = openConnectionTo.getResponseCode();
        if (isHttpSuccess(responseCode)) {
            return openConnectionTo;
        }
        if (isHttpRedirect(responseCode)) {
            Uri uri2;
            String headerField = openConnectionTo.getHeaderField(HttpHeaders.LOCATION);
            openConnectionTo.disconnect();
            if (headerField == null) {
                uri2 = null;
            } else {
                uri2 = Uri.parse(headerField);
            }
            headerField = uri.getScheme();
            if (i > 0 && uri2 != null && !uri2.getScheme().equals(headerField)) {
                return downloadFrom(uri2, i - 1);
            }
            String error;
            if (i == 0) {
                error = error("URL %s follows too many redirects", uri.toString());
            } else {
                error = error("URL %s returned %d without a valid redirect", uri.toString(), Integer.valueOf(responseCode));
            }
            throw new IOException(error);
        }
        openConnectionTo.disconnect();
        throw new IOException(String.format("Image URL %s returned HTTP code %d", new Object[]{uri.toString(), Integer.valueOf(responseCode)}));
    }

    @VisibleForTesting
    static HttpURLConnection openConnectionTo(Uri uri) throws IOException {
        return (HttpURLConnection) UriUtil.uriToUrl(uri).openConnection();
    }

    public void onFetchCompletion(HttpUrlConnectionNetworkFetchState httpUrlConnectionNetworkFetchState, int i) {
        httpUrlConnectionNetworkFetchState.fetchCompleteTime = this.mMonotonicClock.now();
    }

    private static String error(String str, Object... objArr) {
        return String.format(Locale.getDefault(), str, objArr);
    }

    public Map<String, String> getExtraMap(HttpUrlConnectionNetworkFetchState httpUrlConnectionNetworkFetchState, int i) {
        Map<String, String> hashMap = new HashMap(4);
        hashMap.put(QUEUE_TIME, Long.toString(httpUrlConnectionNetworkFetchState.responseTime - httpUrlConnectionNetworkFetchState.submitTime));
        hashMap.put(FETCH_TIME, Long.toString(httpUrlConnectionNetworkFetchState.fetchCompleteTime - httpUrlConnectionNetworkFetchState.responseTime));
        hashMap.put(TOTAL_TIME, Long.toString(httpUrlConnectionNetworkFetchState.fetchCompleteTime - httpUrlConnectionNetworkFetchState.submitTime));
        hashMap.put(IMAGE_SIZE, Integer.toString(i));
        return hashMap;
    }
}
