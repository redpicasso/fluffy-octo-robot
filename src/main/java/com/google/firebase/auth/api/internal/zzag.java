package com.google.firebase.auth.api.internal;

import androidx.annotation.GuardedBy;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseError;
import java.util.concurrent.Future;

public abstract class zzag<T extends zzai> {
    private static Logger zzjt = new Logger("BiChannelGoogleApi", "FirebaseAuth: ");
    @GuardedBy("this")
    private zzaj<T> zzma;

    abstract Future<zzaj<T>> zzdq();

    public final <ResultT, A extends AnyClient> Task<ResultT> zza(zzan<A, ResultT> zzan) {
        GoogleApi zzbw = zzbw(zzan.zzdu());
        if (zzbw == null) {
            return zzdr();
        }
        return zzbw.doRead(zzan.zzdv());
    }

    public final <ResultT, A extends AnyClient> Task<ResultT> zzb(zzan<A, ResultT> zzan) {
        GoogleApi zzbw = zzbw(zzan.zzdu());
        if (zzbw == null) {
            return zzdr();
        }
        return zzbw.doWrite(zzan.zzdv());
    }

    private static <ResultT> Task<ResultT> zzdr() {
        return Tasks.forException(zzdr.zzb(new Status(FirebaseError.ERROR_INTERNAL_ERROR, "Unable to connect to GoogleApi instance - Google Play Services may be unavailable")));
    }

    private final GoogleApi<T> zzbw(String str) {
        zzaj zzds = zzds();
        Logger logger;
        String valueOf;
        StringBuilder stringBuilder;
        if (zzds.zzmf.zzbx(str)) {
            logger = zzjt;
            valueOf = String.valueOf(zzds.zzme);
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 43);
            stringBuilder.append("getGoogleApiForMethod() returned Fallback: ");
            stringBuilder.append(valueOf);
            logger.w(stringBuilder.toString(), new Object[0]);
            return zzds.zzme;
        }
        logger = zzjt;
        valueOf = String.valueOf(zzds.zzmd);
        stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 38);
        stringBuilder.append("getGoogleApiForMethod() returned Gms: ");
        stringBuilder.append(valueOf);
        logger.w(stringBuilder.toString(), new Object[0]);
        return zzds.zzmd;
    }

    private final zzaj<T> zzds() {
        zzaj<T> zzaj;
        synchronized (this) {
            if (this.zzma == null) {
                try {
                    this.zzma = (zzaj) zzdq().get();
                } catch (Exception e) {
                    String str = "There was an error while initializing the connection to Google Play Services: ";
                    String valueOf = String.valueOf(e.getMessage());
                    throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                }
            }
            zzaj = this.zzma;
        }
        return zzaj;
    }
}
