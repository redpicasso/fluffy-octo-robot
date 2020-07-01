package com.facebook.react.modules.network;

import android.net.Uri;
import android.util.Base64;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.StandardCharsets;
import com.facebook.react.common.network.OkHttpCallUtil;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.ByteString;
import okio.GzipSource;
import okio.Okio;
import okio.Source;

@ReactModule(name = "Networking")
public final class NetworkingModule extends ReactContextBaseJavaModule {
    private static final int CHUNK_TIMEOUT_NS = 100000000;
    private static final String CONTENT_ENCODING_HEADER_NAME = "content-encoding";
    private static final String CONTENT_TYPE_HEADER_NAME = "content-type";
    private static final int MAX_CHUNK_SIZE_BETWEEN_FLUSHES = 8192;
    protected static final String NAME = "Networking";
    private static final String REQUEST_BODY_KEY_BASE64 = "base64";
    private static final String REQUEST_BODY_KEY_FORMDATA = "formData";
    private static final String REQUEST_BODY_KEY_STRING = "string";
    private static final String REQUEST_BODY_KEY_URI = "uri";
    private static final String TAG = "NetworkingModule";
    private static final String USER_AGENT_HEADER_NAME = "user-agent";
    @Nullable
    private static CustomClientBuilder customClientBuilder;
    private final OkHttpClient mClient;
    private final ForwardingCookieHandler mCookieHandler;
    private final CookieJarContainer mCookieJarContainer;
    @Nullable
    private final String mDefaultUserAgent;
    private final List<RequestBodyHandler> mRequestBodyHandlers;
    private final Set<Integer> mRequestIds;
    private final List<ResponseHandler> mResponseHandlers;
    private boolean mShuttingDown;
    private final List<UriHandler> mUriHandlers;

    public interface CustomClientBuilder {
        void apply(Builder builder);
    }

    public interface RequestBodyHandler {
        boolean supports(ReadableMap readableMap);

        RequestBody toRequestBody(ReadableMap readableMap, String str);
    }

    public interface ResponseHandler {
        boolean supports(String str);

        WritableMap toResponseData(ResponseBody responseBody) throws IOException;
    }

    public interface UriHandler {
        WritableMap fetch(Uri uri) throws IOException;

        boolean supports(Uri uri, String str);
    }

    private static boolean shouldDispatch(long j, long j2) {
        return j2 + 100000000 < j;
    }

    public String getName() {
        return NAME;
    }

    NetworkingModule(ReactApplicationContext reactApplicationContext, @Nullable String str, OkHttpClient okHttpClient, @Nullable List<NetworkInterceptorCreator> list) {
        super(reactApplicationContext);
        this.mRequestBodyHandlers = new ArrayList();
        this.mUriHandlers = new ArrayList();
        this.mResponseHandlers = new ArrayList();
        if (list != null) {
            Builder newBuilder = okHttpClient.newBuilder();
            for (NetworkInterceptorCreator create : list) {
                newBuilder.addNetworkInterceptor(create.create());
            }
            okHttpClient = newBuilder.build();
        }
        this.mClient = okHttpClient;
        this.mCookieHandler = new ForwardingCookieHandler(reactApplicationContext);
        this.mCookieJarContainer = (CookieJarContainer) this.mClient.cookieJar();
        this.mShuttingDown = false;
        this.mDefaultUserAgent = str;
        this.mRequestIds = new HashSet();
    }

    NetworkingModule(ReactApplicationContext reactApplicationContext, @Nullable String str, OkHttpClient okHttpClient) {
        this(reactApplicationContext, str, okHttpClient, null);
    }

    public NetworkingModule(ReactApplicationContext reactApplicationContext) {
        this(reactApplicationContext, null, OkHttpClientProvider.createClient(reactApplicationContext), null);
    }

    public NetworkingModule(ReactApplicationContext reactApplicationContext, List<NetworkInterceptorCreator> list) {
        this(reactApplicationContext, null, OkHttpClientProvider.createClient(reactApplicationContext), list);
    }

    public NetworkingModule(ReactApplicationContext reactApplicationContext, String str) {
        this(reactApplicationContext, str, OkHttpClientProvider.createClient(reactApplicationContext), null);
    }

    public static void setCustomClientBuilder(CustomClientBuilder customClientBuilder) {
        customClientBuilder = customClientBuilder;
    }

    private static void applyCustomBuilder(Builder builder) {
        CustomClientBuilder customClientBuilder = customClientBuilder;
        if (customClientBuilder != null) {
            customClientBuilder.apply(builder);
        }
    }

    public void initialize() {
        this.mCookieJarContainer.setCookieJar(new JavaNetCookieJar(this.mCookieHandler));
    }

    public void onCatalystInstanceDestroy() {
        this.mShuttingDown = true;
        cancelAllRequests();
        this.mCookieHandler.destroy();
        this.mCookieJarContainer.removeCookieJar();
        this.mRequestBodyHandlers.clear();
        this.mResponseHandlers.clear();
        this.mUriHandlers.clear();
    }

    public void addUriHandler(UriHandler uriHandler) {
        this.mUriHandlers.add(uriHandler);
    }

    public void addRequestBodyHandler(RequestBodyHandler requestBodyHandler) {
        this.mRequestBodyHandlers.add(requestBodyHandler);
    }

    public void addResponseHandler(ResponseHandler responseHandler) {
        this.mResponseHandlers.add(responseHandler);
    }

    public void removeUriHandler(UriHandler uriHandler) {
        this.mUriHandlers.remove(uriHandler);
    }

    public void removeRequestBodyHandler(RequestBodyHandler requestBodyHandler) {
        this.mRequestBodyHandlers.remove(requestBodyHandler);
    }

    public void removeResponseHandler(ResponseHandler responseHandler) {
        this.mResponseHandlers.remove(responseHandler);
    }

    @ReactMethod
    public void sendRequest(String str, String str2, int i, ReadableArray readableArray, ReadableMap readableMap, String str3, boolean z, int i2, boolean z2) {
        try {
            sendRequestInternal(str, str2, i, readableArray, readableMap, str3, z, i2, z2);
        } catch (Throwable th) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to send url request: ");
            stringBuilder.append(str2);
            FLog.e(TAG, stringBuilder.toString(), th);
            ResponseUtil.onRequestError(getEventEmitter(), i, th.getMessage(), th);
        }
    }

    public void sendRequestInternal(String str, String str2, final int i, ReadableArray readableArray, ReadableMap readableMap, final String str3, boolean z, int i2, boolean z2) {
        final RCTDeviceEventEmitter eventEmitter = getEventEmitter();
        try {
            Uri parse = Uri.parse(str2);
            for (UriHandler uriHandler : this.mUriHandlers) {
                if (uriHandler.supports(parse, str3)) {
                    ResponseUtil.onDataReceived(eventEmitter, i, uriHandler.fetch(parse));
                    ResponseUtil.onRequestSuccess(eventEmitter, i);
                    return;
                }
            }
            try {
                Request.Builder url = new Request.Builder().url(str2);
                if (i != 0) {
                    url.tag(Integer.valueOf(i));
                }
                Builder newBuilder = this.mClient.newBuilder();
                applyCustomBuilder(newBuilder);
                if (!z2) {
                    newBuilder.cookieJar(CookieJar.NO_COOKIES);
                }
                if (z) {
                    newBuilder.addNetworkInterceptor(new Interceptor() {
                        public Response intercept(Chain chain) throws IOException {
                            Response proceed = chain.proceed(chain.request());
                            return proceed.newBuilder().body(new ProgressResponseBody(proceed.body(), new ProgressListener() {
                                long last = System.nanoTime();

                                public void onProgress(long j, long j2, boolean z) {
                                    long nanoTime = System.nanoTime();
                                    if ((z || NetworkingModule.shouldDispatch(nanoTime, this.last)) && !str3.equals("text")) {
                                        ResponseUtil.onDataReceivedProgress(eventEmitter, i, j, j2);
                                        this.last = nanoTime;
                                    }
                                }
                            })).build();
                        }
                    });
                }
                if (i2 != this.mClient.connectTimeoutMillis()) {
                    newBuilder.connectTimeout((long) i2, TimeUnit.MILLISECONDS);
                }
                OkHttpClient build = newBuilder.build();
                Headers extractHeaders = extractHeaders(readableArray, readableMap);
                if (extractHeaders == null) {
                    ResponseUtil.onRequestError(eventEmitter, i, "Unrecognized headers format", null);
                    return;
                }
                RequestBody emptyBody;
                String str4;
                String str5 = extractHeaders.get(CONTENT_TYPE_HEADER_NAME);
                String str6 = extractHeaders.get("content-encoding");
                url.headers(extractHeaders);
                if (readableMap != null) {
                    for (RequestBodyHandler requestBodyHandler : this.mRequestBodyHandlers) {
                        if (requestBodyHandler.supports(readableMap)) {
                            break;
                        }
                    }
                }
                RequestBodyHandler requestBodyHandler2 = null;
                if (readableMap == null || str.toLowerCase().equals("get") || str.toLowerCase().equals("head")) {
                    emptyBody = RequestBodyUtil.getEmptyBody(str);
                } else if (requestBodyHandler2 != null) {
                    emptyBody = requestBodyHandler2.toRequestBody(readableMap, str5);
                } else {
                    String str7 = REQUEST_BODY_KEY_STRING;
                    str4 = "Payload is set but no content-type header specified";
                    if (!readableMap.hasKey(str7)) {
                        str7 = "base64";
                        if (!readableMap.hasKey(str7)) {
                            str7 = "uri";
                            if (!readableMap.hasKey(str7)) {
                                str7 = REQUEST_BODY_KEY_FORMDATA;
                                if (readableMap.hasKey(str7)) {
                                    if (str5 == null) {
                                        str5 = "multipart/form-data";
                                    }
                                    MultipartBody.Builder constructMultipartBody = constructMultipartBody(readableMap.getArray(str7), str5, i);
                                    if (constructMultipartBody != null) {
                                        emptyBody = constructMultipartBody.build();
                                    } else {
                                        return;
                                    }
                                }
                                emptyBody = RequestBodyUtil.getEmptyBody(str);
                            } else if (str5 == null) {
                                ResponseUtil.onRequestError(eventEmitter, i, str4, null);
                                return;
                            } else {
                                str7 = readableMap.getString(str7);
                                InputStream fileInputStream = RequestBodyUtil.getFileInputStream(access$200(), str7);
                                if (fileInputStream == null) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("Could not retrieve file for uri ");
                                    stringBuilder.append(str7);
                                    ResponseUtil.onRequestError(eventEmitter, i, stringBuilder.toString(), null);
                                    return;
                                }
                                emptyBody = RequestBodyUtil.create(MediaType.parse(str5), fileInputStream);
                            }
                        } else if (str5 == null) {
                            ResponseUtil.onRequestError(eventEmitter, i, str4, null);
                            return;
                        } else {
                            emptyBody = RequestBody.create(MediaType.parse(str5), ByteString.decodeBase64(readableMap.getString(str7)));
                        }
                    } else if (str5 == null) {
                        ResponseUtil.onRequestError(eventEmitter, i, str4, null);
                        return;
                    } else {
                        str7 = readableMap.getString(str7);
                        MediaType parse2 = MediaType.parse(str5);
                        if (RequestBodyUtil.isGzipEncoding(str6)) {
                            emptyBody = RequestBodyUtil.createGzip(parse2, str7);
                            if (emptyBody == null) {
                                ResponseUtil.onRequestError(eventEmitter, i, "Failed to gzip request body", null);
                                return;
                            }
                        }
                        Charset charset;
                        if (parse2 == null) {
                            charset = StandardCharsets.UTF_8;
                        } else {
                            charset = parse2.charset(StandardCharsets.UTF_8);
                        }
                        emptyBody = RequestBody.create(parse2, str7.getBytes(charset));
                    }
                }
                url.method(str, wrapRequestBodyWithProgressEmitter(emptyBody, eventEmitter, i));
                addRequest(i);
                final int i3 = i;
                str4 = str3;
                final boolean z3 = z;
                build.newCall(url.build()).enqueue(new Callback() {
                    public void onFailure(Call call, IOException iOException) {
                        if (!NetworkingModule.this.mShuttingDown) {
                            String message;
                            NetworkingModule.this.removeRequest(i3);
                            if (iOException.getMessage() != null) {
                                message = iOException.getMessage();
                            } else {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Error while executing request: ");
                                stringBuilder.append(iOException.getClass().getSimpleName());
                                message = stringBuilder.toString();
                            }
                            ResponseUtil.onRequestError(eventEmitter, i3, message, iOException);
                        }
                    }

                    public void onResponse(Call call, Response response) throws IOException {
                        if (!NetworkingModule.this.mShuttingDown) {
                            NetworkingModule.this.removeRequest(i3);
                            ResponseUtil.onResponseReceived(eventEmitter, i3, response.code(), NetworkingModule.translateHeaders(response.headers()), response.request().url().toString());
                            try {
                                ResponseBody body = response.body();
                                if ("gzip".equalsIgnoreCase(response.header(HttpHeaders.CONTENT_ENCODING)) && body != null) {
                                    Source gzipSource = new GzipSource(body.source());
                                    String header = response.header(HttpHeaders.CONTENT_TYPE);
                                    body = ResponseBody.create(header != null ? MediaType.parse(header) : null, -1, Okio.buffer(gzipSource));
                                }
                                for (ResponseHandler responseHandler : NetworkingModule.this.mResponseHandlers) {
                                    if (responseHandler.supports(str4)) {
                                        ResponseUtil.onDataReceived(eventEmitter, i3, responseHandler.toResponseData(body));
                                        ResponseUtil.onRequestSuccess(eventEmitter, i3);
                                        return;
                                    }
                                }
                                String str = "text";
                                if (z3) {
                                    if (str4.equals(str)) {
                                        NetworkingModule.this.readWithProgress(eventEmitter, i3, body);
                                        ResponseUtil.onRequestSuccess(eventEmitter, i3);
                                        return;
                                    }
                                }
                                String str2 = "";
                                if (str4.equals(str)) {
                                    try {
                                        str2 = body.string();
                                    } catch (Throwable e) {
                                        if (!response.request().method().equalsIgnoreCase("HEAD")) {
                                            ResponseUtil.onRequestError(eventEmitter, i3, e.getMessage(), e);
                                        }
                                    }
                                } else if (str4.equals("base64")) {
                                    str2 = Base64.encodeToString(body.bytes(), 2);
                                }
                                ResponseUtil.onDataReceived(eventEmitter, i3, str2);
                                ResponseUtil.onRequestSuccess(eventEmitter, i3);
                            } catch (Throwable e2) {
                                ResponseUtil.onRequestError(eventEmitter, i3, e2.getMessage(), e2);
                            }
                        }
                    }
                });
            } catch (Exception e) {
                ResponseUtil.onRequestError(eventEmitter, i, e.getMessage(), null);
            }
        } catch (Throwable e2) {
            ResponseUtil.onRequestError(eventEmitter, i, e2.getMessage(), e2);
        }
    }

    private RequestBody wrapRequestBodyWithProgressEmitter(RequestBody requestBody, final RCTDeviceEventEmitter rCTDeviceEventEmitter, final int i) {
        return requestBody == null ? null : RequestBodyUtil.createProgressRequest(requestBody, new ProgressListener() {
            long last = System.nanoTime();

            public void onProgress(long j, long j2, boolean z) {
                long nanoTime = System.nanoTime();
                if (z || NetworkingModule.shouldDispatch(nanoTime, this.last)) {
                    ResponseUtil.onDataSend(rCTDeviceEventEmitter, i, j, j2);
                    this.last = nanoTime;
                }
            }
        });
    }

    private void readWithProgress(RCTDeviceEventEmitter rCTDeviceEventEmitter, int i, ResponseBody responseBody) throws IOException {
        long totalBytesRead;
        InputStream byteStream;
        long j = -1;
        try {
            ProgressResponseBody progressResponseBody = (ProgressResponseBody) responseBody;
            totalBytesRead = progressResponseBody.totalBytesRead();
            try {
                j = progressResponseBody.contentLength();
            } catch (ClassCastException unused) {
                Charset charset;
                if (responseBody.contentType() == null) {
                    charset = StandardCharsets.UTF_8;
                } else {
                    charset = responseBody.contentType().charset(StandardCharsets.UTF_8);
                }
                ProgressiveStringDecoder progressiveStringDecoder = new ProgressiveStringDecoder(charset);
                byteStream = responseBody.byteStream();
                byte[] bArr = new byte[8192];
                while (true) {
                    int read = byteStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    ResponseUtil.onIncrementalDataReceived(rCTDeviceEventEmitter, i, progressiveStringDecoder.decodeNext(bArr, read), totalBytesRead, j);
                }
            } finally {
                byteStream.close();
            }
        } catch (ClassCastException unused2) {
            totalBytesRead = -1;
        }
    }

    private synchronized void addRequest(int i) {
        this.mRequestIds.add(Integer.valueOf(i));
    }

    private synchronized void removeRequest(int i) {
        this.mRequestIds.remove(Integer.valueOf(i));
    }

    private synchronized void cancelAllRequests() {
        for (Integer intValue : this.mRequestIds) {
            cancelRequest(intValue.intValue());
        }
        this.mRequestIds.clear();
    }

    private static WritableMap translateHeaders(Headers headers) {
        WritableMap createMap = Arguments.createMap();
        for (int i = 0; i < headers.size(); i++) {
            String name = headers.name(i);
            if (createMap.hasKey(name)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(createMap.getString(name));
                stringBuilder.append(", ");
                stringBuilder.append(headers.value(i));
                createMap.putString(name, stringBuilder.toString());
            } else {
                createMap.putString(name, headers.value(i));
            }
        }
        return createMap;
    }

    @ReactMethod
    public void abortRequest(int i) {
        cancelRequest(i);
        removeRequest(i);
    }

    private void cancelRequest(final int i) {
        new GuardedAsyncTask<Void, Void>(access$200()) {
            protected void doInBackgroundGuarded(Void... voidArr) {
                OkHttpCallUtil.cancelTag(NetworkingModule.this.mClient, Integer.valueOf(i));
            }
        }.execute(new Void[0]);
    }

    @ReactMethod
    public void clearCookies(com.facebook.react.bridge.Callback callback) {
        this.mCookieHandler.clearCookies(callback);
    }

    @Nullable
    private MultipartBody.Builder constructMultipartBody(ReadableArray readableArray, String str, int i) {
        RCTDeviceEventEmitter eventEmitter = getEventEmitter();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MediaType.parse(str));
        int size = readableArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            ReadableMap map = readableArray.getMap(i2);
            Headers extractHeaders = extractHeaders(map.getArray("headers"), null);
            if (extractHeaders == null) {
                ResponseUtil.onRequestError(eventEmitter, i, "Missing or invalid header format for FormData part.", null);
                return null;
            }
            MediaType parse;
            String str2 = CONTENT_TYPE_HEADER_NAME;
            String str3 = extractHeaders.get(str2);
            if (str3 != null) {
                parse = MediaType.parse(str3);
                extractHeaders = extractHeaders.newBuilder().removeAll(str2).build();
            } else {
                parse = null;
            }
            str2 = REQUEST_BODY_KEY_STRING;
            if (map.hasKey(str2)) {
                builder.addPart(extractHeaders, RequestBody.create(parse, map.getString(str2)));
            } else {
                str2 = "uri";
                if (!map.hasKey(str2)) {
                    ResponseUtil.onRequestError(eventEmitter, i, "Unrecognized FormData part.", null);
                } else if (parse == null) {
                    ResponseUtil.onRequestError(eventEmitter, i, "Binary FormData part needs a content-type header.", null);
                    return null;
                } else {
                    String string = map.getString(str2);
                    InputStream fileInputStream = RequestBodyUtil.getFileInputStream(access$200(), string);
                    if (fileInputStream == null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Could not retrieve file for uri ");
                        stringBuilder.append(string);
                        ResponseUtil.onRequestError(eventEmitter, i, stringBuilder.toString(), null);
                        return null;
                    }
                    builder.addPart(extractHeaders, RequestBodyUtil.create(parse, fileInputStream));
                }
            }
        }
        return builder;
    }

    @Nullable
    private Headers extractHeaders(@Nullable ReadableArray readableArray, @Nullable ReadableMap readableMap) {
        if (readableArray == null) {
            return null;
        }
        Headers.Builder builder = new Headers.Builder();
        int size = readableArray.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            ReadableArray array = readableArray.getArray(i2);
            if (array != null && array.size() == 2) {
                String stripHeaderName = HeaderUtil.stripHeaderName(array.getString(0));
                String stripHeaderValue = HeaderUtil.stripHeaderValue(array.getString(1));
                if (!(stripHeaderName == null || stripHeaderValue == null)) {
                    builder.add(stripHeaderName, stripHeaderValue);
                    i2++;
                }
            }
            return null;
        }
        String str = USER_AGENT_HEADER_NAME;
        if (builder.get(str) == null) {
            String str2 = this.mDefaultUserAgent;
            if (str2 != null) {
                builder.add(str, str2);
            }
        }
        if (readableMap != null && readableMap.hasKey(REQUEST_BODY_KEY_STRING)) {
            i = 1;
        }
        if (i == 0) {
            builder.removeAll("content-encoding");
        }
        return builder.build();
    }

    private RCTDeviceEventEmitter getEventEmitter() {
        return (RCTDeviceEventEmitter) access$200().getJSModule(RCTDeviceEventEmitter.class);
    }
}
