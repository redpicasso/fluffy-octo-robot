package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.GoogleApiManager.zaa;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zac;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zaw<O extends ApiOptions> extends GoogleApi<O> {
    private final AbstractClientBuilder<? extends zac, SignInOptions> zacf;
    private final ClientSettings zafa;
    private final Client zafj;
    private final zap zafk;

    public zaw(@NonNull Context context, Api<O> api, Looper looper, @NonNull Client client, @NonNull zap zap, ClientSettings clientSettings, AbstractClientBuilder<? extends zac, SignInOptions> abstractClientBuilder) {
        super(context, api, looper);
        this.zafj = client;
        this.zafk = zap;
        this.zafa = clientSettings;
        this.zacf = abstractClientBuilder;
        this.zabo.zaa((GoogleApi) this);
    }

    public final Client zaad() {
        return this.zafj;
    }

    public final Client zaa(Looper looper, zaa<O> zaa) {
        this.zafk.zaa(zaa);
        return this.zafj;
    }

    public final zace zaa(Context context, Handler handler) {
        return new zace(context, handler, this.zafa, this.zacf);
    }
}
