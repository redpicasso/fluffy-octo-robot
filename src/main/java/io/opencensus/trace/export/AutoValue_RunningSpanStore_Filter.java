package io.opencensus.trace.export;

import io.opencensus.trace.export.RunningSpanStore.Filter;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_RunningSpanStore_Filter extends Filter {
    private final int maxSpansToReturn;
    private final String spanName;

    AutoValue_RunningSpanStore_Filter(String str, int i) {
        if (str != null) {
            this.spanName = str;
            this.maxSpansToReturn = i;
            return;
        }
        throw new NullPointerException("Null spanName");
    }

    public String getSpanName() {
        return this.spanName;
    }

    public int getMaxSpansToReturn() {
        return this.maxSpansToReturn;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Filter{spanName=");
        stringBuilder.append(this.spanName);
        stringBuilder.append(", maxSpansToReturn=");
        stringBuilder.append(this.maxSpansToReturn);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Filter)) {
            return false;
        }
        Filter filter = (Filter) obj;
        if (!(this.spanName.equals(filter.getSpanName()) && this.maxSpansToReturn == filter.getMaxSpansToReturn())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.spanName.hashCode() ^ 1000003) * 1000003) ^ this.maxSpansToReturn;
    }
}
