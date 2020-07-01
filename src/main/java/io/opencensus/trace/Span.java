package io.opencensus.trace;

import com.google.common.base.Preconditions;
import io.opencensus.internal.BaseMessageEventUtil;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class Span {
    private static final Set<Options> DEFAULT_OPTIONS = Collections.unmodifiableSet(EnumSet.noneOf(Options.class));
    private static final Map<String, AttributeValue> EMPTY_ATTRIBUTES = Collections.emptyMap();
    private final SpanContext context;
    private final Set<Options> options;

    public enum Options {
        RECORD_EVENTS
    }

    public abstract void addAnnotation(Annotation annotation);

    public abstract void addAnnotation(String str, Map<String, AttributeValue> map);

    public abstract void addLink(Link link);

    public abstract void end(EndSpanOptions endSpanOptions);

    public void setStatus(Status status) {
    }

    protected Span(SpanContext spanContext, @Nullable EnumSet<Options> enumSet) {
        Set set;
        this.context = (SpanContext) Preconditions.checkNotNull(spanContext, "context");
        if (enumSet == null) {
            set = DEFAULT_OPTIONS;
        } else {
            set = Collections.unmodifiableSet(EnumSet.copyOf(enumSet));
        }
        this.options = set;
        boolean z = !spanContext.getTraceOptions().isSampled() || this.options.contains(Options.RECORD_EVENTS);
        Preconditions.checkArgument(z, "Span is sampled, but does not have RECORD_EVENTS set.");
    }

    public void putAttribute(String str, AttributeValue attributeValue) {
        putAttributes(Collections.singletonMap(str, attributeValue));
    }

    public void putAttributes(Map<String, AttributeValue> map) {
        addAttributes(map);
    }

    @Deprecated
    public void addAttributes(Map<String, AttributeValue> map) {
        putAttributes(map);
    }

    public final void addAnnotation(String str) {
        addAnnotation(str, EMPTY_ATTRIBUTES);
    }

    @Deprecated
    public void addNetworkEvent(NetworkEvent networkEvent) {
        addMessageEvent(BaseMessageEventUtil.asMessageEvent(networkEvent));
    }

    public void addMessageEvent(MessageEvent messageEvent) {
        addNetworkEvent(BaseMessageEventUtil.asNetworkEvent(messageEvent));
    }

    public final void end() {
        end(EndSpanOptions.DEFAULT);
    }

    public final SpanContext getContext() {
        return this.context;
    }

    public final Set<Options> getOptions() {
        return this.options;
    }
}
