package io.opencensus.trace.config;

import io.opencensus.trace.Sampler;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_TraceParams extends TraceParams {
    private final int maxNumberOfAnnotations;
    private final int maxNumberOfAttributes;
    private final int maxNumberOfLinks;
    private final int maxNumberOfMessageEvents;
    private final Sampler sampler;

    static final class Builder extends io.opencensus.trace.config.TraceParams.Builder {
        private Integer maxNumberOfAnnotations;
        private Integer maxNumberOfAttributes;
        private Integer maxNumberOfLinks;
        private Integer maxNumberOfMessageEvents;
        private Sampler sampler;

        Builder() {
        }

        private Builder(TraceParams traceParams) {
            this.sampler = traceParams.getSampler();
            this.maxNumberOfAttributes = Integer.valueOf(traceParams.getMaxNumberOfAttributes());
            this.maxNumberOfAnnotations = Integer.valueOf(traceParams.getMaxNumberOfAnnotations());
            this.maxNumberOfMessageEvents = Integer.valueOf(traceParams.getMaxNumberOfMessageEvents());
            this.maxNumberOfLinks = Integer.valueOf(traceParams.getMaxNumberOfLinks());
        }

        public io.opencensus.trace.config.TraceParams.Builder setSampler(Sampler sampler) {
            if (sampler != null) {
                this.sampler = sampler;
                return this;
            }
            throw new NullPointerException("Null sampler");
        }

        public io.opencensus.trace.config.TraceParams.Builder setMaxNumberOfAttributes(int i) {
            this.maxNumberOfAttributes = Integer.valueOf(i);
            return this;
        }

        public io.opencensus.trace.config.TraceParams.Builder setMaxNumberOfAnnotations(int i) {
            this.maxNumberOfAnnotations = Integer.valueOf(i);
            return this;
        }

        public io.opencensus.trace.config.TraceParams.Builder setMaxNumberOfMessageEvents(int i) {
            this.maxNumberOfMessageEvents = Integer.valueOf(i);
            return this;
        }

        public io.opencensus.trace.config.TraceParams.Builder setMaxNumberOfLinks(int i) {
            this.maxNumberOfLinks = Integer.valueOf(i);
            return this;
        }

        TraceParams autoBuild() {
            StringBuilder stringBuilder;
            String str = "";
            if (this.sampler == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(" sampler");
                str = stringBuilder.toString();
            }
            if (this.maxNumberOfAttributes == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(" maxNumberOfAttributes");
                str = stringBuilder.toString();
            }
            if (this.maxNumberOfAnnotations == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(" maxNumberOfAnnotations");
                str = stringBuilder.toString();
            }
            if (this.maxNumberOfMessageEvents == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(" maxNumberOfMessageEvents");
                str = stringBuilder.toString();
            }
            if (this.maxNumberOfLinks == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(" maxNumberOfLinks");
                str = stringBuilder.toString();
            }
            if (str.isEmpty()) {
                return new AutoValue_TraceParams(this.sampler, this.maxNumberOfAttributes.intValue(), this.maxNumberOfAnnotations.intValue(), this.maxNumberOfMessageEvents.intValue(), this.maxNumberOfLinks.intValue());
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Missing required properties:");
            stringBuilder2.append(str);
            throw new IllegalStateException(stringBuilder2.toString());
        }
    }

    private AutoValue_TraceParams(Sampler sampler, int i, int i2, int i3, int i4) {
        this.sampler = sampler;
        this.maxNumberOfAttributes = i;
        this.maxNumberOfAnnotations = i2;
        this.maxNumberOfMessageEvents = i3;
        this.maxNumberOfLinks = i4;
    }

    public Sampler getSampler() {
        return this.sampler;
    }

    public int getMaxNumberOfAttributes() {
        return this.maxNumberOfAttributes;
    }

    public int getMaxNumberOfAnnotations() {
        return this.maxNumberOfAnnotations;
    }

    public int getMaxNumberOfMessageEvents() {
        return this.maxNumberOfMessageEvents;
    }

    public int getMaxNumberOfLinks() {
        return this.maxNumberOfLinks;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TraceParams{sampler=");
        stringBuilder.append(this.sampler);
        stringBuilder.append(", maxNumberOfAttributes=");
        stringBuilder.append(this.maxNumberOfAttributes);
        stringBuilder.append(", maxNumberOfAnnotations=");
        stringBuilder.append(this.maxNumberOfAnnotations);
        stringBuilder.append(", maxNumberOfMessageEvents=");
        stringBuilder.append(this.maxNumberOfMessageEvents);
        stringBuilder.append(", maxNumberOfLinks=");
        stringBuilder.append(this.maxNumberOfLinks);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TraceParams)) {
            return false;
        }
        TraceParams traceParams = (TraceParams) obj;
        if (!(this.sampler.equals(traceParams.getSampler()) && this.maxNumberOfAttributes == traceParams.getMaxNumberOfAttributes() && this.maxNumberOfAnnotations == traceParams.getMaxNumberOfAnnotations() && this.maxNumberOfMessageEvents == traceParams.getMaxNumberOfMessageEvents() && this.maxNumberOfLinks == traceParams.getMaxNumberOfLinks())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((((((((this.sampler.hashCode() ^ 1000003) * 1000003) ^ this.maxNumberOfAttributes) * 1000003) ^ this.maxNumberOfAnnotations) * 1000003) ^ this.maxNumberOfMessageEvents) * 1000003) ^ this.maxNumberOfLinks;
    }

    public io.opencensus.trace.config.TraceParams.Builder toBuilder() {
        return new Builder(this);
    }
}
