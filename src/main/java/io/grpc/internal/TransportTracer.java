package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.InternalChannelz.TransportStats;

public final class TransportTracer {
    private static final Factory DEFAULT_FACTORY = new Factory(TimeProvider.SYSTEM_TIME_PROVIDER);
    private FlowControlReader flowControlWindowReader;
    private long keepAlivesSent;
    private long lastLocalStreamCreatedTimeNanos;
    private volatile long lastMessageReceivedTimeNanos;
    private long lastMessageSentTimeNanos;
    private long lastRemoteStreamCreatedTimeNanos;
    private final LongCounter messagesReceived;
    private long messagesSent;
    private long streamsFailed;
    private long streamsStarted;
    private long streamsSucceeded;
    private final TimeProvider timeProvider;

    public static final class Factory {
        private TimeProvider timeProvider;

        @VisibleForTesting
        public Factory(TimeProvider timeProvider) {
            this.timeProvider = timeProvider;
        }

        public TransportTracer create() {
            return new TransportTracer(this.timeProvider);
        }
    }

    public interface FlowControlReader {
        FlowControlWindows read();
    }

    public static final class FlowControlWindows {
        public final long localBytes;
        public final long remoteBytes;

        public FlowControlWindows(long j, long j2) {
            this.localBytes = j;
            this.remoteBytes = j2;
        }
    }

    public TransportTracer() {
        this.messagesReceived = LongCounterFactory.create();
        this.timeProvider = TimeProvider.SYSTEM_TIME_PROVIDER;
    }

    private TransportTracer(TimeProvider timeProvider) {
        this.messagesReceived = LongCounterFactory.create();
        this.timeProvider = timeProvider;
    }

    public TransportStats getStats() {
        FlowControlReader flowControlReader = this.flowControlWindowReader;
        long j = -1;
        long j2 = flowControlReader == null ? -1 : flowControlReader.read().localBytes;
        flowControlReader = this.flowControlWindowReader;
        if (flowControlReader != null) {
            j = flowControlReader.read().remoteBytes;
        }
        return new TransportStats(this.streamsStarted, this.lastLocalStreamCreatedTimeNanos, this.lastRemoteStreamCreatedTimeNanos, this.streamsSucceeded, this.streamsFailed, this.messagesSent, this.messagesReceived.value(), this.keepAlivesSent, this.lastMessageSentTimeNanos, this.lastMessageReceivedTimeNanos, j2, j);
    }

    public void reportLocalStreamStarted() {
        this.streamsStarted++;
        this.lastLocalStreamCreatedTimeNanos = this.timeProvider.currentTimeNanos();
    }

    public void reportRemoteStreamStarted() {
        this.streamsStarted++;
        this.lastRemoteStreamCreatedTimeNanos = this.timeProvider.currentTimeNanos();
    }

    public void reportStreamClosed(boolean z) {
        if (z) {
            this.streamsSucceeded++;
        } else {
            this.streamsFailed++;
        }
    }

    public void reportMessageSent(int i) {
        if (i != 0) {
            this.messagesSent += (long) i;
            this.lastMessageSentTimeNanos = this.timeProvider.currentTimeNanos();
        }
    }

    public void reportMessageReceived() {
        this.messagesReceived.add(1);
        this.lastMessageReceivedTimeNanos = this.timeProvider.currentTimeNanos();
    }

    public void reportKeepAliveSent() {
        this.keepAlivesSent++;
    }

    public void setFlowControlWindowReader(FlowControlReader flowControlReader) {
        this.flowControlWindowReader = (FlowControlReader) Preconditions.checkNotNull(flowControlReader);
    }

    public static Factory getDefaultFactory() {
        return DEFAULT_FACTORY;
    }
}
