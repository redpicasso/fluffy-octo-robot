package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.errorprone.annotations.Immutable;
import java.util.Comparator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
@Beta
public final class ElementOrder<T> {
    @NullableDecl
    private final Comparator<T> comparator;
    private final Type type;

    /* renamed from: com.google.common.graph.ElementOrder$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$common$graph$ElementOrder$Type = new int[Type.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$google$common$graph$ElementOrder$Type[com.google.common.graph.ElementOrder.Type.SORTED.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.google.common.graph.ElementOrder.Type.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$common$graph$ElementOrder$Type = r0;
            r0 = $SwitchMap$com$google$common$graph$ElementOrder$Type;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.common.graph.ElementOrder.Type.UNORDERED;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$common$graph$ElementOrder$Type;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.common.graph.ElementOrder.Type.INSERTION;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$google$common$graph$ElementOrder$Type;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.google.common.graph.ElementOrder.Type.SORTED;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.graph.ElementOrder.1.<clinit>():void");
        }
    }

    public enum Type {
        UNORDERED,
        INSERTION,
        SORTED
    }

    <T1 extends T> ElementOrder<T1> cast() {
        return this;
    }

    private ElementOrder(Type type, @NullableDecl Comparator<T> comparator) {
        this.type = (Type) Preconditions.checkNotNull(type);
        this.comparator = comparator;
        boolean z = true;
        if ((type == Type.SORTED ? 1 : null) != (comparator != null ? 1 : null)) {
            z = false;
        }
        Preconditions.checkState(z);
    }

    public static <S> ElementOrder<S> unordered() {
        return new ElementOrder(Type.UNORDERED, null);
    }

    public static <S> ElementOrder<S> insertion() {
        return new ElementOrder(Type.INSERTION, null);
    }

    public static <S extends Comparable<? super S>> ElementOrder<S> natural() {
        return new ElementOrder(Type.SORTED, Ordering.natural());
    }

    public static <S> ElementOrder<S> sorted(Comparator<S> comparator) {
        return new ElementOrder(Type.SORTED, comparator);
    }

    public Type type() {
        return this.type;
    }

    public Comparator<T> comparator() {
        Comparator<T> comparator = this.comparator;
        if (comparator != null) {
            return comparator;
        }
        throw new UnsupportedOperationException("This ordering does not define a comparator.");
    }

    public boolean equals(@NullableDecl Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ElementOrder)) {
            return false;
        }
        ElementOrder elementOrder = (ElementOrder) obj;
        if (!(this.type == elementOrder.type && Objects.equal(this.comparator, elementOrder.comparator))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return Objects.hashCode(this.type, this.comparator);
    }

    public String toString() {
        ToStringHelper add = MoreObjects.toStringHelper((Object) this).add("type", this.type);
        Object obj = this.comparator;
        if (obj != null) {
            add.add("comparator", obj);
        }
        return add.toString();
    }

    <K extends T, V> Map<K, V> createMap(int i) {
        int i2 = AnonymousClass1.$SwitchMap$com$google$common$graph$ElementOrder$Type[this.type.ordinal()];
        if (i2 == 1) {
            return Maps.newHashMapWithExpectedSize(i);
        }
        if (i2 == 2) {
            return Maps.newLinkedHashMapWithExpectedSize(i);
        }
        if (i2 == 3) {
            return Maps.newTreeMap(comparator());
        }
        throw new AssertionError();
    }
}
