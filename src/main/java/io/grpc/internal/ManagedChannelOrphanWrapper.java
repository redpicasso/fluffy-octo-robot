package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import io.grpc.ManagedChannel;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

final class ManagedChannelOrphanWrapper extends ForwardingManagedChannel {
    private static final Logger logger = Logger.getLogger(ManagedChannelOrphanWrapper.class.getName());
    private static final ReferenceQueue<ManagedChannelOrphanWrapper> refqueue = new ReferenceQueue();
    private static final ConcurrentMap<ManagedChannelReference, ManagedChannelReference> refs = new ConcurrentHashMap();
    private final ManagedChannelReference phantom;

    @VisibleForTesting
    static final class ManagedChannelReference extends WeakReference<ManagedChannelOrphanWrapper> {
        private static final String ALLOCATION_SITE_PROPERTY_NAME = "io.grpc.ManagedChannel.enableAllocationTracking";
        private static final boolean ENABLE_ALLOCATION_TRACKING = Boolean.parseBoolean(System.getProperty(ALLOCATION_SITE_PROPERTY_NAME, "true"));
        private static final RuntimeException missingCallSite = missingCallSite();
        private final Reference<RuntimeException> allocationSite;
        private final ManagedChannel channel;
        private final ReferenceQueue<ManagedChannelOrphanWrapper> refqueue;
        private final ConcurrentMap<ManagedChannelReference, ManagedChannelReference> refs;
        private volatile boolean shutdown;
        private volatile boolean shutdownNow;

        ManagedChannelReference(ManagedChannelOrphanWrapper managedChannelOrphanWrapper, ManagedChannel managedChannel, ReferenceQueue<ManagedChannelOrphanWrapper> referenceQueue, ConcurrentMap<ManagedChannelReference, ManagedChannelReference> concurrentMap) {
            Object runtimeException;
            super(managedChannelOrphanWrapper, referenceQueue);
            if (ENABLE_ALLOCATION_TRACKING) {
                runtimeException = new RuntimeException("ManagedChannel allocation site");
            } else {
                runtimeException = missingCallSite;
            }
            this.allocationSite = new SoftReference(runtimeException);
            this.channel = managedChannel;
            this.refqueue = referenceQueue;
            this.refs = concurrentMap;
            this.refs.put(this, this);
            cleanQueue(referenceQueue);
        }

        public void clear() {
            clearInternal();
            cleanQueue(this.refqueue);
        }

        private void clearInternal() {
            super.clear();
            this.refs.remove(this);
            this.allocationSite.clear();
        }

        private static RuntimeException missingCallSite() {
            RuntimeException runtimeException = new RuntimeException("ManagedChannel allocation site not recorded.  Set -Dio.grpc.ManagedChannel.enableAllocationTracking=true to enable it");
            runtimeException.setStackTrace(new StackTraceElement[0]);
            return runtimeException;
        }

        @VisibleForTesting
        static int cleanQueue(ReferenceQueue<ManagedChannelOrphanWrapper> referenceQueue) {
            int i = 0;
            while (true) {
                ManagedChannelReference managedChannelReference = (ManagedChannelReference) referenceQueue.poll();
                if (managedChannelReference == null) {
                    return i;
                }
                RuntimeException runtimeException = (RuntimeException) managedChannelReference.allocationSite.get();
                managedChannelReference.clearInternal();
                if (!managedChannelReference.shutdown || !managedChannelReference.channel.isTerminated()) {
                    i++;
                    Level level = managedChannelReference.shutdownNow ? Level.FINE : Level.SEVERE;
                    if (ManagedChannelOrphanWrapper.logger.isLoggable(level)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("*~*~*~ Channel {0} was not ");
                        stringBuilder.append(!managedChannelReference.shutdown ? "shutdown" : "terminated");
                        stringBuilder.append(" properly!!! ~*~*~*");
                        stringBuilder.append(System.getProperty("line.separator"));
                        stringBuilder.append("    Make sure to call shutdown()/shutdownNow() and wait until awaitTermination() returns true.");
                        LogRecord logRecord = new LogRecord(level, stringBuilder.toString());
                        logRecord.setLoggerName(ManagedChannelOrphanWrapper.logger.getName());
                        logRecord.setParameters(new Object[]{managedChannelReference.channel.toString()});
                        logRecord.setThrown(runtimeException);
                        ManagedChannelOrphanWrapper.logger.log(logRecord);
                    }
                }
            }
        }
    }

    ManagedChannelOrphanWrapper(ManagedChannel managedChannel) {
        this(managedChannel, refqueue, refs);
    }

    @VisibleForTesting
    ManagedChannelOrphanWrapper(ManagedChannel managedChannel, ReferenceQueue<ManagedChannelOrphanWrapper> referenceQueue, ConcurrentMap<ManagedChannelReference, ManagedChannelReference> concurrentMap) {
        super(managedChannel);
        this.phantom = new ManagedChannelReference(this, managedChannel, referenceQueue, concurrentMap);
    }

    public ManagedChannel shutdown() {
        this.phantom.shutdown = true;
        return super.shutdown();
    }

    public ManagedChannel shutdownNow() {
        this.phantom.shutdownNow = true;
        return super.shutdownNow();
    }

    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        boolean awaitTermination = super.awaitTermination(j, timeUnit);
        if (awaitTermination) {
            this.phantom.clear();
        }
        return awaitTermination;
    }
}
