package io.grpc;

import io.grpc.Attributes.Key;
import java.util.concurrent.Executor;

public interface CallCredentials {
    @ExperimentalApi("https://github.com/grpc/grpc-java/issues/1914")
    @Deprecated
    public static final Key<String> ATTR_AUTHORITY = Key.create("io.grpc.CallCredentials.authority");
    @ExperimentalApi("https://github.com/grpc/grpc-java/issues/1914")
    @Deprecated
    public static final Key<SecurityLevel> ATTR_SECURITY_LEVEL = Key.create("io.grpc.internal.GrpcAttributes.securityLevel");

    @ExperimentalApi("https://github.com/grpc/grpc-java/issues/1914")
    @Deprecated
    public interface MetadataApplier {
        void apply(Metadata metadata);

        void fail(Status status);
    }

    @ExperimentalApi("https://github.com/grpc/grpc-java/issues/1914")
    public static abstract class RequestInfo {
        public abstract String getAuthority();

        public abstract MethodDescriptor<?, ?> getMethodDescriptor();

        public abstract SecurityLevel getSecurityLevel();

        public abstract Attributes getTransportAttrs();
    }

    @ExperimentalApi("https://github.com/grpc/grpc-java/issues/1914")
    @Deprecated
    void applyRequestMetadata(MethodDescriptor<?, ?> methodDescriptor, Attributes attributes, Executor executor, MetadataApplier metadataApplier);

    @ExperimentalApi("https://github.com/grpc/grpc-java/issues/1914")
    void thisUsesUnstableApi();
}
