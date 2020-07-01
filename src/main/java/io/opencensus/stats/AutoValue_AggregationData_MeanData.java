package io.opencensus.stats;

import io.opencensus.stats.AggregationData.MeanData;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_AggregationData_MeanData extends MeanData {
    private final long count;
    private final double mean;

    AutoValue_AggregationData_MeanData(double d, long j) {
        this.mean = d;
        this.count = j;
    }

    public double getMean() {
        return this.mean;
    }

    public long getCount() {
        return this.count;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MeanData{mean=");
        stringBuilder.append(this.mean);
        stringBuilder.append(", count=");
        stringBuilder.append(this.count);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MeanData)) {
            return false;
        }
        MeanData meanData = (MeanData) obj;
        if (!(Double.doubleToLongBits(this.mean) == Double.doubleToLongBits(meanData.getMean()) && this.count == meanData.getCount())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long doubleToLongBits = (long) (((int) (((long) 1000003) ^ ((Double.doubleToLongBits(this.mean) >>> 32) ^ Double.doubleToLongBits(this.mean)))) * 1000003);
        long j = this.count;
        return (int) (doubleToLongBits ^ (j ^ (j >>> 32)));
    }
}
