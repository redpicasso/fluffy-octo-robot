package com.facebook.soloader;

import javax.annotation.Nullable;

class MergedSoMapping {
    @Nullable
    static String mapLibName(String str) {
        return null;
    }

    MergedSoMapping() {
    }

    static void invokeJniOnload(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown library: ");
        stringBuilder.append(str);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}
