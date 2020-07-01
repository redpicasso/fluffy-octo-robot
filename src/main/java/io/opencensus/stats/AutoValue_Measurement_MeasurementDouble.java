package io.opencensus.stats;

import io.opencensus.stats.Measure.MeasureDouble;
import io.opencensus.stats.Measurement.MeasurementDouble;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_Measurement_MeasurementDouble extends MeasurementDouble {
    private final MeasureDouble measure;
    private final double value;

    AutoValue_Measurement_MeasurementDouble(MeasureDouble measureDouble, double d) {
        if (measureDouble != null) {
            this.measure = measureDouble;
            this.value = d;
            return;
        }
        throw new NullPointerException("Null measure");
    }

    public MeasureDouble getMeasure() {
        return this.measure;
    }

    public double getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MeasurementDouble{measure=");
        stringBuilder.append(this.measure);
        stringBuilder.append(", value=");
        stringBuilder.append(this.value);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MeasurementDouble)) {
            return false;
        }
        MeasurementDouble measurementDouble = (MeasurementDouble) obj;
        if (!(this.measure.equals(measurementDouble.getMeasure()) && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(measurementDouble.getValue()))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (int) (((long) ((this.measure.hashCode() ^ 1000003) * 1000003)) ^ ((Double.doubleToLongBits(this.value) >>> 32) ^ Double.doubleToLongBits(this.value)));
    }
}
