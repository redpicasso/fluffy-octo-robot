package com.google.firebase.database.tubesock;

import android.util.Base64;
import com.google.common.net.HttpHeaders;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
class WebSocketHandshake {
    private static final String WEBSOCKET_VERSION = "13";
    private Map<String, String> extraHeaders = null;
    private String nonce = null;
    private String protocol = null;
    private URI url = null;

    public WebSocketHandshake(URI uri, String str, Map<String, String> map) {
        this.url = uri;
        this.protocol = str;
        this.extraHeaders = map;
        this.nonce = createNonce();
    }

    public byte[] getHandshake() {
        String path = this.url.getPath();
        String query = this.url.getQuery();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(path);
        if (query == null) {
            path = "";
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("?");
            stringBuilder2.append(query);
            path = stringBuilder2.toString();
        }
        stringBuilder.append(path);
        path = stringBuilder.toString();
        Object host = this.url.getHost();
        if (this.url.getPort() != -1) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(host);
            stringBuilder.append(":");
            stringBuilder.append(this.url.getPort());
            host = stringBuilder.toString();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(HttpHeaders.HOST, host);
        query = HttpHeaders.UPGRADE;
        linkedHashMap.put(query, "websocket");
        linkedHashMap.put(HttpHeaders.CONNECTION, query);
        linkedHashMap.put("Sec-WebSocket-Version", WEBSOCKET_VERSION);
        linkedHashMap.put("Sec-WebSocket-Key", this.nonce);
        query = this.protocol;
        if (query != null) {
            linkedHashMap.put("Sec-WebSocket-Protocol", query);
        }
        Map map = this.extraHeaders;
        if (map != null) {
            for (String str : map.keySet()) {
                if (!linkedHashMap.containsKey(str)) {
                    linkedHashMap.put(str, (String) this.extraHeaders.get(str));
                }
            }
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("GET ");
        stringBuilder3.append(path);
        stringBuilder3.append(" HTTP/1.1\r\n");
        path = stringBuilder3.toString();
        stringBuilder3 = new StringBuilder();
        stringBuilder3.append(path);
        stringBuilder3.append(generateHeader(linkedHashMap));
        path = stringBuilder3.toString();
        stringBuilder3 = new StringBuilder();
        stringBuilder3.append(path);
        stringBuilder3.append("\r\n");
        Object bytes = stringBuilder3.toString().getBytes(Charset.defaultCharset());
        host = new byte[bytes.length];
        System.arraycopy(bytes, 0, host, 0, bytes.length);
        return host;
    }

    private String generateHeader(LinkedHashMap<String, String> linkedHashMap) {
        String str = new String();
        for (String str2 : linkedHashMap.keySet()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(str2);
            stringBuilder.append(": ");
            stringBuilder.append((String) linkedHashMap.get(str2));
            stringBuilder.append("\r\n");
            str = stringBuilder.toString();
        }
        return str;
    }

    private String createNonce() {
        byte[] bArr = new byte[16];
        for (int i = 0; i < 16; i++) {
            bArr[i] = (byte) rand(0, 255);
        }
        return Base64.encodeToString(bArr, 2);
    }

    public void verifyServerStatusLine(String str) {
        int parseInt = Integer.parseInt(str.substring(9, 12));
        if (parseInt == 407) {
            throw new WebSocketException("connection failed: proxy authentication not supported");
        } else if (parseInt == 404) {
            throw new WebSocketException("connection failed: 404 not found");
        } else if (parseInt != 101) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("connection failed: unknown status code ");
            stringBuilder.append(parseInt);
            throw new WebSocketException(stringBuilder.toString());
        }
    }

    public void verifyServerHandshakeHeaders(HashMap<String, String> hashMap) {
        String str = "upgrade";
        if (!"websocket".equals(hashMap.get(str))) {
            throw new WebSocketException("connection failed: missing header field in server handshake: Upgrade");
        } else if (!str.equals(hashMap.get("connection"))) {
            throw new WebSocketException("connection failed: missing header field in server handshake: Connection");
        }
    }

    private int rand(int i, int i2) {
        return (int) ((Math.random() * ((double) i2)) + ((double) i));
    }
}
