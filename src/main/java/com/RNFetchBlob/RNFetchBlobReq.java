package com.RNFetchBlob;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.os.Build.VERSION;
import android.util.Base64;
import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.RNFetchBlob.Response.RNFetchBlobFileResp;
import com.bumptech.glide.load.Key;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import com.google.common.net.HttpHeaders;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.TlsVersion;

public class RNFetchBlobReq extends BroadcastReceiver implements Runnable {
    public static HashMap<String, Long> androidDownloadManagerTaskTable = new HashMap();
    static ConnectionPool pool = new ConnectionPool();
    static HashMap<String, RNFetchBlobProgressConfig> progressReport = new HashMap();
    public static HashMap<String, Call> taskTable = new HashMap();
    static HashMap<String, RNFetchBlobProgressConfig> uploadProgressReport = new HashMap();
    Callback callback;
    OkHttpClient client;
    long contentLength;
    String destPath;
    long downloadManagerId;
    ReadableMap headers;
    String method;
    RNFetchBlobConfig options;
    String rawRequestBody;
    ReadableArray rawRequestBodyArray;
    ArrayList<String> redirects = new ArrayList();
    RNFetchBlobBody requestBody;
    RequestType requestType;
    WritableMap respInfo;
    ResponseFormat responseFormat = ResponseFormat.Auto;
    ResponseType responseType;
    String taskId;
    boolean timeout = false;
    String url;

    /* renamed from: com.RNFetchBlob.RNFetchBlobReq$4 */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType = new int[RequestType.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$ResponseType = new int[ResponseType.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:15:0x0052, code:
            return;
     */
        static {
            /*
            r0 = com.RNFetchBlob.RNFetchBlobReq.ResponseType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$ResponseType = r0;
            r0 = 1;
            r1 = $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$ResponseType;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = com.RNFetchBlob.RNFetchBlobReq.ResponseType.KeepInMemory;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = r2.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1[r2] = r0;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r1 = 2;
            r2 = $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$ResponseType;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = com.RNFetchBlob.RNFetchBlobReq.ResponseType.FileStorage;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2[r3] = r1;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r2 = com.RNFetchBlob.RNFetchBlobReq.RequestType.values();
            r2 = r2.length;
            r2 = new int[r2];
            $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType = r2;
            r2 = $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType;	 Catch:{ NoSuchFieldError -> 0x0032 }
            r3 = com.RNFetchBlob.RNFetchBlobReq.RequestType.SingleFile;	 Catch:{ NoSuchFieldError -> 0x0032 }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x0032 }
            r2[r3] = r0;	 Catch:{ NoSuchFieldError -> 0x0032 }
        L_0x0032:
            r0 = $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType;	 Catch:{ NoSuchFieldError -> 0x003c }
            r2 = com.RNFetchBlob.RNFetchBlobReq.RequestType.AsIs;	 Catch:{ NoSuchFieldError -> 0x003c }
            r2 = r2.ordinal();	 Catch:{ NoSuchFieldError -> 0x003c }
            r0[r2] = r1;	 Catch:{ NoSuchFieldError -> 0x003c }
        L_0x003c:
            r0 = $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType;	 Catch:{ NoSuchFieldError -> 0x0047 }
            r1 = com.RNFetchBlob.RNFetchBlobReq.RequestType.Form;	 Catch:{ NoSuchFieldError -> 0x0047 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0047 }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0047 }
        L_0x0047:
            r0 = $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType;	 Catch:{ NoSuchFieldError -> 0x0052 }
            r1 = com.RNFetchBlob.RNFetchBlobReq.RequestType.WithoutBody;	 Catch:{ NoSuchFieldError -> 0x0052 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0052 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0052 }
        L_0x0052:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobReq.4.<clinit>():void");
        }
    }

    enum RequestType {
        Form,
        SingleFile,
        AsIs,
        WithoutBody,
        Others
    }

    enum ResponseFormat {
        Auto,
        UTF8,
        BASE64
    }

    enum ResponseType {
        KeepInMemory,
        FileStorage
    }

    public RNFetchBlobReq(ReadableMap readableMap, String str, String str2, String str3, ReadableMap readableMap2, String str4, ReadableArray readableArray, OkHttpClient okHttpClient, Callback callback) {
        this.method = str2.toUpperCase();
        this.options = new RNFetchBlobConfig(readableMap);
        this.taskId = str;
        this.url = str3;
        this.headers = readableMap2;
        this.callback = callback;
        this.rawRequestBody = str4;
        this.rawRequestBodyArray = readableArray;
        this.client = okHttpClient;
        if (this.options.fileCache.booleanValue() || this.options.path != null) {
            this.responseType = ResponseType.FileStorage;
        } else {
            this.responseType = ResponseType.KeepInMemory;
        }
        if (str4 != null) {
            this.requestType = RequestType.SingleFile;
        } else if (readableArray != null) {
            this.requestType = RequestType.Form;
        } else {
            this.requestType = RequestType.WithoutBody;
        }
    }

    public static void cancelTask(String str) {
        if (taskTable.containsKey(str)) {
            ((Call) taskTable.get(str)).cancel();
            taskTable.remove(str);
        }
        if (androidDownloadManagerTaskTable.containsKey(str)) {
            long longValue = ((Long) androidDownloadManagerTaskTable.get(str)).longValue();
            ((DownloadManager) RNFetchBlob.RCTContext.getApplicationContext().getSystemService("download")).remove(new long[]{longValue});
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:151:0x040e A:{Catch:{ Exception -> 0x04a7 }} */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x0358 A:{Catch:{ Exception -> 0x04a7 }} */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x0458 A:{Catch:{ Exception -> 0x04a7 }} */
    public void run() {
        /*
        r15 = this;
        r0 = "Content-Type";
        r1 = r15.options;
        r1 = r1.addAndroidDownloads;
        r2 = "path";
        r3 = 2;
        r4 = 1;
        if (r1 == 0) goto L_0x0118;
    L_0x000c:
        r1 = r15.options;
        r1 = r1.addAndroidDownloads;
        r5 = "useDownloadManager";
        r1 = r1.hasKey(r5);
        if (r1 == 0) goto L_0x0118;
    L_0x0018:
        r1 = r15.options;
        r1 = r1.addAndroidDownloads;
        r1 = r1.getBoolean(r5);
        if (r1 == 0) goto L_0x0118;
    L_0x0022:
        r0 = r15.url;
        r0 = android.net.Uri.parse(r0);
        r1 = new android.app.DownloadManager$Request;
        r1.<init>(r0);
        r0 = r15.options;
        r0 = r0.addAndroidDownloads;
        r5 = "notification";
        r0 = r0.hasKey(r5);
        if (r0 == 0) goto L_0x0047;
    L_0x0039:
        r0 = r15.options;
        r0 = r0.addAndroidDownloads;
        r0 = r0.getBoolean(r5);
        if (r0 == 0) goto L_0x0047;
    L_0x0043:
        r1.setNotificationVisibility(r4);
        goto L_0x004a;
    L_0x0047:
        r1.setNotificationVisibility(r3);
    L_0x004a:
        r0 = r15.options;
        r0 = r0.addAndroidDownloads;
        r3 = "title";
        r0 = r0.hasKey(r3);
        if (r0 == 0) goto L_0x0061;
    L_0x0056:
        r0 = r15.options;
        r0 = r0.addAndroidDownloads;
        r0 = r0.getString(r3);
        r1.setTitle(r0);
    L_0x0061:
        r0 = r15.options;
        r0 = r0.addAndroidDownloads;
        r3 = "description";
        r0 = r0.hasKey(r3);
        if (r0 == 0) goto L_0x0078;
    L_0x006d:
        r0 = r15.options;
        r0 = r0.addAndroidDownloads;
        r0 = r0.getString(r3);
        r1.setDescription(r0);
    L_0x0078:
        r0 = r15.options;
        r0 = r0.addAndroidDownloads;
        r0 = r0.hasKey(r2);
        if (r0 == 0) goto L_0x00a2;
    L_0x0082:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r3 = "file://";
        r0.append(r3);
        r3 = r15.options;
        r3 = r3.addAndroidDownloads;
        r2 = r3.getString(r2);
        r0.append(r2);
        r0 = r0.toString();
        r0 = android.net.Uri.parse(r0);
        r1.setDestinationUri(r0);
    L_0x00a2:
        r0 = r15.options;
        r0 = r0.addAndroidDownloads;
        r2 = "mime";
        r0 = r0.hasKey(r2);
        if (r0 == 0) goto L_0x00b9;
    L_0x00ae:
        r0 = r15.options;
        r0 = r0.addAndroidDownloads;
        r0 = r0.getString(r2);
        r1.setMimeType(r0);
    L_0x00b9:
        r0 = r15.headers;
        r0 = r0.keySetIterator();
        r2 = r15.options;
        r2 = r2.addAndroidDownloads;
        r3 = "mediaScannable";
        r2 = r2.hasKey(r3);
        if (r2 == 0) goto L_0x00d8;
    L_0x00cb:
        r2 = r15.options;
        r2 = r2.addAndroidDownloads;
        r2 = r2.hasKey(r3);
        if (r2 == 0) goto L_0x00d8;
    L_0x00d5:
        r1.allowScanningByMediaScanner();
    L_0x00d8:
        r2 = r0.hasNextKey();
        if (r2 == 0) goto L_0x00ec;
    L_0x00de:
        r2 = r0.nextKey();
        r3 = r15.headers;
        r3 = r3.getString(r2);
        r1.addRequestHeader(r2, r3);
        goto L_0x00d8;
    L_0x00ec:
        r0 = com.RNFetchBlob.RNFetchBlob.RCTContext;
        r0 = r0.getApplicationContext();
        r2 = "download";
        r2 = r0.getSystemService(r2);
        r2 = (android.app.DownloadManager) r2;
        r1 = r2.enqueue(r1);
        r15.downloadManagerId = r1;
        r1 = androidDownloadManagerTaskTable;
        r2 = r15.taskId;
        r3 = r15.downloadManagerId;
        r3 = java.lang.Long.valueOf(r3);
        r1.put(r2, r3);
        r1 = new android.content.IntentFilter;
        r2 = "android.intent.action.DOWNLOAD_COMPLETE";
        r1.<init>(r2);
        r0.registerReceiver(r15, r1);
        return;
    L_0x0118:
        r1 = r15.taskId;
        r5 = r15.options;
        r5 = r5.appendExt;
        r5 = r5.isEmpty();
        r6 = "";
        if (r5 == 0) goto L_0x0128;
    L_0x0126:
        r5 = r6;
        goto L_0x013d;
    L_0x0128:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r7 = ".";
        r5.append(r7);
        r7 = r15.options;
        r7 = r7.appendExt;
        r5.append(r7);
        r5 = r5.toString();
    L_0x013d:
        r7 = r15.options;
        r7 = r7.key;
        r8 = 3;
        r9 = 0;
        r10 = 0;
        if (r7 == 0) goto L_0x0182;
    L_0x0146:
        r1 = r15.options;
        r1 = r1.key;
        r1 = com.RNFetchBlob.RNFetchBlobUtils.getMD5(r1);
        if (r1 != 0) goto L_0x0152;
    L_0x0150:
        r1 = r15.taskId;
    L_0x0152:
        r7 = new java.io.File;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = com.RNFetchBlob.RNFetchBlobFS.getTmpPath(r1);
        r11.append(r12);
        r11.append(r5);
        r11 = r11.toString();
        r7.<init>(r11);
        r11 = r7.exists();
        if (r11 == 0) goto L_0x0182;
    L_0x0170:
        r0 = r15.callback;
        r1 = new java.lang.Object[r8];
        r1[r10] = r9;
        r1[r4] = r2;
        r2 = r7.getAbsolutePath();
        r1[r3] = r2;
        r0.invoke(r1);
        return;
    L_0x0182:
        r2 = r15.options;
        r2 = r2.path;
        if (r2 == 0) goto L_0x018f;
    L_0x0188:
        r1 = r15.options;
        r1 = r1.path;
        r15.destPath = r1;
        goto L_0x01ae;
    L_0x018f:
        r2 = r15.options;
        r2 = r2.fileCache;
        r2 = r2.booleanValue();
        if (r2 == 0) goto L_0x01ae;
    L_0x0199:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r1 = com.RNFetchBlob.RNFetchBlobFS.getTmpPath(r1);
        r2.append(r1);
        r2.append(r5);
        r1 = r2.toString();
        r15.destPath = r1;
    L_0x01ae:
        r1 = r15.options;	 Catch:{ Exception -> 0x04a7 }
        r1 = r1.trusty;	 Catch:{ Exception -> 0x04a7 }
        r1 = r1.booleanValue();	 Catch:{ Exception -> 0x04a7 }
        if (r1 == 0) goto L_0x01bf;
    L_0x01b8:
        r1 = r15.client;	 Catch:{ Exception -> 0x04a7 }
        r1 = com.RNFetchBlob.RNFetchBlobUtils.getUnsafeOkHttpClient(r1);	 Catch:{ Exception -> 0x04a7 }
        goto L_0x01c5;
    L_0x01bf:
        r1 = r15.client;	 Catch:{ Exception -> 0x04a7 }
        r1 = r1.newBuilder();	 Catch:{ Exception -> 0x04a7 }
    L_0x01c5:
        r2 = r15.options;	 Catch:{ Exception -> 0x04a7 }
        r2 = r2.wifiOnly;	 Catch:{ Exception -> 0x04a7 }
        r2 = r2.booleanValue();	 Catch:{ Exception -> 0x04a7 }
        if (r2 == 0) goto L_0x0231;
    L_0x01cf:
        r2 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x04a7 }
        r5 = 21;
        if (r2 < r5) goto L_0x022c;
    L_0x01d5:
        r2 = com.RNFetchBlob.RNFetchBlob.RCTContext;	 Catch:{ Exception -> 0x04a7 }
        r5 = com.RNFetchBlob.RNFetchBlob.RCTContext;	 Catch:{ Exception -> 0x04a7 }
        r5 = "connectivity";
        r2 = r2.getSystemService(r5);	 Catch:{ Exception -> 0x04a7 }
        r2 = (android.net.ConnectivityManager) r2;	 Catch:{ Exception -> 0x04a7 }
        r5 = r2.getAllNetworks();	 Catch:{ Exception -> 0x04a7 }
        r7 = r5.length;	 Catch:{ Exception -> 0x04a7 }
        r11 = 0;
    L_0x01e7:
        if (r11 >= r7) goto L_0x0216;
    L_0x01e9:
        r12 = r5[r11];	 Catch:{ Exception -> 0x04a7 }
        r13 = r2.getNetworkInfo(r12);	 Catch:{ Exception -> 0x04a7 }
        r14 = r2.getNetworkCapabilities(r12);	 Catch:{ Exception -> 0x04a7 }
        if (r14 == 0) goto L_0x0213;
    L_0x01f5:
        if (r13 != 0) goto L_0x01f8;
    L_0x01f7:
        goto L_0x0213;
    L_0x01f8:
        r13 = r13.isConnected();	 Catch:{ Exception -> 0x04a7 }
        if (r13 != 0) goto L_0x01ff;
    L_0x01fe:
        goto L_0x0213;
    L_0x01ff:
        r13 = r14.hasTransport(r4);	 Catch:{ Exception -> 0x04a7 }
        if (r13 == 0) goto L_0x0213;
    L_0x0205:
        r2 = java.net.Proxy.NO_PROXY;	 Catch:{ Exception -> 0x04a7 }
        r1.proxy(r2);	 Catch:{ Exception -> 0x04a7 }
        r2 = r12.getSocketFactory();	 Catch:{ Exception -> 0x04a7 }
        r1.socketFactory(r2);	 Catch:{ Exception -> 0x04a7 }
        r2 = 1;
        goto L_0x0217;
    L_0x0213:
        r11 = r11 + 1;
        goto L_0x01e7;
    L_0x0216:
        r2 = 0;
    L_0x0217:
        if (r2 != 0) goto L_0x0231;
    L_0x0219:
        r0 = r15.callback;	 Catch:{ Exception -> 0x04a7 }
        r1 = new java.lang.Object[r8];	 Catch:{ Exception -> 0x04a7 }
        r2 = "No available WiFi connections.";
        r1[r10] = r2;	 Catch:{ Exception -> 0x04a7 }
        r1[r4] = r9;	 Catch:{ Exception -> 0x04a7 }
        r1[r3] = r9;	 Catch:{ Exception -> 0x04a7 }
        r0.invoke(r1);	 Catch:{ Exception -> 0x04a7 }
        r15.releaseTaskResource();	 Catch:{ Exception -> 0x04a7 }
        return;
    L_0x022c:
        r2 = "RNFetchBlob: wifiOnly was set, but SDK < 21. wifiOnly was ignored.";
        com.RNFetchBlob.RNFetchBlobUtils.emitWarningEvent(r2);	 Catch:{ Exception -> 0x04a7 }
    L_0x0231:
        r2 = new okhttp3.Request$Builder;	 Catch:{ Exception -> 0x04a7 }
        r2.<init>();	 Catch:{ Exception -> 0x04a7 }
        r5 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x0241 }
        r7 = r15.url;	 Catch:{ MalformedURLException -> 0x0241 }
        r5.<init>(r7);	 Catch:{ MalformedURLException -> 0x0241 }
        r2.url(r5);	 Catch:{ MalformedURLException -> 0x0241 }
        goto L_0x0245;
    L_0x0241:
        r5 = move-exception;
        r5.printStackTrace();	 Catch:{ Exception -> 0x04a7 }
    L_0x0245:
        r5 = new java.util.HashMap;	 Catch:{ Exception -> 0x04a7 }
        r5.<init>();	 Catch:{ Exception -> 0x04a7 }
        r7 = r15.headers;	 Catch:{ Exception -> 0x04a7 }
        if (r7 == 0) goto L_0x0295;
    L_0x024e:
        r7 = r15.headers;	 Catch:{ Exception -> 0x04a7 }
        r7 = r7.keySetIterator();	 Catch:{ Exception -> 0x04a7 }
    L_0x0254:
        r11 = r7.hasNextKey();	 Catch:{ Exception -> 0x04a7 }
        if (r11 == 0) goto L_0x0295;
    L_0x025a:
        r11 = r7.nextKey();	 Catch:{ Exception -> 0x04a7 }
        r12 = r15.headers;	 Catch:{ Exception -> 0x04a7 }
        r12 = r12.getString(r11);	 Catch:{ Exception -> 0x04a7 }
        r13 = "RNFB-Response";
        r13 = r11.equalsIgnoreCase(r13);	 Catch:{ Exception -> 0x04a7 }
        if (r13 == 0) goto L_0x0286;
    L_0x026c:
        r11 = "base64";
        r11 = r12.equalsIgnoreCase(r11);	 Catch:{ Exception -> 0x04a7 }
        if (r11 == 0) goto L_0x0279;
    L_0x0274:
        r11 = com.RNFetchBlob.RNFetchBlobReq.ResponseFormat.BASE64;	 Catch:{ Exception -> 0x04a7 }
        r15.responseFormat = r11;	 Catch:{ Exception -> 0x04a7 }
        goto L_0x0254;
    L_0x0279:
        r11 = "utf8";
        r11 = r12.equalsIgnoreCase(r11);	 Catch:{ Exception -> 0x04a7 }
        if (r11 == 0) goto L_0x0254;
    L_0x0281:
        r11 = com.RNFetchBlob.RNFetchBlobReq.ResponseFormat.UTF8;	 Catch:{ Exception -> 0x04a7 }
        r15.responseFormat = r11;	 Catch:{ Exception -> 0x04a7 }
        goto L_0x0254;
    L_0x0286:
        r13 = r11.toLowerCase();	 Catch:{ Exception -> 0x04a7 }
        r2.header(r13, r12);	 Catch:{ Exception -> 0x04a7 }
        r11 = r11.toLowerCase();	 Catch:{ Exception -> 0x04a7 }
        r5.put(r11, r12);	 Catch:{ Exception -> 0x04a7 }
        goto L_0x0254;
    L_0x0295:
        r7 = r15.method;	 Catch:{ Exception -> 0x04a7 }
        r11 = "post";
        r7 = r7.equalsIgnoreCase(r11);	 Catch:{ Exception -> 0x04a7 }
        r11 = "content-type";
        if (r7 != 0) goto L_0x02bc;
    L_0x02a1:
        r7 = r15.method;	 Catch:{ Exception -> 0x04a7 }
        r12 = "put";
        r7 = r7.equalsIgnoreCase(r12);	 Catch:{ Exception -> 0x04a7 }
        if (r7 != 0) goto L_0x02bc;
    L_0x02ab:
        r7 = r15.method;	 Catch:{ Exception -> 0x04a7 }
        r12 = "patch";
        r7 = r7.equalsIgnoreCase(r12);	 Catch:{ Exception -> 0x04a7 }
        if (r7 == 0) goto L_0x02b6;
    L_0x02b5:
        goto L_0x02bc;
    L_0x02b6:
        r0 = com.RNFetchBlob.RNFetchBlobReq.RequestType.WithoutBody;	 Catch:{ Exception -> 0x04a7 }
        r15.requestType = r0;	 Catch:{ Exception -> 0x04a7 }
        goto L_0x0340;
    L_0x02bc:
        r7 = r15.getHeaderIgnoreCases(r5, r0);	 Catch:{ Exception -> 0x04a7 }
        r7 = r7.toLowerCase();	 Catch:{ Exception -> 0x04a7 }
        r12 = r15.rawRequestBodyArray;	 Catch:{ Exception -> 0x04a7 }
        if (r12 == 0) goto L_0x02cd;
    L_0x02c8:
        r12 = com.RNFetchBlob.RNFetchBlobReq.RequestType.Form;	 Catch:{ Exception -> 0x04a7 }
        r15.requestType = r12;	 Catch:{ Exception -> 0x04a7 }
        goto L_0x02e2;
    L_0x02cd:
        r12 = r7.isEmpty();	 Catch:{ Exception -> 0x04a7 }
        if (r12 == 0) goto L_0x02e2;
    L_0x02d3:
        r12 = r7.equalsIgnoreCase(r6);	 Catch:{ Exception -> 0x04a7 }
        if (r12 != 0) goto L_0x02de;
    L_0x02d9:
        r12 = "application/octet-stream";
        r2.header(r0, r12);	 Catch:{ Exception -> 0x04a7 }
    L_0x02de:
        r12 = com.RNFetchBlob.RNFetchBlobReq.RequestType.SingleFile;	 Catch:{ Exception -> 0x04a7 }
        r15.requestType = r12;	 Catch:{ Exception -> 0x04a7 }
    L_0x02e2:
        r12 = r15.rawRequestBody;	 Catch:{ Exception -> 0x04a7 }
        if (r12 == 0) goto L_0x0340;
    L_0x02e6:
        r12 = r15.rawRequestBody;	 Catch:{ Exception -> 0x04a7 }
        r13 = "RNFetchBlob-file://";
        r12 = r12.startsWith(r13);	 Catch:{ Exception -> 0x04a7 }
        if (r12 != 0) goto L_0x033c;
    L_0x02f0:
        r12 = r15.rawRequestBody;	 Catch:{ Exception -> 0x04a7 }
        r13 = "RNFetchBlob-content://";
        r12 = r12.startsWith(r13);	 Catch:{ Exception -> 0x04a7 }
        if (r12 == 0) goto L_0x02fb;
    L_0x02fa:
        goto L_0x033c;
    L_0x02fb:
        r12 = r7.toLowerCase();	 Catch:{ Exception -> 0x04a7 }
        r13 = ";base64";
        r12 = r12.contains(r13);	 Catch:{ Exception -> 0x04a7 }
        if (r12 != 0) goto L_0x0319;
    L_0x0307:
        r12 = r7.toLowerCase();	 Catch:{ Exception -> 0x04a7 }
        r13 = "application/octet";
        r12 = r12.startsWith(r13);	 Catch:{ Exception -> 0x04a7 }
        if (r12 == 0) goto L_0x0314;
    L_0x0313:
        goto L_0x0319;
    L_0x0314:
        r0 = com.RNFetchBlob.RNFetchBlobReq.RequestType.AsIs;	 Catch:{ Exception -> 0x04a7 }
        r15.requestType = r0;	 Catch:{ Exception -> 0x04a7 }
        goto L_0x0340;
    L_0x0319:
        r12 = ";base64";
        r7 = r7.replace(r12, r6);	 Catch:{ Exception -> 0x04a7 }
        r12 = ";BASE64";
        r6 = r7.replace(r12, r6);	 Catch:{ Exception -> 0x04a7 }
        r7 = r5.containsKey(r11);	 Catch:{ Exception -> 0x04a7 }
        if (r7 == 0) goto L_0x032e;
    L_0x032b:
        r5.put(r11, r6);	 Catch:{ Exception -> 0x04a7 }
    L_0x032e:
        r7 = r5.containsKey(r0);	 Catch:{ Exception -> 0x04a7 }
        if (r7 == 0) goto L_0x0337;
    L_0x0334:
        r5.put(r0, r6);	 Catch:{ Exception -> 0x04a7 }
    L_0x0337:
        r0 = com.RNFetchBlob.RNFetchBlobReq.RequestType.SingleFile;	 Catch:{ Exception -> 0x04a7 }
        r15.requestType = r0;	 Catch:{ Exception -> 0x04a7 }
        goto L_0x0340;
    L_0x033c:
        r0 = com.RNFetchBlob.RNFetchBlobReq.RequestType.SingleFile;	 Catch:{ Exception -> 0x04a7 }
        r15.requestType = r0;	 Catch:{ Exception -> 0x04a7 }
    L_0x0340:
        r0 = "Transfer-Encoding";
        r0 = r15.getHeaderIgnoreCases(r5, r0);	 Catch:{ Exception -> 0x04a7 }
        r6 = "chunked";
        r0 = r0.equalsIgnoreCase(r6);	 Catch:{ Exception -> 0x04a7 }
        r6 = com.RNFetchBlob.RNFetchBlobReq.AnonymousClass4.$SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType;	 Catch:{ Exception -> 0x04a7 }
        r7 = r15.requestType;	 Catch:{ Exception -> 0x04a7 }
        r7 = r7.ordinal();	 Catch:{ Exception -> 0x04a7 }
        r6 = r6[r7];	 Catch:{ Exception -> 0x04a7 }
        if (r6 == r4) goto L_0x040e;
    L_0x0358:
        if (r6 == r3) goto L_0x03e1;
    L_0x035a:
        if (r6 == r8) goto L_0x0394;
    L_0x035c:
        r0 = 4;
        if (r6 == r0) goto L_0x0361;
    L_0x035f:
        goto L_0x043a;
    L_0x0361:
        r0 = r15.method;	 Catch:{ Exception -> 0x04a7 }
        r3 = "post";
        r0 = r0.equalsIgnoreCase(r3);	 Catch:{ Exception -> 0x04a7 }
        if (r0 != 0) goto L_0x0387;
    L_0x036b:
        r0 = r15.method;	 Catch:{ Exception -> 0x04a7 }
        r3 = "put";
        r0 = r0.equalsIgnoreCase(r3);	 Catch:{ Exception -> 0x04a7 }
        if (r0 != 0) goto L_0x0387;
    L_0x0375:
        r0 = r15.method;	 Catch:{ Exception -> 0x04a7 }
        r3 = "patch";
        r0 = r0.equalsIgnoreCase(r3);	 Catch:{ Exception -> 0x04a7 }
        if (r0 == 0) goto L_0x0380;
    L_0x037f:
        goto L_0x0387;
    L_0x0380:
        r0 = r15.method;	 Catch:{ Exception -> 0x04a7 }
        r2.method(r0, r9);	 Catch:{ Exception -> 0x04a7 }
        goto L_0x043a;
    L_0x0387:
        r0 = r15.method;	 Catch:{ Exception -> 0x04a7 }
        r3 = new byte[r10];	 Catch:{ Exception -> 0x04a7 }
        r3 = okhttp3.RequestBody.create(r9, r3);	 Catch:{ Exception -> 0x04a7 }
        r2.method(r0, r3);	 Catch:{ Exception -> 0x04a7 }
        goto L_0x043a;
    L_0x0394:
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x04a7 }
        r3.<init>();	 Catch:{ Exception -> 0x04a7 }
        r5 = "RNFetchBlob-";
        r3.append(r5);	 Catch:{ Exception -> 0x04a7 }
        r5 = r15.taskId;	 Catch:{ Exception -> 0x04a7 }
        r3.append(r5);	 Catch:{ Exception -> 0x04a7 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x04a7 }
        r5 = new com.RNFetchBlob.RNFetchBlobBody;	 Catch:{ Exception -> 0x04a7 }
        r6 = r15.taskId;	 Catch:{ Exception -> 0x04a7 }
        r5.<init>(r6);	 Catch:{ Exception -> 0x04a7 }
        r0 = r5.chunkedEncoding(r0);	 Catch:{ Exception -> 0x04a7 }
        r5 = r15.requestType;	 Catch:{ Exception -> 0x04a7 }
        r0 = r0.setRequestType(r5);	 Catch:{ Exception -> 0x04a7 }
        r5 = r15.rawRequestBodyArray;	 Catch:{ Exception -> 0x04a7 }
        r0 = r0.setBody(r5);	 Catch:{ Exception -> 0x04a7 }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x04a7 }
        r5.<init>();	 Catch:{ Exception -> 0x04a7 }
        r6 = "multipart/form-data; boundary=";
        r5.append(r6);	 Catch:{ Exception -> 0x04a7 }
        r5.append(r3);	 Catch:{ Exception -> 0x04a7 }
        r3 = r5.toString();	 Catch:{ Exception -> 0x04a7 }
        r3 = okhttp3.MediaType.parse(r3);	 Catch:{ Exception -> 0x04a7 }
        r0 = r0.setMIME(r3);	 Catch:{ Exception -> 0x04a7 }
        r15.requestBody = r0;	 Catch:{ Exception -> 0x04a7 }
        r0 = r15.method;	 Catch:{ Exception -> 0x04a7 }
        r3 = r15.requestBody;	 Catch:{ Exception -> 0x04a7 }
        r2.method(r0, r3);	 Catch:{ Exception -> 0x04a7 }
        goto L_0x043a;
    L_0x03e1:
        r3 = new com.RNFetchBlob.RNFetchBlobBody;	 Catch:{ Exception -> 0x04a7 }
        r6 = r15.taskId;	 Catch:{ Exception -> 0x04a7 }
        r3.<init>(r6);	 Catch:{ Exception -> 0x04a7 }
        r0 = r3.chunkedEncoding(r0);	 Catch:{ Exception -> 0x04a7 }
        r3 = r15.requestType;	 Catch:{ Exception -> 0x04a7 }
        r0 = r0.setRequestType(r3);	 Catch:{ Exception -> 0x04a7 }
        r3 = r15.rawRequestBody;	 Catch:{ Exception -> 0x04a7 }
        r0 = r0.setBody(r3);	 Catch:{ Exception -> 0x04a7 }
        r3 = r15.getHeaderIgnoreCases(r5, r11);	 Catch:{ Exception -> 0x04a7 }
        r3 = okhttp3.MediaType.parse(r3);	 Catch:{ Exception -> 0x04a7 }
        r0 = r0.setMIME(r3);	 Catch:{ Exception -> 0x04a7 }
        r15.requestBody = r0;	 Catch:{ Exception -> 0x04a7 }
        r0 = r15.method;	 Catch:{ Exception -> 0x04a7 }
        r3 = r15.requestBody;	 Catch:{ Exception -> 0x04a7 }
        r2.method(r0, r3);	 Catch:{ Exception -> 0x04a7 }
        goto L_0x043a;
    L_0x040e:
        r3 = new com.RNFetchBlob.RNFetchBlobBody;	 Catch:{ Exception -> 0x04a7 }
        r6 = r15.taskId;	 Catch:{ Exception -> 0x04a7 }
        r3.<init>(r6);	 Catch:{ Exception -> 0x04a7 }
        r0 = r3.chunkedEncoding(r0);	 Catch:{ Exception -> 0x04a7 }
        r3 = r15.requestType;	 Catch:{ Exception -> 0x04a7 }
        r0 = r0.setRequestType(r3);	 Catch:{ Exception -> 0x04a7 }
        r3 = r15.rawRequestBody;	 Catch:{ Exception -> 0x04a7 }
        r0 = r0.setBody(r3);	 Catch:{ Exception -> 0x04a7 }
        r3 = r15.getHeaderIgnoreCases(r5, r11);	 Catch:{ Exception -> 0x04a7 }
        r3 = okhttp3.MediaType.parse(r3);	 Catch:{ Exception -> 0x04a7 }
        r0 = r0.setMIME(r3);	 Catch:{ Exception -> 0x04a7 }
        r15.requestBody = r0;	 Catch:{ Exception -> 0x04a7 }
        r0 = r15.method;	 Catch:{ Exception -> 0x04a7 }
        r3 = r15.requestBody;	 Catch:{ Exception -> 0x04a7 }
        r2.method(r0, r3);	 Catch:{ Exception -> 0x04a7 }
    L_0x043a:
        r0 = r2.build();	 Catch:{ Exception -> 0x04a7 }
        r2 = new com.RNFetchBlob.RNFetchBlobReq$1;	 Catch:{ Exception -> 0x04a7 }
        r2.<init>();	 Catch:{ Exception -> 0x04a7 }
        r1.addNetworkInterceptor(r2);	 Catch:{ Exception -> 0x04a7 }
        r2 = new com.RNFetchBlob.RNFetchBlobReq$2;	 Catch:{ Exception -> 0x04a7 }
        r2.<init>(r0);	 Catch:{ Exception -> 0x04a7 }
        r1.addInterceptor(r2);	 Catch:{ Exception -> 0x04a7 }
        r2 = r15.options;	 Catch:{ Exception -> 0x04a7 }
        r2 = r2.timeout;	 Catch:{ Exception -> 0x04a7 }
        r5 = 0;
        r7 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1));
        if (r7 < 0) goto L_0x046a;
    L_0x0458:
        r2 = r15.options;	 Catch:{ Exception -> 0x04a7 }
        r2 = r2.timeout;	 Catch:{ Exception -> 0x04a7 }
        r5 = java.util.concurrent.TimeUnit.MILLISECONDS;	 Catch:{ Exception -> 0x04a7 }
        r1.connectTimeout(r2, r5);	 Catch:{ Exception -> 0x04a7 }
        r2 = r15.options;	 Catch:{ Exception -> 0x04a7 }
        r2 = r2.timeout;	 Catch:{ Exception -> 0x04a7 }
        r5 = java.util.concurrent.TimeUnit.MILLISECONDS;	 Catch:{ Exception -> 0x04a7 }
        r1.readTimeout(r2, r5);	 Catch:{ Exception -> 0x04a7 }
    L_0x046a:
        r2 = pool;	 Catch:{ Exception -> 0x04a7 }
        r1.connectionPool(r2);	 Catch:{ Exception -> 0x04a7 }
        r1.retryOnConnectionFailure(r10);	 Catch:{ Exception -> 0x04a7 }
        r2 = r15.options;	 Catch:{ Exception -> 0x04a7 }
        r2 = r2.followRedirect;	 Catch:{ Exception -> 0x04a7 }
        r2 = r2.booleanValue();	 Catch:{ Exception -> 0x04a7 }
        r1.followRedirects(r2);	 Catch:{ Exception -> 0x04a7 }
        r2 = r15.options;	 Catch:{ Exception -> 0x04a7 }
        r2 = r2.followRedirect;	 Catch:{ Exception -> 0x04a7 }
        r2 = r2.booleanValue();	 Catch:{ Exception -> 0x04a7 }
        r1.followSslRedirects(r2);	 Catch:{ Exception -> 0x04a7 }
        r1.retryOnConnectionFailure(r4);	 Catch:{ Exception -> 0x04a7 }
        r1 = enableTls12OnPreLollipop(r1);	 Catch:{ Exception -> 0x04a7 }
        r1 = r1.build();	 Catch:{ Exception -> 0x04a7 }
        r0 = r1.newCall(r0);	 Catch:{ Exception -> 0x04a7 }
        r1 = taskTable;	 Catch:{ Exception -> 0x04a7 }
        r2 = r15.taskId;	 Catch:{ Exception -> 0x04a7 }
        r1.put(r2, r0);	 Catch:{ Exception -> 0x04a7 }
        r1 = new com.RNFetchBlob.RNFetchBlobReq$3;	 Catch:{ Exception -> 0x04a7 }
        r1.<init>();	 Catch:{ Exception -> 0x04a7 }
        r0.enqueue(r1);	 Catch:{ Exception -> 0x04a7 }
        goto L_0x04d3;
    L_0x04a7:
        r0 = move-exception;
        r0.printStackTrace();
        r15.releaseTaskResource();
        r1 = r15.callback;
        r2 = new java.lang.Object[r4];
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "RNFetchBlob request error: ";
        r3.append(r4);
        r4 = r0.getMessage();
        r3.append(r4);
        r0 = r0.getCause();
        r3.append(r0);
        r0 = r3.toString();
        r2[r10] = r0;
        r1.invoke(r2);
    L_0x04d3:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobReq.run():void");
    }

    private void releaseTaskResource() {
        if (taskTable.containsKey(this.taskId)) {
            taskTable.remove(this.taskId);
        }
        if (androidDownloadManagerTaskTable.containsKey(this.taskId)) {
            androidDownloadManagerTaskTable.remove(this.taskId);
        }
        if (uploadProgressReport.containsKey(this.taskId)) {
            uploadProgressReport.remove(this.taskId);
        }
        if (progressReport.containsKey(this.taskId)) {
            progressReport.remove(this.taskId);
        }
        RNFetchBlobBody rNFetchBlobBody = this.requestBody;
        if (rNFetchBlobBody != null) {
            rNFetchBlobBody.clearRequestBody();
        }
    }

    private void done(Response response) {
        boolean isBlobResponse = isBlobResponse(response);
        emitStateEvent(getResponseInfo(response, isBlobResponse));
        int i = AnonymousClass4.$SwitchMap$com$RNFetchBlob$RNFetchBlobReq$ResponseType[this.responseType.ordinal()];
        String str = RNFetchBlobConst.RNFB_RESPONSE_PATH;
        String str2 = Key.STRING_CHARSET_NAME;
        String str3 = RNFetchBlobConst.RNFB_RESPONSE_UTF8;
        if (i == 1) {
            if (isBlobResponse) {
                try {
                    if (this.options.auto.booleanValue()) {
                        String tmpPath = RNFetchBlobFS.getTmpPath(this.taskId);
                        InputStream byteStream = response.body().byteStream();
                        FileOutputStream fileOutputStream = new FileOutputStream(new File(tmpPath));
                        byte[] bArr = new byte[10240];
                        while (true) {
                            int read = byteStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                        byteStream.close();
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        this.callback.invoke(null, str, tmpPath);
                    }
                } catch (IOException unused) {
                    this.callback.invoke("RNFetchBlob failed to encode response data to BASE64 string.", null);
                }
            }
            byte[] bytes = response.body().bytes();
            CharsetEncoder newEncoder = Charset.forName(str2).newEncoder();
            ResponseFormat responseFormat = this.responseFormat;
            ResponseFormat responseFormat2 = ResponseFormat.BASE64;
            String str4 = RNFetchBlobConst.RNFB_RESPONSE_BASE64;
            if (responseFormat == responseFormat2) {
                this.callback.invoke(null, str4, Base64.encodeToString(bytes, 2));
                return;
            }
            newEncoder.encode(ByteBuffer.wrap(bytes).asCharBuffer());
            String str5 = new String(bytes);
            this.callback.invoke(null, str3, str5);
            try {
            } catch (CharacterCodingException unused2) {
                if (this.responseFormat == ResponseFormat.UTF8) {
                    str5 = new String(bytes);
                    this.callback.invoke(null, str3, str5);
                } else {
                    this.callback.invoke(null, str4, Base64.encodeToString(bytes, 2));
                }
            }
        } else if (i != 2) {
            try {
                this.callback.invoke(null, str3, new String(response.body().bytes(), str2));
            } catch (IOException unused3) {
                this.callback.invoke("RNFetchBlob failed to encode response data to UTF8 string.", null);
            }
        } else {
            ResponseBody body = response.body();
            try {
                body.bytes();
            } catch (Exception unused4) {
                RNFetchBlobFileResp rNFetchBlobFileResp = (RNFetchBlobFileResp) body;
                if (rNFetchBlobFileResp == null || rNFetchBlobFileResp.isDownloadComplete()) {
                    this.destPath = this.destPath.replace("?append=true", "");
                    this.callback.invoke(null, str, this.destPath);
                } else {
                    this.callback.invoke("Download interrupted.", null);
                }
            }
        }
        response.body().close();
        releaseTaskResource();
    }

    public static RNFetchBlobProgressConfig getReportProgress(String str) {
        if (progressReport.containsKey(str)) {
            return (RNFetchBlobProgressConfig) progressReport.get(str);
        }
        return null;
    }

    public static RNFetchBlobProgressConfig getReportUploadProgress(String str) {
        if (uploadProgressReport.containsKey(str)) {
            return (RNFetchBlobProgressConfig) uploadProgressReport.get(str);
        }
        return null;
    }

    private WritableMap getResponseInfo(Response response, boolean z) {
        WritableMap createMap = Arguments.createMap();
        createMap.putInt(NotificationCompat.CATEGORY_STATUS, response.code());
        createMap.putString("state", ExifInterface.GPS_MEASUREMENT_2D);
        createMap.putString("taskId", this.taskId);
        createMap.putBoolean("timeout", this.timeout);
        WritableMap createMap2 = Arguments.createMap();
        for (int i = 0; i < response.headers().size(); i++) {
            createMap2.putString(response.headers().name(i), response.headers().value(i));
        }
        WritableArray createArray = Arguments.createArray();
        Iterator it = this.redirects.iterator();
        while (it.hasNext()) {
            createArray.pushString((String) it.next());
        }
        createMap.putArray("redirects", createArray);
        createMap.putMap("headers", createMap2);
        Headers headers = response.headers();
        String str = "respType";
        if (z) {
            createMap.putString(str, "blob");
        } else {
            String str2 = "content-type";
            if (getHeaderIgnoreCases(headers, str2).equalsIgnoreCase("text/")) {
                createMap.putString(str, "text");
            } else if (getHeaderIgnoreCases(headers, str2).contains("application/json")) {
                createMap.putString(str, "json");
            } else {
                createMap.putString(str, "");
            }
        }
        return createMap;
    }

    private boolean isBlobResponse(Response response) {
        Object obj;
        String headerIgnoreCases = getHeaderIgnoreCases(response.headers(), HttpHeaders.CONTENT_TYPE);
        int equalsIgnoreCase = headerIgnoreCases.equalsIgnoreCase("text/") ^ 1;
        int equalsIgnoreCase2 = headerIgnoreCases.equalsIgnoreCase("application/json") ^ 1;
        if (this.options.binaryContentTypes != null) {
            for (int i = 0; i < this.options.binaryContentTypes.size(); i++) {
                if (headerIgnoreCases.toLowerCase().contains(this.options.binaryContentTypes.getString(i).toLowerCase())) {
                    obj = 1;
                    break;
                }
            }
        }
        obj = null;
        if ((equalsIgnoreCase2 != 0 || equalsIgnoreCase != 0) && obj == null) {
            return false;
        }
        return true;
    }

    private String getHeaderIgnoreCases(Headers headers, String str) {
        String str2 = headers.get(str);
        if (str2 != null) {
            return str2;
        }
        return headers.get(str.toLowerCase()) == null ? "" : headers.get(str.toLowerCase());
    }

    private String getHeaderIgnoreCases(HashMap<String, String> hashMap, String str) {
        String str2 = (String) hashMap.get(str);
        if (str2 != null) {
            return str2;
        }
        String str3 = (String) hashMap.get(str.toLowerCase());
        if (str3 == null) {
            str3 = "";
        }
        return str3;
    }

    private void emitStateEvent(WritableMap writableMap) {
        ((RCTDeviceEventEmitter) RNFetchBlob.RCTContext.getJSModule(RCTDeviceEventEmitter.class)).emit(RNFetchBlobConst.EVENT_HTTP_STATE, writableMap);
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x00fc  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0148  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x010b A:{SYNTHETIC, Splitter: B:30:0x010b} */
    public void onReceive(android.content.Context r14, android.content.Intent r15) {
        /*
        r13 = this;
        r14 = "mime";
        r0 = r15.getAction();
        r1 = "android.intent.action.DOWNLOAD_COMPLETE";
        r0 = r1.equals(r0);
        if (r0 == 0) goto L_0x016f;
    L_0x000e:
        r0 = com.RNFetchBlob.RNFetchBlob.RCTContext;
        r0 = r0.getApplicationContext();
        r15 = r15.getExtras();
        r1 = "extra_download_id";
        r1 = r15.getLong(r1);
        r3 = r13.downloadManagerId;
        r15 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1));
        if (r15 != 0) goto L_0x016f;
    L_0x0024:
        r13.releaseTaskResource();
        r15 = new android.app.DownloadManager$Query;
        r15.<init>();
        r1 = 1;
        r2 = new long[r1];
        r3 = r13.downloadManagerId;
        r5 = 0;
        r2[r5] = r3;
        r15.setFilterById(r2);
        r2 = "download";
        r2 = r0.getSystemService(r2);
        r2 = (android.app.DownloadManager) r2;
        r2.query(r15);
        r15 = r2.query(r15);
        r2 = "Download manager failed to download from  ";
        r3 = 3;
        r4 = 2;
        r6 = 0;
        if (r15 != 0) goto L_0x0071;
    L_0x004d:
        r14 = r13.callback;
        r15 = new java.lang.Object[r3];
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r0.append(r2);
        r2 = r13.url;
        r0.append(r2);
        r2 = ". Query was unsuccessful ";
        r0.append(r2);
        r0 = r0.toString();
        r15[r5] = r0;
        r15[r1] = r6;
        r15[r4] = r6;
        r14.invoke(r15);
        return;
    L_0x0071:
        r7 = r15.moveToFirst();	 Catch:{ all -> 0x0168 }
        if (r7 == 0) goto L_0x00f9;
    L_0x0077:
        r7 = "status";
        r7 = r15.getColumnIndex(r7);	 Catch:{ all -> 0x0168 }
        r7 = r15.getInt(r7);	 Catch:{ all -> 0x0168 }
        r8 = 16;
        if (r7 != r8) goto L_0x00b1;
    L_0x0085:
        r14 = r13.callback;	 Catch:{ all -> 0x0168 }
        r0 = new java.lang.Object[r3];	 Catch:{ all -> 0x0168 }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0168 }
        r3.<init>();	 Catch:{ all -> 0x0168 }
        r3.append(r2);	 Catch:{ all -> 0x0168 }
        r2 = r13.url;	 Catch:{ all -> 0x0168 }
        r3.append(r2);	 Catch:{ all -> 0x0168 }
        r2 = ". Status Code = ";
        r3.append(r2);	 Catch:{ all -> 0x0168 }
        r3.append(r7);	 Catch:{ all -> 0x0168 }
        r2 = r3.toString();	 Catch:{ all -> 0x0168 }
        r0[r5] = r2;	 Catch:{ all -> 0x0168 }
        r0[r1] = r6;	 Catch:{ all -> 0x0168 }
        r0[r4] = r6;	 Catch:{ all -> 0x0168 }
        r14.invoke(r0);	 Catch:{ all -> 0x0168 }
        if (r15 == 0) goto L_0x00b0;
    L_0x00ad:
        r15.close();
    L_0x00b0:
        return;
    L_0x00b1:
        r2 = "local_uri";
        r2 = r15.getColumnIndex(r2);	 Catch:{ all -> 0x0168 }
        r2 = r15.getString(r2);	 Catch:{ all -> 0x0168 }
        if (r2 == 0) goto L_0x00f9;
    L_0x00bd:
        r7 = r13.options;	 Catch:{ all -> 0x0168 }
        r7 = r7.addAndroidDownloads;	 Catch:{ all -> 0x0168 }
        r7 = r7.hasKey(r14);	 Catch:{ all -> 0x0168 }
        if (r7 == 0) goto L_0x00f9;
    L_0x00c7:
        r7 = r13.options;	 Catch:{ all -> 0x0168 }
        r7 = r7.addAndroidDownloads;	 Catch:{ all -> 0x0168 }
        r14 = r7.getString(r14);	 Catch:{ all -> 0x0168 }
        r7 = "image";
        r14 = r14.contains(r7);	 Catch:{ all -> 0x0168 }
        if (r14 == 0) goto L_0x00f9;
    L_0x00d7:
        r8 = android.net.Uri.parse(r2);	 Catch:{ all -> 0x0168 }
        r7 = r0.getContentResolver();	 Catch:{ all -> 0x0168 }
        r14 = "_data";
        r9 = new java.lang.String[]{r14};	 Catch:{ all -> 0x0168 }
        r10 = 0;
        r11 = 0;
        r12 = 0;
        r14 = r7.query(r8, r9, r10, r11, r12);	 Catch:{ all -> 0x0168 }
        if (r14 == 0) goto L_0x00f9;
    L_0x00ee:
        r14.moveToFirst();	 Catch:{ all -> 0x0168 }
        r0 = r14.getString(r5);	 Catch:{ all -> 0x0168 }
        r14.close();	 Catch:{ all -> 0x0168 }
        goto L_0x00fa;
    L_0x00f9:
        r0 = r6;
    L_0x00fa:
        if (r15 == 0) goto L_0x00ff;
    L_0x00fc:
        r15.close();
    L_0x00ff:
        r14 = r13.options;
        r14 = r14.addAndroidDownloads;
        r15 = "path";
        r14 = r14.hasKey(r15);
        if (r14 == 0) goto L_0x0148;
    L_0x010b:
        r14 = r13.options;	 Catch:{ Exception -> 0x0134 }
        r14 = r14.addAndroidDownloads;	 Catch:{ Exception -> 0x0134 }
        r14 = r14.getString(r15);	 Catch:{ Exception -> 0x0134 }
        r0 = new java.io.File;	 Catch:{ Exception -> 0x0134 }
        r0.<init>(r14);	 Catch:{ Exception -> 0x0134 }
        r0 = r0.exists();	 Catch:{ Exception -> 0x0134 }
        if (r0 == 0) goto L_0x012c;
    L_0x011e:
        r0 = r13.callback;	 Catch:{ Exception -> 0x0134 }
        r2 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x0134 }
        r2[r5] = r6;	 Catch:{ Exception -> 0x0134 }
        r2[r1] = r15;	 Catch:{ Exception -> 0x0134 }
        r2[r4] = r14;	 Catch:{ Exception -> 0x0134 }
        r0.invoke(r2);	 Catch:{ Exception -> 0x0134 }
        goto L_0x016f;
    L_0x012c:
        r14 = new java.lang.Exception;	 Catch:{ Exception -> 0x0134 }
        r15 = "Download manager download failed, the file does not downloaded to destination.";
        r14.<init>(r15);	 Catch:{ Exception -> 0x0134 }
        throw r14;	 Catch:{ Exception -> 0x0134 }
    L_0x0134:
        r14 = move-exception;
        r14.printStackTrace();
        r15 = r13.callback;
        r0 = new java.lang.Object[r4];
        r14 = r14.getLocalizedMessage();
        r0[r5] = r14;
        r0[r1] = r6;
        r15.invoke(r0);
        goto L_0x016f;
    L_0x0148:
        if (r0 != 0) goto L_0x015a;
    L_0x014a:
        r14 = r13.callback;
        r0 = new java.lang.Object[r3];
        r2 = "Download manager could not resolve downloaded file path.";
        r0[r5] = r2;
        r0[r1] = r15;
        r0[r4] = r6;
        r14.invoke(r0);
        goto L_0x016f;
    L_0x015a:
        r14 = r13.callback;
        r2 = new java.lang.Object[r3];
        r2[r5] = r6;
        r2[r1] = r15;
        r2[r4] = r0;
        r14.invoke(r2);
        goto L_0x016f;
    L_0x0168:
        r14 = move-exception;
        if (r15 == 0) goto L_0x016e;
    L_0x016b:
        r15.close();
    L_0x016e:
        throw r14;
    L_0x016f:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobReq.onReceive(android.content.Context, android.content.Intent):void");
    }

    public static Builder enableTls12OnPreLollipop(Builder builder) {
        if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT <= 19) {
            try {
                TrustManagerFactory instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                instance.init((KeyStore) null);
                TrustManager[] trustManagers = instance.getTrustManagers();
                if (trustManagers.length == 1 && (trustManagers[0] instanceof X509TrustManager)) {
                    X509TrustManager x509TrustManager = (X509TrustManager) trustManagers[0];
                    SSLContext instance2 = SSLContext.getInstance("SSL");
                    instance2.init(null, new TrustManager[]{x509TrustManager}, null);
                    builder.sslSocketFactory(instance2.getSocketFactory(), x509TrustManager);
                    ConnectionSpec build = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_2).build();
                    List arrayList = new ArrayList();
                    arrayList.add(build);
                    arrayList.add(ConnectionSpec.COMPATIBLE_TLS);
                    arrayList.add(ConnectionSpec.CLEARTEXT);
                    builder.connectionSpecs(arrayList);
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected default trust managers:");
                    stringBuilder.append(Arrays.toString(trustManagers));
                    throw new IllegalStateException(stringBuilder.toString());
                }
            } catch (Throwable e) {
                FLog.e("OkHttpClientProvider", "Error while enabling TLS 1.2", e);
            }
        }
        return builder;
    }
}
