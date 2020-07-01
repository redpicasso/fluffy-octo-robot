package com.google.firebase.database.core.utilities;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Pair<T, U> {
    private final T first;
    private final U second;

    public Pair(T t, U u) {
        this.first = t;
        this.second = u;
    }

    public T getFirst() {
        return this.first;
    }

    public U getSecond() {
        return this.second;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pair pair = (Pair) obj;
        Object obj2 = this.first;
        if (!obj2 == null ? obj2.equals(pair.first) : pair.first == null) {
            return false;
        }
        obj2 = this.second;
        return obj2 == null ? pair.second == null : obj2.equals(pair.second);
    }

    public int hashCode() {
        Object obj = this.first;
        int i = 0;
        int hashCode = (obj != null ? obj.hashCode() : 0) * 31;
        Object obj2 = this.second;
        if (obj2 != null) {
            i = obj2.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Pair(");
        stringBuilder.append(this.first);
        stringBuilder.append(",");
        stringBuilder.append(this.second);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
