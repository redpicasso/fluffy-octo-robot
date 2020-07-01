package com.facebook.imagepipeline.platform;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.ColorSpace;
import android.graphics.Rect;
import android.os.Build.VERSION;
import androidx.core.util.Pools.SynchronizedPool;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.streams.LimitedInputStream;
import com.facebook.common.streams.TailAppendingInputStream;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.BitmapPool;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@TargetApi(21)
@ThreadSafe
public abstract class DefaultDecoder implements PlatformDecoder {
    private static final int DECODE_BUFFER_SIZE = 16384;
    private static final byte[] EOI_TAIL = new byte[]{(byte) -1, (byte) -39};
    private static final Class<?> TAG = DefaultDecoder.class;
    private final BitmapPool mBitmapPool;
    @VisibleForTesting
    final SynchronizedPool<ByteBuffer> mDecodeBuffers;
    @Nullable
    private final PreverificationHelper mPreverificationHelper;

    public abstract int getBitmapSize(int i, int i2, Options options);

    public DefaultDecoder(BitmapPool bitmapPool, int i, SynchronizedPool synchronizedPool) {
        this.mPreverificationHelper = VERSION.SDK_INT >= 26 ? new PreverificationHelper() : null;
        this.mBitmapPool = bitmapPool;
        this.mDecodeBuffers = synchronizedPool;
        for (int i2 = 0; i2 < i; i2++) {
            this.mDecodeBuffers.release(ByteBuffer.allocate(16384));
        }
    }

    public CloseableReference<Bitmap> decodeFromEncodedImage(EncodedImage encodedImage, Config config, @Nullable Rect rect) {
        return decodeFromEncodedImageWithColorSpace(encodedImage, config, rect, null);
    }

    public CloseableReference<Bitmap> decodeJPEGFromEncodedImage(EncodedImage encodedImage, Config config, @Nullable Rect rect, int i) {
        return decodeJPEGFromEncodedImageWithColorSpace(encodedImage, config, rect, i, null);
    }

    public CloseableReference<Bitmap> decodeFromEncodedImageWithColorSpace(EncodedImage encodedImage, Config config, @Nullable Rect rect, @Nullable ColorSpace colorSpace) {
        Options decodeOptionsForStream = getDecodeOptionsForStream(encodedImage, config);
        Object obj = decodeOptionsForStream.inPreferredConfig != Config.ARGB_8888 ? 1 : null;
        try {
            encodedImage = decodeFromStream(encodedImage.getInputStream(), decodeOptionsForStream, rect, colorSpace);
            return encodedImage;
        } catch (RuntimeException e) {
            if (obj != null) {
                return decodeFromEncodedImageWithColorSpace(encodedImage, Config.ARGB_8888, rect, colorSpace);
            }
            throw e;
        }
    }

    public CloseableReference<Bitmap> decodeJPEGFromEncodedImageWithColorSpace(EncodedImage encodedImage, Config config, @Nullable Rect rect, int i, @Nullable ColorSpace colorSpace) {
        boolean isCompleteAt = encodedImage.isCompleteAt(i);
        Options decodeOptionsForStream = getDecodeOptionsForStream(encodedImage, config);
        InputStream inputStream = encodedImage.getInputStream();
        Preconditions.checkNotNull(inputStream);
        if (encodedImage.getSize() > i) {
            inputStream = new LimitedInputStream(inputStream, i);
        }
        InputStream tailAppendingInputStream = !isCompleteAt ? new TailAppendingInputStream(inputStream, EOI_TAIL) : inputStream;
        Object obj = decodeOptionsForStream.inPreferredConfig != Config.ARGB_8888 ? 1 : null;
        try {
            encodedImage = decodeFromStream(tailAppendingInputStream, decodeOptionsForStream, rect, colorSpace);
            return encodedImage;
        } catch (RuntimeException e) {
            if (obj != null) {
                return decodeJPEGFromEncodedImageWithColorSpace(encodedImage, Config.ARGB_8888, rect, i, colorSpace);
            }
            throw e;
        }
    }

    protected CloseableReference<Bitmap> decodeStaticImageFromStream(InputStream inputStream, Options options, @Nullable Rect rect) {
        return decodeFromStream(inputStream, options, rect, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a9 A:{Catch:{ IllegalArgumentException -> 0x00d7, RuntimeException -> 0x00ce, all -> 0x00cc, IOException -> 0x00f7 }} */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x00fe  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0086 A:{SYNTHETIC, Splitter: B:37:0x0086} */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a9 A:{Catch:{ IllegalArgumentException -> 0x00d7, RuntimeException -> 0x00ce, all -> 0x00cc, IOException -> 0x00f7 }} */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00b4 A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00a2 A:{Catch:{ IllegalArgumentException -> 0x00d7, RuntimeException -> 0x00ce, all -> 0x00cc, IOException -> 0x00f7 }} */
    private com.facebook.common.references.CloseableReference<android.graphics.Bitmap> decodeFromStream(java.io.InputStream r9, android.graphics.BitmapFactory.Options r10, @javax.annotation.Nullable android.graphics.Rect r11, @javax.annotation.Nullable android.graphics.ColorSpace r12) {
        /*
        r8 = this;
        com.facebook.common.internal.Preconditions.checkNotNull(r9);
        r0 = r10.outWidth;
        r1 = r10.outHeight;
        if (r11 == 0) goto L_0x0017;
    L_0x0009:
        r0 = r11.width();
        r1 = r10.inSampleSize;
        r0 = r0 / r1;
        r1 = r11.height();
        r2 = r10.inSampleSize;
        r1 = r1 / r2;
    L_0x0017:
        r2 = android.os.Build.VERSION.SDK_INT;
        r3 = 26;
        r4 = 1;
        r5 = 0;
        if (r2 < r3) goto L_0x002d;
    L_0x001f:
        r2 = r8.mPreverificationHelper;
        if (r2 == 0) goto L_0x002d;
    L_0x0023:
        r6 = r10.inPreferredConfig;
        r2 = r2.shouldUseHardwareBitmapConfig(r6);
        if (r2 == 0) goto L_0x002d;
    L_0x002b:
        r2 = 1;
        goto L_0x002e;
    L_0x002d:
        r2 = 0;
    L_0x002e:
        r6 = 0;
        if (r11 != 0) goto L_0x0037;
    L_0x0031:
        if (r2 == 0) goto L_0x0037;
    L_0x0033:
        r10.inMutable = r5;
        r2 = r6;
        goto L_0x004d;
    L_0x0037:
        if (r11 == 0) goto L_0x003f;
    L_0x0039:
        if (r2 == 0) goto L_0x003f;
    L_0x003b:
        r2 = android.graphics.Bitmap.Config.ARGB_8888;
        r10.inPreferredConfig = r2;
    L_0x003f:
        r2 = r8.getBitmapSize(r0, r1, r10);
        r7 = r8.mBitmapPool;
        r2 = r7.get(r2);
        r2 = (android.graphics.Bitmap) r2;
        if (r2 == 0) goto L_0x00fe;
    L_0x004d:
        r10.inBitmap = r2;
        r7 = android.os.Build.VERSION.SDK_INT;
        if (r7 < r3) goto L_0x005d;
    L_0x0053:
        if (r12 != 0) goto L_0x005b;
    L_0x0055:
        r12 = android.graphics.ColorSpace.Named.SRGB;
        r12 = android.graphics.ColorSpace.get(r12);
    L_0x005b:
        r10.inPreferredColorSpace = r12;
    L_0x005d:
        r12 = r8.mDecodeBuffers;
        r12 = r12.acquire();
        r12 = (java.nio.ByteBuffer) r12;
        if (r12 != 0) goto L_0x006d;
    L_0x0067:
        r12 = 16384; // 0x4000 float:2.2959E-41 double:8.0948E-320;
        r12 = java.nio.ByteBuffer.allocate(r12);
    L_0x006d:
        r3 = r12.array();	 Catch:{ IllegalArgumentException -> 0x00d7, RuntimeException -> 0x00ce }
        r10.inTempStorage = r3;	 Catch:{ IllegalArgumentException -> 0x00d7, RuntimeException -> 0x00ce }
        if (r11 == 0) goto L_0x00a6;
    L_0x0075:
        if (r2 == 0) goto L_0x00a6;
    L_0x0077:
        r3 = r10.inPreferredConfig;	 Catch:{ IOException -> 0x008d, all -> 0x008a }
        r2.reconfigure(r0, r1, r3);	 Catch:{ IOException -> 0x008d, all -> 0x008a }
        r0 = android.graphics.BitmapRegionDecoder.newInstance(r9, r4);	 Catch:{ IOException -> 0x008d, all -> 0x008a }
        r11 = r0.decodeRegion(r11, r10);	 Catch:{ IOException -> 0x008e }
        if (r0 == 0) goto L_0x00a7;
    L_0x0086:
        r0.recycle();	 Catch:{ IllegalArgumentException -> 0x00d7, RuntimeException -> 0x00ce }
        goto L_0x00a7;
    L_0x008a:
        r10 = move-exception;
        r0 = r6;
        goto L_0x00a0;
    L_0x008d:
        r0 = r6;
    L_0x008e:
        r1 = TAG;	 Catch:{ all -> 0x009f }
        r3 = "Could not decode region %s, decoding full bitmap instead.";
        r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x009f }
        r4[r5] = r11;	 Catch:{ all -> 0x009f }
        com.facebook.common.logging.FLog.e(r1, r3, r4);	 Catch:{ all -> 0x009f }
        if (r0 == 0) goto L_0x00a6;
    L_0x009b:
        r0.recycle();	 Catch:{ IllegalArgumentException -> 0x00d7, RuntimeException -> 0x00ce }
        goto L_0x00a6;
    L_0x009f:
        r10 = move-exception;
    L_0x00a0:
        if (r0 == 0) goto L_0x00a5;
    L_0x00a2:
        r0.recycle();	 Catch:{ IllegalArgumentException -> 0x00d7, RuntimeException -> 0x00ce }
    L_0x00a5:
        throw r10;	 Catch:{ IllegalArgumentException -> 0x00d7, RuntimeException -> 0x00ce }
    L_0x00a6:
        r11 = r6;
    L_0x00a7:
        if (r11 != 0) goto L_0x00ad;
    L_0x00a9:
        r11 = android.graphics.BitmapFactory.decodeStream(r9, r6, r10);	 Catch:{ IllegalArgumentException -> 0x00d7, RuntimeException -> 0x00ce }
    L_0x00ad:
        r9 = r8.mDecodeBuffers;
        r9.release(r12);
        if (r2 == 0) goto L_0x00c5;
    L_0x00b4:
        if (r2 != r11) goto L_0x00b7;
    L_0x00b6:
        goto L_0x00c5;
    L_0x00b7:
        r9 = r8.mBitmapPool;
        r9.release(r2);
        r11.recycle();
        r9 = new java.lang.IllegalStateException;
        r9.<init>();
        throw r9;
    L_0x00c5:
        r9 = r8.mBitmapPool;
        r9 = com.facebook.common.references.CloseableReference.of(r11, r9);
        return r9;
    L_0x00cc:
        r9 = move-exception;
        goto L_0x00f8;
    L_0x00ce:
        r9 = move-exception;
        if (r2 == 0) goto L_0x00d6;
    L_0x00d1:
        r10 = r8.mBitmapPool;	 Catch:{ all -> 0x00cc }
        r10.release(r2);	 Catch:{ all -> 0x00cc }
    L_0x00d6:
        throw r9;	 Catch:{ all -> 0x00cc }
    L_0x00d7:
        r10 = move-exception;
        if (r2 == 0) goto L_0x00df;
    L_0x00da:
        r11 = r8.mBitmapPool;	 Catch:{ all -> 0x00cc }
        r11.release(r2);	 Catch:{ all -> 0x00cc }
    L_0x00df:
        r9.reset();	 Catch:{ IOException -> 0x00f7 }
        r9 = android.graphics.BitmapFactory.decodeStream(r9);	 Catch:{ IOException -> 0x00f7 }
        if (r9 == 0) goto L_0x00f6;
    L_0x00e8:
        r11 = com.facebook.imagepipeline.bitmaps.SimpleBitmapReleaser.getInstance();	 Catch:{ IOException -> 0x00f7 }
        r9 = com.facebook.common.references.CloseableReference.of(r9, r11);	 Catch:{ IOException -> 0x00f7 }
        r10 = r8.mDecodeBuffers;
        r10.release(r12);
        return r9;
    L_0x00f6:
        throw r10;	 Catch:{ IOException -> 0x00f7 }
    L_0x00f7:
        throw r10;	 Catch:{ all -> 0x00cc }
    L_0x00f8:
        r10 = r8.mDecodeBuffers;
        r10.release(r12);
        throw r9;
    L_0x00fe:
        r9 = new java.lang.NullPointerException;
        r10 = "BitmapPool.get returned null";
        r9.<init>(r10);
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.platform.DefaultDecoder.decodeFromStream(java.io.InputStream, android.graphics.BitmapFactory$Options, android.graphics.Rect, android.graphics.ColorSpace):com.facebook.common.references.CloseableReference<android.graphics.Bitmap>");
    }

    private static Options getDecodeOptionsForStream(EncodedImage encodedImage, Config config) {
        Options options = new Options();
        options.inSampleSize = encodedImage.getSampleSize();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(encodedImage.getInputStream(), null, options);
        if (options.outWidth == -1 || options.outHeight == -1) {
            throw new IllegalArgumentException();
        }
        options.inJustDecodeBounds = false;
        options.inDither = true;
        options.inPreferredConfig = config;
        options.inMutable = true;
        return options;
    }
}
