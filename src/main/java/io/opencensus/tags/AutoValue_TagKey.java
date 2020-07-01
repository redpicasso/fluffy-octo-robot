package io.opencensus.tags;

import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_TagKey extends TagKey {
    private final String name;

    AutoValue_TagKey(String str) {
        if (str != null) {
            this.name = str;
            return;
        }
        throw new NullPointerException("Null name");
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TagKey{name=");
        stringBuilder.append(this.name);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TagKey)) {
            return false;
        }
        return this.name.equals(((TagKey) obj).getName());
    }

    public int hashCode() {
        return this.name.hashCode() ^ 1000003;
    }
}
