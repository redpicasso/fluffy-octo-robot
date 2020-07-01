package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import me.leolin.shortcutbadger.impl.NewHtcHomeBadger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
public final class TreeMultiset<E> extends AbstractSortedMultiset<E> implements Serializable {
    @GwtIncompatible
    private static final long serialVersionUID = 1;
    private final transient AvlNode<E> header;
    private final transient GeneralRange<E> range;
    private final transient Reference<AvlNode<E>> rootReference;

    /* renamed from: com.google.common.collect.TreeMultiset$4 */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$google$common$collect$BoundType = new int[BoundType.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:5:0x001f, code:
            return;
     */
        static {
            /*
            r0 = com.google.common.collect.BoundType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$common$collect$BoundType = r0;
            r0 = $SwitchMap$com$google$common$collect$BoundType;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.common.collect.BoundType.OPEN;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$common$collect$BoundType;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.common.collect.BoundType.CLOSED;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.TreeMultiset.4.<clinit>():void");
        }
    }

    private enum Aggregate {
        SIZE {
            int nodeAggregate(AvlNode<?> avlNode) {
                return avlNode.elemCount;
            }

            long treeAggregate(@NullableDecl AvlNode<?> avlNode) {
                return avlNode == null ? 0 : avlNode.totalCount;
            }
        },
        DISTINCT {
            int nodeAggregate(AvlNode<?> avlNode) {
                return 1;
            }

            long treeAggregate(@NullableDecl AvlNode<?> avlNode) {
                return avlNode == null ? 0 : (long) avlNode.distinctElements;
            }
        };

        abstract int nodeAggregate(AvlNode<?> avlNode);

        abstract long treeAggregate(@NullableDecl AvlNode<?> avlNode);
    }

    private static final class AvlNode<E> {
        private int distinctElements;
        @NullableDecl
        private final E elem;
        private int elemCount;
        private int height;
        @NullableDecl
        private AvlNode<E> left;
        @NullableDecl
        private AvlNode<E> pred;
        @NullableDecl
        private AvlNode<E> right;
        @NullableDecl
        private AvlNode<E> succ;
        private long totalCount;

        AvlNode(@NullableDecl E e, int i) {
            Preconditions.checkArgument(i > 0);
            this.elem = e;
            this.elemCount = i;
            this.totalCount = (long) i;
            this.distinctElements = 1;
            this.height = 1;
            this.left = null;
            this.right = null;
        }

        public int count(Comparator<? super E> comparator, E e) {
            int compare = comparator.compare(e, this.elem);
            int i = 0;
            AvlNode avlNode;
            if (compare < 0) {
                avlNode = this.left;
                if (avlNode != null) {
                    i = avlNode.count(comparator, e);
                }
                return i;
            } else if (compare <= 0) {
                return this.elemCount;
            } else {
                avlNode = this.right;
                if (avlNode != null) {
                    i = avlNode.count(comparator, e);
                }
                return i;
            }
        }

        private AvlNode<E> addRightChild(E e, int i) {
            this.right = new AvlNode(e, i);
            TreeMultiset.successor(this, this.right, this.succ);
            this.height = Math.max(2, this.height);
            this.distinctElements++;
            this.totalCount += (long) i;
            return this;
        }

        private AvlNode<E> addLeftChild(E e, int i) {
            this.left = new AvlNode(e, i);
            TreeMultiset.successor(this.pred, this.left, this);
            this.height = Math.max(2, this.height);
            this.distinctElements++;
            this.totalCount += (long) i;
            return this;
        }

        AvlNode<E> add(Comparator<? super E> comparator, @NullableDecl E e, int i, int[] iArr) {
            int compare = comparator.compare(e, this.elem);
            boolean z = true;
            AvlNode avlNode;
            int i2;
            if (compare < 0) {
                avlNode = this.left;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return addLeftChild(e, i);
                }
                i2 = avlNode.height;
                this.left = avlNode.add(comparator, e, i, iArr);
                if (iArr[0] == 0) {
                    this.distinctElements++;
                }
                this.totalCount += (long) i;
                return this.left.height == i2 ? this : rebalance();
            } else if (compare > 0) {
                avlNode = this.right;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return addRightChild(e, i);
                }
                i2 = avlNode.height;
                this.right = avlNode.add(comparator, e, i, iArr);
                if (iArr[0] == 0) {
                    this.distinctElements++;
                }
                this.totalCount += (long) i;
                return this.right.height == i2 ? this : rebalance();
            } else {
                int i3 = this.elemCount;
                iArr[0] = i3;
                long j = (long) i;
                if (((long) i3) + j > 2147483647L) {
                    z = false;
                }
                Preconditions.checkArgument(z);
                this.elemCount += i;
                this.totalCount += j;
                return this;
            }
        }

        AvlNode<E> remove(Comparator<? super E> comparator, @NullableDecl E e, int i, int[] iArr) {
            int compare = comparator.compare(e, this.elem);
            AvlNode avlNode;
            if (compare < 0) {
                avlNode = this.left;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return this;
                }
                this.left = avlNode.remove(comparator, e, i, iArr);
                if (iArr[0] > 0) {
                    if (i >= iArr[0]) {
                        this.distinctElements--;
                        this.totalCount -= (long) iArr[0];
                    } else {
                        this.totalCount -= (long) i;
                    }
                }
                return iArr[0] == 0 ? this : rebalance();
            } else if (compare > 0) {
                avlNode = this.right;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return this;
                }
                this.right = avlNode.remove(comparator, e, i, iArr);
                if (iArr[0] > 0) {
                    if (i >= iArr[0]) {
                        this.distinctElements--;
                        this.totalCount -= (long) iArr[0];
                    } else {
                        this.totalCount -= (long) i;
                    }
                }
                return rebalance();
            } else {
                int i2 = this.elemCount;
                iArr[0] = i2;
                if (i >= i2) {
                    return deleteMe();
                }
                this.elemCount = i2 - i;
                this.totalCount -= (long) i;
                return this;
            }
        }

        AvlNode<E> setCount(Comparator<? super E> comparator, @NullableDecl E e, int i, int[] iArr) {
            int compare = comparator.compare(e, this.elem);
            AvlNode avlNode;
            if (compare < 0) {
                avlNode = this.left;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return i > 0 ? addLeftChild(e, i) : this;
                }
                this.left = avlNode.setCount(comparator, e, i, iArr);
                if (i == 0 && iArr[0] != 0) {
                    this.distinctElements--;
                } else if (i > 0 && iArr[0] == 0) {
                    this.distinctElements++;
                }
                this.totalCount += (long) (i - iArr[0]);
                return rebalance();
            } else if (compare > 0) {
                avlNode = this.right;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return i > 0 ? addRightChild(e, i) : this;
                }
                this.right = avlNode.setCount(comparator, e, i, iArr);
                if (i == 0 && iArr[0] != 0) {
                    this.distinctElements--;
                } else if (i > 0 && iArr[0] == 0) {
                    this.distinctElements++;
                }
                this.totalCount += (long) (i - iArr[0]);
                return rebalance();
            } else {
                int i2 = this.elemCount;
                iArr[0] = i2;
                if (i == 0) {
                    return deleteMe();
                }
                this.totalCount += (long) (i - i2);
                this.elemCount = i;
                return this;
            }
        }

        AvlNode<E> setCount(Comparator<? super E> comparator, @NullableDecl E e, int i, int i2, int[] iArr) {
            int compare = comparator.compare(e, this.elem);
            AvlNode avlNode;
            if (compare < 0) {
                avlNode = this.left;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return (i != 0 || i2 <= 0) ? this : addLeftChild(e, i2);
                } else {
                    this.left = avlNode.setCount(comparator, e, i, i2, iArr);
                    if (iArr[0] == i) {
                        if (i2 == 0 && iArr[0] != 0) {
                            this.distinctElements--;
                        } else if (i2 > 0 && iArr[0] == 0) {
                            this.distinctElements++;
                        }
                        this.totalCount += (long) (i2 - iArr[0]);
                    }
                    return rebalance();
                }
            } else if (compare > 0) {
                avlNode = this.right;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return (i != 0 || i2 <= 0) ? this : addRightChild(e, i2);
                } else {
                    this.right = avlNode.setCount(comparator, e, i, i2, iArr);
                    if (iArr[0] == i) {
                        if (i2 == 0 && iArr[0] != 0) {
                            this.distinctElements--;
                        } else if (i2 > 0 && iArr[0] == 0) {
                            this.distinctElements++;
                        }
                        this.totalCount += (long) (i2 - iArr[0]);
                    }
                    return rebalance();
                }
            } else {
                int i3 = this.elemCount;
                iArr[0] = i3;
                if (i == i3) {
                    if (i2 == 0) {
                        return deleteMe();
                    }
                    this.totalCount += (long) (i2 - i3);
                    this.elemCount = i2;
                }
                return this;
            }
        }

        private AvlNode<E> deleteMe() {
            int i = this.elemCount;
            this.elemCount = 0;
            TreeMultiset.successor(this.pred, this.succ);
            AvlNode<E> avlNode = this.left;
            if (avlNode == null) {
                return this.right;
            }
            AvlNode avlNode2 = this.right;
            if (avlNode2 == null) {
                return avlNode;
            }
            if (avlNode.height >= avlNode2.height) {
                avlNode2 = this.pred;
                avlNode2.left = avlNode.removeMax(avlNode2);
                avlNode2.right = this.right;
                avlNode2.distinctElements = this.distinctElements - 1;
                avlNode2.totalCount = this.totalCount - ((long) i);
                return avlNode2.rebalance();
            }
            AvlNode avlNode3 = this.succ;
            avlNode3.right = avlNode2.removeMin(avlNode3);
            avlNode3.left = this.left;
            avlNode3.distinctElements = this.distinctElements - 1;
            avlNode3.totalCount = this.totalCount - ((long) i);
            return avlNode3.rebalance();
        }

        private AvlNode<E> removeMin(AvlNode<E> avlNode) {
            AvlNode avlNode2 = this.left;
            if (avlNode2 == null) {
                return this.right;
            }
            this.left = avlNode2.removeMin(avlNode);
            this.distinctElements--;
            this.totalCount -= (long) avlNode.elemCount;
            return rebalance();
        }

        private AvlNode<E> removeMax(AvlNode<E> avlNode) {
            AvlNode avlNode2 = this.right;
            if (avlNode2 == null) {
                return this.left;
            }
            this.right = avlNode2.removeMax(avlNode);
            this.distinctElements--;
            this.totalCount -= (long) avlNode.elemCount;
            return rebalance();
        }

        private void recomputeMultiset() {
            this.distinctElements = (TreeMultiset.distinctElements(this.left) + 1) + TreeMultiset.distinctElements(this.right);
            this.totalCount = (((long) this.elemCount) + totalCount(this.left)) + totalCount(this.right);
        }

        private void recomputeHeight() {
            this.height = Math.max(height(this.left), height(this.right)) + 1;
        }

        private void recompute() {
            recomputeMultiset();
            recomputeHeight();
        }

        private AvlNode<E> rebalance() {
            int balanceFactor = balanceFactor();
            if (balanceFactor == -2) {
                if (this.right.balanceFactor() > 0) {
                    this.right = this.right.rotateRight();
                }
                return rotateLeft();
            } else if (balanceFactor != 2) {
                recomputeHeight();
                return this;
            } else {
                if (this.left.balanceFactor() < 0) {
                    this.left = this.left.rotateLeft();
                }
                return rotateRight();
            }
        }

        private int balanceFactor() {
            return height(this.left) - height(this.right);
        }

        private AvlNode<E> rotateLeft() {
            Preconditions.checkState(this.right != null);
            AvlNode<E> avlNode = this.right;
            this.right = avlNode.left;
            avlNode.left = this;
            avlNode.totalCount = this.totalCount;
            avlNode.distinctElements = this.distinctElements;
            recompute();
            avlNode.recomputeHeight();
            return avlNode;
        }

        private AvlNode<E> rotateRight() {
            Preconditions.checkState(this.left != null);
            AvlNode<E> avlNode = this.left;
            this.left = avlNode.right;
            avlNode.right = this;
            avlNode.totalCount = this.totalCount;
            avlNode.distinctElements = this.distinctElements;
            recompute();
            avlNode.recomputeHeight();
            return avlNode;
        }

        private static long totalCount(@NullableDecl AvlNode<?> avlNode) {
            return avlNode == null ? 0 : avlNode.totalCount;
        }

        private static int height(@NullableDecl AvlNode<?> avlNode) {
            return avlNode == null ? 0 : avlNode.height;
        }

        @NullableDecl
        private AvlNode<E> ceiling(Comparator<? super E> comparator, E e) {
            int compare = comparator.compare(e, this.elem);
            AvlNode avlNode;
            if (compare < 0) {
                avlNode = this.left;
                return avlNode == null ? this : (AvlNode) MoreObjects.firstNonNull(avlNode.ceiling(comparator, e), this);
            } else if (compare == 0) {
                return this;
            } else {
                avlNode = this.right;
                return avlNode == null ? null : avlNode.ceiling(comparator, e);
            }
        }

        @NullableDecl
        private AvlNode<E> floor(Comparator<? super E> comparator, E e) {
            int compare = comparator.compare(e, this.elem);
            AvlNode avlNode;
            if (compare > 0) {
                avlNode = this.right;
                return avlNode == null ? this : (AvlNode) MoreObjects.firstNonNull(avlNode.floor(comparator, e), this);
            } else if (compare == 0) {
                return this;
            } else {
                avlNode = this.left;
                return avlNode == null ? null : avlNode.floor(comparator, e);
            }
        }

        E getElement() {
            return this.elem;
        }

        int getCount() {
            return this.elemCount;
        }

        public String toString() {
            return Multisets.immutableEntry(getElement(), getCount()).toString();
        }
    }

    private static final class Reference<T> {
        @NullableDecl
        private T value;

        private Reference() {
        }

        /* synthetic */ Reference(AnonymousClass1 anonymousClass1) {
            this();
        }

        @NullableDecl
        public T get() {
            return this.value;
        }

        public void checkAndSet(@NullableDecl T t, T t2) {
            if (this.value == t) {
                this.value = t2;
                return;
            }
            throw new ConcurrentModificationException();
        }

        void clear() {
            this.value = null;
        }
    }

    public static <E extends Comparable> TreeMultiset<E> create() {
        return new TreeMultiset(Ordering.natural());
    }

    public static <E> TreeMultiset<E> create(@NullableDecl Comparator<? super E> comparator) {
        return comparator == null ? new TreeMultiset(Ordering.natural()) : new TreeMultiset(comparator);
    }

    public static <E extends Comparable> TreeMultiset<E> create(Iterable<? extends E> iterable) {
        Object create = create();
        Iterables.addAll(create, iterable);
        return create;
    }

    TreeMultiset(Reference<AvlNode<E>> reference, GeneralRange<E> generalRange, AvlNode<E> avlNode) {
        super(generalRange.comparator());
        this.rootReference = reference;
        this.range = generalRange;
        this.header = avlNode;
    }

    TreeMultiset(Comparator<? super E> comparator) {
        super(comparator);
        this.range = GeneralRange.all(comparator);
        this.header = new AvlNode(null, 1);
        AvlNode avlNode = this.header;
        successor(avlNode, avlNode);
        this.rootReference = new Reference();
    }

    private long aggregateForEntries(Aggregate aggregate) {
        AvlNode avlNode = (AvlNode) this.rootReference.get();
        long treeAggregate = aggregate.treeAggregate(avlNode);
        if (this.range.hasLowerBound()) {
            treeAggregate -= aggregateBelowRange(aggregate, avlNode);
        }
        return this.range.hasUpperBound() ? treeAggregate - aggregateAboveRange(aggregate, avlNode) : treeAggregate;
    }

    private long aggregateBelowRange(Aggregate aggregate, @NullableDecl AvlNode<E> avlNode) {
        if (avlNode == null) {
            return 0;
        }
        int compare = comparator().compare(this.range.getLowerEndpoint(), avlNode.elem);
        if (compare < 0) {
            return aggregateBelowRange(aggregate, avlNode.left);
        }
        long nodeAggregate;
        long treeAggregate;
        if (compare == 0) {
            compare = AnonymousClass4.$SwitchMap$com$google$common$collect$BoundType[this.range.getLowerBoundType().ordinal()];
            if (compare == 1) {
                nodeAggregate = (long) aggregate.nodeAggregate(avlNode);
                treeAggregate = aggregate.treeAggregate(avlNode.left);
            } else if (compare == 2) {
                return aggregate.treeAggregate(avlNode.left);
            } else {
                throw new AssertionError();
            }
        }
        nodeAggregate = aggregate.treeAggregate(avlNode.left) + ((long) aggregate.nodeAggregate(avlNode));
        treeAggregate = aggregateBelowRange(aggregate, avlNode.right);
        return nodeAggregate + treeAggregate;
    }

    private long aggregateAboveRange(Aggregate aggregate, @NullableDecl AvlNode<E> avlNode) {
        if (avlNode == null) {
            return 0;
        }
        int compare = comparator().compare(this.range.getUpperEndpoint(), avlNode.elem);
        if (compare > 0) {
            return aggregateAboveRange(aggregate, avlNode.right);
        }
        long nodeAggregate;
        long treeAggregate;
        if (compare == 0) {
            compare = AnonymousClass4.$SwitchMap$com$google$common$collect$BoundType[this.range.getUpperBoundType().ordinal()];
            if (compare == 1) {
                nodeAggregate = (long) aggregate.nodeAggregate(avlNode);
                treeAggregate = aggregate.treeAggregate(avlNode.right);
            } else if (compare == 2) {
                return aggregate.treeAggregate(avlNode.right);
            } else {
                throw new AssertionError();
            }
        }
        nodeAggregate = aggregate.treeAggregate(avlNode.right) + ((long) aggregate.nodeAggregate(avlNode));
        treeAggregate = aggregateAboveRange(aggregate, avlNode.left);
        return nodeAggregate + treeAggregate;
    }

    public int size() {
        return Ints.saturatedCast(aggregateForEntries(Aggregate.SIZE));
    }

    int distinctElements() {
        return Ints.saturatedCast(aggregateForEntries(Aggregate.DISTINCT));
    }

    static int distinctElements(@NullableDecl AvlNode<?> avlNode) {
        return avlNode == null ? 0 : avlNode.distinctElements;
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x001d A:{RETURN, ExcHandler: java.lang.ClassCastException (unused java.lang.ClassCastException), Splitter: B:1:0x0001} */
    /* JADX WARNING: Missing block: B:7:0x001d, code:
            return 0;
     */
    public int count(@org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object r4) {
        /*
        r3 = this;
        r0 = 0;
        r1 = r3.rootReference;	 Catch:{ ClassCastException -> 0x001d, ClassCastException -> 0x001d }
        r1 = r1.get();	 Catch:{ ClassCastException -> 0x001d, ClassCastException -> 0x001d }
        r1 = (com.google.common.collect.TreeMultiset.AvlNode) r1;	 Catch:{ ClassCastException -> 0x001d, ClassCastException -> 0x001d }
        r2 = r3.range;	 Catch:{ ClassCastException -> 0x001d, ClassCastException -> 0x001d }
        r2 = r2.contains(r4);	 Catch:{ ClassCastException -> 0x001d, ClassCastException -> 0x001d }
        if (r2 == 0) goto L_0x001d;
    L_0x0011:
        if (r1 != 0) goto L_0x0014;
    L_0x0013:
        goto L_0x001d;
    L_0x0014:
        r2 = r3.comparator();	 Catch:{ ClassCastException -> 0x001d, ClassCastException -> 0x001d }
        r4 = r1.count(r2, r4);	 Catch:{ ClassCastException -> 0x001d, ClassCastException -> 0x001d }
        return r4;
    L_0x001d:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.TreeMultiset.count(java.lang.Object):int");
    }

    @CanIgnoreReturnValue
    public int add(@NullableDecl E e, int i) {
        CollectPreconditions.checkNonnegative(i, "occurrences");
        if (i == 0) {
            return count(e);
        }
        Preconditions.checkArgument(this.range.contains(e));
        AvlNode avlNode = (AvlNode) this.rootReference.get();
        if (avlNode == null) {
            comparator().compare(e, e);
            AvlNode avlNode2 = new AvlNode(e, i);
            AvlNode avlNode3 = this.header;
            successor(avlNode3, avlNode2, avlNode3);
            this.rootReference.checkAndSet(avlNode, avlNode2);
            return 0;
        }
        int[] iArr = new int[1];
        this.rootReference.checkAndSet(avlNode, avlNode.add(comparator(), e, i, iArr));
        return iArr[0];
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0033 A:{RETURN, ExcHandler: java.lang.ClassCastException (unused java.lang.ClassCastException), Splitter: B:5:0x0018} */
    /* JADX WARNING: Missing block: B:12:0x0033, code:
            return 0;
     */
    @com.google.errorprone.annotations.CanIgnoreReturnValue
    public int remove(@org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object r5, int r6) {
        /*
        r4 = this;
        r0 = "occurrences";
        com.google.common.collect.CollectPreconditions.checkNonnegative(r6, r0);
        if (r6 != 0) goto L_0x000c;
    L_0x0007:
        r5 = r4.count(r5);
        return r5;
    L_0x000c:
        r0 = r4.rootReference;
        r0 = r0.get();
        r0 = (com.google.common.collect.TreeMultiset.AvlNode) r0;
        r1 = 1;
        r1 = new int[r1];
        r2 = 0;
        r3 = r4.range;	 Catch:{ ClassCastException -> 0x0033, ClassCastException -> 0x0033 }
        r3 = r3.contains(r5);	 Catch:{ ClassCastException -> 0x0033, ClassCastException -> 0x0033 }
        if (r3 == 0) goto L_0x0033;
    L_0x0020:
        if (r0 != 0) goto L_0x0023;
    L_0x0022:
        goto L_0x0033;
    L_0x0023:
        r3 = r4.comparator();	 Catch:{ ClassCastException -> 0x0033, ClassCastException -> 0x0033 }
        r5 = r0.remove(r3, r5, r6, r1);	 Catch:{ ClassCastException -> 0x0033, ClassCastException -> 0x0033 }
        r6 = r4.rootReference;
        r6.checkAndSet(r0, r5);
        r5 = r1[r2];
        return r5;
    L_0x0033:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.TreeMultiset.remove(java.lang.Object, int):int");
    }

    @CanIgnoreReturnValue
    public int setCount(@NullableDecl E e, int i) {
        CollectPreconditions.checkNonnegative(i, NewHtcHomeBadger.COUNT);
        boolean z = true;
        if (this.range.contains(e)) {
            AvlNode avlNode = (AvlNode) this.rootReference.get();
            if (avlNode == null) {
                if (i > 0) {
                    add(e, i);
                }
                return 0;
            }
            int[] iArr = new int[1];
            this.rootReference.checkAndSet(avlNode, avlNode.setCount(comparator(), e, i, iArr));
            return iArr[0];
        }
        if (i != 0) {
            z = false;
        }
        Preconditions.checkArgument(z);
        return 0;
    }

    @CanIgnoreReturnValue
    public boolean setCount(@NullableDecl E e, int i, int i2) {
        CollectPreconditions.checkNonnegative(i2, "newCount");
        CollectPreconditions.checkNonnegative(i, "oldCount");
        Preconditions.checkArgument(this.range.contains(e));
        AvlNode avlNode = (AvlNode) this.rootReference.get();
        boolean z = false;
        if (avlNode != null) {
            int[] iArr = new int[1];
            this.rootReference.checkAndSet(avlNode, avlNode.setCount(comparator(), e, i, i2, iArr));
            if (iArr[0] == i) {
                z = true;
            }
            return z;
        } else if (i != 0) {
            return false;
        } else {
            if (i2 > 0) {
                add(e, i2);
            }
            return true;
        }
    }

    public void clear() {
        if (this.range.hasLowerBound() || this.range.hasUpperBound()) {
            Iterators.clear(entryIterator());
            return;
        }
        AvlNode access$800 = this.header.succ;
        while (true) {
            AvlNode avlNode = this.header;
            if (access$800 != avlNode) {
                avlNode = access$800.succ;
                access$800.elemCount = 0;
                access$800.left = null;
                access$800.right = null;
                access$800.pred = null;
                access$800.succ = null;
                access$800 = avlNode;
            } else {
                successor(avlNode, avlNode);
                this.rootReference.clear();
                return;
            }
        }
    }

    private Entry<E> wrapEntry(final AvlNode<E> avlNode) {
        return new AbstractEntry<E>() {
            public E getElement() {
                return avlNode.getElement();
            }

            public int getCount() {
                int count = avlNode.getCount();
                return count == 0 ? TreeMultiset.this.count(getElement()) : count;
            }
        };
    }

    @NullableDecl
    private AvlNode<E> firstNode() {
        if (((AvlNode) this.rootReference.get()) == null) {
            return null;
        }
        AvlNode<E> avlNode;
        if (this.range.hasLowerBound()) {
            Object lowerEndpoint = this.range.getLowerEndpoint();
            AvlNode<E> access$1000 = ((AvlNode) this.rootReference.get()).ceiling(comparator(), lowerEndpoint);
            if (access$1000 == null) {
                return null;
            }
            if (this.range.getLowerBoundType() == BoundType.OPEN && comparator().compare(lowerEndpoint, access$1000.getElement()) == 0) {
                access$1000 = access$1000.succ;
            }
            avlNode = access$1000;
        } else {
            avlNode = this.header.succ;
        }
        if (avlNode == this.header || !this.range.contains(avlNode.getElement())) {
            avlNode = null;
        }
        return avlNode;
    }

    @NullableDecl
    private AvlNode<E> lastNode() {
        if (((AvlNode) this.rootReference.get()) == null) {
            return null;
        }
        AvlNode<E> avlNode;
        if (this.range.hasUpperBound()) {
            Object upperEndpoint = this.range.getUpperEndpoint();
            AvlNode<E> access$1100 = ((AvlNode) this.rootReference.get()).floor(comparator(), upperEndpoint);
            if (access$1100 == null) {
                return null;
            }
            if (this.range.getUpperBoundType() == BoundType.OPEN && comparator().compare(upperEndpoint, access$1100.getElement()) == 0) {
                access$1100 = access$1100.pred;
            }
            avlNode = access$1100;
        } else {
            avlNode = this.header.pred;
        }
        if (avlNode == this.header || !this.range.contains(avlNode.getElement())) {
            avlNode = null;
        }
        return avlNode;
    }

    Iterator<E> elementIterator() {
        return Multisets.elementIterator(entryIterator());
    }

    Iterator<Entry<E>> entryIterator() {
        return new Iterator<Entry<E>>() {
            AvlNode<E> current = TreeMultiset.this.firstNode();
            @NullableDecl
            Entry<E> prevEntry;

            public boolean hasNext() {
                if (this.current == null) {
                    return false;
                }
                if (!TreeMultiset.this.range.tooHigh(this.current.getElement())) {
                    return true;
                }
                this.current = null;
                return false;
            }

            public Entry<E> next() {
                if (hasNext()) {
                    Entry<E> access$1400 = TreeMultiset.this.wrapEntry(this.current);
                    this.prevEntry = access$1400;
                    if (this.current.succ == TreeMultiset.this.header) {
                        this.current = null;
                    } else {
                        this.current = this.current.succ;
                    }
                    return access$1400;
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                CollectPreconditions.checkRemove(this.prevEntry != null);
                TreeMultiset.this.setCount(this.prevEntry.getElement(), 0);
                this.prevEntry = null;
            }
        };
    }

    Iterator<Entry<E>> descendingEntryIterator() {
        return new Iterator<Entry<E>>() {
            AvlNode<E> current = TreeMultiset.this.lastNode();
            Entry<E> prevEntry = null;

            public boolean hasNext() {
                if (this.current == null) {
                    return false;
                }
                if (!TreeMultiset.this.range.tooLow(this.current.getElement())) {
                    return true;
                }
                this.current = null;
                return false;
            }

            public Entry<E> next() {
                if (hasNext()) {
                    Entry<E> access$1400 = TreeMultiset.this.wrapEntry(this.current);
                    this.prevEntry = access$1400;
                    if (this.current.pred == TreeMultiset.this.header) {
                        this.current = null;
                    } else {
                        this.current = this.current.pred;
                    }
                    return access$1400;
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                CollectPreconditions.checkRemove(this.prevEntry != null);
                TreeMultiset.this.setCount(this.prevEntry.getElement(), 0);
                this.prevEntry = null;
            }
        };
    }

    public Iterator<E> iterator() {
        return Multisets.iteratorImpl(this);
    }

    public SortedMultiset<E> headMultiset(@NullableDecl E e, BoundType boundType) {
        return new TreeMultiset(this.rootReference, this.range.intersect(GeneralRange.upTo(comparator(), e, boundType)), this.header);
    }

    public SortedMultiset<E> tailMultiset(@NullableDecl E e, BoundType boundType) {
        return new TreeMultiset(this.rootReference, this.range.intersect(GeneralRange.downTo(comparator(), e, boundType)), this.header);
    }

    private static <T> void successor(AvlNode<T> avlNode, AvlNode<T> avlNode2) {
        avlNode.succ = avlNode2;
        avlNode2.pred = avlNode;
    }

    private static <T> void successor(AvlNode<T> avlNode, AvlNode<T> avlNode2, AvlNode<T> avlNode3) {
        successor(avlNode, avlNode2);
        successor(avlNode2, avlNode3);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(elementSet().comparator());
        Serialization.writeMultiset(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Object obj = (Comparator) objectInputStream.readObject();
        Serialization.getFieldSetter(AbstractSortedMultiset.class, "comparator").set((Object) this, obj);
        Serialization.getFieldSetter(TreeMultiset.class, "range").set((Object) this, GeneralRange.all(obj));
        Serialization.getFieldSetter(TreeMultiset.class, "rootReference").set((Object) this, new Reference());
        obj = new AvlNode(null, 1);
        Serialization.getFieldSetter(TreeMultiset.class, "header").set((Object) this, obj);
        successor(obj, obj);
        Serialization.populateMultiset(this, objectInputStream);
    }
}
