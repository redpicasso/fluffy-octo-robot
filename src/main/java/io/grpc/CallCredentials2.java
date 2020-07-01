package io.grpc;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.grpc.CallCredentials.RequestInfo;
import java.util.concurrent.Executor;

@ExperimentalApi("https://github.com/grpc/grpc-java/issues/4901")
public abstract class CallCredentials2 implements CallCredentials {

    @ExperimentalApi("https://github.com/grpc/grpc-java/issues/1914")
    public static abstract class MetadataApplier implements io.grpc.CallCredentials.MetadataApplier {
    }

    @ExperimentalApi("https://github.com/grpc/grpc-java/issues/1914")
    public abstract void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier);

    public final void applyRequestMetadata(MethodDescriptor<?, ?> methodDescriptor, Attributes attributes, Executor executor, final io.grpc.CallCredentials.MetadataApplier metadataApplier) {
        final String str = (String) Preconditions.checkNotNull(attributes.get(ATTR_AUTHORITY), "authority");
        final SecurityLevel securityLevel = (SecurityLevel) MoreObjects.firstNonNull(attributes.get(ATTR_SECURITY_LEVEL), SecurityLevel.NONE);
        final MethodDescriptor<?, ?> methodDescriptor2 = methodDescriptor;
        final Attributes attributes2 = attributes;
        applyRequestMetadata(new RequestInfo() {
            public MethodDescriptor<?, ?> getMethodDescriptor() {
                return methodDescriptor2;
            }

            public SecurityLevel getSecurityLevel() {
                return securityLevel;
            }

            public String getAuthority() {
                return str;
            }

            public Attributes getTransportAttrs() {
                return attributes2;
            }
        }, executor, new MetadataApplier() {
            public void apply(Metadata metadata) {
                metadataApplier.apply(metadata);
            }

            public void fail(Status status) {
                metadataApplier.fail(status);
            }
        });
    }
}
