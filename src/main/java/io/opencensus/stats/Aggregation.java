package io.opencensus.stats;

import com.google.common.base.Preconditions;
import io.opencensus.common.Function;
import javax.annotation.concurrent.Immutable;

@Immutable
public abstract class Aggregation {

    @Immutable
    public static abstract class Count extends Aggregation {
        private static final Count INSTANCE = new AutoValue_Aggregation_Count();

        Count() {
            super();
        }

        public static Count create() {
            return INSTANCE;
        }

        public final <T> T match(Function<? super Sum, T> function, Function<? super Count, T> function2, Function<? super Mean, T> function3, Function<? super Distribution, T> function4, Function<? super Aggregation, T> function5) {
            return function2.apply(this);
        }
    }

    @Immutable
    public static abstract class Distribution extends Aggregation {
        public abstract BucketBoundaries getBucketBoundaries();

        Distribution() {
            super();
        }

        public static Distribution create(BucketBoundaries bucketBoundaries) {
            Preconditions.checkNotNull(bucketBoundaries, "bucketBoundaries should not be null.");
            return new AutoValue_Aggregation_Distribution(bucketBoundaries);
        }

        public final <T> T match(Function<? super Sum, T> function, Function<? super Count, T> function2, Function<? super Mean, T> function3, Function<? super Distribution, T> function4, Function<? super Aggregation, T> function5) {
            return function4.apply(this);
        }
    }

    @Immutable
    public static abstract class Mean extends Aggregation {
        private static final Mean INSTANCE = new AutoValue_Aggregation_Mean();

        Mean() {
            super();
        }

        public static Mean create() {
            return INSTANCE;
        }

        public final <T> T match(Function<? super Sum, T> function, Function<? super Count, T> function2, Function<? super Mean, T> function3, Function<? super Distribution, T> function4, Function<? super Aggregation, T> function5) {
            return function3.apply(this);
        }
    }

    @Immutable
    public static abstract class Sum extends Aggregation {
        private static final Sum INSTANCE = new AutoValue_Aggregation_Sum();

        Sum() {
            super();
        }

        public static Sum create() {
            return INSTANCE;
        }

        public final <T> T match(Function<? super Sum, T> function, Function<? super Count, T> function2, Function<? super Mean, T> function3, Function<? super Distribution, T> function4, Function<? super Aggregation, T> function5) {
            return function.apply(this);
        }
    }

    public abstract <T> T match(Function<? super Sum, T> function, Function<? super Count, T> function2, Function<? super Mean, T> function3, Function<? super Distribution, T> function4, Function<? super Aggregation, T> function5);

    private Aggregation() {
    }
}
