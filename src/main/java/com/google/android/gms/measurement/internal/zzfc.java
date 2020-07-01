package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

public final class zzfc extends zzge {
    private static final AtomicLong zznj = new AtomicLong(Long.MIN_VALUE);
    private zzfg zzna;
    private zzfg zznb;
    private final PriorityBlockingQueue<zzfh<?>> zznc = new PriorityBlockingQueue();
    private final BlockingQueue<zzfh<?>> zznd = new LinkedBlockingQueue();
    private final UncaughtExceptionHandler zzne = new zzfe(this, "Thread death: Uncaught exception on worker thread");
    private final UncaughtExceptionHandler zznf = new zzfe(this, "Thread death: Uncaught exception on network thread");
    private final Object zzng = new Object();
    private final Semaphore zznh = new Semaphore(2);
    private volatile boolean zzni;

    zzfc(zzfj zzfj) {
        super(zzfj);
    }

    protected final boolean zzbk() {
        return false;
    }

    public final void zzo() {
        if (Thread.currentThread() != this.zzna) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public final void zzn() {
        if (Thread.currentThread() != this.zznb) {
            throw new IllegalStateException("Call expected from network thread");
        }
    }

    public final boolean zzhp() {
        return Thread.currentThread() == this.zzna;
    }

    public final <V> Future<V> zza(Callable<V> callable) throws IllegalStateException {
        zzbi();
        Preconditions.checkNotNull(callable);
        zzfh zzfh = new zzfh(this, (Callable) callable, false, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzna) {
            if (!this.zznc.isEmpty()) {
                zzab().zzgn().zzao("Callable skipped the worker queue.");
            }
            zzfh.run();
        } else {
            zza(zzfh);
        }
        return zzfh;
    }

    public final <V> Future<V> zzb(Callable<V> callable) throws IllegalStateException {
        zzbi();
        Preconditions.checkNotNull(callable);
        zzfh zzfh = new zzfh(this, (Callable) callable, true, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzna) {
            zzfh.run();
        } else {
            zza(zzfh);
        }
        return zzfh;
    }

    public final void zza(Runnable runnable) throws IllegalStateException {
        zzbi();
        Preconditions.checkNotNull(runnable);
        zza(new zzfh(this, runnable, false, "Task exception on worker thread"));
    }

    /* JADX WARNING: Missing block: B:17:?, code:
            r2 = zzab().zzgn();
            r3 = "Interrupted waiting for ";
            r4 = java.lang.String.valueOf(r4);
     */
    /* JADX WARNING: Missing block: B:18:0x0049, code:
            if (r4.length() != 0) goto L_0x004b;
     */
    /* JADX WARNING: Missing block: B:19:0x004b, code:
            r3 = r3.concat(r4);
     */
    /* JADX WARNING: Missing block: B:20:0x0050, code:
            r3 = new java.lang.String(r3);
     */
    /* JADX WARNING: Missing block: B:21:0x0056, code:
            r2.zzao(r3);
     */
    /* JADX WARNING: Missing block: B:23:0x005b, code:
            return null;
     */
    final <T> T zza(java.util.concurrent.atomic.AtomicReference<T> r1, long r2, java.lang.String r4, java.lang.Runnable r5) {
        /*
        r0 = this;
        monitor-enter(r1);
        r2 = r0.zzaa();	 Catch:{ all -> 0x005c }
        r2.zza(r5);	 Catch:{ all -> 0x005c }
        r2 = 15000; // 0x3a98 float:2.102E-41 double:7.411E-320;
        r1.wait(r2);	 Catch:{ InterruptedException -> 0x0037 }
        monitor-exit(r1);	 Catch:{ all -> 0x005c }
        r1 = r1.get();
        if (r1 != 0) goto L_0x0036;
    L_0x0014:
        r2 = r0.zzab();
        r2 = r2.zzgn();
        r3 = "Timed out waiting for ";
        r4 = java.lang.String.valueOf(r4);
        r5 = r4.length();
        if (r5 == 0) goto L_0x002d;
    L_0x0028:
        r3 = r3.concat(r4);
        goto L_0x0033;
    L_0x002d:
        r4 = new java.lang.String;
        r4.<init>(r3);
        r3 = r4;
    L_0x0033:
        r2.zzao(r3);
    L_0x0036:
        return r1;
    L_0x0037:
        r2 = r0.zzab();	 Catch:{ all -> 0x005c }
        r2 = r2.zzgn();	 Catch:{ all -> 0x005c }
        r3 = "Interrupted waiting for ";
        r4 = java.lang.String.valueOf(r4);	 Catch:{ all -> 0x005c }
        r5 = r4.length();	 Catch:{ all -> 0x005c }
        if (r5 == 0) goto L_0x0050;
    L_0x004b:
        r3 = r3.concat(r4);	 Catch:{ all -> 0x005c }
        goto L_0x0056;
    L_0x0050:
        r4 = new java.lang.String;	 Catch:{ all -> 0x005c }
        r4.<init>(r3);	 Catch:{ all -> 0x005c }
        r3 = r4;
    L_0x0056:
        r2.zzao(r3);	 Catch:{ all -> 0x005c }
        r2 = 0;
        monitor-exit(r1);	 Catch:{ all -> 0x005c }
        return r2;
    L_0x005c:
        r2 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x005c }
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfc.zza(java.util.concurrent.atomic.AtomicReference, long, java.lang.String, java.lang.Runnable):T");
    }

    private final void zza(zzfh<?> zzfh) {
        synchronized (this.zzng) {
            this.zznc.add(zzfh);
            if (this.zzna == null) {
                this.zzna = new zzfg(this, "Measurement Worker", this.zznc);
                this.zzna.setUncaughtExceptionHandler(this.zzne);
                this.zzna.start();
            } else {
                this.zzna.zzhr();
            }
        }
    }

    public final void zzb(Runnable runnable) throws IllegalStateException {
        zzbi();
        Preconditions.checkNotNull(runnable);
        zzfh zzfh = new zzfh(this, runnable, false, "Task exception on network thread");
        synchronized (this.zzng) {
            this.zznd.add(zzfh);
            if (this.zznb == null) {
                this.zznb = new zzfg(this, "Measurement Network", this.zznd);
                this.zznb.setUncaughtExceptionHandler(this.zznf);
                this.zznb.start();
            } else {
                this.zznb.zzhr();
            }
        }
    }
}
