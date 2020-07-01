package io.grpc;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.LoadBalancer.Factory;
import io.grpc.LoadBalancer.Helper;
import io.grpc.LoadBalancer.PickResult;
import io.grpc.LoadBalancer.PickSubchannelArgs;
import io.grpc.LoadBalancer.Subchannel;
import io.grpc.LoadBalancer.SubchannelPicker;
import java.util.List;

@ExperimentalApi("https://github.com/grpc/grpc-java/issues/1771")
public final class PickFirstBalancerFactory extends Factory {
    private static final PickFirstBalancerFactory INSTANCE = new PickFirstBalancerFactory();

    /* renamed from: io.grpc.PickFirstBalancerFactory$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$grpc$ConnectivityState = new int[ConnectivityState.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:8:?, code:
            $SwitchMap$io$grpc$ConnectivityState[io.grpc.ConnectivityState.TRANSIENT_FAILURE.ordinal()] = 4;
     */
        static {
            /*
            r0 = io.grpc.ConnectivityState.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$io$grpc$ConnectivityState = r0;
            r0 = $SwitchMap$io$grpc$ConnectivityState;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = io.grpc.ConnectivityState.IDLE;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$io$grpc$ConnectivityState;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = io.grpc.ConnectivityState.CONNECTING;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$io$grpc$ConnectivityState;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = io.grpc.ConnectivityState.READY;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r0 = $SwitchMap$io$grpc$ConnectivityState;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = io.grpc.ConnectivityState.TRANSIENT_FAILURE;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.PickFirstBalancerFactory.1.<clinit>():void");
        }
    }

    @VisibleForTesting
    static final class PickFirstBalancer extends LoadBalancer {
        private final Helper helper;
        private Subchannel subchannel;

        PickFirstBalancer(Helper helper) {
            this.helper = (Helper) Preconditions.checkNotNull(helper, "helper");
        }

        public void handleResolvedAddressGroups(List<EquivalentAddressGroup> list, Attributes attributes) {
            Subchannel subchannel = this.subchannel;
            if (subchannel == null) {
                this.subchannel = this.helper.createSubchannel((List) list, Attributes.EMPTY);
                this.helper.updateBalancingState(ConnectivityState.CONNECTING, new Picker(PickResult.withSubchannel(this.subchannel)));
                this.subchannel.requestConnection();
                return;
            }
            this.helper.updateSubchannelAddresses(subchannel, (List) list);
        }

        public void handleNameResolutionError(Status status) {
            Subchannel subchannel = this.subchannel;
            if (subchannel != null) {
                subchannel.shutdown();
                this.subchannel = null;
            }
            this.helper.updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, new Picker(PickResult.withError(status)));
        }

        public void handleSubchannelState(Subchannel subchannel, ConnectivityStateInfo connectivityStateInfo) {
            ConnectivityState state = connectivityStateInfo.getState();
            if (subchannel == this.subchannel && state != ConnectivityState.SHUTDOWN) {
                SubchannelPicker picker;
                SubchannelPicker picker2;
                int i = AnonymousClass1.$SwitchMap$io$grpc$ConnectivityState[state.ordinal()];
                if (i != 1) {
                    if (i == 2) {
                        picker = new Picker(PickResult.withNoResult());
                    } else if (i == 3) {
                        picker2 = new Picker(PickResult.withSubchannel(subchannel));
                    } else if (i == 4) {
                        picker = new Picker(PickResult.withError(connectivityStateInfo.getStatus()));
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unsupported state:");
                        stringBuilder.append(state);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    this.helper.updateBalancingState(state, picker);
                }
                picker2 = new RequestConnectionPicker(subchannel);
                picker = picker2;
                this.helper.updateBalancingState(state, picker);
            }
        }

        public void shutdown() {
            Subchannel subchannel = this.subchannel;
            if (subchannel != null) {
                subchannel.shutdown();
            }
        }
    }

    private static final class Picker extends SubchannelPicker {
        private final PickResult result;

        Picker(PickResult pickResult) {
            this.result = (PickResult) Preconditions.checkNotNull(pickResult, "result");
        }

        public PickResult pickSubchannel(PickSubchannelArgs pickSubchannelArgs) {
            return this.result;
        }
    }

    private static final class RequestConnectionPicker extends SubchannelPicker {
        private final Subchannel subchannel;

        RequestConnectionPicker(Subchannel subchannel) {
            this.subchannel = (Subchannel) Preconditions.checkNotNull(subchannel, "subchannel");
        }

        public PickResult pickSubchannel(PickSubchannelArgs pickSubchannelArgs) {
            this.subchannel.requestConnection();
            return PickResult.withNoResult();
        }

        public void requestConnection() {
            this.subchannel.requestConnection();
        }
    }

    private PickFirstBalancerFactory() {
    }

    public static PickFirstBalancerFactory getInstance() {
        return INSTANCE;
    }

    public LoadBalancer newLoadBalancer(Helper helper) {
        return new PickFirstBalancer(helper);
    }
}
