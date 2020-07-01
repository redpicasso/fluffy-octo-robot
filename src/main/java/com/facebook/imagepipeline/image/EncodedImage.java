package com.facebook.imagepipeline.image;

import android.graphics.ColorSpace;
import android.util.Pair;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferInputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.SharedReference;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imageformat.ImageFormatChecker;
import com.facebook.imagepipeline.common.BytesRange;
import com.facebook.imageutils.HeifExifUtil;
import com.facebook.imageutils.JfifUtil;
import com.facebook.imageutils.WebpUtil;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public class EncodedImage implements Closeable {
    public static final int DEFAULT_SAMPLE_SIZE = 1;
    public static final int UNKNOWN_HEIGHT = -1;
    public static final int UNKNOWN_ROTATION_ANGLE = -1;
    public static final int UNKNOWN_STREAM_SIZE = -1;
    public static final int UNKNOWN_WIDTH = -1;
    @Nullable
    private BytesRange mBytesRange;
    @Nullable
    private ColorSpace mColorSpace;
    private int mExifOrientation;
    private int mHeight;
    private ImageFormat mImageFormat;
    @Nullable
    private final Supplier<FileInputStream> mInputStreamSupplier;
    @Nullable
    private final CloseableReference<PooledByteBuffer> mPooledByteBufferRef;
    private int mRotationAngle;
    private int mSampleSize;
    private int mStreamSize;
    private int mWidth;

    public EncodedImage(CloseableReference<PooledByteBuffer> closeableReference) {
        this.mImageFormat = ImageFormat.UNKNOWN;
        this.mRotationAngle = -1;
        this.mExifOrientation = 0;
        this.mWidth = -1;
        this.mHeight = -1;
        this.mSampleSize = 1;
        this.mStreamSize = -1;
        Preconditions.checkArgument(CloseableReference.isValid(closeableReference));
        this.mPooledByteBufferRef = closeableReference.clone();
        this.mInputStreamSupplier = null;
    }

    public EncodedImage(Supplier<FileInputStream> supplier) {
        this.mImageFormat = ImageFormat.UNKNOWN;
        this.mRotationAngle = -1;
        this.mExifOrientation = 0;
        this.mWidth = -1;
        this.mHeight = -1;
        this.mSampleSize = 1;
        this.mStreamSize = -1;
        Preconditions.checkNotNull(supplier);
        this.mPooledByteBufferRef = null;
        this.mInputStreamSupplier = supplier;
    }

    public EncodedImage(Supplier<FileInputStream> supplier, int i) {
        this((Supplier) supplier);
        this.mStreamSize = i;
    }

    @Nullable
    public static EncodedImage cloneOrNull(EncodedImage encodedImage) {
        return encodedImage != null ? encodedImage.cloneOrNull() : null;
    }

    @Nullable
    public EncodedImage cloneOrNull() {
        EncodedImage encodedImage;
        Supplier supplier = this.mInputStreamSupplier;
        if (supplier != null) {
            encodedImage = new EncodedImage(supplier, this.mStreamSize);
        } else {
            CloseableReference cloneOrNull = CloseableReference.cloneOrNull(this.mPooledByteBufferRef);
            if (cloneOrNull == null) {
                encodedImage = null;
            } else {
                try {
                    encodedImage = new EncodedImage(cloneOrNull);
                } catch (Throwable th) {
                    CloseableReference.closeSafely(cloneOrNull);
                }
            }
            CloseableReference.closeSafely(cloneOrNull);
        }
        if (encodedImage != null) {
            encodedImage.copyMetaDataFrom(this);
        }
        return encodedImage;
    }

    public void close() {
        CloseableReference.closeSafely(this.mPooledByteBufferRef);
    }

    public synchronized boolean isValid() {
        boolean z;
        z = CloseableReference.isValid(this.mPooledByteBufferRef) || this.mInputStreamSupplier != null;
        return z;
    }

    public CloseableReference<PooledByteBuffer> getByteBufferRef() {
        return CloseableReference.cloneOrNull(this.mPooledByteBufferRef);
    }

    @Nullable
    public InputStream getInputStream() {
        Supplier supplier = this.mInputStreamSupplier;
        if (supplier != null) {
            return (InputStream) supplier.get();
        }
        CloseableReference cloneOrNull = CloseableReference.cloneOrNull(this.mPooledByteBufferRef);
        if (cloneOrNull == null) {
            return null;
        }
        try {
            InputStream pooledByteBufferInputStream = new PooledByteBufferInputStream((PooledByteBuffer) cloneOrNull.get());
            return pooledByteBufferInputStream;
        } finally {
            CloseableReference.closeSafely(cloneOrNull);
        }
    }

    public void setImageFormat(ImageFormat imageFormat) {
        this.mImageFormat = imageFormat;
    }

    public void setHeight(int i) {
        this.mHeight = i;
    }

    public void setWidth(int i) {
        this.mWidth = i;
    }

    public void setRotationAngle(int i) {
        this.mRotationAngle = i;
    }

    public void setExifOrientation(int i) {
        this.mExifOrientation = i;
    }

    public void setSampleSize(int i) {
        this.mSampleSize = i;
    }

    public void setStreamSize(int i) {
        this.mStreamSize = i;
    }

    public void setBytesRange(@Nullable BytesRange bytesRange) {
        this.mBytesRange = bytesRange;
    }

    public ImageFormat getImageFormat() {
        parseMetaDataIfNeeded();
        return this.mImageFormat;
    }

    public int getRotationAngle() {
        parseMetaDataIfNeeded();
        return this.mRotationAngle;
    }

    public int getExifOrientation() {
        parseMetaDataIfNeeded();
        return this.mExifOrientation;
    }

    public int getWidth() {
        parseMetaDataIfNeeded();
        return this.mWidth;
    }

    public int getHeight() {
        parseMetaDataIfNeeded();
        return this.mHeight;
    }

    @Nullable
    public ColorSpace getColorSpace() {
        parseMetaDataIfNeeded();
        return this.mColorSpace;
    }

    public int getSampleSize() {
        return this.mSampleSize;
    }

    @Nullable
    public BytesRange getBytesRange() {
        return this.mBytesRange;
    }

    public boolean isCompleteAt(int i) {
        boolean z = true;
        if (this.mImageFormat != DefaultImageFormats.JPEG || this.mInputStreamSupplier != null) {
            return true;
        }
        Preconditions.checkNotNull(this.mPooledByteBufferRef);
        PooledByteBuffer pooledByteBuffer = (PooledByteBuffer) this.mPooledByteBufferRef.get();
        if (!(pooledByteBuffer.read(i - 2) == (byte) -1 && pooledByteBuffer.read(i - 1) == (byte) -39)) {
            z = false;
        }
        return z;
    }

    public int getSize() {
        CloseableReference closeableReference = this.mPooledByteBufferRef;
        if (closeableReference == null || closeableReference.get() == null) {
            return this.mStreamSize;
        }
        return ((PooledByteBuffer) this.mPooledByteBufferRef.get()).size();
    }

    public String getFirstBytesAsHexString(int i) {
        CloseableReference byteBufferRef = getByteBufferRef();
        String str = "";
        if (byteBufferRef == null) {
            return str;
        }
        i = Math.min(getSize(), i);
        byte[] bArr = new byte[i];
        try {
            PooledByteBuffer pooledByteBuffer = (PooledByteBuffer) byteBufferRef.get();
            if (pooledByteBuffer == null) {
                return str;
            }
            str = null;
            pooledByteBuffer.read(0, bArr, 0, i);
            byteBufferRef.close();
            StringBuilder stringBuilder = new StringBuilder(bArr.length * 2);
            int length = bArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                stringBuilder.append(String.format("%02X", new Object[]{Byte.valueOf(bArr[i2])}));
            }
            return stringBuilder.toString();
        } finally {
            byteBufferRef.close();
        }
    }

    private void parseMetaDataIfNeeded() {
        if (this.mWidth < 0 || this.mHeight < 0) {
            parseMetaData();
        }
    }

    public void parseMetaData() {
        Pair readWebPImageSize;
        ImageFormat imageFormat_WrapIOException = ImageFormatChecker.getImageFormat_WrapIOException(getInputStream());
        this.mImageFormat = imageFormat_WrapIOException;
        if (DefaultImageFormats.isWebpFormat(imageFormat_WrapIOException)) {
            readWebPImageSize = readWebPImageSize();
        } else {
            readWebPImageSize = readImageMetaData().getDimensions();
        }
        if (imageFormat_WrapIOException == DefaultImageFormats.JPEG && this.mRotationAngle == -1) {
            if (readWebPImageSize != null) {
                this.mExifOrientation = JfifUtil.getOrientation(getInputStream());
                this.mRotationAngle = JfifUtil.getAutoRotateAngleFromOrientation(this.mExifOrientation);
            }
        } else if (imageFormat_WrapIOException == DefaultImageFormats.HEIF && this.mRotationAngle == -1) {
            this.mExifOrientation = HeifExifUtil.getOrientation(getInputStream());
            this.mRotationAngle = JfifUtil.getAutoRotateAngleFromOrientation(this.mExifOrientation);
        } else {
            this.mRotationAngle = 0;
        }
    }

    private Pair<Integer, Integer> readWebPImageSize() {
        Pair<Integer, Integer> size = WebpUtil.getSize(getInputStream());
        if (size != null) {
            this.mWidth = ((Integer) size.first).intValue();
            this.mHeight = ((Integer) size.second).intValue();
        }
        return size;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0034 A:{SYNTHETIC, Splitter: B:14:0x0034} */
    private com.facebook.imageutils.ImageMetaData readImageMetaData() {
        /*
        r4 = this;
        r0 = r4.getInputStream();	 Catch:{ all -> 0x0030 }
        r1 = com.facebook.imageutils.BitmapUtil.decodeDimensionsAndColorSpace(r0);	 Catch:{ all -> 0x002e }
        r2 = r1.getColorSpace();	 Catch:{ all -> 0x002e }
        r4.mColorSpace = r2;	 Catch:{ all -> 0x002e }
        r2 = r1.getDimensions();	 Catch:{ all -> 0x002e }
        if (r2 == 0) goto L_0x0028;
    L_0x0014:
        r3 = r2.first;	 Catch:{ all -> 0x002e }
        r3 = (java.lang.Integer) r3;	 Catch:{ all -> 0x002e }
        r3 = r3.intValue();	 Catch:{ all -> 0x002e }
        r4.mWidth = r3;	 Catch:{ all -> 0x002e }
        r2 = r2.second;	 Catch:{ all -> 0x002e }
        r2 = (java.lang.Integer) r2;	 Catch:{ all -> 0x002e }
        r2 = r2.intValue();	 Catch:{ all -> 0x002e }
        r4.mHeight = r2;	 Catch:{ all -> 0x002e }
    L_0x0028:
        if (r0 == 0) goto L_0x002d;
    L_0x002a:
        r0.close();	 Catch:{ IOException -> 0x002d }
    L_0x002d:
        return r1;
    L_0x002e:
        r1 = move-exception;
        goto L_0x0032;
    L_0x0030:
        r1 = move-exception;
        r0 = 0;
    L_0x0032:
        if (r0 == 0) goto L_0x0037;
    L_0x0034:
        r0.close();	 Catch:{ IOException -> 0x0037 }
    L_0x0037:
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.image.EncodedImage.readImageMetaData():com.facebook.imageutils.ImageMetaData");
    }

    public void copyMetaDataFrom(EncodedImage encodedImage) {
        this.mImageFormat = encodedImage.getImageFormat();
        this.mWidth = encodedImage.getWidth();
        this.mHeight = encodedImage.getHeight();
        this.mRotationAngle = encodedImage.getRotationAngle();
        this.mExifOrientation = encodedImage.getExifOrientation();
        this.mSampleSize = encodedImage.getSampleSize();
        this.mStreamSize = encodedImage.getSize();
        this.mBytesRange = encodedImage.getBytesRange();
        this.mColorSpace = encodedImage.getColorSpace();
    }

    public static boolean isMetaDataAvailable(EncodedImage encodedImage) {
        return encodedImage.mRotationAngle >= 0 && encodedImage.mWidth >= 0 && encodedImage.mHeight >= 0;
    }

    public static void closeSafely(@Nullable EncodedImage encodedImage) {
        if (encodedImage != null) {
            encodedImage.close();
        }
    }

    public static boolean isValid(@Nullable EncodedImage encodedImage) {
        return encodedImage != null && encodedImage.isValid();
    }

    @VisibleForTesting
    @Nullable
    public synchronized SharedReference<PooledByteBuffer> getUnderlyingReferenceTestOnly() {
        return this.mPooledByteBufferRef != null ? this.mPooledByteBufferRef.getUnderlyingReferenceTestOnly() : null;
    }
}