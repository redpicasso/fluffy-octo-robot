package io.grpc;

import com.google.common.base.MoreObjects;
import io.grpc.ServerCall.Listener;

abstract class PartialForwardingServerCallListener<ReqT> extends Listener<ReqT> {
    protected abstract Listener<?> delegate();

    PartialForwardingServerCallListener() {
    }

    public void onHalfClose() {
        delegate().onHalfClose();
    }

    public void onCancel() {
        delegate().onCancel();
    }

    public void onComplete() {
        delegate().onComplete();
    }

    public void onReady() {
        delegate().onReady();
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("delegate", delegate()).toString();
    }
}
