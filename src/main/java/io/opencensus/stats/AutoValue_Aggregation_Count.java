package io.opencensus.stats;

import io.opencensus.stats.Aggregation.Count;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_Aggregation_Count extends Count {
    public int hashCode() {
        return 1;
    }

    public String toString() {
        return "Count{}";
    }

    AutoValue_Aggregation_Count() {
    }

    public boolean equals(Object obj) {
        return obj == this || (obj instanceof Count);
    }
}
