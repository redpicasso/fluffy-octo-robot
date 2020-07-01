package io.grpc;

import io.grpc.ServerCall.Listener;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface ServerInterceptor {
    <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler);
}
