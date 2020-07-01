package io.opencensus.tags;

import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_Tag extends Tag {
    private final TagKey key;
    private final TagValue value;

    AutoValue_Tag(TagKey tagKey, TagValue tagValue) {
        if (tagKey != null) {
            this.key = tagKey;
            if (tagValue != null) {
                this.value = tagValue;
                return;
            }
            throw new NullPointerException("Null value");
        }
        throw new NullPointerException("Null key");
    }

    public TagKey getKey() {
        return this.key;
    }

    public TagValue getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tag{key=");
        stringBuilder.append(this.key);
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
        if (!(obj instanceof Tag)) {
            return false;
        }
        Tag tag = (Tag) obj;
        if (!(this.key.equals(tag.getKey()) && this.value.equals(tag.getValue()))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.key.hashCode() ^ 1000003) * 1000003) ^ this.value.hashCode();
    }
}
