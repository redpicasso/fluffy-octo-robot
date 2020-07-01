package io.opencensus.trace.samplers;

import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_ProbabilitySampler extends ProbabilitySampler {
    private final long idUpperBound;
    private final double probability;

    AutoValue_ProbabilitySampler(double d, long j) {
        this.probability = d;
        this.idUpperBound = j;
    }

    double getProbability() {
        return this.probability;
    }

    long getIdUpperBound() {
        return this.idUpperBound;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ProbabilitySampler{probability=");
        stringBuilder.append(this.probability);
        stringBuilder.append(", idUpperBound=");
        stringBuilder.append(this.idUpperBound);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ProbabilitySampler)) {
            return false;
        }
        ProbabilitySampler probabilitySampler = (ProbabilitySampler) obj;
        if (!(Double.doubleToLongBits(this.probability) == Double.doubleToLongBits(probabilitySampler.getProbability()) && this.idUpperBound == probabilitySampler.getIdUpperBound())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long doubleToLongBits = (long) (((int) (((long) 1000003) ^ ((Double.doubleToLongBits(this.probability) >>> 32) ^ Double.doubleToLongBits(this.probability)))) * 1000003);
        long j = this.idUpperBound;
        return (int) (doubleToLongBits ^ (j ^ (j >>> 32)));
    }
}
