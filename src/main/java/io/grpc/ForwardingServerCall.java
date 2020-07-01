package io.grpc;

public abstract class ForwardingServerCall<ReqT, RespT> extends PartialForwardingServerCall<ReqT, RespT> {

    public static abstract class SimpleForwardingServerCall<ReqT, RespT> extends ForwardingServerCall<ReqT, RespT> {
        private final ServerCall<ReqT, RespT> delegate;

        protected SimpleForwardingServerCall(ServerCall<ReqT, RespT> serverCall) {
            this.delegate = serverCall;
        }

        protected ServerCall<ReqT, RespT> delegate() {
            return this.delegate;
        }

        public MethodDescriptor<ReqT, RespT> getMethodDescriptor() {
            return this.delegate.getMethodDescriptor();
        }
    }

    protected abstract ServerCall<ReqT, RespT> delegate();

    /* renamed from: sendMessage */
    public void access$001(RespT respT) {
        delegate().sendMessage(respT);
    }
}
