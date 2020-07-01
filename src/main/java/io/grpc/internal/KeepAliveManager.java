package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;
import io.grpc.Status;
import io.grpc.internal.ClientTransport.PingCallback;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class KeepAliveManager {
    private static final long MIN_KEEPALIVE_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(10);
    private static final long MIN_KEEPALIVE_TIME_NANOS = TimeUnit.SECONDS.toNanos(10);
    private static final SystemTicker SYSTEM_TICKER = new SystemTicker();
    private final boolean keepAliveDuringTransportIdle;
    private final KeepAlivePinger keepAlivePinger;
    private long keepAliveTimeInNanos;
    private long keepAliveTimeoutInNanos;
    private long nextKeepaliveTime;
    private ScheduledFuture<?> pingFuture;
    private final ScheduledExecutorService scheduler;
    private final Runnable sendPing;
    private final Runnable shutdown;
    private ScheduledFuture<?> shutdownFuture;
    private State state;
    private final Ticker ticker;

    public interface KeepAlivePinger {
        void onPingTimeout();

        void ping();
    }

    private enum State {
        IDLE,
        PING_SCHEDULED,
        PING_DELAYED,
        PING_SENT,
        IDLE_AND_PING_SENT,
        DISCONNECTED
    }

    static abstract class Ticker {
        public abstract long read();

        Ticker() {
        }
    }

    public static final class ClientKeepAlivePinger implements KeepAlivePinger {
        private final ConnectionClientTransport transport;

        public ClientKeepAlivePinger(ConnectionClientTransport connectionClientTransport) {
            this.transport = connectionClientTransport;
        }

        public void ping() {
            this.transport.ping(new PingCallback() {
                public void onSuccess(long j) {
                }

                public void onFailure(Throwable th) {
                    ClientKeepAlivePinger.this.transport.shutdownNow(Status.UNAVAILABLE.withDescription("Keepalive failed. The connection is likely gone"));
                }
            }, MoreExecutors.directExecutor());
        }

        public void onPingTimeout() {
            this.transport.shutdownNow(Status.UNAVAILABLE.withDescription("Keepalive failed. The connection is likely gone"));
        }
    }

    private static class SystemTicker extends Ticker {
        private SystemTicker() {
        }

        /* synthetic */ SystemTicker(AnonymousClass1 anonymousClass1) {
            this();
        }

        public long read() {
            return System.nanoTime();
        }
    }

    public KeepAliveManager(KeepAlivePinger keepAlivePinger, ScheduledExecutorService scheduledExecutorService, long j, long j2, boolean z) {
        this(keepAlivePinger, scheduledExecutorService, SYSTEM_TICKER, j, j2, z);
    }

    @VisibleForTesting
    KeepAliveManager(KeepAlivePinger keepAlivePinger, ScheduledExecutorService scheduledExecutorService, Ticker ticker, long j, long j2, boolean z) {
        this.state = State.IDLE;
        this.shutdown = new LogExceptionRunnable(new Runnable() {
            public void run() {
                Object obj;
                synchronized (KeepAliveManager.this) {
                    if (KeepAliveManager.this.state != State.DISCONNECTED) {
                        KeepAliveManager.this.state = State.DISCONNECTED;
                        obj = 1;
                    } else {
                        obj = null;
                    }
                }
                if (obj != null) {
                    KeepAliveManager.this.keepAlivePinger.onPingTimeout();
                }
            }
        });
        this.sendPing = new LogExceptionRunnable(new Runnable() {
            public void run() {
                Object obj;
                KeepAliveManager.this.pingFuture = null;
                synchronized (KeepAliveManager.this) {
                    if (KeepAliveManager.this.state == State.PING_SCHEDULED) {
                        obj = 1;
                        KeepAliveManager.this.state = State.PING_SENT;
                        KeepAliveManager.this.shutdownFuture = KeepAliveManager.this.scheduler.schedule(KeepAliveManager.this.shutdown, KeepAliveManager.this.keepAliveTimeoutInNanos, TimeUnit.NANOSECONDS);
                    } else {
                        if (KeepAliveManager.this.state == State.PING_DELAYED) {
                            KeepAliveManager.this.pingFuture = KeepAliveManager.this.scheduler.schedule(KeepAliveManager.this.sendPing, KeepAliveManager.this.nextKeepaliveTime - KeepAliveManager.this.ticker.read(), TimeUnit.NANOSECONDS);
                            KeepAliveManager.this.state = State.PING_SCHEDULED;
                        }
                        obj = null;
                    }
                }
                if (obj != null) {
                    KeepAliveManager.this.keepAlivePinger.ping();
                }
            }
        });
        this.keepAlivePinger = (KeepAlivePinger) Preconditions.checkNotNull(keepAlivePinger, "keepAlivePinger");
        this.scheduler = (ScheduledExecutorService) Preconditions.checkNotNull(scheduledExecutorService, "scheduler");
        this.ticker = (Ticker) Preconditions.checkNotNull(ticker, "ticker");
        this.keepAliveTimeInNanos = j;
        this.keepAliveTimeoutInNanos = j2;
        this.keepAliveDuringTransportIdle = z;
        this.nextKeepaliveTime = ticker.read() + j;
    }

    public synchronized void onTransportStarted() {
        if (this.keepAliveDuringTransportIdle) {
            onTransportActive();
        }
    }

    /* JADX WARNING: Missing block: B:23:0x0056, code:
            return;
     */
    public synchronized void onDataReceived() {
        /*
        r5 = this;
        monitor-enter(r5);
        r0 = r5.ticker;	 Catch:{ all -> 0x0057 }
        r0 = r0.read();	 Catch:{ all -> 0x0057 }
        r2 = r5.keepAliveTimeInNanos;	 Catch:{ all -> 0x0057 }
        r0 = r0 + r2;
        r5.nextKeepaliveTime = r0;	 Catch:{ all -> 0x0057 }
        r0 = r5.state;	 Catch:{ all -> 0x0057 }
        r1 = io.grpc.internal.KeepAliveManager.State.PING_SCHEDULED;	 Catch:{ all -> 0x0057 }
        if (r0 != r1) goto L_0x0017;
    L_0x0012:
        r0 = io.grpc.internal.KeepAliveManager.State.PING_DELAYED;	 Catch:{ all -> 0x0057 }
        r5.state = r0;	 Catch:{ all -> 0x0057 }
        goto L_0x0055;
    L_0x0017:
        r0 = r5.state;	 Catch:{ all -> 0x0057 }
        r1 = io.grpc.internal.KeepAliveManager.State.PING_SENT;	 Catch:{ all -> 0x0057 }
        if (r0 == r1) goto L_0x0023;
    L_0x001d:
        r0 = r5.state;	 Catch:{ all -> 0x0057 }
        r1 = io.grpc.internal.KeepAliveManager.State.IDLE_AND_PING_SENT;	 Catch:{ all -> 0x0057 }
        if (r0 != r1) goto L_0x0055;
    L_0x0023:
        r0 = r5.shutdownFuture;	 Catch:{ all -> 0x0057 }
        r1 = 0;
        if (r0 == 0) goto L_0x002d;
    L_0x0028:
        r0 = r5.shutdownFuture;	 Catch:{ all -> 0x0057 }
        r0.cancel(r1);	 Catch:{ all -> 0x0057 }
    L_0x002d:
        r0 = r5.state;	 Catch:{ all -> 0x0057 }
        r2 = io.grpc.internal.KeepAliveManager.State.IDLE_AND_PING_SENT;	 Catch:{ all -> 0x0057 }
        if (r0 != r2) goto L_0x0039;
    L_0x0033:
        r0 = io.grpc.internal.KeepAliveManager.State.IDLE;	 Catch:{ all -> 0x0057 }
        r5.state = r0;	 Catch:{ all -> 0x0057 }
        monitor-exit(r5);
        return;
    L_0x0039:
        r0 = io.grpc.internal.KeepAliveManager.State.PING_SCHEDULED;	 Catch:{ all -> 0x0057 }
        r5.state = r0;	 Catch:{ all -> 0x0057 }
        r0 = r5.pingFuture;	 Catch:{ all -> 0x0057 }
        if (r0 != 0) goto L_0x0042;
    L_0x0041:
        r1 = 1;
    L_0x0042:
        r0 = "There should be no outstanding pingFuture";
        com.google.common.base.Preconditions.checkState(r1, r0);	 Catch:{ all -> 0x0057 }
        r0 = r5.scheduler;	 Catch:{ all -> 0x0057 }
        r1 = r5.sendPing;	 Catch:{ all -> 0x0057 }
        r2 = r5.keepAliveTimeInNanos;	 Catch:{ all -> 0x0057 }
        r4 = java.util.concurrent.TimeUnit.NANOSECONDS;	 Catch:{ all -> 0x0057 }
        r0 = r0.schedule(r1, r2, r4);	 Catch:{ all -> 0x0057 }
        r5.pingFuture = r0;	 Catch:{ all -> 0x0057 }
    L_0x0055:
        monitor-exit(r5);
        return;
    L_0x0057:
        r0 = move-exception;
        monitor-exit(r5);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.KeepAliveManager.onDataReceived():void");
    }

    public synchronized void onTransportActive() {
        if (this.state == State.IDLE) {
            this.state = State.PING_SCHEDULED;
            if (this.pingFuture == null) {
                this.pingFuture = this.scheduler.schedule(this.sendPing, this.nextKeepaliveTime - this.ticker.read(), TimeUnit.NANOSECONDS);
            }
        } else if (this.state == State.IDLE_AND_PING_SENT) {
            this.state = State.PING_SENT;
        }
    }

    /* JADX WARNING: Missing block: B:16:0x0022, code:
            return;
     */
    public synchronized void onTransportIdle() {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = r2.keepAliveDuringTransportIdle;	 Catch:{ all -> 0x0023 }
        if (r0 == 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r2);
        return;
    L_0x0007:
        r0 = r2.state;	 Catch:{ all -> 0x0023 }
        r1 = io.grpc.internal.KeepAliveManager.State.PING_SCHEDULED;	 Catch:{ all -> 0x0023 }
        if (r0 == r1) goto L_0x0013;
    L_0x000d:
        r0 = r2.state;	 Catch:{ all -> 0x0023 }
        r1 = io.grpc.internal.KeepAliveManager.State.PING_DELAYED;	 Catch:{ all -> 0x0023 }
        if (r0 != r1) goto L_0x0017;
    L_0x0013:
        r0 = io.grpc.internal.KeepAliveManager.State.IDLE;	 Catch:{ all -> 0x0023 }
        r2.state = r0;	 Catch:{ all -> 0x0023 }
    L_0x0017:
        r0 = r2.state;	 Catch:{ all -> 0x0023 }
        r1 = io.grpc.internal.KeepAliveManager.State.PING_SENT;	 Catch:{ all -> 0x0023 }
        if (r0 != r1) goto L_0x0021;
    L_0x001d:
        r0 = io.grpc.internal.KeepAliveManager.State.IDLE_AND_PING_SENT;	 Catch:{ all -> 0x0023 }
        r2.state = r0;	 Catch:{ all -> 0x0023 }
    L_0x0021:
        monitor-exit(r2);
        return;
    L_0x0023:
        r0 = move-exception;
        monitor-exit(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.KeepAliveManager.onTransportIdle():void");
    }

    public synchronized void onTransportTermination() {
        if (this.state != State.DISCONNECTED) {
            this.state = State.DISCONNECTED;
            if (this.shutdownFuture != null) {
                this.shutdownFuture.cancel(false);
            }
            if (this.pingFuture != null) {
                this.pingFuture.cancel(false);
                this.pingFuture = null;
            }
        }
    }

    public static long clampKeepAliveTimeInNanos(long j) {
        return Math.max(j, MIN_KEEPALIVE_TIME_NANOS);
    }

    public static long clampKeepAliveTimeoutInNanos(long j) {
        return Math.max(j, MIN_KEEPALIVE_TIMEOUT_NANOS);
    }
}
