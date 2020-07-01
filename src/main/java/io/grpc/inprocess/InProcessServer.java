package io.grpc.inprocess;

import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.grpc.InternalChannelz.SocketStats;
import io.grpc.InternalInstrumented;
import io.grpc.ServerStreamTracer.Factory;
import io.grpc.internal.InternalServer;
import io.grpc.internal.ObjectPool;
import io.grpc.internal.ServerListener;
import io.grpc.internal.ServerTransportListener;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
final class InProcessServer implements InternalServer {
    private static final ConcurrentMap<String, InProcessServer> registry = new ConcurrentHashMap();
    private ServerListener listener;
    private final String name;
    private ScheduledExecutorService scheduler;
    private final ObjectPool<ScheduledExecutorService> schedulerPool;
    private boolean shutdown;
    private final List<Factory> streamTracerFactories;

    public int getPort() {
        return -1;
    }

    static InProcessServer findServer(String str) {
        return (InProcessServer) registry.get(str);
    }

    InProcessServer(String str, ObjectPool<ScheduledExecutorService> objectPool, List<Factory> list) {
        this.name = str;
        this.schedulerPool = objectPool;
        this.streamTracerFactories = Collections.unmodifiableList((List) Preconditions.checkNotNull(list, "streamTracerFactories"));
    }

    public void start(ServerListener serverListener) throws IOException {
        this.listener = serverListener;
        this.scheduler = (ScheduledExecutorService) this.schedulerPool.getObject();
        if (registry.putIfAbsent(this.name, this) != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("name already registered: ");
            stringBuilder.append(this.name);
            throw new IOException(stringBuilder.toString());
        }
    }

    public List<InternalInstrumented<SocketStats>> getListenSockets() {
        return Collections.emptyList();
    }

    public void shutdown() {
        if (registry.remove(this.name, this)) {
            this.scheduler = (ScheduledExecutorService) this.schedulerPool.returnObject(this.scheduler);
            synchronized (this) {
                this.shutdown = true;
                this.listener.serverShutdown();
            }
            return;
        }
        throw new AssertionError();
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add(ConditionalUserProperty.NAME, this.name).toString();
    }

    synchronized ServerTransportListener register(InProcessTransport inProcessTransport) {
        if (this.shutdown) {
            return null;
        }
        return this.listener.transportCreated(inProcessTransport);
    }

    ObjectPool<ScheduledExecutorService> getScheduledExecutorServicePool() {
        return this.schedulerPool;
    }

    List<Factory> getStreamTracerFactories() {
        return this.streamTracerFactories;
    }
}
