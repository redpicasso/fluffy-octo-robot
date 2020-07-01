package com.adobe.xmp.options;

import com.adobe.xmp.XMPException;
import com.bumptech.glide.load.Key;

public final class SerializeOptions extends Options {
    public static final int ENCODE_UTF16BE = 2;
    public static final int ENCODE_UTF16LE = 3;
    public static final int ENCODE_UTF8 = 0;
    private static final int ENCODING_MASK = 3;
    public static final int EXACT_PACKET_LENGTH = 512;
    public static final int INCLUDE_THUMBNAIL_PAD = 256;
    private static final int LITTLEENDIAN_BIT = 1;
    public static final int OMIT_PACKET_WRAPPER = 16;
    public static final int OMIT_XMPMETA_ELEMENT = 4096;
    public static final int READONLY_PACKET = 32;
    public static final int SORT = 8192;
    public static final int USE_CANONICAL_FORMAT = 128;
    public static final int USE_COMPACT_FORMAT = 64;
    private static final int UTF16_BIT = 2;
    private int baseIndent = 0;
    private String indent = "  ";
    private String newline = ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE;
    private boolean omitVersionAttribute = false;
    private int padding = 2048;

    public SerializeOptions(int i) throws XMPException {
        super(i);
    }

    public Object clone() throws CloneNotSupportedException {
        try {
            SerializeOptions serializeOptions = new SerializeOptions(getOptions());
            serializeOptions.setBaseIndent(this.baseIndent);
            serializeOptions.setIndent(this.indent);
            serializeOptions.setNewline(this.newline);
            serializeOptions.setPadding(this.padding);
            return serializeOptions;
        } catch (XMPException unused) {
            return null;
        }
    }

    protected String defineOptionName(int i) {
        return i != 16 ? i != 32 ? i != 64 ? i != 256 ? i != 512 ? i != 4096 ? i != 8192 ? null : "NORMALIZED" : "OMIT_XMPMETA_ELEMENT" : "EXACT_PACKET_LENGTH" : "INCLUDE_THUMBNAIL_PAD" : "USE_COMPACT_FORMAT" : "READONLY_PACKET" : "OMIT_PACKET_WRAPPER";
    }

    public int getBaseIndent() {
        return this.baseIndent;
    }

    public boolean getEncodeUTF16BE() {
        return (getOptions() & 3) == 2;
    }

    public boolean getEncodeUTF16LE() {
        return (getOptions() & 3) == 3;
    }

    public String getEncoding() {
        return getEncodeUTF16BE() ? "UTF-16BE" : getEncodeUTF16LE() ? "UTF-16LE" : Key.STRING_CHARSET_NAME;
    }

    public boolean getExactPacketLength() {
        return getOption(512);
    }

    public boolean getIncludeThumbnailPad() {
        return getOption(256);
    }

    public String getIndent() {
        return this.indent;
    }

    public String getNewline() {
        return this.newline;
    }

    public boolean getOmitPacketWrapper() {
        return getOption(16);
    }

    public boolean getOmitVersionAttribute() {
        return this.omitVersionAttribute;
    }

    public boolean getOmitXmpMetaElement() {
        return getOption(4096);
    }

    public int getPadding() {
        return this.padding;
    }

    public boolean getReadOnlyPacket() {
        return getOption(32);
    }

    public boolean getSort() {
        return getOption(8192);
    }

    public boolean getUseCanonicalFormat() {
        return getOption(128);
    }

    public boolean getUseCompactFormat() {
        return getOption(64);
    }

    protected int getValidOptions() {
        return 13168;
    }

    public SerializeOptions setBaseIndent(int i) {
        this.baseIndent = i;
        return this;
    }

    public SerializeOptions setEncodeUTF16BE(boolean z) {
        setOption(3, false);
        setOption(2, z);
        return this;
    }

    public SerializeOptions setEncodeUTF16LE(boolean z) {
        setOption(3, false);
        setOption(3, z);
        return this;
    }

    public SerializeOptions setExactPacketLength(boolean z) {
        setOption(512, z);
        return this;
    }

    public SerializeOptions setIncludeThumbnailPad(boolean z) {
        setOption(256, z);
        return this;
    }

    public SerializeOptions setIndent(String str) {
        this.indent = str;
        return this;
    }

    public SerializeOptions setNewline(String str) {
        this.newline = str;
        return this;
    }

    public SerializeOptions setOmitPacketWrapper(boolean z) {
        setOption(16, z);
        return this;
    }

    public SerializeOptions setOmitXmpMetaElement(boolean z) {
        setOption(4096, z);
        return this;
    }

    public SerializeOptions setPadding(int i) {
        this.padding = i;
        return this;
    }

    public SerializeOptions setReadOnlyPacket(boolean z) {
        setOption(32, z);
        return this;
    }

    public SerializeOptions setSort(boolean z) {
        setOption(8192, z);
        return this;
    }

    public SerializeOptions setUseCanonicalFormat(boolean z) {
        setOption(128, z);
        return this;
    }

    public SerializeOptions setUseCompactFormat(boolean z) {
        setOption(64, z);
        return this;
    }
}
