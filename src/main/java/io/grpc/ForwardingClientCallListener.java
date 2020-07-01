package io.grpc;

import io.grpc.ClientCall.Listener;

public abstract class ForwardingClientCallListener<RespT> extends PartialForwardingClientCallListener<RespT> {

    public static abstract class SimpleForwardingClientCallListener<RespT> extends ForwardingClientCallListener<RespT> {
        private final Listener<RespT> delegate;

        protected SimpleForwardingClientCallListener(Listener<RespT> listener) {
            this.delegate = listener;
        }

        protected Listener<RespT> delegate() {
            return this.delegate;
        }
    }

    protected abstract Listener<RespT> delegate();

    public void onMessage(RespT respT) {
        delegate().onMessage(respT);
    }
}
