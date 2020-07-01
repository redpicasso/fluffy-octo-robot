package com.airbnb.lottie.network;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.core.util.Pair;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieResult;
import com.airbnb.lottie.utils.Logger;
import com.google.logging.type.LogSeverity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipInputStream;

public class NetworkFetcher {
    private final Context appContext;
    private final NetworkCache networkCache;
    private final String url;

    public static LottieResult<LottieComposition> fetchSync(Context context, String str) {
        return new NetworkFetcher(context, str).fetchSync();
    }

    private NetworkFetcher(Context context, String str) {
        this.appContext = context.getApplicationContext();
        this.url = str;
        this.networkCache = new NetworkCache(this.appContext, str);
    }

    @WorkerThread
    public LottieResult<LottieComposition> fetchSync() {
        Object fetchFromCache = fetchFromCache();
        if (fetchFromCache != null) {
            return new LottieResult(fetchFromCache);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Animation for ");
        stringBuilder.append(this.url);
        stringBuilder.append(" not found in cache. Fetching from network.");
        Logger.debug(stringBuilder.toString());
        return fetchFromNetwork();
    }

    @WorkerThread
    @Nullable
    private LottieComposition fetchFromCache() {
        Pair fetch = this.networkCache.fetch();
        if (fetch == null) {
            return null;
        }
        LottieResult fromZipStreamSync;
        FileExtension fileExtension = (FileExtension) fetch.first;
        InputStream inputStream = (InputStream) fetch.second;
        if (fileExtension == FileExtension.ZIP) {
            fromZipStreamSync = LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(inputStream), this.url);
        } else {
            fromZipStreamSync = LottieCompositionFactory.fromJsonInputStreamSync(inputStream, this.url);
        }
        if (fromZipStreamSync.getValue() != null) {
            return (LottieComposition) fromZipStreamSync.getValue();
        }
        return null;
    }

    @WorkerThread
    private LottieResult<LottieComposition> fetchFromNetwork() {
        try {
            return fetchFromNetworkInternal();
        } catch (Throwable e) {
            return new LottieResult(e);
        }
    }

    @WorkerThread
    private LottieResult fetchFromNetworkInternal() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fetching ");
        stringBuilder.append(this.url);
        Logger.debug(stringBuilder.toString());
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.url).openConnection();
        httpURLConnection.setRequestMethod("GET");
        LottieResult lottieResult;
        LottieResult e;
        try {
            httpURLConnection.connect();
            if (httpURLConnection.getErrorStream() == null) {
                int responseCode = httpURLConnection.getResponseCode();
                lottieResult = LogSeverity.INFO_VALUE;
                if (responseCode == LogSeverity.INFO_VALUE) {
                    e = getResultFromConnection(httpURLConnection);
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Completed fetch from network. Success: ");
                    stringBuilder2.append(e.getValue() != null);
                    lottieResult = stringBuilder2.toString();
                    Logger.debug(lottieResult);
                    return e;
                }
            }
            String errorFromConnection = getErrorFromConnection(httpURLConnection);
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Unable to fetch ");
            stringBuilder3.append(this.url);
            stringBuilder3.append(". Failed with ");
            stringBuilder3.append(httpURLConnection.getResponseCode());
            stringBuilder3.append(ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE);
            stringBuilder3.append(errorFromConnection);
            lottieResult = new LottieResult(new IllegalArgumentException(stringBuilder3.toString()));
            httpURLConnection.disconnect();
            return lottieResult;
        } catch (Exception e2) {
            e = e2;
            lottieResult = new LottieResult((Throwable) e);
            return lottieResult;
        } finally {
            httpURLConnection.disconnect();
        }
    }

    private String getErrorFromConnection(HttpURLConnection httpURLConnection) throws IOException {
        httpURLConnection.getResponseCode();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    stringBuilder.append(readLine);
                    stringBuilder.append(10);
                } else {
                    try {
                        break;
                    } catch (Exception unused) {
                        return stringBuilder.toString();
                    }
                }
            } catch (Exception e) {
                throw e;
            } catch (Throwable th) {
                try {
                    bufferedReader.close();
                } catch (Exception unused2) {
                    throw th;
                }
            }
        }
        bufferedReader.close();
    }

    @Nullable
    private LottieResult<LottieComposition> getResultFromConnection(HttpURLConnection httpURLConnection) throws IOException {
        FileExtension fileExtension;
        LottieResult<LottieComposition> fromJsonInputStreamSync;
        String contentType = httpURLConnection.getContentType();
        String str = "application/json";
        if (contentType == null) {
            contentType = str;
        }
        Object obj = -1;
        int hashCode = contentType.hashCode();
        if (hashCode != -1248325150) {
            if (hashCode == -43840953 && contentType.equals(str)) {
                obj = 1;
            }
        } else if (contentType.equals("application/zip")) {
            obj = null;
        }
        if (obj != null) {
            Logger.debug("Received json response.");
            fileExtension = FileExtension.JSON;
            fromJsonInputStreamSync = LottieCompositionFactory.fromJsonInputStreamSync(new FileInputStream(new File(this.networkCache.writeTempCacheFile(httpURLConnection.getInputStream(), fileExtension).getAbsolutePath())), this.url);
        } else {
            Logger.debug("Handling zip response.");
            fileExtension = FileExtension.ZIP;
            fromJsonInputStreamSync = LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(new FileInputStream(this.networkCache.writeTempCacheFile(httpURLConnection.getInputStream(), fileExtension))), this.url);
        }
        if (fromJsonInputStreamSync.getValue() != null) {
            this.networkCache.renameTempFile(fileExtension);
        }
        return fromJsonInputStreamSync;
    }
}
