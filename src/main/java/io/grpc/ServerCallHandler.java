package io.grpc;

import io.grpc.ServerCall.Listener;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface ServerCallHandler<RequestT, ResponseT> {
    Listener<RequestT> startCall(ServerCall<RequestT, ResponseT> serverCall, Metadata metadata);
}
