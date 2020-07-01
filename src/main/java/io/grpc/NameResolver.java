package io.grpc;

import io.grpc.Attributes.Key;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URI;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ExperimentalApi("https://github.com/grpc/grpc-java/issues/1770")
@ThreadSafe
public abstract class NameResolver {

    public static abstract class Factory {
        public static final Key<Integer> PARAMS_DEFAULT_PORT = Key.create("params-default-port");

        public abstract String getDefaultScheme();

        @Nullable
        public abstract NameResolver newNameResolver(URI uri, Attributes attributes);
    }

    @ExperimentalApi("https://github.com/grpc/grpc-java/issues/1770")
    @ThreadSafe
    public interface Listener {
        void onAddresses(List<EquivalentAddressGroup> list, Attributes attributes);

        void onError(Status status);
    }

    @ExperimentalApi("https://github.com/grpc/grpc-java/issues/4972")
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ResolutionResultAttr {
    }

    public abstract String getServiceAuthority();

    public void refresh() {
    }

    public abstract void shutdown();

    public abstract void start(Listener listener);
}
