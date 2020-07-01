package com.facebook.imageformat;

import com.facebook.common.internal.ByteStreams;
import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Throwables;
import com.facebook.imageformat.ImageFormat.FormatChecker;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.annotation.Nullable;

public class ImageFormatChecker {
    private static ImageFormatChecker sInstance;
    @Nullable
    private List<FormatChecker> mCustomImageFormatCheckers;
    private final FormatChecker mDefaultFormatChecker = new DefaultImageFormatChecker();
    private int mMaxHeaderLength;

    private ImageFormatChecker() {
        updateMaxHeaderLength();
    }

    public void setCustomImageFormatCheckers(@Nullable List<FormatChecker> list) {
        this.mCustomImageFormatCheckers = list;
        updateMaxHeaderLength();
    }

    public ImageFormat determineImageFormat(InputStream inputStream) throws IOException {
        Preconditions.checkNotNull(inputStream);
        int i = this.mMaxHeaderLength;
        byte[] bArr = new byte[i];
        int readHeaderFromStream = readHeaderFromStream(i, inputStream, bArr);
        ImageFormat determineFormat = this.mDefaultFormatChecker.determineFormat(bArr, readHeaderFromStream);
        if (determineFormat != null && determineFormat != ImageFormat.UNKNOWN) {
            return determineFormat;
        }
        List<FormatChecker> list = this.mCustomImageFormatCheckers;
        if (list != null) {
            for (FormatChecker determineFormat2 : list) {
                ImageFormat determineFormat3 = determineFormat2.determineFormat(bArr, readHeaderFromStream);
                if (determineFormat3 != null && determineFormat3 != ImageFormat.UNKNOWN) {
                    return determineFormat3;
                }
            }
        }
        return ImageFormat.UNKNOWN;
    }

    private void updateMaxHeaderLength() {
        this.mMaxHeaderLength = this.mDefaultFormatChecker.getHeaderSize();
        List<FormatChecker> list = this.mCustomImageFormatCheckers;
        if (list != null) {
            for (FormatChecker headerSize : list) {
                this.mMaxHeaderLength = Math.max(this.mMaxHeaderLength, headerSize.getHeaderSize());
            }
        }
    }

    private static int readHeaderFromStream(int i, InputStream inputStream, byte[] bArr) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(bArr);
        Preconditions.checkArgument(bArr.length >= i);
        if (!inputStream.markSupported()) {
            return ByteStreams.read(inputStream, bArr, 0, i);
        }
        try {
            inputStream.mark(i);
            i = ByteStreams.read(inputStream, bArr, 0, i);
            return i;
        } finally {
            inputStream.reset();
        }
    }

    public static synchronized ImageFormatChecker getInstance() {
        ImageFormatChecker imageFormatChecker;
        synchronized (ImageFormatChecker.class) {
            if (sInstance == null) {
                sInstance = new ImageFormatChecker();
            }
            imageFormatChecker = sInstance;
        }
        return imageFormatChecker;
    }

    public static ImageFormat getImageFormat(InputStream inputStream) throws IOException {
        return getInstance().determineImageFormat(inputStream);
    }

    public static ImageFormat getImageFormat_WrapIOException(InputStream inputStream) {
        try {
            return getImageFormat(inputStream);
        } catch (Throwable e) {
            throw Throwables.propagate(e);
        }
    }

    public static ImageFormat getImageFormat(String str) {
        Throwable th;
        InputStream inputStream = null;
        InputStream fileInputStream = new FileInputStream(str);
        ImageFormat imageFormat;
        try {
            imageFormat = getImageFormat(fileInputStream);
            Closeables.closeQuietly(fileInputStream);
            return imageFormat;
        } catch (IOException unused) {
            inputStream = fileInputStream;
            try {
            } catch (IOException unused2) {
                imageFormat = ImageFormat.UNKNOWN;
                Closeables.closeQuietly(inputStream);
                return imageFormat;
            } catch (Throwable th2) {
                th = th2;
                Closeables.closeQuietly(inputStream);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            inputStream = fileInputStream;
            Closeables.closeQuietly(inputStream);
            throw th;
        }
    }
}
