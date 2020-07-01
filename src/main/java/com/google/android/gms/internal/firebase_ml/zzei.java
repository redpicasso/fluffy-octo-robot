package com.google.android.gms.internal.firebase_ml;

import io.grpc.internal.GrpcUtil;
import java.io.IOException;

public final class zzei implements zzfd, zzfj {
    private final boolean zzsj;

    public zzei() {
        this(false);
    }

    private zzei(boolean z) {
        this.zzsj = false;
    }

    public final void zza(zzfh zzfh) {
        zzfh.zza((zzfd) this);
    }

    public final void zzb(zzfh zzfh) throws IOException {
        String requestMethod = zzfh.getRequestMethod();
        String str = GrpcUtil.HTTP_METHOD;
        Object obj = 1;
        String str2 = "GET";
        if (requestMethod.equals(str) || ((!requestMethod.equals(str2) || zzfh.zzfa().zzew().length() <= 2048) && zzfh.zzez().zzaj(requestMethod))) {
            obj = null;
        }
        if (obj != null) {
            requestMethod = zzfh.getRequestMethod();
            zzfh.zzag(str);
            zzfh.zzfe().zzb("X-HTTP-Method-Override", requestMethod);
            if (requestMethod.equals(str2)) {
                zzfh.zza(new zzft((zzez) zzfh.zzfa().clone()));
                zzfh.zzfa().clear();
            } else if (zzfh.zzfb() == null) {
                zzfh.zza(new zzew());
            }
        }
    }
}
