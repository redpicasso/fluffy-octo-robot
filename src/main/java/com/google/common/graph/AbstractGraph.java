package com.google.common.graph;

import com.google.common.annotations.Beta;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
public abstract class AbstractGraph<N> extends AbstractBaseGraph<N> implements Graph<N> {
    public final boolean equals(@NullableDecl Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Graph)) {
            return false;
        }
        Graph graph = (Graph) obj;
        if (!(isDirected() == graph.isDirected() && nodes().equals(graph.nodes()) && edges().equals(graph.edges()))) {
            z = false;
        }
        return z;
    }

    public final int hashCode() {
        return edges().hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("isDirected: ");
        stringBuilder.append(isDirected());
        stringBuilder.append(", allowsSelfLoops: ");
        stringBuilder.append(allowsSelfLoops());
        stringBuilder.append(", nodes: ");
        stringBuilder.append(nodes());
        stringBuilder.append(", edges: ");
        stringBuilder.append(edges());
        return stringBuilder.toString();
    }
}
