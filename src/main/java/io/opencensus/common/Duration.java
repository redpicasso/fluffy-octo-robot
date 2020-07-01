package io.opencensus.common;

import com.google.common.primitives.Longs;
import javax.annotation.concurrent.Immutable;

@Immutable
public abstract class Duration implements Comparable<Duration> {
    private static final Duration ZERO = create(0, 0);

    public abstract int getNanos();

    public abstract long getSeconds();

    public static Duration create(long j, int i) {
        if (j < -315576000000L || j > 315576000000L) {
            return ZERO;
        }
        if (i < -999999999 || i > 999999999) {
            return ZERO;
        }
        int i2 = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if ((i2 >= 0 || i <= 0) && (i2 <= 0 || i >= 0)) {
            return new AutoValue_Duration(j, i);
        }
        return ZERO;
    }

    public static Duration fromMillis(long j) {
        return create(j / 1000, (int) ((j % 1000) * 1000000));
    }

    public int compareTo(Duration duration) {
        int compare = Longs.compare(getSeconds(), duration.getSeconds());
        if (compare != 0) {
            return compare;
        }
        return Longs.compare((long) getNanos(), (long) duration.getNanos());
    }

    Duration() {
    }
}
