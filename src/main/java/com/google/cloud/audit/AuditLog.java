package com.google.cloud.audit;

import com.drew.metadata.exif.makernotes.NikonType2MakernoteDirectory;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.MethodToInvoke;
import com.google.protobuf.Internal.ProtobufList;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;
import com.google.protobuf.Struct;
import com.google.rpc.Status;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public final class AuditLog extends GeneratedMessageLite<AuditLog, Builder> implements AuditLogOrBuilder {
    public static final int AUTHENTICATION_INFO_FIELD_NUMBER = 3;
    public static final int AUTHORIZATION_INFO_FIELD_NUMBER = 9;
    private static final AuditLog DEFAULT_INSTANCE = new AuditLog();
    public static final int METHOD_NAME_FIELD_NUMBER = 8;
    public static final int NUM_RESPONSE_ITEMS_FIELD_NUMBER = 12;
    private static volatile Parser<AuditLog> PARSER = null;
    public static final int REQUEST_FIELD_NUMBER = 16;
    public static final int REQUEST_METADATA_FIELD_NUMBER = 4;
    public static final int RESOURCE_NAME_FIELD_NUMBER = 11;
    public static final int RESPONSE_FIELD_NUMBER = 17;
    public static final int SERVICE_DATA_FIELD_NUMBER = 15;
    public static final int SERVICE_NAME_FIELD_NUMBER = 7;
    public static final int STATUS_FIELD_NUMBER = 2;
    private AuthenticationInfo authenticationInfo_;
    private ProtobufList<AuthorizationInfo> authorizationInfo_ = GeneratedMessageLite.emptyProtobufList();
    private int bitField0_;
    private String methodName_;
    private long numResponseItems_;
    private RequestMetadata requestMetadata_;
    private Struct request_;
    private String resourceName_;
    private Struct response_;
    private Any serviceData_;
    private String serviceName_;
    private Status status_;

    /* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<AuditLog, Builder> implements AuditLogOrBuilder {
        private Builder() {
            super(AuditLog.DEFAULT_INSTANCE);
        }

        public String getServiceName() {
            return ((AuditLog) this.instance).getServiceName();
        }

        public ByteString getServiceNameBytes() {
            return ((AuditLog) this.instance).getServiceNameBytes();
        }

        public Builder setServiceName(String str) {
            copyOnWrite();
            ((AuditLog) this.instance).setServiceName(str);
            return this;
        }

        public Builder clearServiceName() {
            copyOnWrite();
            ((AuditLog) this.instance).clearServiceName();
            return this;
        }

        public Builder setServiceNameBytes(ByteString byteString) {
            copyOnWrite();
            ((AuditLog) this.instance).setServiceNameBytes(byteString);
            return this;
        }

        public String getMethodName() {
            return ((AuditLog) this.instance).getMethodName();
        }

        public ByteString getMethodNameBytes() {
            return ((AuditLog) this.instance).getMethodNameBytes();
        }

        public Builder setMethodName(String str) {
            copyOnWrite();
            ((AuditLog) this.instance).setMethodName(str);
            return this;
        }

        public Builder clearMethodName() {
            copyOnWrite();
            ((AuditLog) this.instance).clearMethodName();
            return this;
        }

        public Builder setMethodNameBytes(ByteString byteString) {
            copyOnWrite();
            ((AuditLog) this.instance).setMethodNameBytes(byteString);
            return this;
        }

        public String getResourceName() {
            return ((AuditLog) this.instance).getResourceName();
        }

        public ByteString getResourceNameBytes() {
            return ((AuditLog) this.instance).getResourceNameBytes();
        }

        public Builder setResourceName(String str) {
            copyOnWrite();
            ((AuditLog) this.instance).setResourceName(str);
            return this;
        }

        public Builder clearResourceName() {
            copyOnWrite();
            ((AuditLog) this.instance).clearResourceName();
            return this;
        }

        public Builder setResourceNameBytes(ByteString byteString) {
            copyOnWrite();
            ((AuditLog) this.instance).setResourceNameBytes(byteString);
            return this;
        }

        public long getNumResponseItems() {
            return ((AuditLog) this.instance).getNumResponseItems();
        }

        public Builder setNumResponseItems(long j) {
            copyOnWrite();
            ((AuditLog) this.instance).setNumResponseItems(j);
            return this;
        }

        public Builder clearNumResponseItems() {
            copyOnWrite();
            ((AuditLog) this.instance).clearNumResponseItems();
            return this;
        }

        public boolean hasStatus() {
            return ((AuditLog) this.instance).hasStatus();
        }

        public Status getStatus() {
            return ((AuditLog) this.instance).getStatus();
        }

        public Builder setStatus(Status status) {
            copyOnWrite();
            ((AuditLog) this.instance).setStatus(status);
            return this;
        }

        public Builder setStatus(com.google.rpc.Status.Builder builder) {
            copyOnWrite();
            ((AuditLog) this.instance).setStatus(builder);
            return this;
        }

        public Builder mergeStatus(Status status) {
            copyOnWrite();
            ((AuditLog) this.instance).mergeStatus(status);
            return this;
        }

        public Builder clearStatus() {
            copyOnWrite();
            ((AuditLog) this.instance).clearStatus();
            return this;
        }

        public boolean hasAuthenticationInfo() {
            return ((AuditLog) this.instance).hasAuthenticationInfo();
        }

        public AuthenticationInfo getAuthenticationInfo() {
            return ((AuditLog) this.instance).getAuthenticationInfo();
        }

        public Builder setAuthenticationInfo(AuthenticationInfo authenticationInfo) {
            copyOnWrite();
            ((AuditLog) this.instance).setAuthenticationInfo(authenticationInfo);
            return this;
        }

        public Builder setAuthenticationInfo(com.google.cloud.audit.AuthenticationInfo.Builder builder) {
            copyOnWrite();
            ((AuditLog) this.instance).setAuthenticationInfo(builder);
            return this;
        }

        public Builder mergeAuthenticationInfo(AuthenticationInfo authenticationInfo) {
            copyOnWrite();
            ((AuditLog) this.instance).mergeAuthenticationInfo(authenticationInfo);
            return this;
        }

        public Builder clearAuthenticationInfo() {
            copyOnWrite();
            ((AuditLog) this.instance).clearAuthenticationInfo();
            return this;
        }

        public List<AuthorizationInfo> getAuthorizationInfoList() {
            return Collections.unmodifiableList(((AuditLog) this.instance).getAuthorizationInfoList());
        }

        public int getAuthorizationInfoCount() {
            return ((AuditLog) this.instance).getAuthorizationInfoCount();
        }

        public AuthorizationInfo getAuthorizationInfo(int i) {
            return ((AuditLog) this.instance).getAuthorizationInfo(i);
        }

        public Builder setAuthorizationInfo(int i, AuthorizationInfo authorizationInfo) {
            copyOnWrite();
            ((AuditLog) this.instance).setAuthorizationInfo(i, authorizationInfo);
            return this;
        }

        public Builder setAuthorizationInfo(int i, com.google.cloud.audit.AuthorizationInfo.Builder builder) {
            copyOnWrite();
            ((AuditLog) this.instance).setAuthorizationInfo(i, builder);
            return this;
        }

        public Builder addAuthorizationInfo(AuthorizationInfo authorizationInfo) {
            copyOnWrite();
            ((AuditLog) this.instance).addAuthorizationInfo(authorizationInfo);
            return this;
        }

        public Builder addAuthorizationInfo(int i, AuthorizationInfo authorizationInfo) {
            copyOnWrite();
            ((AuditLog) this.instance).addAuthorizationInfo(i, authorizationInfo);
            return this;
        }

        public Builder addAuthorizationInfo(com.google.cloud.audit.AuthorizationInfo.Builder builder) {
            copyOnWrite();
            ((AuditLog) this.instance).addAuthorizationInfo(builder);
            return this;
        }

        public Builder addAuthorizationInfo(int i, com.google.cloud.audit.AuthorizationInfo.Builder builder) {
            copyOnWrite();
            ((AuditLog) this.instance).addAuthorizationInfo(i, builder);
            return this;
        }

        public Builder addAllAuthorizationInfo(Iterable<? extends AuthorizationInfo> iterable) {
            copyOnWrite();
            ((AuditLog) this.instance).addAllAuthorizationInfo(iterable);
            return this;
        }

        public Builder clearAuthorizationInfo() {
            copyOnWrite();
            ((AuditLog) this.instance).clearAuthorizationInfo();
            return this;
        }

        public Builder removeAuthorizationInfo(int i) {
            copyOnWrite();
            ((AuditLog) this.instance).removeAuthorizationInfo(i);
            return this;
        }

        public boolean hasRequestMetadata() {
            return ((AuditLog) this.instance).hasRequestMetadata();
        }

        public RequestMetadata getRequestMetadata() {
            return ((AuditLog) this.instance).getRequestMetadata();
        }

        public Builder setRequestMetadata(RequestMetadata requestMetadata) {
            copyOnWrite();
            ((AuditLog) this.instance).setRequestMetadata(requestMetadata);
            return this;
        }

        public Builder setRequestMetadata(com.google.cloud.audit.RequestMetadata.Builder builder) {
            copyOnWrite();
            ((AuditLog) this.instance).setRequestMetadata(builder);
            return this;
        }

        public Builder mergeRequestMetadata(RequestMetadata requestMetadata) {
            copyOnWrite();
            ((AuditLog) this.instance).mergeRequestMetadata(requestMetadata);
            return this;
        }

        public Builder clearRequestMetadata() {
            copyOnWrite();
            ((AuditLog) this.instance).clearRequestMetadata();
            return this;
        }

        public boolean hasRequest() {
            return ((AuditLog) this.instance).hasRequest();
        }

        public Struct getRequest() {
            return ((AuditLog) this.instance).getRequest();
        }

        public Builder setRequest(Struct struct) {
            copyOnWrite();
            ((AuditLog) this.instance).setRequest(struct);
            return this;
        }

        public Builder setRequest(com.google.protobuf.Struct.Builder builder) {
            copyOnWrite();
            ((AuditLog) this.instance).setRequest(builder);
            return this;
        }

        public Builder mergeRequest(Struct struct) {
            copyOnWrite();
            ((AuditLog) this.instance).mergeRequest(struct);
            return this;
        }

        public Builder clearRequest() {
            copyOnWrite();
            ((AuditLog) this.instance).clearRequest();
            return this;
        }

        public boolean hasResponse() {
            return ((AuditLog) this.instance).hasResponse();
        }

        public Struct getResponse() {
            return ((AuditLog) this.instance).getResponse();
        }

        public Builder setResponse(Struct struct) {
            copyOnWrite();
            ((AuditLog) this.instance).setResponse(struct);
            return this;
        }

        public Builder setResponse(com.google.protobuf.Struct.Builder builder) {
            copyOnWrite();
            ((AuditLog) this.instance).setResponse(builder);
            return this;
        }

        public Builder mergeResponse(Struct struct) {
            copyOnWrite();
            ((AuditLog) this.instance).mergeResponse(struct);
            return this;
        }

        public Builder clearResponse() {
            copyOnWrite();
            ((AuditLog) this.instance).clearResponse();
            return this;
        }

        public boolean hasServiceData() {
            return ((AuditLog) this.instance).hasServiceData();
        }

        public Any getServiceData() {
            return ((AuditLog) this.instance).getServiceData();
        }

        public Builder setServiceData(Any any) {
            copyOnWrite();
            ((AuditLog) this.instance).setServiceData(any);
            return this;
        }

        public Builder setServiceData(com.google.protobuf.Any.Builder builder) {
            copyOnWrite();
            ((AuditLog) this.instance).setServiceData(builder);
            return this;
        }

        public Builder mergeServiceData(Any any) {
            copyOnWrite();
            ((AuditLog) this.instance).mergeServiceData(any);
            return this;
        }

        public Builder clearServiceData() {
            copyOnWrite();
            ((AuditLog) this.instance).clearServiceData();
            return this;
        }
    }

    private AuditLog() {
        String str = "";
        this.serviceName_ = str;
        this.methodName_ = str;
        this.resourceName_ = str;
    }

    public String getServiceName() {
        return this.serviceName_;
    }

    public ByteString getServiceNameBytes() {
        return ByteString.copyFromUtf8(this.serviceName_);
    }

    private void setServiceName(String str) {
        if (str != null) {
            this.serviceName_ = str;
            return;
        }
        throw new NullPointerException();
    }

    private void clearServiceName() {
        this.serviceName_ = getDefaultInstance().getServiceName();
    }

    private void setServiceNameBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.serviceName_ = byteString.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public String getMethodName() {
        return this.methodName_;
    }

    public ByteString getMethodNameBytes() {
        return ByteString.copyFromUtf8(this.methodName_);
    }

    private void setMethodName(String str) {
        if (str != null) {
            this.methodName_ = str;
            return;
        }
        throw new NullPointerException();
    }

    private void clearMethodName() {
        this.methodName_ = getDefaultInstance().getMethodName();
    }

    private void setMethodNameBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.methodName_ = byteString.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public String getResourceName() {
        return this.resourceName_;
    }

    public ByteString getResourceNameBytes() {
        return ByteString.copyFromUtf8(this.resourceName_);
    }

    private void setResourceName(String str) {
        if (str != null) {
            this.resourceName_ = str;
            return;
        }
        throw new NullPointerException();
    }

    private void clearResourceName() {
        this.resourceName_ = getDefaultInstance().getResourceName();
    }

    private void setResourceNameBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.resourceName_ = byteString.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public long getNumResponseItems() {
        return this.numResponseItems_;
    }

    private void setNumResponseItems(long j) {
        this.numResponseItems_ = j;
    }

    private void clearNumResponseItems() {
        this.numResponseItems_ = 0;
    }

    public boolean hasStatus() {
        return this.status_ != null;
    }

    public Status getStatus() {
        Status status = this.status_;
        return status == null ? Status.getDefaultInstance() : status;
    }

    private void setStatus(Status status) {
        if (status != null) {
            this.status_ = status;
            return;
        }
        throw new NullPointerException();
    }

    private void setStatus(com.google.rpc.Status.Builder builder) {
        this.status_ = (Status) builder.build();
    }

    private void mergeStatus(Status status) {
        Status status2 = this.status_;
        if (status2 == null || status2 == Status.getDefaultInstance()) {
            this.status_ = status;
        } else {
            this.status_ = (Status) ((com.google.rpc.Status.Builder) Status.newBuilder(this.status_).mergeFrom(status)).buildPartial();
        }
    }

    private void clearStatus() {
        this.status_ = null;
    }

    public boolean hasAuthenticationInfo() {
        return this.authenticationInfo_ != null;
    }

    public AuthenticationInfo getAuthenticationInfo() {
        AuthenticationInfo authenticationInfo = this.authenticationInfo_;
        return authenticationInfo == null ? AuthenticationInfo.getDefaultInstance() : authenticationInfo;
    }

    private void setAuthenticationInfo(AuthenticationInfo authenticationInfo) {
        if (authenticationInfo != null) {
            this.authenticationInfo_ = authenticationInfo;
            return;
        }
        throw new NullPointerException();
    }

    private void setAuthenticationInfo(com.google.cloud.audit.AuthenticationInfo.Builder builder) {
        this.authenticationInfo_ = (AuthenticationInfo) builder.build();
    }

    private void mergeAuthenticationInfo(AuthenticationInfo authenticationInfo) {
        AuthenticationInfo authenticationInfo2 = this.authenticationInfo_;
        if (authenticationInfo2 == null || authenticationInfo2 == AuthenticationInfo.getDefaultInstance()) {
            this.authenticationInfo_ = authenticationInfo;
        } else {
            this.authenticationInfo_ = (AuthenticationInfo) ((com.google.cloud.audit.AuthenticationInfo.Builder) AuthenticationInfo.newBuilder(this.authenticationInfo_).mergeFrom(authenticationInfo)).buildPartial();
        }
    }

    private void clearAuthenticationInfo() {
        this.authenticationInfo_ = null;
    }

    public List<AuthorizationInfo> getAuthorizationInfoList() {
        return this.authorizationInfo_;
    }

    public List<? extends AuthorizationInfoOrBuilder> getAuthorizationInfoOrBuilderList() {
        return this.authorizationInfo_;
    }

    public int getAuthorizationInfoCount() {
        return this.authorizationInfo_.size();
    }

    public AuthorizationInfo getAuthorizationInfo(int i) {
        return (AuthorizationInfo) this.authorizationInfo_.get(i);
    }

    public AuthorizationInfoOrBuilder getAuthorizationInfoOrBuilder(int i) {
        return (AuthorizationInfoOrBuilder) this.authorizationInfo_.get(i);
    }

    private void ensureAuthorizationInfoIsMutable() {
        if (!this.authorizationInfo_.isModifiable()) {
            this.authorizationInfo_ = GeneratedMessageLite.mutableCopy(this.authorizationInfo_);
        }
    }

    private void setAuthorizationInfo(int i, AuthorizationInfo authorizationInfo) {
        if (authorizationInfo != null) {
            ensureAuthorizationInfoIsMutable();
            this.authorizationInfo_.set(i, authorizationInfo);
            return;
        }
        throw new NullPointerException();
    }

    private void setAuthorizationInfo(int i, com.google.cloud.audit.AuthorizationInfo.Builder builder) {
        ensureAuthorizationInfoIsMutable();
        this.authorizationInfo_.set(i, (AuthorizationInfo) builder.build());
    }

    private void addAuthorizationInfo(AuthorizationInfo authorizationInfo) {
        if (authorizationInfo != null) {
            ensureAuthorizationInfoIsMutable();
            this.authorizationInfo_.add(authorizationInfo);
            return;
        }
        throw new NullPointerException();
    }

    private void addAuthorizationInfo(int i, AuthorizationInfo authorizationInfo) {
        if (authorizationInfo != null) {
            ensureAuthorizationInfoIsMutable();
            this.authorizationInfo_.add(i, authorizationInfo);
            return;
        }
        throw new NullPointerException();
    }

    private void addAuthorizationInfo(com.google.cloud.audit.AuthorizationInfo.Builder builder) {
        ensureAuthorizationInfoIsMutable();
        this.authorizationInfo_.add((AuthorizationInfo) builder.build());
    }

    private void addAuthorizationInfo(int i, com.google.cloud.audit.AuthorizationInfo.Builder builder) {
        ensureAuthorizationInfoIsMutable();
        this.authorizationInfo_.add(i, (AuthorizationInfo) builder.build());
    }

    private void addAllAuthorizationInfo(Iterable<? extends AuthorizationInfo> iterable) {
        ensureAuthorizationInfoIsMutable();
        AbstractMessageLite.addAll(iterable, this.authorizationInfo_);
    }

    private void clearAuthorizationInfo() {
        this.authorizationInfo_ = GeneratedMessageLite.emptyProtobufList();
    }

    private void removeAuthorizationInfo(int i) {
        ensureAuthorizationInfoIsMutable();
        this.authorizationInfo_.remove(i);
    }

    public boolean hasRequestMetadata() {
        return this.requestMetadata_ != null;
    }

    public RequestMetadata getRequestMetadata() {
        RequestMetadata requestMetadata = this.requestMetadata_;
        return requestMetadata == null ? RequestMetadata.getDefaultInstance() : requestMetadata;
    }

    private void setRequestMetadata(RequestMetadata requestMetadata) {
        if (requestMetadata != null) {
            this.requestMetadata_ = requestMetadata;
            return;
        }
        throw new NullPointerException();
    }

    private void setRequestMetadata(com.google.cloud.audit.RequestMetadata.Builder builder) {
        this.requestMetadata_ = (RequestMetadata) builder.build();
    }

    private void mergeRequestMetadata(RequestMetadata requestMetadata) {
        RequestMetadata requestMetadata2 = this.requestMetadata_;
        if (requestMetadata2 == null || requestMetadata2 == RequestMetadata.getDefaultInstance()) {
            this.requestMetadata_ = requestMetadata;
        } else {
            this.requestMetadata_ = (RequestMetadata) ((com.google.cloud.audit.RequestMetadata.Builder) RequestMetadata.newBuilder(this.requestMetadata_).mergeFrom(requestMetadata)).buildPartial();
        }
    }

    private void clearRequestMetadata() {
        this.requestMetadata_ = null;
    }

    public boolean hasRequest() {
        return this.request_ != null;
    }

    public Struct getRequest() {
        Struct struct = this.request_;
        return struct == null ? Struct.getDefaultInstance() : struct;
    }

    private void setRequest(Struct struct) {
        if (struct != null) {
            this.request_ = struct;
            return;
        }
        throw new NullPointerException();
    }

    private void setRequest(com.google.protobuf.Struct.Builder builder) {
        this.request_ = (Struct) builder.build();
    }

    private void mergeRequest(Struct struct) {
        Struct struct2 = this.request_;
        if (struct2 == null || struct2 == Struct.getDefaultInstance()) {
            this.request_ = struct;
        } else {
            this.request_ = (Struct) ((com.google.protobuf.Struct.Builder) Struct.newBuilder(this.request_).mergeFrom(struct)).buildPartial();
        }
    }

    private void clearRequest() {
        this.request_ = null;
    }

    public boolean hasResponse() {
        return this.response_ != null;
    }

    public Struct getResponse() {
        Struct struct = this.response_;
        return struct == null ? Struct.getDefaultInstance() : struct;
    }

    private void setResponse(Struct struct) {
        if (struct != null) {
            this.response_ = struct;
            return;
        }
        throw new NullPointerException();
    }

    private void setResponse(com.google.protobuf.Struct.Builder builder) {
        this.response_ = (Struct) builder.build();
    }

    private void mergeResponse(Struct struct) {
        Struct struct2 = this.response_;
        if (struct2 == null || struct2 == Struct.getDefaultInstance()) {
            this.response_ = struct;
        } else {
            this.response_ = (Struct) ((com.google.protobuf.Struct.Builder) Struct.newBuilder(this.response_).mergeFrom(struct)).buildPartial();
        }
    }

    private void clearResponse() {
        this.response_ = null;
    }

    public boolean hasServiceData() {
        return this.serviceData_ != null;
    }

    public Any getServiceData() {
        Any any = this.serviceData_;
        return any == null ? Any.getDefaultInstance() : any;
    }

    private void setServiceData(Any any) {
        if (any != null) {
            this.serviceData_ = any;
            return;
        }
        throw new NullPointerException();
    }

    private void setServiceData(com.google.protobuf.Any.Builder builder) {
        this.serviceData_ = (Any) builder.build();
    }

    private void mergeServiceData(Any any) {
        Any any2 = this.serviceData_;
        if (any2 == null || any2 == Any.getDefaultInstance()) {
            this.serviceData_ = any;
        } else {
            this.serviceData_ = (Any) ((com.google.protobuf.Any.Builder) Any.newBuilder(this.serviceData_).mergeFrom(any)).buildPartial();
        }
    }

    private void clearServiceData() {
        this.serviceData_ = null;
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        if (this.status_ != null) {
            codedOutputStream.writeMessage(2, getStatus());
        }
        if (this.authenticationInfo_ != null) {
            codedOutputStream.writeMessage(3, getAuthenticationInfo());
        }
        if (this.requestMetadata_ != null) {
            codedOutputStream.writeMessage(4, getRequestMetadata());
        }
        if (!this.serviceName_.isEmpty()) {
            codedOutputStream.writeString(7, getServiceName());
        }
        if (!this.methodName_.isEmpty()) {
            codedOutputStream.writeString(8, getMethodName());
        }
        for (int i = 0; i < this.authorizationInfo_.size(); i++) {
            codedOutputStream.writeMessage(9, (MessageLite) this.authorizationInfo_.get(i));
        }
        if (!this.resourceName_.isEmpty()) {
            codedOutputStream.writeString(11, getResourceName());
        }
        long j = this.numResponseItems_;
        if (j != 0) {
            codedOutputStream.writeInt64(12, j);
        }
        if (this.serviceData_ != null) {
            codedOutputStream.writeMessage(15, getServiceData());
        }
        if (this.request_ != null) {
            codedOutputStream.writeMessage(16, getRequest());
        }
        if (this.response_ != null) {
            codedOutputStream.writeMessage(17, getResponse());
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        i = this.status_ != null ? CodedOutputStream.computeMessageSize(2, getStatus()) + 0 : 0;
        if (this.authenticationInfo_ != null) {
            i += CodedOutputStream.computeMessageSize(3, getAuthenticationInfo());
        }
        if (this.requestMetadata_ != null) {
            i += CodedOutputStream.computeMessageSize(4, getRequestMetadata());
        }
        if (!this.serviceName_.isEmpty()) {
            i += CodedOutputStream.computeStringSize(7, getServiceName());
        }
        if (!this.methodName_.isEmpty()) {
            i += CodedOutputStream.computeStringSize(8, getMethodName());
        }
        while (i2 < this.authorizationInfo_.size()) {
            i += CodedOutputStream.computeMessageSize(9, (MessageLite) this.authorizationInfo_.get(i2));
            i2++;
        }
        if (!this.resourceName_.isEmpty()) {
            i += CodedOutputStream.computeStringSize(11, getResourceName());
        }
        long j = this.numResponseItems_;
        if (j != 0) {
            i += CodedOutputStream.computeInt64Size(12, j);
        }
        if (this.serviceData_ != null) {
            i += CodedOutputStream.computeMessageSize(15, getServiceData());
        }
        if (this.request_ != null) {
            i += CodedOutputStream.computeMessageSize(16, getRequest());
        }
        if (this.response_ != null) {
            i += CodedOutputStream.computeMessageSize(17, getResponse());
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static AuditLog parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (AuditLog) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static AuditLog parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (AuditLog) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static AuditLog parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (AuditLog) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static AuditLog parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (AuditLog) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static AuditLog parseFrom(InputStream inputStream) throws IOException {
        return (AuditLog) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static AuditLog parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (AuditLog) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static AuditLog parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (AuditLog) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static AuditLog parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (AuditLog) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static AuditLog parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (AuditLog) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static AuditLog parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (AuditLog) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(AuditLog auditLog) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(auditLog);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        Object obj3 = null;
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new AuditLog();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                this.authorizationInfo_.makeImmutable();
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                AuditLog auditLog = (AuditLog) obj2;
                this.serviceName_ = visitor.visitString(this.serviceName_.isEmpty() ^ true, this.serviceName_, auditLog.serviceName_.isEmpty() ^ true, auditLog.serviceName_);
                this.methodName_ = visitor.visitString(this.methodName_.isEmpty() ^ true, this.methodName_, auditLog.methodName_.isEmpty() ^ true, auditLog.methodName_);
                this.resourceName_ = visitor.visitString(this.resourceName_.isEmpty() ^ true, this.resourceName_, auditLog.resourceName_.isEmpty() ^ true, auditLog.resourceName_);
                this.numResponseItems_ = visitor.visitLong(this.numResponseItems_ != 0, this.numResponseItems_, auditLog.numResponseItems_ != 0, auditLog.numResponseItems_);
                this.status_ = (Status) visitor.visitMessage(this.status_, auditLog.status_);
                this.authenticationInfo_ = (AuthenticationInfo) visitor.visitMessage(this.authenticationInfo_, auditLog.authenticationInfo_);
                this.authorizationInfo_ = visitor.visitList(this.authorizationInfo_, auditLog.authorizationInfo_);
                this.requestMetadata_ = (RequestMetadata) visitor.visitMessage(this.requestMetadata_, auditLog.requestMetadata_);
                this.request_ = (Struct) visitor.visitMessage(this.request_, auditLog.request_);
                this.response_ = (Struct) visitor.visitMessage(this.response_, auditLog.response_);
                this.serviceData_ = (Any) visitor.visitMessage(this.serviceData_, auditLog.serviceData_);
                if (visitor == MergeFromVisitor.INSTANCE) {
                    this.bitField0_ |= auditLog.bitField0_;
                }
                return this;
            case MERGE_FROM_STREAM:
                CodedInputStream codedInputStream = (CodedInputStream) obj;
                ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                while (obj3 == null) {
                    try {
                        int readTag = codedInputStream.readTag();
                        com.google.protobuf.Struct.Builder builder;
                        switch (readTag) {
                            case 0:
                                obj3 = 1;
                                break;
                            case 18:
                                com.google.rpc.Status.Builder builder2 = this.status_ != null ? (com.google.rpc.Status.Builder) this.status_.toBuilder() : null;
                                this.status_ = (Status) codedInputStream.readMessage(Status.parser(), extensionRegistryLite);
                                if (builder2 == null) {
                                    break;
                                }
                                builder2.mergeFrom(this.status_);
                                this.status_ = (Status) builder2.buildPartial();
                                break;
                            case 26:
                                com.google.cloud.audit.AuthenticationInfo.Builder builder3 = this.authenticationInfo_ != null ? (com.google.cloud.audit.AuthenticationInfo.Builder) this.authenticationInfo_.toBuilder() : null;
                                this.authenticationInfo_ = (AuthenticationInfo) codedInputStream.readMessage(AuthenticationInfo.parser(), extensionRegistryLite);
                                if (builder3 == null) {
                                    break;
                                }
                                builder3.mergeFrom(this.authenticationInfo_);
                                this.authenticationInfo_ = (AuthenticationInfo) builder3.buildPartial();
                                break;
                            case 34:
                                com.google.cloud.audit.RequestMetadata.Builder builder4 = this.requestMetadata_ != null ? (com.google.cloud.audit.RequestMetadata.Builder) this.requestMetadata_.toBuilder() : null;
                                this.requestMetadata_ = (RequestMetadata) codedInputStream.readMessage(RequestMetadata.parser(), extensionRegistryLite);
                                if (builder4 == null) {
                                    break;
                                }
                                builder4.mergeFrom(this.requestMetadata_);
                                this.requestMetadata_ = (RequestMetadata) builder4.buildPartial();
                                break;
                            case 58:
                                this.serviceName_ = codedInputStream.readStringRequireUtf8();
                                break;
                            case 66:
                                this.methodName_ = codedInputStream.readStringRequireUtf8();
                                break;
                            case 74:
                                if (!this.authorizationInfo_.isModifiable()) {
                                    this.authorizationInfo_ = GeneratedMessageLite.mutableCopy(this.authorizationInfo_);
                                }
                                this.authorizationInfo_.add((AuthorizationInfo) codedInputStream.readMessage(AuthorizationInfo.parser(), extensionRegistryLite));
                                break;
                            case 90:
                                this.resourceName_ = codedInputStream.readStringRequireUtf8();
                                break;
                            case 96:
                                this.numResponseItems_ = codedInputStream.readInt64();
                                break;
                            case 122:
                                com.google.protobuf.Any.Builder builder5 = this.serviceData_ != null ? (com.google.protobuf.Any.Builder) this.serviceData_.toBuilder() : null;
                                this.serviceData_ = (Any) codedInputStream.readMessage(Any.parser(), extensionRegistryLite);
                                if (builder5 == null) {
                                    break;
                                }
                                builder5.mergeFrom(this.serviceData_);
                                this.serviceData_ = (Any) builder5.buildPartial();
                                break;
                            case NikonType2MakernoteDirectory.TAG_ADAPTER /*130*/:
                                builder = this.request_ != null ? (com.google.protobuf.Struct.Builder) this.request_.toBuilder() : null;
                                this.request_ = (Struct) codedInputStream.readMessage(Struct.parser(), extensionRegistryLite);
                                if (builder == null) {
                                    break;
                                }
                                builder.mergeFrom(this.request_);
                                this.request_ = (Struct) builder.buildPartial();
                                break;
                            case 138:
                                builder = this.response_ != null ? (com.google.protobuf.Struct.Builder) this.response_.toBuilder() : null;
                                this.response_ = (Struct) codedInputStream.readMessage(Struct.parser(), extensionRegistryLite);
                                if (builder == null) {
                                    break;
                                }
                                builder.mergeFrom(this.response_);
                                this.response_ = (Struct) builder.buildPartial();
                                break;
                            default:
                                if (codedInputStream.skipField(readTag)) {
                                    break;
                                }
                                obj3 = 1;
                                break;
                        }
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e.setUnfinishedMessage(this));
                    } catch (IOException e2) {
                        throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                    }
                }
                break;
            case GET_DEFAULT_INSTANCE:
                break;
            case GET_PARSER:
                if (PARSER == null) {
                    synchronized (AuditLog.class) {
                        if (PARSER == null) {
                            PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                        }
                    }
                }
                return PARSER;
            default:
                throw new UnsupportedOperationException();
        }
        return DEFAULT_INSTANCE;
    }

    static {
        DEFAULT_INSTANCE.makeImmutable();
    }

    public static AuditLog getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AuditLog> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
