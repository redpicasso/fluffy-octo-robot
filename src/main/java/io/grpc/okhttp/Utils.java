package io.grpc.okhttp;

import com.google.common.base.Preconditions;
import com.squareup.okhttp.TlsVersion;
import io.grpc.InternalChannelz.SocketOptions;
import io.grpc.InternalMetadata;
import io.grpc.Metadata;
import io.grpc.internal.TransportFrameUtil;
import io.grpc.okhttp.internal.CipherSuite;
import io.grpc.okhttp.internal.ConnectionSpec;
import io.grpc.okhttp.internal.ConnectionSpec.Builder;
import io.grpc.okhttp.internal.framed.Header;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class Utils {
    static final int CONNECTION_STREAM_ID = 0;
    static final int DEFAULT_WINDOW_SIZE = 65535;
    private static final Logger log = Logger.getLogger(Utils.class.getName());

    public static Metadata convertHeaders(List<Header> list) {
        return InternalMetadata.newMetadata(convertHeadersToArray(list));
    }

    public static Metadata convertTrailers(List<Header> list) {
        return InternalMetadata.newMetadata(convertHeadersToArray(list));
    }

    private static byte[][] convertHeadersToArray(List<Header> list) {
        byte[][] bArr = new byte[(list.size() * 2)][];
        int i = 0;
        for (Header header : list) {
            int i2 = i + 1;
            bArr[i] = header.name.toByteArray();
            i = i2 + 1;
            bArr[i2] = header.value.toByteArray();
        }
        return TransportFrameUtil.toRawSerializedHeaders(bArr);
    }

    static ConnectionSpec convertSpec(com.squareup.okhttp.ConnectionSpec connectionSpec) {
        Preconditions.checkArgument(connectionSpec.isTls(), "plaintext ConnectionSpec is not accepted");
        List tlsVersions = connectionSpec.tlsVersions();
        String[] strArr = new String[tlsVersions.size()];
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = ((TlsVersion) tlsVersions.get(i)).javaName();
        }
        tlsVersions = connectionSpec.cipherSuites();
        CipherSuite[] cipherSuiteArr = new CipherSuite[tlsVersions.size()];
        for (int i2 = 0; i2 < cipherSuiteArr.length; i2++) {
            cipherSuiteArr[i2] = CipherSuite.valueOf(((com.squareup.okhttp.CipherSuite) tlsVersions.get(i2)).name());
        }
        return new Builder(connectionSpec.isTls()).supportsTlsExtensions(connectionSpec.supportsTlsExtensions()).tlsVersions(strArr).cipherSuites(cipherSuiteArr).build();
    }

    static SocketOptions getSocketOptions(Socket socket) {
        String str = "IP_TOS";
        String str2 = "SO_OOBINLINE";
        String str3 = "SO_KEEPALIVE";
        String str4 = "SO_RECVBUF";
        String str5 = "SO_SNDBUF";
        String str6 = "SO_REUSEADDR";
        String str7 = "TCP_NODELAY";
        String str8 = "channelz_internal_error";
        String str9 = "Exception caught while reading socket option";
        SocketOptions.Builder builder = new SocketOptions.Builder();
        try {
            builder.setSocketOptionLingerSeconds(Integer.valueOf(socket.getSoLinger()));
        } catch (Throwable e) {
            log.log(Level.SEVERE, str9, e);
            builder.addOption("SO_LINGER", str8);
        }
        try {
            builder.setSocketOptionTimeoutMillis(Integer.valueOf(socket.getSoTimeout()));
        } catch (Throwable e2) {
            log.log(Level.SEVERE, str9, e2);
            builder.addOption("SO_TIMEOUT", str8);
        }
        try {
            builder.addOption(str7, socket.getTcpNoDelay());
        } catch (Throwable e22) {
            log.log(Level.SEVERE, str9, e22);
            builder.addOption(str7, str8);
        }
        try {
            builder.addOption(str6, socket.getReuseAddress());
        } catch (Throwable e3) {
            log.log(Level.SEVERE, str9, e3);
            builder.addOption(str6, str8);
        }
        try {
            builder.addOption(str5, socket.getSendBufferSize());
        } catch (Throwable e4) {
            log.log(Level.SEVERE, str9, e4);
            builder.addOption(str5, str8);
        }
        try {
            builder.addOption(str4, socket.getReceiveBufferSize());
        } catch (Throwable e5) {
            log.log(Level.SEVERE, str9, e5);
            builder.addOption(str4, str8);
        }
        try {
            builder.addOption(str3, socket.getKeepAlive());
        } catch (Throwable e6) {
            log.log(Level.SEVERE, str9, e6);
            builder.addOption(str3, str8);
        }
        try {
            builder.addOption(str2, socket.getOOBInline());
        } catch (Throwable e7) {
            log.log(Level.SEVERE, str9, e7);
            builder.addOption(str2, str8);
        }
        try {
            builder.addOption(str, socket.getTrafficClass());
        } catch (Throwable e8) {
            log.log(Level.SEVERE, str9, e8);
            builder.addOption(str, str8);
        }
        return builder.build();
    }

    private Utils() {
    }
}
