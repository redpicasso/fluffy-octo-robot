package com.bumptech.glide.gifdecoder;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.gifdecoder.GifDecoder.BitmapProvider;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class StandardGifDecoder implements GifDecoder {
    private static final int BYTES_PER_INTEGER = 4;
    @ColorInt
    private static final int COLOR_TRANSPARENT_BLACK = 0;
    private static final int INITIAL_FRAME_POINTER = -1;
    private static final int MASK_INT_LOWEST_BYTE = 255;
    private static final int MAX_STACK_SIZE = 4096;
    private static final int NULL_CODE = -1;
    private static final String TAG = "StandardGifDecoder";
    @ColorInt
    private int[] act;
    @NonNull
    private Config bitmapConfig;
    private final BitmapProvider bitmapProvider;
    private byte[] block;
    private int downsampledHeight;
    private int downsampledWidth;
    private int framePointer;
    private GifHeader header;
    @Nullable
    private Boolean isFirstFrameTransparent;
    private byte[] mainPixels;
    @ColorInt
    private int[] mainScratch;
    private GifHeaderParser parser;
    @ColorInt
    private final int[] pct;
    private byte[] pixelStack;
    private short[] prefix;
    private Bitmap previousImage;
    private ByteBuffer rawData;
    private int sampleSize;
    private boolean savePrevious;
    private int status;
    private byte[] suffix;

    public StandardGifDecoder(@NonNull BitmapProvider bitmapProvider, GifHeader gifHeader, ByteBuffer byteBuffer) {
        this(bitmapProvider, gifHeader, byteBuffer, 1);
    }

    public StandardGifDecoder(@NonNull BitmapProvider bitmapProvider, GifHeader gifHeader, ByteBuffer byteBuffer, int i) {
        this(bitmapProvider);
        setData(gifHeader, byteBuffer, i);
    }

    public StandardGifDecoder(@NonNull BitmapProvider bitmapProvider) {
        this.pct = new int[256];
        this.bitmapConfig = Config.ARGB_8888;
        this.bitmapProvider = bitmapProvider;
        this.header = new GifHeader();
    }

    public int getWidth() {
        return this.header.width;
    }

    public int getHeight() {
        return this.header.height;
    }

    @NonNull
    public ByteBuffer getData() {
        return this.rawData;
    }

    public int getStatus() {
        return this.status;
    }

    public void advance() {
        this.framePointer = (this.framePointer + 1) % this.header.frameCount;
    }

    public int getDelay(int i) {
        return (i < 0 || i >= this.header.frameCount) ? -1 : ((GifFrame) this.header.frames.get(i)).delay;
    }

    public int getNextDelay() {
        if (this.header.frameCount > 0) {
            int i = this.framePointer;
            if (i >= 0) {
                return getDelay(i);
            }
        }
        return 0;
    }

    public int getFrameCount() {
        return this.header.frameCount;
    }

    public int getCurrentFrameIndex() {
        return this.framePointer;
    }

    public void resetFrameIndex() {
        this.framePointer = -1;
    }

    @Deprecated
    public int getLoopCount() {
        if (this.header.loopCount == -1) {
            return 1;
        }
        return this.header.loopCount;
    }

    public int getNetscapeLoopCount() {
        return this.header.loopCount;
    }

    public int getTotalIterationCount() {
        if (this.header.loopCount == -1) {
            return 1;
        }
        if (this.header.loopCount == 0) {
            return 0;
        }
        return this.header.loopCount + 1;
    }

    public int getByteSize() {
        return (this.rawData.limit() + this.mainPixels.length) + (this.mainScratch.length * 4);
    }

    /* JADX WARNING: Missing block: B:45:0x00e9, code:
            return null;
     */
    @androidx.annotation.Nullable
    public synchronized android.graphics.Bitmap getNextFrame() {
        /*
        r7 = this;
        monitor-enter(r7);
        r0 = r7.header;	 Catch:{ all -> 0x00ea }
        r0 = r0.frameCount;	 Catch:{ all -> 0x00ea }
        r1 = 3;
        r2 = 1;
        if (r0 <= 0) goto L_0x000d;
    L_0x0009:
        r0 = r7.framePointer;	 Catch:{ all -> 0x00ea }
        if (r0 >= 0) goto L_0x003b;
    L_0x000d:
        r0 = TAG;	 Catch:{ all -> 0x00ea }
        r0 = android.util.Log.isLoggable(r0, r1);	 Catch:{ all -> 0x00ea }
        if (r0 == 0) goto L_0x0039;
    L_0x0015:
        r0 = TAG;	 Catch:{ all -> 0x00ea }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00ea }
        r3.<init>();	 Catch:{ all -> 0x00ea }
        r4 = "Unable to decode frame, frameCount=";
        r3.append(r4);	 Catch:{ all -> 0x00ea }
        r4 = r7.header;	 Catch:{ all -> 0x00ea }
        r4 = r4.frameCount;	 Catch:{ all -> 0x00ea }
        r3.append(r4);	 Catch:{ all -> 0x00ea }
        r4 = ", framePointer=";
        r3.append(r4);	 Catch:{ all -> 0x00ea }
        r4 = r7.framePointer;	 Catch:{ all -> 0x00ea }
        r3.append(r4);	 Catch:{ all -> 0x00ea }
        r3 = r3.toString();	 Catch:{ all -> 0x00ea }
        android.util.Log.d(r0, r3);	 Catch:{ all -> 0x00ea }
    L_0x0039:
        r7.status = r2;	 Catch:{ all -> 0x00ea }
    L_0x003b:
        r0 = r7.status;	 Catch:{ all -> 0x00ea }
        r3 = 0;
        if (r0 == r2) goto L_0x00c8;
    L_0x0040:
        r0 = r7.status;	 Catch:{ all -> 0x00ea }
        r4 = 2;
        if (r0 != r4) goto L_0x0047;
    L_0x0045:
        goto L_0x00c8;
    L_0x0047:
        r0 = 0;
        r7.status = r0;	 Catch:{ all -> 0x00ea }
        r4 = r7.block;	 Catch:{ all -> 0x00ea }
        if (r4 != 0) goto L_0x0058;
    L_0x004e:
        r4 = r7.bitmapProvider;	 Catch:{ all -> 0x00ea }
        r5 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r4 = r4.obtainByteArray(r5);	 Catch:{ all -> 0x00ea }
        r7.block = r4;	 Catch:{ all -> 0x00ea }
    L_0x0058:
        r4 = r7.header;	 Catch:{ all -> 0x00ea }
        r4 = r4.frames;	 Catch:{ all -> 0x00ea }
        r5 = r7.framePointer;	 Catch:{ all -> 0x00ea }
        r4 = r4.get(r5);	 Catch:{ all -> 0x00ea }
        r4 = (com.bumptech.glide.gifdecoder.GifFrame) r4;	 Catch:{ all -> 0x00ea }
        r5 = r7.framePointer;	 Catch:{ all -> 0x00ea }
        r5 = r5 - r2;
        if (r5 < 0) goto L_0x0074;
    L_0x0069:
        r6 = r7.header;	 Catch:{ all -> 0x00ea }
        r6 = r6.frames;	 Catch:{ all -> 0x00ea }
        r5 = r6.get(r5);	 Catch:{ all -> 0x00ea }
        r5 = (com.bumptech.glide.gifdecoder.GifFrame) r5;	 Catch:{ all -> 0x00ea }
        goto L_0x0075;
    L_0x0074:
        r5 = r3;
    L_0x0075:
        r6 = r4.lct;	 Catch:{ all -> 0x00ea }
        if (r6 == 0) goto L_0x007c;
    L_0x0079:
        r6 = r4.lct;	 Catch:{ all -> 0x00ea }
        goto L_0x0080;
    L_0x007c:
        r6 = r7.header;	 Catch:{ all -> 0x00ea }
        r6 = r6.gct;	 Catch:{ all -> 0x00ea }
    L_0x0080:
        r7.act = r6;	 Catch:{ all -> 0x00ea }
        r6 = r7.act;	 Catch:{ all -> 0x00ea }
        if (r6 != 0) goto L_0x00aa;
    L_0x0086:
        r0 = TAG;	 Catch:{ all -> 0x00ea }
        r0 = android.util.Log.isLoggable(r0, r1);	 Catch:{ all -> 0x00ea }
        if (r0 == 0) goto L_0x00a6;
    L_0x008e:
        r0 = TAG;	 Catch:{ all -> 0x00ea }
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00ea }
        r1.<init>();	 Catch:{ all -> 0x00ea }
        r4 = "No valid color table found for frame #";
        r1.append(r4);	 Catch:{ all -> 0x00ea }
        r4 = r7.framePointer;	 Catch:{ all -> 0x00ea }
        r1.append(r4);	 Catch:{ all -> 0x00ea }
        r1 = r1.toString();	 Catch:{ all -> 0x00ea }
        android.util.Log.d(r0, r1);	 Catch:{ all -> 0x00ea }
    L_0x00a6:
        r7.status = r2;	 Catch:{ all -> 0x00ea }
        monitor-exit(r7);
        return r3;
    L_0x00aa:
        r1 = r4.transparency;	 Catch:{ all -> 0x00ea }
        if (r1 == 0) goto L_0x00c2;
    L_0x00ae:
        r1 = r7.act;	 Catch:{ all -> 0x00ea }
        r2 = r7.pct;	 Catch:{ all -> 0x00ea }
        r3 = r7.act;	 Catch:{ all -> 0x00ea }
        r3 = r3.length;	 Catch:{ all -> 0x00ea }
        java.lang.System.arraycopy(r1, r0, r2, r0, r3);	 Catch:{ all -> 0x00ea }
        r1 = r7.pct;	 Catch:{ all -> 0x00ea }
        r7.act = r1;	 Catch:{ all -> 0x00ea }
        r1 = r7.act;	 Catch:{ all -> 0x00ea }
        r2 = r4.transIndex;	 Catch:{ all -> 0x00ea }
        r1[r2] = r0;	 Catch:{ all -> 0x00ea }
    L_0x00c2:
        r0 = r7.setPixels(r4, r5);	 Catch:{ all -> 0x00ea }
        monitor-exit(r7);
        return r0;
    L_0x00c8:
        r0 = TAG;	 Catch:{ all -> 0x00ea }
        r0 = android.util.Log.isLoggable(r0, r1);	 Catch:{ all -> 0x00ea }
        if (r0 == 0) goto L_0x00e8;
    L_0x00d0:
        r0 = TAG;	 Catch:{ all -> 0x00ea }
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00ea }
        r1.<init>();	 Catch:{ all -> 0x00ea }
        r2 = "Unable to decode frame, status=";
        r1.append(r2);	 Catch:{ all -> 0x00ea }
        r2 = r7.status;	 Catch:{ all -> 0x00ea }
        r1.append(r2);	 Catch:{ all -> 0x00ea }
        r1 = r1.toString();	 Catch:{ all -> 0x00ea }
        android.util.Log.d(r0, r1);	 Catch:{ all -> 0x00ea }
    L_0x00e8:
        monitor-exit(r7);
        return r3;
    L_0x00ea:
        r0 = move-exception;
        monitor-exit(r7);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.gifdecoder.StandardGifDecoder.getNextFrame():android.graphics.Bitmap");
    }

    public int read(@Nullable InputStream inputStream, int i) {
        if (inputStream != null) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i > 0 ? i + 4096 : 16384);
                byte[] bArr = new byte[16384];
                while (true) {
                    int read = inputStream.read(bArr, 0, bArr.length);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                byteArrayOutputStream.flush();
                read(byteArrayOutputStream.toByteArray());
            } catch (Throwable e) {
                Log.w(TAG, "Error reading data from stream", e);
            }
        } else {
            this.status = 2;
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Throwable e2) {
                Log.w(TAG, "Error closing stream", e2);
            }
        }
        return this.status;
    }

    public void clear() {
        this.header = null;
        byte[] bArr = this.mainPixels;
        if (bArr != null) {
            this.bitmapProvider.release(bArr);
        }
        int[] iArr = this.mainScratch;
        if (iArr != null) {
            this.bitmapProvider.release(iArr);
        }
        Bitmap bitmap = this.previousImage;
        if (bitmap != null) {
            this.bitmapProvider.release(bitmap);
        }
        this.previousImage = null;
        this.rawData = null;
        this.isFirstFrameTransparent = null;
        byte[] bArr2 = this.block;
        if (bArr2 != null) {
            this.bitmapProvider.release(bArr2);
        }
    }

    public synchronized void setData(@NonNull GifHeader gifHeader, @NonNull byte[] bArr) {
        setData(gifHeader, ByteBuffer.wrap(bArr));
    }

    public synchronized void setData(@NonNull GifHeader gifHeader, @NonNull ByteBuffer byteBuffer) {
        setData(gifHeader, byteBuffer, 1);
    }

    public synchronized void setData(@NonNull GifHeader gifHeader, @NonNull ByteBuffer byteBuffer, int i) {
        if (i > 0) {
            i = Integer.highestOneBit(i);
            this.status = 0;
            this.header = gifHeader;
            this.framePointer = -1;
            this.rawData = byteBuffer.asReadOnlyBuffer();
            this.rawData.position(0);
            this.rawData.order(ByteOrder.LITTLE_ENDIAN);
            this.savePrevious = false;
            for (GifFrame gifFrame : gifHeader.frames) {
                if (gifFrame.dispose == 3) {
                    this.savePrevious = true;
                    break;
                }
            }
            this.sampleSize = i;
            this.downsampledWidth = gifHeader.width / i;
            this.downsampledHeight = gifHeader.height / i;
            this.mainPixels = this.bitmapProvider.obtainByteArray(gifHeader.width * gifHeader.height);
            this.mainScratch = this.bitmapProvider.obtainIntArray(this.downsampledWidth * this.downsampledHeight);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Sample size must be >=0, not: ");
            stringBuilder.append(i);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    @NonNull
    private GifHeaderParser getHeaderParser() {
        if (this.parser == null) {
            this.parser = new GifHeaderParser();
        }
        return this.parser;
    }

    public synchronized int read(@Nullable byte[] bArr) {
        this.header = getHeaderParser().setData(bArr).parseHeader();
        if (bArr != null) {
            setData(this.header, bArr);
        }
        return this.status;
    }

    public void setDefaultBitmapConfig(@NonNull Config config) {
        if (config == Config.ARGB_8888 || config == Config.RGB_565) {
            this.bitmapConfig = config;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported format: ");
        stringBuilder.append(config);
        stringBuilder.append(", must be one of ");
        stringBuilder.append(Config.ARGB_8888);
        stringBuilder.append(" or ");
        stringBuilder.append(Config.RGB_565);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private Bitmap setPixels(GifFrame gifFrame, GifFrame gifFrame2) {
        Bitmap bitmap;
        int i;
        int[] iArr = this.mainScratch;
        int i2 = 0;
        if (gifFrame2 == null) {
            Bitmap bitmap2 = this.previousImage;
            if (bitmap2 != null) {
                this.bitmapProvider.release(bitmap2);
            }
            this.previousImage = null;
            Arrays.fill(iArr, 0);
        }
        if (gifFrame2 != null && gifFrame2.dispose == 3 && this.previousImage == null) {
            Arrays.fill(iArr, 0);
        }
        if (gifFrame2 != null && gifFrame2.dispose > 0) {
            if (gifFrame2.dispose == 2) {
                int i3;
                if (!gifFrame.transparency) {
                    i3 = this.header.bgColor;
                    if (gifFrame.lct == null || this.header.bgIndex != gifFrame.transIndex) {
                        i2 = i3;
                    }
                } else if (this.framePointer == 0) {
                    this.isFirstFrameTransparent = Boolean.valueOf(true);
                }
                i3 = gifFrame2.ih / this.sampleSize;
                int i4 = gifFrame2.iy / this.sampleSize;
                int i5 = gifFrame2.iw / this.sampleSize;
                int i6 = gifFrame2.ix / this.sampleSize;
                int i7 = this.downsampledWidth;
                i4 = (i4 * i7) + i6;
                i3 = (i3 * i7) + i4;
                while (i4 < i3) {
                    i6 = i4 + i5;
                    for (i7 = i4; i7 < i6; i7++) {
                        iArr[i7] = i2;
                    }
                    i4 += this.downsampledWidth;
                }
            } else if (gifFrame2.dispose == 3) {
                bitmap = this.previousImage;
                if (bitmap != null) {
                    i = this.downsampledWidth;
                    bitmap.getPixels(iArr, 0, i, 0, 0, i, this.downsampledHeight);
                }
            }
        }
        decodeBitmapData(gifFrame);
        if (gifFrame.interlace || this.sampleSize != 1) {
            copyCopyIntoScratchRobust(gifFrame);
        } else {
            copyIntoScratchFast(gifFrame);
        }
        if (this.savePrevious && (gifFrame.dispose == 0 || gifFrame.dispose == 1)) {
            if (this.previousImage == null) {
                this.previousImage = getNextBitmap();
            }
            bitmap = this.previousImage;
            i = this.downsampledWidth;
            bitmap.setPixels(iArr, 0, i, 0, 0, i, this.downsampledHeight);
        }
        Bitmap nextBitmap = getNextBitmap();
        i = this.downsampledWidth;
        nextBitmap.setPixels(iArr, 0, i, 0, 0, i, this.downsampledHeight);
        return nextBitmap;
    }

    private void copyIntoScratchFast(GifFrame gifFrame) {
        GifFrame gifFrame2 = gifFrame;
        int[] iArr = this.mainScratch;
        int i = gifFrame2.ih;
        int i2 = gifFrame2.iy;
        int i3 = gifFrame2.iw;
        int i4 = gifFrame2.ix;
        Object obj = this.framePointer == 0 ? 1 : null;
        int i5 = this.downsampledWidth;
        byte[] bArr = this.mainPixels;
        int[] iArr2 = this.act;
        int i6 = 0;
        int i7 = -1;
        while (i6 < i) {
            int i8 = (i6 + i2) * i5;
            int i9 = i8 + i4;
            int i10 = i9 + i3;
            int i11 = i8 + i5;
            if (i11 < i10) {
                i10 = i11;
            }
            i8 = gifFrame2.iw * i6;
            i11 = i9;
            while (i11 < i10) {
                byte b = bArr[i8];
                int i12 = b & 255;
                if (i12 != i7) {
                    i12 = iArr2[i12];
                    if (i12 != 0) {
                        iArr[i11] = i12;
                    } else {
                        i7 = b;
                    }
                }
                i8++;
                i11++;
                gifFrame2 = gifFrame;
            }
            i6++;
            gifFrame2 = gifFrame;
        }
        boolean z = (this.isFirstFrameTransparent != null || obj == null || i7 == -1) ? false : true;
        this.isFirstFrameTransparent = Boolean.valueOf(z);
    }

    private void copyCopyIntoScratchRobust(GifFrame gifFrame) {
        GifFrame gifFrame2 = gifFrame;
        int[] iArr = this.mainScratch;
        int i = gifFrame2.ih / this.sampleSize;
        int i2 = gifFrame2.iy / this.sampleSize;
        int i3 = gifFrame2.iw / this.sampleSize;
        int i4 = gifFrame2.ix / this.sampleSize;
        int i5 = this.framePointer;
        Boolean valueOf = Boolean.valueOf(true);
        Object obj = i5 == 0 ? 1 : null;
        int i6 = this.sampleSize;
        int i7 = this.downsampledWidth;
        int i8 = this.downsampledHeight;
        byte[] bArr = this.mainPixels;
        int[] iArr2 = this.act;
        Boolean bool = this.isFirstFrameTransparent;
        int i9 = 0;
        int i10 = 0;
        int i11 = 1;
        int i12 = 8;
        while (i9 < i) {
            int i13;
            int i14;
            int i15;
            int i16;
            Boolean bool2 = valueOf;
            if (gifFrame2.interlace) {
                if (i10 >= i) {
                    i13 = i;
                    i = i11 + 1;
                    if (i == 2) {
                        i10 = 4;
                    } else if (i == 3) {
                        i10 = 2;
                        i12 = 4;
                    } else if (i == 4) {
                        i10 = 1;
                        i12 = 2;
                    }
                } else {
                    i13 = i;
                    i = i11;
                }
                i14 = i10 + i12;
                i11 = i;
            } else {
                i13 = i;
                i14 = i10;
                i10 = i9;
            }
            i10 += i2;
            Object obj2 = i6 == 1 ? 1 : null;
            if (i10 < i8) {
                i10 *= i7;
                int i17 = i10 + i4;
                i = i17 + i3;
                i10 += i7;
                if (i10 < i) {
                    i = i10;
                }
                i15 = i2;
                i10 = (i9 * i6) * gifFrame2.iw;
                if (obj2 != null) {
                    i2 = i17;
                    while (i2 < i) {
                        i16 = i3;
                        i3 = iArr2[bArr[i10] & 255];
                        if (i3 != 0) {
                            iArr[i2] = i3;
                        } else if (obj != null && r17 == null) {
                            bool = bool2;
                        }
                        i10 += i6;
                        i2++;
                        i3 = i16;
                    }
                } else {
                    i16 = i3;
                    i2 = ((i - i17) * i6) + i10;
                    i3 = i17;
                    while (i3 < i) {
                        i17 = i;
                        i = averageColorsNear(i10, i2, gifFrame2.iw);
                        if (i != 0) {
                            iArr[i3] = i;
                        } else if (obj != null && bool == null) {
                            bool = bool2;
                        }
                        i10 += i6;
                        i3++;
                        i = i17;
                    }
                    i9++;
                    i10 = i14;
                    i3 = i16;
                    valueOf = bool2;
                    i = i13;
                    i2 = i15;
                }
            } else {
                i15 = i2;
            }
            i16 = i3;
            i9++;
            i10 = i14;
            i3 = i16;
            valueOf = bool2;
            i = i13;
            i2 = i15;
        }
        if (this.isFirstFrameTransparent == null) {
            boolean z;
            if (bool == null) {
                z = false;
            } else {
                z = bool.booleanValue();
            }
            this.isFirstFrameTransparent = Boolean.valueOf(z);
        }
    }

    @ColorInt
    private int averageColorsNear(int i, int i2, int i3) {
        int i4 = i;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        while (i4 < this.sampleSize + i) {
            byte[] bArr = this.mainPixels;
            if (i4 >= bArr.length || i4 >= i2) {
                break;
            }
            int i10 = this.act[bArr[i4] & 255];
            if (i10 != 0) {
                i5 += (i10 >> 24) & 255;
                i6 += (i10 >> 16) & 255;
                i7 += (i10 >> 8) & 255;
                i8 += i10 & 255;
                i9++;
            }
            i4++;
        }
        i += i3;
        i3 = i;
        while (i3 < this.sampleSize + i) {
            byte[] bArr2 = this.mainPixels;
            if (i3 >= bArr2.length || i3 >= i2) {
                break;
            }
            i4 = this.act[bArr2[i3] & 255];
            if (i4 != 0) {
                i5 += (i4 >> 24) & 255;
                i6 += (i4 >> 16) & 255;
                i7 += (i4 >> 8) & 255;
                i8 += i4 & 255;
                i9++;
            }
            i3++;
        }
        if (i9 == 0) {
            return 0;
        }
        return ((((i5 / i9) << 24) | ((i6 / i9) << 16)) | ((i7 / i9) << 8)) | (i8 / i9);
    }

    private void decodeBitmapData(GifFrame gifFrame) {
        short s;
        StandardGifDecoder standardGifDecoder = this;
        GifFrame gifFrame2 = gifFrame;
        if (gifFrame2 != null) {
            standardGifDecoder.rawData.position(gifFrame2.bufferFrameStart);
        }
        if (gifFrame2 == null) {
            s = standardGifDecoder.header.width * standardGifDecoder.header.height;
        } else {
            s = gifFrame2.ih * gifFrame2.iw;
        }
        byte[] bArr = standardGifDecoder.mainPixels;
        if (bArr == null || bArr.length < s) {
            standardGifDecoder.mainPixels = standardGifDecoder.bitmapProvider.obtainByteArray(s);
        }
        bArr = standardGifDecoder.mainPixels;
        if (standardGifDecoder.prefix == null) {
            standardGifDecoder.prefix = new short[4096];
        }
        short[] sArr = standardGifDecoder.prefix;
        if (standardGifDecoder.suffix == null) {
            standardGifDecoder.suffix = new byte[4096];
        }
        byte[] bArr2 = standardGifDecoder.suffix;
        if (standardGifDecoder.pixelStack == null) {
            standardGifDecoder.pixelStack = new byte[4097];
        }
        byte[] bArr3 = standardGifDecoder.pixelStack;
        int readByte = readByte();
        int i = 1 << readByte;
        int i2 = i + 1;
        int i3 = i + 2;
        readByte++;
        int i4 = (1 << readByte) - 1;
        short s2 = (short) 0;
        for (int i5 = 0; i5 < i; i5++) {
            sArr[i5] = (short) 0;
            bArr2[i5] = (byte) i5;
        }
        byte[] bArr4 = standardGifDecoder.block;
        int i6 = readByte;
        int i7 = i3;
        int i8 = i4;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        int i14 = -1;
        int i15 = 0;
        int i16 = 0;
        while (s2 < s) {
            if (i9 == 0) {
                i9 = readBlock();
                if (i9 <= 0) {
                    standardGifDecoder.status = 3;
                    break;
                }
                i12 = 0;
            }
            i11 += (bArr4[i12] & 255) << i10;
            i12++;
            i9--;
            int i17 = i10 + 8;
            int i18 = i14;
            int i19 = i15;
            int i20 = i7;
            i14 = i13;
            short s3 = s2;
            int i21 = i6;
            while (i17 >= i21) {
                int i22 = i11 & i8;
                i11 >>= i21;
                i17 -= i21;
                if (i22 == i) {
                    i21 = readByte;
                    i20 = i3;
                    i8 = i4;
                    i18 = -1;
                } else if (i22 == i2) {
                    i10 = i17;
                    i6 = i21;
                    s2 = s3;
                    i13 = i14;
                    i7 = i20;
                    i15 = i19;
                    i14 = i18;
                    break;
                } else if (i18 == -1) {
                    bArr[i14] = bArr2[i22];
                    i14++;
                    s3++;
                    standardGifDecoder = this;
                    i18 = i22;
                    i19 = i18;
                } else {
                    int i23 = i20;
                    if (i22 >= i23) {
                        i7 = i17;
                        bArr3[i16] = (byte) i19;
                        i16++;
                        i17 = i18;
                    } else {
                        i7 = i17;
                        i17 = i22;
                    }
                    while (i17 >= i) {
                        bArr3[i16] = bArr2[i17];
                        i16++;
                        i17 = sArr[i17];
                    }
                    i17 = bArr2[i17] & 255;
                    i6 = readByte;
                    byte b = (byte) i17;
                    bArr[i14] = b;
                    while (true) {
                        i14++;
                        s3 = i13 + 1;
                        if (i16 <= 0) {
                            break;
                        }
                        i16--;
                        bArr[i14] = bArr3[i16];
                    }
                    i20 = i17;
                    if (i23 < 4096) {
                        sArr[i23] = (short) i18;
                        bArr2[i23] = b;
                        i23++;
                        if ((i23 & i8) == 0 && i23 < 4096) {
                            i21++;
                            i8 += i23;
                        }
                    }
                    i18 = i22;
                    i17 = i7;
                    readByte = i6;
                    i19 = i20;
                    i20 = i23;
                    standardGifDecoder = this;
                }
            }
            i6 = i21;
            i15 = i19;
            s2 = s3;
            i13 = i14;
            i10 = i17;
            i7 = i20;
            i14 = i18;
            standardGifDecoder = this;
        }
        Arrays.fill(bArr, i13, s, (byte) 0);
    }

    private int readByte() {
        return this.rawData.get() & 255;
    }

    private int readBlock() {
        int readByte = readByte();
        if (readByte <= 0) {
            return readByte;
        }
        ByteBuffer byteBuffer = this.rawData;
        byteBuffer.get(this.block, 0, Math.min(readByte, byteBuffer.remaining()));
        return readByte;
    }

    private Bitmap getNextBitmap() {
        Boolean bool = this.isFirstFrameTransparent;
        Config config = (bool == null || bool.booleanValue()) ? Config.ARGB_8888 : this.bitmapConfig;
        Bitmap obtain = this.bitmapProvider.obtain(this.downsampledWidth, this.downsampledHeight, config);
        obtain.setHasAlpha(true);
        return obtain;
    }
}
