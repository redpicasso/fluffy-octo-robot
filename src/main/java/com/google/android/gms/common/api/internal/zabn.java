package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Looper;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zabn<O extends ApiOptions> extends zaag {
    private final GoogleApi<O> zajj;

    public zabn(GoogleApi<O> googleApi) {
        super("Method is not supported by connectionless client. APIs supporting connectionless client must not call this method.");
        this.zajj = googleApi;
    }

    public final void zaa(zack zack) {
    }

    public final void zab(zack zack) {
    }

    public final <A extends AnyClient, R extends Result, T extends ApiMethodImpl<R, A>> T enqueue(@NonNull T t) {
        return this.zajj.doRead((ApiMethodImpl) t);
    }

    public final <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t) {
        return this.zajj.doWrite((ApiMethodImpl) t);
    }

    public final Looper getLooper() {
        return this.zajj.getLooper();
    }

    public final Context getContext() {
        return this.zajj.getApplicationContext();
    }
}
