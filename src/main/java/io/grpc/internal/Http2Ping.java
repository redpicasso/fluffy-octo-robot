package io.grpc.internal;

import com.google.common.base.Stopwatch;
import io.grpc.internal.ClientTransport.PingCallback;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

public class Http2Ping {
    private static final Logger log = Logger.getLogger(Http2Ping.class.getName());
    @GuardedBy("this")
    private Map<PingCallback, Executor> callbacks = new LinkedHashMap();
    @GuardedBy("this")
    private boolean completed;
    private final long data;
    @GuardedBy("this")
    private Throwable failureCause;
    @GuardedBy("this")
    private long roundTripTimeNanos;
    private final Stopwatch stopwatch;

    public Http2Ping(long j, Stopwatch stopwatch) {
        this.data = j;
        this.stopwatch = stopwatch;
    }

    /* JADX WARNING: Missing block: B:12:0x001e, code:
            doExecute(r4, r3);
     */
    /* JADX WARNING: Missing block: B:13:0x0021, code:
            return;
     */
    public void addCallback(io.grpc.internal.ClientTransport.PingCallback r3, java.util.concurrent.Executor r4) {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = r2.completed;	 Catch:{ all -> 0x0022 }
        if (r0 != 0) goto L_0x000c;
    L_0x0005:
        r0 = r2.callbacks;	 Catch:{ all -> 0x0022 }
        r0.put(r3, r4);	 Catch:{ all -> 0x0022 }
        monitor-exit(r2);	 Catch:{ all -> 0x0022 }
        return;
    L_0x000c:
        r0 = r2.failureCause;	 Catch:{ all -> 0x0022 }
        if (r0 == 0) goto L_0x0017;
    L_0x0010:
        r0 = r2.failureCause;	 Catch:{ all -> 0x0022 }
        r3 = asRunnable(r3, r0);	 Catch:{ all -> 0x0022 }
        goto L_0x001d;
    L_0x0017:
        r0 = r2.roundTripTimeNanos;	 Catch:{ all -> 0x0022 }
        r3 = asRunnable(r3, r0);	 Catch:{ all -> 0x0022 }
    L_0x001d:
        monitor-exit(r2);	 Catch:{ all -> 0x0022 }
        doExecute(r4, r3);
        return;
    L_0x0022:
        r3 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0022 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.Http2Ping.addCallback(io.grpc.internal.ClientTransport$PingCallback, java.util.concurrent.Executor):void");
    }

    public long payload() {
        return this.data;
    }

    /* JADX WARNING: Missing block: B:9:0x001b, code:
            r3 = r3.entrySet().iterator();
     */
    /* JADX WARNING: Missing block: B:11:0x0027, code:
            if (r3.hasNext() == false) goto L_0x0043;
     */
    /* JADX WARNING: Missing block: B:12:0x0029, code:
            r4 = (java.util.Map.Entry) r3.next();
            doExecute((java.util.concurrent.Executor) r4.getValue(), asRunnable((io.grpc.internal.ClientTransport.PingCallback) r4.getKey(), r1));
     */
    /* JADX WARNING: Missing block: B:13:0x0043, code:
            return true;
     */
    public boolean complete() {
        /*
        r6 = this;
        monitor-enter(r6);
        r0 = r6.completed;	 Catch:{ all -> 0x0044 }
        if (r0 == 0) goto L_0x0008;
    L_0x0005:
        r0 = 0;
        monitor-exit(r6);	 Catch:{ all -> 0x0044 }
        return r0;
    L_0x0008:
        r0 = 1;
        r6.completed = r0;	 Catch:{ all -> 0x0044 }
        r1 = r6.stopwatch;	 Catch:{ all -> 0x0044 }
        r2 = java.util.concurrent.TimeUnit.NANOSECONDS;	 Catch:{ all -> 0x0044 }
        r1 = r1.elapsed(r2);	 Catch:{ all -> 0x0044 }
        r6.roundTripTimeNanos = r1;	 Catch:{ all -> 0x0044 }
        r3 = r6.callbacks;	 Catch:{ all -> 0x0044 }
        r4 = 0;
        r6.callbacks = r4;	 Catch:{ all -> 0x0044 }
        monitor-exit(r6);	 Catch:{ all -> 0x0044 }
        r3 = r3.entrySet();
        r3 = r3.iterator();
    L_0x0023:
        r4 = r3.hasNext();
        if (r4 == 0) goto L_0x0043;
    L_0x0029:
        r4 = r3.next();
        r4 = (java.util.Map.Entry) r4;
        r5 = r4.getValue();
        r5 = (java.util.concurrent.Executor) r5;
        r4 = r4.getKey();
        r4 = (io.grpc.internal.ClientTransport.PingCallback) r4;
        r4 = asRunnable(r4, r1);
        doExecute(r5, r4);
        goto L_0x0023;
    L_0x0043:
        return r0;
    L_0x0044:
        r0 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0044 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.Http2Ping.complete():boolean");
    }

    /* JADX WARNING: Missing block: B:8:0x0012, code:
            r0 = r0.entrySet().iterator();
     */
    /* JADX WARNING: Missing block: B:10:0x001e, code:
            if (r0.hasNext() == false) goto L_0x0036;
     */
    /* JADX WARNING: Missing block: B:11:0x0020, code:
            r1 = (java.util.Map.Entry) r0.next();
            notifyFailed((io.grpc.internal.ClientTransport.PingCallback) r1.getKey(), (java.util.concurrent.Executor) r1.getValue(), r4);
     */
    /* JADX WARNING: Missing block: B:12:0x0036, code:
            return;
     */
    public void failed(java.lang.Throwable r4) {
        /*
        r3 = this;
        monitor-enter(r3);
        r0 = r3.completed;	 Catch:{ all -> 0x0037 }
        if (r0 == 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r3);	 Catch:{ all -> 0x0037 }
        return;
    L_0x0007:
        r0 = 1;
        r3.completed = r0;	 Catch:{ all -> 0x0037 }
        r3.failureCause = r4;	 Catch:{ all -> 0x0037 }
        r0 = r3.callbacks;	 Catch:{ all -> 0x0037 }
        r1 = 0;
        r3.callbacks = r1;	 Catch:{ all -> 0x0037 }
        monitor-exit(r3);	 Catch:{ all -> 0x0037 }
        r0 = r0.entrySet();
        r0 = r0.iterator();
    L_0x001a:
        r1 = r0.hasNext();
        if (r1 == 0) goto L_0x0036;
    L_0x0020:
        r1 = r0.next();
        r1 = (java.util.Map.Entry) r1;
        r2 = r1.getKey();
        r2 = (io.grpc.internal.ClientTransport.PingCallback) r2;
        r1 = r1.getValue();
        r1 = (java.util.concurrent.Executor) r1;
        notifyFailed(r2, r1, r4);
        goto L_0x001a;
    L_0x0036:
        return;
    L_0x0037:
        r4 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0037 }
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.Http2Ping.failed(java.lang.Throwable):void");
    }

    public static void notifyFailed(PingCallback pingCallback, Executor executor, Throwable th) {
        doExecute(executor, asRunnable(pingCallback, th));
    }

    private static void doExecute(Executor executor, Runnable runnable) {
        try {
            executor.execute(runnable);
        } catch (Throwable th) {
            log.log(Level.SEVERE, "Failed to execute PingCallback", th);
        }
    }

    private static Runnable asRunnable(final PingCallback pingCallback, final long j) {
        return new Runnable() {
            public void run() {
                pingCallback.onSuccess(j);
            }
        };
    }

    private static Runnable asRunnable(final PingCallback pingCallback, final Throwable th) {
        return new Runnable() {
            public void run() {
                pingCallback.onFailure(th);
            }
        };
    }
}
