package androidx.core.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Pair<F, S> {
    @Nullable
    public final F first;
    @Nullable
    public final S second;

    public Pair(@Nullable F f, @Nullable S s) {
        this.first = f;
        this.second = s;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        if (ObjectsCompat.equals(pair.first, this.first) && ObjectsCompat.equals(pair.second, this.second)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        Object obj = this.first;
        int i = 0;
        int hashCode = obj == null ? 0 : obj.hashCode();
        Object obj2 = this.second;
        if (obj2 != null) {
            i = obj2.hashCode();
        }
        return hashCode ^ i;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Pair{");
        stringBuilder.append(String.valueOf(this.first));
        stringBuilder.append(" ");
        stringBuilder.append(String.valueOf(this.second));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @NonNull
    public static <A, B> Pair<A, B> create(@Nullable A a, @Nullable B b) {
        return new Pair(a, b);
    }
}
