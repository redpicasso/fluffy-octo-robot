package io.opencensus.trace;

import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_AttributeValue_AttributeValueBoolean extends AttributeValueBoolean {
    private final Boolean booleanValue;

    AutoValue_AttributeValue_AttributeValueBoolean(Boolean bool) {
        if (bool != null) {
            this.booleanValue = bool;
            return;
        }
        throw new NullPointerException("Null booleanValue");
    }

    Boolean getBooleanValue() {
        return this.booleanValue;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AttributeValueBoolean{booleanValue=");
        stringBuilder.append(this.booleanValue);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AttributeValueBoolean)) {
            return false;
        }
        return this.booleanValue.equals(((AttributeValueBoolean) obj).getBooleanValue());
    }

    public int hashCode() {
        return this.booleanValue.hashCode() ^ 1000003;
    }
}
