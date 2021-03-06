package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SparseArrayCompat<E> implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private int[] mKeys;
    private int mSize;
    private Object[] mValues;

    public SparseArrayCompat() {
        this(10);
    }

    public SparseArrayCompat(int i) {
        this.mGarbage = false;
        if (i == 0) {
            this.mKeys = ContainerHelpers.EMPTY_INTS;
            this.mValues = ContainerHelpers.EMPTY_OBJECTS;
            return;
        }
        i = ContainerHelpers.idealIntArraySize(i);
        this.mKeys = new int[i];
        this.mValues = new Object[i];
    }

    public SparseArrayCompat<E> clone() {
        try {
            SparseArrayCompat<E> sparseArrayCompat = (SparseArrayCompat) super.clone();
            sparseArrayCompat.mKeys = (int[]) this.mKeys.clone();
            sparseArrayCompat.mValues = (Object[]) this.mValues.clone();
            return sparseArrayCompat;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Nullable
    public E get(int i) {
        return get(i, null);
    }

    public E get(int i, E e) {
        i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (i >= 0) {
            Object[] objArr = this.mValues;
            if (objArr[i] != DELETED) {
                return objArr[i];
            }
        }
        return e;
    }

    @Deprecated
    public void delete(int i) {
        remove(i);
    }

    public void remove(int i) {
        i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (i >= 0) {
            Object[] objArr = this.mValues;
            Object obj = objArr[i];
            Object obj2 = DELETED;
            if (obj != obj2) {
                objArr[i] = obj2;
                this.mGarbage = true;
            }
        }
    }

    public boolean remove(int i, Object obj) {
        i = indexOfKey(i);
        if (i >= 0) {
            Object valueAt = valueAt(i);
            if (obj == valueAt || (obj != null && obj.equals(valueAt))) {
                removeAt(i);
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

    public void removeAtRange(int i, int i2) {
        i2 = Math.min(this.mSize, i2 + i);
        while (i < i2) {
            removeAt(i);
            i++;
        }
    }

    @Nullable
    public E replace(int i, E e) {
        i = indexOfKey(i);
        if (i < 0) {
            return null;
        }
        Object[] objArr = this.mValues;
        E e2 = objArr[i];
        objArr[i] = e;
        return e2;
    }

    public boolean replace(int i, E e, E e2) {
        i = indexOfKey(i);
        if (i >= 0) {
            E e3 = this.mValues[i];
            if (e3 == e || (e != null && e.equals(e3))) {
                this.mValues[i] = e2;
                return true;
            }
        }
        return false;
    }

    private void gc() {
        int i = this.mSize;
        int[] iArr = this.mKeys;
        Object[] objArr = this.mValues;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != DELETED) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.mGarbage = false;
        this.mSize = i2;
    }

    public void put(int i, E e) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0) {
            this.mValues[binarySearch] = e;
        } else {
            Object obj;
            Object obj2;
            binarySearch = ~binarySearch;
            if (binarySearch < this.mSize) {
                Object[] objArr = this.mValues;
                if (objArr[binarySearch] == DELETED) {
                    this.mKeys[binarySearch] = i;
                    objArr[binarySearch] = e;
                    return;
                }
            }
            if (this.mGarbage && this.mSize >= this.mKeys.length) {
                gc();
                binarySearch = ~ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
            }
            int i2 = this.mSize;
            if (i2 >= this.mKeys.length) {
                i2 = ContainerHelpers.idealIntArraySize(i2 + 1);
                obj = new int[i2];
                obj2 = new Object[i2];
                Object obj3 = this.mKeys;
                System.arraycopy(obj3, 0, obj, 0, obj3.length);
                obj3 = this.mValues;
                System.arraycopy(obj3, 0, obj2, 0, obj3.length);
                this.mKeys = obj;
                this.mValues = obj2;
            }
            i2 = this.mSize;
            if (i2 - binarySearch != 0) {
                obj = this.mKeys;
                int i3 = binarySearch + 1;
                System.arraycopy(obj, binarySearch, obj, i3, i2 - binarySearch);
                obj2 = this.mValues;
                System.arraycopy(obj2, binarySearch, obj2, i3, this.mSize - binarySearch);
            }
            this.mKeys[binarySearch] = i;
            this.mValues[binarySearch] = e;
            this.mSize++;
        }
    }

    public void putAll(@NonNull SparseArrayCompat<? extends E> sparseArrayCompat) {
        int size = sparseArrayCompat.size();
        for (int i = 0; i < size; i++) {
            put(sparseArrayCompat.keyAt(i), sparseArrayCompat.valueAt(i));
        }
    }

    @Nullable
    public E putIfAbsent(int i, E e) {
        E e2 = get(i);
        if (e2 == null) {
            put(i, e);
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

    public int keyAt(int i) {
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

    public int indexOfKey(int i) {
        if (this.mGarbage) {
            gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
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

    public boolean containsKey(int i) {
        return indexOfKey(i) >= 0;
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

    public void append(int i, E e) {
        int i2 = this.mSize;
        if (i2 == 0 || i > this.mKeys[i2 - 1]) {
            if (this.mGarbage && this.mSize >= this.mKeys.length) {
                gc();
            }
            i2 = this.mSize;
            if (i2 >= this.mKeys.length) {
                int idealIntArraySize = ContainerHelpers.idealIntArraySize(i2 + 1);
                Object obj = new int[idealIntArraySize];
                Object obj2 = new Object[idealIntArraySize];
                Object obj3 = this.mKeys;
                System.arraycopy(obj3, 0, obj, 0, obj3.length);
                obj3 = this.mValues;
                System.arraycopy(obj3, 0, obj2, 0, obj3.length);
                this.mKeys = obj;
                this.mValues = obj2;
            }
            this.mKeys[i2] = i;
            this.mValues[i2] = e;
            this.mSize = i2 + 1;
            return;
        }
        put(i, e);
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
            SparseArrayCompat valueAt = valueAt(i);
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
