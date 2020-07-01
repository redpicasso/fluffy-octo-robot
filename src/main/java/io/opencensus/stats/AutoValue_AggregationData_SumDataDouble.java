package io.opencensus.stats;

import io.opencensus.stats.AggregationData.SumDataDouble;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_AggregationData_SumDataDouble extends SumDataDouble {
    private final double sum;

    AutoValue_AggregationData_SumDataDouble(double d) {
        this.sum = d;
    }

    public double getSum() {
        return this.sum;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SumDataDouble{sum=");
        stringBuilder.append(this.sum);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SumDataDouble)) {
            return false;
        }
        if (Double.doubleToLongBits(this.sum) != Double.doubleToLongBits(((SumDataDouble) obj).getSum())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (int) (((long) 1000003) ^ ((Double.doubleToLongBits(this.sum) >>> 32) ^ Double.doubleToLongBits(this.sum)));
    }
}
