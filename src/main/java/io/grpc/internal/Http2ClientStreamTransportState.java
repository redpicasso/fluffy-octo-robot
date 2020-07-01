package io.grpc.internal;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.logging.type.LogSeverity;
import io.grpc.InternalMetadata;
import io.grpc.InternalMetadata.TrustedAsciiMarshaller;
import io.grpc.InternalStatus;
import io.grpc.Metadata;
import io.grpc.Metadata.Key;
import io.grpc.Status;
import java.nio.charset.Charset;
import javax.annotation.Nullable;
import okhttp3.internal.http2.Header;

public abstract class Http2ClientStreamTransportState extends TransportState {
    private static final Key<Integer> HTTP2_STATUS = InternalMetadata.keyOf(Header.RESPONSE_STATUS_UTF8, HTTP_STATUS_MARSHALLER);
    private static final TrustedAsciiMarshaller<Integer> HTTP_STATUS_MARSHALLER = new TrustedAsciiMarshaller<Integer>() {
        public byte[] toAsciiString(Integer num) {
            throw new UnsupportedOperationException();
        }

        public Integer parseAsciiString(byte[] bArr) {
            if (bArr.length >= 3) {
                return Integer.valueOf((((bArr[0] - 48) * 100) + ((bArr[1] - 48) * 10)) + (bArr[2] - 48));
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Malformed status code ");
            stringBuilder.append(new String(bArr, InternalMetadata.US_ASCII));
            throw new NumberFormatException(stringBuilder.toString());
        }
    };
    private Charset errorCharset = Charsets.UTF_8;
    private boolean headersReceived;
    private Status transportError;
    private Metadata transportErrorMetadata;

    protected abstract void http2ProcessingFailed(Status status, boolean z, Metadata metadata);

    protected Http2ClientStreamTransportState(int i, StatsTraceContext statsTraceContext, TransportTracer transportTracer) {
        super(i, statsTraceContext, transportTracer);
    }

    protected void transportHeadersReceived(Metadata metadata) {
        Preconditions.checkNotNull(metadata, "headers");
        Status status = this.transportError;
        String str = "headers: ";
        StringBuilder stringBuilder;
        if (status != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(metadata);
            this.transportError = status.augmentDescription(stringBuilder.toString());
            return;
        }
        try {
            if (this.headersReceived) {
                this.transportError = Status.INTERNAL.withDescription("Received headers twice");
                status = this.transportError;
                if (status != null) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str);
                    stringBuilder.append(metadata);
                    this.transportError = status.augmentDescription(stringBuilder.toString());
                    this.transportErrorMetadata = metadata;
                    this.errorCharset = extractCharset(metadata);
                }
                return;
            }
            Integer num = (Integer) metadata.get(HTTP2_STATUS);
            if (num == null || num.intValue() < 100 || num.intValue() >= LogSeverity.INFO_VALUE) {
                this.headersReceived = true;
                this.transportError = validateInitialMetadata(metadata);
                if (this.transportError != null) {
                    status = this.transportError;
                    if (status != null) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(str);
                        stringBuilder.append(metadata);
                        this.transportError = status.augmentDescription(stringBuilder.toString());
                        this.transportErrorMetadata = metadata;
                        this.errorCharset = extractCharset(metadata);
                    }
                    return;
                }
                stripTransportDetails(metadata);
                inboundHeadersReceived(metadata);
                status = this.transportError;
                if (status != null) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str);
                    stringBuilder.append(metadata);
                    this.transportError = status.augmentDescription(stringBuilder.toString());
                    this.transportErrorMetadata = metadata;
                    this.errorCharset = extractCharset(metadata);
                }
                return;
            }
            status = this.transportError;
            if (status != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(metadata);
                this.transportError = status.augmentDescription(stringBuilder.toString());
                this.transportErrorMetadata = metadata;
                this.errorCharset = extractCharset(metadata);
            }
        } catch (Throwable th) {
            Status status2 = this.transportError;
            if (status2 != null) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str);
                stringBuilder2.append(metadata);
                this.transportError = status2.augmentDescription(stringBuilder2.toString());
                this.transportErrorMetadata = metadata;
                this.errorCharset = extractCharset(metadata);
            }
        }
    }

    protected void transportDataReceived(ReadableBuffer readableBuffer, boolean z) {
        Status status = this.transportError;
        if (status != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DATA-----------------------------\n");
            stringBuilder.append(ReadableBuffers.readAsString(readableBuffer, this.errorCharset));
            this.transportError = status.augmentDescription(stringBuilder.toString());
            readableBuffer.close();
            if (this.transportError.getDescription().length() > 1000 || z) {
                http2ProcessingFailed(this.transportError, false, this.transportErrorMetadata);
            }
        } else if (this.headersReceived) {
            inboundDataReceived(readableBuffer);
            if (z) {
                this.transportError = Status.INTERNAL.withDescription("Received unexpected EOS on DATA frame from server.");
                this.transportErrorMetadata = new Metadata();
                transportReportStatus(this.transportError, false, this.transportErrorMetadata);
            }
        } else {
            http2ProcessingFailed(Status.INTERNAL.withDescription("headers not received before payload"), false, new Metadata());
        }
    }

    protected void transportTrailersReceived(Metadata metadata) {
        Preconditions.checkNotNull(metadata, GrpcUtil.TE_TRAILERS);
        if (this.transportError == null && !this.headersReceived) {
            this.transportError = validateInitialMetadata(metadata);
            if (this.transportError != null) {
                this.transportErrorMetadata = metadata;
            }
        }
        Status status = this.transportError;
        if (status != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("trailers: ");
            stringBuilder.append(metadata);
            this.transportError = status.augmentDescription(stringBuilder.toString());
            http2ProcessingFailed(this.transportError, false, this.transportErrorMetadata);
            return;
        }
        status = statusFromTrailers(metadata);
        stripTransportDetails(metadata);
        inboundTrailersReceived(metadata, status);
    }

    private Status statusFromTrailers(Metadata metadata) {
        Status status = (Status) metadata.get(InternalStatus.CODE_KEY);
        if (status != null) {
            return status.withDescription((String) metadata.get(InternalStatus.MESSAGE_KEY));
        }
        if (this.headersReceived) {
            return Status.UNKNOWN.withDescription("missing GRPC status in response");
        }
        Status httpStatusToGrpcStatus;
        Integer num = (Integer) metadata.get(HTTP2_STATUS);
        if (num != null) {
            httpStatusToGrpcStatus = GrpcUtil.httpStatusToGrpcStatus(num.intValue());
        } else {
            httpStatusToGrpcStatus = Status.INTERNAL.withDescription("missing HTTP status code");
        }
        return httpStatusToGrpcStatus.augmentDescription("missing GRPC status, inferred error from HTTP status code");
    }

    @Nullable
    private Status validateInitialMetadata(Metadata metadata) {
        Integer num = (Integer) metadata.get(HTTP2_STATUS);
        if (num == null) {
            return Status.INTERNAL.withDescription("Missing HTTP status code");
        }
        String str = (String) metadata.get(GrpcUtil.CONTENT_TYPE_KEY);
        if (GrpcUtil.isGrpcContentType(str)) {
            return null;
        }
        Status httpStatusToGrpcStatus = GrpcUtil.httpStatusToGrpcStatus(num.intValue());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid content-type: ");
        stringBuilder.append(str);
        return httpStatusToGrpcStatus.augmentDescription(stringBuilder.toString());
    }

    private static Charset extractCharset(Metadata metadata) {
        String str = (String) metadata.get(GrpcUtil.CONTENT_TYPE_KEY);
        if (str != null) {
            String[] split = str.split("charset=", 2);
            try {
                return Charset.forName(split[split.length - 1].trim());
            } catch (Exception unused) {
                return Charsets.UTF_8;
            }
        }
    }

    private static void stripTransportDetails(Metadata metadata) {
        metadata.discardAll(HTTP2_STATUS);
        metadata.discardAll(InternalStatus.CODE_KEY);
        metadata.discardAll(InternalStatus.MESSAGE_KEY);
    }
}
