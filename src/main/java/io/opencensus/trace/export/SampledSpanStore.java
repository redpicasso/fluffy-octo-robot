package io.opencensus.trace.export;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.opencensus.trace.Status.CanonicalCode;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public abstract class SampledSpanStore {

    @Immutable
    public static abstract class ErrorFilter {
        @Nullable
        public abstract CanonicalCode getCanonicalCode();

        public abstract int getMaxSpansToReturn();

        public abstract String getSpanName();

        ErrorFilter() {
        }

        public static ErrorFilter create(String str, @Nullable CanonicalCode canonicalCode, int i) {
            boolean z = true;
            if (canonicalCode != null) {
                Preconditions.checkArgument(canonicalCode != CanonicalCode.OK, "Invalid canonical code.");
            }
            if (i < 0) {
                z = false;
            }
            Preconditions.checkArgument(z, "Negative maxSpansToReturn.");
            return new AutoValue_SampledSpanStore_ErrorFilter(str, canonicalCode, i);
        }
    }

    public enum LatencyBucketBoundaries {
        ZERO_MICROSx10(0, TimeUnit.MICROSECONDS.toNanos(10)),
        MICROSx10_MICROSx100(TimeUnit.MICROSECONDS.toNanos(10), TimeUnit.MICROSECONDS.toNanos(100)),
        MICROSx100_MILLIx1(TimeUnit.MICROSECONDS.toNanos(100), TimeUnit.MILLISECONDS.toNanos(1)),
        MILLIx1_MILLIx10(TimeUnit.MILLISECONDS.toNanos(1), TimeUnit.MILLISECONDS.toNanos(10)),
        MILLIx10_MILLIx100(TimeUnit.MILLISECONDS.toNanos(10), TimeUnit.MILLISECONDS.toNanos(100)),
        MILLIx100_SECONDx1(TimeUnit.MILLISECONDS.toNanos(100), TimeUnit.SECONDS.toNanos(1)),
        SECONDx1_SECONDx10(TimeUnit.SECONDS.toNanos(1), TimeUnit.SECONDS.toNanos(10)),
        SECONDx10_SECONDx100(TimeUnit.SECONDS.toNanos(10), TimeUnit.SECONDS.toNanos(100)),
        SECONDx100_MAX(TimeUnit.SECONDS.toNanos(100), Long.MAX_VALUE);
        
        private final long latencyLowerNs;
        private final long latencyUpperNs;

        private LatencyBucketBoundaries(long j, long j2) {
            this.latencyLowerNs = j;
            this.latencyUpperNs = j2;
        }

        public long getLatencyLowerNs() {
            return this.latencyLowerNs;
        }

        public long getLatencyUpperNs() {
            return this.latencyUpperNs;
        }
    }

    @Immutable
    public static abstract class LatencyFilter {
        public abstract long getLatencyLowerNs();

        public abstract long getLatencyUpperNs();

        public abstract int getMaxSpansToReturn();

        public abstract String getSpanName();

        LatencyFilter() {
        }

        public static LatencyFilter create(String str, long j, long j2, int i) {
            boolean z = true;
            Preconditions.checkArgument(i >= 0, "Negative maxSpansToReturn.");
            Preconditions.checkArgument(j >= 0, "Negative latencyLowerNs");
            if (j2 < 0) {
                z = false;
            }
            Preconditions.checkArgument(z, "Negative latencyUpperNs");
            return new AutoValue_SampledSpanStore_LatencyFilter(str, j, j2, i);
        }
    }

    @Immutable
    public static abstract class PerSpanNameSummary {
        public abstract Map<CanonicalCode, Integer> getNumbersOfErrorSampledSpans();

        public abstract Map<LatencyBucketBoundaries, Integer> getNumbersOfLatencySampledSpans();

        PerSpanNameSummary() {
        }

        public static PerSpanNameSummary create(Map<LatencyBucketBoundaries, Integer> map, Map<CanonicalCode, Integer> map2) {
            return new AutoValue_SampledSpanStore_PerSpanNameSummary(Collections.unmodifiableMap(new HashMap((Map) Preconditions.checkNotNull(map, "numbersOfLatencySampledSpans"))), Collections.unmodifiableMap(new HashMap((Map) Preconditions.checkNotNull(map2, "numbersOfErrorSampledSpans"))));
        }
    }

    @Immutable
    public static abstract class Summary {
        public abstract Map<String, PerSpanNameSummary> getPerSpanNameSummary();

        Summary() {
        }

        public static Summary create(Map<String, PerSpanNameSummary> map) {
            return new AutoValue_SampledSpanStore_Summary(Collections.unmodifiableMap(new HashMap((Map) Preconditions.checkNotNull(map, "perSpanNameSummary"))));
        }
    }

    @ThreadSafe
    private static final class NoopSampledSpanStore extends SampledSpanStore {
        private static final PerSpanNameSummary EMPTY_PER_SPAN_NAME_SUMMARY = PerSpanNameSummary.create(Collections.emptyMap(), Collections.emptyMap());
        @GuardedBy("registeredSpanNames")
        private final Set<String> registeredSpanNames;

        private NoopSampledSpanStore() {
            this.registeredSpanNames = Sets.newHashSet();
        }

        public Summary getSummary() {
            Map newHashMap = Maps.newHashMap();
            synchronized (this.registeredSpanNames) {
                for (String put : this.registeredSpanNames) {
                    newHashMap.put(put, EMPTY_PER_SPAN_NAME_SUMMARY);
                }
            }
            return Summary.create(newHashMap);
        }

        public Collection<SpanData> getLatencySampledSpans(LatencyFilter latencyFilter) {
            Preconditions.checkNotNull(latencyFilter, "latencyFilter");
            return Collections.emptyList();
        }

        public Collection<SpanData> getErrorSampledSpans(ErrorFilter errorFilter) {
            Preconditions.checkNotNull(errorFilter, "errorFilter");
            return Collections.emptyList();
        }

        public void registerSpanNamesForCollection(Collection<String> collection) {
            Preconditions.checkNotNull(collection, "spanNames");
            synchronized (this.registeredSpanNames) {
                this.registeredSpanNames.addAll(collection);
            }
        }

        public void unregisterSpanNamesForCollection(Collection<String> collection) {
            Preconditions.checkNotNull(collection);
            synchronized (this.registeredSpanNames) {
                this.registeredSpanNames.removeAll(collection);
            }
        }

        public Set<String> getRegisteredSpanNamesForCollection() {
            Set<String> unmodifiableSet;
            synchronized (this.registeredSpanNames) {
                unmodifiableSet = Collections.unmodifiableSet(Sets.newHashSet(this.registeredSpanNames));
            }
            return unmodifiableSet;
        }
    }

    public abstract Collection<SpanData> getErrorSampledSpans(ErrorFilter errorFilter);

    public abstract Collection<SpanData> getLatencySampledSpans(LatencyFilter latencyFilter);

    @VisibleForTesting
    public abstract Set<String> getRegisteredSpanNamesForCollection();

    public abstract Summary getSummary();

    public abstract void registerSpanNamesForCollection(Collection<String> collection);

    public abstract void unregisterSpanNamesForCollection(Collection<String> collection);

    protected SampledSpanStore() {
    }

    static SampledSpanStore newNoopSampledSpanStore() {
        return new NoopSampledSpanStore();
    }
}
