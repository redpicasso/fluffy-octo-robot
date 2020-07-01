package com.drew.metadata;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;

public class Tag {
    @NotNull
    private final Directory _directory;
    private final int _tagType;

    public Tag(int i, @NotNull Directory directory) {
        this._tagType = i;
        this._directory = directory;
    }

    public int getTagType() {
        return this._tagType;
    }

    @NotNull
    public String getTagTypeHex() {
        return String.format("0x%04x", new Object[]{Integer.valueOf(this._tagType)});
    }

    @Nullable
    public String getDescription() {
        return this._directory.getDescription(this._tagType);
    }

    public boolean hasTagName() {
        return this._directory.hasTagName(this._tagType);
    }

    @NotNull
    public String getTagName() {
        return this._directory.getTagName(this._tagType);
    }

    @NotNull
    public String getDirectoryName() {
        return this._directory.getName();
    }

    @NotNull
    public String toString() {
        String description = getDescription();
        if (description == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this._directory.getString(getTagType()));
            stringBuilder.append(" (unable to formulate description)");
            description = stringBuilder.toString();
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[");
        stringBuilder2.append(this._directory.getName());
        stringBuilder2.append("] ");
        stringBuilder2.append(getTagName());
        stringBuilder2.append(" - ");
        stringBuilder2.append(description);
        return stringBuilder2.toString();
    }
}
