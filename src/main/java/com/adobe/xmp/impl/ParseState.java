package com.adobe.xmp.impl;

import com.adobe.xmp.XMPException;

class ParseState {
    private int pos = 0;
    private String str;

    public ParseState(String str) {
        this.str = str;
    }

    public char ch() {
        return this.pos < this.str.length() ? this.str.charAt(this.pos) : 0;
    }

    public char ch(int i) {
        return i < this.str.length() ? this.str.charAt(i) : 0;
    }

    public int gatherInt(String str, int i) throws XMPException {
        int ch = ch(this.pos);
        int i2 = 0;
        Object obj = null;
        while (48 <= ch && ch <= 57) {
            i2 = (i2 * 10) + (ch - 48);
            this.pos++;
            ch = ch(this.pos);
            obj = 1;
        }
        if (obj != null) {
            return i2 > i ? i : i2 < 0 ? 0 : i2;
        } else {
            throw new XMPException(str, 5);
        }
    }

    public boolean hasNext() {
        return this.pos < this.str.length();
    }

    public int length() {
        return this.str.length();
    }

    public int pos() {
        return this.pos;
    }

    public void skip() {
        this.pos++;
    }
}
