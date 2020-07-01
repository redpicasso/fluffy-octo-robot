package com.google.firebase.functions;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.common.net.HttpHeaders;
import com.google.firebase.FirebaseApp;
import com.google.firebase.functions.FirebaseFunctionsException.Code;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

/* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
public class FirebaseFunctions {
    private static boolean providerInstallStarted = false;
    private static final TaskCompletionSource<Void> providerInstalled = new TaskCompletionSource();
    private final OkHttpClient client = new OkHttpClient();
    private final ContextProvider contextProvider;
    private final String projectId;
    private final String region;
    private final Serializer serializer = new Serializer();
    private String urlFormat = "https://%1$s-%2$s.cloudfunctions.net/%3$s";

    FirebaseFunctions(Context context, String str, String str2, ContextProvider contextProvider) {
        this.contextProvider = (ContextProvider) Preconditions.checkNotNull(contextProvider);
        this.projectId = (String) Preconditions.checkNotNull(str);
        this.region = (String) Preconditions.checkNotNull(str2);
        maybeInstallProviders(context);
    }

    private static void maybeInstallProviders(Context context) {
        synchronized (providerInstalled) {
            if (providerInstallStarted) {
                return;
            }
            providerInstallStarted = true;
            new Handler(context.getMainLooper()).post(FirebaseFunctions$$Lambda$1.lambdaFactory$(context));
        }
    }

    public static FirebaseFunctions getInstance(FirebaseApp firebaseApp, String str) {
        Preconditions.checkNotNull(firebaseApp, "You must call FirebaseApp.initializeApp first.");
        Preconditions.checkNotNull(str);
        FunctionsMultiResourceComponent functionsMultiResourceComponent = (FunctionsMultiResourceComponent) firebaseApp.get(FunctionsMultiResourceComponent.class);
        Preconditions.checkNotNull(functionsMultiResourceComponent, "Functions component does not exist.");
        return functionsMultiResourceComponent.get(str);
    }

    public static FirebaseFunctions getInstance(FirebaseApp firebaseApp) {
        return getInstance(firebaseApp, "us-central1");
    }

    public static FirebaseFunctions getInstance(String str) {
        return getInstance(FirebaseApp.getInstance(), str);
    }

    public static FirebaseFunctions getInstance() {
        return getInstance(FirebaseApp.getInstance(), "us-central1");
    }

    public HttpsCallableReference getHttpsCallable(String str) {
        return new HttpsCallableReference(this, str);
    }

    @VisibleForTesting
    URL getURL(String str) {
        try {
            return new URL(String.format(this.urlFormat, new Object[]{this.region, this.projectId, str}));
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void useFunctionsEmulator(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("/%2$s/%1$s/%3$s");
        this.urlFormat = stringBuilder.toString();
    }

    Task<HttpsCallableResult> call(String str, @Nullable Object obj, HttpsCallOptions httpsCallOptions) {
        return providerInstalled.getTask().continueWithTask(FirebaseFunctions$$Lambda$4.lambdaFactory$(this)).continueWithTask(FirebaseFunctions$$Lambda$5.lambdaFactory$(this, str, obj, httpsCallOptions));
    }

    static /* synthetic */ Task lambda$call$2(FirebaseFunctions firebaseFunctions, String str, Object obj, HttpsCallOptions httpsCallOptions, Task task) throws Exception {
        if (task.isSuccessful()) {
            return firebaseFunctions.call(str, obj, (HttpsCallableContext) task.getResult(), httpsCallOptions);
        }
        return Tasks.forException(task.getException());
    }

    private Task<HttpsCallableResult> call(String str, @Nullable Object obj, HttpsCallableContext httpsCallableContext, HttpsCallOptions httpsCallOptions) {
        if (str != null) {
            URL url = getURL(str);
            Map hashMap = new HashMap();
            hashMap.put("data", this.serializer.encode(obj));
            Builder post = new Builder().url(url).post(RequestBody.create(MediaType.parse("application/json"), new JSONObject(hashMap).toString()));
            if (httpsCallableContext.getAuthToken() != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Bearer ");
                stringBuilder.append(httpsCallableContext.getAuthToken());
                post = post.header(HttpHeaders.AUTHORIZATION, stringBuilder.toString());
            }
            if (httpsCallableContext.getInstanceIdToken() != null) {
                post = post.header("Firebase-Instance-ID-Token", httpsCallableContext.getInstanceIdToken());
            }
            Call newCall = httpsCallOptions.apply(this.client).newCall(post.build());
            final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
            newCall.enqueue(new Callback() {
                public void onFailure(Call call, IOException iOException) {
                    if (iOException instanceof InterruptedIOException) {
                        taskCompletionSource.setException(new FirebaseFunctionsException(Code.DEADLINE_EXCEEDED.name(), Code.DEADLINE_EXCEEDED, null, iOException));
                        return;
                    }
                    taskCompletionSource.setException(new FirebaseFunctionsException(Code.INTERNAL.name(), Code.INTERNAL, null, iOException));
                }

                public void onResponse(Call call, Response response) throws IOException {
                    Code fromHttpStatus = Code.fromHttpStatus(response.code());
                    String string = response.body().string();
                    Exception fromResponse = FirebaseFunctionsException.fromResponse(fromHttpStatus, string, FirebaseFunctions.this.serializer);
                    if (fromResponse != null) {
                        taskCompletionSource.setException(fromResponse);
                        return;
                    }
                    try {
                        JSONObject jSONObject = new JSONObject(string);
                        Object opt = jSONObject.opt("data");
                        if (opt == null) {
                            opt = jSONObject.opt("result");
                        }
                        if (opt == null) {
                            taskCompletionSource.setException(new FirebaseFunctionsException("Response is missing data field.", Code.INTERNAL, null));
                            return;
                        }
                        taskCompletionSource.setResult(new HttpsCallableResult(FirebaseFunctions.this.serializer.decode(opt)));
                    } catch (Throwable e) {
                        taskCompletionSource.setException(new FirebaseFunctionsException("Response is not valid JSON object.", Code.INTERNAL, null, e));
                    }
                }
            });
            return taskCompletionSource.getTask();
        }
        throw new IllegalArgumentException("name cannot be null");
    }
}
