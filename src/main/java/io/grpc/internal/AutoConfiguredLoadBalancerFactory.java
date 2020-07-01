package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.ConnectivityState;
import io.grpc.ConnectivityStateInfo;
import io.grpc.EquivalentAddressGroup;
import io.grpc.InternalChannelz.ChannelTrace.Event.Builder;
import io.grpc.InternalChannelz.ChannelTrace.Event.Severity;
import io.grpc.LoadBalancer;
import io.grpc.LoadBalancer.Factory;
import io.grpc.LoadBalancer.Helper;
import io.grpc.LoadBalancer.PickResult;
import io.grpc.LoadBalancer.PickSubchannelArgs;
import io.grpc.LoadBalancer.Subchannel;
import io.grpc.LoadBalancer.SubchannelPicker;
import io.grpc.PickFirstBalancerFactory;
import io.grpc.Status;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

final class AutoConfiguredLoadBalancerFactory extends Factory {
    @VisibleForTesting
    static final String GRPCLB_LOAD_BALANCER_FACTORY_NAME = "io.grpc.grpclb.GrpclbLoadBalancerFactory";
    @VisibleForTesting
    static final String ROUND_ROBIN_LOAD_BALANCER_FACTORY_NAME = "io.grpc.util.RoundRobinLoadBalancerFactory";
    @Nullable
    private final ChannelTracer channelTracer;
    @Nullable
    private final TimeProvider timeProvider;

    @VisibleForTesting
    static final class AutoConfiguredLoadBalancer extends LoadBalancer {
        @CheckForNull
        private ChannelTracer channelTracer;
        private LoadBalancer delegate;
        private Factory delegateFactory = PickFirstBalancerFactory.getInstance();
        private final Helper helper;
        @Nullable
        private final TimeProvider timeProvider;

        AutoConfiguredLoadBalancer(Helper helper, @Nullable ChannelTracer channelTracer, @Nullable TimeProvider timeProvider) {
            this.helper = helper;
            this.delegate = this.delegateFactory.newLoadBalancer(helper);
            this.channelTracer = channelTracer;
            this.timeProvider = timeProvider;
            if (channelTracer != null) {
                Preconditions.checkNotNull(timeProvider, "timeProvider");
            }
        }

        public void handleResolvedAddressGroups(List<EquivalentAddressGroup> list, Attributes attributes) {
            try {
                Factory decideLoadBalancerFactory = decideLoadBalancerFactory(list, (Map) attributes.get(GrpcAttributes.NAME_RESOLVER_SERVICE_CONFIG));
                if (!(decideLoadBalancerFactory == null || decideLoadBalancerFactory == this.delegateFactory)) {
                    this.helper.updateBalancingState(ConnectivityState.CONNECTING, new EmptyPicker());
                    this.delegate.shutdown();
                    this.delegateFactory = decideLoadBalancerFactory;
                    LoadBalancer loadBalancer = this.delegate;
                    this.delegate = this.delegateFactory.newLoadBalancer(this.helper);
                    ChannelTracer channelTracer = this.channelTracer;
                    if (channelTracer != null) {
                        Builder builder = new Builder();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Load balancer changed from ");
                        stringBuilder.append(loadBalancer);
                        stringBuilder.append(" to ");
                        stringBuilder.append(this.delegate);
                        channelTracer.reportEvent(builder.setDescription(stringBuilder.toString()).setSeverity(Severity.CT_INFO).setTimestampNanos(this.timeProvider.currentTimeNanos()).build());
                    }
                }
                getDelegate().handleResolvedAddressGroups(list, attributes);
            } catch (Throwable e) {
                this.helper.updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, new FailingPicker(Status.INTERNAL.withDescription("Failed to pick a load balancer from service config").withCause(e)));
                this.delegate.shutdown();
                this.delegateFactory = null;
                this.delegate = new NoopLoadBalancer();
            }
        }

        public void handleNameResolutionError(Status status) {
            getDelegate().handleNameResolutionError(status);
        }

        public void handleSubchannelState(Subchannel subchannel, ConnectivityStateInfo connectivityStateInfo) {
            getDelegate().handleSubchannelState(subchannel, connectivityStateInfo);
        }

        public void shutdown() {
            this.delegate.shutdown();
            this.delegate = null;
        }

        @VisibleForTesting
        LoadBalancer getDelegate() {
            return this.delegate;
        }

        @VisibleForTesting
        void setDelegate(LoadBalancer loadBalancer) {
            this.delegate = loadBalancer;
        }

        @VisibleForTesting
        Factory getDelegateFactory() {
            return this.delegateFactory;
        }

        @Nullable
        @VisibleForTesting
        static Factory decideLoadBalancerFactory(List<EquivalentAddressGroup> list, @Nullable Map<String, Object> map) {
            Object obj;
            for (EquivalentAddressGroup attributes : list) {
                if (attributes.getAttributes().get(GrpcAttributes.ATTR_LB_ADDR_AUTHORITY) != null) {
                    obj = 1;
                    break;
                }
            }
            obj = null;
            String str = "getInstance";
            if (obj != null) {
                try {
                    return (Factory) Class.forName(AutoConfiguredLoadBalancerFactory.GRPCLB_LOAD_BALANCER_FACTORY_NAME).getMethod(str, new Class[0]).invoke(null, new Object[0]);
                } catch (RuntimeException e) {
                    throw e;
                } catch (Throwable e2) {
                    throw new RuntimeException("Can't get GRPCLB, but balancer addresses were present", e2);
                }
            }
            String loadBalancingPolicyFromServiceConfig = map != null ? ServiceConfigUtil.getLoadBalancingPolicyFromServiceConfig(map) : null;
            if (loadBalancingPolicyFromServiceConfig == null) {
                return PickFirstBalancerFactory.getInstance();
            }
            if (loadBalancingPolicyFromServiceConfig.toUpperCase(Locale.ROOT).equals("ROUND_ROBIN")) {
                try {
                    return (Factory) Class.forName(AutoConfiguredLoadBalancerFactory.ROUND_ROBIN_LOAD_BALANCER_FACTORY_NAME).getMethod(str, new Class[0]).invoke(null, new Object[0]);
                } catch (RuntimeException e3) {
                    throw e3;
                } catch (Throwable e22) {
                    throw new RuntimeException("Can't get Round Robin LB", e22);
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown service config policy: ");
            stringBuilder.append(loadBalancingPolicyFromServiceConfig);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    private static final class EmptyPicker extends SubchannelPicker {
        private EmptyPicker() {
        }

        public PickResult pickSubchannel(PickSubchannelArgs pickSubchannelArgs) {
            return PickResult.withNoResult();
        }
    }

    private static final class FailingPicker extends SubchannelPicker {
        private final Status failure;

        FailingPicker(Status status) {
            this.failure = status;
        }

        public PickResult pickSubchannel(PickSubchannelArgs pickSubchannelArgs) {
            return PickResult.withError(this.failure);
        }
    }

    private static final class NoopLoadBalancer extends LoadBalancer {
        public void handleNameResolutionError(Status status) {
        }

        public void handleResolvedAddressGroups(List<EquivalentAddressGroup> list, Attributes attributes) {
        }

        public void handleSubchannelState(Subchannel subchannel, ConnectivityStateInfo connectivityStateInfo) {
        }

        public void shutdown() {
        }

        private NoopLoadBalancer() {
        }
    }

    AutoConfiguredLoadBalancerFactory(@Nullable ChannelTracer channelTracer, @Nullable TimeProvider timeProvider) {
        this.channelTracer = channelTracer;
        this.timeProvider = timeProvider;
    }

    public LoadBalancer newLoadBalancer(Helper helper) {
        return new AutoConfiguredLoadBalancer(helper, this.channelTracer, this.timeProvider);
    }
}
