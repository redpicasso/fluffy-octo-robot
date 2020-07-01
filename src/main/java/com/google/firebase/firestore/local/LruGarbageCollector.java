package com.google.firebase.firestore.local;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.AsyncQueue.DelayedTask;
import com.google.firebase.firestore.util.AsyncQueue.TimerId;
import com.google.firebase.firestore.util.Logger;
import java.util.Comparator;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class LruGarbageCollector {
    private static final long INITIAL_GC_DELAY_MS = TimeUnit.MINUTES.toMillis(1);
    private static final long REGULAR_GC_DELAY_MS = TimeUnit.MINUTES.toMillis(5);
    private final LruDelegate delegate;
    private final Params params;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static class Params {
        private static final long COLLECTION_DISABLED = -1;
        private static final long DEFAULT_CACHE_SIZE_BYTES = 104857600;
        private static final int DEFAULT_COLLECTION_PERCENTILE = 10;
        private static final int DEFAULT_MAX_SEQUENCE_NUMBERS_TO_COLLECT = 1000;
        final int maximumSequenceNumbersToCollect;
        final long minBytesThreshold;
        final int percentileToCollect;

        public static Params Default() {
            return new Params(DEFAULT_CACHE_SIZE_BYTES, 10, 1000);
        }

        public static Params Disabled() {
            return new Params(-1, 0, 0);
        }

        public static Params WithCacheSizeBytes(long j) {
            return new Params(j, 10, 1000);
        }

        Params(long j, int i, int i2) {
            this.minBytesThreshold = j;
            this.percentileToCollect = i;
            this.maximumSequenceNumbersToCollect = i2;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static class Results {
        private final int documentsRemoved;
        private final boolean hasRun;
        private final int sequenceNumbersCollected;
        private final int targetsRemoved;

        static Results DidNotRun() {
            return new Results(false, 0, 0, 0);
        }

        Results(boolean z, int i, int i2, int i3) {
            this.hasRun = z;
            this.sequenceNumbersCollected = i;
            this.targetsRemoved = i2;
            this.documentsRemoved = i3;
        }

        public boolean hasRun() {
            return this.hasRun;
        }

        public int getSequenceNumbersCollected() {
            return this.sequenceNumbersCollected;
        }

        public int getTargetsRemoved() {
            return this.targetsRemoved;
        }

        public int getDocumentsRemoved() {
            return this.documentsRemoved;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    private static class RollingSequenceNumberBuffer {
        private static final Comparator<Long> COMPARATOR = LruGarbageCollector$RollingSequenceNumberBuffer$$Lambda$1.lambdaFactory$();
        private final int maxElements;
        private final PriorityQueue<Long> queue;

        RollingSequenceNumberBuffer(int i) {
            this.maxElements = i;
            this.queue = new PriorityQueue(i, COMPARATOR);
        }

        void addElement(Long l) {
            if (this.queue.size() < this.maxElements) {
                this.queue.add(l);
                return;
            }
            if (l.longValue() < ((Long) this.queue.peek()).longValue()) {
                this.queue.poll();
                this.queue.add(l);
            }
        }

        long getMaxValue() {
            return ((Long) this.queue.peek()).longValue();
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public class Scheduler {
        private final AsyncQueue asyncQueue;
        @Nullable
        private DelayedTask gcTask;
        private boolean hasRun = false;
        private final LocalStore localStore;

        public Scheduler(AsyncQueue asyncQueue, LocalStore localStore) {
            this.asyncQueue = asyncQueue;
            this.localStore = localStore;
        }

        public void start() {
            if (LruGarbageCollector.this.params.minBytesThreshold != -1) {
                scheduleGC();
            }
        }

        public void stop() {
            DelayedTask delayedTask = this.gcTask;
            if (delayedTask != null) {
                delayedTask.cancel();
            }
        }

        private void scheduleGC() {
            this.gcTask = this.asyncQueue.enqueueAfterDelay(TimerId.GARBAGE_COLLECTION, this.hasRun ? LruGarbageCollector.REGULAR_GC_DELAY_MS : LruGarbageCollector.INITIAL_GC_DELAY_MS, LruGarbageCollector$Scheduler$$Lambda$1.lambdaFactory$(this));
        }

        static /* synthetic */ void lambda$scheduleGC$0(Scheduler scheduler) {
            scheduler.localStore.collectGarbage(LruGarbageCollector.this);
            scheduler.hasRun = true;
            scheduler.scheduleGC();
        }
    }

    LruGarbageCollector(LruDelegate lruDelegate, Params params) {
        this.delegate = lruDelegate;
        this.params = params;
    }

    public Scheduler newScheduler(AsyncQueue asyncQueue, LocalStore localStore) {
        return new Scheduler(asyncQueue, localStore);
    }

    int calculateQueryCount(int i) {
        return (int) ((((float) i) / 100.0f) * ((float) this.delegate.getSequenceNumberCount()));
    }

    long getNthSequenceNumber(int i) {
        if (i == 0) {
            return -1;
        }
        RollingSequenceNumberBuffer rollingSequenceNumberBuffer = new RollingSequenceNumberBuffer(i);
        this.delegate.forEachTarget(LruGarbageCollector$$Lambda$1.lambdaFactory$(rollingSequenceNumberBuffer));
        LruDelegate lruDelegate = this.delegate;
        rollingSequenceNumberBuffer.getClass();
        lruDelegate.forEachOrphanedDocumentSequenceNumber(LruGarbageCollector$$Lambda$2.lambdaFactory$(rollingSequenceNumberBuffer));
        return rollingSequenceNumberBuffer.getMaxValue();
    }

    int removeTargets(long j, SparseArray<?> sparseArray) {
        return this.delegate.removeTargets(j, sparseArray);
    }

    int removeOrphanedDocuments(long j) {
        return this.delegate.removeOrphanedDocuments(j);
    }

    Results collect(SparseArray<?> sparseArray) {
        String str = "LruGarbageCollector";
        if (this.params.minBytesThreshold == -1) {
            Logger.debug(str, "Garbage collection skipped; disabled", new Object[0]);
            return Results.DidNotRun();
        }
        long byteSize = getByteSize();
        if (byteSize >= this.params.minBytesThreshold) {
            return runGarbageCollection(sparseArray);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Garbage collection skipped; Cache size ");
        stringBuilder.append(byteSize);
        stringBuilder.append(" is lower than threshold ");
        stringBuilder.append(this.params.minBytesThreshold);
        Logger.debug(str, stringBuilder.toString(), new Object[0]);
        return Results.DidNotRun();
    }

    private Results runGarbageCollection(SparseArray<?> sparseArray) {
        long currentTimeMillis = System.currentTimeMillis();
        int calculateQueryCount = calculateQueryCount(this.params.percentileToCollect);
        String str = "LruGarbageCollector";
        if (calculateQueryCount > this.params.maximumSequenceNumbersToCollect) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Capping sequence numbers to collect down to the maximum of ");
            stringBuilder.append(this.params.maximumSequenceNumbersToCollect);
            stringBuilder.append(" from ");
            stringBuilder.append(calculateQueryCount);
            Logger.debug(str, stringBuilder.toString(), new Object[0]);
            calculateQueryCount = this.params.maximumSequenceNumbersToCollect;
        }
        long currentTimeMillis2 = System.currentTimeMillis();
        long nthSequenceNumber = getNthSequenceNumber(calculateQueryCount);
        long currentTimeMillis3 = System.currentTimeMillis();
        int removeTargets = removeTargets(nthSequenceNumber, sparseArray);
        long currentTimeMillis4 = System.currentTimeMillis();
        int removeOrphanedDocuments = removeOrphanedDocuments(nthSequenceNumber);
        long currentTimeMillis5 = System.currentTimeMillis();
        if (Logger.isDebugEnabled()) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("LRU Garbage Collection:\n");
            stringBuilder2.append("\tCounted targets in ");
            String str2 = str;
            stringBuilder2.append(currentTimeMillis2 - currentTimeMillis);
            stringBuilder2.append("ms\n");
            str = stringBuilder2.toString();
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(str);
            stringBuilder3.append(String.format(Locale.ROOT, "\tDetermined least recently used %d sequence numbers in %dms\n", new Object[]{Integer.valueOf(calculateQueryCount), Long.valueOf(currentTimeMillis3 - currentTimeMillis2)}));
            String stringBuilder4 = stringBuilder3.toString();
            StringBuilder stringBuilder5 = new StringBuilder();
            stringBuilder5.append(stringBuilder4);
            stringBuilder5.append(String.format(Locale.ROOT, "\tRemoved %d targets in %dms\n", new Object[]{Integer.valueOf(removeTargets), Long.valueOf(currentTimeMillis4 - currentTimeMillis3)}));
            stringBuilder4 = stringBuilder5.toString();
            stringBuilder5 = new StringBuilder();
            stringBuilder5.append(stringBuilder4);
            stringBuilder5.append(String.format(Locale.ROOT, "\tRemoved %d documents in %dms\n", new Object[]{Integer.valueOf(removeOrphanedDocuments), Long.valueOf(currentTimeMillis5 - currentTimeMillis4)}));
            stringBuilder4 = stringBuilder5.toString();
            stringBuilder5 = new StringBuilder();
            stringBuilder5.append(stringBuilder4);
            stringBuilder5.append(String.format(Locale.ROOT, "Total Duration: %dms", new Object[]{Long.valueOf(currentTimeMillis5 - currentTimeMillis)}));
            Logger.debug(str2, stringBuilder5.toString(), new Object[0]);
        }
        return new Results(true, calculateQueryCount, removeTargets, removeOrphanedDocuments);
    }

    long getByteSize() {
        return this.delegate.getByteSize();
    }
}
