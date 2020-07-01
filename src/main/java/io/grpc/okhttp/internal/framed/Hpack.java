package io.grpc.okhttp.internal.framed;

import com.facebook.common.util.UriUtil;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import io.grpc.internal.GrpcUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;

final class Hpack {
    private static final Map<ByteString, Integer> NAME_TO_FIRST_INDEX = nameToFirstIndex();
    private static final int PREFIX_4_BITS = 15;
    private static final int PREFIX_5_BITS = 31;
    private static final int PREFIX_6_BITS = 63;
    private static final int PREFIX_7_BITS = 127;
    private static final Header[] STATIC_HEADER_TABLE;

    static final class Reader {
        Header[] dynamicTable = new Header[8];
        int dynamicTableByteCount = 0;
        int dynamicTableHeaderCount = 0;
        private final List<Header> headerList = new ArrayList();
        private int headerTableSizeSetting;
        private int maxDynamicTableByteCount;
        int nextDynamicTableIndex = (this.dynamicTable.length - 1);
        private final BufferedSource source;

        Reader(int i, Source source) {
            this.headerTableSizeSetting = i;
            this.maxDynamicTableByteCount = i;
            this.source = Okio.buffer(source);
        }

        int maxDynamicTableByteCount() {
            return this.maxDynamicTableByteCount;
        }

        void headerTableSizeSetting(int i) {
            this.headerTableSizeSetting = i;
            this.maxDynamicTableByteCount = i;
            adjustDynamicTableByteCount();
        }

        private void adjustDynamicTableByteCount() {
            int i = this.maxDynamicTableByteCount;
            int i2 = this.dynamicTableByteCount;
            if (i >= i2) {
                return;
            }
            if (i == 0) {
                clearDynamicTable();
            } else {
                evictToRecoverBytes(i2 - i);
            }
        }

        private void clearDynamicTable() {
            Arrays.fill(this.dynamicTable, null);
            this.nextDynamicTableIndex = this.dynamicTable.length - 1;
            this.dynamicTableHeaderCount = 0;
            this.dynamicTableByteCount = 0;
        }

        private int evictToRecoverBytes(int i) {
            int i2 = 0;
            if (i > 0) {
                Object obj;
                int length = this.dynamicTable.length;
                while (true) {
                    length--;
                    if (length < this.nextDynamicTableIndex || i <= 0) {
                        obj = this.dynamicTable;
                        length = this.nextDynamicTableIndex;
                        System.arraycopy(obj, length + 1, obj, (length + 1) + i2, this.dynamicTableHeaderCount);
                        this.nextDynamicTableIndex += i2;
                    } else {
                        i -= this.dynamicTable[length].hpackSize;
                        this.dynamicTableByteCount -= this.dynamicTable[length].hpackSize;
                        this.dynamicTableHeaderCount--;
                        i2++;
                    }
                }
                obj = this.dynamicTable;
                length = this.nextDynamicTableIndex;
                System.arraycopy(obj, length + 1, obj, (length + 1) + i2, this.dynamicTableHeaderCount);
                this.nextDynamicTableIndex += i2;
            }
            return i2;
        }

        void readHeaders() throws IOException {
            while (!this.source.exhausted()) {
                int readByte = this.source.readByte() & 255;
                if (readByte == 128) {
                    throw new IOException("index == 0");
                } else if ((readByte & 128) == 128) {
                    readIndexedHeader(readInt(readByte, Hpack.PREFIX_7_BITS) - 1);
                } else if (readByte == 64) {
                    readLiteralHeaderWithIncrementalIndexingNewName();
                } else if ((readByte & 64) == 64) {
                    readLiteralHeaderWithIncrementalIndexingIndexedName(readInt(readByte, 63) - 1);
                } else if ((readByte & 32) == 32) {
                    this.maxDynamicTableByteCount = readInt(readByte, 31);
                    readByte = this.maxDynamicTableByteCount;
                    if (readByte < 0 || readByte > this.headerTableSizeSetting) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Invalid dynamic table size update ");
                        stringBuilder.append(this.maxDynamicTableByteCount);
                        throw new IOException(stringBuilder.toString());
                    }
                    adjustDynamicTableByteCount();
                } else if (readByte == 16 || readByte == 0) {
                    readLiteralHeaderWithoutIndexingNewName();
                } else {
                    readLiteralHeaderWithoutIndexingIndexedName(readInt(readByte, 15) - 1);
                }
            }
        }

        public List<Header> getAndResetHeaderList() {
            List arrayList = new ArrayList(this.headerList);
            this.headerList.clear();
            return arrayList;
        }

        private void readIndexedHeader(int i) throws IOException {
            if (isStaticHeader(i)) {
                this.headerList.add(Hpack.STATIC_HEADER_TABLE[i]);
                return;
            }
            int dynamicTableIndex = dynamicTableIndex(i - Hpack.STATIC_HEADER_TABLE.length);
            if (dynamicTableIndex >= 0) {
                Header[] headerArr = this.dynamicTable;
                if (dynamicTableIndex <= headerArr.length - 1) {
                    this.headerList.add(headerArr[dynamicTableIndex]);
                    return;
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Header index too large ");
            stringBuilder.append(i + 1);
            throw new IOException(stringBuilder.toString());
        }

        private int dynamicTableIndex(int i) {
            return (this.nextDynamicTableIndex + 1) + i;
        }

        private void readLiteralHeaderWithoutIndexingIndexedName(int i) throws IOException {
            this.headerList.add(new Header(getName(i), readByteString()));
        }

        private void readLiteralHeaderWithoutIndexingNewName() throws IOException {
            this.headerList.add(new Header(Hpack.checkLowercase(readByteString()), readByteString()));
        }

        private void readLiteralHeaderWithIncrementalIndexingIndexedName(int i) throws IOException {
            insertIntoDynamicTable(-1, new Header(getName(i), readByteString()));
        }

        private void readLiteralHeaderWithIncrementalIndexingNewName() throws IOException {
            insertIntoDynamicTable(-1, new Header(Hpack.checkLowercase(readByteString()), readByteString()));
        }

        private ByteString getName(int i) {
            if (isStaticHeader(i)) {
                return Hpack.STATIC_HEADER_TABLE[i].name;
            }
            return this.dynamicTable[dynamicTableIndex(i - Hpack.STATIC_HEADER_TABLE.length)].name;
        }

        private boolean isStaticHeader(int i) {
            return i >= 0 && i <= Hpack.STATIC_HEADER_TABLE.length - 1;
        }

        private void insertIntoDynamicTable(int i, Header header) {
            this.headerList.add(header);
            int i2 = header.hpackSize;
            if (i != -1) {
                i2 -= this.dynamicTable[dynamicTableIndex(i)].hpackSize;
            }
            int i3 = this.maxDynamicTableByteCount;
            if (i2 > i3) {
                clearDynamicTable();
                return;
            }
            i3 = evictToRecoverBytes((this.dynamicTableByteCount + i2) - i3);
            if (i == -1) {
                i = this.dynamicTableHeaderCount + 1;
                Object obj = this.dynamicTable;
                if (i > obj.length) {
                    Object obj2 = new Header[(obj.length * 2)];
                    System.arraycopy(obj, 0, obj2, obj.length, obj.length);
                    this.nextDynamicTableIndex = this.dynamicTable.length - 1;
                    this.dynamicTable = obj2;
                }
                i = this.nextDynamicTableIndex;
                this.nextDynamicTableIndex = i - 1;
                this.dynamicTable[i] = header;
                this.dynamicTableHeaderCount++;
            } else {
                this.dynamicTable[i + (dynamicTableIndex(i) + i3)] = header;
            }
            this.dynamicTableByteCount += i2;
        }

        private int readByte() throws IOException {
            return this.source.readByte() & 255;
        }

        int readInt(int i, int i2) throws IOException {
            i &= i2;
            if (i < i2) {
                return i;
            }
            i = 0;
            while (true) {
                int readByte = readByte();
                if ((readByte & 128) == 0) {
                    return i2 + (readByte << i);
                }
                i2 += (readByte & Hpack.PREFIX_7_BITS) << i;
                i += 7;
            }
        }

        ByteString readByteString() throws IOException {
            int readByte = readByte();
            Object obj = (readByte & 128) == 128 ? 1 : null;
            readByte = readInt(readByte, Hpack.PREFIX_7_BITS);
            if (obj != null) {
                return ByteString.of(Huffman.get().decode(this.source.readByteArray((long) readByte)));
            }
            return this.source.readByteString((long) readByte);
        }
    }

    static final class Writer {
        private final Buffer out;

        Writer(Buffer buffer) {
            this.out = buffer;
        }

        void writeHeaders(List<Header> list) throws IOException {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ByteString toAsciiLowercase = ((Header) list.get(i)).name.toAsciiLowercase();
                Integer num = (Integer) Hpack.NAME_TO_FIRST_INDEX.get(toAsciiLowercase);
                if (num != null) {
                    writeInt(num.intValue() + 1, 15, 0);
                    writeByteString(((Header) list.get(i)).value);
                } else {
                    this.out.writeByte(0);
                    writeByteString(toAsciiLowercase);
                    writeByteString(((Header) list.get(i)).value);
                }
            }
        }

        void writeInt(int i, int i2, int i3) throws IOException {
            if (i < i2) {
                this.out.writeByte(i | i3);
                return;
            }
            this.out.writeByte(i3 | i2);
            i -= i2;
            while (i >= 128) {
                this.out.writeByte(128 | (i & Hpack.PREFIX_7_BITS));
                i >>>= 7;
            }
            this.out.writeByte(i);
        }

        void writeByteString(ByteString byteString) throws IOException {
            writeInt(byteString.size(), Hpack.PREFIX_7_BITS, 0);
            this.out.write(byteString);
        }
    }

    static {
        r0 = new Header[61];
        String str = "";
        r0[0] = new Header(Header.TARGET_AUTHORITY, str);
        r0[1] = new Header(Header.TARGET_METHOD, "GET");
        r0[2] = new Header(Header.TARGET_METHOD, GrpcUtil.HTTP_METHOD);
        r0[3] = new Header(Header.TARGET_PATH, "/");
        r0[4] = new Header(Header.TARGET_PATH, "/index.html");
        r0[5] = new Header(Header.TARGET_SCHEME, UriUtil.HTTP_SCHEME);
        r0[6] = new Header(Header.TARGET_SCHEME, UriUtil.HTTPS_SCHEME);
        r0[7] = new Header(Header.RESPONSE_STATUS, "200");
        r0[8] = new Header(Header.RESPONSE_STATUS, "204");
        r0[9] = new Header(Header.RESPONSE_STATUS, "206");
        r0[10] = new Header(Header.RESPONSE_STATUS, "304");
        r0[11] = new Header(Header.RESPONSE_STATUS, "400");
        r0[12] = new Header(Header.RESPONSE_STATUS, "404");
        r0[13] = new Header(Header.RESPONSE_STATUS, "500");
        r0[14] = new Header("accept-charset", str);
        r0[15] = new Header(GrpcUtil.CONTENT_ACCEPT_ENCODING, "gzip, deflate");
        r0[16] = new Header("accept-language", str);
        r0[17] = new Header("accept-ranges", str);
        r0[18] = new Header("accept", str);
        r0[19] = new Header("access-control-allow-origin", str);
        r0[20] = new Header("age", str);
        r0[21] = new Header("allow", str);
        r0[22] = new Header("authorization", str);
        r0[23] = new Header("cache-control", str);
        r0[24] = new Header("content-disposition", str);
        r0[25] = new Header(GrpcUtil.CONTENT_ENCODING, str);
        r0[26] = new Header("content-language", str);
        r0[27] = new Header("content-length", str);
        r0[28] = new Header("content-location", str);
        r0[29] = new Header("content-range", str);
        r0[30] = new Header("content-type", str);
        r0[31] = new Header("cookie", str);
        r0[32] = new Header("date", str);
        r0[33] = new Header("etag", str);
        r0[34] = new Header("expect", str);
        r0[35] = new Header("expires", str);
        r0[36] = new Header("from", str);
        r0[37] = new Header("host", str);
        r0[38] = new Header("if-match", str);
        r0[39] = new Header("if-modified-since", str);
        r0[40] = new Header("if-none-match", str);
        r0[41] = new Header("if-range", str);
        r0[42] = new Header("if-unmodified-since", str);
        r0[43] = new Header("last-modified", str);
        r0[44] = new Header("link", str);
        r0[45] = new Header(Param.LOCATION, str);
        r0[46] = new Header("max-forwards", str);
        r0[47] = new Header("proxy-authenticate", str);
        r0[48] = new Header("proxy-authorization", str);
        r0[49] = new Header("range", str);
        r0[50] = new Header("referer", str);
        r0[51] = new Header("refresh", str);
        r0[52] = new Header("retry-after", str);
        r0[53] = new Header("server", str);
        r0[54] = new Header("set-cookie", str);
        r0[55] = new Header("strict-transport-security", str);
        r0[56] = new Header("transfer-encoding", str);
        r0[57] = new Header("user-agent", str);
        r0[58] = new Header("vary", str);
        r0[59] = new Header("via", str);
        r0[60] = new Header("www-authenticate", str);
        STATIC_HEADER_TABLE = r0;
    }

    private Hpack() {
    }

    private static Map<ByteString, Integer> nameToFirstIndex() {
        Map linkedHashMap = new LinkedHashMap(STATIC_HEADER_TABLE.length);
        int i = 0;
        while (true) {
            Header[] headerArr = STATIC_HEADER_TABLE;
            if (i >= headerArr.length) {
                return Collections.unmodifiableMap(linkedHashMap);
            }
            if (!linkedHashMap.containsKey(headerArr[i].name)) {
                linkedHashMap.put(STATIC_HEADER_TABLE[i].name, Integer.valueOf(i));
            }
            i++;
        }
    }

    private static ByteString checkLowercase(ByteString byteString) throws IOException {
        int size = byteString.size();
        int i = 0;
        while (i < size) {
            byte b = byteString.getByte(i);
            if (b < (byte) 65 || b > (byte) 90) {
                i++;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("PROTOCOL_ERROR response malformed: mixed case name: ");
                stringBuilder.append(byteString.utf8());
                throw new IOException(stringBuilder.toString());
            }
        }
        return byteString;
    }
}
