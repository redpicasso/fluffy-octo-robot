package io.opencensus.stats;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.opencensus.common.Function;
import io.opencensus.internal.StringUtil;
import javax.annotation.concurrent.Immutable;

@Immutable
public abstract class Measure {
    @VisibleForTesting
    static final int NAME_MAX_LENGTH = 255;

    @Immutable
    public static abstract class MeasureDouble extends Measure {
        public abstract String getDescription();

        public abstract String getName();

        public abstract String getUnit();

        MeasureDouble() {
            super();
        }

        public static MeasureDouble create(String str, String str2, String str3) {
            boolean z = StringUtil.isPrintableString(str) && str.length() <= 255;
            Preconditions.checkArgument(z, "Name should be a ASCII string with a length no greater than 255 characters.");
            return new AutoValue_Measure_MeasureDouble(str, str2, str3);
        }

        public <T> T match(Function<? super MeasureDouble, T> function, Function<? super MeasureLong, T> function2, Function<? super Measure, T> function3) {
            return function.apply(this);
        }
    }

    @Immutable
    public static abstract class MeasureLong extends Measure {
        public abstract String getDescription();

        public abstract String getName();

        public abstract String getUnit();

        MeasureLong() {
            super();
        }

        public static MeasureLong create(String str, String str2, String str3) {
            boolean z = StringUtil.isPrintableString(str) && str.length() <= 255;
            Preconditions.checkArgument(z, "Name should be a ASCII string with a length no greater than 255 characters.");
            return new AutoValue_Measure_MeasureLong(str, str2, str3);
        }

        public <T> T match(Function<? super MeasureDouble, T> function, Function<? super MeasureLong, T> function2, Function<? super Measure, T> function3) {
            return function2.apply(this);
        }
    }

    public abstract String getDescription();

    public abstract String getName();

    public abstract String getUnit();

    public abstract <T> T match(Function<? super MeasureDouble, T> function, Function<? super MeasureLong, T> function2, Function<? super Measure, T> function3);

    private Measure() {
    }
}
