package com.google.firebase.auth.internal;

import com.google.android.gms.common.logging.Logger;
import com.google.firebase.auth.GetTokenResult;
import java.util.Collections;
import java.util.Map;

public final class zzan {
    private static final Logger zzjt = new Logger("GetTokenResultFactory", new String[0]);

    public static GetTokenResult zzdf(String str) {
        Map zzdd;
        try {
            zzdd = zzam.zzdd(str);
        } catch (Throwable e) {
            zzjt.e("Error parsing token claims", e, new Object[0]);
            zzdd = Collections.EMPTY_MAP;
        }
        return new GetTokenResult(str, zzdd);
    }
}
