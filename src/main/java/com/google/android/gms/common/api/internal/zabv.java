package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Api.AnyClient;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zabv {
    public final RegisterListenerMethod<AnyClient, ?> zakc;
    public final UnregisterListenerMethod<AnyClient, ?> zakd;

    public zabv(@NonNull RegisterListenerMethod<AnyClient, ?> registerListenerMethod, @NonNull UnregisterListenerMethod<AnyClient, ?> unregisterListenerMethod) {
        this.zakc = registerListenerMethod;
        this.zakd = unregisterListenerMethod;
    }
}
