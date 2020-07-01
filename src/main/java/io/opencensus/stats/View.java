package io.opencensus.stats;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.opencensus.common.Duration;
import io.opencensus.common.Function;
import io.opencensus.internal.StringUtil;
import io.opencensus.tags.TagKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.annotation.concurrent.Immutable;

@Immutable
public abstract class View {
    @VisibleForTesting
    static final int NAME_MAX_LENGTH = 255;

    @Immutable
    public static abstract class AggregationWindow {

        @Immutable
        public static abstract class Cumulative extends AggregationWindow {
            private static final Cumulative CUMULATIVE = new AutoValue_View_AggregationWindow_Cumulative();

            Cumulative() {
                super();
            }

            public static Cumulative create() {
                return CUMULATIVE;
            }

            public final <T> T match(Function<? super Cumulative, T> function, Function<? super Interval, T> function2, Function<? super AggregationWindow, T> function3) {
                return function.apply(this);
            }
        }

        @Immutable
        public static abstract class Interval extends AggregationWindow {
            private static final Duration ZERO = Duration.create(0, 0);

            public abstract Duration getDuration();

            Interval() {
                super();
            }

            public static Interval create(Duration duration) {
                Preconditions.checkArgument(duration.compareTo(ZERO) > 0, "Duration must be positive");
                return new AutoValue_View_AggregationWindow_Interval(duration);
            }

            public final <T> T match(Function<? super Cumulative, T> function, Function<? super Interval, T> function2, Function<? super AggregationWindow, T> function3) {
                return function2.apply(this);
            }
        }

        public abstract <T> T match(Function<? super Cumulative, T> function, Function<? super Interval, T> function2, Function<? super AggregationWindow, T> function3);

        private AggregationWindow() {
        }
    }

    @Immutable
    public static abstract class Name {
        public abstract String asString();

        Name() {
        }

        public static Name create(String str) {
            boolean z = StringUtil.isPrintableString(str) && str.length() <= 255;
            Preconditions.checkArgument(z, "Name should be a ASCII string with a length no greater than 255 characters.");
            return new AutoValue_View_Name(str);
        }
    }

    public abstract Aggregation getAggregation();

    public abstract List<TagKey> getColumns();

    public abstract String getDescription();

    public abstract Measure getMeasure();

    public abstract Name getName();

    public abstract AggregationWindow getWindow();

    View() {
    }

    public static View create(Name name, String str, Measure measure, Aggregation aggregation, List<TagKey> list, AggregationWindow aggregationWindow) {
        Preconditions.checkArgument(new HashSet(list).size() == list.size(), "Columns have duplicate.");
        return new AutoValue_View(name, str, measure, aggregation, Collections.unmodifiableList(new ArrayList(list)), aggregationWindow);
    }
}
