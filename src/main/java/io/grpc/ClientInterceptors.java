package io.grpc;

import com.google.common.base.Preconditions;
import io.grpc.ClientCall.Listener;
import io.grpc.MethodDescriptor.Marshaller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClientInterceptors {
    private static final ClientCall<Object, Object> NOOP_CALL = new ClientCall<Object, Object>() {
        public void cancel(String str, Throwable th) {
        }

        public void halfClose() {
        }

        public boolean isReady() {
            return false;
        }

        public void request(int i) {
        }

        public void sendMessage(Object obj) {
        }

        public void start(Listener<Object> listener, Metadata metadata) {
        }
    };

    private static class InterceptorChannel extends Channel {
        private final Channel channel;
        private final ClientInterceptor interceptor;

        /* synthetic */ InterceptorChannel(Channel channel, ClientInterceptor clientInterceptor, AnonymousClass1 anonymousClass1) {
            this(channel, clientInterceptor);
        }

        private InterceptorChannel(Channel channel, ClientInterceptor clientInterceptor) {
            this.channel = channel;
            this.interceptor = (ClientInterceptor) Preconditions.checkNotNull(clientInterceptor, "interceptor");
        }

        public <ReqT, RespT> ClientCall<ReqT, RespT> newCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions) {
            return this.interceptor.interceptCall(methodDescriptor, callOptions, this.channel);
        }

        public String authority() {
            return this.channel.authority();
        }
    }

    public static abstract class CheckedForwardingClientCall<ReqT, RespT> extends ForwardingClientCall<ReqT, RespT> {
        private ClientCall<ReqT, RespT> delegate;

        protected abstract void checkedStart(Listener<RespT> listener, Metadata metadata) throws Exception;

        protected CheckedForwardingClientCall(ClientCall<ReqT, RespT> clientCall) {
            this.delegate = clientCall;
        }

        protected final ClientCall<ReqT, RespT> delegate() {
            return this.delegate;
        }

        public final void start(Listener<RespT> listener, Metadata metadata) {
            try {
                checkedStart(listener, metadata);
            } catch (Throwable e) {
                this.delegate = ClientInterceptors.NOOP_CALL;
                listener.onClose(Status.fromThrowable(e), new Metadata());
            }
        }
    }

    private ClientInterceptors() {
    }

    public static Channel interceptForward(Channel channel, ClientInterceptor... clientInterceptorArr) {
        return interceptForward(channel, Arrays.asList(clientInterceptorArr));
    }

    public static Channel interceptForward(Channel channel, List<? extends ClientInterceptor> list) {
        List arrayList = new ArrayList(list);
        Collections.reverse(arrayList);
        return intercept(channel, arrayList);
    }

    public static Channel intercept(Channel channel, ClientInterceptor... clientInterceptorArr) {
        return intercept(channel, Arrays.asList(clientInterceptorArr));
    }

    public static Channel intercept(Channel channel, List<? extends ClientInterceptor> list) {
        Preconditions.checkNotNull(channel, "channel");
        for (ClientInterceptor interceptorChannel : list) {
            channel = new InterceptorChannel(channel, interceptorChannel, null);
        }
        return channel;
    }

    static <WReqT, WRespT> ClientInterceptor wrapClientInterceptor(final ClientInterceptor clientInterceptor, final Marshaller<WReqT> marshaller, final Marshaller<WRespT> marshaller2) {
        return new ClientInterceptor() {
            public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(final MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
                final ClientCall interceptCall = clientInterceptor.interceptCall(methodDescriptor.toBuilder(marshaller, marshaller2).build(), callOptions, channel);
                return new PartialForwardingClientCall<ReqT, RespT>() {
                    public void start(final Listener<RespT> listener, Metadata metadata) {
                        interceptCall.start(new PartialForwardingClientCallListener<WRespT>() {
                            public void onMessage(WRespT wRespT) {
                                listener.onMessage(methodDescriptor.getResponseMarshaller().parse(marshaller2.stream(wRespT)));
                            }

                            protected Listener<?> delegate() {
                                return listener;
                            }
                        }, metadata);
                    }

                    public void sendMessage(ReqT reqT) {
                        interceptCall.sendMessage(marshaller.parse(methodDescriptor.getRequestMarshaller().stream(reqT)));
                    }

                    protected ClientCall<?, ?> delegate() {
                        return interceptCall;
                    }
                };
            }
        };
    }
}
