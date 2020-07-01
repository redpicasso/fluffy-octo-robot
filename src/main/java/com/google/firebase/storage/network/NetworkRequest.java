package com.google.firebase.storage.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.common.net.HttpHeaders;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.internal.SlashUtil;
import com.google.firebase.storage.network.connection.HttpURLConnectionFactory;
import com.google.firebase.storage.network.connection.HttpURLConnectionFactoryImpl;
import com.google.logging.type.LogSeverity;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public abstract class NetworkRequest {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    static final String DELETE = "DELETE";
    static final String GET = "GET";
    public static final int INITIALIZATION_EXCEPTION = -1;
    private static final int MAXIMUM_TOKEN_WAIT_TIME_MS = 30000;
    public static final int NETWORK_UNAVAILABLE = -2;
    static final String PATCH = "PATCH";
    static final String POST = "POST";
    static final String PUT = "PUT";
    private static final String TAG = "NetworkRequest";
    private static final String UTF_8 = "UTF-8";
    private static final String X_FIREBASE_GMPID = "x-firebase-gmpid";
    static HttpURLConnectionFactory connectionFactory = new HttpURLConnectionFactoryImpl();
    private static String gmsCoreVersion = null;
    @NonNull
    public static String sNetworkRequestUrl = "https://firebasestorage.googleapis.com/v0";
    @NonNull
    public static String sUploadUrl = "https://firebasestorage.googleapis.com/v0/b/";
    private HttpURLConnection connection;
    private Context context;
    protected Exception mException;
    protected final Uri mGsUri;
    private String rawStringResponse;
    private Map<String, String> requestHeaders = new HashMap();
    private int resultCode;
    private Map<String, List<String>> resultHeaders;
    private InputStream resultInputStream;
    private int resultingContentLength;

    @NonNull
    protected abstract String getAction();

    @Nullable
    protected JSONObject getOutputJSON() {
        return null;
    }

    @Nullable
    protected byte[] getOutputRaw() {
        return null;
    }

    protected int getOutputRawSize() {
        return 0;
    }

    @Nullable
    protected String getQueryParameters() throws UnsupportedEncodingException {
        return null;
    }

    public NetworkRequest(@NonNull Uri uri, @NonNull FirebaseApp firebaseApp) {
        Preconditions.checkNotNull(uri);
        Preconditions.checkNotNull(firebaseApp);
        this.mGsUri = uri;
        this.context = firebaseApp.getApplicationContext();
        setCustomHeader(X_FIREBASE_GMPID, firebaseApp.getOptions().getApplicationId());
    }

    @NonNull
    public static String getAuthority() {
        return Uri.parse(sNetworkRequestUrl).getAuthority();
    }

    @NonNull
    public static String getdefaultURL(@NonNull Uri uri) {
        Preconditions.checkNotNull(uri);
        String pathWithoutBucket = getPathWithoutBucket(uri);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sNetworkRequestUrl);
        stringBuilder.append("/b/");
        stringBuilder.append(uri.getAuthority());
        stringBuilder.append("/o/");
        stringBuilder.append(pathWithoutBucket != null ? SlashUtil.unSlashize(pathWithoutBucket) : "");
        return stringBuilder.toString();
    }

    @Nullable
    public static String getPathWithoutBucket(@NonNull Uri uri) {
        String encodedPath = uri.getEncodedPath();
        return (encodedPath == null || !encodedPath.startsWith("/")) ? encodedPath : encodedPath.substring(1);
    }

    @NonNull
    protected String getURL() {
        return getdefaultURL(this.mGsUri);
    }

    @Nullable
    public String getPathWithoutBucket() {
        return getPathWithoutBucket(this.mGsUri);
    }

    public final void reset() {
        this.mException = null;
        this.resultCode = 0;
    }

    public void setCustomHeader(String str, String str2) {
        this.requestHeaders.put(str, str2);
    }

    public InputStream getStream() {
        return this.resultInputStream;
    }

    public JSONObject getResultBody() {
        if (TextUtils.isEmpty(this.rawStringResponse)) {
            return new JSONObject();
        }
        try {
            return new JSONObject(this.rawStringResponse);
        } catch (Throwable e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("error parsing result into JSON:");
            stringBuilder.append(this.rawStringResponse);
            Log.e(TAG, stringBuilder.toString(), e);
            return new JSONObject();
        }
    }

    public void performRequestStart(String str) {
        if (this.mException != null) {
            this.resultCode = -1;
            return;
        }
        String str2 = TAG;
        String str3 = " ";
        if (Log.isLoggable(str2, 3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sending network request ");
            stringBuilder.append(getAction());
            stringBuilder.append(str3);
            stringBuilder.append(getURL());
            Log.d(str2, stringBuilder.toString());
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            this.resultCode = -2;
            this.mException = new SocketException("Network subsystem is unavailable");
            return;
        }
        try {
            this.connection = createConnection();
            this.connection.setRequestMethod(getAction());
            constructMessage(this.connection, str);
            parseResponse(this.connection);
            if (Log.isLoggable(str2, 3)) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("network request result ");
                stringBuilder2.append(this.resultCode);
                Log.d(str2, stringBuilder2.toString());
            }
        } catch (Throwable e) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("error sending network request ");
            stringBuilder3.append(getAction());
            stringBuilder3.append(str3);
            stringBuilder3.append(getURL());
            Log.w(str2, stringBuilder3.toString(), e);
            this.mException = e;
            this.resultCode = -2;
        }
    }

    public void performRequestEnd() {
        HttpURLConnection httpURLConnection = this.connection;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    private final void performRequest(String str) {
        performRequestStart(str);
        try {
            processResponseStream();
        } catch (Throwable e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("error sending network request ");
            stringBuilder.append(getAction());
            stringBuilder.append(" ");
            stringBuilder.append(getURL());
            Log.w(TAG, stringBuilder.toString(), e);
            this.mException = e;
            this.resultCode = -2;
        }
        performRequestEnd();
    }

    public void performRequest(@Nullable String str, @NonNull Context context) {
        if (ensureNetworkAvailable(context)) {
            performRequest(str);
        }
    }

    private boolean ensureNetworkAvailable(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        this.mException = new SocketException("Network subsystem is unavailable");
        this.resultCode = -2;
        return false;
    }

    private HttpURLConnection createConnection() throws IOException {
        String url;
        Object queryParameters = getQueryParameters();
        if (TextUtils.isEmpty(queryParameters)) {
            url = getURL();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getURL());
            stringBuilder.append("?");
            stringBuilder.append(queryParameters);
            url = stringBuilder.toString();
        }
        return connectionFactory.createInstance(new URL(url));
    }

    @NonNull
    private static String getGmsCoreVersion(Context context) {
        if (gmsCoreVersion == null) {
            try {
                gmsCoreVersion = context.getPackageManager().getPackageInfo("com.google.android.gms", 0).versionName;
            } catch (Throwable e) {
                Log.e(TAG, "Unable to find gmscore in package manager", e);
            }
            if (gmsCoreVersion == null) {
                gmsCoreVersion = "[No Gmscore]";
            }
        }
        return gmsCoreVersion;
    }

    private void constructMessage(@NonNull HttpURLConnection httpURLConnection, String str) throws IOException {
        byte[] bytes;
        int length;
        Preconditions.checkNotNull(httpURLConnection);
        boolean isEmpty = TextUtils.isEmpty(str);
        String str2 = TAG;
        if (isEmpty) {
            Log.w(str2, "no auth token for request");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Firebase ");
            stringBuilder.append(str);
            httpURLConnection.setRequestProperty(HttpHeaders.AUTHORIZATION, stringBuilder.toString());
        }
        StringBuilder stringBuilder2 = new StringBuilder("Android/");
        Object gmsCoreVersion = getGmsCoreVersion(this.context);
        if (!TextUtils.isEmpty(gmsCoreVersion)) {
            stringBuilder2.append(gmsCoreVersion);
        }
        httpURLConnection.setRequestProperty("X-Firebase-Storage-Version", stringBuilder2.toString());
        for (Entry entry : this.requestHeaders.entrySet()) {
            httpURLConnection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
        }
        JSONObject outputJSON = getOutputJSON();
        if (outputJSON != null) {
            bytes = outputJSON.toString().getBytes("UTF-8");
            length = bytes.length;
        } else {
            bytes = getOutputRaw();
            length = getOutputRawSize();
            if (length == 0 && bytes != null) {
                length = bytes.length;
            }
        }
        String str3 = "Content-Length";
        if (bytes == null || bytes.length <= 0) {
            httpURLConnection.setRequestProperty(str3, "0");
        } else {
            if (outputJSON != null) {
                httpURLConnection.setRequestProperty("Content-Type", APPLICATION_JSON);
            }
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty(str3, Integer.toString(length));
        }
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        if (bytes != null && bytes.length > 0) {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            if (outputStream != null) {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                try {
                    bufferedOutputStream.write(bytes, 0, length);
                } finally {
                    bufferedOutputStream.close();
                }
            } else {
                Log.e(str2, "Unable to write to the http request!");
            }
        }
    }

    private void parseResponse(@NonNull HttpURLConnection httpURLConnection) throws IOException {
        Preconditions.checkNotNull(httpURLConnection);
        this.resultCode = httpURLConnection.getResponseCode();
        this.resultHeaders = httpURLConnection.getHeaderFields();
        this.resultingContentLength = httpURLConnection.getContentLength();
        if (isResultSuccess()) {
            this.resultInputStream = httpURLConnection.getInputStream();
        } else {
            this.resultInputStream = httpURLConnection.getErrorStream();
        }
    }

    private void processResponseStream() throws IOException {
        if (isResultSuccess()) {
            parseSuccessulResponse(this.resultInputStream);
        } else {
            parseErrorResponse(this.resultInputStream);
        }
    }

    protected void parseSuccessulResponse(@Nullable InputStream inputStream) throws IOException {
        parseResponse(inputStream);
    }

    protected void parseErrorResponse(@Nullable InputStream inputStream) throws IOException {
        parseResponse(inputStream);
    }

    private void parseResponse(@Nullable InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    stringBuilder.append(readLine);
                } finally {
                    bufferedReader.close();
                }
            }
        }
        this.rawStringResponse = stringBuilder.toString();
        if (!isResultSuccess()) {
            this.mException = new IOException(this.rawStringResponse);
        }
    }

    @Nullable
    public String getRawResult() {
        return this.rawStringResponse;
    }

    @NonNull
    public Map<String, String> getResultHeaders() {
        return this.requestHeaders;
    }

    @Nullable
    public Exception getException() {
        return this.mException;
    }

    @Nullable
    public Map<String, List<String>> getResultHeadersImpl() {
        return this.resultHeaders;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public boolean isResultSuccess() {
        int i = this.resultCode;
        return i >= LogSeverity.INFO_VALUE && i < 300;
    }

    String getPostDataString(@Nullable List<String> list, List<String> list2, boolean z) throws UnsupportedEncodingException {
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        for (int i = 0; i < list.size(); i++) {
            if (obj != null) {
                obj = null;
            } else {
                stringBuilder.append("&");
            }
            String str = "UTF-8";
            stringBuilder.append(z ? URLEncoder.encode((String) list.get(i), str) : (String) list.get(i));
            stringBuilder.append("=");
            stringBuilder.append(z ? URLEncoder.encode((String) list2.get(i), str) : (String) list2.get(i));
        }
        return stringBuilder.toString();
    }

    @Nullable
    public String getResultString(String str) {
        Map resultHeadersImpl = getResultHeadersImpl();
        if (resultHeadersImpl != null) {
            List list = (List) resultHeadersImpl.get(str);
            if (list != null && list.size() > 0) {
                return (String) list.get(0);
            }
        }
        return null;
    }

    public int getResultingContentLength() {
        return this.resultingContentLength;
    }

    public <TResult> void completeTask(TaskCompletionSource<TResult> taskCompletionSource, TResult tResult) {
        Throwable exception = getException();
        if (isResultSuccess() && exception == null) {
            taskCompletionSource.setResult(tResult);
        } else {
            taskCompletionSource.setException(StorageException.fromExceptionAndHttpCode(exception, getResultCode()));
        }
    }
}
