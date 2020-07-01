package io.opencensus.common;

import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_Timestamp extends Timestamp {
    private final int nanos;
    private final long seconds;

    AutoValue_Timestamp(long j, int i) {
        this.seconds = j;
        this.nanos = i;
    }

    public long getSeconds() {
        return this.seconds;
    }

    public int getNanos() {
        return this.nanos;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Timestamp{seconds=");
        stringBuilder.append(this.seconds);
        stringBuilder.append(", nanos=");
        stringBuilder.append(this.nanos);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Timestamp)) {
            return false;
        }
        Timestamp timestamp = (Timestamp) obj;
        if (!(this.seconds == timestamp.getSeconds() && this.nanos == timestamp.getNanos())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long j = (long) 1000003;
        long j2 = this.seconds;
        return this.nanos ^ (((int) (j ^ (j2 ^ (j2 >>> 32)))) * 1000003);
    }
}
