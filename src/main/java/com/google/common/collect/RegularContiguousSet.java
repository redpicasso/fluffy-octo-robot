package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Collection;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
final class RegularContiguousSet<C extends Comparable> extends ContiguousSet<C> {
    private static final long serialVersionUID = 0;
    private final Range<C> range;

    @GwtIncompatible
    private static final class SerializedForm<C extends Comparable> implements Serializable {
        final DiscreteDomain<C> domain;
        final Range<C> range;

        /* synthetic */ SerializedForm(Range range, DiscreteDomain discreteDomain, AnonymousClass1 anonymousClass1) {
            this(range, discreteDomain);
        }

        private SerializedForm(Range<C> range, DiscreteDomain<C> discreteDomain) {
            this.range = range;
            this.domain = discreteDomain;
        }

        private Object readResolve() {
            return new RegularContiguousSet(this.range, this.domain);
        }
    }

    public boolean isEmpty() {
        return false;
    }

    boolean isPartialView() {
        return false;
    }

    RegularContiguousSet(Range<C> range, DiscreteDomain<C> discreteDomain) {
        super(discreteDomain);
        this.range = range;
    }

    private ContiguousSet<C> intersectionInCurrentDomain(Range<C> range) {
        return this.range.isConnected(range) ? ContiguousSet.create(this.range.intersection(range), this.domain) : new EmptyContiguousSet(this.domain);
    }

    ContiguousSet<C> headSetImpl(C c, boolean z) {
        return intersectionInCurrentDomain(Range.upTo(c, BoundType.forBoolean(z)));
    }

    ContiguousSet<C> subSetImpl(C c, boolean z, C c2, boolean z2) {
        if (c.compareTo(c2) != 0 || z || z2) {
            return intersectionInCurrentDomain(Range.range(c, BoundType.forBoolean(z), c2, BoundType.forBoolean(z2)));
        }
        return new EmptyContiguousSet(this.domain);
    }

    ContiguousSet<C> tailSetImpl(C c, boolean z) {
        return intersectionInCurrentDomain(Range.downTo(c, BoundType.forBoolean(z)));
    }

    @GwtIncompatible
    int indexOf(Object obj) {
        return contains(obj) ? (int) this.domain.distance(first(), (Comparable) obj) : -1;
    }

    public UnmodifiableIterator<C> iterator() {
        return new AbstractSequentialIterator<C>(first()) {
            final C last = RegularContiguousSet.this.last();

            protected C computeNext(C c) {
                return RegularContiguousSet.equalsOrThrow(c, this.last) ? null : RegularContiguousSet.this.domain.next(c);
            }
        };
    }

    @GwtIncompatible
    public UnmodifiableIterator<C> descendingIterator() {
        return new AbstractSequentialIterator<C>(last()) {
            final C first = RegularContiguousSet.this.first();

            protected C computeNext(C c) {
                return RegularContiguousSet.equalsOrThrow(c, this.first) ? null : RegularContiguousSet.this.domain.previous(c);
            }
        };
    }

    private static boolean equalsOrThrow(Comparable<?> comparable, @NullableDecl Comparable<?> comparable2) {
        return comparable2 != null && Range.compareOrThrow(comparable, comparable2) == 0;
    }

    public C first() {
        return this.range.lowerBound.leastValueAbove(this.domain);
    }

    public C last() {
        return this.range.upperBound.greatestValueBelow(this.domain);
    }

    ImmutableList<C> createAsList() {
        return this.domain.supportsFastOffset ? new ImmutableAsList<C>() {
            ImmutableSortedSet<C> delegateCollection() {
                return RegularContiguousSet.this;
            }

            public C get(int i) {
                Preconditions.checkElementIndex(i, size());
                return RegularContiguousSet.this.domain.offset(RegularContiguousSet.this.first(), (long) i);
            }
        } : super.createAsList();
    }

    public int size() {
        long distance = this.domain.distance(first(), last());
        return distance >= 2147483647L ? Integer.MAX_VALUE : ((int) distance) + 1;
    }

    public boolean contains(@NullableDecl Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return this.range.contains((Comparable) obj);
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public boolean containsAll(Collection<?> collection) {
        return Collections2.containsAllImpl(this, collection);
    }

    public ContiguousSet<C> intersection(ContiguousSet<C> contiguousSet) {
        Preconditions.checkNotNull(contiguousSet);
        Preconditions.checkArgument(this.domain.equals(contiguousSet.domain));
        if (contiguousSet.isEmpty()) {
            return contiguousSet;
        }
        Comparable comparable = (Comparable) Ordering.natural().max(first(), contiguousSet.first());
        Comparable comparable2 = (Comparable) Ordering.natural().min(last(), contiguousSet.last());
        return comparable.compareTo(comparable2) <= 0 ? ContiguousSet.create(Range.closed(comparable, comparable2), this.domain) : new EmptyContiguousSet(this.domain);
    }

    public Range<C> range() {
        return range(BoundType.CLOSED, BoundType.CLOSED);
    }

    public Range<C> range(BoundType boundType, BoundType boundType2) {
        return Range.create(this.range.lowerBound.withLowerBoundType(boundType, this.domain), this.range.upperBound.withUpperBoundType(boundType2, this.domain));
    }

    public boolean equals(@NullableDecl Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (obj instanceof RegularContiguousSet) {
            RegularContiguousSet regularContiguousSet = (RegularContiguousSet) obj;
            if (this.domain.equals(regularContiguousSet.domain)) {
                if (!(first().equals(regularContiguousSet.first()) && last().equals(regularContiguousSet.last()))) {
                    z = false;
                }
                return z;
            }
        }
        return super.equals(obj);
    }

    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }

    @GwtIncompatible
    Object writeReplace() {
        return new SerializedForm(this.range, this.domain, null);
    }
}
