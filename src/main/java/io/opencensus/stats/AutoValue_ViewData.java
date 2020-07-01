package io.opencensus.stats;

import io.opencensus.stats.ViewData.AggregationWindowData;
import io.opencensus.tags.TagValue;
import java.util.List;
import java.util.Map;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_ViewData extends ViewData {
    private final Map<List<TagValue>, AggregationData> aggregationMap;
    private final View view;
    private final AggregationWindowData windowData;

    AutoValue_ViewData(View view, Map<List<TagValue>, AggregationData> map, AggregationWindowData aggregationWindowData) {
        if (view != null) {
            this.view = view;
            if (map != null) {
                this.aggregationMap = map;
                if (aggregationWindowData != null) {
                    this.windowData = aggregationWindowData;
                    return;
                }
                throw new NullPointerException("Null windowData");
            }
            throw new NullPointerException("Null aggregationMap");
        }
        throw new NullPointerException("Null view");
    }

    public View getView() {
        return this.view;
    }

    public Map<List<TagValue>, AggregationData> getAggregationMap() {
        return this.aggregationMap;
    }

    public AggregationWindowData getWindowData() {
        return this.windowData;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ViewData{view=");
        stringBuilder.append(this.view);
        stringBuilder.append(", aggregationMap=");
        stringBuilder.append(this.aggregationMap);
        stringBuilder.append(", windowData=");
        stringBuilder.append(this.windowData);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ViewData)) {
            return false;
        }
        ViewData viewData = (ViewData) obj;
        if (!(this.view.equals(viewData.getView()) && this.aggregationMap.equals(viewData.getAggregationMap()) && this.windowData.equals(viewData.getWindowData()))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((((this.view.hashCode() ^ 1000003) * 1000003) ^ this.aggregationMap.hashCode()) * 1000003) ^ this.windowData.hashCode();
    }
}
