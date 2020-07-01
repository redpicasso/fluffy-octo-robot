package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.Queue;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
@Beta
public final class LineReader {
    private final char[] buf = this.cbuf.array();
    private final CharBuffer cbuf = CharStreams.createBuffer();
    private final LineBuffer lineBuf = new LineBuffer() {
        protected void handleLine(String str, String str2) {
            LineReader.this.lines.add(str);
        }
    };
    private final Queue<String> lines = new LinkedList();
    private final Readable readable;
    @NullableDecl
    private final Reader reader;

    public LineReader(Readable readable) {
        this.readable = (Readable) Preconditions.checkNotNull(readable);
        this.reader = readable instanceof Reader ? (Reader) readable : null;
    }

    @CanIgnoreReturnValue
    public String readLine() throws IOException {
        while (this.lines.peek() == null) {
            int read;
            this.cbuf.clear();
            Reader reader = this.reader;
            if (reader != null) {
                char[] cArr = this.buf;
                read = reader.read(cArr, 0, cArr.length);
            } else {
                read = this.readable.read(this.cbuf);
            }
            if (read == -1) {
                this.lineBuf.finish();
                break;
            }
            this.lineBuf.add(this.buf, 0, read);
        }
        return (String) this.lines.poll();
    }
}
