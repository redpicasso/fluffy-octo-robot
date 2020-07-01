package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.request.Postprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessorRunner;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class PostprocessorProducer implements Producer<CloseableReference<CloseableImage>> {
    public static final String NAME = "PostprocessorProducer";
    @VisibleForTesting
    static final String POSTPROCESSOR = "Postprocessor";
    private final PlatformBitmapFactory mBitmapFactory;
    private final Executor mExecutor;
    private final Producer<CloseableReference<CloseableImage>> mInputProducer;

    private class PostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {
        @GuardedBy("PostprocessorConsumer.this")
        private boolean mIsClosed;
        @GuardedBy("PostprocessorConsumer.this")
        private boolean mIsDirty = false;
        @GuardedBy("PostprocessorConsumer.this")
        private boolean mIsPostProcessingRunning = false;
        private final ProducerListener mListener;
        private final Postprocessor mPostprocessor;
        private final String mRequestId;
        @GuardedBy("PostprocessorConsumer.this")
        @Nullable
        private CloseableReference<CloseableImage> mSourceImageRef = null;
        @GuardedBy("PostprocessorConsumer.this")
        private int mStatus = 0;

        public PostprocessorConsumer(Consumer<CloseableReference<CloseableImage>> consumer, ProducerListener producerListener, String str, Postprocessor postprocessor, ProducerContext producerContext) {
            super(consumer);
            this.mListener = producerListener;
            this.mRequestId = str;
            this.mPostprocessor = postprocessor;
            producerContext.addCallbacks(new BaseProducerContextCallbacks(PostprocessorProducer.this) {
                public void onCancellationRequested() {
                    PostprocessorConsumer.this.maybeNotifyOnCancellation();
                }
            });
        }

        protected void onNewResultImpl(CloseableReference<CloseableImage> closeableReference, int i) {
            if (CloseableReference.isValid(closeableReference)) {
                updateSourceImageRef(closeableReference, i);
                return;
            }
            if (BaseConsumer.isLast(i)) {
                maybeNotifyOnNewResult(null, i);
            }
        }

        protected void onFailureImpl(Throwable th) {
            maybeNotifyOnFailure(th);
        }

        protected void onCancellationImpl() {
            maybeNotifyOnCancellation();
        }

        /* JADX WARNING: Missing block: B:8:0x0019, code:
            com.facebook.common.references.CloseableReference.closeSafely(r0);
     */
        /* JADX WARNING: Missing block: B:9:0x001c, code:
            if (r2 == false) goto L_0x0021;
     */
        /* JADX WARNING: Missing block: B:10:0x001e, code:
            submitPostprocessing();
     */
        /* JADX WARNING: Missing block: B:11:0x0021, code:
            return;
     */
        private void updateSourceImageRef(@javax.annotation.Nullable com.facebook.common.references.CloseableReference<com.facebook.imagepipeline.image.CloseableImage> r2, int r3) {
            /*
            r1 = this;
            monitor-enter(r1);
            r0 = r1.mIsClosed;	 Catch:{ all -> 0x0022 }
            if (r0 == 0) goto L_0x0007;
        L_0x0005:
            monitor-exit(r1);	 Catch:{ all -> 0x0022 }
            return;
        L_0x0007:
            r0 = r1.mSourceImageRef;	 Catch:{ all -> 0x0022 }
            r2 = com.facebook.common.references.CloseableReference.cloneOrNull(r2);	 Catch:{ all -> 0x0022 }
            r1.mSourceImageRef = r2;	 Catch:{ all -> 0x0022 }
            r1.mStatus = r3;	 Catch:{ all -> 0x0022 }
            r2 = 1;
            r1.mIsDirty = r2;	 Catch:{ all -> 0x0022 }
            r2 = r1.setRunningIfDirtyAndNotRunning();	 Catch:{ all -> 0x0022 }
            monitor-exit(r1);	 Catch:{ all -> 0x0022 }
            com.facebook.common.references.CloseableReference.closeSafely(r0);
            if (r2 == 0) goto L_0x0021;
        L_0x001e:
            r1.submitPostprocessing();
        L_0x0021:
            return;
        L_0x0022:
            r2 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0022 }
            throw r2;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.PostprocessorProducer.PostprocessorConsumer.updateSourceImageRef(com.facebook.common.references.CloseableReference, int):void");
        }

        private void submitPostprocessing() {
            PostprocessorProducer.this.mExecutor.execute(new Runnable() {
                public void run() {
                    CloseableReference access$300;
                    int access$400;
                    synchronized (PostprocessorConsumer.this) {
                        access$300 = PostprocessorConsumer.this.mSourceImageRef;
                        access$400 = PostprocessorConsumer.this.mStatus;
                        PostprocessorConsumer.this.mSourceImageRef = null;
                        PostprocessorConsumer.this.mIsDirty = false;
                    }
                    if (CloseableReference.isValid(access$300)) {
                        try {
                            PostprocessorConsumer.this.doPostprocessing(access$300, access$400);
                        } finally {
                            CloseableReference.closeSafely(access$300);
                        }
                    }
                    PostprocessorConsumer.this.clearRunningAndStartIfDirty();
                }
            });
        }

        private void clearRunningAndStartIfDirty() {
            boolean runningIfDirtyAndNotRunning;
            synchronized (this) {
                this.mIsPostProcessingRunning = false;
                runningIfDirtyAndNotRunning = setRunningIfDirtyAndNotRunning();
            }
            if (runningIfDirtyAndNotRunning) {
                submitPostprocessing();
            }
        }

        private synchronized boolean setRunningIfDirtyAndNotRunning() {
            if (this.mIsClosed || !this.mIsDirty || this.mIsPostProcessingRunning || !CloseableReference.isValid(this.mSourceImageRef)) {
                return false;
            }
            this.mIsPostProcessingRunning = true;
            return true;
        }

        private void doPostprocessing(CloseableReference<CloseableImage> closeableReference, int i) {
            Preconditions.checkArgument(CloseableReference.isValid(closeableReference));
            if (shouldPostprocess((CloseableImage) closeableReference.get())) {
                ProducerListener producerListener = this.mListener;
                String str = this.mRequestId;
                String str2 = PostprocessorProducer.NAME;
                producerListener.onProducerStart(str, str2);
                CloseableReference closeableReference2 = null;
                try {
                    closeableReference2 = postprocessInternal((CloseableImage) closeableReference.get());
                    this.mListener.onProducerFinishWithSuccess(this.mRequestId, str2, getExtraMap(this.mListener, this.mRequestId, this.mPostprocessor));
                    maybeNotifyOnNewResult(closeableReference2, i);
                } catch (Throwable e) {
                    this.mListener.onProducerFinishWithFailure(this.mRequestId, str2, e, getExtraMap(this.mListener, this.mRequestId, this.mPostprocessor));
                    maybeNotifyOnFailure(e);
                } finally {
                    CloseableReference.closeSafely(closeableReference2);
                }
            } else {
                maybeNotifyOnNewResult(closeableReference, i);
            }
        }

        @Nullable
        private Map<String, String> getExtraMap(ProducerListener producerListener, String str, Postprocessor postprocessor) {
            if (!producerListener.requiresExtraMap(str)) {
                return null;
            }
            return ImmutableMap.of(PostprocessorProducer.POSTPROCESSOR, postprocessor.getName());
        }

        private boolean shouldPostprocess(CloseableImage closeableImage) {
            return closeableImage instanceof CloseableStaticBitmap;
        }

        private CloseableReference<CloseableImage> postprocessInternal(CloseableImage closeableImage) {
            CloseableStaticBitmap closeableStaticBitmap = (CloseableStaticBitmap) closeableImage;
            CloseableReference process = this.mPostprocessor.process(closeableStaticBitmap.getUnderlyingBitmap(), PostprocessorProducer.this.mBitmapFactory);
            try {
                CloseableReference<CloseableImage> of = CloseableReference.of(new CloseableStaticBitmap(process, closeableImage.getQualityInfo(), closeableStaticBitmap.getRotationAngle(), closeableStaticBitmap.getExifOrientation()));
                return of;
            } finally {
                CloseableReference.closeSafely(process);
            }
        }

        private void maybeNotifyOnNewResult(CloseableReference<CloseableImage> closeableReference, int i) {
            boolean isLast = BaseConsumer.isLast(i);
            if ((!isLast && !isClosed()) || (isLast && close())) {
                getConsumer().onNewResult(closeableReference, i);
            }
        }

        private void maybeNotifyOnFailure(Throwable th) {
            if (close()) {
                getConsumer().onFailure(th);
            }
        }

        private void maybeNotifyOnCancellation() {
            if (close()) {
                getConsumer().onCancellation();
            }
        }

        private synchronized boolean isClosed() {
            return this.mIsClosed;
        }

        private boolean close() {
            synchronized (this) {
                if (this.mIsClosed) {
                    return false;
                }
                CloseableReference closeableReference = this.mSourceImageRef;
                this.mSourceImageRef = null;
                this.mIsClosed = true;
                CloseableReference.closeSafely(closeableReference);
                return true;
            }
        }
    }

    class RepeatedPostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> implements RepeatedPostprocessorRunner {
        @GuardedBy("RepeatedPostprocessorConsumer.this")
        private boolean mIsClosed;
        @GuardedBy("RepeatedPostprocessorConsumer.this")
        @Nullable
        private CloseableReference<CloseableImage> mSourceImageRef;

        private RepeatedPostprocessorConsumer(PostprocessorConsumer postprocessorConsumer, RepeatedPostprocessor repeatedPostprocessor, ProducerContext producerContext) {
            super(postprocessorConsumer);
            this.mIsClosed = false;
            this.mSourceImageRef = null;
            repeatedPostprocessor.setCallback(this);
            producerContext.addCallbacks(new BaseProducerContextCallbacks(PostprocessorProducer.this) {
                public void onCancellationRequested() {
                    if (RepeatedPostprocessorConsumer.this.close()) {
                        RepeatedPostprocessorConsumer.this.getConsumer().onCancellation();
                    }
                }
            });
        }

        protected void onNewResultImpl(CloseableReference<CloseableImage> closeableReference, int i) {
            if (!BaseConsumer.isNotLast(i)) {
                setSourceImageRef(closeableReference);
                updateInternal();
            }
        }

        protected void onFailureImpl(Throwable th) {
            if (close()) {
                getConsumer().onFailure(th);
            }
        }

        protected void onCancellationImpl() {
            if (close()) {
                getConsumer().onCancellation();
            }
        }

        public synchronized void update() {
            updateInternal();
        }

        /* JADX WARNING: Missing block: B:9:?, code:
            getConsumer().onNewResult(r0, 0);
     */
        /* JADX WARNING: Missing block: B:12:0x001b, code:
            com.facebook.common.references.CloseableReference.closeSafely(r0);
     */
        private void updateInternal() {
            /*
            r3 = this;
            monitor-enter(r3);
            r0 = r3.mIsClosed;	 Catch:{ all -> 0x001f }
            if (r0 == 0) goto L_0x0007;
        L_0x0005:
            monitor-exit(r3);	 Catch:{ all -> 0x001f }
            return;
        L_0x0007:
            r0 = r3.mSourceImageRef;	 Catch:{ all -> 0x001f }
            r0 = com.facebook.common.references.CloseableReference.cloneOrNull(r0);	 Catch:{ all -> 0x001f }
            monitor-exit(r3);	 Catch:{ all -> 0x001f }
            r1 = r3.getConsumer();	 Catch:{ all -> 0x001a }
            r2 = 0;
            r1.onNewResult(r0, r2);	 Catch:{ all -> 0x001a }
            com.facebook.common.references.CloseableReference.closeSafely(r0);
            return;
        L_0x001a:
            r1 = move-exception;
            com.facebook.common.references.CloseableReference.closeSafely(r0);
            throw r1;
        L_0x001f:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x001f }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.PostprocessorProducer.RepeatedPostprocessorConsumer.updateInternal():void");
        }

        private void setSourceImageRef(CloseableReference<CloseableImage> closeableReference) {
            synchronized (this) {
                if (this.mIsClosed) {
                    return;
                }
                CloseableReference closeableReference2 = this.mSourceImageRef;
                this.mSourceImageRef = CloseableReference.cloneOrNull((CloseableReference) closeableReference);
                CloseableReference.closeSafely(closeableReference2);
            }
        }

        private boolean close() {
            synchronized (this) {
                if (this.mIsClosed) {
                    return false;
                }
                CloseableReference closeableReference = this.mSourceImageRef;
                this.mSourceImageRef = null;
                this.mIsClosed = true;
                CloseableReference.closeSafely(closeableReference);
                return true;
            }
        }
    }

    class SingleUsePostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {
        private SingleUsePostprocessorConsumer(PostprocessorConsumer postprocessorConsumer) {
            super(postprocessorConsumer);
        }

        protected void onNewResultImpl(CloseableReference<CloseableImage> closeableReference, int i) {
            if (!BaseConsumer.isNotLast(i)) {
                getConsumer().onNewResult(closeableReference, i);
            }
        }
    }

    public PostprocessorProducer(Producer<CloseableReference<CloseableImage>> producer, PlatformBitmapFactory platformBitmapFactory, Executor executor) {
        this.mInputProducer = (Producer) Preconditions.checkNotNull(producer);
        this.mBitmapFactory = platformBitmapFactory;
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
    }

    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
        Consumer singleUsePostprocessorConsumer;
        ProducerListener listener = producerContext.getListener();
        Postprocessor postprocessor = producerContext.getImageRequest().getPostprocessor();
        PostprocessorConsumer postprocessorConsumer = new PostprocessorConsumer(consumer, listener, producerContext.getId(), postprocessor, producerContext);
        if (postprocessor instanceof RepeatedPostprocessor) {
            Consumer repeatedPostprocessorConsumer = new RepeatedPostprocessorConsumer(postprocessorConsumer, (RepeatedPostprocessor) postprocessor, producerContext);
        } else {
            singleUsePostprocessorConsumer = new SingleUsePostprocessorConsumer(postprocessorConsumer);
        }
        this.mInputProducer.produceResults(singleUsePostprocessorConsumer, producerContext);
    }
}
