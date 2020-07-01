package com.facebook.imagepipeline.producers;

import android.graphics.Bitmap;
import androidx.core.os.EnvironmentCompat;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.ByteArrayPool;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.ExceptionWithNoStacktrace;
import com.facebook.common.util.UriUtil;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.CloseableReferenceFactory;
import com.facebook.imagepipeline.decoder.DecodeException;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegParser;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.producers.JobScheduler.JobRunnable;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import com.facebook.imagepipeline.transcoder.DownsampleUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class DecodeProducer implements Producer<CloseableReference<CloseableImage>> {
    public static final String ENCODED_IMAGE_SIZE = "encodedImageSize";
    public static final String EXTRA_BITMAP_SIZE = "bitmapSize";
    public static final String EXTRA_HAS_GOOD_QUALITY = "hasGoodQuality";
    public static final String EXTRA_IMAGE_FORMAT_NAME = "imageFormat";
    public static final String EXTRA_IS_FINAL = "isFinal";
    public static final String PRODUCER_NAME = "DecodeProducer";
    public static final String REQUESTED_IMAGE_SIZE = "requestedImageSize";
    public static final String SAMPLE_SIZE = "sampleSize";
    private final ByteArrayPool mByteArrayPool;
    private final CloseableReferenceFactory mCloseableReferenceFactory;
    private final boolean mDecodeCancellationEnabled;
    private final boolean mDownsampleEnabled;
    private final boolean mDownsampleEnabledForNetwork;
    private final Executor mExecutor;
    private final ImageDecoder mImageDecoder;
    private final Producer<EncodedImage> mInputProducer;
    private final int mMaxBitmapSize;
    private final ProgressiveJpegConfig mProgressiveJpegConfig;

    private abstract class ProgressiveDecoder extends DelegatingConsumer<EncodedImage, CloseableReference<CloseableImage>> {
        private static final int DECODE_EXCEPTION_MESSAGE_NUM_HEADER_BYTES = 10;
        private final String TAG = "ProgressiveDecoder";
        private final ImageDecodeOptions mImageDecodeOptions;
        @GuardedBy("this")
        private boolean mIsFinished;
        private final JobScheduler mJobScheduler;
        private final ProducerContext mProducerContext;
        private final ProducerListener mProducerListener;

        protected abstract int getIntermediateImageEndOffset(EncodedImage encodedImage);

        protected abstract QualityInfo getQualityInfo();

        public ProgressiveDecoder(Consumer<CloseableReference<CloseableImage>> consumer, final ProducerContext producerContext, final boolean z, final int i) {
            super(consumer);
            this.mProducerContext = producerContext;
            this.mProducerListener = producerContext.getListener();
            this.mImageDecodeOptions = producerContext.getImageRequest().getImageDecodeOptions();
            this.mIsFinished = false;
            this.mJobScheduler = new JobScheduler(DecodeProducer.this.mExecutor, new JobRunnable(DecodeProducer.this) {
                public void run(EncodedImage encodedImage, int i) {
                    if (encodedImage != null) {
                        if (DecodeProducer.this.mDownsampleEnabled || !BaseConsumer.statusHasFlag(i, 16)) {
                            ImageRequest imageRequest = producerContext.getImageRequest();
                            if (DecodeProducer.this.mDownsampleEnabledForNetwork || !UriUtil.isNetworkUri(imageRequest.getSourceUri())) {
                                encodedImage.setSampleSize(DownsampleUtil.determineSampleSize(imageRequest.getRotationOptions(), imageRequest.getResizeOptions(), encodedImage, i));
                            }
                        }
                        ProgressiveDecoder.this.doDecode(encodedImage, i);
                    }
                }
            }, this.mImageDecodeOptions.minDecodeIntervalMs);
            this.mProducerContext.addCallbacks(new BaseProducerContextCallbacks(DecodeProducer.this) {
                public void onIsIntermediateResultExpectedChanged() {
                    if (ProgressiveDecoder.this.mProducerContext.isIntermediateResultExpected()) {
                        ProgressiveDecoder.this.mJobScheduler.scheduleJob();
                    }
                }

                public void onCancellationRequested() {
                    if (z) {
                        ProgressiveDecoder.this.handleCancellation();
                    }
                }
            });
        }

        public void onNewResultImpl(EncodedImage encodedImage, int i) {
            try {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.beginSection("DecodeProducer#onNewResultImpl");
                }
                boolean isLast = BaseConsumer.isLast(i);
                if (isLast && !EncodedImage.isValid(encodedImage)) {
                    handleError(new ExceptionWithNoStacktrace("Encoded image is not valid."));
                } else if (updateDecodeJob(encodedImage, i)) {
                    boolean statusHasFlag = BaseConsumer.statusHasFlag(i, 4);
                    if (isLast || statusHasFlag || this.mProducerContext.isIntermediateResultExpected()) {
                        this.mJobScheduler.scheduleJob();
                    }
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.endSection();
                    }
                } else {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.endSection();
                    }
                }
            } finally {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
            }
        }

        protected void onProgressUpdateImpl(float f) {
            super.onProgressUpdateImpl(f * 0.99f);
        }

        public void onFailureImpl(Throwable th) {
            handleError(th);
        }

        public void onCancellationImpl() {
            handleCancellation();
        }

        protected boolean updateDecodeJob(EncodedImage encodedImage, int i) {
            return this.mJobScheduler.updateJob(encodedImage, i);
        }

        private void doDecode(EncodedImage encodedImage, int i) {
            Throwable e;
            CloseableImage closeableImage;
            int i2 = i;
            String str = DecodeProducer.PRODUCER_NAME;
            if ((encodedImage.getImageFormat() == DefaultImageFormats.JPEG || !BaseConsumer.isNotLast(i)) && !isFinished() && EncodedImage.isValid(encodedImage)) {
                ImageFormat imageFormat = encodedImage.getImageFormat();
                String str2 = EnvironmentCompat.MEDIA_UNKNOWN;
                String name = imageFormat != null ? imageFormat.getName() : str2;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(encodedImage.getWidth());
                String str3 = "x";
                stringBuilder.append(str3);
                stringBuilder.append(encodedImage.getHeight());
                String stringBuilder2 = stringBuilder.toString();
                String valueOf = String.valueOf(encodedImage.getSampleSize());
                boolean isLast = BaseConsumer.isLast(i);
                Object obj = (!isLast || BaseConsumer.statusHasFlag(i2, 8)) ? null : 1;
                boolean statusHasFlag = BaseConsumer.statusHasFlag(i2, 4);
                ResizeOptions resizeOptions = this.mProducerContext.getImageRequest().getResizeOptions();
                if (resizeOptions != null) {
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append(resizeOptions.width);
                    stringBuilder3.append(str3);
                    stringBuilder3.append(resizeOptions.height);
                    str2 = stringBuilder3.toString();
                }
                String str4 = str2;
                try {
                    int size;
                    QualityInfo qualityInfo;
                    long queuedTime = this.mJobScheduler.getQueuedTime();
                    str2 = String.valueOf(this.mProducerContext.getImageRequest().getSourceUri());
                    if (obj != null || statusHasFlag) {
                        size = encodedImage.getSize();
                    } else {
                        size = getIntermediateImageEndOffset(encodedImage);
                    }
                    if (obj != null || statusHasFlag) {
                        qualityInfo = ImmutableQualityInfo.FULL_QUALITY;
                    } else {
                        qualityInfo = getQualityInfo();
                    }
                    this.mProducerListener.onProducerStart(this.mProducerContext.getId(), str);
                    try {
                        CloseableImage decode = DecodeProducer.this.mImageDecoder.decode(encodedImage, size, qualityInfo, this.mImageDecodeOptions);
                        try {
                            if (encodedImage.getSampleSize() != 1) {
                                i2 |= 16;
                            }
                            this.mProducerListener.onProducerFinishWithSuccess(this.mProducerContext.getId(), str, getExtraMap(decode, queuedTime, qualityInfo, isLast, name, stringBuilder2, str4, valueOf));
                            handleResult(decode, i2);
                        } catch (Exception e2) {
                            e = e2;
                            closeableImage = decode;
                            this.mProducerListener.onProducerFinishWithFailure(this.mProducerContext.getId(), str, e, getExtraMap(closeableImage, queuedTime, qualityInfo, isLast, name, stringBuilder2, str4, valueOf));
                            handleError(e);
                            EncodedImage.closeSafely(encodedImage);
                        }
                    } catch (DecodeException e3) {
                        EncodedImage encodedImage2 = e3.getEncodedImage();
                        FLog.w("ProgressiveDecoder", "%s, {uri: %s, firstEncodedBytes: %s, length: %d}", e3.getMessage(), str2, encodedImage2.getFirstBytesAsHexString(10), Integer.valueOf(encodedImage2.getSize()));
                        throw e3;
                    } catch (Exception e4) {
                        e = e4;
                        closeableImage = null;
                        this.mProducerListener.onProducerFinishWithFailure(this.mProducerContext.getId(), str, e, getExtraMap(closeableImage, queuedTime, qualityInfo, isLast, name, stringBuilder2, str4, valueOf));
                        handleError(e);
                        EncodedImage.closeSafely(encodedImage);
                    }
                } finally {
                    EncodedImage.closeSafely(encodedImage);
                }
            }
        }

        @Nullable
        private Map<String, String> getExtraMap(@Nullable CloseableImage closeableImage, long j, QualityInfo qualityInfo, boolean z, String str, String str2, String str3, String str4) {
            CloseableImage closeableImage2 = closeableImage;
            String str5 = str;
            String str6 = str2;
            String str7 = str3;
            String str8 = str4;
            if (!this.mProducerListener.requiresExtraMap(this.mProducerContext.getId())) {
                return null;
            }
            String valueOf = String.valueOf(j);
            String valueOf2 = String.valueOf(qualityInfo.isOfGoodEnoughQuality());
            String valueOf3 = String.valueOf(z);
            boolean z2 = closeableImage2 instanceof CloseableStaticBitmap;
            String str9 = DecodeProducer.SAMPLE_SIZE;
            String str10 = DecodeProducer.REQUESTED_IMAGE_SIZE;
            String str11 = DecodeProducer.EXTRA_IMAGE_FORMAT_NAME;
            String str12 = "encodedImageSize";
            String str13 = DecodeProducer.EXTRA_IS_FINAL;
            String str14 = DecodeProducer.EXTRA_HAS_GOOD_QUALITY;
            String str15 = "queueTime";
            String stringBuilder;
            Map hashMap;
            if (z2) {
                Bitmap underlyingBitmap = ((CloseableStaticBitmap) closeableImage2).getUnderlyingBitmap();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(underlyingBitmap.getWidth());
                stringBuilder2.append("x");
                stringBuilder2.append(underlyingBitmap.getHeight());
                stringBuilder = stringBuilder2.toString();
                hashMap = new HashMap(8);
                hashMap.put(DecodeProducer.EXTRA_BITMAP_SIZE, stringBuilder);
                hashMap.put(str15, valueOf);
                hashMap.put(str14, valueOf2);
                hashMap.put(str13, valueOf3);
                hashMap.put(str12, str6);
                hashMap.put(str11, str5);
                hashMap.put(str10, str7);
                hashMap.put(str9, str4);
                return ImmutableMap.copyOf(hashMap);
            }
            stringBuilder = str8;
            hashMap = new HashMap(7);
            hashMap.put(str15, valueOf);
            hashMap.put(str14, valueOf2);
            hashMap.put(str13, valueOf3);
            hashMap.put(str12, str6);
            hashMap.put(str11, str5);
            hashMap.put(str10, str7);
            hashMap.put(str9, stringBuilder);
            return ImmutableMap.copyOf(hashMap);
        }

        private synchronized boolean isFinished() {
            return this.mIsFinished;
        }

        private void maybeFinish(boolean z) {
            synchronized (this) {
                if (z) {
                    if (!this.mIsFinished) {
                        getConsumer().onProgressUpdate(1.0f);
                        this.mIsFinished = true;
                        this.mJobScheduler.clearJob();
                        return;
                    }
                }
            }
        }

        private void handleResult(CloseableImage closeableImage, int i) {
            CloseableReference create = DecodeProducer.this.mCloseableReferenceFactory.create(closeableImage);
            try {
                maybeFinish(BaseConsumer.isLast(i));
                getConsumer().onNewResult(create, i);
            } finally {
                CloseableReference.closeSafely(create);
            }
        }

        private void handleError(Throwable th) {
            maybeFinish(true);
            getConsumer().onFailure(th);
        }

        private void handleCancellation() {
            maybeFinish(true);
            getConsumer().onCancellation();
        }
    }

    private class LocalImagesProgressiveDecoder extends ProgressiveDecoder {
        public LocalImagesProgressiveDecoder(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext, boolean z, int i) {
            super(consumer, producerContext, z, i);
        }

        protected synchronized boolean updateDecodeJob(EncodedImage encodedImage, int i) {
            if (BaseConsumer.isNotLast(i)) {
                return false;
            }
            return super.updateDecodeJob(encodedImage, i);
        }

        protected int getIntermediateImageEndOffset(EncodedImage encodedImage) {
            return encodedImage.getSize();
        }

        protected QualityInfo getQualityInfo() {
            return ImmutableQualityInfo.of(0, false, false);
        }
    }

    private class NetworkImagesProgressiveDecoder extends ProgressiveDecoder {
        private int mLastScheduledScanNumber = 0;
        private final ProgressiveJpegConfig mProgressiveJpegConfig;
        private final ProgressiveJpegParser mProgressiveJpegParser;

        public NetworkImagesProgressiveDecoder(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext, ProgressiveJpegParser progressiveJpegParser, ProgressiveJpegConfig progressiveJpegConfig, boolean z, int i) {
            super(consumer, producerContext, z, i);
            this.mProgressiveJpegParser = (ProgressiveJpegParser) Preconditions.checkNotNull(progressiveJpegParser);
            this.mProgressiveJpegConfig = (ProgressiveJpegConfig) Preconditions.checkNotNull(progressiveJpegConfig);
        }

        /* JADX WARNING: Missing block: B:32:0x0056, code:
            return r0;
     */
        protected synchronized boolean updateDecodeJob(com.facebook.imagepipeline.image.EncodedImage r4, int r5) {
            /*
            r3 = this;
            monitor-enter(r3);
            r0 = super.updateDecodeJob(r4, r5);	 Catch:{ all -> 0x0057 }
            r1 = com.facebook.imagepipeline.producers.BaseConsumer.isNotLast(r5);	 Catch:{ all -> 0x0057 }
            if (r1 != 0) goto L_0x0013;
        L_0x000b:
            r1 = 8;
            r1 = com.facebook.imagepipeline.producers.BaseConsumer.statusHasFlag(r5, r1);	 Catch:{ all -> 0x0057 }
            if (r1 == 0) goto L_0x0055;
        L_0x0013:
            r1 = 4;
            r5 = com.facebook.imagepipeline.producers.BaseConsumer.statusHasFlag(r5, r1);	 Catch:{ all -> 0x0057 }
            if (r5 != 0) goto L_0x0055;
        L_0x001a:
            r5 = com.facebook.imagepipeline.image.EncodedImage.isValid(r4);	 Catch:{ all -> 0x0057 }
            if (r5 == 0) goto L_0x0055;
        L_0x0020:
            r5 = r4.getImageFormat();	 Catch:{ all -> 0x0057 }
            r1 = com.facebook.imageformat.DefaultImageFormats.JPEG;	 Catch:{ all -> 0x0057 }
            if (r5 != r1) goto L_0x0055;
        L_0x0028:
            r5 = r3.mProgressiveJpegParser;	 Catch:{ all -> 0x0057 }
            r4 = r5.parseMoreData(r4);	 Catch:{ all -> 0x0057 }
            r5 = 0;
            if (r4 != 0) goto L_0x0033;
        L_0x0031:
            monitor-exit(r3);
            return r5;
        L_0x0033:
            r4 = r3.mProgressiveJpegParser;	 Catch:{ all -> 0x0057 }
            r4 = r4.getBestScanNumber();	 Catch:{ all -> 0x0057 }
            r1 = r3.mLastScheduledScanNumber;	 Catch:{ all -> 0x0057 }
            if (r4 > r1) goto L_0x003f;
        L_0x003d:
            monitor-exit(r3);
            return r5;
        L_0x003f:
            r1 = r3.mProgressiveJpegConfig;	 Catch:{ all -> 0x0057 }
            r2 = r3.mLastScheduledScanNumber;	 Catch:{ all -> 0x0057 }
            r1 = r1.getNextScanNumberToDecode(r2);	 Catch:{ all -> 0x0057 }
            if (r4 >= r1) goto L_0x0053;
        L_0x0049:
            r1 = r3.mProgressiveJpegParser;	 Catch:{ all -> 0x0057 }
            r1 = r1.isEndMarkerRead();	 Catch:{ all -> 0x0057 }
            if (r1 != 0) goto L_0x0053;
        L_0x0051:
            monitor-exit(r3);
            return r5;
        L_0x0053:
            r3.mLastScheduledScanNumber = r4;	 Catch:{ all -> 0x0057 }
        L_0x0055:
            monitor-exit(r3);
            return r0;
        L_0x0057:
            r4 = move-exception;
            monitor-exit(r3);
            throw r4;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.DecodeProducer.NetworkImagesProgressiveDecoder.updateDecodeJob(com.facebook.imagepipeline.image.EncodedImage, int):boolean");
        }

        protected int getIntermediateImageEndOffset(EncodedImage encodedImage) {
            return this.mProgressiveJpegParser.getBestScanEndOffset();
        }

        protected QualityInfo getQualityInfo() {
            return this.mProgressiveJpegConfig.getQualityInfo(this.mProgressiveJpegParser.getBestScanNumber());
        }
    }

    public DecodeProducer(ByteArrayPool byteArrayPool, Executor executor, ImageDecoder imageDecoder, ProgressiveJpegConfig progressiveJpegConfig, boolean z, boolean z2, boolean z3, Producer<EncodedImage> producer, int i, CloseableReferenceFactory closeableReferenceFactory) {
        this.mByteArrayPool = (ByteArrayPool) Preconditions.checkNotNull(byteArrayPool);
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        this.mImageDecoder = (ImageDecoder) Preconditions.checkNotNull(imageDecoder);
        this.mProgressiveJpegConfig = (ProgressiveJpegConfig) Preconditions.checkNotNull(progressiveJpegConfig);
        this.mDownsampleEnabled = z;
        this.mDownsampleEnabledForNetwork = z2;
        this.mInputProducer = (Producer) Preconditions.checkNotNull(producer);
        this.mDecodeCancellationEnabled = z3;
        this.mMaxBitmapSize = i;
        this.mCloseableReferenceFactory = closeableReferenceFactory;
    }

    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
        try {
            Consumer networkImagesProgressiveDecoder;
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("DecodeProducer#produceResults");
            }
            if (UriUtil.isNetworkUri(producerContext.getImageRequest().getSourceUri())) {
                networkImagesProgressiveDecoder = new NetworkImagesProgressiveDecoder(consumer, producerContext, new ProgressiveJpegParser(this.mByteArrayPool), this.mProgressiveJpegConfig, this.mDecodeCancellationEnabled, this.mMaxBitmapSize);
            } else {
                Consumer localImagesProgressiveDecoder = new LocalImagesProgressiveDecoder(consumer, producerContext, this.mDecodeCancellationEnabled, this.mMaxBitmapSize);
            }
            this.mInputProducer.produceResults(networkImagesProgressiveDecoder, producerContext);
        } finally {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        }
    }
}
