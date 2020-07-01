package io.opencensus.stats;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import io.opencensus.common.Function;
import io.opencensus.common.Functions;
import io.opencensus.common.Timestamp;
import io.opencensus.stats.Aggregation.Count;
import io.opencensus.stats.Aggregation.Distribution;
import io.opencensus.stats.Aggregation.Mean;
import io.opencensus.stats.Aggregation.Sum;
import io.opencensus.stats.AggregationData.CountData;
import io.opencensus.stats.AggregationData.DistributionData;
import io.opencensus.stats.AggregationData.MeanData;
import io.opencensus.stats.AggregationData.SumDataDouble;
import io.opencensus.stats.AggregationData.SumDataLong;
import io.opencensus.stats.Measure.MeasureDouble;
import io.opencensus.stats.Measure.MeasureLong;
import io.opencensus.stats.View.AggregationWindow;
import io.opencensus.stats.View.AggregationWindow.Cumulative;
import io.opencensus.stats.View.AggregationWindow.Interval;
import io.opencensus.tags.TagValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.concurrent.Immutable;

@Immutable
public abstract class ViewData {

    @Immutable
    public static abstract class AggregationWindowData {

        @Immutable
        public static abstract class CumulativeData extends AggregationWindowData {
            public abstract Timestamp getEnd();

            public abstract Timestamp getStart();

            CumulativeData() {
                super();
            }

            public final <T> T match(Function<? super CumulativeData, T> function, Function<? super IntervalData, T> function2, Function<? super AggregationWindowData, T> function3) {
                return function.apply(this);
            }

            public static CumulativeData create(Timestamp timestamp, Timestamp timestamp2) {
                if (timestamp.compareTo(timestamp2) <= 0) {
                    return new AutoValue_ViewData_AggregationWindowData_CumulativeData(timestamp, timestamp2);
                }
                throw new IllegalArgumentException("Start time is later than end time.");
            }
        }

        @Immutable
        public static abstract class IntervalData extends AggregationWindowData {
            public abstract Timestamp getEnd();

            IntervalData() {
                super();
            }

            public final <T> T match(Function<? super CumulativeData, T> function, Function<? super IntervalData, T> function2, Function<? super AggregationWindowData, T> function3) {
                return function2.apply(this);
            }

            public static IntervalData create(Timestamp timestamp) {
                return new AutoValue_ViewData_AggregationWindowData_IntervalData(timestamp);
            }
        }

        public abstract <T> T match(Function<? super CumulativeData, T> function, Function<? super IntervalData, T> function2, Function<? super AggregationWindowData, T> function3);

        /* synthetic */ AggregationWindowData(AnonymousClass1 anonymousClass1) {
            this();
        }

        private AggregationWindowData() {
        }
    }

    public abstract Map<List<TagValue>, AggregationData> getAggregationMap();

    public abstract View getView();

    public abstract AggregationWindowData getWindowData();

    ViewData() {
    }

    public static ViewData create(View view, Map<? extends List<TagValue>, ? extends AggregationData> map, AggregationWindowData aggregationWindowData) {
        checkWindow(view.getWindow(), aggregationWindowData);
        Map newHashMap = Maps.newHashMap();
        for (Entry entry : map.entrySet()) {
            checkAggregation(view.getAggregation(), (AggregationData) entry.getValue(), view.getMeasure());
            newHashMap.put(Collections.unmodifiableList(new ArrayList((Collection) entry.getKey())), entry.getValue());
        }
        return new AutoValue_ViewData(view, Collections.unmodifiableMap(newHashMap), aggregationWindowData);
    }

    private static void checkWindow(AggregationWindow aggregationWindow, final AggregationWindowData aggregationWindowData) {
        aggregationWindow.match(new Function<Cumulative, Void>() {
            public Void apply(Cumulative cumulative) {
                AggregationWindowData aggregationWindowData = aggregationWindowData;
                Preconditions.checkArgument(aggregationWindowData instanceof CumulativeData, ViewData.createErrorMessageForWindow(cumulative, aggregationWindowData));
                return null;
            }
        }, new Function<Interval, Void>() {
            public Void apply(Interval interval) {
                AggregationWindowData aggregationWindowData = aggregationWindowData;
                Preconditions.checkArgument(aggregationWindowData instanceof IntervalData, ViewData.createErrorMessageForWindow(interval, aggregationWindowData));
                return null;
            }
        }, Functions.throwIllegalArgumentException());
    }

    private static String createErrorMessageForWindow(AggregationWindow aggregationWindow, AggregationWindowData aggregationWindowData) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AggregationWindow and AggregationWindowData types mismatch. AggregationWindow: ");
        stringBuilder.append(aggregationWindow);
        stringBuilder.append(" AggregationWindowData: ");
        stringBuilder.append(aggregationWindowData);
        return stringBuilder.toString();
    }

    private static void checkAggregation(final Aggregation aggregation, final AggregationData aggregationData, final Measure measure) {
        aggregation.match(new Function<Sum, Void>() {
            public Void apply(Sum sum) {
                measure.match(new Function<MeasureDouble, Void>() {
                    public Void apply(MeasureDouble measureDouble) {
                        Preconditions.checkArgument(aggregationData instanceof SumDataDouble, ViewData.createErrorMessageForAggregation(aggregation, aggregationData));
                        return null;
                    }
                }, new Function<MeasureLong, Void>() {
                    public Void apply(MeasureLong measureLong) {
                        Preconditions.checkArgument(aggregationData instanceof SumDataLong, ViewData.createErrorMessageForAggregation(aggregation, aggregationData));
                        return null;
                    }
                }, Functions.throwAssertionError());
                return null;
            }
        }, new Function<Count, Void>() {
            public Void apply(Count count) {
                AggregationData aggregationData = aggregationData;
                Preconditions.checkArgument(aggregationData instanceof CountData, ViewData.createErrorMessageForAggregation(aggregation, aggregationData));
                return null;
            }
        }, new Function<Mean, Void>() {
            public Void apply(Mean mean) {
                AggregationData aggregationData = aggregationData;
                Preconditions.checkArgument(aggregationData instanceof MeanData, ViewData.createErrorMessageForAggregation(aggregation, aggregationData));
                return null;
            }
        }, new Function<Distribution, Void>() {
            public Void apply(Distribution distribution) {
                AggregationData aggregationData = aggregationData;
                Preconditions.checkArgument(aggregationData instanceof DistributionData, ViewData.createErrorMessageForAggregation(aggregation, aggregationData));
                return null;
            }
        }, Functions.throwAssertionError());
    }

    private static String createErrorMessageForAggregation(Aggregation aggregation, AggregationData aggregationData) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Aggregation and AggregationData types mismatch. Aggregation: ");
        stringBuilder.append(aggregation);
        stringBuilder.append(" AggregationData: ");
        stringBuilder.append(aggregationData);
        return stringBuilder.toString();
    }
}
