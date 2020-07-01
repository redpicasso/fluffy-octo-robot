package io.opencensus.stats;

import io.opencensus.stats.AggregationData.SumDataLong;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_AggregationData_SumDataLong extends SumDataLong {
    private final long sum;

    AutoValue_AggregationData_SumDataLong(long j) {
        this.sum = j;
    }

    public long getSum() {
        return this.sum;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SumDataLong{sum=");
        stringBuilder.append(this.sum);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SumDataLong)) {
            return false;
        }
        if (this.sum != ((SumDataLong) obj).getSum()) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long j = (long) 1000003;
        long j2 = this.sum;
        return (int) (j ^ (j2 ^ (j2 >>> 32)));
    }
}
