package io.grpc;

import io.grpc.ServerCall.Listener;

public abstract class ForwardingServerCallListener<ReqT> extends PartialForwardingServerCallListener<ReqT> {

    public static abstract class SimpleForwardingServerCallListener<ReqT> extends ForwardingServerCallListener<ReqT> {
        private final Listener<ReqT> delegate;

        protected SimpleForwardingServerCallListener(Listener<ReqT> listener) {
            this.delegate = listener;
        }

        protected Listener<ReqT> delegate() {
            return this.delegate;
        }
    }

    protected abstract Listener<ReqT> delegate();

    public void onMessage(ReqT reqT) {
        delegate().onMessage(reqT);
    }
}
