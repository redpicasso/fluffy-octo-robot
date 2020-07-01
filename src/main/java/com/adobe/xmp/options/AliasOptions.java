package com.adobe.xmp.options;

import com.adobe.xmp.XMPException;

public final class AliasOptions extends Options {
    public static final int PROP_ARRAY = 512;
    public static final int PROP_ARRAY_ALTERNATE = 2048;
    public static final int PROP_ARRAY_ALT_TEXT = 4096;
    public static final int PROP_ARRAY_ORDERED = 1024;
    public static final int PROP_DIRECT = 0;

    public AliasOptions(int i) throws XMPException {
        super(i);
    }

    protected String defineOptionName(int i) {
        return i != 0 ? i != 512 ? i != 1024 ? i != 2048 ? i != 4096 ? null : "ARRAY_ALT_TEXT" : "ARRAY_ALTERNATE" : "ARRAY_ORDERED" : "ARRAY" : "PROP_DIRECT";
    }

    protected int getValidOptions() {
        return 7680;
    }

    public boolean isArray() {
        return getOption(512);
    }

    public boolean isArrayAltText() {
        return getOption(4096);
    }

    public boolean isArrayAlternate() {
        return getOption(2048);
    }

    public boolean isArrayOrdered() {
        return getOption(1024);
    }

    public boolean isSimple() {
        return getOptions() == 0;
    }

    public AliasOptions setArray(boolean z) {
        setOption(512, z);
        return this;
    }

    public AliasOptions setArrayAltText(boolean z) {
        setOption(7680, z);
        return this;
    }

    public AliasOptions setArrayAlternate(boolean z) {
        setOption(3584, z);
        return this;
    }

    public AliasOptions setArrayOrdered(boolean z) {
        setOption(1536, z);
        return this;
    }

    public PropertyOptions toPropertyOptions() throws XMPException {
        return new PropertyOptions(getOptions());
    }
}
