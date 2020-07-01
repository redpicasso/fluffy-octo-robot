package com.reactnative.ivpusic.imagepicker;

import android.util.Log;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;
import java.util.concurrent.atomic.AtomicInteger;

class ResultCollector {
    private WritableArray arrayResult;
    private boolean multiple;
    private Promise promise;
    private boolean resultSent;
    private int waitCount;
    private AtomicInteger waitCounter;

    ResultCollector() {
    }

    synchronized void setup(Promise promise, boolean z) {
        this.promise = promise;
        this.multiple = z;
        this.resultSent = false;
        this.waitCount = 0;
        this.waitCounter = new AtomicInteger(0);
        if (z) {
            this.arrayResult = new WritableNativeArray();
        }
    }

    synchronized void setWaitCount(int i) {
        this.waitCount = i;
        this.waitCounter = new AtomicInteger(0);
    }

    private synchronized boolean isRequestValid() {
        if (this.resultSent) {
            Log.w("image-crop-picker", "Skipping result, already sent...");
            return false;
        } else if (this.promise != null) {
            return true;
        } else {
            Log.w("image-crop-picker", "Trying to notify success but promise is not set");
            return false;
        }
    }

    /* JADX WARNING: Missing block: B:14:0x002f, code:
            return;
     */
    synchronized void notifySuccess(com.facebook.react.bridge.WritableMap r3) {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = r2.isRequestValid();	 Catch:{ all -> 0x0030 }
        if (r0 != 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r2);
        return;
    L_0x0009:
        r0 = r2.multiple;	 Catch:{ all -> 0x0030 }
        r1 = 1;
        if (r0 == 0) goto L_0x0027;
    L_0x000e:
        r0 = r2.arrayResult;	 Catch:{ all -> 0x0030 }
        r0.pushMap(r3);	 Catch:{ all -> 0x0030 }
        r3 = r2.waitCounter;	 Catch:{ all -> 0x0030 }
        r3 = r3.addAndGet(r1);	 Catch:{ all -> 0x0030 }
        r0 = r2.waitCount;	 Catch:{ all -> 0x0030 }
        if (r3 != r0) goto L_0x002e;
    L_0x001d:
        r3 = r2.promise;	 Catch:{ all -> 0x0030 }
        r0 = r2.arrayResult;	 Catch:{ all -> 0x0030 }
        r3.resolve(r0);	 Catch:{ all -> 0x0030 }
        r2.resultSent = r1;	 Catch:{ all -> 0x0030 }
        goto L_0x002e;
    L_0x0027:
        r0 = r2.promise;	 Catch:{ all -> 0x0030 }
        r0.resolve(r3);	 Catch:{ all -> 0x0030 }
        r2.resultSent = r1;	 Catch:{ all -> 0x0030 }
    L_0x002e:
        monitor-exit(r2);
        return;
    L_0x0030:
        r3 = move-exception;
        monitor-exit(r2);
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.reactnative.ivpusic.imagepicker.ResultCollector.notifySuccess(com.facebook.react.bridge.WritableMap):void");
    }

    synchronized void notifyProblem(String str, String str2) {
        if (isRequestValid()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Promise rejected. ");
            stringBuilder.append(str2);
            Log.e("image-crop-picker", stringBuilder.toString());
            this.promise.reject(str, str2);
            this.resultSent = true;
        }
    }

    synchronized void notifyProblem(String str, Throwable th) {
        if (isRequestValid()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Promise rejected. ");
            stringBuilder.append(th.getMessage());
            Log.e("image-crop-picker", stringBuilder.toString());
            this.promise.reject(str, th);
            this.resultSent = true;
        }
    }
}
