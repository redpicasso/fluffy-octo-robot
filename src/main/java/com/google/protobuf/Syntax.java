package com.google.protobuf;

import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.Internal.EnumLiteMap;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public enum Syntax implements EnumLite {
    SYNTAX_PROTO2(0),
    SYNTAX_PROTO3(1),
    UNRECOGNIZED(-1);
    
    public static final int SYNTAX_PROTO2_VALUE = 0;
    public static final int SYNTAX_PROTO3_VALUE = 1;
    private static final EnumLiteMap<Syntax> internalValueMap = null;
    private final int value;

    static {
        internalValueMap = new EnumLiteMap<Syntax>() {
            public Syntax findValueByNumber(int i) {
                return Syntax.forNumber(i);
            }
        };
    }

    public final int getNumber() {
        return this.value;
    }

    @Deprecated
    public static Syntax valueOf(int i) {
        return forNumber(i);
    }

    public static Syntax forNumber(int i) {
        if (i != 0) {
            return i != 1 ? null : SYNTAX_PROTO3;
        } else {
            return SYNTAX_PROTO2;
        }
    }

    public static EnumLiteMap<Syntax> internalGetValueMap() {
        return internalValueMap;
    }

    private Syntax(int i) {
        this.value = i;
    }
}
