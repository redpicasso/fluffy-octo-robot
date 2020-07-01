package io.grpc;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import io.grpc.Metadata.Key;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class Status {
    public static final Status ABORTED = Code.ABORTED.toStatus();
    public static final Status ALREADY_EXISTS = Code.ALREADY_EXISTS.toStatus();
    public static final Status CANCELLED = Code.CANCELLED.toStatus();
    static final Key<Status> CODE_KEY = Key.of("grpc-status", false, new StatusCodeMarshaller());
    public static final Status DATA_LOSS = Code.DATA_LOSS.toStatus();
    public static final Status DEADLINE_EXCEEDED = Code.DEADLINE_EXCEEDED.toStatus();
    public static final Status FAILED_PRECONDITION = Code.FAILED_PRECONDITION.toStatus();
    public static final Status INTERNAL = Code.INTERNAL.toStatus();
    public static final Status INVALID_ARGUMENT = Code.INVALID_ARGUMENT.toStatus();
    static final Key<String> MESSAGE_KEY = Key.of("grpc-message", false, STATUS_MESSAGE_MARSHALLER);
    public static final Status NOT_FOUND = Code.NOT_FOUND.toStatus();
    public static final Status OK = Code.OK.toStatus();
    public static final Status OUT_OF_RANGE = Code.OUT_OF_RANGE.toStatus();
    public static final Status PERMISSION_DENIED = Code.PERMISSION_DENIED.toStatus();
    public static final Status RESOURCE_EXHAUSTED = Code.RESOURCE_EXHAUSTED.toStatus();
    private static final List<Status> STATUS_LIST = buildStatusList();
    private static final TrustedAsciiMarshaller<String> STATUS_MESSAGE_MARSHALLER = new StatusMessageMarshaller();
    public static final Status UNAUTHENTICATED = Code.UNAUTHENTICATED.toStatus();
    public static final Status UNAVAILABLE = Code.UNAVAILABLE.toStatus();
    public static final Status UNIMPLEMENTED = Code.UNIMPLEMENTED.toStatus();
    public static final Status UNKNOWN = Code.UNKNOWN.toStatus();
    private final Throwable cause;
    private final Code code;
    private final String description;

    public enum Code {
        OK(0),
        CANCELLED(1),
        UNKNOWN(2),
        INVALID_ARGUMENT(3),
        DEADLINE_EXCEEDED(4),
        NOT_FOUND(5),
        ALREADY_EXISTS(6),
        PERMISSION_DENIED(7),
        RESOURCE_EXHAUSTED(8),
        FAILED_PRECONDITION(9),
        ABORTED(10),
        OUT_OF_RANGE(11),
        UNIMPLEMENTED(12),
        INTERNAL(13),
        UNAVAILABLE(14),
        DATA_LOSS(15),
        UNAUTHENTICATED(16);
        
        private final int value;
        private final byte[] valueAscii;

        private Code(int i) {
            this.value = i;
            this.valueAscii = Integer.toString(i).getBytes(Charsets.US_ASCII);
        }

        public int value() {
            return this.value;
        }

        public Status toStatus() {
            return (Status) Status.STATUS_LIST.get(this.value);
        }

        private byte[] valueAscii() {
            return this.valueAscii;
        }
    }

    private static final class StatusCodeMarshaller implements TrustedAsciiMarshaller<Status> {
        private StatusCodeMarshaller() {
        }

        public byte[] toAsciiString(Status status) {
            return status.getCode().valueAscii();
        }

        public Status parseAsciiString(byte[] bArr) {
            return Status.fromCodeValue(bArr);
        }
    }

    private static final class StatusMessageMarshaller implements TrustedAsciiMarshaller<String> {
        private static final byte[] HEX = new byte[]{(byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70};

        private static boolean isEscapingChar(byte b) {
            return b < (byte) 32 || b >= (byte) 126 || b == (byte) 37;
        }

        private StatusMessageMarshaller() {
        }

        public byte[] toAsciiString(String str) {
            byte[] bytes = str.getBytes(Charsets.UTF_8);
            for (int i = 0; i < bytes.length; i++) {
                if (isEscapingChar(bytes[i])) {
                    return toAsciiStringSlow(bytes, i);
                }
            }
            return bytes;
        }

        private static byte[] toAsciiStringSlow(byte[] bArr, int i) {
            Object obj = new byte[(((bArr.length - i) * 3) + i)];
            if (i != 0) {
                System.arraycopy(bArr, 0, obj, 0, i);
            }
            int i2 = i;
            while (i < bArr.length) {
                byte b = bArr[i];
                int i3;
                if (isEscapingChar(b)) {
                    obj[i2] = (byte) 37;
                    i3 = i2 + 1;
                    byte[] bArr2 = HEX;
                    obj[i3] = bArr2[(b >> 4) & 15];
                    obj[i2 + 2] = bArr2[b & 15];
                    i2 += 3;
                } else {
                    i3 = i2 + 1;
                    obj[i2] = b;
                    i2 = i3;
                }
                i++;
            }
            Object obj2 = new byte[i2];
            System.arraycopy(obj, 0, obj2, 0, i2);
            return obj2;
        }

        public String parseAsciiString(byte[] bArr) {
            int i = 0;
            while (i < bArr.length) {
                byte b = bArr[i];
                if (b < (byte) 32 || b >= (byte) 126 || (b == (byte) 37 && i + 2 < bArr.length)) {
                    return parseAsciiStringSlow(bArr);
                }
                i++;
            }
            return new String(bArr, 0);
        }

        private static String parseAsciiStringSlow(byte[] bArr) {
            ByteBuffer allocate = ByteBuffer.allocate(bArr.length);
            int i = 0;
            while (i < bArr.length) {
                if (bArr[i] == (byte) 37 && i + 2 < bArr.length) {
                    try {
                        allocate.put((byte) Integer.parseInt(new String(bArr, i + 1, 2, Charsets.US_ASCII), 16));
                        i += 3;
                    } catch (NumberFormatException unused) {
                        allocate.put(bArr[i]);
                        i++;
                    }
                }
            }
            return new String(allocate.array(), 0, allocate.position(), Charsets.UTF_8);
        }
    }

    private static List<Status> buildStatusList() {
        TreeMap treeMap = new TreeMap();
        Code[] values = Code.values();
        int length = values.length;
        int i = 0;
        while (i < length) {
            Code code = values[i];
            Status status = (Status) treeMap.put(Integer.valueOf(code.value()), new Status(code));
            if (status == null) {
                i++;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Code value duplication between ");
                stringBuilder.append(status.getCode().name());
                stringBuilder.append(" & ");
                stringBuilder.append(code.name());
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        return Collections.unmodifiableList(new ArrayList(treeMap.values()));
    }

    public static Status fromCodeValue(int i) {
        if (i >= 0 && i <= STATUS_LIST.size()) {
            return (Status) STATUS_LIST.get(i);
        }
        Status status = UNKNOWN;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown code ");
        stringBuilder.append(i);
        return status.withDescription(stringBuilder.toString());
    }

    private static Status fromCodeValue(byte[] bArr) {
        if (bArr.length == 1 && bArr[0] == (byte) 48) {
            return OK;
        }
        return fromCodeValueSlow(bArr);
    }

    private static Status fromCodeValueSlow(byte[] bArr) {
        Status status;
        StringBuilder stringBuilder;
        int length = bArr.length;
        int i = 1;
        int i2 = 0;
        if (length != 1) {
            if (length == 2 && bArr[0] >= (byte) 48 && bArr[0] <= (byte) 57) {
                i2 = 0 + ((bArr[0] - 48) * 10);
            }
            status = UNKNOWN;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown code ");
            stringBuilder.append(new String(bArr, Charsets.US_ASCII));
            return status.withDescription(stringBuilder.toString());
        }
        i = 0;
        if (bArr[i] >= (byte) 48 && bArr[i] <= (byte) 57) {
            i2 += bArr[i] - 48;
            if (i2 < STATUS_LIST.size()) {
                return (Status) STATUS_LIST.get(i2);
            }
        }
        status = UNKNOWN;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown code ");
        stringBuilder.append(new String(bArr, Charsets.US_ASCII));
        return status.withDescription(stringBuilder.toString());
    }

    public static Status fromCode(Code code) {
        return code.toStatus();
    }

    public static Status fromThrowable(Throwable th) {
        for (Throwable th2 = (Throwable) Preconditions.checkNotNull(th, "t"); th2 != null; th2 = th2.getCause()) {
            if (th2 instanceof StatusException) {
                return ((StatusException) th2).getStatus();
            }
            if (th2 instanceof StatusRuntimeException) {
                return ((StatusRuntimeException) th2).getStatus();
            }
        }
        return UNKNOWN.withCause(th);
    }

    @ExperimentalApi("https://github.com/grpc/grpc-java/issues/4683")
    public static Metadata trailersFromThrowable(Throwable th) {
        for (th = (Throwable) Preconditions.checkNotNull(th, "t"); th != null; th = th.getCause()) {
            if (th instanceof StatusException) {
                return ((StatusException) th).getTrailers();
            }
            if (th instanceof StatusRuntimeException) {
                return ((StatusRuntimeException) th).getTrailers();
            }
        }
        return null;
    }

    static String formatThrowableMessage(Status status) {
        if (status.description == null) {
            return status.code.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(status.code);
        stringBuilder.append(": ");
        stringBuilder.append(status.description);
        return stringBuilder.toString();
    }

    private Status(Code code) {
        this(code, null, null);
    }

    private Status(Code code, @Nullable String str, @Nullable Throwable th) {
        this.code = (Code) Preconditions.checkNotNull(code, "code");
        this.description = str;
        this.cause = th;
    }

    public Status withCause(Throwable th) {
        if (Objects.equal(this.cause, th)) {
            return this;
        }
        return new Status(this.code, this.description, th);
    }

    public Status withDescription(String str) {
        if (Objects.equal(this.description, str)) {
            return this;
        }
        return new Status(this.code, str, this.cause);
    }

    public Status augmentDescription(String str) {
        if (str == null) {
            return this;
        }
        if (this.description == null) {
            return new Status(this.code, str, this.cause);
        }
        Code code = this.code;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.description);
        stringBuilder.append(ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE);
        stringBuilder.append(str);
        return new Status(code, stringBuilder.toString(), this.cause);
    }

    public Code getCode() {
        return this.code;
    }

    @Nullable
    public String getDescription() {
        return this.description;
    }

    @Nullable
    public Throwable getCause() {
        return this.cause;
    }

    public boolean isOk() {
        return Code.OK == this.code;
    }

    public StatusRuntimeException asRuntimeException() {
        return new StatusRuntimeException(this);
    }

    @ExperimentalApi("https://github.com/grpc/grpc-java/issues/4683")
    public StatusRuntimeException asRuntimeException(@Nullable Metadata metadata) {
        return new StatusRuntimeException(this, metadata);
    }

    public StatusException asException() {
        return new StatusException(this);
    }

    @ExperimentalApi("https://github.com/grpc/grpc-java/issues/4683")
    public StatusException asException(@Nullable Metadata metadata) {
        return new StatusException(this, metadata);
    }

    public String toString() {
        String str = "description";
        ToStringHelper add = MoreObjects.toStringHelper((Object) this).add("code", this.code.name()).add(str, this.description);
        Object obj = this.cause;
        if (obj != null) {
            obj = Throwables.getStackTraceAsString(obj);
        }
        return add.add("cause", obj).toString();
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
        return super.hashCode();
    }
}
