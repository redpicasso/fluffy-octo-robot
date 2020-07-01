package com.facebook.react.bridge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ReadableMap {
    @Nullable
    ReadableArray getArray(@Nonnull String str);

    boolean getBoolean(@Nonnull String str);

    double getDouble(@Nonnull String str);

    @Nonnull
    Dynamic getDynamic(@Nonnull String str);

    @Nonnull
    Iterator<Entry<String, Object>> getEntryIterator();

    int getInt(@Nonnull String str);

    @Nullable
    ReadableMap getMap(@Nonnull String str);

    @Nullable
    String getString(@Nonnull String str);

    @Nonnull
    ReadableType getType(@Nonnull String str);

    boolean hasKey(@Nonnull String str);

    boolean isNull(@Nonnull String str);

    @Nonnull
    ReadableMapKeySetIterator keySetIterator();

    @Nonnull
    HashMap<String, Object> toHashMap();
}
