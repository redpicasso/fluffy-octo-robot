package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

@VisibleForTesting
public final class ServiceConfigUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long DURATION_SECONDS_MAX = 315576000000L;
    private static final long DURATION_SECONDS_MIN = -315576000000L;
    private static final String HEDGING_POLICY_HEDGING_DELAY_KEY = "hedgingDelay";
    private static final String HEDGING_POLICY_MAX_ATTEMPTS_KEY = "maxAttempts";
    private static final String HEDGING_POLICY_NON_FATAL_STATUS_CODES_KEY = "nonFatalStatusCodes";
    private static final String METHOD_CONFIG_HEDGING_POLICY_KEY = "hedgingPolicy";
    private static final String METHOD_CONFIG_MAX_REQUEST_MESSAGE_BYTES_KEY = "maxRequestMessageBytes";
    private static final String METHOD_CONFIG_MAX_RESPONSE_MESSAGE_BYTES_KEY = "maxResponseMessageBytes";
    private static final String METHOD_CONFIG_NAME_KEY = "name";
    private static final String METHOD_CONFIG_RETRY_POLICY_KEY = "retryPolicy";
    private static final String METHOD_CONFIG_TIMEOUT_KEY = "timeout";
    private static final String METHOD_CONFIG_WAIT_FOR_READY_KEY = "waitForReady";
    private static final String NAME_METHOD_KEY = "method";
    private static final String NAME_SERVICE_KEY = "service";
    private static final long NANOS_PER_SECOND = TimeUnit.SECONDS.toNanos(1);
    private static final String RETRY_POLICY_BACKOFF_MULTIPLIER_KEY = "backoffMultiplier";
    private static final String RETRY_POLICY_INITIAL_BACKOFF_KEY = "initialBackoff";
    private static final String RETRY_POLICY_MAX_ATTEMPTS_KEY = "maxAttempts";
    private static final String RETRY_POLICY_MAX_BACKOFF_KEY = "maxBackoff";
    private static final String RETRY_POLICY_RETRYABLE_STATUS_CODES_KEY = "retryableStatusCodes";
    private static final String SERVICE_CONFIG_LOAD_BALANCING_POLICY_KEY = "loadBalancingPolicy";
    private static final String SERVICE_CONFIG_METHOD_CONFIG_KEY = "methodConfig";
    private static final String SERVICE_CONFIG_STICKINESS_METADATA_KEY = "stickinessMetadataKey";

    private static long saturatedAdd(long j, long j2) {
        long j3 = j + j2;
        int i = 1;
        int i2 = (j2 ^ j) < 0 ? 1 : 0;
        if ((j ^ j3) < 0) {
            i = 0;
        }
        return (i2 | i) != 0 ? j3 : ((j3 >>> 63) ^ 1) + Long.MAX_VALUE;
    }

    private ServiceConfigUtil() {
    }

    @Nullable
    static Throttle getThrottlePolicy(@Nullable Map<String, Object> map) {
        if (map != null) {
            String str = "retryThrottling";
            if (map.containsKey(str)) {
                Map object = getObject(map, str);
                float floatValue = getDouble(object, "maxTokens").floatValue();
                float floatValue2 = getDouble(object, "tokenRatio").floatValue();
                boolean z = true;
                Preconditions.checkState(floatValue > 0.0f, "maxToken should be greater than zero");
                if (floatValue2 <= 0.0f) {
                    z = false;
                }
                Preconditions.checkState(z, "tokenRatio should be greater than zero");
                return new Throttle(floatValue, floatValue2);
            }
        }
        return null;
    }

    @Nullable
    static Integer getMaxAttemptsFromRetryPolicy(Map<String, Object> map) {
        String str = "maxAttempts";
        if (map.containsKey(str)) {
            return Integer.valueOf(getDouble(map, str).intValue());
        }
        return null;
    }

    @Nullable
    static Long getInitialBackoffNanosFromRetryPolicy(Map<String, Object> map) {
        String str = RETRY_POLICY_INITIAL_BACKOFF_KEY;
        if (!map.containsKey(str)) {
            return null;
        }
        try {
            return Long.valueOf(parseDuration(getString((Map) map, str)));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    static Long getMaxBackoffNanosFromRetryPolicy(Map<String, Object> map) {
        String str = RETRY_POLICY_MAX_BACKOFF_KEY;
        if (!map.containsKey(str)) {
            return null;
        }
        try {
            return Long.valueOf(parseDuration(getString((Map) map, str)));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    static Double getBackoffMultiplierFromRetryPolicy(Map<String, Object> map) {
        String str = RETRY_POLICY_BACKOFF_MULTIPLIER_KEY;
        if (map.containsKey(str)) {
            return getDouble(map, str);
        }
        return null;
    }

    @Nullable
    static List<String> getRetryableStatusCodesFromRetryPolicy(Map<String, Object> map) {
        String str = RETRY_POLICY_RETRYABLE_STATUS_CODES_KEY;
        if (map.containsKey(str)) {
            return checkStringList(getList(map, str));
        }
        return null;
    }

    @Nullable
    static Integer getMaxAttemptsFromHedgingPolicy(Map<String, Object> map) {
        String str = "maxAttempts";
        if (map.containsKey(str)) {
            return Integer.valueOf(getDouble(map, str).intValue());
        }
        return null;
    }

    @Nullable
    static Long getHedgingDelayNanosFromHedgingPolicy(Map<String, Object> map) {
        String str = HEDGING_POLICY_HEDGING_DELAY_KEY;
        if (!map.containsKey(str)) {
            return null;
        }
        try {
            return Long.valueOf(parseDuration(getString((Map) map, str)));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    static List<String> getNonFatalStatusCodesFromHedgingPolicy(Map<String, Object> map) {
        String str = HEDGING_POLICY_NON_FATAL_STATUS_CODES_KEY;
        if (map.containsKey(str)) {
            return checkStringList(getList(map, str));
        }
        return null;
    }

    @Nullable
    static String getServiceFromName(Map<String, Object> map) {
        String str = "service";
        if (map.containsKey(str)) {
            return getString((Map) map, str);
        }
        return null;
    }

    @Nullable
    static String getMethodFromName(Map<String, Object> map) {
        String str = "method";
        if (map.containsKey(str)) {
            return getString((Map) map, str);
        }
        return null;
    }

    @Nullable
    static Map<String, Object> getRetryPolicyFromMethodConfig(Map<String, Object> map) {
        String str = METHOD_CONFIG_RETRY_POLICY_KEY;
        if (map.containsKey(str)) {
            return getObject(map, str);
        }
        return null;
    }

    @Nullable
    static Map<String, Object> getHedgingPolicyFromMethodConfig(Map<String, Object> map) {
        String str = METHOD_CONFIG_HEDGING_POLICY_KEY;
        if (map.containsKey(str)) {
            return getObject(map, str);
        }
        return null;
    }

    @Nullable
    static List<Map<String, Object>> getNameListFromMethodConfig(Map<String, Object> map) {
        String str = "name";
        if (map.containsKey(str)) {
            return checkObjectList(getList(map, str));
        }
        return null;
    }

    @Nullable
    static Long getTimeoutFromMethodConfig(Map<String, Object> map) {
        String str = METHOD_CONFIG_TIMEOUT_KEY;
        if (!map.containsKey(str)) {
            return null;
        }
        try {
            return Long.valueOf(parseDuration(getString((Map) map, str)));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    static Boolean getWaitForReadyFromMethodConfig(Map<String, Object> map) {
        String str = METHOD_CONFIG_WAIT_FOR_READY_KEY;
        if (map.containsKey(str)) {
            return getBoolean(map, str);
        }
        return null;
    }

    @Nullable
    static Integer getMaxRequestMessageBytesFromMethodConfig(Map<String, Object> map) {
        String str = METHOD_CONFIG_MAX_REQUEST_MESSAGE_BYTES_KEY;
        if (map.containsKey(str)) {
            return Integer.valueOf(getDouble(map, str).intValue());
        }
        return null;
    }

    @Nullable
    static Integer getMaxResponseMessageBytesFromMethodConfig(Map<String, Object> map) {
        String str = METHOD_CONFIG_MAX_RESPONSE_MESSAGE_BYTES_KEY;
        if (map.containsKey(str)) {
            return Integer.valueOf(getDouble(map, str).intValue());
        }
        return null;
    }

    @Nullable
    static List<Map<String, Object>> getMethodConfigFromServiceConfig(Map<String, Object> map) {
        String str = SERVICE_CONFIG_METHOD_CONFIG_KEY;
        if (map.containsKey(str)) {
            return checkObjectList(getList(map, str));
        }
        return null;
    }

    @Nullable
    @VisibleForTesting
    public static String getLoadBalancingPolicyFromServiceConfig(Map<String, Object> map) {
        String str = SERVICE_CONFIG_LOAD_BALANCING_POLICY_KEY;
        if (map.containsKey(str)) {
            return getString((Map) map, str);
        }
        return null;
    }

    @Nullable
    public static String getStickinessMetadataKeyFromServiceConfig(Map<String, Object> map) {
        String str = SERVICE_CONFIG_STICKINESS_METADATA_KEY;
        if (map.containsKey(str)) {
            return getString((Map) map, str);
        }
        return null;
    }

    static List<Object> getList(Map<String, Object> map, String str) {
        Object checkNotNull = Preconditions.checkNotNull(map.get(str), "no such key %s", (Object) str);
        if (checkNotNull instanceof List) {
            return (List) checkNotNull;
        }
        throw new ClassCastException(String.format("value %s for key %s in %s is not List", new Object[]{checkNotNull, str, map}));
    }

    static Map<String, Object> getObject(Map<String, Object> map, String str) {
        Object checkNotNull = Preconditions.checkNotNull(map.get(str), "no such key %s", (Object) str);
        if (checkNotNull instanceof Map) {
            return (Map) checkNotNull;
        }
        throw new ClassCastException(String.format("value %s for key %s in %s is not object", new Object[]{checkNotNull, str, map}));
    }

    static Double getDouble(Map<String, Object> map, String str) {
        Object checkNotNull = Preconditions.checkNotNull(map.get(str), "no such key %s", (Object) str);
        if (checkNotNull instanceof Double) {
            return (Double) checkNotNull;
        }
        throw new ClassCastException(String.format("value %s for key %s in %s is not Double", new Object[]{checkNotNull, str, map}));
    }

    static String getString(Map<String, Object> map, String str) {
        Object checkNotNull = Preconditions.checkNotNull(map.get(str), "no such key %s", (Object) str);
        if (checkNotNull instanceof String) {
            return (String) checkNotNull;
        }
        throw new ClassCastException(String.format("value %s for key %s in %s is not String", new Object[]{checkNotNull, str, map}));
    }

    static String getString(List<Object> list, int i) {
        Object checkNotNull = Preconditions.checkNotNull(list.get(i), "idx %s in %s is null", i, (Object) list);
        if (checkNotNull instanceof String) {
            return (String) checkNotNull;
        }
        throw new ClassCastException(String.format("value %s for idx %d in %s is not String", new Object[]{checkNotNull, Integer.valueOf(i), list}));
    }

    static Boolean getBoolean(Map<String, Object> map, String str) {
        Object checkNotNull = Preconditions.checkNotNull(map.get(str), "no such key %s", (Object) str);
        if (checkNotNull instanceof Boolean) {
            return (Boolean) checkNotNull;
        }
        throw new ClassCastException(String.format("value %s for key %s in %s is not Boolean", new Object[]{checkNotNull, str, map}));
    }

    private static List<Map<String, Object>> checkObjectList(List<Object> list) {
        int i = 0;
        while (i < list.size()) {
            if (list.get(i) instanceof Map) {
                i++;
            } else {
                throw new ClassCastException(String.format("value %s for idx %d in %s is not object", new Object[]{list.get(i), Integer.valueOf(i), list}));
            }
        }
        return list;
    }

    static List<String> checkStringList(List<Object> list) {
        int i = 0;
        while (i < list.size()) {
            if (list.get(i) instanceof String) {
                i++;
            } else {
                throw new ClassCastException(String.format("value %s for idx %d in %s is not string", new Object[]{list.get(i), Integer.valueOf(i), list}));
            }
        }
        return list;
    }

    private static long parseDuration(String str) throws ParseException {
        String str2 = "Invalid duration string: ";
        StringBuilder stringBuilder;
        if (str.isEmpty() || str.charAt(str.length() - 1) != 's') {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(str);
            throw new ParseException(stringBuilder.toString(), 0);
        }
        Object obj;
        String substring;
        if (str.charAt(0) == '-') {
            str = str.substring(1);
            obj = 1;
        } else {
            obj = null;
        }
        String substring2 = str.substring(0, str.length() - 1);
        int indexOf = substring2.indexOf(46);
        if (indexOf != -1) {
            substring = substring2.substring(indexOf + 1);
            substring2 = substring2.substring(0, indexOf);
        } else {
            substring = "";
        }
        long parseLong = Long.parseLong(substring2);
        int parseNanos = substring.isEmpty() ? 0 : parseNanos(substring);
        if (parseLong >= 0) {
            if (obj != null) {
                parseLong = -parseLong;
                parseNanos = -parseNanos;
            }
            try {
                return normalizedDuration(parseLong, parseNanos);
            } catch (IllegalArgumentException unused) {
                throw new ParseException("Duration value is out of range.", 0);
            }
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append(str);
        throw new ParseException(stringBuilder.toString(), 0);
    }

    private static int parseNanos(String str) throws ParseException {
        int i = 0;
        int i2 = 0;
        while (i < 9) {
            i2 *= 10;
            if (i < str.length()) {
                if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                    throw new ParseException("Invalid nanoseconds.", 0);
                }
                i2 += str.charAt(i) - 48;
            }
            i++;
        }
        return i2;
    }

    private static long normalizedDuration(long j, int i) {
        long j2 = (long) i;
        long j3 = NANOS_PER_SECOND;
        if (j2 <= (-j3) || j2 >= j3) {
            j = LongMath.checkedAdd(j, j2 / NANOS_PER_SECOND);
            i = (int) (j2 % NANOS_PER_SECOND);
        }
        if (j > 0 && i < 0) {
            i = (int) (((long) i) + NANOS_PER_SECOND);
            j--;
        }
        if (j < 0 && i > 0) {
            i = (int) (((long) i) - NANOS_PER_SECOND);
            j++;
        }
        if (durationIsValid(j, i)) {
            return saturatedAdd(TimeUnit.SECONDS.toNanos(j), (long) i);
        }
        throw new IllegalArgumentException(String.format("Duration is not valid. See proto definition for valid values. Seconds (%s) must be in range [-315,576,000,000, +315,576,000,000]. Nanos (%s) must be in range [-999,999,999, +999,999,999]. Nanos must have the same sign as seconds", new Object[]{Long.valueOf(j), Integer.valueOf(i)}));
    }

    private static boolean durationIsValid(long j, int i) {
        if (j >= DURATION_SECONDS_MIN && j <= DURATION_SECONDS_MAX) {
            long j2 = (long) i;
            if (j2 >= -999999999 && j2 < NANOS_PER_SECOND) {
                int i2 = (j > 0 ? 1 : (j == 0 ? 0 : -1));
                if ((i2 >= 0 && i >= 0) || (i2 <= 0 && i <= 0)) {
                    return true;
                }
            }
        }
        return false;
    }
}
