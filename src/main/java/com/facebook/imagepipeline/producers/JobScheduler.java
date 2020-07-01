package com.facebook.imagepipeline.producers;

import android.os.SystemClock;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.imagepipeline.image.EncodedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;

public class JobScheduler {
    static final String QUEUE_TIME_KEY = "queueTime";
    private final Runnable mDoJobRunnable = new Runnable() {
        public void run() {
            JobScheduler.this.doJob();
        }
    };
    @GuardedBy("this")
    @VisibleForTesting
    EncodedImage mEncodedImage = null;
    private final Executor mExecutor;
    private final JobRunnable mJobRunnable;
    @GuardedBy("this")
    @VisibleForTesting
    long mJobStartTime = 0;
    @GuardedBy("this")
    @VisibleForTesting
    JobState mJobState = JobState.IDLE;
    @GuardedBy("this")
    @VisibleForTesting
    long mJobSubmitTime = 0;
    private final int mMinimumJobIntervalMs;
    @GuardedBy("this")
    @VisibleForTesting
    int mStatus = 0;
    private final Runnable mSubmitJobRunnable = new Runnable() {
        public void run() {
            JobScheduler.this.submitJob();
        }
    };

    /* renamed from: com.facebook.imagepipeline.producers.JobScheduler$3 */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$imagepipeline$producers$JobScheduler$JobState = new int[JobState.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:8:?, code:
            $SwitchMap$com$facebook$imagepipeline$producers$JobScheduler$JobState[com.facebook.imagepipeline.producers.JobScheduler.JobState.RUNNING_AND_PENDING.ordinal()] = 4;
     */
        static {
            /*
            r0 = com.facebook.imagepipeline.producers.JobScheduler.JobState.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$facebook$imagepipeline$producers$JobScheduler$JobState = r0;
            r0 = $SwitchMap$com$facebook$imagepipeline$producers$JobScheduler$JobState;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.facebook.imagepipeline.producers.JobScheduler.JobState.IDLE;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$facebook$imagepipeline$producers$JobScheduler$JobState;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.facebook.imagepipeline.producers.JobScheduler.JobState.QUEUED;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$facebook$imagepipeline$producers$JobScheduler$JobState;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.facebook.imagepipeline.producers.JobScheduler.JobState.RUNNING;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r0 = $SwitchMap$com$facebook$imagepipeline$producers$JobScheduler$JobState;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = com.facebook.imagepipeline.producers.JobScheduler.JobState.RUNNING_AND_PENDING;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.JobScheduler.3.<clinit>():void");
        }
    }

    public interface JobRunnable {
        void run(EncodedImage encodedImage, int i);
    }

    @VisibleForTesting
    static class JobStartExecutorSupplier {
        private static ScheduledExecutorService sJobStarterExecutor;

        JobStartExecutorSupplier() {
        }

        static ScheduledExecutorService get() {
            if (sJobStarterExecutor == null) {
                sJobStarterExecutor = Executors.newSingleThreadScheduledExecutor();
            }
            return sJobStarterExecutor;
        }
    }

    @VisibleForTesting
    enum JobState {
        IDLE,
        QUEUED,
        RUNNING,
        RUNNING_AND_PENDING
    }

    public JobScheduler(Executor executor, JobRunnable jobRunnable, int i) {
        this.mExecutor = executor;
        this.mJobRunnable = jobRunnable;
        this.mMinimumJobIntervalMs = i;
    }

    public void clearJob() {
        EncodedImage encodedImage;
        synchronized (this) {
            encodedImage = this.mEncodedImage;
            this.mEncodedImage = null;
            this.mStatus = 0;
        }
        EncodedImage.closeSafely(encodedImage);
    }

    public boolean updateJob(EncodedImage encodedImage, int i) {
        if (!shouldProcess(encodedImage, i)) {
            return false;
        }
        EncodedImage encodedImage2;
        synchronized (this) {
            encodedImage2 = this.mEncodedImage;
            this.mEncodedImage = EncodedImage.cloneOrNull(encodedImage);
            this.mStatus = i;
        }
        EncodedImage.closeSafely(encodedImage2);
        return true;
    }

    /* JADX WARNING: Missing block: B:17:0x003f, code:
            if (r3 == false) goto L_0x0045;
     */
    /* JADX WARNING: Missing block: B:18:0x0041, code:
            enqueueJob(r5 - r0);
     */
    /* JADX WARNING: Missing block: B:19:0x0045, code:
            return true;
     */
    public boolean scheduleJob() {
        /*
        r7 = this;
        r0 = android.os.SystemClock.uptimeMillis();
        monitor-enter(r7);
        r2 = r7.mEncodedImage;	 Catch:{ all -> 0x0046 }
        r3 = r7.mStatus;	 Catch:{ all -> 0x0046 }
        r2 = shouldProcess(r2, r3);	 Catch:{ all -> 0x0046 }
        r3 = 0;
        if (r2 != 0) goto L_0x0012;
    L_0x0010:
        monitor-exit(r7);	 Catch:{ all -> 0x0046 }
        return r3;
    L_0x0012:
        r2 = com.facebook.imagepipeline.producers.JobScheduler.AnonymousClass3.$SwitchMap$com$facebook$imagepipeline$producers$JobScheduler$JobState;	 Catch:{ all -> 0x0046 }
        r4 = r7.mJobState;	 Catch:{ all -> 0x0046 }
        r4 = r4.ordinal();	 Catch:{ all -> 0x0046 }
        r2 = r2[r4];	 Catch:{ all -> 0x0046 }
        r4 = 1;
        if (r2 == r4) goto L_0x002d;
    L_0x001f:
        r5 = 2;
        if (r2 == r5) goto L_0x002a;
    L_0x0022:
        r5 = 3;
        if (r2 == r5) goto L_0x0026;
    L_0x0025:
        goto L_0x002a;
    L_0x0026:
        r2 = com.facebook.imagepipeline.producers.JobScheduler.JobState.RUNNING_AND_PENDING;	 Catch:{ all -> 0x0046 }
        r7.mJobState = r2;	 Catch:{ all -> 0x0046 }
    L_0x002a:
        r5 = 0;
        goto L_0x003e;
    L_0x002d:
        r2 = r7.mJobStartTime;	 Catch:{ all -> 0x0046 }
        r5 = r7.mMinimumJobIntervalMs;	 Catch:{ all -> 0x0046 }
        r5 = (long) r5;	 Catch:{ all -> 0x0046 }
        r2 = r2 + r5;
        r5 = java.lang.Math.max(r2, r0);	 Catch:{ all -> 0x0046 }
        r7.mJobSubmitTime = r0;	 Catch:{ all -> 0x0046 }
        r2 = com.facebook.imagepipeline.producers.JobScheduler.JobState.QUEUED;	 Catch:{ all -> 0x0046 }
        r7.mJobState = r2;	 Catch:{ all -> 0x0046 }
        r3 = 1;
    L_0x003e:
        monitor-exit(r7);	 Catch:{ all -> 0x0046 }
        if (r3 == 0) goto L_0x0045;
    L_0x0041:
        r5 = r5 - r0;
        r7.enqueueJob(r5);
    L_0x0045:
        return r4;
    L_0x0046:
        r0 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x0046 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.JobScheduler.scheduleJob():boolean");
    }

    private void enqueueJob(long j) {
        if (j > 0) {
            JobStartExecutorSupplier.get().schedule(this.mSubmitJobRunnable, j, TimeUnit.MILLISECONDS);
        } else {
            this.mSubmitJobRunnable.run();
        }
    }

    private void submitJob() {
        this.mExecutor.execute(this.mDoJobRunnable);
    }

    private void doJob() {
        EncodedImage encodedImage;
        int i;
        long uptimeMillis = SystemClock.uptimeMillis();
        synchronized (this) {
            encodedImage = this.mEncodedImage;
            i = this.mStatus;
            this.mEncodedImage = null;
            this.mStatus = 0;
            this.mJobState = JobState.RUNNING;
            this.mJobStartTime = uptimeMillis;
        }
        try {
            if (shouldProcess(encodedImage, i)) {
                this.mJobRunnable.run(encodedImage, i);
            }
            EncodedImage.closeSafely(encodedImage);
            onJobFinished();
        } catch (Throwable th) {
            EncodedImage.closeSafely(encodedImage);
            onJobFinished();
        }
    }

    private void onJobFinished() {
        long max;
        Object obj;
        long uptimeMillis = SystemClock.uptimeMillis();
        synchronized (this) {
            if (this.mJobState == JobState.RUNNING_AND_PENDING) {
                max = Math.max(this.mJobStartTime + ((long) this.mMinimumJobIntervalMs), uptimeMillis);
                obj = 1;
                this.mJobSubmitTime = uptimeMillis;
                this.mJobState = JobState.QUEUED;
            } else {
                this.mJobState = JobState.IDLE;
                max = 0;
                obj = null;
            }
        }
        if (obj != null) {
            enqueueJob(max - uptimeMillis);
        }
    }

    private static boolean shouldProcess(EncodedImage encodedImage, int i) {
        return BaseConsumer.isLast(i) || BaseConsumer.statusHasFlag(i, 4) || EncodedImage.isValid(encodedImage);
    }

    public synchronized long getQueuedTime() {
        return this.mJobStartTime - this.mJobSubmitTime;
    }
}
