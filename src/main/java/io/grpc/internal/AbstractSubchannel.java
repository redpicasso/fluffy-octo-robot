package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import io.grpc.InternalChannelz.ChannelStats;
import io.grpc.InternalInstrumented;
import io.grpc.LoadBalancer.Subchannel;
import javax.annotation.Nullable;

abstract class AbstractSubchannel extends Subchannel {
    @VisibleForTesting
    abstract InternalInstrumented<ChannelStats> getInternalSubchannel();

    @Nullable
    abstract ClientTransport obtainActiveTransport();

    AbstractSubchannel() {
    }
}
