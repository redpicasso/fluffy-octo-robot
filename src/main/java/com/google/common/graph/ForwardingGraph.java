package com.google.common.graph;

import java.util.Set;

abstract class ForwardingGraph<N> extends AbstractGraph<N> {
    protected abstract BaseGraph<N> delegate();

    ForwardingGraph() {
    }

    public Set<N> nodes() {
        return delegate().nodes();
    }

    protected long edgeCount() {
        return (long) delegate().edges().size();
    }

    public boolean isDirected() {
        return delegate().isDirected();
    }

    public boolean allowsSelfLoops() {
        return delegate().allowsSelfLoops();
    }

    public ElementOrder<N> nodeOrder() {
        return delegate().nodeOrder();
    }

    public Set<N> adjacentNodes(N n) {
        return delegate().adjacentNodes(n);
    }

    public Set<N> predecessors(N n) {
        return delegate().predecessors(n);
    }

    public Set<N> successors(N n) {
        return delegate().successors(n);
    }

    public int degree(N n) {
        return delegate().degree(n);
    }

    public int inDegree(N n) {
        return delegate().inDegree(n);
    }

    public int outDegree(N n) {
        return delegate().outDegree(n);
    }

    public boolean hasEdgeConnecting(N n, N n2) {
        return delegate().hasEdgeConnecting(n, n2);
    }
}
