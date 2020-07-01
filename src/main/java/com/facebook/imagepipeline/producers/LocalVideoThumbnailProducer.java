package com.facebook.imagepipeline.producers;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.ParcelFileDescriptor;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.SimpleBitmapReleaser;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class LocalVideoThumbnailProducer implements Producer<CloseableReference<CloseableImage>> {
    @VisibleForTesting
    static final String CREATED_THUMBNAIL = "createdThumbnail";
    public static final String PRODUCER_NAME = "VideoThumbnailProducer";
    private final ContentResolver mContentResolver;
    private final Executor mExecutor;

    public LocalVideoThumbnailProducer(Executor executor, ContentResolver contentResolver) {
        this.mExecutor = executor;
        this.mContentResolver = contentResolver;
    }

    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
        final ProducerListener listener = producerContext.getListener();
        final String id = producerContext.getId();
        final ImageRequest imageRequest = producerContext.getImageRequest();
        final Runnable anonymousClass1 = new StatefulProducerRunnable<CloseableReference<CloseableImage>>(consumer, listener, PRODUCER_NAME, id) {
            protected void onSuccess(CloseableReference<CloseableImage> closeableReference) {
                super.onSuccess(closeableReference);
                listener.onUltimateProducerReached(id, LocalVideoThumbnailProducer.PRODUCER_NAME, closeableReference != null);
            }

            protected void onFailure(Exception exception) {
                super.onFailure(exception);
                listener.onUltimateProducerReached(id, LocalVideoThumbnailProducer.PRODUCER_NAME, false);
            }

            @Nullable
            protected CloseableReference<CloseableImage> getResult() throws Exception {
                String access$000;
                Bitmap createVideoThumbnail;
                try {
                    access$000 = LocalVideoThumbnailProducer.this.getLocalFilePath(imageRequest);
                } catch (IllegalArgumentException unused) {
                    access$000 = null;
                }
                if (access$000 != null) {
                    createVideoThumbnail = ThumbnailUtils.createVideoThumbnail(access$000, LocalVideoThumbnailProducer.calculateKind(imageRequest));
                } else {
                    createVideoThumbnail = LocalVideoThumbnailProducer.createThumbnailFromContentProvider(LocalVideoThumbnailProducer.this.mContentResolver, imageRequest.getSourceUri());
                }
                if (createVideoThumbnail == null) {
                    return null;
                }
                return CloseableReference.of(new CloseableStaticBitmap(createVideoThumbnail, SimpleBitmapReleaser.getInstance(), ImmutableQualityInfo.FULL_QUALITY, 0));
            }

            protected Map<String, String> getExtraMapOnSuccess(CloseableReference<CloseableImage> closeableReference) {
                return ImmutableMap.of(LocalVideoThumbnailProducer.CREATED_THUMBNAIL, String.valueOf(closeableReference != null));
            }

            protected void disposeResult(CloseableReference<CloseableImage> closeableReference) {
                CloseableReference.closeSafely((CloseableReference) closeableReference);
            }
        };
        producerContext.addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
                anonymousClass1.cancel();
            }
        });
        this.mExecutor.execute(anonymousClass1);
    }

    private static int calculateKind(ImageRequest imageRequest) {
        return (imageRequest.getPreferredWidth() > 96 || imageRequest.getPreferredHeight() > 96) ? 1 : 3;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0058 A:{SYNTHETIC, Splitter: B:14:0x0058} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0075  */
    @javax.annotation.Nullable
    private java.lang.String getLocalFilePath(com.facebook.imagepipeline.request.ImageRequest r11) {
        /*
        r10 = this;
        r0 = r11.getSourceUri();
        r1 = com.facebook.common.util.UriUtil.isLocalFileUri(r0);
        if (r1 == 0) goto L_0x0013;
    L_0x000a:
        r11 = r11.getSourceFile();
        r11 = r11.getPath();
        return r11;
    L_0x0013:
        r11 = com.facebook.common.util.UriUtil.isLocalContentUri(r0);
        r1 = 0;
        if (r11 == 0) goto L_0x0078;
    L_0x001a:
        r11 = android.os.Build.VERSION.SDK_INT;
        r2 = 19;
        if (r11 < r2) goto L_0x0046;
    L_0x0020:
        r11 = r0.getAuthority();
        r2 = "com.android.providers.media.documents";
        r11 = r2.equals(r11);
        if (r11 == 0) goto L_0x0046;
    L_0x002c:
        r11 = android.provider.DocumentsContract.getDocumentId(r0);
        r0 = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        r2 = 1;
        r3 = new java.lang.String[r2];
        r4 = 0;
        r5 = ":";
        r11 = r11.split(r5);
        r11 = r11[r2];
        r3[r4] = r11;
        r11 = "_id=?";
        r7 = r11;
        r5 = r0;
        r8 = r3;
        goto L_0x0049;
    L_0x0046:
        r5 = r0;
        r7 = r1;
        r8 = r7;
    L_0x0049:
        r4 = r10.mContentResolver;
        r11 = "_data";
        r6 = new java.lang.String[]{r11};
        r9 = 0;
        r0 = r4.query(r5, r6, r7, r8, r9);
        if (r0 == 0) goto L_0x0073;
    L_0x0058:
        r2 = r0.moveToFirst();	 Catch:{ all -> 0x006c }
        if (r2 == 0) goto L_0x0073;
    L_0x005e:
        r11 = r0.getColumnIndexOrThrow(r11);	 Catch:{ all -> 0x006c }
        r11 = r0.getString(r11);	 Catch:{ all -> 0x006c }
        if (r0 == 0) goto L_0x006b;
    L_0x0068:
        r0.close();
    L_0x006b:
        return r11;
    L_0x006c:
        r11 = move-exception;
        if (r0 == 0) goto L_0x0072;
    L_0x006f:
        r0.close();
    L_0x0072:
        throw r11;
    L_0x0073:
        if (r0 == 0) goto L_0x0078;
    L_0x0075:
        r0.close();
    L_0x0078:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.LocalVideoThumbnailProducer.getLocalFilePath(com.facebook.imagepipeline.request.ImageRequest):java.lang.String");
    }

    @Nullable
    private static Bitmap createThumbnailFromContentProvider(ContentResolver contentResolver, Uri uri) {
        if (VERSION.SDK_INT >= 10) {
            try {
                ParcelFileDescriptor openFileDescriptor = contentResolver.openFileDescriptor(uri, "r");
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(openFileDescriptor.getFileDescriptor());
                return mediaMetadataRetriever.getFrameAtTime(-1);
            } catch (FileNotFoundException unused) {
                return null;
            }
        }
    }
}
