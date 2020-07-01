package com.drew.metadata.ico;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.TagDescriptor;

public class IcoDescriptor extends TagDescriptor<IcoDirectory> {
    public IcoDescriptor(@NotNull IcoDirectory icoDirectory) {
        super(icoDirectory);
    }

    public String getDescription(int i) {
        if (i == 1) {
            return getImageTypeDescription();
        }
        if (i == 2) {
            return getImageWidthDescription();
        }
        if (i == 3) {
            return getImageHeightDescription();
        }
        if (i != 4) {
            return super.getDescription(i);
        }
        return getColourPaletteSizeDescription();
    }

    @Nullable
    public String getImageTypeDescription() {
        return getIndexedDescription(1, 1, "Icon", "Cursor");
    }

    @Nullable
    public String getImageWidthDescription() {
        Integer integer = ((IcoDirectory) this._directory).getInteger(2);
        if (integer == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(integer.intValue() == 0 ? 256 : integer.intValue());
        stringBuilder.append(" pixels");
        return stringBuilder.toString();
    }

    @Nullable
    public String getImageHeightDescription() {
        Integer integer = ((IcoDirectory) this._directory).getInteger(3);
        if (integer == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(integer.intValue() == 0 ? 256 : integer.intValue());
        stringBuilder.append(" pixels");
        return stringBuilder.toString();
    }

    @Nullable
    public String getColourPaletteSizeDescription() {
        Integer integer = ((IcoDirectory) this._directory).getInteger(4);
        if (integer == null) {
            return null;
        }
        String str;
        if (integer.intValue() == 0) {
            str = "No palette";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(integer);
            stringBuilder.append(" colour");
            stringBuilder.append(integer.intValue() == 1 ? "" : "s");
            str = stringBuilder.toString();
        }
        return str;
    }
}
