package com.google.android.gms.internal.firebase_ml;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image.Plane;
import android.util.Log;
import androidx.annotation.NonNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public final class zzpx {
    private static final zzpx zzaxp = new zzpx();

    private zzpx() {
    }

    public static zzpx zznk() {
        return zzaxp;
    }

    public static byte[] zza(@NonNull ByteBuffer byteBuffer) {
        byteBuffer.rewind();
        byte[] bArr = new byte[byteBuffer.limit()];
        byteBuffer.get(bArr, 0, bArr.length);
        return bArr;
    }

    public static byte[] zza(@NonNull byte[] bArr, int i, int i2) {
        Throwable th;
        Throwable th2;
        YuvImage yuvImage = new YuvImage(bArr, 17, i, i2, null);
        byte[] toByteArray;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                yuvImage.compressToJpeg(new Rect(0, 0, i, i2), 100, byteArrayOutputStream);
                toByteArray = byteArrayOutputStream.toByteArray();
                return toByteArray;
            } catch (Throwable th22) {
                Throwable th3 = th22;
                th22 = th;
                th = th3;
            }
            try {
                zza(null, byteArrayOutputStream);
            } catch (IOException unused) {
                Log.w("ImageConvertUtils", "Error closing ByteArrayOutputStream");
            }
            zza(th22, byteArrayOutputStream);
            throw th;
        } catch (IOException unused2) {
            toByteArray = null;
        }
    }

    public static byte[] zza(@NonNull Bitmap bitmap) {
        Throwable th;
        Throwable th2;
        byte[] toByteArray;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                bitmap.compress(CompressFormat.JPEG, 100, byteArrayOutputStream);
                toByteArray = byteArrayOutputStream.toByteArray();
                return toByteArray;
            } catch (Throwable th22) {
                Throwable th3 = th22;
                th22 = th;
                th = th3;
            }
            try {
                zza(null, byteArrayOutputStream);
            } catch (IOException unused) {
                Log.w("ImageConvertUtils", "Error closing ByteArrayOutputStream");
            }
            zza(th22, byteArrayOutputStream);
            throw th;
        } catch (IOException unused2) {
            toByteArray = null;
        }
    }

    @TargetApi(19)
    public static ByteBuffer zza(Plane[] planeArr, int i, int i2) {
        int i3 = i * i2;
        byte[] bArr = new byte[(((i3 / 4) * 2) + i3)];
        ByteBuffer buffer = planeArr[1].getBuffer();
        ByteBuffer buffer2 = planeArr[2].getBuffer();
        int position = buffer2.position();
        int limit = buffer.limit();
        buffer2.position(position + 1);
        buffer.limit(limit - 1);
        int i4 = (i3 * 2) / 4;
        Object obj = (buffer2.remaining() == i4 + -2 && buffer2.compareTo(buffer) == 0) ? 1 : null;
        buffer2.position(position);
        buffer.limit(limit);
        if (obj != null) {
            planeArr[0].getBuffer().get(bArr, 0, i3);
            ByteBuffer buffer3 = planeArr[1].getBuffer();
            planeArr[2].getBuffer().get(bArr, i3, 1);
            buffer3.get(bArr, i3 + 1, i4 - 1);
        } else {
            limit = i;
            int i5 = i2;
            byte[] bArr2 = bArr;
            zza(planeArr[0], limit, i5, bArr2, 0, 1);
            zza(planeArr[1], limit, i5, bArr2, i3 + 1, 2);
            zza(planeArr[2], i, i2, bArr, i3, 2);
        }
        return ByteBuffer.wrap(bArr);
    }

    @TargetApi(19)
    private static void zza(Plane plane, int i, int i2, byte[] bArr, int i3, int i4) {
        ByteBuffer buffer = plane.getBuffer();
        int position = buffer.position();
        int remaining = ((buffer.remaining() + plane.getRowStride()) - 1) / plane.getRowStride();
        i /= i2 / remaining;
        int i5 = i3;
        i3 = 0;
        int i6 = 0;
        while (i3 < remaining) {
            int i7 = i6;
            int i8 = i5;
            for (i5 = 0; i5 < i; i5++) {
                bArr[i8] = buffer.get(i7);
                i8 += i4;
                i7 += plane.getPixelStride();
            }
            i6 += plane.getRowStride();
            i3++;
            i5 = i8;
        }
        buffer.position(position);
    }

    public static byte[] zzf(byte[] bArr) {
        int length = bArr.length;
        int i = length / 6;
        Object obj = new byte[length];
        int i2 = i << 2;
        int i3 = 0;
        System.arraycopy(bArr, 0, obj, 0, i2);
        while (i3 < (i << 1)) {
            obj[i2 + i3] = bArr[(((i3 % 2) * i) + i2) + (i3 / 2)];
            i3++;
        }
        return obj;
    }

    private static /* synthetic */ void zza(Throwable th, ByteArrayOutputStream byteArrayOutputStream) {
        if (th != null) {
            try {
                byteArrayOutputStream.close();
                return;
            } catch (Throwable th2) {
                zzlx.zza(th, th2);
                return;
            }
        }
        byteArrayOutputStream.close();
    }
}
