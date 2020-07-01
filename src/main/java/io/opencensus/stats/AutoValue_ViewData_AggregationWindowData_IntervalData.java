package io.opencensus.stats;

import io.opencensus.common.Timestamp;
import io.opencensus.stats.ViewData.AggregationWindowData.IntervalData;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_ViewData_AggregationWindowData_IntervalData extends IntervalData {
    private final Timestamp end;

    AutoValue_ViewData_AggregationWindowData_IntervalData(Timestamp timestamp) {
        if (timestamp != null) {
            this.end = timestamp;
            return;
        }
        throw new NullPointerException("Null end");
    }

    public Timestamp getEnd() {
        return this.end;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IntervalData{end=");
        stringBuilder.append(this.end);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof IntervalData)) {
            return false;
        }
        return this.end.equals(((IntervalData) obj).getEnd());
    }

    public int hashCode() {
        return this.end.hashCode() ^ 1000003;
    }
}
