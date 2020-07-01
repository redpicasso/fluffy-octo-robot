package io.grpc;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ServerServiceDefinition {
    private final Map<String, ServerMethodDefinition<?, ?>> methods;
    private final ServiceDescriptor serviceDescriptor;

    public static final class Builder {
        private final Map<String, ServerMethodDefinition<?, ?>> methods;
        private final ServiceDescriptor serviceDescriptor;
        private final String serviceName;

        private Builder(String str) {
            this.methods = new HashMap();
            this.serviceName = (String) Preconditions.checkNotNull(str, "serviceName");
            this.serviceDescriptor = null;
        }

        private Builder(ServiceDescriptor serviceDescriptor) {
            this.methods = new HashMap();
            this.serviceDescriptor = (ServiceDescriptor) Preconditions.checkNotNull(serviceDescriptor, "serviceDescriptor");
            this.serviceName = serviceDescriptor.getName();
        }

        public <ReqT, RespT> Builder addMethod(MethodDescriptor<ReqT, RespT> methodDescriptor, ServerCallHandler<ReqT, RespT> serverCallHandler) {
            return addMethod(ServerMethodDefinition.create((MethodDescriptor) Preconditions.checkNotNull(methodDescriptor, "method must not be null"), (ServerCallHandler) Preconditions.checkNotNull(serverCallHandler, "handler must not be null")));
        }

        public <ReqT, RespT> Builder addMethod(ServerMethodDefinition<ReqT, RespT> serverMethodDefinition) {
            MethodDescriptor methodDescriptor = serverMethodDefinition.getMethodDescriptor();
            Preconditions.checkArgument(this.serviceName.equals(MethodDescriptor.extractFullServiceName(methodDescriptor.getFullMethodName())), "Method name should be prefixed with service name and separated with '/'. Expected service name: '%s'. Actual fully qualifed method name: '%s'.", this.serviceName, methodDescriptor.getFullMethodName());
            Object fullMethodName = methodDescriptor.getFullMethodName();
            Preconditions.checkState(this.methods.containsKey(fullMethodName) ^ 1, "Method by same name already registered: %s", fullMethodName);
            this.methods.put(fullMethodName, serverMethodDefinition);
            return this;
        }

        public ServerServiceDefinition build() {
            ServiceDescriptor serviceDescriptor = this.serviceDescriptor;
            if (serviceDescriptor == null) {
                Collection arrayList = new ArrayList(this.methods.size());
                for (ServerMethodDefinition methodDescriptor : this.methods.values()) {
                    arrayList.add(methodDescriptor.getMethodDescriptor());
                }
                serviceDescriptor = new ServiceDescriptor(this.serviceName, arrayList);
            }
            Map hashMap = new HashMap(this.methods);
            for (MethodDescriptor methodDescriptor2 : serviceDescriptor.getMethods()) {
                ServerMethodDefinition serverMethodDefinition = (ServerMethodDefinition) hashMap.remove(methodDescriptor2.getFullMethodName());
                StringBuilder stringBuilder;
                if (serverMethodDefinition == null) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("No method bound for descriptor entry ");
                    stringBuilder.append(methodDescriptor2.getFullMethodName());
                    throw new IllegalStateException(stringBuilder.toString());
                } else if (serverMethodDefinition.getMethodDescriptor() != methodDescriptor2) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Bound method for ");
                    stringBuilder.append(methodDescriptor2.getFullMethodName());
                    stringBuilder.append(" not same instance as method in service descriptor");
                    throw new IllegalStateException(stringBuilder.toString());
                }
            }
            if (hashMap.size() <= 0) {
                return new ServerServiceDefinition(serviceDescriptor, this.methods);
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("No entry in descriptor matching bound method ");
            stringBuilder2.append(((ServerMethodDefinition) hashMap.values().iterator().next()).getMethodDescriptor().getFullMethodName());
            throw new IllegalStateException(stringBuilder2.toString());
        }
    }

    public static Builder builder(String str) {
        return new Builder(str, null);
    }

    public static Builder builder(ServiceDescriptor serviceDescriptor) {
        return new Builder(serviceDescriptor, null);
    }

    private ServerServiceDefinition(ServiceDescriptor serviceDescriptor, Map<String, ServerMethodDefinition<?, ?>> map) {
        this.serviceDescriptor = (ServiceDescriptor) Preconditions.checkNotNull(serviceDescriptor, "serviceDescriptor");
        this.methods = Collections.unmodifiableMap(new HashMap(map));
    }

    public ServiceDescriptor getServiceDescriptor() {
        return this.serviceDescriptor;
    }

    public Collection<ServerMethodDefinition<?, ?>> getMethods() {
        return this.methods.values();
    }

    @Internal
    public ServerMethodDefinition<?, ?> getMethod(String str) {
        return (ServerMethodDefinition) this.methods.get(str);
    }
}
