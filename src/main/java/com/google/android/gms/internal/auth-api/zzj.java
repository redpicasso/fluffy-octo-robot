package com.google.android.gms.internal.auth-api;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzj extends zzp<CredentialRequestResult> {
    private final /* synthetic */ CredentialRequest zzam;

    zzj(zzi zzi, GoogleApiClient googleApiClient, CredentialRequest credentialRequest) {
        this.zzam = credentialRequest;
        super(googleApiClient);
    }

    protected final void zzc(Context context, zzw zzw) throws RemoteException {
        zzw.zzc(new zzk(this), this.zzam);
    }
}
