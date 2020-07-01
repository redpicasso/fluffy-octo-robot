package io.opencensus.common;

import com.google.common.math.LongMath;
import com.google.common.primitives.Longs;
import java.math.RoundingMode;
import javax.annotation.concurrent.Immutable;

@Immutable
public abstract class Timestamp implements Comparable<Timestamp> {
    private static final Timestamp EPOCH = new AutoValue_Timestamp(0, 0);

    public abstract int getNanos();

    public abstract long getSeconds();

    Timestamp() {
    }

    public static Timestamp create(long j, int i) {
        if (j < -315576000000L || j > 315576000000L) {
            return EPOCH;
        }
        if (i < 0 || i > 999999999) {
            return EPOCH;
        }
        return new AutoValue_Timestamp(j, i);
    }

    public static Timestamp fromMillis(long j) {
        return create(floorDiv(j, 1000), (int) (((long) ((int) floorMod(j, 1000))) * 1000000));
    }

    public Timestamp addNanos(long j) {
        return plus(0, j);
    }

    public Timestamp addDuration(Duration duration) {
        return plus(duration.getSeconds(), (long) duration.getNanos());
    }

    public Duration subtractTimestamp(Timestamp timestamp) {
        long j;
        long seconds = getSeconds() - timestamp.getSeconds();
        int nanos = getNanos() - timestamp.getNanos();
        int i = (seconds > 0 ? 1 : (seconds == 0 ? 0 : -1));
        if (i >= 0 || nanos <= 0) {
            if (i > 0 && nanos < 0) {
                seconds--;
                j = ((long) nanos) + 1000000000;
            }
            return Duration.create(seconds, nanos);
        }
        seconds++;
        j = ((long) nanos) - 1000000000;
        nanos = (int) j;
        return Duration.create(seconds, nanos);
    }

    public int compareTo(Timestamp timestamp) {
        int compare = Longs.compare(getSeconds(), timestamp.getSeconds());
        if (compare != 0) {
            return compare;
        }
        return Longs.compare((long) getNanos(), (long) timestamp.getNanos());
    }

    private Timestamp plus(long j, long j2) {
        if ((j | j2) == 0) {
            return this;
        }
        return ofEpochSecond(LongMath.checkedAdd(LongMath.checkedAdd(getSeconds(), j), j2 / 1000000000), ((long) getNanos()) + (j2 % 1000000000));
    }

    private static Timestamp ofEpochSecond(long j, long j2) {
        return create(LongMath.checkedAdd(j, floorDiv(j2, 1000000000)), (int) floorMod(j2, 1000000000));
    }

    private static long floorDiv(long j, long j2) {
        return LongMath.divide(j, j2, RoundingMode.FLOOR);
    }

    private static long floorMod(long j, long j2) {
        return j - (floorDiv(j, j2) * j2);
    }
}
