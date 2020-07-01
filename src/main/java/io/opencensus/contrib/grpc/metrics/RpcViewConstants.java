package io.opencensus.contrib.grpc.metrics;

import com.google.common.annotations.VisibleForTesting;
import io.opencensus.common.Duration;
import io.opencensus.stats.Aggregation;
import io.opencensus.stats.Aggregation.Count;
import io.opencensus.stats.Aggregation.Distribution;
import io.opencensus.stats.Aggregation.Mean;
import io.opencensus.stats.BucketBoundaries;
import io.opencensus.stats.View;
import io.opencensus.stats.View.AggregationWindow;
import io.opencensus.stats.View.AggregationWindow.Cumulative;
import io.opencensus.stats.View.AggregationWindow.Interval;
import io.opencensus.stats.View.Name;
import io.opencensus.tags.TagKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class RpcViewConstants {
    @VisibleForTesting
    static final Aggregation AGGREGATION_WITH_BYTES_HISTOGRAM = Distribution.create(BucketBoundaries.create(RPC_BYTES_BUCKET_BOUNDARIES));
    @VisibleForTesting
    static final Aggregation AGGREGATION_WITH_COUNT_HISTOGRAM = Distribution.create(BucketBoundaries.create(RPC_COUNT_BUCKET_BOUNDARIES));
    @VisibleForTesting
    static final Aggregation AGGREGATION_WITH_MILLIS_HISTOGRAM = Distribution.create(BucketBoundaries.create(RPC_MILLIS_BUCKET_BOUNDARIES));
    @VisibleForTesting
    static final Aggregation COUNT = Count.create();
    @VisibleForTesting
    static final AggregationWindow CUMULATIVE = Cumulative.create();
    @VisibleForTesting
    static final Duration HOUR = Duration.create(3600, 0);
    @VisibleForTesting
    static final AggregationWindow INTERVAL_HOUR = Interval.create(HOUR);
    @VisibleForTesting
    static final AggregationWindow INTERVAL_MINUTE = Interval.create(MINUTE);
    @VisibleForTesting
    static final Aggregation MEAN = Mean.create();
    @VisibleForTesting
    static final Duration MINUTE = Duration.create(60, 0);
    @VisibleForTesting
    static final List<Double> RPC_BYTES_BUCKET_BOUNDARIES;
    public static final View RPC_CLIENT_ERROR_COUNT_HOUR_VIEW;
    public static final View RPC_CLIENT_ERROR_COUNT_MINUTE_VIEW;
    public static final View RPC_CLIENT_ERROR_COUNT_VIEW;
    public static final View RPC_CLIENT_FINISHED_COUNT_CUMULATIVE_VIEW;
    public static final View RPC_CLIENT_FINISHED_COUNT_HOUR_VIEW;
    public static final View RPC_CLIENT_FINISHED_COUNT_MINUTE_VIEW;
    public static final View RPC_CLIENT_REQUEST_BYTES_HOUR_VIEW;
    public static final View RPC_CLIENT_REQUEST_BYTES_MINUTE_VIEW;
    public static final View RPC_CLIENT_REQUEST_BYTES_VIEW;
    public static final View RPC_CLIENT_REQUEST_COUNT_HOUR_VIEW;
    public static final View RPC_CLIENT_REQUEST_COUNT_MINUTE_VIEW;
    public static final View RPC_CLIENT_REQUEST_COUNT_VIEW;
    public static final View RPC_CLIENT_RESPONSE_BYTES_HOUR_VIEW;
    public static final View RPC_CLIENT_RESPONSE_BYTES_MINUTE_VIEW;
    public static final View RPC_CLIENT_RESPONSE_BYTES_VIEW;
    public static final View RPC_CLIENT_RESPONSE_COUNT_HOUR_VIEW;
    public static final View RPC_CLIENT_RESPONSE_COUNT_MINUTE_VIEW;
    public static final View RPC_CLIENT_RESPONSE_COUNT_VIEW;
    public static final View RPC_CLIENT_ROUNDTRIP_LATENCY_HOUR_VIEW;
    public static final View RPC_CLIENT_ROUNDTRIP_LATENCY_MINUTE_VIEW;
    public static final View RPC_CLIENT_ROUNDTRIP_LATENCY_VIEW;
    public static final View RPC_CLIENT_SERVER_ELAPSED_TIME_HOUR_VIEW;
    public static final View RPC_CLIENT_SERVER_ELAPSED_TIME_MINUTE_VIEW;
    public static final View RPC_CLIENT_SERVER_ELAPSED_TIME_VIEW;
    public static final View RPC_CLIENT_STARTED_COUNT_CUMULATIVE_VIEW;
    public static final View RPC_CLIENT_STARTED_COUNT_HOUR_VIEW;
    public static final View RPC_CLIENT_STARTED_COUNT_MINUTE_VIEW;
    public static final View RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES_HOUR_VIEW;
    public static final View RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES_MINUTE_VIEW;
    public static final View RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES_VIEW;
    public static final View RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES_HOUR_VIEW;
    public static final View RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES_MINUTE_VIEW;
    public static final View RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES_VIEW;
    @VisibleForTesting
    static final List<Double> RPC_COUNT_BUCKET_BOUNDARIES;
    @VisibleForTesting
    static final List<Double> RPC_MILLIS_BUCKET_BOUNDARIES;
    public static final View RPC_SERVER_ERROR_COUNT_HOUR_VIEW;
    public static final View RPC_SERVER_ERROR_COUNT_MINUTE_VIEW;
    public static final View RPC_SERVER_ERROR_COUNT_VIEW;
    public static final View RPC_SERVER_FINISHED_COUNT_CUMULATIVE_VIEW;
    public static final View RPC_SERVER_FINISHED_COUNT_HOUR_VIEW;
    public static final View RPC_SERVER_FINISHED_COUNT_MINUTE_VIEW;
    public static final View RPC_SERVER_REQUEST_BYTES_HOUR_VIEW;
    public static final View RPC_SERVER_REQUEST_BYTES_MINUTE_VIEW;
    public static final View RPC_SERVER_REQUEST_BYTES_VIEW;
    public static final View RPC_SERVER_REQUEST_COUNT_HOUR_VIEW;
    public static final View RPC_SERVER_REQUEST_COUNT_MINUTE_VIEW;
    public static final View RPC_SERVER_REQUEST_COUNT_VIEW;
    public static final View RPC_SERVER_RESPONSE_BYTES_HOUR_VIEW;
    public static final View RPC_SERVER_RESPONSE_BYTES_MINUTE_VIEW;
    public static final View RPC_SERVER_RESPONSE_BYTES_VIEW;
    public static final View RPC_SERVER_RESPONSE_COUNT_HOUR_VIEW;
    public static final View RPC_SERVER_RESPONSE_COUNT_MINUTE_VIEW;
    public static final View RPC_SERVER_RESPONSE_COUNT_VIEW;
    public static final View RPC_SERVER_SERVER_ELAPSED_TIME_HOUR_VIEW;
    public static final View RPC_SERVER_SERVER_ELAPSED_TIME_MINUTE_VIEW;
    public static final View RPC_SERVER_SERVER_ELAPSED_TIME_VIEW;
    public static final View RPC_SERVER_SERVER_LATENCY_HOUR_VIEW;
    public static final View RPC_SERVER_SERVER_LATENCY_MINUTE_VIEW;
    public static final View RPC_SERVER_SERVER_LATENCY_VIEW;
    public static final View RPC_SERVER_STARTED_COUNT_CUMULATIVE_VIEW;
    public static final View RPC_SERVER_STARTED_COUNT_HOUR_VIEW;
    public static final View RPC_SERVER_STARTED_COUNT_MINUTE_VIEW;
    public static final View RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES_HOUR_VIEW;
    public static final View RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES_MINUTE_VIEW;
    public static final View RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES_VIEW;
    public static final View RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES_HOUR_VIEW;
    public static final View RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES_MINUTE_VIEW;
    public static final View RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES_VIEW;

    static {
        r1 = new Double[14];
        r1[0] = Double.valueOf(0.0d);
        r1[1] = Double.valueOf(1024.0d);
        r1[2] = Double.valueOf(2048.0d);
        r1[3] = Double.valueOf(4096.0d);
        r1[4] = Double.valueOf(16384.0d);
        r1[5] = Double.valueOf(65536.0d);
        r1[6] = Double.valueOf(262144.0d);
        r1[7] = Double.valueOf(1048576.0d);
        r1[8] = Double.valueOf(4194304.0d);
        r1[9] = Double.valueOf(1.6777216E7d);
        r1[10] = Double.valueOf(6.7108864E7d);
        r1[11] = Double.valueOf(2.68435456E8d);
        r1[12] = Double.valueOf(1.073741824E9d);
        r1[13] = Double.valueOf(4.294967296E9d);
        RPC_BYTES_BUCKET_BOUNDARIES = Collections.unmodifiableList(Arrays.asList(r1));
        RPC_MILLIS_BUCKET_BOUNDARIES = Collections.unmodifiableList(Arrays.asList(new Double[]{r2, Double.valueOf(1.0d), Double.valueOf(2.0d), Double.valueOf(3.0d), Double.valueOf(4.0d), Double.valueOf(5.0d), Double.valueOf(6.0d), Double.valueOf(8.0d), Double.valueOf(10.0d), Double.valueOf(13.0d), Double.valueOf(16.0d), Double.valueOf(20.0d), Double.valueOf(25.0d), Double.valueOf(30.0d), Double.valueOf(40.0d), Double.valueOf(50.0d), Double.valueOf(65.0d), Double.valueOf(80.0d), Double.valueOf(100.0d), Double.valueOf(130.0d), Double.valueOf(160.0d), Double.valueOf(200.0d), Double.valueOf(250.0d), Double.valueOf(300.0d), Double.valueOf(400.0d), Double.valueOf(500.0d), Double.valueOf(650.0d), Double.valueOf(800.0d), Double.valueOf(1000.0d), Double.valueOf(2000.0d), Double.valueOf(5000.0d), Double.valueOf(10000.0d), Double.valueOf(20000.0d), Double.valueOf(50000.0d), Double.valueOf(100000.0d)}));
        RPC_COUNT_BUCKET_BOUNDARIES = Collections.unmodifiableList(Arrays.asList(new Double[]{r2, Double.valueOf(1.0d), Double.valueOf(2.0d), Double.valueOf(4.0d), Double.valueOf(8.0d), Double.valueOf(16.0d), Double.valueOf(32.0d), Double.valueOf(64.0d), Double.valueOf(128.0d), Double.valueOf(256.0d), Double.valueOf(512.0d), Double.valueOf(1024.0d), Double.valueOf(2048.0d), Double.valueOf(4096.0d), Double.valueOf(8192.0d), Double.valueOf(16384.0d), Double.valueOf(32768.0d), Double.valueOf(65536.0d)}));
        String str = "RPC Errors";
        RPC_CLIENT_ERROR_COUNT_VIEW = View.create(Name.create("grpc.io/client/error_count/cumulative"), str, RpcMeasureConstants.RPC_CLIENT_ERROR_COUNT, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_STATUS, RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str = "Latency in msecs";
        RPC_CLIENT_ROUNDTRIP_LATENCY_VIEW = View.create(Name.create("grpc.io/client/roundtrip_latency/cumulative"), str, RpcMeasureConstants.RPC_CLIENT_ROUNDTRIP_LATENCY, AGGREGATION_WITH_MILLIS_HISTOGRAM, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str = "Server elapsed time in msecs";
        RPC_CLIENT_SERVER_ELAPSED_TIME_VIEW = View.create(Name.create("grpc.io/client/server_elapsed_time/cumulative"), str, RpcMeasureConstants.RPC_CLIENT_SERVER_ELAPSED_TIME, AGGREGATION_WITH_MILLIS_HISTOGRAM, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str = "Request bytes";
        RPC_CLIENT_REQUEST_BYTES_VIEW = View.create(Name.create("grpc.io/client/request_bytes/cumulative"), str, RpcMeasureConstants.RPC_CLIENT_REQUEST_BYTES, AGGREGATION_WITH_BYTES_HISTOGRAM, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str = "Response bytes";
        RPC_CLIENT_RESPONSE_BYTES_VIEW = View.create(Name.create("grpc.io/client/response_bytes/cumulative"), str, RpcMeasureConstants.RPC_CLIENT_RESPONSE_BYTES, AGGREGATION_WITH_BYTES_HISTOGRAM, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str = "Uncompressed Request bytes";
        RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES_VIEW = View.create(Name.create("grpc.io/client/uncompressed_request_bytes/cumulative"), str, RpcMeasureConstants.RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES, AGGREGATION_WITH_BYTES_HISTOGRAM, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str = "Uncompressed Response bytes";
        RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES_VIEW = View.create(Name.create("grpc.io/client/uncompressed_response_bytes/cumulative"), str, RpcMeasureConstants.RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES, AGGREGATION_WITH_BYTES_HISTOGRAM, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str = "Count of request messages per client RPC";
        RPC_CLIENT_REQUEST_COUNT_VIEW = View.create(Name.create("grpc.io/client/request_count/cumulative"), str, RpcMeasureConstants.RPC_CLIENT_REQUEST_COUNT, AGGREGATION_WITH_COUNT_HISTOGRAM, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str = "Count of response messages per client RPC";
        RPC_CLIENT_RESPONSE_COUNT_VIEW = View.create(Name.create("grpc.io/client/response_count/cumulative"), str, RpcMeasureConstants.RPC_CLIENT_RESPONSE_COUNT, AGGREGATION_WITH_COUNT_HISTOGRAM, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str = "Number of started client RPCs";
        RPC_CLIENT_STARTED_COUNT_CUMULATIVE_VIEW = View.create(Name.create("grpc.io/client/started_count/cumulative"), str, RpcMeasureConstants.RPC_CLIENT_STARTED_COUNT, COUNT, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str = "Number of finished client RPCs";
        RPC_CLIENT_FINISHED_COUNT_CUMULATIVE_VIEW = View.create(Name.create("grpc.io/client/finished_count/cumulative"), str, RpcMeasureConstants.RPC_CLIENT_FINISHED_COUNT, COUNT, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str = "RPC Errors";
        RPC_SERVER_ERROR_COUNT_VIEW = View.create(Name.create("grpc.io/server/error_count/cumulative"), str, RpcMeasureConstants.RPC_SERVER_ERROR_COUNT, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_STATUS, RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        String str2 = "Latency in msecs";
        RPC_SERVER_SERVER_LATENCY_VIEW = View.create(Name.create("grpc.io/server/server_latency/cumulative"), str2, RpcMeasureConstants.RPC_SERVER_SERVER_LATENCY, AGGREGATION_WITH_MILLIS_HISTOGRAM, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str2 = "Server elapsed time in msecs";
        RPC_SERVER_SERVER_ELAPSED_TIME_VIEW = View.create(Name.create("grpc.io/server/elapsed_time/cumulative"), str2, RpcMeasureConstants.RPC_SERVER_SERVER_ELAPSED_TIME, AGGREGATION_WITH_MILLIS_HISTOGRAM, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str2 = "Request bytes";
        RPC_SERVER_REQUEST_BYTES_VIEW = View.create(Name.create("grpc.io/server/request_bytes/cumulative"), str2, RpcMeasureConstants.RPC_SERVER_REQUEST_BYTES, AGGREGATION_WITH_BYTES_HISTOGRAM, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str2 = "Response bytes";
        RPC_SERVER_RESPONSE_BYTES_VIEW = View.create(Name.create("grpc.io/server/response_bytes/cumulative"), str2, RpcMeasureConstants.RPC_SERVER_RESPONSE_BYTES, AGGREGATION_WITH_BYTES_HISTOGRAM, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str2 = "Uncompressed Request bytes";
        RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES_VIEW = View.create(Name.create("grpc.io/server/uncompressed_request_bytes/cumulative"), str2, RpcMeasureConstants.RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES, AGGREGATION_WITH_BYTES_HISTOGRAM, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str2 = "Uncompressed Response bytes";
        RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES_VIEW = View.create(Name.create("grpc.io/server/uncompressed_response_bytes/cumulative"), str2, RpcMeasureConstants.RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES, AGGREGATION_WITH_BYTES_HISTOGRAM, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str2 = "Count of request messages per server RPC";
        RPC_SERVER_REQUEST_COUNT_VIEW = View.create(Name.create("grpc.io/server/request_count/cumulative"), str2, RpcMeasureConstants.RPC_SERVER_REQUEST_COUNT, AGGREGATION_WITH_COUNT_HISTOGRAM, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str2 = "Count of response messages per server RPC";
        RPC_SERVER_RESPONSE_COUNT_VIEW = View.create(Name.create("grpc.io/server/response_count/cumulative"), str2, RpcMeasureConstants.RPC_SERVER_RESPONSE_COUNT, AGGREGATION_WITH_COUNT_HISTOGRAM, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str2 = "Number of started server RPCs";
        RPC_SERVER_STARTED_COUNT_CUMULATIVE_VIEW = View.create(Name.create("grpc.io/server/started_count/cumulative"), str2, RpcMeasureConstants.RPC_SERVER_STARTED_COUNT, COUNT, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str2 = "Number of finished server RPCs";
        RPC_SERVER_FINISHED_COUNT_CUMULATIVE_VIEW = View.create(Name.create("grpc.io/server/finished_count/cumulative"), str2, RpcMeasureConstants.RPC_SERVER_FINISHED_COUNT, COUNT, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), CUMULATIVE);
        str2 = "Minute stats for latency in msecs";
        RPC_CLIENT_ROUNDTRIP_LATENCY_MINUTE_VIEW = View.create(Name.create("grpc.io/client/roundtrip_latency/minute"), str2, RpcMeasureConstants.RPC_CLIENT_ROUNDTRIP_LATENCY, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats for request size in bytes";
        RPC_CLIENT_REQUEST_BYTES_MINUTE_VIEW = View.create(Name.create("grpc.io/client/request_bytes/minute"), str2, RpcMeasureConstants.RPC_CLIENT_REQUEST_BYTES, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats for response size in bytes";
        RPC_CLIENT_RESPONSE_BYTES_MINUTE_VIEW = View.create(Name.create("grpc.io/client/response_bytes/minute"), str2, RpcMeasureConstants.RPC_CLIENT_RESPONSE_BYTES, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats for rpc errors";
        RPC_CLIENT_ERROR_COUNT_MINUTE_VIEW = View.create(Name.create("grpc.io/client/error_count/minute"), str2, RpcMeasureConstants.RPC_CLIENT_ERROR_COUNT, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats for uncompressed request size in bytes";
        RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES_MINUTE_VIEW = View.create(Name.create("grpc.io/client/uncompressed_request_bytes/minute"), str2, RpcMeasureConstants.RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats for uncompressed response size in bytes";
        RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES_MINUTE_VIEW = View.create(Name.create("grpc.io/client/uncompressed_response_bytes/minute"), str2, RpcMeasureConstants.RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats for server elapsed time in msecs";
        RPC_CLIENT_SERVER_ELAPSED_TIME_MINUTE_VIEW = View.create(Name.create("grpc.io/client/server_elapsed_time/minute"), str2, RpcMeasureConstants.RPC_CLIENT_SERVER_ELAPSED_TIME, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats on the number of client RPCs started";
        RPC_CLIENT_STARTED_COUNT_MINUTE_VIEW = View.create(Name.create("grpc.io/client/started_count/minute"), str2, RpcMeasureConstants.RPC_CLIENT_STARTED_COUNT, COUNT, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats on the number of client RPCs finished";
        RPC_CLIENT_FINISHED_COUNT_MINUTE_VIEW = View.create(Name.create("grpc.io/client/finished_count/minute"), str2, RpcMeasureConstants.RPC_CLIENT_FINISHED_COUNT, COUNT, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats on the count of request messages per client RPC";
        RPC_CLIENT_REQUEST_COUNT_MINUTE_VIEW = View.create(Name.create("grpc.io/client/request_count/minute"), str2, RpcMeasureConstants.RPC_CLIENT_REQUEST_COUNT, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats on the count of response messages per client RPC";
        RPC_CLIENT_RESPONSE_COUNT_MINUTE_VIEW = View.create(Name.create("grpc.io/client/response_count/minute"), str2, RpcMeasureConstants.RPC_CLIENT_RESPONSE_COUNT, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Hour stats for latency in msecs";
        RPC_CLIENT_ROUNDTRIP_LATENCY_HOUR_VIEW = View.create(Name.create("grpc.io/client/roundtrip_latency/hour"), str2, RpcMeasureConstants.RPC_CLIENT_ROUNDTRIP_LATENCY, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats for request size in bytes";
        RPC_CLIENT_REQUEST_BYTES_HOUR_VIEW = View.create(Name.create("grpc.io/client/request_bytes/hour"), str2, RpcMeasureConstants.RPC_CLIENT_REQUEST_BYTES, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats for response size in bytes";
        RPC_CLIENT_RESPONSE_BYTES_HOUR_VIEW = View.create(Name.create("grpc.io/client/response_bytes/hour"), str2, RpcMeasureConstants.RPC_CLIENT_RESPONSE_BYTES, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats for rpc errors";
        RPC_CLIENT_ERROR_COUNT_HOUR_VIEW = View.create(Name.create("grpc.io/client/error_count/hour"), str2, RpcMeasureConstants.RPC_CLIENT_ERROR_COUNT, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats for uncompressed request size in bytes";
        RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES_HOUR_VIEW = View.create(Name.create("grpc.io/client/uncompressed_request_bytes/hour"), str2, RpcMeasureConstants.RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats for uncompressed response size in bytes";
        RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES_HOUR_VIEW = View.create(Name.create("grpc.io/client/uncompressed_response_bytes/hour"), str2, RpcMeasureConstants.RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats for server elapsed time in msecs";
        RPC_CLIENT_SERVER_ELAPSED_TIME_HOUR_VIEW = View.create(Name.create("grpc.io/client/server_elapsed_time/hour"), str2, RpcMeasureConstants.RPC_CLIENT_SERVER_ELAPSED_TIME, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats on the number of client RPCs started";
        RPC_CLIENT_STARTED_COUNT_HOUR_VIEW = View.create(Name.create("grpc.io/client/started_count/hour"), str2, RpcMeasureConstants.RPC_CLIENT_STARTED_COUNT, COUNT, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats on the number of client RPCs finished";
        RPC_CLIENT_FINISHED_COUNT_HOUR_VIEW = View.create(Name.create("grpc.io/client/finished_count/hour"), str2, RpcMeasureConstants.RPC_CLIENT_FINISHED_COUNT, COUNT, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats on the count of request messages per client RPC";
        RPC_CLIENT_REQUEST_COUNT_HOUR_VIEW = View.create(Name.create("grpc.io/client/request_count/hour"), str2, RpcMeasureConstants.RPC_CLIENT_REQUEST_COUNT, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats on the count of response messages per client RPC";
        RPC_CLIENT_RESPONSE_COUNT_HOUR_VIEW = View.create(Name.create("grpc.io/client/response_count/hour"), str2, RpcMeasureConstants.RPC_CLIENT_RESPONSE_COUNT, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Minute stats for server latency in msecs";
        RPC_SERVER_SERVER_LATENCY_MINUTE_VIEW = View.create(Name.create("grpc.io/server/server_latency/minute"), str2, RpcMeasureConstants.RPC_SERVER_SERVER_LATENCY, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats for request size in bytes";
        RPC_SERVER_REQUEST_BYTES_MINUTE_VIEW = View.create(Name.create("grpc.io/server/request_bytes/minute"), str2, RpcMeasureConstants.RPC_SERVER_REQUEST_BYTES, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats for response size in bytes";
        RPC_SERVER_RESPONSE_BYTES_MINUTE_VIEW = View.create(Name.create("grpc.io/server/response_bytes/minute"), str2, RpcMeasureConstants.RPC_SERVER_RESPONSE_BYTES, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats for rpc errors";
        RPC_SERVER_ERROR_COUNT_MINUTE_VIEW = View.create(Name.create("grpc.io/server/error_count/minute"), str2, RpcMeasureConstants.RPC_SERVER_ERROR_COUNT, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats for uncompressed request size in bytes";
        RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES_MINUTE_VIEW = View.create(Name.create("grpc.io/server/uncompressed_request_bytes/minute"), str2, RpcMeasureConstants.RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats for uncompressed response size in bytes";
        RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES_MINUTE_VIEW = View.create(Name.create("grpc.io/server/uncompressed_response_bytes/minute"), str2, RpcMeasureConstants.RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats for server elapsed time in msecs";
        RPC_SERVER_SERVER_ELAPSED_TIME_MINUTE_VIEW = View.create(Name.create("grpc.io/server/server_elapsed_time/minute"), str2, RpcMeasureConstants.RPC_SERVER_SERVER_ELAPSED_TIME, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats on the number of server RPCs started";
        RPC_SERVER_STARTED_COUNT_MINUTE_VIEW = View.create(Name.create("grpc.io/server/started_count/minute"), str2, RpcMeasureConstants.RPC_SERVER_STARTED_COUNT, COUNT, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats on the number of server RPCs finished";
        RPC_SERVER_FINISHED_COUNT_MINUTE_VIEW = View.create(Name.create("grpc.io/server/finished_count/minute"), str2, RpcMeasureConstants.RPC_SERVER_FINISHED_COUNT, COUNT, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats on the count of request messages per server RPC";
        RPC_SERVER_REQUEST_COUNT_MINUTE_VIEW = View.create(Name.create("grpc.io/server/request_count/minute"), str2, RpcMeasureConstants.RPC_SERVER_REQUEST_COUNT, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Minute stats on the count of response messages per server RPC";
        RPC_SERVER_RESPONSE_COUNT_MINUTE_VIEW = View.create(Name.create("grpc.io/server/response_count/minute"), str2, RpcMeasureConstants.RPC_SERVER_RESPONSE_COUNT, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_MINUTE);
        str2 = "Hour stats for server latency in msecs";
        RPC_SERVER_SERVER_LATENCY_HOUR_VIEW = View.create(Name.create("grpc.io/server/server_latency/hour"), str2, RpcMeasureConstants.RPC_SERVER_SERVER_LATENCY, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats for request size in bytes";
        RPC_SERVER_REQUEST_BYTES_HOUR_VIEW = View.create(Name.create("grpc.io/server/request_bytes/hour"), str2, RpcMeasureConstants.RPC_SERVER_REQUEST_BYTES, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats for response size in bytes";
        RPC_SERVER_RESPONSE_BYTES_HOUR_VIEW = View.create(Name.create("grpc.io/server/response_bytes/hour"), str2, RpcMeasureConstants.RPC_SERVER_RESPONSE_BYTES, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats for rpc errors";
        RPC_SERVER_ERROR_COUNT_HOUR_VIEW = View.create(Name.create("grpc.io/server/error_count/hour"), str2, RpcMeasureConstants.RPC_SERVER_ERROR_COUNT, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats for uncompressed request size in bytes";
        RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES_HOUR_VIEW = View.create(Name.create("grpc.io/server/uncompressed_request_bytes/hour"), str2, RpcMeasureConstants.RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats for uncompressed response size in bytes";
        RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES_HOUR_VIEW = View.create(Name.create("grpc.io/server/uncompressed_response_bytes/hour"), str2, RpcMeasureConstants.RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats for server elapsed time in msecs";
        RPC_SERVER_SERVER_ELAPSED_TIME_HOUR_VIEW = View.create(Name.create("grpc.io/server/server_elapsed_time/hour"), str2, RpcMeasureConstants.RPC_SERVER_SERVER_ELAPSED_TIME, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats on the number of server RPCs started";
        RPC_SERVER_STARTED_COUNT_HOUR_VIEW = View.create(Name.create("grpc.io/server/started_count/hour"), str2, RpcMeasureConstants.RPC_SERVER_STARTED_COUNT, COUNT, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats on the number of server RPCs finished";
        RPC_SERVER_FINISHED_COUNT_HOUR_VIEW = View.create(Name.create("grpc.io/server/finished_count/hour"), str2, RpcMeasureConstants.RPC_SERVER_FINISHED_COUNT, COUNT, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats on the count of request messages per server RPC";
        RPC_SERVER_REQUEST_COUNT_HOUR_VIEW = View.create(Name.create("grpc.io/server/request_count/hour"), str2, RpcMeasureConstants.RPC_SERVER_REQUEST_COUNT, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
        str2 = "Hour stats on the count of response messages per server RPC";
        RPC_SERVER_RESPONSE_COUNT_HOUR_VIEW = View.create(Name.create("grpc.io/server/response_count/hour"), str2, RpcMeasureConstants.RPC_SERVER_RESPONSE_COUNT, MEAN, Arrays.asList(new TagKey[]{RpcMeasureConstants.RPC_METHOD}), INTERVAL_HOUR);
    }

    RpcViewConstants() {
        throw new AssertionError();
    }
}