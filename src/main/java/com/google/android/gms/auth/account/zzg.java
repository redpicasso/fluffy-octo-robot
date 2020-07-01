package com.google.android.gms.auth.account;

import android.accounts.Account;
import com.google.android.gms.auth.account.WorkAccountApi.AddAccountResult;
import com.google.android.gms.common.internal.PendingResultUtil.ResultConverter;

final class zzg implements ResultConverter<AddAccountResult, Account> {
    zzg(WorkAccountClient workAccountClient) {
    }
}
