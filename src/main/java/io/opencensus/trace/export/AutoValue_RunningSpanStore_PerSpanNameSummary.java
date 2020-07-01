package io.opencensus.trace.export;

import io.opencensus.trace.export.RunningSpanStore.PerSpanNameSummary;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_RunningSpanStore_PerSpanNameSummary extends PerSpanNameSummary {
    private final int numRunningSpans;

    AutoValue_RunningSpanStore_PerSpanNameSummary(int i) {
        this.numRunningSpans = i;
    }

    public int getNumRunningSpans() {
        return this.numRunningSpans;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PerSpanNameSummary{numRunningSpans=");
        stringBuilder.append(this.numRunningSpans);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PerSpanNameSummary)) {
            return false;
        }
        if (this.numRunningSpans != ((PerSpanNameSummary) obj).getNumRunningSpans()) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return this.numRunningSpans ^ 1000003;
    }
}
