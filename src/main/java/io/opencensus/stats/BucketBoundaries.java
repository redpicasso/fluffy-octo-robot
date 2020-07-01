package io.opencensus.stats;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.concurrent.Immutable;

@Immutable
public abstract class BucketBoundaries {
    public abstract List<Double> getBoundaries();

    public static final BucketBoundaries create(List<Double> list) {
        Preconditions.checkNotNull(list, "bucketBoundaries list should not be null.");
        List arrayList = new ArrayList(list);
        if (arrayList.size() > 1) {
            double doubleValue = ((Double) arrayList.get(0)).doubleValue();
            int i = 1;
            while (i < arrayList.size()) {
                double doubleValue2 = ((Double) arrayList.get(i)).doubleValue();
                Preconditions.checkArgument(doubleValue < doubleValue2, "Bucket boundaries not sorted.");
                i++;
                doubleValue = doubleValue2;
            }
        }
        return new AutoValue_BucketBoundaries(Collections.unmodifiableList(arrayList));
    }
}
