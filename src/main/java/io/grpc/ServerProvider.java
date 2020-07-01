package io.grpc;

import io.grpc.ManagedChannelProvider.ProviderNotFoundException;
import io.grpc.ServiceProviders.PriorityAccessor;
import java.util.Collections;

@Internal
public abstract class ServerProvider {
    private static final ServerProvider provider = ((ServerProvider) ServiceProviders.load(ServerProvider.class, Collections.emptyList(), ServerProvider.class.getClassLoader(), new PriorityAccessor<ServerProvider>() {
        public boolean isAvailable(ServerProvider serverProvider) {
            return serverProvider.isAvailable();
        }

        public int getPriority(ServerProvider serverProvider) {
            return serverProvider.priority();
        }
    }));

    protected abstract ServerBuilder<?> builderForPort(int i);

    protected abstract boolean isAvailable();

    protected abstract int priority();

    public static ServerProvider provider() {
        ServerProvider serverProvider = provider;
        if (serverProvider != null) {
            return serverProvider;
        }
        throw new ProviderNotFoundException("No functional server found. Try adding a dependency on the grpc-netty or grpc-netty-shaded artifact");
    }
}
