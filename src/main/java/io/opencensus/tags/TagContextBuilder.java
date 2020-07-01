package io.opencensus.tags;

import io.opencensus.common.Scope;

public abstract class TagContextBuilder {
    public abstract TagContext build();

    public abstract Scope buildScoped();

    public abstract TagContextBuilder put(TagKey tagKey, TagValue tagValue);

    public abstract TagContextBuilder remove(TagKey tagKey);
}
