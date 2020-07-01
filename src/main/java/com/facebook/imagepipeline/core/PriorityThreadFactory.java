package com.facebook.imagepipeline.core;

import android.os.Process;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class PriorityThreadFactory implements ThreadFactory {
    private final boolean mAddThreadNumber;
    private final String mPrefix;
    private final AtomicInteger mThreadNumber;
    private final int mThreadPriority;

    public PriorityThreadFactory(int i) {
        this(i, "PriorityThreadFactory", true);
    }

    public PriorityThreadFactory(int i, String str, boolean z) {
        this.mThreadNumber = new AtomicInteger(1);
        this.mThreadPriority = i;
        this.mPrefix = str;
        this.mAddThreadNumber = z;
    }

    public Thread newThread(final Runnable runnable) {
        String stringBuilder;
        Runnable anonymousClass1 = new Runnable() {
            public void run() {
                try {
                    Process.setThreadPriority(PriorityThreadFactory.this.mThreadPriority);
                } catch (Throwable unused) {
                    runnable.run();
                }
            }
        };
        if (this.mAddThreadNumber) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(this.mPrefix);
            stringBuilder2.append("-");
            stringBuilder2.append(this.mThreadNumber.getAndIncrement());
            stringBuilder = stringBuilder2.toString();
        } else {
            stringBuilder = this.mPrefix;
        }
        return new Thread(anonymousClass1, stringBuilder);
    }
}
