package io.opencensus.trace.export;

import io.opencensus.trace.Link;
import io.opencensus.trace.export.SpanData.Links;
import java.util.List;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_SpanData_Links extends Links {
    private final int droppedLinksCount;
    private final List<Link> links;

    AutoValue_SpanData_Links(List<Link> list, int i) {
        if (list != null) {
            this.links = list;
            this.droppedLinksCount = i;
            return;
        }
        throw new NullPointerException("Null links");
    }

    public List<Link> getLinks() {
        return this.links;
    }

    public int getDroppedLinksCount() {
        return this.droppedLinksCount;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Links{links=");
        stringBuilder.append(this.links);
        stringBuilder.append(", droppedLinksCount=");
        stringBuilder.append(this.droppedLinksCount);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Links)) {
            return false;
        }
        Links links = (Links) obj;
        if (!(this.links.equals(links.getLinks()) && this.droppedLinksCount == links.getDroppedLinksCount())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.links.hashCode() ^ 1000003) * 1000003) ^ this.droppedLinksCount;
    }
}
