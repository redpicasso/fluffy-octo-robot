package io.grpc.internal;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs.CastExtraArgs;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.Compressor;
import io.grpc.Deadline;
import io.grpc.DecompressorRegistry;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.internal.ClientStreamListener.RpcProgress;
import io.grpc.internal.StreamListener.MessageProducer;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.concurrent.GuardedBy;

class DelayedStream implements ClientStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    @GuardedBy("this")
    private DelayedStreamListener delayedListener;
    @GuardedBy("this")
    private Status error;
    private ClientStreamListener listener;
    private volatile boolean passThrough;
    @GuardedBy("this")
    private List<Runnable> pendingCalls = new ArrayList();
    private ClientStream realStream;

    private static class DelayedStreamListener implements ClientStreamListener {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private volatile boolean passThrough;
        @GuardedBy("this")
        private List<Runnable> pendingCallbacks = new ArrayList();
        private final ClientStreamListener realListener;

        static {
            Class cls = DelayedStream.class;
        }

        public DelayedStreamListener(ClientStreamListener clientStreamListener) {
            this.realListener = clientStreamListener;
        }

        private void delayOrExecute(Runnable runnable) {
            synchronized (this) {
                if (this.passThrough) {
                    runnable.run();
                    return;
                }
                this.pendingCallbacks.add(runnable);
            }
        }

        public void messagesAvailable(final MessageProducer messageProducer) {
            if (this.passThrough) {
                this.realListener.messagesAvailable(messageProducer);
            } else {
                delayOrExecute(new Runnable() {
                    public void run() {
                        DelayedStreamListener.this.realListener.messagesAvailable(messageProducer);
                    }
                });
            }
        }

        public void onReady() {
            if (this.passThrough) {
                this.realListener.onReady();
            } else {
                delayOrExecute(new Runnable() {
                    public void run() {
                        DelayedStreamListener.this.realListener.onReady();
                    }
                });
            }
        }

        public void headersRead(final Metadata metadata) {
            delayOrExecute(new Runnable() {
                public void run() {
                    DelayedStreamListener.this.realListener.headersRead(metadata);
                }
            });
        }

        public void closed(final Status status, final Metadata metadata) {
            delayOrExecute(new Runnable() {
                public void run() {
                    DelayedStreamListener.this.realListener.closed(status, metadata);
                }
            });
        }

        public void closed(final Status status, final RpcProgress rpcProgress, final Metadata metadata) {
            delayOrExecute(new Runnable() {
                public void run() {
                    DelayedStreamListener.this.realListener.closed(status, rpcProgress, metadata);
                }
            });
        }

        /* JADX WARNING: Missing block: B:10:0x001b, code:
            r0 = r1.iterator();
     */
        /* JADX WARNING: Missing block: B:12:0x0023, code:
            if (r0.hasNext() == false) goto L_0x002f;
     */
        /* JADX WARNING: Missing block: B:13:0x0025, code:
            ((java.lang.Runnable) r0.next()).run();
     */
        public void drainPendingCallbacks() {
            /*
            r3 = this;
            r0 = new java.util.ArrayList;
            r0.<init>();
        L_0x0005:
            monitor-enter(r3);
            r1 = r3.pendingCallbacks;	 Catch:{ all -> 0x0034 }
            r1 = r1.isEmpty();	 Catch:{ all -> 0x0034 }
            if (r1 == 0) goto L_0x0016;
        L_0x000e:
            r0 = 0;
            r3.pendingCallbacks = r0;	 Catch:{ all -> 0x0034 }
            r0 = 1;
            r3.passThrough = r0;	 Catch:{ all -> 0x0034 }
            monitor-exit(r3);	 Catch:{ all -> 0x0034 }
            return;
        L_0x0016:
            r1 = r3.pendingCallbacks;	 Catch:{ all -> 0x0034 }
            r3.pendingCallbacks = r0;	 Catch:{ all -> 0x0034 }
            monitor-exit(r3);	 Catch:{ all -> 0x0034 }
            r0 = r1.iterator();
        L_0x001f:
            r2 = r0.hasNext();
            if (r2 == 0) goto L_0x002f;
        L_0x0025:
            r2 = r0.next();
            r2 = (java.lang.Runnable) r2;
            r2.run();
            goto L_0x001f;
        L_0x002f:
            r1.clear();
            r0 = r1;
            goto L_0x0005;
        L_0x0034:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x0034 }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.DelayedStream.DelayedStreamListener.drainPendingCallbacks():void");
        }
    }

    DelayedStream() {
    }

    public void setMaxInboundMessageSize(final int i) {
        if (this.passThrough) {
            this.realStream.setMaxInboundMessageSize(i);
        } else {
            delayOrExecute(new Runnable() {
                public void run() {
                    DelayedStream.this.realStream.setMaxInboundMessageSize(i);
                }
            });
        }
    }

    public void setMaxOutboundMessageSize(final int i) {
        if (this.passThrough) {
            this.realStream.setMaxOutboundMessageSize(i);
        } else {
            delayOrExecute(new Runnable() {
                public void run() {
                    DelayedStream.this.realStream.setMaxOutboundMessageSize(i);
                }
            });
        }
    }

    public void setDeadline(final Deadline deadline) {
        delayOrExecute(new Runnable() {
            public void run() {
                DelayedStream.this.realStream.setDeadline(deadline);
            }
        });
    }

    final void setStream(ClientStream clientStream) {
        synchronized (this) {
            if (this.realStream != null) {
                return;
            }
            this.realStream = (ClientStream) Preconditions.checkNotNull(clientStream, "stream");
            drainPendingCalls();
        }
    }

    /* JADX WARNING: Missing block: B:7:0x0017, code:
            if (r0 == null) goto L_?;
     */
    /* JADX WARNING: Missing block: B:8:0x0019, code:
            r0.drainPendingCallbacks();
     */
    /* JADX WARNING: Missing block: B:12:0x0022, code:
            r0 = r1.iterator();
     */
    /* JADX WARNING: Missing block: B:14:0x002a, code:
            if (r0.hasNext() == false) goto L_0x0036;
     */
    /* JADX WARNING: Missing block: B:15:0x002c, code:
            ((java.lang.Runnable) r0.next()).run();
     */
    /* JADX WARNING: Missing block: B:26:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:27:?, code:
            return;
     */
    private void drainPendingCalls() {
        /*
        r3 = this;
        r0 = new java.util.ArrayList;
        r0.<init>();
    L_0x0005:
        monitor-enter(r3);
        r1 = r3.pendingCalls;	 Catch:{ all -> 0x003b }
        r1 = r1.isEmpty();	 Catch:{ all -> 0x003b }
        if (r1 == 0) goto L_0x001d;
    L_0x000e:
        r0 = 0;
        r3.pendingCalls = r0;	 Catch:{ all -> 0x003b }
        r0 = 1;
        r3.passThrough = r0;	 Catch:{ all -> 0x003b }
        r0 = r3.delayedListener;	 Catch:{ all -> 0x003b }
        monitor-exit(r3);	 Catch:{ all -> 0x003b }
        if (r0 == 0) goto L_0x001c;
    L_0x0019:
        r0.drainPendingCallbacks();
    L_0x001c:
        return;
    L_0x001d:
        r1 = r3.pendingCalls;	 Catch:{ all -> 0x003b }
        r3.pendingCalls = r0;	 Catch:{ all -> 0x003b }
        monitor-exit(r3);	 Catch:{ all -> 0x003b }
        r0 = r1.iterator();
    L_0x0026:
        r2 = r0.hasNext();
        if (r2 == 0) goto L_0x0036;
    L_0x002c:
        r2 = r0.next();
        r2 = (java.lang.Runnable) r2;
        r2.run();
        goto L_0x0026;
    L_0x0036:
        r1.clear();
        r0 = r1;
        goto L_0x0005;
    L_0x003b:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x003b }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.DelayedStream.drainPendingCalls():void");
    }

    private void delayOrExecute(Runnable runnable) {
        synchronized (this) {
            if (this.passThrough) {
                runnable.run();
                return;
            }
            this.pendingCalls.add(runnable);
        }
    }

    public void setAuthority(final String str) {
        Preconditions.checkState(this.listener == null, "May only be called before start");
        Preconditions.checkNotNull(str, "authority");
        delayOrExecute(new Runnable() {
            public void run() {
                DelayedStream.this.realStream.setAuthority(str);
            }
        });
    }

    public void start(ClientStreamListener clientStreamListener) {
        Status status;
        boolean z;
        Preconditions.checkState(this.listener == null, "already started");
        synchronized (this) {
            this.listener = (ClientStreamListener) Preconditions.checkNotNull(clientStreamListener, CastExtraArgs.LISTENER);
            status = this.error;
            z = this.passThrough;
            if (!z) {
                DelayedStreamListener delayedStreamListener = new DelayedStreamListener(clientStreamListener);
                this.delayedListener = delayedStreamListener;
                clientStreamListener = delayedStreamListener;
            }
        }
        if (status != null) {
            clientStreamListener.closed(status, new Metadata());
            return;
        }
        if (z) {
            this.realStream.start(clientStreamListener);
        } else {
            delayOrExecute(new Runnable() {
                public void run() {
                    DelayedStream.this.realStream.start(clientStreamListener);
                }
            });
        }
    }

    public Attributes getAttributes() {
        Preconditions.checkState(this.passThrough, "Called getAttributes before attributes are ready");
        return this.realStream.getAttributes();
    }

    public void writeMessage(final InputStream inputStream) {
        Preconditions.checkNotNull(inputStream, "message");
        if (this.passThrough) {
            this.realStream.writeMessage(inputStream);
        } else {
            delayOrExecute(new Runnable() {
                public void run() {
                    DelayedStream.this.realStream.writeMessage(inputStream);
                }
            });
        }
    }

    public void flush() {
        if (this.passThrough) {
            this.realStream.flush();
        } else {
            delayOrExecute(new Runnable() {
                public void run() {
                    DelayedStream.this.realStream.flush();
                }
            });
        }
    }

    public void cancel(final Status status) {
        Object obj;
        ClientStreamListener clientStreamListener;
        Preconditions.checkNotNull(status, "reason");
        synchronized (this) {
            if (this.realStream == null) {
                this.realStream = NoopClientStream.INSTANCE;
                obj = null;
                clientStreamListener = this.listener;
                this.error = status;
            } else {
                obj = 1;
                clientStreamListener = null;
            }
        }
        if (obj != null) {
            delayOrExecute(new Runnable() {
                public void run() {
                    DelayedStream.this.realStream.cancel(status);
                }
            });
            return;
        }
        if (clientStreamListener != null) {
            clientStreamListener.closed(status, new Metadata());
        }
        drainPendingCalls();
    }

    public void halfClose() {
        delayOrExecute(new Runnable() {
            public void run() {
                DelayedStream.this.realStream.halfClose();
            }
        });
    }

    public void request(final int i) {
        if (this.passThrough) {
            this.realStream.request(i);
        } else {
            delayOrExecute(new Runnable() {
                public void run() {
                    DelayedStream.this.realStream.request(i);
                }
            });
        }
    }

    public void setCompressor(final Compressor compressor) {
        Preconditions.checkNotNull(compressor, "compressor");
        delayOrExecute(new Runnable() {
            public void run() {
                DelayedStream.this.realStream.setCompressor(compressor);
            }
        });
    }

    public void setFullStreamDecompression(final boolean z) {
        delayOrExecute(new Runnable() {
            public void run() {
                DelayedStream.this.realStream.setFullStreamDecompression(z);
            }
        });
    }

    public void setDecompressorRegistry(final DecompressorRegistry decompressorRegistry) {
        Preconditions.checkNotNull(decompressorRegistry, "decompressorRegistry");
        delayOrExecute(new Runnable() {
            public void run() {
                DelayedStream.this.realStream.setDecompressorRegistry(decompressorRegistry);
            }
        });
    }

    public boolean isReady() {
        return this.passThrough ? this.realStream.isReady() : false;
    }

    public void setMessageCompression(final boolean z) {
        if (this.passThrough) {
            this.realStream.setMessageCompression(z);
        } else {
            delayOrExecute(new Runnable() {
                public void run() {
                    DelayedStream.this.realStream.setMessageCompression(z);
                }
            });
        }
    }

    @VisibleForTesting
    ClientStream getRealStream() {
        return this.realStream;
    }
}
