package com.google.android.gms.vision.face;

import android.content.Context;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.common.util.RetainForClient;
import com.google.android.gms.vision.EngineManager;
import com.google.android.gms.vision.clearcut.DynamiteClearcutLogger;
import com.google.android.gms.vision.face.internal.client.zze;
import com.google.android.gms.vision.face.internal.client.zzh;
import java.io.File;

@RetainForClient
@KeepForSdk
@DynamiteApi
public class ChimeraNativeFaceDetectorCreator extends ChimeraNativeBaseFaceDetectorCreator {
    private static final EngineManager zzby = EngineManager.zza("face", "libmobile_vision_face.so");

    protected final EngineManager zzo() {
        return zzby;
    }

    protected final boolean zza(zze zze, String str) {
        return NativeFaceDetectorImpl.validateModelFilesJni(NativeFaceSettings.zzc(zze), str);
    }

    protected final zzh zza(Context context, DynamiteClearcutLogger dynamiteClearcutLogger, File file, zze zze) {
        return new NativeFaceDetectorImpl(context, dynamiteClearcutLogger, file, zze);
    }
}
