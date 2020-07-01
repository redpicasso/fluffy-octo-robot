package io.opencensus.trace.propagation;

import com.google.common.base.Preconditions;
import io.opencensus.trace.SpanContext;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public abstract class TextFormat {
    private static final NoopTextFormat NOOP_TEXT_FORMAT = new NoopTextFormat();

    public static abstract class Getter<C> {
        @Nullable
        public abstract String get(C c, String str);
    }

    public static abstract class Setter<C> {
        public abstract void put(C c, String str, String str2);
    }

    private static final class NoopTextFormat extends TextFormat {
        private NoopTextFormat() {
        }

        public List<String> fields() {
            return Collections.emptyList();
        }

        public <C> void inject(SpanContext spanContext, C c, Setter<C> setter) {
            Preconditions.checkNotNull(spanContext, "spanContext");
            Preconditions.checkNotNull(c, "carrier");
            Preconditions.checkNotNull(setter, "setter");
        }

        public <C> SpanContext extract(C c, Getter<C> getter) {
            Preconditions.checkNotNull(c, "carrier");
            Preconditions.checkNotNull(getter, "getter");
            return SpanContext.INVALID;
        }
    }

    public abstract <C> SpanContext extract(C c, Getter<C> getter) throws SpanContextParseException;

    public abstract List<String> fields();

    public abstract <C> void inject(SpanContext spanContext, C c, Setter<C> setter);

    static TextFormat getNoopTextFormat() {
        return NOOP_TEXT_FORMAT;
    }
}
