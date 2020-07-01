package com.facebook.react.devsupport;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.util.Pair;
import androidx.core.app.NotificationCompat;
import androidx.core.os.EnvironmentCompat;
import com.facebook.cache.disk.DefaultDiskStorage.FileType;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.NativeDeltaClient;
import com.facebook.react.common.DebugServerException;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.devsupport.BundleDeltaClient.ClientType;
import com.facebook.react.devsupport.MultipartStreamReader.ChunkListener;
import com.facebook.react.devsupport.interfaces.DevBundleDownloadListener;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.common.net.HttpHeaders;
import com.google.logging.type.LogSeverity;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;
import org.json.JSONException;
import org.json.JSONObject;

public class BundleDownloader {
    private static final int FILES_CHANGED_COUNT_NOT_BUILT_BY_BUNDLER = -2;
    private static final String TAG = "BundleDownloader";
    private BundleDeltaClient mBundleDeltaClient;
    private final OkHttpClient mClient;
    @Nullable
    private Call mDownloadBundleFromURLCall;

    public static class BundleInfo {
        @Nullable
        private String mDeltaClientName;
        private int mFilesChangedCount;
        @Nullable
        private String mUrl;

        @Nullable
        public static BundleInfo fromJSONString(String str) {
            if (str == null) {
                return null;
            }
            BundleInfo bundleInfo = new BundleInfo();
            try {
                JSONObject jSONObject = new JSONObject(str);
                bundleInfo.mDeltaClientName = jSONObject.getString("deltaClient");
                bundleInfo.mUrl = jSONObject.getString(ImagesContract.URL);
                bundleInfo.mFilesChangedCount = jSONObject.getInt("filesChangedCount");
                return bundleInfo;
            } catch (Throwable e) {
                Log.e(BundleDownloader.TAG, "Invalid bundle info: ", e);
                return null;
            }
        }

        @Nullable
        public String toJSONString() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("deltaClient", this.mDeltaClientName);
                jSONObject.put(ImagesContract.URL, this.mUrl);
                jSONObject.put("filesChangedCount", this.mFilesChangedCount);
                return jSONObject.toString();
            } catch (Throwable e) {
                Log.e(BundleDownloader.TAG, "Can't serialize bundle info: ", e);
                return null;
            }
        }

        @Nullable
        public String getDeltaClient() {
            return this.mDeltaClientName;
        }

        public String getUrl() {
            String str = this.mUrl;
            return str != null ? str : EnvironmentCompat.MEDIA_UNKNOWN;
        }

        public int getFilesChangedCount() {
            return this.mFilesChangedCount;
        }
    }

    public BundleDownloader(OkHttpClient okHttpClient) {
        this.mClient = okHttpClient;
    }

    public void downloadBundleFromURL(DevBundleDownloadListener devBundleDownloadListener, File file, String str, @Nullable BundleInfo bundleInfo, ClientType clientType) {
        downloadBundleFromURL(devBundleDownloadListener, file, str, bundleInfo, clientType, new Builder());
    }

    public void downloadBundleFromURL(DevBundleDownloadListener devBundleDownloadListener, File file, String str, @Nullable BundleInfo bundleInfo, ClientType clientType, Builder builder) {
        this.mDownloadBundleFromURLCall = (Call) Assertions.assertNotNull(this.mClient.newCall(builder.url(formatBundleUrl(str, clientType)).build()));
        final DevBundleDownloadListener devBundleDownloadListener2 = devBundleDownloadListener;
        final File file2 = file;
        final BundleInfo bundleInfo2 = bundleInfo;
        final ClientType clientType2 = clientType;
        this.mDownloadBundleFromURLCall.enqueue(new Callback() {
            public void onFailure(Call call, IOException iOException) {
                if (BundleDownloader.this.mDownloadBundleFromURLCall == null || BundleDownloader.this.mDownloadBundleFromURLCall.isCanceled()) {
                    BundleDownloader.this.mDownloadBundleFromURLCall = null;
                    return;
                }
                BundleDownloader.this.mDownloadBundleFromURLCall = null;
                String httpUrl = call.request().url().toString();
                DevBundleDownloadListener devBundleDownloadListener = devBundleDownloadListener2;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("URL: ");
                stringBuilder.append(httpUrl);
                devBundleDownloadListener.onFailure(DebugServerException.makeGeneric(httpUrl, "Could not connect to development server.", stringBuilder.toString(), iOException));
            }

            public void onResponse(Call call, Response response) throws IOException {
                Throwable th;
                if (BundleDownloader.this.mDownloadBundleFromURLCall == null || BundleDownloader.this.mDownloadBundleFromURLCall.isCanceled()) {
                    BundleDownloader.this.mDownloadBundleFromURLCall = null;
                    return;
                }
                BundleDownloader.this.mDownloadBundleFromURLCall = null;
                String httpUrl = response.request().url().toString();
                Matcher matcher = Pattern.compile("multipart/mixed;.*boundary=\"([^\"]+)\"").matcher(response.header("content-type"));
                try {
                    if (matcher.find()) {
                        BundleDownloader.this.processMultipartResponse(httpUrl, response, matcher.group(1), file2, bundleInfo2, clientType2, devBundleDownloadListener2);
                    } else {
                        BundleDownloader.this.processBundleResult(httpUrl, response.code(), response.headers(), Okio.buffer(response.body().source()), file2, bundleInfo2, clientType2, devBundleDownloadListener2);
                    }
                    if (response != null) {
                        response.close();
                    }
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
        });
    }

    private String formatBundleUrl(String str, ClientType clientType) {
        if (!BundleDeltaClient.isDeltaUrl(str)) {
            return str;
        }
        BundleDeltaClient bundleDeltaClient = this.mBundleDeltaClient;
        return (bundleDeltaClient == null || !bundleDeltaClient.canHandle(clientType)) ? str : this.mBundleDeltaClient.extendUrlForDelta(str);
    }

    private void processMultipartResponse(String str, Response response, String str2, File file, @Nullable BundleInfo bundleInfo, ClientType clientType, DevBundleDownloadListener devBundleDownloadListener) throws IOException {
        String str3 = str2;
        final Response response2 = response;
        final String str4 = str;
        final File file2 = file;
        final BundleInfo bundleInfo2 = bundleInfo;
        final ClientType clientType2 = clientType;
        final DevBundleDownloadListener devBundleDownloadListener2 = devBundleDownloadListener;
        if (!new MultipartStreamReader(response.body().source(), str2).readAllParts(new ChunkListener() {
            public void onChunkComplete(Map<String, String> map, Buffer buffer, boolean z) throws IOException {
                String str = "total";
                String str2 = "done";
                String str3 = NotificationCompat.CATEGORY_STATUS;
                if (z) {
                    int code = response2.code();
                    str = "X-Http-Status";
                    if (map.containsKey(str)) {
                        code = Integer.parseInt((String) map.get(str));
                    }
                    BundleDownloader.this.processBundleResult(str4, code, Headers.of((Map) map), buffer, file2, bundleInfo2, clientType2, devBundleDownloadListener2);
                    return;
                }
                String str4 = HttpHeaders.CONTENT_TYPE;
                if (map.containsKey(str4) && ((String) map.get(str4)).equals("application/json")) {
                    try {
                        JSONObject jSONObject = new JSONObject(buffer.readUtf8());
                        Integer num = null;
                        String string = jSONObject.has(str3) ? jSONObject.getString(str3) : null;
                        Integer valueOf = jSONObject.has(str2) ? Integer.valueOf(jSONObject.getInt(str2)) : null;
                        if (jSONObject.has(str)) {
                            num = Integer.valueOf(jSONObject.getInt(str));
                        }
                        devBundleDownloadListener2.onProgress(string, valueOf, num);
                    } catch (JSONException e) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Error parsing progress JSON. ");
                        stringBuilder.append(e.toString());
                        FLog.e(ReactConstants.TAG, stringBuilder.toString());
                    }
                }
            }

            public void onChunkProgress(Map<String, String> map, long j, long j2) throws IOException {
                if ("application/javascript".equals(map.get(HttpHeaders.CONTENT_TYPE))) {
                    devBundleDownloadListener2.onProgress("Downloading JavaScript bundle", Integer.valueOf((int) (j / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)), Integer.valueOf((int) (j2 / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)));
                }
            }
        })) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error while reading multipart response.\n\nResponse code: ");
            stringBuilder.append(response.code());
            stringBuilder.append("\n\nURL: ");
            stringBuilder.append(str.toString());
            stringBuilder.append("\n\n");
            devBundleDownloadListener.onFailure(new DebugServerException(stringBuilder.toString()));
        }
    }

    private void processBundleResult(String str, int i, Headers headers, BufferedSource bufferedSource, File file, BundleInfo bundleInfo, ClientType clientType, DevBundleDownloadListener devBundleDownloadListener) throws IOException {
        if (i != LogSeverity.INFO_VALUE) {
            String readUtf8 = bufferedSource.readUtf8();
            Exception parse = DebugServerException.parse(str, readUtf8);
            if (parse != null) {
                devBundleDownloadListener.onFailure(parse);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("The development server returned response error code: ");
                stringBuilder.append(i);
                String str2 = "\n\n";
                stringBuilder.append(str2);
                stringBuilder.append("URL: ");
                stringBuilder.append(str);
                stringBuilder.append(str2);
                stringBuilder.append("Body:\n");
                stringBuilder.append(readUtf8);
                devBundleDownloadListener.onFailure(new DebugServerException(stringBuilder.toString()));
            }
            return;
        }
        boolean booleanValue;
        if (bundleInfo != null) {
            populateBundleInfo(str, headers, clientType, bundleInfo);
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(file.getPath());
        stringBuilder2.append(FileType.TEMP);
        File file2 = new File(stringBuilder2.toString());
        NativeDeltaClient nativeDeltaClient = null;
        if (BundleDeltaClient.isDeltaUrl(str)) {
            BundleDeltaClient bundleDeltaClient = getBundleDeltaClient(clientType);
            Assertions.assertNotNull(bundleDeltaClient);
            Pair processDelta = bundleDeltaClient.processDelta(headers, bufferedSource, file2);
            booleanValue = ((Boolean) processDelta.first).booleanValue();
            nativeDeltaClient = (NativeDeltaClient) processDelta.second;
        } else {
            this.mBundleDeltaClient = null;
            booleanValue = storePlainJSInFile(bufferedSource, file2);
        }
        if (!booleanValue || file2.renameTo(file)) {
            devBundleDownloadListener.onSuccess(nativeDeltaClient);
            return;
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("Couldn't rename ");
        stringBuilder3.append(file2);
        stringBuilder3.append(" to ");
        stringBuilder3.append(file);
        throw new IOException(stringBuilder3.toString());
    }

    private BundleDeltaClient getBundleDeltaClient(ClientType clientType) {
        BundleDeltaClient bundleDeltaClient = this.mBundleDeltaClient;
        if (bundleDeltaClient == null || !bundleDeltaClient.canHandle(clientType)) {
            this.mBundleDeltaClient = BundleDeltaClient.create(clientType);
        }
        return this.mBundleDeltaClient;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0014  */
    private static boolean storePlainJSInFile(okio.BufferedSource r0, java.io.File r1) throws java.io.IOException {
        /*
        r1 = okio.Okio.sink(r1);	 Catch:{ all -> 0x0010 }
        r0.readAll(r1);	 Catch:{ all -> 0x000e }
        if (r1 == 0) goto L_0x000c;
    L_0x0009:
        r1.close();
    L_0x000c:
        r0 = 1;
        return r0;
    L_0x000e:
        r0 = move-exception;
        goto L_0x0012;
    L_0x0010:
        r0 = move-exception;
        r1 = 0;
    L_0x0012:
        if (r1 == 0) goto L_0x0017;
    L_0x0014:
        r1.close();
    L_0x0017:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.devsupport.BundleDownloader.storePlainJSInFile(okio.BufferedSource, java.io.File):boolean");
    }

    private static void populateBundleInfo(String str, Headers headers, ClientType clientType, BundleInfo bundleInfo) {
        bundleInfo.mDeltaClientName = clientType == ClientType.NONE ? null : clientType.name();
        bundleInfo.mUrl = str;
        str = headers.get("X-Metro-Files-Changed-Count");
        if (str != null) {
            try {
                bundleInfo.mFilesChangedCount = Integer.parseInt(str);
            } catch (NumberFormatException unused) {
                bundleInfo.mFilesChangedCount = -2;
            }
        }
    }
}
