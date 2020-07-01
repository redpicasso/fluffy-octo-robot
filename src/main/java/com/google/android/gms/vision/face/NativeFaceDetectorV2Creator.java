package com.google.android.gms.vision.face;

import android.content.Context;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.common.util.RetainForClient;
import com.google.android.gms.vision.EngineManager;
import com.google.android.gms.vision.clearcut.DynamiteClearcutLogger;
import com.google.android.gms.vision.face.internal.client.zze;
import com.google.android.gms.vision.face.internal.client.zzh;
import java.io.File;

@RetainForClient
@DynamiteApi
public class NativeFaceDetectorV2Creator extends ChimeraNativeBaseFaceDetectorCreator {
    private static final EngineManager zzby = EngineManager.zza("face", "libface_detector_v2_jni.so");

    final boolean zza(zze zze, String str) {
        return true;
    }

    protected final EngineManager zzo() {
        return zzby;
    }

    protected final zzh zza(Context context, DynamiteClearcutLogger dynamiteClearcutLogger, File file, zze zze) {
        return new NativeFaceDetectorV2Impl(context, dynamiteClearcutLogger, file, zze, new FaceDetectorV2Jni());
    }
}
