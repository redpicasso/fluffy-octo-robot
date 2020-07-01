package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.util.AbstractSet;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractBaseGraph<N> implements BaseGraph<N> {

    private static abstract class IncidentEdgeSet<N> extends AbstractSet<EndpointPair<N>> {
        protected final BaseGraph<N> graph;
        protected final N node;

        private static final class Directed<N> extends IncidentEdgeSet<N> {
            /* synthetic */ Directed(BaseGraph baseGraph, Object obj, AnonymousClass1 anonymousClass1) {
                this(baseGraph, obj);
            }

            private Directed(BaseGraph<N> baseGraph, N n) {
                super(baseGraph, n, null);
            }

            public UnmodifiableIterator<EndpointPair<N>> iterator() {
                return Iterators.unmodifiableIterator(Iterators.concat(Iterators.transform(this.graph.predecessors(this.node).iterator(), new Function<N, EndpointPair<N>>() {
                    public EndpointPair<N> apply(N n) {
                        return EndpointPair.ordered(n, Directed.this.node);
                    }
                }), Iterators.transform(Sets.difference(this.graph.successors(this.node), ImmutableSet.of(this.node)).iterator(), new Function<N, EndpointPair<N>>() {
                    public EndpointPair<N> apply(N n) {
                        return EndpointPair.ordered(Directed.this.node, n);
                    }
                })));
            }

            public int size() {
                return (this.graph.inDegree(this.node) + this.graph.outDegree(this.node)) - this.graph.successors(this.node).contains(this.node);
            }

            public boolean contains(@NullableDecl Object obj) {
                boolean z = false;
                if (!(obj instanceof EndpointPair)) {
                    return false;
                }
                EndpointPair endpointPair = (EndpointPair) obj;
                if (!endpointPair.isOrdered()) {
                    return false;
                }
                Object source = endpointPair.source();
                obj = endpointPair.target();
                if ((this.node.equals(source) && this.graph.successors(this.node).contains(obj)) || (this.node.equals(obj) && this.graph.predecessors(this.node).contains(source))) {
                    z = true;
                }
                return z;
            }
        }

        private static final class Undirected<N> extends IncidentEdgeSet<N> {
            /* synthetic */ Undirected(BaseGraph baseGraph, Object obj, AnonymousClass1 anonymousClass1) {
                this(baseGraph, obj);
            }

            private Undirected(BaseGraph<N> baseGraph, N n) {
                super(baseGraph, n, null);
            }

            public UnmodifiableIterator<EndpointPair<N>> iterator() {
                return Iterators.unmodifiableIterator(Iterators.transform(this.graph.adjacentNodes(this.node).iterator(), new Function<N, EndpointPair<N>>() {
                    public EndpointPair<N> apply(N n) {
                        return EndpointPair.unordered(Undirected.this.node, n);
                    }
                }));
            }

            public int size() {
                return this.graph.adjacentNodes(this.node).size();
            }

            public boolean contains(@NullableDecl Object obj) {
                boolean z = false;
                if (!(obj instanceof EndpointPair)) {
                    return false;
                }
                EndpointPair endpointPair = (EndpointPair) obj;
                if (endpointPair.isOrdered()) {
                    return false;
                }
                Set adjacentNodes = this.graph.adjacentNodes(this.node);
                Object nodeU = endpointPair.nodeU();
                obj = endpointPair.nodeV();
                if ((this.node.equals(obj) && adjacentNodes.contains(nodeU)) || (this.node.equals(nodeU) && adjacentNodes.contains(obj))) {
                    z = true;
                }
                return z;
            }
        }

        /* synthetic */ IncidentEdgeSet(BaseGraph baseGraph, Object obj, AnonymousClass1 anonymousClass1) {
            this(baseGraph, obj);
        }

        public static <N> IncidentEdgeSet<N> of(BaseGraph<N> baseGraph, N n) {
            return baseGraph.isDirected() ? new Directed(baseGraph, n, null) : new Undirected(baseGraph, n, null);
        }

        private IncidentEdgeSet(BaseGraph<N> baseGraph, N n) {
            this.graph = baseGraph;
            this.node = n;
        }

        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }
    }

    AbstractBaseGraph() {
    }

    protected long edgeCount() {
        long j = 0;
        for (Object degree : nodes()) {
            j += (long) degree(degree);
        }
        Preconditions.checkState((1 & j) == 0);
        return j >>> 1;
    }

    public Set<EndpointPair<N>> edges() {
        return new AbstractSet<EndpointPair<N>>() {
            public UnmodifiableIterator<EndpointPair<N>> iterator() {
                return EndpointPairIterator.of(AbstractBaseGraph.this);
            }

            public int size() {
                return Ints.saturatedCast(AbstractBaseGraph.this.edgeCount());
            }

            public boolean remove(Object obj) {
                throw new UnsupportedOperationException();
            }

            public boolean contains(@NullableDecl Object obj) {
                boolean z = false;
                if (!(obj instanceof EndpointPair)) {
                    return false;
                }
                EndpointPair endpointPair = (EndpointPair) obj;
                if (AbstractBaseGraph.this.isDirected() == endpointPair.isOrdered() && AbstractBaseGraph.this.nodes().contains(endpointPair.nodeU()) && AbstractBaseGraph.this.successors(endpointPair.nodeU()).contains(endpointPair.nodeV())) {
                    z = true;
                }
                return z;
            }
        };
    }

    public Set<EndpointPair<N>> incidentEdges(N n) {
        Preconditions.checkNotNull(n);
        Preconditions.checkArgument(nodes().contains(n), "Node %s is not an element of this graph.", (Object) n);
        return IncidentEdgeSet.of(this, n);
    }

    public int degree(N n) {
        if (isDirected()) {
            return IntMath.saturatedAdd(predecessors(n).size(), successors(n).size());
        }
        Set adjacentNodes = adjacentNodes(n);
        int i = (allowsSelfLoops() && adjacentNodes.contains(n)) ? 1 : 0;
        return IntMath.saturatedAdd(adjacentNodes.size(), i);
    }

    public int inDegree(N n) {
        return isDirected() ? predecessors(n).size() : degree(n);
    }

    public int outDegree(N n) {
        return isDirected() ? successors(n).size() : degree(n);
    }

    public boolean hasEdgeConnecting(N n, N n2) {
        Preconditions.checkNotNull(n);
        Preconditions.checkNotNull(n2);
        return nodes().contains(n) && successors(n).contains(n2);
    }
}
