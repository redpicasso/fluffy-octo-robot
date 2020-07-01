package io.opencensus.tags;

import javax.annotation.concurrent.Immutable;

@Immutable
public abstract class Tag {
    public abstract TagKey getKey();

    public abstract TagValue getValue();

    Tag() {
    }

    public static Tag create(TagKey tagKey, TagValue tagValue) {
        return new AutoValue_Tag(tagKey, tagValue);
    }
}
