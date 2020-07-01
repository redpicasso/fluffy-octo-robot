package io.opencensus.trace;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_EndSpanOptions extends EndSpanOptions {
    private final boolean sampleToLocalSpanStore;
    private final Status status;

    static final class Builder extends io.opencensus.trace.EndSpanOptions.Builder {
        private Boolean sampleToLocalSpanStore;
        private Status status;

        Builder() {
        }

        public io.opencensus.trace.EndSpanOptions.Builder setSampleToLocalSpanStore(boolean z) {
            this.sampleToLocalSpanStore = Boolean.valueOf(z);
            return this;
        }

        public io.opencensus.trace.EndSpanOptions.Builder setStatus(@Nullable Status status) {
            this.status = status;
            return this;
        }

        public EndSpanOptions build() {
            String str = "";
            if (this.sampleToLocalSpanStore == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(" sampleToLocalSpanStore");
                str = stringBuilder.toString();
            }
            if (str.isEmpty()) {
                return new AutoValue_EndSpanOptions(this.sampleToLocalSpanStore.booleanValue(), this.status);
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Missing required properties:");
            stringBuilder2.append(str);
            throw new IllegalStateException(stringBuilder2.toString());
        }
    }

    private AutoValue_EndSpanOptions(boolean z, @Nullable Status status) {
        this.sampleToLocalSpanStore = z;
        this.status = status;
    }

    public boolean getSampleToLocalSpanStore() {
        return this.sampleToLocalSpanStore;
    }

    @Nullable
    public Status getStatus() {
        return this.status;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("EndSpanOptions{sampleToLocalSpanStore=");
        stringBuilder.append(this.sampleToLocalSpanStore);
        stringBuilder.append(", status=");
        stringBuilder.append(this.status);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof EndSpanOptions)) {
            return false;
        }
        EndSpanOptions endSpanOptions = (EndSpanOptions) obj;
        if (this.sampleToLocalSpanStore == endSpanOptions.getSampleToLocalSpanStore()) {
            Status status = this.status;
            if (status != null) {
            }
        }
        z = false;
        return z;
    }

    public int hashCode() {
        int i = ((this.sampleToLocalSpanStore ? 1231 : 1237) ^ 1000003) * 1000003;
        Status status = this.status;
        return i ^ (status == null ? 0 : status.hashCode());
    }
}
