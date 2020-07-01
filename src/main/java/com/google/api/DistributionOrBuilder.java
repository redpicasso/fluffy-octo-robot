package com.google.api;

import com.google.api.Distribution.BucketOptions;
import com.google.api.Distribution.Range;
import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public interface DistributionOrBuilder extends MessageLiteOrBuilder {
    long getBucketCounts(int i);

    int getBucketCountsCount();

    List<Long> getBucketCountsList();

    BucketOptions getBucketOptions();

    long getCount();

    double getMean();

    Range getRange();

    double getSumOfSquaredDeviation();

    boolean hasBucketOptions();

    boolean hasRange();
}
