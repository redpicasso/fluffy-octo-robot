package com.google.android.gms.internal.firebase_ml;

import io.grpc.internal.GrpcUtil;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public abstract class zzfo {
    static final Logger zzve = Logger.getLogger(zzfo.class.getName());
    private static final String[] zzvf;

    protected abstract zzfp zzc(String str, String str2) throws IOException;

    public final zzfi zza(zzfj zzfj) {
        return new zzfi(this, zzfj);
    }

    public boolean zzaj(String str) throws IOException {
        return Arrays.binarySearch(zzvf, str) >= 0;
    }

    static {
        String[] strArr = new String[]{"DELETE", "GET", GrpcUtil.HTTP_METHOD, "PUT"};
        zzvf = strArr;
        Arrays.sort(strArr);
    }
}
