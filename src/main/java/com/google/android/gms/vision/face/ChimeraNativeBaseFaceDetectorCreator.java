package com.google.android.gms.vision.face;

import android.content.Context;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.common.util.RetainForClient;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.vision.zzdn;
import com.google.android.gms.internal.vision.zzdr;
import com.google.android.gms.internal.vision.zzdu;
import com.google.android.gms.vision.EngineManager;
import com.google.android.gms.vision.clearcut.DynamiteClearcutLogger;
import com.google.android.gms.vision.clearcut.LogUtils;
import com.google.android.gms.vision.face.internal.client.zze;
import com.google.android.gms.vision.face.internal.client.zzg;
import com.google.android.gms.vision.face.internal.client.zzh;
import com.google.android.gms.vision.face.internal.client.zzk;
import java.io.File;

@RetainForClient
@KeepForSdk
@DynamiteApi
public abstract class ChimeraNativeBaseFaceDetectorCreator extends zzk {
    protected abstract zzh zza(Context context, DynamiteClearcutLogger dynamiteClearcutLogger, File file, zze zze);

    abstract boolean zza(zze zze, String str);

    abstract EngineManager zzo();

    public zzg newFaceDetector(IObjectWrapper iObjectWrapper, zze zze) throws RemoteException {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Context context = (Context) ObjectWrapper.unwrap(iObjectWrapper);
        DynamiteClearcutLogger dynamiteClearcutLogger = new DynamiteClearcutLogger(context);
        zzg zzg = null;
        try {
            zzo().zza(context);
            zzo();
            File zzc = EngineManager.zzc(context);
            boolean zza = zza(zze, new File(zzc, "models").toString());
            if (!zza) {
                Log.w("FaceDetectorCreatorImpl", "Missing model files were required by the face detector library");
            }
            if (zza) {
                zzg = zza(context, dynamiteClearcutLogger, zzc, zze);
            } else {
                zzo().zzb(context);
            }
            if (zzg != null) {
                zza(dynamiteClearcutLogger, context, zze, null, SystemClock.elapsedRealtime() - elapsedRealtime);
            }
            return zzg;
        } catch (RemoteException e) {
            String message = e.getMessage();
            throw e;
        } catch (Throwable th) {
            String str = null;
            Throwable th2 = th;
            if (str != null) {
                zza(dynamiteClearcutLogger, context, zze, str, SystemClock.elapsedRealtime() - elapsedRealtime);
            }
        }
    }

    private static void zza(DynamiteClearcutLogger dynamiteClearcutLogger, Context context, zze zze, @Nullable String str, long j) {
        zzdu zzdu = new zzdu();
        zzdr zzdr = new zzdr();
        zzdu.zzqd = zzdr;
        zzdr.name = "face";
        zzdr.zzpq = Long.valueOf(j);
        zzdr.zzps = new zzdn();
        int i = zze.mode;
        Integer valueOf = Integer.valueOf(3);
        Integer valueOf2 = Integer.valueOf(2);
        Integer valueOf3 = Integer.valueOf(1);
        if (i == 1) {
            zzdr.zzps.zzow = valueOf3;
        } else if (zze.mode == 0) {
            zzdr.zzps.zzow = valueOf2;
        } else if (zze.mode == 2) {
            zzdr.zzps.zzow = valueOf;
        }
        if (zze.landmarkType == 1) {
            zzdr.zzps.zzox = valueOf2;
        } else if (zze.landmarkType == 0) {
            zzdr.zzps.zzox = valueOf3;
        } else if (zze.landmarkType == 2) {
            zzdr.zzps.zzox = valueOf;
        }
        if (zze.zzcn == 1) {
            zzdr.zzps.zzoy = valueOf2;
        } else if (zze.zzcn == 0) {
            zzdr.zzps.zzoy = valueOf3;
        }
        zzdr.zzps.zzoz = Boolean.valueOf(zze.zzcm);
        zzdr.zzps.zzpa = Boolean.valueOf(zze.trackingEnabled);
        zzdr.zzps.zzpb = Float.valueOf(zze.proportionalMinFaceSize);
        if (str != null) {
            zzdr.zzon = str;
        }
        zzdr.zzpr = LogUtils.zzd(context);
        dynamiteClearcutLogger.zza(2, zzdu);
    }
}
