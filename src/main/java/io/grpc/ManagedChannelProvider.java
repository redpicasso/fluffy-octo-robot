package io.grpc;

import com.google.common.annotations.VisibleForTesting;
import io.grpc.ServiceProviders.PriorityAccessor;

@Internal
public abstract class ManagedChannelProvider {
    @VisibleForTesting
    static final Iterable<Class<?>> HARDCODED_CLASSES = new HardcodedClasses();
    private static final ManagedChannelProvider provider = ((ManagedChannelProvider) ServiceProviders.load(ManagedChannelProvider.class, HARDCODED_CLASSES, ManagedChannelProvider.class.getClassLoader(), new PriorityAccessor<ManagedChannelProvider>() {
        public boolean isAvailable(ManagedChannelProvider managedChannelProvider) {
            return managedChannelProvider.isAvailable();
        }

        public int getPriority(ManagedChannelProvider managedChannelProvider) {
            return managedChannelProvider.priority();
        }
    }));

    private static final class HardcodedClasses implements Iterable<Class<?>> {
        private HardcodedClasses() {
        }

        /* synthetic */ HardcodedClasses(AnonymousClass1 anonymousClass1) {
            this();
        }

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:0x001b, code:
            return r0.iterator();
     */
        public java.util.Iterator<java.lang.Class<?>> iterator() {
            /*
            r2 = this;
            r0 = new java.util.ArrayList;
            r0.<init>();
            r1 = "io.grpc.okhttp.OkHttpChannelProvider";
            r1 = java.lang.Class.forName(r1);	 Catch:{ ClassNotFoundException -> 0x000e }
            r0.add(r1);	 Catch:{ ClassNotFoundException -> 0x000e }
        L_0x000e:
            r1 = "io.grpc.netty.NettyChannelProvider";
            r1 = java.lang.Class.forName(r1);	 Catch:{ ClassNotFoundException -> 0x0017 }
            r0.add(r1);	 Catch:{ ClassNotFoundException -> 0x0017 }
        L_0x0017:
            r0 = r0.iterator();
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.ManagedChannelProvider.HardcodedClasses.iterator():java.util.Iterator<java.lang.Class<?>>");
        }
    }

    public static final class ProviderNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1;

        public ProviderNotFoundException(String str) {
            super(str);
        }
    }

    protected abstract ManagedChannelBuilder<?> builderForAddress(String str, int i);

    protected abstract ManagedChannelBuilder<?> builderForTarget(String str);

    protected abstract boolean isAvailable();

    protected abstract int priority();

    public static ManagedChannelProvider provider() {
        ManagedChannelProvider managedChannelProvider = provider;
        if (managedChannelProvider != null) {
            return managedChannelProvider;
        }
        throw new ProviderNotFoundException("No functional channel service provider found. Try adding a dependency on the grpc-okhttp, grpc-netty, or grpc-netty-shaded artifact");
    }
}
