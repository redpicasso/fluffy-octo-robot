package com.google.protobuf;

import com.google.protobuf.Internal.LongList;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class LongArrayList extends AbstractProtobufList<Long> implements LongList, RandomAccess {
    private static final LongArrayList EMPTY_LIST = new LongArrayList();
    private long[] array;
    private int size;

    static {
        EMPTY_LIST.makeImmutable();
    }

    public static LongArrayList emptyList() {
        return EMPTY_LIST;
    }

    LongArrayList() {
        this(new long[10], 0);
    }

    private LongArrayList(long[] jArr, int i) {
        this.array = jArr;
        this.size = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LongArrayList)) {
            return super.equals(obj);
        }
        LongArrayList longArrayList = (LongArrayList) obj;
        if (this.size != longArrayList.size) {
            return false;
        }
        long[] jArr = longArrayList.array;
        for (int i = 0; i < this.size; i++) {
            if (this.array[i] != jArr[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + Internal.hashLong(this.array[i2]);
        }
        return i;
    }

    public LongList mutableCopyWithCapacity(int i) {
        if (i >= this.size) {
            return new LongArrayList(Arrays.copyOf(this.array, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public Long get(int i) {
        return Long.valueOf(getLong(i));
    }

    public long getLong(int i) {
        ensureIndexInRange(i);
        return this.array[i];
    }

    public int size() {
        return this.size;
    }

    public Long set(int i, Long l) {
        return Long.valueOf(setLong(i, l.longValue()));
    }

    public long setLong(int i, long j) {
        ensureIsMutable();
        ensureIndexInRange(i);
        long[] jArr = this.array;
        long j2 = jArr[i];
        jArr[i] = j;
        return j2;
    }

    public void add(int i, Long l) {
        addLong(i, l.longValue());
    }

    public void addLong(long j) {
        addLong(this.size, j);
    }

    private void addLong(int i, long j) {
        ensureIsMutable();
        if (i >= 0) {
            int i2 = this.size;
            if (i <= i2) {
                Object obj = this.array;
                if (i2 < obj.length) {
                    System.arraycopy(obj, i, obj, i + 1, i2 - i);
                } else {
                    Object obj2 = new long[(((i2 * 3) / 2) + 1)];
                    System.arraycopy(obj, 0, obj2, 0, i);
                    System.arraycopy(this.array, i, obj2, i + 1, this.size - i);
                    this.array = obj2;
                }
                this.array[i] = j;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(i));
    }

    public boolean addAll(Collection<? extends Long> collection) {
        ensureIsMutable();
        if (collection == null) {
            throw new NullPointerException();
        } else if (!(collection instanceof LongArrayList)) {
            return super.addAll(collection);
        } else {
            LongArrayList longArrayList = (LongArrayList) collection;
            int i = longArrayList.size;
            if (i == 0) {
                return false;
            }
            int i2 = this.size;
            if (Integer.MAX_VALUE - i2 >= i) {
                i2 += i;
                long[] jArr = this.array;
                if (i2 > jArr.length) {
                    this.array = Arrays.copyOf(jArr, i2);
                }
                System.arraycopy(longArrayList.array, 0, this.array, this.size, longArrayList.size);
                this.size = i2;
                this.modCount++;
                return true;
            }
            throw new OutOfMemoryError();
        }
    }

    public boolean remove(Object obj) {
        ensureIsMutable();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Long.valueOf(this.array[i]))) {
                obj = this.array;
                System.arraycopy(obj, i + 1, obj, i, this.size - i);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    public Long remove(int i) {
        ensureIsMutable();
        ensureIndexInRange(i);
        Object obj = this.array;
        long j = obj[i];
        System.arraycopy(obj, i + 1, obj, i, this.size - i);
        this.size--;
        this.modCount++;
        return Long.valueOf(j);
    }

    private void ensureIndexInRange(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(i));
        }
    }

    private String makeOutOfBoundsExceptionMessage(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index:");
        stringBuilder.append(i);
        stringBuilder.append(", Size:");
        stringBuilder.append(this.size);
        return stringBuilder.toString();
    }
}
