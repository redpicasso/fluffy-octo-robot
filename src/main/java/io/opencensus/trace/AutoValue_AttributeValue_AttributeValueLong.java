package io.opencensus.trace;

import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_AttributeValue_AttributeValueLong extends AttributeValueLong {
    private final Long longValue;

    AutoValue_AttributeValue_AttributeValueLong(Long l) {
        if (l != null) {
            this.longValue = l;
            return;
        }
        throw new NullPointerException("Null longValue");
    }

    Long getLongValue() {
        return this.longValue;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AttributeValueLong{longValue=");
        stringBuilder.append(this.longValue);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AttributeValueLong)) {
            return false;
        }
        return this.longValue.equals(((AttributeValueLong) obj).getLongValue());
    }

    public int hashCode() {
        return this.longValue.hashCode() ^ 1000003;
    }
}
