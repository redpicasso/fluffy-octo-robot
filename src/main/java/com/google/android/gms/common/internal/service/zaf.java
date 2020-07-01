package com.google.android.gms.common.internal.service;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
abstract class zaf<R extends Result> extends ApiMethodImpl<R, zah> {
    public zaf(GoogleApiClient googleApiClient) {
        super(Common.API, googleApiClient);
    }
}
