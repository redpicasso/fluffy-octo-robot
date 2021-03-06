package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Verify;
import io.grpc.CallOptions;
import io.grpc.CallOptions.Key;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.Deadline;
import io.grpc.MethodDescriptor;
import io.grpc.Status.Code;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

final class ServiceConfigInterceptor implements ClientInterceptor {
    static final Key<Provider> HEDGING_POLICY_KEY = Key.create("internal-hedging-policy");
    static final Key<Provider> RETRY_POLICY_KEY = Key.create("internal-retry-policy");
    private static final Logger logger = Logger.getLogger(ServiceConfigInterceptor.class.getName());
    private final int maxHedgedAttemptsLimit;
    private final int maxRetryAttemptsLimit;
    private volatile boolean nameResolveComplete;
    private final boolean retryEnabled;
    @VisibleForTesting
    final AtomicReference<Map<String, MethodInfo>> serviceMap = new AtomicReference();
    @VisibleForTesting
    final AtomicReference<Map<String, MethodInfo>> serviceMethodMap = new AtomicReference();

    static final class MethodInfo {
        final HedgingPolicy hedgingPolicy;
        final Integer maxInboundMessageSize;
        final Integer maxOutboundMessageSize;
        final RetryPolicy retryPolicy;
        final Long timeoutNanos;
        final Boolean waitForReady;

        MethodInfo(Map<String, Object> map, boolean z, int i, int i2) {
            this.timeoutNanos = ServiceConfigUtil.getTimeoutFromMethodConfig(map);
            this.waitForReady = ServiceConfigUtil.getWaitForReadyFromMethodConfig(map);
            this.maxInboundMessageSize = ServiceConfigUtil.getMaxResponseMessageBytesFromMethodConfig(map);
            Integer num = this.maxInboundMessageSize;
            boolean z2 = true;
            if (num != null) {
                Preconditions.checkArgument(num.intValue() >= 0, "maxInboundMessageSize %s exceeds bounds", this.maxInboundMessageSize);
            }
            this.maxOutboundMessageSize = ServiceConfigUtil.getMaxRequestMessageBytesFromMethodConfig(map);
            num = this.maxOutboundMessageSize;
            if (num != null) {
                if (num.intValue() < 0) {
                    z2 = false;
                }
                Preconditions.checkArgument(z2, "maxOutboundMessageSize %s exceeds bounds", this.maxOutboundMessageSize);
            }
            Map map2 = null;
            Map retryPolicyFromMethodConfig = z ? ServiceConfigUtil.getRetryPolicyFromMethodConfig(map) : null;
            this.retryPolicy = retryPolicyFromMethodConfig == null ? RetryPolicy.DEFAULT : retryPolicy(retryPolicyFromMethodConfig, i);
            if (z) {
                map2 = ServiceConfigUtil.getHedgingPolicyFromMethodConfig(map);
            }
            this.hedgingPolicy = map2 == null ? HedgingPolicy.DEFAULT : ServiceConfigInterceptor.hedgingPolicy(map2, i2);
        }

        public int hashCode() {
            return Objects.hashCode(this.timeoutNanos, this.waitForReady, this.maxInboundMessageSize, this.maxOutboundMessageSize, this.retryPolicy);
        }

        public boolean equals(Object obj) {
            boolean z = false;
            if (!(obj instanceof MethodInfo)) {
                return false;
            }
            MethodInfo methodInfo = (MethodInfo) obj;
            if (Objects.equal(this.timeoutNanos, methodInfo.timeoutNanos) && Objects.equal(this.waitForReady, methodInfo.waitForReady) && Objects.equal(this.maxInboundMessageSize, methodInfo.maxInboundMessageSize) && Objects.equal(this.maxOutboundMessageSize, methodInfo.maxOutboundMessageSize) && Objects.equal(this.retryPolicy, methodInfo.retryPolicy)) {
                z = true;
            }
            return z;
        }

        public String toString() {
            return MoreObjects.toStringHelper((Object) this).add("timeoutNanos", this.timeoutNanos).add("waitForReady", this.waitForReady).add("maxInboundMessageSize", this.maxInboundMessageSize).add("maxOutboundMessageSize", this.maxOutboundMessageSize).add("retryPolicy", this.retryPolicy).toString();
        }

        private static RetryPolicy retryPolicy(Map<String, Object> map, int i) {
            int intValue = ((Integer) Preconditions.checkNotNull(ServiceConfigUtil.getMaxAttemptsFromRetryPolicy(map), "maxAttempts cannot be empty")).intValue();
            Preconditions.checkArgument(intValue >= 2, "maxAttempts must be greater than 1: %s", intValue);
            int min = Math.min(intValue, i);
            long longValue = ((Long) Preconditions.checkNotNull(ServiceConfigUtil.getInitialBackoffNanosFromRetryPolicy(map), "initialBackoff cannot be empty")).longValue();
            Preconditions.checkArgument(longValue > 0, "initialBackoffNanos must be greater than 0: %s", longValue);
            long longValue2 = ((Long) Preconditions.checkNotNull(ServiceConfigUtil.getMaxBackoffNanosFromRetryPolicy(map), "maxBackoff cannot be empty")).longValue();
            Preconditions.checkArgument(longValue2 > 0, "maxBackoff must be greater than 0: %s", longValue2);
            double doubleValue = ((Double) Preconditions.checkNotNull(ServiceConfigUtil.getBackoffMultiplierFromRetryPolicy(map), "backoffMultiplier cannot be empty")).doubleValue();
            Preconditions.checkArgument(doubleValue > 0.0d, "backoffMultiplier must be greater than 0: %s", Double.valueOf(doubleValue));
            List<String> retryableStatusCodesFromRetryPolicy = ServiceConfigUtil.getRetryableStatusCodesFromRetryPolicy(map);
            Preconditions.checkNotNull(retryableStatusCodesFromRetryPolicy, "rawCodes must be present");
            Preconditions.checkArgument(retryableStatusCodesFromRetryPolicy.isEmpty() ^ true, "rawCodes can't be empty");
            Set noneOf = EnumSet.noneOf(Code.class);
            for (String str : retryableStatusCodesFromRetryPolicy) {
                Verify.verify("OK".equals(str) ^ true, "rawCode can not be \"OK\"", new Object[0]);
                noneOf.add(Code.valueOf(str));
            }
            return new RetryPolicy(min, longValue, longValue2, doubleValue, Collections.unmodifiableSet(noneOf));
        }
    }

    ServiceConfigInterceptor(boolean z, int i, int i2) {
        this.retryEnabled = z;
        this.maxRetryAttemptsLimit = i;
        this.maxHedgedAttemptsLimit = i2;
    }

    void handleUpdate(@Nonnull Map<String, Object> map) {
        Map hashMap = new HashMap();
        Map hashMap2 = new HashMap();
        List<Object> methodConfigFromServiceConfig = ServiceConfigUtil.getMethodConfigFromServiceConfig(map);
        if (methodConfigFromServiceConfig == null) {
            logger.log(Level.FINE, "No method configs found, skipping");
            this.nameResolveComplete = true;
            return;
        }
        for (Object obj : methodConfigFromServiceConfig) {
            MethodInfo methodInfo = new MethodInfo(obj, this.retryEnabled, this.maxRetryAttemptsLimit, this.maxHedgedAttemptsLimit);
            List<Map> nameListFromMethodConfig = ServiceConfigUtil.getNameListFromMethodConfig(obj);
            boolean z = (nameListFromMethodConfig == null || nameListFromMethodConfig.isEmpty()) ? false : true;
            Preconditions.checkArgument(z, "no names in method config %s", obj);
            for (Map map2 : nameListFromMethodConfig) {
                Object serviceFromName = ServiceConfigUtil.getServiceFromName(map2);
                Preconditions.checkArgument(Strings.isNullOrEmpty(serviceFromName) ^ true, "missing service name");
                String methodFromName = ServiceConfigUtil.getMethodFromName(map2);
                if (Strings.isNullOrEmpty(methodFromName)) {
                    Preconditions.checkArgument(hashMap2.containsKey(serviceFromName) ^ true, "Duplicate service %s", serviceFromName);
                    hashMap2.put(serviceFromName, methodInfo);
                } else {
                    Object generateFullMethodName = MethodDescriptor.generateFullMethodName(serviceFromName, methodFromName);
                    Preconditions.checkArgument(hashMap.containsKey(generateFullMethodName) ^ true, "Duplicate method name %s", generateFullMethodName);
                    hashMap.put(generateFullMethodName, methodInfo);
                }
            }
        }
        this.serviceMethodMap.set(Collections.unmodifiableMap(hashMap));
        this.serviceMap.set(Collections.unmodifiableMap(hashMap2));
        this.nameResolveComplete = true;
    }

    private static HedgingPolicy hedgingPolicy(Map<String, Object> map, int i) {
        int intValue = ((Integer) Preconditions.checkNotNull(ServiceConfigUtil.getMaxAttemptsFromHedgingPolicy(map), "maxAttempts cannot be empty")).intValue();
        Preconditions.checkArgument(intValue >= 2, "maxAttempts must be greater than 1: %s", intValue);
        i = Math.min(intValue, i);
        long longValue = ((Long) Preconditions.checkNotNull(ServiceConfigUtil.getHedgingDelayNanosFromHedgingPolicy(map), "hedgingDelay cannot be empty")).longValue();
        Preconditions.checkArgument(longValue >= 0, "hedgingDelay must not be negative: %s", longValue);
        List<String> nonFatalStatusCodesFromHedgingPolicy = ServiceConfigUtil.getNonFatalStatusCodesFromHedgingPolicy(map);
        Preconditions.checkNotNull(nonFatalStatusCodesFromHedgingPolicy, "rawCodes must be present");
        Preconditions.checkArgument(nonFatalStatusCodesFromHedgingPolicy.isEmpty() ^ true, "rawCodes can't be empty");
        Set noneOf = EnumSet.noneOf(Code.class);
        for (String str : nonFatalStatusCodesFromHedgingPolicy) {
            Verify.verify("OK".equals(str) ^ true, "rawCode can not be \"OK\"", new Object[0]);
            noneOf.add(Code.valueOf(str));
        }
        return new HedgingPolicy(i, longValue, Collections.unmodifiableSet(noneOf));
    }

    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(final MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        if (this.retryEnabled) {
            if (this.nameResolveComplete) {
                final RetryPolicy retryPolicyFromConfig = getRetryPolicyFromConfig(methodDescriptor);
                final HedgingPolicy hedgingPolicyFromConfig = getHedgingPolicyFromConfig(methodDescriptor);
                boolean z = retryPolicyFromConfig.equals(RetryPolicy.DEFAULT) || hedgingPolicyFromConfig.equals(HedgingPolicy.DEFAULT);
                Verify.verify(z, "Can not apply both retry and hedging policy for the method '%s'", (Object) methodDescriptor);
                callOptions = callOptions.withOption(RETRY_POLICY_KEY, new Provider() {
                    public RetryPolicy get() {
                        return retryPolicyFromConfig;
                    }
                }).withOption(HEDGING_POLICY_KEY, new Provider() {
                    public HedgingPolicy get() {
                        return hedgingPolicyFromConfig;
                    }
                });
            } else {
                callOptions = callOptions.withOption(RETRY_POLICY_KEY, new Provider() {
                    public RetryPolicy get() {
                        if (ServiceConfigInterceptor.this.nameResolveComplete) {
                            return ServiceConfigInterceptor.this.getRetryPolicyFromConfig(methodDescriptor);
                        }
                        return RetryPolicy.DEFAULT;
                    }
                }).withOption(HEDGING_POLICY_KEY, new Provider() {
                    public HedgingPolicy get() {
                        if (!ServiceConfigInterceptor.this.nameResolveComplete) {
                            return HedgingPolicy.DEFAULT;
                        }
                        HedgingPolicy hedgingPolicyFromConfig = ServiceConfigInterceptor.this.getHedgingPolicyFromConfig(methodDescriptor);
                        boolean z = hedgingPolicyFromConfig.equals(HedgingPolicy.DEFAULT) || ServiceConfigInterceptor.this.getRetryPolicyFromConfig(methodDescriptor).equals(RetryPolicy.DEFAULT);
                        Verify.verify(z, "Can not apply both retry and hedging policy for the method '%s'", methodDescriptor);
                        return hedgingPolicyFromConfig;
                    }
                });
            }
        }
        MethodInfo methodInfo = getMethodInfo(methodDescriptor);
        if (methodInfo == null) {
            return channel.newCall(methodDescriptor, callOptions);
        }
        Integer maxInboundMessageSize;
        if (methodInfo.timeoutNanos != null) {
            Deadline after = Deadline.after(methodInfo.timeoutNanos.longValue(), TimeUnit.NANOSECONDS);
            Deadline deadline = callOptions.getDeadline();
            if (deadline == null || after.compareTo(deadline) < 0) {
                callOptions = callOptions.withDeadline(after);
            }
        }
        if (methodInfo.waitForReady != null) {
            callOptions = methodInfo.waitForReady.booleanValue() ? callOptions.withWaitForReady() : callOptions.withoutWaitForReady();
        }
        if (methodInfo.maxInboundMessageSize != null) {
            maxInboundMessageSize = callOptions.getMaxInboundMessageSize();
            if (maxInboundMessageSize != null) {
                callOptions = callOptions.withMaxInboundMessageSize(Math.min(maxInboundMessageSize.intValue(), methodInfo.maxInboundMessageSize.intValue()));
            } else {
                callOptions = callOptions.withMaxInboundMessageSize(methodInfo.maxInboundMessageSize.intValue());
            }
        }
        if (methodInfo.maxOutboundMessageSize != null) {
            maxInboundMessageSize = callOptions.getMaxOutboundMessageSize();
            if (maxInboundMessageSize != null) {
                callOptions = callOptions.withMaxOutboundMessageSize(Math.min(maxInboundMessageSize.intValue(), methodInfo.maxOutboundMessageSize.intValue()));
            } else {
                callOptions = callOptions.withMaxOutboundMessageSize(methodInfo.maxOutboundMessageSize.intValue());
            }
        }
        return channel.newCall(methodDescriptor, callOptions);
    }

    @CheckForNull
    private MethodInfo getMethodInfo(MethodDescriptor<?, ?> methodDescriptor) {
        Map map = (Map) this.serviceMethodMap.get();
        MethodInfo methodInfo = map != null ? (MethodInfo) map.get(methodDescriptor.getFullMethodName()) : null;
        if (methodInfo != null) {
            return methodInfo;
        }
        Map map2 = (Map) this.serviceMap.get();
        return map2 != null ? (MethodInfo) map2.get(MethodDescriptor.extractFullServiceName(methodDescriptor.getFullMethodName())) : methodInfo;
    }

    @VisibleForTesting
    RetryPolicy getRetryPolicyFromConfig(MethodDescriptor<?, ?> methodDescriptor) {
        MethodInfo methodInfo = getMethodInfo(methodDescriptor);
        return methodInfo == null ? RetryPolicy.DEFAULT : methodInfo.retryPolicy;
    }

    @VisibleForTesting
    HedgingPolicy getHedgingPolicyFromConfig(MethodDescriptor<?, ?> methodDescriptor) {
        MethodInfo methodInfo = getMethodInfo(methodDescriptor);
        return methodInfo == null ? HedgingPolicy.DEFAULT : methodInfo.hedgingPolicy;
    }
}
