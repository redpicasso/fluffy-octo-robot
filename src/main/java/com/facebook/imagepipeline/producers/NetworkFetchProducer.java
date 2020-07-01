package com.facebook.imagepipeline.producers;

import android.os.SystemClock;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.memory.ByteArrayPool;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteBufferOutputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.common.BytesRange;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.NetworkFetcher.Callback;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.annotation.Nullable;

public class NetworkFetchProducer implements Producer<EncodedImage> {
    public static final String INTERMEDIATE_RESULT_PRODUCER_EVENT = "intermediate_result";
    public static final String PRODUCER_NAME = "NetworkFetchProducer";
    private static final int READ_SIZE = 16384;
    @VisibleForTesting
    static final long TIME_BETWEEN_PARTIAL_RESULTS_MS = 100;
    private final ByteArrayPool mByteArrayPool;
    private final NetworkFetcher mNetworkFetcher;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    public NetworkFetchProducer(PooledByteBufferFactory pooledByteBufferFactory, ByteArrayPool byteArrayPool, NetworkFetcher networkFetcher) {
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mByteArrayPool = byteArrayPool;
        this.mNetworkFetcher = networkFetcher;
    }

    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        producerContext.getListener().onProducerStart(producerContext.getId(), PRODUCER_NAME);
        final FetchState createFetchState = this.mNetworkFetcher.createFetchState(consumer, producerContext);
        this.mNetworkFetcher.fetch(createFetchState, new Callback() {
            public void onResponse(InputStream inputStream, int i) throws IOException {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.beginSection("NetworkFetcher->onResponse");
                }
                NetworkFetchProducer.this.onResponse(createFetchState, inputStream, i);
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
            }

            public void onFailure(Throwable th) {
                NetworkFetchProducer.this.onFailure(createFetchState, th);
            }

            public void onCancellation() {
                NetworkFetchProducer.this.onCancellation(createFetchState);
            }
        });
    }

    protected void onResponse(FetchState fetchState, InputStream inputStream, int i) throws IOException {
        PooledByteBufferOutputStream newOutputStream;
        if (i > 0) {
            newOutputStream = this.mPooledByteBufferFactory.newOutputStream(i);
        } else {
            newOutputStream = this.mPooledByteBufferFactory.newOutputStream();
        }
        byte[] bArr = (byte[]) this.mByteArrayPool.get(16384);
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read < 0) {
                    break;
                } else if (read > 0) {
                    newOutputStream.write(bArr, 0, read);
                    maybeHandleIntermediateResult(newOutputStream, fetchState);
                    fetchState.getConsumer().onProgressUpdate(calculateProgress(newOutputStream.size(), i));
                }
            } finally {
                this.mByteArrayPool.release(bArr);
                newOutputStream.close();
            }
        }
        this.mNetworkFetcher.onFetchCompletion(fetchState, newOutputStream.size());
        handleFinalResult(newOutputStream, fetchState);
    }

    protected static float calculateProgress(int i, int i2) {
        return i2 > 0 ? ((float) i) / ((float) i2) : 1.0f - ((float) Math.exp(((double) (-i)) / 50000.0d));
    }

    protected void maybeHandleIntermediateResult(PooledByteBufferOutputStream pooledByteBufferOutputStream, FetchState fetchState) {
        long uptimeMillis = SystemClock.uptimeMillis();
        if (shouldPropagateIntermediateResults(fetchState) && uptimeMillis - fetchState.getLastIntermediateResultTimeMs() >= TIME_BETWEEN_PARTIAL_RESULTS_MS) {
            fetchState.setLastIntermediateResultTimeMs(uptimeMillis);
            fetchState.getListener().onProducerEvent(fetchState.getId(), PRODUCER_NAME, INTERMEDIATE_RESULT_PRODUCER_EVENT);
            notifyConsumer(pooledByteBufferOutputStream, fetchState.getOnNewResultStatusFlags(), fetchState.getResponseBytesRange(), fetchState.getConsumer());
        }
    }

    protected void handleFinalResult(PooledByteBufferOutputStream pooledByteBufferOutputStream, FetchState fetchState) {
        Map extraMap = getExtraMap(fetchState, pooledByteBufferOutputStream.size());
        ProducerListener listener = fetchState.getListener();
        String id = fetchState.getId();
        String str = PRODUCER_NAME;
        listener.onProducerFinishWithSuccess(id, str, extraMap);
        listener.onUltimateProducerReached(fetchState.getId(), str, true);
        notifyConsumer(pooledByteBufferOutputStream, fetchState.getOnNewResultStatusFlags() | 1, fetchState.getResponseBytesRange(), fetchState.getConsumer());
    }

    protected static void notifyConsumer(PooledByteBufferOutputStream pooledByteBufferOutputStream, int i, @Nullable BytesRange bytesRange, Consumer<EncodedImage> consumer) {
        Throwable th;
        CloseableReference of = CloseableReference.of(pooledByteBufferOutputStream.toByteBuffer());
        EncodedImage encodedImage;
        try {
            encodedImage = new EncodedImage(of);
            try {
                encodedImage.setBytesRange(bytesRange);
                encodedImage.parseMetaData();
                consumer.onNewResult(encodedImage, i);
                EncodedImage.closeSafely(encodedImage);
                CloseableReference.closeSafely(of);
            } catch (Throwable th2) {
                th = th2;
                EncodedImage.closeSafely(encodedImage);
                CloseableReference.closeSafely(of);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            encodedImage = null;
            EncodedImage.closeSafely(encodedImage);
            CloseableReference.closeSafely(of);
            throw th;
        }
    }

    private void onFailure(FetchState fetchState, Throwable th) {
        ProducerListener listener = fetchState.getListener();
        String id = fetchState.getId();
        String str = PRODUCER_NAME;
        listener.onProducerFinishWithFailure(id, str, th, null);
        fetchState.getListener().onUltimateProducerReached(fetchState.getId(), str, false);
        fetchState.getConsumer().onFailure(th);
    }

    private void onCancellation(FetchState fetchState) {
        fetchState.getListener().onProducerFinishWithCancellation(fetchState.getId(), PRODUCER_NAME, null);
        fetchState.getConsumer().onCancellation();
    }

    private boolean shouldPropagateIntermediateResults(FetchState fetchState) {
        if (fetchState.getContext().isIntermediateResultExpected()) {
            return this.mNetworkFetcher.shouldPropagate(fetchState);
        }
        return false;
    }

    @Nullable
    private Map<String, String> getExtraMap(FetchState fetchState, int i) {
        if (fetchState.getListener().requiresExtraMap(fetchState.getId())) {
            return this.mNetworkFetcher.getExtraMap(fetchState, i);
        }
        return null;
    }
}
