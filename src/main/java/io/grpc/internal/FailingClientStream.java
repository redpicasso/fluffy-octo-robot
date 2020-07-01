package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.internal.ClientStreamListener.RpcProgress;

public final class FailingClientStream extends NoopClientStream {
    private final Status error;
    private final RpcProgress rpcProgress;
    private boolean started;

    public FailingClientStream(Status status) {
        this(status, RpcProgress.PROCESSED);
    }

    public FailingClientStream(Status status, RpcProgress rpcProgress) {
        Preconditions.checkArgument(status.isOk() ^ 1, "error must not be OK");
        this.error = status;
        this.rpcProgress = rpcProgress;
    }

    public void start(ClientStreamListener clientStreamListener) {
        Preconditions.checkState(this.started ^ true, "already started");
        this.started = true;
        clientStreamListener.closed(this.error, this.rpcProgress, new Metadata());
    }

    @VisibleForTesting
    Status getError() {
        return this.error;
    }
}
