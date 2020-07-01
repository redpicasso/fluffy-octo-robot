package com.google.android.gms.internal.firebase_messaging;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Deque;

/* compiled from: com.google.firebase:firebase-messaging@@20.0.0 */
public final class zzj {
    private static final OutputStream zza = new zzi();

    private static byte[] zza(Deque<byte[]> deque, int i) {
        Object obj = new byte[i];
        int i2 = i;
        while (i2 > 0) {
            byte[] bArr = (byte[]) deque.removeFirst();
            int min = Math.min(i2, bArr.length);
            System.arraycopy(bArr, 0, obj, i - i2, min);
            i2 -= min;
        }
        return obj;
    }

    public static byte[] zza(InputStream inputStream) throws IOException {
        zzg.zza(inputStream);
        Deque arrayDeque = new ArrayDeque(20);
        int i = 8192;
        int i2;
        for (int i3 = 0; i3 < 2147483639; i3 = i2) {
            Object obj = new byte[Math.min(i, 2147483639 - i3)];
            arrayDeque.add(obj);
            i2 = i3;
            i3 = 0;
            while (i3 < obj.length) {
                int read = inputStream.read(obj, i3, obj.length - i3);
                if (read == -1) {
                    return zza(arrayDeque, i2);
                }
                i3 += read;
                i2 += read;
            }
            i = zzn.zza(i, 2);
        }
        if (inputStream.read() == -1) {
            return zza(arrayDeque, 2147483639);
        }
        throw new OutOfMemoryError("input is too large to fit in a byte array");
    }

    public static InputStream zza(InputStream inputStream, long j) {
        return new zzl(inputStream, 1048577);
    }
}
