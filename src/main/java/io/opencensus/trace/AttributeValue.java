package io.opencensus.trace;

import com.google.common.base.Preconditions;
import io.opencensus.common.Function;
import javax.annotation.concurrent.Immutable;

@Immutable
public abstract class AttributeValue {

    @Immutable
    static abstract class AttributeValueBoolean extends AttributeValue {
        abstract Boolean getBooleanValue();

        AttributeValueBoolean() {
        }

        static AttributeValue create(Boolean bool) {
            return new AutoValue_AttributeValue_AttributeValueBoolean((Boolean) Preconditions.checkNotNull(bool, "stringValue"));
        }

        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<Object, T> function4) {
            return function2.apply(getBooleanValue());
        }
    }

    @Immutable
    static abstract class AttributeValueLong extends AttributeValue {
        abstract Long getLongValue();

        AttributeValueLong() {
        }

        static AttributeValue create(Long l) {
            return new AutoValue_AttributeValue_AttributeValueLong((Long) Preconditions.checkNotNull(l, "stringValue"));
        }

        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<Object, T> function4) {
            return function3.apply(getLongValue());
        }
    }

    @Immutable
    static abstract class AttributeValueString extends AttributeValue {
        abstract String getStringValue();

        AttributeValueString() {
        }

        static AttributeValue create(String str) {
            return new AutoValue_AttributeValue_AttributeValueString((String) Preconditions.checkNotNull(str, "stringValue"));
        }

        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<Object, T> function4) {
            return function.apply(getStringValue());
        }
    }

    public abstract <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<Object, T> function4);

    public static AttributeValue stringAttributeValue(String str) {
        return AttributeValueString.create(str);
    }

    public static AttributeValue booleanAttributeValue(boolean z) {
        return AttributeValueBoolean.create(Boolean.valueOf(z));
    }

    public static AttributeValue longAttributeValue(long j) {
        return AttributeValueLong.create(Long.valueOf(j));
    }

    AttributeValue() {
    }
}
