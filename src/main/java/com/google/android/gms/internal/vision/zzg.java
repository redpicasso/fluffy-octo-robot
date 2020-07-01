package com.google.android.gms.internal.vision;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.vision.barcode.Barcode;
import java.nio.ByteBuffer;

public final class zzg extends zzm<zzh> {
    private final zze zzbm;

    public zzg(Context context, zze zze) {
        super(context, "BarcodeNativeHandle", "barcode");
        this.zzbm = zze;
        zzq();
    }

    public final Barcode[] zza(ByteBuffer byteBuffer, zzn zzn) {
        if (!isOperational()) {
            return new Barcode[0];
        }
        try {
            return ((zzh) zzq()).zza(ObjectWrapper.wrap(byteBuffer), zzn);
        } catch (Throwable e) {
            Log.e("BarcodeNativeHandle", "Error calling native barcode detector", e);
            return new Barcode[0];
        }
    }

    public final Barcode[] zza(Bitmap bitmap, zzn zzn) {
        if (!isOperational()) {
            return new Barcode[0];
        }
        try {
            return ((zzh) zzq()).zzb(ObjectWrapper.wrap(bitmap), zzn);
        } catch (Throwable e) {
            Log.e("BarcodeNativeHandle", "Error calling native barcode detector", e);
            return new Barcode[0];
        }
    }

    protected final void zzm() throws RemoteException {
        if (isOperational()) {
            ((zzh) zzq()).zzn();
        }
    }
}
