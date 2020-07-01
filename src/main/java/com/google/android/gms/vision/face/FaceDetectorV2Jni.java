package com.google.android.gms.vision.face;

import android.content.res.AssetManager;
import android.os.RemoteException;
import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import com.google.android.gms.internal.vision.zzbo;
import com.google.android.gms.internal.vision.zzca;
import com.google.android.gms.internal.vision.zzca.zzc;
import com.google.android.gms.internal.vision.zzca.zzd;
import com.google.android.gms.internal.vision.zzfk;
import com.google.android.gms.internal.vision.zzgf;
import com.google.android.gms.vision.L;
import java.nio.ByteBuffer;

public class FaceDetectorV2Jni {
    private final zzfk zzco = zzfk.zzej();

    public FaceDetectorV2Jni() {
        this.zzco.zza(zzca.zziv);
    }

    @Keep
    private native void closeDetectorJni(long j);

    @Keep
    private native byte[] detectFacesImageByteArrayJni(long j, byte[] bArr, byte[] bArr2);

    @Keep
    private native byte[] detectFacesImageByteBufferJni(long j, ByteBuffer byteBuffer, byte[] bArr);

    @Keep
    private native long initDetectorJni(byte[] bArr, AssetManager assetManager);

    final long zza(zzd zzd, AssetManager assetManager) {
        String str = "FaceDetectorV2Jni";
        L.zza(str, "initialize.start()");
        long initDetectorJni = initDetectorJni(zzd.toByteArray(), assetManager);
        L.zza(str, "initialize.end()");
        return initDetectorJni;
    }

    @Nullable
    final zzc zza(long j, ByteBuffer byteBuffer, zzbo zzbo) throws RemoteException {
        String str = "FaceDetectorV2Jni";
        L.zza(str, "detectFacesImageByteBuffer.start()");
        zzc zzc = null;
        try {
            byte[] detectFacesImageByteBufferJni = detectFacesImageByteBufferJni(j, byteBuffer, zzbo.toByteArray());
            if (detectFacesImageByteBufferJni != null && detectFacesImageByteBufferJni.length > 0) {
                zzc = zzc.zza(detectFacesImageByteBufferJni, this.zzco);
            }
        } catch (zzgf e) {
            Object[] objArr = new Object[1];
            String str2 = "detectFacesImageByteBuffer failed to parse result: ";
            String valueOf = String.valueOf(e.getMessage());
            objArr[0] = valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2);
            L.zzc(str, objArr);
        }
        L.zza(str, "detectFacesImageByteBuffer.end()");
        return zzc;
    }

    @Nullable
    final zzc zza(long j, byte[] bArr, zzbo zzbo) throws RemoteException {
        String str = "FaceDetectorV2Jni";
        L.zza(str, "detectFacesImageByteArray.start()");
        zzc zzc = null;
        try {
            byte[] detectFacesImageByteArrayJni = detectFacesImageByteArrayJni(j, bArr, zzbo.toByteArray());
            if (detectFacesImageByteArrayJni != null && detectFacesImageByteArrayJni.length > 0) {
                zzc = zzc.zza(detectFacesImageByteArrayJni, this.zzco);
            }
        } catch (zzgf e) {
            Object[] objArr = new Object[1];
            String str2 = "detectFacesImageByteArray failed to parse result: ";
            String valueOf = String.valueOf(e.getMessage());
            objArr[0] = valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2);
            L.zzc(str, objArr);
        }
        L.zza(str, "detectFacesImageByteArray.end()");
        return zzc;
    }

    final void zza(long j) throws RemoteException {
        String str = "FaceDetectorV2Jni";
        L.zza(str, "closeDetector.start()");
        closeDetectorJni(j);
        L.zza(str, "closeDetector.end()");
    }
}
