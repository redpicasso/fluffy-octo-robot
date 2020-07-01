package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;

@GwtIncompatible
abstract class LineBuffer {
    private StringBuilder line = new StringBuilder();
    private boolean sawReturn;

    protected abstract void handleLine(String str, String str2) throws IOException;

    LineBuffer() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x001f  */
    protected void add(char[] r7, int r8, int r9) throws java.io.IOException {
        /*
        r6 = this;
        r0 = r6.sawReturn;
        r1 = 0;
        r2 = 10;
        r3 = 1;
        if (r0 == 0) goto L_0x001a;
    L_0x0008:
        if (r9 <= 0) goto L_0x001a;
    L_0x000a:
        r0 = r7[r8];
        if (r0 != r2) goto L_0x0010;
    L_0x000e:
        r0 = 1;
        goto L_0x0011;
    L_0x0010:
        r0 = 0;
    L_0x0011:
        r0 = r6.finishLine(r0);
        if (r0 == 0) goto L_0x001a;
    L_0x0017:
        r0 = r8 + 1;
        goto L_0x001b;
    L_0x001a:
        r0 = r8;
    L_0x001b:
        r8 = r8 + r9;
        r9 = r0;
    L_0x001d:
        if (r0 >= r8) goto L_0x0052;
    L_0x001f:
        r4 = r7[r0];
        if (r4 == r2) goto L_0x0044;
    L_0x0023:
        r5 = 13;
        if (r4 == r5) goto L_0x0028;
    L_0x0027:
        goto L_0x0050;
    L_0x0028:
        r4 = r6.line;
        r5 = r0 - r9;
        r4.append(r7, r9, r5);
        r6.sawReturn = r3;
        r9 = r0 + 1;
        if (r9 >= r8) goto L_0x004e;
    L_0x0035:
        r4 = r7[r9];
        if (r4 != r2) goto L_0x003b;
    L_0x0039:
        r4 = 1;
        goto L_0x003c;
    L_0x003b:
        r4 = 0;
    L_0x003c:
        r4 = r6.finishLine(r4);
        if (r4 == 0) goto L_0x004e;
    L_0x0042:
        r0 = r9;
        goto L_0x004e;
    L_0x0044:
        r4 = r6.line;
        r5 = r0 - r9;
        r4.append(r7, r9, r5);
        r6.finishLine(r3);
    L_0x004e:
        r9 = r0 + 1;
    L_0x0050:
        r0 = r0 + r3;
        goto L_0x001d;
    L_0x0052:
        r0 = r6.line;
        r8 = r8 - r9;
        r0.append(r7, r9, r8);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.io.LineBuffer.add(char[], int, int):void");
    }

    @CanIgnoreReturnValue
    private boolean finishLine(boolean z) throws IOException {
        String str = this.sawReturn ? z ? "\r\n" : "\r" : z ? ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE : "";
        handleLine(this.line.toString(), str);
        this.line = new StringBuilder();
        this.sawReturn = false;
        return z;
    }

    protected void finish() throws IOException {
        if (this.sawReturn || this.line.length() > 0) {
            finishLine(false);
        }
    }
}
