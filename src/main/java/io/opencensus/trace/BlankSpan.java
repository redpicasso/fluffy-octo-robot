package io.opencensus.trace;

import java.util.Map;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class BlankSpan extends Span {
    public static final BlankSpan INSTANCE = new BlankSpan();

    public void addAnnotation(Annotation annotation) {
    }

    public void addAnnotation(String str, Map<String, AttributeValue> map) {
    }

    public void addLink(Link link) {
    }

    public void addMessageEvent(MessageEvent messageEvent) {
    }

    @Deprecated
    public void addNetworkEvent(NetworkEvent networkEvent) {
    }

    public void end(EndSpanOptions endSpanOptions) {
    }

    public void putAttribute(String str, AttributeValue attributeValue) {
    }

    public void putAttributes(Map<String, AttributeValue> map) {
    }

    public void setStatus(Status status) {
    }

    public String toString() {
        return "BlankSpan";
    }

    private BlankSpan() {
        super(SpanContext.INVALID, null);
    }
}
