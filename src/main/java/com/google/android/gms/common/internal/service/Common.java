package com.google.android.gms.common.internal.service;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.ClientKey;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class Common {
    @KeepForSdk
    public static final Api<NoOptions> API = new Api("Common.API", zapv, CLIENT_KEY);
    @KeepForSdk
    public static final ClientKey<zah> CLIENT_KEY = new ClientKey();
    private static final AbstractClientBuilder<zah, NoOptions> zapv = new zac();
    public static final zab zapw = new zae();
}
