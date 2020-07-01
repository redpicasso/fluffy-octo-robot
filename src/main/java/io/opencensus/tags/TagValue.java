package io.opencensus.tags;

import com.google.common.base.Preconditions;
import io.opencensus.internal.StringUtil;
import javax.annotation.concurrent.Immutable;

@Immutable
public abstract class TagValue {
    public static final int MAX_LENGTH = 255;

    public abstract String asString();

    TagValue() {
    }

    public static TagValue create(String str) {
        Preconditions.checkArgument(isValid(str));
        return new AutoValue_TagValue(str);
    }

    private static boolean isValid(String str) {
        return str.length() <= 255 && StringUtil.isPrintableString(str);
    }
}
