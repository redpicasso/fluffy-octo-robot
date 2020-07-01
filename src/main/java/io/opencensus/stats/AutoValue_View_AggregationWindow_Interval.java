package io.opencensus.stats;

import io.opencensus.common.Duration;
import io.opencensus.stats.View.AggregationWindow.Interval;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_View_AggregationWindow_Interval extends Interval {
    private final Duration duration;

    AutoValue_View_AggregationWindow_Interval(Duration duration) {
        if (duration != null) {
            this.duration = duration;
            return;
        }
        throw new NullPointerException("Null duration");
    }

    public Duration getDuration() {
        return this.duration;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Interval{duration=");
        stringBuilder.append(this.duration);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Interval)) {
            return false;
        }
        return this.duration.equals(((Interval) obj).getDuration());
    }

    public int hashCode() {
        return this.duration.hashCode() ^ 1000003;
    }
}
