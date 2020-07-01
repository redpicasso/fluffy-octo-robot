package com.facebook.react.devsupport;

import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

public class MultipartStreamReader {
    private static final String CRLF = "\r\n";
    private final String mBoundary;
    private long mLastProgressEvent;
    private final BufferedSource mSource;

    public interface ChunkListener {
        void onChunkComplete(Map<String, String> map, Buffer buffer, boolean z) throws IOException;

        void onChunkProgress(Map<String, String> map, long j, long j2) throws IOException;
    }

    public MultipartStreamReader(BufferedSource bufferedSource, String str) {
        this.mSource = bufferedSource;
        this.mBoundary = str;
    }

    private Map<String, String> parseHeaders(Buffer buffer) {
        Map<String, String> hashMap = new HashMap();
        for (String str : buffer.readUtf8().split(CRLF)) {
            int indexOf = str.indexOf(":");
            if (indexOf != -1) {
                hashMap.put(str.substring(0, indexOf).trim(), str.substring(indexOf + 1).trim());
            }
        }
        return hashMap;
    }

    private void emitChunk(Buffer buffer, boolean z, ChunkListener chunkListener) throws IOException {
        ByteString encodeUtf8 = ByteString.encodeUtf8("\r\n\r\n");
        long indexOf = buffer.indexOf(encodeUtf8);
        if (indexOf == -1) {
            chunkListener.onChunkComplete(null, buffer, z);
            return;
        }
        Buffer buffer2 = new Buffer();
        Object buffer3 = new Buffer();
        buffer.read(buffer2, indexOf);
        buffer.skip((long) encodeUtf8.size());
        buffer.readAll(buffer3);
        chunkListener.onChunkComplete(parseHeaders(buffer2), buffer3, z);
    }

    private void emitProgress(Map<String, String> map, long j, boolean z, ChunkListener chunkListener) throws IOException {
        if (map != null && chunkListener != null) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.mLastProgressEvent > 16 || z) {
                this.mLastProgressEvent = currentTimeMillis;
                String str = HttpHeaders.CONTENT_LENGTH;
                chunkListener.onChunkProgress(map, j, map.get(str) != null ? Long.parseLong((String) map.get(str)) : 0);
            }
        }
    }

    public boolean readAllParts(ChunkListener chunkListener) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String str = "\r\n--";
        stringBuilder.append(str);
        stringBuilder.append(this.mBoundary);
        String str2 = CRLF;
        stringBuilder.append(str2);
        ByteString encodeUtf8 = ByteString.encodeUtf8(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(this.mBoundary);
        stringBuilder.append("--");
        stringBuilder.append(str2);
        ByteString encodeUtf82 = ByteString.encodeUtf8(stringBuilder.toString());
        ByteString encodeUtf83 = ByteString.encodeUtf8("\r\n\r\n");
        Buffer buffer = new Buffer();
        long j = 0;
        long j2 = j;
        long j3 = j2;
        Map map = null;
        while (true) {
            boolean z;
            long max = Math.max(j - ((long) encodeUtf82.size()), j2);
            j = buffer.indexOf(encodeUtf8, max);
            if (j == -1) {
                j = buffer.indexOf(encodeUtf82, max);
                z = true;
            } else {
                z = false;
            }
            if (j == -1) {
                long size = buffer.size();
                if (map == null) {
                    j = buffer.indexOf(encodeUtf83, max);
                    if (j >= 0) {
                        this.mSource.read(buffer, j);
                        Buffer buffer2 = new Buffer();
                        buffer.copyTo(buffer2, max, j - max);
                        j3 = buffer2.size() + ((long) encodeUtf83.size());
                        map = parseHeaders(buffer2);
                    }
                } else {
                    emitProgress(map, buffer.size() - j3, false, chunkListener);
                }
                if (this.mSource.read(buffer, (long) 4096) <= 0) {
                    return false;
                }
                j = size;
            } else {
                max = j - j2;
                if (j2 > 0) {
                    Buffer buffer3 = new Buffer();
                    buffer.skip(j2);
                    buffer.read(buffer3, max);
                    Buffer buffer4 = buffer3;
                    emitProgress(map, buffer3.size() - j3, true, chunkListener);
                    emitChunk(buffer4, z, chunkListener);
                    j3 = 0;
                    map = null;
                } else {
                    ChunkListener chunkListener2 = chunkListener;
                    buffer.skip(j);
                }
                if (z) {
                    return true;
                }
                j2 = (long) encodeUtf8.size();
                j = j2;
            }
        }
    }
}
