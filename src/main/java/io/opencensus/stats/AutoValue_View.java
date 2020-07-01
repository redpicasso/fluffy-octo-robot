package io.opencensus.stats;

import io.opencensus.stats.View.AggregationWindow;
import io.opencensus.stats.View.Name;
import io.opencensus.tags.TagKey;
import java.util.List;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_View extends View {
    private final Aggregation aggregation;
    private final List<TagKey> columns;
    private final String description;
    private final Measure measure;
    private final Name name;
    private final AggregationWindow window;

    AutoValue_View(Name name, String str, Measure measure, Aggregation aggregation, List<TagKey> list, AggregationWindow aggregationWindow) {
        if (name != null) {
            this.name = name;
            if (str != null) {
                this.description = str;
                if (measure != null) {
                    this.measure = measure;
                    if (aggregation != null) {
                        this.aggregation = aggregation;
                        if (list != null) {
                            this.columns = list;
                            if (aggregationWindow != null) {
                                this.window = aggregationWindow;
                                return;
                            }
                            throw new NullPointerException("Null window");
                        }
                        throw new NullPointerException("Null columns");
                    }
                    throw new NullPointerException("Null aggregation");
                }
                throw new NullPointerException("Null measure");
            }
            throw new NullPointerException("Null description");
        }
        throw new NullPointerException("Null name");
    }

    public Name getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Measure getMeasure() {
        return this.measure;
    }

    public Aggregation getAggregation() {
        return this.aggregation;
    }

    public List<TagKey> getColumns() {
        return this.columns;
    }

    public AggregationWindow getWindow() {
        return this.window;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("View{name=");
        stringBuilder.append(this.name);
        stringBuilder.append(", description=");
        stringBuilder.append(this.description);
        stringBuilder.append(", measure=");
        stringBuilder.append(this.measure);
        stringBuilder.append(", aggregation=");
        stringBuilder.append(this.aggregation);
        stringBuilder.append(", columns=");
        stringBuilder.append(this.columns);
        stringBuilder.append(", window=");
        stringBuilder.append(this.window);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof View)) {
            return false;
        }
        View view = (View) obj;
        if (!(this.name.equals(view.getName()) && this.description.equals(view.getDescription()) && this.measure.equals(view.getMeasure()) && this.aggregation.equals(view.getAggregation()) && this.columns.equals(view.getColumns()) && this.window.equals(view.getWindow()))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((((((((((this.name.hashCode() ^ 1000003) * 1000003) ^ this.description.hashCode()) * 1000003) ^ this.measure.hashCode()) * 1000003) ^ this.aggregation.hashCode()) * 1000003) ^ this.columns.hashCode()) * 1000003) ^ this.window.hashCode();
    }
}
