package io.opencensus.stats;

import io.opencensus.stats.AggregationData.DistributionData;
import java.util.List;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_AggregationData_DistributionData extends DistributionData {
    private final List<Long> bucketCounts;
    private final long count;
    private final double max;
    private final double mean;
    private final double min;
    private final double sumOfSquaredDeviations;

    AutoValue_AggregationData_DistributionData(double d, long j, double d2, double d3, double d4, List<Long> list) {
        this.mean = d;
        this.count = j;
        this.min = d2;
        this.max = d3;
        this.sumOfSquaredDeviations = d4;
        if (list != null) {
            this.bucketCounts = list;
            return;
        }
        throw new NullPointerException("Null bucketCounts");
    }

    public double getMean() {
        return this.mean;
    }

    public long getCount() {
        return this.count;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public double getSumOfSquaredDeviations() {
        return this.sumOfSquaredDeviations;
    }

    public List<Long> getBucketCounts() {
        return this.bucketCounts;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DistributionData{mean=");
        stringBuilder.append(this.mean);
        stringBuilder.append(", count=");
        stringBuilder.append(this.count);
        stringBuilder.append(", min=");
        stringBuilder.append(this.min);
        stringBuilder.append(", max=");
        stringBuilder.append(this.max);
        stringBuilder.append(", sumOfSquaredDeviations=");
        stringBuilder.append(this.sumOfSquaredDeviations);
        stringBuilder.append(", bucketCounts=");
        stringBuilder.append(this.bucketCounts);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DistributionData)) {
            return false;
        }
        DistributionData distributionData = (DistributionData) obj;
        if (!(Double.doubleToLongBits(this.mean) == Double.doubleToLongBits(distributionData.getMean()) && this.count == distributionData.getCount() && Double.doubleToLongBits(this.min) == Double.doubleToLongBits(distributionData.getMin()) && Double.doubleToLongBits(this.max) == Double.doubleToLongBits(distributionData.getMax()) && Double.doubleToLongBits(this.sumOfSquaredDeviations) == Double.doubleToLongBits(distributionData.getSumOfSquaredDeviations()) && this.bucketCounts.equals(distributionData.getBucketCounts()))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long doubleToLongBits = (long) (((int) (((long) 1000003) ^ ((Double.doubleToLongBits(this.mean) >>> 32) ^ Double.doubleToLongBits(this.mean)))) * 1000003);
        long j = this.count;
        return this.bucketCounts.hashCode() ^ (((int) (((long) (((int) (((long) (((int) (((long) (((int) (doubleToLongBits ^ (j ^ (j >>> 32)))) * 1000003)) ^ ((Double.doubleToLongBits(this.min) >>> 32) ^ Double.doubleToLongBits(this.min)))) * 1000003)) ^ ((Double.doubleToLongBits(this.max) >>> 32) ^ Double.doubleToLongBits(this.max)))) * 1000003)) ^ ((Double.doubleToLongBits(this.sumOfSquaredDeviations) >>> 32) ^ Double.doubleToLongBits(this.sumOfSquaredDeviations)))) * 1000003);
    }
}
