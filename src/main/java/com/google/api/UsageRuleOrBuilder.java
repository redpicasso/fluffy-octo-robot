package com.google.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public interface UsageRuleOrBuilder extends MessageLiteOrBuilder {
    boolean getAllowUnregisteredCalls();

    String getSelector();

    ByteString getSelectorBytes();

    boolean getSkipServiceControl();
}
