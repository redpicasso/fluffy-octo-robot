package okhttp3.internal.http;

import com.adobe.xmp.XMPError;
import com.drew.metadata.exif.makernotes.OlympusRawInfoMakernoteDirectory;
import com.google.logging.type.LogSeverity;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import okhttp3.Challenge;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.ByteString;

public final class HttpHeaders {
    private static final ByteString QUOTED_STRING_DELIMITERS = ByteString.encodeUtf8("\"\\");
    private static final ByteString TOKEN_DELIMITERS = ByteString.encodeUtf8("\t ,=");

    private HttpHeaders() {
    }

    public static long contentLength(Response response) {
        return contentLength(response.headers());
    }

    public static long contentLength(Headers headers) {
        return stringToLong(headers.get(com.google.common.net.HttpHeaders.CONTENT_LENGTH));
    }

    private static long stringToLong(String str) {
        long j = -1;
        if (str == null) {
            return -1;
        }
        try {
            j = Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return j;
        }
    }

    public static boolean varyMatches(Response response, Headers headers, Request request) {
        for (String str : varyFields(response)) {
            if (!Util.equal(headers.values(str), request.headers(str))) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasVaryAll(Response response) {
        return hasVaryAll(response.headers());
    }

    public static boolean hasVaryAll(Headers headers) {
        return varyFields(headers).contains("*");
    }

    private static Set<String> varyFields(Response response) {
        return varyFields(response.headers());
    }

    public static Set<String> varyFields(Headers headers) {
        Set<String> emptySet = Collections.emptySet();
        int size = headers.size();
        Set<String> set = emptySet;
        for (int i = 0; i < size; i++) {
            if (com.google.common.net.HttpHeaders.VARY.equalsIgnoreCase(headers.name(i))) {
                String value = headers.value(i);
                if (set.isEmpty()) {
                    set = new TreeSet(String.CASE_INSENSITIVE_ORDER);
                }
                for (String trim : value.split(",")) {
                    set.add(trim.trim());
                }
            }
        }
        return set;
    }

    public static Headers varyHeaders(Response response) {
        return varyHeaders(response.networkResponse().request().headers(), response.headers());
    }

    public static Headers varyHeaders(Headers headers, Headers headers2) {
        Set varyFields = varyFields(headers2);
        if (varyFields.isEmpty()) {
            return new Builder().build();
        }
        Builder builder = new Builder();
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            String name = headers.name(i);
            if (varyFields.contains(name)) {
                builder.add(name, headers.value(i));
            }
        }
        return builder.build();
    }

    public static List<Challenge> parseChallenges(Headers headers, String str) {
        List<Challenge> arrayList = new ArrayList();
        for (int i = 0; i < headers.size(); i++) {
            if (str.equalsIgnoreCase(headers.name(i))) {
                parseChallengeHeader(arrayList, new Buffer().writeUtf8(headers.value(i)));
            }
        }
        return arrayList;
    }

    private static void parseChallengeHeader(List<Challenge> list, Buffer buffer) {
        while (true) {
            String readToken;
            int skipAll;
            String str = null;
            while (true) {
                if (str == null) {
                    skipWhitespaceAndCommas(buffer);
                    str = readToken(buffer);
                    if (str == null) {
                        return;
                    }
                }
                boolean skipWhitespaceAndCommas = skipWhitespaceAndCommas(buffer);
                readToken = readToken(buffer);
                if (readToken != null) {
                    skipAll = skipAll(buffer, (byte) 61);
                    boolean skipWhitespaceAndCommas2 = skipWhitespaceAndCommas(buffer);
                    if (skipWhitespaceAndCommas || !(skipWhitespaceAndCommas2 || buffer.exhausted())) {
                        Map linkedHashMap = new LinkedHashMap();
                        skipAll += skipAll(buffer, (byte) 61);
                        while (true) {
                            if (readToken == null) {
                                readToken = readToken(buffer);
                                if (skipWhitespaceAndCommas(buffer)) {
                                    continue;
                                    break;
                                }
                                skipAll = skipAll(buffer, (byte) 61);
                            }
                            if (skipAll == 0) {
                                continue;
                                break;
                            } else if (skipAll <= 1 && !skipWhitespaceAndCommas(buffer)) {
                                Object readToken2;
                                if (buffer.exhausted() || buffer.getByte(0) != (byte) 34) {
                                    readToken2 = readToken(buffer);
                                } else {
                                    readToken2 = readQuotedString(buffer);
                                }
                                if (readToken2 == null || ((String) linkedHashMap.put(r3, readToken2)) != null) {
                                    return;
                                }
                                if (skipWhitespaceAndCommas(buffer) || buffer.exhausted()) {
                                    readToken = null;
                                } else {
                                    return;
                                }
                            } else {
                                return;
                            }
                        }
                        list.add(new Challenge(str, linkedHashMap));
                        str = readToken;
                    }
                } else if (buffer.exhausted()) {
                    list.add(new Challenge(str, Collections.emptyMap()));
                    return;
                } else {
                    return;
                }
            }
            String str2 = (String) null;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(readToken);
            stringBuilder.append(repeat('=', skipAll));
            list.add(new Challenge(str, Collections.singletonMap(str2, stringBuilder.toString())));
        }
    }

    private static boolean skipWhitespaceAndCommas(Buffer buffer) {
        boolean z = false;
        while (!buffer.exhausted()) {
            byte b = buffer.getByte(0);
            if (b != (byte) 44) {
                if (b != (byte) 32 && b != (byte) 9) {
                    break;
                }
                buffer.readByte();
            } else {
                buffer.readByte();
                z = true;
            }
        }
        return z;
    }

    private static int skipAll(Buffer buffer, byte b) {
        int i = 0;
        while (!buffer.exhausted() && buffer.getByte(0) == b) {
            i++;
            buffer.readByte();
        }
        return i;
    }

    private static String readQuotedString(Buffer buffer) {
        if (buffer.readByte() == (byte) 34) {
            Buffer buffer2 = new Buffer();
            while (true) {
                long indexOfElement = buffer.indexOfElement(QUOTED_STRING_DELIMITERS);
                if (indexOfElement == -1) {
                    return null;
                }
                if (buffer.getByte(indexOfElement) == (byte) 34) {
                    buffer2.write(buffer, indexOfElement);
                    buffer.readByte();
                    return buffer2.readUtf8();
                } else if (buffer.size() == indexOfElement + 1) {
                    return null;
                } else {
                    buffer2.write(buffer, indexOfElement);
                    buffer.readByte();
                    buffer2.write(buffer, 1);
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static String readToken(Buffer buffer) {
        try {
            long indexOfElement = buffer.indexOfElement(TOKEN_DELIMITERS);
            if (indexOfElement == -1) {
                indexOfElement = buffer.size();
            }
            return indexOfElement != 0 ? buffer.readUtf8(indexOfElement) : null;
        } catch (EOFException unused) {
            throw new AssertionError();
        }
    }

    private static String repeat(char c, int i) {
        char[] cArr = new char[i];
        Arrays.fill(cArr, c);
        return new String(cArr);
    }

    public static void receiveHeaders(CookieJar cookieJar, HttpUrl httpUrl, Headers headers) {
        if (cookieJar != CookieJar.NO_COOKIES) {
            List parseAll = Cookie.parseAll(httpUrl, headers);
            if (!parseAll.isEmpty()) {
                cookieJar.saveFromResponse(httpUrl, parseAll);
            }
        }
    }

    public static boolean hasBody(Response response) {
        if (response.request().method().equals("HEAD")) {
            return false;
        }
        int code = response.code();
        if (((code >= 100 && code < LogSeverity.INFO_VALUE) || code == XMPError.BADSTREAM || code == OlympusRawInfoMakernoteDirectory.TagWbRbLevelsDaylightFluor) && contentLength(response) == -1) {
            if ("chunked".equalsIgnoreCase(response.header(com.google.common.net.HttpHeaders.TRANSFER_ENCODING))) {
                return true;
            }
            return false;
        }
        return true;
    }

    public static int skipUntil(String str, int i, String str2) {
        while (i < str.length() && str2.indexOf(str.charAt(i)) == -1) {
            i++;
        }
        return i;
    }

    public static int skipWhitespace(String str, int i) {
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt != ' ' && charAt != 9) {
                break;
            }
            i++;
        }
        return i;
    }

    public static int parseSeconds(String str, int i) {
        try {
            long parseLong = Long.parseLong(str);
            if (parseLong > 2147483647L) {
                return Integer.MAX_VALUE;
            }
            if (parseLong < 0) {
                return 0;
            }
            i = (int) parseLong;
        } catch (NumberFormatException unused) {
            return i;
        }
    }
}
