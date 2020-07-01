package io.opencensus.stats;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.opencensus.common.Function;
import java.util.Collections;
import java.util.List;
import javax.annotation.concurrent.Immutable;

@Immutable
public abstract class AggregationData {

    @Immutable
    public static abstract class CountData extends AggregationData {
        public abstract long getCount();

        CountData() {
            super();
        }

        public static CountData create(long j) {
            return new AutoValue_AggregationData_CountData(j);
        }

        public final <T> T match(Function<? super SumDataDouble, T> function, Function<? super SumDataLong, T> function2, Function<? super CountData, T> function3, Function<? super MeanData, T> function4, Function<? super DistributionData, T> function5, Function<? super AggregationData, T> function6) {
            return function3.apply(this);
        }
    }

    @Immutable
    public static abstract class DistributionData extends AggregationData {
        public abstract List<Long> getBucketCounts();

        public abstract long getCount();

        public abstract double getMax();

        public abstract double getMean();

        public abstract double getMin();

        public abstract double getSumOfSquaredDeviations();

        DistributionData() {
            super();
        }

        public static DistributionData create(double d, long j, double d2, double d3, double d4, List<Long> list) {
            if (!(d2 == Double.POSITIVE_INFINITY && d3 == Double.NEGATIVE_INFINITY)) {
                Preconditions.checkArgument(d2 <= d3, "max should be greater or equal to min.");
            }
            Preconditions.checkNotNull(list, "bucket counts should not be null.");
            List<Long> unmodifiableList = Collections.unmodifiableList(Lists.newArrayList((Iterable) list));
            for (Long checkNotNull : unmodifiableList) {
                Preconditions.checkNotNull(checkNotNull, "bucket should not be null.");
            }
            return new AutoValue_AggregationData_DistributionData(d, j, d2, d3, d4, unmodifiableList);
        }

        public final <T> T match(Function<? super SumDataDouble, T> function, Function<? super SumDataLong, T> function2, Function<? super CountData, T> function3, Function<? super MeanData, T> function4, Function<? super DistributionData, T> function5, Function<? super AggregationData, T> function6) {
            return function5.apply(this);
        }
    }

    @Immutable
    public static abstract class MeanData extends AggregationData {
        public abstract long getCount();

        public abstract double getMean();

        MeanData() {
            super();
        }

        public static MeanData create(double d, long j) {
            return new AutoValue_AggregationData_MeanData(d, j);
        }

        public final <T> T match(Function<? super SumDataDouble, T> function, Function<? super SumDataLong, T> function2, Function<? super CountData, T> function3, Function<? super MeanData, T> function4, Function<? super DistributionData, T> function5, Function<? super AggregationData, T> function6) {
            return function4.apply(this);
        }
    }

    @Immutable
    public static abstract class SumDataDouble extends AggregationData {
        public abstract double getSum();

        SumDataDouble() {
            super();
        }

        public static SumDataDouble create(double d) {
            return new AutoValue_AggregationData_SumDataDouble(d);
        }

        public final <T> T match(Function<? super SumDataDouble, T> function, Function<? super SumDataLong, T> function2, Function<? super CountData, T> function3, Function<? super MeanData, T> function4, Function<? super DistributionData, T> function5, Function<? super AggregationData, T> function6) {
            return function.apply(this);
        }
    }

    @Immutable
    public static abstract class SumDataLong extends AggregationData {
        public abstract long getSum();

        SumDataLong() {
            super();
        }

        public static SumDataLong create(long j) {
            return new AutoValue_AggregationData_SumDataLong(j);
        }

        public final <T> T match(Function<? super SumDataDouble, T> function, Function<? super SumDataLong, T> function2, Function<? super CountData, T> function3, Function<? super MeanData, T> function4, Function<? super DistributionData, T> function5, Function<? super AggregationData, T> function6) {
            return function2.apply(this);
        }
    }

    public abstract <T> T match(Function<? super SumDataDouble, T> function, Function<? super SumDataLong, T> function2, Function<? super CountData, T> function3, Function<? super MeanData, T> function4, Function<? super DistributionData, T> function5, Function<? super AggregationData, T> function6);

    private AggregationData() {
    }
}
