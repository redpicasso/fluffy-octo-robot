package io.grpc;

import io.grpc.ClientCall.Listener;

public abstract class ForwardingClientCall<ReqT, RespT> extends PartialForwardingClientCall<ReqT, RespT> {

    public static abstract class SimpleForwardingClientCall<ReqT, RespT> extends ForwardingClientCall<ReqT, RespT> {
        private final ClientCall<ReqT, RespT> delegate;

        protected SimpleForwardingClientCall(ClientCall<ReqT, RespT> clientCall) {
            this.delegate = clientCall;
        }

        protected ClientCall<ReqT, RespT> delegate() {
            return this.delegate;
        }
    }

    protected abstract ClientCall<ReqT, RespT> delegate();

    public void start(Listener<RespT> listener, Metadata metadata) {
        delegate().start(listener, metadata);
    }

    public void sendMessage(ReqT reqT) {
        delegate().sendMessage(reqT);
    }
}
