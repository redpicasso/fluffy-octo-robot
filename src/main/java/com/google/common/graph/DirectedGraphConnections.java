package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class DirectedGraphConnections<N, V> implements GraphConnections<N, V> {
    private static final Object PRED = new Object();
    private final Map<N, Object> adjacentNodeValues;
    private int predecessorCount;
    private int successorCount;

    private static final class PredAndSucc {
        private final Object successorValue;

        PredAndSucc(Object obj) {
            this.successorValue = obj;
        }
    }

    private DirectedGraphConnections(Map<N, Object> map, int i, int i2) {
        this.adjacentNodeValues = (Map) Preconditions.checkNotNull(map);
        this.predecessorCount = Graphs.checkNonNegative(i);
        this.successorCount = Graphs.checkNonNegative(i2);
        boolean z = i <= map.size() && i2 <= map.size();
        Preconditions.checkState(z);
    }

    static <N, V> DirectedGraphConnections<N, V> of() {
        return new DirectedGraphConnections(new HashMap(4, 1.0f), 0, 0);
    }

    static <N, V> DirectedGraphConnections<N, V> ofImmutable(Set<N> set, Map<N, V> map) {
        Map hashMap = new HashMap();
        hashMap.putAll(map);
        for (Object next : set) {
            Object put = hashMap.put(next, PRED);
            if (put != null) {
                hashMap.put(next, new PredAndSucc(put));
            }
        }
        return new DirectedGraphConnections(ImmutableMap.copyOf(hashMap), set.size(), map.size());
    }

    public Set<N> adjacentNodes() {
        return Collections.unmodifiableSet(this.adjacentNodeValues.keySet());
    }

    public Set<N> predecessors() {
        return new AbstractSet<N>() {
            public UnmodifiableIterator<N> iterator() {
                final Iterator it = DirectedGraphConnections.this.adjacentNodeValues.entrySet().iterator();
                return new AbstractIterator<N>() {
                    protected N computeNext() {
                        while (it.hasNext()) {
                            Entry entry = (Entry) it.next();
                            if (DirectedGraphConnections.isPredecessor(entry.getValue())) {
                                return entry.getKey();
                            }
                        }
                        return endOfData();
                    }
                };
            }

            public int size() {
                return DirectedGraphConnections.this.predecessorCount;
            }

            public boolean contains(@NullableDecl Object obj) {
                return DirectedGraphConnections.isPredecessor(DirectedGraphConnections.this.adjacentNodeValues.get(obj));
            }
        };
    }

    public Set<N> successors() {
        return new AbstractSet<N>() {
            public UnmodifiableIterator<N> iterator() {
                final Iterator it = DirectedGraphConnections.this.adjacentNodeValues.entrySet().iterator();
                return new AbstractIterator<N>() {
                    protected N computeNext() {
                        while (it.hasNext()) {
                            Entry entry = (Entry) it.next();
                            if (DirectedGraphConnections.isSuccessor(entry.getValue())) {
                                return entry.getKey();
                            }
                        }
                        return endOfData();
                    }
                };
            }

            public int size() {
                return DirectedGraphConnections.this.successorCount;
            }

            public boolean contains(@NullableDecl Object obj) {
                return DirectedGraphConnections.isSuccessor(DirectedGraphConnections.this.adjacentNodeValues.get(obj));
            }
        };
    }

    public V value(N n) {
        V v = this.adjacentNodeValues.get(n);
        if (v == PRED) {
            return null;
        }
        if (v instanceof PredAndSucc) {
            v = ((PredAndSucc) v).successorValue;
        }
        return v;
    }

    public void removePredecessor(N n) {
        Object obj = this.adjacentNodeValues.get(n);
        int i;
        if (obj == PRED) {
            this.adjacentNodeValues.remove(n);
            i = this.predecessorCount - 1;
            this.predecessorCount = i;
            Graphs.checkNonNegative(i);
        } else if (obj instanceof PredAndSucc) {
            this.adjacentNodeValues.put(n, ((PredAndSucc) obj).successorValue);
            i = this.predecessorCount - 1;
            this.predecessorCount = i;
            Graphs.checkNonNegative(i);
        }
    }

    public V removeSuccessor(Object obj) {
        V v = this.adjacentNodeValues.get(obj);
        if (v != null) {
            V v2 = PRED;
            if (v != v2) {
                int i;
                if (v instanceof PredAndSucc) {
                    this.adjacentNodeValues.put(obj, v2);
                    i = this.successorCount - 1;
                    this.successorCount = i;
                    Graphs.checkNonNegative(i);
                    return ((PredAndSucc) v).successorValue;
                }
                this.adjacentNodeValues.remove(obj);
                i = this.successorCount - 1;
                this.successorCount = i;
                Graphs.checkNonNegative(i);
                return v;
            }
        }
        return null;
    }

    public void addPredecessor(N n, V v) {
        Object put = this.adjacentNodeValues.put(n, PRED);
        int i;
        if (put == null) {
            i = this.predecessorCount + 1;
            this.predecessorCount = i;
            Graphs.checkPositive(i);
        } else if (put instanceof PredAndSucc) {
            this.adjacentNodeValues.put(n, put);
        } else if (put != PRED) {
            this.adjacentNodeValues.put(n, new PredAndSucc(put));
            i = this.predecessorCount + 1;
            this.predecessorCount = i;
            Graphs.checkPositive(i);
        }
    }

    public V addSuccessor(N n, V v) {
        V put = this.adjacentNodeValues.put(n, v);
        int i;
        if (put == null) {
            i = this.successorCount + 1;
            this.successorCount = i;
            Graphs.checkPositive(i);
            return null;
        } else if (put instanceof PredAndSucc) {
            this.adjacentNodeValues.put(n, new PredAndSucc(v));
            return ((PredAndSucc) put).successorValue;
        } else if (put != PRED) {
            return put;
        } else {
            this.adjacentNodeValues.put(n, new PredAndSucc(v));
            i = this.successorCount + 1;
            this.successorCount = i;
            Graphs.checkPositive(i);
            return null;
        }
    }

    private static boolean isPredecessor(@NullableDecl Object obj) {
        return obj == PRED || (obj instanceof PredAndSucc);
    }

    private static boolean isSuccessor(@NullableDecl Object obj) {
        return (obj == PRED || obj == null) ? false : true;
    }
}
