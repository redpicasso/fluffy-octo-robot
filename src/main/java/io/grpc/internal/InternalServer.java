package io.grpc.internal;

import io.grpc.InternalChannelz.SocketStats;
import io.grpc.InternalInstrumented;
import java.io.IOException;
import java.util.List;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface InternalServer {
    List<InternalInstrumented<SocketStats>> getListenSockets();

    int getPort();

    void shutdown();

    void start(ServerListener serverListener) throws IOException;
}
