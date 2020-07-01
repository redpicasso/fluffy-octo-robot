package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LongSparseArray<E> implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private long[] mKeys;
    private int mSize;
    private Object[] mValues;

    public LongSparseArray() {
        this(10);
    }

    public LongSparseArray(int i) {
        this.mGarbage = false;
        if (i == 0) {
            this.mKeys = ContainerHelpers.EMPTY_LONGS;
            this.mValues = ContainerHelpers.EMPTY_OBJECTS;
            return;
        }
        i = ContainerHelpers.idealLongArraySize(i);
        this.mKeys = new long[i];
        this.mValues = new Object[i];
    }

    public LongSparseArray<E> clone() {
        try {
            LongSparseArray<E> longSparseArray = (LongSparseArray) super.clone();
            longSparseArray.mKeys = (long[]) this.mKeys.clone();
            longSparseArray.mValues = (Object[]) this.mValues.clone();
            return longSparseArray;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Nullable
    public E get(long j) {
        return get(j, null);
    }

    public E get(long j, E e) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        if (binarySearch >= 0) {
            Object[] objArr = this.mValues;
            if (objArr[binarySearch] != DELETED) {
                return objArr[binarySearch];
            }
        }
        return e;
    }

    @Deprecated
    public void delete(long j) {
        remove(j);
    }

    public void remove(long j) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        if (binarySearch >= 0) {
            Object[] objArr = this.mValues;
            Object obj = objArr[binarySearch];
            Object obj2 = DELETED;
            if (obj != obj2) {
                objArr[binarySearch] = obj2;
                this.mGarbage = true;
            }
        }
    }

    public boolean remove(long j, Object obj) {
        int indexOfKey = indexOfKey(j);
        if (indexOfKey >= 0) {
            Object valueAt = valueAt(indexOfKey);
            if (obj == valueAt || (obj != null && obj.equals(valueAt))) {
                removeAt(indexOfKey);
                return true;
            }
        }
        return false;
    }

    public void removeAt(int i) {
        Object[] objArr = this.mValues;
        Object obj = objArr[i];
        Object obj2 = DELETED;
        if (obj != obj2) {
            objArr[i] = obj2;
            this.mGarbage = true;
        }
    }

    @Nullable
    public E replace(long j, E e) {
        int indexOfKey = indexOfKey(j);
        if (indexOfKey < 0) {
            return null;
        }
        Object[] objArr = this.mValues;
        E e2 = objArr[indexOfKey];
        objArr[indexOfKey] = e;
        return e2;
    }

    public boolean replace(long j, E e, E e2) {
        int indexOfKey = indexOfKey(j);
        if (indexOfKey >= 0) {
            E e3 = this.mValues[indexOfKey];
            if (e3 == e || (e != null && e.equals(e3))) {
                this.mValues[indexOfKey] = e2;
                return true;
            }
        }
        return false;
    }

    private void gc() {
        int i = this.mSize;
        long[] jArr = this.mKeys;
        Object[] objArr = this.mValues;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != DELETED) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.mGarbage = false;
        this.mSize = i2;
    }

    public void put(long j, E e) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        if (binarySearch >= 0) {
            this.mValues[binarySearch] = e;
        } else {
            Object obj;
            Object obj2;
            binarySearch = ~binarySearch;
            if (binarySearch < this.mSize) {
                Object[] objArr = this.mValues;
                if (objArr[binarySearch] == DELETED) {
                    this.mKeys[binarySearch] = j;
                    objArr[binarySearch] = e;
                    return;
                }
            }
            if (this.mGarbage && this.mSize >= this.mKeys.length) {
                gc();
                binarySearch = ~ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
            }
            int i = this.mSize;
            if (i >= this.mKeys.length) {
                i = ContainerHelpers.idealLongArraySize(i + 1);
                obj = new long[i];
                obj2 = new Object[i];
                Object obj3 = this.mKeys;
                System.arraycopy(obj3, 0, obj, 0, obj3.length);
                obj3 = this.mValues;
                System.arraycopy(obj3, 0, obj2, 0, obj3.length);
                this.mKeys = obj;
                this.mValues = obj2;
            }
            i = this.mSize;
            if (i - binarySearch != 0) {
                obj = this.mKeys;
                int i2 = binarySearch + 1;
                System.arraycopy(obj, binarySearch, obj, i2, i - binarySearch);
                obj2 = this.mValues;
                System.arraycopy(obj2, binarySearch, obj2, i2, this.mSize - binarySearch);
            }
            this.mKeys[binarySearch] = j;
            this.mValues[binarySearch] = e;
            this.mSize++;
        }
    }

    public void putAll(@NonNull LongSparseArray<? extends E> longSparseArray) {
        int size = longSparseArray.size();
        for (int i = 0; i < size; i++) {
            put(longSparseArray.keyAt(i), longSparseArray.valueAt(i));
        }
    }

    @Nullable
    public E putIfAbsent(long j, E e) {
        E e2 = get(j);
        if (e2 == null) {
            put(j, e);
        }
        return e2;
    }

    public int size() {
        if (this.mGarbage) {
            gc();
        }
        return this.mSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public long keyAt(int i) {
        if (this.mGarbage) {
            gc();
        }
        return this.mKeys[i];
    }

    public E valueAt(int i) {
        if (this.mGarbage) {
            gc();
        }
        return this.mValues[i];
    }

    public void setValueAt(int i, E e) {
        if (this.mGarbage) {
            gc();
        }
        this.mValues[i] = e;
    }

    public int indexOfKey(long j) {
        if (this.mGarbage) {
            gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
    }

    public int indexOfValue(E e) {
        if (this.mGarbage) {
            gc();
        }
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == e) {
                return i;
            }
        }
        return -1;
    }

    public boolean containsKey(long j) {
        return indexOfKey(j) >= 0;
    }

    public boolean containsValue(E e) {
        return indexOfValue(e) >= 0;
    }

    public void clear() {
        int i = this.mSize;
        Object[] objArr = this.mValues;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.mSize = 0;
        this.mGarbage = false;
    }

    public void append(long j, E e) {
        int i = this.mSize;
        if (i == 0 || j > this.mKeys[i - 1]) {
            if (this.mGarbage && this.mSize >= this.mKeys.length) {
                gc();
            }
            i = this.mSize;
            if (i >= this.mKeys.length) {
                int idealLongArraySize = ContainerHelpers.idealLongArraySize(i + 1);
                Object obj = new long[idealLongArraySize];
                Object obj2 = new Object[idealLongArraySize];
                Object obj3 = this.mKeys;
                System.arraycopy(obj3, 0, obj, 0, obj3.length);
                obj3 = this.mValues;
                System.arraycopy(obj3, 0, obj2, 0, obj3.length);
                this.mKeys = obj;
                this.mValues = obj2;
            }
            this.mKeys[i] = j;
            this.mValues[i] = e;
            this.mSize = i + 1;
            return;
        }
        put(j, e);
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.mSize * 28);
        stringBuilder.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(keyAt(i));
            stringBuilder.append('=');
            LongSparseArray valueAt = valueAt(i);
            if (valueAt != this) {
                stringBuilder.append(valueAt);
            } else {
                stringBuilder.append("(this Map)");
            }
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
