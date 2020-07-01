package io.grpc;

import io.grpc.Metadata.AsciiMarshaller;
import io.grpc.Metadata.Key;
import java.nio.charset.Charset;

@Internal
public final class InternalMetadata {
    @Internal
    public static final Charset US_ASCII = Charset.forName("US-ASCII");

    @Internal
    public interface TrustedAsciiMarshaller<T> extends TrustedAsciiMarshaller<T> {
    }

    @Internal
    public static <T> Key<T> keyOf(String str, TrustedAsciiMarshaller<T> trustedAsciiMarshaller) {
        boolean z = false;
        if (!(str == null || str.isEmpty() || str.charAt(0) != ':')) {
            z = true;
        }
        return Key.of(str, z, (TrustedAsciiMarshaller) trustedAsciiMarshaller);
    }

    @Internal
    public static <T> Key<T> keyOf(String str, AsciiMarshaller<T> asciiMarshaller) {
        boolean z = false;
        if (!(str == null || str.isEmpty() || str.charAt(0) != ':')) {
            z = true;
        }
        return Key.of(str, z, (AsciiMarshaller) asciiMarshaller);
    }

    @Internal
    public static Metadata newMetadata(byte[]... bArr) {
        return new Metadata(bArr);
    }

    @Internal
    public static Metadata newMetadata(int i, byte[]... bArr) {
        return new Metadata(i, bArr);
    }

    @Internal
    public static byte[][] serialize(Metadata metadata) {
        return metadata.serialize();
    }

    @Internal
    public static int headerCount(Metadata metadata) {
        return metadata.headerCount();
    }
}
