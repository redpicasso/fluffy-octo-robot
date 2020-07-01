package io.grpc.internal;

import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.NameResolverProvider;
import java.net.URI;

public final class DnsNameResolverProvider extends NameResolverProvider {
    private static final String SCHEME = "dns";

    public String getDefaultScheme() {
        return SCHEME;
    }

    protected boolean isAvailable() {
        return true;
    }

    protected int priority() {
        return 5;
    }

    public DnsNameResolver newNameResolver(URI uri, Attributes attributes) {
        if (!SCHEME.equals(uri.getScheme())) {
            return null;
        }
        Object obj = (String) Preconditions.checkNotNull(uri.getPath(), "targetPath");
        Preconditions.checkArgument(obj.startsWith("/"), "the path component (%s) of the target (%s) must start with '/'", obj, (Object) uri);
        return new DnsNameResolver(uri.getAuthority(), obj.substring(1), attributes, GrpcUtil.SHARED_CHANNEL_EXECUTOR, GrpcUtil.getDefaultProxyDetector());
    }
}
