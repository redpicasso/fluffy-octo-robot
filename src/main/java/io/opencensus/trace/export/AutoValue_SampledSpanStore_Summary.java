package io.opencensus.trace.export;

import io.opencensus.trace.export.SampledSpanStore.PerSpanNameSummary;
import io.opencensus.trace.export.SampledSpanStore.Summary;
import java.util.Map;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_SampledSpanStore_Summary extends Summary {
    private final Map<String, PerSpanNameSummary> perSpanNameSummary;

    AutoValue_SampledSpanStore_Summary(Map<String, PerSpanNameSummary> map) {
        if (map != null) {
            this.perSpanNameSummary = map;
            return;
        }
        throw new NullPointerException("Null perSpanNameSummary");
    }

    public Map<String, PerSpanNameSummary> getPerSpanNameSummary() {
        return this.perSpanNameSummary;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Summary{perSpanNameSummary=");
        stringBuilder.append(this.perSpanNameSummary);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Summary)) {
            return false;
        }
        return this.perSpanNameSummary.equals(((Summary) obj).getPerSpanNameSummary());
    }

    public int hashCode() {
        return this.perSpanNameSummary.hashCode() ^ 1000003;
    }
}
