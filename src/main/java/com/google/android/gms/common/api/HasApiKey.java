package com.google.android.gms.common.api;

import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.internal.ApiKey;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public interface HasApiKey<O extends ApiOptions> {
    ApiKey<O> getApiKey();
}
