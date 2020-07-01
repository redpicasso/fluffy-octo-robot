package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;

public class BitmapEncoder implements ResourceEncoder<Bitmap> {
    public static final Option<CompressFormat> COMPRESSION_FORMAT = Option.memory("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionFormat");
    public static final Option<Integer> COMPRESSION_QUALITY = Option.memory("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionQuality", Integer.valueOf(90));
    private static final String TAG = "BitmapEncoder";
    @Nullable
    private final ArrayPool arrayPool;

    public BitmapEncoder(@NonNull ArrayPool arrayPool) {
        this.arrayPool = arrayPool;
    }

    @Deprecated
    public BitmapEncoder() {
        this.arrayPool = null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0061 A:{Catch:{ all -> 0x0057 }} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00bc A:{SYNTHETIC, Splitter: B:37:0x00bc} */
    /* JADX WARNING: Missing block: B:28:0x0066, code:
            if (r6 == null) goto L_0x0069;
     */
    public boolean encode(@androidx.annotation.NonNull com.bumptech.glide.load.engine.Resource<android.graphics.Bitmap> r9, @androidx.annotation.NonNull java.io.File r10, @androidx.annotation.NonNull com.bumptech.glide.load.Options r11) {
        /*
        r8 = this;
        r0 = "BitmapEncoder";
        r9 = r9.get();
        r9 = (android.graphics.Bitmap) r9;
        r1 = r8.getFormat(r9, r11);
        r2 = r9.getWidth();
        r2 = java.lang.Integer.valueOf(r2);
        r3 = r9.getHeight();
        r3 = java.lang.Integer.valueOf(r3);
        r4 = "encode: [%dx%d] %s";
        com.bumptech.glide.util.pool.GlideTrace.beginSectionFormat(r4, r2, r3, r1);
        r2 = com.bumptech.glide.util.LogTime.getLogTime();	 Catch:{ all -> 0x00c0 }
        r4 = COMPRESSION_QUALITY;	 Catch:{ all -> 0x00c0 }
        r4 = r11.get(r4);	 Catch:{ all -> 0x00c0 }
        r4 = (java.lang.Integer) r4;	 Catch:{ all -> 0x00c0 }
        r4 = r4.intValue();	 Catch:{ all -> 0x00c0 }
        r5 = 0;
        r6 = 0;
        r7 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x0059 }
        r7.<init>(r10);	 Catch:{ IOException -> 0x0059 }
        r10 = r8.arrayPool;	 Catch:{ IOException -> 0x0054, all -> 0x0051 }
        if (r10 == 0) goto L_0x0045;
    L_0x003c:
        r10 = new com.bumptech.glide.load.data.BufferedOutputStream;	 Catch:{ IOException -> 0x0054, all -> 0x0051 }
        r6 = r8.arrayPool;	 Catch:{ IOException -> 0x0054, all -> 0x0051 }
        r10.<init>(r7, r6);	 Catch:{ IOException -> 0x0054, all -> 0x0051 }
        r6 = r10;
        goto L_0x0046;
    L_0x0045:
        r6 = r7;
    L_0x0046:
        r9.compress(r1, r4, r6);	 Catch:{ IOException -> 0x0059 }
        r6.close();	 Catch:{ IOException -> 0x0059 }
        r5 = 1;
    L_0x004d:
        r6.close();	 Catch:{ IOException -> 0x0069 }
        goto L_0x0069;
    L_0x0051:
        r9 = move-exception;
        r6 = r7;
        goto L_0x00ba;
    L_0x0054:
        r10 = move-exception;
        r6 = r7;
        goto L_0x005a;
    L_0x0057:
        r9 = move-exception;
        goto L_0x00ba;
    L_0x0059:
        r10 = move-exception;
    L_0x005a:
        r4 = 3;
        r4 = android.util.Log.isLoggable(r0, r4);	 Catch:{ all -> 0x0057 }
        if (r4 == 0) goto L_0x0066;
    L_0x0061:
        r4 = "Failed to encode Bitmap";
        android.util.Log.d(r0, r4, r10);	 Catch:{ all -> 0x0057 }
    L_0x0066:
        if (r6 == 0) goto L_0x0069;
    L_0x0068:
        goto L_0x004d;
    L_0x0069:
        r10 = 2;
        r10 = android.util.Log.isLoggable(r0, r10);	 Catch:{ all -> 0x00c0 }
        if (r10 == 0) goto L_0x00b6;
    L_0x0070:
        r10 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00c0 }
        r10.<init>();	 Catch:{ all -> 0x00c0 }
        r4 = "Compressed with type: ";
        r10.append(r4);	 Catch:{ all -> 0x00c0 }
        r10.append(r1);	 Catch:{ all -> 0x00c0 }
        r1 = " of size ";
        r10.append(r1);	 Catch:{ all -> 0x00c0 }
        r1 = com.bumptech.glide.util.Util.getBitmapByteSize(r9);	 Catch:{ all -> 0x00c0 }
        r10.append(r1);	 Catch:{ all -> 0x00c0 }
        r1 = " in ";
        r10.append(r1);	 Catch:{ all -> 0x00c0 }
        r1 = com.bumptech.glide.util.LogTime.getElapsedMillis(r2);	 Catch:{ all -> 0x00c0 }
        r10.append(r1);	 Catch:{ all -> 0x00c0 }
        r1 = ", options format: ";
        r10.append(r1);	 Catch:{ all -> 0x00c0 }
        r1 = COMPRESSION_FORMAT;	 Catch:{ all -> 0x00c0 }
        r11 = r11.get(r1);	 Catch:{ all -> 0x00c0 }
        r10.append(r11);	 Catch:{ all -> 0x00c0 }
        r11 = ", hasAlpha: ";
        r10.append(r11);	 Catch:{ all -> 0x00c0 }
        r9 = r9.hasAlpha();	 Catch:{ all -> 0x00c0 }
        r10.append(r9);	 Catch:{ all -> 0x00c0 }
        r9 = r10.toString();	 Catch:{ all -> 0x00c0 }
        android.util.Log.v(r0, r9);	 Catch:{ all -> 0x00c0 }
    L_0x00b6:
        com.bumptech.glide.util.pool.GlideTrace.endSection();
        return r5;
    L_0x00ba:
        if (r6 == 0) goto L_0x00bf;
    L_0x00bc:
        r6.close();	 Catch:{ IOException -> 0x00bf }
    L_0x00bf:
        throw r9;	 Catch:{ all -> 0x00c0 }
    L_0x00c0:
        r9 = move-exception;
        com.bumptech.glide.util.pool.GlideTrace.endSection();
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.resource.bitmap.BitmapEncoder.encode(com.bumptech.glide.load.engine.Resource, java.io.File, com.bumptech.glide.load.Options):boolean");
    }

    private CompressFormat getFormat(Bitmap bitmap, Options options) {
        CompressFormat compressFormat = (CompressFormat) options.get(COMPRESSION_FORMAT);
        if (compressFormat != null) {
            return compressFormat;
        }
        if (bitmap.hasAlpha()) {
            return CompressFormat.PNG;
        }
        return CompressFormat.JPEG;
    }

    @NonNull
    public EncodeStrategy getEncodeStrategy(@NonNull Options options) {
        return EncodeStrategy.TRANSFORMED;
    }
}
