package com.bumptech.glide.load.model;

import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import java.io.InputStream;

public class StreamEncoder implements Encoder<InputStream> {
    private static final String TAG = "StreamEncoder";
    private final ArrayPool byteArrayPool;

    public StreamEncoder(ArrayPool arrayPool) {
        this.byteArrayPool = arrayPool;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0038 A:{Catch:{ all -> 0x002e }} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x003f A:{SYNTHETIC, Splitter: B:23:0x003f} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x004a A:{SYNTHETIC, Splitter: B:28:0x004a} */
    public boolean encode(@androidx.annotation.NonNull java.io.InputStream r5, @androidx.annotation.NonNull java.io.File r6, @androidx.annotation.NonNull com.bumptech.glide.load.Options r7) {
        /*
        r4 = this;
        r7 = "StreamEncoder";
        r0 = r4.byteArrayPool;
        r1 = byte[].class;
        r2 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        r0 = r0.get(r2, r1);
        r0 = (byte[]) r0;
        r1 = 0;
        r2 = 0;
        r3 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x0030 }
        r3.<init>(r6);	 Catch:{ IOException -> 0x0030 }
    L_0x0015:
        r6 = r5.read(r0);	 Catch:{ IOException -> 0x002b, all -> 0x0028 }
        r2 = -1;
        if (r6 == r2) goto L_0x0020;
    L_0x001c:
        r3.write(r0, r1, r6);	 Catch:{ IOException -> 0x002b, all -> 0x0028 }
        goto L_0x0015;
    L_0x0020:
        r3.close();	 Catch:{ IOException -> 0x002b, all -> 0x0028 }
        r1 = 1;
        r3.close();	 Catch:{ IOException -> 0x0042 }
        goto L_0x0042;
    L_0x0028:
        r5 = move-exception;
        r2 = r3;
        goto L_0x0048;
    L_0x002b:
        r5 = move-exception;
        r2 = r3;
        goto L_0x0031;
    L_0x002e:
        r5 = move-exception;
        goto L_0x0048;
    L_0x0030:
        r5 = move-exception;
    L_0x0031:
        r6 = 3;
        r6 = android.util.Log.isLoggable(r7, r6);	 Catch:{ all -> 0x002e }
        if (r6 == 0) goto L_0x003d;
    L_0x0038:
        r6 = "Failed to encode data onto the OutputStream";
        android.util.Log.d(r7, r6, r5);	 Catch:{ all -> 0x002e }
    L_0x003d:
        if (r2 == 0) goto L_0x0042;
    L_0x003f:
        r2.close();	 Catch:{ IOException -> 0x0042 }
    L_0x0042:
        r5 = r4.byteArrayPool;
        r5.put(r0);
        return r1;
    L_0x0048:
        if (r2 == 0) goto L_0x004d;
    L_0x004a:
        r2.close();	 Catch:{ IOException -> 0x004d }
    L_0x004d:
        r6 = r4.byteArrayPool;
        r6.put(r0);
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.model.StreamEncoder.encode(java.io.InputStream, java.io.File, com.bumptech.glide.load.Options):boolean");
    }
}
