package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Ints;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
@Beta
public final class InetAddresses {
    private static final Inet4Address ANY4 = ((Inet4Address) forString("0.0.0.0"));
    private static final int IPV4_PART_COUNT = 4;
    private static final Splitter IPV4_SPLITTER = Splitter.on('.').limit(4);
    private static final int IPV6_PART_COUNT = 8;
    private static final Splitter IPV6_SPLITTER = Splitter.on(':').limit(10);
    private static final Inet4Address LOOPBACK4 = ((Inet4Address) forString("127.0.0.1"));

    @Beta
    public static final class TeredoInfo {
        private final Inet4Address client;
        private final int flags;
        private final int port;
        private final Inet4Address server;

        public TeredoInfo(@NullableDecl Inet4Address inet4Address, @NullableDecl Inet4Address inet4Address2, int i, int i2) {
            boolean z = true;
            boolean z2 = i >= 0 && i <= 65535;
            Preconditions.checkArgument(z2, "port '%s' is out of range (0 <= port <= 0xffff)", i);
            if (i2 < 0 || i2 > 65535) {
                z = false;
            }
            Preconditions.checkArgument(z, "flags '%s' is out of range (0 <= flags <= 0xffff)", i2);
            this.server = (Inet4Address) MoreObjects.firstNonNull(inet4Address, InetAddresses.ANY4);
            this.client = (Inet4Address) MoreObjects.firstNonNull(inet4Address2, InetAddresses.ANY4);
            this.port = i;
            this.flags = i2;
        }

        public Inet4Address getServer() {
            return this.server;
        }

        public Inet4Address getClient() {
            return this.client;
        }

        public int getPort() {
            return this.port;
        }

        public int getFlags() {
            return this.flags;
        }
    }

    private InetAddresses() {
    }

    private static Inet4Address getInet4Address(byte[] bArr) {
        Preconditions.checkArgument(bArr.length == 4, "Byte array has invalid length for an IPv4 address: %s != 4.", bArr.length);
        return (Inet4Address) bytesToInetAddress(bArr);
    }

    public static InetAddress forString(String str) {
        byte[] ipStringToBytes = ipStringToBytes(str);
        if (ipStringToBytes != null) {
            return bytesToInetAddress(ipStringToBytes);
        }
        throw formatIllegalArgumentException("'%s' is not an IP string literal.", str);
    }

    public static boolean isInetAddress(String str) {
        return ipStringToBytes(str) != null;
    }

    @NullableDecl
    private static byte[] ipStringToBytes(String str) {
        Object obj = null;
        Object obj2 = null;
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '.') {
                obj2 = 1;
            } else if (charAt == ':') {
                if (obj2 != null) {
                    return null;
                }
                obj = 1;
            } else if (Character.digit(charAt, 16) == -1) {
                return null;
            }
        }
        if (obj != null) {
            if (obj2 != null) {
                str = convertDottedQuadToHex(str);
                if (str == null) {
                    return null;
                }
            }
            return textToNumericFormatV6(str);
        } else if (obj2 != null) {
            return textToNumericFormatV4(str);
        } else {
            return null;
        }
    }

    @NullableDecl
    private static byte[] textToNumericFormatV4(String str) {
        byte[] bArr = new byte[4];
        try {
            int i = 0;
            for (String parseOctet : IPV4_SPLITTER.split(str)) {
                int i2 = i + 1;
                bArr[i] = parseOctet(parseOctet);
                i = i2;
            }
            if (i != 4) {
                bArr = null;
            }
            return bArr;
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    @NullableDecl
    private static byte[] textToNumericFormatV6(String str) {
        List splitToList = IPV6_SPLITTER.splitToList(str);
        if (splitToList.size() < 3 || splitToList.size() > 9) {
            return null;
        }
        int size;
        int i;
        int i2 = -1;
        for (int i3 = 1; i3 < splitToList.size() - 1; i3++) {
            if (((String) splitToList.get(i3)).length() == 0) {
                if (i2 >= 0) {
                    return null;
                }
                i2 = i3;
            }
        }
        if (i2 >= 0) {
            size = (splitToList.size() - i2) - 1;
            if (((String) splitToList.get(0)).length() == 0) {
                i = i2 - 1;
                if (i != 0) {
                    return null;
                }
            }
            i = i2;
            if (((String) Iterables.getLast(splitToList)).length() == 0) {
                size--;
                if (size != 0) {
                    return null;
                }
            }
        }
        i = splitToList.size();
        size = 0;
        int i4 = 8 - (i + size);
        if (!i2 < 0 ? i4 >= 1 : i4 == 0) {
            return null;
        }
        ByteBuffer allocate = ByteBuffer.allocate(16);
        i2 = 0;
        while (i2 < i) {
            try {
                allocate.putShort(parseHextet((String) splitToList.get(i2)));
                i2++;
            } catch (NumberFormatException unused) {
                return null;
            }
        }
        for (i2 = 0; i2 < i4; i2++) {
            allocate.putShort((short) 0);
        }
        while (size > 0) {
            allocate.putShort(parseHextet((String) splitToList.get(splitToList.size() - size)));
            size--;
        }
        return allocate.array();
    }

    @NullableDecl
    private static String convertDottedQuadToHex(String str) {
        int lastIndexOf = str.lastIndexOf(58) + 1;
        String substring = str.substring(0, lastIndexOf);
        byte[] textToNumericFormatV4 = textToNumericFormatV4(str.substring(lastIndexOf));
        if (textToNumericFormatV4 == null) {
            return null;
        }
        String toHexString = Integer.toHexString(((textToNumericFormatV4[0] & 255) << 8) | (textToNumericFormatV4[1] & 255));
        str = Integer.toHexString((textToNumericFormatV4[3] & 255) | ((textToNumericFormatV4[2] & 255) << 8));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(substring);
        stringBuilder.append(toHexString);
        stringBuilder.append(":");
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    private static byte parseOctet(String str) {
        int parseInt = Integer.parseInt(str);
        if (parseInt <= 255 && (!str.startsWith("0") || str.length() <= 1)) {
            return (byte) parseInt;
        }
        throw new NumberFormatException();
    }

    private static short parseHextet(String str) {
        int parseInt = Integer.parseInt(str, 16);
        if (parseInt <= 65535) {
            return (short) parseInt;
        }
        throw new NumberFormatException();
    }

    private static InetAddress bytesToInetAddress(byte[] bArr) {
        try {
            return InetAddress.getByAddress(bArr);
        } catch (UnknownHostException e) {
            throw new AssertionError(e);
        }
    }

    public static String toAddrString(InetAddress inetAddress) {
        Preconditions.checkNotNull(inetAddress);
        if (inetAddress instanceof Inet4Address) {
            return inetAddress.getHostAddress();
        }
        Preconditions.checkArgument(inetAddress instanceof Inet6Address);
        byte[] address = inetAddress.getAddress();
        int[] iArr = new int[8];
        for (int i = 0; i < iArr.length; i++) {
            int i2 = i * 2;
            iArr[i] = Ints.fromBytes((byte) 0, (byte) 0, address[i2], address[i2 + 1]);
        }
        compressLongestRunOfZeroes(iArr);
        return hextetsToIPv6String(iArr);
    }

    private static void compressLongestRunOfZeroes(int[] iArr) {
        int i = 0;
        int i2 = -1;
        int i3 = -1;
        int i4 = -1;
        while (i < iArr.length + 1) {
            if (i >= iArr.length || iArr[i] != 0) {
                if (i4 >= 0) {
                    int i5 = i - i4;
                    if (i5 > i2) {
                        i3 = i4;
                        i2 = i5;
                    }
                    i4 = -1;
                }
            } else if (i4 < 0) {
                i4 = i;
            }
            i++;
        }
        if (i2 >= 2) {
            Arrays.fill(iArr, i3, i2 + i3, -1);
        }
    }

    private static String hextetsToIPv6String(int[] iArr) {
        StringBuilder stringBuilder = new StringBuilder(39);
        int i = 0;
        Object obj = null;
        while (i < iArr.length) {
            Object obj2 = iArr[i] >= 0 ? 1 : null;
            if (obj2 != null) {
                if (obj != null) {
                    stringBuilder.append(':');
                }
                stringBuilder.append(Integer.toHexString(iArr[i]));
            } else if (i == 0 || obj != null) {
                stringBuilder.append("::");
            }
            i++;
            obj = obj2;
        }
        return stringBuilder.toString();
    }

    public static String toUriString(InetAddress inetAddress) {
        if (!(inetAddress instanceof Inet6Address)) {
            return toAddrString(inetAddress);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(toAddrString(inetAddress));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static InetAddress forUriString(String str) {
        InetAddress forUriStringNoThrow = forUriStringNoThrow(str);
        if (forUriStringNoThrow != null) {
            return forUriStringNoThrow;
        }
        throw formatIllegalArgumentException("Not a valid URI IP literal: '%s'", str);
    }

    @NullableDecl
    private static InetAddress forUriStringNoThrow(String str) {
        int i;
        Preconditions.checkNotNull(str);
        if (str.startsWith("[") && str.endsWith("]")) {
            str = str.substring(1, str.length() - 1);
            i = 16;
        } else {
            i = 4;
        }
        byte[] ipStringToBytes = ipStringToBytes(str);
        return (ipStringToBytes == null || ipStringToBytes.length != i) ? null : bytesToInetAddress(ipStringToBytes);
    }

    public static boolean isUriInetAddress(String str) {
        return forUriStringNoThrow(str) != null;
    }

    public static boolean isCompatIPv4Address(Inet6Address inet6Address) {
        if (!inet6Address.isIPv4CompatibleAddress()) {
            return false;
        }
        byte[] address = inet6Address.getAddress();
        if (address[12] == (byte) 0 && address[13] == (byte) 0 && address[14] == (byte) 0 && (address[15] == (byte) 0 || address[15] == (byte) 1)) {
            return false;
        }
        return true;
    }

    public static Inet4Address getCompatIPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(isCompatIPv4Address(inet6Address), "Address '%s' is not IPv4-compatible.", toAddrString(inet6Address));
        return getInet4Address(Arrays.copyOfRange(inet6Address.getAddress(), 12, 16));
    }

    public static boolean is6to4Address(Inet6Address inet6Address) {
        byte[] address = inet6Address.getAddress();
        if (address[0] == (byte) 32 && address[1] == (byte) 2) {
            return true;
        }
        return false;
    }

    public static Inet4Address get6to4IPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(is6to4Address(inet6Address), "Address '%s' is not a 6to4 address.", toAddrString(inet6Address));
        return getInet4Address(Arrays.copyOfRange(inet6Address.getAddress(), 2, 6));
    }

    public static boolean isTeredoAddress(Inet6Address inet6Address) {
        byte[] address = inet6Address.getAddress();
        if (address[0] == (byte) 32 && address[1] == (byte) 1 && address[2] == (byte) 0 && address[3] == (byte) 0) {
            return true;
        }
        return false;
    }

    public static TeredoInfo getTeredoInfo(Inet6Address inet6Address) {
        Preconditions.checkArgument(isTeredoAddress(inet6Address), "Address '%s' is not a Teredo address.", toAddrString(inet6Address));
        byte[] address = inet6Address.getAddress();
        Inet4Address inet4Address = getInet4Address(Arrays.copyOfRange(address, 4, 8));
        int readShort = ByteStreams.newDataInput(address, 8).readShort() & 65535;
        int i = 65535 & (~ByteStreams.newDataInput(address, 10).readShort());
        address = Arrays.copyOfRange(address, 12, 16);
        for (int i2 = 0; i2 < address.length; i2++) {
            address[i2] = (byte) (~address[i2]);
        }
        return new TeredoInfo(inet4Address, getInet4Address(address), i, readShort);
    }

    public static boolean isIsatapAddress(Inet6Address inet6Address) {
        boolean z = false;
        if (isTeredoAddress(inet6Address)) {
            return false;
        }
        byte[] address = inet6Address.getAddress();
        if ((address[8] | 3) != 3) {
            return false;
        }
        if (address[9] == (byte) 0 && address[10] == (byte) 94 && address[11] == (byte) -2) {
            z = true;
        }
        return z;
    }

    public static Inet4Address getIsatapIPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(isIsatapAddress(inet6Address), "Address '%s' is not an ISATAP address.", toAddrString(inet6Address));
        return getInet4Address(Arrays.copyOfRange(inet6Address.getAddress(), 12, 16));
    }

    public static boolean hasEmbeddedIPv4ClientAddress(Inet6Address inet6Address) {
        return isCompatIPv4Address(inet6Address) || is6to4Address(inet6Address) || isTeredoAddress(inet6Address);
    }

    public static Inet4Address getEmbeddedIPv4ClientAddress(Inet6Address inet6Address) {
        if (isCompatIPv4Address(inet6Address)) {
            return getCompatIPv4Address(inet6Address);
        }
        if (is6to4Address(inet6Address)) {
            return get6to4IPv4Address(inet6Address);
        }
        if (isTeredoAddress(inet6Address)) {
            return getTeredoInfo(inet6Address).getClient();
        }
        throw formatIllegalArgumentException("'%s' has no embedded IPv4 address.", toAddrString(inet6Address));
    }

    public static boolean isMappedIPv4Address(String str) {
        byte[] ipStringToBytes = ipStringToBytes(str);
        if (ipStringToBytes == null || ipStringToBytes.length != 16) {
            return false;
        }
        int i = 0;
        while (true) {
            int i2 = 10;
            if (i >= 10) {
                while (i2 < 12) {
                    if (ipStringToBytes[i2] != (byte) -1) {
                        return false;
                    }
                    i2++;
                }
                return true;
            } else if (ipStringToBytes[i] != (byte) 0) {
                return false;
            } else {
                i++;
            }
        }
    }

    public static Inet4Address getCoercedIPv4Address(InetAddress inetAddress) {
        if (inetAddress instanceof Inet4Address) {
            return (Inet4Address) inetAddress;
        }
        Object obj;
        byte[] address = inetAddress.getAddress();
        for (int i = 0; i < 15; i++) {
            if (address[i] != (byte) 0) {
                obj = null;
                break;
            }
        }
        obj = 1;
        if (obj != null && address[15] == (byte) 1) {
            return LOOPBACK4;
        }
        if (obj != null && address[15] == (byte) 0) {
            return ANY4;
        }
        long hashCode;
        Inet6Address inet6Address = (Inet6Address) inetAddress;
        if (hasEmbeddedIPv4ClientAddress(inet6Address)) {
            hashCode = (long) getEmbeddedIPv4ClientAddress(inet6Address).hashCode();
        } else {
            hashCode = ByteBuffer.wrap(inet6Address.getAddress(), 0, 8).getLong();
        }
        int asInt = Hashing.murmur3_32().hashLong(hashCode).asInt() | -536870912;
        if (asInt == -1) {
            asInt = -2;
        }
        return getInet4Address(Ints.toByteArray(asInt));
    }

    public static int coerceToInteger(InetAddress inetAddress) {
        return ByteStreams.newDataInput(getCoercedIPv4Address(inetAddress).getAddress()).readInt();
    }

    public static Inet4Address fromInteger(int i) {
        return getInet4Address(Ints.toByteArray(i));
    }

    public static InetAddress fromLittleEndianByteArray(byte[] bArr) throws UnknownHostException {
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = bArr[(bArr.length - i) - 1];
        }
        return InetAddress.getByAddress(bArr2);
    }

    public static InetAddress decrement(InetAddress inetAddress) {
        byte[] address = inetAddress.getAddress();
        int length = address.length - 1;
        while (length >= 0 && address[length] == (byte) 0) {
            address[length] = (byte) -1;
            length--;
        }
        Preconditions.checkArgument(length >= 0, "Decrementing %s would wrap.", (Object) inetAddress);
        address[length] = (byte) (address[length] - 1);
        return bytesToInetAddress(address);
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0016  */
    public static java.net.InetAddress increment(java.net.InetAddress r6) {
        /*
        r0 = r6.getAddress();
        r1 = r0.length;
        r2 = 1;
        r1 = r1 - r2;
    L_0x0007:
        r3 = 0;
        if (r1 < 0) goto L_0x0014;
    L_0x000a:
        r4 = r0[r1];
        r5 = -1;
        if (r4 != r5) goto L_0x0014;
    L_0x000f:
        r0[r1] = r3;
        r1 = r1 + -1;
        goto L_0x0007;
    L_0x0014:
        if (r1 < 0) goto L_0x0017;
    L_0x0016:
        r3 = 1;
    L_0x0017:
        r4 = "Incrementing %s would wrap.";
        com.google.common.base.Preconditions.checkArgument(r3, r4, r6);
        r6 = r0[r1];
        r6 = r6 + r2;
        r6 = (byte) r6;
        r0[r1] = r6;
        r6 = bytesToInetAddress(r0);
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.net.InetAddresses.increment(java.net.InetAddress):java.net.InetAddress");
    }

    public static boolean isMaximum(InetAddress inetAddress) {
        byte[] address = inetAddress.getAddress();
        for (byte b : address) {
            if (b != (byte) -1) {
                return false;
            }
        }
        return true;
    }

    private static IllegalArgumentException formatIllegalArgumentException(String str, Object... objArr) {
        return new IllegalArgumentException(String.format(Locale.ROOT, str, objArr));
    }
}
