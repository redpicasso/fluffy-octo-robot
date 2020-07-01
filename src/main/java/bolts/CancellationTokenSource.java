package bolts;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CancellationTokenSource implements Closeable {
    private boolean cancellationRequested;
    private boolean closed;
    private final ScheduledExecutorService executor = BoltsExecutors.scheduled();
    private final Object lock = new Object();
    private final List<CancellationTokenRegistration> registrations = new ArrayList();
    private ScheduledFuture<?> scheduledCancellation;

    public boolean isCancellationRequested() {
        boolean z;
        synchronized (this.lock) {
            throwIfClosed();
            z = this.cancellationRequested;
        }
        return z;
    }

    public CancellationToken getToken() {
        CancellationToken cancellationToken;
        synchronized (this.lock) {
            throwIfClosed();
            cancellationToken = new CancellationToken(this);
        }
        return cancellationToken;
    }

    public void cancel() {
        synchronized (this.lock) {
            throwIfClosed();
            if (this.cancellationRequested) {
                return;
            }
            cancelScheduledCancellation();
            this.cancellationRequested = true;
            List arrayList = new ArrayList(this.registrations);
            notifyListeners(arrayList);
        }
    }

    public void cancelAfter(long j) {
        cancelAfter(j, TimeUnit.MILLISECONDS);
    }

    /* JADX WARNING: Missing block: B:17:0x002c, code:
            return;
     */
    private void cancelAfter(long r5, java.util.concurrent.TimeUnit r7) {
        /*
        r4 = this;
        r0 = -1;
        r2 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1));
        if (r2 < 0) goto L_0x0030;
    L_0x0006:
        r0 = 0;
        r3 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1));
        if (r3 != 0) goto L_0x0010;
    L_0x000c:
        r4.cancel();
        return;
    L_0x0010:
        r0 = r4.lock;
        monitor-enter(r0);
        r1 = r4.cancellationRequested;	 Catch:{ all -> 0x002d }
        if (r1 == 0) goto L_0x0019;
    L_0x0017:
        monitor-exit(r0);	 Catch:{ all -> 0x002d }
        return;
    L_0x0019:
        r4.cancelScheduledCancellation();	 Catch:{ all -> 0x002d }
        if (r2 == 0) goto L_0x002b;
    L_0x001e:
        r1 = r4.executor;	 Catch:{ all -> 0x002d }
        r2 = new bolts.CancellationTokenSource$1;	 Catch:{ all -> 0x002d }
        r2.<init>();	 Catch:{ all -> 0x002d }
        r5 = r1.schedule(r2, r5, r7);	 Catch:{ all -> 0x002d }
        r4.scheduledCancellation = r5;	 Catch:{ all -> 0x002d }
    L_0x002b:
        monitor-exit(r0);	 Catch:{ all -> 0x002d }
        return;
    L_0x002d:
        r5 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x002d }
        throw r5;
    L_0x0030:
        r5 = new java.lang.IllegalArgumentException;
        r6 = "Delay must be >= -1";
        r5.<init>(r6);
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: bolts.CancellationTokenSource.cancelAfter(long, java.util.concurrent.TimeUnit):void");
    }

    public void close() {
        synchronized (this.lock) {
            if (this.closed) {
                return;
            }
            cancelScheduledCancellation();
            for (CancellationTokenRegistration close : this.registrations) {
                close.close();
            }
            this.registrations.clear();
            this.closed = true;
        }
    }

    CancellationTokenRegistration register(Runnable runnable) {
        CancellationTokenRegistration cancellationTokenRegistration;
        synchronized (this.lock) {
            throwIfClosed();
            cancellationTokenRegistration = new CancellationTokenRegistration(this, runnable);
            if (this.cancellationRequested) {
                cancellationTokenRegistration.runAction();
            } else {
                this.registrations.add(cancellationTokenRegistration);
            }
        }
        return cancellationTokenRegistration;
    }

    void throwIfCancellationRequested() throws CancellationException {
        synchronized (this.lock) {
            throwIfClosed();
            if (this.cancellationRequested) {
                throw new CancellationException();
            }
        }
    }

    void unregister(CancellationTokenRegistration cancellationTokenRegistration) {
        synchronized (this.lock) {
            throwIfClosed();
            this.registrations.remove(cancellationTokenRegistration);
        }
    }

    private void notifyListeners(List<CancellationTokenRegistration> list) {
        for (CancellationTokenRegistration runAction : list) {
            runAction.runAction();
        }
    }

    public String toString() {
        return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", new Object[]{getClass().getName(), Integer.toHexString(hashCode()), Boolean.toString(isCancellationRequested())});
    }

    private void throwIfClosed() {
        if (this.closed) {
            throw new IllegalStateException("Object already closed");
        }
    }

    private void cancelScheduledCancellation() {
        ScheduledFuture scheduledFuture = this.scheduledCancellation;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.scheduledCancellation = null;
        }
    }
}
