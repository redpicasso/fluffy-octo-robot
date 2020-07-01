package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.Immutable;
import java.util.Map;

@Immutable(containerOf = {"N", "E"})
@Beta
public final class ImmutableNetwork<N, E> extends ConfigurableNetwork<N, E> {
    private ImmutableNetwork(Network<N, E> network) {
        super(NetworkBuilder.from(network), getNodeConnections(network), getEdgeToReferenceNode(network));
    }

    public static <N, E> ImmutableNetwork<N, E> copyOf(Network<N, E> network) {
        return network instanceof ImmutableNetwork ? (ImmutableNetwork) network : new ImmutableNetwork(network);
    }

    @Deprecated
    public static <N, E> ImmutableNetwork<N, E> copyOf(ImmutableNetwork<N, E> immutableNetwork) {
        return (ImmutableNetwork) Preconditions.checkNotNull(immutableNetwork);
    }

    public ImmutableGraph<N> asGraph() {
        return new ImmutableGraph(super.asGraph());
    }

    private static <N, E> Map<N, NetworkConnections<N, E>> getNodeConnections(Network<N, E> network) {
        Builder builder = ImmutableMap.builder();
        for (Object next : network.nodes()) {
            builder.put(next, connectionsOf(network, next));
        }
        return builder.build();
    }

    private static <N, E> Map<E, N> getEdgeToReferenceNode(Network<N, E> network) {
        Builder builder = ImmutableMap.builder();
        for (Object next : network.edges()) {
            builder.put(next, network.incidentNodes(next).nodeU());
        }
        return builder.build();
    }

    private static <N, E> NetworkConnections<N, E> connectionsOf(Network<N, E> network, N n) {
        NetworkConnections<N, E> ofImmutable;
        if (network.isDirected()) {
            Map asMap = Maps.asMap(network.inEdges(n), sourceNodeFn(network));
            Map asMap2 = Maps.asMap(network.outEdges(n), targetNodeFn(network));
            int size = network.edgesConnecting(n, n).size();
            if (network.allowsParallelEdges()) {
                ofImmutable = DirectedMultiNetworkConnections.ofImmutable(asMap, asMap2, size);
            } else {
                ofImmutable = DirectedNetworkConnections.ofImmutable(asMap, asMap2, size);
            }
            return ofImmutable;
        }
        Map asMap3 = Maps.asMap(network.incidentEdges(n), adjacentNodeFn(network, n));
        if (network.allowsParallelEdges()) {
            ofImmutable = UndirectedMultiNetworkConnections.ofImmutable(asMap3);
        } else {
            ofImmutable = UndirectedNetworkConnections.ofImmutable(asMap3);
        }
        return ofImmutable;
    }

    private static <N, E> Function<E, N> sourceNodeFn(final Network<N, E> network) {
        return new Function<E, N>() {
            public N apply(E e) {
                return network.incidentNodes(e).source();
            }
        };
    }

    private static <N, E> Function<E, N> targetNodeFn(final Network<N, E> network) {
        return new Function<E, N>() {
            public N apply(E e) {
                return network.incidentNodes(e).target();
            }
        };
    }

    private static <N, E> Function<E, N> adjacentNodeFn(final Network<N, E> network, final N n) {
        return new Function<E, N>() {
            public N apply(E e) {
                return network.incidentNodes(e).adjacentNode(n);
            }
        };
    }
}
