package okhttp3.internal;

import com.bumptech.glide.load.Key;
import com.google.common.base.Ascii;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.IDN;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.internal.http2.Header;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;

public final class Util {
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final RequestBody EMPTY_REQUEST = RequestBody.create(null, EMPTY_BYTE_ARRAY);
    public static final ResponseBody EMPTY_RESPONSE = ResponseBody.create(null, EMPTY_BYTE_ARRAY);
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Comparator<String> NATURAL_ORDER = new Comparator<String>() {
        public int compare(String str, String str2) {
            return str.compareTo(str2);
        }
    };
    public static final TimeZone UTC = TimeZone.getTimeZone("GMT");
    private static final Charset UTF_16_BE = Charset.forName("UTF-16BE");
    private static final ByteString UTF_16_BE_BOM = ByteString.decodeHex("feff");
    private static final Charset UTF_16_LE = Charset.forName("UTF-16LE");
    private static final ByteString UTF_16_LE_BOM = ByteString.decodeHex("fffe");
    private static final Charset UTF_32_BE = Charset.forName("UTF-32BE");
    private static final ByteString UTF_32_BE_BOM = ByteString.decodeHex("0000ffff");
    private static final Charset UTF_32_LE = Charset.forName("UTF-32LE");
    private static final ByteString UTF_32_LE_BOM = ByteString.decodeHex("ffff0000");
    public static final Charset UTF_8 = Charset.forName(Key.STRING_CHARSET_NAME);
    private static final ByteString UTF_8_BOM = ByteString.decodeHex("efbbbf");
    private static final Pattern VERIFY_AS_IP_ADDRESS = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");
    private static final Method addSuppressedExceptionMethod;

    public static int decodeHexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        int i = 97;
        if (c < 'a' || c > 'f') {
            i = 65;
            if (c < 'A' || c > 'F') {
                return -1;
            }
        }
        return (c - i) + 10;
    }

    static {
        Method method = null;
        try {
            method = Throwable.class.getDeclaredMethod("addSuppressed", new Class[]{Throwable.class});
        } catch (Exception unused) {
            addSuppressedExceptionMethod = method;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:5:0x000d A:{RETURN, ExcHandler: java.lang.reflect.InvocationTargetException (unused java.lang.reflect.InvocationTargetException), Splitter: B:3:0x0005} */
    /* JADX WARNING: Missing block: B:5:0x000d, code:
            return;
     */
    public static void addSuppressedIfPossible(java.lang.Throwable r3, java.lang.Throwable r4) {
        /*
        r0 = addSuppressedExceptionMethod;
        if (r0 == 0) goto L_0x000d;
    L_0x0004:
        r1 = 1;
        r1 = new java.lang.Object[r1];	 Catch:{ InvocationTargetException -> 0x000d, InvocationTargetException -> 0x000d }
        r2 = 0;
        r1[r2] = r4;	 Catch:{ InvocationTargetException -> 0x000d, InvocationTargetException -> 0x000d }
        r0.invoke(r3, r1);	 Catch:{ InvocationTargetException -> 0x000d, InvocationTargetException -> 0x000d }
    L_0x000d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.Util.addSuppressedIfPossible(java.lang.Throwable, java.lang.Throwable):void");
    }

    private Util() {
    }

    public static void checkOffsetAndCount(long j, long j2, long j3) {
        if ((j2 | j3) < 0 || j2 > j || j - j2 < j3) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public static boolean equal(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
    }

    public static void closeQuietly(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (AssertionError e) {
                if (!isAndroidGetsocknameError(e)) {
                    throw e;
                }
            } catch (RuntimeException e2) {
                throw e2;
            } catch (Exception unused) {
            }
        }
    }

    public static void closeQuietly(ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
    }

    public static boolean discard(Source source, int i, TimeUnit timeUnit) {
        try {
            return skipAll(source, i, timeUnit);
        } catch (IOException unused) {
            return false;
        }
    }

    /* JADX WARNING: Missing block: B:23:0x0074, code:
            if (r5 != Long.MAX_VALUE) goto L_0x007e;
     */
    /* JADX WARNING: Missing block: B:24:0x0076, code:
            r11.timeout().clearDeadline();
     */
    /* JADX WARNING: Missing block: B:25:0x007e, code:
            r11.timeout().deadlineNanoTime(r0 + r5);
     */
    /* JADX WARNING: Missing block: B:26:0x0086, code:
            return false;
     */
    public static boolean skipAll(okio.Source r11, int r12, java.util.concurrent.TimeUnit r13) throws java.io.IOException {
        /*
        r0 = java.lang.System.nanoTime();
        r2 = r11.timeout();
        r2 = r2.hasDeadline();
        r3 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
        if (r2 == 0) goto L_0x001d;
    L_0x0013:
        r2 = r11.timeout();
        r5 = r2.deadlineNanoTime();
        r5 = r5 - r0;
        goto L_0x001e;
    L_0x001d:
        r5 = r3;
    L_0x001e:
        r2 = r11.timeout();
        r7 = (long) r12;
        r12 = r13.toNanos(r7);
        r12 = java.lang.Math.min(r5, r12);
        r12 = r12 + r0;
        r2.deadlineNanoTime(r12);
        r12 = new okio.Buffer;	 Catch:{ InterruptedIOException -> 0x0070, all -> 0x005a }
        r12.<init>();	 Catch:{ InterruptedIOException -> 0x0070, all -> 0x005a }
    L_0x0034:
        r7 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r7 = r11.read(r12, r7);	 Catch:{ InterruptedIOException -> 0x0070, all -> 0x005a }
        r9 = -1;
        r13 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1));
        if (r13 == 0) goto L_0x0044;
    L_0x0040:
        r12.clear();	 Catch:{ InterruptedIOException -> 0x0070, all -> 0x005a }
        goto L_0x0034;
    L_0x0044:
        r12 = 1;
        r13 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1));
        if (r13 != 0) goto L_0x0051;
    L_0x0049:
        r11 = r11.timeout();
        r11.clearDeadline();
        goto L_0x0059;
    L_0x0051:
        r11 = r11.timeout();
        r0 = r0 + r5;
        r11.deadlineNanoTime(r0);
    L_0x0059:
        return r12;
    L_0x005a:
        r12 = move-exception;
        r13 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1));
        if (r13 != 0) goto L_0x0067;
    L_0x005f:
        r11 = r11.timeout();
        r11.clearDeadline();
        goto L_0x006f;
    L_0x0067:
        r11 = r11.timeout();
        r0 = r0 + r5;
        r11.deadlineNanoTime(r0);
    L_0x006f:
        throw r12;
        r12 = 0;
        r13 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1));
        if (r13 != 0) goto L_0x007e;
    L_0x0076:
        r11 = r11.timeout();
        r11.clearDeadline();
        goto L_0x0086;
    L_0x007e:
        r11 = r11.timeout();
        r0 = r0 + r5;
        r11.deadlineNanoTime(r0);
    L_0x0086:
        return r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.Util.skipAll(okio.Source, int, java.util.concurrent.TimeUnit):boolean");
    }

    public static <T> List<T> immutableList(List<T> list) {
        return Collections.unmodifiableList(new ArrayList(list));
    }

    public static <K, V> Map<K, V> immutableMap(Map<K, V> map) {
        if (map.isEmpty()) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(new LinkedHashMap(map));
    }

    public static <T> List<T> immutableList(T... tArr) {
        return Collections.unmodifiableList(Arrays.asList((Object[]) tArr.clone()));
    }

    public static ThreadFactory threadFactory(final String str, final boolean z) {
        return new ThreadFactory() {
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, str);
                thread.setDaemon(z);
                return thread;
            }
        };
    }

    public static String[] intersect(Comparator<? super String> comparator, String[] strArr, String[] strArr2) {
        List arrayList = new ArrayList();
        for (Object obj : strArr) {
            for (Object compare : strArr2) {
                if (comparator.compare(obj, compare) == 0) {
                    arrayList.add(obj);
                    break;
                }
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static boolean nonEmptyIntersection(Comparator<String> comparator, String[] strArr, String[] strArr2) {
        if (!(strArr == null || strArr2 == null || strArr.length == 0 || strArr2.length == 0)) {
            for (Object obj : strArr) {
                for (Object compare : strArr2) {
                    if (comparator.compare(obj, compare) == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String hostHeader(HttpUrl httpUrl, boolean z) {
        String stringBuilder;
        Object obj = ":";
        if (httpUrl.host().contains(obj)) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("[");
            stringBuilder2.append(httpUrl.host());
            stringBuilder2.append("]");
            stringBuilder = stringBuilder2.toString();
        } else {
            stringBuilder = httpUrl.host();
        }
        if (!z && httpUrl.port() == HttpUrl.defaultPort(httpUrl.scheme())) {
            return stringBuilder;
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(stringBuilder);
        stringBuilder3.append(obj);
        stringBuilder3.append(httpUrl.port());
        return stringBuilder3.toString();
    }

    public static boolean isAndroidGetsocknameError(AssertionError assertionError) {
        return (assertionError.getCause() == null || assertionError.getMessage() == null || !assertionError.getMessage().contains("getsockname failed")) ? false : true;
    }

    public static int indexOf(Comparator<String> comparator, String[] strArr, String str) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (comparator.compare(strArr[i], str) == 0) {
                return i;
            }
        }
        return -1;
    }

    public static String[] concat(String[] strArr, String str) {
        Object obj = new String[(strArr.length + 1)];
        System.arraycopy(strArr, 0, obj, 0, strArr.length);
        obj[obj.length - 1] = str;
        return obj;
    }

    public static int skipLeadingAsciiWhitespace(String str, int i, int i2) {
        while (i < i2) {
            char charAt = str.charAt(i);
            if (charAt != 9 && charAt != 10 && charAt != 12 && charAt != 13 && charAt != ' ') {
                return i;
            }
            i++;
        }
        return i2;
    }

    public static int skipTrailingAsciiWhitespace(String str, int i, int i2) {
        for (i2--; i2 >= i; i2--) {
            char charAt = str.charAt(i2);
            if (charAt != 9 && charAt != 10 && charAt != 12 && charAt != 13 && charAt != ' ') {
                return i2 + 1;
            }
        }
        return i;
    }

    public static String trimSubstring(String str, int i, int i2) {
        i = skipLeadingAsciiWhitespace(str, i, i2);
        return str.substring(i, skipTrailingAsciiWhitespace(str, i, i2));
    }

    public static int delimiterOffset(String str, int i, int i2, String str2) {
        while (i < i2) {
            if (str2.indexOf(str.charAt(i)) != -1) {
                return i;
            }
            i++;
        }
        return i2;
    }

    public static int delimiterOffset(String str, int i, int i2, char c) {
        while (i < i2) {
            if (str.charAt(i) == c) {
                return i;
            }
            i++;
        }
        return i2;
    }

    public static String canonicalizeHost(String str) {
        if (str.contains(":")) {
            InetAddress decodeIpv6;
            if (str.startsWith("[") && str.endsWith("]")) {
                decodeIpv6 = decodeIpv6(str, 1, str.length() - 1);
            } else {
                decodeIpv6 = decodeIpv6(str, 0, str.length());
            }
            if (decodeIpv6 == null) {
                return null;
            }
            byte[] address = decodeIpv6.getAddress();
            if (address.length == 16) {
                return inet6AddressToAscii(address);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid IPv6 address: '");
            stringBuilder.append(str);
            stringBuilder.append("'");
            throw new AssertionError(stringBuilder.toString());
        }
        try {
            str = IDN.toASCII(str).toLowerCase(Locale.US);
            if (str.isEmpty() || containsInvalidHostnameAsciiCodes(str)) {
                return null;
            }
            return str;
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    /* JADX WARNING: Missing block: B:11:0x0023, code:
            return true;
     */
    private static boolean containsInvalidHostnameAsciiCodes(java.lang.String r5) {
        /*
        r0 = 0;
        r1 = 0;
    L_0x0002:
        r2 = r5.length();
        if (r1 >= r2) goto L_0x0024;
    L_0x0008:
        r2 = r5.charAt(r1);
        r3 = 31;
        r4 = 1;
        if (r2 <= r3) goto L_0x0023;
    L_0x0011:
        r3 = 127; // 0x7f float:1.78E-43 double:6.27E-322;
        if (r2 < r3) goto L_0x0016;
    L_0x0015:
        goto L_0x0023;
    L_0x0016:
        r3 = " #%/:?@[\\]";
        r2 = r3.indexOf(r2);
        r3 = -1;
        if (r2 == r3) goto L_0x0020;
    L_0x001f:
        return r4;
    L_0x0020:
        r1 = r1 + 1;
        goto L_0x0002;
    L_0x0023:
        return r4;
    L_0x0024:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.Util.containsInvalidHostnameAsciiCodes(java.lang.String):boolean");
    }

    public static int indexOfControlOrNonAscii(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt <= 31 || charAt >= Ascii.MAX) {
                return i;
            }
        }
        return -1;
    }

    public static boolean verifyAsIpAddress(String str) {
        return VERIFY_AS_IP_ADDRESS.matcher(str).matches();
    }

    public static String format(String str, Object... objArr) {
        return String.format(Locale.US, str, objArr);
    }

    public static Charset bomAwareCharset(BufferedSource bufferedSource, Charset charset) throws IOException {
        if (bufferedSource.rangeEquals(0, UTF_8_BOM)) {
            bufferedSource.skip((long) UTF_8_BOM.size());
            return UTF_8;
        } else if (bufferedSource.rangeEquals(0, UTF_16_BE_BOM)) {
            bufferedSource.skip((long) UTF_16_BE_BOM.size());
            return UTF_16_BE;
        } else if (bufferedSource.rangeEquals(0, UTF_16_LE_BOM)) {
            bufferedSource.skip((long) UTF_16_LE_BOM.size());
            return UTF_16_LE;
        } else if (bufferedSource.rangeEquals(0, UTF_32_BE_BOM)) {
            bufferedSource.skip((long) UTF_32_BE_BOM.size());
            return UTF_32_BE;
        } else if (!bufferedSource.rangeEquals(0, UTF_32_LE_BOM)) {
            return charset;
        } else {
            bufferedSource.skip((long) UTF_32_LE_BOM.size());
            return UTF_32_LE;
        }
    }

    public static int checkDuration(String str, long j, TimeUnit timeUnit) {
        int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        StringBuilder stringBuilder;
        if (i < 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(" < 0");
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (timeUnit != null) {
            j = timeUnit.toMillis(j);
            if (j > 2147483647L) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(" too large.");
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (j != 0 || i <= 0) {
                return (int) j;
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(" too small.");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        } else {
            throw new NullPointerException("unit == null");
        }
    }

    public static AssertionError assertionError(String str, Exception exception) {
        AssertionError assertionError = new AssertionError(str);
        try {
            assertionError.initCause(exception);
        } catch (IllegalStateException unused) {
            return assertionError;
        }
    }

    @Nullable
    private static InetAddress decodeIpv6(String str, int i, int i2) {
        Object obj = new byte[16];
        int i3 = 0;
        int i4 = -1;
        int i5 = -1;
        while (i < i2) {
            if (i3 == obj.length) {
                return null;
            }
            int decodeHexDigit;
            int i6 = i + 2;
            if (i6 > i2 || !str.regionMatches(i, "::", 0, 2)) {
                if (i3 != 0) {
                    if (str.regionMatches(i, ":", 0, 1)) {
                        i++;
                    } else if (!str.regionMatches(i, ".", 0, 1) || !decodeIpv4Suffix(str, i5, i2, obj, i3 - 2)) {
                        return null;
                    } else {
                        i3 += 2;
                    }
                }
                i5 = i;
            } else if (i4 != -1) {
                return null;
            } else {
                i3 += 2;
                if (i6 == i2) {
                    i4 = i3;
                    break;
                }
                i4 = i3;
                i5 = i6;
            }
            i = i5;
            i6 = 0;
            while (i < i2) {
                decodeHexDigit = decodeHexDigit(str.charAt(i));
                if (decodeHexDigit == -1) {
                    break;
                }
                i6 = (i6 << 4) + decodeHexDigit;
                i++;
            }
            decodeHexDigit = i - i5;
            if (decodeHexDigit == 0 || decodeHexDigit > 4) {
                return null;
            }
            int i7 = i3 + 1;
            obj[i3] = (byte) ((i6 >>> 8) & 255);
            i3 = i7 + 1;
            obj[i7] = (byte) (i6 & 255);
        }
        if (i3 != obj.length) {
            if (i4 == -1) {
                return null;
            }
            i = i3 - i4;
            System.arraycopy(obj, i4, obj, obj.length - i, i);
            Arrays.fill(obj, i4, (obj.length - i3) + i4, (byte) 0);
        }
        try {
            return InetAddress.getByAddress(obj);
        } catch (UnknownHostException unused) {
            throw new AssertionError();
        }
    }

    private static boolean decodeIpv4Suffix(String str, int i, int i2, byte[] bArr, int i3) {
        int i4 = i3;
        while (i < i2) {
            if (i4 == bArr.length) {
                return false;
            }
            if (i4 != i3) {
                if (str.charAt(i) != '.') {
                    return false;
                }
                i++;
            }
            int i5 = i;
            int i6 = 0;
            while (i5 < i2) {
                char charAt = str.charAt(i5);
                if (charAt < '0' || charAt > '9') {
                    break;
                } else if (i6 == 0 && i != i5) {
                    return false;
                } else {
                    i6 = ((i6 * 10) + charAt) - 48;
                    if (i6 > 255) {
                        return false;
                    }
                    i5++;
                }
            }
            if (i5 - i == 0) {
                return false;
            }
            i = i4 + 1;
            bArr[i4] = (byte) i6;
            i4 = i;
            i = i5;
        }
        if (i4 != i3 + 4) {
            return false;
        }
        return true;
    }

    private static String inet6AddressToAscii(byte[] bArr) {
        int i = 0;
        int i2 = 0;
        int i3 = -1;
        int i4 = 0;
        while (i2 < bArr.length) {
            int i5 = i2;
            while (i5 < 16 && bArr[i5] == (byte) 0 && bArr[i5 + 1] == (byte) 0) {
                i5 += 2;
            }
            int i6 = i5 - i2;
            if (i6 > i4 && i6 >= 4) {
                i3 = i2;
                i4 = i6;
            }
            i2 = i5 + 2;
        }
        Buffer buffer = new Buffer();
        while (i < bArr.length) {
            if (i == i3) {
                buffer.writeByte(58);
                i += i4;
                if (i == 16) {
                    buffer.writeByte(58);
                }
            } else {
                if (i > 0) {
                    buffer.writeByte(58);
                }
                buffer.writeHexadecimalUnsignedLong((long) (((bArr[i] & 255) << 8) | (bArr[i + 1] & 255)));
                i += 2;
            }
        }
        return buffer.readUtf8();
    }

    public static X509TrustManager platformTrustManager() {
        try {
            TrustManagerFactory instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            instance.init((KeyStore) null);
            TrustManager[] trustManagers = instance.getTrustManagers();
            if (trustManagers.length == 1 && (trustManagers[0] instanceof X509TrustManager)) {
                return (X509TrustManager) trustManagers[0];
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected default trust managers:");
            stringBuilder.append(Arrays.toString(trustManagers));
            throw new IllegalStateException(stringBuilder.toString());
        } catch (Exception e) {
            throw assertionError("No System TLS", e);
        }
    }

    public static Headers toHeaders(List<Header> list) {
        Builder builder = new Builder();
        for (Header header : list) {
            Internal.instance.addLenient(builder, header.name.utf8(), header.value.utf8());
        }
        return builder.build();
    }
}
