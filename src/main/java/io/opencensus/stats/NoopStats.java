package io.opencensus.stats;

import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.opencensus.common.Functions;
import io.opencensus.common.Timestamp;
import io.opencensus.stats.Measure.MeasureDouble;
import io.opencensus.stats.Measure.MeasureLong;
import io.opencensus.stats.View.AggregationWindow.Cumulative;
import io.opencensus.stats.View.Name;
import io.opencensus.stats.ViewData.AggregationWindowData;
import io.opencensus.stats.ViewData.AggregationWindowData.CumulativeData;
import io.opencensus.stats.ViewData.AggregationWindowData.IntervalData;
import io.opencensus.tags.TagContext;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

final class NoopStats {

    @Immutable
    private static final class NoopMeasureMap extends MeasureMap {
        static final MeasureMap INSTANCE = new NoopMeasureMap();

        public MeasureMap put(MeasureDouble measureDouble, double d) {
            return this;
        }

        public MeasureMap put(MeasureLong measureLong, long j) {
            return this;
        }

        public void record() {
        }

        private NoopMeasureMap() {
        }

        public void record(TagContext tagContext) {
            Preconditions.checkNotNull(tagContext, "tags");
        }
    }

    @ThreadSafe
    private static final class NoopStatsComponent extends StatsComponent {
        private volatile boolean isRead;
        private final ViewManager viewManager;

        private NoopStatsComponent() {
            this.viewManager = NoopStats.newNoopViewManager();
        }

        public ViewManager getViewManager() {
            return this.viewManager;
        }

        public StatsRecorder getStatsRecorder() {
            return NoopStats.getNoopStatsRecorder();
        }

        public StatsCollectionState getState() {
            this.isRead = true;
            return StatsCollectionState.DISABLED;
        }

        @Deprecated
        public void setState(StatsCollectionState statsCollectionState) {
            Preconditions.checkNotNull(statsCollectionState, "state");
            Preconditions.checkState(this.isRead ^ 1, "State was already read, cannot set state.");
        }
    }

    @Immutable
    private static final class NoopStatsRecorder extends StatsRecorder {
        static final StatsRecorder INSTANCE = new NoopStatsRecorder();

        private NoopStatsRecorder() {
        }

        public MeasureMap newMeasureMap() {
            return NoopStats.getNoopMeasureMap();
        }
    }

    @ThreadSafe
    private static final class NoopViewManager extends ViewManager {
        private static final Timestamp ZERO_TIMESTAMP = Timestamp.create(0, 0);
        @Nullable
        private volatile Set<View> exportedViews;
        @GuardedBy("registeredViews")
        private final Map<Name, View> registeredViews;

        private NoopViewManager() {
            this.registeredViews = Maps.newHashMap();
        }

        public void registerView(View view) {
            Preconditions.checkNotNull(view, "newView");
            synchronized (this.registeredViews) {
                this.exportedViews = null;
                View view2 = (View) this.registeredViews.get(view.getName());
                boolean z = view2 == null || view.equals(view2);
                Preconditions.checkArgument(z, "A different view with the same name already exists.");
                if (view2 == null) {
                    this.registeredViews.put(view.getName(), view);
                }
            }
        }

        @Nullable
        public ViewData getView(Name name) {
            Preconditions.checkNotNull(name, ConditionalUserProperty.NAME);
            synchronized (this.registeredViews) {
                View view = (View) this.registeredViews.get(name);
                if (view == null) {
                    return null;
                }
                ViewData create = ViewData.create(view, Collections.emptyMap(), (AggregationWindowData) view.getWindow().match(Functions.returnConstant(CumulativeData.create(ZERO_TIMESTAMP, ZERO_TIMESTAMP)), Functions.returnConstant(IntervalData.create(ZERO_TIMESTAMP)), Functions.throwAssertionError()));
                return create;
            }
        }

        public Set<View> getAllExportedViews() {
            Set<View> set = this.exportedViews;
            if (set == null) {
                synchronized (this.registeredViews) {
                    set = filterExportedViews(this.registeredViews.values());
                    this.exportedViews = set;
                }
            }
            return set;
        }

        private static Set<View> filterExportedViews(Collection<View> collection) {
            Set newHashSet = Sets.newHashSet();
            for (View view : collection) {
                if (view.getWindow() instanceof Cumulative) {
                    newHashSet.add(view);
                }
            }
            return Collections.unmodifiableSet(newHashSet);
        }
    }

    private NoopStats() {
    }

    static StatsComponent newNoopStatsComponent() {
        return new NoopStatsComponent();
    }

    static StatsRecorder getNoopStatsRecorder() {
        return NoopStatsRecorder.INSTANCE;
    }

    static MeasureMap getNoopMeasureMap() {
        return NoopMeasureMap.INSTANCE;
    }

    static ViewManager newNoopViewManager() {
        return new NoopViewManager();
    }
}
