package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Table.Cell;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
@Beta
public final class ArrayTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {
    private static final long serialVersionUID = 0;
    private final V[][] array;
    private final ImmutableMap<C, Integer> columnKeyToIndex;
    private final ImmutableList<C> columnList;
    @MonotonicNonNullDecl
    private transient ColumnMap columnMap;
    private final ImmutableMap<R, Integer> rowKeyToIndex;
    private final ImmutableList<R> rowList;
    @MonotonicNonNullDecl
    private transient RowMap rowMap;

    private static abstract class ArrayMap<K, V> extends IteratorBasedAbstractMap<K, V> {
        private final ImmutableMap<K, Integer> keyIndex;

        abstract String getKeyRole();

        @NullableDecl
        abstract V getValue(int i);

        @NullableDecl
        abstract V setValue(int i, V v);

        /* synthetic */ ArrayMap(ImmutableMap immutableMap, AnonymousClass1 anonymousClass1) {
            this(immutableMap);
        }

        private ArrayMap(ImmutableMap<K, Integer> immutableMap) {
            this.keyIndex = immutableMap;
        }

        public Set<K> keySet() {
            return this.keyIndex.keySet();
        }

        K getKey(int i) {
            return this.keyIndex.keySet().asList().get(i);
        }

        public int size() {
            return this.keyIndex.size();
        }

        public boolean isEmpty() {
            return this.keyIndex.isEmpty();
        }

        Entry<K, V> getEntry(final int i) {
            Preconditions.checkElementIndex(i, size());
            return new AbstractMapEntry<K, V>() {
                public K getKey() {
                    return ArrayMap.this.getKey(i);
                }

                public V getValue() {
                    return ArrayMap.this.getValue(i);
                }

                public V setValue(V v) {
                    return ArrayMap.this.setValue(i, v);
                }
            };
        }

        Iterator<Entry<K, V>> entryIterator() {
            return new AbstractIndexedListIterator<Entry<K, V>>(size()) {
                protected Entry<K, V> get(int i) {
                    return ArrayMap.this.getEntry(i);
                }
            };
        }

        public boolean containsKey(@NullableDecl Object obj) {
            return this.keyIndex.containsKey(obj);
        }

        public V get(@NullableDecl Object obj) {
            Integer num = (Integer) this.keyIndex.get(obj);
            if (num == null) {
                return null;
            }
            return getValue(num.intValue());
        }

        public V put(K k, V v) {
            Integer num = (Integer) this.keyIndex.get(k);
            if (num != null) {
                return setValue(num.intValue(), v);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getKeyRole());
            stringBuilder.append(" ");
            stringBuilder.append(k);
            stringBuilder.append(" not in ");
            stringBuilder.append(this.keyIndex.keySet());
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public V remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }
    }

    private class Column extends ArrayMap<R, V> {
        final int columnIndex;

        String getKeyRole() {
            return "Row";
        }

        Column(int i) {
            super(ArrayTable.this.rowKeyToIndex, null);
            this.columnIndex = i;
        }

        V getValue(int i) {
            return ArrayTable.this.at(i, this.columnIndex);
        }

        V setValue(int i, V v) {
            return ArrayTable.this.set(i, this.columnIndex, v);
        }
    }

    private class ColumnMap extends ArrayMap<C, Map<R, V>> {
        String getKeyRole() {
            return "Column";
        }

        /* synthetic */ ColumnMap(ArrayTable arrayTable, AnonymousClass1 anonymousClass1) {
            this();
        }

        private ColumnMap() {
            super(ArrayTable.this.columnKeyToIndex, null);
        }

        Map<R, V> getValue(int i) {
            return new Column(i);
        }

        Map<R, V> setValue(int i, Map<R, V> map) {
            throw new UnsupportedOperationException();
        }

        public Map<R, V> put(C c, Map<R, V> map) {
            throw new UnsupportedOperationException();
        }
    }

    private class Row extends ArrayMap<C, V> {
        final int rowIndex;

        String getKeyRole() {
            return "Column";
        }

        Row(int i) {
            super(ArrayTable.this.columnKeyToIndex, null);
            this.rowIndex = i;
        }

        V getValue(int i) {
            return ArrayTable.this.at(this.rowIndex, i);
        }

        V setValue(int i, V v) {
            return ArrayTable.this.set(this.rowIndex, i, v);
        }
    }

    private class RowMap extends ArrayMap<R, Map<C, V>> {
        String getKeyRole() {
            return "Row";
        }

        /* synthetic */ RowMap(ArrayTable arrayTable, AnonymousClass1 anonymousClass1) {
            this();
        }

        private RowMap() {
            super(ArrayTable.this.rowKeyToIndex, null);
        }

        Map<C, V> getValue(int i) {
            return new Row(i);
        }

        Map<C, V> setValue(int i, Map<C, V> map) {
            throw new UnsupportedOperationException();
        }

        public Map<C, V> put(R r, Map<C, V> map) {
            throw new UnsupportedOperationException();
        }
    }

    public static <R, C, V> ArrayTable<R, C, V> create(Iterable<? extends R> iterable, Iterable<? extends C> iterable2) {
        return new ArrayTable(iterable, iterable2);
    }

    public static <R, C, V> ArrayTable<R, C, V> create(Table<R, C, V> table) {
        return table instanceof ArrayTable ? new ArrayTable((ArrayTable) table) : new ArrayTable((Table) table);
    }

    private ArrayTable(Iterable<? extends R> iterable, Iterable<? extends C> iterable2) {
        this.rowList = ImmutableList.copyOf((Iterable) iterable);
        this.columnList = ImmutableList.copyOf((Iterable) iterable2);
        Preconditions.checkArgument(this.rowList.isEmpty() == this.columnList.isEmpty());
        this.rowKeyToIndex = Maps.indexMap(this.rowList);
        this.columnKeyToIndex = Maps.indexMap(this.columnList);
        this.array = (Object[][]) Array.newInstance(Object.class, new int[]{this.rowList.size(), this.columnList.size()});
        eraseAll();
    }

    private ArrayTable(Table<R, C, V> table) {
        this(table.rowKeySet(), table.columnKeySet());
        putAll(table);
    }

    private ArrayTable(ArrayTable<R, C, V> arrayTable) {
        this.rowList = arrayTable.rowList;
        this.columnList = arrayTable.columnList;
        this.rowKeyToIndex = arrayTable.rowKeyToIndex;
        this.columnKeyToIndex = arrayTable.columnKeyToIndex;
        Object[][] objArr = (Object[][]) Array.newInstance(Object.class, new int[]{this.rowList.size(), this.columnList.size()});
        this.array = objArr;
        for (int i = 0; i < this.rowList.size(); i++) {
            Object[][] objArr2 = arrayTable.array;
            System.arraycopy(objArr2[i], 0, objArr[i], 0, objArr2[i].length);
        }
    }

    public ImmutableList<R> rowKeyList() {
        return this.rowList;
    }

    public ImmutableList<C> columnKeyList() {
        return this.columnList;
    }

    public V at(int i, int i2) {
        Preconditions.checkElementIndex(i, this.rowList.size());
        Preconditions.checkElementIndex(i2, this.columnList.size());
        return this.array[i][i2];
    }

    @CanIgnoreReturnValue
    public V set(int i, int i2, @NullableDecl V v) {
        Preconditions.checkElementIndex(i, this.rowList.size());
        Preconditions.checkElementIndex(i2, this.columnList.size());
        Object[][] objArr = this.array;
        V v2 = objArr[i][i2];
        objArr[i][i2] = v;
        return v2;
    }

    @GwtIncompatible
    public V[][] toArray(Class<V> cls) {
        Object[][] objArr = (Object[][]) Array.newInstance(cls, new int[]{this.rowList.size(), this.columnList.size()});
        for (int i = 0; i < this.rowList.size(); i++) {
            Object[][] objArr2 = this.array;
            System.arraycopy(objArr2[i], 0, objArr[i], 0, objArr2[i].length);
        }
        return objArr;
    }

    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    public void eraseAll() {
        for (Object[] fill : this.array) {
            Arrays.fill(fill, null);
        }
    }

    public boolean contains(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return containsRow(obj) && containsColumn(obj2);
    }

    public boolean containsColumn(@NullableDecl Object obj) {
        return this.columnKeyToIndex.containsKey(obj);
    }

    public boolean containsRow(@NullableDecl Object obj) {
        return this.rowKeyToIndex.containsKey(obj);
    }

    public boolean containsValue(@NullableDecl Object obj) {
        for (Object[] objArr : this.array) {
            for (Object equal : r0[r3]) {
                if (Objects.equal(obj, equal)) {
                    return true;
                }
            }
        }
        return false;
    }

    public V get(@NullableDecl Object obj, @NullableDecl Object obj2) {
        Integer num = (Integer) this.rowKeyToIndex.get(obj);
        Integer num2 = (Integer) this.columnKeyToIndex.get(obj2);
        return (num == null || num2 == null) ? null : at(num.intValue(), num2.intValue());
    }

    public boolean isEmpty() {
        return this.rowList.isEmpty() || this.columnList.isEmpty();
    }

    @CanIgnoreReturnValue
    public V put(R r, C c, @NullableDecl V v) {
        Preconditions.checkNotNull(r);
        Preconditions.checkNotNull(c);
        Integer num = (Integer) this.rowKeyToIndex.get(r);
        boolean z = true;
        Preconditions.checkArgument(num != null, "Row %s not in %s", (Object) r, this.rowList);
        Integer num2 = (Integer) this.columnKeyToIndex.get(c);
        if (num2 == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "Column %s not in %s", (Object) c, this.columnList);
        return set(num.intValue(), num2.intValue(), v);
    }

    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        super.putAll(table);
    }

    @CanIgnoreReturnValue
    @Deprecated
    public V remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    public V erase(@NullableDecl Object obj, @NullableDecl Object obj2) {
        Integer num = (Integer) this.rowKeyToIndex.get(obj);
        Integer num2 = (Integer) this.columnKeyToIndex.get(obj2);
        if (num == null || num2 == null) {
            return null;
        }
        return set(num.intValue(), num2.intValue(), null);
    }

    public int size() {
        return this.rowList.size() * this.columnList.size();
    }

    public Set<Cell<R, C, V>> cellSet() {
        return super.cellSet();
    }

    Iterator<Cell<R, C, V>> cellIterator() {
        return new AbstractIndexedListIterator<Cell<R, C, V>>(size()) {
            protected Cell<R, C, V> get(int i) {
                return ArrayTable.this.getCell(i);
            }
        };
    }

    private Cell<R, C, V> getCell(final int i) {
        return new AbstractCell<R, C, V>() {
            final int columnIndex = (i % ArrayTable.this.columnList.size());
            final int rowIndex = (i / ArrayTable.this.columnList.size());

            public R getRowKey() {
                return ArrayTable.this.rowList.get(this.rowIndex);
            }

            public C getColumnKey() {
                return ArrayTable.this.columnList.get(this.columnIndex);
            }

            public V getValue() {
                return ArrayTable.this.at(this.rowIndex, this.columnIndex);
            }
        };
    }

    private V getValue(int i) {
        return at(i / this.columnList.size(), i % this.columnList.size());
    }

    public Map<R, V> column(C c) {
        Preconditions.checkNotNull(c);
        Integer num = (Integer) this.columnKeyToIndex.get(c);
        return num == null ? ImmutableMap.of() : new Column(num.intValue());
    }

    public ImmutableSet<C> columnKeySet() {
        return this.columnKeyToIndex.keySet();
    }

    public Map<C, Map<R, V>> columnMap() {
        Map<C, Map<R, V>> map = this.columnMap;
        if (map != null) {
            return map;
        }
        Map columnMap = new ColumnMap(this, null);
        this.columnMap = columnMap;
        return columnMap;
    }

    public Map<C, V> row(R r) {
        Preconditions.checkNotNull(r);
        Integer num = (Integer) this.rowKeyToIndex.get(r);
        return num == null ? ImmutableMap.of() : new Row(num.intValue());
    }

    public ImmutableSet<R> rowKeySet() {
        return this.rowKeyToIndex.keySet();
    }

    public Map<R, Map<C, V>> rowMap() {
        Map<R, Map<C, V>> map = this.rowMap;
        if (map != null) {
            return map;
        }
        Map rowMap = new RowMap(this, null);
        this.rowMap = rowMap;
        return rowMap;
    }

    public Collection<V> values() {
        return super.values();
    }

    Iterator<V> valuesIterator() {
        return new AbstractIndexedListIterator<V>(size()) {
            protected V get(int i) {
                return ArrayTable.this.getValue(i);
            }
        };
    }
}
