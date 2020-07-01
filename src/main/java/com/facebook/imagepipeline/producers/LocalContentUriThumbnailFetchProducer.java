package com.facebook.imagepipeline.producers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import com.android.vending.expansion.zipfile.APEZProvider;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.BitmapCounterConfig;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imageutils.JfifUtil;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class LocalContentUriThumbnailFetchProducer extends LocalFetchProducer implements ThumbnailProducer<EncodedImage> {
    private static final Rect MICRO_THUMBNAIL_DIMENSIONS = new Rect(0, 0, 96, 96);
    private static final Rect MINI_THUMBNAIL_DIMENSIONS = new Rect(0, 0, 512, BitmapCounterConfig.DEFAULT_MAX_BITMAP_COUNT);
    private static final int NO_THUMBNAIL = 0;
    public static final String PRODUCER_NAME = "LocalContentUriThumbnailFetchProducer";
    private static final String[] PROJECTION;
    private static final Class<?> TAG = LocalContentUriThumbnailFetchProducer.class;
    private static final String[] THUMBNAIL_PROJECTION;
    private final ContentResolver mContentResolver;

    protected String getProducerName() {
        return PRODUCER_NAME;
    }

    static {
        String str = "_data";
        PROJECTION = new String[]{APEZProvider.FILEID, str};
        THUMBNAIL_PROJECTION = new String[]{str};
    }

    public LocalContentUriThumbnailFetchProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, ContentResolver contentResolver) {
        super(executor, pooledByteBufferFactory);
        this.mContentResolver = contentResolver;
    }

    public boolean canProvideImageForSize(ResizeOptions resizeOptions) {
        return ThumbnailSizeChecker.isImageBigEnough(MINI_THUMBNAIL_DIMENSIONS.width(), MINI_THUMBNAIL_DIMENSIONS.height(), resizeOptions);
    }

    @Nullable
    protected EncodedImage getEncodedImage(ImageRequest imageRequest) throws IOException {
        Uri sourceUri = imageRequest.getSourceUri();
        if (UriUtil.isLocalCameraUri(sourceUri)) {
            EncodedImage cameraImage = getCameraImage(sourceUri, imageRequest.getResizeOptions());
            if (cameraImage != null) {
                return cameraImage;
            }
        }
        return null;
    }

    @Nullable
    private EncodedImage getCameraImage(Uri uri, ResizeOptions resizeOptions) throws IOException {
        Cursor query = this.mContentResolver.query(uri, PROJECTION, null, null, null);
        if (query == null) {
            return null;
        }
        try {
            if (query.getCount() == 0) {
                return null;
            }
            query.moveToFirst();
            String string = query.getString(query.getColumnIndex("_data"));
            if (resizeOptions != null) {
                EncodedImage thumbnail = getThumbnail(resizeOptions, query.getInt(query.getColumnIndex(APEZProvider.FILEID)));
                if (thumbnail != null) {
                    thumbnail.setRotationAngle(getRotationAngle(string));
                    query.close();
                    return thumbnail;
                }
            }
            query.close();
            return null;
        } finally {
            query.close();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0056  */
    @javax.annotation.Nullable
    private com.facebook.imagepipeline.image.EncodedImage getThumbnail(com.facebook.imagepipeline.common.ResizeOptions r5, int r6) throws java.io.IOException {
        /*
        r4 = this;
        r5 = getThumbnailKind(r5);
        r0 = 0;
        if (r5 != 0) goto L_0x0008;
    L_0x0007:
        return r0;
    L_0x0008:
        r1 = r4.mContentResolver;	 Catch:{ all -> 0x0052 }
        r2 = (long) r6;	 Catch:{ all -> 0x0052 }
        r6 = THUMBNAIL_PROJECTION;	 Catch:{ all -> 0x0052 }
        r5 = android.provider.MediaStore.Images.Thumbnails.queryMiniThumbnail(r1, r2, r5, r6);	 Catch:{ all -> 0x0052 }
        if (r5 != 0) goto L_0x0019;
    L_0x0013:
        if (r5 == 0) goto L_0x0018;
    L_0x0015:
        r5.close();
    L_0x0018:
        return r0;
    L_0x0019:
        r5.moveToFirst();	 Catch:{ all -> 0x0050 }
        r6 = r5.getCount();	 Catch:{ all -> 0x0050 }
        if (r6 <= 0) goto L_0x004a;
    L_0x0022:
        r6 = "_data";
        r6 = r5.getColumnIndex(r6);	 Catch:{ all -> 0x0050 }
        r6 = r5.getString(r6);	 Catch:{ all -> 0x0050 }
        r1 = new java.io.File;	 Catch:{ all -> 0x0050 }
        r1.<init>(r6);	 Catch:{ all -> 0x0050 }
        r1 = r1.exists();	 Catch:{ all -> 0x0050 }
        if (r1 == 0) goto L_0x004a;
    L_0x0037:
        r0 = new java.io.FileInputStream;	 Catch:{ all -> 0x0050 }
        r0.<init>(r6);	 Catch:{ all -> 0x0050 }
        r6 = getLength(r6);	 Catch:{ all -> 0x0050 }
        r6 = r4.getEncodedImage(r0, r6);	 Catch:{ all -> 0x0050 }
        if (r5 == 0) goto L_0x0049;
    L_0x0046:
        r5.close();
    L_0x0049:
        return r6;
    L_0x004a:
        if (r5 == 0) goto L_0x004f;
    L_0x004c:
        r5.close();
    L_0x004f:
        return r0;
    L_0x0050:
        r6 = move-exception;
        goto L_0x0054;
    L_0x0052:
        r6 = move-exception;
        r5 = r0;
    L_0x0054:
        if (r5 == 0) goto L_0x0059;
    L_0x0056:
        r5.close();
    L_0x0059:
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.LocalContentUriThumbnailFetchProducer.getThumbnail(com.facebook.imagepipeline.common.ResizeOptions, int):com.facebook.imagepipeline.image.EncodedImage");
    }

    private static int getThumbnailKind(ResizeOptions resizeOptions) {
        if (ThumbnailSizeChecker.isImageBigEnough(MICRO_THUMBNAIL_DIMENSIONS.width(), MICRO_THUMBNAIL_DIMENSIONS.height(), resizeOptions)) {
            return 3;
        }
        return ThumbnailSizeChecker.isImageBigEnough(MINI_THUMBNAIL_DIMENSIONS.width(), MINI_THUMBNAIL_DIMENSIONS.height(), resizeOptions) ? 1 : 0;
    }

    private static int getLength(String str) {
        return str == null ? -1 : (int) new File(str).length();
    }

    private static int getRotationAngle(String str) {
        if (str != null) {
            try {
                str = JfifUtil.getAutoRotateAngleFromOrientation(new ExifInterface(str).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1));
                return str;
            } catch (Throwable e) {
                FLog.e(TAG, e, "Unable to retrieve thumbnail rotation for %s", str);
            }
        }
        return 0;
    }
}
