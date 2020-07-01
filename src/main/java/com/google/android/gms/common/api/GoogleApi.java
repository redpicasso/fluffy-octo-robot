package com.google.android.gms.common.api;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.ApiKey;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.GoogleApiManager.zaa;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.api.internal.RegistrationMethods;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.common.api.internal.zaad;
import com.google.android.gms.common.api.internal.zabn;
import com.google.android.gms.common.api.internal.zace;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public class GoogleApi<O extends ApiOptions> implements HasApiKey<O> {
    private final Api<O> mApi;
    private final Context mContext;
    private final int mId;
    private final O zabj;
    private final ApiKey<O> zabk;
    private final Looper zabl;
    private final GoogleApiClient zabm;
    private final StatusExceptionMapper zabn;
    protected final GoogleApiManager zabo;

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    public static class Settings {
        @KeepForSdk
        public static final Settings DEFAULT_SETTINGS = new Builder().build();
        public final StatusExceptionMapper zabp;
        public final Looper zabq;

        @KeepForSdk
        /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
        public static class Builder {
            private Looper zabl;
            private StatusExceptionMapper zabn;

            @KeepForSdk
            public Builder setMapper(StatusExceptionMapper statusExceptionMapper) {
                Preconditions.checkNotNull(statusExceptionMapper, "StatusExceptionMapper must not be null.");
                this.zabn = statusExceptionMapper;
                return this;
            }

            @KeepForSdk
            public Builder setLooper(Looper looper) {
                Preconditions.checkNotNull(looper, "Looper must not be null.");
                this.zabl = looper;
                return this;
            }

            @KeepForSdk
            public Settings build() {
                if (this.zabn == null) {
                    this.zabn = new ApiExceptionMapper();
                }
                if (this.zabl == null) {
                    this.zabl = Looper.getMainLooper();
                }
                return new Settings(this.zabn, null, this.zabl, null);
            }
        }

        @KeepForSdk
        private Settings(StatusExceptionMapper statusExceptionMapper, Account account, Looper looper) {
            this.zabp = statusExceptionMapper;
            this.zabq = looper;
        }

        /* synthetic */ Settings(StatusExceptionMapper statusExceptionMapper, Account account, Looper looper, zab zab) {
            this(statusExceptionMapper, null, looper);
        }
    }

    @KeepForSdk
    protected GoogleApi(@NonNull Context context, Api<O> api, Looper looper) {
        Preconditions.checkNotNull(context, "Null context is not permitted.");
        Preconditions.checkNotNull(api, "Api must not be null.");
        Preconditions.checkNotNull(looper, "Looper must not be null.");
        this.mContext = context.getApplicationContext();
        this.mApi = api;
        this.zabj = null;
        this.zabl = looper;
        this.zabk = ApiKey.getUniqueApiKey(api);
        this.zabm = new zabn(this);
        this.zabo = GoogleApiManager.zab(this.mContext);
        this.mId = this.zabo.zabb();
        this.zabn = new ApiExceptionMapper();
    }

    @KeepForSdk
    @Deprecated
    public GoogleApi(@NonNull Context context, Api<O> api, @Nullable O o, Looper looper, StatusExceptionMapper statusExceptionMapper) {
        this(context, (Api) api, (ApiOptions) o, new Builder().setLooper(looper).setMapper(statusExceptionMapper).build());
    }

    @MainThread
    @KeepForSdk
    public GoogleApi(@NonNull Activity activity, Api<O> api, @Nullable O o, Settings settings) {
        Preconditions.checkNotNull(activity, "Null activity is not permitted.");
        Preconditions.checkNotNull(api, "Api must not be null.");
        Preconditions.checkNotNull(settings, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.mContext = activity.getApplicationContext();
        this.mApi = api;
        this.zabj = o;
        this.zabl = settings.zabq;
        this.zabk = ApiKey.getSharedApiKey(this.mApi, this.zabj);
        this.zabm = new zabn(this);
        this.zabo = GoogleApiManager.zab(this.mContext);
        this.mId = this.zabo.zabb();
        this.zabn = settings.zabp;
        if (!(activity instanceof GoogleApiActivity)) {
            zaad.zaa(activity, this.zabo, this.zabk);
        }
        this.zabo.zaa(this);
    }

    @KeepForSdk
    public GoogleApi(@NonNull Context context, Api<O> api, @Nullable O o, Settings settings) {
        Preconditions.checkNotNull(context, "Null context is not permitted.");
        Preconditions.checkNotNull(api, "Api must not be null.");
        Preconditions.checkNotNull(settings, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.mContext = context.getApplicationContext();
        this.mApi = api;
        this.zabj = o;
        this.zabl = settings.zabq;
        this.zabk = ApiKey.getSharedApiKey(this.mApi, this.zabj);
        this.zabm = new zabn(this);
        this.zabo = GoogleApiManager.zab(this.mContext);
        this.mId = this.zabo.zabb();
        this.zabn = settings.zabp;
        this.zabo.zaa(this);
    }

    @KeepForSdk
    @Deprecated
    public GoogleApi(@NonNull Activity activity, Api<O> api, @Nullable O o, StatusExceptionMapper statusExceptionMapper) {
        this(activity, (Api) api, (ApiOptions) o, new Builder().setMapper(statusExceptionMapper).setLooper(activity.getMainLooper()).build());
    }

    @KeepForSdk
    @Deprecated
    public GoogleApi(@NonNull Context context, Api<O> api, @Nullable O o, StatusExceptionMapper statusExceptionMapper) {
        this(context, (Api) api, (ApiOptions) o, new Builder().setMapper(statusExceptionMapper).build());
    }

    private final <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T zaa(int i, @NonNull T t) {
        t.zar();
        this.zabo.zaa(this, i, (ApiMethodImpl) t);
        return t;
    }

    private final <TResult, A extends AnyClient> Task<TResult> zaa(int i, @NonNull TaskApiCall<A, TResult> taskApiCall) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.zabo.zaa(this, i, taskApiCall, taskCompletionSource, this.zabn);
        return taskCompletionSource.getTask();
    }

    @KeepForSdk
    public <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T doRead(@NonNull T t) {
        return zaa(0, (ApiMethodImpl) t);
    }

    @KeepForSdk
    public <TResult, A extends AnyClient> Task<TResult> doRead(TaskApiCall<A, TResult> taskApiCall) {
        return zaa(0, (TaskApiCall) taskApiCall);
    }

    @KeepForSdk
    public <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T doWrite(@NonNull T t) {
        return zaa(1, (ApiMethodImpl) t);
    }

    @KeepForSdk
    public <TResult, A extends AnyClient> Task<TResult> doWrite(TaskApiCall<A, TResult> taskApiCall) {
        return zaa(1, (TaskApiCall) taskApiCall);
    }

    @KeepForSdk
    public <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T doBestEffortWrite(@NonNull T t) {
        return zaa(2, (ApiMethodImpl) t);
    }

    @KeepForSdk
    public <TResult, A extends AnyClient> Task<TResult> doBestEffortWrite(TaskApiCall<A, TResult> taskApiCall) {
        return zaa(2, (TaskApiCall) taskApiCall);
    }

    @KeepForSdk
    @Deprecated
    public <A extends AnyClient, T extends RegisterListenerMethod<A, ?>, U extends UnregisterListenerMethod<A, ?>> Task<Void> doRegisterEventListener(@NonNull T t, U u) {
        Preconditions.checkNotNull(t);
        Preconditions.checkNotNull(u);
        String str = "Listener has already been released.";
        Preconditions.checkNotNull(t.getListenerKey(), str);
        Preconditions.checkNotNull(u.getListenerKey(), str);
        Preconditions.checkArgument(t.getListenerKey().equals(u.getListenerKey()), "Listener registration and unregistration methods must be constructed with the same ListenerHolder.");
        return this.zabo.zaa(this, (RegisterListenerMethod) t, (UnregisterListenerMethod) u);
    }

    @KeepForSdk
    public <A extends AnyClient> Task<Void> doRegisterEventListener(@NonNull RegistrationMethods<A, ?> registrationMethods) {
        Preconditions.checkNotNull(registrationMethods);
        String str = "Listener has already been released.";
        Preconditions.checkNotNull(registrationMethods.zaka.getListenerKey(), str);
        Preconditions.checkNotNull(registrationMethods.zakb.getListenerKey(), str);
        return this.zabo.zaa(this, registrationMethods.zaka, registrationMethods.zakb);
    }

    @KeepForSdk
    public Task<Boolean> doUnregisterEventListener(@NonNull ListenerKey<?> listenerKey) {
        Preconditions.checkNotNull(listenerKey, "Listener key cannot be null.");
        return this.zabo.zaa(this, (ListenerKey) listenerKey);
    }

    @KeepForSdk
    public <L> ListenerHolder<L> registerListener(@NonNull L l, String str) {
        return ListenerHolders.createListenerHolder(l, this.zabl, str);
    }

    @KeepForSdk
    protected Task<Boolean> disconnectService() {
        return this.zabo.zac(this);
    }

    @WorkerThread
    public Client zaa(Looper looper, zaa<O> zaa) {
        return this.mApi.zai().buildClient(this.mContext, looper, createClientSettingsBuilder().build(), this.zabj, (ConnectionCallbacks) zaa, (OnConnectionFailedListener) zaa);
    }

    public final Api<O> getApi() {
        return this.mApi;
    }

    @KeepForSdk
    public O getApiOptions() {
        return this.zabj;
    }

    public ApiKey<O> getApiKey() {
        return this.zabk;
    }

    public final int getInstanceId() {
        return this.mId;
    }

    @KeepForSdk
    public GoogleApiClient asGoogleApiClient() {
        return this.zabm;
    }

    @KeepForSdk
    public Looper getLooper() {
        return this.zabl;
    }

    @KeepForSdk
    public Context getApplicationContext() {
        return this.mContext;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0030  */
    @com.google.android.gms.common.annotation.KeepForSdk
    protected com.google.android.gms.common.internal.ClientSettings.Builder createClientSettingsBuilder() {
        /*
        r3 = this;
        r0 = new com.google.android.gms.common.internal.ClientSettings$Builder;
        r0.<init>();
        r1 = r3.zabj;
        r2 = r1 instanceof com.google.android.gms.common.api.Api.ApiOptions.HasGoogleSignInAccountOptions;
        if (r2 == 0) goto L_0x0018;
    L_0x000b:
        r1 = (com.google.android.gms.common.api.Api.ApiOptions.HasGoogleSignInAccountOptions) r1;
        r1 = r1.getGoogleSignInAccount();
        if (r1 == 0) goto L_0x0018;
    L_0x0013:
        r1 = r1.getAccount();
        goto L_0x0026;
    L_0x0018:
        r1 = r3.zabj;
        r2 = r1 instanceof com.google.android.gms.common.api.Api.ApiOptions.HasAccountOptions;
        if (r2 == 0) goto L_0x0025;
    L_0x001e:
        r1 = (com.google.android.gms.common.api.Api.ApiOptions.HasAccountOptions) r1;
        r1 = r1.getAccount();
        goto L_0x0026;
    L_0x0025:
        r1 = 0;
    L_0x0026:
        r0 = r0.setAccount(r1);
        r1 = r3.zabj;
        r2 = r1 instanceof com.google.android.gms.common.api.Api.ApiOptions.HasGoogleSignInAccountOptions;
        if (r2 == 0) goto L_0x003d;
    L_0x0030:
        r1 = (com.google.android.gms.common.api.Api.ApiOptions.HasGoogleSignInAccountOptions) r1;
        r1 = r1.getGoogleSignInAccount();
        if (r1 == 0) goto L_0x003d;
    L_0x0038:
        r1 = r1.getRequestedScopes();
        goto L_0x0041;
    L_0x003d:
        r1 = java.util.Collections.emptySet();
    L_0x0041:
        r0 = r0.addAllRequiredScopes(r1);
        r1 = r3.mContext;
        r1 = r1.getClass();
        r1 = r1.getName();
        r0 = r0.setRealClientClassName(r1);
        r1 = r3.mContext;
        r1 = r1.getPackageName();
        r0 = r0.setRealClientPackageName(r1);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.GoogleApi.createClientSettingsBuilder():com.google.android.gms.common.internal.ClientSettings$Builder");
    }

    public zace zaa(Context context, Handler handler) {
        return new zace(context, handler, createClientSettingsBuilder().build());
    }
}
