package io.opencensus.stats;

import io.opencensus.stats.Aggregation.Distribution;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_Aggregation_Distribution extends Distribution {
    private final BucketBoundaries bucketBoundaries;

    AutoValue_Aggregation_Distribution(BucketBoundaries bucketBoundaries) {
        if (bucketBoundaries != null) {
            this.bucketBoundaries = bucketBoundaries;
            return;
        }
        throw new NullPointerException("Null bucketBoundaries");
    }

    public BucketBoundaries getBucketBoundaries() {
        return this.bucketBoundaries;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Distribution{bucketBoundaries=");
        stringBuilder.append(this.bucketBoundaries);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Distribution)) {
            return false;
        }
        return this.bucketBoundaries.equals(((Distribution) obj).getBucketBoundaries());
    }

    public int hashCode() {
        return this.bucketBoundaries.hashCode() ^ 1000003;
    }
}
