package io.grpc;

import com.google.common.base.Preconditions;
import io.grpc.MethodDescriptor.Marshaller;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerServiceDefinition.Builder;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class ServerInterceptors {

    static final class InterceptCallHandler<ReqT, RespT> implements ServerCallHandler<ReqT, RespT> {
        private final ServerCallHandler<ReqT, RespT> callHandler;
        private final ServerInterceptor interceptor;

        public static <ReqT, RespT> InterceptCallHandler<ReqT, RespT> create(ServerInterceptor serverInterceptor, ServerCallHandler<ReqT, RespT> serverCallHandler) {
            return new InterceptCallHandler(serverInterceptor, serverCallHandler);
        }

        private InterceptCallHandler(ServerInterceptor serverInterceptor, ServerCallHandler<ReqT, RespT> serverCallHandler) {
            this.interceptor = (ServerInterceptor) Preconditions.checkNotNull(serverInterceptor, "interceptor");
            this.callHandler = serverCallHandler;
        }

        public Listener<ReqT> startCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata) {
            return this.interceptor.interceptCall(serverCall, metadata, this.callHandler);
        }
    }

    private ServerInterceptors() {
    }

    public static ServerServiceDefinition interceptForward(ServerServiceDefinition serverServiceDefinition, ServerInterceptor... serverInterceptorArr) {
        return interceptForward(serverServiceDefinition, Arrays.asList(serverInterceptorArr));
    }

    public static ServerServiceDefinition interceptForward(BindableService bindableService, ServerInterceptor... serverInterceptorArr) {
        return interceptForward(bindableService.bindService(), Arrays.asList(serverInterceptorArr));
    }

    public static ServerServiceDefinition interceptForward(ServerServiceDefinition serverServiceDefinition, List<? extends ServerInterceptor> list) {
        List arrayList = new ArrayList(list);
        Collections.reverse(arrayList);
        return intercept(serverServiceDefinition, arrayList);
    }

    public static ServerServiceDefinition interceptForward(BindableService bindableService, List<? extends ServerInterceptor> list) {
        return interceptForward(bindableService.bindService(), (List) list);
    }

    public static ServerServiceDefinition intercept(ServerServiceDefinition serverServiceDefinition, ServerInterceptor... serverInterceptorArr) {
        return intercept(serverServiceDefinition, Arrays.asList(serverInterceptorArr));
    }

    public static ServerServiceDefinition intercept(BindableService bindableService, ServerInterceptor... serverInterceptorArr) {
        Preconditions.checkNotNull(bindableService, "bindableService");
        return intercept(bindableService.bindService(), Arrays.asList(serverInterceptorArr));
    }

    public static ServerServiceDefinition intercept(ServerServiceDefinition serverServiceDefinition, List<? extends ServerInterceptor> list) {
        Preconditions.checkNotNull(serverServiceDefinition, "serviceDef");
        if (list.isEmpty()) {
            return serverServiceDefinition;
        }
        Builder builder = ServerServiceDefinition.builder(serverServiceDefinition.getServiceDescriptor());
        for (ServerMethodDefinition wrapAndAddMethod : serverServiceDefinition.getMethods()) {
            wrapAndAddMethod(builder, wrapAndAddMethod, list);
        }
        return builder.build();
    }

    public static ServerServiceDefinition intercept(BindableService bindableService, List<? extends ServerInterceptor> list) {
        Preconditions.checkNotNull(bindableService, "bindableService");
        return intercept(bindableService.bindService(), (List) list);
    }

    @ExperimentalApi("https://github.com/grpc/grpc-java/issues/1712")
    public static ServerServiceDefinition useInputStreamMessages(ServerServiceDefinition serverServiceDefinition) {
        return useMarshalledMessages(serverServiceDefinition, new Marshaller<InputStream>() {
            public InputStream stream(InputStream inputStream) {
                return inputStream;
            }

            public InputStream parse(InputStream inputStream) {
                if (inputStream.markSupported()) {
                    return inputStream;
                }
                return new BufferedInputStream(inputStream);
            }
        });
    }

    @ExperimentalApi("https://github.com/grpc/grpc-java/issues/1712")
    public static <T> ServerServiceDefinition useMarshalledMessages(ServerServiceDefinition serverServiceDefinition, Marshaller<T> marshaller) {
        List<ServerMethodDefinition> arrayList = new ArrayList();
        Collection arrayList2 = new ArrayList();
        for (ServerMethodDefinition serverMethodDefinition : serverServiceDefinition.getMethods()) {
            MethodDescriptor build = serverMethodDefinition.getMethodDescriptor().toBuilder(marshaller, marshaller).build();
            arrayList2.add(build);
            arrayList.add(wrapMethod(serverMethodDefinition, build));
        }
        Builder builder = ServerServiceDefinition.builder(new ServiceDescriptor(serverServiceDefinition.getServiceDescriptor().getName(), arrayList2));
        for (ServerMethodDefinition addMethod : arrayList) {
            builder.addMethod(addMethod);
        }
        return builder.build();
    }

    private static <ReqT, RespT> void wrapAndAddMethod(Builder builder, ServerMethodDefinition<ReqT, RespT> serverMethodDefinition, List<? extends ServerInterceptor> list) {
        ServerCallHandler serverCallHandler = serverMethodDefinition.getServerCallHandler();
        for (ServerInterceptor create : list) {
            serverCallHandler = InterceptCallHandler.create(create, serverCallHandler);
        }
        builder.addMethod(serverMethodDefinition.withServerCallHandler(serverCallHandler));
    }

    static <OReqT, ORespT, WReqT, WRespT> ServerMethodDefinition<WReqT, WRespT> wrapMethod(ServerMethodDefinition<OReqT, ORespT> serverMethodDefinition, MethodDescriptor<WReqT, WRespT> methodDescriptor) {
        return ServerMethodDefinition.create(methodDescriptor, wrapHandler(serverMethodDefinition.getServerCallHandler(), serverMethodDefinition.getMethodDescriptor(), methodDescriptor));
    }

    private static <OReqT, ORespT, WReqT, WRespT> ServerCallHandler<WReqT, WRespT> wrapHandler(final ServerCallHandler<OReqT, ORespT> serverCallHandler, final MethodDescriptor<OReqT, ORespT> methodDescriptor, final MethodDescriptor<WReqT, WRespT> methodDescriptor2) {
        return new ServerCallHandler<WReqT, WRespT>() {
            public Listener<WReqT> startCall(final ServerCall<WReqT, WRespT> serverCall, Metadata metadata) {
                final Listener startCall = serverCallHandler.startCall(new PartialForwardingServerCall<OReqT, ORespT>() {
                    protected ServerCall<WReqT, WRespT> delegate() {
                        return serverCall;
                    }

                    public void sendMessage(ORespT oRespT) {
                        delegate().sendMessage(methodDescriptor2.parseResponse(methodDescriptor.streamResponse(oRespT)));
                    }

                    public MethodDescriptor<OReqT, ORespT> getMethodDescriptor() {
                        return methodDescriptor;
                    }
                }, metadata);
                return new PartialForwardingServerCallListener<WReqT>() {
                    protected Listener<OReqT> delegate() {
                        return startCall;
                    }

                    public void onMessage(WReqT wReqT) {
                        delegate().onMessage(methodDescriptor.parseRequest(methodDescriptor2.streamRequest(wReqT)));
                    }
                };
            }
        };
    }
}
